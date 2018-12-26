package me.chill.utility.extensions

import java.io.File

// TODO: Add more image extension types
private val imageExtensions = setOf("jpg", "jpeg", "tif", "gif", "png", "ico")

val File.isImage: Boolean
  get() = imageExtensions.contains(extension)