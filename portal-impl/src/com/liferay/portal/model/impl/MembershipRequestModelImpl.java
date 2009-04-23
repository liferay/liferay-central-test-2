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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.MembershipRequest;
import com.liferay.portal.model.MembershipRequestSoap;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;

import java.io.Serializable;

import java.lang.StringBuilder;
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
 * This class is a model that represents the <code>MembershipRequest</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.model.MembershipRequest
 * @see com.liferay.portal.model.MembershipRequestModel
 * @see com.liferay.portal.model.impl.MembershipRequestImpl
 *
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
			_expandoBridge = new ExpandoBridgeImpl(MembershipRequest.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
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

	public String toHtmlString() {
		StringBuilder sb = new StringBuilder();

		sb.append("<table class=\"lfr-table\">\n");

		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>membershipRequestId</b></td><td>" +
			getMembershipRequestId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>companyId</b></td><td>" +
			getCompanyId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>userId</b></td><td>" +
			getUserId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>createDate</b></td><td>" +
			getCreateDate() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>groupId</b></td><td>" +
			getGroupId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>comments</b></td><td>" +
			getComments() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>replyComments</b></td><td>" +
			getReplyComments() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>replyDate</b></td><td>" +
			getReplyDate() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>replierUserId</b></td><td>" +
			getReplierUserId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>statusId</b></td><td>" +
			getStatusId() + "</td></tr>\n");

		sb.append("</table>");

		return sb.toString();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("com.liferay.portal.model.MembershipRequest (");

		sb.append("membershipRequestId: " + getMembershipRequestId() + ", ");
		sb.append("companyId: " + getCompanyId() + ", ");
		sb.append("userId: " + getUserId() + ", ");
		sb.append("createDate: " + getCreateDate() + ", ");
		sb.append("groupId: " + getGroupId() + ", ");
		sb.append("comments: " + getComments() + ", ");
		sb.append("replyComments: " + getReplyComments() + ", ");
		sb.append("replyDate: " + getReplyDate() + ", ");
		sb.append("replierUserId: " + getReplierUserId() + ", ");
		sb.append("statusId: " + getStatusId() + ", ");

		sb.append(")");

		return sb.toString();
	}

	private long _membershipRequestId;
	private long _companyId;
	private long _userId;
	private Date _createDate;
	private long _groupId;
	private String _comments;
	private String _replyComments;
	private Date _replyDate;
	private long _replierUserId;
	private int _statusId;
	private transient ExpandoBridge _expandoBridge;
}