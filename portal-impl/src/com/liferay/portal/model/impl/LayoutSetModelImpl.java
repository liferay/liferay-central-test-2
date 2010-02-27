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
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetSoap;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="LayoutSetModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the LayoutSet table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutSetImpl
 * @see       com.liferay.portal.model.LayoutSet
 * @see       com.liferay.portal.model.LayoutSetModel
 * @generated
 */
public class LayoutSetModelImpl extends BaseModelImpl<LayoutSet> {
	public static final String TABLE_NAME = "LayoutSet";
	public static final Object[][] TABLE_COLUMNS = {
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
			{ "virtualHost", new Integer(Types.VARCHAR) },
			{ "layoutSetPrototypeId", new Integer(Types.BIGINT) }
		};
	public static final String TABLE_SQL_CREATE = "create table LayoutSet (layoutSetId LONG not null primary key,groupId LONG,companyId LONG,privateLayout BOOLEAN,logo BOOLEAN,logoId LONG,themeId VARCHAR(75) null,colorSchemeId VARCHAR(75) null,wapThemeId VARCHAR(75) null,wapColorSchemeId VARCHAR(75) null,css STRING null,pageCount INTEGER,virtualHost VARCHAR(75) null,layoutSetPrototypeId LONG)";
	public static final String TABLE_SQL_DROP = "drop table LayoutSet";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.LayoutSet"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.LayoutSet"),
			true);

	public static LayoutSet toModel(LayoutSetSoap soapModel) {
		LayoutSet model = new LayoutSetImpl();

		model.setLayoutSetId(soapModel.getLayoutSetId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setPrivateLayout(soapModel.getPrivateLayout());
		model.setLogo(soapModel.getLogo());
		model.setLogoId(soapModel.getLogoId());
		model.setThemeId(soapModel.getThemeId());
		model.setColorSchemeId(soapModel.getColorSchemeId());
		model.setWapThemeId(soapModel.getWapThemeId());
		model.setWapColorSchemeId(soapModel.getWapColorSchemeId());
		model.setCss(soapModel.getCss());
		model.setPageCount(soapModel.getPageCount());
		model.setVirtualHost(soapModel.getVirtualHost());
		model.setLayoutSetPrototypeId(soapModel.getLayoutSetPrototypeId());

		return model;
	}

	public static List<LayoutSet> toModels(LayoutSetSoap[] soapModels) {
		List<LayoutSet> models = new ArrayList<LayoutSet>(soapModels.length);

		for (LayoutSetSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.LayoutSet"));

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
		_layoutSetId = layoutSetId;
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

	public boolean getLogo() {
		return _logo;
	}

	public boolean isLogo() {
		return _logo;
	}

	public void setLogo(boolean logo) {
		_logo = logo;
	}

	public long getLogoId() {
		return _logoId;
	}

	public void setLogoId(long logoId) {
		_logoId = logoId;
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

	public int getPageCount() {
		return _pageCount;
	}

	public void setPageCount(int pageCount) {
		_pageCount = pageCount;
	}

	public String getVirtualHost() {
		return GetterUtil.getString(_virtualHost);
	}

	public void setVirtualHost(String virtualHost) {
		_virtualHost = virtualHost;

		if (_originalVirtualHost == null) {
			_originalVirtualHost = virtualHost;
		}
	}

	public String getOriginalVirtualHost() {
		return GetterUtil.getString(_originalVirtualHost);
	}

	public long getLayoutSetPrototypeId() {
		return _layoutSetPrototypeId;
	}

	public void setLayoutSetPrototypeId(long layoutSetPrototypeId) {
		_layoutSetPrototypeId = layoutSetPrototypeId;
	}

	public LayoutSet toEscapedModel() {
		if (isEscapedModel()) {
			return (LayoutSet)this;
		}
		else {
			LayoutSet model = new LayoutSetImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setLayoutSetId(getLayoutSetId());
			model.setGroupId(getGroupId());
			model.setCompanyId(getCompanyId());
			model.setPrivateLayout(getPrivateLayout());
			model.setLogo(getLogo());
			model.setLogoId(getLogoId());
			model.setThemeId(HtmlUtil.escape(getThemeId()));
			model.setColorSchemeId(HtmlUtil.escape(getColorSchemeId()));
			model.setWapThemeId(HtmlUtil.escape(getWapThemeId()));
			model.setWapColorSchemeId(HtmlUtil.escape(getWapColorSchemeId()));
			model.setCss(HtmlUtil.escape(getCss()));
			model.setPageCount(getPageCount());
			model.setVirtualHost(HtmlUtil.escape(getVirtualHost()));
			model.setLayoutSetPrototypeId(getLayoutSetPrototypeId());

			model = (LayoutSet)Proxy.newProxyInstance(LayoutSet.class.getClassLoader(),
					new Class[] { LayoutSet.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					LayoutSet.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
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
		clone.setLayoutSetPrototypeId(getLayoutSetPrototypeId());

		return clone;
	}

	public int compareTo(LayoutSet layoutSet) {
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

		LayoutSet layoutSet = null;

		try {
			layoutSet = (LayoutSet)obj;
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

	public String toString() {
		StringBundler sb = new StringBundler(29);

		sb.append("{layoutSetId=");
		sb.append(getLayoutSetId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", privateLayout=");
		sb.append(getPrivateLayout());
		sb.append(", logo=");
		sb.append(getLogo());
		sb.append(", logoId=");
		sb.append(getLogoId());
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
		sb.append(", pageCount=");
		sb.append(getPageCount());
		sb.append(", virtualHost=");
		sb.append(getVirtualHost());
		sb.append(", layoutSetPrototypeId=");
		sb.append(getLayoutSetPrototypeId());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(46);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.LayoutSet");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>layoutSetId</column-name><column-value><![CDATA[");
		sb.append(getLayoutSetId());
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
			"<column><column-name>logo</column-name><column-value><![CDATA[");
		sb.append(getLogo());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>logoId</column-name><column-value><![CDATA[");
		sb.append(getLogoId());
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
			"<column><column-name>pageCount</column-name><column-value><![CDATA[");
		sb.append(getPageCount());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>virtualHost</column-name><column-value><![CDATA[");
		sb.append(getVirtualHost());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>layoutSetPrototypeId</column-name><column-value><![CDATA[");
		sb.append(getLayoutSetPrototypeId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _layoutSetId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private boolean _privateLayout;
	private boolean _originalPrivateLayout;
	private boolean _setOriginalPrivateLayout;
	private boolean _logo;
	private long _logoId;
	private String _themeId;
	private String _colorSchemeId;
	private String _wapThemeId;
	private String _wapColorSchemeId;
	private String _css;
	private int _pageCount;
	private String _virtualHost;
	private String _originalVirtualHost;
	private long _layoutSetPrototypeId;
	private transient ExpandoBridge _expandoBridge;
}