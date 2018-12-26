package me.chill.views.editor

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.*
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import me.chill.controllers.EditorController
import me.chill.styles.MenuBarStyles.Companion.tbar
import tornadofx.*

class ToolBar : View() {
  private val controller: EditorController by inject()

  override val root = menubar {
    addClass(tbar)
    menu {
      graphic = addGlyph(FOLDER_OPEN)
      tooltip("Open Folder")
      setOnMouseClicked { println("Hello") }
    }.action { controller.openFolder(primaryStage) }

    menu {
      graphic = addGlyph(SAVE)
      tooltip("Save File")
    }

    menu {
      graphic = addGlyph(CUT)
      tooltip("Cut")
    }

    menu {
      graphic = addGlyph(COPY)
      tooltip("Copy")
    }

    menu {
      graphic = addGlyph(PASTE)
      tooltip("Paste")
    }
  }

  private fun addGlyph(glyph: FontAwesomeIcon) =
    FontAwesomeIconView(glyph).apply { glyphSize = 24 }
}