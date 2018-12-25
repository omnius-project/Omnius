package me.chill.styles

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
      padding = box(5.px, 20.px)
    }
  }
}