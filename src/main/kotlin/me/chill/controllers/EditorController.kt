package me.chill.controllers

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.FILE
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.FOLDER
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
  // Opens a folder and populates the tree view with the folder structure
  fun openFolder(primaryStage: Stage) {
    // TODO: Open folder relative to the current directory
    val directoryChooser = DirectoryChooser()
    directoryChooser.title = "Open Folder"
    val folder = directoryChooser.showDialog(primaryStage)

    folder?.let { populateFolderView(it) } ?: return
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

  private fun createTree(file: File, parent: TreeItem<String>) {
    if (file.isHidden) return

    if (file.isDirectory) {
      val treeItem = TreeItem(file.name)
      treeItem.graphic = FontAwesomeIconView(FOLDER)
      parent.children.add(treeItem)
      file.listFiles()?.forEach { createTree(it, treeItem) }
    } else {
      val treeItem = TreeItem(file.name)
      treeItem.graphic = FontAwesomeIconView(FILE)
      parent.children.add(treeItem)
    }
  }

  private fun populateFolderView(file: File) {
    val rootItem = TreeItem(file.nameWithoutExtension)
    rootItem.graphic = FontAwesomeIconView(FOLDER)
    file.listFiles()?.forEach { createTree(it, rootItem) }
    find(EditingArea::class).folderStructure.root = rootItem
  }
}