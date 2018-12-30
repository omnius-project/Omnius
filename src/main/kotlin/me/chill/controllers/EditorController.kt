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
import me.chill.ui.TabContentArea
import me.chill.ui.markdownarea.MarkdownEditingArea
import me.chill.ui.markdownarea.MarkdownTextArea
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

  // Has to be lazily initialized because the view cannot be called before the view is initialized
  private val editor by lazy { find<Editor>() }

  private var fileExplorer: FolderTreeView
  private var contentArea: TabContentArea<MarkdownEditingArea, FileExplorerItem>
  private val toolBar = find<ToolBar>()
  private val menuBar = find<MenuBar>()

  private val statusBarController = find<StatusBarController>()

  init {
    fileExplorer = editor.fileExplorer
    contentArea = editor.tabContentArea

    fileExplorer.onDoubleClick(this::fileSelectionAction)
    contentArea.setOnOpenAction { fileItem, tab -> openFileContents(fileItem.file, tab) }

    toolBar.addObserver(this)
    menuBar.addObserver(this)
  }

  override fun update(actionMap: ActionMap) = actionMapAction(actionMap)

  // Populates the tree view with the folder structure
  private fun openFolder(primaryStage: Stage) {
    // TODO: Open folder relative to the current directory
    val folder = DirectoryChooser()
      .apply { title = "Open Folder" }
      .showDialog(primaryStage)

    folder ?: return

    contentArea.clearArea()
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

  private fun undoAction() {
    performActionInMarkdownArea { undo() }
  }

  private fun redoAction() {
    performActionInMarkdownArea { redo() }
  }

  private fun cut() {
    performActionInMarkdownArea { cut() }
  }

  private fun copy() {
    performActionInMarkdownArea { copy() }
  }

  private fun paste() {
    performActionInMarkdownArea { paste() }
  }

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

  private fun getCurrentTabMarkdownArea() = (contentArea.getCurrentTab().content as MarkdownEditingArea).content

  private fun performActionInMarkdownArea(action: MarkdownTextArea.() -> Unit) {
    with(getCurrentTabMarkdownArea()) {
      if (isFocused) action()
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

  private fun actionMapAction(actionMap: ActionMap) {
    when (actionMap) {
      OPEN_FOLDER -> openFolder(primaryStage)
      CUT -> cut()
      COPY -> copy()
      PASTE -> paste()
      UNDO -> undoAction()
      REDO -> redoAction()
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

  private fun openFileContents(file: File, tab: Tab) {
    with((tab.content as MarkdownEditingArea).content) {
      replaceText(file.readText())
      requestFocus()
      moveTo(0)
      requestFollowCaret()
    }
  }
}