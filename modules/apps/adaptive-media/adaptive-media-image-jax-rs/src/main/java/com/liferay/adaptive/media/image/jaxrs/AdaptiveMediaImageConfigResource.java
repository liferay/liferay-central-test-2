package com.liferay.adaptive.media.image.jaxrs;

import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfiguration;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationEntry;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;
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