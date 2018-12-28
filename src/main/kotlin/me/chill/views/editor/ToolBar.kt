package me.chill.views.editor

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.*
import javafx.geometry.Orientation.VERTICAL
import me.chill.controllers.EditorController
import me.chill.utility.glyphtools.GlyphFactory
import tornadofx.*

class ToolBar : View() {

  private val controller: EditorController by inject()
  private val glyphFactory = GlyphFactory.Builder().glyphSize(24).build()

  override val root = toolbar {
    button {
      graphic = addGlyph(FILE_ALT)
      tooltip("New File")
    }

    button {
      graphic = addGlyph(FOLDER_OPEN_ALT)
      tooltip("Open Folder")
      setOnMouseClicked { controller.openFolder(primaryStage) }
    }

    button {
      graphic = addGlyph(SAVE)
      tooltip("Save File")
    }

    separator(VERTICAL)

    button {
      graphic = addGlyph(CUT)
      tooltip("Cut")
    }

    button {
      graphic = addGlyph(COPY)
      tooltip("Copy")
    }

    button {
      graphic = addGlyph(PASTE)
      tooltip("Paste")
    }
  }

  private fun addGlyph(glyph: FontAwesomeIcon) = glyphFactory.make(glyph)
}