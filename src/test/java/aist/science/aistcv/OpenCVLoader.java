/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package aist.science.aistcv;


import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;

/**
 * <p>Loads a Open CV Image by InputStream using the</p>
 *
 * @author Christoph Praschl
 */
public class OpenCVLoader implements Function<InputStream, Mat> {

    /**
     * Method which reads an inputstream into a byte array
     *
     * @param stream stream which should be read
     * @return Returns read inputstream
     * @throws IOException is thrown when stream could not be read/written
     */
    private static byte[] readStream(InputStream stream) throws IOException {
        // Copy content of the image to byte-array
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = stream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        byte[] temporaryImageInMemory = buffer.toByteArray();
        buffer.close();
        stream.close();
        return temporaryImageInMemory;
    }

    @Override
    public Mat apply(InputStream input) {
        Mat m;
        byte[] temporaryImageInMemory;
        try {
            temporaryImageInMemory = readStream(input);
            m = Imgcodecs.imdecode(new MatOfByte(temporaryImageInMemory), Imgcodecs.IMREAD_COLOR);
        } catch (IOException e) {
            throw new IllegalArgumentException("Inputstream could not be converted into image.", e);
        }

        return m;
    }

}
