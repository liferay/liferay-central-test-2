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

import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.XSSUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.Date;

/**
 * <a href="UserTrackerModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>UserTracker</code> table in the
 * database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.model.UserTracker
 * @see com.liferay.portal.service.model.UserTrackerModel
 * @see com.liferay.portal.service.model.impl.UserTrackerImpl
 *
 */
public class UserTrackerModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "UserTracker";
	public static Object[][] TABLE_COLUMNS = {
			{ "userTrackerId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.BIGINT) },
			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			{ "sessionId", new Integer(Types.VARCHAR) },
			{ "remoteAddr", new Integer(Types.VARCHAR) },
			{ "remoteHost", new Integer(Types.VARCHAR) },
			{ "userAgent", new Integer(Types.VARCHAR) }
		};
	public static String TABLE_SQL_CREATE = "create table UserTracker (userTrackerId LONG not null primary key,companyId LONG,userId LONG,modifiedDate DATE null,sessionId VARCHAR(200) null,remoteAddr VARCHAR(75) null,remoteHost VARCHAR(75) null,userAgent VARCHAR(200) null)";
	public static String TABLE_SQL_DROP = "drop table UserTracker";
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.UserTracker"), XSS_ALLOW);
	public static boolean XSS_ALLOW_SESSIONID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.UserTracker.sessionId"),
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

	public long getPrimaryKey() {
		return _userTrackerId;
	}

	public void setPrimaryKey(long pk) {
		setUserTrackerId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_userTrackerId);
	}

	public long getUserTrackerId() {
		return _userTrackerId;
	}

	public void setUserTrackerId(long userTrackerId) {
		if (userTrackerId != _userTrackerId) {
			_userTrackerId = userTrackerId;
		}
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		if (companyId != _companyId) {
			_companyId = companyId;
		}
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		if (userId != _userId) {
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

	public String getSessionId() {
		return GetterUtil.getString(_sessionId);
	}

	public void setSessionId(String sessionId) {
		if (((sessionId == null) && (_sessionId != null)) ||
				((sessionId != null) && (_sessionId == null)) ||
				((sessionId != null) && (_sessionId != null) &&
				!sessionId.equals(_sessionId))) {
			if (!XSS_ALLOW_SESSIONID) {
				sessionId = XSSUtil.strip(sessionId);
			}

			_sessionId = sessionId;
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
		clone.setSessionId(getSessionId());
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
		long pk = userTracker.getPrimaryKey();

		if (getPrimaryKey() < pk) {
			return -1;
		}
		else if (getPrimaryKey() > pk) {
			return 1;
		}
		else {
			return 0;
		}
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

		long pk = userTracker.getPrimaryKey();

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

	private long _userTrackerId;
	private long _companyId;
	private long _userId;
	private Date _modifiedDate;
	private String _sessionId;
	private String _remoteAddr;
	private String _remoteHost;
	private String _userAgent;
}