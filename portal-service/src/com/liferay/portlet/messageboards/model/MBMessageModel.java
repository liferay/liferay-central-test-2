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

package com.liferay.portlet.messageboards.model;

import com.liferay.portal.kernel.annotation.AutoEscape;
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

	@AutoEscape
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

	@AutoEscape
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

	@AutoEscape
	public String getSubject();

	public void setSubject(String subject);

	@AutoEscape
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

	public boolean getAllowPingbacks();

	public boolean isAllowPingbacks();

	public void setAllowPingbacks(boolean allowPingbacks);

	public int getStatus();

	public void setStatus(int status);

	public long getStatusByUserId();

	public void setStatusByUserId(long statusByUserId);

	public String getStatusByUserUuid() throws SystemException;

	public void setStatusByUserUuid(String statusByUserUuid);

	@AutoEscape
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