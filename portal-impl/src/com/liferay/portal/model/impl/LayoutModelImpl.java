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
 * <a href="LayoutModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>Layout</code> table in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.model.Layout
 * @see com.liferay.portal.service.model.LayoutModel
 * @see com.liferay.portal.service.model.impl.LayoutImpl
 *
 */
public class LayoutModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "Layout";
	public static Object[][] TABLE_COLUMNS = {
			{ "plid", new Integer(Types.BIGINT) },
			{ "groupId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "privateLayout", new Integer(Types.BOOLEAN) },
			{ "layoutId", new Integer(Types.BIGINT) },
			{ "parentLayoutId", new Integer(Types.BIGINT) },
			{ "name", new Integer(Types.VARCHAR) },
			{ "title", new Integer(Types.VARCHAR) },
			{ "type_", new Integer(Types.VARCHAR) },
			{ "typeSettings", new Integer(Types.CLOB) },
			{ "hidden_", new Integer(Types.BOOLEAN) },
			{ "friendlyURL", new Integer(Types.VARCHAR) },
			{ "iconImage", new Integer(Types.BOOLEAN) },
			{ "iconImageId", new Integer(Types.BIGINT) },
			{ "themeId", new Integer(Types.VARCHAR) },
			{ "colorSchemeId", new Integer(Types.VARCHAR) },
			{ "wapThemeId", new Integer(Types.VARCHAR) },
			{ "wapColorSchemeId", new Integer(Types.VARCHAR) },
			{ "css", new Integer(Types.VARCHAR) },
			{ "priority", new Integer(Types.INTEGER) }
		};
	public static String TABLE_SQL_CREATE = "create table Layout (plid LONG not null primary key,groupId LONG,companyId LONG,privateLayout BOOLEAN,layoutId LONG,parentLayoutId LONG,name STRING null,title STRING null,type_ VARCHAR(75) null,typeSettings TEXT null,hidden_ BOOLEAN,friendlyURL VARCHAR(100) null,iconImage BOOLEAN,iconImageId LONG,themeId VARCHAR(75) null,colorSchemeId VARCHAR(75) null,wapThemeId VARCHAR(75) null,wapColorSchemeId VARCHAR(75) null,css STRING null,priority INTEGER)";
	public static String TABLE_SQL_DROP = "drop table Layout";
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Layout"), XSS_ALLOW);
	public static boolean XSS_ALLOW_NAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Layout.name"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_TITLE = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Layout.title"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_TYPE = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Layout.type"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_TYPESETTINGS = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Layout.typeSettings"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_FRIENDLYURL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Layout.friendlyURL"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_THEMEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Layout.themeId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COLORSCHEMEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Layout.colorSchemeId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_WAPTHEMEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Layout.wapThemeId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_WAPCOLORSCHEMEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Layout.wapColorSchemeId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CSS = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Layout.css"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.LayoutModel"));

	public LayoutModelImpl() {
	}

	public long getPrimaryKey() {
		return _plid;
	}

	public void setPrimaryKey(long pk) {
		setPlid(pk);
	}

	public long getPlid() {
		return _plid;
	}

	public void setPlid(long plid) {
		if (plid != _plid) {
			_plid = plid;
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

	public long getLayoutId() {
		return _layoutId;
	}

	public void setLayoutId(long layoutId) {
		if (layoutId != _layoutId) {
			_layoutId = layoutId;
		}
	}

	public long getParentLayoutId() {
		return _parentLayoutId;
	}

	public void setParentLayoutId(long parentLayoutId) {
		if (parentLayoutId != _parentLayoutId) {
			_parentLayoutId = parentLayoutId;
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

	public String getTitle() {
		return GetterUtil.getString(_title);
	}

	public void setTitle(String title) {
		if (((title == null) && (_title != null)) ||
				((title != null) && (_title == null)) ||
				((title != null) && (_title != null) && !title.equals(_title))) {
			if (!XSS_ALLOW_TITLE) {
				title = XSSUtil.strip(title);
			}

			_title = title;
		}
	}

	public String getType() {
		return GetterUtil.getString(_type);
	}

	public void setType(String type) {
		if (((type == null) && (_type != null)) ||
				((type != null) && (_type == null)) ||
				((type != null) && (_type != null) && !type.equals(_type))) {
			if (!XSS_ALLOW_TYPE) {
				type = XSSUtil.strip(type);
			}

			_type = type;
		}
	}

	public String getTypeSettings() {
		return GetterUtil.getString(_typeSettings);
	}

	public void setTypeSettings(String typeSettings) {
		if (((typeSettings == null) && (_typeSettings != null)) ||
				((typeSettings != null) && (_typeSettings == null)) ||
				((typeSettings != null) && (_typeSettings != null) &&
				!typeSettings.equals(_typeSettings))) {
			if (!XSS_ALLOW_TYPESETTINGS) {
				typeSettings = XSSUtil.strip(typeSettings);
			}

			_typeSettings = typeSettings;
		}
	}

	public boolean getHidden() {
		return _hidden;
	}

	public boolean isHidden() {
		return _hidden;
	}

	public void setHidden(boolean hidden) {
		if (hidden != _hidden) {
			_hidden = hidden;
		}
	}

	public String getFriendlyURL() {
		return GetterUtil.getString(_friendlyURL);
	}

	public void setFriendlyURL(String friendlyURL) {
		if (((friendlyURL == null) && (_friendlyURL != null)) ||
				((friendlyURL != null) && (_friendlyURL == null)) ||
				((friendlyURL != null) && (_friendlyURL != null) &&
				!friendlyURL.equals(_friendlyURL))) {
			if (!XSS_ALLOW_FRIENDLYURL) {
				friendlyURL = XSSUtil.strip(friendlyURL);
			}

			_friendlyURL = friendlyURL;
		}
	}

	public boolean getIconImage() {
		return _iconImage;
	}

	public boolean isIconImage() {
		return _iconImage;
	}

	public void setIconImage(boolean iconImage) {
		if (iconImage != _iconImage) {
			_iconImage = iconImage;
		}
	}

	public long getIconImageId() {
		return _iconImageId;
	}

	public void setIconImageId(long iconImageId) {
		if (iconImageId != _iconImageId) {
			_iconImageId = iconImageId;
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

	public int getPriority() {
		return _priority;
	}

	public void setPriority(int priority) {
		if (priority != _priority) {
			_priority = priority;
		}
	}

	public Object clone() {
		LayoutImpl clone = new LayoutImpl();
		clone.setPlid(getPlid());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setPrivateLayout(getPrivateLayout());
		clone.setLayoutId(getLayoutId());
		clone.setParentLayoutId(getParentLayoutId());
		clone.setName(getName());
		clone.setTitle(getTitle());
		clone.setType(getType());
		clone.setTypeSettings(getTypeSettings());
		clone.setHidden(getHidden());
		clone.setFriendlyURL(getFriendlyURL());
		clone.setIconImage(getIconImage());
		clone.setIconImageId(getIconImageId());
		clone.setThemeId(getThemeId());
		clone.setColorSchemeId(getColorSchemeId());
		clone.setWapThemeId(getWapThemeId());
		clone.setWapColorSchemeId(getWapColorSchemeId());
		clone.setCss(getCss());
		clone.setPriority(getPriority());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		LayoutImpl layout = (LayoutImpl)obj;
		int value = 0;

		if (getParentLayoutId() < layout.getParentLayoutId()) {
			value = -1;
		}
		else if (getParentLayoutId() > layout.getParentLayoutId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (getPriority() < layout.getPriority()) {
			value = -1;
		}
		else if (getPriority() > layout.getPriority()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		LayoutImpl layout = null;

		try {
			layout = (LayoutImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = layout.getPrimaryKey();

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

	private long _plid;
	private long _groupId;
	private long _companyId;
	private boolean _privateLayout;
	private long _layoutId;
	private long _parentLayoutId;
	private String _name;
	private String _title;
	private String _type;
	private String _typeSettings;
	private boolean _hidden;
	private String _friendlyURL;
	private boolean _iconImage;
	private long _iconImageId;
	private String _themeId;
	private String _colorSchemeId;
	private String _wapThemeId;
	private String _wapColorSchemeId;
	private String _css;
	private int _priority;
}