package com.epsilon.spark.poc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.kerberos.client.KerberosRestTemplate;
import org.springframework.web.client.RestTemplate;

import com.cloudera.com.fasterxml.jackson.core.JsonProcessingException;
import com.cloudera.com.fasterxml.jackson.databind.ObjectMapper;
import com.cloudera.com.fasterxml.jackson.databind.ObjectWriter;

@Slf4j
public class SparkYarnRestClient {

    public static void main(String[] arguments) {

        // keberos
        Configuration conf = new Configuration();
        conf.set("hadoop.security.authentication", "Kerberos");

        UserGroupInformation.setConfiguration(conf);

        // System.setProperty("javax.security.auth.useSubjectCredsOnly", "false");
        System.setProperty("java.security.auth.login.config", "true");
        // System.setProperty("java.security.krb5.kdc", "qc1udtlhapp16.epsilon.com");

        try {
            UserGroupInformation.loginUserFromKeytab("cds_user", "C:/docs/secure_Cluster/StageA/cds_user.keytab");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }

        System.out.println("Auth Completed");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // dev : cds_user@CDS-DEV.EPSILON.COM
        // stage:cds_user@CDS-STGB.EPSILON.COM

        String kerberosUser = "cds_user@CDS-STGB.EPSILON.COM";

        RestTemplate kerberosRestTemplate = new KerberosRestTemplate("C:/docs/secure_Cluster/StageA/cds_user.keytab", kerberosUser);

        List<String> classArgs = new ArrayList<String>();
        // classArgs.add("hdfs:///data/staging/cds_support/spark_poc/Tower_and_Bridge.ids.0229_converted_seq");
        classArgs.add("hdfs:///data/staging/cds_support/spark_poc/PERFQA_CPT_25A_32GB.csv");
        // delimiter
        classArgs.add("|");
        // file type
        classArgs.add("2");
        // minPartitions
        classArgs.add("150");

        SparkRequest sparkRequest = SparkRequest.builder()
                .className("com.epsilon.spark.poc.SparkStreamingTest")
                .driverMemory("1G").driverCores(1)
                .executorCores(3).executorMemory("2G").numExecutors(80)
                .queue("default")
                .args(classArgs).name("SparkPOC_32GB_Test")
                .proxyUser("cds_user")
                .file("hdfs:///opt/app/cds/sparkPOC-1.0.1.jar")
                .build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        String sparkRequestJson = null;
        try {
            sparkRequestJson = ow.writeValueAsString(sparkRequest);
            System.out.println("sparkRequestJson:" + sparkRequestJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Submitting Request to Livy");

        // dev:http://dc1udtlhcld001.stack.qadev.corp:8998
        // Stage: http://cdsstagea-apps.qadev.harmony.global:8998

        kerberosRestTemplate.postForObject("http://qc1udtlhhad02.epsilon.com:8998" + "/batches", sparkRequestJson, String.class);

        System.out.println("Submitting Request to Livy Completed");

    }

}
