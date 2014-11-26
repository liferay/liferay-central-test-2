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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchRegionException;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.AggregateTestRule;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Region;
import com.liferay.portal.model.impl.RegionModelImpl;
import com.liferay.portal.test.LiferayIntegrationTestRule;
import com.liferay.portal.test.PersistenceTestRule;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

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
public class RegionPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@After
	public void tearDown() throws Exception {
		Iterator<Region> iterator = _regions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Region region = _persistence.create(pk);

		Assert.assertNotNull(region);

		Assert.assertEquals(region.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Region newRegion = addRegion();

		_persistence.remove(newRegion);

		Region existingRegion = _persistence.fetchByPrimaryKey(newRegion.getPrimaryKey());

		Assert.assertNull(existingRegion);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addRegion();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Region newRegion = _persistence.create(pk);

		newRegion.setMvccVersion(RandomTestUtil.nextLong());

		newRegion.setCountryId(RandomTestUtil.nextLong());

		newRegion.setRegionCode(RandomTestUtil.randomString());

		newRegion.setName(RandomTestUtil.randomString());

		newRegion.setActive(RandomTestUtil.randomBoolean());

		_regions.add(_persistence.update(newRegion));

		Region existingRegion = _persistence.findByPrimaryKey(newRegion.getPrimaryKey());

		Assert.assertEquals(existingRegion.getMvccVersion(),
			newRegion.getMvccVersion());
		Assert.assertEquals(existingRegion.getRegionId(),
			newRegion.getRegionId());
		Assert.assertEquals(existingRegion.getCountryId(),
			newRegion.getCountryId());
		Assert.assertEquals(existingRegion.getRegionCode(),
			newRegion.getRegionCode());
		Assert.assertEquals(existingRegion.getName(), newRegion.getName());
		Assert.assertEquals(existingRegion.getActive(), newRegion.getActive());
	}

	@Test
	public void testCountByCountryId() {
		try {
			_persistence.countByCountryId(RandomTestUtil.nextLong());

			_persistence.countByCountryId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByActive() {
		try {
			_persistence.countByActive(RandomTestUtil.randomBoolean());

			_persistence.countByActive(RandomTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_R() {
		try {
			_persistence.countByC_R(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByC_R(0L, StringPool.NULL);

			_persistence.countByC_R(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_A() {
		try {
			_persistence.countByC_A(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean());

			_persistence.countByC_A(0L, RandomTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Region newRegion = addRegion();

		Region existingRegion = _persistence.findByPrimaryKey(newRegion.getPrimaryKey());

		Assert.assertEquals(existingRegion, newRegion);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchRegionException");
		}
		catch (NoSuchRegionException nsee) {
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

	protected OrderByComparator<Region> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("Region", "mvccVersion",
			true, "regionId", true, "countryId", true, "regionCode", true,
			"name", true, "active", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Region newRegion = addRegion();

		Region existingRegion = _persistence.fetchByPrimaryKey(newRegion.getPrimaryKey());

		Assert.assertEquals(existingRegion, newRegion);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Region missingRegion = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingRegion);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		Region newRegion1 = addRegion();
		Region newRegion2 = addRegion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRegion1.getPrimaryKey());
		primaryKeys.add(newRegion2.getPrimaryKey());

		Map<Serializable, Region> regions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, regions.size());
		Assert.assertEquals(newRegion1, regions.get(newRegion1.getPrimaryKey()));
		Assert.assertEquals(newRegion2, regions.get(newRegion2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Region> regions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(regions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		Region newRegion = addRegion();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRegion.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Region> regions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, regions.size());
		Assert.assertEquals(newRegion, regions.get(newRegion.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Region> regions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(regions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		Region newRegion = addRegion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRegion.getPrimaryKey());

		Map<Serializable, Region> regions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, regions.size());
		Assert.assertEquals(newRegion, regions.get(newRegion.getPrimaryKey()));
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Region newRegion = addRegion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Region.class,
				Region.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("regionId",
				newRegion.getRegionId()));

		List<Region> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Region existingRegion = result.get(0);

		Assert.assertEquals(existingRegion, newRegion);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Region.class,
				Region.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("regionId",
				RandomTestUtil.nextLong()));

		List<Region> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Region newRegion = addRegion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Region.class,
				Region.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("regionId"));

		Object newRegionId = newRegion.getRegionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("regionId",
				new Object[] { newRegionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingRegionId = result.get(0);

		Assert.assertEquals(existingRegionId, newRegionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Region.class,
				Region.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("regionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("regionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		Region newRegion = addRegion();

		_persistence.clearCache();

		RegionModelImpl existingRegionModelImpl = (RegionModelImpl)_persistence.findByPrimaryKey(newRegion.getPrimaryKey());

		Assert.assertEquals(existingRegionModelImpl.getCountryId(),
			existingRegionModelImpl.getOriginalCountryId());
		Assert.assertTrue(Validator.equals(
				existingRegionModelImpl.getRegionCode(),
				existingRegionModelImpl.getOriginalRegionCode()));
	}

	protected Region addRegion() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Region region = _persistence.create(pk);

		region.setMvccVersion(RandomTestUtil.nextLong());

		region.setCountryId(RandomTestUtil.nextLong());

		region.setRegionCode(RandomTestUtil.randomString());

		region.setName(RandomTestUtil.randomString());

		region.setActive(RandomTestUtil.randomBoolean());

		_regions.add(_persistence.update(region));

		return region;
	}

	private List<Region> _regions = new ArrayList<Region>();
	private RegionPersistence _persistence = RegionUtil.getPersistence();
}