package me.chill.styles

import javafx.scene.Cursor.HAND
import javafx.scene.paint.Color.WHITE
import javafx.scene.text.FontWeight.BOLD
import tornadofx.*
import tornadofx.FXVisibility.COLLAPSE

/**
 * Generic styles to be applied across the editor
 */
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

    select(".decrement-button, .increment-button") {
      prefHeight = 0.px
    }

    select(".decrement-arrow, .increment-arrow") {
      visibility = COLLAPSE
    }

    thumb {
      and(hover) {
        cursor = HAND
        backgroundColor += c("#BDBDBD", 0.5)
      }

      backgroundRadius += box(15.px)
      backgroundColor += c("#BDBDBD", 0.2)
    }

    track {
      backgroundColor += c("#EEEEEE")
    }
  }
}