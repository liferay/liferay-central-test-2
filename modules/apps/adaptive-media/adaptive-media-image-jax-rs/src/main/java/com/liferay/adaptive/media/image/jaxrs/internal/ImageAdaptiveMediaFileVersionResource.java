package com.liferay.adaptive.media.image.jaxrs.internal;

import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.AdaptiveMediaAttribute;
import com.liferay.adaptive.media.AdaptiveMediaException;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationHelper;
import com.liferay.adaptive.media.image.finder.ImageAdaptiveMediaFinder;
import com.liferay.adaptive.media.image.finder.ImageAdaptiveMediaQueryBuilder;
import com.liferay.adaptive.media.image.jaxrs.internal.provider.AdaptiveMediaApiQuery;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaAttribute;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
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

	@GET
	@Path("/data")
	@Produces("image")
	public Response getData(@Context AdaptiveMediaApiQuery query)
		throws AdaptiveMediaException, PortalException {

		List<AdaptiveMediaApiQuery.QueryAttribute> queryList = query.select(
			_allowedAttributes);

		if (queryList.isEmpty()) {
			throw new BadRequestException("You must provide a valid query");
		}

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_getAdaptiveMediaStream(queryList);

		return _getFirstAdaptiveMedia(stream);
	}

	private Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>>
			_getAdaptiveMediaStream(
				List<AdaptiveMediaApiQuery.QueryAttribute> query)
		throws AdaptiveMediaException, PortalException {

		return _finder.getAdaptiveMedia(
			queryBuilder -> {
				ImageAdaptiveMediaQueryBuilder.InitialStep step =
					queryBuilder.forVersion(_fileVersion);

				query.forEach(
					a -> step.with(
						(AdaptiveMediaAttribute)a.getAttribute(),
						a.getValue()));

				return step.done();
			});
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

	private static final Map<String, AdaptiveMediaAttribute<?, ?>>
		_allowedAttributes = new HashMap<>();

	static {
		_allowedAttributes.putAll(
			ImageAdaptiveMediaAttribute.allowedAttributes());
		_allowedAttributes.putAll(AdaptiveMediaAttribute.allowedAttributes());
	}

	private final FileVersion _fileVersion;
	private final ImageAdaptiveMediaFinder _finder;

}