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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PropsUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.Date;

/**
 * <a href="ReleaseModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>Release_</code> table in the
 * database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.model.Release
 * @see com.liferay.portal.service.model.ReleaseModel
 * @see com.liferay.portal.service.model.impl.ReleaseImpl
 *
 */
public class ReleaseModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "Release_";
	public static Object[][] TABLE_COLUMNS = {
			{ "releaseId", new Integer(Types.BIGINT) },
			{ "createDate", new Integer(Types.TIMESTAMP) },
			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			{ "buildNumber", new Integer(Types.INTEGER) },
			{ "buildDate", new Integer(Types.TIMESTAMP) },
			{ "verified", new Integer(Types.BOOLEAN) }
		};
	public static String TABLE_SQL_CREATE = "create table Release_ (releaseId LONG not null primary key,createDate DATE null,modifiedDate DATE null,buildNumber INTEGER,buildDate DATE null,verified BOOLEAN)";
	public static String TABLE_SQL_DROP = "drop table Release_";
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Release"), XSS_ALLOW);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.ReleaseModel"));

	public ReleaseModelImpl() {
	}

	public long getPrimaryKey() {
		return _releaseId;
	}

	public void setPrimaryKey(long pk) {
		setReleaseId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_releaseId);
	}

	public long getReleaseId() {
		return _releaseId;
	}

	public void setReleaseId(long releaseId) {
		if (releaseId != _releaseId) {
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
		long pk = release.getPrimaryKey();

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

		ReleaseImpl release = null;

		try {
			release = (ReleaseImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = release.getPrimaryKey();

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

	private long _releaseId;
	private Date _createDate;
	private Date _modifiedDate;
	private int _buildNumber;
	private Date _buildDate;
	private boolean _verified;
}