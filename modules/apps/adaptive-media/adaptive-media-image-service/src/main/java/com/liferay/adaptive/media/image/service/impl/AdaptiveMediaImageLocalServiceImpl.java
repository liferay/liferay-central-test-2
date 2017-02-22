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

package com.liferay.adaptive.media.image.service.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationHelper;
import com.liferay.adaptive.media.image.counter.AdaptiveMediaImageCounter;
import com.liferay.adaptive.media.image.exception.DuplicateAdaptiveMediaImageException;
import com.liferay.adaptive.media.image.model.AdaptiveMediaImage;
import com.liferay.adaptive.media.image.service.base.AdaptiveMediaImageLocalServiceBaseImpl;
import com.liferay.adaptive.media.image.storage.ImageStorage;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.InputStream;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Sergio González
 */
@ProviderType
public class AdaptiveMediaImageLocalServiceImpl
	extends AdaptiveMediaImageLocalServiceBaseImpl {

	@Override
	public AdaptiveMediaImage addAdaptiveMediaImage(
			ImageAdaptiveMediaConfigurationEntry configurationEntry,
			FileVersion fileVersion, int width, int height,
			InputStream inputStream, int size)
		throws PortalException {

		_checkDuplicates(
			configurationEntry.getUUID(), fileVersion.getFileVersionId());

		long imageId = counterLocalService.increment();

		AdaptiveMediaImage image = adaptiveMediaImagePersistence.create(
			imageId);

		image.setCompanyId(fileVersion.getCompanyId());
		image.setGroupId(fileVersion.getGroupId());
		image.setCreateDate(new Date());
		image.setFileVersionId(fileVersion.getFileVersionId());
		image.setMimeType(fileVersion.getMimeType());
		image.setHeight(height);
		image.setWidth(width);
		image.setSize(size);
		image.setConfigurationUuid(configurationEntry.getUUID());

		ImageStorage imageStorage = _imageStorageServiceTracker.getService();

		imageStorage.save(fileVersion, configurationEntry, inputStream);

		return adaptiveMediaImagePersistence.update(image);
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();

		Bundle bundle = FrameworkUtil.getBundle(
			AdaptiveMediaImageLocalServiceImpl.class);

		_imageStorageServiceTracker = ServiceTrackerFactory.open(
			bundle, ImageStorage.class);
		_configurationHelperServiceTracker = ServiceTrackerFactory.open(
			bundle, ImageAdaptiveMediaConfigurationHelper.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, AdaptiveMediaImageCounter.class,
			"adaptive.media.key");
	}

	@Override
	public void deleteAdaptiveMediaImageFileVersion(long fileVersionId)
		throws PortalException {

		List<AdaptiveMediaImage> images =
			adaptiveMediaImagePersistence.findByFileVersionId(fileVersionId);

		for (AdaptiveMediaImage image : images) {
			adaptiveMediaImagePersistence.remove(image);
		}

		FileVersion fileVersion = dlAppLocalService.getFileVersion(
			fileVersionId);

		ImageStorage imageStorage = _imageStorageServiceTracker.getService();

		imageStorage.delete(fileVersion);
	}

	@Override
	public void destroy() {
		super.destroy();

		_imageStorageServiceTracker.close();
		_configurationHelperServiceTracker.close();
		_serviceTrackerMap.close();
	}

	@Override
	public AdaptiveMediaImage fetchAdaptiveMediaImage(
		String configurationUuid, long fileVersionId) {

		return adaptiveMediaImagePersistence.fetchByC_F(
			configurationUuid, fileVersionId);
	}

	@Override
	public InputStream getAdaptiveMediaImageContentStream(
		ImageAdaptiveMediaConfigurationEntry configurationEntry,
		FileVersion fileVersion) {

		ImageStorage imageStorage = _imageStorageServiceTracker.getService();

		return imageStorage.getContentStream(fileVersion, configurationEntry);
	}

	@Override
	public int getPercentage(final long companyId, String configurationUuid) {
		Collection<AdaptiveMediaImageCounter> adaptiveMediaImageCounters =
			_serviceTrackerMap.values();

		int expectedAdaptiveMediaImages =
			adaptiveMediaImageCounters.stream().mapToInt(
				adaptiveMediaImageCounter ->
					adaptiveMediaImageCounter.countExpectedAdaptiveMediaImages(
						companyId)).sum();

		int actualAdaptiveMediaImages =
			adaptiveMediaImagePersistence.countByC_C(
				companyId, configurationUuid);

		if (expectedAdaptiveMediaImages == 0) {
			return 0;
		}

		return Math.min(
			actualAdaptiveMediaImages * 100 / expectedAdaptiveMediaImages, 100);
	}

	@ServiceReference(type = DLAppLocalService.class)
	protected DLAppLocalService dlAppLocalService;

	private void _checkDuplicates(String configurationUuid, long fileVersionId)
		throws DuplicateAdaptiveMediaImageException {

		AdaptiveMediaImage adaptiveMediaImage =
			adaptiveMediaImagePersistence.fetchByC_F(
				configurationUuid, fileVersionId);

		if (adaptiveMediaImage != null) {
			throw new DuplicateAdaptiveMediaImageException();
		}
	}

	private ServiceTracker
		<ImageAdaptiveMediaConfigurationHelper,
			ImageAdaptiveMediaConfigurationHelper>
				_configurationHelperServiceTracker;
	private ServiceTracker<ImageStorage, ImageStorage>
		_imageStorageServiceTracker;
	private ServiceTrackerMap<String, AdaptiveMediaImageCounter>
		_serviceTrackerMap;

}