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

package com.liferay.portlet.deletion.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.deletion.NoSuchEntryException;
import com.liferay.portlet.deletion.model.DeletionEntry;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DeletionEntryPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DeletionEntryPersistence)PortalBeanLocatorUtil.locate(DeletionEntryPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DeletionEntry deletionEntry = _persistence.create(pk);

		assertNotNull(deletionEntry);

		assertEquals(deletionEntry.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DeletionEntry newDeletionEntry = addDeletionEntry();

		_persistence.remove(newDeletionEntry);

		DeletionEntry existingDeletionEntry = _persistence.fetchByPrimaryKey(newDeletionEntry.getPrimaryKey());

		assertNull(existingDeletionEntry);
	}

	public void testUpdateNew() throws Exception {
		addDeletionEntry();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DeletionEntry newDeletionEntry = _persistence.create(pk);

		newDeletionEntry.setGroupId(nextLong());
		newDeletionEntry.setCompanyId(nextLong());
		newDeletionEntry.setCreateDate(nextDate());
		newDeletionEntry.setClassNameId(nextLong());
		newDeletionEntry.setClassPK(nextLong());
		newDeletionEntry.setClassUuid(randomString());
		newDeletionEntry.setParentId(nextLong());

		_persistence.update(newDeletionEntry, false);

		DeletionEntry existingDeletionEntry = _persistence.findByPrimaryKey(newDeletionEntry.getPrimaryKey());

		assertEquals(existingDeletionEntry.getEntryId(),
			newDeletionEntry.getEntryId());
		assertEquals(existingDeletionEntry.getGroupId(),
			newDeletionEntry.getGroupId());
		assertEquals(existingDeletionEntry.getCompanyId(),
			newDeletionEntry.getCompanyId());
		assertEquals(Time.getShortTimestamp(
				existingDeletionEntry.getCreateDate()),
			Time.getShortTimestamp(newDeletionEntry.getCreateDate()));
		assertEquals(existingDeletionEntry.getClassNameId(),
			newDeletionEntry.getClassNameId());
		assertEquals(existingDeletionEntry.getClassPK(),
			newDeletionEntry.getClassPK());
		assertEquals(existingDeletionEntry.getClassUuid(),
			newDeletionEntry.getClassUuid());
		assertEquals(existingDeletionEntry.getParentId(),
			newDeletionEntry.getParentId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DeletionEntry newDeletionEntry = addDeletionEntry();

		DeletionEntry existingDeletionEntry = _persistence.findByPrimaryKey(newDeletionEntry.getPrimaryKey());

		assertEquals(existingDeletionEntry, newDeletionEntry);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchEntryException");
		}
		catch (NoSuchEntryException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DeletionEntry newDeletionEntry = addDeletionEntry();

		DeletionEntry existingDeletionEntry = _persistence.fetchByPrimaryKey(newDeletionEntry.getPrimaryKey());

		assertEquals(existingDeletionEntry, newDeletionEntry);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DeletionEntry missingDeletionEntry = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDeletionEntry);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DeletionEntry newDeletionEntry = addDeletionEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DeletionEntry.class,
				DeletionEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				newDeletionEntry.getEntryId()));

		List<DeletionEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DeletionEntry existingDeletionEntry = result.get(0);

		assertEquals(existingDeletionEntry, newDeletionEntry);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DeletionEntry.class,
				DeletionEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId", nextLong()));

		List<DeletionEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected DeletionEntry addDeletionEntry() throws Exception {
		long pk = nextLong();

		DeletionEntry deletionEntry = _persistence.create(pk);

		deletionEntry.setGroupId(nextLong());
		deletionEntry.setCompanyId(nextLong());
		deletionEntry.setCreateDate(nextDate());
		deletionEntry.setClassNameId(nextLong());
		deletionEntry.setClassPK(nextLong());
		deletionEntry.setClassUuid(randomString());
		deletionEntry.setParentId(nextLong());

		_persistence.update(deletionEntry, false);

		return deletionEntry;
	}

	private DeletionEntryPersistence _persistence;
}