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

package com.liferay.portal.util.test;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Manuel de la Peña
 */
public class ThemeDisplayTestUtil {

	public static ThemeDisplay getThemeDisplay()
		throws PortalException, SystemException {

		return getThemeDisplay(TestPropsValues.getCompanyId());
	}

	public static ThemeDisplay getThemeDisplay(long companyId)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		Company company = CompanyLocalServiceUtil.getCompany(companyId);

		themeDisplay.setCompany(company);

		long plid = TestPropsValues.getPlid();

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		themeDisplay.setLayout(layout);

		themeDisplay.setPlid(plid);

		HttpServletRequest request = new MockHttpServletRequest();

		request.setAttribute(WebKeys.COMPANY_ID, companyId);
		request.setAttribute(
			WebKeys.CURRENT_URL, "http://localhost:80/web/guest/home");
		request.setAttribute(WebKeys.USER, TestPropsValues.getUser());

		themeDisplay.setRequest(request);

		themeDisplay.setScopeGroupId(layout.getGroupId());
		themeDisplay.setSecure(false);
		themeDisplay.setServerPort(8080);
		themeDisplay.setSiteGroupId(layout.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());
		themeDisplay.setWidget(false);

		request.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		return themeDisplay;
	}

}