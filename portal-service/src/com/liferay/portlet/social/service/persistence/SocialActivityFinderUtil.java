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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="SocialActivityFinderUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SocialActivityFinderUtil {
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getFinder().countByGroupId(groupId);
	}

	public static int countByGroupUsers(long groupId)
		throws com.liferay.portal.SystemException {
		return getFinder().countByGroupUsers(groupId);
	}

	public static int countByOrganizationId(long organizationId)
		throws com.liferay.portal.SystemException {
		return getFinder().countByOrganizationId(organizationId);
	}

	public static int countByOrganizationUsers(long organizationId)
		throws com.liferay.portal.SystemException {
		return getFinder().countByOrganizationUsers(organizationId);
	}

	public static int countByRelation(long userId)
		throws com.liferay.portal.SystemException {
		return getFinder().countByRelation(userId);
	}

	public static int countByRelationType(long userId, int type)
		throws com.liferay.portal.SystemException {
		return getFinder().countByRelationType(userId, type);
	}

	public static int countByUserGroups(long userId)
		throws com.liferay.portal.SystemException {
		return getFinder().countByUserGroups(userId);
	}

	public static int countByUserGroupsAndOrganizations(long userId)
		throws com.liferay.portal.SystemException {
		return getFinder().countByUserGroupsAndOrganizations(userId);
	}

	public static int countByUserOrganizations(long userId)
		throws com.liferay.portal.SystemException {
		return getFinder().countByUserOrganizations(userId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getFinder().findByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByGroupUsers(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getFinder().findByGroupUsers(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByOrganizationId(
		long organizationId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getFinder().findByOrganizationId(organizationId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByOrganizationUsers(
		long organizationId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getFinder().findByOrganizationUsers(organizationId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByRelation(
		long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getFinder().findByRelation(userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByRelationType(
		long userId, int type, int start, int end)
		throws com.liferay.portal.SystemException {
		return getFinder().findByRelationType(userId, type, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByUserGroups(
		long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getFinder().findByUserGroups(userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByUserGroupsAndOrganizations(
		long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getFinder().findByUserGroupsAndOrganizations(userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByUserOrganizations(
		long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getFinder().findByUserOrganizations(userId, start, end);
	}

	public static SocialActivityFinder getFinder() {
		if (_finder == null) {
			_finder = (SocialActivityFinder)PortalBeanLocatorUtil.locate(SocialActivityFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(SocialActivityFinder finder) {
		_finder = finder;
	}

	private static SocialActivityFinder _finder;
}