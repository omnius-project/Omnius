package me.chill.utility.extensions

fun <K, V> Map<K, V>.first(predicate: (Map.Entry<K, V>) -> Boolean) =
  filter(predicate).entries.first()
