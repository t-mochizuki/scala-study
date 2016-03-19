import scala.reflect.macros.blackbox.Context

object Macros {
  def myPrintImpl(c: Context)(s: c.Expr[String]): c.Expr[Unit] = {
    import c.universe._
    reify(println(s.splice))
  }

  def myPrint(s: String): Unit = macro myPrintImpl
}
