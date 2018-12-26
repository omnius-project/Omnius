package me.chill.controllers

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.*
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.scene.control.*
import javafx.stage.DirectoryChooser
import javafx.stage.Stage
import me.chill.models.FileExplorerItem
import me.chill.views.editor.EditingArea
import me.chill.views.fragments.ExitFragment
import tornadofx.Controller
import tornadofx.DrawerStyles.Companion.contentArea
import tornadofx.select
import java.io.File

class EditorController : Controller() {
  private var isOpeningFile = false

  // Opens a folder and populates the tree view with the folder structure
  fun openFolder(primaryStage: Stage) {
    // TODO: Open folder relative to the current directory
    val folder = DirectoryChooser()
      .apply { title = "Open Folder" }
      .showDialog(primaryStage)

    // TODO: Opening the folder should inform the user via progress bar in notification system
    folder?.let {
      if (!isOpeningFile) {
        StatusBarController.dispatchMessage("Opening folder: ${it.nameWithoutExtension}")
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
      parent.children.add(treeItem)
      treeItem.setupFolderIconAction()
      file.listFiles()?.forEach { createTree(it, treeItem) }
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
          setupFolderIconAction()
        }
      file.listFiles()?.forEach { createTree(it, rootItem) }

      rootItem
    } ui {
      find<EditingArea>()
        .folderStructure
        .apply {
          root = it
          setupFileSelectionAction()
        }
      isOpeningFile = false
      StatusBarController.dispatchMessage("${file.nameWithoutExtension} opened successfully")
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

  private fun <T> TreeItem<T>.setupFolderIconAction() {
    expandedProperty().addListener { _, old, new ->
      graphic = FontAwesomeIconView(if (!old && new) FOLDER_OPEN else FOLDER)
    }
  }

  private fun openFileInContentArea(fileItem: FileExplorerItem) {
    val file = fileItem.file
    StatusBarController.dispatchMessage("Opening: ${file.name}")

    val tab = Tab(file.name).apply {
      content = TextArea()
    }

    find<EditingArea>().contentArea.apply {
      tabs.add(tab)
      selectionModel.select(tab)
    }
  }
}