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

package com.liferay.portlet.imagegallery.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.imagegallery.NoSuchFolderException;
import com.liferay.portlet.imagegallery.model.IGFolder;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class IGFolderPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (IGFolderPersistence)PortalBeanLocatorUtil.locate(IGFolderPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		IGFolder igFolder = _persistence.create(pk);

		assertNotNull(igFolder);

		assertEquals(igFolder.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		IGFolder newIGFolder = addIGFolder();

		_persistence.remove(newIGFolder);

		IGFolder existingIGFolder = _persistence.fetchByPrimaryKey(newIGFolder.getPrimaryKey());

		assertNull(existingIGFolder);
	}

	public void testUpdateNew() throws Exception {
		addIGFolder();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		IGFolder newIGFolder = _persistence.create(pk);

		newIGFolder.setUuid(randomString());
		newIGFolder.setGroupId(nextLong());
		newIGFolder.setCompanyId(nextLong());
		newIGFolder.setUserId(nextLong());
		newIGFolder.setCreateDate(nextDate());
		newIGFolder.setModifiedDate(nextDate());
		newIGFolder.setParentFolderId(nextLong());
		newIGFolder.setName(randomString());
		newIGFolder.setDescription(randomString());

		_persistence.update(newIGFolder, false);

		IGFolder existingIGFolder = _persistence.findByPrimaryKey(newIGFolder.getPrimaryKey());

		assertEquals(existingIGFolder.getUuid(), newIGFolder.getUuid());
		assertEquals(existingIGFolder.getFolderId(), newIGFolder.getFolderId());
		assertEquals(existingIGFolder.getGroupId(), newIGFolder.getGroupId());
		assertEquals(existingIGFolder.getCompanyId(), newIGFolder.getCompanyId());
		assertEquals(existingIGFolder.getUserId(), newIGFolder.getUserId());
		assertEquals(Time.getShortTimestamp(existingIGFolder.getCreateDate()),
			Time.getShortTimestamp(newIGFolder.getCreateDate()));
		assertEquals(Time.getShortTimestamp(existingIGFolder.getModifiedDate()),
			Time.getShortTimestamp(newIGFolder.getModifiedDate()));
		assertEquals(existingIGFolder.getParentFolderId(),
			newIGFolder.getParentFolderId());
		assertEquals(existingIGFolder.getName(), newIGFolder.getName());
		assertEquals(existingIGFolder.getDescription(),
			newIGFolder.getDescription());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		IGFolder newIGFolder = addIGFolder();

		IGFolder existingIGFolder = _persistence.findByPrimaryKey(newIGFolder.getPrimaryKey());

		assertEquals(existingIGFolder, newIGFolder);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchFolderException");
		}
		catch (NoSuchFolderException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		IGFolder newIGFolder = addIGFolder();

		IGFolder existingIGFolder = _persistence.fetchByPrimaryKey(newIGFolder.getPrimaryKey());

		assertEquals(existingIGFolder, newIGFolder);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		IGFolder missingIGFolder = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingIGFolder);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		IGFolder newIGFolder = addIGFolder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(IGFolder.class,
				IGFolder.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("folderId",
				newIGFolder.getFolderId()));

		List<IGFolder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		IGFolder existingIGFolder = result.get(0);

		assertEquals(existingIGFolder, newIGFolder);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(IGFolder.class,
				IGFolder.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("folderId", nextLong()));

		List<IGFolder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		IGFolder newIGFolder = addIGFolder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(IGFolder.class,
				IGFolder.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("folderId"));

		Object newFolderId = newIGFolder.getFolderId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("folderId",
				new Object[] { newFolderId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingFolderId = result.get(0);

		assertEquals(existingFolderId, newFolderId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		IGFolder newIGFolder = addIGFolder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(IGFolder.class,
				IGFolder.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("folderId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("folderId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected IGFolder addIGFolder() throws Exception {
		long pk = nextLong();

		IGFolder igFolder = _persistence.create(pk);

		igFolder.setUuid(randomString());
		igFolder.setGroupId(nextLong());
		igFolder.setCompanyId(nextLong());
		igFolder.setUserId(nextLong());
		igFolder.setCreateDate(nextDate());
		igFolder.setModifiedDate(nextDate());
		igFolder.setParentFolderId(nextLong());
		igFolder.setName(randomString());
		igFolder.setDescription(randomString());

		_persistence.update(igFolder, false);

		return igFolder;
	}

	private IGFolderPersistence _persistence;
}