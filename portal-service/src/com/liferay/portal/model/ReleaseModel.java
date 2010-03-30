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
 * <a href="ReleaseModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the Release_ table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Release
 * @see       com.liferay.portal.model.impl.ReleaseImpl
 * @see       com.liferay.portal.model.impl.ReleaseModelImpl
 * @generated
 */
public interface ReleaseModel extends BaseModel<Release> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getReleaseId();

	public void setReleaseId(long releaseId);

	public Date getCreateDate();

	public void setCreateDate(Date createDate);

	public Date getModifiedDate();

	public void setModifiedDate(Date modifiedDate);

	@AutoEscape
	public String getServletContextName();

	public void setServletContextName(String servletContextName);

	public int getBuildNumber();

	public void setBuildNumber(int buildNumber);

	public Date getBuildDate();

	public void setBuildDate(Date buildDate);

	public boolean getVerified();

	public boolean isVerified();

	public void setVerified(boolean verified);

	@AutoEscape
	public String getTestString();

	public void setTestString(String testString);

	public Release toEscapedModel();

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

	public int compareTo(Release release);

	public int hashCode();

	public String toString();

	public String toXmlString();
}