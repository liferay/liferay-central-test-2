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
import com.liferay.portal.service.persistence.LayoutPK;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.sql.Types;

/**
 * <a href="LayoutModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class LayoutModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "Layout";
	public static Object[][] TABLE_COLUMNS = {
			{ "layoutId", new Integer(Types.VARCHAR) },
			{ "ownerId", new Integer(Types.VARCHAR) },
			{ "companyId", new Integer(Types.VARCHAR) },
			{ "parentLayoutId", new Integer(Types.VARCHAR) },
			{ "name", new Integer(Types.VARCHAR) },
			{ "title", new Integer(Types.VARCHAR) },
			{ "type_", new Integer(Types.VARCHAR) },
			{ "typeSettings", new Integer(Types.VARCHAR) },
			{ "hidden_", new Integer(Types.BOOLEAN) },
			{ "friendlyURL", new Integer(Types.VARCHAR) },
			{ "iconImage", new Integer(Types.BOOLEAN) },
			{ "themeId", new Integer(Types.VARCHAR) },
			{ "colorSchemeId", new Integer(Types.VARCHAR) },
			{ "priority", new Integer(Types.INTEGER) }
		};
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Layout"), XSS_ALLOW);
	public static boolean XSS_ALLOW_LAYOUTID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Layout.layoutId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_OWNERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Layout.ownerId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COMPANYID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Layout.companyId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_PARENTLAYOUTID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Layout.parentLayoutId"),
			XSS_ALLOW_BY_MODEL);
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
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.LayoutModel"));

	public LayoutModelImpl() {
	}

	public LayoutPK getPrimaryKey() {
		return new LayoutPK(_layoutId, _ownerId);
	}

	public void setPrimaryKey(LayoutPK pk) {
		setLayoutId(pk.layoutId);
		setOwnerId(pk.ownerId);
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

	public String getParentLayoutId() {
		return GetterUtil.getString(_parentLayoutId);
	}

	public void setParentLayoutId(String parentLayoutId) {
		if (((parentLayoutId == null) && (_parentLayoutId != null)) ||
				((parentLayoutId != null) && (_parentLayoutId == null)) ||
				((parentLayoutId != null) && (_parentLayoutId != null) &&
				!parentLayoutId.equals(_parentLayoutId))) {
			if (!XSS_ALLOW_PARENTLAYOUTID) {
				parentLayoutId = XSSUtil.strip(parentLayoutId);
			}

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
		clone.setLayoutId(getLayoutId());
		clone.setOwnerId(getOwnerId());
		clone.setCompanyId(getCompanyId());
		clone.setParentLayoutId(getParentLayoutId());
		clone.setName(getName());
		clone.setTitle(getTitle());
		clone.setType(getType());
		clone.setTypeSettings(getTypeSettings());
		clone.setHidden(getHidden());
		clone.setFriendlyURL(getFriendlyURL());
		clone.setIconImage(getIconImage());
		clone.setThemeId(getThemeId());
		clone.setColorSchemeId(getColorSchemeId());
		clone.setPriority(getPriority());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		LayoutImpl layout = (LayoutImpl)obj;
		int value = 0;
		value = getParentLayoutId().compareTo(layout.getParentLayoutId());

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

		LayoutPK pk = layout.getPrimaryKey();

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

	private String _layoutId;
	private String _ownerId;
	private String _companyId;
	private String _parentLayoutId;
	private String _name;
	private String _title;
	private String _type;
	private String _typeSettings;
	private boolean _hidden;
	private String _friendlyURL;
	private boolean _iconImage;
	private String _themeId;
	private String _colorSchemeId;
	private int _priority;
}