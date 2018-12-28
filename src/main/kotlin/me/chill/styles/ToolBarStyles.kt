package me.chill.styles

import javafx.scene.Cursor.HAND
import javafx.scene.paint.Color.TRANSPARENT
import javafx.scene.paint.Color.WHITE
import me.chill.styles.Styles.Companion.subtleGrey
import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.px

class ToolBarStyles : Stylesheet() {

  init {
    toolBar {
      backgroundColor += WHITE
      borderColor += box(TRANSPARENT, TRANSPARENT, subtleGrey, TRANSPARENT)
      borderWidth += box(0.px, 0.px, 1.px, 0.px)

      button {
        backgroundColor += TRANSPARENT
        backgroundRadius += box(0.px)

        and(hover) {
          cursor = HAND
          backgroundColor += subtleGrey
        }
      }
    }
  }
}