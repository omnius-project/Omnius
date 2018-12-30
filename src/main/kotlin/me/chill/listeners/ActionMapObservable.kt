package me.chill.listeners

import me.chill.keymap.ActionMap

interface ActionMapObservable {
  fun addObserver(actionMapObserver: ActionMapObserver)
  fun removeObserver(actionMapObserver: ActionMapObserver)
  fun notifyObservers(actionMap: ActionMap)
}