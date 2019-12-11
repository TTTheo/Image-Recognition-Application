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


wget https://repo.anaconda.com/miniconda/Miniconda3-latest-Linux-x86_64.sh
bash Miniconda3-latest-Linux-x86_64.sh

#wget http://repo.continuum.io/archive/Anaconda3-4.0.0-Linux-x86_64.sh
#bash Anaconda3-4.0.0-Linux-x86_64.sh -b
export PATH=~/miniconda3/bin:$PATH
conda update -y conda

echo 'export PATH=~/miniconda3/bin:$PATH' >> ~/.bashrc
source ~/.bashrc

conda install -y pytorch torchvision cpuonly -c pytorch


