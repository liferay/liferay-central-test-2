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
 * <a href="OrganizationModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the Organization_ table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Organization
 * @see       com.liferay.portal.model.impl.OrganizationImpl
 * @see       com.liferay.portal.model.impl.OrganizationModelImpl
 * @generated
 */
public interface OrganizationModel extends BaseModel<Organization> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getOrganizationId();

	public void setOrganizationId(long organizationId);

	public long getCompanyId();

	public void setCompanyId(long companyId);

	public long getParentOrganizationId();

	public void setParentOrganizationId(long parentOrganizationId);

	public long getLeftOrganizationId();

	public void setLeftOrganizationId(long leftOrganizationId);

	public long getRightOrganizationId();

	public void setRightOrganizationId(long rightOrganizationId);

	@AutoEscape
	public String getName();

	public void setName(String name);

	public String getType();

	public void setType(String type);

	public boolean getRecursable();

	public boolean isRecursable();

	public void setRecursable(boolean recursable);

	public long getRegionId();

	public void setRegionId(long regionId);

	public long getCountryId();

	public void setCountryId(long countryId);

	public int getStatusId();

	public void setStatusId(int statusId);

	@AutoEscape
	public String getComments();

	public void setComments(String comments);

	public Organization toEscapedModel();

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

	public int compareTo(Organization organization);

	public int hashCode();

	public String toString();

	public String toXmlString();
}