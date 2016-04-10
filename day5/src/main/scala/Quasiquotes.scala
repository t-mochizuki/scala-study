import scala.reflect.runtime.universe._
import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox

object Quasiquotes {
  val toolbox = currentMirror.mkToolBox()
  val C = q"class C"
  def showCodeC = println(showCode(C))
  def showRowC = println(showRaw(C))
}

object Macro {
  import scala.reflect.macros.whitebox.Context
  def sum(lhs: Int, rhs: Int): Int = macro impl
  def impl(c: Context)(lhs: c.Tree, rhs: c.Tree) = {
    import c.universe._
    q"$lhs + $rhs"
  }
}
