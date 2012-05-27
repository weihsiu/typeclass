package com.netgents.typeclass
/*
object ZipWiths extends App {
  
  def repeat[A](x: A): Stream [A] = x #:: repeat(x)
  
  def zapp[A, B](xs: Stream[A => B], ys: Stream[A]): Stream[B] = (xs, ys) match {
    case (f #:: fs, s #:: ss) => f(s) #:: zapp(fs, ss)
    case (_, _) => Stream.empty
  }
  
  trait ZipWith[S] {
    type ZipWithType
    def manyApp: Stream[S] => ZipWithType
    def zipWith: S => ZipWithType = f => manyApp(repeat(f))
  }
  
  trait ZipWithDefault {
    implicit def ZeroZW[S] = new ZipWith[S] {
      type ZipWithType = Stream[S]
      def manyApp = identity
    }
  }
  
  object ZipWith extends ZipWithDefault {
    def apply[S](s: S)(implicit zw: ZipWith[S]): zw.ZipWithType = zw.zipWith(s)
    implicit def SuccZW[S, R](implicit zw: ZipWith[R]) = new ZipWith[S => R] {
      type ZipWithType = Stream[S] => zw.ZipWithType
      def manyApp = xs => ss => zw.manyApp(zapp(xs, ss))
    }
  }
  
  def zipWith0: Stream[Int] = ZipWith(0)
  
  def map[A, B](f: A => B): Stream[A] => Stream[B] = ZipWith(f)
  
  def zipWith3[A, B, C, D](f: A => B => C => D): Stream[A] => Stream[B] => Stream[C] => Stream[D] = ZipWith(f)
  
  val toLengths = map((s: String) => s.length)
  toLengths("a" #:: "aa" #:: "aaa" #:: Stream.empty) foreach println
  
  def toTriples = zipWith3((a: Int) => (b: Char) => (c: String) => (a, b, c))
  toTriples(Stream.from(1))(Stream.from('a'.toInt).map(_.toChar))("this is awesome".split(' ').toStream) foreach println
}
*/