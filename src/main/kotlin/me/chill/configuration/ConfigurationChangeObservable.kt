package me.chill.configuration

import com.google.gson.JsonObject

/**
 * [ConfigurationChangeObserver] subscribe to this to be notified when changes are made to the
 * configuration.
 *
 * @see [ConfigurationChangeObserver]
 */
interface ConfigurationChangeObservable {

  /**
   * Adds a [ConfigurationChangeObserver] to the observable.
   *
   * @param observer [ConfigurationChangeObserver] to add to the observable
   */
  fun addObserver(observer: ConfigurationChangeObserver)

  /**
   * Removes a [ConfigurationChangeObserver] from the observable.
   *
   * @param observer [ConfigurationChangeObserver] to remove from the observable
   */
  fun removeObserver(observer: ConfigurationChangeObserver)

  /**
   * Notifies all observers that configurations have been changed.
   *
   * @param configuration Optional [JsonObject] that contains the changed configurations
   */
  fun notifyObservers(configuration: JsonObject? = null)
}