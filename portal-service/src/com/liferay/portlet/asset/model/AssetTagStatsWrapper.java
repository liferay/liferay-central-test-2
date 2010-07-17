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

/**
 * <p>
 * This class is a wrapper for {@link AssetTagStats}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetTagStats
 * @generated
 */
public class AssetTagStatsWrapper implements AssetTagStats {
	public AssetTagStatsWrapper(AssetTagStats assetTagStats) {
		_assetTagStats = assetTagStats;
	}

	public long getPrimaryKey() {
		return _assetTagStats.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_assetTagStats.setPrimaryKey(pk);
	}

	public long getTagStatsId() {
		return _assetTagStats.getTagStatsId();
	}

	public void setTagStatsId(long tagStatsId) {
		_assetTagStats.setTagStatsId(tagStatsId);
	}

	public long getTagId() {
		return _assetTagStats.getTagId();
	}

	public void setTagId(long tagId) {
		_assetTagStats.setTagId(tagId);
	}

	public java.lang.String getClassName() {
		return _assetTagStats.getClassName();
	}

	public long getClassNameId() {
		return _assetTagStats.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_assetTagStats.setClassNameId(classNameId);
	}

	public int getAssetCount() {
		return _assetTagStats.getAssetCount();
	}

	public void setAssetCount(int assetCount) {
		_assetTagStats.setAssetCount(assetCount);
	}

	public com.liferay.portlet.asset.model.AssetTagStats toEscapedModel() {
		return _assetTagStats.toEscapedModel();
	}

	public boolean isNew() {
		return _assetTagStats.isNew();
	}

	public void setNew(boolean n) {
		_assetTagStats.setNew(n);
	}

	public boolean isCachedModel() {
		return _assetTagStats.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_assetTagStats.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _assetTagStats.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_assetTagStats.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _assetTagStats.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _assetTagStats.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_assetTagStats.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _assetTagStats.clone();
	}

	public int compareTo(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats) {
		return _assetTagStats.compareTo(assetTagStats);
	}

	public int hashCode() {
		return _assetTagStats.hashCode();
	}

	public java.lang.String toString() {
		return _assetTagStats.toString();
	}

	public java.lang.String toXmlString() {
		return _assetTagStats.toXmlString();
	}

	public AssetTagStats getWrappedAssetTagStats() {
		return _assetTagStats;
	}

	private AssetTagStats _assetTagStats;
}