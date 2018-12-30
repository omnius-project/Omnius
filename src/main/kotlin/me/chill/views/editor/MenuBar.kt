package me.chill.views.editor

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.UNDO
import javafx.scene.control.Menu
import javafx.scene.input.KeyCombination
import me.chill.controllers.EditorController
import me.chill.keymap.ActionMap
import me.chill.utility.glyphtools.GlyphFactory
import me.chill.views.editor.ToolBar.Position.LEFT
import me.chill.views.editor.ToolBar.Position.TOP
import tornadofx.*

class MenuBar : View() {

  private val controller: EditorController by inject()
  private val glyphFactory = GlyphFactory.Builder().build()

  // TODO: Make a keymap system
  override val root = menubar {
    menu("File") {
      menu("New").apply {
        addListItem(ActionMap.NEW_FOLDER)

        separator()

        addListItem(ActionMap.NEW_MARKDOWN_FILE)
        addListItem(ActionMap.NEW_UNTITLED_FILE)
      }

      addItem(ActionMap.OPEN_FOLDER) {
        controller.openFolder(primaryStage)
      }

      separator()

      addItem(ActionMap.SAVE_FILE, controller::saveFile)
      addItem(ActionMap.SAVE_ALL, action = controller::saveAll)

      separator()

      addItem(ActionMap.IMPORT_VCS, controller::importFromVCS)
      addItem(ActionMap.EXPORT_PDF)

      separator()

      addItem(ActionMap.OPTIONS, controller::launchOptions)
      addItem(ActionMap.EXIT, controller::exit)
    }

    menu("Edit") {
      addItem(ActionMap.UNDO, controller::undoAction)
      item("Redo").apply {
        graphic = addGlyph(UNDO)
          .apply { rotate = 180.0 }
        accelerator = ActionMap.REDO.shortCut
        action(controller::redoAction)
      }

      separator()

      addItem(ActionMap.CUT, action = controller::cut)
      addItem(ActionMap.COPY, action = controller::copy)
      addItem(ActionMap.PASTE, action = controller::paste)

      separator()

      addItem(ActionMap.BOLD)
      addItem(ActionMap.ITALIC)
      addItem(ActionMap.UNDERLINE)
      addItem(ActionMap.STRIKETHROUGH)
    }

    menu("View") {
      menu("Toolbar") {

        togglegroup {
          radiomenuitem("Top").apply {
            toggleGroup = this@togglegroup
            isSelected = true
            action { controller.moveToolBar(TOP) }
          }
          radiomenuitem("Left") {
            toggleGroup = this@togglegroup
            action { controller.moveToolBar(LEFT) }
          }
        }

        separator()

        checkmenuitem("Toggle toolbar").apply {
          isSelected = true
          action(controller::toggleToolBar)
        }
      }
    }
  }

  private fun Menu.addItem(
    title: String,
    icon: FontAwesomeIcon? = null,
    accelerator: KeyCombination? = null,
    action: () -> Unit = { }) =
    item(title).apply {
      icon?.let { graphic = addGlyph(it) }
      accelerator?.let { this@apply.accelerator = it }
      action(action)
    }

  private fun Menu.addItem(
    actionMap: ActionMap,
    action: () -> Unit = { }) = with(actionMap) { addItem(actionName, icon, shortCut, action) }

  private fun Menu.addListItem(
    actionMap: ActionMap,
    action: () -> Unit = { }) =
    with(actionMap) {
      addItem(actionName.substringAfter(this@addListItem.text), icon, shortCut, action)
    }

  private fun addGlyph(glyph: FontAwesomeIcon) = glyphFactory.make(glyph)
}