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
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.dynamicdatamapping.NoSuchListException;
import com.liferay.portlet.dynamicdatamapping.model.DDMList;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMListModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMListPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DDMListPersistence)PortalBeanLocatorUtil.locate(DDMListPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DDMList ddmList = _persistence.create(pk);

		assertNotNull(ddmList);

		assertEquals(ddmList.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DDMList newDDMList = addDDMList();

		_persistence.remove(newDDMList);

		DDMList existingDDMList = _persistence.fetchByPrimaryKey(newDDMList.getPrimaryKey());

		assertNull(existingDDMList);
	}

	public void testUpdateNew() throws Exception {
		addDDMList();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DDMList newDDMList = _persistence.create(pk);

		newDDMList.setUuid(randomString());
		newDDMList.setGroupId(nextLong());
		newDDMList.setCompanyId(nextLong());
		newDDMList.setUserId(nextLong());
		newDDMList.setUserName(randomString());
		newDDMList.setCreateDate(nextDate());
		newDDMList.setModifiedDate(nextDate());
		newDDMList.setName(randomString());
		newDDMList.setDescription(randomString());
		newDDMList.setStructureId(nextLong());

		_persistence.update(newDDMList, false);

		DDMList existingDDMList = _persistence.findByPrimaryKey(newDDMList.getPrimaryKey());

		assertEquals(existingDDMList.getUuid(), newDDMList.getUuid());
		assertEquals(existingDDMList.getListId(), newDDMList.getListId());
		assertEquals(existingDDMList.getGroupId(), newDDMList.getGroupId());
		assertEquals(existingDDMList.getCompanyId(), newDDMList.getCompanyId());
		assertEquals(existingDDMList.getUserId(), newDDMList.getUserId());
		assertEquals(existingDDMList.getUserName(), newDDMList.getUserName());
		assertEquals(Time.getShortTimestamp(existingDDMList.getCreateDate()),
			Time.getShortTimestamp(newDDMList.getCreateDate()));
		assertEquals(Time.getShortTimestamp(existingDDMList.getModifiedDate()),
			Time.getShortTimestamp(newDDMList.getModifiedDate()));
		assertEquals(existingDDMList.getName(), newDDMList.getName());
		assertEquals(existingDDMList.getDescription(),
			newDDMList.getDescription());
		assertEquals(existingDDMList.getStructureId(),
			newDDMList.getStructureId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DDMList newDDMList = addDDMList();

		DDMList existingDDMList = _persistence.findByPrimaryKey(newDDMList.getPrimaryKey());

		assertEquals(existingDDMList, newDDMList);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchListException");
		}
		catch (NoSuchListException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDMList newDDMList = addDDMList();

		DDMList existingDDMList = _persistence.fetchByPrimaryKey(newDDMList.getPrimaryKey());

		assertEquals(existingDDMList, newDDMList);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DDMList missingDDMList = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDDMList);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDMList newDDMList = addDDMList();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMList.class,
				DDMList.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("listId",
				newDDMList.getListId()));

		List<DDMList> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DDMList existingDDMList = result.get(0);

		assertEquals(existingDDMList, newDDMList);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMList.class,
				DDMList.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("listId", nextLong()));

		List<DDMList> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDMList newDDMList = addDDMList();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMList.class,
				DDMList.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("listId"));

		Object newListId = newDDMList.getListId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("listId",
				new Object[] { newListId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingListId = result.get(0);

		assertEquals(existingListId, newListId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMList.class,
				DDMList.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("listId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("listId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DDMList newDDMList = addDDMList();

		_persistence.clearCache();

		DDMListModelImpl existingDDMListModelImpl = (DDMListModelImpl)_persistence.findByPrimaryKey(newDDMList.getPrimaryKey());

		assertTrue(Validator.equals(existingDDMListModelImpl.getUuid(),
				existingDDMListModelImpl.getOriginalUuid()));
		assertEquals(existingDDMListModelImpl.getGroupId(),
			existingDDMListModelImpl.getOriginalGroupId());
	}

	protected DDMList addDDMList() throws Exception {
		long pk = nextLong();

		DDMList ddmList = _persistence.create(pk);

		ddmList.setUuid(randomString());
		ddmList.setGroupId(nextLong());
		ddmList.setCompanyId(nextLong());
		ddmList.setUserId(nextLong());
		ddmList.setUserName(randomString());
		ddmList.setCreateDate(nextDate());
		ddmList.setModifiedDate(nextDate());
		ddmList.setName(randomString());
		ddmList.setDescription(randomString());
		ddmList.setStructureId(nextLong());

		_persistence.update(ddmList, false);

		return ddmList;
	}

	private DDMListPersistence _persistence;
}