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

package com.liferay.adaptive.media.image.internal.handler;

import com.liferay.adaptive.media.AdaptiveMediaRuntimeException;
import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaConfigurationEntry;
import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaConfigurationHelper;
import com.liferay.adaptive.media.image.internal.util.Tuple;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = PathInterpreter.class)
public class PathInterpreter {

	public Optional<Tuple<FileVersion, Map<String, String>>>
		interpretPath(String pathInfo) {

		try {
			if (pathInfo == null) {
				throw new IllegalArgumentException("pathInfo cannot be null");
			}

			Matcher matcher = _URL_PATTERN.matcher(pathInfo);

			if (!matcher.matches()) {
				return Optional.empty();
			}

			long fileEntryId = Long.valueOf(matcher.group(1));

			FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

			long fileVersionId = _getFileVersionId(matcher);

			FileVersion fileVersion = _getFileVersion(fileEntry, fileVersionId);

			String configurationEntryUUID = _getConfigurationEntryUUID(matcher);

			Optional<ImageAdaptiveMediaConfigurationEntry>
				configurationEntryOptional =
					_configurationHelper.
						getImageAdaptiveMediaConfigurationEntry(
							fileVersion.getCompanyId(), configurationEntryUUID);

			Map<String, String> properties = configurationEntryOptional.
				map(ImageAdaptiveMediaConfigurationEntry::getProperties).
				orElse(new HashMap<>());

			return Optional.of(Tuple.of(fileVersion, properties));
		}
		catch (PortalException pe) {
			throw new AdaptiveMediaRuntimeException(pe);
		}
	}

	@Reference(unbind = "-")
	public void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	@Reference(unbind = "-")
	public void setImageAdaptiveMediaConfigurationHelper(
		ImageAdaptiveMediaConfigurationHelper configurationHelper) {

		_configurationHelper = configurationHelper;
	}

	private String _getConfigurationEntryUUID(Matcher matcher) {
		return matcher.group(3);
	}

	private FileVersion _getFileVersion(FileEntry fileEntry, long fileVersionId)
		throws PortalException {

		if (fileVersionId == 0) {
			return fileEntry.getLatestFileVersion();
		}

		return _dlAppLocalService.getFileVersion(fileVersionId);
	}

	private long _getFileVersionId(Matcher matcher) {
		if (matcher.group(2) == null) {
			return 0;
		}

		return Long.valueOf(matcher.group(2));
	}

	private static final Pattern _URL_PATTERN = Pattern.compile(
		"image/(\\d+)(?:/(\\d+))?/([^/]+)/(?:[^/]+)");

	private ImageAdaptiveMediaConfigurationHelper _configurationHelper;
	private DLAppLocalService _dlAppLocalService;

}