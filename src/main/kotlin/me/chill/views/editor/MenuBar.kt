package me.chill.views.editor

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.*
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.scene.input.KeyCombination
import me.chill.controllers.MenuBarController
import tornadofx.*

class MenuBar : View() {
  private val controller: MenuBarController by inject()

  // TODO: Make a keymap system
  override val root = menubar {
    menu("File") {
      item("Open folder").apply {
        graphic = FontAwesomeIconView(FOLDER_OPEN)
        accelerator = KeyCombination.keyCombination("Ctrl+O")
      }.action(controller::openFolder)

      separator()

      item("Save").apply {
        graphic = FontAwesomeIconView(SAVE)
        accelerator = KeyCombination.keyCombination("Ctrl+S")
      }.action(controller::saveFile)
      item("Save All").apply {
        accelerator = KeyCombination.keyCombination("Ctrl+Shift+S")
      }.action(controller::saveAll)

      separator()

      item("Import from VCS").action(controller::importFromVCS)
      item("Export").action(controller::export)

      separator()
      item("Options").apply {
        graphic = FontAwesomeIconView(COG)
        accelerator = KeyCombination.keyCombination("Ctrl+Shift+O")
      }.action(controller::launchOptions)
      item("Exit").action(controller::exit)
    }

    menu("Edit") {
      item("Undo").apply {
        graphic = FontAwesomeIconView(UNDO)
        accelerator = KeyCombination.keyCombination("Ctrl+Z")
      }.action(controller::undoAction)
      item("Redo").apply {
        graphic = FontAwesomeIconView(FontAwesomeIcon.UNDO).apply {
          rotate = 180.0
        }
        accelerator = KeyCombination.keyCombination("Ctrl+Y")
      }.action(controller::redoAction)

      separator()

      item("Cut").apply {
        graphic = FontAwesomeIconView(FontAwesomeIcon.CUT)
        accelerator = KeyCombination.keyCombination("Ctrl+X")
      }.action(controller::cut)
      item("Copy").apply {
        graphic = FontAwesomeIconView(FontAwesomeIcon.COPY)
        accelerator = KeyCombination.keyCombination("Ctrl+C")
      }.action(controller::copy)
    }
  }
}