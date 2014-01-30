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

package com.liferay.portal.license.util;

import java.util.Set;

import org.junit.Assert;

/**
 * @author Manuel de la Peña
 */
public class LicenseUtilLinuxTest extends BaseLicenseUtilTestCase {

	@Override
	protected String getDependenciesFileName() {
		return "ubuntu";
	}

	@Override
	protected void testMacAddresses(Set<String> macAddresses) {
		Assert.assertEquals(macAddresses.size(), 2);
		Assert.assertTrue(macAddresses.contains("5c:26:0a:33:b3:d5"));
		Assert.assertTrue(macAddresses.contains("00:24:d7:82:96:f4"));
	}

}