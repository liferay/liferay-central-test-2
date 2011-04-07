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

package com.liferay.portlet.dynamicdatalists.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.dynamicdatalists.NoSuchEntryItemException;
import com.liferay.portlet.dynamicdatalists.model.DDLEntryItem;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DDLEntryItemPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DDLEntryItemPersistence)PortalBeanLocatorUtil.locate(DDLEntryItemPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DDLEntryItem ddlEntryItem = _persistence.create(pk);

		assertNotNull(ddlEntryItem);

		assertEquals(ddlEntryItem.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DDLEntryItem newDDLEntryItem = addDDLEntryItem();

		_persistence.remove(newDDLEntryItem);

		DDLEntryItem existingDDLEntryItem = _persistence.fetchByPrimaryKey(newDDLEntryItem.getPrimaryKey());

		assertNull(existingDDLEntryItem);
	}

	public void testUpdateNew() throws Exception {
		addDDLEntryItem();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DDLEntryItem newDDLEntryItem = _persistence.create(pk);

		newDDLEntryItem.setUuid(randomString());
		newDDLEntryItem.setClassNameId(nextLong());
		newDDLEntryItem.setClassPK(nextLong());
		newDDLEntryItem.setEntryId(nextLong());

		_persistence.update(newDDLEntryItem, false);

		DDLEntryItem existingDDLEntryItem = _persistence.findByPrimaryKey(newDDLEntryItem.getPrimaryKey());

		assertEquals(existingDDLEntryItem.getUuid(), newDDLEntryItem.getUuid());
		assertEquals(existingDDLEntryItem.getEntryItemId(),
			newDDLEntryItem.getEntryItemId());
		assertEquals(existingDDLEntryItem.getClassNameId(),
			newDDLEntryItem.getClassNameId());
		assertEquals(existingDDLEntryItem.getClassPK(),
			newDDLEntryItem.getClassPK());
		assertEquals(existingDDLEntryItem.getEntryId(),
			newDDLEntryItem.getEntryId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DDLEntryItem newDDLEntryItem = addDDLEntryItem();

		DDLEntryItem existingDDLEntryItem = _persistence.findByPrimaryKey(newDDLEntryItem.getPrimaryKey());

		assertEquals(existingDDLEntryItem, newDDLEntryItem);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchEntryItemException");
		}
		catch (NoSuchEntryItemException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDLEntryItem newDDLEntryItem = addDDLEntryItem();

		DDLEntryItem existingDDLEntryItem = _persistence.fetchByPrimaryKey(newDDLEntryItem.getPrimaryKey());

		assertEquals(existingDDLEntryItem, newDDLEntryItem);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DDLEntryItem missingDDLEntryItem = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDDLEntryItem);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDLEntryItem newDDLEntryItem = addDDLEntryItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLEntryItem.class,
				DDLEntryItem.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryItemId",
				newDDLEntryItem.getEntryItemId()));

		List<DDLEntryItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DDLEntryItem existingDDLEntryItem = result.get(0);

		assertEquals(existingDDLEntryItem, newDDLEntryItem);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLEntryItem.class,
				DDLEntryItem.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryItemId", nextLong()));

		List<DDLEntryItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDLEntryItem newDDLEntryItem = addDDLEntryItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLEntryItem.class,
				DDLEntryItem.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryItemId"));

		Object newEntryItemId = newDDLEntryItem.getEntryItemId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryItemId",
				new Object[] { newEntryItemId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingEntryItemId = result.get(0);

		assertEquals(existingEntryItemId, newEntryItemId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLEntryItem.class,
				DDLEntryItem.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryItemId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryItemId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected DDLEntryItem addDDLEntryItem() throws Exception {
		long pk = nextLong();

		DDLEntryItem ddlEntryItem = _persistence.create(pk);

		ddlEntryItem.setUuid(randomString());
		ddlEntryItem.setClassNameId(nextLong());
		ddlEntryItem.setClassPK(nextLong());
		ddlEntryItem.setEntryId(nextLong());

		_persistence.update(ddlEntryItem, false);

		return ddlEntryItem;
	}

	private DDLEntryItemPersistence _persistence;
}