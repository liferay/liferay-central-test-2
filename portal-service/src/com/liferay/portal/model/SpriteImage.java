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

package com.liferay.portal.model;

import java.io.Serializable;

/**
 * <a href="SpriteImage.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SpriteImage implements Serializable {

	public SpriteImage(
		String spriteFileName, String imageFileName, int offset, int height,
		int width) {

		_spriteFileName = spriteFileName;
		_imageFileName = imageFileName;
		_offset = offset;
		_height = height;
		_width = width;
	}

	public String getSpriteFileName() {
		return _spriteFileName;
	}

	public String getImageFileName() {
		return _imageFileName;
	}

	public int getOffset() {
		return _offset;
	}

	public int getHeight() {
		return _height;
	}

	public int getWidth() {
		return _width;
	}

	private String _spriteFileName;
	private String _imageFileName;
	private int _offset;
	private int _height;
	private int _width;

}