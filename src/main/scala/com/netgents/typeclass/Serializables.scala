package com.netgents.typeclass

import java.util.Date

object Serializables extends App {
  
  case class Item(name: String, price: Int, quantity: Int)
  case class Order(customer: String, date: Date, items: Set[Item])
  
  val order = Order("Walter", new Date, Set(Item("BigMac", 100, 1), Item("Coke", 30, 1)))
  
  object UseOverloading {
    def serialize(item: Item): String = ???
    def serialize(order: Order): String = ???
  }
  
  UseOverloading.serialize(order)
  
  object UsePatternMatching {
    def serialize(x: Any): String = x match {
      case Item(name, price, quantity) => ???
      case Order(customer, date, items) => ???
    }
  }
  
  UsePatternMatching.serialize(order)
  
  object UseInheritance {
    trait Serializable {
      def serialize: String
    }
    class SerializableItem(item: Item) extends Serializable {
      def serialize = ???
    }
    class SerializableOrder(order: Order) extends Serializable {
      def serialize = ???
    }
  }
  
  new UseInheritance.SerializableOrder(order).serialize
  
  object UseTypeclass {
    trait Serializable[A] {
      def serialize(x: A): String
    }
    object Serializable {
      implicit val itemSerializable = new Serializable[Item] {
        def serialize(x: Item) = ???
      }
      implicit val orderSerializable = new Serializable[Order] {
        def serialize(x: Order) = ???
      }
    }
    def serialize[A](x: A)(implicit s: Serializable[A]): String = s.serialize(x)
    
//    def implicitly[A](implicit x: A): A = x
    
//    def serialize[A : Serializable](x: A): String = implicitly[Serializable[A]].serialize(x)
  }
  
  UseTypeclass.serialize(order)
}