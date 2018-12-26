package me.chill.styles

import javafx.scene.Cursor.HAND
import javafx.scene.paint.Color.*
import javafx.scene.text.FontWeight
import javafx.scene.text.FontWeight.BOLD
import tornadofx.Stylesheet
import tornadofx.c
import tornadofx.cssclass

class Styles : Stylesheet() {
  companion object {
    val dangerButton by cssclass()

    private val bg = c("#FAFAFA")
    val dangerRed = c("#EF5350")
  }

  init {
    root { backgroundColor += bg }

    button {
      and(hover) {
        cursor = HAND
      }
    }

    dangerButton {
      backgroundColor += dangerRed
      fontWeight = BOLD
      textFill = WHITE

      and(hover) {
        backgroundColor += c("#E53935")
      }
    }
  }
}