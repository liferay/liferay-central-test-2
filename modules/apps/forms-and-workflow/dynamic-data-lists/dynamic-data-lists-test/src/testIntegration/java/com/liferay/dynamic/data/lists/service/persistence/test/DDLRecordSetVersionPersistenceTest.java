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

package com.liferay.dynamic.data.lists.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.dynamic.data.lists.exception.NoSuchRecordSetVersionException;
import com.liferay.dynamic.data.lists.model.DDLRecordSetVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordSetVersionLocalServiceUtil;
import com.liferay.dynamic.data.lists.service.persistence.DDLRecordSetVersionPersistence;
import com.liferay.dynamic.data.lists.service.persistence.DDLRecordSetVersionUtil;

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
public class DDLRecordSetVersionPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.dynamic.data.lists.service"));

	@Before
	public void setUp() {
		_persistence = DDLRecordSetVersionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<DDLRecordSetVersion> iterator = _ddlRecordSetVersions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDLRecordSetVersion ddlRecordSetVersion = _persistence.create(pk);

		Assert.assertNotNull(ddlRecordSetVersion);

		Assert.assertEquals(ddlRecordSetVersion.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DDLRecordSetVersion newDDLRecordSetVersion = addDDLRecordSetVersion();

		_persistence.remove(newDDLRecordSetVersion);

		DDLRecordSetVersion existingDDLRecordSetVersion = _persistence.fetchByPrimaryKey(newDDLRecordSetVersion.getPrimaryKey());

		Assert.assertNull(existingDDLRecordSetVersion);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDDLRecordSetVersion();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDLRecordSetVersion newDDLRecordSetVersion = _persistence.create(pk);

		newDDLRecordSetVersion.setGroupId(RandomTestUtil.nextLong());

		newDDLRecordSetVersion.setCompanyId(RandomTestUtil.nextLong());

		newDDLRecordSetVersion.setUserId(RandomTestUtil.nextLong());

		newDDLRecordSetVersion.setUserName(RandomTestUtil.randomString());

		newDDLRecordSetVersion.setCreateDate(RandomTestUtil.nextDate());

		newDDLRecordSetVersion.setRecordSetId(RandomTestUtil.nextLong());

		newDDLRecordSetVersion.setDDMStructureVersionId(RandomTestUtil.nextLong());

		newDDLRecordSetVersion.setName(RandomTestUtil.randomString());

		newDDLRecordSetVersion.setDescription(RandomTestUtil.randomString());

		newDDLRecordSetVersion.setSettings(RandomTestUtil.randomString());

		newDDLRecordSetVersion.setVersion(RandomTestUtil.randomString());

		newDDLRecordSetVersion.setStatus(RandomTestUtil.nextInt());

		newDDLRecordSetVersion.setStatusByUserId(RandomTestUtil.nextLong());

		newDDLRecordSetVersion.setStatusByUserName(RandomTestUtil.randomString());

		newDDLRecordSetVersion.setStatusDate(RandomTestUtil.nextDate());

		_ddlRecordSetVersions.add(_persistence.update(newDDLRecordSetVersion));

		DDLRecordSetVersion existingDDLRecordSetVersion = _persistence.findByPrimaryKey(newDDLRecordSetVersion.getPrimaryKey());

		Assert.assertEquals(existingDDLRecordSetVersion.getRecordSetVersionId(),
			newDDLRecordSetVersion.getRecordSetVersionId());
		Assert.assertEquals(existingDDLRecordSetVersion.getGroupId(),
			newDDLRecordSetVersion.getGroupId());
		Assert.assertEquals(existingDDLRecordSetVersion.getCompanyId(),
			newDDLRecordSetVersion.getCompanyId());
		Assert.assertEquals(existingDDLRecordSetVersion.getUserId(),
			newDDLRecordSetVersion.getUserId());
		Assert.assertEquals(existingDDLRecordSetVersion.getUserName(),
			newDDLRecordSetVersion.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDDLRecordSetVersion.getCreateDate()),
			Time.getShortTimestamp(newDDLRecordSetVersion.getCreateDate()));
		Assert.assertEquals(existingDDLRecordSetVersion.getRecordSetId(),
			newDDLRecordSetVersion.getRecordSetId());
		Assert.assertEquals(existingDDLRecordSetVersion.getDDMStructureVersionId(),
			newDDLRecordSetVersion.getDDMStructureVersionId());
		Assert.assertEquals(existingDDLRecordSetVersion.getName(),
			newDDLRecordSetVersion.getName());
		Assert.assertEquals(existingDDLRecordSetVersion.getDescription(),
			newDDLRecordSetVersion.getDescription());
		Assert.assertEquals(existingDDLRecordSetVersion.getSettings(),
			newDDLRecordSetVersion.getSettings());
		Assert.assertEquals(existingDDLRecordSetVersion.getVersion(),
			newDDLRecordSetVersion.getVersion());
		Assert.assertEquals(existingDDLRecordSetVersion.getStatus(),
			newDDLRecordSetVersion.getStatus());
		Assert.assertEquals(existingDDLRecordSetVersion.getStatusByUserId(),
			newDDLRecordSetVersion.getStatusByUserId());
		Assert.assertEquals(existingDDLRecordSetVersion.getStatusByUserName(),
			newDDLRecordSetVersion.getStatusByUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDDLRecordSetVersion.getStatusDate()),
			Time.getShortTimestamp(newDDLRecordSetVersion.getStatusDate()));
	}

	@Test
	public void testCountByRecordSetId() throws Exception {
		_persistence.countByRecordSetId(RandomTestUtil.nextLong());

		_persistence.countByRecordSetId(0L);
	}

	@Test
	public void testCountByRS_V() throws Exception {
		_persistence.countByRS_V(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByRS_V(0L, StringPool.NULL);

		_persistence.countByRS_V(0L, (String)null);
	}

	@Test
	public void testCountByRS_S() throws Exception {
		_persistence.countByRS_S(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByRS_S(0L, 0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DDLRecordSetVersion newDDLRecordSetVersion = addDDLRecordSetVersion();

		DDLRecordSetVersion existingDDLRecordSetVersion = _persistence.findByPrimaryKey(newDDLRecordSetVersion.getPrimaryKey());

		Assert.assertEquals(existingDDLRecordSetVersion, newDDLRecordSetVersion);
	}

	@Test(expected = NoSuchRecordSetVersionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<DDLRecordSetVersion> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("DDLRecordSetVersion",
			"recordSetVersionId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"recordSetId", true, "DDMStructureVersionId", true, "name", true,
			"description", true, "version", true, "status", true,
			"statusByUserId", true, "statusByUserName", true, "statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDLRecordSetVersion newDDLRecordSetVersion = addDDLRecordSetVersion();

		DDLRecordSetVersion existingDDLRecordSetVersion = _persistence.fetchByPrimaryKey(newDDLRecordSetVersion.getPrimaryKey());

		Assert.assertEquals(existingDDLRecordSetVersion, newDDLRecordSetVersion);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDLRecordSetVersion missingDDLRecordSetVersion = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDDLRecordSetVersion);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		DDLRecordSetVersion newDDLRecordSetVersion1 = addDDLRecordSetVersion();
		DDLRecordSetVersion newDDLRecordSetVersion2 = addDDLRecordSetVersion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDLRecordSetVersion1.getPrimaryKey());
		primaryKeys.add(newDDLRecordSetVersion2.getPrimaryKey());

		Map<Serializable, DDLRecordSetVersion> ddlRecordSetVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, ddlRecordSetVersions.size());
		Assert.assertEquals(newDDLRecordSetVersion1,
			ddlRecordSetVersions.get(newDDLRecordSetVersion1.getPrimaryKey()));
		Assert.assertEquals(newDDLRecordSetVersion2,
			ddlRecordSetVersions.get(newDDLRecordSetVersion2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, DDLRecordSetVersion> ddlRecordSetVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ddlRecordSetVersions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		DDLRecordSetVersion newDDLRecordSetVersion = addDDLRecordSetVersion();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDLRecordSetVersion.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, DDLRecordSetVersion> ddlRecordSetVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ddlRecordSetVersions.size());
		Assert.assertEquals(newDDLRecordSetVersion,
			ddlRecordSetVersions.get(newDDLRecordSetVersion.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, DDLRecordSetVersion> ddlRecordSetVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ddlRecordSetVersions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		DDLRecordSetVersion newDDLRecordSetVersion = addDDLRecordSetVersion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDLRecordSetVersion.getPrimaryKey());

		Map<Serializable, DDLRecordSetVersion> ddlRecordSetVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ddlRecordSetVersions.size());
		Assert.assertEquals(newDDLRecordSetVersion,
			ddlRecordSetVersions.get(newDDLRecordSetVersion.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = DDLRecordSetVersionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<DDLRecordSetVersion>() {
				@Override
				public void performAction(
					DDLRecordSetVersion ddlRecordSetVersion) {
					Assert.assertNotNull(ddlRecordSetVersion);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDLRecordSetVersion newDDLRecordSetVersion = addDDLRecordSetVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecordSetVersion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("recordSetVersionId",
				newDDLRecordSetVersion.getRecordSetVersionId()));

		List<DDLRecordSetVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DDLRecordSetVersion existingDDLRecordSetVersion = result.get(0);

		Assert.assertEquals(existingDDLRecordSetVersion, newDDLRecordSetVersion);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecordSetVersion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("recordSetVersionId",
				RandomTestUtil.nextLong()));

		List<DDLRecordSetVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDLRecordSetVersion newDDLRecordSetVersion = addDDLRecordSetVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecordSetVersion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"recordSetVersionId"));

		Object newRecordSetVersionId = newDDLRecordSetVersion.getRecordSetVersionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("recordSetVersionId",
				new Object[] { newRecordSetVersionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingRecordSetVersionId = result.get(0);

		Assert.assertEquals(existingRecordSetVersionId, newRecordSetVersionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecordSetVersion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"recordSetVersionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("recordSetVersionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		DDLRecordSetVersion newDDLRecordSetVersion = addDDLRecordSetVersion();

		_persistence.clearCache();

		DDLRecordSetVersion existingDDLRecordSetVersion = _persistence.findByPrimaryKey(newDDLRecordSetVersion.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingDDLRecordSetVersion.getRecordSetId()),
			ReflectionTestUtil.<Long>invoke(existingDDLRecordSetVersion,
				"getOriginalRecordSetId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(
				existingDDLRecordSetVersion.getVersion(),
				ReflectionTestUtil.invoke(existingDDLRecordSetVersion,
					"getOriginalVersion", new Class<?>[0])));
	}

	protected DDLRecordSetVersion addDDLRecordSetVersion()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDLRecordSetVersion ddlRecordSetVersion = _persistence.create(pk);

		ddlRecordSetVersion.setGroupId(RandomTestUtil.nextLong());

		ddlRecordSetVersion.setCompanyId(RandomTestUtil.nextLong());

		ddlRecordSetVersion.setUserId(RandomTestUtil.nextLong());

		ddlRecordSetVersion.setUserName(RandomTestUtil.randomString());

		ddlRecordSetVersion.setCreateDate(RandomTestUtil.nextDate());

		ddlRecordSetVersion.setRecordSetId(RandomTestUtil.nextLong());

		ddlRecordSetVersion.setDDMStructureVersionId(RandomTestUtil.nextLong());

		ddlRecordSetVersion.setName(RandomTestUtil.randomString());

		ddlRecordSetVersion.setDescription(RandomTestUtil.randomString());

		ddlRecordSetVersion.setSettings(RandomTestUtil.randomString());

		ddlRecordSetVersion.setVersion(RandomTestUtil.randomString());

		ddlRecordSetVersion.setStatus(RandomTestUtil.nextInt());

		ddlRecordSetVersion.setStatusByUserId(RandomTestUtil.nextLong());

		ddlRecordSetVersion.setStatusByUserName(RandomTestUtil.randomString());

		ddlRecordSetVersion.setStatusDate(RandomTestUtil.nextDate());

		_ddlRecordSetVersions.add(_persistence.update(ddlRecordSetVersion));

		return ddlRecordSetVersion;
	}

	private List<DDLRecordSetVersion> _ddlRecordSetVersions = new ArrayList<DDLRecordSetVersion>();
	private DDLRecordSetVersionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}