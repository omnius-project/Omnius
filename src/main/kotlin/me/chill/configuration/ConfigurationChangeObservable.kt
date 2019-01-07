package me.chill.configuration

import com.google.gson.JsonObject

interface ConfigurationChangeObservable {
  fun addObserver(observer: ConfigurationChangeObserver)
  fun removeObserver(observer: ConfigurationChangeObserver)
  fun notifyObservers(configuration: JsonObject? = null)
}