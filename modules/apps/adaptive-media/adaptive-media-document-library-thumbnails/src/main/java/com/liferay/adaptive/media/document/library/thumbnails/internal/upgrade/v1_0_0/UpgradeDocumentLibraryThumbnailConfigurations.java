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

package com.liferay.adaptive.media.document.library.thumbnails.internal.upgrade.v1_0_0;

import com.liferay.adaptive.media.exception.AdaptiveMediaImageConfigurationException;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationHelper;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.util.PrefsPropsUtil;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Roberto Díaz
 */
public class UpgradeDocumentLibraryThumbnailConfigurations
	extends UpgradeProcess {

	public UpgradeDocumentLibraryThumbnailConfigurations(
		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper,
		CompanyLocalService companyLocalService) {

		_adaptiveMediaImageConfigurationHelper =
			adaptiveMediaImageConfigurationHelper;
		_companyLocalService = companyLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			int dlFileEntryThumbnailMaxHeight = PrefsPropsUtil.getInteger(
				PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_HEIGHT);
			int dlFileEntryThumbnailMaxWidth = PrefsPropsUtil.getInteger(
				PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_WIDTH);

			if ((dlFileEntryThumbnailMaxHeight > 0) &&
				(dlFileEntryThumbnailMaxWidth > 0)) {

				_createAdaptiveMediaDocumentLibraryThumbnailConfiguration(
					"dl.file.entry.thumbnail", dlFileEntryThumbnailMaxHeight,
					dlFileEntryThumbnailMaxWidth);
			}

			int dlFileEntryThumbnailCustom1MaxHeight =
				PrefsPropsUtil.getInteger(
					PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_1_MAX_HEIGHT);
			int dlFileEntryThumbnailCustom1MaxWidth = PrefsPropsUtil.getInteger(
				PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_1_MAX_WIDTH);

			if ((dlFileEntryThumbnailCustom1MaxHeight > 0) &&
				(dlFileEntryThumbnailCustom1MaxWidth > 0)) {

				_createAdaptiveMediaDocumentLibraryThumbnailConfiguration(
					"dl.file.entry.thumbnail.custom1",
					dlFileEntryThumbnailCustom1MaxHeight,
					dlFileEntryThumbnailCustom1MaxWidth);
			}

			int dlFileEntryThumbnailCustom2MaxHeight =
				PrefsPropsUtil.getInteger(
					PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_2_MAX_HEIGHT);
			int dlFileEntryThumbnailCustom2MaxWidth = PrefsPropsUtil.getInteger(
				PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_2_MAX_WIDTH);

			if ((dlFileEntryThumbnailCustom2MaxHeight > 0) &&
				(dlFileEntryThumbnailCustom2MaxWidth > 0)) {

				_createAdaptiveMediaDocumentLibraryThumbnailConfiguration(
					"dl.file.entry.thumbnail.custom2",
					dlFileEntryThumbnailCustom2MaxHeight,
					dlFileEntryThumbnailCustom2MaxWidth);
			}
		}
	}

	private void _createAdaptiveMediaDocumentLibraryThumbnailConfiguration(
			String name, int height, int width)
		throws AdaptiveMediaImageConfigurationException, IOException {

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", String.valueOf(height));
		properties.put("max-width", String.valueOf(width));

		List<Company> companies = _companyLocalService.getCompanies();

		for (Company company : companies) {
			_adaptiveMediaImageConfigurationHelper.
				addAdaptiveMediaImageConfigurationEntry(
					company.getCompanyId(), name, StringPool.BLANK,
					PortalUUIDUtil.generate(), properties);
		}
	}

	private final AdaptiveMediaImageConfigurationHelper
		_adaptiveMediaImageConfigurationHelper;
	private final CompanyLocalService _companyLocalService;

}