/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="GroupServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link GroupService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       GroupService
 * @generated
 */
public class GroupServiceUtil {
	public static com.liferay.portal.model.Group addGroup(
		java.lang.String name, java.lang.String description, int type,
		java.lang.String friendlyURL, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addGroup(name, description, type, friendlyURL, active,
			serviceContext);
	}

	public static com.liferay.portal.model.Group addGroup(long liveGroupId,
		java.lang.String name, java.lang.String description, int type,
		java.lang.String friendlyURL, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addGroup(liveGroupId, name, description, type, friendlyURL,
			active, serviceContext);
	}

	public static void addRoleGroups(long roleId, long[] groupIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().addRoleGroups(roleId, groupIds);
	}

	public static void deleteGroup(long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteGroup(groupId);
	}

	public static com.liferay.portal.model.Group getGroup(long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getGroup(groupId);
	}

	public static com.liferay.portal.model.Group getGroup(long companyId,
		java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getGroup(companyId, name);
	}

	public static java.util.List<com.liferay.portal.model.Group> getManageableGroups(
		java.lang.String actionId, int max)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getManageableGroups(actionId, max);
	}

	public static java.util.List<com.liferay.portal.model.Group> getOrganizationsGroups(
		java.util.List<com.liferay.portal.model.Organization> organizations) {
		return getService().getOrganizationsGroups(organizations);
	}

	public static com.liferay.portal.model.Group getUserGroup(long companyId,
		long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getUserGroup(companyId, userId);
	}

	public static java.util.List<com.liferay.portal.model.Group> getUserGroupsGroups(
		java.util.List<com.liferay.portal.model.UserGroup> userGroups) {
		return getService().getUserGroupsGroups(userGroups);
	}

	public static java.util.List<com.liferay.portal.model.Group> getUserOrganizationsGroups(
		long userId, int start, int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getUserOrganizationsGroups(userId, start, end);
	}

	public static boolean hasUserGroup(long userId, long groupId)
		throws com.liferay.portal.SystemException {
		return getService().hasUserGroup(userId, groupId);
	}

	public static java.util.List<com.liferay.portal.model.Group> search(
		long companyId, java.lang.String name, java.lang.String description,
		java.lang.String[] params, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService()
				   .search(companyId, name, description, params, start, end);
	}

	public static int searchCount(long companyId, java.lang.String name,
		java.lang.String description, java.lang.String[] params)
		throws com.liferay.portal.SystemException {
		return getService().searchCount(companyId, name, description, params);
	}

	public static void setRoleGroups(long roleId, long[] groupIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().setRoleGroups(roleId, groupIds);
	}

	public static void unsetRoleGroups(long roleId, long[] groupIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().unsetRoleGroups(roleId, groupIds);
	}

	public static com.liferay.portal.model.Group updateFriendlyURL(
		long groupId, java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateFriendlyURL(groupId, friendlyURL);
	}

	public static com.liferay.portal.model.Group updateGroup(long groupId,
		java.lang.String name, java.lang.String description, int type,
		java.lang.String friendlyURL, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateGroup(groupId, name, description, type, friendlyURL,
			active, serviceContext);
	}

	public static com.liferay.portal.model.Group updateGroup(long groupId,
		java.lang.String typeSettings)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateGroup(groupId, typeSettings);
	}

	public static com.liferay.portal.model.Group updateWorkflow(long groupId,
		boolean workflowEnabled, int workflowStages,
		java.lang.String workflowRoleNames)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateWorkflow(groupId, workflowEnabled, workflowStages,
			workflowRoleNames);
	}

	public static GroupService getService() {
		if (_service == null) {
			_service = (GroupService)PortalBeanLocatorUtil.locate(GroupService.class.getName());
		}

		return _service;
	}

	public void setService(GroupService service) {
		_service = service;
	}

	private static GroupService _service;
}