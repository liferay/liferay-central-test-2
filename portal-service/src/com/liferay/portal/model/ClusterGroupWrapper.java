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

/**
 * <p>
 * This class is a wrapper for {@link ClusterGroup}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ClusterGroup
 * @generated
 */
public class ClusterGroupWrapper implements ClusterGroup {
	public ClusterGroupWrapper(ClusterGroup clusterGroup) {
		_clusterGroup = clusterGroup;
	}

	public long getPrimaryKey() {
		return _clusterGroup.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_clusterGroup.setPrimaryKey(pk);
	}

	public long getClusterGroupId() {
		return _clusterGroup.getClusterGroupId();
	}

	public void setClusterGroupId(long clusterGroupId) {
		_clusterGroup.setClusterGroupId(clusterGroupId);
	}

	public java.lang.String getName() {
		return _clusterGroup.getName();
	}

	public void setName(java.lang.String name) {
		_clusterGroup.setName(name);
	}

	public java.lang.String getClusterNodeIds() {
		return _clusterGroup.getClusterNodeIds();
	}

	public void setClusterNodeIds(java.lang.String clusterNodeIds) {
		_clusterGroup.setClusterNodeIds(clusterNodeIds);
	}

	public boolean getWholeCluster() {
		return _clusterGroup.getWholeCluster();
	}

	public boolean isWholeCluster() {
		return _clusterGroup.isWholeCluster();
	}

	public void setWholeCluster(boolean wholeCluster) {
		_clusterGroup.setWholeCluster(wholeCluster);
	}

	public com.liferay.portal.model.ClusterGroup toEscapedModel() {
		return _clusterGroup.toEscapedModel();
	}

	public boolean isNew() {
		return _clusterGroup.isNew();
	}

	public void setNew(boolean n) {
		_clusterGroup.setNew(n);
	}

	public boolean isCachedModel() {
		return _clusterGroup.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_clusterGroup.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _clusterGroup.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_clusterGroup.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _clusterGroup.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _clusterGroup.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_clusterGroup.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _clusterGroup.clone();
	}

	public int compareTo(com.liferay.portal.model.ClusterGroup clusterGroup) {
		return _clusterGroup.compareTo(clusterGroup);
	}

	public int hashCode() {
		return _clusterGroup.hashCode();
	}

	public java.lang.String toString() {
		return _clusterGroup.toString();
	}

	public java.lang.String toXmlString() {
		return _clusterGroup.toXmlString();
	}

	public java.lang.String[] getClusterNodeIdsArray() {
		return _clusterGroup.getClusterNodeIdsArray();
	}

	public ClusterGroup getWrappedClusterGroup() {
		return _clusterGroup;
	}

	private ClusterGroup _clusterGroup;
}