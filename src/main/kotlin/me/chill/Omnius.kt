package me.chill

import javafx.stage.Stage
import me.chill.styles.*
import me.chill.views.editor.Editor
import tornadofx.App

// TODO: Introduce a custom title bar
class Omnius : App(
  Editor::class,
  MenuBarStyles::class,
  ToolBarStyles::class,
  EditingAreaStyles::class,
  StatusBarStyles::class,
  Styles::class
) {
  override fun start(stage: Stage) {
    stage.isMaximized = true
    stage.icons += resources.image("../../icon.png")
    super.start(stage)
  }
}

