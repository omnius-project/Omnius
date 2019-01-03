package me.chill.configuration

import com.google.gson.Gson
import me.chill.models.Configuration
import java.io.File
import java.io.FileReader
import java.io.FileWriter

/**
 * Manages the configurations of the editor for the installation
 */
object ConfigurationManager {

  private const val configurationFilePath = "config/config.json"
  private val gson = Gson()
  lateinit var configuration: Configuration
    private set

  /**
   * Loads the user's settings - creates the settings config file if not made yet
   */
  // TODO: Make a proper way to load settings into the user's machine
  fun loadConfiguration() {
    with(File(configurationFilePath)) {
      if (!exists()) {
        generateConfigurationFile(this)
      }

      configuration = gson.fromJson(FileReader(this), Configuration::class.java)
    }
  }

  private fun generateConfigurationFile(configurationFile: File) {
    configurationFile.parentFile.mkdir()
    configurationFile.createNewFile()

    val fileWriter = FileWriter(configurationFilePath)
    gson.toJson(Configuration(), fileWriter)
    fileWriter.close()
  }
}