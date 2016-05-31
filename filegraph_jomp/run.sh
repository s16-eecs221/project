#!/bin/sh  
# Define some constants
# :set fileformat=unix
PROJECT_NAME=filegraph
PROJECT_PATH=/data/users/yskyerda/project/filegraph_jomp
JAR_PATH=$PROJECT_PATH/libs
BIN_PATH=$PROJECT_PATH/bin
SRC_PATH=$PROJECT_PATH/src
#  
# run the project
java -classpath $BIN_PATH:$JAR_PATH/guava-19.0.jar:$JAR_PATH/commons-codec-1.9.jar:$JAR_PATH/json-20160212.jarr filegraph.test.File2GraphTest  
#
# eof
