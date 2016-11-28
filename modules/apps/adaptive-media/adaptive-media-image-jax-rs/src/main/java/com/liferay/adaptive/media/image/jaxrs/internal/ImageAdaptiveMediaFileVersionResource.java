package com.liferay.adaptive.media.image.jaxrs.internal;

import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.AdaptiveMediaException;
import com.liferay.adaptive.media.image.finder.ImageAdaptiveMediaFinder;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.util.Optional;
import java.util.stream.Stream;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * @author Alejandro Hernández
 */
public class ImageAdaptiveMediaFileVersionResource {

	public ImageAdaptiveMediaFileVersionResource(
		FileVersion fileVersion, ImageAdaptiveMediaFinder finder) {

		_fileVersion = fileVersion;
		_finder = finder;
	}

	@GET
	@Path("/config/{uuid}")
	@Produces("image")
	public Response getConfiguration(@PathParam("uuid") String uuid)
		throws AdaptiveMediaException, PortalException {

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder -> queryBuilder.forVersion(_fileVersion).
					forConfiguration(uuid).done());

		return _getFirstAdaptiveMedia(stream);
	}

	private Response _getFirstAdaptiveMedia(
		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream) {

		Optional<AdaptiveMedia<ImageAdaptiveMediaProcessor>> adaptiveMedia =
			stream.findFirst();

		if (!adaptiveMedia.isPresent()) {
			throw new NotFoundException();
		}

		return Response.status(200).type(_fileVersion.getMimeType()).entity(
			adaptiveMedia.get().getInputStream()).build();
	}

	private final FileVersion _fileVersion;
	private final ImageAdaptiveMediaFinder _finder;

}