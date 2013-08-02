/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.json;

import com.liferay.portal.kernel.json.JSONFactoryUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Igor Spasic
 */
public class JSONIncludesManagerTest {

	@Before
	public void setUp() throws Exception {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testExtendsOne() {
		JSONIncludesManager jsonIncludesManager = new JSONIncludesManager();

		String[] excludes = jsonIncludesManager.lookupExcludes(
			ExtendsOne.class);

		Assert.assertEquals(1, excludes.length);
		Assert.assertEquals("*", excludes[0]);

		String[] includes = jsonIncludesManager.lookupIncludes(
			ExtendsOne.class);

		Assert.assertEquals(1, includes.length);
		Assert.assertEquals("ftwo", includes[0]);
	}

	@Test
	public void testExtendsTwo() {
		JSONIncludesManager jsonIncludesManager = new JSONIncludesManager();

		String[] excludes = jsonIncludesManager.lookupExcludes(
			ExtendsTwo.class);

		Assert.assertEquals(1, excludes.length);
		Assert.assertEquals("*", excludes[0]);

		String[] includes = jsonIncludesManager.lookupIncludes(
			ExtendsTwo.class);

		Assert.assertEquals(1, includes.length);
		Assert.assertEquals("ftwo", includes[0]);
	}

	@Test
	public void testOne() {
		JSONIncludesManager jsonIncludesManager = new JSONIncludesManager();

		String[] excludes = jsonIncludesManager.lookupExcludes(One.class);

		Assert.assertEquals(1, excludes.length);
		Assert.assertEquals("not", excludes[0]);

		String[] includes = jsonIncludesManager.lookupIncludes(One.class);

		Assert.assertEquals(1, includes.length);
		Assert.assertEquals("ftwo", includes[0]);
	}

	@Test
	public void testThree() {
		JSONIncludesManager jsonIncludesManager = new JSONIncludesManager();

		String[] excludes = jsonIncludesManager.lookupExcludes(Three.class);

		Assert.assertEquals(1, excludes.length);
		Assert.assertEquals("ignore", excludes[0]);

		String[] includes = jsonIncludesManager.lookupIncludes(Three.class);

		Assert.assertEquals(0, includes.length);
	}

	@Test
	public void testTwo() {
		JSONIncludesManager jsonIncludesManager = new JSONIncludesManager();

		String[] excludes = jsonIncludesManager.lookupExcludes(Two.class);

		Assert.assertEquals(1, excludes.length);
		Assert.assertEquals("*", excludes[0]);

		String[] includes = jsonIncludesManager.lookupIncludes(Two.class);

		Assert.assertEquals(1, includes.length);
		Assert.assertEquals("ftwo", includes[0]);
	}

}