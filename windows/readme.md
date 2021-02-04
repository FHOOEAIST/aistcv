# OpenCV Windows build

The OpenCV Windows build is based on Powershell scripts.

**Note**: We tried to use Windows Docker images with [dockcross](https://github.com/dockcross/dockcross) for the build. Unfortunately, this did not work out.

## Requirements

You need:

- Windows 10 64bit (probably)
- Powershell (with activated Windows Developer Settings)
- Visual Studio (respectively msbuild)

### Additional Requirements

**Note:** You can skip this step if you have installed the additional software and have set environment variables

Additional required programs are installed into `C:\tmp` via the `prepare.ps1` script.

This will install

- Python 3.7.5
- Openjdk 11.0.2
- Apache Ant 1.10.7
- CMake 3.17.1

and set/adapt the environment variables

- Path (Python, Apache Ant, CMake)
- JAVA_HOME
- ANT_HOME

## How to build

Change the version variable in the beginning of the `build.ps1` file.
Run it. Afterwards, have a look into the `C:\tmp\opencv\build\bin\Release` folder, and you will find the result files `opencv_java<version>.dll`, `opencv_videoio_ffmpeg<version>_64.dll` and `opencv-<version>.jar`. Copy the .dll files into the AistCV main resources folder `aistcv\src\main\resources`.
