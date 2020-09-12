package core

import scala.concurrent.ExecutionContext

case class AsyncExecutor() extends ExecutionContext {
  override def execute(runnable: Runnable): Unit = {
    println(java.time.ZonedDateTime.now() + " " + Thread.currentThread().getId)
    new Thread(runnable).start()
  }

  override def reportFailure(@deprecatedName('t) cause: Throwable): Unit = cause.printStackTrace()
}
