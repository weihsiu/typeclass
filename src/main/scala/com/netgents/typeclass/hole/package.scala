package com.netgents.typeclass

package object hole {
  
  def findHole[A : Hole](x: A) = Hole[A].findHole(x)
  
  implicit val rabbitHoleInHolePackage = new Hole[Rabbit] {
    def findHole(x: Rabbit) = "Rabbit found the hole in Hole package object"
  }
}