package me.chill.ui

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import me.chill.controllers.StatusBarController
import me.chill.models.FileExplorerItem
import tornadofx.find
import tornadofx.runAsync
import tornadofx.ui
import java.io.File

/**
 * Custom TreeView for loading the folder structure and providing a way to add action handlers to the
 * TreeView
 */
class FolderTreeView : TreeView<FileExplorerItem>() {

  private var isOpeningFile = false
  private val statusBarController = find<StatusBarController>()

  fun onDoubleClick(action: (FileExplorerItem) -> Unit) {
    setOnMouseClicked {
      if (it.clickCount == 2 && children.isNotEmpty()) {
        action((selectionModel.selectedItem as TreeItem<FileExplorerItem>).value)
      }
    }
  }

  // TODO: Opening the folder should inform the user via progress bar in notification system
  fun loadFolder(folder: File) {
    if (!isOpeningFile) {
      statusBarController.dispatchMessage("Opening folder: ${folder.nameWithoutExtension}")
      isOpeningFile = true
      populateFolderView(folder)
    }
  }

  private fun populateFolderView(file: File) {
    runAsync {
      val rootItem = TreeItem(FileExplorerItem(file))
        .apply {
          graphic = FontAwesomeIconView(FontAwesomeIcon.FOLDER_OPEN)
          isExpanded = true
        }
      rootItem.setupFolderIconAction()
      file.listFiles()?.forEach { createTree(it, rootItem) }

      rootItem
    } ui {
      root = it
      isOpeningFile = false
      statusBarController.dispatchMessage("${file.nameWithoutExtension} opened successfully")
    }
  }

  // TODO: Optimize tree traversal algorithm for large folders
  private fun createTree(file: File, parent: TreeItem<FileExplorerItem>) {
    if (file.isHidden) return

    if (file.isDirectory) {
      val treeItem = TreeItem(FileExplorerItem(file))
        .apply { graphic = FontAwesomeIconView(FontAwesomeIcon.FOLDER) }
      with(treeItem) {
        parent.children.add(this)
        setupFolderIconAction()
        file.listFiles()?.forEach { createTree(it, this) }
      }
    } else {
      parent.children.add(
        TreeItem(FileExplorerItem(file))
          .apply { graphic = FontAwesomeIconView(FontAwesomeIcon.FILE) }
      )
    }
  }

  private fun TreeItem<FileExplorerItem>.setupFolderIconAction() {
    expandedProperty().addListener { _, old, new ->
      val isExpand = !old && new
      val folderIcon =
        if (isExpand) FontAwesomeIcon.FOLDER_OPEN
        else FontAwesomeIcon.FOLDER
      graphic = FontAwesomeIconView(folderIcon)
    }
  }
}