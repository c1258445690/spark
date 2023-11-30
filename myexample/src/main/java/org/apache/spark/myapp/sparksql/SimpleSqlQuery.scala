// scalastyle:off
package org.apache.spark.myapp.sparksql

import org.apache.spark.sql.SparkSession

object SimpleSqlQuery extends App {
  val spark = SparkSession.builder()
    .appName("SimpleSqlQuery")
    .master("local")
    .getOrCreate()
  spark.read.json("spark/student.json")
    .createOrReplaceTempView("student")
  spark.sql("select name from student where age > 18").show()
}
