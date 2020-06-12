package example.foo_server

import example.base.http.Server
import example.base.logging.Logger

object Main extends App with Logger {

  val server = new Server()
  server.start()

}
