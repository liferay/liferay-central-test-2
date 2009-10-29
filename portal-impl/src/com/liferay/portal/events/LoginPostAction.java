/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.events;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.NoSuchUserGroupException;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.enterpriseadmin.util.EnterpriseAdminUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;

/**
 * <a href="LoginPostAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class LoginPostAction extends Action {

	public void run(HttpServletRequest request, HttpServletResponse response)
		throws ActionException {

		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Running " + request.getRemoteUser());
			}

			HttpSession session = request.getSession();
			long companyId = PortalUtil.getCompanyId(request);
			User user = PortalUtil.getUser(request);
			boolean adminDefaultAddToExistingUser = PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.ADMIN_DEFAULT_ADD_TO_EXISTING_USER);

			// Language

			session.removeAttribute(Globals.LOCALE_KEY);

			// Live users

			if (PropsValues.LIVE_USERS_ENABLED) {
				String sessionId = session.getId();
				String remoteAddr = request.getRemoteAddr();
				String remoteHost = request.getRemoteHost();
				String userAgent = request.getHeader(HttpHeaders.USER_AGENT);

				JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

				jsonObj.put("command", "signIn");
				jsonObj.put("companyId", companyId);
				jsonObj.put("userId", user.getUserId());
				jsonObj.put("sessionId", sessionId);
				jsonObj.put("remoteAddr", remoteAddr);
				jsonObj.put("remoteHost", remoteHost);
				jsonObj.put("userAgent", userAgent);

				MessageBusUtil.sendMessage(
					DestinationNames.LIVE_USERS, jsonObj);
			}

			if (adminDefaultAddToExistingUser) {
				// Groups

				Set<Long> groupIdSet = new HashSet<Long>();

				String[] defaultGroupNames = PrefsPropsUtil.getStringArray(
					companyId, PropsKeys.ADMIN_DEFAULT_GROUP_NAMES,
					StringPool.NEW_LINE, PropsValues.ADMIN_DEFAULT_GROUP_NAMES);
				List<Group> groups = user.getGroups();

				for (String defaultGroupName : defaultGroupNames) {
					try {
						Group defaultGroup = GroupLocalServiceUtil.getGroup(
							companyId, defaultGroupName);
						if (!groups.contains(defaultGroup)) {
							groupIdSet.add(defaultGroup.getGroupId());
						}
					}
					catch (NoSuchGroupException nsge) {
					}
				}

				long[] groupIds = ArrayUtil.toArray(
					(Long[])groupIdSet.toArray(new Long[groupIdSet.size()]));

				GroupLocalServiceUtil.addUserGroups(user.getUserId(), groupIds);

				// Roles

				Set<Long> roleIdSet = new HashSet<Long>();

				String[] defaultRoleNames = PrefsPropsUtil.getStringArray(
					companyId, PropsKeys.ADMIN_DEFAULT_ROLE_NAMES,
					StringPool.NEW_LINE, PropsValues.ADMIN_DEFAULT_ROLE_NAMES);
				List<Role> roles = user.getRoles();

				for (String defaultRoleName : defaultRoleNames) {
					try {
						Role defaultRole = RoleLocalServiceUtil.getRole(
							companyId, defaultRoleName);
						if (!roles.contains(defaultRole)) {
							roleIdSet.add(defaultRole.getRoleId());
						}
					}
					catch (NoSuchRoleException nsre) {
					}
				}

				long[] roleIds = ArrayUtil.toArray(
					(Long[])roleIdSet.toArray(new Long[roleIdSet.size()]));

				roleIds = EnterpriseAdminUtil.addRequiredRoles(
					user.getUserId(), roleIds);

				RoleLocalServiceUtil.addUserRoles(user.getUserId(), roleIds);

				// User groups

				Set<Long> userGroupIdSet = new HashSet<Long>();

				String[] defaultUserGroupNames = PrefsPropsUtil.getStringArray(
					companyId, PropsKeys.ADMIN_DEFAULT_USER_GROUP_NAMES,
					StringPool.NEW_LINE,
					PropsValues.ADMIN_DEFAULT_USER_GROUP_NAMES);
				List<UserGroup> userGroups = user.getUserGroups();

				for (String defaultUserGroupName : defaultUserGroupNames) {
					try {
						UserGroup defaultUserGroup =
							UserGroupLocalServiceUtil.getUserGroup(
								companyId, defaultUserGroupName);
						if (!userGroups.contains(defaultUserGroup)) {
							userGroupIdSet.add(
								defaultUserGroup.getUserGroupId());
						}
					}
					catch (NoSuchUserGroupException nsuge) {
					}
				}

				long[] userIds = new long[]{user.getUserId()};

				for (Long userGroupId : userGroupIdSet) {
					UserLocalServiceUtil.addUserGroupUsers(
						userGroupId, userIds);
				}
			}
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LoginPostAction.class);

}