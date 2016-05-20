import argonaut.CodecJson

sealed trait Tree[+A]
case class Leaf[A](value: A) extends Tree[A]
object Leaf {
  implicit def LeafCodecJson[A]: CodecJson[Leaf[A]] = macro leafCodecJsonImpl[A]

  def leafCodecJsonImpl[A: c.WeakTypeTag](c: scala.reflect.macros.blackbox.Context): c.Expr[CodecJson[Leaf[A]]] = {
    import c.universe._

    val typeA = c.weakTypeTag[A]
    val tree =
      q"""
        casecodec1(Leaf.apply[$typeA], Leaf.unapply[$typeA])("value")
      """
    c.Expr[CodecJson[Leaf[A]]](tree)
  }
}
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
// object Branch {
//   implicit def BranchCodecJson[A]: CodecJson[Branch[A]] = macro branchCodecJsonImpl[A]
//
//   def branchCodecJsonImpl[A: c.WeakTypeTag](c: scala.reflect.macros.blackbox.Context): c.Expr[CodecJson[Branch[A]]] = {
//     import c.universe._
//
//     val typeA = c.weakTypeTag[A]
//     val tree =
//       q"""
//         casecodec2(Branch.apply[$typeA], Branch.unapply[$typeA])("left", "right")
//       """
//     c.Expr[CodecJson[Branch[A]]](tree)
//   }
// }
