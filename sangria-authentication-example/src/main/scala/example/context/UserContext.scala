package example.context

import example.repository.NumberRepo

trait UserContext {
  val numberRepo: NumberRepo

  def numbers(): List[Int]

  def login(id: String, password: String): String

  def logout(): String
}
