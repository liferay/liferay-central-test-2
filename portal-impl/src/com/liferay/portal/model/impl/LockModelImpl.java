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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.LockSoap;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="LockModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the Lock_ table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LockImpl
 * @see       com.liferay.portal.model.Lock
 * @see       com.liferay.portal.model.LockModel
 * @generated
 */
public class LockModelImpl extends BaseModelImpl<Lock> {
	public static final String TABLE_NAME = "Lock_";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", new Integer(Types.VARCHAR) },
			{ "lockId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.BIGINT) },
			{ "userName", new Integer(Types.VARCHAR) },
			{ "createDate", new Integer(Types.TIMESTAMP) },
			{ "className", new Integer(Types.VARCHAR) },
			{ "key_", new Integer(Types.VARCHAR) },
			{ "owner", new Integer(Types.VARCHAR) },
			{ "inheritable", new Integer(Types.BOOLEAN) },
			{ "expirationDate", new Integer(Types.TIMESTAMP) }
		};
	public static final String TABLE_SQL_CREATE = "create table Lock_ (uuid_ VARCHAR(75) null,lockId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,className VARCHAR(75) null,key_ VARCHAR(200) null,owner VARCHAR(75) null,inheritable BOOLEAN,expirationDate DATE null)";
	public static final String TABLE_SQL_DROP = "drop table Lock_";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.Lock"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.Lock"),
			true);

	public static Lock toModel(LockSoap soapModel) {
		Lock model = new LockImpl();

		model.setUuid(soapModel.getUuid());
		model.setLockId(soapModel.getLockId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setClassName(soapModel.getClassName());
		model.setKey(soapModel.getKey());
		model.setOwner(soapModel.getOwner());
		model.setInheritable(soapModel.getInheritable());
		model.setExpirationDate(soapModel.getExpirationDate());

		return model;
	}

	public static List<Lock> toModels(LockSoap[] soapModels) {
		List<Lock> models = new ArrayList<Lock>(soapModels.length);

		for (LockSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.Lock"));

	public LockModelImpl() {
	}

	public long getPrimaryKey() {
		return _lockId;
	}

	public void setPrimaryKey(long pk) {
		setLockId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_lockId);
	}

	public String getUuid() {
		return GetterUtil.getString(_uuid);
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getLockId() {
		return _lockId;
	}

	public void setLockId(long lockId) {
		_lockId = lockId;
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

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
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

	public String getClassName() {
		return GetterUtil.getString(_className);
	}

	public void setClassName(String className) {
		_className = className;

		if (_originalClassName == null) {
			_originalClassName = className;
		}
	}

	public String getOriginalClassName() {
		return GetterUtil.getString(_originalClassName);
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

	public String getOwner() {
		return GetterUtil.getString(_owner);
	}

	public void setOwner(String owner) {
		_owner = owner;
	}

	public boolean getInheritable() {
		return _inheritable;
	}

	public boolean isInheritable() {
		return _inheritable;
	}

	public void setInheritable(boolean inheritable) {
		_inheritable = inheritable;
	}

	public Date getExpirationDate() {
		return _expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		_expirationDate = expirationDate;
	}

	public Lock toEscapedModel() {
		if (isEscapedModel()) {
			return (Lock)this;
		}
		else {
			Lock model = new LockImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setUuid(HtmlUtil.escape(getUuid()));
			model.setLockId(getLockId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setUserName(HtmlUtil.escape(getUserName()));
			model.setCreateDate(getCreateDate());
			model.setClassName(HtmlUtil.escape(getClassName()));
			model.setKey(HtmlUtil.escape(getKey()));
			model.setOwner(HtmlUtil.escape(getOwner()));
			model.setInheritable(getInheritable());
			model.setExpirationDate(getExpirationDate());

			model = (Lock)Proxy.newProxyInstance(Lock.class.getClassLoader(),
					new Class[] { Lock.class }, new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					Lock.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		LockImpl clone = new LockImpl();

		clone.setUuid(getUuid());
		clone.setLockId(getLockId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setClassName(getClassName());
		clone.setKey(getKey());
		clone.setOwner(getOwner());
		clone.setInheritable(getInheritable());
		clone.setExpirationDate(getExpirationDate());

		return clone;
	}

	public int compareTo(Lock lock) {
		long pk = lock.getPrimaryKey();

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

		Lock lock = null;

		try {
			lock = (Lock)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = lock.getPrimaryKey();

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
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", lockId=");
		sb.append(getLockId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", className=");
		sb.append(getClassName());
		sb.append(", key=");
		sb.append(getKey());
		sb.append(", owner=");
		sb.append(getOwner());
		sb.append(", inheritable=");
		sb.append(getInheritable());
		sb.append(", expirationDate=");
		sb.append(getExpirationDate());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(37);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.Lock");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lockId</column-name><column-value><![CDATA[");
		sb.append(getLockId());
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
			"<column><column-name>className</column-name><column-value><![CDATA[");
		sb.append(getClassName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>key</column-name><column-value><![CDATA[");
		sb.append(getKey());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>owner</column-name><column-value><![CDATA[");
		sb.append(getOwner());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>inheritable</column-name><column-value><![CDATA[");
		sb.append(getInheritable());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>expirationDate</column-name><column-value><![CDATA[");
		sb.append(getExpirationDate());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private String _uuid;
	private long _lockId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private String _userName;
	private Date _createDate;
	private String _className;
	private String _originalClassName;
	private String _key;
	private String _originalKey;
	private String _owner;
	private boolean _inheritable;
	private Date _expirationDate;
	private transient ExpandoBridge _expandoBridge;
}