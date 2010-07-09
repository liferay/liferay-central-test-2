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

package com.liferay.portal.json;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.Writer;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class JSONObjectImpl implements JSONObject {

	public JSONObjectImpl() {
		_jsonObj = new org.json.JSONObject();
	}

	public JSONObjectImpl(JSONObject jsonObj, String[] names)
		throws JSONException {

		try {
			JSONObjectImpl jsonObjImpl = (JSONObjectImpl)jsonObj;

			_jsonObj = new org.json.JSONObject(
				jsonObjImpl.getJSONObject(), names);
		}
		catch (Exception e) {
			throw new JSONException(e);
		}
	}

	public JSONObjectImpl(Map<?, ?> map) {
		_jsonObj = new org.json.JSONObject(map);
	}

	public JSONObjectImpl(Object bean) {
		_jsonObj = new org.json.JSONObject(bean);
	}

	public JSONObjectImpl(Object obj, String[] names) {
		_jsonObj = new org.json.JSONObject(obj, names);
	}

	public JSONObjectImpl(String json) throws JSONException {
		try {
			_jsonObj = new org.json.JSONObject(json);
		}
		catch (Exception e) {
			throw new JSONException(e);
		}
	}

	public JSONObjectImpl(org.json.JSONObject jsonObj) {
		_jsonObj = jsonObj;
	}

	public boolean getBoolean(String key) {
		return _jsonObj.optBoolean(key);
	}

	public double getDouble(String key) {
		return _jsonObj.optDouble(key);
	}

	public int getInt(String key) {
		return _jsonObj.optInt(key);
	}

	public JSONArray getJSONArray(String key) {
		org.json.JSONArray jsonArray = _jsonObj.optJSONArray(key);

		if (jsonArray == null) {
			return null;
		}

		return new JSONArrayImpl(jsonArray);
	}

	public org.json.JSONObject getJSONObject() {
		return _jsonObj;
	}

	public JSONObject getJSONObject(String key) {
		org.json.JSONObject jsonObj = _jsonObj.optJSONObject(key);

		if (jsonObj == null) {
			return null;
		}

		return new JSONObjectImpl(jsonObj);
	}

	public long getLong(String key) {
		return _jsonObj.optLong(key);
	}

	public String getString(String key) {
		return _jsonObj.optString(key);
	}

	public boolean has(String key) {
		return _jsonObj.has(key);
	}

	public boolean isNull(String key) {
		return _jsonObj.isNull(key);
	}

	public Iterator<String> keys() {
		return _jsonObj.keys();
	}

	public int length() {
		return _jsonObj.length();
	}

	public JSONArray names() {
		return new JSONArrayImpl(_jsonObj.names());
	}

	public JSONObject put(String key, boolean value) {
		try {
			_jsonObj.put(key, value);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return this;
	}

	public JSONObject put(String key, double value) {
		try {
			_jsonObj.put(key, value);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return this;
	}

	public JSONObject put(String key, int value) {
		try {
			_jsonObj.put(key, value);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return this;
	}

	public JSONObject put(String key, long value) {
		try {
			_jsonObj.put(key, value);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return this;
	}

	public JSONObject put(String key, Date value) {
		try {
			_jsonObj.put(key, value);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return this;
	}

	public JSONObject put(String key, JSONArray value) {
		try {
			_jsonObj.put(key, ((JSONArrayImpl)value).getJSONArray());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return this;
	}

	public JSONObject put(String key, JSONObject value) {
		try {
			_jsonObj.put(key, ((JSONObjectImpl)value).getJSONObject());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return this;
	}

	public JSONObject put(String key, String value) {
		try {
			_jsonObj.put(key, value);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return this;
	}

	public Object remove(String key) {
		return _jsonObj.remove(key);
	}

	public String toString() {
		return _jsonObj.toString();
	}

	public String toString(int indentFactor) throws JSONException {
		try {
			return _jsonObj.toString(indentFactor);
		}
		catch (Exception e) {
			throw new JSONException(e);
		}
	}

	public Writer write(Writer writer) throws JSONException {
		try {
			return _jsonObj.write(writer);
		}
		catch (Exception e) {
			throw new JSONException(e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(JSONObjectImpl.class);

	private org.json.JSONObject _jsonObj;

}