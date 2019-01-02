package me.chill.models

import me.chill.actionmap.ActionMap
import me.chill.actionmap.ActionMap.*
import me.chill.actionmap.ActionMapObservable
import me.chill.actionmap.ActionMapObserver
import me.chill.views.editor.ToolBar
import me.chill.views.editor.ToolBar.Position.LEFT
import me.chill.views.editor.ToolBar.Position.TOP

/**
 * Model for the editor - stores user preferences that can be loaded from the settings file
 */
// TODO: Make this a singleton since it's going to be loaded once
class EditorModel private constructor(
  private var toolBarPosition: ToolBar.Position = TOP,
  private var toolBarVisibility: Boolean = true) : ActionMapObservable {

  private val listeners = mutableListOf<ActionMapObserver>()

  companion object {
    val INSTANCE = loadEditorPreferences()

    private fun loadEditorPreferences(): EditorModel {
      // TODO: Add settings reading functionality
      return EditorModel()
    }
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

  fun setToolBarPosition(toolBarPosition: ToolBar.Position): EditorModel {
    when (toolBarPosition) {
      TOP -> notifyObservers(MOVE_TOOLBAR_TOP)
      LEFT -> notifyObservers(MOVE_TOOLBAR_LEFT)
    }
    this.toolBarPosition = toolBarPosition
    return this
  }

  fun getToolBarPosition() = toolBarPosition

  fun toggleToolBarVisibility(): EditorModel {
    notifyObservers(TOGGLE_TOOBAR_VISIBILITY)
    this.toolBarVisibility = !this.toolBarVisibility
    return this
  }

  fun getToolBarVisibility() = toolBarVisibility
}