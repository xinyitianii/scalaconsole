package org.scalaconsole.fxui
import javafx.scene.text.Font

object Variables {
  var commandlineOption = Option.empty[String]

  def decodeFont(desc: String) = {
    val Array(family, size) = desc.split("-")
    Font.font(family, size.toDouble)
  }
  def encodeFont(f: Font) = s"${f.getFamily}-${f.getSize.toInt}"

  var displayFont = decodeFont(System.getProperty("font", Constants.defaultFont))

  var currentScalaVersion = Constants.originScalaVersion

  var theme = "ace/theme/github"
}
