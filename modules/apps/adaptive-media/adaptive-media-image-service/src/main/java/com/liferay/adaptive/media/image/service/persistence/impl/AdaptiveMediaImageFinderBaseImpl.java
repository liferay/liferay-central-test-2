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

package com.liferay.adaptive.media.image.service.persistence.impl;

import com.liferay.adaptive.media.image.model.AdaptiveMediaImage;
import com.liferay.adaptive.media.image.service.persistence.AdaptiveMediaImagePersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AdaptiveMediaImageFinderBaseImpl extends BasePersistenceImpl<AdaptiveMediaImage> {
	@Override
	public Set<String> getBadColumnNames() {
		return getAdaptiveMediaImagePersistence().getBadColumnNames();
	}

	/**
	 * Returns the adaptive media image persistence.
	 *
	 * @return the adaptive media image persistence
	 */
	public AdaptiveMediaImagePersistence getAdaptiveMediaImagePersistence() {
		return adaptiveMediaImagePersistence;
	}

	/**
	 * Sets the adaptive media image persistence.
	 *
	 * @param adaptiveMediaImagePersistence the adaptive media image persistence
	 */
	public void setAdaptiveMediaImagePersistence(
		AdaptiveMediaImagePersistence adaptiveMediaImagePersistence) {
		this.adaptiveMediaImagePersistence = adaptiveMediaImagePersistence;
	}

	@BeanReference(type = AdaptiveMediaImagePersistence.class)
	protected AdaptiveMediaImagePersistence adaptiveMediaImagePersistence;
}