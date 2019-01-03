package me.chill.views

import com.google.gson.JsonObject
import javafx.geometry.Orientation.HORIZONTAL
import javafx.geometry.Orientation.VERTICAL
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Priority
import me.chill.actionmap.ActionMap
import me.chill.actionmap.ActionMap.*
import me.chill.actionmap.ActionMapObserver
import me.chill.models.EditorModel
import me.chill.models.FileExplorerItem
import me.chill.styles.StatusBarStyles
import me.chill.ui.FolderTreeView
import me.chill.utility.extensions.isImage
import me.chill.views.ToolBar.Position.LEFT
import me.chill.views.ToolBar.Position.TOP
import tornadofx.*
import java.io.File

// TODO: Add support for Jekyll specific sites aka editing the metadata of .md files - front matters
class Editor : View("Omnius"), ActionMapObserver {

  private val menuBar: MenuBar by inject()
  private val toolBar: ToolBar by inject()
  private val statusMessage = label()
  private val caretPosition = label()

  private val fileExplorer = FolderTreeView().apply { onDoubleClick(this@Editor::fileSelectionAction) }
  private val markdownArea = find<MarkdownArea>()

  init {
    EditorModel.addObserver(this)
  }

  override fun update(actionMap: ActionMap, data: JsonObject?) {
    when (actionMap) {
      MOVE_TOOLBAR_TOP -> moveToolBar(TOP)
      MOVE_TOOLBAR_LEFT -> moveToolBar(LEFT)
      TOGGLE_TOOLBAR_VISIBILITY -> toolBar.toggleToolBarVisibility()
      FOLDER_CHANGED -> EditorModel.currentFolder?.let { loadFolder(it) }
      else -> return
    }
  }

  override val root = borderpane {
    top = vbox {
      add(menuBar)
      add(toolBar)
    }

    center = splitpane(HORIZONTAL) {
      setDividerPositions(0.1, 0.9)

      add(fileExplorer)

      // TODO: When maximizing the window, take into account the divider positions and apply the same ratio to maximized window
      // TODO: Allow the file explorer window to be collapsed, display an arrow to re-show if that happens
      // TODO: Load the prior open folder contents from last use
      // TODO: Allow the tabs to be re-arranged
      // TODO: Bind the cursor position in the text area to the status bar to show where the cursor is
      add(markdownArea)
    }

    // TODO: Split the status bar into a different view and have a model-controller for it
    bottom = hbox {
      addClass(StatusBarStyles.statusBar)
      add(statusMessage)
      region { hgrow = Priority.ALWAYS }
      add(caretPosition)
    }
  }

  private fun loadFolder(folder: File) {
    markdownArea.clearArea()
    fileExplorer.loadFolder(folder)
  }

  // Moves the tool bar
  private fun moveToolBar(position: ToolBar.Position) {
    with(root) {
      with(toolBar.root) {
        when (position) {
          TOP -> {
            orientation = HORIZONTAL
            top.add(this)
          }
          LEFT -> {
            left = left ?: AnchorPane()
            orientation = VERTICAL
            left.add(this.anchorpaneConstraints {
              bottomAnchor = 0
              topAnchor = 0
              leftAnchor = 0
              rightAnchor = 0
            })
          }
        }
      }
    }
  }

  // TODO: Check the file extension first before opening
  private fun fileSelectionAction(fileItem: FileExplorerItem) {
    with(fileItem.file) {
      if (isFile && !isImage) {
        dispatchMessage("Opening: $name")
        markdownArea.openTab(fileItem, name)
      }
    }
  }

  private fun dispatchMessage(message: String) {
    statusMessage.text = message
  }
}