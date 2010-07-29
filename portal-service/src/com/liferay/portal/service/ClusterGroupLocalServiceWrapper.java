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

package com.liferay.portal.service;

/**
 * <p>
 * This class is a wrapper for {@link ClusterGroupLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ClusterGroupLocalService
 * @generated
 */
public class ClusterGroupLocalServiceWrapper implements ClusterGroupLocalService {
	public ClusterGroupLocalServiceWrapper(
		ClusterGroupLocalService clusterGroupLocalService) {
		_clusterGroupLocalService = clusterGroupLocalService;
	}

	public com.liferay.portal.model.ClusterGroup addClusterGroup(
		com.liferay.portal.model.ClusterGroup clusterGroup)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _clusterGroupLocalService.addClusterGroup(clusterGroup);
	}

	public com.liferay.portal.model.ClusterGroup createClusterGroup(
		long clusterGroupId) {
		return _clusterGroupLocalService.createClusterGroup(clusterGroupId);
	}

	public void deleteClusterGroup(long clusterGroupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_clusterGroupLocalService.deleteClusterGroup(clusterGroupId);
	}

	public void deleteClusterGroup(
		com.liferay.portal.model.ClusterGroup clusterGroup)
		throws com.liferay.portal.kernel.exception.SystemException {
		_clusterGroupLocalService.deleteClusterGroup(clusterGroup);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _clusterGroupLocalService.dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _clusterGroupLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _clusterGroupLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _clusterGroupLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.ClusterGroup getClusterGroup(
		long clusterGroupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _clusterGroupLocalService.getClusterGroup(clusterGroupId);
	}

	public java.util.List<com.liferay.portal.model.ClusterGroup> getClusterGroups(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _clusterGroupLocalService.getClusterGroups(start, end);
	}

	public int getClusterGroupsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _clusterGroupLocalService.getClusterGroupsCount();
	}

	public com.liferay.portal.model.ClusterGroup updateClusterGroup(
		com.liferay.portal.model.ClusterGroup clusterGroup)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _clusterGroupLocalService.updateClusterGroup(clusterGroup);
	}

	public com.liferay.portal.model.ClusterGroup updateClusterGroup(
		com.liferay.portal.model.ClusterGroup clusterGroup, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _clusterGroupLocalService.updateClusterGroup(clusterGroup, merge);
	}

	public ClusterGroupLocalService getWrappedClusterGroupLocalService() {
		return _clusterGroupLocalService;
	}

	private ClusterGroupLocalService _clusterGroupLocalService;
}