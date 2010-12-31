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

package com.liferay.portal.kernel.test;

import com.liferay.portal.kernel.util.MapUtil;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class TestCase extends junit.framework.TestCase {

	protected void assertEquals(double expected, double actual)
		throws Exception {

		assertEquals(expected, actual, 0);
	}

	protected void assertEquals(
		Map<String, ?> expected, Map<String, ?> actual) {

		assertEquals(
			"The maps are different sizes", expected.size(), actual.size());

		for (String name : expected.keySet()) {
			assertEquals(
				"The values for key '" + name + "' are different",
				MapUtil.getString(expected, name),
				MapUtil.getString(actual, name));
		}
	}

	protected void assertLessThan(double expected, double actual)
		throws Exception {

		if (actual > expected) {
			fail(actual + " is not less than " + expected);
		}
	}

}