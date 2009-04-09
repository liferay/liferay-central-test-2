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

package com.liferay.portlet.social.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;
import com.liferay.portlet.social.model.SocialRequest;
import com.liferay.portlet.social.model.SocialRequestSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="SocialRequestModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>SocialRequest</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.social.model.SocialRequest
 * @see com.liferay.portlet.social.model.SocialRequestModel
 * @see com.liferay.portlet.social.model.impl.SocialRequestImpl
 *
 */
public class SocialRequestModelImpl extends BaseModelImpl<SocialRequest> {
	public static final String TABLE_NAME = "SocialRequest";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", new Integer(Types.VARCHAR) },
			

			{ "requestId", new Integer(Types.BIGINT) },
			

			{ "groupId", new Integer(Types.BIGINT) },
			

			{ "companyId", new Integer(Types.BIGINT) },
			

			{ "userId", new Integer(Types.BIGINT) },
			

			{ "createDate", new Integer(Types.TIMESTAMP) },
			

			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			

			{ "classNameId", new Integer(Types.BIGINT) },
			

			{ "classPK", new Integer(Types.BIGINT) },
			

			{ "type_", new Integer(Types.INTEGER) },
			

			{ "extraData", new Integer(Types.VARCHAR) },
			

			{ "receiverUserId", new Integer(Types.BIGINT) },
			

			{ "status", new Integer(Types.INTEGER) }
		};
	public static final String TABLE_SQL_CREATE = "create table SocialRequest (uuid_ VARCHAR(75) null,requestId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,type_ INTEGER,extraData STRING null,receiverUserId LONG,status INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table SocialRequest";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.social.model.SocialRequest"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.social.model.SocialRequest"),
			true);

	public static SocialRequest toModel(SocialRequestSoap soapModel) {
		SocialRequest model = new SocialRequestImpl();

		model.setUuid(soapModel.getUuid());
		model.setRequestId(soapModel.getRequestId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setClassNameId(soapModel.getClassNameId());
		model.setClassPK(soapModel.getClassPK());
		model.setType(soapModel.getType());
		model.setExtraData(soapModel.getExtraData());
		model.setReceiverUserId(soapModel.getReceiverUserId());
		model.setStatus(soapModel.getStatus());

		return model;
	}

	public static List<SocialRequest> toModels(SocialRequestSoap[] soapModels) {
		List<SocialRequest> models = new ArrayList<SocialRequest>(soapModels.length);

		for (SocialRequestSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.social.model.SocialRequest"));

	public SocialRequestModelImpl() {
	}

	public long getPrimaryKey() {
		return _requestId;
	}

	public void setPrimaryKey(long pk) {
		setRequestId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_requestId);
	}

	public String getUuid() {
		return GetterUtil.getString(_uuid);
	}

	public void setUuid(String uuid) {
		if ((uuid != null) && !uuid.equals(_uuid)) {
			_uuid = uuid;

			if (_originalUuid == null) {
				_originalUuid = uuid;
			}
		}
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	public long getRequestId() {
		return _requestId;
	}

	public void setRequestId(long requestId) {
		if (requestId != _requestId) {
			_requestId = requestId;
		}
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		if (groupId != _groupId) {
			_groupId = groupId;

			if (!_setOriginalGroupId) {
				_setOriginalGroupId = true;

				_originalGroupId = groupId;
			}
		}
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		if (companyId != _companyId) {
			_companyId = companyId;
		}
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		if (userId != _userId) {
			_userId = userId;

			if (!_setOriginalUserId) {
				_setOriginalUserId = true;

				_originalUserId = userId;
			}
		}
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		if ((createDate != _createDate) ||
				((createDate != null) && !createDate.equals(_createDate))) {
			_createDate = createDate;
		}
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		if ((modifiedDate != _modifiedDate) ||
				((modifiedDate != null) && !modifiedDate.equals(_modifiedDate))) {
			_modifiedDate = modifiedDate;
		}
	}

	public String getClassName() {
		if (getClassNameId() <= 0) {
			return StringPool.BLANK;
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		if (classNameId != _classNameId) {
			_classNameId = classNameId;

			if (!_setOriginalClassNameId) {
				_setOriginalClassNameId = true;

				_originalClassNameId = classNameId;
			}
		}
	}

	public long getOriginalClassNameId() {
		return _originalClassNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		if (classPK != _classPK) {
			_classPK = classPK;

			if (!_setOriginalClassPK) {
				_setOriginalClassPK = true;

				_originalClassPK = classPK;
			}
		}
	}

	public long getOriginalClassPK() {
		return _originalClassPK;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		if (type != _type) {
			_type = type;

			if (!_setOriginalType) {
				_setOriginalType = true;

				_originalType = type;
			}
		}
	}

	public int getOriginalType() {
		return _originalType;
	}

	public String getExtraData() {
		return GetterUtil.getString(_extraData);
	}

	public void setExtraData(String extraData) {
		if ((extraData != _extraData) ||
				((extraData != null) && !extraData.equals(_extraData))) {
			_extraData = extraData;
		}
	}

	public long getReceiverUserId() {
		return _receiverUserId;
	}

	public void setReceiverUserId(long receiverUserId) {
		if (receiverUserId != _receiverUserId) {
			_receiverUserId = receiverUserId;

			if (!_setOriginalReceiverUserId) {
				_setOriginalReceiverUserId = true;

				_originalReceiverUserId = receiverUserId;
			}
		}
	}

	public long getOriginalReceiverUserId() {
		return _originalReceiverUserId;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		if (status != _status) {
			_status = status;

			if (!_setOriginalStatus) {
				_setOriginalStatus = true;

				_originalStatus = status;
			}
		}
	}

	public int getOriginalStatus() {
		return _originalStatus;
	}

	public SocialRequest toEscapedModel() {
		if (isEscapedModel()) {
			return (SocialRequest)this;
		}
		else {
			SocialRequest model = new SocialRequestImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setUuid(HtmlUtil.escape(getUuid()));
			model.setRequestId(getRequestId());
			model.setGroupId(getGroupId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setCreateDate(getCreateDate());
			model.setModifiedDate(getModifiedDate());
			model.setClassNameId(getClassNameId());
			model.setClassPK(getClassPK());
			model.setType(getType());
			model.setExtraData(HtmlUtil.escape(getExtraData()));
			model.setReceiverUserId(getReceiverUserId());
			model.setStatus(getStatus());

			model = (SocialRequest)Proxy.newProxyInstance(SocialRequest.class.getClassLoader(),
					new Class[] { SocialRequest.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = new ExpandoBridgeImpl(SocialRequest.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		SocialRequestImpl clone = new SocialRequestImpl();

		clone.setUuid(getUuid());
		clone.setRequestId(getRequestId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setClassNameId(getClassNameId());
		clone.setClassPK(getClassPK());
		clone.setType(getType());
		clone.setExtraData(getExtraData());
		clone.setReceiverUserId(getReceiverUserId());
		clone.setStatus(getStatus());

		return clone;
	}

	public int compareTo(SocialRequest socialRequest) {
		int value = 0;

		if (getRequestId() < socialRequest.getRequestId()) {
			value = -1;
		}
		else if (getRequestId() > socialRequest.getRequestId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		value = value * -1;

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		SocialRequest socialRequest = null;

		try {
			socialRequest = (SocialRequest)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = socialRequest.getPrimaryKey();

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

	private String _uuid;
	private String _originalUuid;
	private long _requestId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _userId;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private int _type;
	private int _originalType;
	private boolean _setOriginalType;
	private String _extraData;
	private long _receiverUserId;
	private long _originalReceiverUserId;
	private boolean _setOriginalReceiverUserId;
	private int _status;
	private int _originalStatus;
	private boolean _setOriginalStatus;
	private transient ExpandoBridge _expandoBridge;
}