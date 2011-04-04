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
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.dynamicdatamapping.NoSuchListEntryException;
import com.liferay.portlet.dynamicdatamapping.model.DDMListEntry;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMListEntryPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DDMListEntryPersistence)PortalBeanLocatorUtil.locate(DDMListEntryPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DDMListEntry ddmListEntry = _persistence.create(pk);

		assertNotNull(ddmListEntry);

		assertEquals(ddmListEntry.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DDMListEntry newDDMListEntry = addDDMListEntry();

		_persistence.remove(newDDMListEntry);

		DDMListEntry existingDDMListEntry = _persistence.fetchByPrimaryKey(newDDMListEntry.getPrimaryKey());

		assertNull(existingDDMListEntry);
	}

	public void testUpdateNew() throws Exception {
		addDDMListEntry();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DDMListEntry newDDMListEntry = _persistence.create(pk);

		newDDMListEntry.setUuid(randomString());
		newDDMListEntry.setListId(nextLong());
		newDDMListEntry.setClassPK(nextLong());

		_persistence.update(newDDMListEntry, false);

		DDMListEntry existingDDMListEntry = _persistence.findByPrimaryKey(newDDMListEntry.getPrimaryKey());

		assertEquals(existingDDMListEntry.getUuid(), newDDMListEntry.getUuid());
		assertEquals(existingDDMListEntry.getListEntryId(),
			newDDMListEntry.getListEntryId());
		assertEquals(existingDDMListEntry.getListId(),
			newDDMListEntry.getListId());
		assertEquals(existingDDMListEntry.getClassPK(),
			newDDMListEntry.getClassPK());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DDMListEntry newDDMListEntry = addDDMListEntry();

		DDMListEntry existingDDMListEntry = _persistence.findByPrimaryKey(newDDMListEntry.getPrimaryKey());

		assertEquals(existingDDMListEntry, newDDMListEntry);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchListEntryException");
		}
		catch (NoSuchListEntryException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDMListEntry newDDMListEntry = addDDMListEntry();

		DDMListEntry existingDDMListEntry = _persistence.fetchByPrimaryKey(newDDMListEntry.getPrimaryKey());

		assertEquals(existingDDMListEntry, newDDMListEntry);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DDMListEntry missingDDMListEntry = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDDMListEntry);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDMListEntry newDDMListEntry = addDDMListEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMListEntry.class,
				DDMListEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("listEntryId",
				newDDMListEntry.getListEntryId()));

		List<DDMListEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DDMListEntry existingDDMListEntry = result.get(0);

		assertEquals(existingDDMListEntry, newDDMListEntry);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMListEntry.class,
				DDMListEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("listEntryId", nextLong()));

		List<DDMListEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDMListEntry newDDMListEntry = addDDMListEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMListEntry.class,
				DDMListEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("listEntryId"));

		Object newListEntryId = newDDMListEntry.getListEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("listEntryId",
				new Object[] { newListEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingListEntryId = result.get(0);

		assertEquals(existingListEntryId, newListEntryId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMListEntry.class,
				DDMListEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("listEntryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("listEntryId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected DDMListEntry addDDMListEntry() throws Exception {
		long pk = nextLong();

		DDMListEntry ddmListEntry = _persistence.create(pk);

		ddmListEntry.setUuid(randomString());
		ddmListEntry.setListId(nextLong());
		ddmListEntry.setClassPK(nextLong());

		_persistence.update(ddmListEntry, false);

		return ddmListEntry;
	}

	private DDMListEntryPersistence _persistence;
}