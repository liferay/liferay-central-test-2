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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchUserIdMapperException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.model.UserIdMapper;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class UserIdMapperPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (UserIdMapperPersistence)PortalBeanLocatorUtil.locate(UserIdMapperPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		UserIdMapper userIdMapper = _persistence.create(pk);

		assertNotNull(userIdMapper);

		assertEquals(userIdMapper.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		UserIdMapper newUserIdMapper = addUserIdMapper();

		_persistence.remove(newUserIdMapper);

		UserIdMapper existingUserIdMapper = _persistence.fetchByPrimaryKey(newUserIdMapper.getPrimaryKey());

		assertNull(existingUserIdMapper);
	}

	public void testUpdateNew() throws Exception {
		addUserIdMapper();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		UserIdMapper newUserIdMapper = _persistence.create(pk);

		newUserIdMapper.setUserId(nextLong());
		newUserIdMapper.setType(randomString());
		newUserIdMapper.setDescription(randomString());
		newUserIdMapper.setExternalUserId(randomString());

		_persistence.update(newUserIdMapper, false);

		UserIdMapper existingUserIdMapper = _persistence.findByPrimaryKey(newUserIdMapper.getPrimaryKey());

		assertEquals(existingUserIdMapper.getUserIdMapperId(),
			newUserIdMapper.getUserIdMapperId());
		assertEquals(existingUserIdMapper.getUserId(),
			newUserIdMapper.getUserId());
		assertEquals(existingUserIdMapper.getType(), newUserIdMapper.getType());
		assertEquals(existingUserIdMapper.getDescription(),
			newUserIdMapper.getDescription());
		assertEquals(existingUserIdMapper.getExternalUserId(),
			newUserIdMapper.getExternalUserId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		UserIdMapper newUserIdMapper = addUserIdMapper();

		UserIdMapper existingUserIdMapper = _persistence.findByPrimaryKey(newUserIdMapper.getPrimaryKey());

		assertEquals(existingUserIdMapper, newUserIdMapper);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchUserIdMapperException");
		}
		catch (NoSuchUserIdMapperException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		UserIdMapper newUserIdMapper = addUserIdMapper();

		UserIdMapper existingUserIdMapper = _persistence.fetchByPrimaryKey(newUserIdMapper.getPrimaryKey());

		assertEquals(existingUserIdMapper, newUserIdMapper);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		UserIdMapper missingUserIdMapper = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingUserIdMapper);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		UserIdMapper newUserIdMapper = addUserIdMapper();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserIdMapper.class,
				UserIdMapper.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userIdMapperId",
				newUserIdMapper.getUserIdMapperId()));

		List<UserIdMapper> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		UserIdMapper existingUserIdMapper = result.get(0);

		assertEquals(existingUserIdMapper, newUserIdMapper);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserIdMapper.class,
				UserIdMapper.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userIdMapperId", nextLong()));

		List<UserIdMapper> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected UserIdMapper addUserIdMapper() throws Exception {
		long pk = nextLong();

		UserIdMapper userIdMapper = _persistence.create(pk);

		userIdMapper.setUserId(nextLong());
		userIdMapper.setType(randomString());
		userIdMapper.setDescription(randomString());
		userIdMapper.setExternalUserId(randomString());

		_persistence.update(userIdMapper, false);

		return userIdMapper;
	}

	private UserIdMapperPersistence _persistence;
}