// scalastyle:off
package org.apache.spark.myapp

import org.apache.spark.sql.SparkSession

import java.time.LocalDateTime

object CheckpointApplication extends App {
//  日志可以通过yarn查看：yarn logs  -applicationId application_1699838710415_0009
  val logger = org.slf4j.LoggerFactory.getLogger(this.getClass)
  logger.info("CheckpointApplication start:"+ LocalDateTime.now())

  val sc = SparkSession
    .builder()
    .appName("CheckpointApplication")
    .getOrCreate()
    .sparkContext
  sc.setCheckpointDir("hdfs://192.168.1.66:9001/home/cwj/mycheckpoint")
  val inputRDD = sc.parallelize(Array[(Int, String)](
    (1, "a"), (2, "b"), (3, "c"), (4, "d"), (5, "e"), (3, "f"), (2, "g"), (1, "h")
  ), 3)
  val mappedRDD = inputRDD.map(r => (r._1 + 1, r._2))
  mappedRDD.cache()
  val reducedRDD = mappedRDD.reduceByKey((x, y) => x + "_" + y, 2)
  reducedRDD.cache()
  reducedRDD.checkpoint()
  reducedRDD.foreach(println)
  val groupedRDD = mappedRDD.groupByKey().mapValues(v => v.toList)
  groupedRDD.cache()
  groupedRDD.checkpoint()
  groupedRDD.foreach(println)
  val joinedRDD = reducedRDD.join(groupedRDD)
  joinedRDD.foreach(println)
  logger.info("CheckpointApplication end:"+ LocalDateTime.now())

}




































