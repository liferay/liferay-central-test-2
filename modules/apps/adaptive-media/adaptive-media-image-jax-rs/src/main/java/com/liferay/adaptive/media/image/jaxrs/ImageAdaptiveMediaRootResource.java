package com.liferay.adaptive.media.image.jaxrs;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationHelper;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true, service = ImageAdaptiveMediaRootResource.class)
@Path("/images")
public class ImageAdaptiveMediaRootResource {

	@Path("/configuration/{companyId}")
	public ImageAdaptiveMediaConfigResource getConfiguration(
		@PathParam("companyId") long companyId) {

		return new ImageAdaptiveMediaConfigResource(
			companyId, imageAdaptiveMediaConfigurationHelper);
	}

	@Reference
	protected ImageAdaptiveMediaConfigurationHelper
		imageAdaptiveMediaConfigurationHelper;

}