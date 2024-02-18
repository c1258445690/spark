// scalastyle:off

package org.apache.spark.myapp.sparksql

import org.apache.spark.sql.SparkSession

object SqlOnHive  extends App{
  val ss = SparkSession.builder()
    .appName("SimpleSqlQuery")
    .master("local")
    .config("hive.metastore.uris","thrift://ubuntu01:9083")
    .enableHiveSupport()
    .getOrCreate()
  val sc=ss.sparkContext
  sc.setLogLevel("ERROR")
  ss.catalog.listTables().show()
  val df=ss.sql("show tables")
  df.show()

  import ss.implicits._

  val df01=List(
    "zhangsan","lisi"
  ).toDF()
  df01.createTempView("ooxx")

  ss.sql("create table cwj1(id int)")

  ss.catalog.listTables().show()

  ss.sql("insert into cwj1 values(2),(4),(5)")

  df01.write.saveAsTable("cwj2")










}
