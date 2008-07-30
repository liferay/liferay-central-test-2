/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;


/**
 * <a href="CompanyLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portal.service.CompanyLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.CompanyLocalService
 *
 */
public class CompanyLocalServiceUtil {
	public static com.liferay.portal.model.Company addCompany(
		com.liferay.portal.model.Company company)
		throws com.liferay.portal.SystemException {
		return _service.addCompany(company);
	}

	public static void deleteCompany(long companyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.deleteCompany(companyId);
	}

	public static void deleteCompany(com.liferay.portal.model.Company company)
		throws com.liferay.portal.SystemException {
		_service.deleteCompany(company);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _service.dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _service.dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.Company getCompany(long companyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getCompany(companyId);
	}

	public static java.util.List<com.liferay.portal.model.Company> getCompanies(
		int start, int end) throws com.liferay.portal.SystemException {
		return _service.getCompanies(start, end);
	}

	public static int getCompaniesCount()
		throws com.liferay.portal.SystemException {
		return _service.getCompaniesCount();
	}

	public static com.liferay.portal.model.Company updateCompany(
		com.liferay.portal.model.Company company)
		throws com.liferay.portal.SystemException {
		return _service.updateCompany(company);
	}

	public static com.liferay.portal.model.Company addCompany(
		java.lang.String webId, java.lang.String virtualHost,
		java.lang.String mx)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.addCompany(webId, virtualHost, mx);
	}

	public static com.liferay.portal.model.Company checkCompany(
		java.lang.String webId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.checkCompany(webId);
	}

	public static com.liferay.portal.model.Company checkCompany(
		java.lang.String webId, java.lang.String mx)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.checkCompany(webId, mx);
	}

	public static void checkCompanyKey(long companyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.checkCompanyKey(companyId);
	}

	public static java.util.List<com.liferay.portal.model.Company> getCompanies()
		throws com.liferay.portal.SystemException {
		return _service.getCompanies();
	}

	public static com.liferay.portal.model.Company getCompanyById(
		long companyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getCompanyById(companyId);
	}

	public static com.liferay.portal.model.Company getCompanyByLogoId(
		long logoId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getCompanyByLogoId(logoId);
	}

	public static com.liferay.portal.model.Company getCompanyByMx(
		java.lang.String mx)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getCompanyByMx(mx);
	}

	public static com.liferay.portal.model.Company getCompanyByVirtualHost(
		java.lang.String virtualHost)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getCompanyByVirtualHost(virtualHost);
	}

	public static com.liferay.portal.model.Company getCompanyByWebId(
		java.lang.String webId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getCompanyByWebId(webId);
	}

	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		java.lang.String keywords, int start, int end)
		throws com.liferay.portal.SystemException {
		return _service.search(companyId, keywords, start, end);
	}

	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		java.lang.String portletId, long groupId, java.lang.String type,
		java.lang.String keywords, int start, int end)
		throws com.liferay.portal.SystemException {
		return _service.search(companyId, portletId, groupId, type, keywords,
			start, end);
	}

	public static com.liferay.portal.model.Company updateCompany(
		long companyId, java.lang.String virtualHost, java.lang.String mx)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.updateCompany(companyId, virtualHost, mx);
	}

	public static com.liferay.portal.model.Company updateCompany(
		long companyId, java.lang.String virtualHost, java.lang.String mx,
		java.lang.String name, java.lang.String legalName,
		java.lang.String legalId, java.lang.String legalType,
		java.lang.String sicCode, java.lang.String tickerSymbol,
		java.lang.String industry, java.lang.String type, java.lang.String size)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.updateCompany(companyId, virtualHost, mx, name,
			legalName, legalId, legalType, sicCode, tickerSymbol, industry,
			type, size);
	}

	public static void updateDisplay(long companyId,
		java.lang.String languageId, java.lang.String timeZoneId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.updateDisplay(companyId, languageId, timeZoneId);
	}

	public static void updateLogo(long companyId, java.io.File file)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.updateLogo(companyId, file);
	}

	public static void updateSecurity(long companyId,
		java.lang.String authType, boolean autoLogin, boolean sendPassword,
		boolean strangers, boolean strangersWithMx, boolean strangersVerify,
		boolean communityLogo)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.updateSecurity(companyId, authType, autoLogin, sendPassword,
			strangers, strangersWithMx, strangersVerify, communityLogo);
	}

	public static CompanyLocalService getService() {
		return _service;
	}

	public void setService(CompanyLocalService service) {
		_service = service;
	}

	private static CompanyLocalService _service;
}