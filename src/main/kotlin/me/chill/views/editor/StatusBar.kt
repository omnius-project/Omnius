package me.chill.views.editor

import javafx.scene.control.Label
import me.chill.styles.StatusBarStyles.Companion.statusBar
import tornadofx.View
import tornadofx.addClass
import tornadofx.hbox
import tornadofx.label

// TODO: Add a notification of background services system (include progress bar)
class StatusBar : View() {
  lateinit var statusMessage: Label

  override val root = hbox {
    addClass(statusBar)
    statusMessage = label("Hello world")
  }
}