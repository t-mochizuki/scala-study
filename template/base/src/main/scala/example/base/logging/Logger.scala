package example.base.logging

import example.base.hook.Listener
import org.slf4j.LoggerFactory

trait Logger extends Listener {
  lazy val logger = LoggerFactory.getLogger(this.getClass)
}
