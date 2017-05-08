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

package com.liferay.journal.web.internal.upload;

import com.liferay.document.library.kernel.util.DLValidator;
import com.liferay.journal.configuration.JournalFileUploadsConfiguration;
import com.liferay.journal.service.permission.JournalPermission;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.ImageTypeException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourcePermissionCheckerUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UniqueFileNameProvider;
import com.liferay.upload.UploadFileEntryHandler;

import java.io.IOException;
import java.io.InputStream;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 * @author Alejandro Tardín
 */
@Component(
	configurationPid = "com.liferay.journal.configuration.JournalFileUploadsConfiguration",
	immediate = true, service = ImageJournalUploadFileEntryHandler.class
)
public class ImageJournalUploadFileEntryHandler
	implements UploadFileEntryHandler {

	@Override
	public FileEntry upload(UploadPortletRequest uploadPortletRequest)
		throws IOException, PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)uploadPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_checkPermission(
			themeDisplay.getScopeGroupId(),
			themeDisplay.getPermissionChecker());

		String fileName = uploadPortletRequest.getFileName(_PARAMETER_NAME);
		long size = uploadPortletRequest.getSize(_PARAMETER_NAME);

		_validateFile(fileName, size);

		String contentType = uploadPortletRequest.getContentType(
			_PARAMETER_NAME);

		try (InputStream inputStream =
				uploadPortletRequest.getFileAsStream(_PARAMETER_NAME)) {

			String uniqueFileName = _uniqueFileNameProvider.provide(
				fileName, curFileName -> _exists(themeDisplay, curFileName));

			return TempFileEntryUtil.addTempFileEntry(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
				_TEMP_FOLDER_NAME, uniqueFileName, inputStream, contentType);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_journalFileUploadsConfiguration = ConfigurableUtil.createConfigurable(
			JournalFileUploadsConfiguration.class, properties);
	}

	private void _checkPermission(
			long groupId, PermissionChecker permissionChecker)
		throws PortalException {

		boolean containsResourcePermission =
			ResourcePermissionCheckerUtil.containsResourcePermission(
				permissionChecker, JournalPermission.RESOURCE_NAME, groupId,
				ActionKeys.ADD_ARTICLE);

		if (!containsResourcePermission) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, JournalPermission.RESOURCE_NAME, groupId,
				ActionKeys.ADD_ARTICLE);
		}
	}

	private boolean _exists(ThemeDisplay themeDisplay, String curFileName) {
		try {
			if (TempFileEntryUtil.getTempFileEntry(
					themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
					_TEMP_FOLDER_NAME, curFileName) != null) {

				return true;
			}

			return false;
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return false;
		}
	}

	private void _validateFile(String fileName, long size)
		throws PortalException {

		_dlValidator.validateFileSize(fileName, size);

		String extension = FileUtil.getExtension(fileName);

		for (String imageExtension :
				_journalFileUploadsConfiguration.imageExtensions()) {

			if (StringPool.STAR.equals(imageExtension) ||
				imageExtension.equals(StringPool.PERIOD + extension)) {

				return;
			}
		}

		throw new ImageTypeException(
			"Invalid image type for file name " + fileName);
	}

	private static final String _PARAMETER_NAME = "imageSelectorFileName";

	private static final String _TEMP_FOLDER_NAME =
		ImageJournalUploadFileEntryHandler.class.getName();

	private static final Log _log = LogFactoryUtil.getLog(
		ImageJournalUploadFileEntryHandler.class);

	@Reference
	private DLValidator _dlValidator;

	private JournalFileUploadsConfiguration _journalFileUploadsConfiguration;

	@Reference
	private UniqueFileNameProvider _uniqueFileNameProvider;

}