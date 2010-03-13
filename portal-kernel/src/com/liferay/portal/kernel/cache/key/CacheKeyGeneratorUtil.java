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

package com.liferay.portal.kernel.cache.key;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="CacheKeyGeneratorUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class CacheKeyGeneratorUtil {

	public static String getCacheKey(String key, String cacheName) {
		CacheKeyGenerator cacheKeyGenerator = _cacheKeyGenerators.get(
			cacheName);

		if (cacheKeyGenerator == null) {
			cacheKeyGenerator = _defaultCacheKeyGenerator;
		}

		if (cacheKeyGenerator != null) {
			return cacheKeyGenerator.getCacheKey(key);
		}

		return key;
	}

	public void setCacheKeyGenerators(
		Map<String, CacheKeyGenerator> cacheKeyGenerators) {

		_cacheKeyGenerators = cacheKeyGenerators;
	}

	public void setDefaultCacheKeyGenerator(
		CacheKeyGenerator defaultCacheKeyGenerator) {

		_defaultCacheKeyGenerator = defaultCacheKeyGenerator;
	}

	private static Map<String, CacheKeyGenerator> _cacheKeyGenerators =
		new HashMap<String, CacheKeyGenerator>();
	private static CacheKeyGenerator _defaultCacheKeyGenerator;

}