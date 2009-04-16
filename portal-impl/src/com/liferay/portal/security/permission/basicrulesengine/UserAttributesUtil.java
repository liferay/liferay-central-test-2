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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2009 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.security.permission.basicrulesengine;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;
import java.io.Serializable;
import java.util.Date;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.HashMap;
import java.util.Map;

/**
 * <a href="UserAttributesUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Prashant Dighe
 * @author Brian Wing Shun Chan
 *
 */
public class UserAttributesUtil
{
	public static String USER_ATTR_PREFIX = "user.attr.";
	public static String USER_ATTR_CUSTOM_PREFIX = "user.attr.custom.";

	public static String getAttribute(long userId, String attr) {

		String value = "";

		if (_log.isDebugEnabled()) {
			_log.debug("userId = " + userId + ", attribute = " + attr);
		}

		if(attr.startsWith(USER_ATTR_CUSTOM_PREFIX)) {

			value = _getCustomAttribute(userId, attr);
		}
		else if (attr.startsWith(USER_ATTR_PREFIX)) {

			value = _getAttribute(userId, _attrbiuteMap.get(attr));
		}

		return value;
	}

	public static String getTimeZoneId(long userId) {

		String timeZoneId = _getAttribute(
			userId, _attrbiuteMap.get(USER_ATTR_PREFIX + "timeZoneId"));
		
		return timeZoneId;
	}

	protected static String _getAttribute(long userId, Integer attrId) {

		String value = "";

		if (userId <= 0 || Validator.isNull(attrId)) {

			if (_log.isDebugEnabled()) {
				_log.debug("userId or attrId is null.");
			}
			
			return value;
		}

		try {

			User user = UserLocalServiceUtil.getUserById(userId);
			Contact contact = user.getContact();

			switch (attrId) {

				case 1:
					value = user.getCompanyMx();
					break;

				case 2:
					value = "" + contact.getAccountId();
					break;

				case 3: 
					value = "" + contact.getParentContactId();
					break;

				case 4:
					value = user.getFirstName();
					break;

				case 5:
					value = user.getMiddleName();
					break;

				case 6:
					value = user.getLastName();
					break;

				case 7:
					value = "" + contact.getPrefixId();
					break;

				case 8:
					value = "" + contact.getSuffixId();
					break;

				case 9:
					value = "" + user.isMale();
					break;

				case 10:
					value = DateTimeUtil.formatDate(user.getBirthday());
					break;

				case 11:
					value = contact.getSmsSn();
					break;

				case 12:
					value = contact.getAimSn();
					break;

				case 13:
					value = contact.getFacebookSn();
					break;

				case 14:
					value = contact.getIcqSn();
					break;

				case 15:
					value = contact.getJabberSn();
					break;

				case 16:
					value = contact.getMsnSn();
					break;

				case 17:
					value = contact.getMySpaceSn();
					break;

				case 18:
					value = contact.getSkypeSn();
					break;

				case 19:
					value = contact.getTwitterSn();
					break;

				case 20:
					value = contact.getYmSn();
					break;

				case 21:
					value = contact.getEmployeeStatusId();
					break;

				case 22:
					value = contact.getEmployeeNumber();
					break;

				case 23:
					value = contact.getJobTitle();
					break;

				case 24:
					value = contact.getJobClass();
					break;

				case 25:
					value = contact.getHoursOfOperation();
					break;

				case 26:
					value = user.getUuid();
					break;

				case 27:
					value = "" + userId;
					break;

				case 28:
					value = "" + user.getCompanyId();
					break;

				case 29:
					value = DateTimeUtil.formatDate(user.getCreateDate());
					break;

				case 30:
					value = DateTimeUtil.formatDate(user.getModifiedDate());
					break;

				case 31:
					value = "" + contact.getContactId();
					break;

				case 32:
					value = "" + user.getPasswordReset();
					break;

				case 33:
					value = "" + user.getPasswordModified();
					break;

				case 34:
					value = "" + user.getGraceLoginCount();
					break;

				case 35:
					value = user.getScreenName();
					break;

				case 36:
					value = user.getEmailAddress();
					break;

				case 37:
					value = user.getOpenId();
					break;

				case 38:
					value = "" + user.getPortraitId();
					break;

				case 39:
					value = user.getLanguageId();
					break;

				case 40:
					value = user.getTimeZoneId();
					break;

				case 41:
					value = user.getGreeting();
					break;

				case 42:
					value = user.getComments();
					break;
					
				case 43:
					value = DateTimeUtil.formatDate(user.getLoginDate());
					break;

				case 44:
					value = user.getLoginIP();
					break;

				case 45:
					value = DateTimeUtil.formatDate(user.getLastLoginDate());
					break;

				case 46:
					value = user.getLastLoginIP();
					break;

				case 47:
					value = 
						DateTimeUtil.formatDate(user.getLastFailedLoginDate());
					break;

				case 48:
					value = "" + user.getFailedLoginAttempts();
					break;

				case 49:
					value = "" + user.getAgreedToTermsOfUse();
					break;

				case 50:
					Date dob = user.getBirthday();
					Date today = new Date();
					TimeZone timeZone = user.getTimeZone();
					Calendar ageCal = Calendar.getInstance(timeZone);
					Date ageDate = new Date(today.getTime() - dob.getTime());
					ageCal.setTime(ageDate);
					value = (ageCal.get(Calendar.YEAR) - 1970) +
						"." + ageCal.get(Calendar.MONTH);
					break;

				default:
					if (_log.isDebugEnabled()) {
						_log.debug("No matching attribute, returning empty");
					}
					value = "";
					break;
			}

		}
		catch (PortalException ex) {
			_log.error(ex);
		}
		catch (SystemException ex) {
			_log.error(ex);
		}

		return value;
	}

	protected static String _getCustomAttribute(long userId, String attr) {

		String value = null;

		String name = attr.replace(USER_ATTR_CUSTOM_PREFIX, "");

		String className = "com.liferay.portal.model.User";

		ExpandoBridge expandoBridge = new
			ExpandoBridgeImpl(className, userId);

		int type = expandoBridge.getAttributeType(name);

		Serializable v = expandoBridge.getAttribute(name);

		if (type == ExpandoColumnConstants.BOOLEAN_ARRAY) {
			 value = "" + ((Boolean[])v)[0];
		}
		else if (type == ExpandoColumnConstants.DATE) {
			value = DateTimeUtil.formatDate((Date)v);
		}
		else if (type == ExpandoColumnConstants.DATE_ARRAY) {
			value = DateTimeUtil.formatDate(((Date[])v)[0]);
		}
		else if (type == ExpandoColumnConstants.DOUBLE_ARRAY) {
			value = "" + ((double[])v)[0];
		}
		else if (type == ExpandoColumnConstants.FLOAT_ARRAY) {
			value = "" + ((float[])v)[0];
		}
		else if (type == ExpandoColumnConstants.INTEGER_ARRAY) {
			value = "" + ((int[])v)[0];
		}
		else if (type == ExpandoColumnConstants.LONG_ARRAY) {
			value = "" + ((long[])v)[0];
		}
		else if (type == ExpandoColumnConstants.SHORT_ARRAY) {
			value = "" + ((short[])v)[0];
		}
		else if (type == ExpandoColumnConstants.STRING_ARRAY) {
			value = ((String[])v)[0];
		}
		else {
			value = "" + v;
		}

		return value;
	}

	protected static Map<String, Integer> _attrbiuteMap =
		new HashMap<String, Integer>();

	static {
		_attrbiuteMap.put(USER_ATTR_PREFIX + "companyMx",			1);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "accountId",			2);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "parentContactId",		3);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "firstName",			4);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "middleName",			5);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "lastName",			6);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "prefixId",			7);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "suffixId",			8);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "male",				9);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "birthday",			10);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "smsSn",				11);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "aimSn",				12);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "facebookSn",			13);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "icqSn",				14);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "jabberSn",			15);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "msnSn",				16);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "mySpaceSn",			17);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "skypeSn",				18);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "twitterSn",			19);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "ymSn",				20);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "employeeStatusId",	21);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "employeeNumber",		22);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "jobTitle",			23);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "jobClass",			24);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "hoursOfOperation",	25);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "uuid",				26);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "userId",				27);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "companyId",			28);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "createDate",			29);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "modifiedDate",		30);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "contactId",			31);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "passwordReset",		32);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "passwordModifiedDate",33);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "graceLoginCount",		34);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "screenName",			35);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "emailAddress",		36);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "openId",				37);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "portraitId",			38);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "languageId",			39);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "timeZoneId",			40);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "greeting",			41);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "comments",			42);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "loginDate",			43);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "loginIP",				44);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "lastLoginDate",		45);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "lastLoginIP",			46);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "lastFailedLoginDate",	47);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "failedLoginAttempts",	48);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "agreedToTermsOfUse",	49);
		_attrbiuteMap.put(USER_ATTR_PREFIX + "age",					50);
	}

	private static Log _log =
		 LogFactoryUtil.getLog(UserAttributesUtil.class);
}