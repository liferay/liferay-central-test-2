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
 * <a href="ShardLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ShardLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShardLocalService
 * @generated
 */
public class ShardLocalServiceWrapper implements ShardLocalService {
	public ShardLocalServiceWrapper(ShardLocalService shardLocalService) {
		_shardLocalService = shardLocalService;
	}

	public com.liferay.portal.model.Shard addShard(
		com.liferay.portal.model.Shard shard)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shardLocalService.addShard(shard);
	}

	public com.liferay.portal.model.Shard createShard(long shardId) {
		return _shardLocalService.createShard(shardId);
	}

	public void deleteShard(long shardId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_shardLocalService.deleteShard(shardId);
	}

	public void deleteShard(com.liferay.portal.model.Shard shard)
		throws com.liferay.portal.kernel.exception.SystemException {
		_shardLocalService.deleteShard(shard);
	}

	public java.util.List<com.liferay.portal.model.Shard> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shardLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<com.liferay.portal.model.Shard> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _shardLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public java.util.List<com.liferay.portal.model.Shard> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shardLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shardLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.Shard getShard(long shardId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shardLocalService.getShard(shardId);
	}

	public java.util.List<com.liferay.portal.model.Shard> getShards(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _shardLocalService.getShards(start, end);
	}

	public int getShardsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shardLocalService.getShardsCount();
	}

	public com.liferay.portal.model.Shard updateShard(
		com.liferay.portal.model.Shard shard)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shardLocalService.updateShard(shard);
	}

	public com.liferay.portal.model.Shard updateShard(
		com.liferay.portal.model.Shard shard, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shardLocalService.updateShard(shard, merge);
	}

	public com.liferay.portal.model.Shard addShard(java.lang.String className,
		long classPK, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shardLocalService.addShard(className, classPK, name);
	}

	public com.liferay.portal.model.Shard getShard(java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shardLocalService.getShard(className, classPK);
	}

	public ShardLocalService getWrappedShardLocalService() {
		return _shardLocalService;
	}

	private ShardLocalService _shardLocalService;
}