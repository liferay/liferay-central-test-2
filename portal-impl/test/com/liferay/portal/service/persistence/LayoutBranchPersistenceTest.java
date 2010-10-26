/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchLayoutBranchException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.LayoutBranch;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutBranchPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (LayoutBranchPersistence)PortalBeanLocatorUtil.locate(LayoutBranchPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		LayoutBranch layoutBranch = _persistence.create(pk);

		assertNotNull(layoutBranch);

		assertEquals(layoutBranch.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		LayoutBranch newLayoutBranch = addLayoutBranch();

		_persistence.remove(newLayoutBranch);

		LayoutBranch existingLayoutBranch = _persistence.fetchByPrimaryKey(newLayoutBranch.getPrimaryKey());

		assertNull(existingLayoutBranch);
	}

	public void testUpdateNew() throws Exception {
		addLayoutBranch();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		LayoutBranch newLayoutBranch = _persistence.create(pk);

		newLayoutBranch.setGroupId(nextLong());
		newLayoutBranch.setCompanyId(nextLong());
		newLayoutBranch.setUserId(nextLong());
		newLayoutBranch.setUserName(randomString());
		newLayoutBranch.setCreateDate(nextDate());
		newLayoutBranch.setModifiedDate(nextDate());
		newLayoutBranch.setName(randomString());
		newLayoutBranch.setDescription(randomString());

		_persistence.update(newLayoutBranch, false);

		LayoutBranch existingLayoutBranch = _persistence.findByPrimaryKey(newLayoutBranch.getPrimaryKey());

		assertEquals(existingLayoutBranch.getBranchId(),
			newLayoutBranch.getBranchId());
		assertEquals(existingLayoutBranch.getGroupId(),
			newLayoutBranch.getGroupId());
		assertEquals(existingLayoutBranch.getCompanyId(),
			newLayoutBranch.getCompanyId());
		assertEquals(existingLayoutBranch.getUserId(),
			newLayoutBranch.getUserId());
		assertEquals(existingLayoutBranch.getUserName(),
			newLayoutBranch.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingLayoutBranch.getCreateDate()),
			Time.getShortTimestamp(newLayoutBranch.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingLayoutBranch.getModifiedDate()),
			Time.getShortTimestamp(newLayoutBranch.getModifiedDate()));
		assertEquals(existingLayoutBranch.getName(), newLayoutBranch.getName());
		assertEquals(existingLayoutBranch.getDescription(),
			newLayoutBranch.getDescription());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutBranch newLayoutBranch = addLayoutBranch();

		LayoutBranch existingLayoutBranch = _persistence.findByPrimaryKey(newLayoutBranch.getPrimaryKey());

		assertEquals(existingLayoutBranch, newLayoutBranch);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchLayoutBranchException");
		}
		catch (NoSuchLayoutBranchException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutBranch newLayoutBranch = addLayoutBranch();

		LayoutBranch existingLayoutBranch = _persistence.fetchByPrimaryKey(newLayoutBranch.getPrimaryKey());

		assertEquals(existingLayoutBranch, newLayoutBranch);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		LayoutBranch missingLayoutBranch = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingLayoutBranch);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		LayoutBranch newLayoutBranch = addLayoutBranch();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutBranch.class,
				LayoutBranch.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("branchId",
				newLayoutBranch.getBranchId()));

		List<LayoutBranch> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		LayoutBranch existingLayoutBranch = result.get(0);

		assertEquals(existingLayoutBranch, newLayoutBranch);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutBranch.class,
				LayoutBranch.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("branchId", nextLong()));

		List<LayoutBranch> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected LayoutBranch addLayoutBranch() throws Exception {
		long pk = nextLong();

		LayoutBranch layoutBranch = _persistence.create(pk);

		layoutBranch.setGroupId(nextLong());
		layoutBranch.setCompanyId(nextLong());
		layoutBranch.setUserId(nextLong());
		layoutBranch.setUserName(randomString());
		layoutBranch.setCreateDate(nextDate());
		layoutBranch.setModifiedDate(nextDate());
		layoutBranch.setName(randomString());
		layoutBranch.setDescription(randomString());

		_persistence.update(layoutBranch, false);

		return layoutBranch;
	}

	private LayoutBranchPersistence _persistence;
}