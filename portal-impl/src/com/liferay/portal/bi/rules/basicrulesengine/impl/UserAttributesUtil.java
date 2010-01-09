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

package com.liferay.portal.bi.rules.basicrulesengine.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;

import java.io.Serializable;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * <a href="UserAttributesUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Prashant Dighe
 * @author Brian Wing Shun Chan
 *
 */
public class UserAttributesUtil
{
	public static String USER_ATTRIBUTE_PREFIX = "user.";

	public static String getAttribute(long userId, String attributeName) {
		String value = StringPool.BLANK;

		if (_log.isDebugEnabled()) {
			_log.debug("userId = " + userId + ", attribute = " + attributeName);
		}

		value = getAttribute(userId, attributeMap.get(attributeName));

		if (value == null) {
			value = getCustomAttribute(userId, attributeName);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(attributeName + " = " + value);
		}

		return value;
	}

	public static String getTimeZoneId(long userId) {
		String timeZoneId = getAttribute(
			userId, attributeMap.get(USER_ATTRIBUTE_PREFIX + "timeZoneId"));

		return timeZoneId;
	}

	protected static String getAttribute(long userId, Integer attributeId) {
		String value = "";

		if (userId <= 0 || Validator.isNull(attributeId)) {
			if (_log.isDebugEnabled()) {
				_log.debug("userId or attrId is null.");
			}

			return value;
		}

		int attrId = attributeId.intValue();

		try {
			User user = UserLocalServiceUtil.getUserById(userId);

			Contact contact = user.getContact();

			if (attrId == 1) {
				value = user.getCompanyMx();
			}
			else if (attrId == 2) {
				value = String.valueOf(contact.getAccountId());
			}
			else if (attrId == 3) {
				value = String.valueOf(contact.getParentContactId());
			}
			else if (attrId == 4) {
				value = user.getFirstName();
			}
			else if (attrId == 5) {
				value = user.getMiddleName();
			}
			else if (attrId == 6) {
				value = user.getLastName();
			}
			else if (attrId == 7) {
				value = String.valueOf(contact.getPrefixId());
			}
			else if (attrId == 8) {
				value = String.valueOf(contact.getSuffixId());
			}
			else if (attrId == 9) {
				value = String.valueOf(user.isMale());
			}
			else if (attrId == 10) {
				value = DateTimeUtil.formatDate(user.getBirthday());
			}
			else if (attrId == 11) {
				value = contact.getSmsSn();
			}
			else if (attrId == 12) {
				value = contact.getAimSn();
			}
			else if (attrId == 13) {
				value = contact.getFacebookSn();
			}
			else if (attrId == 14) {
				value = contact.getIcqSn();
			}
			else if (attrId == 15) {
				value = contact.getJabberSn();
			}
			else if (attrId == 16) {
				value = contact.getMsnSn();
			}
			else if (attrId == 17) {
				value = contact.getMySpaceSn();
			}
			else if (attrId == 18) {
				value = contact.getSkypeSn();
			}
			else if (attrId == 19) {
				value = contact.getTwitterSn();
			}
			else if (attrId == 20) {
				value = contact.getYmSn();
			}
			else if (attrId == 21) {
				value = contact.getEmployeeStatusId();
			}
			else if (attrId == 22) {
				value = contact.getEmployeeNumber();
			}
			else if (attrId == 23) {
				value = contact.getJobTitle();
			}
			else if (attrId == 24) {
				value = contact.getJobClass();
			}
			else if (attrId == 25) {
				value = contact.getHoursOfOperation();
			}
			else if (attrId == 26) {
				value = user.getUuid();
			}
			else if (attrId == 27) {
				value = String.valueOf(userId);
			}
			else if (attrId == 28) {
				value = String.valueOf(user.getCompanyId());
			}
			else if (attrId == 29) {
				value = DateTimeUtil.formatDate(user.getCreateDate());
			}
			else if (attrId == 30) {
				value = DateTimeUtil.formatDate(user.getModifiedDate());
			}
			else if (attrId == 31) {
				value = String.valueOf(contact.getContactId());
			}
			else if (attrId == 32) {
				value = String.valueOf(user.getPasswordReset());
			}
			else if (attrId == 33) {
				value = String.valueOf(user.getPasswordModified());
			}
			else if (attrId == 34) {
				value = String.valueOf(user.getGraceLoginCount());
			}
			else if (attrId == 35) {
				value = user.getScreenName();
			}
			else if (attrId == 36) {
				value = user.getEmailAddress();
			}
			else if (attrId == 37) {
				value = user.getOpenId();
			}
			else if (attrId == 38) {
				value = String.valueOf(user.getPortraitId());
			}
			else if (attrId == 39) {
				value = user.getLanguageId();
			}
			else if (attrId == 40) {
				value = user.getTimeZoneId();
			}
			else if (attrId == 41) {
				value = user.getGreeting();
			}
			else if (attrId == 42) {
				value = user.getComments();
			}
			else if (attrId == 43) {
				value = DateTimeUtil.formatDate(user.getLoginDate());
			}
			else if (attrId == 44) {
				value = user.getLoginIP();
			}
			else if (attrId == 45) {
				value = DateTimeUtil.formatDate(user.getLastLoginDate());
			}
			else if (attrId == 46) {
				value = user.getLastLoginIP();
			}
			else if (attrId == 47) {
				value = DateTimeUtil.formatDate(user.getLastFailedLoginDate());
			}
			else if (attrId == 48) {
				value = String.valueOf(user.getFailedLoginAttempts());
			}
			else if (attrId == 49) {
				value = String.valueOf(user.getAgreedToTermsOfUse());
			}
			else if (attrId == 50) {
				Date dob = user.getBirthday();

				Date today = new Date();

				TimeZone timeZone = user.getTimeZone();

				Calendar ageCal = Calendar.getInstance(timeZone);

				Date ageDate = new Date(today.getTime() - dob.getTime());

				ageCal.setTime(ageDate);

				value = (ageCal.get(Calendar.YEAR) - 1970) +
						"." + ageCal.get(Calendar.MONTH);
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug("No matching attribute, returning empty");
				}
				value = "";
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

	protected static String getCustomAttribute(
		long userId, String attributeName) {

		String value = null;

		String name = attributeName.replace(USER_ATTRIBUTE_PREFIX, "");

		String className = "com.liferay.portal.model.User";

		ExpandoBridge expandoBridge = new
			ExpandoBridgeImpl(className, userId);

		int type = expandoBridge.getAttributeType(name);

		Serializable v = expandoBridge.getAttribute(name);

		if (type == ExpandoColumnConstants.BOOLEAN_ARRAY) {
			 value = String.valueOf(((Boolean[])v)[0]);
		}
		else if (type == ExpandoColumnConstants.DATE) {
			value = DateTimeUtil.formatDate((Date)v);
		}
		else if (type == ExpandoColumnConstants.DATE_ARRAY) {
			value = DateTimeUtil.formatDate(((Date[])v)[0]);
		}
		else if (type == ExpandoColumnConstants.DOUBLE_ARRAY) {
			value = String.valueOf(((double[])v)[0]);
		}
		else if (type == ExpandoColumnConstants.FLOAT_ARRAY) {
			value = String.valueOf(((float[])v)[0]);
		}
		else if (type == ExpandoColumnConstants.INTEGER_ARRAY) {
			value = String.valueOf(((int[])v)[0]);
		}
		else if (type == ExpandoColumnConstants.LONG_ARRAY) {
			value = String.valueOf(((long[])v)[0]);
		}
		else if (type == ExpandoColumnConstants.SHORT_ARRAY) {
			value = String.valueOf(((short[])v)[0]);
		}
		else if (type == ExpandoColumnConstants.STRING_ARRAY) {
			value = ((String[])v)[0];
		}
		else {
			value = String.valueOf(v);
		}

		return value;
	}

	protected static Map<String, Integer> attributeMap =
		new HashMap<String, Integer>();

	static {
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "companyMx",			1);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "accountId",			2);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "parentContactId",	3);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "firstName",			4);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "middleName",			5);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "lastName",			6);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "prefixId",			7);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "suffixId",			8);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "male",				9);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "birthday",			10);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "smsSn",				11);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "aimSn",				12);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "facebookSn",			13);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "icqSn",				14);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "jabberSn",			15);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "msnSn",				16);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "mySpaceSn",			17);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "skypeSn",			18);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "twitterSn",			19);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "ymSn",				20);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "employeeStatusId",	21);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "employeeNumber",		22);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "jobTitle",			23);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "jobClass",			24);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "hoursOfOperation",	25);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "uuid",				26);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "userId",				27);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "companyId",			28);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "createDate",			29);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "modifiedDate",		30);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "contactId",			31);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "passwordReset",		32);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "passwordModifiedDate",33);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "graceLoginCount",	34);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "screenName",			35);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "emailAddress",		36);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "openId",				37);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "portraitId",			38);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "languageId",			39);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "timeZoneId",			40);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "greeting",			41);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "comments",			42);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "loginDate",			43);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "loginIP",			44);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "lastLoginDate",		45);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "lastLoginIP",		46);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "lastFailedLoginDate",47);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "failedLoginAttempts",48);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "agreedToTermsOfUse",	49);
		attributeMap.put(USER_ATTRIBUTE_PREFIX + "age",				50);
	}

	private static Log _log =
		 LogFactoryUtil.getLog(UserAttributesUtil.class);

}