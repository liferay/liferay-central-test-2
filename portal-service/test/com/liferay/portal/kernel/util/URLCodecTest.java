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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.test.TestCase;

import java.net.URLEncoder;

/**
 * @author Shuyang Zhou
 */
public class URLCodecTest extends TestCase {

	public void testDecodeURL() throws Exception {
		for(int i = 0; i < _rawURLStrings.length; i++) {
			String result = URLCodec.decodeURL(
				_encodedURLStrings[i], StringPool.UTF8);
			assertEquals(_rawURLStrings[i], result);
			result = URLCodec.decodeURL(
				_escapeSpacesEncodeURLStrings[i], StringPool.UTF8);
			assertEquals(_rawURLStrings[i], result);
		}
	}

	public void testEncodeURL() throws Exception {
		for(int i = 0; i < _rawURLStrings.length; i++) {
			String result = URLCodec.encodeURL(
				_rawURLStrings[i], StringPool.UTF8, false);
			assertTrue(_encodedURLStrings[i].equalsIgnoreCase(result));
			result = URLCodec.encodeURL(
				_rawURLStrings[i], StringPool.UTF8, true);
			assertTrue(
				_escapeSpacesEncodeURLStrings[i].equalsIgnoreCase(result));
		}
	}

	private static final String[] _rawURLStrings = {
		"abcdefghijklmnopqrstuvwxyz",
		"ABCDEFGHIJKLMNOPQRSTUVWXYZ",
		"0123456789",
		".-*_",
		" ",
		"~`!@#$%^&()+={[}]|\\:;\"'<,>?/",
		"中文测试",
		"/abc/def"
	};

	private static final String[] _encodedURLStrings =
		new String[_rawURLStrings.length];

	private static final String[] _escapeSpacesEncodeURLStrings =
		new String[_rawURLStrings.length];

	static {
		try {
			for(int i = 0; i < _rawURLStrings.length; i++) {
				_encodedURLStrings[i] = URLEncoder.encode(
					_rawURLStrings[i], StringPool.UTF8);
				_escapeSpacesEncodeURLStrings[i] = StringUtil.replace(
					_encodedURLStrings[i], StringPool.PLUS, "%20");
			}
		}
		catch(Throwable t) {
			throw new ExceptionInInitializerError(t);
		}
	}

}