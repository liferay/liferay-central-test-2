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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.MembershipRequest;
import com.liferay.portal.model.MembershipRequestSoap;
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
 * <a href="MembershipRequestModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the MembershipRequest table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MembershipRequestImpl
 * @see       com.liferay.portal.model.MembershipRequest
 * @see       com.liferay.portal.model.MembershipRequestModel
 * @generated
 */
public class MembershipRequestModelImpl extends BaseModelImpl<MembershipRequest> {
	public static final String TABLE_NAME = "MembershipRequest";
	public static final Object[][] TABLE_COLUMNS = {
			{ "membershipRequestId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.BIGINT) },
			{ "createDate", new Integer(Types.TIMESTAMP) },
			{ "groupId", new Integer(Types.BIGINT) },
			{ "comments", new Integer(Types.VARCHAR) },
			{ "replyComments", new Integer(Types.VARCHAR) },
			{ "replyDate", new Integer(Types.TIMESTAMP) },
			{ "replierUserId", new Integer(Types.BIGINT) },
			{ "statusId", new Integer(Types.INTEGER) }
		};
	public static final String TABLE_SQL_CREATE = "create table MembershipRequest (membershipRequestId LONG not null primary key,companyId LONG,userId LONG,createDate DATE null,groupId LONG,comments STRING null,replyComments STRING null,replyDate DATE null,replierUserId LONG,statusId INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table MembershipRequest";
	public static final String ORDER_BY_JPQL = " ORDER BY membershipRequest.createDate DESC";
	public static final String ORDER_BY_SQL = " ORDER BY MembershipRequest.createDate DESC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.MembershipRequest"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.MembershipRequest"),
			true);

	public static MembershipRequest toModel(MembershipRequestSoap soapModel) {
		MembershipRequest model = new MembershipRequestImpl();

		model.setMembershipRequestId(soapModel.getMembershipRequestId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setCreateDate(soapModel.getCreateDate());
		model.setGroupId(soapModel.getGroupId());
		model.setComments(soapModel.getComments());
		model.setReplyComments(soapModel.getReplyComments());
		model.setReplyDate(soapModel.getReplyDate());
		model.setReplierUserId(soapModel.getReplierUserId());
		model.setStatusId(soapModel.getStatusId());

		return model;
	}

	public static List<MembershipRequest> toModels(
		MembershipRequestSoap[] soapModels) {
		List<MembershipRequest> models = new ArrayList<MembershipRequest>(soapModels.length);

		for (MembershipRequestSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.MembershipRequest"));

	public MembershipRequestModelImpl() {
	}

	public long getPrimaryKey() {
		return _membershipRequestId;
	}

	public void setPrimaryKey(long pk) {
		setMembershipRequestId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_membershipRequestId);
	}

	public long getMembershipRequestId() {
		return _membershipRequestId;
	}

	public void setMembershipRequestId(long membershipRequestId) {
		_membershipRequestId = membershipRequestId;
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

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public String getComments() {
		return GetterUtil.getString(_comments);
	}

	public void setComments(String comments) {
		_comments = comments;
	}

	public String getReplyComments() {
		return GetterUtil.getString(_replyComments);
	}

	public void setReplyComments(String replyComments) {
		_replyComments = replyComments;
	}

	public Date getReplyDate() {
		return _replyDate;
	}

	public void setReplyDate(Date replyDate) {
		_replyDate = replyDate;
	}

	public long getReplierUserId() {
		return _replierUserId;
	}

	public void setReplierUserId(long replierUserId) {
		_replierUserId = replierUserId;
	}

	public String getReplierUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getReplierUserId(), "uuid",
			_replierUserUuid);
	}

	public void setReplierUserUuid(String replierUserUuid) {
		_replierUserUuid = replierUserUuid;
	}

	public int getStatusId() {
		return _statusId;
	}

	public void setStatusId(int statusId) {
		_statusId = statusId;
	}

	public MembershipRequest toEscapedModel() {
		if (isEscapedModel()) {
			return (MembershipRequest)this;
		}
		else {
			MembershipRequest model = new MembershipRequestImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setMembershipRequestId(getMembershipRequestId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setCreateDate(getCreateDate());
			model.setGroupId(getGroupId());
			model.setComments(HtmlUtil.escape(getComments()));
			model.setReplyComments(HtmlUtil.escape(getReplyComments()));
			model.setReplyDate(getReplyDate());
			model.setReplierUserId(getReplierUserId());
			model.setStatusId(getStatusId());

			model = (MembershipRequest)Proxy.newProxyInstance(MembershipRequest.class.getClassLoader(),
					new Class[] { MembershipRequest.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					MembershipRequest.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		MembershipRequestImpl clone = new MembershipRequestImpl();

		clone.setMembershipRequestId(getMembershipRequestId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setCreateDate(getCreateDate());
		clone.setGroupId(getGroupId());
		clone.setComments(getComments());
		clone.setReplyComments(getReplyComments());
		clone.setReplyDate(getReplyDate());
		clone.setReplierUserId(getReplierUserId());
		clone.setStatusId(getStatusId());

		return clone;
	}

	public int compareTo(MembershipRequest membershipRequest) {
		int value = 0;

		value = DateUtil.compareTo(getCreateDate(),
				membershipRequest.getCreateDate());

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

		MembershipRequest membershipRequest = null;

		try {
			membershipRequest = (MembershipRequest)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = membershipRequest.getPrimaryKey();

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
		StringBundler sb = new StringBundler(21);

		sb.append("{membershipRequestId=");
		sb.append(getMembershipRequestId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", comments=");
		sb.append(getComments());
		sb.append(", replyComments=");
		sb.append(getReplyComments());
		sb.append(", replyDate=");
		sb.append(getReplyDate());
		sb.append(", replierUserId=");
		sb.append(getReplierUserId());
		sb.append(", statusId=");
		sb.append(getStatusId());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(34);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.MembershipRequest");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>membershipRequestId</column-name><column-value><![CDATA[");
		sb.append(getMembershipRequestId());
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
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>comments</column-name><column-value><![CDATA[");
		sb.append(getComments());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>replyComments</column-name><column-value><![CDATA[");
		sb.append(getReplyComments());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>replyDate</column-name><column-value><![CDATA[");
		sb.append(getReplyDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>replierUserId</column-name><column-value><![CDATA[");
		sb.append(getReplierUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>statusId</column-name><column-value><![CDATA[");
		sb.append(getStatusId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _membershipRequestId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private Date _createDate;
	private long _groupId;
	private String _comments;
	private String _replyComments;
	private Date _replyDate;
	private long _replierUserId;
	private String _replierUserUuid;
	private int _statusId;
	private transient ExpandoBridge _expandoBridge;
}