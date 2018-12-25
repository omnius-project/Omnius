package me.chill.styles

import javafx.scene.paint.Color.TRANSPARENT
import tornadofx.Stylesheet
import tornadofx.*
import tornadofx.px

class EditingAreaStyles : Stylesheet() {
  init {
    splitPaneDivider {
      backgroundColor += TRANSPARENT
      padding = box(1.px)
    }
  }
}