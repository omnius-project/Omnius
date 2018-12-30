package me.chill.listeners

import me.chill.keymap.ActionMap

interface ActionMapObserver {
  fun update(actionMap: ActionMap)
}