package me.chill.views.editor

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import javafx.geometry.Orientation.VERTICAL
import javafx.scene.control.ToolBar
import me.chill.actionmap.ActionMap
import me.chill.actionmap.ActionMap.*
import me.chill.actionmap.ActionMapObservable
import me.chill.actionmap.ActionMapObserver
import me.chill.utility.glyphtools.GlyphFactory
import tornadofx.*

// TODO: Make the visibility of the tool bar an observable value
// TODO: Allow the tool bar to be dragged around
class ToolBar : View(), ActionMapObservable {

  enum class Position {
    TOP, LEFT
  }

  private val listeners = mutableListOf<ActionMapObserver>()
  private val glyphFactory = GlyphFactory.Builder().glyphSize(24).build()

  override fun addObserver(actionMapObserver: ActionMapObserver) {
    listeners.add(actionMapObserver)
  }

  override fun removeObserver(actionMapObserver: ActionMapObserver) {
    listeners.remove(actionMapObserver)
  }

  override fun notifyObservers(actionMap: ActionMap) {
    listeners.forEach { it.update(actionMap) }
  }

  override val root = toolbar {
    managedProperty().bind(visibleProperty())

    buttons(NEW_UNTITLED_FILE, OPEN_FOLDER, SAVE_FILE)
    separator(VERTICAL)
    buttons(CUT, COPY, PASTE)
    separator(VERTICAL)
    buttons(BOLD, ITALIC, UNDERLINE, STRIKETHROUGH)
    separator(VERTICAL)
  }

  private fun ToolBar.button(actionMap: ActionMap) =
    with(actionMap) {
      button {
        prefWidth = 40.0
        prefHeight = 40.0
        icon?.let { graphic = addGlyph(it) }
        tooltip(actionName)
        action { notifyObservers(actionMap) }
      }
    }

  private fun ToolBar.buttons(vararg actionMaps: ActionMap) {
    actionMaps.forEach { button(it) }
  }

  private fun addGlyph(glyph: FontAwesomeIcon) = glyphFactory.make(glyph)
}