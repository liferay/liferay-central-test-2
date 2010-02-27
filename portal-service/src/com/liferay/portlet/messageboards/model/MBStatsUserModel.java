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
 * <a href="MBStatsUserModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the MBStatsUser table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBStatsUser
 * @see       com.liferay.portlet.messageboards.model.impl.MBStatsUserImpl
 * @see       com.liferay.portlet.messageboards.model.impl.MBStatsUserModelImpl
 * @generated
 */
public interface MBStatsUserModel extends BaseModel<MBStatsUser> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getStatsUserId();

	public void setStatsUserId(long statsUserId);

	public String getStatsUserUuid() throws SystemException;

	public void setStatsUserUuid(String statsUserUuid);

	public long getGroupId();

	public void setGroupId(long groupId);

	public long getUserId();

	public void setUserId(long userId);

	public String getUserUuid() throws SystemException;

	public void setUserUuid(String userUuid);

	public int getMessageCount();

	public void setMessageCount(int messageCount);

	public Date getLastPostDate();

	public void setLastPostDate(Date lastPostDate);

	public MBStatsUser toEscapedModel();

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

	public int compareTo(MBStatsUser mbStatsUser);

	public int hashCode();

	public String toString();

	public String toXmlString();
}