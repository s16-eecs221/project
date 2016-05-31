#!/bin/sh  
# Define some constants
# :set fileformat=unix
PROJECT_NAME=filegraph
PROJECT_PATH=/data/users/yskyerda/filegraph_jomp
JAR_PATH=$PROJECT_PATH/libs
BIN_PATH=$PROJECT_PATH/bin
SRC_PATH=$PROJECT_PATH/src
# First remove the sources.list file if it exists and then create the sources file of the project
find $SRC_PATH -name *.java > $PROJECT_PATH/sources.list
#
# 
# First remove the PROJECT_NAME directory if it exists and then create the bin directory of PROJECT_NAME  
rm -rf $BIN_PATH/$PROJECT_NAME  
mkdir $BIN_PATH/$PROJECT_NAME  
#  
# Compile the project  
javac -d $BIN_PATH/$PROJECT_NAME -classpath $JAR_PATH/guava-19.0.jar:$JAR_PATH/commons-codec-1.9.jar:$JAR_PATH/json-20160212.jar @$PROJECT_PATH/sources.list
#
# eof
