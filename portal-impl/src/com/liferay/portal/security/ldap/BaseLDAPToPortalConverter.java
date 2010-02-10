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

package com.liferay.portal.security.ldap;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.model.impl.ContactImpl;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.util.ldap.LDAPUtil;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

/**
 * <a href="BaseLDAPToPortalConverter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Edward Han
 * @author Brian Wing Shun Chan
 */
public class BaseLDAPToPortalConverter implements LDAPToPortalConverter {

	public LDAPGroup importLDAPGroup(
			long companyId, Attributes attributes, Properties groupMappings)
		throws Exception {

		String groupName = LDAPUtil.getAttributeValue(
			attributes, groupMappings, GroupConverterKeys.GROUP_NAME).
				toLowerCase();
		String description = LDAPUtil.getAttributeValue(
			attributes, groupMappings, GroupConverterKeys.DESCRIPTION);

		LDAPGroup ldapGroup = new LDAPGroup();

		ldapGroup.setCompanyId(companyId);
		ldapGroup.setDescription(description);
		ldapGroup.setGroupName(groupName);

		return ldapGroup;
	}

	public LDAPUser importLDAPUser(
			long companyId, Attributes attributes, Properties userMappings,
			Properties userExpandoMappings, Properties contactMappings,
			Properties contactExpandoMappings, String password)
		throws Exception {

		boolean autoPassword = false;
		boolean updatePassword = true;

		if (password.equals(StringPool.BLANK)) {
			autoPassword = true;
			updatePassword = false;
		}

		long creatorUserId = 0;
		boolean passwordReset = false;
		boolean autoScreenName = false;

		String screenName = LDAPUtil.getAttributeValue(
			attributes, userMappings, UserConverterKeys.SCREEN_NAME).
				toLowerCase();
		String emailAddress = LDAPUtil.getAttributeValue(
			attributes, userMappings, UserConverterKeys.EMAIL_ADDRESS);
		String openId = StringPool.BLANK;
		Locale locale = LocaleUtil.getDefault();
		String firstName = LDAPUtil.getAttributeValue(
			attributes, userMappings, UserConverterKeys.FIRST_NAME);
		String middleName = LDAPUtil.getAttributeValue(
			attributes, userMappings, UserConverterKeys.MIDDLE_NAME);
		String lastName = LDAPUtil.getAttributeValue(
			attributes, userMappings, UserConverterKeys.LAST_NAME);

		if (Validator.isNull(firstName) || Validator.isNull(lastName)) {
			String fullName = LDAPUtil.getAttributeValue(
				attributes, userMappings, UserConverterKeys.FULL_NAME);

			String[] names = LDAPUtil.splitFullName(fullName);

			firstName = names[0];
			middleName = names[1];
			lastName = names[2];
		}

		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = LDAPUtil.getAttributeValue(
			attributes, userMappings, UserConverterKeys.JOB_TITLE);
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		List<UserGroupRole> userGroupRoles = null;
		long[] userGroupIds = null;
		boolean sendEmail = false;

		ServiceContext serviceContext = new ServiceContext();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Screen name " + screenName + " and email address " +
					emailAddress);
		}

		if (Validator.isNull(screenName) || Validator.isNull(emailAddress)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Cannot add user because screen name and email address " +
						"are required");
			}

			return null;
		}

		User user = new UserImpl();

		user.setCompanyId(companyId);
		user.setEmailAddress(emailAddress);
		user.setFirstName(firstName);
		user.setJobTitle(jobTitle);
		user.setLanguageId(locale.getLanguage());
		user.setLastName(lastName);
		user.setMiddleName(middleName);
		user.setOpenId(openId);
		user.setPasswordUnencrypted(password);
		user.setScreenName(screenName);

		Map<String, String> userExpandoAttributes = getExpandoAttributes(
			attributes, userExpandoMappings);

		Contact contact = new ContactImpl();

		contact.setBirthday(
			CalendarFactoryUtil.getCalendar(
				birthdayYear, birthdayMonth, birthdayDay).getTime());
		contact.setMale(male);
		contact.setPrefixId(prefixId);
		contact.setSuffixId(suffixId);

		Map<String, String> contactExpandoAttributes = getExpandoAttributes(
			attributes, contactExpandoMappings);

		LDAPUser ldapUser = new LDAPUser();

		ldapUser.setAutoPassword(autoPassword);
		ldapUser.setAutoScreenName(autoScreenName);
		ldapUser.setContact(contact);
		ldapUser.setContactExpandoAttributes(contactExpandoAttributes);
		ldapUser.setCreatorUserId(creatorUserId);
		ldapUser.setGroupIds(groupIds);
		ldapUser.setOrganizationIds(organizationIds);
		ldapUser.setPasswordReset(passwordReset);
		ldapUser.setRoleIds(roleIds);
		ldapUser.setSendEmail(sendEmail);
		ldapUser.setServiceContext(serviceContext);
		ldapUser.setUpdatePassword(updatePassword);
		ldapUser.setUser(user);
		ldapUser.setUserExpandoAttributes(userExpandoAttributes);
		ldapUser.setUserGroupIds(userGroupIds);
		ldapUser.setUserGroupRoles(userGroupRoles);

		return ldapUser;
	}

	protected Map<String, String> getExpandoAttributes(
			Attributes attributes, Properties expandoMappings)
		throws NamingException {

		Map<String, String> expandoAttributes = new HashMap<String, String>();

		for (Object key : expandoMappings.keySet()) {
			String name = (String)key;

			String value = LDAPUtil.getAttributeValue(
				attributes,	expandoMappings, name);

			if (Validator.isNotNull(value)) {
				expandoAttributes.put(name, value);
			}
		}

		return expandoAttributes;
	}

	private static Log _log = LogFactoryUtil.getLog(
		BaseLDAPToPortalConverter.class);

}