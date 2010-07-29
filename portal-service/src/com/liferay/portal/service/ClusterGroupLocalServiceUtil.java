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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <p>
 * This class provides static methods for the
 * {@link ClusterGroupLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ClusterGroupLocalService
 * @generated
 */
public class ClusterGroupLocalServiceUtil {
	public static com.liferay.portal.model.ClusterGroup addClusterGroup(
		com.liferay.portal.model.ClusterGroup clusterGroup)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addClusterGroup(clusterGroup);
	}

	public static com.liferay.portal.model.ClusterGroup createClusterGroup(
		long clusterGroupId) {
		return getService().createClusterGroup(clusterGroupId);
	}

	public static void deleteClusterGroup(long clusterGroupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteClusterGroup(clusterGroupId);
	}

	public static void deleteClusterGroup(
		com.liferay.portal.model.ClusterGroup clusterGroup)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteClusterGroup(clusterGroup);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portal.model.ClusterGroup getClusterGroup(
		long clusterGroupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getClusterGroup(clusterGroupId);
	}

	public static java.util.List<com.liferay.portal.model.ClusterGroup> getClusterGroups(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getClusterGroups(start, end);
	}

	public static int getClusterGroupsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getClusterGroupsCount();
	}

	public static com.liferay.portal.model.ClusterGroup updateClusterGroup(
		com.liferay.portal.model.ClusterGroup clusterGroup)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateClusterGroup(clusterGroup);
	}

	public static com.liferay.portal.model.ClusterGroup updateClusterGroup(
		com.liferay.portal.model.ClusterGroup clusterGroup, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateClusterGroup(clusterGroup, merge);
	}

	public static ClusterGroupLocalService getService() {
		if (_service == null) {
			_service = (ClusterGroupLocalService)PortalBeanLocatorUtil.locate(ClusterGroupLocalService.class.getName());
		}

		return _service;
	}

	public void setService(ClusterGroupLocalService service) {
		_service = service;
	}

	private static ClusterGroupLocalService _service;
}