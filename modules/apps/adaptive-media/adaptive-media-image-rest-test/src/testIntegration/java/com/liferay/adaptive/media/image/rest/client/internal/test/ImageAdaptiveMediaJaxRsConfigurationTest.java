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

package com.liferay.adaptive.media.image.rest.client.internal.test;

import static com.liferay.adaptive.media.image.rest.client.internal.util.ImageAdaptiveMediaTestUtil.IDENTITY_WEB_TARGET_RESOLVER;
import static com.liferay.adaptive.media.image.rest.client.internal.util.ImageAdaptiveMediaTestUtil.TEST_AUTH;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.liferay.adaptive.media.image.rest.client.internal.util.ImageAdaptiveMediaTestUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Alejandro Hernández
 */
@RunAsClient
@RunWith(Arquillian.class)
public class ImageAdaptiveMediaJaxRsConfigurationTest {

	@Before
	public void setUp() {
		_deleteAllConfigurationEntries(false);
		_deleteAllConfigurationEntries(true);
	}

	@Test
	public void testAddConfigurationReturnsConfigurationObject()
		throws Exception {

		JsonObject randomConfigurationJsonObject =
			_getRandomConfigurationJsonObject();

		Invocation.Builder builder = _getAuthenticatedInvocationBuilder(
			_getId(randomConfigurationJsonObject));

		JsonObject responseJsonObject = builder.put(
			Entity.json(randomConfigurationJsonObject), JsonObject.class);

		JSONAssert.assertEquals(
			randomConfigurationJsonObject.toString(),
			responseJsonObject.toString(), true);
	}

	@Test
	public void testAddConfigurationWithoutAuthorizationReturns403() {
		JsonObject randomConfigurationJsonObject =
			_getRandomConfigurationJsonObject();

		Invocation.Builder builder = _getUnauthenticatedInvocationBuilder(
			_getId(randomConfigurationJsonObject));

		Response response = builder.put(
			Entity.json(randomConfigurationJsonObject));

		Assert.assertEquals(403, response.getStatus());
	}

	@Test
	public void testAddConfigurationWithoutBodyReturns400() {
		Invocation.Builder builder = _getAuthenticatedInvocationBuilder(
			ImageAdaptiveMediaTestUtil.getRandomUuid());

		Response response = builder.put(Entity.json(""));

		Assert.assertEquals(400, response.getStatus());
	}

	@Test
	public void testDeleteConfigurationWithoutAuthorizationReturns403() {
		Invocation.Builder builder = _getUnauthenticatedInvocationBuilder(
			ImageAdaptiveMediaTestUtil.getRandomUuid());

		Response response = builder.delete();

		Assert.assertEquals(403, response.getStatus());
	}

	@Test
	public void testDeleteDisabledConfigurationDeletesConfiguration()
		throws Exception {

		JsonObject configurationJsonObject = _putConfiguration(
			_getRandomDisabledConfigurationJsonObject());

		Invocation.Builder builder = _getAuthenticatedInvocationBuilder(
			_getId(configurationJsonObject));

		JsonObject responseJsonObject = builder.get(JsonObject.class);

		JSONAssert.assertEquals(
			configurationJsonObject.toString(), responseJsonObject.toString(),
			true);

		builder.delete();

		Response response = builder.get();

		Assert.assertEquals(404, response.getStatus());
	}

	@Test
	public void testDeleteEnabledConfigurationReturns400() throws Exception {
		JsonObject configurationJsonObject = _putConfiguration(
			_getRandomConfigurationJsonObject());

		Invocation.Builder builder = _getAuthenticatedInvocationBuilder(
			_getId(configurationJsonObject));

		JsonObject responseJsonObject = builder.get(JsonObject.class);

		JSONAssert.assertEquals(
			configurationJsonObject.toString(), responseJsonObject.toString(),
			true);

		Response response = builder.delete();

		Assert.assertEquals(400, response.getStatus());
	}

	@Test
	public void testDeleteExistingConfigurationReturns204() {
		JsonObject configurationJsonObject = _putConfiguration(
			_getRandomDisabledConfigurationJsonObject());

		Invocation.Builder builder = _getAuthenticatedInvocationBuilder(
			_getId(configurationJsonObject));

		Response response = builder.delete();

		Assert.assertEquals(204, response.getStatus());
	}

	@Test
	public void testDeleteNonExistingConfigurationReturns204() {
		Invocation.Builder builder = _getAuthenticatedInvocationBuilder(
			ImageAdaptiveMediaTestUtil.getRandomUuid());

		Response response = builder.delete();

		Assert.assertEquals(204, response.getStatus());
	}

	@Test
	public void testGetAllConfigurationsEmpty() {
		Invocation.Builder builder =
			ImageAdaptiveMediaTestUtil.getConfigurationRequest(
				IDENTITY_WEB_TARGET_RESOLVER);

		JsonArray responseJsonArray = builder.get(JsonArray.class);

		Assert.assertEquals(0, responseJsonArray.size());
	}

	@Test
	public void testGetConfigurationsListsDisabledIfParam() throws Exception {
		Map<String, JsonObject> configurations = _putConfigurations(
				_configurationJsonObjects);

		Invocation.Builder builder =
				ImageAdaptiveMediaTestUtil.getConfigurationRequest(
						webTarget -> webTarget.queryParam("enabled", false));

		JsonArray responseJsonArray = builder.get(JsonArray.class);

		Map<String, JsonObject> disabledConfigurations =
			configurations.entrySet().stream().filter(
				entry -> !entry.getValue().get("enabled").getAsBoolean()).
				collect(
					Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

		_assertConfigurations(disabledConfigurations, responseJsonArray);
	}

	@Test
	public void testGetConfigurationsListsOnlyEnabled() throws Exception {
		Map<String, JsonObject> configurations = _putConfigurations(
				_configurationJsonObjects);

		Invocation.Builder builder =
				ImageAdaptiveMediaTestUtil.getConfigurationRequest(
						webTarget -> webTarget.queryParam("enabled", true));

		JsonArray responseJsonArray = builder.get(JsonArray.class);

		Map<String, JsonObject> enabledConfigurations =
			configurations.entrySet().stream().filter(
				entry -> entry.getValue().get("enabled").getAsBoolean()).
				collect(
					Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

		_assertConfigurations(enabledConfigurations, responseJsonArray);
	}

	@Test
	public void testGetConfigurationWithExistingIdReturnsConfiguration()
		throws Exception {

		JsonObject configurationJsonObject = _putConfiguration(
			_getRandomConfigurationJsonObject());

		Invocation.Builder builder = _getUnauthenticatedInvocationBuilder(
			_getId(configurationJsonObject));

		JsonObject responseJsonObject = builder.get(JsonObject.class);

		JSONAssert.assertEquals(
			configurationJsonObject.toString(), responseJsonObject.toString(),
			true);
	}

	@Test
	public void testGetConfigurationWithNonExistingIdReturns404() {
		Invocation.Builder builder = _getUnauthenticatedInvocationBuilder(
			ImageAdaptiveMediaTestUtil.getRandomUuid());

		Response response = builder.get();

		Assert.assertEquals(404, response.getStatus());
	}

	private static long _getRandomLong() {
		return Math.abs(new Random().nextLong() % 1000);
	}

	private JsonObject _putConfiguration(JsonObject jsonObject) {
		return _getAuthenticatedInvocationBuilder(_getId(jsonObject)).put(
			Entity.json(jsonObject), JsonObject.class);
	}

	private Response _deleteConfiguration(JsonObject jsonObject) {
		return _getAuthenticatedInvocationBuilder(_getId(jsonObject)).delete();
	}

	private Map<String, JsonObject> _putConfigurations(
		List<JsonObject> configurationJsonObjects) {

		Map<String, JsonObject> configurationJsonObjectMap = new HashMap<>();

		configurationJsonObjects.forEach(
			configurationJsonObject -> {
				configurationJsonObjectMap.put(
					_getId(configurationJsonObject), configurationJsonObject);

				_putConfiguration(configurationJsonObject);
			});

		return configurationJsonObjectMap;
	}

	private void _assertConfigurations(
			Map<String, JsonObject> configurationsMap,
			JsonArray responseJsonArray)
		throws Exception {

		Assert.assertEquals(
				configurationsMap.size(), responseJsonArray.size());

		for (JsonElement responseJsonElement : responseJsonArray) {
			JsonObject responseJsonObject =
				responseJsonElement.getAsJsonObject();

			JsonObject expectedObject = configurationsMap.get(
				_getId(responseJsonObject));

			JSONAssert.assertEquals(
				expectedObject.toString(), responseJsonObject.toString(), true);
		}
	}

	private void _deleteAllConfigurationEntries(boolean enabled) {
		Invocation.Builder builder =
			ImageAdaptiveMediaTestUtil.getConfigurationRequest(webTarget ->
				webTarget.queryParam("enabled", enabled));

		JsonArray responseJsonArray = builder.get(JsonArray.class);

		responseJsonArray.forEach(
			jsonElement -> {
				JsonObject jsonObject = jsonElement.getAsJsonObject();

				jsonObject.addProperty("enabled", false);

				_putConfiguration(jsonObject);

				_deleteConfiguration(jsonObject);
			});
	}

	private Invocation.Builder _getAuthenticatedInvocationBuilder(String id) {
		return _getUnauthenticatedInvocationBuilder(
			id).header("Authorization", TEST_AUTH);
	}

	private String _getId(JsonObject configurationJsonObject) {
		JsonElement configurationJsonElement = configurationJsonObject.get(
			"id");

		return configurationJsonElement.getAsString();
	}

	private JsonObject _getRandomConfigurationJsonObject() {
		Random random = new Random();

		JsonObject jsonObject = _configurationJsonObjects.get(
			random.nextInt(_configurationJsonObjects.size()));

		if (!jsonObject.get("enabled").getAsBoolean()) {
			jsonObject.addProperty("enabled", true);
		}

		return jsonObject;
	}

	private JsonObject _getRandomDisabledConfigurationJsonObject() {
		JsonObject jsonObject = _getRandomConfigurationJsonObject();

		if (jsonObject.get("enabled").getAsBoolean()) {
			jsonObject.addProperty("enabled", false);
		}

		return jsonObject;
	}

	private Invocation.Builder _getUnauthenticatedInvocationBuilder(String id) {
		return ImageAdaptiveMediaTestUtil.getConfigurationRequest(
			webTarget -> webTarget.path("/{id}").resolveTemplate("id", id));
	}

	private static final List<JsonObject> _configurationJsonObjects =
		new ArrayList<>();

	static {
		for (int i = 0; i < 10; i++) {
			JsonObject jsonObject = new JsonObject();

			String id = ImageAdaptiveMediaTestUtil.getRandomUuid();

			Boolean enabled = new Random().nextBoolean();

			jsonObject.addProperty("enabled", enabled);

			jsonObject.addProperty("name", id + " Size");

			jsonObject.addProperty("id", id);

			jsonObject.addProperty(
				"max-height", String.valueOf(_getRandomLong()));

			jsonObject.addProperty(
				"max-width", String.valueOf(_getRandomLong()));

			_configurationJsonObjects.add(jsonObject);
		}
	}

}