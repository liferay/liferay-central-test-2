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

package com.liferay.portlet.usergroupsadmin;

import com.liferay.portal.DuplicateUserGroupException;
import com.liferay.portal.NoSuchUserGroupException;
import com.liferay.portal.RequiredUserGroupException;
import com.liferay.portal.UserGroupNameException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.membershippolicy.MembershipPolicyException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserGroupServiceUtil;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portlet.sites.util.SitesUtil;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Charles May
 * @author Drew Brokke
 */

public class UserGroupsAdminPortlet extends MVCPortlet {

	public void deleteUserGroups(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] deleteUserGroupIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "deleteUserGroupIds"), 0L);

		for (long deleteUserGroupId : deleteUserGroupIds) {
			UserGroupServiceUtil.deleteUserGroup(deleteUserGroupId);
		}
	}

	public void editUserGroup(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long userGroupId = ParamUtil.getLong(actionRequest, "userGroupId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			UserGroup.class.getName(), actionRequest);

		UserGroup userGroup = null;

		if (userGroupId <= 0) {

			// Add user group

			userGroup = UserGroupServiceUtil.addUserGroup(
				name, description, serviceContext);
		}
		else {

			// Update user group

			userGroup = UserGroupServiceUtil.updateUserGroup(
				userGroupId, name, description, serviceContext);
		}

		// Layout set prototypes

		long publicLayoutSetPrototypeId = ParamUtil.getLong(
			actionRequest, "publicLayoutSetPrototypeId");
		long privateLayoutSetPrototypeId = ParamUtil.getLong(
			actionRequest, "privateLayoutSetPrototypeId");
		boolean publicLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
			actionRequest, "publicLayoutSetPrototypeLinkEnabled");
		boolean privateLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
			actionRequest, "privateLayoutSetPrototypeLinkEnabled");

		if ((privateLayoutSetPrototypeId > 0) ||
			(publicLayoutSetPrototypeId > 0)) {

			SitesUtil.updateLayoutSetPrototypesLinks(
				userGroup.getGroup(), publicLayoutSetPrototypeId,
				privateLayoutSetPrototypeId,
				publicLayoutSetPrototypeLinkEnabled,
				privateLayoutSetPrototypeLinkEnabled);
		}
	}

	public void editUserGroupAssignments(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long userGroupId = ParamUtil.getLong(actionRequest, "userGroupId");

		long[] addUserIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "addUserIds"), 0L);
		long[] removeUserIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "removeUserIds"), 0L);

		UserServiceUtil.addUserGroupUsers(userGroupId, addUserIds);
		UserServiceUtil.unsetUserGroupUsers(userGroupId, removeUserIds);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (SessionErrors.contains(
				renderRequest, NoSuchUserGroupException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, PrincipalException.class.getName())) {

			include(
				"/html/portlet/user_groups_admin/error.jsp", renderRequest,
				renderResponse);
		}
		else if (SessionErrors.contains(
					renderRequest,
					RequiredUserGroupException.class.getName())) {

			include(
				"/html/portlet/user_groups_admin/view.jsp", renderRequest,
				renderResponse);
		}
		else if (SessionErrors.contains(
					renderRequest,
					DuplicateUserGroupException.class.getName()) ||
				 SessionErrors.contains(
					 renderRequest, UserGroupNameException.class.getName())) {

			include(
				"/html/portlet/user_groups_admin/edit_user_group.jsp",
				renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof DuplicateUserGroupException ||
			cause instanceof MembershipPolicyException ||
			cause instanceof NoSuchUserGroupException ||
			cause instanceof PrincipalException ||
			cause instanceof RequiredUserGroupException ||
			cause instanceof UserGroupNameException) {

			return true;
		}

		return false;
	}

}