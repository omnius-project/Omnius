package me.chill.styles

import javafx.scene.Cursor.HAND
import javafx.scene.paint.Color.WHITE
import tornadofx.Stylesheet
import tornadofx.c
import tornadofx.cssclass

class Styles : Stylesheet() {
  companion object {
    val dangerButton by cssclass()

    private val bg = c("#FAFAFA")
  }

  init {
    root { backgroundColor += bg }

    button {
      and(hover) {
        cursor = HAND
      }
    }

    dangerButton {
      backgroundColor += c("#FF5252")
      textFill = WHITE
    }
  }
}