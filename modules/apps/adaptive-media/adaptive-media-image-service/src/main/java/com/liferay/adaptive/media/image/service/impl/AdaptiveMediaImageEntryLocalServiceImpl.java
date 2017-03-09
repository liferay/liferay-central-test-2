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

import com.liferay.adaptive.media.AdaptiveMediaRuntimeException;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.counter.AdaptiveMediaImageCounter;
import com.liferay.adaptive.media.image.exception.DuplicateAdaptiveMediaImageEntryException;
import com.liferay.adaptive.media.image.internal.storage.ImageStorage;
import com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry;
import com.liferay.adaptive.media.image.service.base.AdaptiveMediaImageEntryLocalServiceBaseImpl;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.InputStream;

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
public class AdaptiveMediaImageEntryLocalServiceImpl
	extends AdaptiveMediaImageEntryLocalServiceBaseImpl {

	@Override
	public AdaptiveMediaImageEntry addAdaptiveMediaImageEntry(
			AdaptiveMediaImageConfigurationEntry configurationEntry,
			FileVersion fileVersion, int width, int height,
			InputStream inputStream, int size)
		throws PortalException {

		_checkDuplicates(
			configurationEntry.getUUID(), fileVersion.getFileVersionId());

		long imageEntryId = counterLocalService.increment();

		AdaptiveMediaImageEntry imageEntry =
			adaptiveMediaImageEntryPersistence.create(imageEntryId);

		imageEntry.setCompanyId(fileVersion.getCompanyId());
		imageEntry.setGroupId(fileVersion.getGroupId());
		imageEntry.setCreateDate(new Date());
		imageEntry.setFileVersionId(fileVersion.getFileVersionId());
		imageEntry.setMimeType(fileVersion.getMimeType());
		imageEntry.setHeight(height);
		imageEntry.setWidth(width);
		imageEntry.setSize(size);
		imageEntry.setConfigurationUuid(configurationEntry.getUUID());

		imageStorage.save(
			fileVersion, configurationEntry.getUUID(), inputStream);

		return adaptiveMediaImageEntryPersistence.update(imageEntry);
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();

		Bundle bundle = FrameworkUtil.getBundle(
			AdaptiveMediaImageEntryLocalServiceImpl.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, AdaptiveMediaImageCounter.class,
			"adaptive.media.key");
	}

	@Override
	public void deleteAdaptiveMediaImageEntryFileVersion(long fileVersionId)
		throws PortalException {

		FileVersion fileVersion = dlAppLocalService.getFileVersion(
			fileVersionId);

		List<AdaptiveMediaImageEntry> imageEntries =
			adaptiveMediaImageEntryPersistence.findByFileVersionId(
				fileVersionId);

		for (AdaptiveMediaImageEntry imageEntry : imageEntries) {
			try {
				adaptiveMediaImageEntryPersistence.remove(imageEntry);

				imageStorage.delete(
					fileVersion, imageEntry.getConfigurationUuid());
			}
			catch (AdaptiveMediaRuntimeException.IOException amreioe) {
				_log.error(amreioe);
			}
		}
	}

	@Override
	public void destroy() {
		super.destroy();

		_serviceTrackerMap.close();
	}

	@Override
	public AdaptiveMediaImageEntry fetchAdaptiveMediaImageEntry(
		String configurationUuid, long fileVersionId) {

		return adaptiveMediaImageEntryPersistence.fetchByC_F(
			configurationUuid, fileVersionId);
	}

	@Override
	public int getAdaptiveMediaImageEntriesCount(
		long companyId, String configurationUuid) {

		return adaptiveMediaImageEntryPersistence.countByC_C(
			companyId, configurationUuid);
	}

	@Override
	public InputStream getAdaptiveMediaImageEntryContentStream(
		AdaptiveMediaImageConfigurationEntry configurationEntry,
		FileVersion fileVersion) {

		return imageStorage.getContentStream(
			fileVersion, configurationEntry.getUUID());
	}

	@Override
	public int getPercentage(final long companyId, String configurationUuid) {
		Collection<AdaptiveMediaImageCounter> imageCounters =
			_serviceTrackerMap.values();

		int expectedImageEntries = imageCounters.stream().mapToInt(
			adaptiveMediaImageCounter ->
				adaptiveMediaImageCounter.
					countExpectedAdaptiveMediaImageEntries(companyId)).sum();

		int actualImageEntries = adaptiveMediaImageEntryPersistence.countByC_C(
			companyId, configurationUuid);

		if (expectedImageEntries == 0) {
			return 0;
		}

		return Math.min(actualImageEntries * 100 / expectedImageEntries, 100);
	}

	@ServiceReference(type = DLAppLocalService.class)
	protected DLAppLocalService dlAppLocalService;

	@ServiceReference(type = ImageStorage.class)
	protected ImageStorage imageStorage;

	private void _checkDuplicates(String configurationUuid, long fileVersionId)
		throws DuplicateAdaptiveMediaImageEntryException {

		AdaptiveMediaImageEntry imageEntry =
			adaptiveMediaImageEntryPersistence.fetchByC_F(
				configurationUuid, fileVersionId);

		if (imageEntry != null) {
			throw new DuplicateAdaptiveMediaImageEntryException();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AdaptiveMediaImageEntryLocalServiceImpl.class);

	private ServiceTrackerMap<String, AdaptiveMediaImageCounter>
		_serviceTrackerMap;

}