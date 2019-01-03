package me.chill.configuration

import com.google.gson.Gson
import com.google.gson.JsonObject
import me.chill.actionmap.ActionMap
import me.chill.actionmap.ActionMap.OPTIONS_SAVE
import me.chill.actionmap.ActionMapObservable
import me.chill.actionmap.ActionMapObserver
import me.chill.models.Configuration
import me.chill.models.EditorModel.fontFamily
import me.chill.views.ToolBar.Position.TOP
import java.io.File
import java.io.FileReader
import java.io.FileWriter

/**
 * Manages the configurations of the editor for the installation
 */
object ConfigurationManager : ActionMapObservable {

  private val listeners = mutableListOf<ActionMapObserver>()
  private const val configurationFilePath = "config/config.json"
  private val gson = Gson()
  lateinit var configuration: Configuration
    private set

  override fun addObserver(actionMapObserver: ActionMapObserver) {
    listeners.add(actionMapObserver)
  }

  override fun removeObserver(actionMapObserver: ActionMapObserver) {
    listeners.remove(actionMapObserver)
  }

  override fun notifyObservers(actionMap: ActionMap, data: JsonObject?) {
    listeners.forEach { it.update(actionMap, data) }
  }

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

  /**
   * Updates the configuration file with the data
   */
  fun updateConfiguration(data: JsonObject) {
    val toolBarPosition = TOP
    val toolBarVisibility = data.get("toolBarVisibility")?.asBoolean ?: configuration.toolBarVisibility
    val fontSize = data.get("fontSize")?.asInt ?: configuration.fontSize
    val fontFamily = data.get("fontFamily")?.asJsonArray?.map { it.asString } ?: configuration.fontFamily

    configuration = Configuration(toolBarPosition, toolBarVisibility, null, fontSize, fontFamily)
    writeGsonToFile(configuration)
    notifyObservers(OPTIONS_SAVE)
  }

  private fun generateConfigurationFile(configurationFile: File) {
    configurationFile.parentFile.mkdir()
    configurationFile.createNewFile()
    writeGsonToFile(Configuration())
  }

  private fun <T> writeGsonToFile(data: T) {
    val fileWriter = FileWriter(configurationFilePath)
    gson.toJson(data, fileWriter)
    fileWriter.close()
  }
}