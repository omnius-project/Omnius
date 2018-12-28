package me.chill.views.editor

import javafx.geometry.Orientation
import javafx.scene.control.TabPane.TabClosingPolicy.ALL_TABS
import me.chill.controllers.EditorController
import me.chill.models.FileExplorerItem
import me.chill.ui.FolderTreeView
import me.chill.ui.TabContentArea
import me.chill.ui.markdownarea.MarkdownTextArea
import tornadofx.View
import tornadofx.splitpane

// TODO: When maximizing the window, take into account the divider positions and apply the same ratio to maximized window
// TODO: Allow the file explorer window to be collapsed, display an arrow to re-show if that happens
class EditingArea : View() {

  private val controller: EditorController by inject()
  lateinit var folderStructure: FolderTreeView
  lateinit var tabContentArea: TabContentArea<MarkdownTextArea, FileExplorerItem>

  override val root = splitpane(Orientation.HORIZONTAL) {
    setDividerPositions(0.1, 0.9)

    folderStructure = FolderTreeView()
    add(folderStructure)

    // TODO: Load the prior open folder contents from last use
    // TODO: Allow the tabs to be re-arranged
    // TODO: Bind the cursor position in the text area to the status bar to show where the cursor is
    tabContentArea = TabContentArea<MarkdownTextArea, FileExplorerItem>(MarkdownTextArea::class)
      .apply { tabClosingPolicy = ALL_TABS }
    add(tabContentArea)
  }
}