/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchUserGroupRoleException;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.test.RandomTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @generated
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class UserGroupRolePersistenceTest {
	@ClassRule
	public static TransactionalTestRule transactionalTestRule = new TransactionalTestRule(Propagation.REQUIRED);

	@BeforeClass
	public static void setupClass() throws TemplateException {
		try {
			DBUpgrader.upgrade();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		TemplateManagerUtil.init();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<UserGroupRole> iterator = _userGroupRoles.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		UserGroupRolePK pk = new UserGroupRolePK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		UserGroupRole userGroupRole = _persistence.create(pk);

		Assert.assertNotNull(userGroupRole);

		Assert.assertEquals(userGroupRole.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		UserGroupRole newUserGroupRole = addUserGroupRole();

		_persistence.remove(newUserGroupRole);

		UserGroupRole existingUserGroupRole = _persistence.fetchByPrimaryKey(newUserGroupRole.getPrimaryKey());

		Assert.assertNull(existingUserGroupRole);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addUserGroupRole();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		UserGroupRolePK pk = new UserGroupRolePK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		UserGroupRole newUserGroupRole = _persistence.create(pk);

		newUserGroupRole.setMvccVersion(RandomTestUtil.nextLong());

		_userGroupRoles.add(_persistence.update(newUserGroupRole));

		UserGroupRole existingUserGroupRole = _persistence.findByPrimaryKey(newUserGroupRole.getPrimaryKey());

		Assert.assertEquals(existingUserGroupRole.getMvccVersion(),
			newUserGroupRole.getMvccVersion());
		Assert.assertEquals(existingUserGroupRole.getUserId(),
			newUserGroupRole.getUserId());
		Assert.assertEquals(existingUserGroupRole.getGroupId(),
			newUserGroupRole.getGroupId());
		Assert.assertEquals(existingUserGroupRole.getRoleId(),
			newUserGroupRole.getRoleId());
	}

	@Test
	public void testCountByUserId() {
		try {
			_persistence.countByUserId(RandomTestUtil.nextLong());

			_persistence.countByUserId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByGroupId() {
		try {
			_persistence.countByGroupId(RandomTestUtil.nextLong());

			_persistence.countByGroupId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByRoleId() {
		try {
			_persistence.countByRoleId(RandomTestUtil.nextLong());

			_persistence.countByRoleId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByU_G() {
		try {
			_persistence.countByU_G(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByU_G(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_R() {
		try {
			_persistence.countByG_R(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByG_R(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		UserGroupRole newUserGroupRole = addUserGroupRole();

		UserGroupRole existingUserGroupRole = _persistence.findByPrimaryKey(newUserGroupRole.getPrimaryKey());

		Assert.assertEquals(existingUserGroupRole, newUserGroupRole);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		UserGroupRolePK pk = new UserGroupRolePK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchUserGroupRoleException");
		}
		catch (NoSuchUserGroupRoleException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		UserGroupRole newUserGroupRole = addUserGroupRole();

		UserGroupRole existingUserGroupRole = _persistence.fetchByPrimaryKey(newUserGroupRole.getPrimaryKey());

		Assert.assertEquals(existingUserGroupRole, newUserGroupRole);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		UserGroupRolePK pk = new UserGroupRolePK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		UserGroupRole missingUserGroupRole = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingUserGroupRole);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		UserGroupRole newUserGroupRole1 = addUserGroupRole();
		UserGroupRole newUserGroupRole2 = addUserGroupRole();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newUserGroupRole1.getPrimaryKey());
		primaryKeys.add(newUserGroupRole2.getPrimaryKey());

		Map<Serializable, UserGroupRole> userGroupRoles = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, userGroupRoles.size());
		Assert.assertEquals(newUserGroupRole1,
			userGroupRoles.get(newUserGroupRole1.getPrimaryKey()));
		Assert.assertEquals(newUserGroupRole2,
			userGroupRoles.get(newUserGroupRole2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		UserGroupRolePK pk1 = new UserGroupRolePK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		UserGroupRolePK pk2 = new UserGroupRolePK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, UserGroupRole> userGroupRoles = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(userGroupRoles.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		UserGroupRole newUserGroupRole = addUserGroupRole();

		UserGroupRolePK pk = new UserGroupRolePK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newUserGroupRole.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, UserGroupRole> userGroupRoles = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, userGroupRoles.size());
		Assert.assertEquals(newUserGroupRole,
			userGroupRoles.get(newUserGroupRole.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, UserGroupRole> userGroupRoles = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(userGroupRoles.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		UserGroupRole newUserGroupRole = addUserGroupRole();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newUserGroupRole.getPrimaryKey());

		Map<Serializable, UserGroupRole> userGroupRoles = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, userGroupRoles.size());
		Assert.assertEquals(newUserGroupRole,
			userGroupRoles.get(newUserGroupRole.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = UserGroupRoleLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					UserGroupRole userGroupRole = (UserGroupRole)object;

					Assert.assertNotNull(userGroupRole);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		UserGroupRole newUserGroupRole = addUserGroupRole();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserGroupRole.class,
				UserGroupRole.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.userId",
				newUserGroupRole.getUserId()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.groupId",
				newUserGroupRole.getGroupId()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.roleId",
				newUserGroupRole.getRoleId()));

		List<UserGroupRole> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		UserGroupRole existingUserGroupRole = result.get(0);

		Assert.assertEquals(existingUserGroupRole, newUserGroupRole);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserGroupRole.class,
				UserGroupRole.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.userId",
				RandomTestUtil.nextLong()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.groupId",
				RandomTestUtil.nextLong()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.roleId",
				RandomTestUtil.nextLong()));

		List<UserGroupRole> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		UserGroupRole newUserGroupRole = addUserGroupRole();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserGroupRole.class,
				UserGroupRole.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("id.userId"));

		Object newUserId = newUserGroupRole.getUserId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("id.userId",
				new Object[] { newUserId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingUserId = result.get(0);

		Assert.assertEquals(existingUserId, newUserId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserGroupRole.class,
				UserGroupRole.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("id.userId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("id.userId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected UserGroupRole addUserGroupRole() throws Exception {
		UserGroupRolePK pk = new UserGroupRolePK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		UserGroupRole userGroupRole = _persistence.create(pk);

		userGroupRole.setMvccVersion(RandomTestUtil.nextLong());

		_userGroupRoles.add(_persistence.update(userGroupRole));

		return userGroupRole;
	}

	private static Log _log = LogFactoryUtil.getLog(UserGroupRolePersistenceTest.class);
	private List<UserGroupRole> _userGroupRoles = new ArrayList<UserGroupRole>();
	private ModelListener<UserGroupRole>[] _modelListeners;
	private UserGroupRolePersistence _persistence = UserGroupRoleUtil.getPersistence();
}