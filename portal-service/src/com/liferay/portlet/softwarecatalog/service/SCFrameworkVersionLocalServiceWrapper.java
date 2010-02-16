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

package com.liferay.portlet.softwarecatalog.service;


/**
 * <a href="SCFrameworkVersionLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link SCFrameworkVersionLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCFrameworkVersionLocalService
 * @generated
 */
public class SCFrameworkVersionLocalServiceWrapper
	implements SCFrameworkVersionLocalService {
	public SCFrameworkVersionLocalServiceWrapper(
		SCFrameworkVersionLocalService scFrameworkVersionLocalService) {
		_scFrameworkVersionLocalService = scFrameworkVersionLocalService;
	}

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion addSCFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.addSCFrameworkVersion(scFrameworkVersion);
	}

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion createSCFrameworkVersion(
		long frameworkVersionId) {
		return _scFrameworkVersionLocalService.createSCFrameworkVersion(frameworkVersionId);
	}

	public void deleteSCFrameworkVersion(long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scFrameworkVersionLocalService.deleteSCFrameworkVersion(frameworkVersionId);
	}

	public void deleteSCFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scFrameworkVersionLocalService.deleteSCFrameworkVersion(scFrameworkVersion);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion getSCFrameworkVersion(
		long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.getSCFrameworkVersion(frameworkVersionId);
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.getSCFrameworkVersions(start, end);
	}

	public int getSCFrameworkVersionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.getSCFrameworkVersionsCount();
	}

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion updateSCFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.updateSCFrameworkVersion(scFrameworkVersion);
	}

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion updateSCFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.updateSCFrameworkVersion(scFrameworkVersion,
			merge);
	}

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion addFrameworkVersion(
		long userId, java.lang.String name, java.lang.String url,
		boolean active, int priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.addFrameworkVersion(userId,
			name, url, active, priority, serviceContext);
	}

	public void addFrameworkVersionResources(long frameworkVersionId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scFrameworkVersionLocalService.addFrameworkVersionResources(frameworkVersionId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFrameworkVersionResources(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion frameworkVersion,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scFrameworkVersionLocalService.addFrameworkVersionResources(frameworkVersion,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFrameworkVersionResources(long frameworkVersionId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scFrameworkVersionLocalService.addFrameworkVersionResources(frameworkVersionId,
			communityPermissions, guestPermissions);
	}

	public void addFrameworkVersionResources(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion frameworkVersion,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scFrameworkVersionLocalService.addFrameworkVersionResources(frameworkVersion,
			communityPermissions, guestPermissions);
	}

	public void deleteFrameworkVersion(long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scFrameworkVersionLocalService.deleteFrameworkVersion(frameworkVersionId);
	}

	public void deleteFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion frameworkVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scFrameworkVersionLocalService.deleteFrameworkVersion(frameworkVersion);
	}

	public void deleteFrameworkVersions(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scFrameworkVersionLocalService.deleteFrameworkVersions(groupId);
	}

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion getFrameworkVersion(
		long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.getFrameworkVersion(frameworkVersionId);
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.getFrameworkVersions(groupId,
			start, end);
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions(
		long groupId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.getFrameworkVersions(groupId,
			active);
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions(
		long groupId, boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.getFrameworkVersions(groupId,
			active, start, end);
	}

	public int getFrameworkVersionsCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.getFrameworkVersionsCount(groupId);
	}

	public int getFrameworkVersionsCount(long groupId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.getFrameworkVersionsCount(groupId,
			active);
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getProductVersionFrameworkVersions(
		long productVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.getProductVersionFrameworkVersions(productVersionId);
	}

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion updateFrameworkVersion(
		long frameworkVersionId, java.lang.String name, java.lang.String url,
		boolean active, int priority)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.updateFrameworkVersion(frameworkVersionId,
			name, url, active, priority);
	}

	public SCFrameworkVersionLocalService getWrappedSCFrameworkVersionLocalService() {
		return _scFrameworkVersionLocalService;
	}

	private SCFrameworkVersionLocalService _scFrameworkVersionLocalService;
}