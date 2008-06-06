/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.util.servlet;

import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.portlet.ResourceResponse;

import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="PortletResponseUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortletResponseUtil {

	public static void sendFile(
			ResourceResponse res, String fileName, byte[] bytes)
		throws IOException {

		sendFile(res, fileName, bytes, null);
	}

	public static void sendFile(
			ResourceResponse res, String fileName, byte[] bytes,
			String contentType)
		throws IOException {

		setHeaders(res, fileName, contentType);

		write(res, bytes);
	}

	public static void sendFile(
			ResourceResponse res, String fileName, InputStream is)
		throws IOException {

		sendFile(res, fileName, is, null);
	}

	public static void sendFile(
			ResourceResponse res, String fileName, InputStream is,
			String contentType)
		throws IOException {

		setHeaders(res, fileName, contentType);

		write(res, is);
	}

	public static void write(ResourceResponse res, String s)
		throws IOException {

		write(res, s.getBytes(StringPool.UTF8));
	}

	public static void write(ResourceResponse res, byte[] bytes)
		throws IOException {

		write(res, bytes, 0);
	}

	public static void write(
			ResourceResponse res, byte[] bytes, int contentLength)
		throws IOException {

		OutputStream os = null;

		try {

			// LEP-3122

			if (!res.isCommitted() || ServerDetector.isPramati()) {

				// LEP-536

				if (contentLength == 0) {
					contentLength = bytes.length;
				}

				res.setContentLength(contentLength);

				os = new BufferedOutputStream(res.getPortletOutputStream());

				os.write(bytes, 0, contentLength);
			}
		}
		finally {
			ServletResponseUtil.cleanUp(os);
		}
	}

	public static void write(ResourceResponse res, InputStream is)
		throws IOException {

		OutputStream os = null;

		try {
			if (!res.isCommitted()) {
				os = new BufferedOutputStream(res.getPortletOutputStream());

				int c = is.read();

				while (c != -1) {
					os.write(c);

					c = is.read();
				}
			}
		}
		finally {
			ServletResponseUtil.cleanUp(os, is);
		}
	}

	protected static void setHeaders(
		ResourceResponse res, String fileName, String contentType) {

		if (_log.isDebugEnabled()) {
			_log.debug("Sending file of type " + contentType);
		}

		// LEP-2201

		if (Validator.isNotNull(contentType)) {
			res.setContentType(contentType);
		}

		res.setProperty(HttpHeaders.CACHE_CONTROL, HttpHeaders.PUBLIC);
		res.setProperty(HttpHeaders.PRAGMA, HttpHeaders.PUBLIC);

		if (Validator.isNotNull(fileName)) {
			String contentDisposition =
				"attachment; filename=\"" + fileName + "\"";

			// If necessary for non-ASCII characters, encode based on RFC 2184.
			// However, not all browsers support RFC 2184. See LEP-3127.

			boolean ascii = true;

			for (int i = 0; i < fileName.length(); i++) {
				if (!CharUtils.isAscii(fileName.charAt(i))) {
					ascii = false;

					break;
				}
			}

			try {
				if (!ascii) {
					URLCodec codec = new URLCodec(StringPool.UTF8);

					String encodedFileName =
						StringUtil.replace(codec.encode(fileName), "+", "%20");

					contentDisposition =
						"attachment; filename*=UTF-8''" + encodedFileName;
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e);
				}
			}

			String extension = GetterUtil.getString(
				FileUtil.getExtension(fileName));

			if (extension.equals("pdf")) {
				contentDisposition = StringUtil.replace(
					contentDisposition, "attachment; ", "inline; ");
			}

			res.setProperty(
				HttpHeaders.CONTENT_DISPOSITION, contentDisposition);
		}
	}

	private static Log _log = LogFactory.getLog(PortletResponseUtil.class);

}