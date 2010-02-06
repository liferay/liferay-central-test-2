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

import com.liferay.portal.NoSuchShardException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.model.Shard;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * <a href="ShardPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ShardPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ShardPersistence)PortalBeanLocatorUtil.locate(ShardPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Shard shard = _persistence.create(pk);

		assertNotNull(shard);

		assertEquals(shard.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Shard newShard = addShard();

		_persistence.remove(newShard);

		Shard existingShard = _persistence.fetchByPrimaryKey(newShard.getPrimaryKey());

		assertNull(existingShard);
	}

	public void testUpdateNew() throws Exception {
		addShard();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Shard newShard = _persistence.create(pk);

		newShard.setClassNameId(nextLong());
		newShard.setClassPK(nextLong());
		newShard.setName(randomString());

		_persistence.update(newShard, false);

		Shard existingShard = _persistence.findByPrimaryKey(newShard.getPrimaryKey());

		assertEquals(existingShard.getShardId(), newShard.getShardId());
		assertEquals(existingShard.getClassNameId(), newShard.getClassNameId());
		assertEquals(existingShard.getClassPK(), newShard.getClassPK());
		assertEquals(existingShard.getName(), newShard.getName());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Shard newShard = addShard();

		Shard existingShard = _persistence.findByPrimaryKey(newShard.getPrimaryKey());

		assertEquals(existingShard, newShard);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchShardException");
		}
		catch (NoSuchShardException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Shard newShard = addShard();

		Shard existingShard = _persistence.fetchByPrimaryKey(newShard.getPrimaryKey());

		assertEquals(existingShard, newShard);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Shard missingShard = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingShard);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Shard newShard = addShard();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Shard.class,
				Shard.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("shardId",
				newShard.getShardId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Shard existingShard = (Shard)result.get(0);

		assertEquals(existingShard, newShard);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Shard.class,
				Shard.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("shardId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected Shard addShard() throws Exception {
		long pk = nextLong();

		Shard shard = _persistence.create(pk);

		shard.setClassNameId(nextLong());
		shard.setClassPK(nextLong());
		shard.setName(randomString());

		_persistence.update(shard, false);

		return shard;
	}

	private ShardPersistence _persistence;
}