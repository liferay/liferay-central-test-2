/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.spring.GroupLocalServiceUtil;
import com.liferay.portal.service.spring.UserLocalServiceUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.LDAPUtil;
import com.liferay.util.PropertiesUtil;
import com.liferay.util.StringPool;
import com.liferay.util.Validator;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="LDAPImportUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Michael Young
 *
 */
public class LDAPImportUtil {

	public static void addOrUpdateUser(
			String creatorUserId, String companyId, boolean autoUserId,
			String userId, boolean autoPassword, String password1,
			String password2, boolean passwordReset, String emailAddress,
			Locale locale, String firstName, String middleName, String lastName,
			String nickName, String prefixId, String suffixId, boolean male,
			int birthdayMonth, int birthdayDay, int birthdayYear,
			String jobTitle, String organizationId, String locationId,
			boolean sendEmail, boolean checkExists, boolean updatePassword)
		throws PortalException, SystemException {

		boolean create = true;

		if (checkExists) {
			try {
				UserLocalServiceUtil.getUserByEmailAddress(
					companyId, emailAddress);

				if (updatePassword) {
					UserLocalServiceUtil.updatePassword(
						userId, password1, password2, passwordReset);
				}

				create = false;
			}
			catch (NoSuchUserException nsue) {

				// User does not exist so create

			}
		}

		if (create) {
			try {
				UserLocalServiceUtil.addUser(
					creatorUserId, companyId, autoUserId, userId, autoPassword,
					password1, password2, passwordReset, emailAddress, locale,
					firstName, middleName, lastName, nickName, prefixId,
					suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
					jobTitle, organizationId, locationId, sendEmail);
			}
			catch (Exception e){
				_log.error(StackTraceUtil.getStackTrace(e));
			}
		}
	}

	public static void importLDAP(String companyId) throws Exception {
		Properties env = new Properties();

		env.put(
			Context.INITIAL_CONTEXT_FACTORY,
			PrefsPropsUtil.getString(PropsUtil.LDAP_IMPORT_FACTORY_INITIAL));
		env.put(
			Context.PROVIDER_URL,
			PrefsPropsUtil.getString(PropsUtil.LDAP_IMPORT_PROVIDER_URL));
		env.put(
			Context.SECURITY_PRINCIPAL,
			PrefsPropsUtil.getString(PropsUtil.LDAP_IMPORT_SECURITY_PRINCIPAL));
		env.put(
			Context.SECURITY_CREDENTIALS,
			PrefsPropsUtil.getString(
				PropsUtil.LDAP_IMPORT_SECURITY_CREDENTIALS));

		if (_log.isDebugEnabled()) {
			StringWriter sw = new StringWriter();

			env.list(new PrintWriter(sw));

			_log.debug(sw.getBuffer().toString());
		}

		LdapContext ctx = null;

		try {
			ctx = new InitialLdapContext(env, null);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Failed to bind to the LDAP server");
			}

			return;
		}

		Properties userMappings = PropertiesUtil.load(
			PrefsPropsUtil.getString(PropsUtil.LDAP_IMPORT_USER_MAPPINGS));

		if (_log.isDebugEnabled()) {
			StringWriter sw = new StringWriter();

			userMappings.list(new PrintWriter(sw));

			_log.debug(sw.getBuffer().toString());
		}

		Properties groupMappings = PropertiesUtil.load(
			PrefsPropsUtil.getString(PropsUtil.LDAP_IMPORT_GROUP_MAPPINGS));

		if (_log.isDebugEnabled()) {
			StringWriter sw = new StringWriter();

			groupMappings.list(new PrintWriter(sw));

			_log.debug(sw.getBuffer().toString());
		}
		try {
			String filter = PrefsPropsUtil.getString(
				PropsUtil.LDAP_IMPORT_SEARCH_FILTER);

			String context = PrefsPropsUtil.getString(
				PropsUtil.LDAP_IMPORT_CONTEXT);

			NamingEnumeration enu = ctx.search(context, filter, null);

			while (enu.hasMore()) {
				SearchResult result = (SearchResult)enu.next();

				_importLDAP(
					companyId, ctx, userMappings, groupMappings, result);
			}
		}
		catch (Exception e) {
			_log.error("Error importing LDAP users and groups", e);
		}
		finally {
			if (ctx != null) {
				ctx.close();
			}
		}
	}

	private static void _importLDAP(
			String companyId, LdapContext ctx, Properties userMappings,
			Properties groupMappings, SearchResult result)
		throws Exception {

		String userCN = result.getName();

		if (_log.isDebugEnabled()) {
			_log.debug("User CN " + userCN);
		}

		Attributes attrs = result.getAttributes();

		String creatorUserId = null;
		boolean autoUserId = false;
		String userId = LDAPUtil.getAttributeValue(
			attrs, userMappings.getProperty("userId"));
		boolean autoPassword = true;
		String password1 = StringPool.BLANK;
		String password2 = StringPool.BLANK;
		boolean passwordReset = false;
		String emailAddress = LDAPUtil.getAttributeValue(
			attrs, userMappings.getProperty("emailAddress"));
		Locale locale = Locale.US;
		String firstName = LDAPUtil.getAttributeValue(
			attrs, userMappings.getProperty("firstName"));
		String middleName = LDAPUtil.getAttributeValue(
			attrs, userMappings.getProperty("middleName"));
		String lastName = LDAPUtil.getAttributeValue(
			attrs, userMappings.getProperty("lastName"));

		if (Validator.isNull(firstName) || Validator.isNull(lastName)) {
			String fullName = LDAPUtil.getAttributeValue(
				attrs, userMappings.getProperty("fullName"));

			String[] names = LDAPUtil.splitFullName(fullName);

			firstName = names[0];
			middleName = names[1];
			lastName = names[2];
		}

		String nickName = null;
		String prefixId = null;
		String suffixId = null;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = LDAPUtil.getAttributeValue(
			attrs, userMappings.getProperty("jobTitle"));
		String organizationId = null;
		String locationId = null;
		boolean sendEmail = false;

		addOrUpdateUser(
			creatorUserId, companyId, autoUserId, userId, autoPassword,
			password1, password2, passwordReset, emailAddress, locale,
			firstName, middleName, lastName, nickName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, organizationId,
			locationId, sendEmail, true, false);

		// Import and add user to group

		Attribute attr = attrs.get(userMappings.getProperty("group"));

		for (int i = 0; i < attr.size(); i++) {
			String groupDN = (String)attr.get(i);

			Attributes groupAttrs = ctx.getAttributes(groupDN);

			String groupName = LDAPUtil.getAttributeValue(
				groupAttrs, groupMappings.getProperty("groupName"));
			String description = LDAPUtil.getAttributeValue(
				groupAttrs, groupMappings.getProperty("description"));

			Group group = null;

			try {
				group = GroupLocalServiceUtil.getGroup(companyId, groupName);
			}
			catch (NoSuchGroupException nsge) {
				group = GroupLocalServiceUtil.addGroup(
					User.getDefaultUserId(companyId), StringPool.BLANK,
					StringPool.BLANK, groupName, description, StringPool.BLANK,
					StringPool.BLANK);
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Adding " + userCN + " to group " + groupDN);
			}

			UserLocalServiceUtil.addGroupUsers(
				group.getGroupId(), new String[] {userId});
		}
	}

	private static Log _log = LogFactory.getLog(LDAPImportUtil.class);

}