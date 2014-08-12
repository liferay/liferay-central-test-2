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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import com.liferay.portlet.asset.NoSuchTagException;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.impl.AssetTagModelImpl;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;

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
public class AssetTagPersistenceTest {
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
		Iterator<AssetTag> iterator = _assetTags.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetTag assetTag = _persistence.create(pk);

		Assert.assertNotNull(assetTag);

		Assert.assertEquals(assetTag.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AssetTag newAssetTag = addAssetTag();

		_persistence.remove(newAssetTag);

		AssetTag existingAssetTag = _persistence.fetchByPrimaryKey(newAssetTag.getPrimaryKey());

		Assert.assertNull(existingAssetTag);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssetTag();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetTag newAssetTag = _persistence.create(pk);

		newAssetTag.setGroupId(RandomTestUtil.nextLong());

		newAssetTag.setCompanyId(RandomTestUtil.nextLong());

		newAssetTag.setUserId(RandomTestUtil.nextLong());

		newAssetTag.setUserName(RandomTestUtil.randomString());

		newAssetTag.setCreateDate(RandomTestUtil.nextDate());

		newAssetTag.setModifiedDate(RandomTestUtil.nextDate());

		newAssetTag.setName(RandomTestUtil.randomString());

		newAssetTag.setAssetCount(RandomTestUtil.nextInt());

		_assetTags.add(_persistence.update(newAssetTag));

		AssetTag existingAssetTag = _persistence.findByPrimaryKey(newAssetTag.getPrimaryKey());

		Assert.assertEquals(existingAssetTag.getTagId(), newAssetTag.getTagId());
		Assert.assertEquals(existingAssetTag.getGroupId(),
			newAssetTag.getGroupId());
		Assert.assertEquals(existingAssetTag.getCompanyId(),
			newAssetTag.getCompanyId());
		Assert.assertEquals(existingAssetTag.getUserId(),
			newAssetTag.getUserId());
		Assert.assertEquals(existingAssetTag.getUserName(),
			newAssetTag.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetTag.getCreateDate()),
			Time.getShortTimestamp(newAssetTag.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetTag.getModifiedDate()),
			Time.getShortTimestamp(newAssetTag.getModifiedDate()));
		Assert.assertEquals(existingAssetTag.getName(), newAssetTag.getName());
		Assert.assertEquals(existingAssetTag.getAssetCount(),
			newAssetTag.getAssetCount());
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
	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetTag newAssetTag = addAssetTag();

		AssetTag existingAssetTag = _persistence.findByPrimaryKey(newAssetTag.getPrimaryKey());

		Assert.assertEquals(existingAssetTag, newAssetTag);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchTagException");
		}
		catch (NoSuchTagException nsee) {
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

	protected OrderByComparator<AssetTag> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("AssetTag", "tagId", true,
			"groupId", true, "companyId", true, "userId", true, "userName",
			true, "createDate", true, "modifiedDate", true, "name", true,
			"assetCount", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetTag newAssetTag = addAssetTag();

		AssetTag existingAssetTag = _persistence.fetchByPrimaryKey(newAssetTag.getPrimaryKey());

		Assert.assertEquals(existingAssetTag, newAssetTag);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetTag missingAssetTag = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAssetTag);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		AssetTag newAssetTag1 = addAssetTag();
		AssetTag newAssetTag2 = addAssetTag();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetTag1.getPrimaryKey());
		primaryKeys.add(newAssetTag2.getPrimaryKey());

		Map<Serializable, AssetTag> assetTags = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, assetTags.size());
		Assert.assertEquals(newAssetTag1,
			assetTags.get(newAssetTag1.getPrimaryKey()));
		Assert.assertEquals(newAssetTag2,
			assetTags.get(newAssetTag2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AssetTag> assetTags = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetTags.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		AssetTag newAssetTag = addAssetTag();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetTag.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AssetTag> assetTags = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetTags.size());
		Assert.assertEquals(newAssetTag,
			assetTags.get(newAssetTag.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AssetTag> assetTags = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetTags.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		AssetTag newAssetTag = addAssetTag();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetTag.getPrimaryKey());

		Map<Serializable, AssetTag> assetTags = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetTags.size());
		Assert.assertEquals(newAssetTag,
			assetTags.get(newAssetTag.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = AssetTagLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					AssetTag assetTag = (AssetTag)object;

					Assert.assertNotNull(assetTag);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AssetTag newAssetTag = addAssetTag();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetTag.class,
				AssetTag.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("tagId",
				newAssetTag.getTagId()));

		List<AssetTag> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AssetTag existingAssetTag = result.get(0);

		Assert.assertEquals(existingAssetTag, newAssetTag);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetTag.class,
				AssetTag.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("tagId",
				RandomTestUtil.nextLong()));

		List<AssetTag> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		AssetTag newAssetTag = addAssetTag();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetTag.class,
				AssetTag.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("tagId"));

		Object newTagId = newAssetTag.getTagId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("tagId",
				new Object[] { newTagId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingTagId = result.get(0);

		Assert.assertEquals(existingTagId, newTagId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetTag.class,
				AssetTag.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("tagId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("tagId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		AssetTag newAssetTag = addAssetTag();

		_persistence.clearCache();

		AssetTagModelImpl existingAssetTagModelImpl = (AssetTagModelImpl)_persistence.findByPrimaryKey(newAssetTag.getPrimaryKey());

		Assert.assertEquals(existingAssetTagModelImpl.getGroupId(),
			existingAssetTagModelImpl.getOriginalGroupId());
		Assert.assertTrue(Validator.equals(
				existingAssetTagModelImpl.getName(),
				existingAssetTagModelImpl.getOriginalName()));
	}

	protected AssetTag addAssetTag() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetTag assetTag = _persistence.create(pk);

		assetTag.setGroupId(RandomTestUtil.nextLong());

		assetTag.setCompanyId(RandomTestUtil.nextLong());

		assetTag.setUserId(RandomTestUtil.nextLong());

		assetTag.setUserName(RandomTestUtil.randomString());

		assetTag.setCreateDate(RandomTestUtil.nextDate());

		assetTag.setModifiedDate(RandomTestUtil.nextDate());

		assetTag.setName(RandomTestUtil.randomString());

		assetTag.setAssetCount(RandomTestUtil.nextInt());

		_assetTags.add(_persistence.update(assetTag));

		return assetTag;
	}

	private static Log _log = LogFactoryUtil.getLog(AssetTagPersistenceTest.class);
	private List<AssetTag> _assetTags = new ArrayList<AssetTag>();
	private ModelListener<AssetTag>[] _modelListeners;
	private AssetTagPersistence _persistence = AssetTagUtil.getPersistence();
}