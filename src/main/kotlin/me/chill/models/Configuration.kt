package me.chill.models

import me.chill.views.MarkdownArea
import me.chill.views.ToolBar
import me.chill.views.ToolBar.Position.TOP

/**
 * Data class used to generate the configuration json used to hold settings.
 *
 * @param toolBarPosition Position of the toolbar - defaults to the [TOP]
 * @param toolBarVisibility Visibility of the toolbar - defaults to visible (true)
 * @param previousOpenFolderPath File path to the previous folder that was open before editor was closed
 * @param fontSize Font size of the text in the [MarkdownArea]
 * @param fontFamily Font name of the font family to use for the text in the [MarkdownArea]
 */
data class Configuration(
  val toolBarPosition: ToolBar.Position = TOP,
  val toolBarVisibility: Boolean = true,
  val previousOpenFolderPath: String? = null,
  val fontSize: Int = 16,
  val fontFamily: List<String> = listOf("monospace")
)