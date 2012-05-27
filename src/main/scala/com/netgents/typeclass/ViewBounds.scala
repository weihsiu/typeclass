package com.netgents.typeclass

import language.implicitConversions

object ViewBounds extends App {
  
  case class Dish(name: String)
  
  case class Beef(weight: Int)
  
  implicit def cook(beef: Beef): Dish = Dish("%d oz. steak".format(beef.weight))
  
  def eat[A <% Dish](x: A): A = {
    println("eating a dish named %s".format(x.name))
    x
  }
  
  def dream(x: Dish): String = "dreaming about a dish named %s".format(x.name)
  
  println(eat(Beef(10)))
  println(dream(Beef(20)))
}