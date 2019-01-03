package me.chill.utility.extensions

import com.google.gson.Gson
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