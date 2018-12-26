package me.chill.keymap

import javafx.scene.input.KeyCombination
import javafx.scene.input.KeyCombination.keyCombination

enum class Keymap(val keyCombination: KeyCombination) {
  OPEN_FOLDER(keyCombination("Ctrl+O")),
  CUT(keyCombination("Ctrl+X")),
  COPY(keyCombination("Ctrl+C")),
  PASTE(keyCombination("Ctrl+V")),
  UNDO(keyCombination("Ctrl+Z")),
  REDO(keyCombination("Ctrl+Y")),
  SAVE_FILE(keyCombination("Ctrl+S")),
  SAVE_ALL(keyCombination("Ctrl+Shift+S")),
  OPTIONS(keyCombination("Ctrl+Shift+O")),
  EXIT(keyCombination("Ctrl+Shift+E"))
}