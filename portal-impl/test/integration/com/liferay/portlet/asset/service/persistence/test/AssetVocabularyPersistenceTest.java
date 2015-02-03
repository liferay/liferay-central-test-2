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

package com.liferay.portlet.asset.service.persistence.test;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.asset.NoSuchVocabularyException;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetVocabularyPersistence;
import com.liferay.portlet.asset.service.persistence.AssetVocabularyUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
public class AssetVocabularyPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = AssetVocabularyUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AssetVocabulary> iterator = _assetVocabularies.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetVocabulary assetVocabulary = _persistence.create(pk);

		Assert.assertNotNull(assetVocabulary);

		Assert.assertEquals(assetVocabulary.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AssetVocabulary newAssetVocabulary = addAssetVocabulary();

		_persistence.remove(newAssetVocabulary);

		AssetVocabulary existingAssetVocabulary = _persistence.fetchByPrimaryKey(newAssetVocabulary.getPrimaryKey());

		Assert.assertNull(existingAssetVocabulary);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssetVocabulary();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetVocabulary newAssetVocabulary = _persistence.create(pk);

		newAssetVocabulary.setUuid(RandomTestUtil.randomString());

		newAssetVocabulary.setGroupId(RandomTestUtil.nextLong());

		newAssetVocabulary.setCompanyId(RandomTestUtil.nextLong());

		newAssetVocabulary.setUserId(RandomTestUtil.nextLong());

		newAssetVocabulary.setUserName(RandomTestUtil.randomString());

		newAssetVocabulary.setCreateDate(RandomTestUtil.nextDate());

		newAssetVocabulary.setModifiedDate(RandomTestUtil.nextDate());

		newAssetVocabulary.setName(RandomTestUtil.randomString());

		newAssetVocabulary.setTitle(RandomTestUtil.randomString());

		newAssetVocabulary.setDescription(RandomTestUtil.randomString());

		newAssetVocabulary.setSettings(RandomTestUtil.randomString());

		_assetVocabularies.add(_persistence.update(newAssetVocabulary));

		AssetVocabulary existingAssetVocabulary = _persistence.findByPrimaryKey(newAssetVocabulary.getPrimaryKey());

		Assert.assertEquals(existingAssetVocabulary.getUuid(),
			newAssetVocabulary.getUuid());
		Assert.assertEquals(existingAssetVocabulary.getVocabularyId(),
			newAssetVocabulary.getVocabularyId());
		Assert.assertEquals(existingAssetVocabulary.getGroupId(),
			newAssetVocabulary.getGroupId());
		Assert.assertEquals(existingAssetVocabulary.getCompanyId(),
			newAssetVocabulary.getCompanyId());
		Assert.assertEquals(existingAssetVocabulary.getUserId(),
			newAssetVocabulary.getUserId());
		Assert.assertEquals(existingAssetVocabulary.getUserName(),
			newAssetVocabulary.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetVocabulary.getCreateDate()),
			Time.getShortTimestamp(newAssetVocabulary.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetVocabulary.getModifiedDate()),
			Time.getShortTimestamp(newAssetVocabulary.getModifiedDate()));
		Assert.assertEquals(existingAssetVocabulary.getName(),
			newAssetVocabulary.getName());
		Assert.assertEquals(existingAssetVocabulary.getTitle(),
			newAssetVocabulary.getTitle());
		Assert.assertEquals(existingAssetVocabulary.getDescription(),
			newAssetVocabulary.getDescription());
		Assert.assertEquals(existingAssetVocabulary.getSettings(),
			newAssetVocabulary.getSettings());
	}

	@Test
	public void testCountByUuid() {
		try {
			_persistence.countByUuid(StringPool.BLANK);

			_persistence.countByUuid(StringPool.NULL);

			_persistence.countByUuid((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUUID_G() {
		try {
			_persistence.countByUUID_G(StringPool.BLANK,
				RandomTestUtil.nextLong());

			_persistence.countByUUID_G(StringPool.NULL, 0L);

			_persistence.countByUUID_G((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUuid_C() {
		try {
			_persistence.countByUuid_C(StringPool.BLANK,
				RandomTestUtil.nextLong());

			_persistence.countByUuid_C(StringPool.NULL, 0L);

			_persistence.countByUuid_C((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByGroupId() {
		try {
			_persistence.countByGroupId(RandomTestUtil.nextLong());

			_persistence.countByGroupId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByGroupIdArrayable() {
		try {
			_persistence.countByGroupId(new long[] { RandomTestUtil.nextLong(), 0L });
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByCompanyId() {
		try {
			_persistence.countByCompanyId(RandomTestUtil.nextLong());

			_persistence.countByCompanyId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_N() {
		try {
			_persistence.countByG_N(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByG_N(0L, StringPool.NULL);

			_persistence.countByG_N(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_LikeN() {
		try {
			_persistence.countByG_LikeN(RandomTestUtil.nextLong(),
				StringPool.BLANK);

			_persistence.countByG_LikeN(0L, StringPool.NULL);

			_persistence.countByG_LikeN(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetVocabulary newAssetVocabulary = addAssetVocabulary();

		AssetVocabulary existingAssetVocabulary = _persistence.findByPrimaryKey(newAssetVocabulary.getPrimaryKey());

		Assert.assertEquals(existingAssetVocabulary, newAssetVocabulary);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchVocabularyException");
		}
		catch (NoSuchVocabularyException nsee) {
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

	@Test
	public void testFilterFindByGroupId() throws Exception {
		try {
			_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator<AssetVocabulary> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("AssetVocabulary", "uuid",
			true, "vocabularyId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "name", true, "title", true, "description",
			true, "settings", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetVocabulary newAssetVocabulary = addAssetVocabulary();

		AssetVocabulary existingAssetVocabulary = _persistence.fetchByPrimaryKey(newAssetVocabulary.getPrimaryKey());

		Assert.assertEquals(existingAssetVocabulary, newAssetVocabulary);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetVocabulary missingAssetVocabulary = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAssetVocabulary);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		AssetVocabulary newAssetVocabulary1 = addAssetVocabulary();
		AssetVocabulary newAssetVocabulary2 = addAssetVocabulary();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetVocabulary1.getPrimaryKey());
		primaryKeys.add(newAssetVocabulary2.getPrimaryKey());

		Map<Serializable, AssetVocabulary> assetVocabularies = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, assetVocabularies.size());
		Assert.assertEquals(newAssetVocabulary1,
			assetVocabularies.get(newAssetVocabulary1.getPrimaryKey()));
		Assert.assertEquals(newAssetVocabulary2,
			assetVocabularies.get(newAssetVocabulary2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AssetVocabulary> assetVocabularies = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetVocabularies.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		AssetVocabulary newAssetVocabulary = addAssetVocabulary();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetVocabulary.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AssetVocabulary> assetVocabularies = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetVocabularies.size());
		Assert.assertEquals(newAssetVocabulary,
			assetVocabularies.get(newAssetVocabulary.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AssetVocabulary> assetVocabularies = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetVocabularies.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		AssetVocabulary newAssetVocabulary = addAssetVocabulary();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetVocabulary.getPrimaryKey());

		Map<Serializable, AssetVocabulary> assetVocabularies = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetVocabularies.size());
		Assert.assertEquals(newAssetVocabulary,
			assetVocabularies.get(newAssetVocabulary.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = AssetVocabularyLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					AssetVocabulary assetVocabulary = (AssetVocabulary)object;

					Assert.assertNotNull(assetVocabulary);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AssetVocabulary newAssetVocabulary = addAssetVocabulary();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetVocabulary.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("vocabularyId",
				newAssetVocabulary.getVocabularyId()));

		List<AssetVocabulary> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AssetVocabulary existingAssetVocabulary = result.get(0);

		Assert.assertEquals(existingAssetVocabulary, newAssetVocabulary);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetVocabulary.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("vocabularyId",
				RandomTestUtil.nextLong()));

		List<AssetVocabulary> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		AssetVocabulary newAssetVocabulary = addAssetVocabulary();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetVocabulary.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"vocabularyId"));

		Object newVocabularyId = newAssetVocabulary.getVocabularyId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("vocabularyId",
				new Object[] { newVocabularyId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingVocabularyId = result.get(0);

		Assert.assertEquals(existingVocabularyId, newVocabularyId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetVocabulary.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"vocabularyId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("vocabularyId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		AssetVocabulary newAssetVocabulary = addAssetVocabulary();

		_persistence.clearCache();

		AssetVocabulary existingAssetVocabulary = _persistence.findByPrimaryKey(newAssetVocabulary.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingAssetVocabulary.getUuid(),
				ReflectionTestUtil.invoke(existingAssetVocabulary,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(existingAssetVocabulary.getGroupId(),
			ReflectionTestUtil.invoke(existingAssetVocabulary,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(existingAssetVocabulary.getGroupId(),
			ReflectionTestUtil.invoke(existingAssetVocabulary,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertTrue(Validator.equals(existingAssetVocabulary.getName(),
				ReflectionTestUtil.invoke(existingAssetVocabulary,
					"getOriginalName", new Class<?>[0])));
	}

	protected AssetVocabulary addAssetVocabulary() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetVocabulary assetVocabulary = _persistence.create(pk);

		assetVocabulary.setUuid(RandomTestUtil.randomString());

		assetVocabulary.setGroupId(RandomTestUtil.nextLong());

		assetVocabulary.setCompanyId(RandomTestUtil.nextLong());

		assetVocabulary.setUserId(RandomTestUtil.nextLong());

		assetVocabulary.setUserName(RandomTestUtil.randomString());

		assetVocabulary.setCreateDate(RandomTestUtil.nextDate());

		assetVocabulary.setModifiedDate(RandomTestUtil.nextDate());

		assetVocabulary.setName(RandomTestUtil.randomString());

		assetVocabulary.setTitle(RandomTestUtil.randomString());

		assetVocabulary.setDescription(RandomTestUtil.randomString());

		assetVocabulary.setSettings(RandomTestUtil.randomString());

		_assetVocabularies.add(_persistence.update(assetVocabulary));

		return assetVocabulary;
	}

	private List<AssetVocabulary> _assetVocabularies = new ArrayList<AssetVocabulary>();
	private AssetVocabularyPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}