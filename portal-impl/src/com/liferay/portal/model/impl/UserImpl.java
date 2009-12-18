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

package com.liferay.portal.model.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.ContactConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.auth.EmailAddressGenerator;
import com.liferay.portal.security.auth.EmailAddressGeneratorFactory;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ContactLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.PasswordPolicyLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.UniqueList;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;

/**
 * <a href="UserImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Wesley Gong
 */
public class UserImpl extends UserModelImpl implements User {

	public UserImpl() {
	}

	public Date getBirthday() {
		return getContact().getBirthday();
	}

	public String getCompanyMx() {
		String companyMx = null;

		try {
			Company company = CompanyLocalServiceUtil.getCompanyById(
				getCompanyId());

			companyMx = company.getMx();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return companyMx;
	}

	public Contact getContact() {
		Contact contact = null;

		try {
			contact = ContactLocalServiceUtil.getContact(getContactId());
		}
		catch (Exception e) {
			contact = new ContactImpl();

			_log.error(e, e);
		}

		return contact;
	}

	public String getDisplayEmailAddress() {
		String emailAddress = super.getEmailAddress();

		EmailAddressGenerator emailAddressGenerator =
			EmailAddressGeneratorFactory.getInstance();

		if (emailAddressGenerator.isFake(emailAddress)) {
			emailAddress = StringPool.BLANK;
		}

		return emailAddress;
	}

	public String getDisplayURL(ThemeDisplay themeDisplay) {
		return getDisplayURL(
			themeDisplay.getPortalURL(), themeDisplay.getPathMain());

	}

	public String getDisplayURL(String portalURL, String mainPath) {
		try {
			Group group = getGroup();

			if (group != null) {
				int publicLayoutsPageCount = group.getPublicLayoutsPageCount();

				if (publicLayoutsPageCount > 0) {
					StringBuilder sb = new StringBuilder();

					sb.append(portalURL);
					sb.append(mainPath);
					sb.append("/my_places/view?groupId=");
					sb.append(group.getGroupId());
					sb.append("&privateLayout=0");

					return sb.toString();
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return StringPool.BLANK;
	}

	public boolean getFemale() {
		return !getMale();
	}

	public String getFullName() {
		return ContactConstants.getFullName(
			getFirstName(), getMiddleName(), getLastName());
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

	public long[] getGroupIds() {
		List<Group> groups = getGroups();

		long[] groupIds = new long[groups.size()];

		for (int i = 0; i < groups.size(); i++) {
			Group group = groups.get(i);

			groupIds[i] = group.getGroupId();
		}

		return groupIds;
	}

	public List<Group> getGroups() {
		try {
			return GroupLocalServiceUtil.getUserGroups(getUserId());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get groups for user " + getUserId());
			}
		}

		return new ArrayList<Group>();
	}

	public Locale getLocale() {
		return _locale;
	}

	public String getLogin() throws PortalException, SystemException {
		String login = null;

		Company company = CompanyLocalServiceUtil.getCompanyById(
			getCompanyId());

		if (company.getAuthType().equals(CompanyConstants.AUTH_TYPE_EA)) {
			login = getEmailAddress();
		}
		else if (company.getAuthType().equals(CompanyConstants.AUTH_TYPE_SN)) {
			login = getScreenName();
		}
		else if (company.getAuthType().equals(CompanyConstants.AUTH_TYPE_ID)) {
			login = String.valueOf(getUserId());
		}

		return login;
	}

	public boolean getMale() {
		return getContact().getMale();
	}

	public List<Group> getMyPlaces() {
		return getMyPlaces(QueryUtil.ALL_POS);
	}

	public List<Group> getMyPlaces(int max) {
		List<Group> myPlaces = new UniqueList<Group>();

		try {
			if (isDefaultUser()) {
				return myPlaces;
			}

			int start = QueryUtil.ALL_POS;
			int end = QueryUtil.ALL_POS;

			if (max != QueryUtil.ALL_POS) {
				start = 0;
				end = max;
			}

			LinkedHashMap<String, Object> groupParams =
				new LinkedHashMap<String, Object>();

			groupParams.put("usersGroups", new Long(getUserId()));
			//groupParams.put("pageCount", StringPool.BLANK);

			myPlaces.addAll(
				GroupLocalServiceUtil.search(
					getCompanyId(), null, null, groupParams, start, end));

			LinkedHashMap<String, Object> organizationParams =
				new LinkedHashMap<String, Object>();

			organizationParams.put("usersOrgs", new Long(getUserId()));

			List<Organization> userOrgs = OrganizationLocalServiceUtil.search(
				getCompanyId(),
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, null,	null,
				null, null, organizationParams, start, end);

			for (Organization organization : userOrgs) {
				myPlaces.add(0, organization.getGroup());

				if (!PropsValues.ORGANIZATIONS_MEMBERSHIP_STRICT) {
					for (Organization ancestorOrganization :
							organization.getAncestors()) {

						myPlaces.add(0, ancestorOrganization.getGroup());
					}
				}
			}

			if (PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED ||
				PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED) {

				Group userGroup = getGroup();

				myPlaces.add(0, userGroup);
			}

			if ((max != QueryUtil.ALL_POS) && (myPlaces.size() > max)) {
				myPlaces = ListUtil.subList(myPlaces, start, end);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return myPlaces;
	}

	public long[] getOrganizationIds() {
		List<Organization> organizations = getOrganizations();

		long[] organizationIds = new long[organizations.size()];

		for (int i = 0; i < organizations.size(); i++) {
			Organization organization = organizations.get(i);

			organizationIds[i] = organization.getOrganizationId();
		}

		return organizationIds;
	}

	public List<Organization> getOrganizations() {
		try {
			return OrganizationLocalServiceUtil.getUserOrganizations(
				getUserId());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get organizations for user " + getUserId());
			}
		}

		return new ArrayList<Organization>();
	}

	public boolean getPasswordModified() {
		return _passwordModified;
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
			_log.error(e, e);
		}

		return 0;
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
			_log.error(e, e);
		}

		return 0;
	}

	public Set<String> getReminderQueryQuestions()
		throws PortalException, SystemException {

		Set<String> questions = new TreeSet<String>();

		List<Organization> organizations =
			OrganizationLocalServiceUtil.getUserOrganizations(
				getUserId(), true);

		for (Organization organization : organizations) {
			Set<String> organizationQuestions =
				organization.getReminderQueryQuestions(getLanguageId());

			if (organizationQuestions.size() == 0) {
				Organization parentOrganization =
					organization.getParentOrganization();

				while ((organizationQuestions.size() == 0) &&
						(parentOrganization != null)) {

					organizationQuestions =
						parentOrganization.getReminderQueryQuestions(
							getLanguageId());

					parentOrganization =
						parentOrganization.getParentOrganization();
				}
			}

			questions.addAll(organizationQuestions);
		}

		if (questions.size() == 0) {
			Set<String> defaultQuestions = SetUtil.fromArray(
				PropsUtil.getArray(PropsKeys.USERS_REMINDER_QUERIES_QUESTIONS));

			questions.addAll(defaultQuestions);
		}

		return questions;
	}

	public long[] getRoleIds() {
		List<Role> roles = getRoles();

		long[] roleIds = new long[roles.size()];

		for (int i = 0; i < roles.size(); i++) {
			Role role = roles.get(i);

			roleIds[i] = role.getRoleId();
		}

		return roleIds;
	}

	public List<Role> getRoles() {
		try {
			return RoleLocalServiceUtil.getUserRoles(getUserId());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get roles for user " + getUserId());
			}
		}

		return new ArrayList<Role>();
	}

	public long[] getUserGroupIds() {
		List<UserGroup> userGroups = getUserGroups();

		long[] userGroupIds = new long[userGroups.size()];

		for (int i = 0; i < userGroups.size(); i++) {
			UserGroup userGroup = userGroups.get(i);

			userGroupIds[i] = userGroup.getUserGroupId();
		}

		return userGroupIds;
	}

	public List<UserGroup> getUserGroups() {
		try {
			return UserGroupLocalServiceUtil.getUserUserGroups(getUserId());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get user groups for user " + getUserId());
			}
		}

		return new ArrayList<UserGroup>();
	}

	public TimeZone getTimeZone() {
		return _timeZone;
	}

	public boolean hasCompanyMx() {
		return hasCompanyMx(getEmailAddress());
	}

	public boolean hasCompanyMx(String emailAddress) {
		if (Validator.isNull(emailAddress)) {
			return false;
		}

		try {
			Company company = CompanyLocalServiceUtil.getCompanyById(
				getCompanyId());

			return company.hasCompanyMx(emailAddress);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	public boolean hasMyPlaces() {
		try {
			if (isDefaultUser()) {
				return false;
			}

			LinkedHashMap<String, Object> groupParams =
				new LinkedHashMap<String, Object>();

			groupParams.put("usersGroups", new Long(getUserId()));
			//groupParams.put("pageCount", StringPool.BLANK);

			int count = GroupLocalServiceUtil.searchCount(
				getCompanyId(), null, null, groupParams);

			if (count > 0) {
				return true;
			}

			count = OrganizationLocalServiceUtil.getUserOrganizationsCount(
				getUserId());

			if (count > 0) {
				return true;
			}

			if (PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED ||
				PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED) {

				return true;
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return false;
	}

	public boolean hasOrganization() {
		if (getOrganizations().size() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean hasPrivateLayouts() {
		if (getPrivateLayoutsPageCount() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean hasPublicLayouts() {
		if (getPublicLayoutsPageCount() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean hasReminderQuery() {
		if (Validator.isNotNull(getReminderQueryQuestion()) &&
			Validator.isNotNull(getReminderQueryAnswer())) {

			return true;
		}
		else {
			return false;
		}
	}

	public boolean isFemale() {
		return getFemale();
	}

	public boolean isMale() {
		return getMale();
	}

	public boolean isPasswordModified() {
		return _passwordModified;
	}

	public void setLanguageId(String languageId) {
		_locale = LocaleUtil.fromLanguageId(languageId);

		super.setLanguageId(LocaleUtil.toLanguageId(_locale));
	}

	public void setPasswordModified(boolean passwordModified) {
		_passwordModified = passwordModified;
	}

	public void setPasswordUnencrypted(String passwordUnencrypted) {
		_passwordUnencrypted = passwordUnencrypted;
	}

	public void setTimeZoneId(String timeZoneId) {
		if (Validator.isNull(timeZoneId)) {
			timeZoneId = TimeZoneUtil.getDefault().getID();
		}

		_timeZone = TimeZone.getTimeZone(timeZoneId);

		super.setTimeZoneId(timeZoneId);
	}

	private static Log _log = LogFactoryUtil.getLog(UserImpl.class);

	private Locale _locale;
	private boolean _passwordModified;
	private String _passwordUnencrypted;
	private TimeZone _timeZone;

}