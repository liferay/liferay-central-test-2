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

package com.liferay.portlet.asset.model;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * <a href="AssetEntryModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the AssetEntry table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetEntry
 * @see       com.liferay.portlet.asset.model.impl.AssetEntryImpl
 * @see       com.liferay.portlet.asset.model.impl.AssetEntryModelImpl
 * @generated
 */
public interface AssetEntryModel extends BaseModel<AssetEntry> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getEntryId();

	public void setEntryId(long entryId);

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

	public boolean getVisible();

	public boolean isVisible();

	public void setVisible(boolean visible);

	public Date getStartDate();

	public void setStartDate(Date startDate);

	public Date getEndDate();

	public void setEndDate(Date endDate);

	public Date getPublishDate();

	public void setPublishDate(Date publishDate);

	public Date getExpirationDate();

	public void setExpirationDate(Date expirationDate);

	public String getMimeType();

	public void setMimeType(String mimeType);

	public String getTitle();

	public void setTitle(String title);

	public String getDescription();

	public void setDescription(String description);

	public String getSummary();

	public void setSummary(String summary);

	public String getUrl();

	public void setUrl(String url);

	public int getHeight();

	public void setHeight(int height);

	public int getWidth();

	public void setWidth(int width);

	public double getPriority();

	public void setPriority(double priority);

	public int getViewCount();

	public void setViewCount(int viewCount);

	public AssetEntry toEscapedModel();

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

	public int compareTo(AssetEntry assetEntry);

	public int hashCode();

	public String toString();

	public String toXmlString();
}