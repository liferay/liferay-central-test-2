/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.service.spring;

/**
 * <a href="GroupLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class GroupLocalServiceUtil {
	public static com.liferay.portal.model.Group addGroup(
		java.lang.String userId, java.lang.String className,
		java.lang.String classPK, java.lang.String name,
		java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

			return groupLocalService.addGroup(userId, className, classPK, name,
				friendlyURL);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portal.model.Group addGroup(
		java.lang.String userId, java.lang.String className,
		java.lang.String classPK, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

			return groupLocalService.addGroup(userId, className, classPK, name,
				description, type, friendlyURL);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static boolean addCommunityOrgs(java.lang.String groupId,
		java.lang.String[] orgIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

			return groupLocalService.addCommunityOrgs(groupId, orgIds);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static boolean addRoleGroups(java.lang.String roleId,
		java.lang.String[] groupIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

			return groupLocalService.addRoleGroups(roleId, groupIds);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static boolean addUserGroups(java.lang.String userId,
		java.lang.String[] groupIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

			return groupLocalService.addUserGroups(userId, groupIds);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void checkSystemGroups(java.lang.String companyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();
			groupLocalService.checkSystemGroups(companyId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteGroup(java.lang.String groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();
			groupLocalService.deleteGroup(groupId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static boolean hasCommunityOrg(java.lang.String communityGroupId,
		java.lang.String organizationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

			return groupLocalService.hasCommunityOrg(communityGroupId,
				organizationId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static boolean hasRoleGroup(java.lang.String roleId,
		java.lang.String groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

			return groupLocalService.hasRoleGroup(roleId, groupId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static boolean hasUserGroup(java.lang.String userId,
		java.lang.String groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

			return groupLocalService.hasUserGroup(userId, groupId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portal.model.Group getFriendlyURLGroup(
		java.lang.String companyId, java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

			return groupLocalService.getFriendlyURLGroup(companyId, friendlyURL);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portal.model.Group getGroup(
		java.lang.String groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

			return groupLocalService.getGroup(groupId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portal.model.Group getGroup(
		java.lang.String companyId, java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

			return groupLocalService.getGroup(companyId, name);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portal.model.Group getOrganizationGroup(
		java.lang.String companyId, java.lang.String organizationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

			return groupLocalService.getOrganizationGroup(companyId,
				organizationId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List getOrganizationGroups(
		java.util.List organizations)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

			return groupLocalService.getOrganizationGroups(organizations);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List getPublicGroups(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		try {
			GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

			return groupLocalService.getPublicGroups(companyId);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List getRoleGroups(java.lang.String roleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

			return groupLocalService.getRoleGroups(roleId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portal.model.Group getUserGroup(
		java.lang.String companyId, java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

			return groupLocalService.getUserGroup(companyId, userId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List getUserGroups(java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

			return groupLocalService.getUserGroups(userId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List getUserGroups(java.lang.String companyId,
		java.lang.String userId, boolean privateLayout)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

			return groupLocalService.getUserGroups(companyId, userId,
				privateLayout);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List search(java.lang.String companyId,
		java.lang.String name, java.util.Map params)
		throws com.liferay.portal.SystemException {
		try {
			GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

			return groupLocalService.search(companyId, name, params);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List search(java.lang.String companyId,
		java.lang.String name, java.util.Map params, int begin, int end)
		throws com.liferay.portal.SystemException {
		try {
			GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

			return groupLocalService.search(companyId, name, params, begin, end);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static int searchCount(java.lang.String companyId,
		java.lang.String name, java.util.Map params)
		throws com.liferay.portal.SystemException {
		try {
			GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

			return groupLocalService.searchCount(companyId, name, params);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void setRoleGroups(java.lang.String roleId,
		java.lang.String[] groupIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();
			groupLocalService.setRoleGroups(roleId, groupIds);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static boolean unsetCommunityOrgs(java.lang.String groupId,
		java.lang.String[] orgIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

			return groupLocalService.unsetCommunityOrgs(groupId, orgIds);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static boolean unsetRoleGroups(java.lang.String roleId,
		java.lang.String[] groupIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

			return groupLocalService.unsetRoleGroups(roleId, groupIds);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portal.model.Group updateGroup(
		java.lang.String groupId, java.lang.String name,
		java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

			return groupLocalService.updateGroup(groupId, name, friendlyURL);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portal.model.Group updateGroup(
		java.lang.String groupId, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

			return groupLocalService.updateGroup(groupId, name, description,
				type, friendlyURL);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}
}