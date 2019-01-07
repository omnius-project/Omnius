package me.chill.configuration

import com.google.gson.JsonObject

interface ConfigurationChangeObserver {
  fun update(configuration: JsonObject? = null)
}