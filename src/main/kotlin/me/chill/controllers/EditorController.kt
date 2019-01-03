package me.chill.controllers

import javafx.stage.DirectoryChooser
import me.chill.actionmap.ActionMap
import me.chill.actionmap.ActionMap.*
import me.chill.actionmap.ActionMapObserver
import me.chill.models.EditorModel.currentFolder
import me.chill.models.EditorModel.toggleToolBarVisibility
import me.chill.models.EditorModel.toolBarPosition
import me.chill.views.editor.Editor
import me.chill.views.editor.MarkdownArea
import me.chill.views.editor.MenuBar
import me.chill.views.editor.ToolBar
import me.chill.views.editor.ToolBar.Position.LEFT
import me.chill.views.editor.ToolBar.Position.TOP
import me.chill.views.fragments.ExitFragment
import tornadofx.Controller

// TODO: Split out the controllers for the editing area
class EditorController : Controller(), ActionMapObserver {

  private val markdownArea = find<MarkdownArea>()
  private val toolBar = find<ToolBar>()
  private val menuBar = find<MenuBar>()

  init {
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
      MOVE_TOOLBAR_TOP -> toolBarPosition = TOP
      MOVE_TOOLBAR_LEFT -> toolBarPosition = LEFT
      TOGGLE_TOOLBAR_VISIBILITY -> toggleToolBarVisibility()
      else -> return
    }
  }

  // Populates the tree view with the folder structure
  private fun openFolder() {
    // TODO: Open folder relative to the current directory
    val folder = DirectoryChooser()
      .apply { title = "Open Folder" }
      .showDialog(primaryStage)

    folder ?: return

    currentFolder = folder
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
}