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

package com.liferay.adaptive.media.image.internal.util;

import com.liferay.adaptive.media.AdaptiveMediaRuntimeException;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationEntry;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = ImageStorage.class)
public class ImageStorage {

	public void copy(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion) {

		try {
			String destinationBasePath = getFileVersionPath(
				destinationFileVersion);

			String sourceBasePath = getFileVersionPath(sourceFileVersion);

			String[] sourceFileNames = DLStoreUtil.getFileNames(
				sourceFileVersion.getCompanyId(), CompanyConstants.SYSTEM,
				sourceBasePath);

			for (String sourceFileName : sourceFileNames) {
				try (InputStream inputStream = getFileAsStream(
						sourceFileVersion.getCompanyId(), sourceFileName)) {

					String destinationPath =
						destinationBasePath + sourceFileName;

					DLStoreUtil.addFile(
						sourceFileVersion.getCompanyId(),
						CompanyConstants.SYSTEM, destinationPath, false,
						inputStream);
				}
			}
		}
		catch (IOException | PortalException e) {
			throw new AdaptiveMediaRuntimeException.IOException(e);
		}
	}

	public void delete(FileVersion fileVersion) {
		String fileVersionPath = getFileVersionPath(fileVersion);

		DLStoreUtil.deleteDirectory(
			fileVersion.getCompanyId(), CompanyConstants.SYSTEM,
			fileVersionPath);
	}

	public InputStream getContentStream(
		FileVersion fileVersion,
		ImageAdaptiveMediaConfigurationEntry configurationEntry) {

		try {
			String fileVersionVariantPath = getFileVersionVariantPath(
				fileVersion, configurationEntry);

			return getFileAsStream(
				fileVersion.getCompanyId(), fileVersionVariantPath);
		}
		catch (PortalException pe) {
			throw new AdaptiveMediaRuntimeException.IOException(pe);
		}
	}

	public void save(
		FileVersion fileVersion,
		ImageAdaptiveMediaConfigurationEntry configurationEntry,
		InputStream inputStream) {

		try {
			String fileVersionVariantPath = getFileVersionVariantPath(
				fileVersion, configurationEntry);

			DLStoreUtil.addFile(
				fileVersion.getCompanyId(), CompanyConstants.SYSTEM,
				fileVersionVariantPath, false, inputStream);
		}
		catch (PortalException pe) {
			throw new AdaptiveMediaRuntimeException.IOException(pe);
		}
	}

	protected File getFile(long companyId, String path) throws PortalException {
		return DLStoreUtil.getFile(companyId, CompanyConstants.SYSTEM, path);
	}

	protected InputStream getFileAsStream(long companyId, String path)
		throws PortalException {

		return DLStoreUtil.getFileAsStream(
			companyId, CompanyConstants.SYSTEM, path);
	}

	protected String getFileVersionPath(FileVersion fileVersion) {
		return String.format(
			"adaptive/%d/%d/%d/%d/", fileVersion.getGroupId(),
			fileVersion.getRepositoryId(), fileVersion.getFileEntryId(),
			fileVersion.getFileVersionId());
	}

	protected String getFileVersionVariantPath(
		FileVersion fileVersion,
		ImageAdaptiveMediaConfigurationEntry configurationEntry) {

		String basePath = getFileVersionPath(fileVersion);

		return basePath + configurationEntry.getUUID();
	}

}