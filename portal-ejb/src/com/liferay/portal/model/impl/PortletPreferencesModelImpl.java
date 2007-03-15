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
import com.liferay.portal.service.persistence.PortletPreferencesPK;
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
			{ "portletId", new Integer(Types.VARCHAR) },
			{ "layoutId", new Integer(Types.VARCHAR) },
			{ "ownerId", new Integer(Types.VARCHAR) },
			{ "preferences", new Integer(Types.CLOB) }
		};
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.PortletPreferences"),
			XSS_ALLOW);
	public static boolean XSS_ALLOW_PORTLETID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.PortletPreferences.portletId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_LAYOUTID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.PortletPreferences.layoutId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_OWNERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.PortletPreferences.ownerId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_PREFERENCES = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.PortletPreferences.preferences"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.PortletPreferencesModel"));

	public PortletPreferencesModelImpl() {
	}

	public PortletPreferencesPK getPrimaryKey() {
		return new PortletPreferencesPK(_portletId, _layoutId, _ownerId);
	}

	public void setPrimaryKey(PortletPreferencesPK pk) {
		setPortletId(pk.portletId);
		setLayoutId(pk.layoutId);
		setOwnerId(pk.ownerId);
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

	public String getLayoutId() {
		return GetterUtil.getString(_layoutId);
	}

	public void setLayoutId(String layoutId) {
		if (((layoutId == null) && (_layoutId != null)) ||
				((layoutId != null) && (_layoutId == null)) ||
				((layoutId != null) && (_layoutId != null) &&
				!layoutId.equals(_layoutId))) {
			if (!XSS_ALLOW_LAYOUTID) {
				layoutId = XSSUtil.strip(layoutId);
			}

			_layoutId = layoutId;
		}
	}

	public String getOwnerId() {
		return GetterUtil.getString(_ownerId);
	}

	public void setOwnerId(String ownerId) {
		if (((ownerId == null) && (_ownerId != null)) ||
				((ownerId != null) && (_ownerId == null)) ||
				((ownerId != null) && (_ownerId != null) &&
				!ownerId.equals(_ownerId))) {
			if (!XSS_ALLOW_OWNERID) {
				ownerId = XSSUtil.strip(ownerId);
			}

			_ownerId = ownerId;
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
		clone.setPortletId(getPortletId());
		clone.setLayoutId(getLayoutId());
		clone.setOwnerId(getOwnerId());
		clone.setPreferences(getPreferences());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		PortletPreferencesImpl portletPreferences = (PortletPreferencesImpl)obj;
		PortletPreferencesPK pk = portletPreferences.getPrimaryKey();

		return getPrimaryKey().compareTo(pk);
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

		PortletPreferencesPK pk = portletPreferences.getPrimaryKey();

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

	private String _portletId;
	private String _layoutId;
	private String _ownerId;
	private String _preferences;
}