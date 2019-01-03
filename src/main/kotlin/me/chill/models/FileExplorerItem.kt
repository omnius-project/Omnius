package me.chill.models

import java.io.File

/**
 * Simple wrapper around the [File] class that overwrites the [toString] method to contain the name
 * of the file.
 *
 * @param file File to represent
 */
class FileExplorerItem(val file: File) {
  override fun toString(): String = file.name
}