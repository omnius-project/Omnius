package me.chill.controllers

import me.chill.views.editor.StatusBar
import tornadofx.Controller
import tornadofx.find

class StatusBarController : Controller() {
  companion object {
    fun dispatchMessage(message: String) {
      find(StatusBar::class).statusMessage.text = message
    }
  }
}