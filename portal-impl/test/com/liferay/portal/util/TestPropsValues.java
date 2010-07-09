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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class TestPropsValues {

	public static final long COMPANY_ID = GetterUtil.getLong(
		TestPropsUtil.get("company.id"));

	public static final String COMPANY_WEB_ID =
		TestPropsUtil.get("company.web.id");

	public static final long LAYOUT_PLID = GetterUtil.getLong(
		TestPropsUtil.get("layout.plid"));

	public static final String PORTAL_URL = TestPropsUtil.get("portal.url");

	public static final long USER_ID = GetterUtil.getLong(
		TestPropsUtil.get("user.id"));

	public static final String USER_PASSWORD =
		TestPropsUtil.get("user.password");

}