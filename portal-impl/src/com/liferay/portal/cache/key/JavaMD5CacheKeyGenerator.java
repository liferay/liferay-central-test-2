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

package com.liferay.portal.cache.key;

import com.liferay.portal.kernel.cache.key.CacheKeyGenerator;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.security.MessageDigest;

/**
 * <a href="JavaMD5CacheKeyGenerator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class JavaMD5CacheKeyGenerator implements CacheKeyGenerator {

	public String getCacheKey(String key) {
		try {
			return doGetCacheKey(key);
		}
		catch (Exception e) {
			_log.error(e, e);

			return key;
		}
	}

	public void setMaxLength(int maxLength) {
		_maxLength = maxLength;
	}

	protected String doGetCacheKey(String key) throws Exception {
		if ((_maxLength > -1) && (key.length() < _maxLength)) {
			return key;
		}

		MessageDigest messageDigest = MessageDigest.getInstance(_ALGORITHM_MD5);

		messageDigest.update(key.getBytes());

		byte[] bytes = messageDigest.digest();

		StringBundler sb = new StringBundler(2 * bytes.length);

		for (int i = 0; i < bytes.length; i++) {
			int value = bytes[i] & 0xff;

			sb.append((char)_HEX_CHARACTERS[value >> 4]);
			sb.append((char)_HEX_CHARACTERS[value & 0xf]);
		}

		return sb.toString();
	}

	private static final String _ALGORITHM_MD5 = "MD5";

	private static final byte[] _HEX_CHARACTERS = {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
		'e', 'f'
	};

	private static Log _log = LogFactoryUtil.getLog(
		JavaMD5CacheKeyGenerator.class);

	private int _maxLength = -1;

}