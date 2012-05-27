package com.netgents.typeclass

object Monoids extends App {
  
  /**
   * mempty mappend x = x // identity
   * x mappend mempty = x // identity
   * (x mappend y) mappend z = x mappend (y mappend z) // associativity
   */
  trait Monoid[A] {
    def mempty: A
    def mappend(x: A, y: A): A
  }
  
  case class First[A](getFirst: Option[A])
  
  object Monoid {
    def apply[A : Monoid] = implicitly[Monoid[A]]
    implicit object SumMonoid extends Monoid[Int] {
      def mempty = 0
      def mappend(x: Int, y: Int) = x + y
    }
    implicit object StringMonoid extends Monoid[String] {
      def mempty = ""
      def mappend(x: String, y: String) = x + y
    }
    implicit def ListMonoid[A] = new Monoid[List[A]] {
      def mempty = List.empty
      def mappend(x: List[A], y: List[A]) = x ::: y
    }
    implicit def OptionMonoid[A : Monoid] = new Monoid[Option[A]] {
      def mempty = None
      def mappend(x: Option[A], y: Option[A]) = (x, y) match {
        case (None, m) => m
        case (m, None) => m
        case (Some(m1), Some(m2)) => Some(Monoid[A].mappend(m1, m2))
      }
    }
    implicit def FirstMonoid[A] = new Monoid[First[A]] {
      def mempty = First(None)
      def mappend(x: First[A], y: First[A]) = (x, y) match {
        case (f@First(Some(_)), _) => f
        case (First(None), m) => m
      }
    }
  }
  
  def append[A : Monoid](x: A, y: A): A = Monoid[A].mappend(x, y)
  
  def concat[A : Monoid](xs: List[A]): A = (Monoid[A].mempty /: xs)(Monoid[A].mappend)
  
  val sum = concat(List(1, 2, 3, 4))
  assert(sum == 10)
  
  object ProductMonoid extends Monoid[Int] {
    def mempty = 1
    def mappend(x: Int, y: Int) = x * y
  }
  
  val factorial = concat(List(1, 2, 3, 4))(ProductMonoid)
  assert(factorial == 24)
  
  assert(concat(List("hello", " ", "world")) == "hello world")
  
  assert(concat(List(List(1, 2), List(3, 4))) == List(1, 2, 3, 4))
  assert(concat(List(List('a', 'b'), List('c', 'd'))) == List('a', 'b', 'c', 'd'))
  
  assert(append(None, Some(123)) == Some(123))
  assert(append(Some(123), None) == Some(123))
  assert(append(Some(123): Option[Int], Some(456)) == Some(579))
  
  assert(concat(List(Some(1), Some(2), None, Some(3))) == Some(6))
  assert(concat(List(None, Some("a"), Some("b"), Some("c"))) == Some("abc"))
  
  implicit class ToOption[A](x: A) {
    def some: Option[A] = Some(x)
  }
  def none[A]: Option[A] = None
  
  assert(append(123.some, 456.some) == Some(579))
  assert(append(123.some, none) == Some(123))
  
  def getFirst[A](xs: List[Option[A]]): Option[A] = concat(xs.map(First(_))).getFirst
  
  assert(getFirst(List(None, Some(1), Some(2), None)) == Some(1))
  
}
