#!/bin/bash

#wget http://search.maven.org/remotecontent?filepath=junit/junit/4.12/junit-4.12.jar -O junit-4.12.jar
#wget http://search.maven.org/remotecontent?filepath=org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar -O hamcrest-core-1.3.jar
wget https://sourceforge.net/projects/ini4j/files/ini4j-bin/0.5.4/ini4j-0.5.4-bin.zip/download -O ini4j.zip

unzip ini4j.zip

mv ./ini4j-0.5.4/ini4j-0.5.4.jar ./
if [ -d ./ini4j-0.5.4 ]; then
    rm -rf ./ini4j-0.5.4
fi
rm ini4j.zip
