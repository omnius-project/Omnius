package me.chill.controllers

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.*
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.scene.control.Tab
import javafx.scene.control.TextArea
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.stage.DirectoryChooser
import javafx.stage.Stage
import me.chill.models.FileExplorerItem
import me.chill.utility.extensions.first
import me.chill.views.editor.EditingArea
import me.chill.views.fragments.ExitFragment
import tornadofx.Controller
import java.io.File

// TODO: Split out the controllers for the editing area
class EditorController : Controller() {
  private var isOpeningFile = false
  private val openTabs = mutableMapOf<FileExplorerItem, Tab>()
  private val statusBarController = find<StatusBarController>()

  // Opens a folder and populates the tree view with the folder structure
  fun openFolder(primaryStage: Stage) {
    // TODO: Open folder relative to the current directory
    val folder = DirectoryChooser()
      .apply { title = "Open Folder" }
      .showDialog(primaryStage)

    // TODO: Opening the folder should inform the user via progress bar in notification system
    folder?.let {
      if (!isOpeningFile) {
        statusBarController.dispatchMessage("Opening folder: ${it.nameWithoutExtension}")
        isOpeningFile = true
        populateFolderView(it)
      }
    } ?: return
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

  // TODO: Optimize tree traversal algorithm for large folders
  private fun createTree(file: File, parent: TreeItem<FileExplorerItem>) {
    if (file.isHidden) return

    if (file.isDirectory) {
      val treeItem = TreeItem(FileExplorerItem(file))
        .apply { graphic = FontAwesomeIconView(FOLDER) }
      with(treeItem) {
        parent.children.add(this)
        setupFolderIconAction()
        file.listFiles()?.forEach { createTree(it, this) }
      }
    } else {
      parent.children.add(
        TreeItem(FileExplorerItem(file))
          .apply { graphic = FontAwesomeIconView(FILE) }
      )
    }
  }

  private fun populateFolderView(file: File) {
    runAsync {
      val rootItem = TreeItem(FileExplorerItem(file))
        .apply {
          graphic = FontAwesomeIconView(FOLDER_OPEN)
          isExpanded = true
        }
      with(rootItem) {
        setupFolderIconAction()
      }
      file.listFiles()?.forEach { createTree(it, rootItem) }

      rootItem
    } ui {
      with(find<EditingArea>().folderStructure.apply { root = it }) {
        setupFileSelectionAction()
      }
      isOpeningFile = false
      statusBarController.dispatchMessage("${file.nameWithoutExtension} opened successfully")
    }
  }

  private fun <T> TreeItem<T>.setupFolderIconAction() {
    expandedProperty().addListener { _, old, new ->
      graphic = FontAwesomeIconView(if (!old && new) FOLDER_OPEN else FOLDER)
    }
  }

  private fun TreeView<FileExplorerItem>.setupFileSelectionAction() {
    setOnMouseClicked {
      if (it.clickCount == 2) {
        val fileItem = (selectionModel.selectedItem as TreeItem<FileExplorerItem>).value
        if (fileItem.file.isFile) openFileInContentArea(fileItem)
      }
    }
  }

  private fun openFileInContentArea(fileItem: FileExplorerItem) {
    statusBarController.dispatchMessage("Opening: ${fileItem.file.name}")

    openTab(fileItem)
  }

  private fun openTab(fileItem: FileExplorerItem) {
    val file = fileItem.file
    val tab = Tab(file.name)
      .apply { content = TextArea() }
    tab.setOnClosed { openTabs.remove(fileItem) }

    with(find<EditingArea>().contentArea) {
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

  private fun openFileContents(file: File, tab: Tab) {
    with(tab.content as TextArea) {
      text = file.readText()
    }
  }
}