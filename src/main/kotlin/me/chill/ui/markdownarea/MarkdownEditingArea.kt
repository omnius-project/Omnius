package me.chill.ui.markdownarea

import org.fxmisc.flowless.VirtualizedScrollPane

/**
 * Markdown text placed into a scroll pane
 */
class MarkdownEditingArea : VirtualizedScrollPane<MarkdownTextArea>(MarkdownTextArea())