/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.documentlibrary.service;

import com.liferay.documentlibrary.NoSuchContentException;
import com.liferay.documentlibrary.model.Content;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

import java.io.InputStream;

import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class ContentLocalServiceUtil {

	public static void addContent(
			long companyId, String portletId, long groupId, long repositoryId,
			String path, byte[] bytes)
		throws SystemException {

		getService().addContent(
			companyId, portletId, groupId, repositoryId, path, bytes);
	}

	public static void addContent(
			long companyId, String portletId, long groupId, long repositoryId,
			String path, InputStream inputStream, long size)
		throws SystemException {

		getService().addContent(
			companyId, portletId, groupId, repositoryId, path, inputStream,
			size);
	}

	public static boolean deleteContent(
			long companyId, String portletId, long repositoryId, String path,
			String version)
		throws SystemException {

		return getService().deleteContent(
			companyId, portletId, repositoryId, path, version);
	}

	public static Content getContent(
			long companyId, String portletId, long repositoryId, String path,
			String version)
		throws NoSuchContentException, SystemException {

		return getService().getContent(
			companyId, portletId, repositoryId, path, version);
	}

	public static List<Content> getContents(
			long companyId, String portletId, long repositoryId, String path)
		throws SystemException {

		return getService().getContents(
			companyId, portletId, repositoryId, path);
	}

	public static ContentLocalService getService() {
		if (_service == null) {
			_service = (ContentLocalService)PortalBeanLocatorUtil.locate(
				ContentLocalService.class.getName());

			ReferenceRegistry.registerReference(
				ContentLocalServiceUtil.class, "_service");

			MethodCache.remove(ContentLocalService.class);
		}

		return _service;
	}

	public static boolean hasContent(
			long companyId, String portletId, long repositoryId, String path,
			String version)
		throws SystemException {

		return getService().hasContent(
			companyId, portletId, repositoryId, path, version);
	}

	public void setService(ContentLocalService service) {
		_service = service;
	}

	private static ContentLocalService _service;

}