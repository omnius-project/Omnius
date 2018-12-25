package me.chill.views.editor

import javafx.scene.input.KeyCombination
import me.chill.controllers.EditorController
import tornadofx.View
import tornadofx.borderpane
import tornadofx.vbox

class Editor : View("Omnius") {
  private val controller: EditorController by inject()

  override val root = borderpane {
    minWidth = 1000.0
    minHeight = 800.0

    loadShortcuts()

    top = vbox {
      add(find(MenuBar::class))
      add(find(ToolBar::class))
    }

    center(EditingArea::class)

    bottom(StatusBar::class)
  }

  private fun loadShortcuts() {
    shortcut(KeyCombination.keyCombination("Ctrl+O")) {
      controller.openFolder(primaryStage)
    }
  }
}