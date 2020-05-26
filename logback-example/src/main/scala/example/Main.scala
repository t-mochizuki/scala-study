package example

import org.slf4j.LoggerFactory;

object Main extends App {
  val logger = LoggerFactory.getLogger(this.getClass)

  logger.info("Entering application.")

  val foo = new Foo()
  foo.doIt()

  logger.info("Exiting application.")
}
