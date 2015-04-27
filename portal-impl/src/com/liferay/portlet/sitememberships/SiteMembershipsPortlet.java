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

package com.liferay.portlet.sitememberships;

import com.liferay.portal.MembershipRequestCommentsException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.liveusers.LiveUsers;
import com.liferay.portal.model.MembershipRequest;
import com.liferay.portal.model.MembershipRequestConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.membershippolicy.MembershipPolicyException;
import com.liferay.portal.service.MembershipRequestServiceUtil;
import com.liferay.portal.service.OrganizationServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserGroupGroupRoleServiceUtil;
import com.liferay.portal.service.UserGroupRoleServiceUtil;
import com.liferay.portal.service.UserGroupServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import java.io.IOException;

import java.util.HashSet;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class SiteMembershipsPortlet extends MVCPortlet {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals("user_group_role_users")) {
				updateUserGroupRoleUsers(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof MembershipPolicyException) {
				SessionErrors.add(actionRequest, e.getClass(), e);
			}
			else if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.sites_admin.error");
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getGroup(renderRequest);
			ActionUtil.getRole(renderRequest);

			Role role = (Role)renderRequest.getAttribute(WebKeys.ROLE);

			if (role != null) {
				String roleName = role.getName();

				if (roleName.equals(RoleConstants.ORGANIZATION_USER) ||
					roleName.equals(RoleConstants.SITE_MEMBER)) {

					throw new NoSuchRoleException();
				}
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof NoSuchRoleException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.sites_admin.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.sites_admin.edit_user_roles"));
	}

	public void replyMembershipRequest(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

		long membershipRequestId = ParamUtil.getLong(
			actionRequest, "membershipRequestId");

		long statusId = ParamUtil.getLong(actionRequest, "statusId");
		String replyComments = ParamUtil.getString(
			actionRequest, "replyComments");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		MembershipRequestServiceUtil.updateStatus(
			membershipRequestId, replyComments, statusId, serviceContext);

		if (statusId == MembershipRequestConstants.STATUS_APPROVED) {
			MembershipRequest membershipRequest =
				MembershipRequestServiceUtil.getMembershipRequest(
					membershipRequestId);

			LiveUsers.joinGroup(
				themeDisplay.getCompanyId(), membershipRequest.getGroupId(),
				new long[] {membershipRequest.getUserId()});
		}

		SessionMessages.add(actionRequest, "membershipReplySent");

		sendRedirect(actionRequest, actionResponse);
	}

	public void updateGroupOrganizations(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		long[] addOrganizationIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "addOrganizationIds"), 0L);
		long[] removeOrganizationIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "removeOrganizationIds"), 0L);

		OrganizationServiceUtil.addGroupOrganizations(
			groupId, addOrganizationIds);
		OrganizationServiceUtil.unsetGroupOrganizations(
			groupId, removeOrganizationIds);

		String redirect = ParamUtil.getString(
			actionRequest, "assignmentsRedirect");

		if (Validator.isNotNull(redirect)) {
			actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
		}
	}

	public void updateGroupUserGroups(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		long[] addUserGroupIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "addUserGroupIds"), 0L);
		long[] removeUserGroupIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "removeUserGroupIds"), 0L);

		UserGroupServiceUtil.addGroupUserGroups(groupId, addUserGroupIds);
		UserGroupServiceUtil.unsetGroupUserGroups(groupId, removeUserGroupIds);

		String redirect = ParamUtil.getString(
			actionRequest, "assignmentsRedirect");

		if (Validator.isNotNull(redirect)) {
			actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
		}
	}

	public void updateGroupUsers(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		long[] addUserIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "addUserIds"), 0L);

		addUserIds = filterAddUserIds(groupId, addUserIds);

		long[] removeUserIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "removeUserIds"), 0L);

		removeUserIds = filterRemoveUserIds(groupId, removeUserIds);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		UserServiceUtil.addGroupUsers(groupId, addUserIds, serviceContext);
		UserServiceUtil.unsetGroupUsers(groupId, removeUserIds, serviceContext);

		LiveUsers.joinGroup(themeDisplay.getCompanyId(), groupId, addUserIds);
		LiveUsers.leaveGroup(
			themeDisplay.getCompanyId(), groupId, removeUserIds);

		String redirect = ParamUtil.getString(
			actionRequest, "assignmentsRedirect");

		if (Validator.isNotNull(redirect)) {
			actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
		}
	}

	public void updateUserGroupGroupRole(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		long userGroupId = ParamUtil.getLong(actionRequest, "userGroupId");

		long[] addRoleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "addRoleIds"), 0L);
		long[] removeRoleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "removeRoleIds"), 0L);

		UserGroupGroupRoleServiceUtil.addUserGroupGroupRoles(
			userGroupId, groupId, addRoleIds);
		UserGroupGroupRoleServiceUtil.deleteUserGroupGroupRoles(
			userGroupId, groupId, removeRoleIds);

		String redirect = ParamUtil.getString(
			actionRequest, "assignmentsRedirect");

		if (Validator.isNotNull(redirect)) {
			actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
		}
	}

	public void updateUserGroupRole(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		User user = PortalUtil.getSelectedUser(actionRequest, false);

		if (user == null) {
			return;
		}

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		long[] addRoleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "addRoleIds"), 0L);
		long[] removeRoleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "removeRoleIds"), 0L);

		UserGroupRoleServiceUtil.addUserGroupRoles(
			user.getUserId(), groupId, addRoleIds);
		UserGroupRoleServiceUtil.deleteUserGroupRoles(
			user.getUserId(), groupId, removeRoleIds);

		String redirect = ParamUtil.getString(
			actionRequest, "assignmentsRedirect");

		if (Validator.isNotNull(redirect)) {
			actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
		}
	}

	public void updateUserGroupGroupRoleUsers(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		long roleId = ParamUtil.getLong(actionRequest, "roleId");

		long[] addUserGroupIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "addUserGroupIds"), 0L);
		long[] removeUserGroupIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "removeUserGroupIds"), 0L);

		UserGroupGroupRoleServiceUtil.addUserGroupGroupRoles(
			addUserGroupIds, groupId, roleId);
		UserGroupGroupRoleServiceUtil.deleteUserGroupGroupRoles(
			removeUserGroupIds, groupId, roleId);
	}

	public void updateUserGroupRoleUsers(ActionRequest actionRequest)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		long roleId = ParamUtil.getLong(actionRequest, "roleId");

		long[] addUserIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "addUserIds"), 0L);
		long[] removeUserIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "removeUserIds"), 0L);

		UserGroupRoleServiceUtil.addUserGroupRoles(addUserIds, groupId, roleId);
		UserGroupRoleServiceUtil.deleteUserGroupRoles(
			removeUserIds, groupId, roleId);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (SessionErrors.contains(
				renderRequest, NoSuchGroupException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, NoSuchRoleException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, PrincipalException.class.getName())) {

			include("/error.jsp", renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	protected long[] filterAddUserIds(long groupId, long[] userIds)
		throws Exception {

		Set<Long> filteredUserIds = new HashSet<>(userIds.length);

		for (long userId : userIds) {
			if (!UserLocalServiceUtil.hasGroupUser(groupId, userId)) {
				filteredUserIds.add(userId);
			}
		}

		return ArrayUtil.toArray(
			filteredUserIds.toArray(new Long[filteredUserIds.size()]));
	}

	protected long[] filterRemoveUserIds(long groupId, long[] userIds)
		throws Exception {

		Set<Long> filteredUserIds = new HashSet<>(userIds.length);

		for (long userId : userIds) {
			if (UserLocalServiceUtil.hasGroupUser(groupId, userId)) {
				filteredUserIds.add(userId);
			}
		}

		return ArrayUtil.toArray(
			filteredUserIds.toArray(new Long[filteredUserIds.size()]));
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof MembershipPolicyException ||
			cause instanceof MembershipRequestCommentsException ||
			cause instanceof NoSuchGroupException ||
			cause instanceof NoSuchRoleException ||
			cause instanceof PrincipalException ||
			super.isSessionErrorException(cause)) {

			return true;
		}

		return false;
	}

}