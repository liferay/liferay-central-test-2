package com.liferay.adaptive.media.image.jaxrs;

import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfiguration;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.Collection;
import java.util.Dictionary;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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
public class AdaptiveMediaImageConfigResource {

	public AdaptiveMediaImageConfigResource(
		long companyId,
		ImageAdaptiveMediaConfiguration imageAdaptiveMediaConfiguration) {

		_companyId = companyId;
		_imageAdaptiveMediaConfiguration = imageAdaptiveMediaConfiguration;
	}

	@Path("/{uuid}")
	@Produces({"application/json", "application/xml"})
	@PUT
	public Response addConfiguration(
			@PathParam("uuid") String uuid,
			AdaptiveMediaImageConfigRepr userConfig)
		throws PortalException {

		List<AdaptiveMediaImageConfigRepr> configs = _getDiferentConfigurations(
			uuid);

		userConfig.setUuid(uuid);
		configs.add(userConfig);

		try {
			_writeProperties(configs);
		}
		catch (Exception e) {
			return Response.serverError().build();
		}

		return Response.ok(userConfig).build();
	}

	@DELETE
	@Path("/{uuid}")
	public Response deleteConfiguration(
			@PathParam("uuid") String uuid, AdaptiveMediaImageConfigRepr config)
		throws PortalException {

		List<AdaptiveMediaImageConfigRepr> configs = _getDiferentConfigurations(
			uuid);

		try {
			_writeProperties(configs);
		}
		catch (Exception e) {
			return Response.serverError().build();
		}

		return Response.noContent().build();
	}

	@GET
	@Path("/{uuid}")
	@Produces({"application/json", "application/xml"})
	public Response getConfiguration(@PathParam("uuid") String uuid) {
		Optional<ImageAdaptiveMediaConfigurationEntry> entry =
			_imageAdaptiveMediaConfiguration.
				getImageAdaptiveMediaConfigurationEntry(_companyId, uuid);

		if (!entry.isPresent()) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		AdaptiveMediaImageConfigRepr config = new AdaptiveMediaImageConfigRepr(
			entry.get());

		return Response.ok(config).build();
	}

	@GET
	@Produces({"application/json", "application/xml"})
	public Response getConfigurations() {
		Collection<ImageAdaptiveMediaConfigurationEntry>
			imageAdaptiveMediaConfigurationEntries =
				_imageAdaptiveMediaConfiguration.
					getImageAdaptiveMediaConfigurationEntries(_companyId);

		List<AdaptiveMediaImageConfigRepr> configs =
			imageAdaptiveMediaConfigurationEntries.stream().map(
				AdaptiveMediaImageConfigRepr::new).collect(Collectors.toList());

		GenericEntity<List<AdaptiveMediaImageConfigRepr>> entity =
			new GenericEntity<List<AdaptiveMediaImageConfigRepr>>(configs) {
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

	private List<AdaptiveMediaImageConfigRepr> _getDiferentConfigurations(
		String uuid) {

		Collection<ImageAdaptiveMediaConfigurationEntry>
			imageAdaptiveMediaConfigurationEntries =
				_imageAdaptiveMediaConfiguration.
					getImageAdaptiveMediaConfigurationEntries(_companyId);

		return imageAdaptiveMediaConfigurationEntries.stream().map(
			AdaptiveMediaImageConfigRepr::new).filter(
				c -> !c.getUuid().equals(uuid)).collect(Collectors.toList());
	}

	private <T> T _getService(Class<T> clazz) {
		Registry registry = RegistryUtil.getRegistry();

		return registry.getService(clazz);
	}

	private void _writeProperties(List<AdaptiveMediaImageConfigRepr> configs)
		throws Exception {

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("imageVariants", configs.stream().map(
			AdaptiveMediaImageConfigRepr::toString).collect(
				Collectors.toList()).toArray(new String[configs.size()]));

		Configuration configuration = _getConfiguration();

		configuration.update(properties);
	}

	private final long _companyId;
	private final ImageAdaptiveMediaConfiguration
		_imageAdaptiveMediaConfiguration;

}