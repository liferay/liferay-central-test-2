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
 * <a href="UserTrackerPathModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>UserTrackerPath</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.model.UserTrackerPath
 * @see com.liferay.portal.service.model.UserTrackerPathModel
 * @see com.liferay.portal.service.model.impl.UserTrackerPathImpl
 *
 */
public class UserTrackerPathModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "UserTrackerPath";
	public static Object[][] TABLE_COLUMNS = {
			{ "userTrackerPathId", new Integer(Types.BIGINT) },
			{ "userTrackerId", new Integer(Types.BIGINT) },
			{ "path", new Integer(Types.VARCHAR) },
			{ "pathDate", new Integer(Types.TIMESTAMP) }
		};
	public static String TABLE_SQL_CREATE = "create table UserTrackerPath (userTrackerPathId LONG not null primary key,userTrackerId LONG,path STRING null,pathDate DATE null)";
	public static String TABLE_SQL_DROP = "drop table UserTrackerPath";
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.UserTrackerPath"), XSS_ALLOW);
	public static boolean XSS_ALLOW_PATH = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.UserTrackerPath.path"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.UserTrackerPathModel"));

	public UserTrackerPathModelImpl() {
	}

	public long getPrimaryKey() {
		return _userTrackerPathId;
	}

	public void setPrimaryKey(long pk) {
		setUserTrackerPathId(pk);
	}

	public long getUserTrackerPathId() {
		return _userTrackerPathId;
	}

	public void setUserTrackerPathId(long userTrackerPathId) {
		if (userTrackerPathId != _userTrackerPathId) {
			_userTrackerPathId = userTrackerPathId;
		}
	}

	public long getUserTrackerId() {
		return _userTrackerId;
	}

	public void setUserTrackerId(long userTrackerId) {
		if (userTrackerId != _userTrackerId) {
			_userTrackerId = userTrackerId;
		}
	}

	public String getPath() {
		return GetterUtil.getString(_path);
	}

	public void setPath(String path) {
		if (((path == null) && (_path != null)) ||
				((path != null) && (_path == null)) ||
				((path != null) && (_path != null) && !path.equals(_path))) {
			if (!XSS_ALLOW_PATH) {
				path = XSSUtil.strip(path);
			}

			_path = path;
		}
	}

	public Date getPathDate() {
		return _pathDate;
	}

	public void setPathDate(Date pathDate) {
		if (((pathDate == null) && (_pathDate != null)) ||
				((pathDate != null) && (_pathDate == null)) ||
				((pathDate != null) && (_pathDate != null) &&
				!pathDate.equals(_pathDate))) {
			_pathDate = pathDate;
		}
	}

	public Object clone() {
		UserTrackerPathImpl clone = new UserTrackerPathImpl();
		clone.setUserTrackerPathId(getUserTrackerPathId());
		clone.setUserTrackerId(getUserTrackerId());
		clone.setPath(getPath());
		clone.setPathDate(getPathDate());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		UserTrackerPathImpl userTrackerPath = (UserTrackerPathImpl)obj;
		long pk = userTrackerPath.getPrimaryKey();

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

		UserTrackerPathImpl userTrackerPath = null;

		try {
			userTrackerPath = (UserTrackerPathImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = userTrackerPath.getPrimaryKey();

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

	private long _userTrackerPathId;
	private long _userTrackerId;
	private String _path;
	private Date _pathDate;
}