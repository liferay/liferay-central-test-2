package com.liferay.adaptive.media.image.jaxrs.client.test.internal;

import aQute.lib.base64.Base64;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.liferay.adaptive.media.image.jaxrs.client.test.internal.provider.GsonProvider;

import java.net.URL;

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
		JsonObject json = _getBaseRequest(
			t -> t.path("/{uuid}").resolveTemplate("uuid", "small")).header(
				"Authorization", _testAuth).put(Entity.json(_testConfig),
				JsonObject.class);

		Assert.assertEquals("small", json.get("id").getAsString());
		Assert.assertEquals(
			json.get("width").getAsLong(),
			_testConfig.get("width").getAsLong());
		Assert.assertEquals(
			json.get("height").getAsLong(),
			_testConfig.get("height").getAsLong());
		Assert.assertEquals(
			json.get("name").getAsString(),
			_testConfig.get("name").getAsString());
	}

	@Test
	public void testGetAllConfigurationsAreEmpty() {
		JsonArray jsonArray = _getBaseRequest(_NO_PATH).get(JsonArray.class);

		Assert.assertEquals(0, jsonArray.size());
	}

	@Test
	public void testGetConfigurationWithUUIDReturnNotFound() {
		Response response = _getBaseRequest(
			t -> t.path("/{id}").resolveTemplate("id", "small")).get();

		Assert.assertEquals(404, response.getStatus());
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
	private static final JsonObject _testConfig = new JsonObject();

	static {
		_testConfig.addProperty("name", "Small Sizes");
		_testConfig.addProperty("height", 650);
		_testConfig.addProperty("width", 500);
	}

	@ArquillianResource
	private URL _context;

}