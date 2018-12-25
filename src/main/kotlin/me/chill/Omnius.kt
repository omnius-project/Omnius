package me.chill

import javafx.stage.Stage
import me.chill.styles.MenuBarStyles
import me.chill.styles.Styles
import me.chill.views.editor.Editor
import tornadofx.App

class Omnius : App(Editor::class, MenuBarStyles::class, Styles::class) {
  override fun start(stage: Stage) {
    stage.isMaximized = true
    stage.title = "Omnius: Markdown Editor"
    stage.icons += resources.image("../../icon.png")
    super.start(stage)
  }
}

