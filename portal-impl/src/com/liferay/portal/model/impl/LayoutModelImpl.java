/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSoap;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="LayoutModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the Layout table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutImpl
 * @see       com.liferay.portal.model.Layout
 * @see       com.liferay.portal.model.LayoutModel
 * @generated
 */
public class LayoutModelImpl extends BaseModelImpl<Layout> {
	public static final String TABLE_NAME = "Layout";
	public static final Object[][] TABLE_COLUMNS = {
			{ "plid", new Integer(Types.BIGINT) },
			{ "groupId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "privateLayout", new Integer(Types.BOOLEAN) },
			{ "layoutId", new Integer(Types.BIGINT) },
			{ "parentLayoutId", new Integer(Types.BIGINT) },
			{ "name", new Integer(Types.VARCHAR) },
			{ "title", new Integer(Types.VARCHAR) },
			{ "description", new Integer(Types.VARCHAR) },
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
			{ "priority", new Integer(Types.INTEGER) },
			{ "layoutPrototypeId", new Integer(Types.BIGINT) },
			{ "dlFolderId", new Integer(Types.BIGINT) }
		};
	public static final String TABLE_SQL_CREATE = "create table Layout (plid LONG not null primary key,groupId LONG,companyId LONG,privateLayout BOOLEAN,layoutId LONG,parentLayoutId LONG,name STRING null,title STRING null,description STRING null,type_ VARCHAR(75) null,typeSettings TEXT null,hidden_ BOOLEAN,friendlyURL VARCHAR(255) null,iconImage BOOLEAN,iconImageId LONG,themeId VARCHAR(75) null,colorSchemeId VARCHAR(75) null,wapThemeId VARCHAR(75) null,wapColorSchemeId VARCHAR(75) null,css STRING null,priority INTEGER,layoutPrototypeId LONG,dlFolderId LONG)";
	public static final String TABLE_SQL_DROP = "drop table Layout";
	public static final String ORDER_BY_JPQL = " ORDER BY layout.parentLayoutId ASC, layout.priority ASC";
	public static final String ORDER_BY_SQL = " ORDER BY Layout.parentLayoutId ASC, Layout.priority ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.Layout"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.Layout"),
			true);

	public static Layout toModel(LayoutSoap soapModel) {
		Layout model = new LayoutImpl();

		model.setPlid(soapModel.getPlid());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setPrivateLayout(soapModel.getPrivateLayout());
		model.setLayoutId(soapModel.getLayoutId());
		model.setParentLayoutId(soapModel.getParentLayoutId());
		model.setName(soapModel.getName());
		model.setTitle(soapModel.getTitle());
		model.setDescription(soapModel.getDescription());
		model.setType(soapModel.getType());
		model.setTypeSettings(soapModel.getTypeSettings());
		model.setHidden(soapModel.getHidden());
		model.setFriendlyURL(soapModel.getFriendlyURL());
		model.setIconImage(soapModel.getIconImage());
		model.setIconImageId(soapModel.getIconImageId());
		model.setThemeId(soapModel.getThemeId());
		model.setColorSchemeId(soapModel.getColorSchemeId());
		model.setWapThemeId(soapModel.getWapThemeId());
		model.setWapColorSchemeId(soapModel.getWapColorSchemeId());
		model.setCss(soapModel.getCss());
		model.setPriority(soapModel.getPriority());
		model.setLayoutPrototypeId(soapModel.getLayoutPrototypeId());
		model.setDlFolderId(soapModel.getDlFolderId());

		return model;
	}

	public static List<Layout> toModels(LayoutSoap[] soapModels) {
		List<Layout> models = new ArrayList<Layout>(soapModels.length);

		for (LayoutSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.Layout"));

	public LayoutModelImpl() {
	}

	public long getPrimaryKey() {
		return _plid;
	}

	public void setPrimaryKey(long pk) {
		setPlid(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_plid);
	}

	public long getPlid() {
		return _plid;
	}

	public void setPlid(long plid) {
		_plid = plid;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;

		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = groupId;
		}
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public boolean getPrivateLayout() {
		return _privateLayout;
	}

	public boolean isPrivateLayout() {
		return _privateLayout;
	}

	public void setPrivateLayout(boolean privateLayout) {
		_privateLayout = privateLayout;

		if (!_setOriginalPrivateLayout) {
			_setOriginalPrivateLayout = true;

			_originalPrivateLayout = privateLayout;
		}
	}

	public boolean getOriginalPrivateLayout() {
		return _originalPrivateLayout;
	}

	public long getLayoutId() {
		return _layoutId;
	}

	public void setLayoutId(long layoutId) {
		_layoutId = layoutId;

		if (!_setOriginalLayoutId) {
			_setOriginalLayoutId = true;

			_originalLayoutId = layoutId;
		}
	}

	public long getOriginalLayoutId() {
		return _originalLayoutId;
	}

	public long getParentLayoutId() {
		return _parentLayoutId;
	}

	public void setParentLayoutId(long parentLayoutId) {
		_parentLayoutId = parentLayoutId;
	}

	public String getName() {
		return GetterUtil.getString(_name);
	}

	public void setName(String name) {
		_name = name;
	}

	public String getTitle() {
		return GetterUtil.getString(_title);
	}

	public void setTitle(String title) {
		_title = title;
	}

	public String getDescription() {
		return GetterUtil.getString(_description);
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getType() {
		return GetterUtil.getString(_type);
	}

	public void setType(String type) {
		_type = type;
	}

	public String getTypeSettings() {
		return GetterUtil.getString(_typeSettings);
	}

	public void setTypeSettings(String typeSettings) {
		_typeSettings = typeSettings;
	}

	public boolean getHidden() {
		return _hidden;
	}

	public boolean isHidden() {
		return _hidden;
	}

	public void setHidden(boolean hidden) {
		_hidden = hidden;
	}

	public String getFriendlyURL() {
		return GetterUtil.getString(_friendlyURL);
	}

	public void setFriendlyURL(String friendlyURL) {
		_friendlyURL = friendlyURL;

		if (_originalFriendlyURL == null) {
			_originalFriendlyURL = friendlyURL;
		}
	}

	public String getOriginalFriendlyURL() {
		return GetterUtil.getString(_originalFriendlyURL);
	}

	public boolean getIconImage() {
		return _iconImage;
	}

	public boolean isIconImage() {
		return _iconImage;
	}

	public void setIconImage(boolean iconImage) {
		_iconImage = iconImage;
	}

	public long getIconImageId() {
		return _iconImageId;
	}

	public void setIconImageId(long iconImageId) {
		_iconImageId = iconImageId;

		if (!_setOriginalIconImageId) {
			_setOriginalIconImageId = true;

			_originalIconImageId = iconImageId;
		}
	}

	public long getOriginalIconImageId() {
		return _originalIconImageId;
	}

	public String getThemeId() {
		return GetterUtil.getString(_themeId);
	}

	public void setThemeId(String themeId) {
		_themeId = themeId;
	}

	public String getColorSchemeId() {
		return GetterUtil.getString(_colorSchemeId);
	}

	public void setColorSchemeId(String colorSchemeId) {
		_colorSchemeId = colorSchemeId;
	}

	public String getWapThemeId() {
		return GetterUtil.getString(_wapThemeId);
	}

	public void setWapThemeId(String wapThemeId) {
		_wapThemeId = wapThemeId;
	}

	public String getWapColorSchemeId() {
		return GetterUtil.getString(_wapColorSchemeId);
	}

	public void setWapColorSchemeId(String wapColorSchemeId) {
		_wapColorSchemeId = wapColorSchemeId;
	}

	public String getCss() {
		return GetterUtil.getString(_css);
	}

	public void setCss(String css) {
		_css = css;
	}

	public int getPriority() {
		return _priority;
	}

	public void setPriority(int priority) {
		_priority = priority;
	}

	public long getLayoutPrototypeId() {
		return _layoutPrototypeId;
	}

	public void setLayoutPrototypeId(long layoutPrototypeId) {
		_layoutPrototypeId = layoutPrototypeId;
	}

	public long getDlFolderId() {
		return _dlFolderId;
	}

	public void setDlFolderId(long dlFolderId) {
		_dlFolderId = dlFolderId;

		if (!_setOriginalDlFolderId) {
			_setOriginalDlFolderId = true;

			_originalDlFolderId = dlFolderId;
		}
	}

	public long getOriginalDlFolderId() {
		return _originalDlFolderId;
	}

	public Layout toEscapedModel() {
		if (isEscapedModel()) {
			return (Layout)this;
		}
		else {
			Layout model = new LayoutImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setPlid(getPlid());
			model.setGroupId(getGroupId());
			model.setCompanyId(getCompanyId());
			model.setPrivateLayout(getPrivateLayout());
			model.setLayoutId(getLayoutId());
			model.setParentLayoutId(getParentLayoutId());
			model.setName(HtmlUtil.escape(getName()));
			model.setTitle(HtmlUtil.escape(getTitle()));
			model.setDescription(HtmlUtil.escape(getDescription()));
			model.setType(HtmlUtil.escape(getType()));
			model.setTypeSettings(HtmlUtil.escape(getTypeSettings()));
			model.setHidden(getHidden());
			model.setFriendlyURL(HtmlUtil.escape(getFriendlyURL()));
			model.setIconImage(getIconImage());
			model.setIconImageId(getIconImageId());
			model.setThemeId(HtmlUtil.escape(getThemeId()));
			model.setColorSchemeId(HtmlUtil.escape(getColorSchemeId()));
			model.setWapThemeId(HtmlUtil.escape(getWapThemeId()));
			model.setWapColorSchemeId(HtmlUtil.escape(getWapColorSchemeId()));
			model.setCss(HtmlUtil.escape(getCss()));
			model.setPriority(getPriority());
			model.setLayoutPrototypeId(getLayoutPrototypeId());
			model.setDlFolderId(getDlFolderId());

			model = (Layout)Proxy.newProxyInstance(Layout.class.getClassLoader(),
					new Class[] { Layout.class }, new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					Layout.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
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
		clone.setDescription(getDescription());
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
		clone.setLayoutPrototypeId(getLayoutPrototypeId());
		clone.setDlFolderId(getDlFolderId());

		return clone;
	}

	public int compareTo(Layout layout) {
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

		Layout layout = null;

		try {
			layout = (Layout)obj;
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

	public String toString() {
		StringBundler sb = new StringBundler(47);

		sb.append("{plid=");
		sb.append(getPlid());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", privateLayout=");
		sb.append(getPrivateLayout());
		sb.append(", layoutId=");
		sb.append(getLayoutId());
		sb.append(", parentLayoutId=");
		sb.append(getParentLayoutId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", title=");
		sb.append(getTitle());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", typeSettings=");
		sb.append(getTypeSettings());
		sb.append(", hidden=");
		sb.append(getHidden());
		sb.append(", friendlyURL=");
		sb.append(getFriendlyURL());
		sb.append(", iconImage=");
		sb.append(getIconImage());
		sb.append(", iconImageId=");
		sb.append(getIconImageId());
		sb.append(", themeId=");
		sb.append(getThemeId());
		sb.append(", colorSchemeId=");
		sb.append(getColorSchemeId());
		sb.append(", wapThemeId=");
		sb.append(getWapThemeId());
		sb.append(", wapColorSchemeId=");
		sb.append(getWapColorSchemeId());
		sb.append(", css=");
		sb.append(getCss());
		sb.append(", priority=");
		sb.append(getPriority());
		sb.append(", layoutPrototypeId=");
		sb.append(getLayoutPrototypeId());
		sb.append(", dlFolderId=");
		sb.append(getDlFolderId());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(73);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.Layout");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>plid</column-name><column-value><![CDATA[");
		sb.append(getPlid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>privateLayout</column-name><column-value><![CDATA[");
		sb.append(getPrivateLayout());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>layoutId</column-name><column-value><![CDATA[");
		sb.append(getLayoutId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>parentLayoutId</column-name><column-value><![CDATA[");
		sb.append(getParentLayoutId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>title</column-name><column-value><![CDATA[");
		sb.append(getTitle());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>typeSettings</column-name><column-value><![CDATA[");
		sb.append(getTypeSettings());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>hidden</column-name><column-value><![CDATA[");
		sb.append(getHidden());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>friendlyURL</column-name><column-value><![CDATA[");
		sb.append(getFriendlyURL());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>iconImage</column-name><column-value><![CDATA[");
		sb.append(getIconImage());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>iconImageId</column-name><column-value><![CDATA[");
		sb.append(getIconImageId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>themeId</column-name><column-value><![CDATA[");
		sb.append(getThemeId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>colorSchemeId</column-name><column-value><![CDATA[");
		sb.append(getColorSchemeId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>wapThemeId</column-name><column-value><![CDATA[");
		sb.append(getWapThemeId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>wapColorSchemeId</column-name><column-value><![CDATA[");
		sb.append(getWapColorSchemeId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>css</column-name><column-value><![CDATA[");
		sb.append(getCss());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>priority</column-name><column-value><![CDATA[");
		sb.append(getPriority());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>layoutPrototypeId</column-name><column-value><![CDATA[");
		sb.append(getLayoutPrototypeId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>dlFolderId</column-name><column-value><![CDATA[");
		sb.append(getDlFolderId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _plid;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private boolean _privateLayout;
	private boolean _originalPrivateLayout;
	private boolean _setOriginalPrivateLayout;
	private long _layoutId;
	private long _originalLayoutId;
	private boolean _setOriginalLayoutId;
	private long _parentLayoutId;
	private String _name;
	private String _title;
	private String _description;
	private String _type;
	private String _typeSettings;
	private boolean _hidden;
	private String _friendlyURL;
	private String _originalFriendlyURL;
	private boolean _iconImage;
	private long _iconImageId;
	private long _originalIconImageId;
	private boolean _setOriginalIconImageId;
	private String _themeId;
	private String _colorSchemeId;
	private String _wapThemeId;
	private String _wapColorSchemeId;
	private String _css;
	private int _priority;
	private long _layoutPrototypeId;
	private long _dlFolderId;
	private long _originalDlFolderId;
	private boolean _setOriginalDlFolderId;
	private transient ExpandoBridge _expandoBridge;
}