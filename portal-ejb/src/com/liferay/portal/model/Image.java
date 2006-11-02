/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.model;

import com.liferay.util.Base64;

import com.sun.imageio.plugins.gif.GIFImageReader;
import com.sun.imageio.plugins.jpeg.JPEGImageReader;
import com.sun.imageio.plugins.png.PNGImageReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="Image.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class Image extends ImageModel {

	public static final String TYPE_GIF = "gif";

	public static final String TYPE_JPEG = "jpeg";

	public static final String TYPE_PNG = "png";

	public static final String TYPE_NOT_AVAILABLE = "na";

	public Image() {
	}

	public byte[] getTextObj() {
		if (_textObj == null) {
			_textObj = (byte[])Base64.stringToObject(getText());
		}

		return _textObj;
	}

	public void setTextObj(byte[] textObj) {
		_textObj = textObj;

		setTypeFromTextObj(textObj);

		super.setText(Base64.objectToString(textObj));
	}

	public void setTypeFromTextObj(byte[] textObj) {
		if (_log.isDebugEnabled()) {
			_log.debug("Attempt to detect type for " + getImageId());
		}

		if (textObj == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Abort attempt to detect type because image is null");
			}

			return;
		}

		String type = TYPE_NOT_AVAILABLE;

		ByteArrayInputStream bais = null;
		MemoryCacheImageInputStream mcis = null;

		try {
			bais = new ByteArrayInputStream(textObj);
			mcis = new MemoryCacheImageInputStream(bais);

			Iterator itr = ImageIO.getImageReaders(mcis);

			while (itr.hasNext()) {
				ImageReader reader = (ImageReader)itr.next();

				if (reader instanceof GIFImageReader) {
					type = TYPE_GIF;
				}
				else if (reader instanceof JPEGImageReader) {
					type = TYPE_JPEG;
				}
				else if (reader instanceof PNGImageReader) {
					type = TYPE_PNG;
				}

				reader.dispose();
			}
		}
		finally {
			if (bais != null) {
				try {
					bais.close();
				}
				catch (IOException ioe) {
					if (_log.isWarnEnabled()) {
						_log.warn(ioe);
					}
				}
			}

			if (mcis != null) {
				try {
					mcis.close();
				}
				catch (IOException ioe) {
					if (_log.isWarnEnabled()) {
						_log.warn(ioe);
					}
				}
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Detected type " + type);
		}

		super.setType(type);
	}

	private static Log _log = LogFactory.getLog(Image.class);

	private byte[] _textObj;

}