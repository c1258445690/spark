// scalastyle:off
package org.apache.spark.myapp

object ScalaLang extends App {
  val pf: PartialFunction[String, Unit] = {
    case input if "abc" == input =>
      println("match")
    case _ =>
      println("unmatch")
  }
  assert(pf.isDefinedAt("abc"))
  assert(pf.isDefinedAt("abcd"))
  println("isDefinedAt 不执行匹配块")
  pf.apply("abc")
}
