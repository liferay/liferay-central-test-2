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

package com.liferay.adaptive.media.image.rest.internal.resource;

import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationHelper;
import com.liferay.adaptive.media.image.finder.AdaptiveMediaImageFinder;
import com.liferay.adaptive.media.processor.AdaptiveMediaAsyncProcessorLocator;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFileVersionException;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;

import javax.ws.rs.NotFoundException;
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
@Component(immediate = true, service = AdaptiveMediaImageRootResource.class)
@Path("/images")
public class AdaptiveMediaImageRootResource {

	@Path("/configuration")
	public AdaptiveMediaImageConfigResource getConfiguration(
		@Context Company company) {

		return new AdaptiveMediaImageConfigResource(
			company.getCompanyId(), configurationHelper);
	}

	@Path("/content/file/{fileEntryId}/version/{version}")
	public AdaptiveMediaImageFileVersionResource getFileEntryVersion(
			@PathParam("fileEntryId") long fileEntryId,
			@PathParam("version") String version)
		throws PortalException {

		FileEntry fileEntry = null;

		try {
			fileEntry = dlAppService.getFileEntry(fileEntryId);
		}
		catch (NoSuchFileEntryException nsfee) {
			throw new NotFoundException();
		}

		FileVersion fileVersion = null;

		if (version.equals("last")) {
			fileVersion = fileEntry.getFileVersion();
		}
		else {
			fileVersion = fileEntry.getFileVersion(version);
		}

		return new AdaptiveMediaImageFileVersionResource(
			fileVersion, finder, configurationHelper, _asyncProcessorLocator,
			_getBaseUriBuilder());
	}

	@Path("/content/version/{fileVersionId}")
	public AdaptiveMediaImageFileVersionResource getVersion(
			@PathParam("fileVersionId") long fileVersionId)
		throws PortalException {

		FileVersion fileVersion = null;

		try {
			fileVersion = dlAppService.getFileVersion(fileVersionId);
		}
		catch (NoSuchFileVersionException nsfve) {
			throw new NotFoundException();
		}

		return new AdaptiveMediaImageFileVersionResource(
			fileVersion, finder, configurationHelper, _asyncProcessorLocator,
			_getBaseUriBuilder());
	}

	@Reference
	protected AdaptiveMediaImageConfigurationHelper configurationHelper;

	@Reference
	protected DLAppService dlAppService;

	@Reference
	protected AdaptiveMediaImageFinder finder;

	@Context
	protected UriInfo uriInfo;

	private UriBuilder _getBaseUriBuilder() {
		return uriInfo.getBaseUriBuilder().clone().path(
			AdaptiveMediaImageRootResource.class).path(
				AdaptiveMediaImageRootResource.class, "getVersion");
	}

	@Reference(unbind = "-")
	private AdaptiveMediaAsyncProcessorLocator _asyncProcessorLocator;

}