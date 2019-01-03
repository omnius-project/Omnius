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
object EditorModel : ActionMapObservable {

  private val listeners = mutableListOf<ActionMapObserver>()

  private var toolBarPosition = TOP
  private var toolBarVisibility = true

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

  fun setToolBarPosition(toolBarPosition: ToolBar.Position): EditorModel {
    notifyObservers(
      when (toolBarPosition) {
        TOP -> MOVE_TOOLBAR_TOP
        LEFT -> MOVE_TOOLBAR_LEFT
      }
    )
    this.toolBarPosition = toolBarPosition
    return this
  }

  fun getToolBarPosition() = toolBarPosition

  fun toggleToolBarVisibility(): EditorModel {
    notifyObservers(TOGGLE_TOOBAR_VISIBILITY)
    toolBarVisibility = !toolBarVisibility
    println(toolBarVisibility)
    return this
  }

  fun getToolBarVisibility() = toolBarVisibility
}