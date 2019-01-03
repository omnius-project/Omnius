package me.chill.ui

import javafx.scene.Node
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import me.chill.utility.extensions.first
import kotlin.reflect.KClass

/**
 * Custom TabPane for adding new tabs based on certain events
 * <F> is the type of tabContentArea to fill each tab with
 * <CT> is the content type each tab will hold onto
 */
class TabContentArea<F : Node, CT>(private val contentType: KClass<F>) : TabPane() {

  private val openTabs = mutableMapOf<CT, Tab>()
  private var onNewFileOpenAction: ((CT, Tab) -> Unit)? = null

  fun setOnNewFileOpenAction(action: (CT, Tab) -> Unit) {
    onNewFileOpenAction = action
  }

  // TODO: Function should save the open tabs to be restored the next time the same folder is opened
  fun clearArea() {
    openTabs.clear()
    tabs.clear()
  }

  fun getCurrentTab() = selectionModel.selectedItem

  fun getCurrentTabData() = openTabs.first { it.value == getCurrentTab() }

  fun getCurrentTabContent() = getCurrentTabData().key

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