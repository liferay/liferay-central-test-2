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
 * <a href="DataTrackerModel.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class DataTrackerModel extends BaseModel {
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.DataTracker"), XSS_ALLOW);
	public static boolean XSS_ALLOW_DATATRACKERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.DataTracker.dataTrackerId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COMPANYID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.DataTracker.companyId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CREATEDBYUSERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.DataTracker.createdByUserId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CREATEDBYUSERNAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.DataTracker.createdByUserName"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_UPDATEDBY = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.DataTracker.updatedBy"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CLASSNAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.DataTracker.className"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CLASSPK = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.DataTracker.classPK"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.DataTrackerModel"));

	public DataTrackerModel() {
	}

	public String getPrimaryKey() {
		return _dataTrackerId;
	}

	public void setPrimaryKey(String pk) {
		setDataTrackerId(pk);
	}

	public String getDataTrackerId() {
		return GetterUtil.getString(_dataTrackerId);
	}

	public void setDataTrackerId(String dataTrackerId) {
		if (((dataTrackerId == null) && (_dataTrackerId != null)) ||
				((dataTrackerId != null) && (_dataTrackerId == null)) ||
				((dataTrackerId != null) && (_dataTrackerId != null) &&
				!dataTrackerId.equals(_dataTrackerId))) {
			if (!XSS_ALLOW_DATATRACKERID) {
				dataTrackerId = XSSUtil.strip(dataTrackerId);
			}

			_dataTrackerId = dataTrackerId;
			setModified(true);
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
			setModified(true);
		}
	}

	public Date getCreatedOn() {
		return _createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		if (((createdOn == null) && (_createdOn != null)) ||
				((createdOn != null) && (_createdOn == null)) ||
				((createdOn != null) && (_createdOn != null) &&
				!createdOn.equals(_createdOn))) {
			_createdOn = createdOn;
			setModified(true);
		}
	}

	public String getCreatedByUserId() {
		return GetterUtil.getString(_createdByUserId);
	}

	public void setCreatedByUserId(String createdByUserId) {
		if (((createdByUserId == null) && (_createdByUserId != null)) ||
				((createdByUserId != null) && (_createdByUserId == null)) ||
				((createdByUserId != null) && (_createdByUserId != null) &&
				!createdByUserId.equals(_createdByUserId))) {
			if (!XSS_ALLOW_CREATEDBYUSERID) {
				createdByUserId = XSSUtil.strip(createdByUserId);
			}

			_createdByUserId = createdByUserId;
			setModified(true);
		}
	}

	public String getCreatedByUserName() {
		return GetterUtil.getString(_createdByUserName);
	}

	public void setCreatedByUserName(String createdByUserName) {
		if (((createdByUserName == null) && (_createdByUserName != null)) ||
				((createdByUserName != null) && (_createdByUserName == null)) ||
				((createdByUserName != null) && (_createdByUserName != null) &&
				!createdByUserName.equals(_createdByUserName))) {
			if (!XSS_ALLOW_CREATEDBYUSERNAME) {
				createdByUserName = XSSUtil.strip(createdByUserName);
			}

			_createdByUserName = createdByUserName;
			setModified(true);
		}
	}

	public Date getUpdatedOn() {
		return _updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		if (((updatedOn == null) && (_updatedOn != null)) ||
				((updatedOn != null) && (_updatedOn == null)) ||
				((updatedOn != null) && (_updatedOn != null) &&
				!updatedOn.equals(_updatedOn))) {
			_updatedOn = updatedOn;
			setModified(true);
		}
	}

	public String getUpdatedBy() {
		return GetterUtil.getString(_updatedBy);
	}

	public void setUpdatedBy(String updatedBy) {
		if (((updatedBy == null) && (_updatedBy != null)) ||
				((updatedBy != null) && (_updatedBy == null)) ||
				((updatedBy != null) && (_updatedBy != null) &&
				!updatedBy.equals(_updatedBy))) {
			if (!XSS_ALLOW_UPDATEDBY) {
				updatedBy = XSSUtil.strip(updatedBy);
			}

			_updatedBy = updatedBy;
			setModified(true);
		}
	}

	public String getClassName() {
		return GetterUtil.getString(_className);
	}

	public void setClassName(String className) {
		if (((className == null) && (_className != null)) ||
				((className != null) && (_className == null)) ||
				((className != null) && (_className != null) &&
				!className.equals(_className))) {
			if (!XSS_ALLOW_CLASSNAME) {
				className = XSSUtil.strip(className);
			}

			_className = className;
			setModified(true);
		}
	}

	public String getClassPK() {
		return GetterUtil.getString(_classPK);
	}

	public void setClassPK(String classPK) {
		if (((classPK == null) && (_classPK != null)) ||
				((classPK != null) && (_classPK == null)) ||
				((classPK != null) && (_classPK != null) &&
				!classPK.equals(_classPK))) {
			if (!XSS_ALLOW_CLASSPK) {
				classPK = XSSUtil.strip(classPK);
			}

			_classPK = classPK;
			setModified(true);
		}
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		if (active != _active) {
			_active = active;
			setModified(true);
		}
	}

	public Object clone() {
		DataTracker clone = new DataTracker();
		clone.setDataTrackerId(getDataTrackerId());
		clone.setCompanyId(getCompanyId());
		clone.setCreatedOn(getCreatedOn());
		clone.setCreatedByUserId(getCreatedByUserId());
		clone.setCreatedByUserName(getCreatedByUserName());
		clone.setUpdatedOn(getUpdatedOn());
		clone.setUpdatedBy(getUpdatedBy());
		clone.setClassName(getClassName());
		clone.setClassPK(getClassPK());
		clone.setActive(getActive());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		DataTracker dataTracker = (DataTracker)obj;
		String pk = dataTracker.getPrimaryKey();

		return getPrimaryKey().compareTo(pk);
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		DataTracker dataTracker = null;

		try {
			dataTracker = (DataTracker)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		String pk = dataTracker.getPrimaryKey();

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

	private String _dataTrackerId;
	private String _companyId;
	private Date _createdOn;
	private String _createdByUserId;
	private String _createdByUserName;
	private Date _updatedOn;
	private String _updatedBy;
	private String _className;
	private String _classPK;
	private boolean _active;
}