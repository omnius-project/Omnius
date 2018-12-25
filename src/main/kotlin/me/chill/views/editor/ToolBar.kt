package me.chill.views.editor

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.*
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.geometry.Orientation
import javafx.geometry.Orientation.VERTICAL
import javafx.scene.control.SeparatorMenuItem
import me.chill.styles.MenuBarStyles.Companion.tbar
import tornadofx.*

class ToolBar : View() {
  override val root = vbox {
    addClass(tbar)
    menubar {
      menu {
        graphic = setGlyph(FOLDER_OPEN)
        tooltip("Open Folder")
      }

      menu {
        graphic = setGlyph(SAVE)
        tooltip("Save File")
      }

      separator(VERTICAL)

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

      separator()
    }
  }

  private fun setGlyph(glyph: FontAwesomeIcon) =
    FontAwesomeIconView(glyph).apply { glyphSize = 24 }
}