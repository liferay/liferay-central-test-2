/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.util.HttpHeaders;
import com.liferay.util.Validator;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ServletResponseUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ServletResponseUtil {

	public static final String DEFAULT_CONTENT_TYPE =
		"application/octet-stream";

	public static void sendFile(
			HttpServletResponse res, String fileName, byte[] byteArray)
		throws IOException {

		sendFile(res, fileName, byteArray, DEFAULT_CONTENT_TYPE);
	}

	public static void sendFile(
			HttpServletResponse res, String fileName, byte[] byteArray,
			String contentType)
		throws IOException {

		if (_log.isDebugEnabled()) {
			_log.debug("Sending file of type " + contentType);
		}

		res.setContentType(contentType);

		res.setHeader(HttpHeaders.CACHE_CONTROL, HttpHeaders.PUBLIC);
		res.setHeader(HttpHeaders.PRAGMA, HttpHeaders.PUBLIC);

		if (Validator.isNotNull(fileName)) {
			res.setHeader(
				HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + fileName + "\"");
		}

		ServletOutputStream sos = null;

		try {
			sos = res.getOutputStream();

			sos.write(byteArray);
		}
		finally {
			try {
				sos.flush();
			}
			catch (Exception e) {
				_log.warn(e);
			}

			try {
				sos.close();
			}
			catch (Exception e) {
				_log.warn(e);
			}
		}
	}

	public static void sendFile(
			HttpServletResponse res, String fileName, InputStream is)
		throws IOException {

		sendFile(res, fileName, is, DEFAULT_CONTENT_TYPE);
	}

	public static void sendFile(
			HttpServletResponse res, String fileName, InputStream is,
			String contentType)
		throws IOException {

		if (_log.isDebugEnabled()) {
			_log.debug("Sending file of type " + contentType);
		}

		res.setContentType(contentType);

		res.setHeader(HttpHeaders.CACHE_CONTROL, HttpHeaders.PUBLIC);
		res.setHeader(HttpHeaders.PRAGMA, HttpHeaders.PUBLIC);

		if (Validator.isNotNull(fileName)) {
			res.setHeader(
				HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + fileName + "\"");
		}

		ServletOutputStream sos = null;

		try {
			sos = res.getOutputStream();

			int c = is.read();

			while (c != -1) {
				sos.write(c);

				c = is.read();
			}
		}
		finally {
			try {
				is.close();
			}
			catch (Exception e) {
				_log.warn(e);
			}

			try {
				sos.flush();
			}
			catch (Exception e) {
				_log.warn(e);
			}

			try {
				sos.close();
			}
			catch (Exception e) {
				_log.warn(e);
			}
		}
	}

	private static Log _log = LogFactory.getLog(ServletResponseUtil.class);

}