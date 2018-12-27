package me.chill.styles

import javafx.scene.Cursor.HAND
import javafx.scene.paint.Color.*
import tornadofx.*

class MenuBarStyles : Stylesheet() {
  companion object {
    val tbar by cssclass()

    private val subtleGrey = c("#EDEDED")
  }

  init {
    menuBar {
      backgroundColor += WHITE
      borderColor += box(TRANSPARENT, TRANSPARENT, subtleGrey, TRANSPARENT)
      borderWidth += box(0.px, 0.px, 1.px, 0.px)

      menu {
//        and(hover) {
//          backgroundColor += subtleGrey
//          label {
//            textFill = BLACK
//          }
//        }
//
//        and(focused) {
//          backgroundColor += subtleGrey
//          label {
//            textFill = BLACK
//          }
//        }
      }

      menuItem {
        graphicContainer {
          padding = box(0.px, 5.px)
        }

//        and(hover) {
//          cursor = HAND
//          backgroundColor += subtleGrey
//          label {
//            textFill = BLACK
//          }
//        }
//
//        and(focused) {
//          backgroundColor += subtleGrey
//          label {
//            textFill = BLACK
//          }
//        }
      }
    }

    tbar {
      backgroundColor += WHITE
      borderColor += box(TRANSPARENT, TRANSPARENT, subtleGrey, TRANSPARENT)
      borderWidth += box(0.px, 0.px, 1.px, 0.px)

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