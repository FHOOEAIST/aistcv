# Copyright (c) 2021 the original author or authors.
# DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
#
# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at https://mozilla.org/MPL/2.0/.

$opencv_version=4.5.3;

# 0. create tmp folder (if not exist)
New-Item -ItemType Directory -Force -Path "C:\tmp"

# 5. download opencv
wget "https://github.com/opencv/opencv/archive/refs/tags/4.5.3.zip" -OutFile "C:\tmp\opencv.zip"
Expand-Archive -Path "C:\tmp\opencv.zip" -DestinationPath "C:\tmp\";
Move-Item "C:\tmp\opencv-${opencv_version}" "C:\tmp\opencv";
Remove-Item "C:\tmp\opencv.zip" -Force ;

# 6.download opencv extra modules
wget "https://github.com/opencv/opencv_contrib/archive/4.5.3.zip" -OutFile "C:\tmp\opencv_contrib.zip"
Expand-Archive -Path "C:\tmp\opencv_contrib.zip" -DestinationPath "C:\tmp\";
Move-Item "C:\tmp\opencv_contrib-${opencv_version}" "C:\tmp\opencv_contrib";
Remove-Item "C:\tmp\opencv_contrib.zip" -Force ;

# 7. build the shit
New-Item -ItemType Directory -Force -Path "C:\tmp\opencv\build";
dir "C:\tmp\opencv\build";

cmake -D CMAKE_BUILD_TYPE=Release -D BUILD_opencv_world=OFF -DBUILD_SHARED_LIBS=OFF `
        -DBUILD_EXAMPLES=OFF -DBUILD_TESTS=OFF -DBUILD_PERF_TESTS=OFF `
        -DWITH_CUDA=OFF -DBUILD_FAT_JAVA_LIB=ON `
        -DOPENCV_FORCE_3RDPARTY_BUILD=ON `
        -D OPENCV_EXTRA_MODULES_PATH=C:\tmp\opencv_contrib\modules `
        -G "Visual Studio 16" `
        ..;


msbuild /m OpenCV.sln /t:Build /p:Configuration=Release /v:m;