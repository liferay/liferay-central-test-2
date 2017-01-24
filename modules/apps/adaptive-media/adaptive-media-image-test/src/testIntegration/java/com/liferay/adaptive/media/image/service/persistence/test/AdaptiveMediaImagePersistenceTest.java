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

package com.liferay.adaptive.media.image.service.persistence.test;

import com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageException;
import com.liferay.adaptive.media.image.model.AdaptiveMediaImage;
import com.liferay.adaptive.media.image.service.AdaptiveMediaImageLocalServiceUtil;
import com.liferay.adaptive.media.image.service.persistence.AdaptiveMediaImagePersistence;
import com.liferay.adaptive.media.image.service.persistence.AdaptiveMediaImageUtil;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class AdaptiveMediaImagePersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.adaptive.media.image.service"));

	@Before
	public void setUp() {
		_persistence = AdaptiveMediaImageUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AdaptiveMediaImage> iterator = _adaptiveMediaImages.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AdaptiveMediaImage adaptiveMediaImage = _persistence.create(pk);

		Assert.assertNotNull(adaptiveMediaImage);

		Assert.assertEquals(adaptiveMediaImage.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AdaptiveMediaImage newAdaptiveMediaImage = addAdaptiveMediaImage();

		_persistence.remove(newAdaptiveMediaImage);

		AdaptiveMediaImage existingAdaptiveMediaImage = _persistence.fetchByPrimaryKey(newAdaptiveMediaImage.getPrimaryKey());

		Assert.assertNull(existingAdaptiveMediaImage);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAdaptiveMediaImage();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AdaptiveMediaImage newAdaptiveMediaImage = _persistence.create(pk);

		newAdaptiveMediaImage.setUuid(RandomTestUtil.randomString());

		newAdaptiveMediaImage.setGroupId(RandomTestUtil.nextLong());

		newAdaptiveMediaImage.setCompanyId(RandomTestUtil.nextLong());

		newAdaptiveMediaImage.setCreateDate(RandomTestUtil.nextDate());

		newAdaptiveMediaImage.setConfigurationUuid(RandomTestUtil.randomString());

		newAdaptiveMediaImage.setFileVersionId(RandomTestUtil.nextLong());

		newAdaptiveMediaImage.setHeight(RandomTestUtil.nextInt());

		newAdaptiveMediaImage.setWidth(RandomTestUtil.nextInt());

		newAdaptiveMediaImage.setSize(RandomTestUtil.nextLong());

		_adaptiveMediaImages.add(_persistence.update(newAdaptiveMediaImage));

		AdaptiveMediaImage existingAdaptiveMediaImage = _persistence.findByPrimaryKey(newAdaptiveMediaImage.getPrimaryKey());

		Assert.assertEquals(existingAdaptiveMediaImage.getUuid(),
			newAdaptiveMediaImage.getUuid());
		Assert.assertEquals(existingAdaptiveMediaImage.getAdaptiveMediaImageId(),
			newAdaptiveMediaImage.getAdaptiveMediaImageId());
		Assert.assertEquals(existingAdaptiveMediaImage.getGroupId(),
			newAdaptiveMediaImage.getGroupId());
		Assert.assertEquals(existingAdaptiveMediaImage.getCompanyId(),
			newAdaptiveMediaImage.getCompanyId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingAdaptiveMediaImage.getCreateDate()),
			Time.getShortTimestamp(newAdaptiveMediaImage.getCreateDate()));
		Assert.assertEquals(existingAdaptiveMediaImage.getConfigurationUuid(),
			newAdaptiveMediaImage.getConfigurationUuid());
		Assert.assertEquals(existingAdaptiveMediaImage.getFileVersionId(),
			newAdaptiveMediaImage.getFileVersionId());
		Assert.assertEquals(existingAdaptiveMediaImage.getHeight(),
			newAdaptiveMediaImage.getHeight());
		Assert.assertEquals(existingAdaptiveMediaImage.getWidth(),
			newAdaptiveMediaImage.getWidth());
		Assert.assertEquals(existingAdaptiveMediaImage.getSize(),
			newAdaptiveMediaImage.getSize());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid(StringPool.BLANK);

		_persistence.countByUuid(StringPool.NULL);

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUUID_G(StringPool.NULL, 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUuid_C(StringPool.NULL, 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByConfigurationUuid() throws Exception {
		_persistence.countByConfigurationUuid(StringPool.BLANK);

		_persistence.countByConfigurationUuid(StringPool.NULL);

		_persistence.countByConfigurationUuid((String)null);
	}

	@Test
	public void testCountByFileVersionId() throws Exception {
		_persistence.countByFileVersionId(RandomTestUtil.nextLong());

		_persistence.countByFileVersionId(0L);
	}

	@Test
	public void testCountByC_F() throws Exception {
		_persistence.countByC_F(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByC_F(StringPool.NULL, 0L);

		_persistence.countByC_F((String)null, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AdaptiveMediaImage newAdaptiveMediaImage = addAdaptiveMediaImage();

		AdaptiveMediaImage existingAdaptiveMediaImage = _persistence.findByPrimaryKey(newAdaptiveMediaImage.getPrimaryKey());

		Assert.assertEquals(existingAdaptiveMediaImage, newAdaptiveMediaImage);
	}

	@Test(expected = NoSuchAdaptiveMediaImageException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<AdaptiveMediaImage> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("AdaptiveMediaImage",
			"uuid", true, "adaptiveMediaImageId", true, "groupId", true,
			"companyId", true, "createDate", true, "configurationUuid", true,
			"fileVersionId", true, "height", true, "width", true, "size", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AdaptiveMediaImage newAdaptiveMediaImage = addAdaptiveMediaImage();

		AdaptiveMediaImage existingAdaptiveMediaImage = _persistence.fetchByPrimaryKey(newAdaptiveMediaImage.getPrimaryKey());

		Assert.assertEquals(existingAdaptiveMediaImage, newAdaptiveMediaImage);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AdaptiveMediaImage missingAdaptiveMediaImage = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAdaptiveMediaImage);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		AdaptiveMediaImage newAdaptiveMediaImage1 = addAdaptiveMediaImage();
		AdaptiveMediaImage newAdaptiveMediaImage2 = addAdaptiveMediaImage();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAdaptiveMediaImage1.getPrimaryKey());
		primaryKeys.add(newAdaptiveMediaImage2.getPrimaryKey());

		Map<Serializable, AdaptiveMediaImage> adaptiveMediaImages = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, adaptiveMediaImages.size());
		Assert.assertEquals(newAdaptiveMediaImage1,
			adaptiveMediaImages.get(newAdaptiveMediaImage1.getPrimaryKey()));
		Assert.assertEquals(newAdaptiveMediaImage2,
			adaptiveMediaImages.get(newAdaptiveMediaImage2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AdaptiveMediaImage> adaptiveMediaImages = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(adaptiveMediaImages.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		AdaptiveMediaImage newAdaptiveMediaImage = addAdaptiveMediaImage();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAdaptiveMediaImage.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AdaptiveMediaImage> adaptiveMediaImages = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, adaptiveMediaImages.size());
		Assert.assertEquals(newAdaptiveMediaImage,
			adaptiveMediaImages.get(newAdaptiveMediaImage.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AdaptiveMediaImage> adaptiveMediaImages = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(adaptiveMediaImages.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		AdaptiveMediaImage newAdaptiveMediaImage = addAdaptiveMediaImage();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAdaptiveMediaImage.getPrimaryKey());

		Map<Serializable, AdaptiveMediaImage> adaptiveMediaImages = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, adaptiveMediaImages.size());
		Assert.assertEquals(newAdaptiveMediaImage,
			adaptiveMediaImages.get(newAdaptiveMediaImage.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = AdaptiveMediaImageLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<AdaptiveMediaImage>() {
				@Override
				public void performAction(AdaptiveMediaImage adaptiveMediaImage) {
					Assert.assertNotNull(adaptiveMediaImage);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AdaptiveMediaImage newAdaptiveMediaImage = addAdaptiveMediaImage();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AdaptiveMediaImage.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("adaptiveMediaImageId",
				newAdaptiveMediaImage.getAdaptiveMediaImageId()));

		List<AdaptiveMediaImage> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AdaptiveMediaImage existingAdaptiveMediaImage = result.get(0);

		Assert.assertEquals(existingAdaptiveMediaImage, newAdaptiveMediaImage);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AdaptiveMediaImage.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("adaptiveMediaImageId",
				RandomTestUtil.nextLong()));

		List<AdaptiveMediaImage> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		AdaptiveMediaImage newAdaptiveMediaImage = addAdaptiveMediaImage();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AdaptiveMediaImage.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"adaptiveMediaImageId"));

		Object newAdaptiveMediaImageId = newAdaptiveMediaImage.getAdaptiveMediaImageId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("adaptiveMediaImageId",
				new Object[] { newAdaptiveMediaImageId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAdaptiveMediaImageId = result.get(0);

		Assert.assertEquals(existingAdaptiveMediaImageId,
			newAdaptiveMediaImageId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AdaptiveMediaImage.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"adaptiveMediaImageId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("adaptiveMediaImageId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AdaptiveMediaImage newAdaptiveMediaImage = addAdaptiveMediaImage();

		_persistence.clearCache();

		AdaptiveMediaImage existingAdaptiveMediaImage = _persistence.findByPrimaryKey(newAdaptiveMediaImage.getPrimaryKey());

		Assert.assertTrue(Objects.equals(existingAdaptiveMediaImage.getUuid(),
				ReflectionTestUtil.invoke(existingAdaptiveMediaImage,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(
				existingAdaptiveMediaImage.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingAdaptiveMediaImage,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertTrue(Objects.equals(
				existingAdaptiveMediaImage.getConfigurationUuid(),
				ReflectionTestUtil.invoke(existingAdaptiveMediaImage,
					"getOriginalConfigurationUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(
				existingAdaptiveMediaImage.getFileVersionId()),
			ReflectionTestUtil.<Long>invoke(existingAdaptiveMediaImage,
				"getOriginalFileVersionId", new Class<?>[0]));
	}

	protected AdaptiveMediaImage addAdaptiveMediaImage()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		AdaptiveMediaImage adaptiveMediaImage = _persistence.create(pk);

		adaptiveMediaImage.setUuid(RandomTestUtil.randomString());

		adaptiveMediaImage.setGroupId(RandomTestUtil.nextLong());

		adaptiveMediaImage.setCompanyId(RandomTestUtil.nextLong());

		adaptiveMediaImage.setCreateDate(RandomTestUtil.nextDate());

		adaptiveMediaImage.setConfigurationUuid(RandomTestUtil.randomString());

		adaptiveMediaImage.setFileVersionId(RandomTestUtil.nextLong());

		adaptiveMediaImage.setHeight(RandomTestUtil.nextInt());

		adaptiveMediaImage.setWidth(RandomTestUtil.nextInt());

		adaptiveMediaImage.setSize(RandomTestUtil.nextLong());

		_adaptiveMediaImages.add(_persistence.update(adaptiveMediaImage));

		return adaptiveMediaImage;
	}

	private List<AdaptiveMediaImage> _adaptiveMediaImages = new ArrayList<AdaptiveMediaImage>();
	private AdaptiveMediaImagePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}