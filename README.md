[![DOI](https://zenodo.org/badge/335648723.svg)](https://zenodo.org/badge/latestdoi/335648723)
[![License: MPL 2.0](https://img.shields.io/badge/License-MPL%202.0-brightgreen.svg)](https://opensource.org/licenses/MPL-2.0)
[![GitHub release](https://img.shields.io/github/v/release/fhooeaist/aistcv.svg)](https://github.com/fhooeaist/aistcv/releases)
[![Maven Central](https://img.shields.io/maven-central/v/science.aist/aistcv.svg?label=Maven%20Central)](https://search.maven.org/artifact/science.aist/aistcv)
[![javadoc](https://javadoc.io/badge2/science.aist/aistcv/javadoc.svg)](https://javadoc.io/doc/science.aist/aistcv) 

![logo](./documentation/logo.png)

# AistCV




AistCV is a Java wrapper for [OpenCV](https://github.com/opencv/opencv).

There are already different great java wrappers for OpenCV available:

| Wrapper | Link                                       | Advantages                                                 | Disadvantages                                                             |
|---------|--------------------------------------------|------------------------------------------------------------|---------------------------------------------------------------------------|
| OpenPnP | [Link](https://github.com/openpnp/opencv)  | Is based on the original OpenCV build (same packages, class names, ...)      | Does not contain  contrib modules                                         |
| JavaCV  | [Link](https://github.com/bytedeco/javacv) |  Contains contrib modules | Is a hand made wrapper of OpenCV (differs in packages, class names, ...). Is strongly based on static methods instead of object-oriented calls. |

Because of the disadvantages AistCV was created with the advantages of both worlds: AistCV is based on the original OpenCV build (same packages, class names, ...) and contains the contrib modules.

| Wrapper | Advantages                                                 | Disadvantages                                                             |
|---------|------------------------------------------------------------|---------------------------------------------------------------------------|
| AistCV |  Is based on the original OpenCV build (same packages, class names, ...) and contains contains contrib modules      | Currently does only support Windows and Linux (no MacOS support!) |

## Getting Started

If you want to use the AistCV wrapper you have to:

- include the maven dependency to your project. The AistCV Version corresponds to the wrapped OpenCV Version.
- use the `AistCVLoader.loadShared()` to load native libraries before calling any wrapped functionality
- also think about to call `AistCVLoader.cleanupShared()` at the very end of your program


```xml
<dependency>
    <groupId>science.aist</groupId>
    <artifactId>aistcv</artifactId>
    <version>4.5.3</version>
</dependency>
```

## Supported platforms

| Platform | Architecture | Build Readme |
|----------|--------------|--------------|
| Windows  | 64bit        | [Link](./windows/readme.md) |
| Linux    | 64bit        | [Link](./linux/readme.md) |

## How to build

If you have to build OpenCV on your own use the prepared build scripts (have a look at the certain build readmes) and add

- `libopencv_java<version>.so` (Linux)
- `opencv_java<version>.dll` (Windows)
- `opencv_videoio_ffmpeg<version>_64.dll` (Windows)

to the main resource folder and

- adapt the filenames in the `nativelibrary.properties`
- remove the old libraries
- add `opencv-x.y.z.jar` to lib folder as well as adapt the pom to use the correct opencv-x.y.z file.

### Additional versions

If you build additional versions (e.g. MacOS or 32bit) you have to extend `nativelibrary.properties` and adapt the `NativeLibraryProperty` class. 

## FAQ

- I am getting a `java.lang.UnsatisfiedLinkError`. What should I do?
  - This means that you have forgotten to load the native library via `AistCVLoader.loadShared();`.
- I want to use OpenCV in Version x.y.z, which AistCV Version do I have to use?
  - The AistCV Version corresponds to the wrapped OpenCV version. So if you want e.g. to use OpenCV 4.3.0 use AistCV 4.3.0. Note that, there is not an AistCV Version for every OpenCV version. If you need a non-available version please create an issue. 
- I am using the Spring Framework, how can I load the native libraries?
  -  Create a bean definition for the AistCVLoader which calls the `loadShared()` method for initialization (see XML definition below).

```XML
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns="http://www.springframework.org/schema/beans"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!-- loads the OpenCV DLL -->
    <bean id="openCVLoader" class="aist.science.aistcv.AistCVLoader" init-method="loadShared"/>
    <!-- further beans -->
</beans>
```

## Contributing

**First make sure to read our [general contribution guidelines](https://fhooeaist.github.io/CONTRIBUTING.html).**
   
## Licence

Copyright (c) 2020 the original author or authors.
DO NOT ALTER OR REMOVE COPYRIGHT NOTICES.

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.

The following code is under different licence and copyright: 

| Licence | Filepaths |
|-|-|
| **3-Clause BSD**<br>see LICENCE_BSD | src/main/java/science/aist/aistcv/* |

## Research

If you are going to use this project as part of a research paper, we would ask you to reference this project by citing
it. 

[![DOI](https://zenodo.org/badge/335648723.svg)](https://zenodo.org/badge/latestdoi/335648723)
