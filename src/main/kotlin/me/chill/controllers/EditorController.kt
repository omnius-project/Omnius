package me.chill.controllers

import com.google.gson.JsonObject
import javafx.stage.DirectoryChooser
import me.chill.actionmap.ActionMap
import me.chill.actionmap.ActionMap.*
import me.chill.actionmap.ActionMapObserver
import me.chill.configuration.ConfigurationChangeObserver
import me.chill.configuration.ConfigurationManager
import me.chill.dialogs.ExitDialog
import me.chill.dialogs.OptionsDialog
import me.chill.models.EditorModel
import me.chill.models.EditorModel.currentFolder
import me.chill.models.EditorModel.toggleToolBarVisibility
import me.chill.models.EditorModel.toolBarPosition
import me.chill.ui.FolderTreeView
import me.chill.views.Editor
import me.chill.views.MarkdownArea
import me.chill.views.MenuBar
import me.chill.views.ToolBar
import me.chill.views.ToolBar.Position.LEFT
import me.chill.views.ToolBar.Position.TOP
import tornadofx.Controller

/**
 * Controller between [Editor] and [EditorModel].
 */
// TODO: Split out the controllers for the editing area
class EditorController : Controller(), ActionMapObserver, ConfigurationChangeObserver {

  private val markdownArea = find<MarkdownArea>()
  private val toolBar = find<ToolBar>()
  private val menuBar = find<MenuBar>()

  init {
    toolBar.addObserver(this)
    menuBar.addObserver(this)
  }

  override fun update(configuration: JsonObject?) {
    configuration
      ?: throw IllegalStateException("Configuration object cannot be null when the controller is handling it")
    ConfigurationManager.updateConfiguration(configuration)
  }

  override fun update(actionMap: ActionMap) {
    when (actionMap) {
      OPEN_FOLDER -> openFolder()
      CUT -> markdownArea.cut()
      COPY -> markdownArea.copy()
      PASTE -> markdownArea.paste()
      UNDO -> markdownArea.undo()
      REDO -> markdownArea.redo()
      SAVE_FILE -> saveFile()
      SAVE_ALL -> saveAll()
      OPTIONS -> showOptions()
      EXIT -> exit()
      BOLD -> TODO()
      ITALIC -> TODO()
      UNDERLINE -> TODO()
      STRIKETHROUGH -> TODO()
      NEW_FOLDER -> TODO()
      NEW_MARKDOWN_FILE -> TODO()
      NEW_UNTITLED_FILE -> TODO()
      IMPORT_VCS -> importFromVCS()
      EXPORT_PDF -> export()
      MOVE_TOOLBAR_TOP -> toolBarPosition = TOP
      MOVE_TOOLBAR_LEFT -> toolBarPosition = LEFT
      TOGGLE_TOOLBAR_VISIBILITY -> toggleToolBarVisibility()
      else -> return
    }
  }

  /**
   * Opens a folder selection dialog box for the user to select the folder to expand.
   * If the user selects cancel, the dialog closes, returning null, when this happens, the
   * method stops.
   *
   * Changes the [currentFolder] variable of the [EditorModel] which triggers the view to
   * update the [FolderTreeView] accordingly.
   */
  // TODO: Open folder relative to the current directory
  private fun openFolder() {
    val folder = DirectoryChooser()
      .apply { title = "Open Folder" }
      .showDialog(primaryStage)

    folder ?: return

    currentFolder = folder
  }

  /**
   * Launches the [OptionsDialog] modal allowing users to change their user preferences.
   *
   * Subscribes the [EditorController] as an [ActionMapObserver] to the [OptionsDialog],
   * listening for primarily [OPTIONS_SAVE] events in order to trigger the [ConfigurationManager]
   * to update the configurations.
   */
  private fun showOptions() {
    with(find<OptionsDialog>()) {
      addObserver(this@EditorController)
      openModal()
    }
  }

  /**
   * Launches the [ExitDialog] modal allowing users to decide if they want to leave the editor
   * before saving their files.
   *
   * If users exit even after prompting, changes to their files will be unsaved.
   */
  // TODO: Check if work is saved
  private fun exit() {
    find<ExitDialog>().openModal(resizable = false)
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
}