/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.secure;

import static org.mockito.Mockito.when;

import static org.powermock.api.mockito.PowerMockito.mockStatic;

import com.liferay.portal.util.PropsUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Mariano Alvaro Saiz
 */
@PrepareForTest(PropsUtil.class)
@RunWith(PowerMockRunner.class)
public class SecureFilterTest {

	@Test
	public void testSecureFilterIsEnabledIfDisabled() {
		mockStatic(PropsUtil.class);

		when(
			PropsUtil.get(SecureFilter.class.getName())
		).thenReturn(
			"false"
		);

		SecureFilter secureFilter = new SecureFilter();

		Assert.assertTrue(secureFilter.isFilterEnabled());
	}

	@Test
	public void testSecureFilterIsEnabledIfEnabled() {
		mockStatic(PropsUtil.class);

		when(
			PropsUtil.get(SecureFilter.class.getName())
		).thenReturn(
			"true"
		);

		SecureFilter secureFilter = new SecureFilter();

		Assert.assertTrue(secureFilter.isFilterEnabled());
	}

}