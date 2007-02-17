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

package com.liferay.portal.webdav;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.util.FileUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.StringUtil;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="WebDAVUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class WebDAVUtil {

	public static final int SC_MULTI_STATUS = 207;

	public static String fixPath(String path) {
		if (path.endsWith(StringPool.SLASH)) {
			path = path.substring(0, path.length() - 1);
		}

		return path;
	}

	public static String getCompanyId(String path) {
		String[] pathArray = getPathArray(path);

		if (pathArray.length <= 0) {
			return StringPool.BLANK;
		}
		else {
			return pathArray[0];
		}
	}

	public static String getDestination(HttpServletRequest req) {
		String destination = req.getHeader("Destination");

		if (_log.isDebugEnabled()) {
			_log.debug("Destination " + destination);
		}

		return destination;
	}

	public static long getGroupId(String path) {
		String[] pathArray = getPathArray(path);

		if (pathArray.length <= 1) {
			return 0;
		}
		else {
			return GetterUtil.getLong(pathArray[1]);
		}
	}

	public static String[] getPathArray(String path) {
		if (path.startsWith(StringPool.SLASH)) {
			path = path.substring(1, path.length());
		}

		return StringUtil.split(path, StringPool.SLASH);
	}

	public static String getRequestXML(HttpServletRequest req)
		throws IOException {

		String xml = new String(FileUtil.getBytes(req.getInputStream()));

		if (_log.isDebugEnabled()) {
			_log.debug("Request XML\n" + xml);
		}

		return xml;
	}

	private static Log _log = LogFactory.getLog(WebDAVUtil.class);

}