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

package com.liferay.portal.model.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ContactLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.PasswordPolicyLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.comparator.OrganizationNameComparator;
import com.liferay.portlet.expando.NoSuchTableException;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.model.impl.ExpandoColumnImpl;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UserImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UserImpl extends UserModelImpl implements User {

	public UserImpl() {
	}

	public void addAttribute(String name) {
		addAttribute(name, ExpandoColumnConstants.STRING, null);
	}

	public void addAttribute(String name, int type) {
		addAttribute(name, type, null);
	}

	public void addAttribute(String name, int type, Object defaultValue) {
		try {
			ExpandoTable table = null;

			try {
				table = ExpandoTableLocalServiceUtil.getDefaultTable(
					User.class.getName());
			}
			catch(NoSuchTableException nste) {
				table = ExpandoTableLocalServiceUtil.addDefaultTable(
					User.class.getName());
			}

			ExpandoColumnLocalServiceUtil.addColumn(
				table.getTableId(), name, type, defaultValue);
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	public Object getAttribute(String name) {
		Object data = null;

		try {
			data = ExpandoValueLocalServiceUtil.getData(
				User.class.getName(), ExpandoTableConstants.DEFAULT_TABLE_NAME,
				name, getUserId());
		}
		catch (Exception e) {
			_log.error(e);
		}

		return data;
	}

	public Map<String, Object> getAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();
		List<ExpandoColumn> columns = new ArrayList<ExpandoColumn>();

		try {
			columns = ExpandoColumnLocalServiceUtil.getDefaultTableColumns(
				User.class.getName());
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e);
			}
		}

		for (ExpandoColumn column : columns) {
			attributes.put(column.getName(), getAttribute(column.getName()));
		}

		return attributes;
	}

	public Enumeration<String> getAttributeNames() {
		List<ExpandoColumn> columns = new ArrayList<ExpandoColumn>();

		try {
			columns = ExpandoColumnLocalServiceUtil.getDefaultTableColumns(
				User.class.getName());
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e);
			}
		}

		Vector<String> columnNames = new Vector<String>();

		for (ExpandoColumn column : columns) {
			columnNames.add(column.getName());
		}

		return columnNames.elements();
	}

	public Object getAttributeDefault(String name) {
		ExpandoColumn column = new ExpandoColumnImpl();

		try {
			column = ExpandoColumnLocalServiceUtil.getDefaultTableColumn(
				User.class.getName(), name);
		}
		catch (Exception e) {
			_log.error(e);
		}

		return column.getDefaultValue();
	}

	public int getAttributeType(String name) {
		ExpandoColumn column = new ExpandoColumnImpl();

		try {
			column = ExpandoColumnLocalServiceUtil.getDefaultTableColumn(
				User.class.getName(), name);
		}
		catch (Exception e) {
			_log.error(e);
		}

		return column.getType();
	}

	public void setAttribute(String name, Object value) {
		try {
			ExpandoValueLocalServiceUtil.addValue(
				User.class.getName(), ExpandoTableConstants.DEFAULT_TABLE_NAME,
				name, getUserId(), value);
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	public void setAttributeDefault(String name, Object defaultValue) {
		try {
			ExpandoColumn column =
				ExpandoColumnLocalServiceUtil.getDefaultTableColumn(
					User.class.getName(), name);

			ExpandoColumnLocalServiceUtil.updateColumn(
				column.getColumnId(), column.getName(), column.getType(),
				defaultValue);
		}
		catch (Exception e) {
			_log.error(e);
		}
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
		try {
			Company company = CompanyLocalServiceUtil.getCompanyById(
				getCompanyId());

			return company.hasCompanyMx(emailAddress);
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

	public boolean getPasswordModified() {
		return _passwordModified;
	}

	public boolean isPasswordModified() {
		return _passwordModified;
	}

	public void setPasswordModified(boolean passwordModified) {
		_passwordModified = passwordModified;
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
			timeZoneId = TimeZoneUtil.getDefault().getID();
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

	/**
	 * @deprecated Will return the first organization of a list in
	 * alphabetical order.
	 */
	public Organization getOrganization() {
		try {
			List<Organization> organizations =
				OrganizationLocalServiceUtil.getUserOrganizations(getUserId());

			if (organizations.size() > 0) {
				Collections.sort(
					organizations, new OrganizationNameComparator(true));

				return organizations.get(0);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get an organization for user " + getUserId());
			}
		}

		return new OrganizationImpl();
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

	public boolean hasOrganization() {
		if (getOrganizations().size() > 0) {
			return true;
		}
		else {
			return false;
		}
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

	public List<Group> getMyPlaces() {
		List<Group> myPlaces = new ArrayList<Group>();

		try {
			if (isDefaultUser()) {
				return myPlaces;
			}

			LinkedHashMap<String, Object> groupParams =
				new LinkedHashMap<String, Object>();

			groupParams.put("usersGroups", new Long(getUserId()));
			//groupParams.put("pageCount", StringPool.BLANK);

			myPlaces = GroupLocalServiceUtil.search(
				getCompanyId(), null, null, groupParams, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

			List<Organization> userOrgs = getOrganizations();

			for (Organization organization : userOrgs) {
				myPlaces.add(0, organization.getGroup());
			}

			if (PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED ||
				PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED) {

				Group userGroup = getGroup();

				myPlaces.add(0, userGroup);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return myPlaces;
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

	public String getDisplayURL(ThemeDisplay themeDisplay) {
		try {
			Group group = getGroup();

			if (group != null) {
				int publicLayoutsPageCount = group.getPublicLayoutsPageCount();

				if (publicLayoutsPageCount > 0) {
					StringBuilder sb = new StringBuilder();

					sb.append(themeDisplay.getPortalURL());
					sb.append(themeDisplay.getPathMain());
					sb.append("/my_places/view?groupId=");
					sb.append(group.getGroupId());
					sb.append("&privateLayout=0");

					return sb.toString();
				}
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		return StringPool.BLANK;
	}

	private static Log _log = LogFactory.getLog(UserImpl.class);

	private boolean _passwordModified;
	private String _passwordUnencrypted;
	private Locale _locale;
	private TimeZone _timeZone;

}