package me.chill.controllers

import javafx.application.Platform
import javafx.stage.Stage
import tornadofx.Controller

class ExitFragmentController : Controller() {
  fun exit() {
    // TODO: Save preferences on close
    Platform.exit()
  }
}