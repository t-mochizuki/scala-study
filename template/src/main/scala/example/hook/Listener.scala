package example.hook

import example.logging.Logger
import scalikejdbc.GlobalSettings

trait Listener { self: Logger =>
  GlobalSettings.queryCompletionListener =
    (sql: String, params: scala.collection.Seq[Any], millis: Long) => {
      logger.info(
        Map(
          "sql"    -> sql,
          "params" -> params.mkString("[", ",", "]"),
          "millis" -> millis.toString
        ).toString
      )
    }

  GlobalSettings.queryFailureListener =
    (sql: String, params: scala.collection.Seq[Any], e: Throwable) => {
      logger.error(
        Map(
          "sql"     -> sql,
          "params"  -> params.mkString("[", ",", "]"),
          "message" -> (e.getMessage + "\n" + e.getStackTrace.take(7).mkString("\n"))
        ).toString
      )
    }
}
