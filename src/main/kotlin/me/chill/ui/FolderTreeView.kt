package me.chill.ui

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.*
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import me.chill.models.FileExplorerItem
import me.chill.utility.glyphtools.GlyphFactory
import tornadofx.runAsync
import tornadofx.ui
import java.io.File

/**
 * Custom TreeView for loading the folder structure and providing a way to add action handlers to the
 * TreeView
 */
class FolderTreeView : TreeView<FileExplorerItem>() {

  // TODO: Allow users to filter only markdown file
  private var isOpeningFile = false
  private val glyphFactory = GlyphFactory.Builder().build()

  fun onDoubleClick(action: (FileExplorerItem) -> Unit) {
    setOnMouseClicked {
      val selectedItem = selectionModel.selectedItem
      selectedItem ?: return@setOnMouseClicked
      if (it.clickCount == 2 && children.isNotEmpty()) {
        action((selectedItem as TreeItem<FileExplorerItem>).value)
      }
    }
  }

  // TODO: Opening the folder should inform the user via progress bar in notification system
  fun loadFolder(folder: File) {
    with(folder) {
      if (!isOpeningFile) {
        isOpeningFile = true
        populateFolderView(this)
      }
    }
  }

  private fun populateFolderView(file: File) {
    runAsync {
      val rootItem = TreeItem(FileExplorerItem(file))
        .apply {
          graphic = addGlyph(FOLDER_OPEN_ALT)
          isExpanded = true
        }
      rootItem.setupFolderIconAction()
      file.listFiles()?.forEach { createTree(it, rootItem) }

      rootItem
    } ui {
      root = it
      isOpeningFile = false
    }
  }

  // TODO: Optimize tree traversal algorithm for large folders
  private fun createTree(file: File, parent: TreeItem<FileExplorerItem>) {
    if (file.isHidden) return

    if (file.isDirectory) {
      val treeItem = TreeItem(FileExplorerItem(file))
        .apply { graphic = addGlyph(FOLDER_ALT) }
      with(treeItem) {
        parent.children.add(this)
        setupFolderIconAction()
        file.listFiles()?.forEach { createTree(it, this) }
      }
    } else {
      parent.children.add(
        TreeItem(FileExplorerItem(file))
          .apply { graphic = addGlyph(FILE_ALT) }
      )
    }
  }

  private fun TreeItem<FileExplorerItem>.setupFolderIconAction() {
    expandedProperty().addListener { _, old, new ->
      val isExpand = !old && new
      val folderIcon = if (isExpand) FOLDER_OPEN_ALT else FOLDER_ALT
      graphic = addGlyph(folderIcon)
    }
  }

  private fun addGlyph(icon: FontAwesomeIcon) = glyphFactory.make(icon)
}