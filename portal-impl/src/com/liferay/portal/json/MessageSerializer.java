/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.json;

import com.liferay.portal.kernel.messaging.Message;

import java.lang.reflect.Field;

import java.util.Iterator;
import java.util.Map;

import org.jabsorb.serializer.AbstractSerializer;
import org.jabsorb.serializer.MarshallException;
import org.jabsorb.serializer.ObjectMatch;
import org.jabsorb.serializer.SerializerState;
import org.jabsorb.serializer.UnmarshallException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Tina Tian
 */
public class MessageSerializer extends AbstractSerializer {

	@SuppressWarnings("rawtypes")
	public boolean canSerialize(Class javaClass, Class jsonClass) {
		if (super.canSerialize(javaClass, jsonClass)) {
			return true;
		}
		else if (Message.class.isAssignableFrom(javaClass) &&
			(jsonClass == null || jsonClass == JSONObject.class)) {

			return true;
		}

		return false;
	}

	@SuppressWarnings("rawtypes")
	public Class[] getJSONClasses() {
		return _JSONClasses;
	}

	@SuppressWarnings("rawtypes")
	public Class[] getSerializableClasses() {
		return _serializableClass;
	}

	public Object marshall(
			SerializerState serializerState, Object parent, Object object)
		throws MarshallException {

		JSONObject jsonObject = new JSONObject();

		try {
			Message message = (Message)object;

			jsonObject.put("javaClass", object.getClass().getName());

			jsonObject.put("_destinationName", message.getDestinationName());
			jsonObject.put("_payload", message.getPayload());
			jsonObject.put("_response", message.getResponse());
			jsonObject.put(
				"_responseDestinationName",
				message.getResponseDestinationName());
			jsonObject.put("_responseId", message.getResponseId());

			_valuesField.setAccessible(true);
			@SuppressWarnings("rawtypes")
			Map<String, Object> values = (Map<String, Object>)_valuesField.get(
				message);

			jsonObject.put("_values", values);

		}
		catch (Exception e) {
			throw new MarshallException(
				"Can not marshall object: " + object, e);
		}

		return jsonObject;
	}

	@SuppressWarnings("rawtypes")
	public ObjectMatch tryUnmarshall(
			SerializerState serializerState, Class type, Object object)
		throws UnmarshallException {

		JSONObject jsonObject = (JSONObject)object;

		String javaClassName;

		try {
			javaClassName = jsonObject.getString("javaClass");
		}
		catch (JSONException e) {
			throw new UnmarshallException("Could not read javaClass", e);
		}

		if (javaClassName == null) {
			throw new UnmarshallException("No type hint");
		}

		if (javaClassName.equals(Message.class.getName())) {
			return new ObjectMatch(-3);
		}

		return new ObjectMatch(-1);
	}

	@SuppressWarnings("rawtypes")
	public Object unmarshall(
			SerializerState serializerState, Class type, Object object)
		throws UnmarshallException {

		JSONObject jsonObject = (JSONObject)object;

		String javaClassName;

		try {
			javaClassName = jsonObject.getString("javaClass");
		}
		catch (JSONException e) {
			throw new UnmarshallException("Could not read javaClass", e);
		}

		if (javaClassName == null) {
			throw new UnmarshallException("No type hint");
		}

		Message message;

		if (javaClassName.equals(Message.class.getName())) {
			message = new Message();
		}
		else {
			throw new UnmarshallException("Not a Message");
		}

		serializerState.setSerialized(object, message);

		Iterator<?> iterator = jsonObject.keys();

		String key = null;

		try
		{
			while (iterator.hasNext())
			{
				key = (String)iterator.next();

				if (key.equals("_destinationName")) {
					message.setDestinationName((String)jsonObject.get(key));
				}
				else if (key.equals("_payload")) {
					message.setPayload(jsonObject.get(key));
				}
				else if (key.equals("_response")) {
					message.setResponse(jsonObject.get(key));
				}
				else if (key.equals("_responseDestinationName")) {
					message.setResponseDestinationName(
						(String)jsonObject.get(key));
				}
				else if (key.equals("_responseId")) {
					message.setResponseId((String)jsonObject.get(key));
				}
				else if (key.equals("_values")) {
					JSONObject jsonValues = (JSONObject)jsonObject.get(key);

					Iterator<?> valueIterator = jsonValues.keys();

					String valueKey = null;

					while (valueIterator.hasNext()) {
						valueKey = (String)valueIterator.next();

						message.put(valueKey, jsonValues.get(valueKey));
					}
				}
			}
		}
		catch (JSONException e) {
		  throw new UnmarshallException(
			  "Can not unmarshall object : " + object + e.getMessage(), e);
		}

		return message;
	}

	private static Class<?>[] _JSONClasses = new Class<?>[]{JSONObject.class };
	private static Class<?>[] _serializableClass =
		new Class<?>[]{Message.class};
	private static Field _valuesField;

	static {
		try {
			_valuesField = Message.class.getDeclaredField("_values");
		}
		catch (NoSuchFieldException nsfe) {
			throw new ExceptionInInitializerError(nsfe);
		}
	}

}