/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarerepository.service;

/**
 * <a href="SRFrameworkVersionLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class SRFrameworkVersionLocalServiceUtil {
	public static com.liferay.portlet.softwarerepository.model.SRFrameworkVersion addFrameworkVersion(
		java.lang.String userId, java.lang.String plid, java.lang.String name,
		java.lang.String url, boolean active, int priority,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRFrameworkVersionLocalService srFrameworkVersionLocalService = SRFrameworkVersionLocalServiceFactory.getService();

		return srFrameworkVersionLocalService.addFrameworkVersion(userId, plid,
			name, url, active, priority, addCommunityPermissions,
			addGuestPermissions);
	}

	public static com.liferay.portlet.softwarerepository.model.SRFrameworkVersion addFrameworkVersion(
		java.lang.String userId, java.lang.String plid, java.lang.String name,
		java.lang.String url, boolean active, int priority,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRFrameworkVersionLocalService srFrameworkVersionLocalService = SRFrameworkVersionLocalServiceFactory.getService();

		return srFrameworkVersionLocalService.addFrameworkVersion(userId, plid,
			name, url, active, priority, communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.softwarerepository.model.SRFrameworkVersion addFrameworkVersion(
		java.lang.String userId, java.lang.String plid, java.lang.String name,
		java.lang.String url, boolean active, int priority,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRFrameworkVersionLocalService srFrameworkVersionLocalService = SRFrameworkVersionLocalServiceFactory.getService();

		return srFrameworkVersionLocalService.addFrameworkVersion(userId, plid,
			name, url, active, priority, addCommunityPermissions,
			addGuestPermissions, communityPermissions, guestPermissions);
	}

	public static void addFrameworkVersionResources(long frameworkVersionId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRFrameworkVersionLocalService srFrameworkVersionLocalService = SRFrameworkVersionLocalServiceFactory.getService();
		srFrameworkVersionLocalService.addFrameworkVersionResources(frameworkVersionId,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addFrameworkVersionResources(
		com.liferay.portlet.softwarerepository.model.SRFrameworkVersion frameworkVersion,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRFrameworkVersionLocalService srFrameworkVersionLocalService = SRFrameworkVersionLocalServiceFactory.getService();
		srFrameworkVersionLocalService.addFrameworkVersionResources(frameworkVersion,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addFrameworkVersionResources(long frameworkVersionId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRFrameworkVersionLocalService srFrameworkVersionLocalService = SRFrameworkVersionLocalServiceFactory.getService();
		srFrameworkVersionLocalService.addFrameworkVersionResources(frameworkVersionId,
			communityPermissions, guestPermissions);
	}

	public static void addFrameworkVersionResources(
		com.liferay.portlet.softwarerepository.model.SRFrameworkVersion frameworkVersion,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRFrameworkVersionLocalService srFrameworkVersionLocalService = SRFrameworkVersionLocalServiceFactory.getService();
		srFrameworkVersionLocalService.addFrameworkVersionResources(frameworkVersion,
			communityPermissions, guestPermissions);
	}

	public static void deleteFrameworkVersion(long frameworkVersionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRFrameworkVersionLocalService srFrameworkVersionLocalService = SRFrameworkVersionLocalServiceFactory.getService();
		srFrameworkVersionLocalService.deleteFrameworkVersion(frameworkVersionId);
	}

	public static com.liferay.portlet.softwarerepository.model.SRFrameworkVersion getFrameworkVersion(
		long frameworkVersionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRFrameworkVersionLocalService srFrameworkVersionLocalService = SRFrameworkVersionLocalServiceFactory.getService();

		return srFrameworkVersionLocalService.getFrameworkVersion(frameworkVersionId);
	}

	public static java.util.List getFrameworkVersions(long groupId, int begin,
		int end) throws com.liferay.portal.SystemException {
		SRFrameworkVersionLocalService srFrameworkVersionLocalService = SRFrameworkVersionLocalServiceFactory.getService();

		return srFrameworkVersionLocalService.getFrameworkVersions(groupId,
			begin, end);
	}

	public static java.util.List getFrameworkVersions(long groupId,
		boolean active, int begin, int end)
		throws com.liferay.portal.SystemException {
		SRFrameworkVersionLocalService srFrameworkVersionLocalService = SRFrameworkVersionLocalServiceFactory.getService();

		return srFrameworkVersionLocalService.getFrameworkVersions(groupId,
			active, begin, end);
	}

	public static int getFrameworkVersionsCount(long groupId)
		throws com.liferay.portal.SystemException {
		SRFrameworkVersionLocalService srFrameworkVersionLocalService = SRFrameworkVersionLocalServiceFactory.getService();

		return srFrameworkVersionLocalService.getFrameworkVersionsCount(groupId);
	}

	public static int getFrameworkVersionsCount(long groupId, boolean active)
		throws com.liferay.portal.SystemException {
		SRFrameworkVersionLocalService srFrameworkVersionLocalService = SRFrameworkVersionLocalServiceFactory.getService();

		return srFrameworkVersionLocalService.getFrameworkVersionsCount(groupId,
			active);
	}

	public static java.util.List getProductVersionFrameworkVersions(
		long productVersionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRFrameworkVersionLocalService srFrameworkVersionLocalService = SRFrameworkVersionLocalServiceFactory.getService();

		return srFrameworkVersionLocalService.getProductVersionFrameworkVersions(productVersionId);
	}

	public static com.liferay.portlet.softwarerepository.model.SRFrameworkVersion updateFrameworkVersion(
		long frameworkVersionId, java.lang.String name, java.lang.String url,
		boolean active, int priority)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRFrameworkVersionLocalService srFrameworkVersionLocalService = SRFrameworkVersionLocalServiceFactory.getService();

		return srFrameworkVersionLocalService.updateFrameworkVersion(frameworkVersionId,
			name, url, active, priority);
	}
}