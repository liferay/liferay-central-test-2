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

package com.liferay.adaptive.media.document.library.internal.exportimport.data.handler;

import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.AdaptiveMediaAttribute;
import com.liferay.adaptive.media.exception.AdaptiveMediaException;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntrySerializer;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationHelper;
import com.liferay.adaptive.media.image.finder.AdaptiveMediaImageFinder;
import com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry;
import com.liferay.adaptive.media.image.processor.AdaptiveMediaImageAttribute;
import com.liferay.adaptive.media.image.processor.AdaptiveMediaImageProcessor;
import com.liferay.adaptive.media.image.service.AdaptiveMediaImageEntryLocalService;
import com.liferay.adaptive.media.image.util.AdaptiveMediaImageSerializer;
import com.liferay.document.library.exportimport.data.handler.DLPluggableContentDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;

import java.io.IOException;
import java.io.InputStream;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=com.liferay.portal.kernel.repository.model.FileEntry"
	},
	service = DLPluggableContentDataHandler.class
)
public class AdaptiveMediaImageDLPluggableContentDataHandler
	implements DLPluggableContentDataHandler<FileEntry> {

	@Override
	public void exportContent(
			PortletDataContext portletDataContext, Element fileEntryElement,
			FileEntry fileEntry)
		throws Exception {

		Collection<AdaptiveMediaImageConfigurationEntry> configurationEntries =
			_configurationHelper.getAdaptiveMediaImageConfigurationEntries(
				portletDataContext.getCompanyId());

		configurationEntries.forEach(
			configurationEntry -> _exportConfigurationEntry(
				portletDataContext, configurationEntry));

		_exportMedia(portletDataContext, fileEntry);
	}

	@Override
	public void importContent(
			PortletDataContext portletDataContext, Element fileEntryElement,
			FileEntry fileEntry, FileEntry importedFileEntry)
		throws Exception {

		Collection<AdaptiveMediaImageConfigurationEntry> configurationEntries =
			_configurationHelper.getAdaptiveMediaImageConfigurationEntries(
				portletDataContext.getCompanyId());

		for (AdaptiveMediaImageConfigurationEntry configurationEntry :
				configurationEntries) {

			_importGeneratedMedia(
				portletDataContext, importedFileEntry, configurationEntry);
		}
	}

	private void _exportConfigurationEntry(
		PortletDataContext portletDataContext,
		AdaptiveMediaImageConfigurationEntry configurationEntry) {

		portletDataContext.addZipEntry(
			_getConfigurationEntryBinPath(configurationEntry),
			_imageConfigurationEntrySerializer.serialize(configurationEntry));
	}

	private void _exportMedia(
			PortletDataContext portletDataContext, FileEntry fileEntry)
		throws IOException, PortalException {

		List<FileVersion> fileVersions = fileEntry.getFileVersions(
			WorkflowConstants.STATUS_APPROVED);

		for (FileVersion fileVersion : fileVersions) {
			Stream<AdaptiveMedia<AdaptiveMediaImageProcessor>> stream =
				_finder.getAdaptiveMedia(
					queryBuilder ->
						queryBuilder.allForVersion(fileVersion).done());

			List<AdaptiveMedia<AdaptiveMediaImageProcessor>> mediaList =
				stream.collect(Collectors.toList());

			for (AdaptiveMedia<AdaptiveMediaImageProcessor> media : mediaList) {
				_exportMedia(portletDataContext, fileVersion, media);
			}
		}
	}

	private void _exportMedia(
			PortletDataContext portletDataContext, FileVersion fileVersion,
			AdaptiveMedia<AdaptiveMediaImageProcessor> media)
		throws IOException {

		Optional<String> configurationUuidOptional = media.getAttributeValue(
			AdaptiveMediaAttribute.configurationUuid());

		if (!configurationUuidOptional.isPresent()) {
			return;
		}

		String basePath = _getMediaBasePath(
			fileVersion, configurationUuidOptional.get());

		try (InputStream inputStream = media.getInputStream()) {
			portletDataContext.addZipEntry(basePath + ".bin", inputStream);
		}

		portletDataContext.addZipEntry(
			basePath + ".json", _imageSerializer.serialize(media));
	}

	private String _getConfigurationEntryBinPath(
		AdaptiveMediaImageConfigurationEntry configurationEntry) {

		return String.format(
			"adaptive-media/%s.cf", configurationEntry.getUUID());
	}

	private AdaptiveMedia<AdaptiveMediaImageProcessor> _getExportedMedia(
		PortletDataContext portletDataContext, FileVersion fileVersion,
		AdaptiveMediaImageConfigurationEntry configurationEntry) {

		String basePath = _getMediaBasePath(
			fileVersion, configurationEntry.getUUID());

		String serializedMedia = portletDataContext.getZipEntryAsString(
			basePath + ".json");

		if (serializedMedia == null) {
			return null;
		}

		return _imageSerializer.deserialize(
			serializedMedia,
			() -> portletDataContext.getZipEntryAsInputStream(
				basePath + ".bin"));
	}

	private String _getMediaBasePath(FileVersion fileVersion, String uuid) {
		return String.format(
			"adaptive-media/%s/%s/%s", FileVersion.class.getSimpleName(),
			fileVersion.getUuid(), uuid);
	}

	private void _importGeneratedMedia(
			PortletDataContext portletDataContext, FileEntry importedFileEntry,
			AdaptiveMediaImageConfigurationEntry configurationEntry)
		throws IOException, PortalException {

		String configuration = portletDataContext.getZipEntryAsString(
			_getConfigurationEntryBinPath(configurationEntry));

		if (configuration == null) {
			return;
		}

		AdaptiveMediaImageConfigurationEntry importedConfigurationEntry =
			_imageConfigurationEntrySerializer.deserialize(configuration);

		if (!importedConfigurationEntry.equals(configurationEntry)) {
			return;
		}

		List<FileVersion> fileVersions = importedFileEntry.getFileVersions(
			WorkflowConstants.STATUS_APPROVED);

		for (FileVersion fileVersion : fileVersions) {
			AdaptiveMedia<AdaptiveMediaImageProcessor> media =
				_getExportedMedia(
					portletDataContext, fileVersion, configurationEntry);

			if (media == null) {
				continue;
			}

			Optional<Integer> contentLengthOptional = media.getAttributeValue(
				AdaptiveMediaAttribute.contentLength());

			Optional<Integer> widthOptional = media.getAttributeValue(
				AdaptiveMediaImageAttribute.IMAGE_WIDTH);

			Optional<Integer> heightOptional = media.getAttributeValue(
				AdaptiveMediaImageAttribute.IMAGE_HEIGHT);

			if (!contentLengthOptional.isPresent() ||
				!widthOptional.isPresent() || !heightOptional.isPresent()) {

				continue;
			}

			AdaptiveMediaImageEntry imageEntry =
				_imageEntryLocalService.fetchAdaptiveMediaImageEntry(
					configurationEntry.getUUID(),
					fileVersion.getFileVersionId());

			if (imageEntry != null) {
				_imageEntryLocalService.
					deleteAdaptiveMediaImageEntryFileVersion(
						configurationEntry.getUUID(),
						fileVersion.getFileVersionId());
			}

			try (InputStream inputStream = media.getInputStream()) {
				_imageEntryLocalService.addAdaptiveMediaImageEntry(
					configurationEntry, fileVersion, widthOptional.get(),
					heightOptional.get(), inputStream,
					contentLengthOptional.get());
			}
		}
	}

	@Reference
	private AdaptiveMediaImageConfigurationHelper _configurationHelper;

	@Reference
	private AdaptiveMediaImageFinder _finder;

	@Reference
	private AdaptiveMediaImageConfigurationEntrySerializer
		_imageConfigurationEntrySerializer;

	@Reference
	private AdaptiveMediaImageEntryLocalService _imageEntryLocalService;

	@Reference
	private AdaptiveMediaImageSerializer _imageSerializer;

}