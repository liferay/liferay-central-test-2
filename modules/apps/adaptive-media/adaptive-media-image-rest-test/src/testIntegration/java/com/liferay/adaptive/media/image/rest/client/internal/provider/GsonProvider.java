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

package com.liferay.adaptive.media.image.rest.client.internal.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

/**
 * @author Alejandro Hernández
 */
@Consumes(MediaType.APPLICATION_JSON)
@Provider
public class GsonProvider
	implements MessageBodyReader<Object>, MessageBodyWriter<Object> {

	public GsonProvider() {
		_gson = new GsonBuilder().create();
	}

	@Override
	public long getSize(
		Object o, Class<?> type, Type genericType, Annotation[] annotations,
		MediaType mediaType) {

		return -1;
	}

	@Override
	public boolean isReadable(
		Class<?> type, Type genericType, Annotation[] annotations,
		MediaType mediaType) {

		return true;
	}

	@Override
	public boolean isWriteable(
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

		return _gson.fromJson(streamReader, genericType);
	}

	@Override
	public void writeTo(
		Object o, Class<?> type, Type genericType, Annotation[] annotations,
		MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
		OutputStream entityStream) {

		PrintWriter printWriter = new PrintWriter(entityStream);

		String json = _gson.toJson(o);

		printWriter.write(json);

		printWriter.flush();
		printWriter.close();
	}

	private final Gson _gson;

}