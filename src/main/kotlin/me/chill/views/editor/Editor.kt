package me.chill.views.editor

import javafx.geometry.Orientation
import me.chill.ui.FolderTreeView
import tornadofx.View
import tornadofx.borderpane
import tornadofx.splitpane
import tornadofx.vbox

// TODO: Add support for Jekyll specific sites aka editing the metadata of .md files
class Editor : View("Omnius") {

  private val menuBar: MenuBar by inject()
  private val toolBar: ToolBar by inject()
  private val statusBar: StatusBar by inject()
  lateinit var fileExplorer: FolderTreeView
  val markdownArea = find<MarkdownArea>()

  override val root = borderpane {
    top = vbox {
      add(menuBar)
      add(toolBar)
    }

    center = splitpane(Orientation.HORIZONTAL) {
      setDividerPositions(0.1, 0.9)

      fileExplorer = FolderTreeView()
      add(fileExplorer)

      // TODO: When maximizing the window, take into account the divider positions and apply the same ratio to maximized window
      // TODO: Allow the file explorer window to be collapsed, display an arrow to re-show if that happens
      // TODO: Load the prior open folder contents from last use
      // TODO: Allow the tabs to be re-arranged
      // TODO: Bind the cursor position in the text area to the status bar to show where the cursor is
      add(markdownArea)
    }

    bottom(statusBar::class)
  }
}