package me.chill.styles

import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.px

class MenuBarStyles : Stylesheet() {
  init {
    menuItem {
      graphicContainer {
        padding = box(0.px, 10.px, 0.px, 0.px)
      }
    }
  }
}