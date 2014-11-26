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

package com.liferay.portal.service;

import com.liferay.portal.kernel.test.AggregateTestRule;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.LiferayIntegrationTestRule;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.util.test.GroupTestUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@RunWith(Enclosed.class)
public class ResourcePermissionLocalServiceTest {

	public static final class WhenCheckingIfRoleHasResourcePermissions {

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
		public void shouldFailIfFirstResourceIsNotIndividual()
			throws Exception {

			List<Resource> resources = new ArrayList<Resource>();

			Resource firstResource = new ResourceImpl();

			firstResource.setScope(ResourceConstants.SCOPE_GROUP);

			resources.add(firstResource);

			Resource lastResource = new ResourceImpl();

			lastResource.setScope(ResourceConstants.SCOPE_COMPANY);

			resources.add(lastResource);

			long[] roleIds = new long[1];

			Role guestRole = RoleLocalServiceUtil.getRole(
				_group.getCompanyId(), RoleConstants.GUEST);

			roleIds[0] = guestRole.getRoleId();

			try {
				ResourcePermissionLocalServiceUtil.hasResourcePermission(
					resources, roleIds, ActionKeys.VIEW);
			}
			catch (IllegalArgumentException iae) {
				Assert.assertEquals(
					"The first resource must be an individual scope",
					iae.getMessage());
			}
		}

		@Test
		public void shouldFailIfLastResourceIsNotCompany() throws Exception {
			List<Resource> resources = new ArrayList<Resource>();

			Resource firstResource = new ResourceImpl();

			firstResource.setScope(ResourceConstants.SCOPE_INDIVIDUAL);

			resources.add(firstResource);

			Resource lastResource = new ResourceImpl();

			lastResource.setScope(ResourceConstants.SCOPE_GROUP);

			resources.add(lastResource);

			long[] roleIds = new long[1];

			Role guestRole = RoleLocalServiceUtil.getRole(
				_group.getCompanyId(), RoleConstants.GUEST);

			roleIds[0] = guestRole.getRoleId();

			try {
				ResourcePermissionLocalServiceUtil.hasResourcePermission(
					resources, roleIds, ActionKeys.VIEW);
			}
			catch (IllegalArgumentException iae) {
				Assert.assertEquals(
					"The last resource must be a company scope",
					iae.getMessage());
			}
		}

		@Test
		public void shouldFailIfResourcesIsLessThanTwo() throws Exception {
			List<Resource> resources = new ArrayList<Resource>();

			resources.add(new ResourceImpl());

			long[] roleIds = new long[1];

			Role guestRole = RoleLocalServiceUtil.getRole(
				_group.getCompanyId(), RoleConstants.GUEST);

			roleIds[0] = guestRole.getRoleId();

			try {
				ResourcePermissionLocalServiceUtil.hasResourcePermission(
					resources, roleIds, ActionKeys.VIEW);
			}
			catch (IllegalArgumentException iae) {
				Assert.assertEquals(
					"The list of resources must contain at least two values",
					iae.getMessage());
			}
		}

		@DeleteAfterTestRun
		private Group _group;

	}

}