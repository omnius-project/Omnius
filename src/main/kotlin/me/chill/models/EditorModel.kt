package me.chill.models

import me.chill.actionmap.ActionMap
import me.chill.actionmap.ActionMap.*
import me.chill.actionmap.ActionMapObservable
import me.chill.actionmap.ActionMapObserver
import me.chill.views.editor.ToolBar.Position.LEFT
import me.chill.views.editor.ToolBar.Position.TOP
import java.io.File


/**
 * Model for the editor - stores user preferences that can be loaded from the settings file
 */
object EditorModel : ActionMapObservable {

  private val listeners = mutableListOf<ActionMapObserver>()

  var toolBarPosition = TOP
    set(value) {
      field = value
      notifyObservers(
        when (value) {
          TOP -> MOVE_TOOLBAR_TOP
          LEFT -> MOVE_TOOLBAR_LEFT
        }
      )
    }
  var toolBarVisibility = true
    private set
  var currentFolder: File? = null
    set(value) {
      field = value
      notifyObservers(FOLDER_CHANGED)
    }

  init {
    // TODO: Add settings loading here
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