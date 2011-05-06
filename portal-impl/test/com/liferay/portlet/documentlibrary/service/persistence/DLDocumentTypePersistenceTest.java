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
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException;
import com.liferay.portlet.documentlibrary.model.DLDocumentType;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DLDocumentTypePersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DLDocumentTypePersistence)PortalBeanLocatorUtil.locate(DLDocumentTypePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DLDocumentType dlDocumentType = _persistence.create(pk);

		assertNotNull(dlDocumentType);

		assertEquals(dlDocumentType.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DLDocumentType newDLDocumentType = addDLDocumentType();

		_persistence.remove(newDLDocumentType);

		DLDocumentType existingDLDocumentType = _persistence.fetchByPrimaryKey(newDLDocumentType.getPrimaryKey());

		assertNull(existingDLDocumentType);
	}

	public void testUpdateNew() throws Exception {
		addDLDocumentType();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DLDocumentType newDLDocumentType = _persistence.create(pk);

		newDLDocumentType.setGroupId(nextLong());
		newDLDocumentType.setCompanyId(nextLong());
		newDLDocumentType.setUserId(nextLong());
		newDLDocumentType.setUserName(randomString());
		newDLDocumentType.setCreateDate(nextDate());
		newDLDocumentType.setModifiedDate(nextDate());
		newDLDocumentType.setName(randomString());
		newDLDocumentType.setDescription(randomString());

		_persistence.update(newDLDocumentType, false);

		DLDocumentType existingDLDocumentType = _persistence.findByPrimaryKey(newDLDocumentType.getPrimaryKey());

		assertEquals(existingDLDocumentType.getDocumentTypeId(),
			newDLDocumentType.getDocumentTypeId());
		assertEquals(existingDLDocumentType.getGroupId(),
			newDLDocumentType.getGroupId());
		assertEquals(existingDLDocumentType.getCompanyId(),
			newDLDocumentType.getCompanyId());
		assertEquals(existingDLDocumentType.getUserId(),
			newDLDocumentType.getUserId());
		assertEquals(existingDLDocumentType.getUserName(),
			newDLDocumentType.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingDLDocumentType.getCreateDate()),
			Time.getShortTimestamp(newDLDocumentType.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingDLDocumentType.getModifiedDate()),
			Time.getShortTimestamp(newDLDocumentType.getModifiedDate()));
		assertEquals(existingDLDocumentType.getName(),
			newDLDocumentType.getName());
		assertEquals(existingDLDocumentType.getDescription(),
			newDLDocumentType.getDescription());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DLDocumentType newDLDocumentType = addDLDocumentType();

		DLDocumentType existingDLDocumentType = _persistence.findByPrimaryKey(newDLDocumentType.getPrimaryKey());

		assertEquals(existingDLDocumentType, newDLDocumentType);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchDocumentTypeException");
		}
		catch (NoSuchDocumentTypeException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DLDocumentType newDLDocumentType = addDLDocumentType();

		DLDocumentType existingDLDocumentType = _persistence.fetchByPrimaryKey(newDLDocumentType.getPrimaryKey());

		assertEquals(existingDLDocumentType, newDLDocumentType);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DLDocumentType missingDLDocumentType = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDLDocumentType);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DLDocumentType newDLDocumentType = addDLDocumentType();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLDocumentType.class,
				DLDocumentType.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("documentTypeId",
				newDLDocumentType.getDocumentTypeId()));

		List<DLDocumentType> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DLDocumentType existingDLDocumentType = result.get(0);

		assertEquals(existingDLDocumentType, newDLDocumentType);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLDocumentType.class,
				DLDocumentType.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("documentTypeId", nextLong()));

		List<DLDocumentType> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DLDocumentType newDLDocumentType = addDLDocumentType();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLDocumentType.class,
				DLDocumentType.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"documentTypeId"));

		Object newDocumentTypeId = newDLDocumentType.getDocumentTypeId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("documentTypeId",
				new Object[] { newDocumentTypeId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingDocumentTypeId = result.get(0);

		assertEquals(existingDocumentTypeId, newDocumentTypeId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLDocumentType.class,
				DLDocumentType.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"documentTypeId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("documentTypeId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected DLDocumentType addDLDocumentType() throws Exception {
		long pk = nextLong();

		DLDocumentType dlDocumentType = _persistence.create(pk);

		dlDocumentType.setGroupId(nextLong());
		dlDocumentType.setCompanyId(nextLong());
		dlDocumentType.setUserId(nextLong());
		dlDocumentType.setUserName(randomString());
		dlDocumentType.setCreateDate(nextDate());
		dlDocumentType.setModifiedDate(nextDate());
		dlDocumentType.setName(randomString());
		dlDocumentType.setDescription(randomString());

		_persistence.update(dlDocumentType, false);

		return dlDocumentType;
	}

	private DLDocumentTypePersistence _persistence;
}