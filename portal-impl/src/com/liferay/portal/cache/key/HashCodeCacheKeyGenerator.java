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
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public class HashCodeCacheKeyGenerator extends BaseCacheKeyGenerator {

	public CacheKeyGenerator clone() {
		return new HashCodeCacheKeyGenerator();
	}

	public String getCacheKey(String key) {
		return String.valueOf(key.hashCode());
	}

	public String getCacheKey(String[] keys) {
		int hashCode = 0;

		for (String key : keys) {
			hashCode = 31 * hashCode + key.hashCode();
		}

		return String.valueOf(hashCode);
	}

	public String getCacheKey(StringBundler sb) {
		int hashCode = 0;

		for (int i = 0; i < sb.index(); i++) {
			hashCode = 31 * hashCode + sb.stringAt(i).hashCode();
		}

		return String.valueOf(hashCode);
	}

}