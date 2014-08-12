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

package com.liferay.portlet.softwarecatalog.service.persistence;

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

import com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException;
import com.liferay.portlet.softwarecatalog.model.SCProductScreenshot;
import com.liferay.portlet.softwarecatalog.model.impl.SCProductScreenshotModelImpl;
import com.liferay.portlet.softwarecatalog.service.SCProductScreenshotLocalServiceUtil;

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
public class SCProductScreenshotPersistenceTest {
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
		Iterator<SCProductScreenshot> iterator = _scProductScreenshots.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SCProductScreenshot scProductScreenshot = _persistence.create(pk);

		Assert.assertNotNull(scProductScreenshot);

		Assert.assertEquals(scProductScreenshot.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SCProductScreenshot newSCProductScreenshot = addSCProductScreenshot();

		_persistence.remove(newSCProductScreenshot);

		SCProductScreenshot existingSCProductScreenshot = _persistence.fetchByPrimaryKey(newSCProductScreenshot.getPrimaryKey());

		Assert.assertNull(existingSCProductScreenshot);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSCProductScreenshot();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SCProductScreenshot newSCProductScreenshot = _persistence.create(pk);

		newSCProductScreenshot.setCompanyId(RandomTestUtil.nextLong());

		newSCProductScreenshot.setGroupId(RandomTestUtil.nextLong());

		newSCProductScreenshot.setProductEntryId(RandomTestUtil.nextLong());

		newSCProductScreenshot.setThumbnailId(RandomTestUtil.nextLong());

		newSCProductScreenshot.setFullImageId(RandomTestUtil.nextLong());

		newSCProductScreenshot.setPriority(RandomTestUtil.nextInt());

		_scProductScreenshots.add(_persistence.update(newSCProductScreenshot));

		SCProductScreenshot existingSCProductScreenshot = _persistence.findByPrimaryKey(newSCProductScreenshot.getPrimaryKey());

		Assert.assertEquals(existingSCProductScreenshot.getProductScreenshotId(),
			newSCProductScreenshot.getProductScreenshotId());
		Assert.assertEquals(existingSCProductScreenshot.getCompanyId(),
			newSCProductScreenshot.getCompanyId());
		Assert.assertEquals(existingSCProductScreenshot.getGroupId(),
			newSCProductScreenshot.getGroupId());
		Assert.assertEquals(existingSCProductScreenshot.getProductEntryId(),
			newSCProductScreenshot.getProductEntryId());
		Assert.assertEquals(existingSCProductScreenshot.getThumbnailId(),
			newSCProductScreenshot.getThumbnailId());
		Assert.assertEquals(existingSCProductScreenshot.getFullImageId(),
			newSCProductScreenshot.getFullImageId());
		Assert.assertEquals(existingSCProductScreenshot.getPriority(),
			newSCProductScreenshot.getPriority());
	}

	@Test
	public void testCountByProductEntryId() {
		try {
			_persistence.countByProductEntryId(RandomTestUtil.nextLong());

			_persistence.countByProductEntryId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByThumbnailId() {
		try {
			_persistence.countByThumbnailId(RandomTestUtil.nextLong());

			_persistence.countByThumbnailId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByFullImageId() {
		try {
			_persistence.countByFullImageId(RandomTestUtil.nextLong());

			_persistence.countByFullImageId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByP_P() {
		try {
			_persistence.countByP_P(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByP_P(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SCProductScreenshot newSCProductScreenshot = addSCProductScreenshot();

		SCProductScreenshot existingSCProductScreenshot = _persistence.findByPrimaryKey(newSCProductScreenshot.getPrimaryKey());

		Assert.assertEquals(existingSCProductScreenshot, newSCProductScreenshot);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchProductScreenshotException");
		}
		catch (NoSuchProductScreenshotException nsee) {
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

	protected OrderByComparator<SCProductScreenshot> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("SCProductScreenshot",
			"productScreenshotId", true, "companyId", true, "groupId", true,
			"productEntryId", true, "thumbnailId", true, "fullImageId", true,
			"priority", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SCProductScreenshot newSCProductScreenshot = addSCProductScreenshot();

		SCProductScreenshot existingSCProductScreenshot = _persistence.fetchByPrimaryKey(newSCProductScreenshot.getPrimaryKey());

		Assert.assertEquals(existingSCProductScreenshot, newSCProductScreenshot);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SCProductScreenshot missingSCProductScreenshot = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSCProductScreenshot);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		SCProductScreenshot newSCProductScreenshot1 = addSCProductScreenshot();
		SCProductScreenshot newSCProductScreenshot2 = addSCProductScreenshot();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSCProductScreenshot1.getPrimaryKey());
		primaryKeys.add(newSCProductScreenshot2.getPrimaryKey());

		Map<Serializable, SCProductScreenshot> scProductScreenshots = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, scProductScreenshots.size());
		Assert.assertEquals(newSCProductScreenshot1,
			scProductScreenshots.get(newSCProductScreenshot1.getPrimaryKey()));
		Assert.assertEquals(newSCProductScreenshot2,
			scProductScreenshots.get(newSCProductScreenshot2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, SCProductScreenshot> scProductScreenshots = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(scProductScreenshots.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		SCProductScreenshot newSCProductScreenshot = addSCProductScreenshot();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSCProductScreenshot.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, SCProductScreenshot> scProductScreenshots = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, scProductScreenshots.size());
		Assert.assertEquals(newSCProductScreenshot,
			scProductScreenshots.get(newSCProductScreenshot.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, SCProductScreenshot> scProductScreenshots = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(scProductScreenshots.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		SCProductScreenshot newSCProductScreenshot = addSCProductScreenshot();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSCProductScreenshot.getPrimaryKey());

		Map<Serializable, SCProductScreenshot> scProductScreenshots = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, scProductScreenshots.size());
		Assert.assertEquals(newSCProductScreenshot,
			scProductScreenshots.get(newSCProductScreenshot.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = SCProductScreenshotLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					SCProductScreenshot scProductScreenshot = (SCProductScreenshot)object;

					Assert.assertNotNull(scProductScreenshot);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SCProductScreenshot newSCProductScreenshot = addSCProductScreenshot();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCProductScreenshot.class,
				SCProductScreenshot.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("productScreenshotId",
				newSCProductScreenshot.getProductScreenshotId()));

		List<SCProductScreenshot> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		SCProductScreenshot existingSCProductScreenshot = result.get(0);

		Assert.assertEquals(existingSCProductScreenshot, newSCProductScreenshot);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCProductScreenshot.class,
				SCProductScreenshot.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("productScreenshotId",
				RandomTestUtil.nextLong()));

		List<SCProductScreenshot> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SCProductScreenshot newSCProductScreenshot = addSCProductScreenshot();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCProductScreenshot.class,
				SCProductScreenshot.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"productScreenshotId"));

		Object newProductScreenshotId = newSCProductScreenshot.getProductScreenshotId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("productScreenshotId",
				new Object[] { newProductScreenshotId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingProductScreenshotId = result.get(0);

		Assert.assertEquals(existingProductScreenshotId, newProductScreenshotId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCProductScreenshot.class,
				SCProductScreenshot.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"productScreenshotId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("productScreenshotId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		SCProductScreenshot newSCProductScreenshot = addSCProductScreenshot();

		_persistence.clearCache();

		SCProductScreenshotModelImpl existingSCProductScreenshotModelImpl = (SCProductScreenshotModelImpl)_persistence.findByPrimaryKey(newSCProductScreenshot.getPrimaryKey());

		Assert.assertEquals(existingSCProductScreenshotModelImpl.getThumbnailId(),
			existingSCProductScreenshotModelImpl.getOriginalThumbnailId());

		Assert.assertEquals(existingSCProductScreenshotModelImpl.getFullImageId(),
			existingSCProductScreenshotModelImpl.getOriginalFullImageId());

		Assert.assertEquals(existingSCProductScreenshotModelImpl.getProductEntryId(),
			existingSCProductScreenshotModelImpl.getOriginalProductEntryId());
		Assert.assertEquals(existingSCProductScreenshotModelImpl.getPriority(),
			existingSCProductScreenshotModelImpl.getOriginalPriority());
	}

	protected SCProductScreenshot addSCProductScreenshot()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		SCProductScreenshot scProductScreenshot = _persistence.create(pk);

		scProductScreenshot.setCompanyId(RandomTestUtil.nextLong());

		scProductScreenshot.setGroupId(RandomTestUtil.nextLong());

		scProductScreenshot.setProductEntryId(RandomTestUtil.nextLong());

		scProductScreenshot.setThumbnailId(RandomTestUtil.nextLong());

		scProductScreenshot.setFullImageId(RandomTestUtil.nextLong());

		scProductScreenshot.setPriority(RandomTestUtil.nextInt());

		_scProductScreenshots.add(_persistence.update(scProductScreenshot));

		return scProductScreenshot;
	}

	private static Log _log = LogFactoryUtil.getLog(SCProductScreenshotPersistenceTest.class);
	private List<SCProductScreenshot> _scProductScreenshots = new ArrayList<SCProductScreenshot>();
	private ModelListener<SCProductScreenshot>[] _modelListeners;
	private SCProductScreenshotPersistence _persistence = SCProductScreenshotUtil.getPersistence();
}