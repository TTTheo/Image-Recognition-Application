#!/bin/bash

sudo apt-get update

sudo mkdir ~/java
cd ~/java
sudo wget https://download.java.net/java/GA/jdk13.0.1/cec27d702aa74d5a8630c65ae61e4305/9/GPL/openjdk-13.0.1_linux-x64_bin.tar.gz
sudo tar -xf openjdk-13.0.1_linux-x64_bin.tar.gz
