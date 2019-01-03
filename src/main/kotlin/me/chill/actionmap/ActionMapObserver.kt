package me.chill.actionmap

import com.google.gson.JsonObject

/**
 * Classes implementing this interface can observe classes that implement the [ActionMapObservable]
 * interface and will be notified by these observables when an action from the [ActionMap] is triggered
 * so it can react accordingly.
 *
 * @see [ActionMapObservable]
 */
interface ActionMapObserver {

  /**
   * Observer is notified of an action from [ActionMap] that occurred from an [ActionMapObservable]
   * it is observing, might receive some data from the [ActionMapObservable].
   *
   * @param actionMap Action triggered
   * @param data JSON data to supply when the action is triggered
   */
  fun update(actionMap: ActionMap, data: JsonObject? = null)
}