// scalastyle:off
package org.apache.spark.myapp.sparksql

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.InternalRow
import org.apache.spark.sql.internal.{SQLConf, StaticSQLConf}
import org.apache.spark.sql.internal.SQLConf.PLAN_CHANGE_LOG_LEVEL

/**
 *

 */
object SimpleSqlQuery extends App {
  val spark = SparkSession.builder()
    .appName("SimpleSqlQuery")
    .master("local")
    .getOrCreate()
  val sqlConf = new SQLConf
  //打印RuleExecutor执行的Rule,Applying Rule
  sqlConf.setConf(PLAN_CHANGE_LOG_LEVEL, "info")
  //SQL生成的java代码中包含注释
  sqlConf.setConf(StaticSQLConf.CODEGEN_COMMENTS, true)
  SQLConf.setSQLConfGetter(() =>sqlConf)
//  org/apache/spark/sql/catalyst/parser/SqlBaseParser.g4
  spark.read
    .json("spark/student.json")
    .createOrReplaceTempView("student")
  spark.sql("select name from student where age > 18").show()
}

