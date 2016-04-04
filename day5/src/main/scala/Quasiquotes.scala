import scala.reflect.runtime.universe._
import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox

object Quasiquotes {
  val toolbox = currentMirror.mkToolBox()
  val C = q"class C"
  def showCodeC = println(showCode(C))
  def showRowC = println(showRaw(C))
}
