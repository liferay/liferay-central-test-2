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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import com.liferay.portlet.softwarecatalog.NoSuchProductVersionException;
import com.liferay.portlet.softwarecatalog.model.SCProductVersion;
import com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionModelImpl;
import com.liferay.portlet.softwarecatalog.service.SCProductVersionLocalServiceUtil;

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
public class SCProductVersionPersistenceTest {
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
		Iterator<SCProductVersion> iterator = _scProductVersions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SCProductVersion scProductVersion = _persistence.create(pk);

		Assert.assertNotNull(scProductVersion);

		Assert.assertEquals(scProductVersion.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SCProductVersion newSCProductVersion = addSCProductVersion();

		_persistence.remove(newSCProductVersion);

		SCProductVersion existingSCProductVersion = _persistence.fetchByPrimaryKey(newSCProductVersion.getPrimaryKey());

		Assert.assertNull(existingSCProductVersion);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSCProductVersion();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SCProductVersion newSCProductVersion = _persistence.create(pk);

		newSCProductVersion.setCompanyId(RandomTestUtil.nextLong());

		newSCProductVersion.setUserId(RandomTestUtil.nextLong());

		newSCProductVersion.setUserName(RandomTestUtil.randomString());

		newSCProductVersion.setCreateDate(RandomTestUtil.nextDate());

		newSCProductVersion.setModifiedDate(RandomTestUtil.nextDate());

		newSCProductVersion.setProductEntryId(RandomTestUtil.nextLong());

		newSCProductVersion.setVersion(RandomTestUtil.randomString());

		newSCProductVersion.setChangeLog(RandomTestUtil.randomString());

		newSCProductVersion.setDownloadPageURL(RandomTestUtil.randomString());

		newSCProductVersion.setDirectDownloadURL(RandomTestUtil.randomString());

		newSCProductVersion.setRepoStoreArtifact(RandomTestUtil.randomBoolean());

		_scProductVersions.add(_persistence.update(newSCProductVersion));

		SCProductVersion existingSCProductVersion = _persistence.findByPrimaryKey(newSCProductVersion.getPrimaryKey());

		Assert.assertEquals(existingSCProductVersion.getProductVersionId(),
			newSCProductVersion.getProductVersionId());
		Assert.assertEquals(existingSCProductVersion.getCompanyId(),
			newSCProductVersion.getCompanyId());
		Assert.assertEquals(existingSCProductVersion.getUserId(),
			newSCProductVersion.getUserId());
		Assert.assertEquals(existingSCProductVersion.getUserName(),
			newSCProductVersion.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingSCProductVersion.getCreateDate()),
			Time.getShortTimestamp(newSCProductVersion.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingSCProductVersion.getModifiedDate()),
			Time.getShortTimestamp(newSCProductVersion.getModifiedDate()));
		Assert.assertEquals(existingSCProductVersion.getProductEntryId(),
			newSCProductVersion.getProductEntryId());
		Assert.assertEquals(existingSCProductVersion.getVersion(),
			newSCProductVersion.getVersion());
		Assert.assertEquals(existingSCProductVersion.getChangeLog(),
			newSCProductVersion.getChangeLog());
		Assert.assertEquals(existingSCProductVersion.getDownloadPageURL(),
			newSCProductVersion.getDownloadPageURL());
		Assert.assertEquals(existingSCProductVersion.getDirectDownloadURL(),
			newSCProductVersion.getDirectDownloadURL());
		Assert.assertEquals(existingSCProductVersion.getRepoStoreArtifact(),
			newSCProductVersion.getRepoStoreArtifact());
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
	public void testCountByDirectDownloadURL() {
		try {
			_persistence.countByDirectDownloadURL(StringPool.BLANK);

			_persistence.countByDirectDownloadURL(StringPool.NULL);

			_persistence.countByDirectDownloadURL((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SCProductVersion newSCProductVersion = addSCProductVersion();

		SCProductVersion existingSCProductVersion = _persistence.findByPrimaryKey(newSCProductVersion.getPrimaryKey());

		Assert.assertEquals(existingSCProductVersion, newSCProductVersion);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchProductVersionException");
		}
		catch (NoSuchProductVersionException nsee) {
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

	protected OrderByComparator<SCProductVersion> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("SCProductVersion",
			"productVersionId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"productEntryId", true, "version", true, "changeLog", true,
			"downloadPageURL", true, "directDownloadURL", true,
			"repoStoreArtifact", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SCProductVersion newSCProductVersion = addSCProductVersion();

		SCProductVersion existingSCProductVersion = _persistence.fetchByPrimaryKey(newSCProductVersion.getPrimaryKey());

		Assert.assertEquals(existingSCProductVersion, newSCProductVersion);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SCProductVersion missingSCProductVersion = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSCProductVersion);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		SCProductVersion newSCProductVersion1 = addSCProductVersion();
		SCProductVersion newSCProductVersion2 = addSCProductVersion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSCProductVersion1.getPrimaryKey());
		primaryKeys.add(newSCProductVersion2.getPrimaryKey());

		Map<Serializable, SCProductVersion> scProductVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, scProductVersions.size());
		Assert.assertEquals(newSCProductVersion1,
			scProductVersions.get(newSCProductVersion1.getPrimaryKey()));
		Assert.assertEquals(newSCProductVersion2,
			scProductVersions.get(newSCProductVersion2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, SCProductVersion> scProductVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(scProductVersions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		SCProductVersion newSCProductVersion = addSCProductVersion();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSCProductVersion.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, SCProductVersion> scProductVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, scProductVersions.size());
		Assert.assertEquals(newSCProductVersion,
			scProductVersions.get(newSCProductVersion.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, SCProductVersion> scProductVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(scProductVersions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		SCProductVersion newSCProductVersion = addSCProductVersion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSCProductVersion.getPrimaryKey());

		Map<Serializable, SCProductVersion> scProductVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, scProductVersions.size());
		Assert.assertEquals(newSCProductVersion,
			scProductVersions.get(newSCProductVersion.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = SCProductVersionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					SCProductVersion scProductVersion = (SCProductVersion)object;

					Assert.assertNotNull(scProductVersion);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SCProductVersion newSCProductVersion = addSCProductVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCProductVersion.class,
				SCProductVersion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("productVersionId",
				newSCProductVersion.getProductVersionId()));

		List<SCProductVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		SCProductVersion existingSCProductVersion = result.get(0);

		Assert.assertEquals(existingSCProductVersion, newSCProductVersion);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCProductVersion.class,
				SCProductVersion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("productVersionId",
				RandomTestUtil.nextLong()));

		List<SCProductVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SCProductVersion newSCProductVersion = addSCProductVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCProductVersion.class,
				SCProductVersion.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"productVersionId"));

		Object newProductVersionId = newSCProductVersion.getProductVersionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("productVersionId",
				new Object[] { newProductVersionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingProductVersionId = result.get(0);

		Assert.assertEquals(existingProductVersionId, newProductVersionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCProductVersion.class,
				SCProductVersion.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"productVersionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("productVersionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		SCProductVersion newSCProductVersion = addSCProductVersion();

		_persistence.clearCache();

		SCProductVersionModelImpl existingSCProductVersionModelImpl = (SCProductVersionModelImpl)_persistence.findByPrimaryKey(newSCProductVersion.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingSCProductVersionModelImpl.getDirectDownloadURL(),
				existingSCProductVersionModelImpl.getOriginalDirectDownloadURL()));
	}

	protected SCProductVersion addSCProductVersion() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SCProductVersion scProductVersion = _persistence.create(pk);

		scProductVersion.setCompanyId(RandomTestUtil.nextLong());

		scProductVersion.setUserId(RandomTestUtil.nextLong());

		scProductVersion.setUserName(RandomTestUtil.randomString());

		scProductVersion.setCreateDate(RandomTestUtil.nextDate());

		scProductVersion.setModifiedDate(RandomTestUtil.nextDate());

		scProductVersion.setProductEntryId(RandomTestUtil.nextLong());

		scProductVersion.setVersion(RandomTestUtil.randomString());

		scProductVersion.setChangeLog(RandomTestUtil.randomString());

		scProductVersion.setDownloadPageURL(RandomTestUtil.randomString());

		scProductVersion.setDirectDownloadURL(RandomTestUtil.randomString());

		scProductVersion.setRepoStoreArtifact(RandomTestUtil.randomBoolean());

		_scProductVersions.add(_persistence.update(scProductVersion));

		return scProductVersion;
	}

	private static Log _log = LogFactoryUtil.getLog(SCProductVersionPersistenceTest.class);
	private List<SCProductVersion> _scProductVersions = new ArrayList<SCProductVersion>();
	private ModelListener<SCProductVersion>[] _modelListeners;
	private SCProductVersionPersistence _persistence = SCProductVersionUtil.getPersistence();
}