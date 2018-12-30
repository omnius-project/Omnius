package me.chill.views.editor

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.UNDO
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.scene.control.Menu
import javafx.scene.input.KeyCombination
import me.chill.controllers.EditorController
import me.chill.keymap.ActionMap
import me.chill.keymap.ActionMap.*
import me.chill.listeners.ActionMapObservable
import me.chill.listeners.ActionMapObserver
import me.chill.utility.glyphtools.GlyphFactory
import me.chill.views.editor.ToolBar.Position.LEFT
import me.chill.views.editor.ToolBar.Position.TOP
import tornadofx.*
import tornadofx.WizardStyles.Companion.graphic

class MenuBar : View(), ActionMapObservable {

  private val listeners = mutableListOf<ActionMapObserver>()
  private val glyphFactory = GlyphFactory.Builder().build()

  override fun addObserver(actionMapObserver: ActionMapObserver) {
    listeners.add(actionMapObserver)
  }

  override fun removeObserver(actionMapObserver: ActionMapObserver) {
    listeners.remove(actionMapObserver)
  }

  override fun notifyObservers(actionMap: ActionMap) {
    listeners.forEach { it.update(actionMap) }
  }

  override val root = menubar {
    menu("File") {
      menu("New").apply {
        addListItem(NEW_FOLDER)

        separator()

        addListItem(NEW_MARKDOWN_FILE)
        addListItem(NEW_UNTITLED_FILE)
      }

      addItem(OPEN_FOLDER)

      separator()

      addItem(SAVE_FILE)
      addItem(SAVE_ALL)

      separator()

      addItem(IMPORT_VCS)
      addItem(EXPORT_PDF)

      separator()

      addItem(OPTIONS)
      addItem(EXIT)
    }

    menu("Edit") {
      addItem(ActionMap.UNDO)
      addItem(REDO) {
        rotate = 180.0
      }

      separator()

      addItem(CUT)
      addItem(COPY)
      addItem(PASTE)

      separator()

      addItem(BOLD)
      addItem(ITALIC)
      addItem(UNDERLINE)
      addItem(STRIKETHROUGH)
    }

    menu("View") {
      menu("Toolbar") {

        togglegroup {
          radiomenuitem("Top").apply {
            toggleGroup = this@togglegroup
            isSelected = true
//            action { controller.moveToolBar(TOP) }
          }
          radiomenuitem("Left") {
            toggleGroup = this@togglegroup
//            action { controller.moveToolBar(LEFT) }
          }
        }

        separator()

        checkmenuitem("Toggle toolbar").apply {
          isSelected = true
//          action(controller::toggleToolBar)
        }
      }
    }
  }

  private fun Menu.addItem(
    title: String,
    icon: FontAwesomeIcon? = null,
    iconProperties: FontAwesomeIconView.() -> Unit,
    accelerator: KeyCombination? = null,
    actionMap: ActionMap) =
    item(title).apply {
      icon?.let { graphic = addGlyph(it).apply(iconProperties) }
      accelerator?.let { this@apply.accelerator = it }
      action {
        notifyObservers(actionMap)
      }
    }

  private fun Menu.addItem(
    actionMap: ActionMap,
    iconProperties: FontAwesomeIconView.() -> Unit = {  }) =
    with(actionMap) { addItem(actionName, icon, iconProperties, shortCut, actionMap) }

  private fun Menu.addListItem(
    actionMap: ActionMap,
    iconProperties: FontAwesomeIconView.() -> Unit = {  }) =
    with(actionMap) {
      addItem(
        actionName.substringAfter(this@addListItem.text),
        icon,
        iconProperties,
        shortCut,
        actionMap
      )
    }

  private fun addGlyph(glyph: FontAwesomeIcon) = glyphFactory.make(glyph)
}