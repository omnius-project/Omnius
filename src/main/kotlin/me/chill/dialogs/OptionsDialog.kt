package me.chill.dialogs

import com.google.gson.JsonObject
import javafx.geometry.Insets
import javafx.scene.control.CheckBox
import javafx.scene.control.ComboBox
import me.chill.actionmap.ActionMap
import me.chill.actionmap.ActionMap.OPTIONS_SAVE
import me.chill.actionmap.ActionMapObservable
import me.chill.actionmap.ActionMapObserver
import me.chill.configuration.ConfigurationManager.ConfigurationKeys.FONT_SIZE
import me.chill.configuration.ConfigurationManager.ConfigurationKeys.TOOLBAR_VISIBILITY
import me.chill.controllers.EditorController
import me.chill.models.EditorModel
import me.chill.utility.extensions.addProperty
import tornadofx.*

/**
 * Dialog to allow users to edit their preferences, which is then saved to the configuration
 * file.
 *
 * When the user saves the options, a [OPTIONS_SAVE] event is triggered, notifying all
 * observers, which react accordingly.
 *
 * Is a [ActionMapObservable]
 * - Will be observed by [EditorController] when this dialog is inflated
 *
 * @see [EditorController]
 */
class OptionsDialog : Fragment("Options"), ActionMapObservable {

  private val listeners = mutableListOf<ActionMapObserver>()

  private lateinit var toolBarVisibilityCheckBox: CheckBox
  private lateinit var fontSizeComboBox: ComboBox<String>

  override fun addObserver(actionMapObserver: ActionMapObserver) {
    listeners.add(actionMapObserver)
  }

  override fun removeObserver(actionMapObserver: ActionMapObserver) {
    listeners.remove(actionMapObserver)
  }

  override fun notifyObservers(actionMap: ActionMap, data: JsonObject?) {
    listeners.forEach { it.update(actionMap, data) }
  }

  override val root = gridpane {
    prefWidth = 400.0
    padding = Insets(20.0)

    row {
      hbox {
        label("Toolbar Visibility") {
          style {
            paddingRight = 20
          }
        }

        toolBarVisibilityCheckBox = CheckBox().apply { isSelected = EditorModel.toolBarVisibility }
        add(toolBarVisibilityCheckBox)
      }
    }

    row {
      hbox {
        label("Font Size:") {
          style {
            paddingRight = 20
          }
        }

        val fontSizes = (9..50 step 3).toList().map { it.toString() }.toTypedArray()
        fontSizeComboBox = ComboBox(observableList(*fontSizes))
          .apply {
            prefWidth = 200.0
            selectionModel.select(fontSizes.first { it.toInt() == EditorModel.fontSize })
          }
        add(fontSizeComboBox)
      }
    }

    row {
      button("Save & Apply").action {
        val options = JsonObject()
          .addProperty(
            FONT_SIZE,
            fontSizeComboBox.selectionModel.selectedItem.toInt()
          )
          .addProperty(
            TOOLBAR_VISIBILITY,
            toolBarVisibilityCheckBox.isSelected
          )
        notifyObservers(OPTIONS_SAVE, options)
        close()
      }
    }
  }
}