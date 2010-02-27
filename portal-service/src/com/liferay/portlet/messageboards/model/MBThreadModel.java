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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * <a href="MBThreadModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the MBThread table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBThread
 * @see       com.liferay.portlet.messageboards.model.impl.MBThreadImpl
 * @see       com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl
 * @generated
 */
public interface MBThreadModel extends BaseModel<MBThread> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getThreadId();

	public void setThreadId(long threadId);

	public long getGroupId();

	public void setGroupId(long groupId);

	public long getCategoryId();

	public void setCategoryId(long categoryId);

	public long getRootMessageId();

	public void setRootMessageId(long rootMessageId);

	public int getMessageCount();

	public void setMessageCount(int messageCount);

	public int getViewCount();

	public void setViewCount(int viewCount);

	public long getLastPostByUserId();

	public void setLastPostByUserId(long lastPostByUserId);

	public String getLastPostByUserUuid() throws SystemException;

	public void setLastPostByUserUuid(String lastPostByUserUuid);

	public Date getLastPostDate();

	public void setLastPostDate(Date lastPostDate);

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

	public MBThread toEscapedModel();

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

	public int compareTo(MBThread mbThread);

	public int hashCode();

	public String toString();

	public String toXmlString();
}