import scala.reflect.macros.whitebox.Context
import scala.annotation.StaticAnnotation

class inspect extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro inspectMacro.impl
}

object inspectMacro {
  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    println(showRaw(annottees.map(_.tree).toList))
    c.Expr[Any](Literal(Constant(())))
  }
}
