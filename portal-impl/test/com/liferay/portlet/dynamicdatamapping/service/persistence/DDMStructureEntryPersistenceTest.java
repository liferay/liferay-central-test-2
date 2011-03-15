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
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMStructureEntryPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DDMStructureEntryPersistence)PortalBeanLocatorUtil.locate(DDMStructureEntryPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DDMStructureEntry ddmStructureEntry = _persistence.create(pk);

		assertNotNull(ddmStructureEntry);

		assertEquals(ddmStructureEntry.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DDMStructureEntry newDDMStructureEntry = addDDMStructureEntry();

		_persistence.remove(newDDMStructureEntry);

		DDMStructureEntry existingDDMStructureEntry = _persistence.fetchByPrimaryKey(newDDMStructureEntry.getPrimaryKey());

		assertNull(existingDDMStructureEntry);
	}

	public void testUpdateNew() throws Exception {
		addDDMStructureEntry();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DDMStructureEntry newDDMStructureEntry = _persistence.create(pk);

		newDDMStructureEntry.setUuid(randomString());
		newDDMStructureEntry.setGroupId(nextLong());
		newDDMStructureEntry.setCompanyId(nextLong());
		newDDMStructureEntry.setUserId(nextLong());
		newDDMStructureEntry.setUserName(randomString());
		newDDMStructureEntry.setCreateDate(nextDate());
		newDDMStructureEntry.setModifiedDate(nextDate());
		newDDMStructureEntry.setStructureId(randomString());
		newDDMStructureEntry.setName(randomString());
		newDDMStructureEntry.setDescription(randomString());
		newDDMStructureEntry.setXsd(randomString());

		_persistence.update(newDDMStructureEntry, false);

		DDMStructureEntry existingDDMStructureEntry = _persistence.findByPrimaryKey(newDDMStructureEntry.getPrimaryKey());

		assertEquals(existingDDMStructureEntry.getUuid(),
			newDDMStructureEntry.getUuid());
		assertEquals(existingDDMStructureEntry.getStructureEntryId(),
			newDDMStructureEntry.getStructureEntryId());
		assertEquals(existingDDMStructureEntry.getGroupId(),
			newDDMStructureEntry.getGroupId());
		assertEquals(existingDDMStructureEntry.getCompanyId(),
			newDDMStructureEntry.getCompanyId());
		assertEquals(existingDDMStructureEntry.getUserId(),
			newDDMStructureEntry.getUserId());
		assertEquals(existingDDMStructureEntry.getUserName(),
			newDDMStructureEntry.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingDDMStructureEntry.getCreateDate()),
			Time.getShortTimestamp(newDDMStructureEntry.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingDDMStructureEntry.getModifiedDate()),
			Time.getShortTimestamp(newDDMStructureEntry.getModifiedDate()));
		assertEquals(existingDDMStructureEntry.getStructureId(),
			newDDMStructureEntry.getStructureId());
		assertEquals(existingDDMStructureEntry.getName(),
			newDDMStructureEntry.getName());
		assertEquals(existingDDMStructureEntry.getDescription(),
			newDDMStructureEntry.getDescription());
		assertEquals(existingDDMStructureEntry.getXsd(),
			newDDMStructureEntry.getXsd());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DDMStructureEntry newDDMStructureEntry = addDDMStructureEntry();

		DDMStructureEntry existingDDMStructureEntry = _persistence.findByPrimaryKey(newDDMStructureEntry.getPrimaryKey());

		assertEquals(existingDDMStructureEntry, newDDMStructureEntry);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchStructureEntryException");
		}
		catch (NoSuchStructureEntryException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDMStructureEntry newDDMStructureEntry = addDDMStructureEntry();

		DDMStructureEntry existingDDMStructureEntry = _persistence.fetchByPrimaryKey(newDDMStructureEntry.getPrimaryKey());

		assertEquals(existingDDMStructureEntry, newDDMStructureEntry);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DDMStructureEntry missingDDMStructureEntry = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDDMStructureEntry);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDMStructureEntry newDDMStructureEntry = addDDMStructureEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructureEntry.class,
				DDMStructureEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("structureEntryId",
				newDDMStructureEntry.getStructureEntryId()));

		List<DDMStructureEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DDMStructureEntry existingDDMStructureEntry = result.get(0);

		assertEquals(existingDDMStructureEntry, newDDMStructureEntry);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructureEntry.class,
				DDMStructureEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("structureEntryId",
				nextLong()));

		List<DDMStructureEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected DDMStructureEntry addDDMStructureEntry()
		throws Exception {
		long pk = nextLong();

		DDMStructureEntry ddmStructureEntry = _persistence.create(pk);

		ddmStructureEntry.setUuid(randomString());
		ddmStructureEntry.setGroupId(nextLong());
		ddmStructureEntry.setCompanyId(nextLong());
		ddmStructureEntry.setUserId(nextLong());
		ddmStructureEntry.setUserName(randomString());
		ddmStructureEntry.setCreateDate(nextDate());
		ddmStructureEntry.setModifiedDate(nextDate());
		ddmStructureEntry.setStructureId(randomString());
		ddmStructureEntry.setName(randomString());
		ddmStructureEntry.setDescription(randomString());
		ddmStructureEntry.setXsd(randomString());

		_persistence.update(ddmStructureEntry, false);

		return ddmStructureEntry;
	}

	private DDMStructureEntryPersistence _persistence;
}