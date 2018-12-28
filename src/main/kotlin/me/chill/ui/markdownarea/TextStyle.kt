package me.chill.ui.markdownarea

import javafx.scene.paint.Color

class TextStyle private constructor(
  bold: Boolean?,
  italic: Boolean?,
  underline: Boolean?,
  strikethrough: Boolean?,
  fontSize: Int?,
  fontFamily: String?,
  textColor: Color?,
  backgroundColor: Color?) {

  fun toCss(): String {
    return ""
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

    fun fontFamily(fontFamily: String): Builder {
      this.fontFamily = fontFamily
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