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

  fun copy() {
    performActionInMarkdownArea {
      getCurrentMarkdownArea().copy()
    }
  }

  fun paste() {
    performActionInMarkdownArea {
      getCurrentMarkdownArea().paste()
    }
  }

  fun cut() {
    performActionInMarkdownArea {
      getCurrentMarkdownArea().cut()
    }
  }

  fun undo() {
    performActionInMarkdownArea {
      getCurrentMarkdownArea().undo()
    }
  }

  fun redo() {
    performActionInMarkdownArea {
      getCurrentMarkdownArea().redo()
    }
  }

  private fun getCurrentMarkdownArea() = (root.getCurrentTab().content as ScrollArea).content

  private fun hasAnyTabs() = root.tabs.isNotEmpty()

  private fun isMarkdownAreaInFocus() = hasAnyTabs() && getCurrentMarkdownArea().isFocused

  private fun performActionInMarkdownArea(action: () -> Unit) {
    if (isMarkdownAreaInFocus()) action()
  }

}