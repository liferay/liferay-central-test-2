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
import com.liferay.adaptive.media.processor.AdaptiveMediaAsyncProcessor;
import com.liferay.adaptive.media.processor.AdaptiveMediaAsyncProcessorLocator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.io.InputStream;

import java.net.URI;

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
		ImageAdaptiveMediaConfigurationHelper configurationHelper,
		AdaptiveMediaAsyncProcessorLocator asyncProcessorLocator,
		UriBuilder uriBuilder) {

		_fileVersion = fileVersion;
		_finder = finder;
		_configurationHelper = configurationHelper;
		_uriBuilder = uriBuilder;

		_asyncProcessor = asyncProcessorLocator.locateForClass(
			FileVersion.class);
	}

	@GET
	@Path("/config/{id}")
	@Produces("image")
	public Response getConfiguration(
			@PathParam("id") String id,
			@DefaultValue("true") @QueryParam("original") boolean original)
		throws AdaptiveMediaException, PortalException {

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder -> queryBuilder.forVersion(_fileVersion).
					forConfiguration(id).done());

		Optional<AdaptiveMedia<ImageAdaptiveMediaProcessor>>
			adaptiveMediaOptional = stream.findFirst();

		_processAdaptiveMediaImage(adaptiveMediaOptional, id);

		InputStream is = _getInputStream(adaptiveMediaOptional, original);

		return Response.status(200).type(_fileVersion.getMimeType()).entity(is).
			build();
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

		Optional<AdaptiveMedia<ImageAdaptiveMediaProcessor>>
			adaptiveMediaOptional = stream.findFirst();

		InputStream is = _getInputStream(adaptiveMediaOptional, original);

		return Response.status(200).type(_fileVersion.getMimeType()).entity(is).
			build();
	}

	@GET
	@Path("/variants")
	@Produces("application/json")
	public List<ImageAdaptiveMediaRepr> getVariants(
			@Context OrderBySelector orderBySelector,
			@Context AdaptiveMediaApiQuery query)
		throws AdaptiveMediaException, PortalException {

		List<OrderBySelector.FieldOrder> fieldOrderList =
			orderBySelector.select(_allowedAttributes.keySet());

		List<AdaptiveMediaApiQuery.QueryAttribute> queryAtrributeList =
			query.select(_allowedAttributes);

		if (!queryAtrributeList.isEmpty() && !fieldOrderList.isEmpty()) {
			throw new BadRequestException(
				"Query and order requests cannot be used at the same time");
		}

		if (queryAtrributeList.isEmpty() && fieldOrderList.isEmpty()) {
			throw new BadRequestException(
				"You must provide, at least, a valid query or order");
		}

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream;

		if (fieldOrderList.isEmpty()) {
			stream = _getAdaptiveMediaStream(queryAtrributeList);
		}
		else {
			stream = _finder.getAdaptiveMedia(queryBuilder -> {
				ImageAdaptiveMediaQueryBuilder.InitialStep initialStep =
					queryBuilder.forVersion(_fileVersion);

				fieldOrderList.forEach(
					fieldOrder -> initialStep.orderBy(
						(AdaptiveMediaAttribute)_allowedAttributes.get(
							fieldOrder.getFieldName()),
						fieldOrder.isAscending()));

				return initialStep.done();
			});
		}

		return _getImageAdaptiveMediaList(stream);
	}

	private Optional<ImageAdaptiveMediaConfigurationEntry>
		_getAdaptiveMediaConfigurationEntry(
			AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia) {

		Optional<String> uuidOptional = adaptiveMedia.getAttributeValue(
			AdaptiveMediaAttribute.configurationUuid());

		return uuidOptional.flatMap(
			uuid ->
				_configurationHelper.getImageAdaptiveMediaConfigurationEntry(
					_fileVersion.getCompanyId(), uuid));
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
					queryAttribute -> step.with(
						(AdaptiveMediaAttribute)queryAttribute.getAttribute(),
						queryAttribute.getValue()));

				return step.done();
			});
	}

	private Optional<String> _getAdaptiveMediaUri(
		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia) {

		UriBuilder uriBuilder = _uriBuilder.clone().path(
			ImageAdaptiveMediaFileVersionResource.class, "getConfiguration");

		Optional<String> attributeValueOptional =
			adaptiveMedia.getAttributeValue(
				AdaptiveMediaAttribute.configurationUuid());

		Optional<URI> uriOptional = attributeValueOptional.map(
			(value) ->
				uriBuilder.build(
					String.valueOf(_fileVersion.getFileVersionId()), value));

		return uriOptional.map((uri) -> uri.toString());
	}

	private List<ImageAdaptiveMediaRepr> _getImageAdaptiveMediaList(
		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream) {

		return stream.flatMap(
			(adaptiveMedia) ->
				_getRepr(adaptiveMedia)).collect(Collectors.toList());
	}

	private InputStream _getInputStream(
			Optional<AdaptiveMedia<ImageAdaptiveMediaProcessor>>
				adaptiveMediaOptional,
			boolean fallbackToOriginal)
		throws PortalException {

		if (!adaptiveMediaOptional.isPresent() && !fallbackToOriginal) {
			throw new NotFoundException();
		}

		InputStream inputStream = null;

		if (adaptiveMediaOptional.isPresent()) {
			inputStream = adaptiveMediaOptional.get().getInputStream();
		}
		else {
			inputStream = _fileVersion.getContentStream(true);
		}

		return inputStream;
	}

	private Stream<ImageAdaptiveMediaRepr> _getRepr(
		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia) {

		Optional<ImageAdaptiveMediaRepr> imageAdaptiveMediaReprOptional =
			_getAdaptiveMediaConfigurationEntry(adaptiveMedia).flatMap(
				configurationEntry -> {
					Optional<String> uriOptional = _getAdaptiveMediaUri(
						adaptiveMedia);

					return uriOptional.map((uri) ->
						new ImageAdaptiveMediaRepr(
							adaptiveMedia, uri, configurationEntry));
				});

		if (imageAdaptiveMediaReprOptional.isPresent()) {
			return Stream.of(imageAdaptiveMediaReprOptional.get());
		}

		return Stream.empty();
	}

	private void _processAdaptiveMediaImage(
			Optional<AdaptiveMedia<ImageAdaptiveMediaProcessor>>
				adaptiveMediaOptional,
			String id)
		throws AdaptiveMediaException, PortalException {

		if (adaptiveMediaOptional.isPresent()) {
			return;
		}

		Optional<ImageAdaptiveMediaConfigurationEntry>
			configurationEntryOptional =
				_configurationHelper.getImageAdaptiveMediaConfigurationEntry(
					_fileVersion.getCompanyId(), id);

		if (!configurationEntryOptional.isPresent()) {
			return;
		}

		try {
			_asyncProcessor.triggerProcess(
				_fileVersion, String.valueOf(_fileVersion.getFileVersionId()));
		}
		catch (AdaptiveMediaException ame) {
			_log.error(
				"Unable to create lazy adaptive media for fileVersion id " +
					_fileVersion.getFileVersionId());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImageAdaptiveMediaFileVersionResource.class);

	private static final Map<String, AdaptiveMediaAttribute<?, ?>>
		_allowedAttributes = new HashMap<>();

	static {
		_allowedAttributes.putAll(
			ImageAdaptiveMediaAttribute.allowedAttributes());
		_allowedAttributes.putAll(AdaptiveMediaAttribute.allowedAttributes());
	}

	private final AdaptiveMediaAsyncProcessor<FileVersion, ?> _asyncProcessor;
	private final ImageAdaptiveMediaConfigurationHelper _configurationHelper;
	private final FileVersion _fileVersion;
	private final ImageAdaptiveMediaFinder _finder;
	private final UriBuilder _uriBuilder;

}