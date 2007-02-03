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

/**
 * <a href="RegionModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class RegionModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "Region";
	public static Object[][] TABLE_COLUMNS = {
			{ "regionId", new Integer(Types.VARCHAR) },
			{ "countryId", new Integer(Types.VARCHAR) },
			{ "regionCode", new Integer(Types.VARCHAR) },
			{ "name", new Integer(Types.VARCHAR) },
			{ "active_", new Integer(Types.BOOLEAN) }
		};
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Region"), XSS_ALLOW);
	public static boolean XSS_ALLOW_REGIONID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Region.regionId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COUNTRYID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Region.countryId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_REGIONCODE = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Region.regionCode"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_NAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Region.name"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.RegionModel"));

	public RegionModelImpl() {
	}

	public String getPrimaryKey() {
		return _regionId;
	}

	public void setPrimaryKey(String pk) {
		setRegionId(pk);
	}

	public String getRegionId() {
		return GetterUtil.getString(_regionId);
	}

	public void setRegionId(String regionId) {
		if (((regionId == null) && (_regionId != null)) ||
				((regionId != null) && (_regionId == null)) ||
				((regionId != null) && (_regionId != null) &&
				!regionId.equals(_regionId))) {
			if (!XSS_ALLOW_REGIONID) {
				regionId = XSSUtil.strip(regionId);
			}

			_regionId = regionId;
		}
	}

	public String getCountryId() {
		return GetterUtil.getString(_countryId);
	}

	public void setCountryId(String countryId) {
		if (((countryId == null) && (_countryId != null)) ||
				((countryId != null) && (_countryId == null)) ||
				((countryId != null) && (_countryId != null) &&
				!countryId.equals(_countryId))) {
			if (!XSS_ALLOW_COUNTRYID) {
				countryId = XSSUtil.strip(countryId);
			}

			_countryId = countryId;
		}
	}

	public String getRegionCode() {
		return GetterUtil.getString(_regionCode);
	}

	public void setRegionCode(String regionCode) {
		if (((regionCode == null) && (_regionCode != null)) ||
				((regionCode != null) && (_regionCode == null)) ||
				((regionCode != null) && (_regionCode != null) &&
				!regionCode.equals(_regionCode))) {
			if (!XSS_ALLOW_REGIONCODE) {
				regionCode = XSSUtil.strip(regionCode);
			}

			_regionCode = regionCode;
		}
	}

	public String getName() {
		return GetterUtil.getString(_name);
	}

	public void setName(String name) {
		if (((name == null) && (_name != null)) ||
				((name != null) && (_name == null)) ||
				((name != null) && (_name != null) && !name.equals(_name))) {
			if (!XSS_ALLOW_NAME) {
				name = XSSUtil.strip(name);
			}

			_name = name;
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
		}
	}

	public Object clone() {
		RegionImpl clone = new RegionImpl();
		clone.setRegionId(getRegionId());
		clone.setCountryId(getCountryId());
		clone.setRegionCode(getRegionCode());
		clone.setName(getName());
		clone.setActive(getActive());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		RegionImpl region = (RegionImpl)obj;
		int value = 0;
		value = getName().compareTo(region.getName());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		RegionImpl region = null;

		try {
			region = (RegionImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		String pk = region.getPrimaryKey();

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

	private String _regionId;
	private String _countryId;
	private String _regionCode;
	private String _name;
	private boolean _active;
}