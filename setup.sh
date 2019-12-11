#!/bin/bash

sudo apt-get update

sudo mkdir ~/java
cd ~/java
sudo wget https://download.java.net/java/GA/jdk13.0.1/cec27d702aa74d5a8630c65ae61e4305/9/GPL/openjdk-13.0.1_linux-x64_bin.tar.gz
sudo tar -xf openjdk-13.0.1_linux-x64_bin.tar.gz
cd ..

echo 'export JAVA_HOME=~/java/jdk-13.0.1' >> ~/.bashrc 
echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.bashrc 
source ~/.bashrc
sudo mkdir tomcat
cd tomcat
sudo wget http://mirrors.advancedhosters.com/apache/tomcat/tomcat-9/v9.0.29/bin/apache-tomcat-9.0.29.tar.gz
sudo tar -xf apache-tomcat-9.0.29.tar.gz
cd apache-tomcat-9.0.29/bin
sudo bash startup.sh
cd ..
cd ..
sudo wget https://raw.githubusercontent.com/TTTheo/Image-Recognition-Application/master/manager.xml
sudo wget https://raw.githubusercontent.com/TTTheo/Image-Recognition-Application/master/tomcat-users.xml
sudo mv manager.xml apache-tomcat-9.0.29/conf/Catalina/localhost/
sudo mv tomcat-users.xml apache-tomcat-9.0.29/conf/
cd apache-tomcat-9.0.29/bin
sudo bash startup.sh
