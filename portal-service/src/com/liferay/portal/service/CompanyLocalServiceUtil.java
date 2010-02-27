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

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

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
 * {@link CompanyLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CompanyLocalService
 * @generated
 */
public class CompanyLocalServiceUtil {
	public static com.liferay.portal.model.Company addCompany(
		com.liferay.portal.model.Company company)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addCompany(company);
	}

	public static com.liferay.portal.model.Company createCompany(long companyId) {
		return getService().createCompany(companyId);
	}

	public static void deleteCompany(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteCompany(companyId);
	}

	public static void deleteCompany(com.liferay.portal.model.Company company)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteCompany(company);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.Company getCompany(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompany(companyId);
	}

	public static java.util.List<com.liferay.portal.model.Company> getCompanies(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanies(start, end);
	}

	public static int getCompaniesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompaniesCount();
	}

	public static com.liferay.portal.model.Company updateCompany(
		com.liferay.portal.model.Company company)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateCompany(company);
	}

	public static com.liferay.portal.model.Company updateCompany(
		com.liferay.portal.model.Company company, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateCompany(company, merge);
	}

	public static com.liferay.portal.model.Company addCompany(
		java.lang.String webId, java.lang.String virtualHost,
		java.lang.String mx, java.lang.String shardName, boolean system)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addCompany(webId, virtualHost, mx, shardName, system);
	}

	public static com.liferay.portal.model.Company checkCompany(
		java.lang.String webId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().checkCompany(webId);
	}

	public static com.liferay.portal.model.Company checkCompany(
		java.lang.String webId, java.lang.String mx, java.lang.String shardName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().checkCompany(webId, mx, shardName);
	}

	public static void checkCompanyKey(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().checkCompanyKey(companyId);
	}

	public static void deleteLogo(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteLogo(companyId);
	}

	public static java.util.List<com.liferay.portal.model.Company> getCompanies()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanies();
	}

	public static java.util.List<com.liferay.portal.model.Company> getCompanies(
		boolean system)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanies(system);
	}

	public static int getCompaniesCount(boolean system)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompaniesCount(system);
	}

	public static com.liferay.portal.model.Company getCompanyById(
		long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyById(companyId);
	}

	public static com.liferay.portal.model.Company getCompanyByLogoId(
		long logoId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyByLogoId(logoId);
	}

	public static com.liferay.portal.model.Company getCompanyByMx(
		java.lang.String mx)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyByMx(mx);
	}

	public static com.liferay.portal.model.Company getCompanyByVirtualHost(
		java.lang.String virtualHost)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyByVirtualHost(virtualHost);
	}

	public static com.liferay.portal.model.Company getCompanyByWebId(
		java.lang.String webId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyByWebId(webId);
	}

	public static void removePreferences(long companyId, java.lang.String[] keys)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().removePreferences(companyId, keys);
	}

	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		long userId, java.lang.String keywords, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().search(companyId, userId, keywords, start, end);
	}

	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		long userId, java.lang.String portletId, long groupId,
		java.lang.String type, java.lang.String keywords, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, userId, portletId, groupId, type,
			keywords, start, end);
	}

	public static com.liferay.portal.model.Company updateCompany(
		long companyId, java.lang.String virtualHost, java.lang.String mx)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateCompany(companyId, virtualHost, mx);
	}

	public static com.liferay.portal.model.Company updateCompany(
		long companyId, java.lang.String virtualHost, java.lang.String mx,
		java.lang.String homeURL, java.lang.String name,
		java.lang.String legalName, java.lang.String legalId,
		java.lang.String legalType, java.lang.String sicCode,
		java.lang.String tickerSymbol, java.lang.String industry,
		java.lang.String type, java.lang.String size)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateCompany(companyId, virtualHost, mx, homeURL, name,
			legalName, legalId, legalType, sicCode, tickerSymbol, industry,
			type, size);
	}

	public static void updateDisplay(long companyId,
		java.lang.String languageId, java.lang.String timeZoneId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateDisplay(companyId, languageId, timeZoneId);
	}

	public static void updateLogo(long companyId, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateLogo(companyId, bytes);
	}

	public static void updateLogo(long companyId, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateLogo(companyId, file);
	}

	public static void updateLogo(long companyId, java.io.InputStream is)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateLogo(companyId, is);
	}

	public static void updatePreferences(long companyId,
		com.liferay.portal.kernel.util.UnicodeProperties properties)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().updatePreferences(companyId, properties);
	}

	public static void updateSecurity(long companyId,
		java.lang.String authType, boolean autoLogin, boolean sendPassword,
		boolean strangers, boolean strangersWithMx, boolean strangersVerify,
		boolean communityLogo)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateSecurity(companyId, authType, autoLogin, sendPassword,
			strangers, strangersWithMx, strangersVerify, communityLogo);
	}

	public static CompanyLocalService getService() {
		if (_service == null) {
			_service = (CompanyLocalService)PortalBeanLocatorUtil.locate(CompanyLocalService.class.getName());
		}

		return _service;
	}

	public void setService(CompanyLocalService service) {
		_service = service;
	}

	private static CompanyLocalService _service;
}