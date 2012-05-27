package com.netgents.typeclass

import java.io._

object Coerces extends App {
  
  trait Coerce[-A, +B] {
    def coerce(x: A): B
  }
  
  object Coerce {
    implicit def AnyToString[A] = new Coerce[A, String] {
      def coerce(x: A): String = x.toString
    }
    implicit def SeqToString[A] = new Coerce[Seq[A], String] {
      def coerce(x: Seq[A]): String = x.mkString("[", ",", "]")
    }
    implicit def StringToReader = new Coerce[String, Reader] {
      def coerce(x: String): Reader = new StringReader(x)
    }
  }
  
  def coerce[A, B](x: A)(implicit c: Coerce[A, B]): B = c.coerce(x)
  
  println(coerce(123))
  println(coerce(List(1, 2, 3)))
  
  val reader: Reader = coerce("hello world")
  
  println(reader)
  println(coerce[String, Reader]("goodbye world"))
  
}