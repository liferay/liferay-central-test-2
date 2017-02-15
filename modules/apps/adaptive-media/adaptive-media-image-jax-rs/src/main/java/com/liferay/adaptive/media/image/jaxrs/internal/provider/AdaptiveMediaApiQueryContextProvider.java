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

package com.liferay.adaptive.media.image.jaxrs.internal.provider;

import com.liferay.adaptive.media.AdaptiveMediaAttribute;
import com.liferay.adaptive.media.AdaptiveMediaRuntimeException.AdaptiveMediaAttributeFormatException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.ServletRequest;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

/**
 * @author Alejandro Hernández
 */
@Provider
public class AdaptiveMediaApiQueryContextProvider
	implements ContextProvider<AdaptiveMediaApiQuery> {

	@Override
	public AdaptiveMediaApiQuery createContext(Message message) {
		ServletRequest request = (ServletRequest)message.getContextualProperty(
			"HTTP.REQUEST");

		String[] queries = request.getParameterValues("q");

		if (queries == null) {
			return availableAttributes -> Collections.emptyList();
		}

		return availableAttributes ->
			Arrays.stream(queries).map(
				query -> parseQuery(query, availableAttributes)).filter(
				Optional::isPresent).map(Optional::get).collect(
				Collectors.toList());
	}

	protected Optional<AdaptiveMediaApiQuery.QueryAttribute> parseQuery(
		String query,
		Map<String, AdaptiveMediaAttribute<?, ?>> availableAttributes) {

		String[] keyValuePair = _KEY_VALUE_SEPARATOR_PATTERN.split(query);

		if ((keyValuePair.length != 2) ||
			!availableAttributes.containsKey(keyValuePair[0])) {

			return Optional.empty();
		}

		AdaptiveMediaAttribute attribute = availableAttributes.get(
			keyValuePair[0]);

		try {
			return Optional.of(
				new AdaptiveMediaApiQuery.QueryAttribute(
					attribute, attribute.convert(keyValuePair[1])));
		}
		catch (AdaptiveMediaAttributeFormatException amafe) {
			throw new BadRequestException(
				String.format(
					"Incorrect or malformed query {q=\"%s\"}", query));
		}
	}

	private static final Pattern _KEY_VALUE_SEPARATOR_PATTERN = Pattern.compile(
		"\\s*:\\s*");

}