package com.netgents.typeclass.hole

case class Squirrel

object Squirrel {
  
  implicit val squirrelHoleInSquirrel = new Hole[Squirrel] {
    def findHole(x: Squirrel) = "Squirrel found the hole in Squirrel companion object"
  }
}