package com.liferay.adaptive.media.image.jaxrs.client.test.internal.provider;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

/**
 * @author Alejandro Hernández
 */
@Consumes(MediaType.APPLICATION_JSON)
@Provider
public class GsonProvider implements MessageBodyReader<Object> {

	@Override
	public boolean isReadable(
		Class<?> type, Type genericType, Annotation[] annotations,
		MediaType mediaType) {

		return true;
	}

	@Override
	public Object readFrom(
			Class<Object> type, Type genericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
			InputStream entityStream)
		throws IOException {

		InputStreamReader streamReader = new InputStreamReader(
			entityStream, "UTF-8");

		return new GsonBuilder().create().fromJson(streamReader, genericType);
	}

}