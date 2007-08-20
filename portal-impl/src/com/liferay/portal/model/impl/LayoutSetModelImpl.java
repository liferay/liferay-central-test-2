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

import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.XSSUtil;

import java.io.Serializable;

import java.sql.Types;

/**
 * <a href="LayoutSetModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>LayoutSet</code> table in the
 * database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.model.LayoutSet
 * @see com.liferay.portal.service.model.LayoutSetModel
 * @see com.liferay.portal.service.model.impl.LayoutSetImpl
 *
 */
public class LayoutSetModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "LayoutSet";
	public static Object[][] TABLE_COLUMNS = {
			{ "layoutSetId", new Integer(Types.BIGINT) },
			{ "groupId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "privateLayout", new Integer(Types.BOOLEAN) },
			{ "logo", new Integer(Types.BOOLEAN) },
			{ "logoId", new Integer(Types.BIGINT) },
			{ "themeId", new Integer(Types.VARCHAR) },
			{ "colorSchemeId", new Integer(Types.VARCHAR) },
			{ "wapThemeId", new Integer(Types.VARCHAR) },
			{ "wapColorSchemeId", new Integer(Types.VARCHAR) },
			{ "css", new Integer(Types.VARCHAR) },
			{ "pageCount", new Integer(Types.INTEGER) },
			{ "virtualHost", new Integer(Types.VARCHAR) }
		};
	public static String TABLE_SQL_CREATE = "create table LayoutSet (layoutSetId LONG not null primary key,groupId LONG,companyId LONG,privateLayout BOOLEAN,logo BOOLEAN,logoId LONG,themeId VARCHAR(75) null,colorSchemeId VARCHAR(75) null,wapThemeId VARCHAR(75) null,wapColorSchemeId VARCHAR(75) null,css VARCHAR(75) null,pageCount INTEGER,virtualHost VARCHAR(75) null)";
	public static String TABLE_SQL_DROP = "drop table LayoutSet";
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.LayoutSet"), XSS_ALLOW);
	public static boolean XSS_ALLOW_THEMEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.LayoutSet.themeId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COLORSCHEMEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.LayoutSet.colorSchemeId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_WAPTHEMEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.LayoutSet.wapThemeId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_WAPCOLORSCHEMEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.LayoutSet.wapColorSchemeId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CSS = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.LayoutSet.css"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_VIRTUALHOST = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.LayoutSet.virtualHost"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.LayoutSetModel"));

	public LayoutSetModelImpl() {
	}

	public long getPrimaryKey() {
		return _layoutSetId;
	}

	public void setPrimaryKey(long pk) {
		setLayoutSetId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_layoutSetId);
	}

	public long getLayoutSetId() {
		return _layoutSetId;
	}

	public void setLayoutSetId(long layoutSetId) {
		if (layoutSetId != _layoutSetId) {
			_layoutSetId = layoutSetId;
		}
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		if (groupId != _groupId) {
			_groupId = groupId;
		}
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		if (companyId != _companyId) {
			_companyId = companyId;
		}
	}

	public boolean getPrivateLayout() {
		return _privateLayout;
	}

	public boolean isPrivateLayout() {
		return _privateLayout;
	}

	public void setPrivateLayout(boolean privateLayout) {
		if (privateLayout != _privateLayout) {
			_privateLayout = privateLayout;
		}
	}

	public boolean getLogo() {
		return _logo;
	}

	public boolean isLogo() {
		return _logo;
	}

	public void setLogo(boolean logo) {
		if (logo != _logo) {
			_logo = logo;
		}
	}

	public long getLogoId() {
		return _logoId;
	}

	public void setLogoId(long logoId) {
		if (logoId != _logoId) {
			_logoId = logoId;
		}
	}

	public String getThemeId() {
		return GetterUtil.getString(_themeId);
	}

	public void setThemeId(String themeId) {
		if (((themeId == null) && (_themeId != null)) ||
				((themeId != null) && (_themeId == null)) ||
				((themeId != null) && (_themeId != null) &&
				!themeId.equals(_themeId))) {
			if (!XSS_ALLOW_THEMEID) {
				themeId = XSSUtil.strip(themeId);
			}

			_themeId = themeId;
		}
	}

	public String getColorSchemeId() {
		return GetterUtil.getString(_colorSchemeId);
	}

	public void setColorSchemeId(String colorSchemeId) {
		if (((colorSchemeId == null) && (_colorSchemeId != null)) ||
				((colorSchemeId != null) && (_colorSchemeId == null)) ||
				((colorSchemeId != null) && (_colorSchemeId != null) &&
				!colorSchemeId.equals(_colorSchemeId))) {
			if (!XSS_ALLOW_COLORSCHEMEID) {
				colorSchemeId = XSSUtil.strip(colorSchemeId);
			}

			_colorSchemeId = colorSchemeId;
		}
	}

	public String getWapThemeId() {
		return GetterUtil.getString(_wapThemeId);
	}

	public void setWapThemeId(String wapThemeId) {
		if (((wapThemeId == null) && (_wapThemeId != null)) ||
				((wapThemeId != null) && (_wapThemeId == null)) ||
				((wapThemeId != null) && (_wapThemeId != null) &&
				!wapThemeId.equals(_wapThemeId))) {
			if (!XSS_ALLOW_WAPTHEMEID) {
				wapThemeId = XSSUtil.strip(wapThemeId);
			}

			_wapThemeId = wapThemeId;
		}
	}

	public String getWapColorSchemeId() {
		return GetterUtil.getString(_wapColorSchemeId);
	}

	public void setWapColorSchemeId(String wapColorSchemeId) {
		if (((wapColorSchemeId == null) && (_wapColorSchemeId != null)) ||
				((wapColorSchemeId != null) && (_wapColorSchemeId == null)) ||
				((wapColorSchemeId != null) && (_wapColorSchemeId != null) &&
				!wapColorSchemeId.equals(_wapColorSchemeId))) {
			if (!XSS_ALLOW_WAPCOLORSCHEMEID) {
				wapColorSchemeId = XSSUtil.strip(wapColorSchemeId);
			}

			_wapColorSchemeId = wapColorSchemeId;
		}
	}

	public String getCss() {
		return GetterUtil.getString(_css);
	}

	public void setCss(String css) {
		if (((css == null) && (_css != null)) ||
				((css != null) && (_css == null)) ||
				((css != null) && (_css != null) && !css.equals(_css))) {
			if (!XSS_ALLOW_CSS) {
				css = XSSUtil.strip(css);
			}

			_css = css;
		}
	}

	public int getPageCount() {
		return _pageCount;
	}

	public void setPageCount(int pageCount) {
		if (pageCount != _pageCount) {
			_pageCount = pageCount;
		}
	}

	public String getVirtualHost() {
		return GetterUtil.getString(_virtualHost);
	}

	public void setVirtualHost(String virtualHost) {
		if (((virtualHost == null) && (_virtualHost != null)) ||
				((virtualHost != null) && (_virtualHost == null)) ||
				((virtualHost != null) && (_virtualHost != null) &&
				!virtualHost.equals(_virtualHost))) {
			if (!XSS_ALLOW_VIRTUALHOST) {
				virtualHost = XSSUtil.strip(virtualHost);
			}

			_virtualHost = virtualHost;
		}
	}

	public Object clone() {
		LayoutSetImpl clone = new LayoutSetImpl();
		clone.setLayoutSetId(getLayoutSetId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setPrivateLayout(getPrivateLayout());
		clone.setLogo(getLogo());
		clone.setLogoId(getLogoId());
		clone.setThemeId(getThemeId());
		clone.setColorSchemeId(getColorSchemeId());
		clone.setWapThemeId(getWapThemeId());
		clone.setWapColorSchemeId(getWapColorSchemeId());
		clone.setCss(getCss());
		clone.setPageCount(getPageCount());
		clone.setVirtualHost(getVirtualHost());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		LayoutSetImpl layoutSet = (LayoutSetImpl)obj;
		long pk = layoutSet.getPrimaryKey();

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

		LayoutSetImpl layoutSet = null;

		try {
			layoutSet = (LayoutSetImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = layoutSet.getPrimaryKey();

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

	private long _layoutSetId;
	private long _groupId;
	private long _companyId;
	private boolean _privateLayout;
	private boolean _logo;
	private long _logoId;
	private String _themeId;
	private String _colorSchemeId;
	private String _wapThemeId;
	private String _wapColorSchemeId;
	private String _css;
	private int _pageCount;
	private String _virtualHost;
}