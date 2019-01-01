package me.chill.controllers

import javafx.geometry.Orientation.HORIZONTAL
import javafx.geometry.Orientation.VERTICAL
import javafx.scene.control.Tab
import javafx.scene.layout.AnchorPane
import javafx.stage.DirectoryChooser
import javafx.stage.Stage
import me.chill.actionmap.ActionMap
import me.chill.actionmap.ActionMap.*
import me.chill.actionmap.ActionMapObserver
import me.chill.models.FileExplorerItem
import me.chill.ui.FolderTreeView
import me.chill.ui.markdownarea.MarkdownArea
import me.chill.utility.extensions.isImage
import me.chill.views.editor.Editor
import me.chill.views.editor.MenuBar
import me.chill.views.editor.ToolBar
import me.chill.views.editor.ToolBar.Position.LEFT
import me.chill.views.editor.ToolBar.Position.TOP
import me.chill.views.fragments.ExitFragment
import tornadofx.Controller
import tornadofx.add
import tornadofx.anchorpaneConstraints
import java.io.File

// TODO: Split out the controllers for the editing area
class EditorController : Controller(), ActionMapObserver {

  private val editor  = find<Editor>()
  private val fileExplorer = editor.fileExplorer
  private val markdownArea = editor.markdownArea

  private val toolBar = find<ToolBar>()
  private val menuBar = find<MenuBar>()

  private val statusBarController = find<StatusBarController>()

  init {
    fileExplorer.onDoubleClick(this::fileSelectionAction)
    markdownArea.onOpen { fileExplorerItem, tab -> openFileContents(fileExplorerItem.file, tab) }

    toolBar.addObserver(this)
    menuBar.addObserver(this)
  }

  override fun update(actionMap: ActionMap) = actionMapAction(actionMap)

  private fun actionMapAction(actionMap: ActionMap) {
    when (actionMap) {
      OPEN_FOLDER -> openFolder(primaryStage)
      CUT -> cut()
      COPY -> copy()
      PASTE -> paste()
      UNDO -> undo()
      REDO -> redo()
      SAVE_FILE -> saveFile()
      SAVE_ALL -> saveAll()
      OPTIONS -> launchOptions()
      EXIT -> exit()
      BOLD -> TODO()
      ITALIC -> TODO()
      UNDERLINE -> TODO()
      NEW_FOLDER -> TODO()
      NEW_MARKDOWN_FILE -> TODO()
      NEW_UNTITLED_FILE -> TODO()
      IMPORT_VCS -> TODO()
      EXPORT_PDF -> TODO()
      STRIKETHROUGH -> TODO()
      MOVE_TOOLBAR_TOP -> moveToolBar(TOP)
      MOVE_TOOLBAR_LEFT -> moveToolBar(LEFT)
      TOGGLE_TOOBAR_VISIBILITY -> toggleToolBar()
    }
  }

  // Populates the tree view with the folder structure
  private fun openFolder(primaryStage: Stage) {
    // TODO: Open folder relative to the current directory
    val folder = DirectoryChooser()
      .apply { title = "Open Folder" }
      .showDialog(primaryStage)

    folder ?: return

    markdownArea.root.clearArea()
    fileExplorer.loadFolder(folder)
  }

  private fun saveFile() {
    println("Saving file")
  }

  private fun saveAll() {
    println("Saving all")
  }

  private fun importFromVCS() {
    println("Importing from VCS")
  }

  private fun export() {
    println("Exporting")
  }

  private fun launchOptions() {
    println("Launching options")
  }

  private fun exit() {
    // TODO: Check if work is saved
    find<ExitFragment>().openModal(resizable = false)
  }

  private fun undo() = markdownArea.undo()

  private fun redo() = markdownArea.redo()

  private fun cut() = markdownArea.cut()

  private fun copy() = markdownArea.copy()

  private fun paste() = markdownArea.paste()

  // Toggles the visibility of the tool bar
  private fun toggleToolBar() {
    with(toolBar.root) {
      isVisible = !isVisible
    }
  }

  // Moves the tool bar
  private fun moveToolBar(position: ToolBar.Position) {
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
        markdownArea.root.openTab(fileItem, name)
      }
    }
  }

  private fun openFileContents(file: File, tab: Tab) {
    with((tab.content as MarkdownArea.ScrollArea).content) {
      replaceText(file.readText())
      requestFocus()
      moveTo(0)
      requestFollowCaret()
    }
  }
}