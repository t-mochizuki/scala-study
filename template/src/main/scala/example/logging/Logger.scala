package example.logging

import example.hook.Listener
import org.slf4j.LoggerFactory

trait Logger extends Listener {
  lazy val logger = LoggerFactory.getLogger(this.getClass)
}
