
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

import edu.emory.mathcs.backport.java.util.Arrays;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

/**
 * @author Shuyang Zhou
 */
public class BaseCacheKeyGeneratorTest extends TestCase {

	public void testConsistency() {
		StringBundler sb = new StringBundler(_KEYS);

		String combinedKey = sb.toString();

		String hashCode1 = cacheKeyGenerator.getCacheKey(combinedKey);

		String hashCode2 = cacheKeyGenerator.getCacheKey(_KEYS);

		String hashCode3 = cacheKeyGenerator.getCacheKey(sb);

		assertEquals(hashCode1, hashCode2);
		assertEquals(hashCode2, hashCode3);
	}

	public void testScan() {
		Map<String, String> checkMap = new HashMap<String, String>();

		for (int i = 0; i < 1000000; i++) {
			String value = String.valueOf(i);

			String key =  cacheKeyGenerator.getCacheKey(value);

			String oldValue = checkMap.put(key, value);

			if (oldValue != null) {
				fail("Value {" + oldValue + "}, {" + value + "} generate same "
					+ "key : {" + key + "}");
			}
		}
	}

	public void testSpecialCases() {
		Map<String, String> checkMap = new HashMap<String, String>();

		for (String[] values : _SPECIAL_CASES) {

			String key =  cacheKeyGenerator.getCacheKey(values);

			String oldValue = checkMap.put(key, Arrays.toString(values));

			if (oldValue != null) {
				fail("Value {" + oldValue + "}, {" + Arrays.toString(values) +
					"} generate same " + "key : {" + key + "}");
			}
		}
	}

	private static String[] _KEYS = {"test1", "test2", "test3", "test4"};

	private static final String[][] _SPECIAL_CASES = {
		{"fetchByT_C_C_P_.java.lang.Long.java.lang.Long.java.lang.Long_A_", ".",
			"10302", ".", "10303", ".", "13710"},
		{"fetchByT_C_C_P_.java.lang.Long.java.lang.Long.java.lang.Long_A_", ".",
			"10302", ".", "10305", ".", "13510"}
	};

	protected CacheKeyGenerator cacheKeyGenerator;

}