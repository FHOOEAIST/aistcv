# Copyright (c) 2021 the original author or authors.
# DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
#
# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at https://mozilla.org/MPL/2.0/.

# 0. create tmp folder (if not exist)
New-Item -ItemType Directory -Force -Path "C:\tmp"
New-Item -ItemType Directory -Force -Path "C:\tmp\python"

# 1. install python
wget "https://www.python.org/ftp/python/3.7.5/python-3.7.5.exe" -OutFile "C:\tmp\python\python-3.7.5.exe";
Start-Process C:\tmp\python\python-3.7.5.exe -ArgumentList '/quiet InstallAllUsers=1 PrependPath=1' -Wait;

# 1.1 add python to environment
$Env:Path += ";c:\tmp\python\python-3.7.5.exe"

# 2. install java
wget "https://download.java.net/java/GA/jdk11/9/GPL/openjdk-11.0.2_windows-x64_bin.zip" -OutFile "C:\tmp\openjdk.zip" ;
Expand-Archive -Path "C:\tmp\openjdk.zip" -DestinationPath "C:\tmp\" ;
Remove-Item "C:\tmp\openjdk.zip" -Force ;

# 2.1 set JAVA_HOME
$Env:JAVA_HOME = "c:\tmp\jdk-11.0.2"

# 3. install ant
wget "https://mirror.klaus-uwe.me/apache/ant/binaries/apache-ant-1.10.11-bin.zip" -OutFile "C:\tmp\ant.zip" ;
Expand-Archive -Path "C:\tmp\ant.zip" -DestinationPath "C:\tmp\";
Remove-Item "C:\tmp\ant.zip" -Force ;

# 3.1 set ant home
$Env:ANT_HOME = "c:\tmp\apache-ant-1.10.11"
$Env:Path += ";c:\tmp\apache-ant-1.10.11\bin"

# 4. install cmake 
wget "https://github.com/Kitware/CMake/releases/download/v3.17.1/cmake-3.17.1-win64-x64.zip" -OutFile "C:\tmp\cmake.zip"
Expand-Archive -Path "C:\tmp\cmake.zip" -DestinationPath "C:\tmp\";
Remove-Item "C:\tmp\cmake.zip" -Force ;

# 4.1 set environment var
$Env:Path += ";c:\tmp\cmake-3.17.1-win64-x64\bin\"