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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Transactional;

import java.io.InputStream;

/**
 * @author Shuyang Zhou
 * @author Michael Chen
 */
@Transactional(rollbackFor = {PortalException.class, SystemException.class})
public interface ContentLocalService {

	public void addContent(
			long companyId, String portletId, long groupId, long repositoryId,
			String path, String version, byte[] bytes)
		throws SystemException;

	public void addContent(
			long companyId, String portletId, long groupId, long repositoryId,
			String path, String version, InputStream is, long size)
		throws SystemException;

	public Content getContent(
			long companyId, long repositoryId, String path)
		throws NoSuchContentException, SystemException;

	public Content getContent(
			long companyId, long repositoryId, String path, String version)
		throws NoSuchContentException, SystemException;

	public String[] getContentNames(
			long companyId, long repositoryId, String path)
		throws SystemException;

	public long getContentSize(long companyId, long repositoryId, String path)
		throws SystemException;

	public boolean hasContent(
			long companyId, long repositoryId, String path, String version)
		throws SystemException;

	public boolean removeByC_P_R_P(
			long companyId, String portletId, long repositoryId, String path)
		throws SystemException;

	public boolean removeByC_R_P_V(
			long companyId, long repositoryId, String path, String version)
		throws SystemException;

	public void removeByC_R_P(
			long companyId, long repositoryId, String path)
		throws SystemException;

	public void updateContent(
			long companyId, String portletId, long groupId, long repositoryId,
			long newRepositoryId, String path)
		throws NoSuchContentException, SystemException;

	public void updateContent(
			long companyId, String portletId, long groupId, long repositoryId,
			String path, String newPath)
		throws NoSuchContentException, SystemException;

}