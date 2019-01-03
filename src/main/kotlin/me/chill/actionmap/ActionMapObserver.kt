package me.chill.actionmap

import com.google.gson.JsonObject

interface ActionMapObserver {

  /**
   * Pass an optional JsonObject with settings if necessary
   *
   * @param actionMap Action triggered
   * @param data Json data to supply when the action is triggered
   */
  fun update(actionMap: ActionMap, data: JsonObject? = null)
}