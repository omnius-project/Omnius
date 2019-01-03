package me.chill.utility.extensions

/**
 * Returns the first [Map.Entry] that matches the [predicate].
 *
 * @param K Data type of key in Map
 * @param V Data type of value in Map
 * @param predicate Predicate to check each entry in the Map for
 * @return First [Map.Entry] that matches the [predicate]
 */
fun <K, V> Map<K, V>.first(predicate: (Map.Entry<K, V>) -> Boolean) =
  filter(predicate).entries.first()
