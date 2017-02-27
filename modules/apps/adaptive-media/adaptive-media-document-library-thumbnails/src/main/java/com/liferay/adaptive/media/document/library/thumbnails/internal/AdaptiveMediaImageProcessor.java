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

package com.liferay.adaptive.media.document.library.thumbnails.internal;

import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.AdaptiveMediaAttribute;
import com.liferay.adaptive.media.AdaptiveMediaException;
import com.liferay.adaptive.media.image.constants.ImageAdaptiveMediaConstants;
import com.liferay.adaptive.media.image.finder.ImageAdaptiveMediaFinder;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaAttribute;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;
import com.liferay.document.library.kernel.model.DLProcessorConstants;
import com.liferay.document.library.kernel.util.DLProcessor;
import com.liferay.document.library.kernel.util.ImageProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.util.ImageProcessorImpl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true, property = {"service.ranking:Integer=100"},
	service = {AdaptiveMediaImageProcessor.class, DLProcessor.class}
)
public class AdaptiveMediaImageProcessor
	implements DLProcessor, ImageProcessor {

	@Override
	public void afterPropertiesSet() throws Exception {
	}

	@Override
	public void cleanUp(FileEntry fileEntry) {
	}

	@Override
	public void cleanUp(FileVersion fileVersion) {
	}

	@Override
	public void copy(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion) {
	}

	@Override
	public void exportGeneratedFiles(
		PortletDataContext portletDataContext, FileEntry fileEntry,
		Element fileEntryElement) {
	}

	@Override
	public void generateImages(
			FileVersion sourceFileVersion, FileVersion destinationFileVersion)
		throws Exception {
	}

	@Override
	public Set<String> getImageMimeTypes() {
		return ImageAdaptiveMediaConstants.getSupportedMimeTypes();
	}

	@Override
	public InputStream getPreviewAsStream(FileVersion fileVersion)
		throws Exception {

		return _imageProcessor.getPreviewAsStream(fileVersion);
	}

	@Override
	public long getPreviewFileSize(FileVersion fileVersion) throws Exception {
		return _imageProcessor.getPreviewFileSize(fileVersion);
	}

	@Override
	public String getPreviewType(FileVersion fileVersion) {
		return _imageProcessor.getPreviewType(fileVersion);
	}

	@Override
	public InputStream getThumbnailAsStream(FileVersion fileVersion, int index)
		throws Exception {

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_getThumbnailAdaptiveMedia(fileVersion);

		return stream.findFirst().map(AdaptiveMedia::getInputStream).orElse(
			new ByteArrayInputStream(new byte[0]));
	}

	@Override
	public long getThumbnailFileSize(FileVersion fileVersion, int index)
		throws Exception {

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_getThumbnailAdaptiveMedia(fileVersion);

		return stream.findFirst().flatMap(mediaMedia ->
			mediaMedia.getAttributeValue(
				AdaptiveMediaAttribute.contentLength())).orElse(0);
	}

	@Override
	public String getThumbnailType(FileVersion fileVersion) {
		return _imageProcessor.getThumbnailType(fileVersion);
	}

	@Override
	public String getType() {
		return DLProcessorConstants.IMAGE_PROCESSOR;
	}

	@Override
	public boolean hasImages(FileVersion fileVersion) {
		try {
			Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
				_getThumbnailAdaptiveMedia(fileVersion);

			Optional<AdaptiveMedia<ImageAdaptiveMediaProcessor>>
				adaptiveMediaOptional = stream.findFirst();

			if (adaptiveMediaOptional.isPresent()) {
				return true;
			}

			return false;
		}
		catch (AdaptiveMediaException | PortalException e) {
			return false;
		}
	}

	@Override
	public void importGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			FileEntry importedFileEntry, Element fileEntryElement)
		throws Exception {
	}

	@Override
	public boolean isImageSupported(FileVersion fileVersion) {
		return _isMimeTypeSupported(fileVersion.getMimeType());
	}

	@Override
	public boolean isImageSupported(String mimeType) {
		return _isMimeTypeSupported(mimeType);
	}

	@Override
	public boolean isSupported(FileVersion fileVersion) {
		return _isMimeTypeSupported(fileVersion.getMimeType());
	}

	@Override
	public boolean isSupported(String mimeType) {
		return _isMimeTypeSupported(mimeType);
	}

	@Override
	public void storeThumbnail(
			long companyId, long groupId, long fileEntryId, long fileVersionId,
			long custom1ImageId, long custom2ImageId, InputStream is,
			String type)
		throws Exception {
	}

	@Override
	public void trigger(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion) {
	}

	private Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>>
			_getThumbnailAdaptiveMedia(FileVersion fileVersion)
		throws AdaptiveMediaException, PortalException {

		return _imageAdaptiveMediaFinder.getAdaptiveMedia(queryBuilder ->
			queryBuilder.forVersion(fileVersion).with(
				ImageAdaptiveMediaAttribute.IMAGE_WIDTH,
				PropsValues.DL_FILE_ENTRY_THUMBNAIL_MAX_WIDTH).with(
				ImageAdaptiveMediaAttribute.IMAGE_HEIGHT,
				PropsValues.DL_FILE_ENTRY_THUMBNAIL_MAX_HEIGHT).done());
	}

	private boolean _isMimeTypeSupported(String mimeType) {
		Set<String> supportedMimeTypes =
			ImageAdaptiveMediaConstants.getSupportedMimeTypes();

		return supportedMimeTypes.contains(mimeType);
	}

	@Reference
	private ImageAdaptiveMediaFinder _imageAdaptiveMediaFinder;

	private final ImageProcessor _imageProcessor = new ImageProcessorImpl();

}