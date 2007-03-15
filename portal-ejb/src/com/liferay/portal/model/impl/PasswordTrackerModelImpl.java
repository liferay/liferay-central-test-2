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

import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.DateUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.sql.Types;

import java.util.Date;

/**
 * <a href="PasswordTrackerModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>PasswordTracker</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.model.PasswordTracker
 * @see com.liferay.portal.service.model.PasswordTrackerModel
 * @see com.liferay.portal.service.model.impl.PasswordTrackerImpl
 *
 */
public class PasswordTrackerModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "PasswordTracker";
	public static Object[][] TABLE_COLUMNS = {
			{ "passwordTrackerId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.VARCHAR) },
			{ "createDate", new Integer(Types.TIMESTAMP) },
			{ "password_", new Integer(Types.VARCHAR) }
		};
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.PasswordTracker"), XSS_ALLOW);
	public static boolean XSS_ALLOW_USERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.PasswordTracker.userId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_PASSWORD = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.PasswordTracker.password"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.PasswordTrackerModel"));

	public PasswordTrackerModelImpl() {
	}

	public long getPrimaryKey() {
		return _passwordTrackerId;
	}

	public void setPrimaryKey(long pk) {
		setPasswordTrackerId(pk);
	}

	public long getPasswordTrackerId() {
		return _passwordTrackerId;
	}

	public void setPasswordTrackerId(long passwordTrackerId) {
		if (passwordTrackerId != _passwordTrackerId) {
			_passwordTrackerId = passwordTrackerId;
		}
	}

	public String getUserId() {
		return GetterUtil.getString(_userId);
	}

	public void setUserId(String userId) {
		if (((userId == null) && (_userId != null)) ||
				((userId != null) && (_userId == null)) ||
				((userId != null) && (_userId != null) &&
				!userId.equals(_userId))) {
			if (!XSS_ALLOW_USERID) {
				userId = XSSUtil.strip(userId);
			}

			_userId = userId;
		}
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		if (((createDate == null) && (_createDate != null)) ||
				((createDate != null) && (_createDate == null)) ||
				((createDate != null) && (_createDate != null) &&
				!createDate.equals(_createDate))) {
			_createDate = createDate;
		}
	}

	public String getPassword() {
		return GetterUtil.getString(_password);
	}

	public void setPassword(String password) {
		if (((password == null) && (_password != null)) ||
				((password != null) && (_password == null)) ||
				((password != null) && (_password != null) &&
				!password.equals(_password))) {
			if (!XSS_ALLOW_PASSWORD) {
				password = XSSUtil.strip(password);
			}

			_password = password;
		}
	}

	public Object clone() {
		PasswordTrackerImpl clone = new PasswordTrackerImpl();
		clone.setPasswordTrackerId(getPasswordTrackerId());
		clone.setUserId(getUserId());
		clone.setCreateDate(getCreateDate());
		clone.setPassword(getPassword());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		PasswordTrackerImpl passwordTracker = (PasswordTrackerImpl)obj;
		int value = 0;
		value = getUserId().compareTo(passwordTracker.getUserId());
		value = value * -1;

		if (value != 0) {
			return value;
		}

		value = DateUtil.compareTo(getCreateDate(),
				passwordTracker.getCreateDate());
		value = value * -1;

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		PasswordTrackerImpl passwordTracker = null;

		try {
			passwordTracker = (PasswordTrackerImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = passwordTracker.getPrimaryKey();

		if (getPrimaryKey() == pk) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (int)getPrimaryKey();
	}

	private long _passwordTrackerId;
	private String _userId;
	private Date _createDate;
	private String _password;
}