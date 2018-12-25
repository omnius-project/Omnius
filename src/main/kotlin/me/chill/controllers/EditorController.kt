package me.chill.controllers

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.*
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.application.Platform
import javafx.scene.control.MenuItem
import javafx.scene.control.TreeItem
import javafx.stage.DirectoryChooser
import javafx.stage.Stage
import me.chill.views.editor.EditingArea
import tornadofx.Controller
import java.io.File

class EditorController : Controller() {
  private var isOpeningFile = false

  // Opens a folder and populates the tree view with the folder structure
  fun openFolder(primaryStage: Stage) {
    // TODO: Open folder relative to the current directory
    val directoryChooser = DirectoryChooser()
    directoryChooser.title = "Open Folder"
    val folder = directoryChooser.showDialog(primaryStage)

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

  fun exit(menuItem: MenuItem) {
    // TODO: Check if work is saved
    // TODO: Save preferences on close
    Platform.exit()
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

  // TODO: Optimize tree traversal algorithm for large folders
  private fun createTree(file: File, parent: TreeItem<String>) {
    if (file.isHidden) return

    if (file.isDirectory) {
      val treeItem = TreeItem(file.name)
      treeItem.graphic = FontAwesomeIconView(FOLDER)
      parent.children.add(treeItem)
      treeItem.expandedProperty().addListener { _, old, new ->
        treeItem.graphic = FontAwesomeIconView(if (!old && new) FOLDER_OPEN else FOLDER)
      }
      file.listFiles()?.forEach { createTree(it, treeItem) }
    } else {
      val treeItem = TreeItem(file.name)
      treeItem.graphic = FontAwesomeIconView(FILE)
      parent.children.add(treeItem)
    }
  }

  private fun populateFolderView(file: File) {
    runAsync {
      val rootItem = TreeItem(file.nameWithoutExtension)
      rootItem.graphic = FontAwesomeIconView(FOLDER_OPEN)
      file.listFiles()?.forEach { createTree(it, rootItem) }

      rootItem
    } ui {
      val folderView = find(EditingArea::class).folderStructure
      folderView.root = it
      it.isExpanded = true
      isOpeningFile = false
      StatusBarController.dispatchMessage("${file.nameWithoutExtension} opened successfully")
    }
  }
}