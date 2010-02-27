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

import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * <a href="OrgLaborModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the OrgLabor table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       OrgLabor
 * @see       com.liferay.portal.model.impl.OrgLaborImpl
 * @see       com.liferay.portal.model.impl.OrgLaborModelImpl
 * @generated
 */
public interface OrgLaborModel extends BaseModel<OrgLabor> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getOrgLaborId();

	public void setOrgLaborId(long orgLaborId);

	public long getOrganizationId();

	public void setOrganizationId(long organizationId);

	public int getTypeId();

	public void setTypeId(int typeId);

	public int getSunOpen();

	public void setSunOpen(int sunOpen);

	public int getSunClose();

	public void setSunClose(int sunClose);

	public int getMonOpen();

	public void setMonOpen(int monOpen);

	public int getMonClose();

	public void setMonClose(int monClose);

	public int getTueOpen();

	public void setTueOpen(int tueOpen);

	public int getTueClose();

	public void setTueClose(int tueClose);

	public int getWedOpen();

	public void setWedOpen(int wedOpen);

	public int getWedClose();

	public void setWedClose(int wedClose);

	public int getThuOpen();

	public void setThuOpen(int thuOpen);

	public int getThuClose();

	public void setThuClose(int thuClose);

	public int getFriOpen();

	public void setFriOpen(int friOpen);

	public int getFriClose();

	public void setFriClose(int friClose);

	public int getSatOpen();

	public void setSatOpen(int satOpen);

	public int getSatClose();

	public void setSatClose(int satClose);

	public OrgLabor toEscapedModel();

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

	public int compareTo(OrgLabor orgLabor);

	public int hashCode();

	public String toString();

	public String toXmlString();
}