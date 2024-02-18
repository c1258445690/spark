// scalastyle:off
package org.apache.spark.myapp.sparksql

import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, DoubleType, IntegerType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object SqlFunctions  extends App{
  val ss = SparkSession.builder()
    .appName("SimpleSqlQuery")
    .master("local")
    .config("hive.metastore.uris", "thrift://ubuntu01:9083")
    .enableHiveSupport()
    .getOrCreate()
  val sc = ss.sparkContext
  sc.setLogLevel("ERROR")

  import ss.implicits._

  private val df: DataFrame = List(
    ("A", 1, 90),
    ("B", 2, 60),
    ("C", 1, 70),
    ("A", 2, 80),
    ("B", 1, 70),
    ("C", 2, 90)
  ).toDF("name", "class", "score")
  df.createTempView("users")
  ss.sql("""select * from users order by name desc, score""")
    .show()

  ss.udf.register("plus10",(x:Int)=>x*10)
  ss.sql(
      """select *,plus10(score)
                  from users
                  order by name desc, score""")
    .show()

  ss.udf.register("agg",new MyAggFun)
  ss.sql(
      """select name,agg(score)
                 from users
                 group by name""")
    .show()

  ss.sql(
      """
         select *,
          case
         when score<=100 and score>=90 then 'good'
         when score<90 and score>=80 then 'middle'
         else then 'bad' as evaluation
         from users""")
    .show()

  ss.sql(
      """
         select count(*),
         case
             when score<=100 and score>=90 then 'good'
             when score<90 and score>=80 then 'middle'
             else then 'bad' as evaluation
         from users
         group by
         case
           when score<=100 and score>=90 then 'good'
           when score<90 and score>=80 then 'middle'
           else then 'bad'
               """)
    .show()

  ss.sql(
      """
         select *,
         rank() over(partition by class order by score desc) as rank,
         row_number() over(partition by class order by score desc) as number
         from users
               """)
    .show()

  ss.sql(
      """
         select *,
         count(score) over(partition by class) as num
         from users
               """)
    .show()

  ss.sql(
      """
         select class,
         count(score)
         from users
         group by class
               """)
    .show()


}

class MyAggFun extends UserDefinedAggregateFunction {

  override def inputSchema: StructType = {
    StructType.apply(Array(StructField.apply("score",IntegerType,false)))
  }


  override def bufferSchema: StructType = {
    StructType.apply(Array(
      StructField.apply("sum",IntegerType,false),
      StructField.apply("count",IntegerType,false)
    ))
  }


  override def dataType: DataType = DoubleType


  override def deterministic: Boolean = true


  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(0)=0
    buffer(1)=0
  }


  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    buffer(0)=buffer.getInt(0)+input.getInt(0)
    buffer(1)=buffer.getInt(1)+1
  }


  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    buffer1(0)=buffer1.getInt(0)+buffer2.getInt(0)
    buffer1(1)=buffer1.getInt(1)+buffer2.getInt(1)
  }


  override def evaluate(buffer: Row): Double = {
    buffer.getInt(0)/buffer.getInt(1)
  }
}