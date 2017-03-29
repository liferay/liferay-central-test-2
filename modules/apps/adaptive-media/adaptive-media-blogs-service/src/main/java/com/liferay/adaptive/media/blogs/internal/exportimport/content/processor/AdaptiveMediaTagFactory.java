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

package com.liferay.adaptive.media.blogs.internal.exportimport.content.processor;

import com.liferay.adaptive.media.AdaptiveMediaException;
import com.liferay.adaptive.media.image.html.AdaptiveMediaImageHTMLTagFactory;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringPool;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = AdaptiveMediaTagFactory.class)
public class AdaptiveMediaTagFactory {

	public String createDynamicTag(FileEntry fileEntry) throws PortalException {
		String previewURL = DLUtil.getPreviewURL(
			fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK);

		return String.format(
			"<img data-fileentryid=\"%d\" src=\"%s\" />",
			fileEntry.getFileEntryId(), previewURL);
	}

	public String createStaticTag(FileEntry fileEntry) throws PortalException {
		try {
			String previewURL = DLUtil.getPreviewURL(
				fileEntry, fileEntry.getFileVersion(), null, null);

			String fallbackTag = String.format(
				"<img src=\"%s\" />", previewURL);

			return _htmlTagFactory.create(fallbackTag, fileEntry);
		}
		catch (AdaptiveMediaException ame) {
			throw new PortalException(ame);
		}
	}

	@Reference
	private AdaptiveMediaImageHTMLTagFactory _htmlTagFactory;

}