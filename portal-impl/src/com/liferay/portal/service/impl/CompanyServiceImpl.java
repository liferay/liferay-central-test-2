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

package com.liferay.portal.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
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
import com.liferay.portlet.enterpriseadmin.util.EnterpriseAdminUtil;

import java.io.File;

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
			boolean system)
		throws PortalException, SystemException {

		if (!getPermissionChecker().isOmniadmin()) {
			throw new PrincipalException();
		}

		return companyLocalService.addCompany(
			webId, virtualHost, mx, shardName, system);
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

	public Company updateCompany(long companyId, String virtualHost, String mx)
		throws PortalException, SystemException {

		if (!getPermissionChecker().isOmniadmin()) {
			throw new PrincipalException();
		}

		return companyLocalService.updateCompany(companyId, virtualHost, mx);
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

}