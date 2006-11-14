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

package com.liferay.portlet.imagegallery.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.impl.PrincipalBean;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.permission.IGFolderPermission;
import com.liferay.portlet.imagegallery.service.permission.IGImagePermission;
import com.liferay.portlet.imagegallery.service.spring.IGImageLocalServiceUtil;
import com.liferay.portlet.imagegallery.service.spring.IGImageService;

import java.io.File;

/**
 * <a href="IGImageServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class IGImageServiceImpl
	extends PrincipalBean implements IGImageService {

	public IGImage addImage(
			String folderId, String description, File file, String contentType,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		IGFolderPermission.check(
			getPermissionChecker(), folderId, ActionKeys.ADD_IMAGE);

		return IGImageLocalServiceUtil.addImage(
			getUserId(), folderId, description, file, contentType,
			addCommunityPermissions, addGuestPermissions);
	}

	public IGImage addImage(
			String folderId, String description, File file, String contentType,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		IGFolderPermission.check(
			getPermissionChecker(), folderId, ActionKeys.ADD_IMAGE);

		return IGImageLocalServiceUtil.addImage(
			getUserId(), folderId, description, file, contentType,
			communityPermissions, guestPermissions);
	}

	public void deleteImage(String companyId, String imageId)
		throws PortalException, SystemException {

		IGImagePermission.check(
			getPermissionChecker(), companyId, imageId, ActionKeys.DELETE);

		IGImageLocalServiceUtil.deleteImage(companyId, imageId);
	}

	public IGImage getImage(String companyId, String imageId)
		throws PortalException, SystemException {

		IGImagePermission.check(
			getPermissionChecker(), companyId, imageId, ActionKeys.VIEW);

		return IGImageLocalServiceUtil.getImage(companyId, imageId);
	}

	public IGImage updateImage(
			String imageId, String folderId, String description, File file,
			String contentType)
		throws PortalException, SystemException {

		User user = getUser();

		IGImagePermission.check(
			getPermissionChecker(), user.getCompanyId(), imageId,
			ActionKeys.UPDATE);

		return IGImageLocalServiceUtil.updateImage(
			user.getCompanyId(), imageId, folderId, description, file,
			contentType);
	}

}