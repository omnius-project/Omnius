package me.chill.configuration

import com.google.gson.Gson
import com.google.gson.JsonObject
import me.chill.actionmap.ActionMap
import me.chill.actionmap.ActionMap.OPTIONS_SAVE
import me.chill.actionmap.ActionMapObservable
import me.chill.actionmap.ActionMapObserver
import me.chill.configuration.ConfigurationManager.ConfigurationKeys.*
import me.chill.models.Configuration
import me.chill.models.EditorModel
import me.chill.utility.extensions.get
import me.chill.utility.extensions.writeToFile
import me.chill.views.ToolBar.Position.TOP
import java.io.File
import java.io.FileReader

/**
 * Manages the configurations of the editor for the installation.
 *
 * Editing the configurations will notify [EditorModel] and the model will react accordingly to
 * the changes made
 *
 * Is a [ActionMapObservable]
 * - Observed by [EditorModel] for changes in the configuration.
 */
object ConfigurationManager : ActionMapObservable {

  /**
   * Constant set of keys the configuration JSON should use.
   *
   * @param keyName Name of the JSON key to use
   */
  enum class ConfigurationKeys(val keyName: String) {
    TOOLBAR_POSITION("toolBarPosition"),
    TOOLBAR_VISIBILITY("toolBarVisibility"),
    FONT_SIZE("fontSize"),
    FONT_FAMILY("fontFamily"),
  }

  private const val configurationFilePath = "config/config.json"

  private val listeners = mutableListOf<ActionMapObserver>()

  private val gson = Gson()
  lateinit var configuration: Configuration
    private set

  /**
   * Updates the configuration file with the data
   *
   * @param data JSON object for the updated configurations
   */
  fun updateConfiguration(data: JsonObject) {
    val toolBarPosition = TOP
    val toolBarVisibility = data.get(TOOLBAR_VISIBILITY)?.asBoolean ?: configuration.toolBarVisibility
    val fontSize = data.get(FONT_SIZE)?.asInt ?: configuration.fontSize
    val fontFamily = data.get(FONT_FAMILY)?.asJsonArray?.map { it.asString } ?: configuration.fontFamily

    configuration = Configuration(toolBarPosition, toolBarVisibility, null, fontSize, fontFamily)
    gson.writeToFile(configurationFilePath, configuration)
    notifyObservers(OPTIONS_SAVE)
  }

  /**
   * Loads the user's settings - creates the settings config file if not made yet
   */
  // TODO: Make a proper way to load settings into the user's machine
  fun loadConfiguration() {
    with(File(configurationFilePath)) {
      if (!exists()) generateConfigurationFile(this)

      val fileReader = FileReader(this)
      configuration = gson.fromJson(fileReader, Configuration::class.java)
      fileReader.close()
    }
  }

  /**
   * Creates the configuration folder and configuration JSON file.
   *
   * @param configurationFile Configuration file which contains the full file path for it
   */
  private fun generateConfigurationFile(configurationFile: File) {
    configurationFile.parentFile.mkdir()
    configurationFile.createNewFile()
    gson.writeToFile(configurationFilePath, Configuration())
  }

  override fun addObserver(actionMapObserver: ActionMapObserver) {
    listeners.add(actionMapObserver)
  }

  override fun removeObserver(actionMapObserver: ActionMapObserver) {
    listeners.remove(actionMapObserver)
  }

  override fun notifyObservers(actionMap: ActionMap, data: JsonObject?) {
    listeners.forEach { it.update(actionMap, data) }
  }
}