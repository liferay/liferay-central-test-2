/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ContactLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.PasswordPolicyLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.LocaleUtil;
import com.liferay.util.Validator;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UserImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UserImpl extends UserModelImpl implements User {

	public static String getFullName(
		String firstName, String middleName, String lastName) {

		return ContactImpl.getFullName(firstName, middleName, lastName);
	}

	public UserImpl() {
	}

	public String getCompanyMx() {
		String companyMx = null;

		try {
			Company company = CompanyLocalServiceUtil.getCompanyById(
				getCompanyId());

			companyMx = company.getMx();
		}
		catch (Exception e) {
			_log.error(e);
		}

		return companyMx;
	}

	public boolean hasCompanyMx() {
		return hasCompanyMx(getEmailAddress());
	}

	public boolean hasCompanyMx(String emailAddress) {
		String mx = emailAddress.substring(
			emailAddress.indexOf(StringPool.AT) + 1, emailAddress.length());

		if (mx.equals(getCompanyMx())) {
			return true;
		}

		try {
			String[] mailHostNames = PrefsPropsUtil.getStringArray(
				getCompanyId(), PropsUtil.ADMIN_MAIL_HOST_NAMES);

			for (int i = 0; i < mailHostNames.length; i++) {
				if (mx.equalsIgnoreCase(mailHostNames[i])) {
					return true;
				}
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		return false;
	}

	public String getLogin() throws PortalException, SystemException {
		String login = null;

		Company company = CompanyLocalServiceUtil.getCompanyById(
			getCompanyId());

		if (company.getAuthType().equals(CompanyImpl.AUTH_TYPE_EA)) {
			login = getEmailAddress();
		}
		else if (company.getAuthType().equals(CompanyImpl.AUTH_TYPE_SN)) {
			login = getScreenName();
		}
		else if (company.getAuthType().equals(CompanyImpl.AUTH_TYPE_ID)) {
			login = String.valueOf(getUserId());
		}

		return login;
	}

	public PasswordPolicy getPasswordPolicy()
		throws PortalException, SystemException {

		PasswordPolicy passwordPolicy =
			PasswordPolicyLocalServiceUtil.getPasswordPolicyByUserId(
				getUserId());

		return passwordPolicy;
	}

	public String getPasswordUnencrypted() {
		return _passwordUnencrypted;
	}

	public void setPasswordUnencrypted(String passwordUnencrypted) {
		_passwordUnencrypted = passwordUnencrypted;
	}

	public boolean isPasswordExpired() {
		if (getPasswordExpirationDate() != null &&
			getPasswordExpirationDate().before(new Date())) {

			return true;
		}
		else {
			return false;
		}
	}

	public Locale getLocale() {
		return _locale;
	}

	public void setLanguageId(String languageId) {
		_locale = LocaleUtil.fromLanguageId(languageId);

		super.setLanguageId(LocaleUtil.toLanguageId(_locale));
	}

	public TimeZone getTimeZone() {
		return _timeZone;
	}

	public void setTimeZoneId(String timeZoneId) {
		if (Validator.isNull(timeZoneId)) {
			timeZoneId = TimeZone.getDefault().getID();
		}

		_timeZone = TimeZone.getTimeZone(timeZoneId);

		super.setTimeZoneId(timeZoneId);
	}

	public Contact getContact() {
		Contact contact = null;

		try {
			contact = ContactLocalServiceUtil.getContact(getContactId());
		}
		catch (Exception e) {
			contact = new ContactImpl();

			_log.error(e);
		}

		return contact;
	}

	public String getFirstName() {
		return getContact().getFirstName();
	}

	public String getMiddleName() {
		return getContact().getMiddleName();
	}

	public String getLastName() {
		return getContact().getLastName();
	}

	public String getFullName() {
		return getContact().getFullName();
	}

	public boolean getMale() {
		return getContact().getMale();
	}

	public boolean isMale() {
		return getMale();
	}

	public boolean getFemale() {
		return !getMale();
	}

	public boolean isFemale() {
		return getFemale();
	}

	public Date getBirthday() {
		return getContact().getBirthday();
	}

	public Group getGroup() {
		Group group = null;

		try {
			group = GroupLocalServiceUtil.getUserGroup(
				getCompanyId(), getUserId());
		}
		catch (Exception e) {
		}

		return group;
	}

	public Organization getOrganization() {
		try {
			List organizations =
				OrganizationLocalServiceUtil.getUserOrganizations(getUserId());

			for (int i = 0; i < organizations.size(); i++) {
				Organization organization = (Organization)organizations.get(i);

				if (organization.getParentOrganizationId() ==
						OrganizationImpl.DEFAULT_PARENT_ORGANIZATION_ID) {

					return organization;
				}
			}
		}
		catch (Exception e) {
			_log.warn("User does not have belong to an organization");
		}

		return new OrganizationImpl();
	}

	public Organization getLocation() {
		try {
			List organizations =
				OrganizationLocalServiceUtil.getUserOrganizations(getUserId());

			for (int i = 0; i < organizations.size(); i++) {
				Organization organization = (Organization)organizations.get(i);

				if (organization.getParentOrganizationId() !=
						OrganizationImpl.DEFAULT_PARENT_ORGANIZATION_ID) {

					return organization;
				}
			}
		}
		catch (Exception e) {
			_log.warn("User does not have belong to a location");
		}

		return new OrganizationImpl();
	}

	public int getPrivateLayoutsPageCount() {
		try {
			Group group = getGroup();

			if (group == null) {
				return 0;
			}
			else {
				return group.getPrivateLayoutsPageCount();
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		return 0;
	}

	public boolean hasPrivateLayouts() {
		if (getPrivateLayoutsPageCount() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public int getPublicLayoutsPageCount() {
		try {
			Group group = getGroup();

			if (group == null) {
				return 0;
			}
			else {
				return group.getPublicLayoutsPageCount();
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		return 0;
	}

	public boolean hasPublicLayouts() {
		if (getPublicLayoutsPageCount() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isLayoutsRequired() {
		try {
			Role role = RoleLocalServiceUtil.getRole(
				getCompanyId(), RoleImpl.POWER_USER);

			return UserLocalServiceUtil.hasRoleUser(
				role.getRoleId(), getUserId());
		}
		catch (Exception e) {
			return false;
		}
	}

	private static Log _log = LogFactory.getLog(UserImpl.class);

	private String _passwordUnencrypted;
	private Locale _locale;
	private TimeZone _timeZone;

}