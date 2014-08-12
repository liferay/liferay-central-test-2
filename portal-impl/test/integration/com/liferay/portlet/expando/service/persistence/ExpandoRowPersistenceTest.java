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

package com.liferay.portlet.expando.service.persistence;

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
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import com.liferay.portlet.expando.NoSuchRowException;
import com.liferay.portlet.expando.model.ExpandoRow;
import com.liferay.portlet.expando.model.impl.ExpandoRowModelImpl;
import com.liferay.portlet.expando.service.ExpandoRowLocalServiceUtil;

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
public class ExpandoRowPersistenceTest {
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
		Iterator<ExpandoRow> iterator = _expandoRows.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ExpandoRow expandoRow = _persistence.create(pk);

		Assert.assertNotNull(expandoRow);

		Assert.assertEquals(expandoRow.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ExpandoRow newExpandoRow = addExpandoRow();

		_persistence.remove(newExpandoRow);

		ExpandoRow existingExpandoRow = _persistence.fetchByPrimaryKey(newExpandoRow.getPrimaryKey());

		Assert.assertNull(existingExpandoRow);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addExpandoRow();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ExpandoRow newExpandoRow = _persistence.create(pk);

		newExpandoRow.setCompanyId(RandomTestUtil.nextLong());

		newExpandoRow.setModifiedDate(RandomTestUtil.nextDate());

		newExpandoRow.setTableId(RandomTestUtil.nextLong());

		newExpandoRow.setClassPK(RandomTestUtil.nextLong());

		_expandoRows.add(_persistence.update(newExpandoRow));

		ExpandoRow existingExpandoRow = _persistence.findByPrimaryKey(newExpandoRow.getPrimaryKey());

		Assert.assertEquals(existingExpandoRow.getRowId(),
			newExpandoRow.getRowId());
		Assert.assertEquals(existingExpandoRow.getCompanyId(),
			newExpandoRow.getCompanyId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingExpandoRow.getModifiedDate()),
			Time.getShortTimestamp(newExpandoRow.getModifiedDate()));
		Assert.assertEquals(existingExpandoRow.getTableId(),
			newExpandoRow.getTableId());
		Assert.assertEquals(existingExpandoRow.getClassPK(),
			newExpandoRow.getClassPK());
	}

	@Test
	public void testCountByTableId() {
		try {
			_persistence.countByTableId(RandomTestUtil.nextLong());

			_persistence.countByTableId(0L);
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
	public void testCountByT_C() {
		try {
			_persistence.countByT_C(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByT_C(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ExpandoRow newExpandoRow = addExpandoRow();

		ExpandoRow existingExpandoRow = _persistence.findByPrimaryKey(newExpandoRow.getPrimaryKey());

		Assert.assertEquals(existingExpandoRow, newExpandoRow);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchRowException");
		}
		catch (NoSuchRowException nsee) {
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

	protected OrderByComparator<ExpandoRow> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("ExpandoRow", "rowId", true,
			"companyId", true, "modifiedDate", true, "tableId", true,
			"classPK", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ExpandoRow newExpandoRow = addExpandoRow();

		ExpandoRow existingExpandoRow = _persistence.fetchByPrimaryKey(newExpandoRow.getPrimaryKey());

		Assert.assertEquals(existingExpandoRow, newExpandoRow);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ExpandoRow missingExpandoRow = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingExpandoRow);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		ExpandoRow newExpandoRow1 = addExpandoRow();
		ExpandoRow newExpandoRow2 = addExpandoRow();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newExpandoRow1.getPrimaryKey());
		primaryKeys.add(newExpandoRow2.getPrimaryKey());

		Map<Serializable, ExpandoRow> expandoRows = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, expandoRows.size());
		Assert.assertEquals(newExpandoRow1,
			expandoRows.get(newExpandoRow1.getPrimaryKey()));
		Assert.assertEquals(newExpandoRow2,
			expandoRows.get(newExpandoRow2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ExpandoRow> expandoRows = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(expandoRows.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		ExpandoRow newExpandoRow = addExpandoRow();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newExpandoRow.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ExpandoRow> expandoRows = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, expandoRows.size());
		Assert.assertEquals(newExpandoRow,
			expandoRows.get(newExpandoRow.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ExpandoRow> expandoRows = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(expandoRows.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		ExpandoRow newExpandoRow = addExpandoRow();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newExpandoRow.getPrimaryKey());

		Map<Serializable, ExpandoRow> expandoRows = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, expandoRows.size());
		Assert.assertEquals(newExpandoRow,
			expandoRows.get(newExpandoRow.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ExpandoRowLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					ExpandoRow expandoRow = (ExpandoRow)object;

					Assert.assertNotNull(expandoRow);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ExpandoRow newExpandoRow = addExpandoRow();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoRow.class,
				ExpandoRow.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("rowId",
				newExpandoRow.getRowId()));

		List<ExpandoRow> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ExpandoRow existingExpandoRow = result.get(0);

		Assert.assertEquals(existingExpandoRow, newExpandoRow);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoRow.class,
				ExpandoRow.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("rowId",
				RandomTestUtil.nextLong()));

		List<ExpandoRow> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ExpandoRow newExpandoRow = addExpandoRow();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoRow.class,
				ExpandoRow.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("rowId"));

		Object newRowId = newExpandoRow.getRowId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("rowId",
				new Object[] { newRowId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingRowId = result.get(0);

		Assert.assertEquals(existingRowId, newRowId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoRow.class,
				ExpandoRow.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("rowId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("rowId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ExpandoRow newExpandoRow = addExpandoRow();

		_persistence.clearCache();

		ExpandoRowModelImpl existingExpandoRowModelImpl = (ExpandoRowModelImpl)_persistence.findByPrimaryKey(newExpandoRow.getPrimaryKey());

		Assert.assertEquals(existingExpandoRowModelImpl.getTableId(),
			existingExpandoRowModelImpl.getOriginalTableId());
		Assert.assertEquals(existingExpandoRowModelImpl.getClassPK(),
			existingExpandoRowModelImpl.getOriginalClassPK());
	}

	protected ExpandoRow addExpandoRow() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ExpandoRow expandoRow = _persistence.create(pk);

		expandoRow.setCompanyId(RandomTestUtil.nextLong());

		expandoRow.setModifiedDate(RandomTestUtil.nextDate());

		expandoRow.setTableId(RandomTestUtil.nextLong());

		expandoRow.setClassPK(RandomTestUtil.nextLong());

		_expandoRows.add(_persistence.update(expandoRow));

		return expandoRow;
	}

	private static Log _log = LogFactoryUtil.getLog(ExpandoRowPersistenceTest.class);
	private List<ExpandoRow> _expandoRows = new ArrayList<ExpandoRow>();
	private ModelListener<ExpandoRow>[] _modelListeners;
	private ExpandoRowPersistence _persistence = ExpandoRowUtil.getPersistence();
}