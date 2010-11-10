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

import com.liferay.portal.NoSuchLayoutSetBranchException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutSetBranchPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (LayoutSetBranchPersistence)PortalBeanLocatorUtil.locate(LayoutSetBranchPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		LayoutSetBranch layoutSetBranch = _persistence.create(pk);

		assertNotNull(layoutSetBranch);

		assertEquals(layoutSetBranch.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		LayoutSetBranch newLayoutSetBranch = addLayoutSetBranch();

		_persistence.remove(newLayoutSetBranch);

		LayoutSetBranch existingLayoutSetBranch = _persistence.fetchByPrimaryKey(newLayoutSetBranch.getPrimaryKey());

		assertNull(existingLayoutSetBranch);
	}

	public void testUpdateNew() throws Exception {
		addLayoutSetBranch();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		LayoutSetBranch newLayoutSetBranch = _persistence.create(pk);

		newLayoutSetBranch.setGroupId(nextLong());
		newLayoutSetBranch.setCompanyId(nextLong());
		newLayoutSetBranch.setUserId(nextLong());
		newLayoutSetBranch.setUserName(randomString());
		newLayoutSetBranch.setCreateDate(nextDate());
		newLayoutSetBranch.setModifiedDate(nextDate());
		newLayoutSetBranch.setPrivateLayout(randomBoolean());
		newLayoutSetBranch.setName(randomString());
		newLayoutSetBranch.setDescription(randomString());

		_persistence.update(newLayoutSetBranch, false);

		LayoutSetBranch existingLayoutSetBranch = _persistence.findByPrimaryKey(newLayoutSetBranch.getPrimaryKey());

		assertEquals(existingLayoutSetBranch.getLayoutSetBranchId(),
			newLayoutSetBranch.getLayoutSetBranchId());
		assertEquals(existingLayoutSetBranch.getGroupId(),
			newLayoutSetBranch.getGroupId());
		assertEquals(existingLayoutSetBranch.getCompanyId(),
			newLayoutSetBranch.getCompanyId());
		assertEquals(existingLayoutSetBranch.getUserId(),
			newLayoutSetBranch.getUserId());
		assertEquals(existingLayoutSetBranch.getUserName(),
			newLayoutSetBranch.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingLayoutSetBranch.getCreateDate()),
			Time.getShortTimestamp(newLayoutSetBranch.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingLayoutSetBranch.getModifiedDate()),
			Time.getShortTimestamp(newLayoutSetBranch.getModifiedDate()));
		assertEquals(existingLayoutSetBranch.getPrivateLayout(),
			newLayoutSetBranch.getPrivateLayout());
		assertEquals(existingLayoutSetBranch.getName(),
			newLayoutSetBranch.getName());
		assertEquals(existingLayoutSetBranch.getDescription(),
			newLayoutSetBranch.getDescription());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutSetBranch newLayoutSetBranch = addLayoutSetBranch();

		LayoutSetBranch existingLayoutSetBranch = _persistence.findByPrimaryKey(newLayoutSetBranch.getPrimaryKey());

		assertEquals(existingLayoutSetBranch, newLayoutSetBranch);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchLayoutSetBranchException");
		}
		catch (NoSuchLayoutSetBranchException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutSetBranch newLayoutSetBranch = addLayoutSetBranch();

		LayoutSetBranch existingLayoutSetBranch = _persistence.fetchByPrimaryKey(newLayoutSetBranch.getPrimaryKey());

		assertEquals(existingLayoutSetBranch, newLayoutSetBranch);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		LayoutSetBranch missingLayoutSetBranch = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingLayoutSetBranch);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		LayoutSetBranch newLayoutSetBranch = addLayoutSetBranch();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSetBranch.class,
				LayoutSetBranch.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutSetBranchId",
				newLayoutSetBranch.getLayoutSetBranchId()));

		List<LayoutSetBranch> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		LayoutSetBranch existingLayoutSetBranch = result.get(0);

		assertEquals(existingLayoutSetBranch, newLayoutSetBranch);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSetBranch.class,
				LayoutSetBranch.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutSetBranchId",
				nextLong()));

		List<LayoutSetBranch> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected LayoutSetBranch addLayoutSetBranch() throws Exception {
		long pk = nextLong();

		LayoutSetBranch layoutSetBranch = _persistence.create(pk);

		layoutSetBranch.setGroupId(nextLong());
		layoutSetBranch.setCompanyId(nextLong());
		layoutSetBranch.setUserId(nextLong());
		layoutSetBranch.setUserName(randomString());
		layoutSetBranch.setCreateDate(nextDate());
		layoutSetBranch.setModifiedDate(nextDate());
		layoutSetBranch.setPrivateLayout(randomBoolean());
		layoutSetBranch.setName(randomString());
		layoutSetBranch.setDescription(randomString());

		_persistence.update(layoutSetBranch, false);

		return layoutSetBranch;
	}

	private LayoutSetBranchPersistence _persistence;
}