package me.chill.utility.extensions

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import me.chill.configuration.ConfigurationManager.ConfigurationKeys
import java.io.File
import java.io.FileWriter

/**
 * Writes data as JSON format into a file, overwrites existing contents of the file if it already exists.
 *
 * @param T Generic data type of the data to write to the file
 * @param filePath File path to write the data to
 * @param data Data to be written to the file
 * @throws [IllegalArgumentException] If [filePath] does not exist
 */
fun <T> Gson.writeToFile(filePath: String, data: T) {
  with(File(filePath)) {
    if (!exists()) throw IllegalArgumentException("File path $filePath does not exist")

    val fileWriter = FileWriter(this)
    toJson(data, fileWriter)
    fileWriter.close()
  }
}

/**
 * Retrieves the JsonElement at the index of the specified [ConfigurationKeys].
 *
 * @param key Configuration key to retrieve
 * @return [JsonElement] if the key is found, else null value
 */
fun JsonObject.get(key: ConfigurationKeys): JsonElement? = get(key.keyName)

/**
 * Adds a property to a [JsonObject] with the key being a [ConfigurationKeys].
 *
 * @param T Data type of the [value]
 * @param key Configuration key
 * @param value Data to accompany key
 * @return The same [JsonObject] being acted upon to allow for method chaining
 * @throws [IllegalArgumentException] if [T] is not of type **String, Number, Chat or Boolean**
 */
fun <T> JsonObject.addProperty(key: ConfigurationKeys, value: T): JsonObject {
  when (value) {
    is String -> addProperty(key, value)
    is Number -> addProperty(key, value)
    is Char -> addProperty(key, value)
    is Boolean -> addProperty(key, value)
    else -> throw IllegalArgumentException("Value $value must be of type String, Number, Char or Boolean")
  }

  return this
}