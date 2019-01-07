package me.chill.actionmap

/**
 * Classes implementing this interface can be observed by [ActionMapObserver]s which listen
 * for actions from the [ActionMap] to occur and act accordingly.
 *
 * @see [ActionMapObserver]
 */
interface ActionMapObservable {

  /**
   * Adds an [ActionMapObserver] to the observable.
   *
   * @param actionMapObserver [ActionMapObserver] to add to the observable
   */
  fun addObserver(actionMapObserver: ActionMapObserver)

  /**
   * Removes an [ActionMapObserver] from the observable.
   *
   * @param actionMapObserver [ActionMapObserver] to remove from the observable
   */
  fun removeObserver(actionMapObserver: ActionMapObserver)

  /**
   * Notifies all observers that an action from [ActionMap] has occurred.
   *
   * @param actionMap Action that occurred
   */
  fun notifyObservers(actionMap: ActionMap)
}