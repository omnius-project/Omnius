package me.chill.styles

import tornadofx.Stylesheet
import tornadofx.c

class Styles : Stylesheet() {
  companion object {
    private val bg = c("#FAFAFA")
  }

  init {
    root { backgroundColor += bg }
  }
}