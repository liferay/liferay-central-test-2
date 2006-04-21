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

/**
 * <a href="OrgLaborModel.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class OrgLaborModel extends BaseModel {
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.OrgLabor"), XSS_ALLOW);
	public static boolean XSS_ALLOW_ORGLABORID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.OrgLabor.orgLaborId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_ORGANIZATIONID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.OrgLabor.organizationId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_TYPEID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.OrgLabor.typeId"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.OrgLaborModel"));

	public OrgLaborModel() {
	}

	public String getPrimaryKey() {
		return _orgLaborId;
	}

	public void setPrimaryKey(String pk) {
		setOrgLaborId(pk);
	}

	public String getOrgLaborId() {
		return GetterUtil.getString(_orgLaborId);
	}

	public void setOrgLaborId(String orgLaborId) {
		if (((orgLaborId == null) && (_orgLaborId != null)) ||
				((orgLaborId != null) && (_orgLaborId == null)) ||
				((orgLaborId != null) && (_orgLaborId != null) &&
				!orgLaborId.equals(_orgLaborId))) {
			if (!XSS_ALLOW_ORGLABORID) {
				orgLaborId = XSSUtil.strip(orgLaborId);
			}

			_orgLaborId = orgLaborId;
			setModified(true);
		}
	}

	public String getOrganizationId() {
		return GetterUtil.getString(_organizationId);
	}

	public void setOrganizationId(String organizationId) {
		if (((organizationId == null) && (_organizationId != null)) ||
				((organizationId != null) && (_organizationId == null)) ||
				((organizationId != null) && (_organizationId != null) &&
				!organizationId.equals(_organizationId))) {
			if (!XSS_ALLOW_ORGANIZATIONID) {
				organizationId = XSSUtil.strip(organizationId);
			}

			_organizationId = organizationId;
			setModified(true);
		}
	}

	public String getTypeId() {
		return GetterUtil.getString(_typeId);
	}

	public void setTypeId(String typeId) {
		if (((typeId == null) && (_typeId != null)) ||
				((typeId != null) && (_typeId == null)) ||
				((typeId != null) && (_typeId != null) &&
				!typeId.equals(_typeId))) {
			if (!XSS_ALLOW_TYPEID) {
				typeId = XSSUtil.strip(typeId);
			}

			_typeId = typeId;
			setModified(true);
		}
	}

	public int getSunOpen() {
		return _sunOpen;
	}

	public void setSunOpen(int sunOpen) {
		if (sunOpen != _sunOpen) {
			_sunOpen = sunOpen;
			setModified(true);
		}
	}

	public int getSunClose() {
		return _sunClose;
	}

	public void setSunClose(int sunClose) {
		if (sunClose != _sunClose) {
			_sunClose = sunClose;
			setModified(true);
		}
	}

	public int getMonOpen() {
		return _monOpen;
	}

	public void setMonOpen(int monOpen) {
		if (monOpen != _monOpen) {
			_monOpen = monOpen;
			setModified(true);
		}
	}

	public int getMonClose() {
		return _monClose;
	}

	public void setMonClose(int monClose) {
		if (monClose != _monClose) {
			_monClose = monClose;
			setModified(true);
		}
	}

	public int getTueOpen() {
		return _tueOpen;
	}

	public void setTueOpen(int tueOpen) {
		if (tueOpen != _tueOpen) {
			_tueOpen = tueOpen;
			setModified(true);
		}
	}

	public int getTueClose() {
		return _tueClose;
	}

	public void setTueClose(int tueClose) {
		if (tueClose != _tueClose) {
			_tueClose = tueClose;
			setModified(true);
		}
	}

	public int getWedOpen() {
		return _wedOpen;
	}

	public void setWedOpen(int wedOpen) {
		if (wedOpen != _wedOpen) {
			_wedOpen = wedOpen;
			setModified(true);
		}
	}

	public int getWedClose() {
		return _wedClose;
	}

	public void setWedClose(int wedClose) {
		if (wedClose != _wedClose) {
			_wedClose = wedClose;
			setModified(true);
		}
	}

	public int getThuOpen() {
		return _thuOpen;
	}

	public void setThuOpen(int thuOpen) {
		if (thuOpen != _thuOpen) {
			_thuOpen = thuOpen;
			setModified(true);
		}
	}

	public int getThuClose() {
		return _thuClose;
	}

	public void setThuClose(int thuClose) {
		if (thuClose != _thuClose) {
			_thuClose = thuClose;
			setModified(true);
		}
	}

	public int getFriOpen() {
		return _friOpen;
	}

	public void setFriOpen(int friOpen) {
		if (friOpen != _friOpen) {
			_friOpen = friOpen;
			setModified(true);
		}
	}

	public int getFriClose() {
		return _friClose;
	}

	public void setFriClose(int friClose) {
		if (friClose != _friClose) {
			_friClose = friClose;
			setModified(true);
		}
	}

	public int getSatOpen() {
		return _satOpen;
	}

	public void setSatOpen(int satOpen) {
		if (satOpen != _satOpen) {
			_satOpen = satOpen;
			setModified(true);
		}
	}

	public int getSatClose() {
		return _satClose;
	}

	public void setSatClose(int satClose) {
		if (satClose != _satClose) {
			_satClose = satClose;
			setModified(true);
		}
	}

	public Object clone() {
		OrgLabor clone = new OrgLabor();
		clone.setOrgLaborId(getOrgLaborId());
		clone.setOrganizationId(getOrganizationId());
		clone.setTypeId(getTypeId());
		clone.setSunOpen(getSunOpen());
		clone.setSunClose(getSunClose());
		clone.setMonOpen(getMonOpen());
		clone.setMonClose(getMonClose());
		clone.setTueOpen(getTueOpen());
		clone.setTueClose(getTueClose());
		clone.setWedOpen(getWedOpen());
		clone.setWedClose(getWedClose());
		clone.setThuOpen(getThuOpen());
		clone.setThuClose(getThuClose());
		clone.setFriOpen(getFriOpen());
		clone.setFriClose(getFriClose());
		clone.setSatOpen(getSatOpen());
		clone.setSatClose(getSatClose());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		OrgLabor orgLabor = (OrgLabor)obj;
		int value = 0;
		value = getOrganizationId().compareTo(orgLabor.getOrganizationId());

		if (value != 0) {
			return value;
		}

		value = getTypeId().compareTo(orgLabor.getTypeId());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		OrgLabor orgLabor = null;

		try {
			orgLabor = (OrgLabor)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		String pk = orgLabor.getPrimaryKey();

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

	private String _orgLaborId;
	private String _organizationId;
	private String _typeId;
	private int _sunOpen;
	private int _sunClose;
	private int _monOpen;
	private int _monClose;
	private int _tueOpen;
	private int _tueClose;
	private int _wedOpen;
	private int _wedClose;
	private int _thuOpen;
	private int _thuClose;
	private int _friOpen;
	private int _friClose;
	private int _satOpen;
	private int _satClose;
}