package core

import sttp.capabilities.Effect
import sttp.client.{SttpBackend, Response, Request}
import sttp.monad.MonadError

class LoggingSttpBackend[F[_], S](delegate: SttpBackend[F, S])
  extends SttpBackend[F, S] with Logger {

  override def send[T, R >: S with Effect[F]](request: Request[T, R]): F[Response[T]] = {
    responseMonad.map(responseMonad.handleError(delegate.send(request)) {
      case e: Exception =>
        logger.error(
          s"Exception when sending request: $request.\n" +
            s"To reproduce, run: ${request.toCurl}",
          e
        )
        responseMonad.error(e)
    }) { response =>
      logger.debug(
        s"For request: $request got response: $response.\n" +
          s"To reproduce, run: ${request.toCurl}"
      )
      response
    }
  }
  override def close(): F[Unit] = delegate.close()
  override def responseMonad: MonadError[F] = delegate.responseMonad
}
