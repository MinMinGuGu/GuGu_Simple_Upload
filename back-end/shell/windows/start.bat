@echo off
set startPath=%cd%/../../boot/GuGu_Simple_Upload.jar
set configPath=%cd%/../../config/application.yml
set logConfigPath=%cd%/../../config/logback-spring.xml
set /p password=Enter project key:
java -XX:+HeapDumpOnOutOfMemoryError -jar %startPath% --jasypt.encryptor.password=%password% --spring.config.location=%configPath% --logging.config=%logConfigPath%