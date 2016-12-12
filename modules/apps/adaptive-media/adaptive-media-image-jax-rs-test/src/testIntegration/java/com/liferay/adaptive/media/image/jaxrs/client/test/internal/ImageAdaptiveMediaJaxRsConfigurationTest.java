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
		JsonObject testConfig = _testConfigList.get("0");

		JsonObject json = _addConfiguration(testConfig);

		_assertEquals(testConfig, json);
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

		Assert.assertEquals(jsonArray.size(), configurations.size());

		jsonArray.forEach(configuration -> {
			JsonObject config = configurations.get(
				configuration.getAsJsonObject().get("id").getAsString());

			_assertEquals(config, configuration.getAsJsonObject());
		});
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
			t -> t.path(
				"/{id}").resolveTemplate("id", json.get("id").getAsString()));

		return builder.header("Authorization", _testAuth).put(
			Entity.json(json), JsonObject.class);
	}

	private Map<String, JsonObject> _addMultipleConfigurations() {
		_testConfigList.forEach((k, v) -> _addConfiguration(v));
		return _testConfigList;
	}

	private void _assertEquals(JsonElement json1, JsonElement json2) {
		Assert.assertEquals(json1.getAsString(), json2.getAsString());
	}

	private void _assertEquals(JsonObject json1, JsonObject json2) {
		Assert.assertEquals(json1.entrySet().size(), json2.entrySet().size());

		json1.entrySet().forEach(entry ->
			_assertEquals(json2.get(entry.getKey()), entry.getValue()));
	}

	private void _deleteAllConfigurationEntries() {
		JsonArray jsonArray = _getBaseRequest(_NO_PATH).get(JsonArray.class);

		jsonArray.forEach(e -> {
			String id = e.getAsJsonObject().get("id").getAsString();

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

			jsonObject.addProperty("id", i);
			jsonObject.addProperty("name", i + " Size");
			jsonObject.addProperty("height", _getRandomLong());
			jsonObject.addProperty("width", _getRandomLong());

			_testConfigList.put(String.valueOf(i), jsonObject);
		}
	}

	@ArquillianResource
	private URL _context;

}