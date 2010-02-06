/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.image;

import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageProcessor;
import com.liferay.portal.kernel.image.ImageProcessorUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.util.BaseTestCase;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.RenderedImage;

import java.io.File;
import java.io.RandomAccessFile;

import java.util.Arrays;

import javax.imageio.ImageIO;

/**
 * <a href="ImageProcessorImplTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class ImageProcessorImplTest extends BaseTestCase {

	public void testReadBMP() throws Exception {
		testRead("liferay.bmp");
	}

	public void testReadGIF() throws Exception {
		testRead("liferay.gif");
	}

	public void testReadJPEG() throws Exception {
		testRead("liferay.jpeg");
	}

	public void testReadJPG() throws Exception {
		testRead("liferay.jpg");
	}

	public void testReadPNG() throws Exception {
		testRead("liferay.png");
	}

	protected void testRead(String fileName) throws Exception {
		fileName =
			"portal-impl/test/com/liferay/portal/image/dependencies/" +
				fileName;

		File file = new File(fileName);

		BufferedImage expectedImage = ImageIO.read(file);

		assertNotNull(expectedImage);

		DataBufferByte expectedDataBufferByte =
			(DataBufferByte)expectedImage.getData().getDataBuffer();

		byte[][] expectedData = expectedDataBufferByte.getBankData();

		String expectedType = FileUtil.getExtension(fileName);

		if (expectedType.equals("jpeg")) {
			expectedType = ImageProcessor.TYPE_JPEG;
		}

		RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");

		byte[] bytes = new byte[(int)randomAccessFile.length()];

		randomAccessFile.readFully(bytes);

		ImageBag imageBag = ImageProcessorUtil.read(bytes);

		RenderedImage resultImage = imageBag.getRenderedImage();

		assertNotNull(resultImage);

		DataBufferByte resultDataBufferByte =
			(DataBufferByte)resultImage.getData().getDataBuffer();

		byte[][] resultData = resultDataBufferByte.getBankData();

		String resultType = imageBag.getType();

		assertTrue(expectedType.equalsIgnoreCase(resultType));
		assertTrue(Arrays.deepEquals(expectedData, resultData));
	}

}