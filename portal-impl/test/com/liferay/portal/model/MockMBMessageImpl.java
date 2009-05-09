/*
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

package com.liferay.portal.model;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;

import java.util.Date;

/**
 * <a href="MockMBMessageImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class MockMBMessageImpl
	extends MockBaseModelImpl<MBMessage> implements MBMessage {

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return _originalUuid;
	}

	public void setOriginalUuid(String originalUuid) {
		_originalUuid = originalUuid;
	}

	public long getMessageId() {
		return _messageId;
	}

	public void setMessageId(long messageId) {
		_messageId = messageId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

	public void setOriginalGroupId(long originalGroupId) {
		_originalGroupId = originalGroupId;
	}

	public boolean isSetOriginalGroupId() {
		return _setOriginalGroupId;
	}

	public void setSetOriginalGroupId(boolean setOriginalGroupId) {
		_setOriginalGroupId = setOriginalGroupId;
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
		return _userName;
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

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public long getCategoryId() {
		return _categoryId;
	}

	public void setCategoryId(long categoryId) {
		_categoryId = categoryId;
	}

	public long getThreadId() {
		return _threadId;
	}

	public void setThreadId(long threadId) {
		_threadId = threadId;
	}

	public long getParentMessageId() {
		return _parentMessageId;
	}

	public void setParentMessageId(long parentMessageId) {
		_parentMessageId = parentMessageId;
	}

	public String getSubject() {
		return _subject;
	}

	public void setSubject(String subject) {
		_subject = subject;
	}

	public String getBody() {
		return _body;
	}

	public void setBody(String body) {
		_body = body;
	}

	public boolean isAttachments() {
		return _attachments;
	}

	public void setAttachments(boolean attachments) {
		_attachments = attachments;
	}

	public boolean isAnonymous() {
		return _anonymous;
	}

	public void setAnonymous(boolean anonymous) {
		_anonymous = anonymous;
	}

	public double getPriority() {
		return _priority;
	}

	public void setPriority(double priority) {
		_priority = priority;
	}

	public String getUserUuid() throws SystemException {
		return null;
	}

	public void setUserUuid(String userUuid) {

	}

	public MBCategory getCategory() {
		return null;
	}

	public MBThread getThread() throws PortalException, SystemException {
		return null;
	}

	public boolean isRoot() {
		return false;
	}

	public boolean isReply() {
		return false;
	}

	public boolean isDiscussion() {
		return false;
	}

	public String getBody(boolean translate) {
		return null;
	}

	public String getThreadAttachmentsDir() {
		return null;
	}

	public String getAttachmentsDir() {
		return null;
	}

	public void setAttachmentsDir(String attachmentsDir) {

	}

	public String[] getAttachmentsFiles()
		throws PortalException, SystemException {
		return new String[0];
	}

	public String[] getTagsEntries() throws SystemException {
		return new String[0];
	}

	public long getPrimaryKey() {
		return 0;
	}

	public void setPrimaryKey(long pk) {

	}

	public String getClassName() {
		return null;
	}

	public boolean getAttachments() {
		return isAttachments();  
	}

	public boolean getAnonymous() {
		return isAnonymous();
	}

	public MBMessage toEscapedModel() {
		return null;
	}

	private String _uuid;
	private String _originalUuid;
	private long _messageId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private long _classPK;
	private long _categoryId;
	private long _threadId;
	private long _parentMessageId;
	private String _subject;
	private String _body;
	private boolean _attachments;
	private boolean _anonymous;
	private double _priority;

}
