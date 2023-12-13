// scalastyle:off

package org.apache.spark.myapp.sparksql

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.catalyst.InternalRow
import org.apache.spark.sql.catalyst.expressions.Attribute
import org.apache.spark.sql.execution.{LimitExec, SparkPlan}

object ScalaLangDemo extends App {
  case class CollectLimitExec(limit: Int = -1, child: SparkPlan, offset: Int = 0) extends LimitExec {
    override def output: Seq[Attribute] = ???

    override protected def withNewChildInternal(newChild: SparkPlan): SparkPlan = ???

    /**
     * Produces the result of the query as an `RDD[InternalRow]`
     *
     * Overridden by concrete implementations of SparkPlan.
     */
    override protected def doExecute(): RDD[InternalRow] = ???
  }

  class SeqImpl extends Seq[String]{
    override def length: Int = ???

    override def apply(idx: Int): String = ???

    override def iterator: Iterator[String] = ???
  }
}
