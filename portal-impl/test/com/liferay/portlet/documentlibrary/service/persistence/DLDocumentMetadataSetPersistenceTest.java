/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException;
import com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet;
import com.liferay.portlet.documentlibrary.model.impl.DLDocumentMetadataSetModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DLDocumentMetadataSetPersistenceTest
	extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DLDocumentMetadataSetPersistence)PortalBeanLocatorUtil.locate(DLDocumentMetadataSetPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DLDocumentMetadataSet dlDocumentMetadataSet = _persistence.create(pk);

		assertNotNull(dlDocumentMetadataSet);

		assertEquals(dlDocumentMetadataSet.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DLDocumentMetadataSet newDLDocumentMetadataSet = addDLDocumentMetadataSet();

		_persistence.remove(newDLDocumentMetadataSet);

		DLDocumentMetadataSet existingDLDocumentMetadataSet = _persistence.fetchByPrimaryKey(newDLDocumentMetadataSet.getPrimaryKey());

		assertNull(existingDLDocumentMetadataSet);
	}

	public void testUpdateNew() throws Exception {
		addDLDocumentMetadataSet();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DLDocumentMetadataSet newDLDocumentMetadataSet = _persistence.create(pk);

		newDLDocumentMetadataSet.setUuid(randomString());

		newDLDocumentMetadataSet.setClassNameId(nextLong());

		newDLDocumentMetadataSet.setClassPK(nextLong());

		newDLDocumentMetadataSet.setDDMStructureId(nextLong());

		newDLDocumentMetadataSet.setDocumentTypeId(nextLong());

		newDLDocumentMetadataSet.setFileVersionId(nextLong());

		_persistence.update(newDLDocumentMetadataSet, false);

		DLDocumentMetadataSet existingDLDocumentMetadataSet = _persistence.findByPrimaryKey(newDLDocumentMetadataSet.getPrimaryKey());

		assertEquals(existingDLDocumentMetadataSet.getUuid(),
			newDLDocumentMetadataSet.getUuid());
		assertEquals(existingDLDocumentMetadataSet.getDocumentMetadataSetId(),
			newDLDocumentMetadataSet.getDocumentMetadataSetId());
		assertEquals(existingDLDocumentMetadataSet.getClassNameId(),
			newDLDocumentMetadataSet.getClassNameId());
		assertEquals(existingDLDocumentMetadataSet.getClassPK(),
			newDLDocumentMetadataSet.getClassPK());
		assertEquals(existingDLDocumentMetadataSet.getDDMStructureId(),
			newDLDocumentMetadataSet.getDDMStructureId());
		assertEquals(existingDLDocumentMetadataSet.getDocumentTypeId(),
			newDLDocumentMetadataSet.getDocumentTypeId());
		assertEquals(existingDLDocumentMetadataSet.getFileVersionId(),
			newDLDocumentMetadataSet.getFileVersionId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DLDocumentMetadataSet newDLDocumentMetadataSet = addDLDocumentMetadataSet();

		DLDocumentMetadataSet existingDLDocumentMetadataSet = _persistence.findByPrimaryKey(newDLDocumentMetadataSet.getPrimaryKey());

		assertEquals(existingDLDocumentMetadataSet, newDLDocumentMetadataSet);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail(
				"Missing entity did not throw NoSuchDocumentMetadataSetException");
		}
		catch (NoSuchDocumentMetadataSetException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DLDocumentMetadataSet newDLDocumentMetadataSet = addDLDocumentMetadataSet();

		DLDocumentMetadataSet existingDLDocumentMetadataSet = _persistence.fetchByPrimaryKey(newDLDocumentMetadataSet.getPrimaryKey());

		assertEquals(existingDLDocumentMetadataSet, newDLDocumentMetadataSet);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DLDocumentMetadataSet missingDLDocumentMetadataSet = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDLDocumentMetadataSet);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DLDocumentMetadataSet newDLDocumentMetadataSet = addDLDocumentMetadataSet();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLDocumentMetadataSet.class,
				DLDocumentMetadataSet.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("documentMetadataSetId",
				newDLDocumentMetadataSet.getDocumentMetadataSetId()));

		List<DLDocumentMetadataSet> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DLDocumentMetadataSet existingDLDocumentMetadataSet = result.get(0);

		assertEquals(existingDLDocumentMetadataSet, newDLDocumentMetadataSet);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLDocumentMetadataSet.class,
				DLDocumentMetadataSet.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("documentMetadataSetId",
				nextLong()));

		List<DLDocumentMetadataSet> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DLDocumentMetadataSet newDLDocumentMetadataSet = addDLDocumentMetadataSet();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLDocumentMetadataSet.class,
				DLDocumentMetadataSet.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"documentMetadataSetId"));

		Object newDocumentMetadataSetId = newDLDocumentMetadataSet.getDocumentMetadataSetId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("documentMetadataSetId",
				new Object[] { newDocumentMetadataSetId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingDocumentMetadataSetId = result.get(0);

		assertEquals(existingDocumentMetadataSetId, newDocumentMetadataSetId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLDocumentMetadataSet.class,
				DLDocumentMetadataSet.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"documentMetadataSetId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("documentMetadataSetId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DLDocumentMetadataSet newDLDocumentMetadataSet = addDLDocumentMetadataSet();

		_persistence.clearCache();

		DLDocumentMetadataSetModelImpl existingDLDocumentMetadataSetModelImpl = (DLDocumentMetadataSetModelImpl)_persistence.findByPrimaryKey(newDLDocumentMetadataSet.getPrimaryKey());

		assertEquals(existingDLDocumentMetadataSetModelImpl.getDDMStructureId(),
			existingDLDocumentMetadataSetModelImpl.getOriginalDDMStructureId());
		assertEquals(existingDLDocumentMetadataSetModelImpl.getFileVersionId(),
			existingDLDocumentMetadataSetModelImpl.getOriginalFileVersionId());
	}

	protected DLDocumentMetadataSet addDLDocumentMetadataSet()
		throws Exception {
		long pk = nextLong();

		DLDocumentMetadataSet dlDocumentMetadataSet = _persistence.create(pk);

		dlDocumentMetadataSet.setUuid(randomString());

		dlDocumentMetadataSet.setClassNameId(nextLong());

		dlDocumentMetadataSet.setClassPK(nextLong());

		dlDocumentMetadataSet.setDDMStructureId(nextLong());

		dlDocumentMetadataSet.setDocumentTypeId(nextLong());

		dlDocumentMetadataSet.setFileVersionId(nextLong());

		_persistence.update(dlDocumentMetadataSet, false);

		return dlDocumentMetadataSet;
	}

	private DLDocumentMetadataSetPersistence _persistence;
}