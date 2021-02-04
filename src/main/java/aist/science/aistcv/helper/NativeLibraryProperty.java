/*
 * Copyright 2020 OpenPnP
 *
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file or at
 * https://github.com/openpnp/opencv/blob/develop/LICENSE
 */

package aist.science.aistcv.helper;

import aist.science.aistcv.exception.UnsupportedPlatformException;
import aist.science.aistcv.domain.Architecture;
import aist.science.aistcv.domain.OperatingSystem;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

/**
 * <p>Helper class for accessing the property file and getting the library name for the given operating system and architecture</p>
 * <p>Modified from <a href="https://github.com/openpnp/opencv/blob/develop/src/main/java/nu/pattern/OpenCV.java">OpenPnP OpenCV</a></p>
 *
 * @author Christoph Praschl
 * @author <a href="https://github.com/vonnieda">Jason von Nieda</a>
 * @author <a href="https://github.com/adambenhamo">adambenhamo</a>
 * @author <a href="https://github.com/sirrrich">sirrrich</a>
 * @author <a href="https://github.com/phrack">phrack</a>
 */
public class NativeLibraryProperty {
    private static final String PROPERTIES_FILE = "/nativelibrary.properties";
    private static final String LINUX_64_KEY = "opencv.linux.64bit";
    private static final String WINDOWS_64_KEY = "opencv.windows.64bit";
    private static final Properties properties;

    static  {
        try (InputStream input = BinaryExtractor.class.getResourceAsStream(PROPERTIES_FILE)) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException e){
            throw new UncheckedIOException("Could not load properties file", e);
        }
    }

    private NativeLibraryProperty() {
    }

    /**
     * Returns the native library file name for the given operating system and architecture
     * @param os Operating system
     * @param arch Architecture
     * @return library file name
     */
    public static String getFileName(OperatingSystem os, Architecture arch){
        if(os == OperatingSystem.LINUX && arch == Architecture.X86_64) {
            return properties.getProperty(LINUX_64_KEY);
        } else if(os == OperatingSystem.WINDOWS && arch == Architecture.X86_64) {
            return properties.getProperty(WINDOWS_64_KEY);
        } else {
            throw new UnsupportedPlatformException(os, arch);
        }
    }
}
