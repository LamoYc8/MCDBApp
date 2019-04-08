#!/bin/bash

# Compiler le projet sous Linux
find src -name *.java | xargs javac -d bin -classpath bin:res:lib/miglayout15-swing.jar:lib/swingx-all-1.6.4.jar:lib/mysql-connector-java-5.1.40-bin.jar
