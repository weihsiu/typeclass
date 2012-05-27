package com.netgents.typeclass.hole

object AnotherHole {
  
  implicit val rabbitHoleInAnotherHole = new Hole[Rabbit] {
    def findHole(x: Rabbit) = "Rabbit found the hole in AnotherHole object"
  }
}