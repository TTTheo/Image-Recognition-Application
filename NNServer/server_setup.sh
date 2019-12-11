#!/bin/bash

sudo apt-get update


# install java


# install Python
sudo apt-get -y install python-pip
sudo pip2 install numpy
wget http://repo.continuum.io/archive/Anaconda3-4.0.0-Linux-x86_64.sh
bash Anaconda3-4.0.0-Linux-x86_64.sh -b
export PATH=~/anaconda3/bin:$PATH
conda update -y conda
conda install -y pytorch torchvision cpuonly -c pytorch

