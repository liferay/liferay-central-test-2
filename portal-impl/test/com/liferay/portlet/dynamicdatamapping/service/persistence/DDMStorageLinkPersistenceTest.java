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

package com.liferay.portlet.dynamicdatamapping.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMStorageLinkPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DDMStorageLinkPersistence)PortalBeanLocatorUtil.locate(DDMStorageLinkPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DDMStorageLink ddmStorageLink = _persistence.create(pk);

		assertNotNull(ddmStorageLink);

		assertEquals(ddmStorageLink.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DDMStorageLink newDDMStorageLink = addDDMStorageLink();

		_persistence.remove(newDDMStorageLink);

		DDMStorageLink existingDDMStorageLink = _persistence.fetchByPrimaryKey(newDDMStorageLink.getPrimaryKey());

		assertNull(existingDDMStorageLink);
	}

	public void testUpdateNew() throws Exception {
		addDDMStorageLink();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DDMStorageLink newDDMStorageLink = _persistence.create(pk);

		newDDMStorageLink.setUuid(randomString());
		newDDMStorageLink.setClassNameId(nextLong());
		newDDMStorageLink.setClassPK(nextLong());
		newDDMStorageLink.setStructureId(nextLong());

		_persistence.update(newDDMStorageLink, false);

		DDMStorageLink existingDDMStorageLink = _persistence.findByPrimaryKey(newDDMStorageLink.getPrimaryKey());

		assertEquals(existingDDMStorageLink.getUuid(),
			newDDMStorageLink.getUuid());
		assertEquals(existingDDMStorageLink.getStorageLinkId(),
			newDDMStorageLink.getStorageLinkId());
		assertEquals(existingDDMStorageLink.getClassNameId(),
			newDDMStorageLink.getClassNameId());
		assertEquals(existingDDMStorageLink.getClassPK(),
			newDDMStorageLink.getClassPK());
		assertEquals(existingDDMStorageLink.getStructureId(),
			newDDMStorageLink.getStructureId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DDMStorageLink newDDMStorageLink = addDDMStorageLink();

		DDMStorageLink existingDDMStorageLink = _persistence.findByPrimaryKey(newDDMStorageLink.getPrimaryKey());

		assertEquals(existingDDMStorageLink, newDDMStorageLink);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchStorageLinkException");
		}
		catch (NoSuchStorageLinkException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDMStorageLink newDDMStorageLink = addDDMStorageLink();

		DDMStorageLink existingDDMStorageLink = _persistence.fetchByPrimaryKey(newDDMStorageLink.getPrimaryKey());

		assertEquals(existingDDMStorageLink, newDDMStorageLink);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DDMStorageLink missingDDMStorageLink = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDDMStorageLink);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDMStorageLink newDDMStorageLink = addDDMStorageLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStorageLink.class,
				DDMStorageLink.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("storageLinkId",
				newDDMStorageLink.getStorageLinkId()));

		List<DDMStorageLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DDMStorageLink existingDDMStorageLink = result.get(0);

		assertEquals(existingDDMStorageLink, newDDMStorageLink);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStorageLink.class,
				DDMStorageLink.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("storageLinkId", nextLong()));

		List<DDMStorageLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected DDMStorageLink addDDMStorageLink() throws Exception {
		long pk = nextLong();

		DDMStorageLink ddmStorageLink = _persistence.create(pk);

		ddmStorageLink.setUuid(randomString());
		ddmStorageLink.setClassNameId(nextLong());
		ddmStorageLink.setClassPK(nextLong());
		ddmStorageLink.setStructureId(nextLong());

		_persistence.update(ddmStorageLink, false);

		return ddmStorageLink;
	}

	private DDMStorageLinkPersistence _persistence;
}