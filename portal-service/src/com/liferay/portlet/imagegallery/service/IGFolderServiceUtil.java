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

package com.liferay.portlet.imagegallery.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="IGFolderServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link IGFolderService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       IGFolderService
 * @generated
 */
public class IGFolderServiceUtil {
	public static com.liferay.portlet.imagegallery.model.IGFolder addFolder(
		long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addFolder(parentFolderId, name, description, serviceContext);
	}

	public static com.liferay.portlet.imagegallery.model.IGFolder copyFolder(
		long sourceFolderId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .copyFolder(sourceFolderId, parentFolderId, name,
			description, serviceContext);
	}

	public static void deleteFolder(long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteFolder(folderId);
	}

	public static com.liferay.portlet.imagegallery.model.IGFolder getFolder(
		long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getFolder(folderId);
	}

	public static com.liferay.portlet.imagegallery.model.IGFolder getFolder(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getFolder(groupId, parentFolderId, name);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> getFolders(
		long groupId, long parentFolderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getFolders(groupId, parentFolderId);
	}

	public static com.liferay.portlet.imagegallery.model.IGFolder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentFolder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateFolder(folderId, parentFolderId, name, description,
			mergeWithParentFolder, serviceContext);
	}

	public static IGFolderService getService() {
		if (_service == null) {
			_service = (IGFolderService)PortalBeanLocatorUtil.locate(IGFolderService.class.getName());
		}

		return _service;
	}

	public void setService(IGFolderService service) {
		_service = service;
	}

	private static IGFolderService _service;
}