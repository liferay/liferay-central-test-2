/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.util.json;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.jabsorb.JSONSerializer;
import org.jabsorb.serializer.MarshallException;
import org.jabsorb.serializer.UnmarshallException;

/**
 * @author Brian Wing Shun Chan
 */
public class JSONFactoryUtil {

	public static Object deserialize(JSONObject jsonObj) {
		return _instance._deserialize(jsonObj);
	}

	public static Object deserialize(String json) {
		return _instance._deserialize(json);
	}

	public static String serialize(Object obj) {
		return _instance._serialize(obj);
	}

	private JSONFactoryUtil() {
		_serializer = new JSONSerializer();

		 try {
			 _serializer.registerDefaultSerializers();
		 }
		 catch (Exception e) {
			 _log.error(e, e);
		 }
	}

	private Object _deserialize(JSONObject jsonObj) {
		return _deserialize(jsonObj.toString());
	}

	private Object _deserialize(String json) {
		try {
			return _serializer.fromJSON(json);
		}
		catch (UnmarshallException ue) {
			 _log.error(ue, ue);

			throw new IllegalStateException("Unable to deserialize oject", ue);
		}
	}

	private String _serialize(Object obj) {
		try {
			return _serializer.toJSON(obj);
		}
		catch (MarshallException me) {
			_log.error(me, me);

			throw new IllegalStateException("Unable to serialize oject", me);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(JSONFactoryUtil.class);

	private static JSONFactoryUtil _instance = new JSONFactoryUtil();

	private JSONSerializer _serializer;

}