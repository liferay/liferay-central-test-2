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

import com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMStructureEntryLinkPersistenceTest
	extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DDMStructureEntryLinkPersistence)PortalBeanLocatorUtil.locate(DDMStructureEntryLinkPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DDMStructureEntryLink ddmStructureEntryLink = _persistence.create(pk);

		assertNotNull(ddmStructureEntryLink);

		assertEquals(ddmStructureEntryLink.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DDMStructureEntryLink newDDMStructureEntryLink = addDDMStructureEntryLink();

		_persistence.remove(newDDMStructureEntryLink);

		DDMStructureEntryLink existingDDMStructureEntryLink = _persistence.fetchByPrimaryKey(newDDMStructureEntryLink.getPrimaryKey());

		assertNull(existingDDMStructureEntryLink);
	}

	public void testUpdateNew() throws Exception {
		addDDMStructureEntryLink();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DDMStructureEntryLink newDDMStructureEntryLink = _persistence.create(pk);

		newDDMStructureEntryLink.setStructureId(randomString());
		newDDMStructureEntryLink.setClassName(randomString());
		newDDMStructureEntryLink.setClassPK(nextLong());

		_persistence.update(newDDMStructureEntryLink, false);

		DDMStructureEntryLink existingDDMStructureEntryLink = _persistence.findByPrimaryKey(newDDMStructureEntryLink.getPrimaryKey());

		assertEquals(existingDDMStructureEntryLink.getStructureEntryLinkId(),
			newDDMStructureEntryLink.getStructureEntryLinkId());
		assertEquals(existingDDMStructureEntryLink.getStructureId(),
			newDDMStructureEntryLink.getStructureId());
		assertEquals(existingDDMStructureEntryLink.getClassName(),
			newDDMStructureEntryLink.getClassName());
		assertEquals(existingDDMStructureEntryLink.getClassPK(),
			newDDMStructureEntryLink.getClassPK());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DDMStructureEntryLink newDDMStructureEntryLink = addDDMStructureEntryLink();

		DDMStructureEntryLink existingDDMStructureEntryLink = _persistence.findByPrimaryKey(newDDMStructureEntryLink.getPrimaryKey());

		assertEquals(existingDDMStructureEntryLink, newDDMStructureEntryLink);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail(
				"Missing entity did not throw NoSuchStructureEntryLinkException");
		}
		catch (NoSuchStructureEntryLinkException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDMStructureEntryLink newDDMStructureEntryLink = addDDMStructureEntryLink();

		DDMStructureEntryLink existingDDMStructureEntryLink = _persistence.fetchByPrimaryKey(newDDMStructureEntryLink.getPrimaryKey());

		assertEquals(existingDDMStructureEntryLink, newDDMStructureEntryLink);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DDMStructureEntryLink missingDDMStructureEntryLink = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDDMStructureEntryLink);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDMStructureEntryLink newDDMStructureEntryLink = addDDMStructureEntryLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructureEntryLink.class,
				DDMStructureEntryLink.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("structureEntryLinkId",
				newDDMStructureEntryLink.getStructureEntryLinkId()));

		List<DDMStructureEntryLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DDMStructureEntryLink existingDDMStructureEntryLink = result.get(0);

		assertEquals(existingDDMStructureEntryLink, newDDMStructureEntryLink);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructureEntryLink.class,
				DDMStructureEntryLink.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("structureEntryLinkId",
				nextLong()));

		List<DDMStructureEntryLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected DDMStructureEntryLink addDDMStructureEntryLink()
		throws Exception {
		long pk = nextLong();

		DDMStructureEntryLink ddmStructureEntryLink = _persistence.create(pk);

		ddmStructureEntryLink.setStructureId(randomString());
		ddmStructureEntryLink.setClassName(randomString());
		ddmStructureEntryLink.setClassPK(nextLong());

		_persistence.update(ddmStructureEntryLink, false);

		return ddmStructureEntryLink;
	}

	private DDMStructureEntryLinkPersistence _persistence;
}