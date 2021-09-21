# Copyright (c) 2021 the original author or authors.
# DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
#
# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at https://mozilla.org/MPL/2.0/.

FROM dockcross/linux-x64

ENV DEBIAN_FRONTEND=noninteractive
RUN mkdir -p /usr/share/man/man1 \
    && apt-get update \
    && apt-get install -y ant openjdk-11-jdk unzip \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app
ENV ANT_HOME=/usr/share/ant

# get opencv code by tag
# make build dir
# build and install as lib
# cleanup
ARG opencv_version=4.5.3
RUN wget https://github.com/opencv/opencv/archive/${opencv_version}.zip \
    && unzip ${opencv_version}.zip \
    && mv opencv-${opencv_version} opencv \
    && rm ${opencv_version}.zip \
    && wget https://github.com/opencv/opencv_contrib/archive/${opencv_version}.zip \
    && unzip ${opencv_version}.zip \
    && mv opencv_contrib-${opencv_version} opencv_contrib \
    && rm ${opencv_version}.zip \
    && cd opencv \
    && mkdir build && cd build \
    && cmake -DCMAKE_BUILD_TYPE=Release -DBUILD_opencv_world=OFF -DBUILD_SHARED_LIBS=OFF \
        -DBUILD_EXAMPLES=OFF -DBUILD_TESTS=OFF -DBUILD_PERF_TESTS=OFF \
        -DWITH_CUDA=OFF -DBUILD_JAVA=ON -DBUILD_FAT_JAVA_LIB=ON \
        -DOPENCV_FORCE_3RDPARTY_BUILD=ON \
        -DOPENCV_EXTRA_MODULES_PATH=/app/opencv_contrib/modules \
        .. \
    && make -j8 \
    && make install \
    && cd /app

# set opencv version as environment variable
# in the docker container for later export
# via the export script to the external
# directory mapped to /export
ENV OPENCV_VERSION=${opencv_version}
COPY export.sh export.sh
CMD ["/bin/bash","/app/export.sh"]
