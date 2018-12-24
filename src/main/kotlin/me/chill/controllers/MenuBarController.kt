package me.chill.controllers

import tornadofx.Controller

class MenuBarController : Controller() {
  fun openFolder() {
    println("Opening folder")
  }

  fun saveFile() {
    println("Saving file")
  }

  fun saveAll() {
    println("Saving all")
  }

  fun importFromVCS() {
    println("Importing from VCS")
  }

  fun export() {
    println("Exporting")
  }

  fun exit() {
    println("Exiting")
  }
}