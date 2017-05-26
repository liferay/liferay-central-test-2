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

package com.liferay.adaptive.media.image.internal.url;

import com.liferay.adaptive.media.AdaptiveMediaURIResolver;
import com.liferay.adaptive.media.exception.AdaptiveMediaRuntimeException;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.url.AdaptiveMediaImageURLFactory;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.io.UnsupportedEncodingException;

import java.net.URI;
import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true)
public class AdaptiveMediaImageURLFactoryImpl
	implements AdaptiveMediaImageURLFactory {

	@Override
	public URI createFileEntryURL(
		FileVersion fileVersion,
		AdaptiveMediaImageConfigurationEntry configurationEntry) {

		String relativeURI = String.format(
			"image/%d/%s/%s", fileVersion.getFileEntryId(),
			configurationEntry.getUUID(), _encode(fileVersion.getFileName()));

		return _uriResolver.resolveURI(URI.create(relativeURI));
	}

	@Override
	public URI createFileVersionURL(
		FileVersion fileVersion,
		AdaptiveMediaImageConfigurationEntry configurationEntry) {

		String relativeURI = String.format(
			"image/%d/%d/%s/%s", fileVersion.getFileEntryId(),
			fileVersion.getFileVersionId(), configurationEntry.getUUID(),
			_encode(fileVersion.getFileName()));

		return _uriResolver.resolveURI(URI.create(relativeURI));
	}

	@Reference(unbind = "-")
	protected void setAdaptiveMediaURIResolver(
		AdaptiveMediaURIResolver adaptiveMediaURIResolver) {

		_uriResolver = adaptiveMediaURIResolver;
	}

	private String _encode(String s) {
		try {
			return URLEncoder.encode(s, StandardCharsets.UTF_8.name());
		}
		catch (UnsupportedEncodingException uee) {
			throw new AdaptiveMediaRuntimeException.
				UnsupportedEncodingException(uee);
		}
	}

	private AdaptiveMediaURIResolver _uriResolver;

}