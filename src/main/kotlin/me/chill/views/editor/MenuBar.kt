package me.chill.views.editor

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.*
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.PASTE
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.UNDO
import me.chill.controllers.EditorController
import me.chill.keymap.Keymap
import me.chill.keymap.Keymap.*
import me.chill.utility.glyphtools.GlyphFactory
import tornadofx.*

class MenuBar : View() {

  private val controller: EditorController by inject()
  private val glyphFactory = GlyphFactory.Builder().build()

  // TODO: Make a keymap system
  override val root = menubar {
    menu("File") {
      menu("New").apply {
        item("Folder").apply {
          graphic = addGlyph(FOLDER_ALT)
        }

        separator()

        item("Markdown document").apply {
          graphic = addGlyph(FILE)
        }
        item("Untitled document")
      }

      // TODO: Add options for new file and folder
      item("Open folder").apply {
        graphic = addGlyph(FOLDER_OPEN_ALT)
        accelerator = OPEN_FOLDER.keyCombination
        action { controller.openFolder(primaryStage) }
      }

      separator()

      item("Save").apply {
        graphic = addGlyph(SAVE)
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
        graphic = addGlyph(COG)
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
        graphic = addGlyph(UNDO)
        accelerator = Keymap.UNDO.keyCombination
        action(controller::undoAction)
      }

      item("Redo").apply {
        graphic = addGlyph(UNDO)
          .apply { rotate = 180.0 }
        accelerator = REDO.keyCombination
        action(controller::redoAction)
      }

      separator()

      item("Cut").apply {
        graphic = addGlyph(FontAwesomeIcon.CUT)
        accelerator = Keymap.CUT.keyCombination
        action(controller::cut)
      }

      item("Copy").apply {
        graphic = addGlyph(FontAwesomeIcon.COPY)
        accelerator = Keymap.COPY.keyCombination
        action(controller::copy)
      }

      item("Paste").apply {
        graphic = addGlyph(PASTE)
        accelerator = Keymap.PASTE.keyCombination
        action(controller::paste)
      }
    }

    menu("View") {
      item("Visible Windows")
    }
  }

  private fun addGlyph(glyph: FontAwesomeIcon) = glyphFactory.make(glyph)
}