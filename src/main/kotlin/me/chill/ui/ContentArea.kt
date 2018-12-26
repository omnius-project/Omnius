package me.chill.ui

import javafx.scene.Node
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.control.TextArea
import me.chill.utility.extensions.first
import java.io.File
import kotlin.reflect.KClass

/**
 * Custom TabPane for adding new tabs based on certain events
 * <F> is the type of contentArea to fill each tab with
 * <CT> is the content type each tab will hold onto
 */
class ContentArea<F : Node, CT>(private val contentType: KClass<F>) : TabPane() {

  private val openTabs = mutableMapOf<CT, Tab>()
  private var onOpenAction: ((CT, Tab) -> Unit)? = null

  fun setOnOpenAction(action: (CT, Tab) -> Unit) {
    onOpenAction = action
  }

  fun openTab(item: CT, title: String) {
    val tab = Tab(title)
      .apply { content = contentType.constructors.first().call() }
    tab.setOnClosed { openTabs.remove(item) }

    val matchKeyPredicate: (Map.Entry<CT, Tab>) -> Boolean = { it.key == item }
    val isFileAlreadyOpen = openTabs.none(matchKeyPredicate)

    if (isFileAlreadyOpen) {
      tabs.add(tab)
      selectionModel.select(tab)
      onOpenAction?.invoke(item, tab)
      openTabs[item] = tab
    } else {
      val existingTab = openTabs.first(matchKeyPredicate).value
      selectionModel.select(existingTab)
    }
  }
}