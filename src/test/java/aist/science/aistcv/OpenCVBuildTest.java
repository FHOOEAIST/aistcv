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
import org.opencv.ximgproc.GraphSegmentation;
import org.opencv.ximgproc.Ximgproc;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.InputStream;

/**
 * @author David Baumgartner
 */

public class OpenCVBuildTest {
    @BeforeClass
    public void prepare() {
        AistCVLoader.loadShared();
    }

    @AfterClass
    public void cleanup() {
        AistCVLoader.cleanupShared();
    }

    /**
     * Test core module
     */
    @Test
    void testCore() {
        // given

        // when
        Mat t = new Mat();

        // then
        Assert.assertNotNull(t);
        t.release();
    }

    /**
     * test one contrib module (XImgproc)
     */
    @Test
    public void testXImgproc() {
        // given
        InputStream resourceAsStream = OpenCVBuildTest.class.getResourceAsStream("/testImage.jpg");
        OpenCVLoader loader = new OpenCVLoader();
        Mat seg = loader.apply(resourceAsStream);
        Mat t = new Mat();
        try {
            GraphSegmentation g = Ximgproc.createGraphSegmentation();

            // when
            g.processImage(seg, t);

            // then
            Assert.assertEquals(t.cols(), 480);
            Assert.assertEquals(t.rows(), 640);
            t.release();
            seg.release();
        } catch (Exception e) {
            if (seg != null) {
                seg.release();
            }
            t.release();
        }
    }
}
