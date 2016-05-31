/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.target.platform.indexer.main;

import com.liferay.portal.kernel.util.ReflectionUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shuyang Zhou
 */
public class BytesURLSupport {

	public static void init() {
		URL.setURLStreamHandlerFactory(
			new URLStreamHandlerFactory() {

				@Override
				public URLStreamHandler createURLStreamHandler(
					String protocol) {

					if (!protocol.equals("bytes")) {
						return null;
					}

					return new URLStreamHandler() {

						@Override
						protected URLConnection openConnection(URL url) {
							return new URLConnection(url) {

								@Override
								public void connect() {
								}

								@Override
								public InputStream getInputStream()
									throws IOException {

									byte[] bytes = _dataMap.get(url);

									if (bytes == null) {
										throw new IOException(
											"Unable to find data for " + url);
									}

									return new ByteArrayInputStream(bytes);
								}

							};
						}

					};
				}

			});
	}

	public static URL putData(String id, byte[] data) {
		try {
			URL url = new URL("bytes://localhost/".concat(id));

			_dataMap.put(url, data);

			return url;
		}
		catch (MalformedURLException murle) {
			return ReflectionUtil.throwException(murle);
		}
	}

	public static byte[] removeBytes(URL url) {
		return _dataMap.remove(url);
	}

	private static final Map<URL, byte[]> _dataMap = new ConcurrentHashMap<>();

}