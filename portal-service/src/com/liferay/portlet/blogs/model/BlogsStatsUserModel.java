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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * <a href="BlogsStatsUserModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the BlogsStatsUser table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BlogsStatsUser
 * @see       com.liferay.portlet.blogs.model.impl.BlogsStatsUserImpl
 * @see       com.liferay.portlet.blogs.model.impl.BlogsStatsUserModelImpl
 * @generated
 */
public interface BlogsStatsUserModel extends BaseModel<BlogsStatsUser> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getStatsUserId();

	public void setStatsUserId(long statsUserId);

	public String getStatsUserUuid() throws SystemException;

	public void setStatsUserUuid(String statsUserUuid);

	public long getGroupId();

	public void setGroupId(long groupId);

	public long getCompanyId();

	public void setCompanyId(long companyId);

	public long getUserId();

	public void setUserId(long userId);

	public String getUserUuid() throws SystemException;

	public void setUserUuid(String userUuid);

	public int getEntryCount();

	public void setEntryCount(int entryCount);

	public Date getLastPostDate();

	public void setLastPostDate(Date lastPostDate);

	public int getRatingsTotalEntries();

	public void setRatingsTotalEntries(int ratingsTotalEntries);

	public double getRatingsTotalScore();

	public void setRatingsTotalScore(double ratingsTotalScore);

	public double getRatingsAverageScore();

	public void setRatingsAverageScore(double ratingsAverageScore);

	public BlogsStatsUser toEscapedModel();

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

	public int compareTo(BlogsStatsUser blogsStatsUser);

	public int hashCode();

	public String toString();

	public String toXmlString();
}