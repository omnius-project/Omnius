package me.chill.actionmap

interface ActionMapObservable {
  fun addObserver(actionMapObserver: ActionMapObserver)
  fun removeObserver(actionMapObserver: ActionMapObserver)
  fun notifyObservers(actionMap: ActionMap)
}