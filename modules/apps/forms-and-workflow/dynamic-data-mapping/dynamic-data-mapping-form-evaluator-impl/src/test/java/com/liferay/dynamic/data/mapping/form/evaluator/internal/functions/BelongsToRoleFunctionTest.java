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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.functions;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Leonardo Barros
 */
@RunWith(MockitoJUnitRunner.class)
public class BelongsToRoleFunctionTest {

	@Before
	public void setUp() throws Exception {
		setPortalUtil();
		setRole();
	}

	@Test
	public void testEvaluateFalseWithRegularRole() throws Exception {
		BelongsToRoleFunction belongsToRoleFunction = new BelongsToRoleFunction(
			_request, 1, _groupLocalService, _roleLocalService, _userGroupRoleLocalService, _userLocalService);

		mockHasRegularRole();

		Assert.assertEquals(
			false, belongsToRoleFunction.evaluate("Role0", "Role2"));
	}

	@Test
	public void testEvaluateTrueWithRegularRole() throws Exception {
		BelongsToRoleFunction belongsToRoleFunction = new BelongsToRoleFunction(
			_request, 1, _groupLocalService, _roleLocalService, _userGroupRoleLocalService, _userLocalService);

		mockHasRegularRole();

		Assert.assertEquals(
			true, belongsToRoleFunction.evaluate("Role0", "Role1", "Role2"));
	}

	@Test
	public void testEvaluateFalseWithSiteRole() throws Exception {
		BelongsToRoleFunction belongsToRoleFunction = new BelongsToRoleFunction(
			_request, 1, _groupLocalService, _roleLocalService, _userGroupRoleLocalService, _userLocalService);

		mockHasSiteRole();

		Assert.assertEquals(
			false, belongsToRoleFunction.evaluate("Role0", "Role2"));
	}

	@Test
	public void testEvaluateTrueWithSiteRole() throws Exception {
		BelongsToRoleFunction belongsToRoleFunction = new BelongsToRoleFunction(
			_request, 1, _groupLocalService, _roleLocalService, _userGroupRoleLocalService, _userLocalService);

		mockHasSiteRole();

		Assert.assertEquals(
			true, belongsToRoleFunction.evaluate("Role0", "Role1", "Role2"));
	}

	@Test
	public void testEvaluateFalseWithOrganizationalRole() throws Exception {
		BelongsToRoleFunction belongsToRoleFunction = new BelongsToRoleFunction(
			_request, 1, _groupLocalService, _roleLocalService, _userGroupRoleLocalService, _userLocalService);

		mockHasOrganizationalRole();

		Assert.assertEquals(
			false, belongsToRoleFunction.evaluate("Role0", "Role2"));
	}

	@Test
	public void testEvaluateTrueWithOrganizationalRole() throws Exception {
		BelongsToRoleFunction belongsToRoleFunction = new BelongsToRoleFunction(
			_request, 1, _groupLocalService, _roleLocalService, _userGroupRoleLocalService, _userLocalService);

		mockHasOrganizationalRole();

		Assert.assertEquals(
			true, belongsToRoleFunction.evaluate("Role0", "Role1", "Role2"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArgument() throws Exception {
		BelongsToRoleFunction belongsToRoleFunction = new BelongsToRoleFunction(
			null, 0, null, null, null,null);

		belongsToRoleFunction.evaluate();
	}

	protected void mockHasSiteRole() throws Exception {
		when(
			_role.getType()
		).thenReturn(
			RoleConstants.TYPE_SITE
		);

		when(
			_userGroupRoleLocalService.hasUserGroupRole(
				Matchers.anyLong(), Matchers.anyLong(), Matchers.eq("Role1"), Matchers.eq(true))
		).thenReturn(
			true
		);
	}

	protected void mockHasOrganizationalRole() throws Exception {
		when(
			_role.getType()
		).thenReturn(
			RoleConstants.TYPE_ORGANIZATION
		);

		when(
			_groupLocalService.getOrganizationPrimaryKeys(Matchers.anyLong())
		).thenReturn(
			new long[1]
		);

		when(
			_groupLocalService.getOrganizationGroup(Matchers.anyLong(), Matchers.anyLong())
		).thenReturn(
			_group
		);

		when(
			_userGroupRoleLocalService.hasUserGroupRole(Matchers.anyLong(), Matchers.anyLong(), Matchers.eq("Role1"), Matchers.eq(true))
		).thenReturn(
			true
		);
	}

	protected void mockHasRegularRole() throws Exception {
		when(
			_role.getType()
		).thenReturn(
			RoleConstants.TYPE_REGULAR
		);

		when(
			_userLocalService.hasRoleUser(
				Matchers.anyLong(), Matchers.eq("Role1"), Matchers.anyLong(), Matchers.eq(true))
		).thenReturn(
			true
		);
	}

	protected void setPortalUtil() throws Exception {
		PortalUtil portalUtil = new PortalUtil();

		Portal portal = mock(Portal.class);

		when(portal.getUser(_request)).thenReturn(_user);
		when(portal.getCompany(_request)).thenReturn(_company);

		portalUtil.setPortal(portal);
	}

	protected void setRole() throws Exception {
		when(
			_roleLocalService.fetchRole(
				Matchers.anyLong(), Matchers.anyString())
		).thenReturn(
			_role
		);
	}

	@Mock
	private Company _company;

	@Mock
	private HttpServletRequest _request;

	@Mock
	private User _user;

	@Mock
	protected Group _group;

	@Mock
	private GroupLocalService _groupLocalService;

	@Mock
	private Role _role;

	@Mock
	private RoleLocalService _roleLocalService;

	@Mock
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Mock
	private UserLocalService _userLocalService;

}