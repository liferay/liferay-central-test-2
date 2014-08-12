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
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.test.RandomTestUtil;

import com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;
import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;
import com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionLocalServiceUtil;

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
public class SCFrameworkVersionPersistenceTest {
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
		Iterator<SCFrameworkVersion> iterator = _scFrameworkVersions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SCFrameworkVersion scFrameworkVersion = _persistence.create(pk);

		Assert.assertNotNull(scFrameworkVersion);

		Assert.assertEquals(scFrameworkVersion.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SCFrameworkVersion newSCFrameworkVersion = addSCFrameworkVersion();

		_persistence.remove(newSCFrameworkVersion);

		SCFrameworkVersion existingSCFrameworkVersion = _persistence.fetchByPrimaryKey(newSCFrameworkVersion.getPrimaryKey());

		Assert.assertNull(existingSCFrameworkVersion);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSCFrameworkVersion();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SCFrameworkVersion newSCFrameworkVersion = _persistence.create(pk);

		newSCFrameworkVersion.setGroupId(RandomTestUtil.nextLong());

		newSCFrameworkVersion.setCompanyId(RandomTestUtil.nextLong());

		newSCFrameworkVersion.setUserId(RandomTestUtil.nextLong());

		newSCFrameworkVersion.setUserName(RandomTestUtil.randomString());

		newSCFrameworkVersion.setCreateDate(RandomTestUtil.nextDate());

		newSCFrameworkVersion.setModifiedDate(RandomTestUtil.nextDate());

		newSCFrameworkVersion.setName(RandomTestUtil.randomString());

		newSCFrameworkVersion.setUrl(RandomTestUtil.randomString());

		newSCFrameworkVersion.setActive(RandomTestUtil.randomBoolean());

		newSCFrameworkVersion.setPriority(RandomTestUtil.nextInt());

		_scFrameworkVersions.add(_persistence.update(newSCFrameworkVersion));

		SCFrameworkVersion existingSCFrameworkVersion = _persistence.findByPrimaryKey(newSCFrameworkVersion.getPrimaryKey());

		Assert.assertEquals(existingSCFrameworkVersion.getFrameworkVersionId(),
			newSCFrameworkVersion.getFrameworkVersionId());
		Assert.assertEquals(existingSCFrameworkVersion.getGroupId(),
			newSCFrameworkVersion.getGroupId());
		Assert.assertEquals(existingSCFrameworkVersion.getCompanyId(),
			newSCFrameworkVersion.getCompanyId());
		Assert.assertEquals(existingSCFrameworkVersion.getUserId(),
			newSCFrameworkVersion.getUserId());
		Assert.assertEquals(existingSCFrameworkVersion.getUserName(),
			newSCFrameworkVersion.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingSCFrameworkVersion.getCreateDate()),
			Time.getShortTimestamp(newSCFrameworkVersion.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingSCFrameworkVersion.getModifiedDate()),
			Time.getShortTimestamp(newSCFrameworkVersion.getModifiedDate()));
		Assert.assertEquals(existingSCFrameworkVersion.getName(),
			newSCFrameworkVersion.getName());
		Assert.assertEquals(existingSCFrameworkVersion.getUrl(),
			newSCFrameworkVersion.getUrl());
		Assert.assertEquals(existingSCFrameworkVersion.getActive(),
			newSCFrameworkVersion.getActive());
		Assert.assertEquals(existingSCFrameworkVersion.getPriority(),
			newSCFrameworkVersion.getPriority());
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
	public void testCountByG_A() {
		try {
			_persistence.countByG_A(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean());

			_persistence.countByG_A(0L, RandomTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SCFrameworkVersion newSCFrameworkVersion = addSCFrameworkVersion();

		SCFrameworkVersion existingSCFrameworkVersion = _persistence.findByPrimaryKey(newSCFrameworkVersion.getPrimaryKey());

		Assert.assertEquals(existingSCFrameworkVersion, newSCFrameworkVersion);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchFrameworkVersionException");
		}
		catch (NoSuchFrameworkVersionException nsee) {
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

	protected OrderByComparator<SCFrameworkVersion> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("SCFrameworkVersion",
			"frameworkVersionId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "name", true, "url", true, "active", true,
			"priority", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SCFrameworkVersion newSCFrameworkVersion = addSCFrameworkVersion();

		SCFrameworkVersion existingSCFrameworkVersion = _persistence.fetchByPrimaryKey(newSCFrameworkVersion.getPrimaryKey());

		Assert.assertEquals(existingSCFrameworkVersion, newSCFrameworkVersion);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SCFrameworkVersion missingSCFrameworkVersion = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSCFrameworkVersion);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		SCFrameworkVersion newSCFrameworkVersion1 = addSCFrameworkVersion();
		SCFrameworkVersion newSCFrameworkVersion2 = addSCFrameworkVersion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSCFrameworkVersion1.getPrimaryKey());
		primaryKeys.add(newSCFrameworkVersion2.getPrimaryKey());

		Map<Serializable, SCFrameworkVersion> scFrameworkVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, scFrameworkVersions.size());
		Assert.assertEquals(newSCFrameworkVersion1,
			scFrameworkVersions.get(newSCFrameworkVersion1.getPrimaryKey()));
		Assert.assertEquals(newSCFrameworkVersion2,
			scFrameworkVersions.get(newSCFrameworkVersion2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, SCFrameworkVersion> scFrameworkVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(scFrameworkVersions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		SCFrameworkVersion newSCFrameworkVersion = addSCFrameworkVersion();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSCFrameworkVersion.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, SCFrameworkVersion> scFrameworkVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, scFrameworkVersions.size());
		Assert.assertEquals(newSCFrameworkVersion,
			scFrameworkVersions.get(newSCFrameworkVersion.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, SCFrameworkVersion> scFrameworkVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(scFrameworkVersions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		SCFrameworkVersion newSCFrameworkVersion = addSCFrameworkVersion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSCFrameworkVersion.getPrimaryKey());

		Map<Serializable, SCFrameworkVersion> scFrameworkVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, scFrameworkVersions.size());
		Assert.assertEquals(newSCFrameworkVersion,
			scFrameworkVersions.get(newSCFrameworkVersion.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = SCFrameworkVersionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					SCFrameworkVersion scFrameworkVersion = (SCFrameworkVersion)object;

					Assert.assertNotNull(scFrameworkVersion);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SCFrameworkVersion newSCFrameworkVersion = addSCFrameworkVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCFrameworkVersion.class,
				SCFrameworkVersion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("frameworkVersionId",
				newSCFrameworkVersion.getFrameworkVersionId()));

		List<SCFrameworkVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		SCFrameworkVersion existingSCFrameworkVersion = result.get(0);

		Assert.assertEquals(existingSCFrameworkVersion, newSCFrameworkVersion);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCFrameworkVersion.class,
				SCFrameworkVersion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("frameworkVersionId",
				RandomTestUtil.nextLong()));

		List<SCFrameworkVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SCFrameworkVersion newSCFrameworkVersion = addSCFrameworkVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCFrameworkVersion.class,
				SCFrameworkVersion.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"frameworkVersionId"));

		Object newFrameworkVersionId = newSCFrameworkVersion.getFrameworkVersionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("frameworkVersionId",
				new Object[] { newFrameworkVersionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFrameworkVersionId = result.get(0);

		Assert.assertEquals(existingFrameworkVersionId, newFrameworkVersionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCFrameworkVersion.class,
				SCFrameworkVersion.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"frameworkVersionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("frameworkVersionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected SCFrameworkVersion addSCFrameworkVersion()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		SCFrameworkVersion scFrameworkVersion = _persistence.create(pk);

		scFrameworkVersion.setGroupId(RandomTestUtil.nextLong());

		scFrameworkVersion.setCompanyId(RandomTestUtil.nextLong());

		scFrameworkVersion.setUserId(RandomTestUtil.nextLong());

		scFrameworkVersion.setUserName(RandomTestUtil.randomString());

		scFrameworkVersion.setCreateDate(RandomTestUtil.nextDate());

		scFrameworkVersion.setModifiedDate(RandomTestUtil.nextDate());

		scFrameworkVersion.setName(RandomTestUtil.randomString());

		scFrameworkVersion.setUrl(RandomTestUtil.randomString());

		scFrameworkVersion.setActive(RandomTestUtil.randomBoolean());

		scFrameworkVersion.setPriority(RandomTestUtil.nextInt());

		_scFrameworkVersions.add(_persistence.update(scFrameworkVersion));

		return scFrameworkVersion;
	}

	private static Log _log = LogFactoryUtil.getLog(SCFrameworkVersionPersistenceTest.class);
	private List<SCFrameworkVersion> _scFrameworkVersions = new ArrayList<SCFrameworkVersion>();
	private ModelListener<SCFrameworkVersion>[] _modelListeners;
	private SCFrameworkVersionPersistence _persistence = SCFrameworkVersionUtil.getPersistence();
}