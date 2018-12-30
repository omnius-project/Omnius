package me.chill.views.editor

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.scene.control.Menu
import javafx.scene.input.KeyCombination
import me.chill.actionmap.ActionMap
import me.chill.actionmap.ActionMap.*
import me.chill.actionmap.ActionMapObservable
import me.chill.actionmap.ActionMapObserver
import me.chill.utility.glyphtools.GlyphFactory
import tornadofx.*

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
        listItem(NEW_FOLDER)

        separator()

        listItem(NEW_MARKDOWN_FILE)
        listItem(NEW_UNTITLED_FILE)
      }

      item(OPEN_FOLDER)

      separator()

      items(SAVE_FILE, SAVE_ALL)

      separator()

      items(IMPORT_VCS, EXPORT_PDF)

      separator()

      items(OPTIONS, EXIT)
    }

    menu("Edit") {
      item(UNDO)
      item(REDO) { rotate = 180.0 }

      separator()

      items(COPY, CUT, PASTE)

      separator()

      items(BOLD, ITALIC, UNDERLINE, STRIKETHROUGH)
    }

    menu("View") {
      menu("Toolbar") {
        menu("Position") {
          togglegroup {
            radiomenuitem("Top").apply {
              toggleGroup = this@togglegroup
              isSelected = true
              action { notifyObservers(MOVE_TOOLBAR_TOP) }
            }
            radiomenuitem("Left") {
              toggleGroup = this@togglegroup
              action { notifyObservers(MOVE_TOOLBAR_LEFT) }
            }
          }
        }

        separator()

        checkmenuitem("Toggle toolbar").apply {
          isSelected = true
          action { notifyObservers(TOGGLE_TOOBAR_VISIBILITY) }
        }
      }
    }
  }

  private fun Menu.item(
    title: String,
    icon: FontAwesomeIcon? = null,
    iconProperties: FontAwesomeIconView.() -> Unit,
    accelerator: KeyCombination? = null,
    actionMap: ActionMap) =
    this@item.item(title).apply {
      icon?.let { graphic = addGlyph(it).apply(iconProperties) }
      accelerator?.let { this@apply.accelerator = it }
      action {
        notifyObservers(actionMap)
      }
    }

  private fun Menu.item(
    actionMap: ActionMap,
    iconProperties: FontAwesomeIconView.() -> Unit = {  }) =
    with(actionMap) { this@item.item(actionName, icon, iconProperties, shortCut, actionMap) }

  private fun Menu.listItem(
    actionMap: ActionMap,
    iconProperties: FontAwesomeIconView.() -> Unit = {  }) =
    with(actionMap) {
      item(
        extractActionName(this),
        icon,
        iconProperties,
        shortCut,
        actionMap
      )
    }

  private fun Menu.items(vararg actionMaps: ActionMap) {
    actionMaps.forEach { item(it) }
  }

  private fun Menu.extractActionName(actionMap: ActionMap) = actionMap.actionName.substringAfter(text)

  private fun addGlyph(glyph: FontAwesomeIcon) = glyphFactory.make(glyph)
}