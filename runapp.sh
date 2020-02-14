#!/usr/bin/env bash
echo 'Starting Spring Boot App'
cd '/home/ubuntu/set-impl'
nohup java -jar abstractset-0.0.1-SNAPSHOT.war >/dev/null 2>&1