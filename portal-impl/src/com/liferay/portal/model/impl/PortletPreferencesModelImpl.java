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
 * <a href="PortletPreferencesModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>PortletPreferences</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.model.PortletPreferences
 * @see com.liferay.portal.service.model.PortletPreferencesModel
 * @see com.liferay.portal.service.model.impl.PortletPreferencesImpl
 *
 */
public class PortletPreferencesModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "PortletPreferences";
	public static Object[][] TABLE_COLUMNS = {
			{ "portletPreferencesId", new Integer(Types.BIGINT) },
			{ "ownerId", new Integer(Types.BIGINT) },
			{ "ownerType", new Integer(Types.INTEGER) },
			{ "plid", new Integer(Types.BIGINT) },
			{ "portletId", new Integer(Types.VARCHAR) },
			{ "preferences", new Integer(Types.CLOB) }
		};
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.PortletPreferences"),
			XSS_ALLOW);
	public static boolean XSS_ALLOW_PORTLETID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.PortletPreferences.portletId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_PREFERENCES = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.PortletPreferences.preferences"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.PortletPreferencesModel"));

	public PortletPreferencesModelImpl() {
	}

	public long getPrimaryKey() {
		return _portletPreferencesId;
	}

	public void setPrimaryKey(long pk) {
		setPortletPreferencesId(pk);
	}

	public long getPortletPreferencesId() {
		return _portletPreferencesId;
	}

	public void setPortletPreferencesId(long portletPreferencesId) {
		if (portletPreferencesId != _portletPreferencesId) {
			_portletPreferencesId = portletPreferencesId;
		}
	}

	public long getOwnerId() {
		return _ownerId;
	}

	public void setOwnerId(long ownerId) {
		if (ownerId != _ownerId) {
			_ownerId = ownerId;
		}
	}

	public int getOwnerType() {
		return _ownerType;
	}

	public void setOwnerType(int ownerType) {
		if (ownerType != _ownerType) {
			_ownerType = ownerType;
		}
	}

	public long getPlid() {
		return _plid;
	}

	public void setPlid(long plid) {
		if (plid != _plid) {
			_plid = plid;
		}
	}

	public String getPortletId() {
		return GetterUtil.getString(_portletId);
	}

	public void setPortletId(String portletId) {
		if (((portletId == null) && (_portletId != null)) ||
				((portletId != null) && (_portletId == null)) ||
				((portletId != null) && (_portletId != null) &&
				!portletId.equals(_portletId))) {
			if (!XSS_ALLOW_PORTLETID) {
				portletId = XSSUtil.strip(portletId);
			}

			_portletId = portletId;
		}
	}

	public String getPreferences() {
		return GetterUtil.getString(_preferences);
	}

	public void setPreferences(String preferences) {
		if (((preferences == null) && (_preferences != null)) ||
				((preferences != null) && (_preferences == null)) ||
				((preferences != null) && (_preferences != null) &&
				!preferences.equals(_preferences))) {
			if (!XSS_ALLOW_PREFERENCES) {
				preferences = XSSUtil.strip(preferences);
			}

			_preferences = preferences;
		}
	}

	public Object clone() {
		PortletPreferencesImpl clone = new PortletPreferencesImpl();
		clone.setPortletPreferencesId(getPortletPreferencesId());
		clone.setOwnerId(getOwnerId());
		clone.setOwnerType(getOwnerType());
		clone.setPlid(getPlid());
		clone.setPortletId(getPortletId());
		clone.setPreferences(getPreferences());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		PortletPreferencesImpl portletPreferences = (PortletPreferencesImpl)obj;
		long pk = portletPreferences.getPrimaryKey();

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

		PortletPreferencesImpl portletPreferences = null;

		try {
			portletPreferences = (PortletPreferencesImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = portletPreferences.getPrimaryKey();

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

	private long _portletPreferencesId;
	private long _ownerId;
	private int _ownerType;
	private long _plid;
	private String _portletId;
	private String _preferences;
}