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

package com.liferay.adaptive.media.image.jaxrs.client.test.internal;

import aQute.lib.base64.Base64;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.liferay.adaptive.media.image.jaxrs.client.test.internal.provider.GsonProvider;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Hernández
 */
@RunAsClient
@RunWith(Arquillian.class)
public class ImageAdaptiveMediaJaxRsConfigurationTest {

	@Before
	public void setUp() {
		_deleteAllConfigurationEntries();
	}

	@Test
	public void testAddConfigurationReturnsConfigurationObject() {
		JsonObject randomConfigurationJsonObject =
			_getRandomConfigurationJsonObject();

		Invocation.Builder builder = _getAuthenticatedInvocationBuilder(
			_getUuid(randomConfigurationJsonObject));

		JsonObject responseJsonObject = builder.put(
			Entity.json(randomConfigurationJsonObject), JsonObject.class);

		_assertEquals(randomConfigurationJsonObject, responseJsonObject);
	}

	@Test
	public void testAddConfigurationWithoutAuthorizationReturns403() {
		JsonObject randomConfigurationJsonObject =
			_getRandomConfigurationJsonObject();

		Invocation.Builder builder = _getUnauthenticatedInvocationBuilder(
			_getUuid(randomConfigurationJsonObject));

		Response response = builder.put(
			Entity.json(randomConfigurationJsonObject));

		Assert.assertEquals(403, response.getStatus());
	}

	@Test
	public void testAddConfigurationWithoutBodyReturns400() {
		Invocation.Builder builder = _getAuthenticatedInvocationBuilder(
			_getRandomUuid());

		Response response = builder.put(Entity.json(""));

		Assert.assertEquals(400, response.getStatus());
	}

	@Test
	public void testDeleteConfigurationDeletesConfiguration() {
		JsonObject configurationJsonObject = _addConfiguration(
			_getRandomConfigurationJsonObject());

		Invocation.Builder builder = _getUnauthenticatedInvocationBuilder(
			_getUuid(configurationJsonObject));

		JsonObject responseJsonObject = builder.get(JsonObject.class);

		_assertEquals(configurationJsonObject, responseJsonObject);

		builder.delete();

		Response response = builder.get();

		Assert.assertEquals(404, response.getStatus());
	}

	@Test
	public void testDeleteConfigurationWithoutAuthorizationReturns403() {
		Invocation.Builder builder = _getUnauthenticatedInvocationBuilder(
			_getRandomUuid());

		Response response = builder.delete();

		Assert.assertEquals(403, response.getStatus());
	}

	@Test
	public void testDeleteExistingConfigurationReturns204() {
		JsonObject configurationJsonObject = _addConfiguration(
			_getRandomConfigurationJsonObject());

		Invocation.Builder builder = _getAuthenticatedInvocationBuilder(
			_getUuid(configurationJsonObject));

		Response response = builder.delete();

		Assert.assertEquals(204, response.getStatus());
	}

	@Test
	public void testDeleteNonExistingConfigurationReturns204() {
		Invocation.Builder builder = _getAuthenticatedInvocationBuilder(
			_getRandomUuid());

		Response response = builder.delete();

		Assert.assertEquals(204, response.getStatus());
	}

	@Test
	public void testGetAllConfigurationsEmpty() {
		Invocation.Builder builder = _getBaseRequest(
			_IDENTITY_WEB_TARGET_RESOLVER);

		JsonArray responseJsonArray = builder.get(JsonArray.class);

		Assert.assertEquals(0, responseJsonArray.size());
	}

	@Test
	public void testGetAllConfigurationsNonEmpty() {
		Map<String, JsonObject> configurations = _addConfigurations(
			_configurationJsonObjects);

		Invocation.Builder builder = _getBaseRequest(
			_IDENTITY_WEB_TARGET_RESOLVER);

		JsonArray responseJsonArray = builder.get(JsonArray.class);

		Assert.assertEquals(configurations.size(), responseJsonArray.size());

		responseJsonArray.forEach(
			responseJsonElement -> {
				JsonObject responseJsonObject =
					responseJsonElement.getAsJsonObject();

				JsonObject expectedObject = configurations.get(
					_getUuid(responseJsonObject));

				_assertEquals(expectedObject, responseJsonObject);
			});
	}

	@Test
	public void testGetConfigurationWithExistingUuidReturnsConfiguration() {
		JsonObject configurationJsonObject = _addConfiguration(
			_getRandomConfigurationJsonObject());

		Invocation.Builder builder = _getUnauthenticatedInvocationBuilder(
			_getUuid(configurationJsonObject));

		JsonObject responseJsonObject = builder.get(JsonObject.class);

		_assertEquals(configurationJsonObject, responseJsonObject);
	}

	@Test
	public void testGetConfigurationWithNonExistingUuidReturns404() {
		Invocation.Builder builder = _getUnauthenticatedInvocationBuilder(
			_getRandomUuid());

		Response response = builder.get();

		Assert.assertEquals(404, response.getStatus());
	}

	private static long _getRandomLong() {
		return Math.abs(new Random().nextLong() % 1000);
	}

	private static String _getRandomUuid() {
		return UUID.randomUUID().toString();
	}

	private JsonObject _addConfiguration(JsonObject json) {
		return _getAuthenticatedInvocationBuilder(_getUuid(json)).put(
			Entity.json(json), JsonObject.class);
	}

	private Map<String, JsonObject> _addConfigurations(
		List<JsonObject> configurationJsonObjects) {

		Map<String, JsonObject> configurationJsonObjectMap = new HashMap<>();

		configurationJsonObjects.forEach(
			configurationJsonObject -> {
				configurationJsonObjectMap.put(
					_getUuid(configurationJsonObject), configurationJsonObject);

				_addConfiguration(configurationJsonObject);
			});

		return configurationJsonObjectMap;
	}

	private void _assertEquals(
		JsonObject expectedJsonObject, JsonObject actualJsonObject) {

		JsonParser jsonParser = new JsonParser();

		JsonObject parsedExpectedJsonObject = (JsonObject)jsonParser.parse(
			expectedJsonObject.getAsString());
		JsonObject parsedActualJsonObject = (JsonObject)jsonParser.parse(
			actualJsonObject.getAsString());

		Assert.assertEquals(parsedExpectedJsonObject, parsedActualJsonObject);
	}

	private void _deleteAllConfigurationEntries() {
		Invocation.Builder builder = _getBaseRequest(
			_IDENTITY_WEB_TARGET_RESOLVER);

		JsonArray responseJsonArray = builder.get(JsonArray.class);

		responseJsonArray.forEach(
			jsonElement -> {
				String uuid = _getUuid(jsonElement.getAsJsonObject());

				_getAuthenticatedInvocationBuilder(uuid).delete();
			});
	}

	private Invocation.Builder _getAuthenticatedInvocationBuilder(String uuid) {
		Invocation.Builder builder = _getBaseRequest(
			webTarget ->
				webTarget.path("/{uuid}").resolveTemplate("uuid", uuid));

		return builder.header("Authorization", _testAuth);
	}

	private Invocation.Builder _getBaseRequest(
		Function<WebTarget, WebTarget> webTargetResolver) {

		Client client = ClientBuilder.newClient();

		client.register(GsonProvider.class);

		WebTarget webTarget = client.target(_context.toString());

		webTarget = webTarget.path(_BASE_PATH);

		webTarget = webTargetResolver.apply(webTarget);

		return webTarget.request(MediaType.APPLICATION_JSON_TYPE);
	}

	private JsonObject _getRandomConfigurationJsonObject() {
		Random random = new Random();

		return _configurationJsonObjects.get(
			random.nextInt(_configurationJsonObjects.size()));
	}

	private Invocation.Builder _getUnauthenticatedInvocationBuilder(
		String uuid) {

		return _getBaseRequest(
			webTarget ->
				webTarget.path("/{uuid}").resolveTemplate("uuid", uuid));
	}

	private String _getUuid(JsonObject configurationJsonObject) {
		JsonElement configurationJsonElement = configurationJsonObject.get(
			"uuid");

		return configurationJsonElement.getAsString();
	}

	private static final String _BASE_PATH =
		"/o/adaptive-media/images/configuration";

	private static final Function<WebTarget, WebTarget>
		_IDENTITY_WEB_TARGET_RESOLVER = webTarget -> webTarget;

	private static final List<JsonObject> _configurationJsonObjects =
		new ArrayList<>();
	private static final String _testAuth =
		"Basic " + Base64.encodeBase64("test@liferay.com:test".getBytes());

	static {
		for (int i = 0; i < 10; i++) {
			JsonObject jsonObject = new JsonObject();

			String uuid = _getRandomUuid();

			jsonObject.addProperty("name", uuid + " Size");

			jsonObject.addProperty("uuid", uuid);

			jsonObject.addProperty("height", _getRandomLong());

			jsonObject.addProperty("width", _getRandomLong());

			_configurationJsonObjects.add(jsonObject);
		}
	}

	@ArquillianResource
	private URL _context;

}