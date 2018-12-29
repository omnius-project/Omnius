package me.chill.ui.markdownarea

import javafx.scene.paint.Color

class TextStyle private constructor(
  private var bold: Boolean?,
  private var italic: Boolean?,
  private var underline: Boolean?,
  private var strikethrough: Boolean?,
  private var fontSize: Int?,
  private var fontFamily: String?,
  private var textColor: Color?,
  private var backgroundColor: Color?) {

  constructor() : this(null, null, null, null, null, null, null, null)

  fun bold(bold: Boolean): TextStyle {
    this.bold = bold
    return this
  }

  fun italic(italic: Boolean): TextStyle {
    this.italic = italic
    return this
  }

  fun underline(underline: Boolean): TextStyle {
    this.underline = underline
    return this
  }

  fun strikethrough(strikethrough: Boolean): TextStyle {
    this.strikethrough = strikethrough
    return this
  }

  fun fontSize(fontSize: Int): TextStyle {
    this.fontSize = fontSize
    return this
  }

  fun fontFamily(vararg fontFamily: String): TextStyle {
    this.fontFamily = fontFamily.joinToString(", ") { "\"$it\"" }
    return this
  }

  fun textColor(textColor: Color): TextStyle {
    this.textColor = textColor
    return this
  }

  fun backgroundColor(backgroundColor: Color): TextStyle {
    this.backgroundColor = backgroundColor
    return this
  }

  fun toCss(): String {
    val css = StringBuilder()
    css.appendCss(bold) { createBooleanCssRule("font-weight", "bold", "normal", it) }
    css.appendCss(italic) { createBooleanCssRule("font-style", "italic", "normal", it) }
    css.appendCss(underline) { createBooleanCssRule("underline", true, false, it) }
    css.appendCss(strikethrough) { createBooleanCssRule("strikethrough", true, false, it) }
    css.appendCss(fontSize) { createCssRule("font-size", it) }
    css.appendCss(fontFamily) { createCssRule("font-family", it) }
    css.appendCss(textColor) { createCssRule("fill", cssColor(it)) }
    css.appendCss(backgroundColor) { createCssRule("background-color", cssColor(it)) }

    return css.toString()
  }

  private fun <T> StringBuilder.appendCss(element: T?, action: (T) -> String) {
    element?.let { append(action(it)) }
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
}