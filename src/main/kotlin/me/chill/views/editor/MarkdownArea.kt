package me.chill.views.editor

import javafx.scene.control.Tab
import javafx.scene.control.TabPane.TabClosingPolicy.ALL_TABS
import me.chill.models.FileExplorerItem
import me.chill.ui.TabContentArea
import me.chill.ui.markdownarea.MarkdownTextArea
import org.fxmisc.flowless.VirtualizedScrollPane
import tornadofx.View

/**
 * Markdown text placed into a scroll pane
 */
class MarkdownArea : View() {

  class ScrollArea : VirtualizedScrollPane<MarkdownTextArea>(MarkdownTextArea())

  override val root = TabContentArea<ScrollArea, FileExplorerItem>(ScrollArea::class)
    .apply {
      tabClosingPolicy = ALL_TABS
      setOnNewFileOpenAction(this@MarkdownArea::openFileContents)
    }

  fun clearArea() = root.clearArea()

  fun copy() = performActionInMarkdownArea { copy() }

  fun paste() = performActionInMarkdownArea { paste() }

  fun cut() = performActionInMarkdownArea { cut() }

  fun undo() = performActionInMarkdownArea { undo() }

  fun redo() = performActionInMarkdownArea { redo() }

  fun openTab(fileItem: FileExplorerItem, fileName: String) {
    root.openTab(fileItem, fileName)
  }

  private fun openFileContents(fileItem: FileExplorerItem, tab: Tab) {
    with((tab.content as ScrollArea).content) {
      replaceText(fileItem.file.readText())
      requestFocus()
      moveTo(0)
      requestFollowCaret()
    }
  }

  private fun getCurrentMarkdownArea() = (root.getCurrentTab().content as ScrollArea).content

  private fun hasAnyTabs() = root.tabs.isNotEmpty()

  private fun isMarkdownAreaInFocus() = hasAnyTabs() && getCurrentMarkdownArea().isFocused

  private fun performActionInMarkdownArea(action: MarkdownTextArea.() -> Unit) {
    if (isMarkdownAreaInFocus()) getCurrentMarkdownArea().action()
  }

}