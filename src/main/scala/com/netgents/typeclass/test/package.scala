package com.netgents.typeclass

package object test {
  
  import com.netgents.typeclass.hole._
  
  implicit val rabbitHoleInTestPackage = new Hole[Rabbit] {
    def findHole(x: Rabbit) = "Rabbit found the hole in Test package object"
  }
}