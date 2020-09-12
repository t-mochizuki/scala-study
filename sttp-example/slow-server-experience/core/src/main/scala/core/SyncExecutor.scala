package core

import scala.concurrent.ExecutionContext

case class SyncExecutor() extends ExecutionContext {
  override def execute(runnable: Runnable): Unit = {
    println(java.time.ZonedDateTime.now() + " " + Thread.currentThread().getId)
    runnable.run()
  }

  override def reportFailure(@deprecatedName('t) cause: Throwable): Unit = cause.printStackTrace()
}
