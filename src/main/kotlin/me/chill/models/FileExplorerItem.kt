package me.chill.models

import java.io.File

class FileExplorerItem(val file: File) {
  override fun toString(): String = file.name
}