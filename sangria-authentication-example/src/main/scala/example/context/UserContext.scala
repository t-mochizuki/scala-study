package example.context

import example.repository.NumberRepo

trait UserContext {
  val numberRepo: NumberRepo

  def numbers(): List[Int]
}
