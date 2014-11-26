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

package com.liferay.portlet.documentlibrary.service.persistence;

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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.LiferayIntegrationTestRule;
import com.liferay.portal.test.PersistenceTestRule;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataModelImpl;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalServiceUtil;

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
public class DLFileEntryMetadataPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@After
	public void tearDown() throws Exception {
		Iterator<DLFileEntryMetadata> iterator = _dlFileEntryMetadatas.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DLFileEntryMetadata dlFileEntryMetadata = _persistence.create(pk);

		Assert.assertNotNull(dlFileEntryMetadata);

		Assert.assertEquals(dlFileEntryMetadata.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DLFileEntryMetadata newDLFileEntryMetadata = addDLFileEntryMetadata();

		_persistence.remove(newDLFileEntryMetadata);

		DLFileEntryMetadata existingDLFileEntryMetadata = _persistence.fetchByPrimaryKey(newDLFileEntryMetadata.getPrimaryKey());

		Assert.assertNull(existingDLFileEntryMetadata);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDLFileEntryMetadata();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DLFileEntryMetadata newDLFileEntryMetadata = _persistence.create(pk);

		newDLFileEntryMetadata.setUuid(RandomTestUtil.randomString());

		newDLFileEntryMetadata.setDDMStorageId(RandomTestUtil.nextLong());

		newDLFileEntryMetadata.setDDMStructureId(RandomTestUtil.nextLong());

		newDLFileEntryMetadata.setFileEntryTypeId(RandomTestUtil.nextLong());

		newDLFileEntryMetadata.setFileEntryId(RandomTestUtil.nextLong());

		newDLFileEntryMetadata.setFileVersionId(RandomTestUtil.nextLong());

		_dlFileEntryMetadatas.add(_persistence.update(newDLFileEntryMetadata));

		DLFileEntryMetadata existingDLFileEntryMetadata = _persistence.findByPrimaryKey(newDLFileEntryMetadata.getPrimaryKey());

		Assert.assertEquals(existingDLFileEntryMetadata.getUuid(),
			newDLFileEntryMetadata.getUuid());
		Assert.assertEquals(existingDLFileEntryMetadata.getFileEntryMetadataId(),
			newDLFileEntryMetadata.getFileEntryMetadataId());
		Assert.assertEquals(existingDLFileEntryMetadata.getDDMStorageId(),
			newDLFileEntryMetadata.getDDMStorageId());
		Assert.assertEquals(existingDLFileEntryMetadata.getDDMStructureId(),
			newDLFileEntryMetadata.getDDMStructureId());
		Assert.assertEquals(existingDLFileEntryMetadata.getFileEntryTypeId(),
			newDLFileEntryMetadata.getFileEntryTypeId());
		Assert.assertEquals(existingDLFileEntryMetadata.getFileEntryId(),
			newDLFileEntryMetadata.getFileEntryId());
		Assert.assertEquals(existingDLFileEntryMetadata.getFileVersionId(),
			newDLFileEntryMetadata.getFileVersionId());
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
	public void testCountByFileEntryTypeId() {
		try {
			_persistence.countByFileEntryTypeId(RandomTestUtil.nextLong());

			_persistence.countByFileEntryTypeId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByFileEntryId() {
		try {
			_persistence.countByFileEntryId(RandomTestUtil.nextLong());

			_persistence.countByFileEntryId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByFileVersionId() {
		try {
			_persistence.countByFileVersionId(RandomTestUtil.nextLong());

			_persistence.countByFileVersionId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByD_F() {
		try {
			_persistence.countByD_F(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByD_F(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DLFileEntryMetadata newDLFileEntryMetadata = addDLFileEntryMetadata();

		DLFileEntryMetadata existingDLFileEntryMetadata = _persistence.findByPrimaryKey(newDLFileEntryMetadata.getPrimaryKey());

		Assert.assertEquals(existingDLFileEntryMetadata, newDLFileEntryMetadata);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchFileEntryMetadataException");
		}
		catch (NoSuchFileEntryMetadataException nsee) {
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

	protected OrderByComparator<DLFileEntryMetadata> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("DLFileEntryMetadata",
			"uuid", true, "fileEntryMetadataId", true, "DDMStorageId", true,
			"DDMStructureId", true, "fileEntryTypeId", true, "fileEntryId",
			true, "fileVersionId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DLFileEntryMetadata newDLFileEntryMetadata = addDLFileEntryMetadata();

		DLFileEntryMetadata existingDLFileEntryMetadata = _persistence.fetchByPrimaryKey(newDLFileEntryMetadata.getPrimaryKey());

		Assert.assertEquals(existingDLFileEntryMetadata, newDLFileEntryMetadata);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DLFileEntryMetadata missingDLFileEntryMetadata = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDLFileEntryMetadata);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		DLFileEntryMetadata newDLFileEntryMetadata1 = addDLFileEntryMetadata();
		DLFileEntryMetadata newDLFileEntryMetadata2 = addDLFileEntryMetadata();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDLFileEntryMetadata1.getPrimaryKey());
		primaryKeys.add(newDLFileEntryMetadata2.getPrimaryKey());

		Map<Serializable, DLFileEntryMetadata> dlFileEntryMetadatas = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, dlFileEntryMetadatas.size());
		Assert.assertEquals(newDLFileEntryMetadata1,
			dlFileEntryMetadatas.get(newDLFileEntryMetadata1.getPrimaryKey()));
		Assert.assertEquals(newDLFileEntryMetadata2,
			dlFileEntryMetadatas.get(newDLFileEntryMetadata2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, DLFileEntryMetadata> dlFileEntryMetadatas = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(dlFileEntryMetadatas.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		DLFileEntryMetadata newDLFileEntryMetadata = addDLFileEntryMetadata();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDLFileEntryMetadata.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, DLFileEntryMetadata> dlFileEntryMetadatas = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, dlFileEntryMetadatas.size());
		Assert.assertEquals(newDLFileEntryMetadata,
			dlFileEntryMetadatas.get(newDLFileEntryMetadata.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, DLFileEntryMetadata> dlFileEntryMetadatas = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(dlFileEntryMetadatas.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		DLFileEntryMetadata newDLFileEntryMetadata = addDLFileEntryMetadata();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDLFileEntryMetadata.getPrimaryKey());

		Map<Serializable, DLFileEntryMetadata> dlFileEntryMetadatas = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, dlFileEntryMetadatas.size());
		Assert.assertEquals(newDLFileEntryMetadata,
			dlFileEntryMetadatas.get(newDLFileEntryMetadata.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = DLFileEntryMetadataLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					DLFileEntryMetadata dlFileEntryMetadata = (DLFileEntryMetadata)object;

					Assert.assertNotNull(dlFileEntryMetadata);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DLFileEntryMetadata newDLFileEntryMetadata = addDLFileEntryMetadata();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntryMetadata.class,
				DLFileEntryMetadata.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileEntryMetadataId",
				newDLFileEntryMetadata.getFileEntryMetadataId()));

		List<DLFileEntryMetadata> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DLFileEntryMetadata existingDLFileEntryMetadata = result.get(0);

		Assert.assertEquals(existingDLFileEntryMetadata, newDLFileEntryMetadata);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntryMetadata.class,
				DLFileEntryMetadata.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileEntryMetadataId",
				RandomTestUtil.nextLong()));

		List<DLFileEntryMetadata> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DLFileEntryMetadata newDLFileEntryMetadata = addDLFileEntryMetadata();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntryMetadata.class,
				DLFileEntryMetadata.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"fileEntryMetadataId"));

		Object newFileEntryMetadataId = newDLFileEntryMetadata.getFileEntryMetadataId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("fileEntryMetadataId",
				new Object[] { newFileEntryMetadataId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFileEntryMetadataId = result.get(0);

		Assert.assertEquals(existingFileEntryMetadataId, newFileEntryMetadataId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntryMetadata.class,
				DLFileEntryMetadata.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"fileEntryMetadataId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("fileEntryMetadataId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DLFileEntryMetadata newDLFileEntryMetadata = addDLFileEntryMetadata();

		_persistence.clearCache();

		DLFileEntryMetadataModelImpl existingDLFileEntryMetadataModelImpl = (DLFileEntryMetadataModelImpl)_persistence.findByPrimaryKey(newDLFileEntryMetadata.getPrimaryKey());

		Assert.assertEquals(existingDLFileEntryMetadataModelImpl.getDDMStructureId(),
			existingDLFileEntryMetadataModelImpl.getOriginalDDMStructureId());
		Assert.assertEquals(existingDLFileEntryMetadataModelImpl.getFileVersionId(),
			existingDLFileEntryMetadataModelImpl.getOriginalFileVersionId());
	}

	protected DLFileEntryMetadata addDLFileEntryMetadata()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		DLFileEntryMetadata dlFileEntryMetadata = _persistence.create(pk);

		dlFileEntryMetadata.setUuid(RandomTestUtil.randomString());

		dlFileEntryMetadata.setDDMStorageId(RandomTestUtil.nextLong());

		dlFileEntryMetadata.setDDMStructureId(RandomTestUtil.nextLong());

		dlFileEntryMetadata.setFileEntryTypeId(RandomTestUtil.nextLong());

		dlFileEntryMetadata.setFileEntryId(RandomTestUtil.nextLong());

		dlFileEntryMetadata.setFileVersionId(RandomTestUtil.nextLong());

		_dlFileEntryMetadatas.add(_persistence.update(dlFileEntryMetadata));

		return dlFileEntryMetadata;
	}

	private List<DLFileEntryMetadata> _dlFileEntryMetadatas = new ArrayList<DLFileEntryMetadata>();
	private DLFileEntryMetadataPersistence _persistence = DLFileEntryMetadataUtil.getPersistence();
}