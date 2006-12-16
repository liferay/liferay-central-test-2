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
 * <a href="SRFrameworkVersionServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class SRFrameworkVersionServiceUtil {
	public static com.liferay.portlet.softwarerepository.model.SRFrameworkVersion addFrameworkVersion(
		java.lang.String userId, java.lang.String plid, java.lang.String name,
		boolean active, int priority, java.lang.String url)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		SRFrameworkVersionService srFrameworkVersionService = SRFrameworkVersionServiceFactory.getService();

		return srFrameworkVersionService.addFrameworkVersion(userId, plid,
			name, active, priority, url);
	}

	public static void deleteFrameworkVersion(long frameworkVersionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		SRFrameworkVersionService srFrameworkVersionService = SRFrameworkVersionServiceFactory.getService();
		srFrameworkVersionService.deleteFrameworkVersion(frameworkVersionId);
	}

	public static com.liferay.portlet.softwarerepository.model.SRFrameworkVersion getFrameworkVersion(
		long frameworkVersionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		SRFrameworkVersionService srFrameworkVersionService = SRFrameworkVersionServiceFactory.getService();

		return srFrameworkVersionService.getFrameworkVersion(frameworkVersionId);
	}

	public static java.util.List getFrameworkVersions(java.lang.String groupId)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		SRFrameworkVersionService srFrameworkVersionService = SRFrameworkVersionServiceFactory.getService();

		return srFrameworkVersionService.getFrameworkVersions(groupId);
	}

	public static java.util.List getFrameworkVersions(
		java.lang.String groupId, boolean active)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		SRFrameworkVersionService srFrameworkVersionService = SRFrameworkVersionServiceFactory.getService();

		return srFrameworkVersionService.getFrameworkVersions(groupId, active);
	}

	public static int getFrameworkVersionsCount(java.lang.String groupId)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		SRFrameworkVersionService srFrameworkVersionService = SRFrameworkVersionServiceFactory.getService();

		return srFrameworkVersionService.getFrameworkVersionsCount(groupId);
	}

	public static int getFrameworkVersionsCount(java.lang.String groupId,
		boolean active)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		SRFrameworkVersionService srFrameworkVersionService = SRFrameworkVersionServiceFactory.getService();

		return srFrameworkVersionService.getFrameworkVersionsCount(groupId,
			active);
	}

	public static com.liferay.portlet.softwarerepository.model.SRFrameworkVersion updateFrameworkVersion(
		long frameworkVersionId, java.lang.String name, boolean active,
		int priority, java.lang.String url)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		SRFrameworkVersionService srFrameworkVersionService = SRFrameworkVersionServiceFactory.getService();

		return srFrameworkVersionService.updateFrameworkVersion(frameworkVersionId,
			name, active, priority, url);
	}
}