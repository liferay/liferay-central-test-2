/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.model.impl;

import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PropsUtil;

import com.liferay.portlet.messageboards.service.persistence.MBMessagePK;

import com.liferay.util.DateUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.util.Date;

/**
 * <a href="MBMessageModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBMessageModelImpl extends BaseModelImpl {
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.messageboards.model.MBMessage"),
			XSS_ALLOW);
	public static boolean XSS_ALLOW_TOPICID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.messageboards.model.MBMessage.topicId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_MESSAGEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.messageboards.model.MBMessage.messageId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COMPANYID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.messageboards.model.MBMessage.companyId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.messageboards.model.MBMessage.userId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERNAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.messageboards.model.MBMessage.userName"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CATEGORYID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.messageboards.model.MBMessage.categoryId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_THREADID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.messageboards.model.MBMessage.threadId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_PARENTMESSAGEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.messageboards.model.MBMessage.parentMessageId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_SUBJECT = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.messageboards.model.MBMessage.subject"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_BODY = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.messageboards.model.MBMessage.body"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.messageboards.model.MBMessageModel"));

	public MBMessageModelImpl() {
	}

	public MBMessagePK getPrimaryKey() {
		return new MBMessagePK(_topicId, _messageId);
	}

	public void setPrimaryKey(MBMessagePK pk) {
		setTopicId(pk.topicId);
		setMessageId(pk.messageId);
	}

	public String getTopicId() {
		return GetterUtil.getString(_topicId);
	}

	public void setTopicId(String topicId) {
		if (((topicId == null) && (_topicId != null)) ||
				((topicId != null) && (_topicId == null)) ||
				((topicId != null) && (_topicId != null) &&
				!topicId.equals(_topicId))) {
			if (!XSS_ALLOW_TOPICID) {
				topicId = XSSUtil.strip(topicId);
			}

			_topicId = topicId;
		}
	}

	public String getMessageId() {
		return GetterUtil.getString(_messageId);
	}

	public void setMessageId(String messageId) {
		if (((messageId == null) && (_messageId != null)) ||
				((messageId != null) && (_messageId == null)) ||
				((messageId != null) && (_messageId != null) &&
				!messageId.equals(_messageId))) {
			if (!XSS_ALLOW_MESSAGEID) {
				messageId = XSSUtil.strip(messageId);
			}

			_messageId = messageId;
		}
	}

	public String getCompanyId() {
		return GetterUtil.getString(_companyId);
	}

	public void setCompanyId(String companyId) {
		if (((companyId == null) && (_companyId != null)) ||
				((companyId != null) && (_companyId == null)) ||
				((companyId != null) && (_companyId != null) &&
				!companyId.equals(_companyId))) {
			if (!XSS_ALLOW_COMPANYID) {
				companyId = XSSUtil.strip(companyId);
			}

			_companyId = companyId;
		}
	}

	public String getUserId() {
		return GetterUtil.getString(_userId);
	}

	public void setUserId(String userId) {
		if (((userId == null) && (_userId != null)) ||
				((userId != null) && (_userId == null)) ||
				((userId != null) && (_userId != null) &&
				!userId.equals(_userId))) {
			if (!XSS_ALLOW_USERID) {
				userId = XSSUtil.strip(userId);
			}

			_userId = userId;
		}
	}

	public String getUserName() {
		return GetterUtil.getString(_userName);
	}

	public void setUserName(String userName) {
		if (((userName == null) && (_userName != null)) ||
				((userName != null) && (_userName == null)) ||
				((userName != null) && (_userName != null) &&
				!userName.equals(_userName))) {
			if (!XSS_ALLOW_USERNAME) {
				userName = XSSUtil.strip(userName);
			}

			_userName = userName;
		}
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		if (((createDate == null) && (_createDate != null)) ||
				((createDate != null) && (_createDate == null)) ||
				((createDate != null) && (_createDate != null) &&
				!createDate.equals(_createDate))) {
			_createDate = createDate;
		}
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		if (((modifiedDate == null) && (_modifiedDate != null)) ||
				((modifiedDate != null) && (_modifiedDate == null)) ||
				((modifiedDate != null) && (_modifiedDate != null) &&
				!modifiedDate.equals(_modifiedDate))) {
			_modifiedDate = modifiedDate;
		}
	}

	public String getCategoryId() {
		return GetterUtil.getString(_categoryId);
	}

	public void setCategoryId(String categoryId) {
		if (((categoryId == null) && (_categoryId != null)) ||
				((categoryId != null) && (_categoryId == null)) ||
				((categoryId != null) && (_categoryId != null) &&
				!categoryId.equals(_categoryId))) {
			if (!XSS_ALLOW_CATEGORYID) {
				categoryId = XSSUtil.strip(categoryId);
			}

			_categoryId = categoryId;
		}
	}

	public String getThreadId() {
		return GetterUtil.getString(_threadId);
	}

	public void setThreadId(String threadId) {
		if (((threadId == null) && (_threadId != null)) ||
				((threadId != null) && (_threadId == null)) ||
				((threadId != null) && (_threadId != null) &&
				!threadId.equals(_threadId))) {
			if (!XSS_ALLOW_THREADID) {
				threadId = XSSUtil.strip(threadId);
			}

			_threadId = threadId;
		}
	}

	public String getParentMessageId() {
		return GetterUtil.getString(_parentMessageId);
	}

	public void setParentMessageId(String parentMessageId) {
		if (((parentMessageId == null) && (_parentMessageId != null)) ||
				((parentMessageId != null) && (_parentMessageId == null)) ||
				((parentMessageId != null) && (_parentMessageId != null) &&
				!parentMessageId.equals(_parentMessageId))) {
			if (!XSS_ALLOW_PARENTMESSAGEID) {
				parentMessageId = XSSUtil.strip(parentMessageId);
			}

			_parentMessageId = parentMessageId;
		}
	}

	public String getSubject() {
		return GetterUtil.getString(_subject);
	}

	public void setSubject(String subject) {
		if (((subject == null) && (_subject != null)) ||
				((subject != null) && (_subject == null)) ||
				((subject != null) && (_subject != null) &&
				!subject.equals(_subject))) {
			if (!XSS_ALLOW_SUBJECT) {
				subject = XSSUtil.strip(subject);
			}

			_subject = subject;
		}
	}

	public String getBody() {
		return GetterUtil.getString(_body);
	}

	public void setBody(String body) {
		if (((body == null) && (_body != null)) ||
				((body != null) && (_body == null)) ||
				((body != null) && (_body != null) && !body.equals(_body))) {
			if (!XSS_ALLOW_BODY) {
				body = XSSUtil.strip(body);
			}

			_body = body;
		}
	}

	public boolean getAttachments() {
		return _attachments;
	}

	public boolean isAttachments() {
		return _attachments;
	}

	public void setAttachments(boolean attachments) {
		if (attachments != _attachments) {
			_attachments = attachments;
		}
	}

	public boolean getAnonymous() {
		return _anonymous;
	}

	public boolean isAnonymous() {
		return _anonymous;
	}

	public void setAnonymous(boolean anonymous) {
		if (anonymous != _anonymous) {
			_anonymous = anonymous;
		}
	}

	public Object clone() {
		MBMessageImpl clone = new MBMessageImpl();
		clone.setTopicId(getTopicId());
		clone.setMessageId(getMessageId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setCategoryId(getCategoryId());
		clone.setThreadId(getThreadId());
		clone.setParentMessageId(getParentMessageId());
		clone.setSubject(getSubject());
		clone.setBody(getBody());
		clone.setAttachments(getAttachments());
		clone.setAnonymous(getAnonymous());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		MBMessageImpl mbMessage = (MBMessageImpl)obj;
		int value = 0;
		value = DateUtil.compareTo(getCreateDate(), mbMessage.getCreateDate());

		if (value != 0) {
			return value;
		}

		value = getMessageId().compareTo(mbMessage.getMessageId());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		MBMessageImpl mbMessage = null;

		try {
			mbMessage = (MBMessageImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		MBMessagePK pk = mbMessage.getPrimaryKey();

		if (getPrimaryKey().equals(pk)) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return getPrimaryKey().hashCode();
	}

	private String _topicId;
	private String _messageId;
	private String _companyId;
	private String _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _categoryId;
	private String _threadId;
	private String _parentMessageId;
	private String _subject;
	private String _body;
	private boolean _attachments;
	private boolean _anonymous;
}