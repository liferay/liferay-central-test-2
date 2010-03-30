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
 * <a href="ResourceActionModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the ResourceAction table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourceAction
 * @see       com.liferay.portal.model.impl.ResourceActionImpl
 * @see       com.liferay.portal.model.impl.ResourceActionModelImpl
 * @generated
 */
public interface ResourceActionModel extends BaseModel<ResourceAction> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getResourceActionId();

	public void setResourceActionId(long resourceActionId);

	@AutoEscape
	public String getName();

	public void setName(String name);

	@AutoEscape
	public String getActionId();

	public void setActionId(String actionId);

	public long getBitwiseValue();

	public void setBitwiseValue(long bitwiseValue);

	public ResourceAction toEscapedModel();

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

	public int compareTo(ResourceAction resourceAction);

	public int hashCode();

	public String toString();

	public String toXmlString();
}