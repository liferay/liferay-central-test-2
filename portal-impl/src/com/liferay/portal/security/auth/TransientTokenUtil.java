/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Shuyang Zhou
 */
public class TransientTokenUtil {

	public static String createToken(long timeTolive) {
		long currentTime = System.currentTimeMillis();
		long expireTime = currentTime + timeTolive;

		expungeExpiredToken(currentTime);

		Token token = new Token(expireTime);

		_tokenSortedMap.put(expireTime, token);

		return token.getTokenString();
	}

	public static boolean checkToken(String tokenString) {
		long currentTime = System.currentTimeMillis();

		expungeExpiredToken(currentTime);

		Iterator<Map.Entry<Long,Token>> iterator =
			_tokenSortedMap.entrySet().iterator();

		while (iterator.hasNext()) {
			Map.Entry<Long,Token> entry = iterator.next();
			if (entry.getValue().getTokenString().equals(tokenString)) {
				iterator.remove();
				return true;
			}
		}

		return false;
	}

	public static void clearAll() {
		_tokenSortedMap.clear();
	}

	private static void expungeExpiredToken(long currentTime) {
		_tokenSortedMap.headMap(currentTime).clear();
	}

	private static class Token {

		public Token(long expireTime) {
			_tokenString = PortalUUIDUtil.generate();
			_expireTime = expireTime;
		}

		public long getExpireTime() {
			return _expireTime;
		}

		public String getTokenString() {
			return _tokenString;
		}

		private String _tokenString;
		private long _expireTime;
	}

	private static final SortedMap<Long, Token> _tokenSortedMap =
		Collections.synchronizedSortedMap(new TreeMap<Long, Token>());

}