import scala.reflect.runtime.{universe => ru}
import scala.reflect.runtime.universe._

object Reflections extends App {
  def inspect[T: ru.TypeTag](t: T): Unit = {
    println(ru.typeTag[T].tpe.members)
  }
}
