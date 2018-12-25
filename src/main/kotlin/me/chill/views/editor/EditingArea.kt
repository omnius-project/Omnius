package me.chill.views.editor

import javafx.geometry.Orientation
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import tornadofx.*

// TODO: When maximizing the window, take into account the divider positions and apply the same ratio to maximized window
// TODO: Allow the file explorer window to be collapsed, display an arrow to re-show if that happens
class EditingArea : View() {
  lateinit var folderStructure: TreeView<String>

  override val root = splitpane(Orientation.HORIZONTAL) {
    setDividerPositions(0.3, 0.7)
    folderStructure = treeview(TreeItem("Inbox")) {

    }

    textarea {
    }
  }
}