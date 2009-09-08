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
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoConverterUtil;
import com.liferay.util.ldap.LDAPUtil;
import com.liferay.util.ldap.Modifications;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.naming.Binding;
import javax.naming.Name;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

/**
 * <a href="BasicLDAPConverter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Edward Han
 */
public class BasicLDAPConverter implements LDAPConverter {

	//	This method is run with the expectation that update will be run
	public Attributes createLDAPUser(Name name, Properties userMappings, User user) {
		Attributes attrs = new BasicAttributes(true);

		//	objectClass
        Attribute objectClass = new BasicAttribute(USER_OBJECT_CLASS);

		String[] defaultObjectClasses = PropsUtil.getArray(
			PropsKeys.LDAP_USER_DEFAULT_OBJECT_CLASSES);

		for (String defaultObjectClass : defaultObjectClasses) {
			objectClass.add(defaultObjectClass);
		}

		attrs.put(objectClass);

		//	standard attributes
		addAttributeMapping(
				userMappings.getProperty(LDAPConverterKeys.USER_FIRST_NAME),
				user.getFirstName(), attrs);

		addAttributeMapping(
				userMappings.getProperty(LDAPConverterKeys.USER_LAST_NAME),
				user.getLastName(), attrs);

		addAttributeMapping(
				userMappings.getProperty(LDAPConverterKeys.USER_FULL_NAME),
				user.getFullName(), attrs);

		addAttributeMapping(
				userMappings.getProperty(LDAPConverterKeys.USER_EMAIL_ADDRESS),
				user.getEmailAddress(), attrs);

		addAttributeMapping(
				userMappings.getProperty(LDAPConverterKeys.USER_SCREEN_NAME),
				user.getScreenName(), attrs);

		return attrs;
	}

	public LDAPUserHolder importLDAPUser(
		Attributes attributes, Properties userMappings,
		Properties customMappings, long companyId,	String password)
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
			attributes,
				userMappings.getProperty(
						LDAPConverterKeys.USER_SCREEN_NAME)).toLowerCase();
		String emailAddress = LDAPUtil.getAttributeValue(
			attributes,
				userMappings.getProperty(LDAPConverterKeys.USER_EMAIL_ADDRESS));
		String openId = StringPool.BLANK;
		Locale locale = LocaleUtil.getDefault();
		String firstName = LDAPUtil.getAttributeValue(
			attributes,
				userMappings.getProperty(LDAPConverterKeys.USER_FIRST_NAME));
		String middleName = LDAPUtil.getAttributeValue(
			attributes,
				userMappings.getProperty(LDAPConverterKeys.USER_MIDDLE_NAME));
		String lastName = LDAPUtil.getAttributeValue(
			attributes,
				userMappings.getProperty(LDAPConverterKeys.USER_LAST_NAME));

		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = LDAPUtil.getAttributeValue(
			attributes,
				userMappings.getProperty(LDAPConverterKeys.USER_JOB_TITLE));
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		List<UserGroupRole> userGroupRoles = null;
		long[] userGroupIds = null;
		boolean sendEmail = false;


		if (Validator.isNull(firstName) || Validator.isNull(lastName)) {
			String fullName = LDAPUtil.getAttributeValue(
				attributes,
					userMappings.getProperty(LDAPConverterKeys.USER_FULL_NAME));

			String[] names = LDAPUtil.splitFullName(fullName);

			firstName = names[0];
			middleName = names[1];
			lastName = names[2];
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
		Contact contact = new ContactImpl();

		//	Mapped attributes
		user.setCompanyId(companyId);
		user.setPasswordUnencrypted(password);
		user.setScreenName(screenName);
		user.setEmailAddress(emailAddress);
		user.setOpenId(openId);
		user.setLanguageId(locale.getLanguage());
		user.setFirstName(firstName);
		user.setMiddleName(middleName);
		user.setLastName(lastName);
		user.setJobTitle(jobTitle);

		//	Contact values
		contact.setMale(male);
		contact.setBirthday(
			CalendarFactoryUtil.getCalendar(
				birthdayYear, birthdayMonth, birthdayDay).getTime());
		contact.setSuffixId(suffixId);
		contact.setPrefixId(prefixId);

		//	Custom attributes
		Map<String, String> expandoData =
			new HashMap<String, String>();

		for (Object expandoMappingName : customMappings.keySet()) {
			String attribute =
				LDAPUtil.getAttributeValue(
					attributes,	customMappings.getProperty(
						(String) expandoMappingName));

			if (Validator.isNotNull(attribute)) {
				expandoData.put((String) expandoMappingName, attribute);
			}
		}

		return new LDAPUserHolder(user, contact, expandoData,
			new ServiceContext(), autoPassword, updatePassword, creatorUserId,
			passwordReset, autoScreenName, groupIds, organizationIds, roleIds,
			userGroupRoles, userGroupIds, sendEmail);
	}

	public Modifications updateLDAPUser(
		Name name, Properties userMappings, User user, Binding existing)
		throws Exception {

		Modifications modifications = Modifications.getInstance();

		//	standard expandoAttributeNames
		addModificationItem(
				userMappings.getProperty(LDAPConverterKeys.USER_FIRST_NAME),
				user.getFirstName(), modifications);

		addModificationItem(
				userMappings.getProperty(LDAPConverterKeys.USER_LAST_NAME),
				user.getLastName(), modifications);

		addModificationItem(
				userMappings.getProperty(LDAPConverterKeys.USER_PASSWORD),
				user.getPasswordUnencrypted(), modifications);

		addModificationItem(
				userMappings.getProperty(LDAPConverterKeys.USER_EMAIL_ADDRESS),
				user.getEmailAddress(), modifications);

		addModificationItem(
				userMappings.getProperty(LDAPConverterKeys.USER_SCREEN_NAME),
				user.getScreenName(), modifications);

		addModificationItem(
				userMappings.getProperty(LDAPConverterKeys.USER_FULL_NAME),
				user.getFullName(), modifications);

		addModificationItem(
				userMappings.getProperty(LDAPConverterKeys.USER_JOB_TITLE),
				user.getJobTitle(), modifications);

		//	Expando attribute mapping
		ExpandoBridge expandoBridge = user.getExpandoBridge();
		Enumeration<String> expandoAttributeNames =
				expandoBridge.getAttributeNames();

		while (expandoAttributeNames.hasMoreElements()) {
			String expandoAttributeName = expandoAttributeNames.nextElement();

			String expandoAttributeMapping = userMappings.getProperty(
					expandoAttributeName);

			int type = expandoBridge.getAttributeType(expandoAttributeName);

			String value =
				ExpandoConverterUtil.getStringFromAttribute(
					type, expandoBridge.getAttribute(expandoAttributeName));

			addModificationItem(expandoAttributeMapping, value, modifications);
		}

		return modifications;
	}

	protected void addAttributeMapping(
			String attributeName, String attributeValue, Attributes attrs) {

		if (Validator.isNotNull(attributeName) &&
				Validator.isNotNull(attributeValue)) {
			attrs.put(attributeName, attributeValue);
		}
	}

	public void addModificationItem(
			String attributeName, String attributeValue,
			Modifications modifications) {

		if (Validator.isNotNull(attributeName) &&
				Validator.isNotNull(attributeValue)) {
			modifications.addItem(attributeName, attributeValue);
		}
	}

	public static final String USER_OBJECT_CLASS = "objectclass";

	private static Log _log = LogFactoryUtil.getLog(BasicLDAPConverter.class);
}
