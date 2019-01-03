package me.chill.models

import me.chill.actionmap.ActionMap
import me.chill.actionmap.ActionMap.*
import me.chill.actionmap.ActionMapObservable
import me.chill.actionmap.ActionMapObserver
import me.chill.configuration.ConfigurationManager.configuration
import me.chill.views.ToolBar.Position.LEFT
import me.chill.views.ToolBar.Position.TOP
import java.io.File


/**
 * Model for the editor - stores user preferences that can be loaded from the settings file
 */
object EditorModel : ActionMapObservable {

  private val listeners = mutableListOf<ActionMapObserver>()

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
    private set

  var currentFolder: File? = null
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

  fun toggleToolBarVisibility() {
    toolBarVisibility = !toolBarVisibility
    notifyObservers(TOGGLE_TOOLBAR_VISIBILITY)
  }
}