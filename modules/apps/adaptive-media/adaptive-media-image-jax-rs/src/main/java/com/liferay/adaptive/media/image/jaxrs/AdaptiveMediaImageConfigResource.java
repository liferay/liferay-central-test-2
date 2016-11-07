package com.liferay.adaptive.media.image.jaxrs;

import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfiguration;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationEntry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

	private final long _companyId;
	private final ImageAdaptiveMediaConfiguration
		_imageAdaptiveMediaConfiguration;

}