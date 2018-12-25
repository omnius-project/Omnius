package me.chill.views.editor

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.*
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.scene.input.KeyCombination
import me.chill.controllers.EditorController
import me.chill.styles.MenuBarStyles.Companion.tbar
import tornadofx.*

class ToolBar : View() {
  private val controller: EditorController by inject()

  override val root = vbox {
    addClass(tbar)
    menubar {
      menu {
        graphic = setGlyph(FOLDER_OPEN)
        tooltip("Open Folder")
      }.action { controller.openFolder(primaryStage) }

      menu {
        graphic = setGlyph(SAVE)
        tooltip("Save File")
      }

      menu {
        graphic = setGlyph(CUT)
        tooltip("Cut")
      }

      menu {
        graphic = setGlyph(COPY)
        tooltip("Copy")
      }

      menu {
        graphic = setGlyph(PASTE)
        tooltip("Paste")
      }
    }
  }

  private fun setGlyph(glyph: FontAwesomeIcon) =
    FontAwesomeIconView(glyph).apply { glyphSize = 24 }
}