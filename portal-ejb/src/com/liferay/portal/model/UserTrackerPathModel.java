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

package com.liferay.portal.model;

import com.liferay.portal.model.BaseModel;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.util.Date;

/**
 * <a href="UserTrackerPathModel.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class UserTrackerPathModel extends BaseModel {
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.UserTrackerPath"), XSS_ALLOW);
	public static boolean XSS_ALLOW_USERTRACKERPATHID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.UserTrackerPath.userTrackerPathId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERTRACKERID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.UserTrackerPath.userTrackerId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_PATH = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.UserTrackerPath.path"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.UserTrackerPathModel"));

	public UserTrackerPathModel() {
	}

	public String getPrimaryKey() {
		return _userTrackerPathId;
	}

	public void setPrimaryKey(String pk) {
		setUserTrackerPathId(pk);
	}

	public String getUserTrackerPathId() {
		return GetterUtil.getString(_userTrackerPathId);
	}

	public void setUserTrackerPathId(String userTrackerPathId) {
		if (((userTrackerPathId == null) && (_userTrackerPathId != null)) ||
				((userTrackerPathId != null) && (_userTrackerPathId == null)) ||
				((userTrackerPathId != null) && (_userTrackerPathId != null) &&
				!userTrackerPathId.equals(_userTrackerPathId))) {
			if (!XSS_ALLOW_USERTRACKERPATHID) {
				userTrackerPathId = XSSUtil.strip(userTrackerPathId);
			}

			_userTrackerPathId = userTrackerPathId;
			setModified(true);
		}
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
			setModified(true);
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
			setModified(true);
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
			setModified(true);
		}
	}

	public Object clone() {
		UserTrackerPath clone = new UserTrackerPath();
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

		UserTrackerPath userTrackerPath = (UserTrackerPath)obj;
		String pk = userTrackerPath.getPrimaryKey();

		return getPrimaryKey().compareTo(pk);
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		UserTrackerPath userTrackerPath = null;

		try {
			userTrackerPath = (UserTrackerPath)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		String pk = userTrackerPath.getPrimaryKey();

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

	private String _userTrackerPathId;
	private String _userTrackerId;
	private String _path;
	private Date _pathDate;
}