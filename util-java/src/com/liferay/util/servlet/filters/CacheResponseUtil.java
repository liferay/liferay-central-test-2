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

package com.liferay.util.servlet.filters;

import com.liferay.util.servlet.Header;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.IOException;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * <a href="CacheResponseUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class CacheResponseUtil {

	public static void addHeaders(
		HttpServletResponse response, Map<String, List<Header>> headers) {

		for (Map.Entry<String, List<Header>> entry : headers.entrySet()) {
			String headerKey = entry.getKey();
			List<Header> headerValues = entry.getValue();

			for (Header header : headerValues) {
				int type = header.getType();

				if (type == Header.DATE_TYPE) {
					response.addDateHeader(headerKey, header.getDateValue());
				}
				else if (type == Header.INTEGER_TYPE) {
					response.addIntHeader(headerKey, header.getIntValue());
				}
				else if (type == Header.STRING_TYPE) {
					response.addHeader(headerKey, header.getStringValue());
				}
			}
		}
	}

	public static void write(
			HttpServletResponse response, CacheResponseData cacheResponseData)
		throws IOException {

		addHeaders(response, cacheResponseData.getHeaders());

		response.setContentType(cacheResponseData.getContentType());

		ServletResponseUtil.write(
			response, cacheResponseData.getContent(),
			cacheResponseData.getContentLength());
	}

}