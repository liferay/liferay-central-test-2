/*
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

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.ldap.Modifications;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Binding;
import javax.naming.Name;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * <a href="BasicPortalToLDAPConverter.java.html}"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class BasicPortalToLDAPConverter implements PortalToLDAPConverter {

	public BasicPortalToLDAPConverter() {
		//always reserve the screenName and password attributes for special
		//handling
		_reservedUserFields.put(
			LDAPConverterKeys.USER_SCREEN_NAME,
			LDAPConverterKeys.USER_SCREEN_NAME);
		_reservedUserFields.put(
			LDAPConverterKeys.USER_PASSWORD,
			LDAPConverterKeys.USER_PASSWORD);
		_reservedUserFields.put("group", "group");

		//set the default mapping for a user's DN to be the screenName
		//this can be overriden from Spring.
		_userDNFieldName = LDAPConverterKeys.USER_SCREEN_NAME;
	}

	public Modifications getLDAPContactModifications(
			Name name, Properties contactMappings,
			Contact contact, Binding existing)
		throws Exception {

		if (contactMappings.isEmpty()) {
			return null;
		}

		Modifications modifications = getModifications(
			contact, contactMappings, _reservedContactFields);

		return modifications;
	}

	/**
	 * Creates the LDAP Attributes necessary to initially create user.
	 * This method does not update all the attributes of the user.
	 * It requires the caller to update additional attributes via an
	 * invocation of getLDAPUserModifications method.
	 *
	 * @param name
	 * @param userMappings
	 * @param user
	 * @param ldapServerId
	 * @return
	 * @throws com.liferay.portal.SystemException
	 */
	public Attributes getLDAPUserAttributes(
			Name name, Properties userMappings, User user, long ldapServerId)
		throws SystemException {

		Attributes attrs = new BasicAttributes(true);

		//	objectClass
        Attribute objectClass = new BasicAttribute(_USER_OBJECT_CLASS);

		String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

		String[] defaultObjectClasses = PrefsPropsUtil.getStringArray(
			user.getCompanyId(),
			PropsKeys.LDAP_USER_DEFAULT_OBJECT_CLASSES + postfix,
			StringPool.COMMA);

		for (int i = 0; i < defaultObjectClasses.length; i++) {
			objectClass.add(defaultObjectClasses[i]);
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
				userMappings.getProperty(LDAPConverterKeys.USER_PASSWORD),
				user.getPasswordUnencrypted(), attrs);

		addAttributeMapping(
				userMappings.getProperty(LDAPConverterKeys.USER_EMAIL_ADDRESS),
				user.getEmailAddress(), attrs);

		return attrs;
	}

	public Modifications getLDAPUserModifications(
		Name name, Properties userMappings, User user, Binding existing)
		throws Exception {

		Modifications modifications = getModifications(
			user, userMappings, _reservedUserFields);

		if (user.isPasswordModified() &&
			Validator.isNotNull(user.getPasswordUnencrypted())) {

			addModificationItem(
					userMappings.getProperty(LDAPConverterKeys.USER_PASSWORD),
					user.getPasswordUnencrypted(), modifications);
		}

		return modifications;
	}

	public String getUserDNName(
			long ldapServerId, long companyId,
			User user, Properties userMappings)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append(
			GetterUtil.getString(
				userMappings.getProperty(_userDNFieldName), _DEFAULT_DN));
		sb.append(StringPool.EQUAL);
		sb.append(PropertyUtils.getProperty(user, _userDNFieldName));
		sb.append(StringPool.COMMA);
		sb.append(PortalLDAPUtil.getUsersDN(ldapServerId, companyId));

		return sb.toString();
	}

	/**
	 * Configure additional reserved fields on the Contact object that will
	 * not be synchronized from LDAP.  This value may be modified via the
	 * ldap-spring.xml configuration file.
	 *
	 * @param fieldNames
	 */
	public void setContactReservedFields(List<String> fieldNames) {
		for (String fieldName : fieldNames) {
			_reservedContactFields.put(fieldName, fieldName);
		}
	}

	/**
	 * Configure the User attribute to be used as the User's DN field.  This
	 * is "screenName" by default.  This value may be modified via the
	 * ldap-spring.xml configuration file.
	 *
	 * @param
	 */
	public void setUserDNFieldName(String fieldName) {
		_userDNFieldName = fieldName;
	}

	/**
	 * Configure additional reserved fields on the User object that will
	 * not be synchronized from LDAP.This value may be modified via the
	 * ldap-spring.xml configuration file.
	 *
	 * @param fieldNames
	 */
	public void setUserReservedFields(List<String> fieldNames) {
		for (String fieldName : fieldNames) {
			_reservedUserFields.put(fieldName, fieldName);
		}
	}

	protected void addAttributeMapping(
			String attributeName, String attributeValue, Attributes attrs) {

		if (Validator.isNotNull(attributeName) &&
				Validator.isNotNull(attributeValue)) {
			attrs.put(attributeName, attributeValue);
		}
	}

	protected void addModificationItem(
			String attributeName, String attributeValue,
			Modifications modifications) {

		if (Validator.isNotNull(attributeName) &&
				Validator.isNotNull(attributeValue)) {
			modifications.addItem(attributeName, attributeValue);
		}
	}

	protected Modifications getModifications(
		Object object, Properties objectMappings,
		Map<String, String> reservedFieldNames) {

		Modifications modifications = Modifications.getInstance();

		for (Map.Entry<Object, Object> entry : objectMappings.entrySet()) {

			String fieldName = (String)entry.getKey();

			if (reservedFieldNames.containsKey(fieldName)) {
				continue;
			}

			String ldapAttributeName = (String)entry.getValue();

			try {
				Object attributeValue = PropertyUtils.getProperty(
					object, fieldName);

				if (attributeValue != null) {
					addModificationItem(
						ldapAttributeName, attributeValue.toString(),
						modifications);
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to map field: " + fieldName +
						" of class " + object.getClass(), e);
				}
			}
		}

		return modifications;
	}

	private static final String _DEFAULT_DN = "cn";
	private static final String _USER_OBJECT_CLASS = "objectclass";

	private static Log _log = LogFactoryUtil.getLog(
		BasicLDAPToPortalConverter.class);

	private Map<String, String> _reservedContactFields =
		new HashMap<String, String>();

	private Map<String, String> _reservedUserFields =
		new HashMap<String, String>();

	private String _userDNFieldName;
}