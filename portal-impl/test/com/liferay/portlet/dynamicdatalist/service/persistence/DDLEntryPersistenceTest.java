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

package com.liferay.portlet.dynamicdatalist.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.dynamicdatalist.NoSuchEntryException;
import com.liferay.portlet.dynamicdatalist.model.DDLEntry;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DDLEntryPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DDLEntryPersistence)PortalBeanLocatorUtil.locate(DDLEntryPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DDLEntry ddlEntry = _persistence.create(pk);

		assertNotNull(ddlEntry);

		assertEquals(ddlEntry.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DDLEntry newDDLEntry = addDDLEntry();

		_persistence.remove(newDDLEntry);

		DDLEntry existingDDLEntry = _persistence.fetchByPrimaryKey(newDDLEntry.getPrimaryKey());

		assertNull(existingDDLEntry);
	}

	public void testUpdateNew() throws Exception {
		addDDLEntry();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DDLEntry newDDLEntry = _persistence.create(pk);

		newDDLEntry.setUuid(randomString());
		newDDLEntry.setGroupId(nextLong());
		newDDLEntry.setCompanyId(nextLong());
		newDDLEntry.setUserId(nextLong());
		newDDLEntry.setUserName(randomString());
		newDDLEntry.setCreateDate(nextDate());
		newDDLEntry.setModifiedDate(nextDate());
		newDDLEntry.setName(randomString());
		newDDLEntry.setDescription(randomString());
		newDDLEntry.setStructureId(nextLong());

		_persistence.update(newDDLEntry, false);

		DDLEntry existingDDLEntry = _persistence.findByPrimaryKey(newDDLEntry.getPrimaryKey());

		assertEquals(existingDDLEntry.getUuid(), newDDLEntry.getUuid());
		assertEquals(existingDDLEntry.getEntryId(), newDDLEntry.getEntryId());
		assertEquals(existingDDLEntry.getGroupId(), newDDLEntry.getGroupId());
		assertEquals(existingDDLEntry.getCompanyId(), newDDLEntry.getCompanyId());
		assertEquals(existingDDLEntry.getUserId(), newDDLEntry.getUserId());
		assertEquals(existingDDLEntry.getUserName(), newDDLEntry.getUserName());
		assertEquals(Time.getShortTimestamp(existingDDLEntry.getCreateDate()),
			Time.getShortTimestamp(newDDLEntry.getCreateDate()));
		assertEquals(Time.getShortTimestamp(existingDDLEntry.getModifiedDate()),
			Time.getShortTimestamp(newDDLEntry.getModifiedDate()));
		assertEquals(existingDDLEntry.getName(), newDDLEntry.getName());
		assertEquals(existingDDLEntry.getDescription(),
			newDDLEntry.getDescription());
		assertEquals(existingDDLEntry.getStructureId(),
			newDDLEntry.getStructureId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DDLEntry newDDLEntry = addDDLEntry();

		DDLEntry existingDDLEntry = _persistence.findByPrimaryKey(newDDLEntry.getPrimaryKey());

		assertEquals(existingDDLEntry, newDDLEntry);
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
		DDLEntry newDDLEntry = addDDLEntry();

		DDLEntry existingDDLEntry = _persistence.fetchByPrimaryKey(newDDLEntry.getPrimaryKey());

		assertEquals(existingDDLEntry, newDDLEntry);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DDLEntry missingDDLEntry = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDDLEntry);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDLEntry newDDLEntry = addDDLEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLEntry.class,
				DDLEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				newDDLEntry.getEntryId()));

		List<DDLEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DDLEntry existingDDLEntry = result.get(0);

		assertEquals(existingDDLEntry, newDDLEntry);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLEntry.class,
				DDLEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId", nextLong()));

		List<DDLEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDLEntry newDDLEntry = addDDLEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLEntry.class,
				DDLEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		Object newEntryId = newDDLEntry.getEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { newEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingEntryId = result.get(0);

		assertEquals(existingEntryId, newEntryId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLEntry.class,
				DDLEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected DDLEntry addDDLEntry() throws Exception {
		long pk = nextLong();

		DDLEntry ddlEntry = _persistence.create(pk);

		ddlEntry.setUuid(randomString());
		ddlEntry.setGroupId(nextLong());
		ddlEntry.setCompanyId(nextLong());
		ddlEntry.setUserId(nextLong());
		ddlEntry.setUserName(randomString());
		ddlEntry.setCreateDate(nextDate());
		ddlEntry.setModifiedDate(nextDate());
		ddlEntry.setName(randomString());
		ddlEntry.setDescription(randomString());
		ddlEntry.setStructureId(nextLong());

		_persistence.update(ddlEntry, false);

		return ddlEntry;
	}

	private DDLEntryPersistence _persistence;
}