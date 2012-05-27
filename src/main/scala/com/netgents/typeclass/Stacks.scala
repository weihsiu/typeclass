package com.netgents.typeclass

import language.implicitConversions

object Stacks extends App {
  
  trait Stack[S] {
    def create: S
    def push(s: S, i: Int): S
    def pop(s: S): (Int, S)
    def peek(s: S): Int
  }
  
  object Stack {
    object ListStack extends Stack[List[Int]] {
      def create = List.empty
      def push(s: List[Int], i: Int) = i :: s
      def pop(s: List[Int]) = s match { case x :: xs => (x, xs) }
      def peek(s: List[Int]) = s.head
    }
    case class Func(f: () => (Int, Func)) extends (() => (Int, Func)) {
      def apply = f()
    }
    object FuncStack extends Stack[Func] {
      def create = Func(null)
      def push(s: Func, i: Int) = Func(() => (i, s))
      def pop(s: Func) = s()
      def peek(s: Func) = s()._1
    }
  }
  
  //import Stack.ListStack._
  import Stack.FuncStack._
  val s = create
  val s1 = push(s, 1)
  println(peek(s1))
  val s2 = push(s1, 2)
  val (i, s3) = pop(s2)
  println(i)
  println(peek(s3))
  
}