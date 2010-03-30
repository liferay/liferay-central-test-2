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

package com.liferay.portlet.blogs.model;

import com.liferay.portal.kernel.annotation.AutoEscape;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * <a href="BlogsEntryModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the BlogsEntry table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BlogsEntry
 * @see       com.liferay.portlet.blogs.model.impl.BlogsEntryImpl
 * @see       com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl
 * @generated
 */
public interface BlogsEntryModel extends BaseModel<BlogsEntry> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	@AutoEscape
	public String getUuid();

	public void setUuid(String uuid);

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

	@AutoEscape
	public String getUserName();

	public void setUserName(String userName);

	public Date getCreateDate();

	public void setCreateDate(Date createDate);

	public Date getModifiedDate();

	public void setModifiedDate(Date modifiedDate);

	@AutoEscape
	public String getTitle();

	public void setTitle(String title);

	@AutoEscape
	public String getUrlTitle();

	public void setUrlTitle(String urlTitle);

	@AutoEscape
	public String getContent();

	public void setContent(String content);

	public Date getDisplayDate();

	public void setDisplayDate(Date displayDate);

	public boolean getAllowPingbacks();

	public boolean isAllowPingbacks();

	public void setAllowPingbacks(boolean allowPingbacks);

	public boolean getAllowTrackbacks();

	public boolean isAllowTrackbacks();

	public void setAllowTrackbacks(boolean allowTrackbacks);

	@AutoEscape
	public String getTrackbacks();

	public void setTrackbacks(String trackbacks);

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

	public BlogsEntry toEscapedModel();

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

	public int compareTo(BlogsEntry blogsEntry);

	public int hashCode();

	public String toString();

	public String toXmlString();
}