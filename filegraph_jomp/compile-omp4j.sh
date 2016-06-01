#!/bin/sh  
# Define some constants
# :set fileformat=unix
PROJECT_NAME=filegraph
PROJECT_PATH=/data/users/yskyerda/project/filegraph_jomp
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
java -jar omp4j/target/scala-2.10/omp4j-assembly-1.2.jar -d test -classpath $JAR_PATH/guava-19.0.jar:$JAR_PATH/commons-codec-1.9.jar:$JAR_PATH/json-20160212.jar /data/users/yskyerda/project/filegraph_jomp/src/filegraph/FileGraphIndexing.java /data/users/yskyerda/project/filegraph_jomp/src/filegraph/obj/FileGraphLeaf.java /data/users/yskyerda/project/filegraph_jomp/src/filegraph/obj/FileGraphNode.java /data/users/yskyerda/project/filegraph_jomp/src/filegraph/obj/FileGraphNonLeaf.java /data/users/yskyerda/project/filegraph_jomp/src/filegraph/obj/FileGraphRoot.java /data/users/yskyerda/project/filegraph_jomp/src/filegraph/obj/FileHashProperty.java /data/users/yskyerda/project/filegraph_jomp/src/filegraph/obj/LineGraph.java /data/users/yskyerda/project/filegraph_jomp/src/filegraph/obj/LineGraphEdge.java /data/users/yskyerda/project/filegraph_jomp/src/filegraph/obj/LineGraphVertex.java /data/users/yskyerda/project/filegraph_jomp/src/filegraph/test/File2GraphTest.java /data/users/yskyerda/project/filegraph_jomp/src/filegraph/utl/ByteHashFunction.java /data/users/yskyerda/project/filegraph_jomp/src/filegraph/utl/FileGraphUtli.java /data/users/yskyerda/project/filegraph_jomp/src/filegraph/utl/FileUnit.java /data/users/yskyerda/project/filegraph_jomp/src/filegraph/utl/HashFunction.java /data/users/yskyerda/project/filegraph_jomp/src/filegraph/utl/LocalFile.java
#
# eof
