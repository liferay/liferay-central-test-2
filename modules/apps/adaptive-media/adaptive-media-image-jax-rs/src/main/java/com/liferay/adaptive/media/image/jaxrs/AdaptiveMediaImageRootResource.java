package com.liferay.adaptive.media.image.jaxrs;

import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfiguration;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true, service = AdaptiveMediaImageRootResource.class)
@Path("/images")
public class AdaptiveMediaImageRootResource {

	@Path("/configuration/{companyId}")
	public AdaptiveMediaImageConfigResource getConfiguration(
		@PathParam("companyId") long companyId) {

		return new AdaptiveMediaImageConfigResource(
			companyId, imageAdaptiveMediaConfiguration);
	}

	@Reference
	protected ImageAdaptiveMediaConfiguration imageAdaptiveMediaConfiguration;

}