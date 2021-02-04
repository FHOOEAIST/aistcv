/*
 * Copyright 2020 OpenPnP
 *
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file or at
 * https://github.com/openpnp/opencv/blob/develop/LICENSE
 */

package aist.science.aistcv.helper;

import aist.science.aistcv.domain.Architecture;
import aist.science.aistcv.domain.OperatingSystem;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * <p>Help method for extracting the opencv bianries</p>
 * <p>Modified from <a href="https://github.com/openpnp/opencv/blob/develop/src/main/java/nu/pattern/OpenCV.java">OpenPnP OpenCV</a></p>
 *
 * @author Christoph Praschl
 * @author <a href="https://github.com/vonnieda">Jason von Nieda</a>
 * @author <a href="https://github.com/adambenhamo">adambenhamo</a>
 * @author <a href="https://github.com/sirrrich">sirrrich</a>
 * @author <a href="https://github.com/phrack">phrack</a>
 */
public class BinaryExtractor {
    /**
     * Selects the appropriate packaged binary, extracts it to a temporary location (which gets deleted when the JVM shuts down), and returns a {@link Path} to that file.
     * @return the {@link Path} to the temp folder containing the extracted native library
     */
    public Path extractNativeBinary() {
        final OperatingSystem os = OperatingSystem.getCurrent();
        final Architecture arch = Architecture.getCurrent();
        return extractNativeBinary(os, arch);
    }

    /**
     * Extracts the packaged binary for the specified platform to a temporary location (which gets deleted when the JVM shuts down), and returns a {@link Path} to that file.
     * @param os operating system for which binary should be extracted
     * @param arch architecture for which binary should be extracted
     * @return the {@link Path} to the temp folder containing the extracted native library
     */
    public Path extractNativeBinary(final OperatingSystem os, final Architecture arch) {
        final String location = NativeLibraryProperty.getFileName(os, arch);
        final InputStream binary = BinaryExtractor.class.getResourceAsStream("/"+location);
        final Path destination;

        // Do not try to delete the temporary directory on the close if Windows
        // because there will be a write lock on the file which will cause an
        // AccessDeniedException. Instead, try to delete existing instances of
        // the temporary directory before extracting.
        if (OperatingSystem.WINDOWS.equals(os)) {
            destination = new TemporaryDirectory().deleteOldInstancesOnStart().getPath().resolve("./" + location).normalize();
        } else {
            destination = new TemporaryDirectory().markDeleteOnExit().getPath().resolve("./" + location).normalize();
        }

        try {
            Files.createDirectories(destination.getParent());
            Files.copy(binary, destination);
        } catch (final IOException ioe) {
            throw new IllegalStateException(String.format("Error writing native library to \"%s\".", destination), ioe);
        }

        return destination;
    }
}
