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
 * <a href="ReleaseModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ReleaseModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "Release_";
	public static Object[][] TABLE_COLUMNS = {
			{ "releaseId", new Integer(Types.VARCHAR) },
			{ "createDate", new Integer(Types.VARCHAR) },
			{ "modifiedDate", new Integer(Types.VARCHAR) },
			{ "buildNumber", new Integer(Types.INTEGER) },
			{ "buildDate", new Integer(Types.VARCHAR) },
			{ "verified", new Integer(Types.BOOLEAN) }
		};
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Release"), XSS_ALLOW);
	public static boolean XSS_ALLOW_RELEASEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Release.releaseId"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.ReleaseModel"));

	public ReleaseModelImpl() {
	}

	public String getPrimaryKey() {
		return _releaseId;
	}

	public void setPrimaryKey(String pk) {
		setReleaseId(pk);
	}

	public String getReleaseId() {
		return GetterUtil.getString(_releaseId);
	}

	public void setReleaseId(String releaseId) {
		if (((releaseId == null) && (_releaseId != null)) ||
				((releaseId != null) && (_releaseId == null)) ||
				((releaseId != null) && (_releaseId != null) &&
				!releaseId.equals(_releaseId))) {
			if (!XSS_ALLOW_RELEASEID) {
				releaseId = XSSUtil.strip(releaseId);
			}

			_releaseId = releaseId;
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

	public int getBuildNumber() {
		return _buildNumber;
	}

	public void setBuildNumber(int buildNumber) {
		if (buildNumber != _buildNumber) {
			_buildNumber = buildNumber;
		}
	}

	public Date getBuildDate() {
		return _buildDate;
	}

	public void setBuildDate(Date buildDate) {
		if (((buildDate == null) && (_buildDate != null)) ||
				((buildDate != null) && (_buildDate == null)) ||
				((buildDate != null) && (_buildDate != null) &&
				!buildDate.equals(_buildDate))) {
			_buildDate = buildDate;
		}
	}

	public boolean getVerified() {
		return _verified;
	}

	public boolean isVerified() {
		return _verified;
	}

	public void setVerified(boolean verified) {
		if (verified != _verified) {
			_verified = verified;
		}
	}

	public Object clone() {
		ReleaseImpl clone = new ReleaseImpl();
		clone.setReleaseId(getReleaseId());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setBuildNumber(getBuildNumber());
		clone.setBuildDate(getBuildDate());
		clone.setVerified(getVerified());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		ReleaseImpl release = (ReleaseImpl)obj;
		String pk = release.getPrimaryKey();

		return getPrimaryKey().compareTo(pk);
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		ReleaseImpl release = null;

		try {
			release = (ReleaseImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		String pk = release.getPrimaryKey();

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

	private String _releaseId;
	private Date _createDate;
	private Date _modifiedDate;
	private int _buildNumber;
	private Date _buildDate;
	private boolean _verified;
}