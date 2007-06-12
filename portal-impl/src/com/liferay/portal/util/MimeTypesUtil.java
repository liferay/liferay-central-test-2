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

package com.liferay.portal.util;

import java.io.File;

import javax.activation.MimetypesFileTypeMap;

/**
 * <a href="MimeTypesUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 *
 */
public class MimeTypesUtil {

	public static String getContentType(File file) {
		return _instance._getContentType(file);
	}

	public static String getContentType(String fileName) {
		return _instance._getContentType(fileName);
	}

	private MimeTypesUtil() {
		_mimeTypes = new MimetypesFileTypeMap();
	}

	private String _getContentType(File file) {
		return _mimeTypes.getContentType(file);
	}

	private String _getContentType(String fileName) {
		return _mimeTypes.getContentType(fileName);
	}

	private static MimeTypesUtil _instance = new MimeTypesUtil();

	private MimetypesFileTypeMap _mimeTypes;

}