package me.chill.utility.glyphtools

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.scene.paint.Color
import javafx.scene.paint.Paint

/**
 * Constant factory for generating glyphs
 */
class GlyphFactory private constructor(
  private val glyphSize: Int,
  private val glyphFill: Paint) {

  fun make(icon: FontAwesomeIcon) =
    FontAwesomeIconView(icon).apply {
      glyphSize = this@GlyphFactory.glyphSize
      fill = this@GlyphFactory.glyphFill
    }

  class Builder {
    private var glyphSize: Int = 16
    private var glyphFill: Paint = Color.BLACK

    fun glyphSize(glyphSize: Int): Builder {
      this.glyphSize = glyphSize
      return this
    }

    fun glyphFill(glyphFill: Paint): Builder {
      this.glyphFill = glyphFill
      return this
    }

    fun build() = GlyphFactory(glyphSize, glyphFill)
  }
}