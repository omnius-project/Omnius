package me.chill.views.editor

import me.chill.styles.StatusBarStyles.Companion.statusBar
import tornadofx.*

class StatusBar : View() {
  override val root = hbox {
    addClass(statusBar)
    label("Hello world")

  }
}