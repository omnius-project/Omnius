package me.chill.views.editor

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.*
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.PASTE
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.UNDO
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import me.chill.controllers.EditorController
import me.chill.keymap.Keymap
import me.chill.keymap.Keymap.*
import tornadofx.*

class MenuBar : View() {
  private val controller: EditorController by inject()

  // TODO: Make a keymap system
  override val root = menubar {
    menu("File") {
      item("Open folder").apply {
        graphic = addGraphic(FOLDER_OPEN)
        accelerator = OPEN_FOLDER.keyCombination
        action { controller.openFolder(primaryStage) }
      }

      separator()

      item("Save").apply {
        graphic = addGraphic(SAVE)
        accelerator = SAVE_FILE.keyCombination
        action(controller::saveFile)
      }

      item("Save All").apply {
        accelerator = SAVE_ALL.keyCombination
        action(controller::saveAll)
      }

      separator()

      item("Import from VCS").action(controller::importFromVCS)
      item("Export").action(controller::export)

      separator()

      item("Options").apply {
        graphic = addGraphic(COG)
        accelerator = OPTIONS.keyCombination
        action(controller::launchOptions)
      }

      item("Exit").apply {
        accelerator = EXIT.keyCombination
        action(controller::exit)
      }
    }

    menu("Edit") {
      item("Undo").apply {
        graphic = addGraphic(UNDO)
        accelerator = Keymap.UNDO.keyCombination
        action(controller::undoAction)
      }

      item("Redo").apply {
        graphic = addGraphic(UNDO)
          .apply { rotate = 180.0 }
        accelerator = REDO.keyCombination
        action(controller::redoAction)
      }

      separator()

      item("Cut").apply {
        graphic = addGraphic(FontAwesomeIcon.CUT)
        accelerator = Keymap.CUT.keyCombination
        action(controller::cut)
      }

      item("Copy").apply {
        graphic = addGraphic(FontAwesomeIcon.COPY)
        accelerator = Keymap.COPY.keyCombination
        action(controller::copy)
      }

      item("Paste").apply {
        graphic = addGraphic(PASTE)
        accelerator = Keymap.PASTE.keyCombination
        action(controller::paste)
      }
    }

    menu("View") {
      item("Visible Windows")
    }
  }

  private fun addGraphic(glyph: FontAwesomeIcon) =
    FontAwesomeIconView(glyph).apply { glyphSize = 20 }
}