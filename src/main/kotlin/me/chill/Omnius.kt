package me.chill

import javafx.stage.Stage
import me.chill.styles.Styles
import me.chill.views.SplashScreen
import tornadofx.App

class Omnius : App(SplashScreen::class, Styles::class) {
  override fun start(stage: Stage) {
    stage.icons += resources.image("../../icon.png")
    super.start(stage)
  }
}

