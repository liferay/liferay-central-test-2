/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.document.library.demo.data.creator.internal;

import com.liferay.document.library.demo.data.creator.FileEntryDemoDataCreator;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.exception.ImageResolutionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(
	property = {"source=unsplash", "type=image"},
	service = FileEntryDemoDataCreator.class
)
public class UnsplashFileEntryDemoDataCreatorImpl
	implements FileEntryDemoDataCreator {

	@Override
	public FileEntry create(long userId, long folderId)
		throws IOException, PortalException {

		String sourceFileName = UUID.randomUUID().toString() + ".jpeg";

		return create(userId, folderId, sourceFileName);
	}

	@Override
	public FileEntry create(long userId, long folderId, String name)
		throws IOException, PortalException {

		Folder folder = _dlAppLocalService.getFolder(folderId);

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			userId, folder.getGroupId(), folderId, name, "image/jpeg",
			_fetchRemoteFile(), new ServiceContext());

		_fileEntryIds.add(fileEntry.getFileEntryId());

		return fileEntry;
	}

	@Override
	public void delete() throws PortalException {
		for (long fileEntryId : _fileEntryIds) {
			_dlAppLocalService.deleteFileEntry(fileEntryId);
		}
	}

	@Reference(unbind = "-")
	protected void setDlAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	private byte[] _fetchRemoteFile()
		throws ImageResolutionException, IOException {

		URL url = _getNextUrl();

		InputStream inputStream = null;

		try {
			inputStream = url.openStream();

			return FileUtil.getBytes(inputStream);
		}
		finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

	private URL _getNextUrl() throws MalformedURLException {
		_categoryIndex++;

		if (_categoryIndex == _categories.size()) {
			_categoryIndex = 0;
		}

		String urlString = String.format(
			"https://source.unsplash.com/category/%s/1920x1080",
			_categories.get(_categoryIndex));

		return new URL(urlString);
	}

	private static final List<String> _categories = new ArrayList<>();

	static {
		_categories.add("buildings");
		_categories.add("food");
		_categories.add("nature");
		_categories.add("people");
		_categories.add("technology");
		_categories.add("objects");
	}

	private int _categoryIndex = -1;
	private DLAppLocalService _dlAppLocalService;
	private final List<Long> _fileEntryIds = new ArrayList<>();

}