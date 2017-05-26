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
import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaConfigurationEntryImpl;
import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaConfigurationEntryParser;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.io.IOException;

import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Alejandro Hernández
 */
public class ImageAdaptiveMediaConfigResource {

	public ImageAdaptiveMediaConfigResource(
		long companyId,
		ImageAdaptiveMediaConfigurationHelper configurationHelper,
		ImageAdaptiveMediaConfigurationEntryParser configurationEntryParser) {

		_companyId = companyId;
		_configurationHelper = configurationHelper;
		_permissionChecker = PermissionThreadLocal.getPermissionChecker();
		_configurationEntryParser = configurationEntryParser;
	}

	@Path("/{uuid}")
	@Produces({"application/json", "application/xml"})
	@PUT
	public ImageAdaptiveMediaConfigRepr addConfiguration(
			@PathParam("uuid") String uuid,
			ImageAdaptiveMediaConfigRepr configRepr)
		throws PortalException {

		if (!_permissionChecker.isCompanyAdmin()) {
			throw new ForbiddenException();
		}

		Collection<ImageAdaptiveMediaConfigurationEntry> configurationEntries =
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				_companyId);

		List<ImageAdaptiveMediaConfigurationEntry> updatedConfigurationEntries =
			configurationEntries.stream().filter(
				configurationEntry ->
					!configurationEntry.getUUID().equals(uuid)).collect(
						Collectors.toList());

		updatedConfigurationEntries.add(
			_getImageAdaptiveMediaConfigurationEntry(uuid, configRepr));

		try {
			_updateConfiguration(updatedConfigurationEntries);
		}
		catch (IOException ioe) {
			throw new InternalServerErrorException();
		}

		return configRepr;
	}

	@DELETE
	@Path("/{uuid}")
	public Response deleteConfiguration(@PathParam("uuid") String uuid)
		throws PortalException {

		if (!_permissionChecker.isCompanyAdmin()) {
			throw new ForbiddenException();
		}

		Collection<ImageAdaptiveMediaConfigurationEntry> configurationEntries =
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				_companyId);

		List<ImageAdaptiveMediaConfigurationEntry> updatedConfigurationEntries =
			configurationEntries.stream().filter(
				configurationEntry ->
					!configurationEntry.getUUID().equals(uuid)).collect(
						Collectors.toList());

		try {
			_updateConfiguration(updatedConfigurationEntries);
		}
		catch (IOException ioe) {
			throw new InternalServerErrorException();
		}

		return Response.noContent().build();
	}

	@GET
	@Path("/{uuid}")
	@Produces({"application/json", "application/xml"})
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
	@Produces({"application/json", "application/xml"})
	public Response getConfigurations() {
		Collection<ImageAdaptiveMediaConfigurationEntry> configurationEntries =
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				_companyId);

		List<ImageAdaptiveMediaConfigRepr> configReprs =
			configurationEntries.stream().map(
				ImageAdaptiveMediaConfigRepr::new).collect(Collectors.toList());

		GenericEntity<List<ImageAdaptiveMediaConfigRepr>> entity =
			new GenericEntity<List<ImageAdaptiveMediaConfigRepr>>(configReprs) {
			};

		return Response.ok(entity).build();
	}

	private Configuration _getConfiguration() throws IOException {
		Registry registry = RegistryUtil.getRegistry();

		ConfigurationAdmin configurationAdmin = registry.getService(
			ConfigurationAdmin.class);

		return configurationAdmin.getConfiguration(
			"com.liferay.adaptive.media.image.internal.configuration." +
				"ImageAdaptiveMediaCompanyConfiguration",
			null);
	}

	private ImageAdaptiveMediaConfigurationEntry
		_getImageAdaptiveMediaConfigurationEntry(
			String uuid, ImageAdaptiveMediaConfigRepr configRepr) {

		Map<String, String> properties = new HashMap<>();

		if (configRepr.getHeight() != -1) {
			properties.put("height", String.valueOf(configRepr.getHeight()));
		}

		if (configRepr.getWidth() != -1) {
			properties.put("width", String.valueOf(configRepr.getWidth()));
		}

		return new ImageAdaptiveMediaConfigurationEntryImpl(
			configRepr.getName(), uuid, properties);
	}

	private void _updateConfiguration(
			List<ImageAdaptiveMediaConfigurationEntry> configurationEntries)
		throws IOException {

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("imageVariants", configurationEntries.stream().map(
			_configurationEntryParser::getConfigurationString).collect(
				Collectors.toList()).toArray(
					new String[configurationEntries.size()]));

		Configuration configuration = _getConfiguration();

		configuration.update(properties);
	}

	private final long _companyId;
	private final ImageAdaptiveMediaConfigurationEntryParser
		_configurationEntryParser;
	private final ImageAdaptiveMediaConfigurationHelper _configurationHelper;
	private final PermissionChecker _permissionChecker;

}