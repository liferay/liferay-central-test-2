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

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.sql.Types;

import java.util.Date;

/**
 * <a href="UserTrackerModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UserTrackerModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "UserTracker";
	public static Object[][] TABLE_COLUMNS = {
			{ "userTrackerId", new Integer(Types.VARCHAR) },
			{ "companyId", new Integer(Types.VARCHAR) },
			{ "userId", new Integer(Types.VARCHAR) },
			{ "modifiedDate", new Integer(Types.VARCHAR) },
			{ "remoteAddr", new Integer(Types.VARCHAR) },
			{ "remoteHost", new Integer(Types.VARCHAR) },
			{ "userAgent", new Integer(Types.VARCHAR) }
		};
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.UserTracker"), XSS_ALLOW);
	public static boolean XSS_ALLOW_USERTRACKERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.UserTracker.userTrackerId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COMPANYID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.UserTracker.companyId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.UserTracker.userId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_REMOTEADDR = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.UserTracker.remoteAddr"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_REMOTEHOST = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.UserTracker.remoteHost"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERAGENT = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.UserTracker.userAgent"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.UserTrackerModel"));

	public UserTrackerModelImpl() {
	}

	public String getPrimaryKey() {
		return _userTrackerId;
	}

	public void setPrimaryKey(String pk) {
		setUserTrackerId(pk);
	}

	public String getUserTrackerId() {
		return GetterUtil.getString(_userTrackerId);
	}

	public void setUserTrackerId(String userTrackerId) {
		if (((userTrackerId == null) && (_userTrackerId != null)) ||
				((userTrackerId != null) && (_userTrackerId == null)) ||
				((userTrackerId != null) && (_userTrackerId != null) &&
				!userTrackerId.equals(_userTrackerId))) {
			if (!XSS_ALLOW_USERTRACKERID) {
				userTrackerId = XSSUtil.strip(userTrackerId);
			}

			_userTrackerId = userTrackerId;
		}
	}

	public String getCompanyId() {
		return GetterUtil.getString(_companyId);
	}

	public void setCompanyId(String companyId) {
		if (((companyId == null) && (_companyId != null)) ||
				((companyId != null) && (_companyId == null)) ||
				((companyId != null) && (_companyId != null) &&
				!companyId.equals(_companyId))) {
			if (!XSS_ALLOW_COMPANYID) {
				companyId = XSSUtil.strip(companyId);
			}

			_companyId = companyId;
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

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		if (((modifiedDate == null) && (_modifiedDate != null)) ||
				((modifiedDate != null) && (_modifiedDate == null)) ||
				((modifiedDate != null) && (_modifiedDate != null) &&
				!modifiedDate.equals(_modifiedDate))) {
			_modifiedDate = modifiedDate;
		}
	}

	public String getRemoteAddr() {
		return GetterUtil.getString(_remoteAddr);
	}

	public void setRemoteAddr(String remoteAddr) {
		if (((remoteAddr == null) && (_remoteAddr != null)) ||
				((remoteAddr != null) && (_remoteAddr == null)) ||
				((remoteAddr != null) && (_remoteAddr != null) &&
				!remoteAddr.equals(_remoteAddr))) {
			if (!XSS_ALLOW_REMOTEADDR) {
				remoteAddr = XSSUtil.strip(remoteAddr);
			}

			_remoteAddr = remoteAddr;
		}
	}

	public String getRemoteHost() {
		return GetterUtil.getString(_remoteHost);
	}

	public void setRemoteHost(String remoteHost) {
		if (((remoteHost == null) && (_remoteHost != null)) ||
				((remoteHost != null) && (_remoteHost == null)) ||
				((remoteHost != null) && (_remoteHost != null) &&
				!remoteHost.equals(_remoteHost))) {
			if (!XSS_ALLOW_REMOTEHOST) {
				remoteHost = XSSUtil.strip(remoteHost);
			}

			_remoteHost = remoteHost;
		}
	}

	public String getUserAgent() {
		return GetterUtil.getString(_userAgent);
	}

	public void setUserAgent(String userAgent) {
		if (((userAgent == null) && (_userAgent != null)) ||
				((userAgent != null) && (_userAgent == null)) ||
				((userAgent != null) && (_userAgent != null) &&
				!userAgent.equals(_userAgent))) {
			if (!XSS_ALLOW_USERAGENT) {
				userAgent = XSSUtil.strip(userAgent);
			}

			_userAgent = userAgent;
		}
	}

	public Object clone() {
		UserTrackerImpl clone = new UserTrackerImpl();
		clone.setUserTrackerId(getUserTrackerId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setModifiedDate(getModifiedDate());
		clone.setRemoteAddr(getRemoteAddr());
		clone.setRemoteHost(getRemoteHost());
		clone.setUserAgent(getUserAgent());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		UserTrackerImpl userTracker = (UserTrackerImpl)obj;
		String pk = userTracker.getPrimaryKey();

		return getPrimaryKey().compareTo(pk);
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		UserTrackerImpl userTracker = null;

		try {
			userTracker = (UserTrackerImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		String pk = userTracker.getPrimaryKey();

		if (getPrimaryKey().equals(pk)) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return getPrimaryKey().hashCode();
	}

	private String _userTrackerId;
	private String _companyId;
	private String _userId;
	private Date _modifiedDate;
	private String _remoteAddr;
	private String _remoteHost;
	private String _userAgent;
}