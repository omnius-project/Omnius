package me.chill.actionmap

/**
 * Subscribes to [ActionMapObservable] to be notified of changes to the configuration.
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