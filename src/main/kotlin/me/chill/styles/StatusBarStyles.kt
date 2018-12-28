package me.chill.styles

import javafx.scene.paint.Color
import javafx.scene.paint.Color.WHITE
import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.cssclass
import tornadofx.px

class StatusBarStyles : Stylesheet() {

  companion object {
    val statusBar by cssclass()
  }

  init {
    statusBar {
      backgroundColor += WHITE
      padding = box(5.px, 20.px)
    }
  }
}