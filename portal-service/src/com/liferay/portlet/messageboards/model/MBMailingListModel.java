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
 * <a href="MBMailingListModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the MBMailingList table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBMailingList
 * @see       com.liferay.portlet.messageboards.model.impl.MBMailingListImpl
 * @see       com.liferay.portlet.messageboards.model.impl.MBMailingListModelImpl
 * @generated
 */
public interface MBMailingListModel extends BaseModel<MBMailingList> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public String getUuid();

	public void setUuid(String uuid);

	public long getMailingListId();

	public void setMailingListId(long mailingListId);

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

	public long getCategoryId();

	public void setCategoryId(long categoryId);

	public String getEmailAddress();

	public void setEmailAddress(String emailAddress);

	public String getInProtocol();

	public void setInProtocol(String inProtocol);

	public String getInServerName();

	public void setInServerName(String inServerName);

	public int getInServerPort();

	public void setInServerPort(int inServerPort);

	public boolean getInUseSSL();

	public boolean isInUseSSL();

	public void setInUseSSL(boolean inUseSSL);

	public String getInUserName();

	public void setInUserName(String inUserName);

	public String getInPassword();

	public void setInPassword(String inPassword);

	public int getInReadInterval();

	public void setInReadInterval(int inReadInterval);

	public String getOutEmailAddress();

	public void setOutEmailAddress(String outEmailAddress);

	public boolean getOutCustom();

	public boolean isOutCustom();

	public void setOutCustom(boolean outCustom);

	public String getOutServerName();

	public void setOutServerName(String outServerName);

	public int getOutServerPort();

	public void setOutServerPort(int outServerPort);

	public boolean getOutUseSSL();

	public boolean isOutUseSSL();

	public void setOutUseSSL(boolean outUseSSL);

	public String getOutUserName();

	public void setOutUserName(String outUserName);

	public String getOutPassword();

	public void setOutPassword(String outPassword);

	public boolean getActive();

	public boolean isActive();

	public void setActive(boolean active);

	public MBMailingList toEscapedModel();

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

	public int compareTo(MBMailingList mbMailingList);

	public int hashCode();

	public String toString();

	public String toXmlString();
}