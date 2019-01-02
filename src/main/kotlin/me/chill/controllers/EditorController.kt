package me.chill.controllers

import javafx.scene.control.Tab
import javafx.stage.DirectoryChooser
import me.chill.actionmap.ActionMap
import me.chill.actionmap.ActionMap.*
import me.chill.actionmap.ActionMapObserver
import me.chill.models.FileExplorerItem
import me.chill.utility.extensions.isImage
import me.chill.views.editor.Editor
import me.chill.views.editor.MarkdownArea
import me.chill.views.editor.MenuBar
import me.chill.views.editor.ToolBar
import me.chill.views.editor.ToolBar.Position.LEFT
import me.chill.views.editor.ToolBar.Position.TOP
import me.chill.views.fragments.ExitFragment
import me.chill.models.EditorModel.Companion.INSTANCE
import tornadofx.Controller
import java.io.File

// TODO: Split out the controllers for the editing area
class EditorController : Controller(), ActionMapObserver {

  private val editor = find<Editor>()
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
      OPEN_FOLDER -> openFolder()
      CUT -> markdownArea.cut()
      COPY -> markdownArea.copy()
      PASTE -> markdownArea.paste()
      UNDO -> markdownArea.undo()
      REDO -> markdownArea.redo()
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
      IMPORT_VCS -> importFromVCS()
      EXPORT_PDF -> export()
      STRIKETHROUGH -> TODO()
      MOVE_TOOLBAR_TOP -> INSTANCE.setToolBarPosition(TOP)
      MOVE_TOOLBAR_LEFT -> INSTANCE.setToolBarPosition(LEFT)
      TOGGLE_TOOBAR_VISIBILITY -> INSTANCE.toggleToolBarVisibility()
    }
  }

  // Populates the tree view with the folder structure
  private fun openFolder() {
    // TODO: Open folder relative to the current directory
    val folder = DirectoryChooser()
      .apply { title = "Open Folder" }
      .showDialog(primaryStage)

    folder ?: return

    markdownArea.clearArea()
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