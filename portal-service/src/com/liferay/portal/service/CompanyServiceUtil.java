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
 * <a href="CompanyServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link CompanyService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CompanyService
 * @generated
 */
public class CompanyServiceUtil {
	public static com.liferay.portal.model.Company addCompany(
		java.lang.String webId, java.lang.String virtualHost,
		java.lang.String mx, java.lang.String shardName, boolean system)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addCompany(webId, virtualHost, mx, shardName, system);
	}

	public static void deleteLogo(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteLogo(companyId);
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
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().removePreferences(companyId, keys);
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

	public static com.liferay.portal.model.Company updateCompany(
		long companyId, java.lang.String virtualHost, java.lang.String mx,
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
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateCompany(companyId, virtualHost, mx, homeURL, name,
			legalName, legalId, legalType, sicCode, tickerSymbol, industry,
			type, size, languageId, timeZoneId, addresses, emailAddresses,
			phones, websites, properties);
	}

	public static void updateDisplay(long companyId,
		java.lang.String languageId, java.lang.String timeZoneId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateDisplay(companyId, languageId, timeZoneId);
	}

	public static void updateLogo(long companyId, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateLogo(companyId, file);
	}

	public static void updatePreferences(long companyId,
		com.liferay.portal.kernel.util.UnicodeProperties properties)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updatePreferences(companyId, properties);
	}

	public static void updateSecurity(long companyId,
		java.lang.String authType, boolean autoLogin, boolean sendPassword,
		boolean strangers, boolean strangersWithMx, boolean strangersVerify,
		boolean communityLogo)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateSecurity(companyId, authType, autoLogin, sendPassword,
			strangers, strangersWithMx, strangersVerify, communityLogo);
	}

	public static CompanyService getService() {
		if (_service == null) {
			_service = (CompanyService)PortalBeanLocatorUtil.locate(CompanyService.class.getName());
		}

		return _service;
	}

	public void setService(CompanyService service) {
		_service = service;
	}

	private static CompanyService _service;
}