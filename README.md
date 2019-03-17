# sparkPOC
# Set the Spark Home
export SPARK_HOME=/opt/cloudera/parcels/SPARK2-2.2.0.cloudera2-1.cdh5.12.0.p0.232957/lib/spark2

#Run Spark-Submit
spark-submit --class com.epsilon.spark.poc.SparkBasicTest \
--master local --deploy-mode client\
--executor-memory 1g --name test --conf "spark.app.id=test"\
sparkPOC-1.0.1.jar
