package me.chill.ui.markdownarea

import javafx.scene.control.Tab
import javafx.scene.control.TabPane.TabClosingPolicy.ALL_TABS
import me.chill.models.FileExplorerItem
import me.chill.ui.TabContentArea
import org.fxmisc.flowless.VirtualizedScrollPane
import tornadofx.View

/**
 * Markdown text placed into a scroll pane
 */
class MarkdownArea : View() {

  class ScrollArea : VirtualizedScrollPane<MarkdownTextArea>(MarkdownTextArea())

  override val root = TabContentArea<ScrollArea, FileExplorerItem>(ScrollArea::class)
    .apply { tabClosingPolicy = ALL_TABS }

  fun onOpen(openAction: (FileExplorerItem, Tab) -> Unit) {
    root.setOnOpenAction(openAction)
  }

  fun clearArea() = root.clearArea()

  fun copy() = performActionInMarkdownArea { copy() }

  fun paste() = performActionInMarkdownArea { paste() }

  fun cut() = performActionInMarkdownArea { cut() }

  fun undo() = performActionInMarkdownArea { undo() }

  fun redo() = performActionInMarkdownArea { redo() }

  private fun getCurrentMarkdownArea() = (root.getCurrentTab().content as ScrollArea).content

  private fun hasAnyTabs() = root.tabs.isNotEmpty()

  private fun isMarkdownAreaInFocus() = hasAnyTabs() && getCurrentMarkdownArea().isFocused

  private fun performActionInMarkdownArea(action: MarkdownTextArea.() -> Unit) {
    if (isMarkdownAreaInFocus()) getCurrentMarkdownArea().action()
  }

}