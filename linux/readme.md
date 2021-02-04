# OpenCV Linux build

The OpenCV Linux build is based on [dockcross](https://github.com/dockcross/dockcross) and a shell script that runs a docker image and extracts the result.
The base image is now `dockcross/linux-x64` which has some toolchain settings for cross-building with different system libraries.

We currently build only GNU/linux 64bit and **no** 32bit version.

## How to build

Change the opencv version in the dockerfile.

Run the docker image. This will export the result files `libopencv_java<version>.so` and the `opencov-<version>.jar`.

```shell script
docker build -t aistcv --force-rm .
docker run --rm -v $(pwd):/export aistcv:latest
```
