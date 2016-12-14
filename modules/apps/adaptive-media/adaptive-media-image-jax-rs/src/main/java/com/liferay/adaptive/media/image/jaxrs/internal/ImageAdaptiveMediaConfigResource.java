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

import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * @author Alejandro Hernández
 */
public class ImageAdaptiveMediaConfigResource {

	public ImageAdaptiveMediaConfigResource(
		long companyId,
		ImageAdaptiveMediaConfigurationHelper configurationHelper) {

		_companyId = companyId;
		_configurationHelper = configurationHelper;
		_permissionChecker = PermissionThreadLocal.getPermissionChecker();
	}

	@Path("/{uuid}")
	@Produces("application/json")
	@PUT
	public ImageAdaptiveMediaConfigRepr addConfiguration(
			@PathParam("uuid") String uuid,
			ImageAdaptiveMediaConfigRepr configRepr)
		throws PortalException {

		if (!_permissionChecker.isCompanyAdmin()) {
			throw new ForbiddenException();
		}

		if ((configRepr == null) ||
			MapUtil.isEmpty(configRepr.getProperties()) ||
			Validator.isNull(configRepr.getName())) {

			throw new BadRequestException();
		}

		Map<String, String> properties = configRepr.getProperties();

		configRepr.setUuid(uuid);

		try {
			_configurationHelper.addImageAdaptiveMediaConfigurationEntry(
				_companyId, configRepr.getName(), uuid, properties);
		}
		catch (IOException ioe) {
			throw new InternalServerErrorException();
		}

		return configRepr;
	}

	@DELETE
	@Path("/{uuid}")
	public void deleteConfiguration(@PathParam("uuid") String uuid)
		throws PortalException {

		if (!_permissionChecker.isCompanyAdmin()) {
			throw new ForbiddenException();
		}

		try {
			_configurationHelper.deleteImageAdaptiveMediaConfigurationEntry(
				_companyId, uuid);
		}
		catch (IOException ioe) {
			throw new InternalServerErrorException();
		}
	}

	@GET
	@Path("/{uuid}")
	@Produces("application/json")
	public ImageAdaptiveMediaConfigRepr getConfiguration(
		@PathParam("uuid") String uuid) {

		Optional<ImageAdaptiveMediaConfigurationEntry>
			configurationEntryOptional =
				_configurationHelper.getImageAdaptiveMediaConfigurationEntry(
					_companyId, uuid);

		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			configurationEntryOptional.orElseThrow(NotFoundException::new);

		return new ImageAdaptiveMediaConfigRepr(configurationEntry);
	}

	@GET
	@Produces("application/json")
	public List<ImageAdaptiveMediaConfigRepr> getConfigurations() {
		Collection<ImageAdaptiveMediaConfigurationEntry> configurationEntries =
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				_companyId);

		return configurationEntries.stream().map(
			ImageAdaptiveMediaConfigRepr::new).collect(Collectors.toList());
	}

	private final long _companyId;
	private final ImageAdaptiveMediaConfigurationHelper _configurationHelper;
	private final PermissionChecker _permissionChecker;

}