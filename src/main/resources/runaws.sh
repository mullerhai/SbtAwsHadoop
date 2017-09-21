#!/bin/sh

APP_JAR=/hadoopJars/SbtAwsHadoop.jar
APP_NAME=sbtAwsHadoop
APP_DIR=/hadoopJars/$APP_NAME

RUN_SCRIPT=/hadoopJars/runaws.sh

echo "check  raw  data or directory"
if [-d '$APP_DIR'];then

    echo "delete last rabbish same name directory and make new dir"
    sudo -i rm -rf $APP_DIR
fi


mkdir -p $APP_DIR

echo "move file and script to the  app dir"
cd  /hadoopJars
sudo -i  mv  $RUN_SCRIPT  $APP_DIR/
sudo -i  mv  $APP_JAR    $APP_DIR/
sudo -i  chown -R hadoop:hadoop  $APP_DIR


echo "Beginning configuration the mapReduce task"
HADOOP_BIN_HOME=/usr/local/hadoop-2.8.1/bin/hadoop

JAR_PATH=$APP_DIR/SbtAwsHadoop.jar
INPUT_PATH=/testdata/app.txt
MAIN_CLASS=com.linkme.hadoop.ApplistCount

OUTPUT_PATH=/outsbt01
OUTPUT_FILE=$OUTPUT_PATH/

sleep 2
echo "run mapreduce program  please view the execute result from console and hdfs output"

sudo su hadoop $HADOOP_BIN_HOME jar $JAR_PATH $MAIN_CLASS $INPUT_PATH $OUTPUT_PATH

echo "view the hdfs output result text"

#$HADOOP_BIN_HOME fs -ls $OUTPUT_FILE
#$HADOOP_BIN_HOME fs -text $OUTPUT_FILE

#read  -p "do you wanna save the outputfile on your local file system?"  sure


#$HADOOP_BIN_HOME fs -copyToLocal  $OUTPUT_FILE









