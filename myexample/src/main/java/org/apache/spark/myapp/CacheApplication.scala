// scalastyle:off

package org.apache.spark.myapp

import org.apache.spark.sql.SparkSession

object CacheApplication extends App {
  val sc = SparkSession
    .builder()
    .appName("CacheApplication")
    .getOrCreate()
    .sparkContext
  var inputRDD = sc.parallelize(Array[(Int,String)](
    (1, "a"), (2, "b"), (3, "c"), (4, "d"), (5, "e"), (3, "f"), (2, "g"), (1, "h")
  ),3)
  val mappedRDD = inputRDD.map(r => (r._1 + 1, r._2))
  mappedRDD.cache()
  val reducedRDD = mappedRDD.reduceByKey((x, y) => x + "_" + y, 2)
  reducedRDD.cache()
  reducedRDD.foreach(println)
  val groupedRDD = mappedRDD.groupByKey().mapValues(v => v.toList)
  groupedRDD.cache()
  groupedRDD.foreach(println)
  val joinedRDD = reducedRDD.join(groupedRDD)
  joinedRDD.foreach(println)
}







































