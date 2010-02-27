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

import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * <a href="AssetTagStatsModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the AssetTagStats table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetTagStats
 * @see       com.liferay.portlet.asset.model.impl.AssetTagStatsImpl
 * @see       com.liferay.portlet.asset.model.impl.AssetTagStatsModelImpl
 * @generated
 */
public interface AssetTagStatsModel extends BaseModel<AssetTagStats> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getTagStatsId();

	public void setTagStatsId(long tagStatsId);

	public long getTagId();

	public void setTagId(long tagId);

	public String getClassName();

	public long getClassNameId();

	public void setClassNameId(long classNameId);

	public int getAssetCount();

	public void setAssetCount(int assetCount);

	public AssetTagStats toEscapedModel();

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

	public int compareTo(AssetTagStats assetTagStats);

	public int hashCode();

	public String toString();

	public String toXmlString();
}