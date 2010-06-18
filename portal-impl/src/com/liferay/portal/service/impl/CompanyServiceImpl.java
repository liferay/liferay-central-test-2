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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.Website;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.base.CompanyServiceBaseImpl;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portlet.enterpriseadmin.util.EnterpriseAdminUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="CompanyServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class CompanyServiceImpl extends CompanyServiceBaseImpl {

	public Company addCompany(
			String webId, String virtualHost, String mx, String shardName,
			boolean system, int maxUsers)
		throws PortalException, SystemException {

		if (!getPermissionChecker().isOmniadmin()) {
			throw new PrincipalException();
		}

		return companyLocalService.addCompany(
			webId, virtualHost, mx, shardName, system, maxUsers);
	}

	public void deleteLogo(long companyId)
		throws PortalException, SystemException {

		if (!roleLocalService.hasUserRole(
				getUserId(), companyId, RoleConstants.ADMINISTRATOR, true)) {

			throw new PrincipalException();
		}

		companyLocalService.deleteLogo(companyId);
	}

	public Company getCompanyById(long companyId)
		throws PortalException, SystemException {

		return companyLocalService.getCompanyById(companyId);
	}

	public Company getCompanyByLogoId(long logoId)
		throws PortalException, SystemException {

		return companyLocalService.getCompanyByLogoId(logoId);
	}

	public Company getCompanyByMx(String mx)
		throws PortalException, SystemException {

		return companyLocalService.getCompanyByMx(mx);
	}

	public Company getCompanyByVirtualHost(String virtualHost)
		throws PortalException, SystemException {

		return companyLocalService.getCompanyByVirtualHost(virtualHost);
	}

	public Company getCompanyByWebId(String webId)
		throws PortalException, SystemException {

		return companyLocalService.getCompanyByWebId(webId);
	}

	public void removePreferences(long companyId, String[] keys)
		throws PortalException, SystemException {

		if (!roleLocalService.hasUserRole(
				getUserId(), companyId, RoleConstants.ADMINISTRATOR, true)) {

			throw new PrincipalException();
		}

		companyLocalService.removePreferences(companyId, keys);
	}

	public Company updateCompany(
			long companyId, String virtualHost, String mx, int maxUsers)
		throws PortalException, SystemException {

		if (!getPermissionChecker().isOmniadmin()) {
			throw new PrincipalException();
		}

		return companyLocalService.updateCompany(
			companyId, virtualHost, mx, maxUsers);
	}

	public Company updateCompany(
			long companyId, String virtualHost, String mx, String homeURL,
			String name, String legalName, String legalId, String legalType,
			String sicCode, String tickerSymbol, String industry, String type,
			String size)
		throws PortalException, SystemException {

		if (!roleLocalService.hasUserRole(
				getUserId(), companyId, RoleConstants.ADMINISTRATOR, true)) {

			throw new PrincipalException();
		}

		return companyLocalService.updateCompany(
			companyId, virtualHost, mx, homeURL, name, legalName, legalId,
			legalType, sicCode, tickerSymbol, industry, type, size);
	}

	public Company updateCompany(
			long companyId, String virtualHost, String mx, String homeURL,
			String name, String legalName, String legalId, String legalType,
			String sicCode, String tickerSymbol, String industry, String type,
			String size, String languageId, String timeZoneId,
			List<Address> addresses, List<EmailAddress> emailAddresses,
			List<Phone> phones, List<Website> websites,
			UnicodeProperties properties)
		throws PortalException, SystemException {

		Company company = updateCompany(
			companyId, virtualHost, mx, homeURL, name, legalName, legalId,
			legalType, sicCode, tickerSymbol, industry, type, size);

		updateDisplay(company.getCompanyId(), languageId, timeZoneId);

		updatePreferences(company.getCompanyId(), properties);

		EnterpriseAdminUtil.updateAddresses(
			Account.class.getName(), company.getAccountId(), addresses);

		EnterpriseAdminUtil.updateEmailAddresses(
			Account.class.getName(), company.getAccountId(), emailAddresses);

		EnterpriseAdminUtil.updatePhones(
			Account.class.getName(), company.getAccountId(), phones);

		EnterpriseAdminUtil.updateWebsites(
			Account.class.getName(), company.getAccountId(), websites);

		return company;
	}

	public void updateDisplay(
			long companyId, String languageId, String timeZoneId)
		throws PortalException, SystemException {

		if (!roleLocalService.hasUserRole(
				getUserId(), companyId, RoleConstants.ADMINISTRATOR, true)) {

			throw new PrincipalException();
		}

		companyLocalService.updateDisplay(companyId, languageId, timeZoneId);
	}

	public void updateLogo(long companyId, File file)
		throws PortalException, SystemException {

		if (!roleLocalService.hasUserRole(
				getUserId(), companyId, RoleConstants.ADMINISTRATOR, true)) {

			throw new PrincipalException();
		}

		companyLocalService.updateLogo(companyId, file);
	}

	public void updatePreferences(long companyId, UnicodeProperties properties)
		throws PortalException, SystemException {

		if (!roleLocalService.hasUserRole(
				getUserId(), companyId, RoleConstants.ADMINISTRATOR, true)) {

			throw new PrincipalException();
		}

		if (properties.containsKey(PropsKeys.GOOGLE_APPS_PASSWORD)) {
			String defaultPassword = PrefsPropsUtil.getString(
				companyId, PropsKeys.GOOGLE_APPS_PASSWORD);

			fixPassword(
				companyId, properties, PropsKeys.GOOGLE_APPS_PASSWORD,
				PropsKeys.GOOGLE_APPS_PASSWORD + ".temp", defaultPassword);
		}
		else {
			String defaultPassword = PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_SECURITY_CREDENTIALS);

			List<String> tempKeys = new ArrayList<String>();

			String prefix = PropsKeys.LDAP_SECURITY_CREDENTIALS + ".temp";

			for (String key : properties.keySet()) {
				if (key.startsWith(prefix)) {
					tempKeys.add(key);
				}
			}

			for (String tempKey : tempKeys) {
				String key = tempKey.replace(".temp", "");

				fixPassword(
					companyId, properties, key, tempKey, defaultPassword);
			}
		}

		companyLocalService.updatePreferences(companyId, properties);
	}

	public void updateSecurity(
			long companyId, String authType, boolean autoLogin,
			boolean sendPassword, boolean strangers, boolean strangersWithMx,
			boolean strangersVerify, boolean communityLogo)
		throws PortalException, SystemException {

		if (!roleLocalService.hasUserRole(
				getUserId(), companyId, RoleConstants.ADMINISTRATOR, true)) {

			throw new PrincipalException();
		}

		companyLocalService.updateSecurity(
			companyId, authType, autoLogin, sendPassword, strangers,
			strangersWithMx, strangersVerify, communityLogo);
	}

	protected void fixPassword(
			long companyId, UnicodeProperties properties, String key,
			String tempKey, String defaultPassword)
		throws SystemException {

		String password = properties.get(key);

		String tempPassword = properties.get(tempKey);

		if (password.equals(tempPassword)) {
			properties.setProperty(key, defaultPassword);
		}

		properties.remove(tempKey);
	}

}