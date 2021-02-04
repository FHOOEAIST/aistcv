/*
 * Copyright 2020 OpenPnP
 *
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file or at
 * https://github.com/openpnp/opencv/blob/develop/LICENSE
 */

package aist.science.aistcv;


import aist.science.aistcv.helper.SharedLoader;
import aist.science.aistcv.helper.BinaryExtractor;
import org.opencv.core.Core;

import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>Loader which loads the native OpenCV dependencies for your platform</p>
 * <p>Currently we support 64bit Linux and Windows only!</p>
 * <p>Modified from <a href="https://github.com/openpnp/opencv/blob/develop/src/main/java/nu/pattern/OpenCV.java">OpenPnP OpenCV</a></p>
 *
 * @author Christoph Praschl
 * @author <a href="https://github.com/vonnieda">Jason von Nieda</a>
 * @author <a href="https://github.com/adambenhamo">adambenhamo</a>
 * @author <a href="https://github.com/sirrrich">sirrrich</a>
 * @author <a href="https://github.com/phrack">phrack</a>
 *
 */
public class AistCVLoader {
    private static final Logger logger = Logger.getLogger(AistCVLoader.class.getName());
    private static final BinaryExtractor binaryExtractor = new BinaryExtractor();
    private static SharedLoader sharedLoader = null;

    private AistCVLoader() {
    }

    /**
     * Exactly once per {@link ClassLoader}, attempt to load the native library (via {@link System#loadLibrary(String)} with {@link Core#NATIVE_LIBRARY_NAME}). If the first attempt fails, the native binary will be extracted from the classpath to a temporary location (which gets cleaned up on shutdown), that location is added to the {@code java.library.path} system property and {@link ClassLoader#usr_paths}, and then another call to load the library is made. Note this method uses reflection to gain access to private memory in {@link ClassLoader} as there's no documented method to augment the library path at runtime. Spurious calls are safe.
     */
    public static void loadShared(){
        if(sharedLoader != null){
            logger.log(Level.WARNING, "Already called shared load");
        } else {
            sharedLoader = new SharedLoader(binaryExtractor);
            Path libraryPath = sharedLoader.getLibraryPath();
            System.load(libraryPath.normalize().toString());
        }
    }

    /**
     * Removes the shared library path from the {@link ClassLoader#usr_paths} array, as well as to the {@code java.library.path} system property.
     * Uses the reflection API to make the field accessible, and may be unsafe in environments with a security policy.
     */
    public static void cleanupShared(){
        if(sharedLoader != null){
            sharedLoader.cleanup();
        } else {
            logger.log(Level.WARNING, "Can't cleanup shared library since it was not loaded");
        }

    }

    /**
     * Exactly once per {@link ClassLoader}, extract the native binary from the classpath to a temporary location (which gets cleaned up on shutdown), and load that binary (via {@link System#load(String)}). Spurious calls are safe.
     */
    public static void loadLocally() {
        final Path libraryPath = binaryExtractor.extractNativeBinary();
        System.load(libraryPath.normalize().toString());
    }
}
