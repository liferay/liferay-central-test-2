package com.liferay.adaptive.media.image.jaxrs.client.test.internal;

import aQute.lib.base64.Base64;

import com.google.gson.JsonArray;

import com.liferay.adaptive.media.image.jaxrs.client.test.internal.provider.GsonProvider;

import java.net.URISyntaxException;
import java.net.URL;

import java.util.function.Function;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.Before;
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

	@ArquillianResource
	private URL _context;

}