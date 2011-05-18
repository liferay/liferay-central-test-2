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

package com.liferay.documentlibrary.service.impl;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.documentlibrary.NoSuchContentException;
import com.liferay.documentlibrary.model.Content;
import com.liferay.documentlibrary.service.ContentLocalService;
import com.liferay.documentlibrary.service.persistence.ContentPersistence;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;

import java.io.InputStream;

import java.util.List;

/**
 * @author Shuyang Zhou
 * @author Michael Chen
 */
public class ContentLocalServiceImpl implements ContentLocalService {

	public void addContent(
			long companyId, String portletId, long groupId, long repositoryId,
			String path, String version, byte[] bytes)
		throws SystemException {

		long contentId = _counterLocalService.increment();

		OutputBlob outputBlob = new OutputBlob(
			new UnsyncByteArrayInputStream(bytes), bytes.length);

		Content content = new Content(
			contentId, companyId, portletId, groupId, repositoryId, path,
			version, outputBlob, bytes.length);

		_contentPersistence.update(content);
	}

	public void addContent(
			long companyId, String portletId, long groupId, long repositoryId,
			String path, String version, InputStream inputStream, long size)
		throws SystemException {

		long contentId = _counterLocalService.increment();

		OutputBlob outputBlob = new OutputBlob(inputStream, size);

		Content content = new Content(
			contentId, companyId, portletId, groupId, repositoryId, path,
			version, outputBlob, size);

		_contentPersistence.update(content);
	}

	public Content getContent(long companyId, long repositoryId, String path)
		throws NoSuchContentException, SystemException {

		return _contentPersistence.findByC_R_P(companyId, repositoryId, path);
	}

	public Content getContent(
			long companyId, long repositoryId, String path, String version)
		throws NoSuchContentException, SystemException {

		return _contentPersistence.findByC_R_P_V(
			companyId, repositoryId, path, version);
	}

	public String[] getContentNames(
			long companyId, long repositoryId, String path)
		throws SystemException {

		List<String> list = _contentPersistence.findNamesByC_R_P(
			companyId, repositoryId, path);

		return list.toArray(new String[list.size()]);
	}

	public long getContentSize(long companyId, long repositoryId, String path)
		throws SystemException {

		return _contentPersistence.findSizeByC_R_P(
			companyId, repositoryId, path);
	}

	public boolean hasContent(
			long companyId, long repositoryId, String path, String version)
		throws SystemException {

		int count = _contentPersistence.countByC_R_P_V(
			companyId, repositoryId, path, version);

		if (count > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean removeByC_P_R_P(
			long companyId, String portletId, long repositoryId, String path)
		throws SystemException {

		return _contentPersistence.removeByC_P_R_P(
			companyId, portletId, repositoryId, path);
	}

	public boolean removeByC_R_P_V(
			long companyId, long repositoryId, String path, String version)
		throws SystemException {

		return _contentPersistence.removeByC_R_P_V(
			companyId, repositoryId, path, version);
	}

	public void removeByC_R_P(long companyId, long repositoryId, String path)
		throws SystemException {

		_contentPersistence.removeByC_R_P(companyId, repositoryId, path);
	}

	public void updateContent(
			long companyId, String portletId, long groupId, long repositoryId,
			long newRepositoryId, String path)
		throws NoSuchContentException, SystemException {

		_contentPersistence.update(
			companyId, repositoryId, path, newRepositoryId);
	}

	public void updateContent(
			long companyId, String portletId, long groupId, long repositoryId,
			String path, String newPath)
		throws NoSuchContentException, SystemException {

		_contentPersistence.update(
			companyId, repositoryId, path, newPath);
	}

	@BeanReference(type = ContentPersistence.class)
	private ContentPersistence _contentPersistence;

	@BeanReference(type = CounterLocalService.class)
	private CounterLocalService _counterLocalService;

}