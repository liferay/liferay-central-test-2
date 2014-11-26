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

package com.liferay.portal.security.permission;

import com.liferay.portal.kernel.test.AggregateTestRule;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.LiferayIntegrationTestRule;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.util.test.CompanyTestUtil;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.OrganizationTestUtil;
import com.liferay.portal.util.test.RoleTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portal.util.test.UserTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
public class PermissionCheckerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testIsCompanyAdminWithCompanyAdmin() throws Exception {
		PermissionChecker permissionChecker = _getPermissionChecker(
			TestPropsValues.getUser());

		Assert.assertTrue(permissionChecker.isCompanyAdmin());
	}

	@Test
	public void testIsCompanyAdminWithRegularUser() throws Exception {
		_user = UserTestUtil.addUser();

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertFalse(permissionChecker.isCompanyAdmin());
	}

	@Test
	public void testIsContentReviewerWithCompanyAdminUser() throws Exception {
		PermissionChecker permissionChecker = _getPermissionChecker(
			TestPropsValues.getUser());

		Assert.assertTrue(
			permissionChecker.isContentReviewer(
				TestPropsValues.getCompanyId(), _group.getGroupId()));
	}

	@Test
	public void testIsContentReviewerWithReviewerUser() throws Exception {
		_user = UserTestUtil.addUser();

		_role = RoleTestUtil.addRole(
			RoleConstants.PORTAL_CONTENT_REVIEWER, RoleConstants.TYPE_REGULAR);

		UserLocalServiceUtil.setRoleUsers(
			_role.getRoleId(), new long[] {_user.getUserId()});

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertTrue(
			permissionChecker.isContentReviewer(
				_user.getCompanyId(), _group.getGroupId()));
	}

	@Test
	public void testIsContentReviewerWithSiteContentReviewer()
		throws Exception {

		_role = RoleTestUtil.addRole(
			RoleConstants.SITE_CONTENT_REVIEWER, RoleConstants.TYPE_SITE);

		_user = UserTestUtil.addGroupUser(
			_group, RoleConstants.SITE_CONTENT_REVIEWER);

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertTrue(
			permissionChecker.isContentReviewer(
				_user.getCompanyId(), _group.getGroupId()));
	}

	@Test
	public void testIsGroupAdminWithCompanyAdmin() throws Exception {
		PermissionChecker permissionChecker = _getPermissionChecker(
			TestPropsValues.getUser());

		Assert.assertTrue(permissionChecker.isGroupAdmin(_group.getGroupId()));
	}

	@Test
	public void testIsGroupAdminWithGroupAdmin() throws Exception {
		_user = UserTestUtil.addGroupAdminUser(_group);

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertTrue(permissionChecker.isGroupAdmin(_group.getGroupId()));
	}

	@Test
	public void testIsGroupAdminWithRegularUser() throws Exception {
		_user = UserTestUtil.addUser();

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertFalse(permissionChecker.isGroupAdmin(_group.getGroupId()));
	}

	@Test
	public void testIsGroupMemberWithGroupMember() throws Exception {
		_user = UserTestUtil.addUser();

		UserLocalServiceUtil.addGroupUser(
			_group.getGroupId(), _user.getUserId());

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertTrue(permissionChecker.isGroupMember(_group.getGroupId()));
	}

	@Test
	public void testIsGroupMemberWithNonGroupMember() throws Exception {
		_user = UserTestUtil.addUser();

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertFalse(
			permissionChecker.isGroupMember(_group.getGroupId()));
	}

	@Test
	public void testIsGroupOwnerWithCompanyAdmin() throws Exception {
		PermissionChecker permissionChecker = _getPermissionChecker(
			TestPropsValues.getUser());

		Assert.assertTrue(permissionChecker.isGroupOwner(_group.getGroupId()));
	}

	@Test
	public void testIsGroupOwnerWithGroupAdmin() throws Exception {
		_user = UserTestUtil.addGroupAdminUser(_group);

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertFalse(permissionChecker.isGroupOwner(_group.getGroupId()));
	}

	@Test
	public void testIsGroupOwnerWithOwnerUser() throws Exception {
		_user = UserTestUtil.addGroupOwnerUser(_group);

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertTrue(permissionChecker.isGroupOwner(_group.getGroupId()));
	}

	@Test
	public void testIsGroupOwnerWithRegularUser() throws Exception {
		_user = UserTestUtil.addUser(
			_group.getGroupId(), LocaleUtil.getDefault());

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertFalse(permissionChecker.isGroupOwner(_group.getGroupId()));
	}

	@Test
	public void testIsOmniAdminWithAdministratorRoleUser() throws Exception {
		_user = UserTestUtil.addOmniAdminUser();

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertTrue(permissionChecker.isOmniadmin());
	}

	@Test
	public void testIsOmniAdminWithCompanyAdmin() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		User adminUser = UserTestUtil.addCompanyAdminUser(company);

		PermissionChecker permissionChecker = _getPermissionChecker(adminUser);

		Assert.assertFalse(permissionChecker.isOmniadmin());
	}

	@Test
	public void testIsOmniAdminWithGroupAdmin() throws Exception {
		_user = UserTestUtil.addGroupAdminUser(_group);

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertFalse(permissionChecker.isOmniadmin());
	}

	@Test
	public void testIsOmniAdminWithRegularUser() throws Exception {
		_user = UserTestUtil.addUser();

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertFalse(permissionChecker.isOmniadmin());
	}

	@Test
	public void testIsOrganizationAdminWithCompanyAdmin() throws Exception {
		_organization = OrganizationTestUtil.addOrganization();

		PermissionChecker permissionChecker = _getPermissionChecker(
			TestPropsValues.getUser());

		Assert.assertTrue(
			permissionChecker.isOrganizationAdmin(
				_organization.getOrganizationId()));
	}

	@Test
	public void testIsOrganizationAdminWithGroupAdmin() throws Exception {
		_organization = OrganizationTestUtil.addOrganization();

		_user = UserTestUtil.addGroupAdminUser(_organization.getGroup());

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertFalse(
			permissionChecker.isOrganizationAdmin(
				_organization.getOrganizationId()));
	}

	@Test
	public void testIsOrganizationAdminWithOrganizationAdmin()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		_user = UserTestUtil.addOrganizationAdminUser(_organization);

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertTrue(
			permissionChecker.isOrganizationAdmin(
				_organization.getOrganizationId()));
	}

	@Test
	public void testIsOrganizationAdminWithRegularUser() throws Exception {
		_organization = OrganizationTestUtil.addOrganization();

		_user = UserTestUtil.addUser();

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertFalse(
			permissionChecker.isOrganizationAdmin(
				_organization.getOrganizationId()));
	}

	@Test
	public void testIsOrganizationOwnerWithCompanyAdmin() throws Exception {
		_organization = OrganizationTestUtil.addOrganization();

		PermissionChecker permissionChecker = _getPermissionChecker(
			TestPropsValues.getUser());

		Assert.assertTrue(
			permissionChecker.isOrganizationOwner(
				_organization.getOrganizationId()));
	}

	@Test
	public void testIsOrganizationOwnerWithGroupAdmin() throws Exception {
		_organization = OrganizationTestUtil.addOrganization();

		_user = UserTestUtil.addGroupAdminUser(_organization.getGroup());

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertFalse(
			permissionChecker.isOrganizationOwner(
				_organization.getOrganizationId()));
	}

	@Test
	public void testIsOrganizationOwnerWithOrganizationAdmin()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		_user = UserTestUtil.addOrganizationAdminUser(_organization);

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertFalse(
			permissionChecker.isOrganizationOwner(
				_organization.getOrganizationId()));
	}

	@Test
	public void testIsOrganizationOwnerWithRegularUser() throws Exception {
		_organization = OrganizationTestUtil.addOrganization();

		_user = UserTestUtil.addUser();

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertFalse(
			permissionChecker.isOrganizationOwner(
				_organization.getOrganizationId()));
	}

	private PermissionChecker _getPermissionChecker(User user)
		throws Exception {

		return PermissionCheckerFactoryUtil.create(user);
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private Organization _organization;

	@DeleteAfterTestRun
	private Role _role;

	@DeleteAfterTestRun
	private User _user;

}