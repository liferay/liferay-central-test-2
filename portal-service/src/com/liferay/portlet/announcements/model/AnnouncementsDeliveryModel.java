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

package com.liferay.portlet.announcements.model;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * <a href="AnnouncementsDeliveryModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the AnnouncementsDelivery table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsDelivery
 * @see       com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryImpl
 * @see       com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryModelImpl
 * @generated
 */
public interface AnnouncementsDeliveryModel extends BaseModel<AnnouncementsDelivery> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getDeliveryId();

	public void setDeliveryId(long deliveryId);

	public long getCompanyId();

	public void setCompanyId(long companyId);

	public long getUserId();

	public void setUserId(long userId);

	public String getUserUuid() throws SystemException;

	public void setUserUuid(String userUuid);

	public String getType();

	public void setType(String type);

	public boolean getEmail();

	public boolean isEmail();

	public void setEmail(boolean email);

	public boolean getSms();

	public boolean isSms();

	public void setSms(boolean sms);

	public boolean getWebsite();

	public boolean isWebsite();

	public void setWebsite(boolean website);

	public AnnouncementsDelivery toEscapedModel();

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

	public int compareTo(AnnouncementsDelivery announcementsDelivery);

	public int hashCode();

	public String toString();

	public String toXmlString();
}