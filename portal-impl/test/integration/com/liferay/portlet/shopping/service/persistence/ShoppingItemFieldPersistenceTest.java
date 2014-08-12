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

package com.liferay.portlet.shopping.service.persistence;

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
import com.liferay.portal.util.test.RandomTestUtil;

import com.liferay.portlet.shopping.NoSuchItemFieldException;
import com.liferay.portlet.shopping.model.ShoppingItemField;
import com.liferay.portlet.shopping.service.ShoppingItemFieldLocalServiceUtil;

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
public class ShoppingItemFieldPersistenceTest {
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
		Iterator<ShoppingItemField> iterator = _shoppingItemFields.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingItemField shoppingItemField = _persistence.create(pk);

		Assert.assertNotNull(shoppingItemField);

		Assert.assertEquals(shoppingItemField.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ShoppingItemField newShoppingItemField = addShoppingItemField();

		_persistence.remove(newShoppingItemField);

		ShoppingItemField existingShoppingItemField = _persistence.fetchByPrimaryKey(newShoppingItemField.getPrimaryKey());

		Assert.assertNull(existingShoppingItemField);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addShoppingItemField();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingItemField newShoppingItemField = _persistence.create(pk);

		newShoppingItemField.setItemId(RandomTestUtil.nextLong());

		newShoppingItemField.setName(RandomTestUtil.randomString());

		newShoppingItemField.setValues(RandomTestUtil.randomString());

		newShoppingItemField.setDescription(RandomTestUtil.randomString());

		_shoppingItemFields.add(_persistence.update(newShoppingItemField));

		ShoppingItemField existingShoppingItemField = _persistence.findByPrimaryKey(newShoppingItemField.getPrimaryKey());

		Assert.assertEquals(existingShoppingItemField.getItemFieldId(),
			newShoppingItemField.getItemFieldId());
		Assert.assertEquals(existingShoppingItemField.getItemId(),
			newShoppingItemField.getItemId());
		Assert.assertEquals(existingShoppingItemField.getName(),
			newShoppingItemField.getName());
		Assert.assertEquals(existingShoppingItemField.getValues(),
			newShoppingItemField.getValues());
		Assert.assertEquals(existingShoppingItemField.getDescription(),
			newShoppingItemField.getDescription());
	}

	@Test
	public void testCountByItemId() {
		try {
			_persistence.countByItemId(RandomTestUtil.nextLong());

			_persistence.countByItemId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ShoppingItemField newShoppingItemField = addShoppingItemField();

		ShoppingItemField existingShoppingItemField = _persistence.findByPrimaryKey(newShoppingItemField.getPrimaryKey());

		Assert.assertEquals(existingShoppingItemField, newShoppingItemField);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchItemFieldException");
		}
		catch (NoSuchItemFieldException nsee) {
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

	protected OrderByComparator<ShoppingItemField> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("ShoppingItemField",
			"itemFieldId", true, "itemId", true, "name", true, "values", true,
			"description", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ShoppingItemField newShoppingItemField = addShoppingItemField();

		ShoppingItemField existingShoppingItemField = _persistence.fetchByPrimaryKey(newShoppingItemField.getPrimaryKey());

		Assert.assertEquals(existingShoppingItemField, newShoppingItemField);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingItemField missingShoppingItemField = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingShoppingItemField);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		ShoppingItemField newShoppingItemField1 = addShoppingItemField();
		ShoppingItemField newShoppingItemField2 = addShoppingItemField();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newShoppingItemField1.getPrimaryKey());
		primaryKeys.add(newShoppingItemField2.getPrimaryKey());

		Map<Serializable, ShoppingItemField> shoppingItemFields = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, shoppingItemFields.size());
		Assert.assertEquals(newShoppingItemField1,
			shoppingItemFields.get(newShoppingItemField1.getPrimaryKey()));
		Assert.assertEquals(newShoppingItemField2,
			shoppingItemFields.get(newShoppingItemField2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ShoppingItemField> shoppingItemFields = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(shoppingItemFields.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		ShoppingItemField newShoppingItemField = addShoppingItemField();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newShoppingItemField.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ShoppingItemField> shoppingItemFields = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, shoppingItemFields.size());
		Assert.assertEquals(newShoppingItemField,
			shoppingItemFields.get(newShoppingItemField.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ShoppingItemField> shoppingItemFields = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(shoppingItemFields.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		ShoppingItemField newShoppingItemField = addShoppingItemField();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newShoppingItemField.getPrimaryKey());

		Map<Serializable, ShoppingItemField> shoppingItemFields = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, shoppingItemFields.size());
		Assert.assertEquals(newShoppingItemField,
			shoppingItemFields.get(newShoppingItemField.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ShoppingItemFieldLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					ShoppingItemField shoppingItemField = (ShoppingItemField)object;

					Assert.assertNotNull(shoppingItemField);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ShoppingItemField newShoppingItemField = addShoppingItemField();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingItemField.class,
				ShoppingItemField.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("itemFieldId",
				newShoppingItemField.getItemFieldId()));

		List<ShoppingItemField> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ShoppingItemField existingShoppingItemField = result.get(0);

		Assert.assertEquals(existingShoppingItemField, newShoppingItemField);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingItemField.class,
				ShoppingItemField.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("itemFieldId",
				RandomTestUtil.nextLong()));

		List<ShoppingItemField> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ShoppingItemField newShoppingItemField = addShoppingItemField();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingItemField.class,
				ShoppingItemField.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("itemFieldId"));

		Object newItemFieldId = newShoppingItemField.getItemFieldId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("itemFieldId",
				new Object[] { newItemFieldId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingItemFieldId = result.get(0);

		Assert.assertEquals(existingItemFieldId, newItemFieldId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingItemField.class,
				ShoppingItemField.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("itemFieldId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("itemFieldId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected ShoppingItemField addShoppingItemField()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingItemField shoppingItemField = _persistence.create(pk);

		shoppingItemField.setItemId(RandomTestUtil.nextLong());

		shoppingItemField.setName(RandomTestUtil.randomString());

		shoppingItemField.setValues(RandomTestUtil.randomString());

		shoppingItemField.setDescription(RandomTestUtil.randomString());

		_shoppingItemFields.add(_persistence.update(shoppingItemField));

		return shoppingItemField;
	}

	private static Log _log = LogFactoryUtil.getLog(ShoppingItemFieldPersistenceTest.class);
	private List<ShoppingItemField> _shoppingItemFields = new ArrayList<ShoppingItemField>();
	private ModelListener<ShoppingItemField>[] _modelListeners;
	private ShoppingItemFieldPersistence _persistence = ShoppingItemFieldUtil.getPersistence();
}