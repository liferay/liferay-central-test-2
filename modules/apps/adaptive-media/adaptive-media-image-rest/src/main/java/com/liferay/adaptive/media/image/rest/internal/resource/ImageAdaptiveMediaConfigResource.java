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

import com.liferay.adaptive.media.ImageAdaptiveMediaConfigurationException;
import com.liferay.adaptive.media.ImageAdaptiveMediaConfigurationException.InvalidStateImageAdaptiveMediaConfigurationException;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationHelper;
import com.liferay.adaptive.media.image.rest.internal.model.ImageAdaptiveMediaConfigurationEntryModel;
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
import javax.ws.rs.DefaultValue;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

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

	@Path("/{id}")
	@Produces("application/json")
	@PUT
	public ImageAdaptiveMediaConfigurationEntryModel addConfigurationEntry(
			@PathParam("id") String id,
			ImageAdaptiveMediaConfigurationEntryModel configurationEntryModel)
		throws PortalException {

		if (!_permissionChecker.isCompanyAdmin()) {
			throw new ForbiddenException();
		}

		if ((configurationEntryModel == null) ||
			MapUtil.isEmpty(configurationEntryModel.getProperties()) ||
			Validator.isNull(configurationEntryModel.getName()) ||
			(configurationEntryModel.isEnabled() == null)) {

			throw new BadRequestException();
		}

		Map<String, String> properties =
			configurationEntryModel.getProperties();

		configurationEntryModel.setUuid(id);

		try {
			Optional<ImageAdaptiveMediaConfigurationEntry>
				configurationEntryOptional =
					_configurationHelper.
						getImageAdaptiveMediaConfigurationEntry(_companyId, id);

			if (configurationEntryOptional.isPresent()) {
				_configurationHelper.updateImageAdaptiveMediaConfigurationEntry(
					_companyId, id, configurationEntryModel.getName(), id,
					properties);
			}
			else {
				_configurationHelper.addImageAdaptiveMediaConfigurationEntry(
					_companyId, configurationEntryModel.getName(), id,
					properties);
			}

			if (configurationEntryModel.isEnabled()) {
				_configurationHelper.enableImageAdaptiveMediaConfigurationEntry(
					_companyId, id);
			}
			else {
				_configurationHelper.
					disableImageAdaptiveMediaConfigurationEntry(_companyId, id);
			}
		}
		catch (IOException | ImageAdaptiveMediaConfigurationException e) {
			throw new InternalServerErrorException();
		}

		return configurationEntryModel;
	}

	@DELETE
	@Path("/{id}")
	public void deleteConfigurationEntry(@PathParam("id") String id)
		throws PortalException {

		if (!_permissionChecker.isCompanyAdmin()) {
			throw new ForbiddenException();
		}

		try {
			_configurationHelper.deleteImageAdaptiveMediaConfigurationEntry(
				_companyId, id);
		}
		catch (InvalidStateImageAdaptiveMediaConfigurationException isiamce) {
			throw new BadRequestException();
		}
		catch (IOException ioe) {
			throw new InternalServerErrorException();
		}
	}

	@GET
	@Produces("application/json")
	public List<ImageAdaptiveMediaConfigurationEntryModel>
		getConfigurationEntries(
			@DefaultValue("true") @QueryParam("enabled") boolean enabled) {

		Collection<ImageAdaptiveMediaConfigurationEntry> configurationEntries =
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				_companyId, configurationEntry ->
					configurationEntry.isEnabled() == enabled);

		return configurationEntries.stream().map(
			ImageAdaptiveMediaConfigurationEntryModel::new).collect(
				Collectors.toList());
	}

	@GET
	@Path("/{id}")
	@Produces("application/json")
	public ImageAdaptiveMediaConfigurationEntryModel getConfigurationEntry(
		@PathParam("id") String id) {

		Optional<ImageAdaptiveMediaConfigurationEntry>
			configurationEntryOptional =
				_configurationHelper.getImageAdaptiveMediaConfigurationEntry(
					_companyId, id);

		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			configurationEntryOptional.orElseThrow(NotFoundException::new);

		return new ImageAdaptiveMediaConfigurationEntryModel(
			configurationEntry);
	}

	private final long _companyId;
	private final ImageAdaptiveMediaConfigurationHelper _configurationHelper;
	private final PermissionChecker _permissionChecker;

}