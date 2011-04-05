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

import com.liferay.portlet.dynamicdatamapping.NoSuchListItemException;
import com.liferay.portlet.dynamicdatamapping.model.DDMListItem;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMListItemPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DDMListItemPersistence)PortalBeanLocatorUtil.locate(DDMListItemPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DDMListItem ddmListItem = _persistence.create(pk);

		assertNotNull(ddmListItem);

		assertEquals(ddmListItem.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DDMListItem newDDMListItem = addDDMListItem();

		_persistence.remove(newDDMListItem);

		DDMListItem existingDDMListItem = _persistence.fetchByPrimaryKey(newDDMListItem.getPrimaryKey());

		assertNull(existingDDMListItem);
	}

	public void testUpdateNew() throws Exception {
		addDDMListItem();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DDMListItem newDDMListItem = _persistence.create(pk);

		newDDMListItem.setUuid(randomString());
		newDDMListItem.setClassNameId(nextLong());
		newDDMListItem.setClassPK(nextLong());
		newDDMListItem.setListId(nextLong());

		_persistence.update(newDDMListItem, false);

		DDMListItem existingDDMListItem = _persistence.findByPrimaryKey(newDDMListItem.getPrimaryKey());

		assertEquals(existingDDMListItem.getUuid(), newDDMListItem.getUuid());
		assertEquals(existingDDMListItem.getListItemId(),
			newDDMListItem.getListItemId());
		assertEquals(existingDDMListItem.getClassNameId(),
			newDDMListItem.getClassNameId());
		assertEquals(existingDDMListItem.getClassPK(),
			newDDMListItem.getClassPK());
		assertEquals(existingDDMListItem.getListId(), newDDMListItem.getListId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DDMListItem newDDMListItem = addDDMListItem();

		DDMListItem existingDDMListItem = _persistence.findByPrimaryKey(newDDMListItem.getPrimaryKey());

		assertEquals(existingDDMListItem, newDDMListItem);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchListItemException");
		}
		catch (NoSuchListItemException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDMListItem newDDMListItem = addDDMListItem();

		DDMListItem existingDDMListItem = _persistence.fetchByPrimaryKey(newDDMListItem.getPrimaryKey());

		assertEquals(existingDDMListItem, newDDMListItem);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DDMListItem missingDDMListItem = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDDMListItem);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDMListItem newDDMListItem = addDDMListItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMListItem.class,
				DDMListItem.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("listItemId",
				newDDMListItem.getListItemId()));

		List<DDMListItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DDMListItem existingDDMListItem = result.get(0);

		assertEquals(existingDDMListItem, newDDMListItem);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMListItem.class,
				DDMListItem.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("listItemId", nextLong()));

		List<DDMListItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDMListItem newDDMListItem = addDDMListItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMListItem.class,
				DDMListItem.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("listItemId"));

		Object newListItemId = newDDMListItem.getListItemId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("listItemId",
				new Object[] { newListItemId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingListItemId = result.get(0);

		assertEquals(existingListItemId, newListItemId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMListItem.class,
				DDMListItem.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("listItemId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("listItemId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected DDMListItem addDDMListItem() throws Exception {
		long pk = nextLong();

		DDMListItem ddmListItem = _persistence.create(pk);

		ddmListItem.setUuid(randomString());
		ddmListItem.setClassNameId(nextLong());
		ddmListItem.setClassPK(nextLong());
		ddmListItem.setListId(nextLong());

		_persistence.update(ddmListItem, false);

		return ddmListItem;
	}

	private DDMListItemPersistence _persistence;
}