/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.util;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.SampleModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import net.jmge.gif.Gif89Encoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ImageUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ImageUtil {

	public static void encodeGIF(BufferedImage image, OutputStream out)
		throws IOException {

		Gif89Encoder encoder = new Gif89Encoder(image);

		encoder.encode(out);
	}

	public static void encodeWBMP(BufferedImage image, OutputStream out)
		throws InterruptedException, IOException {

		SampleModel sampleModel = image.getSampleModel();

		int type = sampleModel.getDataType();

		if ((image.getType() != BufferedImage.TYPE_BYTE_BINARY) ||
			(type < DataBuffer.TYPE_BYTE) || (type > DataBuffer.TYPE_INT) ||
			(sampleModel.getNumBands() != 1) ||
			(sampleModel.getSampleSize(0) != 1)) {

			BufferedImage binaryImage = new BufferedImage(
				image.getWidth(), image.getHeight(),
				BufferedImage.TYPE_BYTE_BINARY);

			binaryImage.getGraphics().drawImage(image, 0, 0, null);

			image = binaryImage;
		}

		if (!ImageIO.write(image, "wbmp", out)) {

			// See http://www.jguru.com/faq/view.jsp?EID=127723

			out.write(0);
			out.write(0);
			out.write(_toMultiByte(image.getWidth()));
			out.write(_toMultiByte(image.getHeight()));

			DataBuffer dataBuffer = image.getData().getDataBuffer();

			int size = dataBuffer.getSize();

			for (int i = 0; i < size; i++) {
				out.write((byte)dataBuffer.getElem(i));
			}
		}
	}

	public static byte[] read(ClassLoader classLoader, String name) {
		return read(classLoader.getResourceAsStream(name));
	}

	public static byte[] read(InputStream is) {
		byte[] byteArray = null;

		if (is == null) {
			return byteArray;
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			byte[] bytes = new byte[512];

			for (int i = is.read(bytes, 0, 512); i != -1;
					i = is.read(bytes, 0, 512)) {

				baos.write(bytes, 0, i);
			}

			byteArray = baos.toByteArray();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e);
			}
		}
		finally {
			try {
				is.close();
			}
			catch (Exception e) {
			}

			try {
				baos.close();
			}
			catch (Exception e) {
			}
		}

		return byteArray;
	}

	public static BufferedImage scale(BufferedImage image, double factor) {
		AffineTransformOp op = new AffineTransformOp(
			AffineTransform.getScaleInstance(
				factor, factor), null);

		return op.filter(image, null);
	}

	public static BufferedImage scale(
		BufferedImage image, int maxHeight, int maxWidth) {

		int imageHeight = image.getHeight();
		int imageWidth = image.getWidth();

		if (maxHeight == 0) {
			maxHeight = imageHeight;
		}

		if (maxWidth == 0) {
			maxWidth = imageWidth;
		}

		if ((imageHeight <= maxHeight) && (imageWidth <= maxWidth)) {
			return image;
		}

		double factor = 0.1;

		int heightDelta = imageHeight - maxHeight;
		int widthDelta = imageWidth - maxWidth;

		if (heightDelta > widthDelta) {
			factor = (double)maxHeight / imageHeight;
		}
		else {
			factor = (double)maxWidth / imageWidth;
		}

		return scale(image, factor);
	}

	private static byte[] _toMultiByte(int intValue) {
		int numBits = 32;
		int mask = 0x80000000;

		while (mask != 0 && (intValue & mask) == 0) {
			numBits--;
			mask >>>= 1;
		}

		int numBitsLeft = numBits;
		byte[] multiBytes = new byte[(numBitsLeft + 6) / 7];

		int maxIndex = multiBytes.length - 1;

		for (int b = 0; b <= maxIndex; b++) {
			multiBytes[b] = (byte)((intValue >>> ((maxIndex - b) * 7)) & 0x7f);

			if (b != maxIndex) {
				multiBytes[b] |= (byte)0x80;
			}
		}

		return multiBytes;
	}

	private static Log _log = LogFactory.getLog(ImageUtil.class);

}