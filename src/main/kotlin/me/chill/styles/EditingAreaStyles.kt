package me.chill.styles

import javafx.scene.paint.Color.TRANSPARENT
import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.c
import tornadofx.px

// TODO: Move the colors into a separate class
class EditingAreaStyles : Stylesheet() {
  init {
    splitPaneDivider {
      backgroundColor += TRANSPARENT
      padding = box(1.px)
    }

    select(".tab-pane:focused > .tab-header-area > .headers-region > .tab:selected .focus-indicator") {
      borderColor += box(TRANSPARENT)
    }

    // TODO: Fix the weird resizing of the box whenever the item is leaving focus
    tab {
      backgroundColor += TRANSPARENT

      and(selected) {
        borderColor += box(TRANSPARENT, TRANSPARENT, c("#448AFF"), TRANSPARENT)
        borderWidth += box(0.px, 0.px, 5.px, 0.px)
      }
    }
  }
}