package me.chill.views.editor

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.*
import javafx.geometry.Orientation.VERTICAL
import javafx.scene.control.ToolBar
import me.chill.controllers.EditorController
import me.chill.utility.glyphtools.GlyphFactory
import tornadofx.*

// TODO: Make the visibility of the tool bar an observable value
class ToolBar : View() {

  enum class Position {
    TOP, LEFT
  }

  private val controller: EditorController by inject()
  private val glyphFactory = GlyphFactory.Builder().glyphSize(24).build()

  override val root = toolbar {
    managedProperty().bind(visibleProperty())

    button(FILE_ALT, "New File")
    button(FOLDER_OPEN_ALT, "Open Folder") {
      controller.openFolder(primaryStage)
    }
    button(SAVE, "Save File")

    separator(VERTICAL)

    button(CUT, "Cut")
    button(COPY, "Copy")
    button(PASTE, "Paste")

    separator(VERTICAL)

    button(BOLD, "Bold")
    button(ITALIC, "Italicize")
    button(UNDERLINE, "Underline")
    button(STRIKETHROUGH, "Strikethrough")

    separator(VERTICAL)
  }

  private fun ToolBar.button(icon: FontAwesomeIcon, tooltip: String, action: () -> Unit = { }) =
    button {
      prefWidth = 40.0
      prefHeight = 40.0
      graphic = addGlyph(icon)
      tooltip(tooltip)
      action(action)
    }

  private fun addGlyph(glyph: FontAwesomeIcon) = glyphFactory.make(glyph)
}