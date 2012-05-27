package com.netgents.typeclass

object Foldables extends App {
  
  import language.higherKinds
  import Monoids._
  
  trait Foldable[F[_]] {
    def foldMap[A, M : Monoid](t: F[A], f: A => M): M
  }
  
  trait Tree[+A]
  case object Empty extends Tree[Nothing]
  case class Node[A](value: A, left: Tree[A], right: Tree[A]) extends Tree[A]
  
  object Foldable {
    implicit def TreeFoldable = new Foldable[Tree] {
      def foldMap[A, M : Monoid](t: Tree[A], f: A => M): M = t match {
        case Empty => Monoid[M].mempty
        case Node(v, l, r) => Monoid[M].mappend(foldMap(l, f), Monoid[M].mappend(f(v), foldMap(r, f)))
      }
    }
  }
  
  def foldMap[A[_] : Foldable, B, C : Monoid](t: A[B], f: B => C): C = implicitly[Foldable[A]].foldMap(t, f)
  
  val testTree: Tree[Int] =
    Node(5,
      Node(3,
        Node(1, Empty, Empty),
        Node(4, Empty, Empty)),
      Node(9,
        Node(8, Empty, Empty),
        Node(10, Empty, Empty)))
        
  println(foldMap(testTree, (x: Int) => x))
  
  println(foldMap(testTree, (x: Int) => List(x)))
  
}