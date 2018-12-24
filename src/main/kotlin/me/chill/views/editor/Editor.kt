package me.chill.views.editor

import tornadofx.View
import tornadofx.borderpane

class Editor : View("Omnius") {
  override val root = borderpane {
    minWidth = 800.0
    minHeight = 650.0
    top(MenuBar::class)

  }
}