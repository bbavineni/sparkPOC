package com.epsilon.spark.poc;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Builder
public class SparkRequest implements Serializable {

    private String className;

    private String driverMemory;

    private int driverCores;

    private int executorCores;

    private String executorMemory;

    private int numExecutors;

    private String queue;

    private String name;

    private String proxyUser;

    private String file;

    List<String> args;

}
