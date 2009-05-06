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
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivitySoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="SocialActivityModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>SocialActivity</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.social.model.SocialActivity
 * @see com.liferay.portlet.social.model.SocialActivityModel
 * @see com.liferay.portlet.social.model.impl.SocialActivityImpl
 *
 */
public class SocialActivityModelImpl extends BaseModelImpl<SocialActivity> {
	public static final String TABLE_NAME = "SocialActivity";
	public static final Object[][] TABLE_COLUMNS = {
			{ "activityId", new Integer(Types.BIGINT) },
			

			{ "groupId", new Integer(Types.BIGINT) },
			

			{ "companyId", new Integer(Types.BIGINT) },
			

			{ "userId", new Integer(Types.BIGINT) },
			

			{ "createDate", new Integer(Types.TIMESTAMP) },
			

			{ "mirrorActivityId", new Integer(Types.BIGINT) },
			

			{ "classNameId", new Integer(Types.BIGINT) },
			

			{ "classPK", new Integer(Types.BIGINT) },
			

			{ "type_", new Integer(Types.INTEGER) },
			

			{ "extraData", new Integer(Types.VARCHAR) },
			

			{ "receiverUserId", new Integer(Types.BIGINT) }
		};
	public static final String TABLE_SQL_CREATE = "create table SocialActivity (activityId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,createDate DATE null,mirrorActivityId LONG,classNameId LONG,classPK LONG,type_ INTEGER,extraData STRING null,receiverUserId LONG)";
	public static final String TABLE_SQL_DROP = "drop table SocialActivity";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.social.model.SocialActivity"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.social.model.SocialActivity"),
			true);

	public static SocialActivity toModel(SocialActivitySoap soapModel) {
		SocialActivity model = new SocialActivityImpl();

		model.setActivityId(soapModel.getActivityId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setCreateDate(soapModel.getCreateDate());
		model.setMirrorActivityId(soapModel.getMirrorActivityId());
		model.setClassNameId(soapModel.getClassNameId());
		model.setClassPK(soapModel.getClassPK());
		model.setType(soapModel.getType());
		model.setExtraData(soapModel.getExtraData());
		model.setReceiverUserId(soapModel.getReceiverUserId());

		return model;
	}

	public static List<SocialActivity> toModels(SocialActivitySoap[] soapModels) {
		List<SocialActivity> models = new ArrayList<SocialActivity>(soapModels.length);

		for (SocialActivitySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.social.model.SocialActivity"));

	public SocialActivityModelImpl() {
	}

	public long getPrimaryKey() {
		return _activityId;
	}

	public void setPrimaryKey(long pk) {
		setActivityId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_activityId);
	}

	public long getActivityId() {
		return _activityId;
	}

	public void setActivityId(long activityId) {
		_activityId = activityId;
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

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = userId;
		}
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;

		if (_originalCreateDate == null) {
			_originalCreateDate = createDate;
		}
	}

	public Date getOriginalCreateDate() {
		return _originalCreateDate;
	}

	public long getMirrorActivityId() {
		return _mirrorActivityId;
	}

	public void setMirrorActivityId(long mirrorActivityId) {
		_mirrorActivityId = mirrorActivityId;

		if (!_setOriginalMirrorActivityId) {
			_setOriginalMirrorActivityId = true;

			_originalMirrorActivityId = mirrorActivityId;
		}
	}

	public long getOriginalMirrorActivityId() {
		return _originalMirrorActivityId;
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
		_classNameId = classNameId;

		if (!_setOriginalClassNameId) {
			_setOriginalClassNameId = true;

			_originalClassNameId = classNameId;
		}
	}

	public long getOriginalClassNameId() {
		return _originalClassNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;

		if (!_setOriginalClassPK) {
			_setOriginalClassPK = true;

			_originalClassPK = classPK;
		}
	}

	public long getOriginalClassPK() {
		return _originalClassPK;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;

		if (!_setOriginalType) {
			_setOriginalType = true;

			_originalType = type;
		}
	}

	public int getOriginalType() {
		return _originalType;
	}

	public String getExtraData() {
		return GetterUtil.getString(_extraData);
	}

	public void setExtraData(String extraData) {
		_extraData = extraData;
	}

	public long getReceiverUserId() {
		return _receiverUserId;
	}

	public void setReceiverUserId(long receiverUserId) {
		_receiverUserId = receiverUserId;

		if (!_setOriginalReceiverUserId) {
			_setOriginalReceiverUserId = true;

			_originalReceiverUserId = receiverUserId;
		}
	}

	public long getOriginalReceiverUserId() {
		return _originalReceiverUserId;
	}

	public SocialActivity toEscapedModel() {
		if (isEscapedModel()) {
			return (SocialActivity)this;
		}
		else {
			SocialActivity model = new SocialActivityImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setActivityId(getActivityId());
			model.setGroupId(getGroupId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setCreateDate(getCreateDate());
			model.setMirrorActivityId(getMirrorActivityId());
			model.setClassNameId(getClassNameId());
			model.setClassPK(getClassPK());
			model.setType(getType());
			model.setExtraData(HtmlUtil.escape(getExtraData()));
			model.setReceiverUserId(getReceiverUserId());

			model = (SocialActivity)Proxy.newProxyInstance(SocialActivity.class.getClassLoader(),
					new Class[] { SocialActivity.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = new ExpandoBridgeImpl(SocialActivity.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		SocialActivityImpl clone = new SocialActivityImpl();

		clone.setActivityId(getActivityId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setCreateDate(getCreateDate());
		clone.setMirrorActivityId(getMirrorActivityId());
		clone.setClassNameId(getClassNameId());
		clone.setClassPK(getClassPK());
		clone.setType(getType());
		clone.setExtraData(getExtraData());
		clone.setReceiverUserId(getReceiverUserId());

		return clone;
	}

	public int compareTo(SocialActivity socialActivity) {
		int value = 0;

		value = DateUtil.compareTo(getCreateDate(),
				socialActivity.getCreateDate());

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

		SocialActivity socialActivity = null;

		try {
			socialActivity = (SocialActivity)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = socialActivity.getPrimaryKey();

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

		sb.append("{activityId=");
		sb.append(getActivityId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", mirrorActivityId=");
		sb.append(getMirrorActivityId());
		sb.append(", classNameId=");
		sb.append(getClassNameId());
		sb.append(", classPK=");
		sb.append(getClassPK());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", extraData=");
		sb.append(getExtraData());
		sb.append(", receiverUserId=");
		sb.append(getReceiverUserId());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBuilder sb = new StringBuilder();

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.social.model.SocialActivity");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>activityId</column-name><column-value><![CDATA[");
		sb.append(getActivityId());
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
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>mirrorActivityId</column-name><column-value><![CDATA[");
		sb.append(getMirrorActivityId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classNameId</column-name><column-value><![CDATA[");
		sb.append(getClassNameId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classPK</column-name><column-value><![CDATA[");
		sb.append(getClassPK());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>extraData</column-name><column-value><![CDATA[");
		sb.append(getExtraData());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>receiverUserId</column-name><column-value><![CDATA[");
		sb.append(getReceiverUserId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _activityId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _userId;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private Date _createDate;
	private Date _originalCreateDate;
	private long _mirrorActivityId;
	private long _originalMirrorActivityId;
	private boolean _setOriginalMirrorActivityId;
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
	private transient ExpandoBridge _expandoBridge;
}