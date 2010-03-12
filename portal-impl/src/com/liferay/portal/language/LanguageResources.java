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

package com.liferay.portal.language;

import com.liferay.portal.kernel.util.StringPool;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.struts.util.MessageResources;

/**
 * <a href="LanguageResources.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class LanguageResources {

	public static void clearCache() {
		_instance._clearCache();
	}

	public static String getMessage(Locale locale, String key) {
		return _instance._getMessage(locale, key);
	}

	public static void init(MessageResources messageResources) {
		_instance._init(messageResources);
	}

	public static boolean isInitialized() {
		if (_instance._messageResources == null) {
			return true;
		}
		else {
			return false;
		}
	}

	private LanguageResources() {
		_cache = new ConcurrentHashMap<String, String>(10000);
	}

	private void _clearCache() {
		_cache.clear();
	}

	private String _getCacheKey(Locale locale, String key) {
		return String.valueOf(locale).concat(StringPool.POUND).concat(key);
	}

	private String _getMessage(Locale locale, String key) {
		String cacheKey = _getCacheKey(locale, key);

		String cacheValue = _cache.get(cacheKey);

		if (cacheValue == null) {
			cacheValue = _messageResources.getMessage(locale, key);

			if (cacheValue == null) {
				return null;
			}
			_cache.put(cacheKey, cacheValue);
		}

		return cacheValue;
	}

	private void _init(MessageResources messageResources) {
		_messageResources = messageResources;
	}

	private static LanguageResources _instance = new LanguageResources();

	private Map<String, String> _cache;
	private MessageResources _messageResources;

}