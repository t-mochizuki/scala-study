package example.foo

import example.logging.Logger

object Main extends App with Logger {
  logger.info("Entering application.")

  val foo = new Foo()
  foo.doIt()

  logger.info("Exiting application.")
}
