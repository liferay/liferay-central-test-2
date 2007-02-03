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

package com.liferay.portlet.messageboards.model.impl;

import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PropsUtil;

import com.liferay.portlet.messageboards.service.persistence.MBStatsUserPK;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.sql.Types;

import java.util.Date;

/**
 * <a href="MBStatsUserModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBStatsUserModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "MBStatsUser";
	public static Object[][] TABLE_COLUMNS = {
			{ "groupId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.VARCHAR) },
			{ "messageCount", new Integer(Types.INTEGER) },
			{ "lastPostDate", new Integer(Types.VARCHAR) }
		};
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.messageboards.model.MBStatsUser"),
			XSS_ALLOW);
	public static boolean XSS_ALLOW_USERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.messageboards.model.MBStatsUser.userId"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.messageboards.model.MBStatsUserModel"));

	public MBStatsUserModelImpl() {
	}

	public MBStatsUserPK getPrimaryKey() {
		return new MBStatsUserPK(_groupId, _userId);
	}

	public void setPrimaryKey(MBStatsUserPK pk) {
		setGroupId(pk.groupId);
		setUserId(pk.userId);
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		if (groupId != _groupId) {
			_groupId = groupId;
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

	public int getMessageCount() {
		return _messageCount;
	}

	public void setMessageCount(int messageCount) {
		if (messageCount != _messageCount) {
			_messageCount = messageCount;
		}
	}

	public Date getLastPostDate() {
		return _lastPostDate;
	}

	public void setLastPostDate(Date lastPostDate) {
		if (((lastPostDate == null) && (_lastPostDate != null)) ||
				((lastPostDate != null) && (_lastPostDate == null)) ||
				((lastPostDate != null) && (_lastPostDate != null) &&
				!lastPostDate.equals(_lastPostDate))) {
			_lastPostDate = lastPostDate;
		}
	}

	public Object clone() {
		MBStatsUserImpl clone = new MBStatsUserImpl();
		clone.setGroupId(getGroupId());
		clone.setUserId(getUserId());
		clone.setMessageCount(getMessageCount());
		clone.setLastPostDate(getLastPostDate());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		MBStatsUserImpl mbStatsUser = (MBStatsUserImpl)obj;
		int value = 0;

		if (getMessageCount() < mbStatsUser.getMessageCount()) {
			value = -1;
		}
		else if (getMessageCount() > mbStatsUser.getMessageCount()) {
			value = 1;
		}
		else {
			value = 0;
		}

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

		MBStatsUserImpl mbStatsUser = null;

		try {
			mbStatsUser = (MBStatsUserImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		MBStatsUserPK pk = mbStatsUser.getPrimaryKey();

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

	private long _groupId;
	private String _userId;
	private int _messageCount;
	private Date _lastPostDate;
}