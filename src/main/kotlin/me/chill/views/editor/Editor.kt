package me.chill.views.editor

import tornadofx.View
import tornadofx.borderpane
import tornadofx.vbox

class Editor : View("Omnius") {
  private val menuBar = find(MenuBar::class)
  private val toolBar = find(ToolBar::class)

  override val root = borderpane {
    minWidth = 800.0
    minHeight = 650.0
    top = vbox {
      add(menuBar)
      add(toolBar)
    }
  }
}