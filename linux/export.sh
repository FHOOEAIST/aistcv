#!/bin/bash

# Copyright (c) 2021 the original author or authors.
# DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
#
# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at https://mozilla.org/MPL/2.0/.

OPENCVSHORT="${OPENCV_VERSION//.}"

cp /app/opencv/build/bin/opencv-${OPENCVSHORT}.jar /export/opencv-${OPENCVSHORT}.jar
cp /app/opencv/build/lib/libopencv_java${OPENCVSHORT}.so /export/libopencv_java${OPENCVSHORT}.so