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

package com.liferay.portal.kernel.util;

import java.io.InputStream;

/**
 * <a href="DocumentConversionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 */
public class DocumentConversionUtil {

	public static InputStream convert(
			String id, InputStream is, String sourceExtension,
			String targetExtension)
		throws Exception {

		Object returnObj = PortalClassInvoker.invoke(
			_CLASS, "convert",
			new Object[] {id, is, sourceExtension, targetExtension}, false);

		if (returnObj != null) {
			return (InputStream)returnObj;
		}
		else {
			return null;
		}
	}

	public static String[] getConversions(String extension) throws Exception {
		Object returnObj = PortalClassInvoker.invoke(
			_CLASS, "getConversions", new Object[] {extension}, false);

		if (returnObj != null) {
			return (String[])returnObj;
		}
		else {
			return null;
		}
	}

	private static final String _CLASS =
		"com.liferay.portlet.documentlibrary.util.DocumentConversionUtil";

}