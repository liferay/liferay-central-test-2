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

import com.liferay.portal.NoSuchOrgGroupRoleException;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.OrgGroupRole;
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
public class OrgGroupRolePersistenceTest {
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
		Iterator<OrgGroupRole> iterator = _orgGroupRoles.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		OrgGroupRolePK pk = new OrgGroupRolePK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		OrgGroupRole orgGroupRole = _persistence.create(pk);

		Assert.assertNotNull(orgGroupRole);

		Assert.assertEquals(orgGroupRole.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		OrgGroupRole newOrgGroupRole = addOrgGroupRole();

		_persistence.remove(newOrgGroupRole);

		OrgGroupRole existingOrgGroupRole = _persistence.fetchByPrimaryKey(newOrgGroupRole.getPrimaryKey());

		Assert.assertNull(existingOrgGroupRole);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addOrgGroupRole();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		OrgGroupRolePK pk = new OrgGroupRolePK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		OrgGroupRole newOrgGroupRole = _persistence.create(pk);

		newOrgGroupRole.setMvccVersion(RandomTestUtil.nextLong());

		_orgGroupRoles.add(_persistence.update(newOrgGroupRole));

		OrgGroupRole existingOrgGroupRole = _persistence.findByPrimaryKey(newOrgGroupRole.getPrimaryKey());

		Assert.assertEquals(existingOrgGroupRole.getMvccVersion(),
			newOrgGroupRole.getMvccVersion());
		Assert.assertEquals(existingOrgGroupRole.getOrganizationId(),
			newOrgGroupRole.getOrganizationId());
		Assert.assertEquals(existingOrgGroupRole.getGroupId(),
			newOrgGroupRole.getGroupId());
		Assert.assertEquals(existingOrgGroupRole.getRoleId(),
			newOrgGroupRole.getRoleId());
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
	public void testFindByPrimaryKeyExisting() throws Exception {
		OrgGroupRole newOrgGroupRole = addOrgGroupRole();

		OrgGroupRole existingOrgGroupRole = _persistence.findByPrimaryKey(newOrgGroupRole.getPrimaryKey());

		Assert.assertEquals(existingOrgGroupRole, newOrgGroupRole);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		OrgGroupRolePK pk = new OrgGroupRolePK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchOrgGroupRoleException");
		}
		catch (NoSuchOrgGroupRoleException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		OrgGroupRole newOrgGroupRole = addOrgGroupRole();

		OrgGroupRole existingOrgGroupRole = _persistence.fetchByPrimaryKey(newOrgGroupRole.getPrimaryKey());

		Assert.assertEquals(existingOrgGroupRole, newOrgGroupRole);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		OrgGroupRolePK pk = new OrgGroupRolePK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		OrgGroupRole missingOrgGroupRole = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingOrgGroupRole);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		OrgGroupRole newOrgGroupRole1 = addOrgGroupRole();
		OrgGroupRole newOrgGroupRole2 = addOrgGroupRole();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newOrgGroupRole1.getPrimaryKey());
		primaryKeys.add(newOrgGroupRole2.getPrimaryKey());

		Map<Serializable, OrgGroupRole> orgGroupRoles = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, orgGroupRoles.size());
		Assert.assertEquals(newOrgGroupRole1,
			orgGroupRoles.get(newOrgGroupRole1.getPrimaryKey()));
		Assert.assertEquals(newOrgGroupRole2,
			orgGroupRoles.get(newOrgGroupRole2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		OrgGroupRolePK pk1 = new OrgGroupRolePK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		OrgGroupRolePK pk2 = new OrgGroupRolePK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, OrgGroupRole> orgGroupRoles = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(orgGroupRoles.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		OrgGroupRole newOrgGroupRole = addOrgGroupRole();

		OrgGroupRolePK pk = new OrgGroupRolePK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newOrgGroupRole.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, OrgGroupRole> orgGroupRoles = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, orgGroupRoles.size());
		Assert.assertEquals(newOrgGroupRole,
			orgGroupRoles.get(newOrgGroupRole.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, OrgGroupRole> orgGroupRoles = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(orgGroupRoles.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		OrgGroupRole newOrgGroupRole = addOrgGroupRole();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newOrgGroupRole.getPrimaryKey());

		Map<Serializable, OrgGroupRole> orgGroupRoles = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, orgGroupRoles.size());
		Assert.assertEquals(newOrgGroupRole,
			orgGroupRoles.get(newOrgGroupRole.getPrimaryKey()));
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		OrgGroupRole newOrgGroupRole = addOrgGroupRole();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OrgGroupRole.class,
				OrgGroupRole.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.organizationId",
				newOrgGroupRole.getOrganizationId()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.groupId",
				newOrgGroupRole.getGroupId()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.roleId",
				newOrgGroupRole.getRoleId()));

		List<OrgGroupRole> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		OrgGroupRole existingOrgGroupRole = result.get(0);

		Assert.assertEquals(existingOrgGroupRole, newOrgGroupRole);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OrgGroupRole.class,
				OrgGroupRole.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.organizationId",
				RandomTestUtil.nextLong()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.groupId",
				RandomTestUtil.nextLong()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.roleId",
				RandomTestUtil.nextLong()));

		List<OrgGroupRole> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		OrgGroupRole newOrgGroupRole = addOrgGroupRole();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OrgGroupRole.class,
				OrgGroupRole.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"id.organizationId"));

		Object newOrganizationId = newOrgGroupRole.getOrganizationId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("id.organizationId",
				new Object[] { newOrganizationId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingOrganizationId = result.get(0);

		Assert.assertEquals(existingOrganizationId, newOrganizationId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OrgGroupRole.class,
				OrgGroupRole.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"id.organizationId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("id.organizationId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected OrgGroupRole addOrgGroupRole() throws Exception {
		OrgGroupRolePK pk = new OrgGroupRolePK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		OrgGroupRole orgGroupRole = _persistence.create(pk);

		orgGroupRole.setMvccVersion(RandomTestUtil.nextLong());

		_orgGroupRoles.add(_persistence.update(orgGroupRole));

		return orgGroupRole;
	}

	private static Log _log = LogFactoryUtil.getLog(OrgGroupRolePersistenceTest.class);
	private List<OrgGroupRole> _orgGroupRoles = new ArrayList<OrgGroupRole>();
	private ModelListener<OrgGroupRole>[] _modelListeners;
	private OrgGroupRolePersistence _persistence = OrgGroupRoleUtil.getPersistence();
}