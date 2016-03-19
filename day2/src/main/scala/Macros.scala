import scala.reflect.macros.blackbox.Context

object Macros {
  def myPrintImpl[T: c.WeakTypeTag]
    (c: Context)
    (s: c.Expr[T]): c.Expr[Unit] = {
    import c.universe._
    reify(println(s.splice))
  }

  def myPrint[T](s: T): Unit = macro myPrintImpl[T]
}

