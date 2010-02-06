/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
 * <a href="CompanyServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link CompanyService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CompanyService
 * @generated
 */
public class CompanyServiceWrapper implements CompanyService {
	public CompanyServiceWrapper(CompanyService companyService) {
		_companyService = companyService;
	}

	public com.liferay.portal.model.Company addCompany(java.lang.String webId,
		java.lang.String virtualHost, java.lang.String mx,
		java.lang.String shardName, boolean system)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _companyService.addCompany(webId, virtualHost, mx, shardName,
			system);
	}

	public void deleteLogo(long companyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_companyService.deleteLogo(companyId);
	}

	public com.liferay.portal.model.Company getCompanyById(long companyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _companyService.getCompanyById(companyId);
	}

	public com.liferay.portal.model.Company getCompanyByLogoId(long logoId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _companyService.getCompanyByLogoId(logoId);
	}

	public com.liferay.portal.model.Company getCompanyByMx(java.lang.String mx)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _companyService.getCompanyByMx(mx);
	}

	public com.liferay.portal.model.Company getCompanyByVirtualHost(
		java.lang.String virtualHost)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _companyService.getCompanyByVirtualHost(virtualHost);
	}

	public com.liferay.portal.model.Company getCompanyByWebId(
		java.lang.String webId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _companyService.getCompanyByWebId(webId);
	}

	public void removePreferences(long companyId, java.lang.String[] keys)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_companyService.removePreferences(companyId, keys);
	}

	public com.liferay.portal.model.Company updateCompany(long companyId,
		java.lang.String virtualHost, java.lang.String mx)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _companyService.updateCompany(companyId, virtualHost, mx);
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
		return _companyService.updateCompany(companyId, virtualHost, mx,
			homeURL, name, legalName, legalId, legalType, sicCode,
			tickerSymbol, industry, type, size);
	}

	public com.liferay.portal.model.Company updateCompany(long companyId,
		java.lang.String virtualHost, java.lang.String mx,
		java.lang.String homeURL, java.lang.String name,
		java.lang.String legalName, java.lang.String legalId,
		java.lang.String legalType, java.lang.String sicCode,
		java.lang.String tickerSymbol, java.lang.String industry,
		java.lang.String type, java.lang.String size,
		java.lang.String languageId, java.lang.String timeZoneId,
		java.util.List<com.liferay.portal.model.Address> addresses,
		java.util.List<com.liferay.portal.model.EmailAddress> emailAddresses,
		java.util.List<com.liferay.portal.model.Phone> phones,
		java.util.List<com.liferay.portal.model.Website> websites,
		com.liferay.portal.kernel.util.UnicodeProperties properties)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _companyService.updateCompany(companyId, virtualHost, mx,
			homeURL, name, legalName, legalId, legalType, sicCode,
			tickerSymbol, industry, type, size, languageId, timeZoneId,
			addresses, emailAddresses, phones, websites, properties);
	}

	public void updateDisplay(long companyId, java.lang.String languageId,
		java.lang.String timeZoneId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_companyService.updateDisplay(companyId, languageId, timeZoneId);
	}

	public void updateLogo(long companyId, java.io.File file)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_companyService.updateLogo(companyId, file);
	}

	public void updatePreferences(long companyId,
		com.liferay.portal.kernel.util.UnicodeProperties properties)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_companyService.updatePreferences(companyId, properties);
	}

	public void updateSecurity(long companyId, java.lang.String authType,
		boolean autoLogin, boolean sendPassword, boolean strangers,
		boolean strangersWithMx, boolean strangersVerify, boolean communityLogo)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_companyService.updateSecurity(companyId, authType, autoLogin,
			sendPassword, strangers, strangersWithMx, strangersVerify,
			communityLogo);
	}

	public CompanyService getWrappedCompanyService() {
		return _companyService;
	}

	private CompanyService _companyService;
}