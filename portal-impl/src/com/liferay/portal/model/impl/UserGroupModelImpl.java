/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.UserGroupSoap;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="UserGroupModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the UserGroup table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserGroupImpl
 * @see       com.liferay.portal.model.UserGroup
 * @see       com.liferay.portal.model.UserGroupModel
 * @generated
 */
public class UserGroupModelImpl extends BaseModelImpl<UserGroup> {
	public static final String TABLE_NAME = "UserGroup";
	public static final Object[][] TABLE_COLUMNS = {
			{ "userGroupId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "parentUserGroupId", new Integer(Types.BIGINT) },
			{ "name", new Integer(Types.VARCHAR) },
			{ "description", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table UserGroup (userGroupId LONG not null primary key,companyId LONG,parentUserGroupId LONG,name VARCHAR(75) null,description STRING null)";
	public static final String TABLE_SQL_DROP = "drop table UserGroup";
	public static final String ORDER_BY_JPQL = " ORDER BY userGroup.name ASC";
	public static final String ORDER_BY_SQL = " ORDER BY UserGroup.name ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.UserGroup"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.UserGroup"),
			true);

	public static UserGroup toModel(UserGroupSoap soapModel) {
		UserGroup model = new UserGroupImpl();

		model.setUserGroupId(soapModel.getUserGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setParentUserGroupId(soapModel.getParentUserGroupId());
		model.setName(soapModel.getName());
		model.setDescription(soapModel.getDescription());

		return model;
	}

	public static List<UserGroup> toModels(UserGroupSoap[] soapModels) {
		List<UserGroup> models = new ArrayList<UserGroup>(soapModels.length);

		for (UserGroupSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final String MAPPING_TABLE_USERS_USERGROUPS_NAME = com.liferay.portal.model.impl.UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME;
	public static final boolean FINDER_CACHE_ENABLED_USERS_USERGROUPS = com.liferay.portal.model.impl.UserModelImpl.FINDER_CACHE_ENABLED_USERS_USERGROUPS;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.UserGroup"));

	public UserGroupModelImpl() {
	}

	public long getPrimaryKey() {
		return _userGroupId;
	}

	public void setPrimaryKey(long pk) {
		setUserGroupId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_userGroupId);
	}

	public long getUserGroupId() {
		return _userGroupId;
	}

	public void setUserGroupId(long userGroupId) {
		_userGroupId = userGroupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = companyId;
		}
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	public long getParentUserGroupId() {
		return _parentUserGroupId;
	}

	public void setParentUserGroupId(long parentUserGroupId) {
		_parentUserGroupId = parentUserGroupId;
	}

	public String getName() {
		return GetterUtil.getString(_name);
	}

	public void setName(String name) {
		_name = name;

		if (_originalName == null) {
			_originalName = name;
		}
	}

	public String getOriginalName() {
		return GetterUtil.getString(_originalName);
	}

	public String getDescription() {
		return GetterUtil.getString(_description);
	}

	public void setDescription(String description) {
		_description = description;
	}

	public UserGroup toEscapedModel() {
		if (isEscapedModel()) {
			return (UserGroup)this;
		}
		else {
			UserGroup model = new UserGroupImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setUserGroupId(getUserGroupId());
			model.setCompanyId(getCompanyId());
			model.setParentUserGroupId(getParentUserGroupId());
			model.setName(HtmlUtil.escape(getName()));
			model.setDescription(HtmlUtil.escape(getDescription()));

			model = (UserGroup)Proxy.newProxyInstance(UserGroup.class.getClassLoader(),
					new Class[] { UserGroup.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					UserGroup.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		UserGroupImpl clone = new UserGroupImpl();

		clone.setUserGroupId(getUserGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setParentUserGroupId(getParentUserGroupId());
		clone.setName(getName());
		clone.setDescription(getDescription());

		return clone;
	}

	public int compareTo(UserGroup userGroup) {
		int value = 0;

		value = getName().compareTo(userGroup.getName());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		UserGroup userGroup = null;

		try {
			userGroup = (UserGroup)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = userGroup.getPrimaryKey();

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
		StringBundler sb = new StringBundler(11);

		sb.append("{userGroupId=");
		sb.append(getUserGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", parentUserGroupId=");
		sb.append(getParentUserGroupId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(19);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.UserGroup");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>userGroupId</column-name><column-value><![CDATA[");
		sb.append(getUserGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>parentUserGroupId</column-name><column-value><![CDATA[");
		sb.append(getParentUserGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _userGroupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _parentUserGroupId;
	private String _name;
	private String _originalName;
	private String _description;
	private transient ExpandoBridge _expandoBridge;
}