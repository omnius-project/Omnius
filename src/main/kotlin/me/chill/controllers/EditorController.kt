package me.chill.controllers

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.*
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.scene.control.MenuItem
import javafx.scene.control.TreeItem
import javafx.stage.DirectoryChooser
import javafx.stage.Modality
import javafx.stage.Stage
import me.chill.views.editor.EditingArea
import me.chill.views.fragments.ExitFragment
import tornadofx.Controller
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
  private fun createTree(file: File, parent: TreeItem<String>) {
    if (file.isHidden) return

    if (file.isDirectory) {
      val treeItem = TreeItem(file.name).apply {
        graphic = FontAwesomeIconView(FOLDER)
      }
      parent.children.add(treeItem)
      treeItem.setupFolderIconAction()
      file.listFiles()?.forEach { createTree(it, treeItem) }
    } else {
      parent.children.add(TreeItem(file.name).apply {
        graphic = FontAwesomeIconView(FILE)
      })
    }
  }

  private fun populateFolderView(file: File) {
    runAsync {
      val rootItem = TreeItem(file.nameWithoutExtension).apply {
        graphic = FontAwesomeIconView(FOLDER_OPEN)
        isExpanded = true
        setupFolderIconAction()
      }
      file.listFiles()?.forEach { createTree(it, rootItem) }

      rootItem
    } ui {
      find<EditingArea>().folderStructure.apply { root = it }
      isOpeningFile = false
      StatusBarController.dispatchMessage("${file.nameWithoutExtension} opened successfully")
    }
  }

  private fun <T> TreeItem<T>.setupFolderIconAction() {
    expandedProperty().addListener { _, old, new ->
      graphic = FontAwesomeIconView(if (!old && new) FOLDER_OPEN else FOLDER)
    }
  }
}