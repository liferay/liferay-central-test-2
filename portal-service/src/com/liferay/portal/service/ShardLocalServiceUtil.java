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
 * <a href="ShardLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link ShardLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShardLocalService
 * @generated
 */
public class ShardLocalServiceUtil {
	public static com.liferay.portal.model.Shard addShard(
		com.liferay.portal.model.Shard shard)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addShard(shard);
	}

	public static com.liferay.portal.model.Shard createShard(long shardId) {
		return getService().createShard(shardId);
	}

	public static void deleteShard(long shardId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteShard(shardId);
	}

	public static void deleteShard(com.liferay.portal.model.Shard shard)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteShard(shard);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.Shard getShard(long shardId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getShard(shardId);
	}

	public static java.util.List<com.liferay.portal.model.Shard> getShards(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getShards(start, end);
	}

	public static int getShardsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getShardsCount();
	}

	public static com.liferay.portal.model.Shard updateShard(
		com.liferay.portal.model.Shard shard)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateShard(shard);
	}

	public static com.liferay.portal.model.Shard updateShard(
		com.liferay.portal.model.Shard shard, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateShard(shard, merge);
	}

	public static com.liferay.portal.model.Shard addShard(
		java.lang.String className, long classPK, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addShard(className, classPK, name);
	}

	public static com.liferay.portal.model.Shard getShard(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getShard(className, classPK);
	}

	public static ShardLocalService getService() {
		if (_service == null) {
			_service = (ShardLocalService)PortalBeanLocatorUtil.locate(ShardLocalService.class.getName());
		}

		return _service;
	}

	public void setService(ShardLocalService service) {
		_service = service;
	}

	private static ShardLocalService _service;
}