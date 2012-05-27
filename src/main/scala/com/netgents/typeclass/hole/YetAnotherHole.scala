package com.netgents.typeclass.hole

object YetAnotherHole {
  
  implicit val rabbitHoleInYetAnotherHole = new Hole[Rabbit] {
    def findHole(x: Rabbit) = "Rabbit found the hole in YetAnotherHole object"
  }
}