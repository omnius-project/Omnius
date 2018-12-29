package me.chill.controllers

import javafx.geometry.Orientation.HORIZONTAL
import javafx.geometry.Orientation.VERTICAL
import javafx.scene.control.Tab
import javafx.scene.layout.AnchorPane
import javafx.stage.DirectoryChooser
import javafx.stage.Stage
import me.chill.models.FileExplorerItem
import me.chill.ui.markdownarea.MarkdownEditingArea
import me.chill.utility.extensions.isImage
import me.chill.views.editor.EditingArea
import me.chill.views.editor.Editor
import me.chill.views.editor.ToolBar
import me.chill.views.editor.ToolBar.Position.LEFT
import me.chill.views.editor.ToolBar.Position.TOP
import me.chill.views.fragments.ExitFragment
import tornadofx.Controller
import tornadofx.add
import tornadofx.anchorpaneConstraints
import java.io.File

// TODO: Split out the controllers for the editing area
class EditorController : Controller() {

  // Has to be lazily initialized because the view cannot be called before the view is initialized
  private val editor by lazy { find<Editor>() }

  private val editingArea = find<EditingArea>()
  private val fileExplorer = editingArea.folderStructure
  private val contentArea = editingArea.tabContentArea
  private val toolBar = find<ToolBar>()

  private val statusBarController = find<StatusBarController>()

  init {
    fileExplorer.onDoubleClick(this::fileSelectionAction)
    contentArea.setOnOpenAction { fileItem, tab -> openFileContents(fileItem.file, tab) }
  }

  // Populates the tree view with the folder structure
  fun openFolder(primaryStage: Stage) {
    // TODO: Open folder relative to the current directory
    val folder = DirectoryChooser()
      .apply { title = "Open Folder" }
      .showDialog(primaryStage)

    folder ?: return

    contentArea.clearArea()
    fileExplorer.loadFolder(folder)
  }

  fun saveFile() {
    println("Saving file")
  }

  fun saveAll() {
    println("Saving all")
  }

  fun importFromVCS() {
    println("Importing from VCS")
  }

  fun export() {
    println("Exporting")
  }

  fun launchOptions() {
    println("Launching options")
  }

  fun exit() {
    // TODO: Check if work is saved
    find<ExitFragment>().openModal(resizable = false)
  }

  fun undoAction() {
    println("Undoing action")
  }

  fun redoAction() {
    println("Redoing action")
  }

  fun cut() {
    println("Cutting")
  }

  fun copy() {
    println("Copying")
  }

  fun paste() {
    println("Pasting")
  }

  // Toggles the visibility of the tool bar
  fun toggleToolBar() {
    with(toolBar.root) {
      isVisible = !isVisible
    }
  }

  // Moves the tool bar
  fun moveToolBar(position: ToolBar.Position) {
    with(editor.root) {
      with(toolBar.root) {
        when (position) {
          TOP -> {
            orientation = HORIZONTAL
            top.add(this)
          }
          LEFT -> {
            if (left == null) left = AnchorPane()
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
        statusBarController.dispatchMessage("Opening: $name")
        contentArea.openTab(fileItem, name)
      }
    }
  }

  private fun openFileContents(file: File, tab: Tab) {
    with((tab.content as MarkdownEditingArea).content) {
      replaceText(file.readText())
      requestFocus()
      moveTo(0)
      requestFollowCaret()
    }
  }
}