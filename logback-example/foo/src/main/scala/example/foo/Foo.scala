package example.foo

import example.logging.Logger

class Foo extends Logger {
  def doIt(): Unit = {
    logger.debug("Foo")
  }
}
