package me.chill.controllers

import javafx.application.Platform
import javafx.stage.Stage
import tornadofx.Controller

class ExitFragmentController : Controller() {
  fun exit() {
    // TODO: Check if work is saved
    // TODO: Save preferences on close
    Platform.exit()
  }
}