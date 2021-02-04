/*
 * Copyright 2020 OpenPnP
 *
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file or at
 * https://github.com/openpnp/opencv/blob/develop/LICENSE
 */

package aist.science.aistcv.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * <p>Operating systems enumeration</p>
 * <p>Modified from <a href="https://github.com/openpnp/opencv/blob/develop/src/main/java/nu/pattern/OpenCV.java">OpenPnP OpenCV</a></p>
 *
 * @author Christoph Praschl
 * @author <a href="https://github.com/vonnieda">Jason von Nieda</a>
 * @author <a href="https://github.com/adambenhamo">adambenhamo</a>
 * @author <a href="https://github.com/sirrrich">sirrrich</a>
 * @author <a href="https://github.com/phrack">phrack</a>
 */
public enum OperatingSystem {
    OSX("^[Mm]ac OS X$"),
    LINUX("^[Ll]inux$"),
    WINDOWS("^[Ww]indows.*");

    private static final String OS = "os.name";
    private final Set<Pattern> patterns;

    OperatingSystem(final String... patterns) {
        this.patterns = new HashSet<>();

        for (final String pattern : patterns) {
            this.patterns.add(Pattern.compile(pattern));
        }
    }

    /**
     * @return get operating system of the current system
     */
    public static OperatingSystem getCurrent() {
        final String osName = System.getProperty(OS);

        for (final OperatingSystem os : OperatingSystem.values()) {
            if (os.is(osName)) {
                return os;
            }
        }

        throw new UnsupportedOperationException(String.format("Operating system \"%s\" is not supported.", osName));
    }

    private boolean is(final String id) {
        for (final Pattern pattern : patterns) {
            if (pattern.matcher(id).matches()) {
                return true;
            }
        }
        return false;
    }
}
