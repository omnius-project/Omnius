package me.chill.views.editor

import me.chill.controllers.MenuBarController
import tornadofx.*

class MenuBar : View() {
  private val controller: MenuBarController by inject()

  override val root = menubar {
    menu("File") {
      item("Open folder").action(controller::openFolder)

      separator()

      item("Save").action(controller::saveFile)
      item("Save All").action(controller::saveAll)

      separator()

      item("Import from VCS").action(controller::importFromVCS)
      item("Export").action(controller::export)

      separator()
      item("Options").action(controller::launchOptions)
      item("Exit").action(controller::exit)
    }

    menu("Edit") {
      item("Undo").action(controller::undoAction)
      item("Redo").action(controller::redoAction)

      separator()

      item("Cut").action(controller::cut)
      item("Copy").action(controller::copy)
    }
  }
}