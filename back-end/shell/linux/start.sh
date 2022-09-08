#!/bin/bash
basePath="$( cd "$( dirname "$0" )" && pwd )"
startPath=$basePath/../../boot/GuGu_Simple_Upload.jar
configPath=$basePath/../../config/application.yml
logConfigPath=$basePath/../../config/logback-spring.xml
read -rsp "Enter project key:" password
nohup java -XX:+HeapDumpOnOutOfMemoryError -jar "$startPath" --jasypt.encryptor.password="$password" --spring.config.location="$configPath" --logging.config="$logConfigPath" > /dev/null 2>&1 &
echo $! > process.pid
echo -e "\nGuGu_Simple_Upload Start Successful!"