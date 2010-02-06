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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.asset.NoSuchTagStatsException;
import com.liferay.portlet.asset.model.AssetTagStats;

import java.util.List;

/**
 * <a href="AssetTagStatsPersistenceTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class AssetTagStatsPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (AssetTagStatsPersistence)PortalBeanLocatorUtil.locate(AssetTagStatsPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		AssetTagStats assetTagStats = _persistence.create(pk);

		assertNotNull(assetTagStats);

		assertEquals(assetTagStats.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		AssetTagStats newAssetTagStats = addAssetTagStats();

		_persistence.remove(newAssetTagStats);

		AssetTagStats existingAssetTagStats = _persistence.fetchByPrimaryKey(newAssetTagStats.getPrimaryKey());

		assertNull(existingAssetTagStats);
	}

	public void testUpdateNew() throws Exception {
		addAssetTagStats();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		AssetTagStats newAssetTagStats = _persistence.create(pk);

		newAssetTagStats.setTagId(nextLong());
		newAssetTagStats.setClassNameId(nextLong());
		newAssetTagStats.setAssetCount(nextInt());

		_persistence.update(newAssetTagStats, false);

		AssetTagStats existingAssetTagStats = _persistence.findByPrimaryKey(newAssetTagStats.getPrimaryKey());

		assertEquals(existingAssetTagStats.getTagStatsId(),
			newAssetTagStats.getTagStatsId());
		assertEquals(existingAssetTagStats.getTagId(),
			newAssetTagStats.getTagId());
		assertEquals(existingAssetTagStats.getClassNameId(),
			newAssetTagStats.getClassNameId());
		assertEquals(existingAssetTagStats.getAssetCount(),
			newAssetTagStats.getAssetCount());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetTagStats newAssetTagStats = addAssetTagStats();

		AssetTagStats existingAssetTagStats = _persistence.findByPrimaryKey(newAssetTagStats.getPrimaryKey());

		assertEquals(existingAssetTagStats, newAssetTagStats);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchTagStatsException");
		}
		catch (NoSuchTagStatsException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetTagStats newAssetTagStats = addAssetTagStats();

		AssetTagStats existingAssetTagStats = _persistence.fetchByPrimaryKey(newAssetTagStats.getPrimaryKey());

		assertEquals(existingAssetTagStats, newAssetTagStats);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		AssetTagStats missingAssetTagStats = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingAssetTagStats);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AssetTagStats newAssetTagStats = addAssetTagStats();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetTagStats.class,
				AssetTagStats.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("tagStatsId",
				newAssetTagStats.getTagStatsId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		AssetTagStats existingAssetTagStats = (AssetTagStats)result.get(0);

		assertEquals(existingAssetTagStats, newAssetTagStats);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetTagStats.class,
				AssetTagStats.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("tagStatsId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected AssetTagStats addAssetTagStats() throws Exception {
		long pk = nextLong();

		AssetTagStats assetTagStats = _persistence.create(pk);

		assetTagStats.setTagId(nextLong());
		assetTagStats.setClassNameId(nextLong());
		assetTagStats.setAssetCount(nextInt());

		_persistence.update(assetTagStats, false);

		return assetTagStats;
	}

	private AssetTagStatsPersistence _persistence;
}