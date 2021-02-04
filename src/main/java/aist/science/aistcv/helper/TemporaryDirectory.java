/*
 * Copyright 2020 OpenPnP
 *
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file or at
 * https://github.com/openpnp/opencv/blob/develop/LICENSE
 */

package aist.science.aistcv.helper;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>Helper class for temp folder</p>
 * <p>Modified from <a href="https://github.com/openpnp/opencv/blob/develop/src/main/java/nu/pattern/OpenCV.java">OpenPnP OpenCV</a></p>
 *
 * @author Christoph Praschl
 * @author <a href="https://github.com/vonnieda">Jason von Nieda</a>
 * @author <a href="https://github.com/adambenhamo">adambenhamo</a>
 * @author <a href="https://github.com/sirrrich">sirrrich</a>
 * @author <a href="https://github.com/phrack">phrack</a>
 */
public class TemporaryDirectory {
    private static final Logger logger = Logger.getLogger(TemporaryDirectory.class.getName());

    static final String OPENCV_PREFIX = "aistcv";
    final Path path;


    /**
     * Creates a temporary directory to which the binaries should be saved
     */
    public TemporaryDirectory() {
        try {
            path = Files.createTempDirectory(OPENCV_PREFIX);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Sets flag that old instances of binaries should be deleted before creating new copies
     * @return this
     */
    public TemporaryDirectory deleteOldInstancesOnStart() {
        Path tempDirectory = path.getParent();

        for (File file : Objects.requireNonNull(tempDirectory.toFile().listFiles())) {
            if (file.isDirectory() && file.getName().startsWith(OPENCV_PREFIX)) {
                try {
                    delete(file.toPath());
                } catch (RuntimeException e) {
                    logger.log(Level.WARNING, "Could not delete file" + file.toString());
                }
            }
        }

        return this;
    }

    /**
     * Sets flag that the copied library should be deleted after runtime shutdown
     * @return this
     */
    public TemporaryDirectory markDeleteOnExit() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::delete));
        return this;
    }

    /**
     * Gets the path to the temp folder
     * @return temp folder containing the copied binaries
     */
    public Path getPath() {
        return path;
    }


    private void delete(Path path) {
        if (!Files.exists(path)) {
            return;
        }

        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult postVisitDirectory(final Path dir, final IOException e) throws IOException {
                    Files.deleteIfExists(dir);
                    return super.postVisitDirectory(dir, e);
                }

                @Override
                public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs)
                        throws IOException {
                    Files.deleteIfExists(file);
                    return super.visitFile(file, attrs);
                }
            });
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * deletes the temp folder and its content
     */
    public void delete() {
        delete(path);
    }
}
