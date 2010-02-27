/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.util.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.portlet.MimeResponse;
import javax.portlet.ResourceResponse;

import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang.CharUtils;

/**
 * <a href="PortletResponseUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortletResponseUtil {

	public static void sendFile(
			MimeResponse mimeResponse, String fileName, byte[] bytes)
		throws IOException {

		sendFile(mimeResponse, fileName, bytes, null);
	}

	public static void sendFile(
			MimeResponse mimeResponse, String fileName, byte[] bytes,
			String contentType)
		throws IOException {

		setHeaders(mimeResponse, fileName, contentType);

		write(mimeResponse, bytes);
	}

	public static void sendFile(
			MimeResponse mimeResponse, String fileName, InputStream is)
		throws IOException {

		sendFile(mimeResponse, fileName, is, null);
	}

	public static void sendFile(
			MimeResponse mimeResponse, String fileName, InputStream is,
			String contentType)
		throws IOException {

		sendFile(mimeResponse, fileName, is, 0, contentType);
	}

	public static void sendFile(
			MimeResponse mimeResponse, String fileName, InputStream is,
			int contentLength, String contentType)
		throws IOException {

		setHeaders(mimeResponse, fileName, contentType);

		write(mimeResponse, is, contentLength);
	}

	public static void write(MimeResponse mimeResponse, String s)
		throws IOException {

		write(mimeResponse, s.getBytes(StringPool.UTF8));
	}

	public static void write(MimeResponse mimeResponse, byte[] bytes)
		throws IOException {

		write(mimeResponse, bytes, 0);
	}

	public static void write(
			MimeResponse mimeResponse, byte[] bytes, int contentLength)
		throws IOException {

		// LEP-3122

		if (!mimeResponse.isCommitted()) {

			// LEP-536

			if (contentLength == 0) {
				contentLength = bytes.length;
			}

			if (mimeResponse instanceof ResourceResponse) {
				ResourceResponse resourceResponse =
					(ResourceResponse)mimeResponse;

				resourceResponse.setContentLength(contentLength);
			}

			OutputStream outputStream = mimeResponse.getPortletOutputStream();

			outputStream.write(bytes, 0, contentLength);
		}
	}

	public static void write(MimeResponse mimeResponse, InputStream is)
		throws IOException {

		write(mimeResponse, is, 0);
	}

	public static void write(
			MimeResponse mimeResponse, InputStream is, int contentLength)
		throws IOException {

		if (mimeResponse.isCommitted()) {
			return;
		}

		if (contentLength > 0) {
			if (mimeResponse instanceof ResourceResponse) {
				ResourceResponse resourceResponse =
					(ResourceResponse)mimeResponse;

				resourceResponse.setContentLength(contentLength);
			}
		}

		StreamUtil.transfer(is, mimeResponse.getPortletOutputStream());
	}

	protected static void setHeaders(
		MimeResponse mimeResponse, String fileName, String contentType) {

		if (_log.isDebugEnabled()) {
			_log.debug("Sending file of type " + contentType);
		}

		// LEP-2201

		if (Validator.isNotNull(contentType)) {
			mimeResponse.setContentType(contentType);
		}

		mimeResponse.setProperty(
			HttpHeaders.CACHE_CONTROL, HttpHeaders.CACHE_CONTROL_PUBLIC_VALUE);
		mimeResponse.setProperty(
			HttpHeaders.PRAGMA, HttpHeaders.PRAGMA_PUBLIC_VALUE);

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
				FileUtil.getExtension(fileName)).toLowerCase();

			String[] mimeTypesContentDispositionInline = null;

			try {
				mimeTypesContentDispositionInline = PropsUtil.getArray(
					"mime.types.content.disposition.inline");
			}
			catch (Exception e) {
				mimeTypesContentDispositionInline = new String[0];
			}

			if (ArrayUtil.contains(
					mimeTypesContentDispositionInline, extension)) {

				contentDisposition = StringUtil.replace(
					contentDisposition, "attachment; ", "inline; ");
			}

			mimeResponse.setProperty(
				HttpHeaders.CONTENT_DISPOSITION, contentDisposition);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PortletResponseUtil.class);

}