package example

import org.slf4j.LoggerFactory;

class Foo() {
  val logger = LoggerFactory.getLogger(this.getClass)

  def doIt() {
    logger.debug("Did it again!")
  }
}
