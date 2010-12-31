/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

	/**
	* Adds the shard to the database. Also notifies the appropriate model listeners.
	*
	* @param shard the shard to add
	* @return the shard that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Shard addShard(
		com.liferay.portal.model.Shard shard)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shardLocalService.addShard(shard);
	}

	/**
	* Creates a new shard with the primary key. Does not add the shard to the database.
	*
	* @param shardId the primary key for the new shard
	* @return the new shard
	*/
	public com.liferay.portal.model.Shard createShard(long shardId) {
		return _shardLocalService.createShard(shardId);
	}

	/**
	* Deletes the shard with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param shardId the primary key of the shard to delete
	* @throws PortalException if a shard with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteShard(long shardId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_shardLocalService.deleteShard(shardId);
	}

	/**
	* Deletes the shard from the database. Also notifies the appropriate model listeners.
	*
	* @param shard the shard to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteShard(com.liferay.portal.model.Shard shard)
		throws com.liferay.portal.kernel.exception.SystemException {
		_shardLocalService.deleteShard(shard);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shardLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _shardLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shardLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shardLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the shard with the primary key.
	*
	* @param shardId the primary key of the shard to get
	* @return the shard
	* @throws PortalException if a shard with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Shard getShard(long shardId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shardLocalService.getShard(shardId);
	}

	/**
	* Gets a range of all the shards.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of shards to return
	* @param end the upper bound of the range of shards to return (not inclusive)
	* @return the range of shards
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Shard> getShards(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _shardLocalService.getShards(start, end);
	}

	/**
	* Gets the number of shards.
	*
	* @return the number of shards
	* @throws SystemException if a system exception occurred
	*/
	public int getShardsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shardLocalService.getShardsCount();
	}

	/**
	* Updates the shard in the database. Also notifies the appropriate model listeners.
	*
	* @param shard the shard to update
	* @return the shard that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Shard updateShard(
		com.liferay.portal.model.Shard shard)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shardLocalService.updateShard(shard);
	}

	/**
	* Updates the shard in the database. Also notifies the appropriate model listeners.
	*
	* @param shard the shard to update
	* @param merge whether to merge the shard with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the shard that was updated
	* @throws SystemException if a system exception occurred
	*/
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

	public void setWrappedShardLocalService(ShardLocalService shardLocalService) {
		_shardLocalService = shardLocalService;
	}

	private ShardLocalService _shardLocalService;
}