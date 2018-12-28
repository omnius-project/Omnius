package me.chill.ui.markdownarea

import javafx.scene.paint.Color

class TextStyle private constructor(
  private val bold: Boolean?,
  private val italic: Boolean?,
  private val underline: Boolean?,
  private val strikethrough: Boolean?,
  private val fontSize: Int?,
  private val fontFamily: String?,
  private val textColor: Color?,
  private val backgroundColor: Color?) {

  fun toCss(): String {
    val css = StringBuilder()
    bold?.let { css.append(createBooleanCssRule("font-weight", "bold", "normal", it)) }
    italic?.let { css.append(createBooleanCssRule("font-style", "italic", "normal", it)) }
    underline?.let { css.append(createBooleanCssRule("underline", true, false, it)) }
    strikethrough?.let { css.append(createBooleanCssRule("strikethrough", true, false, it)) }
    fontSize?.let { css.append(createCssRule("font-size", it)) }
    fontFamily?.let { css.append(createCssRule("font-family", it)) }
    textColor?.let { css.append(createCssRule("fill", cssColor(it))) }
    backgroundColor?.let { css.append(createCssRule("background-color", cssColor(it))) }

    return css.toString()
  }

  private fun <T> createCssRule(rule: String, value: T) = "-fx-$rule: $value;\n"

  private fun <T> createBooleanCssRule(rule: String, truthy: T, falsey: T, condition: Boolean) =
    createCssRule(rule, if (condition) truthy else falsey)

  private fun cssColor(color: Color): String {
    val red = (color.red * 255).toInt()
    val green = (color.green * 255).toInt()
    val blue = (color.blue * 255).toInt()
    return "rgb($red, $green, $blue)"
  }

  class Builder {
    private var bold: Boolean? = null
    private var italic: Boolean? = null
    private var underline: Boolean? = null
    private var strikethrough: Boolean? = null
    private var fontSize: Int? = null
    private var fontFamily: String? = null
    private var textColor: Color? = null
    private var backgroundColor: Color? = null

    fun bold(bold: Boolean): Builder {
      this.bold = bold
      return this
    }

    fun italic(italic: Boolean): Builder {
      this.italic = italic
      return this
    }

    fun underline(underline: Boolean): Builder {
      this.underline = underline
      return this
    }

    fun strikethrough(strikethrough: Boolean): Builder {
      this.strikethrough = strikethrough
      return this
    }

    fun fontSize(fontSize: Int): Builder {
      this.fontSize = fontSize
      return this
    }

    fun fontFamily(vararg fontFamily: String): Builder {
      this.fontFamily = fontFamily.joinToString(", ") { "\"$it\"" }
      return this
    }

    fun textColor(textColor: Color): Builder {
      this.textColor = textColor
      return this
    }

    fun backgroundColor(backgroundColor: Color): Builder {
      this.backgroundColor = backgroundColor
      return this
    }

    fun build() = TextStyle(bold, italic, underline, strikethrough, fontSize, fontFamily, textColor, backgroundColor)
  }
}