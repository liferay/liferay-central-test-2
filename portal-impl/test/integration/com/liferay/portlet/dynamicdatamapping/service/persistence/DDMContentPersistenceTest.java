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

package com.liferay.portlet.dynamicdatamapping.service.persistence;

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

import com.liferay.portlet.dynamicdatamapping.NoSuchContentException;
import com.liferay.portlet.dynamicdatamapping.model.DDMContent;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMContentModelImpl;
import com.liferay.portlet.dynamicdatamapping.service.DDMContentLocalServiceUtil;

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
public class DDMContentPersistenceTest {
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
		Iterator<DDMContent> iterator = _ddmContents.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMContent ddmContent = _persistence.create(pk);

		Assert.assertNotNull(ddmContent);

		Assert.assertEquals(ddmContent.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DDMContent newDDMContent = addDDMContent();

		_persistence.remove(newDDMContent);

		DDMContent existingDDMContent = _persistence.fetchByPrimaryKey(newDDMContent.getPrimaryKey());

		Assert.assertNull(existingDDMContent);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDDMContent();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMContent newDDMContent = _persistence.create(pk);

		newDDMContent.setUuid(RandomTestUtil.randomString());

		newDDMContent.setGroupId(RandomTestUtil.nextLong());

		newDDMContent.setCompanyId(RandomTestUtil.nextLong());

		newDDMContent.setUserId(RandomTestUtil.nextLong());

		newDDMContent.setUserName(RandomTestUtil.randomString());

		newDDMContent.setCreateDate(RandomTestUtil.nextDate());

		newDDMContent.setModifiedDate(RandomTestUtil.nextDate());

		newDDMContent.setName(RandomTestUtil.randomString());

		newDDMContent.setDescription(RandomTestUtil.randomString());

		newDDMContent.setData(RandomTestUtil.randomString());

		_ddmContents.add(_persistence.update(newDDMContent));

		DDMContent existingDDMContent = _persistence.findByPrimaryKey(newDDMContent.getPrimaryKey());

		Assert.assertEquals(existingDDMContent.getUuid(),
			newDDMContent.getUuid());
		Assert.assertEquals(existingDDMContent.getContentId(),
			newDDMContent.getContentId());
		Assert.assertEquals(existingDDMContent.getGroupId(),
			newDDMContent.getGroupId());
		Assert.assertEquals(existingDDMContent.getCompanyId(),
			newDDMContent.getCompanyId());
		Assert.assertEquals(existingDDMContent.getUserId(),
			newDDMContent.getUserId());
		Assert.assertEquals(existingDDMContent.getUserName(),
			newDDMContent.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDDMContent.getCreateDate()),
			Time.getShortTimestamp(newDDMContent.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingDDMContent.getModifiedDate()),
			Time.getShortTimestamp(newDDMContent.getModifiedDate()));
		Assert.assertEquals(existingDDMContent.getName(),
			newDDMContent.getName());
		Assert.assertEquals(existingDDMContent.getDescription(),
			newDDMContent.getDescription());
		Assert.assertEquals(existingDDMContent.getData(),
			newDDMContent.getData());
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
	public void testFindByPrimaryKeyExisting() throws Exception {
		DDMContent newDDMContent = addDDMContent();

		DDMContent existingDDMContent = _persistence.findByPrimaryKey(newDDMContent.getPrimaryKey());

		Assert.assertEquals(existingDDMContent, newDDMContent);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchContentException");
		}
		catch (NoSuchContentException nsee) {
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

	protected OrderByComparator<DDMContent> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("DDMContent", "uuid", true,
			"contentId", true, "groupId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"name", true, "description", true, "data", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDMContent newDDMContent = addDDMContent();

		DDMContent existingDDMContent = _persistence.fetchByPrimaryKey(newDDMContent.getPrimaryKey());

		Assert.assertEquals(existingDDMContent, newDDMContent);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMContent missingDDMContent = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDDMContent);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		DDMContent newDDMContent1 = addDDMContent();
		DDMContent newDDMContent2 = addDDMContent();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDMContent1.getPrimaryKey());
		primaryKeys.add(newDDMContent2.getPrimaryKey());

		Map<Serializable, DDMContent> ddmContents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, ddmContents.size());
		Assert.assertEquals(newDDMContent1,
			ddmContents.get(newDDMContent1.getPrimaryKey()));
		Assert.assertEquals(newDDMContent2,
			ddmContents.get(newDDMContent2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, DDMContent> ddmContents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ddmContents.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		DDMContent newDDMContent = addDDMContent();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDMContent.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, DDMContent> ddmContents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ddmContents.size());
		Assert.assertEquals(newDDMContent,
			ddmContents.get(newDDMContent.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, DDMContent> ddmContents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ddmContents.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		DDMContent newDDMContent = addDDMContent();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDMContent.getPrimaryKey());

		Map<Serializable, DDMContent> ddmContents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ddmContents.size());
		Assert.assertEquals(newDDMContent,
			ddmContents.get(newDDMContent.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = DDMContentLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					DDMContent ddmContent = (DDMContent)object;

					Assert.assertNotNull(ddmContent);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDMContent newDDMContent = addDDMContent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMContent.class,
				DDMContent.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("contentId",
				newDDMContent.getContentId()));

		List<DDMContent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DDMContent existingDDMContent = result.get(0);

		Assert.assertEquals(existingDDMContent, newDDMContent);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMContent.class,
				DDMContent.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("contentId",
				RandomTestUtil.nextLong()));

		List<DDMContent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDMContent newDDMContent = addDDMContent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMContent.class,
				DDMContent.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("contentId"));

		Object newContentId = newDDMContent.getContentId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("contentId",
				new Object[] { newContentId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingContentId = result.get(0);

		Assert.assertEquals(existingContentId, newContentId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMContent.class,
				DDMContent.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("contentId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("contentId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DDMContent newDDMContent = addDDMContent();

		_persistence.clearCache();

		DDMContentModelImpl existingDDMContentModelImpl = (DDMContentModelImpl)_persistence.findByPrimaryKey(newDDMContent.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingDDMContentModelImpl.getUuid(),
				existingDDMContentModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingDDMContentModelImpl.getGroupId(),
			existingDDMContentModelImpl.getOriginalGroupId());
	}

	protected DDMContent addDDMContent() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMContent ddmContent = _persistence.create(pk);

		ddmContent.setUuid(RandomTestUtil.randomString());

		ddmContent.setGroupId(RandomTestUtil.nextLong());

		ddmContent.setCompanyId(RandomTestUtil.nextLong());

		ddmContent.setUserId(RandomTestUtil.nextLong());

		ddmContent.setUserName(RandomTestUtil.randomString());

		ddmContent.setCreateDate(RandomTestUtil.nextDate());

		ddmContent.setModifiedDate(RandomTestUtil.nextDate());

		ddmContent.setName(RandomTestUtil.randomString());

		ddmContent.setDescription(RandomTestUtil.randomString());

		ddmContent.setData(RandomTestUtil.randomString());

		_ddmContents.add(_persistence.update(ddmContent));

		return ddmContent;
	}

	private static Log _log = LogFactoryUtil.getLog(DDMContentPersistenceTest.class);
	private List<DDMContent> _ddmContents = new ArrayList<DDMContent>();
	private ModelListener<DDMContent>[] _modelListeners;
	private DDMContentPersistence _persistence = DDMContentUtil.getPersistence();
}