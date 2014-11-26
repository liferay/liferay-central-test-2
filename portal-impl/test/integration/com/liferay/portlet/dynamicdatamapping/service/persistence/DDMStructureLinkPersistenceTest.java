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
import com.liferay.portal.kernel.test.AggregateTestRule;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.LiferayIntegrationTestRule;
import com.liferay.portal.test.PersistenceTestRule;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureLinkModelImpl;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLinkLocalServiceUtil;

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
public class DDMStructureLinkPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@After
	public void tearDown() throws Exception {
		Iterator<DDMStructureLink> iterator = _ddmStructureLinks.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMStructureLink ddmStructureLink = _persistence.create(pk);

		Assert.assertNotNull(ddmStructureLink);

		Assert.assertEquals(ddmStructureLink.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DDMStructureLink newDDMStructureLink = addDDMStructureLink();

		_persistence.remove(newDDMStructureLink);

		DDMStructureLink existingDDMStructureLink = _persistence.fetchByPrimaryKey(newDDMStructureLink.getPrimaryKey());

		Assert.assertNull(existingDDMStructureLink);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDDMStructureLink();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMStructureLink newDDMStructureLink = _persistence.create(pk);

		newDDMStructureLink.setClassNameId(RandomTestUtil.nextLong());

		newDDMStructureLink.setClassPK(RandomTestUtil.nextLong());

		newDDMStructureLink.setStructureId(RandomTestUtil.nextLong());

		_ddmStructureLinks.add(_persistence.update(newDDMStructureLink));

		DDMStructureLink existingDDMStructureLink = _persistence.findByPrimaryKey(newDDMStructureLink.getPrimaryKey());

		Assert.assertEquals(existingDDMStructureLink.getStructureLinkId(),
			newDDMStructureLink.getStructureLinkId());
		Assert.assertEquals(existingDDMStructureLink.getClassNameId(),
			newDDMStructureLink.getClassNameId());
		Assert.assertEquals(existingDDMStructureLink.getClassPK(),
			newDDMStructureLink.getClassPK());
		Assert.assertEquals(existingDDMStructureLink.getStructureId(),
			newDDMStructureLink.getStructureId());
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
	public void testCountByClassPK() {
		try {
			_persistence.countByClassPK(RandomTestUtil.nextLong());

			_persistence.countByClassPK(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByStructureId() {
		try {
			_persistence.countByStructureId(RandomTestUtil.nextLong());

			_persistence.countByStructureId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DDMStructureLink newDDMStructureLink = addDDMStructureLink();

		DDMStructureLink existingDDMStructureLink = _persistence.findByPrimaryKey(newDDMStructureLink.getPrimaryKey());

		Assert.assertEquals(existingDDMStructureLink, newDDMStructureLink);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchStructureLinkException");
		}
		catch (NoSuchStructureLinkException nsee) {
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

	protected OrderByComparator<DDMStructureLink> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("DDMStructureLink",
			"structureLinkId", true, "classNameId", true, "classPK", true,
			"structureId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDMStructureLink newDDMStructureLink = addDDMStructureLink();

		DDMStructureLink existingDDMStructureLink = _persistence.fetchByPrimaryKey(newDDMStructureLink.getPrimaryKey());

		Assert.assertEquals(existingDDMStructureLink, newDDMStructureLink);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMStructureLink missingDDMStructureLink = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDDMStructureLink);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		DDMStructureLink newDDMStructureLink1 = addDDMStructureLink();
		DDMStructureLink newDDMStructureLink2 = addDDMStructureLink();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDMStructureLink1.getPrimaryKey());
		primaryKeys.add(newDDMStructureLink2.getPrimaryKey());

		Map<Serializable, DDMStructureLink> ddmStructureLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, ddmStructureLinks.size());
		Assert.assertEquals(newDDMStructureLink1,
			ddmStructureLinks.get(newDDMStructureLink1.getPrimaryKey()));
		Assert.assertEquals(newDDMStructureLink2,
			ddmStructureLinks.get(newDDMStructureLink2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, DDMStructureLink> ddmStructureLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ddmStructureLinks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		DDMStructureLink newDDMStructureLink = addDDMStructureLink();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDMStructureLink.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, DDMStructureLink> ddmStructureLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ddmStructureLinks.size());
		Assert.assertEquals(newDDMStructureLink,
			ddmStructureLinks.get(newDDMStructureLink.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, DDMStructureLink> ddmStructureLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ddmStructureLinks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		DDMStructureLink newDDMStructureLink = addDDMStructureLink();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDMStructureLink.getPrimaryKey());

		Map<Serializable, DDMStructureLink> ddmStructureLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ddmStructureLinks.size());
		Assert.assertEquals(newDDMStructureLink,
			ddmStructureLinks.get(newDDMStructureLink.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = DDMStructureLinkLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					DDMStructureLink ddmStructureLink = (DDMStructureLink)object;

					Assert.assertNotNull(ddmStructureLink);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDMStructureLink newDDMStructureLink = addDDMStructureLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructureLink.class,
				DDMStructureLink.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("structureLinkId",
				newDDMStructureLink.getStructureLinkId()));

		List<DDMStructureLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DDMStructureLink existingDDMStructureLink = result.get(0);

		Assert.assertEquals(existingDDMStructureLink, newDDMStructureLink);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructureLink.class,
				DDMStructureLink.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("structureLinkId",
				RandomTestUtil.nextLong()));

		List<DDMStructureLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDMStructureLink newDDMStructureLink = addDDMStructureLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructureLink.class,
				DDMStructureLink.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"structureLinkId"));

		Object newStructureLinkId = newDDMStructureLink.getStructureLinkId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("structureLinkId",
				new Object[] { newStructureLinkId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingStructureLinkId = result.get(0);

		Assert.assertEquals(existingStructureLinkId, newStructureLinkId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructureLink.class,
				DDMStructureLink.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"structureLinkId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("structureLinkId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DDMStructureLink newDDMStructureLink = addDDMStructureLink();

		_persistence.clearCache();

		DDMStructureLinkModelImpl existingDDMStructureLinkModelImpl = (DDMStructureLinkModelImpl)_persistence.findByPrimaryKey(newDDMStructureLink.getPrimaryKey());

		Assert.assertEquals(existingDDMStructureLinkModelImpl.getClassPK(),
			existingDDMStructureLinkModelImpl.getOriginalClassPK());
	}

	protected DDMStructureLink addDDMStructureLink() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMStructureLink ddmStructureLink = _persistence.create(pk);

		ddmStructureLink.setClassNameId(RandomTestUtil.nextLong());

		ddmStructureLink.setClassPK(RandomTestUtil.nextLong());

		ddmStructureLink.setStructureId(RandomTestUtil.nextLong());

		_ddmStructureLinks.add(_persistence.update(ddmStructureLink));

		return ddmStructureLink;
	}

	private List<DDMStructureLink> _ddmStructureLinks = new ArrayList<DDMStructureLink>();
	private DDMStructureLinkPersistence _persistence = DDMStructureLinkUtil.getPersistence();
}