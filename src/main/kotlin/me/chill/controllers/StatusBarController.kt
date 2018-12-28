package me.chill.controllers

import me.chill.views.editor.StatusBar
import tornadofx.Controller

class StatusBarController : Controller() {
  // TODO: After 3 seconds, remove the message
  fun dispatchMessage(message: String) {
    find<StatusBar>().statusMessage.text = message
  }
}