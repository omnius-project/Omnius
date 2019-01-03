package me.chill.actionmap

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.*
import javafx.scene.input.KeyCombination
import javafx.scene.input.KeyCombination.keyCombination

enum class ActionMap(
  val actionName: String,
  val shortCut: KeyCombination? = null,
  val icon: FontAwesomeIcon? = null) {

  // Key combinations
  OPEN_FOLDER("Open Folder", keyCombination("Ctrl+O"), FOLDER_OPEN_ALT),
  CUT("Cut", keyCombination("Ctrl+X"), FontAwesomeIcon.CUT),
  COPY("Copy", keyCombination("Ctrl+C"), FontAwesomeIcon.COPY),
  PASTE("Paste", keyCombination("Ctrl+V"), FontAwesomeIcon.PASTE),
  UNDO("Undo", keyCombination("Ctrl+Z"), FontAwesomeIcon.UNDO),
  REDO("Redo", keyCombination("Ctrl+Y"), FontAwesomeIcon.UNDO),
  SAVE_FILE("Save File", keyCombination("Ctrl+S"), SAVE),
  SAVE_ALL("Save All", keyCombination("Ctrl+Shift+S"), FILES_ALT),
  OPTIONS("Options", keyCombination("Ctrl+Shift+O"), COG),
  EXIT("Exit", keyCombination("Ctrl+Shift+E")),
  BOLD("Bold", keyCombination("Ctrl+B"), FontAwesomeIcon.BOLD),
  ITALIC("Italic", keyCombination("Ctrl+I"), FontAwesomeIcon.ITALIC),
  UNDERLINE("Underline", keyCombination("Ctrl+U"), FontAwesomeIcon.UNDERLINE),

  // No combination
  NEW_FOLDER("New Folder", icon = FOLDER_ALT),
  NEW_MARKDOWN_FILE("New Markdown File (.md)", icon = FILE_ALT),
  NEW_UNTITLED_FILE("New Untitled File", icon = FILE_ALT),
  IMPORT_VCS("Import From VCS"),
  EXPORT_PDF("Export As PDF (.pdf)"),
  STRIKETHROUGH("Strikethrough", icon = FontAwesomeIcon.STRIKETHROUGH),
  MOVE_TOOLBAR_TOP("Toolbar Position Top"),
  MOVE_TOOLBAR_LEFT("Toolbar Position Left"),
  TOGGLE_TOOLBAR_VISIBILITY("Toggle Toolbar"),
  FOLDER_CHANGED("Folder Changed"),
  FONT_SIZE_CHANGED("Font Size Changed"),
  FONT_FAMILY_CHANGED("Font Family Changed"),
}