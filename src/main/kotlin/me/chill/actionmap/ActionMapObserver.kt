package me.chill.actionmap

/**
 * Classes implementing this interface can observe classes that implement the [ActionMapObservable]
 * interface and will be notified by these observables when an action from the [ActionMap] is triggered
 * so it can react accordingly.
 *
 * @see [ActionMapObservable]
 */
interface ActionMapObserver {

  /**
   * Observer is notified of an action from [ActionMap] that occurred from an [ActionMapObservable].
   *
   * @param actionMap Action triggered
   */
  fun update(actionMap: ActionMap)
}