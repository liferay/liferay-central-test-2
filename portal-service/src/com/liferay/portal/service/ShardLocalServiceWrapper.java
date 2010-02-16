/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shardLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _shardLocalService.dynamicQuery(dynamicQuery, start, end);
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