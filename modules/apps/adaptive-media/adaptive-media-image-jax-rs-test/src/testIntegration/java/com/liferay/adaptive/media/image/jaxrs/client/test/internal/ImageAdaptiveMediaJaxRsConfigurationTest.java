package com.liferay.adaptive.media.image.jaxrs.client.test.internal;

import aQute.lib.base64.Base64;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.liferay.adaptive.media.image.jaxrs.client.test.internal.provider.GsonProvider;

import java.net.URL;

import java.util.HashMap;
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
	public void testAddConfigurationReturnConfigurationObject() {
		JsonObject expectedResponse = _getRandomConfiguration();

		JsonObject actualResponse = _addConfiguration(expectedResponse);

		_assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void testAddConfigurationWithoutAuthorizationResultsIn403() {
		JsonObject testConfig = _getRandomConfiguration();

		Response response = _getBaseRequest(
			t -> t.path(
				"/{id}").resolveTemplate("id",
				_getId(testConfig))).put(Entity.json(testConfig));

		Assert.assertEquals(403, response.getStatus());
	}

	@Test
	public void testGetAllConfigurationsEmpty() {
		JsonArray jsonArray = _getBaseRequest(_NO_PATH).get(JsonArray.class);

		Assert.assertEquals(0, jsonArray.size());
	}

	@Test
	public void testGetAllConfigurationsNonEmpty() {
		Map<String, JsonObject> configurations = _addMultipleConfigurations();

		JsonArray jsonArray = _getBaseRequest(_NO_PATH).get(JsonArray.class);

		Assert.assertEquals(configurations.size(), jsonArray.size());

		jsonArray.forEach(configuration -> {
			JsonObject actualObject = configuration.getAsJsonObject();

			JsonObject expectedObject = configurations.get(
				_getId(actualObject));

			_assertEquals(expectedObject, actualObject);
		});
	}

	@Test
	public void testGetConfigurationWithCorrectUUIDReturnConfiguration() {
		JsonObject expectedResponse = _getRandomConfiguration();

		_addConfiguration(expectedResponse);

		JsonObject actualResponse = _getBaseRequest(
			t -> t.path(
				"/{id}").resolveTemplate("id",
				_getId(expectedResponse))).get(JsonObject.class);

		_assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void testGetConfigurationWithWrongUUIDReturnNotFound() {
		Response response = _getBaseRequest(
			t -> t.path("/{id}").resolveTemplate("id", "small")).get();

		Assert.assertEquals(404, response.getStatus());
	}

	private static long _getRandomLong() {
		return Math.abs(new Random().nextLong() % 1000);
	}

	private JsonObject _addConfiguration(JsonObject json) {
		Invocation.Builder builder = _getBaseRequest(
			t -> t.path("/{id}").resolveTemplate("id", _getId(json)));

		return builder.header("Authorization", _testAuth).put(
			Entity.json(json), JsonObject.class);
	}

	private Map<String, JsonObject> _addMultipleConfigurations() {
		_testConfigList.forEach((k, v) -> _addConfiguration(v));
		return _testConfigList;
	}

	/**
	 * Asserts that two {@link JsonElement} are equal. If they are not, an
	 * {@link AssertionError} is thrown.
	 *
	 * @param expected expected json element value.
	 * @param actual actual json element value
	 */
	private void _assertEquals(JsonElement expected, JsonElement actual) {
		Assert.assertEquals(expected.getAsString(), actual.getAsString());
	}

	/**
	 * Asserts that two {@link JsonObject} are equal. If they are not, an
	 * {@link AssertionError} is thrown.
	 *
	 * @param expected expected json object value.
	 * @param actual actual json object value
	 */
	private void _assertEquals(JsonObject expected, JsonObject actual) {
		Assert.assertEquals(
			expected.entrySet().size(), actual.entrySet().size());

		expected.entrySet().forEach(entry ->
			_assertEquals(entry.getValue(), actual.get(entry.getKey())));
	}

	private void _deleteAllConfigurationEntries() {
		JsonArray jsonArray = _getBaseRequest(_NO_PATH).get(JsonArray.class);

		jsonArray.forEach(entry -> {
			String id = _getId(entry.getAsJsonObject());

			_getBaseRequest(
				t -> t.path("/{id}").resolveTemplate("id", id)).header(
					"Authorization", _testAuth).delete();
		});
	}

	private Invocation.Builder _getBaseRequest(
		Function<WebTarget, WebTarget> path) {

		Client client = ClientBuilder.newClient().register(GsonProvider.class);

		WebTarget target = client.target(
			String.valueOf(_context)).path(_BASE_PATH);

		return path.apply(target).request(MediaType.APPLICATION_JSON_TYPE);
	}

	private String _getId(JsonObject configuration) {
		return configuration.get("id").getAsString();
	}

	private JsonObject _getRandomConfiguration() {
		Object[] values = _testConfigList.values().toArray();

		return (JsonObject)values[new Random().nextInt(values.length)];
	}

	private static final String _BASE_PATH =
		"/o/adaptive-media/images/configuration";

	private static final Function<WebTarget, WebTarget> _NO_PATH = t -> t;

	private static final String _testAuth =
		"Basic " + Base64.encodeBase64("test@liferay.com:test".getBytes());
	private static final Map<String, JsonObject> _testConfigList =
		new HashMap<>();

	static {
		for (int i = 0; i < 10; i++) {
			JsonObject jsonObject = new JsonObject();

			String uuid = UUID.randomUUID().toString();

			jsonObject.addProperty("id", uuid);

			jsonObject.addProperty("height", _getRandomLong());

			jsonObject.addProperty("name", uuid + " Size");

			jsonObject.addProperty("width", _getRandomLong());

			_testConfigList.put(uuid, jsonObject);
		}
	}

	@ArquillianResource
	private URL _context;

}