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

package com.liferay.portal.cache.key;

import com.liferay.portal.kernel.cache.key.CacheKeyGenerator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public class HashCodeCacheKeyGenerator extends BaseCacheKeyGenerator {

	public CacheKeyGenerator clone() {
		return new HashCodeCacheKeyGenerator();
	}

	public String getCacheKey(String key) {
		return StringUtil.toHexString(key.hashCode());
	}

	public String getCacheKey(String[] keys) {
		int hashCode = 0;
		int weight = 1;

		for (int i = keys.length - 1; i >= 0; i--) {
			String s = keys[i];

			hashCode = s.hashCode() * weight + hashCode;

			for (int j = 0; j < s.length(); j++) {
				weight *= 31;
			}
		}

		return StringUtil.toHexString(hashCode);
	}

	public String getCacheKey(StringBundler sb) {
		int hashCode = 0;
		int weight = 1;

		for (int i = sb.index() - 1; i >= 0; i--) {
			String s = sb.stringAt(i);

			hashCode = s.hashCode() * weight + hashCode;

			for (int j = 0; j < s.length(); j++) {
				weight *= 31;
			}
		}

		return StringUtil.toHexString(hashCode);
	}

}