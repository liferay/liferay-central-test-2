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

package com.liferay.portlet.documentlibrary.service;


/**
 * <a href="DLFileVersionLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link DLFileVersionLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileVersionLocalService
 * @generated
 */
public class DLFileVersionLocalServiceWrapper
	implements DLFileVersionLocalService {
	public DLFileVersionLocalServiceWrapper(
		DLFileVersionLocalService dlFileVersionLocalService) {
		_dlFileVersionLocalService = dlFileVersionLocalService;
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion addDLFileVersion(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion)
		throws com.liferay.portal.SystemException {
		return _dlFileVersionLocalService.addDLFileVersion(dlFileVersion);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion createDLFileVersion(
		long fileVersionId) {
		return _dlFileVersionLocalService.createDLFileVersion(fileVersionId);
	}

	public void deleteDLFileVersion(long fileVersionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_dlFileVersionLocalService.deleteDLFileVersion(fileVersionId);
	}

	public void deleteDLFileVersion(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion)
		throws com.liferay.portal.SystemException {
		_dlFileVersionLocalService.deleteDLFileVersion(dlFileVersion);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _dlFileVersionLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _dlFileVersionLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion getDLFileVersion(
		long fileVersionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _dlFileVersionLocalService.getDLFileVersion(fileVersionId);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> getDLFileVersions(
		int start, int end) throws com.liferay.portal.SystemException {
		return _dlFileVersionLocalService.getDLFileVersions(start, end);
	}

	public int getDLFileVersionsCount()
		throws com.liferay.portal.SystemException {
		return _dlFileVersionLocalService.getDLFileVersionsCount();
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion updateDLFileVersion(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion)
		throws com.liferay.portal.SystemException {
		return _dlFileVersionLocalService.updateDLFileVersion(dlFileVersion);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion updateDLFileVersion(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion,
		boolean merge) throws com.liferay.portal.SystemException {
		return _dlFileVersionLocalService.updateDLFileVersion(dlFileVersion,
			merge);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion getFileVersion(
		long groupId, long folderId, java.lang.String name, double version)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _dlFileVersionLocalService.getFileVersion(groupId, folderId,
			name, version);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> getFileVersions(
		long groupId, long folderId, java.lang.String name, int status)
		throws com.liferay.portal.SystemException {
		return _dlFileVersionLocalService.getFileVersions(groupId, folderId,
			name, status);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion getLatestFileVersion(
		long groupId, long folderId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _dlFileVersionLocalService.getLatestFileVersion(groupId,
			folderId, name);
	}

	public DLFileVersionLocalService getWrappedDLFileVersionLocalService() {
		return _dlFileVersionLocalService;
	}

	private DLFileVersionLocalService _dlFileVersionLocalService;
}