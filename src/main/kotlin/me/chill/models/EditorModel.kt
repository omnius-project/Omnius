package me.chill.models

import com.google.gson.JsonObject
import me.chill.actionmap.ActionMap
import me.chill.actionmap.ActionMap.*
import me.chill.actionmap.ActionMapObservable
import me.chill.actionmap.ActionMapObserver
import me.chill.configuration.ConfigurationChangeObserver
import me.chill.configuration.ConfigurationManager
import me.chill.configuration.ConfigurationManager.configuration
import me.chill.views.Editor
import me.chill.views.ToolBar.Position.LEFT
import me.chill.views.ToolBar.Position.TOP
import java.io.File

/**
 * Model for the editor - stores user preferences that can be loaded from the settings file
 * using [ConfigurationManager].
 */
// TODO: Change the configuration property changes to another observer interface to prevent confusion
object EditorModel : ActionMapObservable, ConfigurationChangeObserver {

  private val listeners = mutableListOf<ActionMapObserver>()

  init {
    ConfigurationManager.addObserver(this)
  }

  var toolBarPosition = configuration.toolBarPosition
    set(value) {
      field = value
      notifyObservers(
        when (value) {
          TOP -> MOVE_TOOLBAR_TOP
          LEFT -> MOVE_TOOLBAR_LEFT
        }
      )
    }

  var toolBarVisibility = configuration.toolBarVisibility
    private set(value) {
      field = value
      notifyObservers(TOGGLE_TOOLBAR_VISIBILITY)
    }

  var currentFolder: File? =
    with(configuration.previousOpenFolderPath) {
      this ?: return@with null
      File(this)
    }
    set(value) {
      field = value
      notifyObservers(FOLDER_CHANGED)
    }

  var fontSize = configuration.fontSize
    set(value) {
      field = value
      notifyObservers(FONT_SIZE_CHANGED)
    }

  var fontFamily = configuration.fontFamily
    set(value) {
      field = value.toList()
      notifyObservers(FONT_FAMILY_CHANGED)
    }

  override fun addObserver(actionMapObserver: ActionMapObserver) {
    listeners.add(actionMapObserver)
  }

  override fun removeObserver(actionMapObserver: ActionMapObserver) {
    listeners.remove(actionMapObserver)
  }

  override fun notifyObservers(actionMap: ActionMap) {
    listeners.forEach { it.update(actionMap) }
  }

  override fun update(configuration: JsonObject?) {
    updateState()
  }

  fun toggleToolBarVisibility() {
    toolBarVisibility = !toolBarVisibility
    notifyObservers(TOGGLE_TOOLBAR_VISIBILITY)
  }

  private fun updateState() {
    toolBarPosition = configuration.toolBarPosition
    toolBarVisibility = configuration.toolBarVisibility
    fontSize = configuration.fontSize
    fontFamily = configuration.fontFamily
  }
}