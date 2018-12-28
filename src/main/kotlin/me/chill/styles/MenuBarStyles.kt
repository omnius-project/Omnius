package me.chill.styles

import javafx.scene.Cursor.HAND
import javafx.scene.paint.Color.*
import me.chill.styles.Styles.Companion.subtleGrey
import tornadofx.*

class MenuBarStyles : Stylesheet() {

  init {
    menuBar {
      backgroundColor += WHITE
      borderColor += box(TRANSPARENT, TRANSPARENT, subtleGrey, TRANSPARENT)
      borderWidth += box(0.px, 0.px, 1.px, 0.px)

      menu {
        and(hover) {
          cursor = HAND
        }
      }

      menuItem {
        graphicContainer {
          padding = box(0.px, 5.px)
        }

        and(hover) {
          cursor = HAND
        }
      }
    }
  }
}