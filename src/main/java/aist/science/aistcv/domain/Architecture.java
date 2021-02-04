/*
 * Copyright 2020 OpenPnP
 *
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file or at
 * https://github.com/openpnp/opencv/blob/develop/LICENSE
 */

package aist.science.aistcv.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Enum for the operating system's architecture</p>
 * <p>Modified from <a href="https://github.com/openpnp/opencv/blob/develop/src/main/java/nu/pattern/OpenCV.java">OpenPnP OpenCV</a></p>
 *
 * @author Christoph Praschl
 * @author <a href="https://github.com/vonnieda">Jason von Nieda</a>
 * @author <a href="https://github.com/adambenhamo">adambenhamo</a>
 * @author <a href="https://github.com/sirrrich">sirrrich</a>
 * @author <a href="https://github.com/phrack">phrack</a>
 */
public enum Architecture {
    X86_32("i386", "i686", "x86"),
    X86_64("amd64", "x86_64");

    private final Set<String> patterns;
    private static final String ARCH = "os.arch";

    Architecture(final String... patterns) {
        this.patterns = new HashSet<>(Arrays.asList(patterns));
    }

    /**
     * @return get architecture of the current system
     */
    public static Architecture getCurrent() {
        final String osArch = System.getProperty(ARCH);

        for (final Architecture arch : Architecture.values()) {
            if (arch.is(osArch)) {
                return arch;
            }
        }

        throw new UnsupportedOperationException(String.format("Architecture \"%s\" is not supported.", osArch));
    }

    private boolean is(final String id) {
        return patterns.contains(id);
    }
}
