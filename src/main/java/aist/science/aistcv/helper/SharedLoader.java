/*
 * Copyright 2020 OpenPnP
 *
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file or at
 * https://github.com/openpnp/opencv/blob/develop/LICENSE
 */

package aist.science.aistcv.helper;

import aist.science.aistcv.exception.UncheckedIllegalAccessException;
import aist.science.aistcv.exception.UncheckedNoSuchFieldException;
import org.opencv.core.Core;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Implementation for loading a shared library</p>
 * <p>Modified from <a href="https://github.com/openpnp/opencv/blob/develop/src/main/java/nu/pattern/OpenCV.java">OpenPnP OpenCV</a></p>
 *
 * @author Christoph Praschl
 * @author <a href="https://github.com/vonnieda">Jason von Nieda</a>
 * @author <a href="https://github.com/adambenhamo">adambenhamo</a>
 * @author <a href="https://github.com/sirrrich">sirrrich</a>
 * @author <a href="https://github.com/phrack">phrack</a>
 */
public class SharedLoader {
    private static final String USR_PATH = "usr_paths";
    private static final String JAVA_LIBRARY_PATH = "java.library.path";

    /**
     * gets value of field {@link SharedLoader#libraryPath}
     *
     * @return value of field libraryPath
     * @see SharedLoader#libraryPath
     */
    public Path getLibraryPath() {
        return libraryPath;
    }

    private Path libraryPath;

    /**
     * Creates a shared loader and extracts the required binary to the temp folder
     * @param extractor used for extracting the required binary
     */
    public SharedLoader(BinaryExtractor extractor) {
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        } catch (final UnsatisfiedLinkError ule) {

            /* Only update the library path and load if the original error indicates it's missing from the library path. */
            if (!ule.getMessage().startsWith(String.format("no %s in "+JAVA_LIBRARY_PATH, Core.NATIVE_LIBRARY_NAME))) {
                throw ule;
            }

            /* Retain this path for cleaning up the library path later. */
            this.libraryPath = extractor.extractNativeBinary();

            addLibraryPath(libraryPath.getParent());
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        }
    }


    /**
     * Adds the provided {@link Path}, normalized, to the {@link ClassLoader#usr_paths} array, as well as to the {@code java.library.path} system property. Uses the reflection API to make the field accessible, and may be unsafe in environments with a security policy.
     *
     * @see <a href="http://stackoverflow.com/q/15409223">Adding new paths for native libraries at runtime in Java</a>
     */
    private static void addLibraryPath(final Path path) {
        final String normalizedPath = path.normalize().toString();

        try {
            final Field field = ClassLoader.class.getDeclaredField(USR_PATH);
            field.setAccessible(true);

            final Set<String> userPaths = new HashSet<>(Arrays.asList((String[]) field.get(null)));
            userPaths.add(normalizedPath);

            field.set(null, userPaths.toArray(new String[userPaths.size()]));

            System.setProperty(JAVA_LIBRARY_PATH, System.getProperty(JAVA_LIBRARY_PATH) + File.pathSeparator + normalizedPath);
        } catch (IllegalAccessException e) {
            throw new UncheckedIllegalAccessException("Failed to get permissions to set library path", e);
        } catch (NoSuchFieldException e) {
            throw new UncheckedNoSuchFieldException("Failed to get field handle to set library path", e);
        }
    }

    /**
     * Removes the provided {@link Path}, normalized, from the {@link ClassLoader#usr_paths} array, as well as to the {@code java.library.path} system property. Uses the reflection API to make the field accessible, and may be unsafe in environments with a security policy.
     */
    private static void removeLibraryPath(final Path path) {
        final String normalizedPath = path.normalize().toString();

        try {
            final Field field = ClassLoader.class.getDeclaredField(USR_PATH);
            field.setAccessible(true);

            final Set<String> userPaths = new HashSet<>(Arrays.asList((String[]) field.get(null)));
            userPaths.remove(normalizedPath);

            field.set(null, userPaths.toArray(new String[userPaths.size()]));

            System.setProperty(JAVA_LIBRARY_PATH, System.getProperty(JAVA_LIBRARY_PATH).replace(File.pathSeparator + path.normalize().toString(), ""));
        } catch (IllegalAccessException e) {
            throw new UncheckedIllegalAccessException("Failed to get permissions to set library path", e);
        } catch (NoSuchFieldException e) {
            throw new UncheckedNoSuchFieldException("Failed to get field handle to set library path", e);
        }
    }


    /**
     * Removes the library path from the {@link ClassLoader#usr_paths} array, as well as to the {@code java.library.path} system property.
     * Uses the reflection API to make the field accessible, and may be unsafe in environments with a security policy.
     */
    public void cleanup()  {
        if (libraryPath != null) {
            removeLibraryPath(libraryPath.getParent());
        }
    }
}
