package me.chill.views.editor

import javafx.scene.control.Label
import javafx.scene.layout.Priority.ALWAYS
import me.chill.styles.StatusBarStyles.Companion.statusBar
import tornadofx.*

// TODO: Add a notification of background services system (include progress bar)
class StatusBar : View() {
  lateinit var statusMessage: Label
  private lateinit var caretPosition: Label

  override val root = hbox {
    addClass(statusBar)
    statusMessage = label()
    region { hgrow = ALWAYS }
    caretPosition = label("Hello")
  }
}