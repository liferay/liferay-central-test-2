/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import com.liferay.portlet.asset.NoSuchTagStatsException;
import com.liferay.portlet.asset.model.AssetTagStats;
import com.liferay.portlet.asset.model.impl.AssetTagStatsModelImpl;
import com.liferay.portlet.asset.service.AssetTagStatsLocalServiceUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @generated
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class AssetTagStatsPersistenceTest {
	@ClassRule
	public static TransactionalTestRule transactionalTestRule = new TransactionalTestRule(Propagation.REQUIRED);

	@BeforeClass
	public static void setupClass() throws TemplateException {
		try {
			DBUpgrader.upgrade();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		TemplateManagerUtil.init();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AssetTagStats> iterator = _assetTagStatses.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetTagStats assetTagStats = _persistence.create(pk);

		Assert.assertNotNull(assetTagStats);

		Assert.assertEquals(assetTagStats.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AssetTagStats newAssetTagStats = addAssetTagStats();

		_persistence.remove(newAssetTagStats);

		AssetTagStats existingAssetTagStats = _persistence.fetchByPrimaryKey(newAssetTagStats.getPrimaryKey());

		Assert.assertNull(existingAssetTagStats);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssetTagStats();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetTagStats newAssetTagStats = _persistence.create(pk);

		newAssetTagStats.setTagId(RandomTestUtil.nextLong());

		newAssetTagStats.setClassNameId(RandomTestUtil.nextLong());

		newAssetTagStats.setAssetCount(RandomTestUtil.nextInt());

		_assetTagStatses.add(_persistence.update(newAssetTagStats));

		AssetTagStats existingAssetTagStats = _persistence.findByPrimaryKey(newAssetTagStats.getPrimaryKey());

		Assert.assertEquals(existingAssetTagStats.getTagStatsId(),
			newAssetTagStats.getTagStatsId());
		Assert.assertEquals(existingAssetTagStats.getTagId(),
			newAssetTagStats.getTagId());
		Assert.assertEquals(existingAssetTagStats.getClassNameId(),
			newAssetTagStats.getClassNameId());
		Assert.assertEquals(existingAssetTagStats.getAssetCount(),
			newAssetTagStats.getAssetCount());
	}

	@Test
	public void testCountByTagId() {
		try {
			_persistence.countByTagId(RandomTestUtil.nextLong());

			_persistence.countByTagId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByClassNameId() {
		try {
			_persistence.countByClassNameId(RandomTestUtil.nextLong());

			_persistence.countByClassNameId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByT_C() {
		try {
			_persistence.countByT_C(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByT_C(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetTagStats newAssetTagStats = addAssetTagStats();

		AssetTagStats existingAssetTagStats = _persistence.findByPrimaryKey(newAssetTagStats.getPrimaryKey());

		Assert.assertEquals(existingAssetTagStats, newAssetTagStats);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchTagStatsException");
		}
		catch (NoSuchTagStatsException nsee) {
		}
	}

	@Test
	public void testFindAll() throws Exception {
		try {
			_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator<AssetTagStats> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("AssetTagStats",
			"tagStatsId", true, "tagId", true, "classNameId", true,
			"assetCount", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetTagStats newAssetTagStats = addAssetTagStats();

		AssetTagStats existingAssetTagStats = _persistence.fetchByPrimaryKey(newAssetTagStats.getPrimaryKey());

		Assert.assertEquals(existingAssetTagStats, newAssetTagStats);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetTagStats missingAssetTagStats = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAssetTagStats);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		AssetTagStats newAssetTagStats1 = addAssetTagStats();
		AssetTagStats newAssetTagStats2 = addAssetTagStats();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetTagStats1.getPrimaryKey());
		primaryKeys.add(newAssetTagStats2.getPrimaryKey());

		Map<Serializable, AssetTagStats> assetTagStatses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, assetTagStatses.size());
		Assert.assertEquals(newAssetTagStats1,
			assetTagStatses.get(newAssetTagStats1.getPrimaryKey()));
		Assert.assertEquals(newAssetTagStats2,
			assetTagStatses.get(newAssetTagStats2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AssetTagStats> assetTagStatses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetTagStatses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		AssetTagStats newAssetTagStats = addAssetTagStats();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetTagStats.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AssetTagStats> assetTagStatses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetTagStatses.size());
		Assert.assertEquals(newAssetTagStats,
			assetTagStatses.get(newAssetTagStats.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AssetTagStats> assetTagStatses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetTagStatses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		AssetTagStats newAssetTagStats = addAssetTagStats();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetTagStats.getPrimaryKey());

		Map<Serializable, AssetTagStats> assetTagStatses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetTagStatses.size());
		Assert.assertEquals(newAssetTagStats,
			assetTagStatses.get(newAssetTagStats.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = AssetTagStatsLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					AssetTagStats assetTagStats = (AssetTagStats)object;

					Assert.assertNotNull(assetTagStats);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AssetTagStats newAssetTagStats = addAssetTagStats();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetTagStats.class,
				AssetTagStats.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("tagStatsId",
				newAssetTagStats.getTagStatsId()));

		List<AssetTagStats> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AssetTagStats existingAssetTagStats = result.get(0);

		Assert.assertEquals(existingAssetTagStats, newAssetTagStats);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetTagStats.class,
				AssetTagStats.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("tagStatsId",
				RandomTestUtil.nextLong()));

		List<AssetTagStats> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		AssetTagStats newAssetTagStats = addAssetTagStats();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetTagStats.class,
				AssetTagStats.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("tagStatsId"));

		Object newTagStatsId = newAssetTagStats.getTagStatsId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("tagStatsId",
				new Object[] { newTagStatsId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingTagStatsId = result.get(0);

		Assert.assertEquals(existingTagStatsId, newTagStatsId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetTagStats.class,
				AssetTagStats.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("tagStatsId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("tagStatsId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		AssetTagStats newAssetTagStats = addAssetTagStats();

		_persistence.clearCache();

		AssetTagStatsModelImpl existingAssetTagStatsModelImpl = (AssetTagStatsModelImpl)_persistence.findByPrimaryKey(newAssetTagStats.getPrimaryKey());

		Assert.assertEquals(existingAssetTagStatsModelImpl.getTagId(),
			existingAssetTagStatsModelImpl.getOriginalTagId());
		Assert.assertEquals(existingAssetTagStatsModelImpl.getClassNameId(),
			existingAssetTagStatsModelImpl.getOriginalClassNameId());
	}

	protected AssetTagStats addAssetTagStats() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetTagStats assetTagStats = _persistence.create(pk);

		assetTagStats.setTagId(RandomTestUtil.nextLong());

		assetTagStats.setClassNameId(RandomTestUtil.nextLong());

		assetTagStats.setAssetCount(RandomTestUtil.nextInt());

		_assetTagStatses.add(_persistence.update(assetTagStats));

		return assetTagStats;
	}

	private static Log _log = LogFactoryUtil.getLog(AssetTagStatsPersistenceTest.class);
	private List<AssetTagStats> _assetTagStatses = new ArrayList<AssetTagStats>();
	private ModelListener<AssetTagStats>[] _modelListeners;
	private AssetTagStatsPersistence _persistence = AssetTagStatsUtil.getPersistence();
}