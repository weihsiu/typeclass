package com.netgents.typeclass

import language.implicitConversions

object Ords extends App {
  
  trait Eq[-T] {
    def equal(a: T, b: T): Boolean
  }
  
  object Eq {
    implicit def AnyEq[T] = new Eq[T] {
      def equal(a: T, b: T) = a == b
    }
  }
 
  class Equal[T : Eq](a: T) {
    def ===(b: T): Boolean = implicitly[Eq[T]].equal(a, b)
  }
  
  implicit def toEqual[T](a: T) = new Equal(a)
  
  // what implicit class will desugar to
  // and since T, in this case, will be Any, 1 === "a" won't work
  //implicit def Equal[T](a: T)(implicit eq: Eq[T]): Equal[T] = new Equal[T](a)(eq)
  
  assert(1 == "a")
  assert(1 === 1)
//  assert(1 === "a")
  
  trait Ord[T] extends Eq[T] {
    def compare(a: T, b: T): Boolean // returns true if a <= b, false otherwise
    def equal(a: T, b: T): Boolean = compare(a, b) && compare(b, a)
  }
  
  object Ord {
    def apply[T : Ord] = implicitly[Ord[T]]
    implicit object IntOrd extends Ord[Int] {
      def compare(a: Int, b: Int) = a <= b
    }
    implicit def ListOrd[T : Ord] = new Ord[List[T]] {
      def compare(l1: List[T], l2: List[T]) = (l1, l2) match {
        case (x :: xs, y :: ys) => if (implicitly[Ord[T]].equal(x, y)) compare(xs, ys) else implicitly[Ord[T]].compare(x, y)
        case (_, Nil) => false
        case (Nil, _) => true
      }
    }
  }
  
//  def cmp[T : Ord](a: T, b: T) = implicitly[Ord[T]].compare(a, b)
  def cmp[T : Ord](a: T, b: T) = Ord[T].compare(a, b)
  
  // comparing Ints
  assert(cmp(1, 2) === true)
  assert(cmp(2, 1) === false)
  
  // comparing Lists of Ints
  assert(cmp(List(1, 2), List(2, 1)) === true)
  assert(cmp(List(2, 1), List(1, 2)) === false)
  
  implicit def ListOrd2[T](implicit ord: Ord[T]) = new Ord[List[T]] {
    def compare(l1: List[T], l2: List[T]) = (l1.length < l2.length)
  }
  
  // comparing Lists of Ints using ListOrd2 implicitly
  assert(cmp(List(1, 2), List(2, 1)) === false)
  assert(cmp(List(2, 1), List(1, 2)) === false)
  
  assert(cmp(List(1, 2), List(2, 1))(Ord.ListOrd) === true)
  assert(cmp(List(2, 1), List(1, 2))(Ord.ListOrd) === false)
  
  assert(cmp(List(1, 1), List(1, 1, 1))(ListOrd2) === true)
  assert(cmp(List(1, 1, 1), List(1, 1))(ListOrd2) === false)
  
  assert(cmp(List(1, 1), List(1, 1, 1)) === true)
  assert(cmp(List(1, 1, 1), List(1, 1)) === false)
}