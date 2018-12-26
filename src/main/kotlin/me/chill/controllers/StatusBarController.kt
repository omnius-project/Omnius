package me.chill.controllers

import me.chill.views.editor.StatusBar
import tornadofx.Controller

class StatusBarController : Controller() {
  fun dispatchMessage(message: String) {
    find<StatusBar>().statusMessage.text = message
  }
}