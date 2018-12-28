package me.chill.views.editor

import me.chill.controllers.EditorController
import me.chill.keymap.Keymap
import tornadofx.View
import tornadofx.borderpane
import tornadofx.vbox

// TODO: Add support for Jekyll specific sites aka editing the metadata of .md files
class Editor : View("Omnius") {

  private val menuBar: MenuBar by inject()
  private val toolBar: ToolBar by inject()
  private val editingArea: EditingArea by inject()
  private val statusBar: StatusBar by inject()

  private val controller: EditorController by inject()

  override val root = borderpane {
    minWidth = 1000.0
    minHeight = 800.0

    loadShortcuts()

    top = vbox {
      add(menuBar)
      add(toolBar)
    }

    center(editingArea::class)

    bottom(statusBar::class)
  }

  private fun loadShortcuts() {
    shortcut(Keymap.OPEN_FOLDER.keyCombination) {
      controller.openFolder(primaryStage)
    }
  }
}