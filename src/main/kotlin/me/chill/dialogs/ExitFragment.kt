package me.chill.dialogs

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.WARNING
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.geometry.Pos.CENTER
import javafx.scene.layout.Priority.ALWAYS
import javafx.scene.text.FontWeight.BOLD
import me.chill.controllers.ExitFragmentController
import me.chill.styles.Styles.Companion.dangerButton
import me.chill.styles.Styles.Companion.dangerRed
import tornadofx.*

class ExitFragment : Fragment("Close warning") {
  private val controller: ExitFragmentController by inject()

  override val root = vbox {
    spacing = 10.0

    style {
      padding = box(5.px, 10.px)
    }

    hbox {
      spacing = 20.0
      val warningIcon = FontAwesomeIconView(WARNING)
        .apply {
          glyphSize = 36
          fill = dangerRed
          alignment = CENTER
        }
      add(warningIcon)

      vbox {
        label("Are you sure you want to exit?") {
          style {
            fontWeight = BOLD
            fontSize = 20.px
          }
        }

        label("You have unsaved changes!")
      }
    }

    hbox {
      spacing = 10.0

      spacer(ALWAYS)

      button("Quit") {
        addClass(dangerButton)
        action(controller::exit)
      }

      button("Cancel") {
        action {
          close()
        }
      }
    }
  }
}