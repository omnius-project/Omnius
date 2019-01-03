package me.chill.views

import javafx.stage.Stage
import me.chill.configuration.ConfigurationManager
import me.chill.controllers.EditorController
import me.chill.styles.*
import tornadofx.App
import tornadofx.find

// TODO: Introduce a custom title bar
class Omnius : App(
  Editor::class,
  MenuBarStyles::class,
  ToolBarStyles::class,
  EditingAreaStyles::class,
  StatusBarStyles::class,
  Styles::class) {

  init {
    find<EditorController>()
    ConfigurationManager.loadConfiguration()
  }

  override fun start(stage: Stage) {
    with(stage) {
      isMaximized = true
      icons += resources.image("../../../icon.png")
      super.start(this)
    }
  }
}

