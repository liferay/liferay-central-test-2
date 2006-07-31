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

package com.liferay.portal.security.permission;

import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.User;
import com.liferay.portal.service.spring.GroupServiceUtil;
import com.liferay.portal.service.spring.OrganizationServiceUtil;
import com.liferay.portal.service.spring.PermissionServiceUtil;
import com.liferay.portal.service.spring.ResourceServiceUtil;
import com.liferay.portal.service.spring.UserGroupServiceUtil;
import com.liferay.util.CollectionFactory;
import com.liferay.util.StringPool;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="PermissionChecker.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Charles May
 * @author  Brian Wing Shun Chan
 *
 */
public class PermissionChecker {

	public PermissionChecker() {
	}

	public void init(User user, boolean signedIn) {
		this.user = user;
		this.signedIn = signedIn;
	}

	public void recycle() {
		user = null;
		signedIn = false;
		permissionCheckerBag = null;
		results.clear();
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

	public boolean isSignedIn() {
		return signedIn;
	}

	public boolean hasPermission(
		String groupId, String name, String primKey, String actionId) {

		long start = 0;

		if (_log.isDebugEnabled()) {
			start = System.currentTimeMillis();
		}

		if (signedIn && (permissionCheckerBag == null)) {
			try {
				List userOrgs =
					OrganizationServiceUtil.getUserOrganizations(
						user.getUserId());

				List userOrgGroups =
					GroupServiceUtil.getOrganizationsGroups(userOrgs);

				List userUserGroups =
					UserGroupServiceUtil.getUserUserGroups(user.getUserId());

				List userUserGroupGroups =
					GroupServiceUtil.getUserGroupsGroups(userUserGroups);

				permissionCheckerBag = new PermissionCheckerBag(
					user.getUserId(), userOrgs, userOrgGroups,
					userUserGroupGroups);
			}
			catch (Exception e) {
				_log.error(StackTraceUtil.getStackTrace(e));
			}
		}

		if (user == null) {
			return false;
		}

		String resultsKey = getResultsKey(groupId, name, primKey, actionId);

		Boolean resultsValue = (Boolean)results.get(resultsKey);

		if (resultsValue == null) {
			try {

				// Cache the check to see if a user belongs to a group to
				// improve performance

				if (signedIn) {
					permissionCheckerBag.hasGroup(groupId);
				}
			}
			catch (Exception e) {
				_log.error(StackTraceUtil.getStackTrace(e));
			}

			resultsValue = new Boolean(
				hasPermissionImpl(groupId, name, primKey, actionId));

			results.put(resultsKey, resultsValue);

			if (_log.isDebugEnabled()) {
				long end = System.currentTimeMillis();

				_log.debug(
					"Checking permission for " + groupId + " " + name + " " +
						primKey + " " + actionId + " takes " + (end - start) +
							" ms");
			}
		}

		return resultsValue.booleanValue();
	}

	protected String getResultsKey(
		String groupId, String name, String primKey, String actionId) {

		return user.getUserId() + RESULTS_SEPARATOR + groupId +
			RESULTS_SEPARATOR + name + RESULTS_SEPARATOR + primKey +
				RESULTS_SEPARATOR + actionId;
	}

	protected boolean hasPermissionImpl(
		String groupId, String name, String primKey, String actionId) {

		try {
			if (!signedIn) {
				return hasGuestPermission(name, primKey, actionId);
			}
			else {
				return hasUserPermission(groupId, name, primKey, actionId);
			}
		}
		catch (Exception e) {
			_log.error(StackTraceUtil.getStackTrace(e));

			return false;
		}
	}

	protected boolean hasGuestPermission(
			String name, String primKey, String actionId)
		throws PortalException, SystemException {

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

		String companyId = user.getActualCompanyId();

		Group guestGroup = GroupServiceUtil.getGroup(companyId, Group.GUEST);

		try {
			Resource resource = ResourceServiceUtil.getResource(
				companyId, name, Resource.TYPE_CLASS, Resource.SCOPE_INDIVIDUAL,
				primKey);

			String resourceId = resource.getResourceId();

			return PermissionServiceUtil.hasGroupPermission(
				guestGroup.getGroupId(), actionId, resourceId);
		}
		catch (NoSuchResourceException nsre) {
			return false;
		}
	}

	protected boolean hasUserPermission(
			String groupId, String name, String primKey, String actionId)
		throws PortalException, SystemException {

		long start = 0;

		if (_log.isDebugEnabled()) {
			start = System.currentTimeMillis();
		}

		String companyId = user.getActualCompanyId();

		if (isAdmin(companyId, groupId, name)) {
			return true;
		}

		start = logHasUserPermission(
			groupId, name, primKey, actionId, start, 1);

		// Individual

		String[] resourceIds = new String[3];

		try {
			Resource resource = ResourceServiceUtil.getResource(
				companyId, name, Resource.TYPE_CLASS,
				Resource.SCOPE_INDIVIDUAL, primKey);

			resourceIds[0] = resource.getResourceId();
		}
		catch (NoSuchResourceException nsre) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Resource " + companyId + " " + name + " " +
						Resource.TYPE_CLASS  + " " + Resource.SCOPE_INDIVIDUAL +
							" " + primKey + " does not exist");
			}
		}

		start = logHasUserPermission(
			groupId, name, primKey, actionId, start, 2);

		// Group

		try {
			if (groupId != null) {
				Resource resource = ResourceServiceUtil.getResource(
					companyId, name, Resource.TYPE_CLASS,
					Resource.SCOPE_GROUP, groupId);

				resourceIds[1] = resource.getResourceId();
			}
		}
		catch (NoSuchResourceException nsre) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Resource " + companyId + " " + name + " " +
						Resource.TYPE_CLASS  + " " + Resource.SCOPE_GROUP +
							" " + groupId + " does not exist");
			}
		}

		start = logHasUserPermission(
			groupId, name, primKey, actionId, start, 3);

		// Company

		try {
			Resource resource = ResourceServiceUtil.getResource(
				companyId, name, Resource.TYPE_CLASS,
				Resource.SCOPE_COMPANY, companyId);

			resourceIds[2] = resource.getResourceId();
		}
		catch (NoSuchResourceException nsre) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Resource " + companyId + " " + name + " " +
						Resource.TYPE_CLASS  + " " + Resource.SCOPE_COMPANY +
							" " + companyId + " does not exist");
			}
		}

		start = logHasUserPermission(
			groupId, name, primKey, actionId, start, 4);

		// Check if user has access to perform the action on the given
		// resource scopes. The resources are scoped to check first for an
		// individual class, then for the group that the class may belong
		// to, and then for the company that the class belongs to.

		boolean value = PermissionServiceUtil.hasUserPermissions(
			user.getUserId(), groupId, actionId, resourceIds,
			permissionCheckerBag);

		start = logHasUserPermission(
			groupId, name, primKey, actionId, start, 5);

		return value;
	}

	protected boolean isAdmin(String companyId, String groupId, String name)
		throws PortalException, SystemException {

		if (permissionCheckerBag.isCompanyAdmin(companyId)) {
			return true;
		}

		if (permissionCheckerBag.isCommunityAdmin(companyId, groupId, name)) {
			return true;
		}

		// You can further modify this method to check if the user has the Admin
		// permission to a portlet that the checked resource belongs to. This
		// would then allow you to set up Admins scoped for a specific portlet.

		return false;
	}

	protected long logHasUserPermission(
		String groupId, String name, String primKey, String actionId,
		long start, int block) {

		if (!_log.isDebugEnabled()) {
			return 0;
		}

		long end = System.currentTimeMillis();

		_log.debug(
			"Checking user permission block " + block + " for " + groupId +
				" " + name + " " + primKey + " " + actionId + " takes " +
					(end - start) + " ms");

		return end;
	}

	protected static final String RESULTS_SEPARATOR = "_RESULTS_SEPARATOR_";

	protected User user;
	protected boolean signedIn;
	protected PermissionCheckerBag permissionCheckerBag;
	protected Map results = CollectionFactory.getHashMap();

	private static Log _log = LogFactory.getLog(PermissionChecker.class);

}