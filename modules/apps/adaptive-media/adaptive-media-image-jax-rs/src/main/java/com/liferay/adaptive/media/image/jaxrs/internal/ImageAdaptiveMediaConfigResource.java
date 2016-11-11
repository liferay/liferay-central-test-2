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
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.Collection;
import java.util.Dictionary;
import java.util.List;
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
		ImageAdaptiveMediaConfigurationHelper configurationHelper) {

		_companyId = companyId;
		_configurationHelper = configurationHelper;
		_permissionChecker = PermissionThreadLocal.getPermissionChecker();
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

		List<ImageAdaptiveMediaConfigRepr> configReprs =
			_getDiferentConfigurations(uuid);

		configRepr.setUuid(uuid);
		configReprs.add(configRepr);

		try {
			_writeProperties(configReprs);
		}
		catch (Exception e) {
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

		List<ImageAdaptiveMediaConfigRepr> configReprs =
			_getDiferentConfigurations(uuid);

		try {
			_writeProperties(configReprs);
		}
		catch (Exception e) {
			throw new InternalServerErrorException();
		}

		return Response.noContent().build();
	}

	@GET
	@Path("/{uuid}")
	@Produces({"application/json", "application/xml"})
	public ImageAdaptiveMediaConfigRepr getConfiguration(
		@PathParam("uuid") String uuid) {

		Optional<ImageAdaptiveMediaConfigurationEntry> configurationEntry =
			_configurationHelper.getImageAdaptiveMediaConfigurationEntry(
				_companyId, uuid);

		if (!configurationEntry.isPresent()) {
			throw new NotFoundException();
		}

		return new ImageAdaptiveMediaConfigRepr(configurationEntry.get());
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

	private Configuration _getConfiguration() throws Exception {
		ConfigurationAdmin configurationAdmin = _getService(
			ConfigurationAdmin.class);

		return configurationAdmin.getConfiguration(
			"com.liferay.adaptive.media.image.internal.configuration." +
				"ImageAdaptiveMediaCompanyConfiguration",
			null);
	}

	private List<ImageAdaptiveMediaConfigRepr> _getDiferentConfigurations(
		String uuid) {

		Collection<ImageAdaptiveMediaConfigurationEntry> configurationEntries =
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				_companyId);

		return configurationEntries.stream().map(
			ImageAdaptiveMediaConfigRepr::new).filter(
				c -> !c.getUuid().equals(uuid)).collect(Collectors.toList());
	}

	private <T> T _getService(Class<T> clazz) {
		Registry registry = RegistryUtil.getRegistry();

		return registry.getService(clazz);
	}

	private void _writeProperties(
			List<ImageAdaptiveMediaConfigRepr> configReprs)
		throws Exception {

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("imageVariants", configReprs.stream().map(
			ImageAdaptiveMediaConfigRepr::toString).collect(
				Collectors.toList()).toArray(new String[configReprs.size()]));

		Configuration configuration = _getConfiguration();

		configuration.update(properties);
	}

	private final long _companyId;
	private final ImageAdaptiveMediaConfigurationHelper _configurationHelper;
	private final PermissionChecker _permissionChecker;

}