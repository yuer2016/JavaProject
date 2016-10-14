#!/bin/bash

 if [ $(which hadoop | wc -l ) -eq 0 ]; then
    echo "Can't find Hadoop executable. Add HADOOP_HOME/bin to the path or run in local mode."
    exit -1;
 fi

if [ $(which sqoop | wc -l ) -eq 0 ]; then
      echo "Can't find Sqoop  executable. Add SQOOP_HOME/bin to the path or run in local mode."
      exit -1;
 fi


if [ $# -eq 0 ]; then
  echo "Usage: mapreduce  COMMAND"
  echo "where COMMAND is one of:"
  echo " BrakeRunning	    Count Brake Running class"
  echo " OilRunning         Count Oil   Running class"
  echo " LocusRunning       Count Locus Running class"
  exit 1
fi

COMMAND=$1
shift

if [ "$COMMAND" = "BrakeRunning" ] ; then

output=/brake/output
tablepath=/brake/output/part*

elif [ "$COMMAND" = "OilRunning" ]; then

output=/oil/output
tablepath=/oil/output/part*

elif [ "$COMMAND" = "LocusRunning" ];then

output=/locus/output
tablepath=/locus/output/part*

else 

output=/default/output
tablepath=/default/output/part*

fi

echo "output path $output " 

hadoop fs -ls $output &> /dev/null

if [ $? -eq 0  ]; then
  hadoop fs -rmr  $output
fi


MR_JOB=brake-job.jar

EXEC_CALL="hadoop jar $MR_JOB $COMMAND $output  $@ "

exec $EXEC_CALL

#SQOOP_CALL="sqoop export --table foo --export-dir $tablepath  --connect JDBC:MySql://192.168.0.230/  --username 'root' --password 'root' "

#$EXEC_CALL && $SQOOP_CALL


#exec $SQOOP_CALL

