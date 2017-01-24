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

import com.liferay.adaptive.media.image.exception.DuplicateAdaptiveMediaImageException;
import com.liferay.adaptive.media.image.model.AdaptiveMediaImage;
import com.liferay.adaptive.media.image.service.base.AdaptiveMediaImageLocalServiceBaseImpl;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.spring.extender.service.ServiceReference;

/**
 * @author Sergio González
 */
@ProviderType
public class AdaptiveMediaImageLocalServiceImpl
	extends AdaptiveMediaImageLocalServiceBaseImpl {

	public AdaptiveMediaImage addAdaptiveMediaImage(
			String configurationUuid, long fileVersionId, int height, int width,
			int size)
		throws PortalException {

		_checkDuplicates(configurationUuid, fileVersionId);

		FileVersion fileVersion = dlAppLocalService.getFileVersion(
			fileVersionId);

		long imageId = counterLocalService.increment();

		AdaptiveMediaImage image = adaptiveMediaImagePersistence.create(
			imageId);

		image.setCompanyId(fileVersion.getCompanyId());
		image.setGroupId(fileVersion.getGroupId());
		image.setFileVersionId(fileVersionId);
		image.setHeight(height);
		image.setWidth(width);
		image.setSize(size);
		image.setConfigurationUuid(configurationUuid);

		return adaptiveMediaImagePersistence.update(image);
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

}