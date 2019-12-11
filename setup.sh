#!/bin/bash

sudo mkdir java
cd java
sudo wget https://download.java.net/java/GA/jdk13.0.1/cec27d702aa74d5a8630c65ae61e4305/9/GPL/openjdk-13.0.1_linux-x64_bin.tar.gz
sudo tar -xf 13.0.1_linux-x64_bin.tar.gz
cd ..
sudo mkdir tomcat
cd tomcat
sudo wget http://mirrors.advancedhosters.com/apache/tomcat/tomcat-9/v9.0.29/bin/apache-tomcat-9.0.29.tar.gz
sudo tar -xf apache-tomcat-9.0.29.tar.gz
sudo wget https://raw.githubusercontent.com/TTTheo/Image-Recognition-Application/master/manager.xml
sudo wget https://raw.githubusercontent.com/TTTheo/Image-Recognition-Application/master/tomcat-users.xml
sudo mv manager.xml apache-tomcat-9.0.29/conf/Catalina/localhost/
sudo mv tomcat-users.xml apache-tomcat-9.0.29/conf/
export JAVA_HOME=~/java/jdk-13.0.1
export PATH=$JAVA_HOME/bin:$PATH
cd apache-tomcat-9.0.29/bin