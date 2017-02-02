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

package com.liferay.adaptive.media.document.library.repository.internal;

import com.liferay.adaptive.media.image.counter.AdaptiveMediaImageCounter;
import com.liferay.adaptive.media.image.service.persistence.AdaptiveMediaImageFinder;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.persistence.DLFileEntryFinder;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {"class.name=DL"},
	service = AdaptiveMediaImageCounter.class
)
public class DLAdaptiveMediaImageCounter implements AdaptiveMediaImageCounter {

	@Override
	public int countExpectedAdaptiveMediaImages(long companyId) {
		return _adaptiveMediaImageFinder.countByDLLiferayFileEntries(
			companyId, _SUPPORTED_MIME_TYPES);
	}

	@Reference
	protected AdaptiveMediaImageFinder _adaptiveMediaImageFinder;

	private final static String[] _SUPPORTED_MIME_TYPES = new String[] {
		"image/bmp", "image/gif", "image/jpeg", "image/pjpeg", "image/png",
			"image/tiff", "image/x-citrix-jpeg", "image/x-citrix-png",
			"image/x-ms-bmp", "image/x-png", "image/x-tiff"
	};

}
