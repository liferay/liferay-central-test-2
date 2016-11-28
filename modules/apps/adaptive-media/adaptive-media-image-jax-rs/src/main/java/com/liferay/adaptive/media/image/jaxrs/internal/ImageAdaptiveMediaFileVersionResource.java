package com.liferay.adaptive.media.image.jaxrs.internal;

import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.AdaptiveMediaAttribute;
import com.liferay.adaptive.media.AdaptiveMediaException;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationHelper;
import com.liferay.adaptive.media.image.finder.ImageAdaptiveMediaFinder;
import com.liferay.adaptive.media.image.finder.ImageAdaptiveMediaQueryBuilder;
import com.liferay.adaptive.media.image.jaxrs.internal.provider.AdaptiveMediaApiQuery;
import com.liferay.adaptive.media.image.jaxrs.internal.provider.OrderBySelector;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaAttribute;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.io.InputStream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

/**
 * @author Alejandro Hernández
 */
public class ImageAdaptiveMediaFileVersionResource {

	public ImageAdaptiveMediaFileVersionResource(
		FileVersion fileVersion, ImageAdaptiveMediaFinder finder,
		ImageAdaptiveMediaConfigurationHelper
			imageAdaptiveMediaConfigurationHelper, UriBuilder uriBuilder) {

		_fileVersion = fileVersion;
		_finder = finder;
		_configurationHelper = imageAdaptiveMediaConfigurationHelper;
		_uriBuilder = uriBuilder;
	}

	@GET
	@Path("/config/{uuid}")
	@Produces("image")
	public Response getConfiguration(
			@PathParam("uuid") String uuid,
			@DefaultValue("true") @QueryParam("original") boolean original)
		throws AdaptiveMediaException, PortalException {

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder -> queryBuilder.forVersion(_fileVersion).
					forConfiguration(uuid).done());

		return _getFirstAdaptiveMedia(stream, original);
	}

	@GET
	@Path("/data")
	@Produces("image")
	public Response getData(
			@Context AdaptiveMediaApiQuery query,
			@DefaultValue("true") @QueryParam("original") boolean original)
		throws AdaptiveMediaException, PortalException {

		List<AdaptiveMediaApiQuery.QueryAttribute> queryList = query.select(
			_allowedAttributes);

		if (queryList.isEmpty()) {
			throw new BadRequestException("You must provide a valid query");
		}

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_getAdaptiveMediaStream(queryList);

		return _getFirstAdaptiveMedia(stream, original);
	}

	@GET
	@Path("/variants")
	@Produces({"application/json", "application/xml"})
	public List<ImageAdaptiveMediaRepr> getVariants(
			@Context OrderBySelector orderBySelector,
			@Context AdaptiveMediaApiQuery query)
		throws AdaptiveMediaException, PortalException {

		List<OrderBySelector.FieldOrder> orderList = orderBySelector.select(
			_allowedAttributes.keySet());

		List<AdaptiveMediaApiQuery.QueryAttribute> queryList = query.select(
			_allowedAttributes);

		if (!queryList.isEmpty() && !orderList.isEmpty()) {
			throw new BadRequestException(
				"Query and order requests cannot be used at the same time");
		}

		if (queryList.isEmpty() && orderList.isEmpty()) {
			throw new BadRequestException(
				"You must provide, at least, a valid query or order");
		}

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream;

		if (orderList.isEmpty()) {
			stream = _getAdaptiveMediaStream(queryList);
		}
		else {
			stream = _finder.getAdaptiveMedia(queryBuilder -> {
				ImageAdaptiveMediaQueryBuilder.InitialStep initialStep =
					queryBuilder.forVersion(_fileVersion);

				orderList.forEach(
					order -> initialStep.orderBy(
						(AdaptiveMediaAttribute)_allowedAttributes.get(
							order.getFieldName()),
						order.isAscending()));

				return initialStep.done();
			});
		}

		return _getImageAdaptiveMediaList(stream);
	}

	private ImageAdaptiveMediaConfigurationEntry
		_getAdaptiveMediaConfigurationEntry(
			AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia) {

		Optional<String> uuid = adaptiveMedia.getAttributeValue(
			AdaptiveMediaAttribute.configurationUuid());

		return _configurationHelper.getImageAdaptiveMediaConfigurationEntry(
			_fileVersion.getCompanyId(), uuid.get()).get();
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

	private String _getAdaptiveMediaUri(
		UriBuilder uriBuilder,
		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia) {

		return uriBuilder.clone().build(
			Long.toString(_fileVersion.getFileVersionId()),
			adaptiveMedia.getAttributeValue(
				AdaptiveMediaAttribute.configurationUuid()).get()).toString();
	}

	private Response _getFirstAdaptiveMedia(
			Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream,
			boolean fallbackToOriginal)
		throws PortalException {

		Optional<AdaptiveMedia<ImageAdaptiveMediaProcessor>> adaptiveMedia =
			stream.findFirst();

		if (adaptiveMedia.isPresent() && !fallbackToOriginal) {
			throw new NotFoundException();
		}

		InputStream content =
			adaptiveMedia.isPresent() ? adaptiveMedia.get().getInputStream() :
				_fileVersion.getContentStream(true);

		return Response.status(200).type(_fileVersion.getMimeType()).entity(
			content).build();
	}

	private List<ImageAdaptiveMediaRepr> _getImageAdaptiveMediaList(
		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream) {

		UriBuilder uriBuilder = _uriBuilder.path(
			ImageAdaptiveMediaFileVersionResource.class, "getConfiguration");

		return stream.map(adaptiveMedia
			-> new ImageAdaptiveMediaRepr(
				adaptiveMedia, _getAdaptiveMediaUri(uriBuilder, adaptiveMedia),
				_getAdaptiveMediaConfigurationEntry(adaptiveMedia))).collect(
					Collectors.toList());
	}

	private static final Map<String, AdaptiveMediaAttribute<?, ?>>
		_allowedAttributes = new HashMap<>();

	static {
		_allowedAttributes.putAll(
			ImageAdaptiveMediaAttribute.allowedAttributes());
		_allowedAttributes.putAll(AdaptiveMediaAttribute.allowedAttributes());
	}

	private final ImageAdaptiveMediaConfigurationHelper _configurationHelper;
	private final FileVersion _fileVersion;
	private final ImageAdaptiveMediaFinder _finder;
	private final UriBuilder _uriBuilder;

}