package me.chill.models

import com.google.gson.JsonObject
import me.chill.actionmap.ActionMap
import me.chill.actionmap.ActionMap.*
import me.chill.actionmap.ActionMapObservable
import me.chill.actionmap.ActionMapObserver
import me.chill.configuration.ConfigurationManager
import me.chill.configuration.ConfigurationManager.configuration
import me.chill.views.ToolBar.Position.LEFT
import me.chill.views.ToolBar.Position.TOP
import java.io.File


/**
 * Model for the editor - stores user preferences that can be loaded from the settings file
 */
object EditorModel : ActionMapObservable, ActionMapObserver {

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
        },
        null
      )
    }

  var toolBarVisibility = configuration.toolBarVisibility
    set(value) {
      field = value
      notifyObservers(TOGGLE_TOOLBAR_VISIBILITY)
    }

  var currentFolder: File? = null
    set(value) {
      field = value
      notifyObservers(FOLDER_CHANGED, null)
    }

  var fontSize = configuration.fontSize
    set(value) {
      field = value
      notifyObservers(FONT_SIZE_CHANGED, null)
    }

  var fontFamily = configuration.fontFamily
    set(value) {
      field = value.toList()
      notifyObservers(FONT_FAMILY_CHANGED, null)
    }

  override fun addObserver(actionMapObserver: ActionMapObserver) {
    listeners.add(actionMapObserver)
  }

  override fun removeObserver(actionMapObserver: ActionMapObserver) {
    listeners.remove(actionMapObserver)
  }

  override fun notifyObservers(actionMap: ActionMap, data: JsonObject?) {
    listeners.forEach { it.update(actionMap, data) }
  }

  override fun update(actionMap: ActionMap, data: JsonObject?) {
    when (actionMap) {
      OPTIONS_SAVE -> updateState()
      else -> return
    }
  }

  private fun updateState() {
    toolBarPosition = configuration.toolBarPosition
    toolBarVisibility = configuration.toolBarVisibility
    fontSize = configuration.fontSize
    fontFamily = configuration.fontFamily
  }

  fun toggleToolBarVisibility() {
    toolBarVisibility = !toolBarVisibility
    notifyObservers(TOGGLE_TOOLBAR_VISIBILITY, null)
  }
}