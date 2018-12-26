package me.chill.styles

import javafx.scene.Cursor.HAND
import javafx.scene.paint.Color.TRANSPARENT
import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.c
import tornadofx.px

// TODO: Move the colors into a separate class
class EditingAreaStyles : Stylesheet() {
  companion object {
    private val blueAccent = c("#448AFF")
  }

  init {
    splitPaneDivider {
      backgroundColor += TRANSPARENT
      padding = box(1.px)
    }

    select(".tab-pane:focused > .tab-header-area > .headers-region > .tab:selected .focus-indicator") {
      borderColor += box(TRANSPARENT)
    }

    tab {
      backgroundColor += c("#ECEFF1")
      padding = box(10.px, 10.px)
      backgroundRadius += box(0.px)
      borderColor += box(TRANSPARENT, TRANSPARENT, TRANSPARENT, TRANSPARENT)
      borderWidth += box(0.px, 0.px, 5.px, 0.px)

      and(hover) {
        cursor = HAND
      }

      and(selected) {
        borderColor += box(TRANSPARENT, TRANSPARENT, blueAccent, TRANSPARENT)
        borderWidth += box(0.px, 0.px, 5.px, 0.px)
      }
    }

    select(".tab-close-button") {
      and(hover) {
        backgroundColor += blueAccent
      }
    }

    select(".tab-header-area") {
      padding = box(0.px)
    }

    select(".tab-label") {
      labelPadding = box(0.px, 10.px, 0.px, 10.px)
      fontSize = 20.px
    }
  }
}