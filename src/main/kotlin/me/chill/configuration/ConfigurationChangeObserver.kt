package me.chill.configuration

import com.google.gson.JsonObject

/**
 * Subscribes to [ConfigurationChangeObservable] to be notified of changes to the configuration.
 *
 * @see [ConfigurationChangeObservable]
 */
interface ConfigurationChangeObserver {

  /**
   * Observer is notified that configurations has been changed.
   *
   * @param configuration [JsonObject] containing the new configurations. Defaults to **null**.
   */
  fun update(configuration: JsonObject? = null)
}