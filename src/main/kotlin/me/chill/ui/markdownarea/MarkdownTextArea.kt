package me.chill.ui.markdownarea

import javafx.scene.Node
import javafx.scene.paint.Color.BLACK
import javafx.scene.text.TextFlow
import org.fxmisc.richtext.GenericStyledArea
import org.fxmisc.richtext.LineNumberFactory
import org.fxmisc.richtext.StyledTextArea
import org.fxmisc.richtext.TextExt
import org.fxmisc.richtext.model.SegmentOps
import org.fxmisc.richtext.model.StyledSegment

/**
 * Rich text area for the markdown rendering
 */
class MarkdownTextArea : GenericStyledArea<ParagraphStyle, String, TextStyle>(
  ParagraphStyle(),
  { paragraph: TextFlow, style: ParagraphStyle -> paragraph.style = style.toCss() },
  TextStyle().fontSize(20).fontFamily("Monaco", "Source Code Pro").textColor(BLACK),
  SegmentOps.styledTextOps<TextStyle>(),
  false,
  { seg -> createNode(seg) { text, style -> text.style = style.toCss() } }) {
  // TODO: Pass an EditableStyleDocument for allowing split window editing
  // TODO: When the user references an image in markdown, replace it with the actual image if the text is not in focus
  // TODO: Quotes get aligned to the right automatically

  init {
    isWrapText = true
    paragraphGraphicFactory = LineNumberFactory.get(this)
  }

  companion object {
    fun createNode(
      segment: StyledSegment<String, TextStyle>,
      applyStyle: (TextExt, TextStyle) -> Unit): Node =
      StyledTextArea.createStyledTextNode(segment, applyStyle)
  }
}