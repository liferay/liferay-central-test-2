/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.friendly.url.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;

import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalizationModel;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * The base model implementation for the FriendlyURLEntryLocalization service. Represents a row in the &quot;FriendlyURLEntryLocalization&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link FriendlyURLEntryLocalizationModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link FriendlyURLEntryLocalizationImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLEntryLocalizationImpl
 * @see FriendlyURLEntryLocalization
 * @see FriendlyURLEntryLocalizationModel
 * @generated
 */
@ProviderType
public class FriendlyURLEntryLocalizationModelImpl extends BaseModelImpl<FriendlyURLEntryLocalization>
	implements FriendlyURLEntryLocalizationModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a friendly url entry localization model instance should use the {@link FriendlyURLEntryLocalization} interface instead.
	 */
	public static final String TABLE_NAME = "FriendlyURLEntryLocalization";
	public static final Object[][] TABLE_COLUMNS = {
			{ "friendlyURLEntryLocalizationId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "friendlyURLEntryId", Types.BIGINT },
			{ "urlTitle", Types.VARCHAR },
			{ "languageId", Types.VARCHAR }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("friendlyURLEntryLocalizationId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("friendlyURLEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("urlTitle", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("languageId", Types.VARCHAR);
	}

	public static final String TABLE_SQL_CREATE = "create table FriendlyURLEntryLocalization (friendlyURLEntryLocalizationId LONG not null primary key,groupId LONG,companyId LONG,friendlyURLEntryId LONG,urlTitle VARCHAR(255) null,languageId VARCHAR(75) null)";
	public static final String TABLE_SQL_DROP = "drop table FriendlyURLEntryLocalization";
	public static final String ORDER_BY_JPQL = " ORDER BY friendlyURLEntryLocalization.friendlyURLEntryLocalizationId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY FriendlyURLEntryLocalization.friendlyURLEntryLocalizationId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.friendly.url.service.util.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.friendly.url.model.FriendlyURLEntryLocalization"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.friendly.url.service.util.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.friendly.url.model.FriendlyURLEntryLocalization"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.friendly.url.service.util.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.liferay.friendly.url.model.FriendlyURLEntryLocalization"),
			true);
	public static final long FRIENDLYURLENTRYID_COLUMN_BITMASK = 1L;
	public static final long GROUPID_COLUMN_BITMASK = 2L;
	public static final long LANGUAGEID_COLUMN_BITMASK = 4L;
	public static final long URLTITLE_COLUMN_BITMASK = 8L;
	public static final long FRIENDLYURLENTRYLOCALIZATIONID_COLUMN_BITMASK = 16L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.friendly.url.service.util.ServiceProps.get(
				"lock.expiration.time.com.liferay.friendly.url.model.FriendlyURLEntryLocalization"));

	public FriendlyURLEntryLocalizationModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _friendlyURLEntryLocalizationId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setFriendlyURLEntryLocalizationId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _friendlyURLEntryLocalizationId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return FriendlyURLEntryLocalization.class;
	}

	@Override
	public String getModelClassName() {
		return FriendlyURLEntryLocalization.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("friendlyURLEntryLocalizationId",
			getFriendlyURLEntryLocalizationId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("friendlyURLEntryId", getFriendlyURLEntryId());
		attributes.put("urlTitle", getUrlTitle());
		attributes.put("languageId", getLanguageId());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long friendlyURLEntryLocalizationId = (Long)attributes.get(
				"friendlyURLEntryLocalizationId");

		if (friendlyURLEntryLocalizationId != null) {
			setFriendlyURLEntryLocalizationId(friendlyURLEntryLocalizationId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long friendlyURLEntryId = (Long)attributes.get("friendlyURLEntryId");

		if (friendlyURLEntryId != null) {
			setFriendlyURLEntryId(friendlyURLEntryId);
		}

		String urlTitle = (String)attributes.get("urlTitle");

		if (urlTitle != null) {
			setUrlTitle(urlTitle);
		}

		String languageId = (String)attributes.get("languageId");

		if (languageId != null) {
			setLanguageId(languageId);
		}
	}

	@Override
	public long getFriendlyURLEntryLocalizationId() {
		return _friendlyURLEntryLocalizationId;
	}

	@Override
	public void setFriendlyURLEntryLocalizationId(
		long friendlyURLEntryLocalizationId) {
		_friendlyURLEntryLocalizationId = friendlyURLEntryLocalizationId;
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_columnBitmask |= GROUPID_COLUMN_BITMASK;

		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = _groupId;
		}

		_groupId = groupId;
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@Override
	public long getFriendlyURLEntryId() {
		return _friendlyURLEntryId;
	}

	@Override
	public void setFriendlyURLEntryId(long friendlyURLEntryId) {
		_columnBitmask |= FRIENDLYURLENTRYID_COLUMN_BITMASK;

		if (!_setOriginalFriendlyURLEntryId) {
			_setOriginalFriendlyURLEntryId = true;

			_originalFriendlyURLEntryId = _friendlyURLEntryId;
		}

		_friendlyURLEntryId = friendlyURLEntryId;
	}

	public long getOriginalFriendlyURLEntryId() {
		return _originalFriendlyURLEntryId;
	}

	@Override
	public String getUrlTitle() {
		if (_urlTitle == null) {
			return StringPool.BLANK;
		}
		else {
			return _urlTitle;
		}
	}

	@Override
	public void setUrlTitle(String urlTitle) {
		_columnBitmask |= URLTITLE_COLUMN_BITMASK;

		if (_originalUrlTitle == null) {
			_originalUrlTitle = _urlTitle;
		}

		_urlTitle = urlTitle;
	}

	public String getOriginalUrlTitle() {
		return GetterUtil.getString(_originalUrlTitle);
	}

	@Override
	public String getLanguageId() {
		if (_languageId == null) {
			return StringPool.BLANK;
		}
		else {
			return _languageId;
		}
	}

	@Override
	public void setLanguageId(String languageId) {
		_columnBitmask |= LANGUAGEID_COLUMN_BITMASK;

		if (_originalLanguageId == null) {
			_originalLanguageId = _languageId;
		}

		_languageId = languageId;
	}

	public String getOriginalLanguageId() {
		return GetterUtil.getString(_originalLanguageId);
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			FriendlyURLEntryLocalization.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public FriendlyURLEntryLocalization toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (FriendlyURLEntryLocalization)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		FriendlyURLEntryLocalizationImpl friendlyURLEntryLocalizationImpl = new FriendlyURLEntryLocalizationImpl();

		friendlyURLEntryLocalizationImpl.setFriendlyURLEntryLocalizationId(getFriendlyURLEntryLocalizationId());
		friendlyURLEntryLocalizationImpl.setGroupId(getGroupId());
		friendlyURLEntryLocalizationImpl.setCompanyId(getCompanyId());
		friendlyURLEntryLocalizationImpl.setFriendlyURLEntryId(getFriendlyURLEntryId());
		friendlyURLEntryLocalizationImpl.setUrlTitle(getUrlTitle());
		friendlyURLEntryLocalizationImpl.setLanguageId(getLanguageId());

		friendlyURLEntryLocalizationImpl.resetOriginalValues();

		return friendlyURLEntryLocalizationImpl;
	}

	@Override
	public int compareTo(
		FriendlyURLEntryLocalization friendlyURLEntryLocalization) {
		long primaryKey = friendlyURLEntryLocalization.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof FriendlyURLEntryLocalization)) {
			return false;
		}

		FriendlyURLEntryLocalization friendlyURLEntryLocalization = (FriendlyURLEntryLocalization)obj;

		long primaryKey = friendlyURLEntryLocalization.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		FriendlyURLEntryLocalizationModelImpl friendlyURLEntryLocalizationModelImpl =
			this;

		friendlyURLEntryLocalizationModelImpl._originalGroupId = friendlyURLEntryLocalizationModelImpl._groupId;

		friendlyURLEntryLocalizationModelImpl._setOriginalGroupId = false;

		friendlyURLEntryLocalizationModelImpl._originalFriendlyURLEntryId = friendlyURLEntryLocalizationModelImpl._friendlyURLEntryId;

		friendlyURLEntryLocalizationModelImpl._setOriginalFriendlyURLEntryId = false;

		friendlyURLEntryLocalizationModelImpl._originalUrlTitle = friendlyURLEntryLocalizationModelImpl._urlTitle;

		friendlyURLEntryLocalizationModelImpl._originalLanguageId = friendlyURLEntryLocalizationModelImpl._languageId;

		friendlyURLEntryLocalizationModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<FriendlyURLEntryLocalization> toCacheModel() {
		FriendlyURLEntryLocalizationCacheModel friendlyURLEntryLocalizationCacheModel =
			new FriendlyURLEntryLocalizationCacheModel();

		friendlyURLEntryLocalizationCacheModel.friendlyURLEntryLocalizationId = getFriendlyURLEntryLocalizationId();

		friendlyURLEntryLocalizationCacheModel.groupId = getGroupId();

		friendlyURLEntryLocalizationCacheModel.companyId = getCompanyId();

		friendlyURLEntryLocalizationCacheModel.friendlyURLEntryId = getFriendlyURLEntryId();

		friendlyURLEntryLocalizationCacheModel.urlTitle = getUrlTitle();

		String urlTitle = friendlyURLEntryLocalizationCacheModel.urlTitle;

		if ((urlTitle != null) && (urlTitle.length() == 0)) {
			friendlyURLEntryLocalizationCacheModel.urlTitle = null;
		}

		friendlyURLEntryLocalizationCacheModel.languageId = getLanguageId();

		String languageId = friendlyURLEntryLocalizationCacheModel.languageId;

		if ((languageId != null) && (languageId.length() == 0)) {
			friendlyURLEntryLocalizationCacheModel.languageId = null;
		}

		return friendlyURLEntryLocalizationCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{friendlyURLEntryLocalizationId=");
		sb.append(getFriendlyURLEntryLocalizationId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", friendlyURLEntryId=");
		sb.append(getFriendlyURLEntryId());
		sb.append(", urlTitle=");
		sb.append(getUrlTitle());
		sb.append(", languageId=");
		sb.append(getLanguageId());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(22);

		sb.append("<model><model-name>");
		sb.append("com.liferay.friendly.url.model.FriendlyURLEntryLocalization");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>friendlyURLEntryLocalizationId</column-name><column-value><![CDATA[");
		sb.append(getFriendlyURLEntryLocalizationId());
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
			"<column><column-name>friendlyURLEntryId</column-name><column-value><![CDATA[");
		sb.append(getFriendlyURLEntryId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>urlTitle</column-name><column-value><![CDATA[");
		sb.append(getUrlTitle());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>languageId</column-name><column-value><![CDATA[");
		sb.append(getLanguageId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = FriendlyURLEntryLocalization.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			FriendlyURLEntryLocalization.class
		};
	private long _friendlyURLEntryLocalizationId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _friendlyURLEntryId;
	private long _originalFriendlyURLEntryId;
	private boolean _setOriginalFriendlyURLEntryId;
	private String _urlTitle;
	private String _originalUrlTitle;
	private String _languageId;
	private String _originalLanguageId;
	private long _columnBitmask;
	private FriendlyURLEntryLocalization _escapedModel;
}