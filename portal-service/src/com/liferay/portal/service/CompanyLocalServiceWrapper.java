/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
 * This class is a wrapper for {@link CompanyLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CompanyLocalService
 * @generated
 */
public class CompanyLocalServiceWrapper implements CompanyLocalService {
	public CompanyLocalServiceWrapper(CompanyLocalService companyLocalService) {
		_companyLocalService = companyLocalService;
	}

	public com.liferay.portal.model.Company addCompany(
		com.liferay.portal.model.Company company)
		throws com.liferay.portal.SystemException {
		return _companyLocalService.addCompany(company);
	}

	public com.liferay.portal.model.Company createCompany(long companyId) {
		return _companyLocalService.createCompany(companyId);
	}

	public void deleteCompany(long companyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_companyLocalService.deleteCompany(companyId);
	}

	public void deleteCompany(com.liferay.portal.model.Company company)
		throws com.liferay.portal.SystemException {
		_companyLocalService.deleteCompany(company);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _companyLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _companyLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portal.model.Company getCompany(long companyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _companyLocalService.getCompany(companyId);
	}

	public java.util.List<com.liferay.portal.model.Company> getCompanies(
		int start, int end) throws com.liferay.portal.SystemException {
		return _companyLocalService.getCompanies(start, end);
	}

	public int getCompaniesCount() throws com.liferay.portal.SystemException {
		return _companyLocalService.getCompaniesCount();
	}

	public com.liferay.portal.model.Company updateCompany(
		com.liferay.portal.model.Company company)
		throws com.liferay.portal.SystemException {
		return _companyLocalService.updateCompany(company);
	}

	public com.liferay.portal.model.Company updateCompany(
		com.liferay.portal.model.Company company, boolean merge)
		throws com.liferay.portal.SystemException {
		return _companyLocalService.updateCompany(company, merge);
	}

	public com.liferay.portal.model.Company addCompany(java.lang.String webId,
		java.lang.String virtualHost, java.lang.String mx,
		java.lang.String shardName, boolean system)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _companyLocalService.addCompany(webId, virtualHost, mx,
			shardName, system);
	}

	public com.liferay.portal.model.Company checkCompany(java.lang.String webId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _companyLocalService.checkCompany(webId);
	}

	public com.liferay.portal.model.Company checkCompany(
		java.lang.String webId, java.lang.String mx, java.lang.String shardName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _companyLocalService.checkCompany(webId, mx, shardName);
	}

	public void checkCompanyKey(long companyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_companyLocalService.checkCompanyKey(companyId);
	}

	public void deleteLogo(long companyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_companyLocalService.deleteLogo(companyId);
	}

	public java.util.List<com.liferay.portal.model.Company> getCompanies()
		throws com.liferay.portal.SystemException {
		return _companyLocalService.getCompanies();
	}

	public java.util.List<com.liferay.portal.model.Company> getCompanies(
		boolean system) throws com.liferay.portal.SystemException {
		return _companyLocalService.getCompanies(system);
	}

	public int getCompaniesCount(boolean system)
		throws com.liferay.portal.SystemException {
		return _companyLocalService.getCompaniesCount(system);
	}

	public com.liferay.portal.model.Company getCompanyById(long companyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _companyLocalService.getCompanyById(companyId);
	}

	public com.liferay.portal.model.Company getCompanyByLogoId(long logoId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _companyLocalService.getCompanyByLogoId(logoId);
	}

	public com.liferay.portal.model.Company getCompanyByMx(java.lang.String mx)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _companyLocalService.getCompanyByMx(mx);
	}

	public com.liferay.portal.model.Company getCompanyByVirtualHost(
		java.lang.String virtualHost)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _companyLocalService.getCompanyByVirtualHost(virtualHost);
	}

	public com.liferay.portal.model.Company getCompanyByWebId(
		java.lang.String webId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _companyLocalService.getCompanyByWebId(webId);
	}

	public void removePreferences(long companyId, java.lang.String[] keys)
		throws com.liferay.portal.SystemException {
		_companyLocalService.removePreferences(companyId, keys);
	}

	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long userId, java.lang.String keywords, int start, int end)
		throws com.liferay.portal.SystemException {
		return _companyLocalService.search(companyId, userId, keywords, start,
			end);
	}

	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long userId, java.lang.String portletId, long groupId,
		java.lang.String type, java.lang.String keywords, int start, int end)
		throws com.liferay.portal.SystemException {
		return _companyLocalService.search(companyId, userId, portletId,
			groupId, type, keywords, start, end);
	}

	public com.liferay.portal.model.Company updateCompany(long companyId,
		java.lang.String virtualHost, java.lang.String mx)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _companyLocalService.updateCompany(companyId, virtualHost, mx);
	}

	public com.liferay.portal.model.Company updateCompany(long companyId,
		java.lang.String virtualHost, java.lang.String mx,
		java.lang.String homeURL, java.lang.String name,
		java.lang.String legalName, java.lang.String legalId,
		java.lang.String legalType, java.lang.String sicCode,
		java.lang.String tickerSymbol, java.lang.String industry,
		java.lang.String type, java.lang.String size)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _companyLocalService.updateCompany(companyId, virtualHost, mx,
			homeURL, name, legalName, legalId, legalType, sicCode,
			tickerSymbol, industry, type, size);
	}

	public void updateDisplay(long companyId, java.lang.String languageId,
		java.lang.String timeZoneId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_companyLocalService.updateDisplay(companyId, languageId, timeZoneId);
	}

	public void updateLogo(long companyId, byte[] bytes)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_companyLocalService.updateLogo(companyId, bytes);
	}

	public void updateLogo(long companyId, java.io.File file)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_companyLocalService.updateLogo(companyId, file);
	}

	public void updateLogo(long companyId, java.io.InputStream is)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_companyLocalService.updateLogo(companyId, is);
	}

	public void updatePreferences(long companyId,
		com.liferay.portal.kernel.util.UnicodeProperties properties)
		throws com.liferay.portal.SystemException {
		_companyLocalService.updatePreferences(companyId, properties);
	}

	public void updateSecurity(long companyId, java.lang.String authType,
		boolean autoLogin, boolean sendPassword, boolean strangers,
		boolean strangersWithMx, boolean strangersVerify, boolean communityLogo)
		throws com.liferay.portal.SystemException {
		_companyLocalService.updateSecurity(companyId, authType, autoLogin,
			sendPassword, strangers, strangersWithMx, strangersVerify,
			communityLogo);
	}

	public CompanyLocalService getWrappedCompanyLocalService() {
		return _companyLocalService;
	}

	private CompanyLocalService _companyLocalService;
}