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

/**
 * <a href="PortletPreferencesModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the PortletPreferences table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletPreferences
 * @see       com.liferay.portal.model.impl.PortletPreferencesImpl
 * @see       com.liferay.portal.model.impl.PortletPreferencesModelImpl
 * @generated
 */
public interface PortletPreferencesModel extends BaseModel<PortletPreferences> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getPortletPreferencesId();

	public void setPortletPreferencesId(long portletPreferencesId);

	public long getOwnerId();

	public void setOwnerId(long ownerId);

	public int getOwnerType();

	public void setOwnerType(int ownerType);

	public long getPlid();

	public void setPlid(long plid);

	@AutoEscape
	public String getPortletId();

	public void setPortletId(String portletId);

	@AutoEscape
	public String getPreferences();

	public void setPreferences(String preferences);

	public PortletPreferences toEscapedModel();

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

	public int compareTo(PortletPreferences portletPreferences);

	public int hashCode();

	public String toString();

	public String toXmlString();
}