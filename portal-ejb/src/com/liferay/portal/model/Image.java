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

	public Image() {
	}

	public void setText(String text) {
		_textObj = null;
		_type = null;

		super.setText(text);
	}

	public byte[] getTextObj() {
		if (_textObj == null) {
			_textObj = (byte[])Base64.stringToObject(getText());
		}

		return _textObj;
	}

	public void setTextObj(byte[] textObj) {
		_textObj = textObj;
		_type = null;

		super.setText(Base64.objectToString(textObj));
	}

	public String getType() {
		if (_textObj == null) {
			getTextObj();
		}

		if ((_type == null) && (_textObj != null)) {
			ByteArrayInputStream bais = null;
			MemoryCacheImageInputStream mcis = null;

			try {
				bais = new ByteArrayInputStream(_textObj);
				mcis = new MemoryCacheImageInputStream(bais);

				Iterator itr = ImageIO.getImageReaders(mcis);

				_type = null;

				if (itr.hasNext()) {
					Object obj = itr.next();

					if (obj instanceof GIFImageReader) {
						_type = "gif";
					}
					else if (obj instanceof JPEGImageReader) {
						_type = "jpeg";
					}
					else if (obj instanceof PNGImageReader) {
						_type = "png";
					}
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
		}

		return _type;
	}

	private static Log _log = LogFactory.getLog(Image.class);

	private byte[] _textObj;
	private String _type;

}