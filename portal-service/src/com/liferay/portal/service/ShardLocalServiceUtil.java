/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

public class ShardLocalServiceUtil {
	public static com.liferay.portal.model.Shard addShard(
		com.liferay.portal.model.Shard shard)
		throws com.liferay.portal.SystemException {
		return getService().addShard(shard);
	}

	public static com.liferay.portal.model.Shard createShard(long shardId) {
		return getService().createShard(shardId);
	}

	public static void deleteShard(long shardId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteShard(shardId);
	}

	public static void deleteShard(com.liferay.portal.model.Shard shard)
		throws com.liferay.portal.SystemException {
		getService().deleteShard(shard);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.Shard getShard(long shardId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getShard(shardId);
	}

	public static java.util.List<com.liferay.portal.model.Shard> getShards(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getShards(start, end);
	}

	public static int getShardsCount()
		throws com.liferay.portal.SystemException {
		return getService().getShardsCount();
	}

	public static com.liferay.portal.model.Shard updateShard(
		com.liferay.portal.model.Shard shard)
		throws com.liferay.portal.SystemException {
		return getService().updateShard(shard);
	}

	public static com.liferay.portal.model.Shard updateShard(
		com.liferay.portal.model.Shard shard, boolean merge)
		throws com.liferay.portal.SystemException {
		return getService().updateShard(shard, merge);
	}

	public static com.liferay.portal.model.Shard addShard(
		java.lang.String className, long classPK, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getService().addShard(className, classPK, name);
	}

	public static com.liferay.portal.model.Shard getShard(
		java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getShard(className, classPK);
	}

	public static ShardLocalService getService() {
		if (_service == null) {
			throw new RuntimeException("ShardLocalService is not set");
		}

		return _service;
	}

	public void setService(ShardLocalService service) {
		_service = service;
	}

	private static ShardLocalService _service;
}