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

import com.liferay.adaptive.media.image.counter.AdaptiveMediaImageCounter;
import com.liferay.adaptive.media.image.exception.DuplicateAdaptiveMediaImageException;
import com.liferay.adaptive.media.image.model.AdaptiveMediaImage;
import com.liferay.adaptive.media.image.service.base.AdaptiveMediaImageLocalServiceBaseImpl;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Sergio González
 */
@ProviderType
public class AdaptiveMediaImageLocalServiceImpl
	extends AdaptiveMediaImageLocalServiceBaseImpl {

	@Override
	public AdaptiveMediaImage addAdaptiveMediaImage(
			String configurationUuid, long fileVersionId, String mimeType,
			int width, int size, int height)
		throws PortalException {

		_checkDuplicates(configurationUuid, fileVersionId);

		FileVersion fileVersion = dlAppLocalService.getFileVersion(
			fileVersionId);

		long imageId = counterLocalService.increment();

		AdaptiveMediaImage image = adaptiveMediaImagePersistence.create(
			imageId);

		image.setCompanyId(fileVersion.getCompanyId());
		image.setGroupId(fileVersion.getGroupId());
		image.setCreateDate(new Date());
		image.setFileVersionId(fileVersionId);
		image.setMimeType(mimeType);
		image.setHeight(height);
		image.setWidth(width);
		image.setSize(size);
		image.setConfigurationUuid(configurationUuid);

		return adaptiveMediaImagePersistence.update(image);
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();

		Bundle bundle = FrameworkUtil.getBundle(
			AdaptiveMediaImageLocalServiceImpl.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceTrackerMap = ServiceTrackerMapFactory.singleValueMap(
			bundleContext, AdaptiveMediaImageCounter.class, "class.name");

		_serviceTrackerMap.open();
	}

	@Override
	public void deleteAdaptiveMediaImageFileVersion(long fileVersionId) {
		List<AdaptiveMediaImage> images =
			adaptiveMediaImagePersistence.findByFileVersionId(fileVersionId);

		for (AdaptiveMediaImage image : images) {
			adaptiveMediaImagePersistence.remove(image);
		}
	}

	@Override
	public void destroy() {
		super.destroy();

		_serviceTrackerMap.close();
	}

	@Override
	public AdaptiveMediaImage fetchAdaptiveMediaImage(
		String configurationUuid, long fileVersionId) {

		return adaptiveMediaImagePersistence.fetchByC_F(
			configurationUuid, fileVersionId);
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

	private ServiceTrackerMap<String, AdaptiveMediaImageCounter>
		_serviceTrackerMap;

}