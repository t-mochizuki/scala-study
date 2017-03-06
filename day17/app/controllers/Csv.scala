package controllers

import play.api.mvc.{Action, Controller}
import java.io.File

object Csv extends Controller {
  def files(id: Long) = Action {
    Ok.sendFile(new java.io.File("/tmp/example.csv"))
  }
}
