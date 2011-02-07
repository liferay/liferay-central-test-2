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

package com.liferay.portlet.forms.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.forms.NoSuchStructureEntryException;
import com.liferay.portlet.forms.model.FormsStructureEntry;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class FormsStructureEntryPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (FormsStructureEntryPersistence)PortalBeanLocatorUtil.locate(FormsStructureEntryPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		FormsStructureEntry formsStructureEntry = _persistence.create(pk);

		assertNotNull(formsStructureEntry);

		assertEquals(formsStructureEntry.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		FormsStructureEntry newFormsStructureEntry = addFormsStructureEntry();

		_persistence.remove(newFormsStructureEntry);

		FormsStructureEntry existingFormsStructureEntry = _persistence.fetchByPrimaryKey(newFormsStructureEntry.getPrimaryKey());

		assertNull(existingFormsStructureEntry);
	}

	public void testUpdateNew() throws Exception {
		addFormsStructureEntry();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		FormsStructureEntry newFormsStructureEntry = _persistence.create(pk);

		newFormsStructureEntry.setUuid(randomString());
		newFormsStructureEntry.setGroupId(nextLong());
		newFormsStructureEntry.setCompanyId(nextLong());
		newFormsStructureEntry.setUserId(nextLong());
		newFormsStructureEntry.setUserName(randomString());
		newFormsStructureEntry.setCreateDate(nextDate());
		newFormsStructureEntry.setModifiedDate(nextDate());
		newFormsStructureEntry.setFormStructureId(randomString());
		newFormsStructureEntry.setName(randomString());
		newFormsStructureEntry.setDescription(randomString());
		newFormsStructureEntry.setXsd(randomString());

		_persistence.update(newFormsStructureEntry, false);

		FormsStructureEntry existingFormsStructureEntry = _persistence.findByPrimaryKey(newFormsStructureEntry.getPrimaryKey());

		assertEquals(existingFormsStructureEntry.getUuid(),
			newFormsStructureEntry.getUuid());
		assertEquals(existingFormsStructureEntry.getId(),
			newFormsStructureEntry.getId());
		assertEquals(existingFormsStructureEntry.getGroupId(),
			newFormsStructureEntry.getGroupId());
		assertEquals(existingFormsStructureEntry.getCompanyId(),
			newFormsStructureEntry.getCompanyId());
		assertEquals(existingFormsStructureEntry.getUserId(),
			newFormsStructureEntry.getUserId());
		assertEquals(existingFormsStructureEntry.getUserName(),
			newFormsStructureEntry.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingFormsStructureEntry.getCreateDate()),
			Time.getShortTimestamp(newFormsStructureEntry.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingFormsStructureEntry.getModifiedDate()),
			Time.getShortTimestamp(newFormsStructureEntry.getModifiedDate()));
		assertEquals(existingFormsStructureEntry.getFormStructureId(),
			newFormsStructureEntry.getFormStructureId());
		assertEquals(existingFormsStructureEntry.getName(),
			newFormsStructureEntry.getName());
		assertEquals(existingFormsStructureEntry.getDescription(),
			newFormsStructureEntry.getDescription());
		assertEquals(existingFormsStructureEntry.getXsd(),
			newFormsStructureEntry.getXsd());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		FormsStructureEntry newFormsStructureEntry = addFormsStructureEntry();

		FormsStructureEntry existingFormsStructureEntry = _persistence.findByPrimaryKey(newFormsStructureEntry.getPrimaryKey());

		assertEquals(existingFormsStructureEntry, newFormsStructureEntry);
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
		FormsStructureEntry newFormsStructureEntry = addFormsStructureEntry();

		FormsStructureEntry existingFormsStructureEntry = _persistence.fetchByPrimaryKey(newFormsStructureEntry.getPrimaryKey());

		assertEquals(existingFormsStructureEntry, newFormsStructureEntry);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		FormsStructureEntry missingFormsStructureEntry = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingFormsStructureEntry);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		FormsStructureEntry newFormsStructureEntry = addFormsStructureEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FormsStructureEntry.class,
				FormsStructureEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id",
				newFormsStructureEntry.getId()));

		List<FormsStructureEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		FormsStructureEntry existingFormsStructureEntry = result.get(0);

		assertEquals(existingFormsStructureEntry, newFormsStructureEntry);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FormsStructureEntry.class,
				FormsStructureEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id", nextLong()));

		List<FormsStructureEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected FormsStructureEntry addFormsStructureEntry()
		throws Exception {
		long pk = nextLong();

		FormsStructureEntry formsStructureEntry = _persistence.create(pk);

		formsStructureEntry.setUuid(randomString());
		formsStructureEntry.setGroupId(nextLong());
		formsStructureEntry.setCompanyId(nextLong());
		formsStructureEntry.setUserId(nextLong());
		formsStructureEntry.setUserName(randomString());
		formsStructureEntry.setCreateDate(nextDate());
		formsStructureEntry.setModifiedDate(nextDate());
		formsStructureEntry.setFormStructureId(randomString());
		formsStructureEntry.setName(randomString());
		formsStructureEntry.setDescription(randomString());
		formsStructureEntry.setXsd(randomString());

		_persistence.update(formsStructureEntry, false);

		return formsStructureEntry;
	}

	private FormsStructureEntryPersistence _persistence;
}