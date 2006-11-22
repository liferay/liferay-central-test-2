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

package com.liferay.portal.service.spring;

/**
 * <a href="CompanyLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class CompanyLocalServiceUtil {
	public static void checkCompany(java.lang.String companyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		CompanyLocalService companyLocalService = CompanyLocalServiceFactory.getService();
		companyLocalService.checkCompany(companyId);
	}

	public static void checkCompanyKey(java.lang.String companyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		CompanyLocalService companyLocalService = CompanyLocalServiceFactory.getService();
		companyLocalService.checkCompanyKey(companyId);
	}

	public static java.util.List getCompanies()
		throws com.liferay.portal.SystemException {
		CompanyLocalService companyLocalService = CompanyLocalServiceFactory.getService();

		return companyLocalService.getCompanies();
	}

	public static com.liferay.portal.model.Company getCompany(
		java.lang.String companyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		CompanyLocalService companyLocalService = CompanyLocalServiceFactory.getService();

		return companyLocalService.getCompany(companyId);
	}

	public static com.liferay.util.lucene.Hits search(
		java.lang.String companyId, java.lang.String keywords)
		throws com.liferay.portal.SystemException {
		CompanyLocalService companyLocalService = CompanyLocalServiceFactory.getService();

		return companyLocalService.search(companyId, keywords);
	}

	public static com.liferay.util.lucene.Hits search(
		java.lang.String companyId, java.lang.String portletId,
		java.lang.String groupId, java.lang.String type,
		java.lang.String keywords) throws com.liferay.portal.SystemException {
		CompanyLocalService companyLocalService = CompanyLocalServiceFactory.getService();

		return companyLocalService.search(companyId, portletId, groupId, type,
			keywords);
	}

	public static com.liferay.portal.model.Company updateCompany(
		java.lang.String companyId, java.lang.String portalURL,
		java.lang.String homeURL, java.lang.String mx, java.lang.String name,
		java.lang.String legalName, java.lang.String legalId,
		java.lang.String legalType, java.lang.String sicCode,
		java.lang.String tickerSymbol, java.lang.String industry,
		java.lang.String type, java.lang.String size)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		CompanyLocalService companyLocalService = CompanyLocalServiceFactory.getService();

		return companyLocalService.updateCompany(companyId, portalURL, homeURL,
			mx, name, legalName, legalId, legalType, sicCode, tickerSymbol,
			industry, type, size);
	}

	public static void updateDisplay(java.lang.String companyId,
		java.lang.String languageId, java.lang.String timeZoneId,
		java.lang.String resolution)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		CompanyLocalService companyLocalService = CompanyLocalServiceFactory.getService();
		companyLocalService.updateDisplay(companyId, languageId, timeZoneId,
			resolution);
	}

	public static void updateLogo(java.lang.String companyId, java.io.File file)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		CompanyLocalService companyLocalService = CompanyLocalServiceFactory.getService();
		companyLocalService.updateLogo(companyId, file);
	}

	public static void updateSecurity(java.lang.String companyId,
		java.lang.String authType, boolean autoLogin, boolean sendPassword,
		boolean strangers)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		CompanyLocalService companyLocalService = CompanyLocalServiceFactory.getService();
		companyLocalService.updateSecurity(companyId, authType, autoLogin,
			sendPassword, strangers);
	}
}