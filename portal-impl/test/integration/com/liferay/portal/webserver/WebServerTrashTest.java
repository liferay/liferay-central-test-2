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

package com.liferay.portal.webserver;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.AggregateTestRule;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.webdav.methods.Method;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.LiferayIntegrationTestRule;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.test.RoleTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portal.util.test.UserTestUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Eduardo Garcia
 */
public class WebServerTrashTest extends BaseWebServerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(null, group.getGroupId());

		_role = RoleTestUtil.addRole(
			"Trash Admin", RoleConstants.TYPE_REGULAR, PortletKeys.TRASH,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			ActionKeys.ACCESS_IN_CONTROL_PANEL);

		RoleTestUtil.addResourcePermission(
			RoleConstants.GUEST, DLPermission.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP_TEMPLATE,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			ActionKeys.VIEW);
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		RoleTestUtil.removeResourcePermission(
			RoleConstants.GUEST, DLPermission.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP_TEMPLATE,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			ActionKeys.VIEW);
	}

	@Test
	public void testRequestFileInTrash() throws Exception {
		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), "Test Trash.txt");

		MockHttpServletResponse mockHttpServletResponse =  testRequestFile(
			fileEntry, _user, false);

		Assert.assertEquals(
			MockHttpServletResponse.SC_OK, mockHttpServletResponse.getStatus());

		DLAppServiceUtil.moveFileEntryToTrash(fileEntry.getFileEntryId());

		mockHttpServletResponse = testRequestFile(fileEntry, _user, false);

		Assert.assertEquals(
			MockHttpServletResponse.SC_NOT_FOUND,
			mockHttpServletResponse.getStatus());

		mockHttpServletResponse = testRequestFile(fileEntry, _user, true);

		Assert.assertEquals(
			MockHttpServletResponse.SC_UNAUTHORIZED,
			mockHttpServletResponse.getStatus());

		RoleLocalServiceUtil.addUserRoles(
			_user.getUserId(), new long[] {_role.getRoleId()});

		mockHttpServletResponse = testRequestFile(fileEntry, _user, true);

		Assert.assertEquals(
			MockHttpServletResponse.SC_OK, mockHttpServletResponse.getStatus());
	}

	protected void resetPermissionThreadLocal() throws Exception {
		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser());

		PermissionThreadLocal.setPermissionChecker(permissionChecker);
	}

	protected MockHttpServletResponse testRequestFile(
			FileEntry fileEntry, User user, boolean statusInTrash)
		throws Exception {

		StringBundler sb = new StringBundler(4);

		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getGroupId());
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getUuid());

		String path = sb.toString();

		Map<String, String> params = new HashMap<String, String>();

		if (statusInTrash) {
			params.put(
				"status", String.valueOf(WorkflowConstants.STATUS_IN_TRASH));
		}

		MockHttpServletResponse mockHttpServletResponse = service(
			Method.GET, path, null, params, user, null);

		resetPermissionThreadLocal();

		return mockHttpServletResponse;
	}

	@DeleteAfterTestRun
	private Role _role;

	@DeleteAfterTestRun
	private User _user;

}