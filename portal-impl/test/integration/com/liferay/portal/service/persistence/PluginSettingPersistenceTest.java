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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchPluginSettingException;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.PluginSetting;
import com.liferay.portal.model.impl.PluginSettingModelImpl;
import com.liferay.portal.service.PluginSettingLocalServiceUtil;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

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
public class PluginSettingPersistenceTest {
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
		Iterator<PluginSetting> iterator = _pluginSettings.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PluginSetting pluginSetting = _persistence.create(pk);

		Assert.assertNotNull(pluginSetting);

		Assert.assertEquals(pluginSetting.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		PluginSetting newPluginSetting = addPluginSetting();

		_persistence.remove(newPluginSetting);

		PluginSetting existingPluginSetting = _persistence.fetchByPrimaryKey(newPluginSetting.getPrimaryKey());

		Assert.assertNull(existingPluginSetting);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addPluginSetting();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PluginSetting newPluginSetting = _persistence.create(pk);

		newPluginSetting.setMvccVersion(RandomTestUtil.nextLong());

		newPluginSetting.setCompanyId(RandomTestUtil.nextLong());

		newPluginSetting.setPluginId(RandomTestUtil.randomString());

		newPluginSetting.setPluginType(RandomTestUtil.randomString());

		newPluginSetting.setRoles(RandomTestUtil.randomString());

		newPluginSetting.setActive(RandomTestUtil.randomBoolean());

		_pluginSettings.add(_persistence.update(newPluginSetting));

		PluginSetting existingPluginSetting = _persistence.findByPrimaryKey(newPluginSetting.getPrimaryKey());

		Assert.assertEquals(existingPluginSetting.getMvccVersion(),
			newPluginSetting.getMvccVersion());
		Assert.assertEquals(existingPluginSetting.getPluginSettingId(),
			newPluginSetting.getPluginSettingId());
		Assert.assertEquals(existingPluginSetting.getCompanyId(),
			newPluginSetting.getCompanyId());
		Assert.assertEquals(existingPluginSetting.getPluginId(),
			newPluginSetting.getPluginId());
		Assert.assertEquals(existingPluginSetting.getPluginType(),
			newPluginSetting.getPluginType());
		Assert.assertEquals(existingPluginSetting.getRoles(),
			newPluginSetting.getRoles());
		Assert.assertEquals(existingPluginSetting.getActive(),
			newPluginSetting.getActive());
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
	public void testCountByC_I_T() {
		try {
			_persistence.countByC_I_T(RandomTestUtil.nextLong(),
				StringPool.BLANK, StringPool.BLANK);

			_persistence.countByC_I_T(0L, StringPool.NULL, StringPool.NULL);

			_persistence.countByC_I_T(0L, (String)null, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		PluginSetting newPluginSetting = addPluginSetting();

		PluginSetting existingPluginSetting = _persistence.findByPrimaryKey(newPluginSetting.getPrimaryKey());

		Assert.assertEquals(existingPluginSetting, newPluginSetting);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchPluginSettingException");
		}
		catch (NoSuchPluginSettingException nsee) {
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

	protected OrderByComparator<PluginSetting> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("PluginSetting",
			"mvccVersion", true, "pluginSettingId", true, "companyId", true,
			"pluginId", true, "pluginType", true, "roles", true, "active", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		PluginSetting newPluginSetting = addPluginSetting();

		PluginSetting existingPluginSetting = _persistence.fetchByPrimaryKey(newPluginSetting.getPrimaryKey());

		Assert.assertEquals(existingPluginSetting, newPluginSetting);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PluginSetting missingPluginSetting = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingPluginSetting);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		PluginSetting newPluginSetting1 = addPluginSetting();
		PluginSetting newPluginSetting2 = addPluginSetting();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPluginSetting1.getPrimaryKey());
		primaryKeys.add(newPluginSetting2.getPrimaryKey());

		Map<Serializable, PluginSetting> pluginSettings = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, pluginSettings.size());
		Assert.assertEquals(newPluginSetting1,
			pluginSettings.get(newPluginSetting1.getPrimaryKey()));
		Assert.assertEquals(newPluginSetting2,
			pluginSettings.get(newPluginSetting2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, PluginSetting> pluginSettings = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(pluginSettings.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		PluginSetting newPluginSetting = addPluginSetting();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPluginSetting.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, PluginSetting> pluginSettings = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, pluginSettings.size());
		Assert.assertEquals(newPluginSetting,
			pluginSettings.get(newPluginSetting.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, PluginSetting> pluginSettings = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(pluginSettings.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		PluginSetting newPluginSetting = addPluginSetting();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPluginSetting.getPrimaryKey());

		Map<Serializable, PluginSetting> pluginSettings = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, pluginSettings.size());
		Assert.assertEquals(newPluginSetting,
			pluginSettings.get(newPluginSetting.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = PluginSettingLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					PluginSetting pluginSetting = (PluginSetting)object;

					Assert.assertNotNull(pluginSetting);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		PluginSetting newPluginSetting = addPluginSetting();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PluginSetting.class,
				PluginSetting.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("pluginSettingId",
				newPluginSetting.getPluginSettingId()));

		List<PluginSetting> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		PluginSetting existingPluginSetting = result.get(0);

		Assert.assertEquals(existingPluginSetting, newPluginSetting);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PluginSetting.class,
				PluginSetting.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("pluginSettingId",
				RandomTestUtil.nextLong()));

		List<PluginSetting> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		PluginSetting newPluginSetting = addPluginSetting();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PluginSetting.class,
				PluginSetting.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"pluginSettingId"));

		Object newPluginSettingId = newPluginSetting.getPluginSettingId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("pluginSettingId",
				new Object[] { newPluginSettingId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingPluginSettingId = result.get(0);

		Assert.assertEquals(existingPluginSettingId, newPluginSettingId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PluginSetting.class,
				PluginSetting.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"pluginSettingId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("pluginSettingId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		PluginSetting newPluginSetting = addPluginSetting();

		_persistence.clearCache();

		PluginSettingModelImpl existingPluginSettingModelImpl = (PluginSettingModelImpl)_persistence.findByPrimaryKey(newPluginSetting.getPrimaryKey());

		Assert.assertEquals(existingPluginSettingModelImpl.getCompanyId(),
			existingPluginSettingModelImpl.getOriginalCompanyId());
		Assert.assertTrue(Validator.equals(
				existingPluginSettingModelImpl.getPluginId(),
				existingPluginSettingModelImpl.getOriginalPluginId()));
		Assert.assertTrue(Validator.equals(
				existingPluginSettingModelImpl.getPluginType(),
				existingPluginSettingModelImpl.getOriginalPluginType()));
	}

	protected PluginSetting addPluginSetting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PluginSetting pluginSetting = _persistence.create(pk);

		pluginSetting.setMvccVersion(RandomTestUtil.nextLong());

		pluginSetting.setCompanyId(RandomTestUtil.nextLong());

		pluginSetting.setPluginId(RandomTestUtil.randomString());

		pluginSetting.setPluginType(RandomTestUtil.randomString());

		pluginSetting.setRoles(RandomTestUtil.randomString());

		pluginSetting.setActive(RandomTestUtil.randomBoolean());

		_pluginSettings.add(_persistence.update(pluginSetting));

		return pluginSetting;
	}

	private static Log _log = LogFactoryUtil.getLog(PluginSettingPersistenceTest.class);
	private List<PluginSetting> _pluginSettings = new ArrayList<PluginSetting>();
	private ModelListener<PluginSetting>[] _modelListeners;
	private PluginSettingPersistence _persistence = PluginSettingUtil.getPersistence();
}