package com.netgents.typeclass.test

object Test extends App {
  
  import com.netgents.typeclass.hole._
  
  implicit val rabbitHoleInOuterTest = new Hole[Rabbit] {
    def findHole(x: Rabbit) = "Rabbit found the hole in outer Test object"
  }
  
//  import AnotherHole.rabbitHoleInAnotherHole
//  
//  import YetAnotherHole._
  
  {
//    implicit val rabbitHoleInInnerTest = new Hole[Rabbit] {
//      def findHole(x: Rabbit) = "Rabbit found the hole in inner Test object"
//    }
  
//    def rabbitHoleInHolePackage = disabled // shadows the typeclass instance in the package object
    
    println(findHole(Rabbit()))
    
    println(findHole(Squirrel()))
  }
}