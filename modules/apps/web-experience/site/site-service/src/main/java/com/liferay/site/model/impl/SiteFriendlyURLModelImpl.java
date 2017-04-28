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

package com.liferay.site.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import com.liferay.site.model.SiteFriendlyURL;
import com.liferay.site.model.SiteFriendlyURLModel;

import java.io.Serializable;

import java.sql.Types;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The base model implementation for the SiteFriendlyURL service. Represents a row in the &quot;SiteFriendlyURL&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link SiteFriendlyURLModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link SiteFriendlyURLImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SiteFriendlyURLImpl
 * @see SiteFriendlyURL
 * @see SiteFriendlyURLModel
 * @generated
 */
@ProviderType
public class SiteFriendlyURLModelImpl extends BaseModelImpl<SiteFriendlyURL>
	implements SiteFriendlyURLModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a site friendly url model instance should use the {@link SiteFriendlyURL} interface instead.
	 */
	public static final String TABLE_NAME = "SiteFriendlyURL";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "siteFriendlyURLId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "groupId", Types.BIGINT },
			{ "friendlyURL", Types.VARCHAR },
			{ "languageId", Types.VARCHAR },
			{ "lastPublishDate", Types.TIMESTAMP }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("siteFriendlyURLId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("friendlyURL", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("languageId", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);
	}

	public static final String TABLE_SQL_CREATE = "create table SiteFriendlyURL (uuid_ VARCHAR(75) null,siteFriendlyURLId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,groupId LONG,friendlyURL VARCHAR(75) null,languageId VARCHAR(75) null,lastPublishDate DATE null)";
	public static final String TABLE_SQL_DROP = "drop table SiteFriendlyURL";
	public static final String ORDER_BY_JPQL = " ORDER BY siteFriendlyURL.siteFriendlyURLId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY SiteFriendlyURL.siteFriendlyURLId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.site.service.util.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.site.model.SiteFriendlyURL"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.site.service.util.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.site.model.SiteFriendlyURL"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.site.service.util.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.liferay.site.model.SiteFriendlyURL"),
			true);
	public static final long COMPANYID_COLUMN_BITMASK = 1L;
	public static final long FRIENDLYURL_COLUMN_BITMASK = 2L;
	public static final long GROUPID_COLUMN_BITMASK = 4L;
	public static final long LANGUAGEID_COLUMN_BITMASK = 8L;
	public static final long UUID_COLUMN_BITMASK = 16L;
	public static final long SITEFRIENDLYURLID_COLUMN_BITMASK = 32L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.site.service.util.ServiceProps.get(
				"lock.expiration.time.com.liferay.site.model.SiteFriendlyURL"));

	public SiteFriendlyURLModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _siteFriendlyURLId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setSiteFriendlyURLId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _siteFriendlyURLId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return SiteFriendlyURL.class;
	}

	@Override
	public String getModelClassName() {
		return SiteFriendlyURL.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("siteFriendlyURLId", getSiteFriendlyURLId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("groupId", getGroupId());
		attributes.put("friendlyURL", getFriendlyURL());
		attributes.put("languageId", getLanguageId());
		attributes.put("lastPublishDate", getLastPublishDate());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long siteFriendlyURLId = (Long)attributes.get("siteFriendlyURLId");

		if (siteFriendlyURLId != null) {
			setSiteFriendlyURLId(siteFriendlyURLId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		String friendlyURL = (String)attributes.get("friendlyURL");

		if (friendlyURL != null) {
			setFriendlyURL(friendlyURL);
		}

		String languageId = (String)attributes.get("languageId");

		if (languageId != null) {
			setLanguageId(languageId);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public String getUuid() {
		if (_uuid == null) {
			return StringPool.BLANK;
		}
		else {
			return _uuid;
		}
	}

	@Override
	public void setUuid(String uuid) {
		if (_originalUuid == null) {
			_originalUuid = _uuid;
		}

		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	@Override
	public long getSiteFriendlyURLId() {
		return _siteFriendlyURLId;
	}

	@Override
	public void setSiteFriendlyURLId(long siteFriendlyURLId) {
		_siteFriendlyURLId = siteFriendlyURLId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_columnBitmask |= COMPANYID_COLUMN_BITMASK;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = _companyId;
		}

		_companyId = companyId;
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return StringPool.BLANK;
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@Override
	public String getUserName() {
		if (_userName == null) {
			return StringPool.BLANK;
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		_modifiedDate = modifiedDate;
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
	public String getFriendlyURL() {
		if (_friendlyURL == null) {
			return StringPool.BLANK;
		}
		else {
			return _friendlyURL;
		}
	}

	@Override
	public void setFriendlyURL(String friendlyURL) {
		_columnBitmask |= FRIENDLYURL_COLUMN_BITMASK;

		if (_originalFriendlyURL == null) {
			_originalFriendlyURL = _friendlyURL;
		}

		_friendlyURL = friendlyURL;
	}

	public String getOriginalFriendlyURL() {
		return GetterUtil.getString(_originalFriendlyURL);
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

	@Override
	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(PortalUtil.getClassNameId(
				SiteFriendlyURL.class.getName()));
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			SiteFriendlyURL.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public SiteFriendlyURL toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (SiteFriendlyURL)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		SiteFriendlyURLImpl siteFriendlyURLImpl = new SiteFriendlyURLImpl();

		siteFriendlyURLImpl.setUuid(getUuid());
		siteFriendlyURLImpl.setSiteFriendlyURLId(getSiteFriendlyURLId());
		siteFriendlyURLImpl.setCompanyId(getCompanyId());
		siteFriendlyURLImpl.setUserId(getUserId());
		siteFriendlyURLImpl.setUserName(getUserName());
		siteFriendlyURLImpl.setCreateDate(getCreateDate());
		siteFriendlyURLImpl.setModifiedDate(getModifiedDate());
		siteFriendlyURLImpl.setGroupId(getGroupId());
		siteFriendlyURLImpl.setFriendlyURL(getFriendlyURL());
		siteFriendlyURLImpl.setLanguageId(getLanguageId());
		siteFriendlyURLImpl.setLastPublishDate(getLastPublishDate());

		siteFriendlyURLImpl.resetOriginalValues();

		return siteFriendlyURLImpl;
	}

	@Override
	public int compareTo(SiteFriendlyURL siteFriendlyURL) {
		long primaryKey = siteFriendlyURL.getPrimaryKey();

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

		if (!(obj instanceof SiteFriendlyURL)) {
			return false;
		}

		SiteFriendlyURL siteFriendlyURL = (SiteFriendlyURL)obj;

		long primaryKey = siteFriendlyURL.getPrimaryKey();

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
		SiteFriendlyURLModelImpl siteFriendlyURLModelImpl = this;

		siteFriendlyURLModelImpl._originalUuid = siteFriendlyURLModelImpl._uuid;

		siteFriendlyURLModelImpl._originalCompanyId = siteFriendlyURLModelImpl._companyId;

		siteFriendlyURLModelImpl._setOriginalCompanyId = false;

		siteFriendlyURLModelImpl._setModifiedDate = false;

		siteFriendlyURLModelImpl._originalGroupId = siteFriendlyURLModelImpl._groupId;

		siteFriendlyURLModelImpl._setOriginalGroupId = false;

		siteFriendlyURLModelImpl._originalFriendlyURL = siteFriendlyURLModelImpl._friendlyURL;

		siteFriendlyURLModelImpl._originalLanguageId = siteFriendlyURLModelImpl._languageId;

		siteFriendlyURLModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<SiteFriendlyURL> toCacheModel() {
		SiteFriendlyURLCacheModel siteFriendlyURLCacheModel = new SiteFriendlyURLCacheModel();

		siteFriendlyURLCacheModel.uuid = getUuid();

		String uuid = siteFriendlyURLCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			siteFriendlyURLCacheModel.uuid = null;
		}

		siteFriendlyURLCacheModel.siteFriendlyURLId = getSiteFriendlyURLId();

		siteFriendlyURLCacheModel.companyId = getCompanyId();

		siteFriendlyURLCacheModel.userId = getUserId();

		siteFriendlyURLCacheModel.userName = getUserName();

		String userName = siteFriendlyURLCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			siteFriendlyURLCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			siteFriendlyURLCacheModel.createDate = createDate.getTime();
		}
		else {
			siteFriendlyURLCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			siteFriendlyURLCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			siteFriendlyURLCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		siteFriendlyURLCacheModel.groupId = getGroupId();

		siteFriendlyURLCacheModel.friendlyURL = getFriendlyURL();

		String friendlyURL = siteFriendlyURLCacheModel.friendlyURL;

		if ((friendlyURL != null) && (friendlyURL.length() == 0)) {
			siteFriendlyURLCacheModel.friendlyURL = null;
		}

		siteFriendlyURLCacheModel.languageId = getLanguageId();

		String languageId = siteFriendlyURLCacheModel.languageId;

		if ((languageId != null) && (languageId.length() == 0)) {
			siteFriendlyURLCacheModel.languageId = null;
		}

		Date lastPublishDate = getLastPublishDate();

		if (lastPublishDate != null) {
			siteFriendlyURLCacheModel.lastPublishDate = lastPublishDate.getTime();
		}
		else {
			siteFriendlyURLCacheModel.lastPublishDate = Long.MIN_VALUE;
		}

		return siteFriendlyURLCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", siteFriendlyURLId=");
		sb.append(getSiteFriendlyURLId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", friendlyURL=");
		sb.append(getFriendlyURL());
		sb.append(", languageId=");
		sb.append(getLanguageId());
		sb.append(", lastPublishDate=");
		sb.append(getLastPublishDate());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(37);

		sb.append("<model><model-name>");
		sb.append("com.liferay.site.model.SiteFriendlyURL");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>siteFriendlyURLId</column-name><column-value><![CDATA[");
		sb.append(getSiteFriendlyURLId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userName</column-name><column-value><![CDATA[");
		sb.append(getUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>friendlyURL</column-name><column-value><![CDATA[");
		sb.append(getFriendlyURL());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>languageId</column-name><column-value><![CDATA[");
		sb.append(getLanguageId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lastPublishDate</column-name><column-value><![CDATA[");
		sb.append(getLastPublishDate());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = SiteFriendlyURL.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			SiteFriendlyURL.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _siteFriendlyURLId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private String _friendlyURL;
	private String _originalFriendlyURL;
	private String _languageId;
	private String _originalLanguageId;
	private Date _lastPublishDate;
	private long _columnBitmask;
	private SiteFriendlyURL _escapedModel;
}