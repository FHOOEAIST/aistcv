/*
 * Copyright 2020 OpenPnP
 *
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file or at
 * https://github.com/openpnp/opencv/blob/develop/LICENSE
 */

package aist.science.aistcv.exception;

/**
 * <p>Unchecked equivalent for {@link NoSuchFieldException}</p>
 * <p>Modified from <a href="https://github.com/openpnp/opencv/blob/develop/src/main/java/nu/pattern/OpenCV.java">OpenPnP OpenCV</a></p>
 *
 * @author Christoph Praschl
 * @author <a href="https://github.com/vonnieda">Jason von Nieda</a>
 * @author <a href="https://github.com/adambenhamo">adambenhamo</a>
 * @author <a href="https://github.com/sirrrich">sirrrich</a>
 * @author <a href="https://github.com/phrack">phrack</a>
 */
public class UncheckedNoSuchFieldException  extends RuntimeException {
    public UncheckedNoSuchFieldException(String message) {
        super(message);
    }

    public UncheckedNoSuchFieldException(String message, NoSuchFieldException cause) {
        super(message, cause);
    }
}
