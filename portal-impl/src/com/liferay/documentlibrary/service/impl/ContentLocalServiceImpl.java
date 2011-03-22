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
import com.liferay.documentlibrary.model.Content;
import com.liferay.documentlibrary.service.ContentLocalService;
import com.liferay.documentlibrary.service.persistence.ContentPersistence;
import com.liferay.documentlibrary.util.Hook;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;

import java.io.InputStream;

/**
 * @author Shuyang Zhou
 */
public class ContentLocalServiceImpl implements ContentLocalService {

	public void addContent(
			long companyId, String portletId, long groupId, long repositoryId,
			String path, byte[] bytes)
		throws SystemException {

		long contentId = _counterLocalService.increment();

		OutputBlob outputBlob = new OutputBlob(
			new UnsyncByteArrayInputStream(bytes), bytes.length);

		Content content = new Content(
			contentId, companyId, portletId, groupId, repositoryId, path,
			Hook.DEFAULT_VERSION, outputBlob, bytes.length);

		_contentPersistence.update(content);
	}

	public void addContent(
			long companyId, String portletId, long groupId, long repositoryId,
			String path, InputStream inputStream, long size)
		throws SystemException {

		long contentId = _counterLocalService.increment();

		OutputBlob outputBlob = new OutputBlob(inputStream, size);

		Content content = new Content(
			contentId, companyId, portletId, groupId, repositoryId, path,
			Hook.DEFAULT_VERSION, outputBlob, size);

		_contentPersistence.update(content);
	}

	public boolean deleteContent(
			long companyId, long repositoryId, String path, String version)
		throws SystemException {

		return _contentPersistence.removeByC_R_P_V(
			companyId, repositoryId, path, version);
	}

	public Content getContent(long companyId, long repositoryId, String path)
		throws SystemException {

		return _contentPersistence.findByC_P_N(companyId, repositoryId, path);
	}

	public Content getContent(
			long companyId, long repositoryId, String path, String version)
		throws SystemException {

		return _contentPersistence.findByC_R_P_V(
			companyId, repositoryId, path, version);
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

	@BeanReference(type = ContentPersistence.class)
	private ContentPersistence _contentPersistence;

	@BeanReference(type = CounterLocalService.class)
	private CounterLocalService _counterLocalService;

}