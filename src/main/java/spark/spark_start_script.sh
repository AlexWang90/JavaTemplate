#!/bin/bash

#env setting
export SPARK_HOME=/home/nlp/Tools/spark-1.6.1-bin-hadoop2.4-lt
export HADOOP_HOME=/home/nlp/Tools/hadoop-2.5.2-lt
export WORK_HOME=/home/nlp/hzwangjian1/docDuplicate

#kinit-hy
source /home/nlp/Tools/recsys_kinit.sh


statis_on_spark()
{
  $SPARK_HOME/bin/spark-submit \
  --name duplicate_opt \
  --master yarn-cluster \
  --class duplicateDetect.MainEntry \
  --num-executors 20 \
  --driver-memory 8g \
  --executor-memory 3g \
  $WORK_HOME/DuplicateDocDetect-1.0-jar-with-dependencies.jar test_spark
}

statis_on_spark
