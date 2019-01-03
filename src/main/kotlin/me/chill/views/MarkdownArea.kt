package me.chill.views

import javafx.scene.control.Tab
import javafx.scene.control.TabPane.TabClosingPolicy.ALL_TABS
import me.chill.models.FileExplorerItem
import me.chill.ui.TabContentArea
import me.chill.ui.markdownarea.MarkdownTextArea
import me.chill.utility.extensions.isMarkdown
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

  fun openTab(fileItem: FileExplorerItem, fileName: String) = root.openTab(fileItem, fileName)

  /**
   * Load the contents of the opened file in a new tab.
   *
   * If the file to load is a markdown (.md) file, perform syntax parsing and apply text styles to
   * the related elements.
   *
   * @param fileItem [FileExplorerItem] to display the contents of
   * @param tab Current open tab to load the file contents into from [fileItem]
   */
  private fun openFileContents(fileItem: FileExplorerItem, tab: Tab) {
    val file = fileItem.file
    with((tab.content as ScrollArea).content) {
      replaceText(file.readText())
      if (file.isMarkdown) parseMarkdown()
      requestFocus()
      moveTo(0)
      requestFollowCaret()
    }
  }

  private fun parseMarkdown() {

  }

  private fun getCurrentMarkdownArea() = (root.getCurrentTab().content as ScrollArea).content

  private fun hasAnyTabs() = root.tabs.isNotEmpty()

  private fun isMarkdownAreaInFocus() = hasAnyTabs() && getCurrentMarkdownArea().isFocused

  private fun performActionInMarkdownArea(action: MarkdownTextArea.() -> Unit) {
    if (isMarkdownAreaInFocus()) getCurrentMarkdownArea().action()
  }

}