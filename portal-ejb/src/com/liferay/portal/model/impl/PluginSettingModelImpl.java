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
 * <a href="PluginSettingModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>PluginSetting</code> table in
 * the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.model.PluginSetting
 * @see com.liferay.portal.service.model.PluginSettingModel
 * @see com.liferay.portal.service.model.impl.PluginSettingImpl
 *
 */
public class PluginSettingModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "PluginSetting";
	public static Object[][] TABLE_COLUMNS = {
			{ "pluginSettingId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.VARCHAR) },
			{ "pluginId", new Integer(Types.VARCHAR) },
			{ "pluginType", new Integer(Types.VARCHAR) },
			{ "roles", new Integer(Types.VARCHAR) },
			{ "active_", new Integer(Types.BOOLEAN) }
		};
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.PluginSetting"), XSS_ALLOW);
	public static boolean XSS_ALLOW_COMPANYID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.PluginSetting.companyId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_PLUGINID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.PluginSetting.pluginId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_PLUGINTYPE = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.PluginSetting.pluginType"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_ROLES = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.PluginSetting.roles"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.PluginSettingModel"));

	public PluginSettingModelImpl() {
	}

	public long getPrimaryKey() {
		return _pluginSettingId;
	}

	public void setPrimaryKey(long pk) {
		setPluginSettingId(pk);
	}

	public long getPluginSettingId() {
		return _pluginSettingId;
	}

	public void setPluginSettingId(long pluginSettingId) {
		if (pluginSettingId != _pluginSettingId) {
			_pluginSettingId = pluginSettingId;
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

	public String getPluginId() {
		return GetterUtil.getString(_pluginId);
	}

	public void setPluginId(String pluginId) {
		if (((pluginId == null) && (_pluginId != null)) ||
				((pluginId != null) && (_pluginId == null)) ||
				((pluginId != null) && (_pluginId != null) &&
				!pluginId.equals(_pluginId))) {
			if (!XSS_ALLOW_PLUGINID) {
				pluginId = XSSUtil.strip(pluginId);
			}

			_pluginId = pluginId;
		}
	}

	public String getPluginType() {
		return GetterUtil.getString(_pluginType);
	}

	public void setPluginType(String pluginType) {
		if (((pluginType == null) && (_pluginType != null)) ||
				((pluginType != null) && (_pluginType == null)) ||
				((pluginType != null) && (_pluginType != null) &&
				!pluginType.equals(_pluginType))) {
			if (!XSS_ALLOW_PLUGINTYPE) {
				pluginType = XSSUtil.strip(pluginType);
			}

			_pluginType = pluginType;
		}
	}

	public String getRoles() {
		return GetterUtil.getString(_roles);
	}

	public void setRoles(String roles) {
		if (((roles == null) && (_roles != null)) ||
				((roles != null) && (_roles == null)) ||
				((roles != null) && (_roles != null) && !roles.equals(_roles))) {
			if (!XSS_ALLOW_ROLES) {
				roles = XSSUtil.strip(roles);
			}

			_roles = roles;
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
		PluginSettingImpl clone = new PluginSettingImpl();
		clone.setPluginSettingId(getPluginSettingId());
		clone.setCompanyId(getCompanyId());
		clone.setPluginId(getPluginId());
		clone.setPluginType(getPluginType());
		clone.setRoles(getRoles());
		clone.setActive(getActive());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		PluginSettingImpl pluginSetting = (PluginSettingImpl)obj;
		long pk = pluginSetting.getPrimaryKey();

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

		PluginSettingImpl pluginSetting = null;

		try {
			pluginSetting = (PluginSettingImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = pluginSetting.getPrimaryKey();

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

	private long _pluginSettingId;
	private String _companyId;
	private String _pluginId;
	private String _pluginType;
	private String _roles;
	private boolean _active;
}