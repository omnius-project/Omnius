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

      item("Exit").action(controller::exit)
    }
  }
}