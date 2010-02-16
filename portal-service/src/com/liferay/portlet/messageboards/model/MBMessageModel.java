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

package com.liferay.portlet.messageboards.model;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * <a href="MBMessageModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the MBMessage table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBMessage
 * @see       com.liferay.portlet.messageboards.model.impl.MBMessageImpl
 * @see       com.liferay.portlet.messageboards.model.impl.MBMessageModelImpl
 * @generated
 */
public interface MBMessageModel extends BaseModel<MBMessage> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public String getUuid();

	public void setUuid(String uuid);

	public long getMessageId();

	public void setMessageId(long messageId);

	public long getGroupId();

	public void setGroupId(long groupId);

	public long getCompanyId();

	public void setCompanyId(long companyId);

	public long getUserId();

	public void setUserId(long userId);

	public String getUserUuid() throws SystemException;

	public void setUserUuid(String userUuid);

	public String getUserName();

	public void setUserName(String userName);

	public Date getCreateDate();

	public void setCreateDate(Date createDate);

	public Date getModifiedDate();

	public void setModifiedDate(Date modifiedDate);

	public String getClassName();

	public long getClassNameId();

	public void setClassNameId(long classNameId);

	public long getClassPK();

	public void setClassPK(long classPK);

	public long getCategoryId();

	public void setCategoryId(long categoryId);

	public long getThreadId();

	public void setThreadId(long threadId);

	public long getParentMessageId();

	public void setParentMessageId(long parentMessageId);

	public String getSubject();

	public void setSubject(String subject);

	public String getBody();

	public void setBody(String body);

	public boolean getAttachments();

	public boolean isAttachments();

	public void setAttachments(boolean attachments);

	public boolean getAnonymous();

	public boolean isAnonymous();

	public void setAnonymous(boolean anonymous);

	public double getPriority();

	public void setPriority(double priority);

	public int getStatus();

	public void setStatus(int status);

	public long getStatusByUserId();

	public void setStatusByUserId(long statusByUserId);

	public String getStatusByUserUuid() throws SystemException;

	public void setStatusByUserUuid(String statusByUserUuid);

	public String getStatusByUserName();

	public void setStatusByUserName(String statusByUserName);

	public Date getStatusDate();

	public void setStatusDate(Date statusDate);

	public MBMessage toEscapedModel();

	public boolean isNew();

	public boolean setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public void setEscapedModel(boolean escapedModel);

	public Serializable getPrimaryKeyObj();

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public Object clone();

	public int compareTo(MBMessage mbMessage);

	public int hashCode();

	public String toString();

	public String toXmlString();
}