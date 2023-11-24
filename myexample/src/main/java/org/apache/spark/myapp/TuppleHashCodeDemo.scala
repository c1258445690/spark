// scalastyle:off

package org.apache.spark.myapp

object TuppleHashCodeDemo extends App {
  println((0,"ddddd").hashCode())
  println((1,"aaaa").hashCode())
  println((100,"aaaa").hashCode())
}
