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

package com.liferay.portlet.messageboards.service.persistence;

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

import com.liferay.portlet.messageboards.NoSuchCategoryException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;

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
public class MBCategoryPersistenceTest {
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
		Iterator<MBCategory> iterator = _mbCategories.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MBCategory mbCategory = _persistence.create(pk);

		Assert.assertNotNull(mbCategory);

		Assert.assertEquals(mbCategory.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		MBCategory newMBCategory = addMBCategory();

		_persistence.remove(newMBCategory);

		MBCategory existingMBCategory = _persistence.fetchByPrimaryKey(newMBCategory.getPrimaryKey());

		Assert.assertNull(existingMBCategory);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addMBCategory();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MBCategory newMBCategory = _persistence.create(pk);

		newMBCategory.setUuid(RandomTestUtil.randomString());

		newMBCategory.setGroupId(RandomTestUtil.nextLong());

		newMBCategory.setCompanyId(RandomTestUtil.nextLong());

		newMBCategory.setUserId(RandomTestUtil.nextLong());

		newMBCategory.setUserName(RandomTestUtil.randomString());

		newMBCategory.setCreateDate(RandomTestUtil.nextDate());

		newMBCategory.setModifiedDate(RandomTestUtil.nextDate());

		newMBCategory.setParentCategoryId(RandomTestUtil.nextLong());

		newMBCategory.setName(RandomTestUtil.randomString());

		newMBCategory.setDescription(RandomTestUtil.randomString());

		newMBCategory.setDisplayStyle(RandomTestUtil.randomString());

		newMBCategory.setThreadCount(RandomTestUtil.nextInt());

		newMBCategory.setMessageCount(RandomTestUtil.nextInt());

		newMBCategory.setLastPostDate(RandomTestUtil.nextDate());

		newMBCategory.setStatus(RandomTestUtil.nextInt());

		newMBCategory.setStatusByUserId(RandomTestUtil.nextLong());

		newMBCategory.setStatusByUserName(RandomTestUtil.randomString());

		newMBCategory.setStatusDate(RandomTestUtil.nextDate());

		_mbCategories.add(_persistence.update(newMBCategory));

		MBCategory existingMBCategory = _persistence.findByPrimaryKey(newMBCategory.getPrimaryKey());

		Assert.assertEquals(existingMBCategory.getUuid(),
			newMBCategory.getUuid());
		Assert.assertEquals(existingMBCategory.getCategoryId(),
			newMBCategory.getCategoryId());
		Assert.assertEquals(existingMBCategory.getGroupId(),
			newMBCategory.getGroupId());
		Assert.assertEquals(existingMBCategory.getCompanyId(),
			newMBCategory.getCompanyId());
		Assert.assertEquals(existingMBCategory.getUserId(),
			newMBCategory.getUserId());
		Assert.assertEquals(existingMBCategory.getUserName(),
			newMBCategory.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMBCategory.getCreateDate()),
			Time.getShortTimestamp(newMBCategory.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingMBCategory.getModifiedDate()),
			Time.getShortTimestamp(newMBCategory.getModifiedDate()));
		Assert.assertEquals(existingMBCategory.getParentCategoryId(),
			newMBCategory.getParentCategoryId());
		Assert.assertEquals(existingMBCategory.getName(),
			newMBCategory.getName());
		Assert.assertEquals(existingMBCategory.getDescription(),
			newMBCategory.getDescription());
		Assert.assertEquals(existingMBCategory.getDisplayStyle(),
			newMBCategory.getDisplayStyle());
		Assert.assertEquals(existingMBCategory.getThreadCount(),
			newMBCategory.getThreadCount());
		Assert.assertEquals(existingMBCategory.getMessageCount(),
			newMBCategory.getMessageCount());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMBCategory.getLastPostDate()),
			Time.getShortTimestamp(newMBCategory.getLastPostDate()));
		Assert.assertEquals(existingMBCategory.getStatus(),
			newMBCategory.getStatus());
		Assert.assertEquals(existingMBCategory.getStatusByUserId(),
			newMBCategory.getStatusByUserId());
		Assert.assertEquals(existingMBCategory.getStatusByUserName(),
			newMBCategory.getStatusByUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMBCategory.getStatusDate()),
			Time.getShortTimestamp(newMBCategory.getStatusDate()));
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
	public void testCountByG_P() {
		try {
			_persistence.countByG_P(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByG_P(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_PArrayable() {
		try {
			_persistence.countByG_P(RandomTestUtil.nextLong(),
				new long[] { RandomTestUtil.nextLong(), 0L });
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_S() {
		try {
			_persistence.countByG_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByG_S(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_S() {
		try {
			_persistence.countByC_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByC_S(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByNotC_G_P() {
		try {
			_persistence.countByNotC_G_P(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

			_persistence.countByNotC_G_P(0L, 0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByNotC_G_PArrayable() {
		try {
			_persistence.countByNotC_G_P(new long[] {
					RandomTestUtil.nextLong(), 0L
				}, RandomTestUtil.nextLong(),
				new long[] { RandomTestUtil.nextLong(), 0L });
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_P_S() {
		try {
			_persistence.countByG_P_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

			_persistence.countByG_P_S(0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_P_SArrayable() {
		try {
			_persistence.countByG_P_S(RandomTestUtil.nextLong(),
				new long[] { RandomTestUtil.nextLong(), 0L },
				RandomTestUtil.nextInt());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByNotC_G_P_S() {
		try {
			_persistence.countByNotC_G_P_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByNotC_G_P_S(0L, 0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByNotC_G_P_SArrayable() {
		try {
			_persistence.countByNotC_G_P_S(new long[] {
					RandomTestUtil.nextLong(), 0L
				}, RandomTestUtil.nextLong(),
				new long[] { RandomTestUtil.nextLong(), 0L },
				RandomTestUtil.nextInt());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		MBCategory newMBCategory = addMBCategory();

		MBCategory existingMBCategory = _persistence.findByPrimaryKey(newMBCategory.getPrimaryKey());

		Assert.assertEquals(existingMBCategory, newMBCategory);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchCategoryException");
		}
		catch (NoSuchCategoryException nsee) {
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

	protected OrderByComparator<MBCategory> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("MBCategory", "uuid", true,
			"categoryId", true, "groupId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"parentCategoryId", true, "name", true, "description", true,
			"displayStyle", true, "threadCount", true, "messageCount", true,
			"lastPostDate", true, "status", true, "statusByUserId", true,
			"statusByUserName", true, "statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		MBCategory newMBCategory = addMBCategory();

		MBCategory existingMBCategory = _persistence.fetchByPrimaryKey(newMBCategory.getPrimaryKey());

		Assert.assertEquals(existingMBCategory, newMBCategory);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MBCategory missingMBCategory = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingMBCategory);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		MBCategory newMBCategory1 = addMBCategory();
		MBCategory newMBCategory2 = addMBCategory();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMBCategory1.getPrimaryKey());
		primaryKeys.add(newMBCategory2.getPrimaryKey());

		Map<Serializable, MBCategory> mbCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, mbCategories.size());
		Assert.assertEquals(newMBCategory1,
			mbCategories.get(newMBCategory1.getPrimaryKey()));
		Assert.assertEquals(newMBCategory2,
			mbCategories.get(newMBCategory2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, MBCategory> mbCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(mbCategories.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		MBCategory newMBCategory = addMBCategory();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMBCategory.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, MBCategory> mbCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, mbCategories.size());
		Assert.assertEquals(newMBCategory,
			mbCategories.get(newMBCategory.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, MBCategory> mbCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(mbCategories.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		MBCategory newMBCategory = addMBCategory();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMBCategory.getPrimaryKey());

		Map<Serializable, MBCategory> mbCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, mbCategories.size());
		Assert.assertEquals(newMBCategory,
			mbCategories.get(newMBCategory.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = MBCategoryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					MBCategory mbCategory = (MBCategory)object;

					Assert.assertNotNull(mbCategory);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MBCategory newMBCategory = addMBCategory();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBCategory.class,
				MBCategory.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("categoryId",
				newMBCategory.getCategoryId()));

		List<MBCategory> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		MBCategory existingMBCategory = result.get(0);

		Assert.assertEquals(existingMBCategory, newMBCategory);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBCategory.class,
				MBCategory.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("categoryId",
				RandomTestUtil.nextLong()));

		List<MBCategory> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MBCategory newMBCategory = addMBCategory();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBCategory.class,
				MBCategory.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("categoryId"));

		Object newCategoryId = newMBCategory.getCategoryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("categoryId",
				new Object[] { newCategoryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCategoryId = result.get(0);

		Assert.assertEquals(existingCategoryId, newCategoryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBCategory.class,
				MBCategory.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("categoryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("categoryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		MBCategory newMBCategory = addMBCategory();

		_persistence.clearCache();

		MBCategoryModelImpl existingMBCategoryModelImpl = (MBCategoryModelImpl)_persistence.findByPrimaryKey(newMBCategory.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingMBCategoryModelImpl.getUuid(),
				existingMBCategoryModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingMBCategoryModelImpl.getGroupId(),
			existingMBCategoryModelImpl.getOriginalGroupId());
	}

	protected MBCategory addMBCategory() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MBCategory mbCategory = _persistence.create(pk);

		mbCategory.setUuid(RandomTestUtil.randomString());

		mbCategory.setGroupId(RandomTestUtil.nextLong());

		mbCategory.setCompanyId(RandomTestUtil.nextLong());

		mbCategory.setUserId(RandomTestUtil.nextLong());

		mbCategory.setUserName(RandomTestUtil.randomString());

		mbCategory.setCreateDate(RandomTestUtil.nextDate());

		mbCategory.setModifiedDate(RandomTestUtil.nextDate());

		mbCategory.setParentCategoryId(RandomTestUtil.nextLong());

		mbCategory.setName(RandomTestUtil.randomString());

		mbCategory.setDescription(RandomTestUtil.randomString());

		mbCategory.setDisplayStyle(RandomTestUtil.randomString());

		mbCategory.setThreadCount(RandomTestUtil.nextInt());

		mbCategory.setMessageCount(RandomTestUtil.nextInt());

		mbCategory.setLastPostDate(RandomTestUtil.nextDate());

		mbCategory.setStatus(RandomTestUtil.nextInt());

		mbCategory.setStatusByUserId(RandomTestUtil.nextLong());

		mbCategory.setStatusByUserName(RandomTestUtil.randomString());

		mbCategory.setStatusDate(RandomTestUtil.nextDate());

		_mbCategories.add(_persistence.update(mbCategory));

		return mbCategory;
	}

	private static Log _log = LogFactoryUtil.getLog(MBCategoryPersistenceTest.class);
	private List<MBCategory> _mbCategories = new ArrayList<MBCategory>();
	private ModelListener<MBCategory>[] _modelListeners;
	private MBCategoryPersistence _persistence = MBCategoryUtil.getPersistence();
}