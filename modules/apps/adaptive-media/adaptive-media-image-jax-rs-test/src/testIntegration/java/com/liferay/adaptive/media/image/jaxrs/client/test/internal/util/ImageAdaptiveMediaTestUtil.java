/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.adaptive.media.image.jaxrs.client.test.internal.util;

import aQute.lib.base64.Base64;

import com.google.gson.JsonObject;

import com.liferay.adaptive.media.image.jaxrs.client.test.internal.provider.GsonProvider;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * @author Alejandro Hernández
 */
public class ImageAdaptiveMediaTestUtil {

	public static final Function<WebTarget, WebTarget>
		IDENTITY_WEB_TARGET_RESOLVER = webTarget -> webTarget;

	public static final String TEST_AUTH =
		"Basic " + Base64.encodeBase64("test@liferay.com:test".getBytes());

	public static Invocation.Builder getBaseRequest(
		String context, String basePath,
		Function<WebTarget, WebTarget> webTargetResolver) {

		Client client = ClientBuilder.newClient();

		client.register(GsonProvider.class);

		WebTarget webTarget = client.target(context);

		webTarget = webTarget.path(basePath);

		webTarget = webTargetResolver.apply(webTarget);

		return webTarget.request(MediaType.APPLICATION_JSON_TYPE);
	}

	public static Invocation.Builder getConfigurationRequest(
		URL context, Function<WebTarget, WebTarget> webTargetResolver) {

		return getBaseRequest(
			context.toString(), _CONFIGURATION_BASE_PATH, webTargetResolver);
	}

	private static final String _CONFIGURATION_BASE_PATH =
		"/o/adaptive-media/images/configuration";

}