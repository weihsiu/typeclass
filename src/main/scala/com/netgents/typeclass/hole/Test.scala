package com.netgents.typeclass.hole

object Test extends App {
  
//  implicit val rabbitHoleInOuterTest = new Hole[Rabbit] {
//    def findHole(x: Rabbit) = "Rabbit found the hole in outer Test object"
//  }
  
//  import AnotherHole.rabbitHoleInAnotherHole
//  
//  import YetAnotherHole._
  
  {
    implicit val rabbitHoleInInnerTest = new Hole[Rabbit] {
      def findHole(x: Rabbit) = "Rabbit found the hole in inner Test object"
    }
  
//    def rabbitHoleInHolePackage = disabled // shadows the typeclass instance in the package object
    
    println(findHole(Rabbit()))
    
    println(findHole(Squirrel()))
  }
}