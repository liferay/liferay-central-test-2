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

package com.liferay.portal.model;

import com.liferay.portal.kernel.annotation.AutoEscape;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * <a href="UserTrackerPathModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the UserTrackerPath table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserTrackerPath
 * @see       com.liferay.portal.model.impl.UserTrackerPathImpl
 * @see       com.liferay.portal.model.impl.UserTrackerPathModelImpl
 * @generated
 */
public interface UserTrackerPathModel extends BaseModel<UserTrackerPath> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getUserTrackerPathId();

	public void setUserTrackerPathId(long userTrackerPathId);

	public long getUserTrackerId();

	public void setUserTrackerId(long userTrackerId);

	@AutoEscape
	public String getPath();

	public void setPath(String path);

	public Date getPathDate();

	public void setPathDate(Date pathDate);

	public UserTrackerPath toEscapedModel();

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

	public int compareTo(UserTrackerPath userTrackerPath);

	public int hashCode();

	public String toString();

	public String toXmlString();
}