/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.permission;

import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerBag;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.OrganizationServiceUtil;
import com.liferay.portal.service.PermissionServiceUtil;
import com.liferay.portal.service.ResourceServiceUtil;
import com.liferay.portal.service.RoleServiceUtil;
import com.liferay.portal.service.UserGroupServiceUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.admin.util.OmniadminUtil;
import com.liferay.util.CollectionFactory;
import com.liferay.util.GetterUtil;

import java.io.Serializable;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="PermissionCheckerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Charles May
 * @author Brian Wing Shun Chan
 *
 */
public class PermissionCheckerImpl implements PermissionChecker, Serializable {

	public static final int USER_CHECK_ALGORITHM = GetterUtil.getInteger(
		PropsUtil.get(PropsUtil.PERMISSIONS_USER_CHECK_ALGORITHM));

	public PermissionCheckerImpl() {
	}

	public void init(User user, boolean signedIn, boolean checkGuest) {
		this.user = user;
		this.signedIn = signedIn;
		this.checkGuest = checkGuest;
	}

	public void recycle() {
		user = null;
		signedIn = false;
		checkGuest = false;
		bags.clear();
		omniadmin = null;
		resetValues();
	}

	public void setValues(PortletRequest req) {

		// This method is called in com.liferay.portlet.StrutsPortlet to allow
		// developers to hook in additiona parameters from the portlet request.
		// Don't overwrite this method unless you're using Liferay in a 2 tier
		// environment and don't expect to make remote calls. Remote calls to
		// service beans will not have any values set from the portlet request.

	}

	public void resetValues() {
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getUserId() {
		return user.getUserId();
	}

	public boolean isSignedIn() {
		return signedIn;
	}

	public void setSignedIn(boolean signedIn) {
		this.signedIn = signedIn;
	}

	public boolean isCheckGuest() {
		return checkGuest;
	}

	public void setCheckGuest(boolean checkGuest) {
		this.checkGuest = checkGuest;
	}

	public boolean hasPermission(
		long groupId, String name, long primKey, String actionId) {

		return hasPermission(groupId, name, String.valueOf(primKey), actionId);
	}

	public boolean hasPermission(
		long groupId, String name, String primKey, String actionId) {

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		PermissionCheckerBag bag = getBag(groupId);

		if (signedIn && (bag == null)) {
			try {

				Group group = null;

				// If the current group is staging, the live group should be
				// checked for permissions instead

				if (groupId > 0) {
					group = GroupServiceUtil.getGroup(groupId);

					if (group.isStagingGroup()) {
						groupId = group.getLiveGroupId();
						group = group.getLiveGroup();
					}
				}

				// If we are checking permissions an object that belongs to a
				// community, then it's only necessary to check the group that
				// represents the community and not all the groups that the user
				// belongs to. This is so because an object cannot belong to
				// more than one community.

				List userGroups = new ArrayList();
				//List userGroups = UserUtil.getGroups(userId);

				if (groupId > 0) {
					if (GroupServiceUtil.hasUserGroup(
							user.getUserId(), groupId)) {

						userGroups.add(group);
					}
				}

				List userOrgs =
					OrganizationServiceUtil.getUserOrganizations(
						user.getUserId());

				List userOrgGroups =
					GroupServiceUtil.getOrganizationsGroups(userOrgs);

				List userUserGroups =
					UserGroupServiceUtil.getUserUserGroups(user.getUserId());

				List userUserGroupGroups =
					GroupServiceUtil.getUserGroupsGroups(userUserGroups);

				List groups = new ArrayList(
					userGroups.size() + userOrgGroups.size() +
						userUserGroupGroups.size());

				groups.addAll(userGroups);
				groups.addAll(userOrgGroups);
				groups.addAll(userUserGroupGroups);

				List roles = null;

				if ((USER_CHECK_ALGORITHM == 3) ||
					(USER_CHECK_ALGORITHM == 4)) {

					roles = RoleServiceUtil.getUserRelatedRoles(
						user.getUserId(), groups);

					List userGroupRoles = RoleServiceUtil.getUserGroupRoles(
						user.getUserId(), groupId);

					roles.addAll(userGroupRoles);
				}
				else {
					roles = new ArrayList();
				}

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Creating bag for " + groupId + " " + name + " " +
							primKey + " " + actionId + " takes " +
								stopWatch.getTime() + " ms");
				}

				bag = new PermissionCheckerBagImpl(
					user.getUserId(), userGroups, userOrgs, userOrgGroups,
					userUserGroupGroups, groups, roles);

				putBag(groupId, bag);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (user == null) {
			return false;
		}

		Boolean value = PermissionCacheUtil.hasPermission(
			user.getUserId(), groupId, name, primKey, actionId);

		if (value == null) {
			value = new Boolean(
				hasPermissionImpl(groupId, name, primKey, actionId));

			PermissionCacheUtil.putPermission(
				user.getUserId(), groupId, name, primKey, actionId, value);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Checking permission for " + groupId + " " + name + " " +
						primKey + " " + actionId + " takes " +
							stopWatch.getTime() + " ms");
			}
		}

		return value.booleanValue();
	}

	public boolean isOmniadmin() {
		if (omniadmin == null) {
			omniadmin = new Boolean(OmniadminUtil.isOmniadmin(getUserId()));
		}

		return omniadmin.booleanValue();
	}

	protected PermissionCheckerBag getBag(long groupId) {
		return (PermissionCheckerBag)bags.get(new Long(groupId));
	}

	protected boolean hasPermissionImpl(
		long groupId, String name, String primKey, String actionId) {

		try {
			if (!signedIn) {
				return hasGuestPermission(name, primKey, actionId);
			}
			else {
				boolean value = false;

				if (checkGuest) {
					signedIn = false;

					value = hasGuestPermission(name, primKey, actionId);

					signedIn = true;
				}

				if (!value) {
					value = hasUserPermission(
						groupId, name, primKey, actionId, true);
				}

				return value;
			}
		}
		catch (Exception e) {
			_log.error(e, e);

			return false;
		}
	}

	protected boolean hasGuestPermission(
			String name, String primKey, String actionId)
		throws PortalException, RemoteException, SystemException {

		if (name.indexOf(StringPool.PERIOD) != -1) {

			// Check unsupported model actions

			List actions = ResourceActionsUtil.
				getModelResourceGuestUnsupportedActions(name);

			if (actions.contains(actionId)) {
				return false;
			}
		}
		else {

			// Check unsupported portlet actions

			List actions = ResourceActionsUtil.
				getPortletResourceGuestUnsupportedActions(name);

			if (actions.contains(actionId)) {
				return false;
			}
		}

		long companyId = user.getCompanyId();

		Group guestGroup = GroupServiceUtil.getGroup(
			companyId, GroupImpl.GUEST);

		// Create bag for Guest group

		PermissionCheckerBag bag = getBag(guestGroup.getGroupId());

		if (bag == null) {
			List groups = new ArrayList();

			groups.add(guestGroup);

			List roles = RoleServiceUtil.getUserRoles(user.getUserId());

			bag = new PermissionCheckerBagImpl(
				user.getUserId(), new ArrayList(), new ArrayList(),
				new ArrayList(), new ArrayList(), groups, roles);

			putBag(guestGroup.getGroupId(), bag);
		}

		try {
			return hasUserPermission(
				guestGroup.getGroupId(), name, primKey, actionId, false);
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean hasUserPermission(
			long groupId, String name, String primKey, String actionId,
			boolean checkAdmin)
		throws Exception {

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		long companyId = user.getCompanyId();

		if (checkAdmin && isAdmin(companyId, groupId, name)) {
			return true;
		}

		logHasUserPermission(groupId, name, primKey, actionId, stopWatch, 1);

		// Individual

		long[] resourceIds = new long[4];

		try {
			Resource resource = ResourceServiceUtil.getResource(
				companyId, name, ResourceImpl.SCOPE_INDIVIDUAL, primKey);

			resourceIds[0] = resource.getResourceId();
		}
		catch (NoSuchResourceException nsre) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Resource " + companyId + " " + name + " " +
						ResourceImpl.SCOPE_INDIVIDUAL + " " + primKey +
							" does not exist");
			}
		}

		logHasUserPermission(groupId, name, primKey, actionId, stopWatch, 2);

		// Group

		try {
			if (groupId > 0) {
				Resource resource = ResourceServiceUtil.getResource(
					companyId, name, ResourceImpl.SCOPE_GROUP,
					String.valueOf(groupId));

				resourceIds[1] = resource.getResourceId();
			}
		}
		catch (NoSuchResourceException nsre) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Resource " + companyId + " " + name + " " +
						ResourceImpl.SCOPE_GROUP + " " + groupId +
							" does not exist");
			}
		}

		logHasUserPermission(groupId, name, primKey, actionId, stopWatch, 3);

		// Group template

		try {
			if (groupId > 0) {
				Resource resource = ResourceServiceUtil.getResource(
					companyId, name, ResourceImpl.SCOPE_GROUP_TEMPLATE,
					String.valueOf(GroupImpl.DEFAULT_PARENT_GROUP_ID));

				resourceIds[2] = resource.getResourceId();
			}
		}
		catch (NoSuchResourceException nsre) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Resource " + companyId + " " + name + " " +
						ResourceImpl.SCOPE_GROUP_TEMPLATE + " " +
							GroupImpl.DEFAULT_PARENT_GROUP_ID +
								" does not exist");
			}
		}

		logHasUserPermission(groupId, name, primKey, actionId, stopWatch, 4);

		// Company

		try {
			Resource resource = ResourceServiceUtil.getResource(
				companyId, name, ResourceImpl.SCOPE_COMPANY,
				String.valueOf(companyId));

			resourceIds[3] = resource.getResourceId();
		}
		catch (NoSuchResourceException nsre) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Resource " + companyId + " " + name + " " +
						ResourceImpl.SCOPE_COMPANY + " " + companyId +
							" does not exist");
			}
		}

		logHasUserPermission(groupId, name, primKey, actionId, stopWatch, 5);

		// Check if user has access to perform the action on the given
		// resource scopes. The resources are scoped to check first for an
		// individual class, then for the group that the class may belong
		// to, and then for the company that the class belongs to.

		PermissionCheckerBag bag = getBag(groupId);

		boolean value = PermissionServiceUtil.hasUserPermissions(
			user.getUserId(), groupId, actionId, resourceIds, bag);

		logHasUserPermission(groupId, name, primKey, actionId, stopWatch, 6);

		return value;
	}

	protected boolean isAdmin(long companyId, long groupId, String name)
		throws Exception {

		PermissionCheckerBag bag = getBag(groupId);

		if (bag == null) {
			_log.error("Bag should never be null");
		}

		if (bag.isCompanyAdmin(companyId)) {
			return true;
		}

		if (bag.isCommunityAdmin(this, companyId, groupId, name)) {
			return true;
		}

		// You can further modify this method to check if the user has the Admin
		// permission to a portlet that the checked resource belongs to. This
		// would then allow you to set up Admins scoped for a specific portlet.

		return false;
	}

	protected void logHasUserPermission(
		long groupId, String name, String primKey, String actionId,
		StopWatch stopWatch, int block) {

		if (!_log.isDebugEnabled()) {
			return;
		}

		_log.debug(
			"Checking user permission block " + block + " for " + groupId +
				" " + name + " " + primKey + " " + actionId + " takes " +
					stopWatch.getTime() + " ms");
	}

	protected void putBag(long groupId, PermissionCheckerBag bag) {
		bags.put(new Long(groupId), bag);
	}

	protected static final String RESULTS_SEPARATOR = "_RESULTS_SEPARATOR_";

	protected User user;
	protected boolean signedIn;
	protected boolean checkGuest;
	protected Map bags = CollectionFactory.getHashMap();
	protected Boolean omniadmin;

	private static Log _log = LogFactory.getLog(PermissionCheckerImpl.class);

}