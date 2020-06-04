package example.bar

import example.logging.Logger

class Bar extends Logger {
  def doIt(): Unit = {
    logger.debug("Bar")
  }
}
