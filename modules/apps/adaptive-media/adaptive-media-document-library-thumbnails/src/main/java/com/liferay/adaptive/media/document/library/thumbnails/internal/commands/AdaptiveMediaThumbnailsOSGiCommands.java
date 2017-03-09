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

package com.liferay.adaptive.media.document.library.thumbnails.internal.commands;

import com.liferay.adaptive.media.document.library.thumbnails.internal.util.comparator.AdaptiveMediaConfigurationPropertiesComparator;
import com.liferay.adaptive.media.document.library.thumbnails.internal.util.comparator.ComparatorUtil;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationHelper;
import com.liferay.adaptive.media.image.constants.AdaptiveMediaImageConstants;
import com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry;
import com.liferay.adaptive.media.image.service.AdaptiveMediaImageEntryLocalService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.document.library.kernel.util.DLPreviewableProcessor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;

import java.awt.image.RenderedImage;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"osgi.command.function=check", "osgi.command.function=cleanUp",
		"osgi.command.function=migrate", "osgi.command.scope=thumbnails"
	},
	service = AdaptiveMediaThumbnailsOSGiCommands.class
)
public class AdaptiveMediaThumbnailsOSGiCommands {

	public void check(String... companyIds) {
		System.out.println("Company ID\t# of thumbnails pending migration");
		System.out.println("-------------------------------------------------");

		int total = 0;

		for (long companyId : _getCompanyIds(companyIds)) {
			try {
				String[] fileNames = DLStoreUtil.getFileNames(
					companyId, DLPreviewableProcessor.REPOSITORY_ID,
					DLPreviewableProcessor.THUMBNAIL_PATH);

				int companyTotal = 0;

				for (String fileName : fileNames) {
					FileVersion fileVersion = _getFileVersion(fileName);

					if ((fileVersion == null) ||
						!_isMimeTypeSupported(fileVersion)) {

						continue;
					}

					companyTotal += 1;
				}

				System.out.printf("%d\t\t%d%n", companyId, companyTotal);

				total += companyTotal;
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		System.out.printf("%nTOTAL: %d%n", total);
	}

	public void cleanUp(String... companyIds) {
		for (long companyId : _getCompanyIds(companyIds)) {
			try {
				String[] fileNames = DLStoreUtil.getFileNames(
					companyId, DLPreviewableProcessor.REPOSITORY_ID,
					DLPreviewableProcessor.THUMBNAIL_PATH);

				for (String fileName : fileNames) {
					FileVersion fileVersion = _getFileVersion(fileName);

					if ((fileVersion == null) ||
						!_isMimeTypeSupported(fileVersion)) {

						continue;
					}

					// See LPS-70788

					String actualFileName = StringUtil.replace(
						fileName, "//", StringPool.SLASH);

					DLStoreUtil.deleteFile(
						companyId, DLPreviewableProcessor.REPOSITORY_ID,
						actualFileName);
				}
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void migrate(String... companyIds) {
		for (long companyId : _getCompanyIds(companyIds)) {
			Collection<AdaptiveMediaImageConfigurationEntry>
				configurationEntries =
					_configurationHelper.
						getAdaptiveMediaImageConfigurationEntries(companyId);

			AdaptiveMediaConfigurationPropertiesComparator<Integer>
				widthComparator = ComparatorUtil.distanceTo(
					"max-width", PropsValues.DL_FILE_ENTRY_THUMBNAIL_MAX_WIDTH);

			AdaptiveMediaConfigurationPropertiesComparator<Integer>
				heightComparator = ComparatorUtil.distanceTo(
					"max-height",
					PropsValues.DL_FILE_ENTRY_THUMBNAIL_MAX_HEIGHT);

			Optional<AdaptiveMediaImageConfigurationEntry>
				configurationEntryOptional =
					configurationEntries.stream().sorted(
						Comparator.comparing(
							AdaptiveMediaImageConfigurationEntry::getProperties,
							widthComparator.thenComparing(heightComparator))).
						findFirst();

			configurationEntryOptional.ifPresent(configurationEntry ->
				_migrate(companyId, configurationEntry));
		}
	}

	private Iterable<Long> _getCompanyIds(String... companyIds) {
		if (companyIds.length == 0) {
			List<Company> companies = _companyLocalService.getCompanies();

			return companies.stream().map(Company::getCompanyId).collect(
				Collectors.toList());
		}

		return Arrays.stream(companyIds).map(Long::parseLong).collect(
			Collectors.toList());
	}

	private FileVersion _getFileVersion(String fileName)
		throws PortalException {

		try {
			Matcher matcher = _FILE_NAME_PATTERN.matcher(fileName);

			if (!matcher.matches()) {
				return null;
			}

			long fileVersionId = Long.parseLong(matcher.group(1));

			FileVersion fileVersion = _dlAppLocalService.getFileVersion(
				fileVersionId);

			if (!_isMimeTypeSupported(fileVersion)) {
				return null;
			}

			return fileVersion;
		}
		catch (PortalException pe) {
			_log.error(
				"Couldn't get fileVersion for thumbnail " + fileName, pe);

			return null;
		}
	}

	private boolean _isMimeTypeSupported(FileVersion fileVersion) {
		Set<String> supportedMimeTypes =
			AdaptiveMediaImageConstants.getSupportedMimeTypes();

		return supportedMimeTypes.contains(fileVersion.getMimeType());
	}

	private void _migrate(
		long companyId,
		AdaptiveMediaImageConfigurationEntry configurationEntry) {

		try {
			String[] fileNames = DLStoreUtil.getFileNames(
				companyId, DLPreviewableProcessor.REPOSITORY_ID,
				DLPreviewableProcessor.THUMBNAIL_PATH);

			for (String fileName : fileNames) {
				FileVersion fileVersion = _getFileVersion(fileName);

				if (fileVersion == null) {
					continue;
				}

				AdaptiveMediaImageEntry imageEntry =
					_imageEntryLocalService.fetchAdaptiveMediaImageEntry(
						configurationEntry.getUUID(),
						fileVersion.getFileVersionId());

				if (imageEntry != null) {
					continue;
				}

				// See LPS-70788

				String actualFileName = StringUtil.replace(
					fileName, "//", StringPool.SLASH);

				byte[] bytes = DLStoreUtil.getFileAsBytes(
					fileVersion.getCompanyId(),
					DLPreviewableProcessor.REPOSITORY_ID, actualFileName);

				ImageBag imageBag = ImageToolUtil.read(bytes);

				RenderedImage renderedImage = imageBag.getRenderedImage();

				_imageEntryLocalService.addAdaptiveMediaImageEntry(
					configurationEntry, fileVersion, renderedImage.getWidth(),
					renderedImage.getHeight(),
					new UnsyncByteArrayInputStream(bytes), bytes.length);
			}
		}
		catch (IOException | PortalException e) {
			_log.error(e);
		}
	}

	private static final Pattern _FILE_NAME_PATTERN = Pattern.compile(
		".*/\\d+/\\d+/\\d+/(\\d+)\\..+$");

	private static final Log _log = LogFactoryUtil.getLog(
		AdaptiveMediaThumbnailsOSGiCommands.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private AdaptiveMediaImageConfigurationHelper _configurationHelper;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private AdaptiveMediaImageEntryLocalService _imageEntryLocalService;

}