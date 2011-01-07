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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONTransformer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.jabsorb.JSONSerializer;
import org.jabsorb.serializer.MarshallException;

/**
 * @author Brian Wing Shun Chan
 */
public class JSONFactoryImpl implements JSONFactory {

	public JSONFactoryImpl() {
		_flexjsonDeserializer = new flexjson.JSONDeserializer<Object>();
		_flexjsonSerializer = new flexjson.JSONSerializer();

		_serializer = new JSONSerializer();

		 try {
			 _serializer.registerDefaultSerializers();
			 _serializer.registerSerializer(new MessageSerializer());
		 }
		 catch (Exception e) {
			 _log.error(e, e);
		 }
	}

	public JSONArray createJSONArray() {
		return new JSONArrayImpl();
	}

	public JSONArray createJSONArray(String json) throws JSONException {
		return new JSONArrayImpl(json);
	}

	public JSONObject createJSONObject() {
		return new JSONObjectImpl();
	}

	public JSONObject createJSONObject(String json) throws JSONException {
		return new JSONObjectImpl(json);
	}

	public Object deserialize(JSONObject jsonObj) {
		return deserialize(jsonObj.toString());
	}

	public Object deserialize(String json) {
		try {
			return _serializer.fromJSON(json);
		}
		catch (Exception e) {
			 _log.error(e, e);

			throw new IllegalStateException("Unable to deserialize oject", e);
		}
	}

	public Object looseDeserialize(String json) {
		try {
			return _flexjsonDeserializer.deserialize(json);
		}
		catch (Exception e) {
			 _log.error(e, e);

			throw new IllegalStateException("Unable to deserialize oject", e);
		}
	}

	public String looseSerialize(Object object) {
		return looseSerialize(object, null, null);
	}

	public String looseSerialize(
		Object object, JSONTransformer jsonTransformer, Class<?> clazz) {

		if ((jsonTransformer != null) && (clazz != null)) {
			_flexjsonSerializer.transform(
				new FlexjsonTransformer(jsonTransformer), clazz);
		}

		return _flexjsonSerializer.deepSerialize(object);
	}

	public String serialize(Object object) {
		try {
			return _serializer.toJSON(object);
		}
		catch (MarshallException me) {
			_log.error(me, me);

			throw new IllegalStateException("Unable to serialize oject", me);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(JSONFactoryImpl.class);

	private flexjson.JSONDeserializer<Object> _flexjsonDeserializer;
	private flexjson.JSONSerializer _flexjsonSerializer;
	private JSONSerializer _serializer;

}