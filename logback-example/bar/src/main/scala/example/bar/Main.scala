package example.bar

import example.logging.Logger

object Main extends App with Logger {
  logger.info("Entering application.")

  val bar = new Bar()
  bar.doIt()

  logger.info("Exiting application.")
}
