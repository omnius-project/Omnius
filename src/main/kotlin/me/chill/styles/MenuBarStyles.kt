package me.chill.styles

import javafx.scene.Cursor.HAND
import javafx.scene.paint.Color.TRANSPARENT
import javafx.scene.paint.Color.WHITE
import tornadofx.*

class MenuBarStyles : Stylesheet() {
  companion object {
    val tbar by cssclass()

    private val subtleGrey = c("#EDEDED")
  }

  init {
    menuItem {
      graphicContainer {
        padding = box(0.px, 10.px, 0.px, 0.px)
      }

      and(hover) {
        cursor = HAND
      }
    }

    tbar {
      menuBar {
        backgroundColor += WHITE
        borderColor += box(
          TRANSPARENT,
          TRANSPARENT,
          subtleGrey,
          TRANSPARENT
        )
        borderWidth += box(0.px, 0.px, 1.px, 0.px)
      }

      menu {
        padding = box(10.px)

        and(hover) {
          cursor = HAND
          backgroundColor += subtleGrey
        }
      }
    }
  }
}