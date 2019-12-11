#!/bin/bash

sudo apt-get update


# install java
sudo mkdir ~/java
cd ~/java
sudo wget https://download.java.net/java/GA/jdk13.0.1/cec27d702aa74d5a8630c65ae61e4305/9/GPL/openjdk-13.0.1_linux-x64_bin.tar.gz
sudo tar -xf openjdk-13.0.1_linux-x64_bin.tar.gz
cd ..
export JAVA_HOME=~/java/jdk-13.0.1
export PATH=$JAVA_HOME/bin:$PATH

# install Python
sudo apt-get -y install python-pip
sudo pip2 install numpy
wget http://repo.continuum.io/archive/Anaconda3-4.0.0-Linux-x86_64.sh
bash Anaconda3-4.0.0-Linux-x86_64.sh -b
export PATH=~/anaconda3/bin:$PATH
conda update -y conda
conda install -y pytorch torchvision cpuonly -c pytorch

