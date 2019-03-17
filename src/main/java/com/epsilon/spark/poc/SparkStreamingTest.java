package com.epsilon.spark.poc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import com.epsilon.frms.filelayout.FieldMapping;
import com.epsilon.frms.filelayout.FrmsFieldMappingGenerator2;
import com.google.common.base.Joiner;

@Slf4j
public class SparkStreamingTest {

    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("Test Application").setMaster("yarn")
                .setSparkHome("/opt/cloudera/parcels/SPARK2-2.2.0.cloudera2-1.cdh5.12.0.p0.232957/lib/spark2");

        String hdfsFilePath = args[0];
        String delimiter = args[1];
        int fieldMappingId = Integer.parseInt(args[2]);
        int minPartitions = Integer.parseInt(args[3]);

        log.info("hdfsFilePath: {}", hdfsFilePath);
        log.info("delimiter: {}", delimiter);
        log.info("fieldMappingId: {}", fieldMappingId);
        // "hdfs:///data/staging/cds_support/spark_poc/PERFQA_CPT_25A_2M.csv";
        // "west_eps_custsurvey_20141107.dat.agility_seq_converted_sorted";

        try (JavaSparkContext javaSparkContext = new JavaSparkContext(conf)) {

            log.info("SparkStreamingTest Started");

            JavaRDD<String> textData = javaSparkContext.textFile(hdfsFilePath, minPartitions);
            long count = textData.count();

            log.info("Number of records:{}", count);

            JavaRDD<String> newData = textData.map(new Function<String, String>() {

                private static final long serialVersionUID = 3615171839171023684L;

                @Override
                public String call(final String record) {
                    // String delimiter = ",";
                    List<String> inputFieldsList = Arrays.asList(record.split(delimiter));
                    List<String> outputFieldsList = new ArrayList<String>();
                    List<FieldMapping> fieldMappings = FrmsFieldMappingGenerator2.getFRMSFieldMapping(fieldMappingId, delimiter);

                    for (int i = 0; i < inputFieldsList.size(); i++) {

                        FieldMapping fieldMapping = FrmsFieldMappingGenerator2.getFieldMappingByInputField(fieldMappings, i + "");
                        if (fieldMapping == null) {
                            continue;
                        }

                        String outputField = inputFieldsList.get(i);

                        if (fieldMapping.getOutputFieldTransformation() != null) {
                            if (fieldMapping.getOutputFieldTransformation().equals("LOWER")) {
                                outputField = outputField.toLowerCase();
                            }
                        }
                        outputFieldsList.add(fieldMapping.getOutputField().getFieldOrder(), outputField);
                    }
                    return Joiner.on(delimiter).join(outputFieldsList);
                }
            });

            String hdfsFolderName = RandomStringUtils.random(5, true, true);
            newData.saveAsTextFile("hdfs:///data/staging/cds_support/sparkTest2/" + hdfsFolderName);

        } catch (

        Exception e)

        {
            log.error("Exception:" + e.getMessage());
        }

    }

}
