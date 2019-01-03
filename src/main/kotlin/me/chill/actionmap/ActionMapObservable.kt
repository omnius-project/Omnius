package me.chill.actionmap

import com.google.gson.JsonObject

interface ActionMapObservable {
  fun addObserver(actionMapObserver: ActionMapObserver)
  fun removeObserver(actionMapObserver: ActionMapObserver)
  fun notifyObservers(actionMap: ActionMap, data: JsonObject? = null)
}