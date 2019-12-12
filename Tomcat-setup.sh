#!/bin/bash

sudo mkdir tomcat
cd tomcat
sudo wget http://mirrors.advancedhosters.com/apache/tomcat/tomcat-9/v9.0.29/bin/apache-tomcat-9.0.29.tar.gz
sudo tar -xf apache-tomcat-9.0.29.tar.gz
sudo wget https://raw.githubusercontent.com/TTTheo/Image-Recognition-Application/master/manager.xml
sudo wget https://raw.githubusercontent.com/TTTheo/Image-Recognition-Application/master/tomcat-users.xml
