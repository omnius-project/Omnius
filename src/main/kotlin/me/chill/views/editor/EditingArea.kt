package me.chill.views.editor

import javafx.geometry.Orientation
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import me.chill.controllers.EditorController
import tornadofx.View
import tornadofx.splitpane
import tornadofx.textarea
import tornadofx.treeview

// TODO: When maximizing the window, take into account the divider positions and apply the same ratio to maximized window
// TODO: Allow the file explorer window to be collapsed, display an arrow to re-show if that happens
class EditingArea : View() {
  private val controller: EditorController by inject()
  lateinit var folderStructure: TreeView<String>

  override val root = splitpane(Orientation.HORIZONTAL) {
    setDividerPositions(0.4, 0.6)
    folderStructure = treeview(TreeItem("Open Folder"))
    folderStructure.selectionModel.selectedItemProperty()

    textarea {
    }
  }
}