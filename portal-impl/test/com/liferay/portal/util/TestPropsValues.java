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

package com.liferay.portal.util;

import java.util.List;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class TestPropsValues {

	public static final long COMPANY_ID;

	public static final String COMPANY_WEB_ID;

	public static final long LAYOUT_PLID;

	public static final String PORTAL_URL = TestPropsUtil.get("portal.url");

	public static final long USER_ID;

	public static final String USER_PASSWORD =
		TestPropsUtil.get("user.password");

	private static Log _log = LogFactoryUtil.getLog(TestPropsValues.class);

	static {
		String webId = TestPropsUtil.get("company.web.id");
		long companyId = GetterUtil.getLong(TestPropsUtil.get("company.id"));
		long layoutPlid = GetterUtil.getLong(TestPropsUtil.get("layout.plid"));
		long userId = GetterUtil.getLong(TestPropsUtil.get("user.id"));

		try {
			if (Validator.isNull(webId)) {
				webId = PropsValues.COMPANY_DEFAULT_WEB_ID;

				TestPropsUtil.set("company.web.id", webId);
			}

			if (companyId == 0) {
				Company company = CompanyLocalServiceUtil.getCompanyByWebId(
					webId);

				companyId = company.getCompanyId();

				TestPropsUtil.set("company.id", String.valueOf(companyId));
			}

			if (layoutPlid == 0) {
				Group group = GroupLocalServiceUtil.getGroup(
					companyId, GroupConstants.GUEST);

				layoutPlid = LayoutLocalServiceUtil.getDefaultPlid(
					group.getGroupId());

				TestPropsUtil.set("layout.plid", String.valueOf(layoutPlid));
			}

			if (userId == 0) {
				List<User> users = UserLocalServiceUtil.getCompanyUsers(
					companyId, 0, 2);

				userId = users.get(1).getUserId();

				TestPropsUtil.set("user.id", String.valueOf(userId));
			}
		}
		catch (Exception e) {
			_log.fatal("Error initializing test properties", e);
		}

		TestPropsUtil.printProperties();

		COMPANY_WEB_ID = webId;
		COMPANY_ID = companyId;
		LAYOUT_PLID = layoutPlid;
		USER_ID = userId;
	}

}