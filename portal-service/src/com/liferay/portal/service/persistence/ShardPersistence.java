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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.Shard;

/**
 * <a href="ShardPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShardPersistenceImpl
 * @see       ShardUtil
 * @generated
 */
public interface ShardPersistence extends BasePersistence<Shard> {
	public void cacheResult(com.liferay.portal.model.Shard shard);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.Shard> shards);

	public com.liferay.portal.model.Shard create(long shardId);

	public com.liferay.portal.model.Shard remove(long shardId)
		throws com.liferay.portal.NoSuchShardException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Shard updateImpl(
		com.liferay.portal.model.Shard shard, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Shard findByPrimaryKey(long shardId)
		throws com.liferay.portal.NoSuchShardException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Shard fetchByPrimaryKey(long shardId)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Shard findByName(java.lang.String name)
		throws com.liferay.portal.NoSuchShardException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Shard fetchByName(java.lang.String name)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Shard fetchByName(java.lang.String name,
		boolean retrieveFromCache) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Shard findByC_C(long classNameId,
		long classPK)
		throws com.liferay.portal.NoSuchShardException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Shard fetchByC_C(long classNameId,
		long classPK) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Shard fetchByC_C(long classNameId,
		long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Shard> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Shard> findAll(int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Shard> findAll(int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByName(java.lang.String name)
		throws com.liferay.portal.NoSuchShardException,
			com.liferay.portal.SystemException;

	public void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.NoSuchShardException,
			com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByName(java.lang.String name)
		throws com.liferay.portal.SystemException;

	public int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}