package me.chill.ui

import javafx.scene.Node
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import me.chill.utility.extensions.first
import kotlin.reflect.KClass

/**
 * Custom TabPane for adding new tabs based on certain events
 *
 * @param F Content type each tab will be filled with
 * @param CT Content type each type will hold
 * @param contentType KClass of [CT] - added because **reified** cannot be specified for a class
 */
class TabContentArea<F : Node, CT>(private val contentType: KClass<F>) : TabPane() {

  private val openTabs = mutableMapOf<CT, Tab>()
  private var onNewFileOpenAction: ((CT, Tab) -> Unit)? = null

  fun setOnNewFileOpenAction(action: (CT, Tab) -> Unit) {
    onNewFileOpenAction = action
  }

  /**
   * Clears the entire content area of any existing tabs.
   */
  // TODO: Function should save the open tabs to be restored the next time the same folder is opened
  fun clearArea() {
    openTabs.clear()
    tabs.clear()
  }

  fun getCurrentTab() = selectionModel.selectedItem

  fun getCurrentTabData() = openTabs.first { it.value == getCurrentTab() }

  fun getCurrentTabContent() = getCurrentTabData().key

  /**
   * Opens a tab in the content area with title as [title].
   *
   * If the tab has already been opened, the content area will focus onto the folder instead.
   *
   * @param item Item to open the tab with
   * @param title Title of the tab
   */
  fun openTab(item: CT, title: String) {
    val tab = Tab(title)
      .apply {
        content = contentType.constructors.first().call()
        setOnClosed { openTabs.remove(item) }
      }

    val matchingKey: (Map.Entry<CT, Tab>) -> Boolean = { it.key == item }
    val isFileAlreadyOpen = openTabs.any(matchingKey)

    if (isFileAlreadyOpen) {
      selectionModel.select(openTabs.first(matchingKey).value)
    } else {
      tabs.add(tab)
      selectionModel.select(tab)
      onNewFileOpenAction?.invoke(item, tab)
      openTabs[item] = tab
    }
  }
}