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

import com.liferay.adaptive.media.AdaptiveMediaImageConfigurationException;
import com.liferay.adaptive.media.AdaptiveMediaImageConfigurationException.InvalidStateAdaptiveMediaImageConfigurationException;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationHelper;
import com.liferay.adaptive.media.image.rest.internal.model.AdaptiveMediaImageConfigurationEntryModel;
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
public class AdaptiveMediaImageConfigResource {

	public AdaptiveMediaImageConfigResource(
		long companyId,
		AdaptiveMediaImageConfigurationHelper configurationHelper) {

		_companyId = companyId;
		_configurationHelper = configurationHelper;
		_permissionChecker = PermissionThreadLocal.getPermissionChecker();
	}

	@Path("/{id}")
	@Produces("application/json")
	@PUT
	public AdaptiveMediaImageConfigurationEntryModel addConfigurationEntry(
			@PathParam("id") String id,
			AdaptiveMediaImageConfigurationEntryModel configurationEntryModel)
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
			Optional<AdaptiveMediaImageConfigurationEntry>
				configurationEntryOptional =
					_configurationHelper.
						getAdaptiveMediaImageConfigurationEntry(_companyId, id);

			if (configurationEntryOptional.isPresent()) {
				_configurationHelper.updateAdaptiveMediaImageConfigurationEntry(
					_companyId, id, configurationEntryModel.getName(), id,
					properties);
			}
			else {
				_configurationHelper.addAdaptiveMediaImageConfigurationEntry(
					_companyId, configurationEntryModel.getName(), id,
					properties);
			}

			if (configurationEntryModel.isEnabled()) {
				_configurationHelper.enableAdaptiveMediaImageConfigurationEntry(
					_companyId, id);
			}
			else {
				_configurationHelper.
					disableAdaptiveMediaImageConfigurationEntry(_companyId, id);
			}
		}
		catch (IOException | AdaptiveMediaImageConfigurationException e) {
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
			_configurationHelper.deleteAdaptiveMediaImageConfigurationEntry(
				_companyId, id);
		}
		catch (InvalidStateAdaptiveMediaImageConfigurationException isamice) {
			throw new BadRequestException();
		}
		catch (IOException ioe) {
			throw new InternalServerErrorException();
		}
	}

	@GET
	@Produces("application/json")
	public List<AdaptiveMediaImageConfigurationEntryModel>
		getConfigurationEntries(
			@DefaultValue("true") @QueryParam("enabled") boolean enabled) {

		Collection<AdaptiveMediaImageConfigurationEntry> configurationEntries =
			_configurationHelper.getAdaptiveMediaImageConfigurationEntries(
				_companyId, configurationEntry ->
					configurationEntry.isEnabled() == enabled);

		return configurationEntries.stream().map(
			AdaptiveMediaImageConfigurationEntryModel::new).collect(
				Collectors.toList());
	}

	@GET
	@Path("/{id}")
	@Produces("application/json")
	public AdaptiveMediaImageConfigurationEntryModel getConfigurationEntry(
		@PathParam("id") String id) {

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				_configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					_companyId, id);

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			configurationEntryOptional.orElseThrow(NotFoundException::new);

		return new AdaptiveMediaImageConfigurationEntryModel(
			configurationEntry);
	}

	private final long _companyId;
	private final AdaptiveMediaImageConfigurationHelper _configurationHelper;
	private final PermissionChecker _permissionChecker;

}