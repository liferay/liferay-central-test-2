/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.tags.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.impl.BaseModelImpl;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;
import com.liferay.portlet.tags.model.TagsProperty;
import com.liferay.portlet.tags.model.TagsPropertySoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="TagsPropertyModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>TagsProperty</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.tags.model.TagsProperty
 * @see com.liferay.portlet.tags.model.TagsPropertyModel
 * @see com.liferay.portlet.tags.model.impl.TagsPropertyImpl
 *
 */
public class TagsPropertyModelImpl extends BaseModelImpl<TagsProperty> {
	public static final String TABLE_NAME = "TagsProperty";
	public static final Object[][] TABLE_COLUMNS = {
			{ "propertyId", new Integer(Types.BIGINT) },
			

			{ "companyId", new Integer(Types.BIGINT) },
			

			{ "userId", new Integer(Types.BIGINT) },
			

			{ "userName", new Integer(Types.VARCHAR) },
			

			{ "createDate", new Integer(Types.TIMESTAMP) },
			

			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			

			{ "entryId", new Integer(Types.BIGINT) },
			

			{ "key_", new Integer(Types.VARCHAR) },
			

			{ "value", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table TagsProperty (propertyId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,entryId LONG,key_ VARCHAR(75) null,value VARCHAR(255) null)";
	public static final String TABLE_SQL_DROP = "drop table TagsProperty";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.tags.model.TagsProperty"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.tags.model.TagsProperty"),
			true);

	public static TagsProperty toModel(TagsPropertySoap soapModel) {
		TagsProperty model = new TagsPropertyImpl();

		model.setPropertyId(soapModel.getPropertyId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setEntryId(soapModel.getEntryId());
		model.setKey(soapModel.getKey());
		model.setValue(soapModel.getValue());

		return model;
	}

	public static List<TagsProperty> toModels(TagsPropertySoap[] soapModels) {
		List<TagsProperty> models = new ArrayList<TagsProperty>(soapModels.length);

		for (TagsPropertySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.tags.model.TagsProperty"));

	public TagsPropertyModelImpl() {
	}

	public long getPrimaryKey() {
		return _propertyId;
	}

	public void setPrimaryKey(long pk) {
		setPropertyId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_propertyId);
	}

	public long getPropertyId() {
		return _propertyId;
	}

	public void setPropertyId(long propertyId) {
		_propertyId = propertyId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return GetterUtil.getString(_userName);
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getEntryId() {
		return _entryId;
	}

	public void setEntryId(long entryId) {
		_entryId = entryId;

		if (!_setOriginalEntryId) {
			_setOriginalEntryId = true;

			_originalEntryId = entryId;
		}
	}

	public long getOriginalEntryId() {
		return _originalEntryId;
	}

	public String getKey() {
		return GetterUtil.getString(_key);
	}

	public void setKey(String key) {
		_key = key;

		if (_originalKey == null) {
			_originalKey = key;
		}
	}

	public String getOriginalKey() {
		return GetterUtil.getString(_originalKey);
	}

	public String getValue() {
		return GetterUtil.getString(_value);
	}

	public void setValue(String value) {
		_value = value;
	}

	public TagsProperty toEscapedModel() {
		if (isEscapedModel()) {
			return (TagsProperty)this;
		}
		else {
			TagsProperty model = new TagsPropertyImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setPropertyId(getPropertyId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setUserName(HtmlUtil.escape(getUserName()));
			model.setCreateDate(getCreateDate());
			model.setModifiedDate(getModifiedDate());
			model.setEntryId(getEntryId());
			model.setKey(HtmlUtil.escape(getKey()));
			model.setValue(HtmlUtil.escape(getValue()));

			model = (TagsProperty)Proxy.newProxyInstance(TagsProperty.class.getClassLoader(),
					new Class[] { TagsProperty.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = new ExpandoBridgeImpl(TagsProperty.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		TagsPropertyImpl clone = new TagsPropertyImpl();

		clone.setPropertyId(getPropertyId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setEntryId(getEntryId());
		clone.setKey(getKey());
		clone.setValue(getValue());

		return clone;
	}

	public int compareTo(TagsProperty tagsProperty) {
		int value = 0;

		value = getKey().compareTo(tagsProperty.getKey());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		TagsProperty tagsProperty = null;

		try {
			tagsProperty = (TagsProperty)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = tagsProperty.getPrimaryKey();

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
		StringBuilder sb = new StringBuilder();

		sb.append("{propertyId=");
		sb.append(getPropertyId());
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
		sb.append(", entryId=");
		sb.append(getEntryId());
		sb.append(", key=");
		sb.append(getKey());
		sb.append(", value=");
		sb.append(getValue());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBuilder sb = new StringBuilder();

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.tags.model.TagsProperty");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>propertyId</column-name><column-value><![CDATA[");
		sb.append("getPropertyId()");
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append("getCompanyId()");
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append("getUserId()");
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userName</column-name><column-value><![CDATA[");
		sb.append("getUserName()");
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append("getCreateDate()");
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append("getModifiedDate()");
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>entryId</column-name><column-value><![CDATA[");
		sb.append("getEntryId()");
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>key</column-name><column-value><![CDATA[");
		sb.append("getKey()");
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>value</column-name><column-value><![CDATA[");
		sb.append("getValue()");
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _propertyId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _entryId;
	private long _originalEntryId;
	private boolean _setOriginalEntryId;
	private String _key;
	private String _originalKey;
	private String _value;
	private transient ExpandoBridge _expandoBridge;
}