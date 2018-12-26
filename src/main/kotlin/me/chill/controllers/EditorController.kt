package me.chill.controllers

import javafx.scene.control.Tab
import javafx.scene.control.TextArea
import javafx.scene.control.TreeItem
import javafx.stage.DirectoryChooser
import javafx.stage.Stage
import me.chill.models.FileExplorerItem
import me.chill.ui.FolderTreeView
import me.chill.utility.extensions.first
import me.chill.utility.extensions.isImage
import me.chill.views.editor.EditingArea
import me.chill.views.fragments.ExitFragment
import tornadofx.Controller
import java.io.File

// TODO: Split out the controllers for the editing area
class EditorController : Controller() {
  private val openTabs = mutableMapOf<FileExplorerItem, Tab>() // TODO: Move this to the custom tab view later
  private val editingArea = find<EditingArea>()
  private val folderView = editingArea.folderStructure
  private val contentArea = editingArea.contentArea
  private val statusBarController = find<StatusBarController>()

  init {
    folderView.onDoubleClick(this::setupFileSelectionAction)
  }

  // Opens a folder and populates the tree view with the folder structure
  fun openFolder(primaryStage: Stage) {
    // TODO: Open folder relative to the current directory
    val folder = DirectoryChooser()
      .apply { title = "Open Folder" }
      .showDialog(primaryStage)

    folder?.let { folderView.loadFolder(it) } ?: return
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

  private fun setupFileSelectionAction(fileItem: FileExplorerItem) {
    val file = fileItem.file
    if (file.isFile && !file.isImage) {
      statusBarController.dispatchMessage("Opening: ${fileItem.file.name}")
      openTab(fileItem)
    }
  }

  private fun openTab(fileItem: FileExplorerItem) {
    val file = fileItem.file
    val tab = Tab(file.name)
      .apply { content = TextArea() }
    tab.setOnClosed { openTabs.remove(fileItem) }

    with(contentArea) {
      if (openTabs.none { it.key == fileItem }) {
        tabs.add(tab)
        selectionModel.select(tab)
        openTabs[fileItem] = tab
        openFileContents(fileItem.file, tab)
      } else {
        val existingTab = openTabs.first { it.key == fileItem }
        selectionModel.select(existingTab.value)
      }
    }
  }

  // TODO: Check the file extension first before opening
  private fun openFileContents(file: File, tab: Tab) {
    with(tab.content as TextArea) {
      text = file.readText()
    }
  }
}