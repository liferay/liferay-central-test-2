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

package com.liferay.adaptive.media.image.jaxrs.internal;

import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationHelper;
import com.liferay.adaptive.media.image.finder.ImageAdaptiveMediaFinder;
import com.liferay.adaptive.media.processor.AdaptiveMediaAsyncProcessorLocator;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true, service = ImageAdaptiveMediaRootResource.class)
@Path("/images")
public class ImageAdaptiveMediaRootResource {

	@Path("/configuration")
	public ImageAdaptiveMediaConfigResource getConfiguration(
		@Context Company company) {

		return new ImageAdaptiveMediaConfigResource(
			company.getCompanyId(), configurationHelper);
	}

	@Path("/content/file/{fileEntryId}/version/{version}")
	public ImageAdaptiveMediaFileVersionResource getFileEntryVersion(
			@PathParam("fileEntryId") long fileEntryId,
			@PathParam("version") String version)
		throws PortalException {

		FileEntry fileEntry = dlAppService.getFileEntry(fileEntryId);

		FileVersion fileVersion = null;

		if (version.equals("last")) {
			fileVersion = fileEntry.getFileVersion();
		}
		else {
			fileVersion = fileEntry.getFileVersion(version);
		}

		return new ImageAdaptiveMediaFileVersionResource(
			fileVersion, finder, configurationHelper, _asyncProcessorLocator,
			_getBaseUriBuilder());
	}

	@Path("/content/version/{fileVersionId}")
	public ImageAdaptiveMediaFileVersionResource getVersion(
			@PathParam("fileVersionId") long fileVersionId)
		throws PortalException {

		FileVersion fileVersion = dlAppService.getFileVersion(fileVersionId);

		return new ImageAdaptiveMediaFileVersionResource(
			fileVersion, finder, configurationHelper, _asyncProcessorLocator,
			_getBaseUriBuilder());
	}

	@Reference
	protected ImageAdaptiveMediaConfigurationHelper configurationHelper;

	@Reference
	protected DLAppService dlAppService;

	@Reference
	protected ImageAdaptiveMediaFinder finder;

	@Context
	protected UriInfo uriInfo;

	private UriBuilder _getBaseUriBuilder() {
		return uriInfo.getBaseUriBuilder().clone().path(
			ImageAdaptiveMediaRootResource.class).path(
				ImageAdaptiveMediaRootResource.class, "getVersion");
	}

	@Reference(unbind = "-")
	private AdaptiveMediaAsyncProcessorLocator _asyncProcessorLocator;

}