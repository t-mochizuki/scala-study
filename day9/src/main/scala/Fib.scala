object Fib {
  def fib(x: Int): Int = x match {
    case 0 => 1
    case 1 => 1
    case x if x > 1 => fib(x - 2) + fib(x - 1)
  }
}
