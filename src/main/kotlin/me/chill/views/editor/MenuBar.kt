package me.chill.views.editor

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.*
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.UNDO
import javafx.scene.control.Menu
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
        addItem("Folder", FOLDER_ALT)

        separator()

        addItem("Markdown (.md)", FILE_ALT)
        addItem("Untitled document", FILE_ALT)
      }

      addItem("Open folder", FOLDER_OPEN_ALT, OPEN_FOLDER) {
        controller.openFolder(primaryStage)
      }

      separator()

      addItem("Save", SAVE, SAVE_FILE, controller::saveFile)
      addItem("Save all", accelerator = SAVE_ALL, action = controller::saveAll)

      separator()

      addItem("Import from VCS", action = controller::importFromVCS)
      menu("Export as").apply {
        addItem("PDF (.pdf)", action = controller::saveAll)
      }

      separator()

      addItem("Options", COG, OPTIONS, controller::launchOptions)
      addItem("Exit", accelerator = EXIT, action = controller::exit)
    }

    menu("Edit") {
      addItem("Undo", UNDO, Keymap.UNDO, controller::undoAction)
      item("Redo").apply {
        graphic = addGlyph(UNDO)
          .apply { rotate = 180.0 }
        accelerator = REDO.keyCombination
        action(controller::redoAction)
      }

      separator()

      addItem("Cut", FontAwesomeIcon.CUT, Keymap.CUT, controller::cut)
      addItem("Copy", FontAwesomeIcon.COPY, Keymap.COPY, controller::copy)
      addItem("Paste", FontAwesomeIcon.PASTE, Keymap.PASTE, controller::paste)

      separator()

      addItem("Bold", FontAwesomeIcon.BOLD, Keymap.BOLD)
      addItem("Italic", FontAwesomeIcon.ITALIC, Keymap.ITALIC)
      addItem("Underline", FontAwesomeIcon.UNDERLINE, Keymap.UNDERLINE)
      addItem("Strikethrough", STRIKETHROUGH)
    }

    menu("View") {
      checkmenuitem("Toggle toolbar").action(controller::toggleToolBar)
    }
  }

  private fun Menu.addItem(
    title: String,
    icon: FontAwesomeIcon? = null,
    accelerator: Keymap? = null,
    action: () -> Unit = { }) =
    item(title).apply {
      icon?.let { graphic = addGlyph(it) }
      accelerator?.let { this@apply.accelerator = it.keyCombination }
      action(action)
    }

  private fun addGlyph(glyph: FontAwesomeIcon) = glyphFactory.make(glyph)
}