/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.impl.RoleImpl;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.CompanyService;
import com.liferay.portal.service.RoleLocalServiceUtil;

import java.io.File;

/**
 * <a href="CompanyServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class CompanyServiceImpl
	extends PrincipalBean implements CompanyService {

	public Company updateCompany(
			String companyId, String portalURL, String homeURL, String mx,
			String name, String legalName, String legalId, String legalType,
			String sicCode, String tickerSymbol, String industry, String type,
			String size)
		throws PortalException, SystemException {

		if (!RoleLocalServiceUtil.hasUserRole(
				getUserId(), companyId, RoleImpl.ADMINISTRATOR)) {

			throw new PrincipalException();
		}

		return CompanyLocalServiceUtil.updateCompany(
			companyId, portalURL, homeURL, mx, name, legalName, legalId,
			legalType, sicCode, tickerSymbol, industry, type, size);
	}

	public void updateDisplay(
			String companyId, String languageId, String timeZoneId,
			String resolution)
		throws PortalException, SystemException {

		if (!RoleLocalServiceUtil.hasUserRole(
				getUserId(), companyId, RoleImpl.ADMINISTRATOR)) {

			throw new PrincipalException();
		}

		CompanyLocalServiceUtil.updateDisplay(
			companyId, languageId, timeZoneId, resolution);
	}

	public void updateLogo(String companyId, File file)
		throws PortalException, SystemException {

		if (!RoleLocalServiceUtil.hasUserRole(
				getUserId(), companyId, RoleImpl.ADMINISTRATOR)) {

			throw new PrincipalException();
		}

		CompanyLocalServiceUtil.updateLogo(companyId, file);
	}

	public void updateSecurity(
			String companyId, String authType, boolean autoLogin,
			boolean sendPassword, boolean strangers)
		throws PortalException, SystemException {

		if (!RoleLocalServiceUtil.hasUserRole(
				getUserId(), companyId, RoleImpl.ADMINISTRATOR)) {

			throw new PrincipalException();
		}

		CompanyLocalServiceUtil.updateSecurity(
			companyId, authType, autoLogin, sendPassword, strangers);
	}

}