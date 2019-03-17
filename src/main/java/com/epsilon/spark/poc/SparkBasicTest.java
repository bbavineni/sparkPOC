package com.epsilon.spark.poc;

import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import com.google.common.base.Joiner;

@Slf4j
public class SparkBasicTest {

    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("Test Application").setMaster("yarn")
                .setSparkHome("/opt/cloudera/parcels/SPARK2-2.2.0.cloudera2-1.cdh5.12.0.p0.232957/lib/spark2");

        String hdfsFilePath = "hdfs:///data/staging/cds_support/west_eps_custsurvey_20141107.dat.agility_seq_converted_sorted";

        try (JavaSparkContext javaSparkContext = new JavaSparkContext(conf)) {

            log.info("SparkBasicTest Started");

            // Filter records with 141105
            JavaRDD<String> textData = javaSparkContext.textFile(hdfsFilePath, 2).filter(s -> s.contains("141105"));
            long count = textData.count();

            log.info("Number of records:{}", count);

            JavaRDD<String> newData = textData.map(new Function<String, String>() {

                private static final long serialVersionUID = 3615171839171023684L;

                @Override
                public String call(final String record) {
                    List<String> recordsList = Arrays.asList(record.toLowerCase().split(","));
                    return Joiner.on(",").join(recordsList.subList(1, recordsList.size()));
                }
            });

            String hdfsFolderName = RandomStringUtils.random(5, true, true);
            newData.saveAsTextFile("hdfs:///data/staging/cds_support/sparkTest/" + hdfsFolderName);

        } catch (Exception e) {
            log.error("Exception:" + e.getMessage());
        }

    }

}
