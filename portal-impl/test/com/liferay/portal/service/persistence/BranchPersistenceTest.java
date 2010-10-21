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

import com.liferay.portal.NoSuchBranchException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Branch;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class BranchPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (BranchPersistence)PortalBeanLocatorUtil.locate(BranchPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Branch branch = _persistence.create(pk);

		assertNotNull(branch);

		assertEquals(branch.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Branch newBranch = addBranch();

		_persistence.remove(newBranch);

		Branch existingBranch = _persistence.fetchByPrimaryKey(newBranch.getPrimaryKey());

		assertNull(existingBranch);
	}

	public void testUpdateNew() throws Exception {
		addBranch();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Branch newBranch = _persistence.create(pk);

		newBranch.setGroupId(nextLong());
		newBranch.setCompanyId(nextLong());
		newBranch.setUserId(nextLong());
		newBranch.setUserName(randomString());
		newBranch.setCreateDate(nextDate());
		newBranch.setModifiedDate(nextDate());
		newBranch.setName(randomString());
		newBranch.setDescription(randomString());

		_persistence.update(newBranch, false);

		Branch existingBranch = _persistence.findByPrimaryKey(newBranch.getPrimaryKey());

		assertEquals(existingBranch.getBranchId(), newBranch.getBranchId());
		assertEquals(existingBranch.getGroupId(), newBranch.getGroupId());
		assertEquals(existingBranch.getCompanyId(), newBranch.getCompanyId());
		assertEquals(existingBranch.getUserId(), newBranch.getUserId());
		assertEquals(existingBranch.getUserName(), newBranch.getUserName());
		assertEquals(Time.getShortTimestamp(existingBranch.getCreateDate()),
			Time.getShortTimestamp(newBranch.getCreateDate()));
		assertEquals(Time.getShortTimestamp(existingBranch.getModifiedDate()),
			Time.getShortTimestamp(newBranch.getModifiedDate()));
		assertEquals(existingBranch.getName(), newBranch.getName());
		assertEquals(existingBranch.getDescription(), newBranch.getDescription());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Branch newBranch = addBranch();

		Branch existingBranch = _persistence.findByPrimaryKey(newBranch.getPrimaryKey());

		assertEquals(existingBranch, newBranch);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchBranchException");
		}
		catch (NoSuchBranchException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Branch newBranch = addBranch();

		Branch existingBranch = _persistence.fetchByPrimaryKey(newBranch.getPrimaryKey());

		assertEquals(existingBranch, newBranch);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Branch missingBranch = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingBranch);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Branch newBranch = addBranch();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Branch.class,
				Branch.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("branchId",
				newBranch.getBranchId()));

		List<Branch> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Branch existingBranch = result.get(0);

		assertEquals(existingBranch, newBranch);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Branch.class,
				Branch.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("branchId", nextLong()));

		List<Branch> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected Branch addBranch() throws Exception {
		long pk = nextLong();

		Branch branch = _persistence.create(pk);

		branch.setGroupId(nextLong());
		branch.setCompanyId(nextLong());
		branch.setUserId(nextLong());
		branch.setUserName(randomString());
		branch.setCreateDate(nextDate());
		branch.setModifiedDate(nextDate());
		branch.setName(randomString());
		branch.setDescription(randomString());

		_persistence.update(branch, false);

		return branch;
	}

	private BranchPersistence _persistence;
}