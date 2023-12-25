package org.apache.spark.myapp.sparksql;

import org.apache.spark.sql.catalyst.InternalRow;
import org.apache.spark.unsafe.types.UTF8String;

public class WholeStageCodegenResult {
    //    全阶段代码生成结果,对应的SQL：select name from student where age > 18
    public Object generate(Object[] references) {
        return new GeneratedIteratorForCodegenStage1(references);
    }

    /*wsc_codegenPipeline*/
    /*wsc_codegenStageId*/
    final class GeneratedIteratorForCodegenStage1 extends org.apache.spark.sql.execution.BufferedRowIterator {
        private Object[] references;
        private scala.collection.Iterator[] inputs;
        private scala.collection.Iterator inputadapter_input_0;
        private org.apache.spark.sql.catalyst.expressions.codegen.UnsafeRowWriter[] filter_mutableStateArray_0 = new org.apache.spark.sql.catalyst.expressions.codegen.UnsafeRowWriter[2];

        public GeneratedIteratorForCodegenStage1(Object[] references) {
            this.references = references;
        }

        public void init(int index, scala.collection.Iterator[] inputs) {
            partitionIndex = index;
            this.inputs = inputs;
            inputadapter_input_0 = inputs[0];
            filter_mutableStateArray_0[0] = new org.apache.spark.sql.catalyst.expressions.codegen.UnsafeRowWriter(2, 32);
            filter_mutableStateArray_0[1] = new org.apache.spark.sql.catalyst.expressions.codegen.UnsafeRowWriter(1, 32);

        }

        protected void processNext() throws java.io.IOException {
            /*project_c_0*/
            /*filter_c_0*/
            /*inputadapter_c_0*/
            while ( inputadapter_input_0.hasNext()) {
                InternalRow inputadapter_row_0 = (InternalRow) inputadapter_input_0.next();

                /*wholestagecodegen_c_2*/
                do {
                    /*inputadapter_c_1*/
                    boolean inputadapter_isNull_0 = inputadapter_row_0.isNullAt(0);
                    long inputadapter_value_0 = inputadapter_isNull_0 ?
                            -1L : (inputadapter_row_0.getLong(0));

                    /*filter_c_1*/
                    boolean filter_value_2 = !inputadapter_isNull_0;
                    if (!filter_value_2) continue;

                    /*filter_c_2*/
                    boolean filter_value_3 = false;
                    filter_value_3 = inputadapter_value_0 > 18L;
                    if (!filter_value_3) continue;

                    ((org.apache.spark.sql.execution.metric.SQLMetric) references[0] /* numOutputRows */).add(1);

                    /*wholestagecodegen_c_1*/
// common sub-expressions

                    /*wholestagecodegen_c_0*/
                    /*project_c_1*/
                    boolean inputadapter_isNull_1 = inputadapter_row_0.isNullAt(1);
                    UTF8String inputadapter_value_1 = inputadapter_isNull_1 ?
                            null : (inputadapter_row_0.getUTF8String(1));
                    filter_mutableStateArray_0[1].reset();

                    filter_mutableStateArray_0[1].zeroOutNullBytes();

                    if (inputadapter_isNull_1) {
                        filter_mutableStateArray_0[1].setNullAt(0);
                    } else {
                        filter_mutableStateArray_0[1].write(0, inputadapter_value_1);
                    }
                    append((filter_mutableStateArray_0[1].getRow()));

                } while(false);
                if (shouldStop()) return;
            }
        }

    }
}
