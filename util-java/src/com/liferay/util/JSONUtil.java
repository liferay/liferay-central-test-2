/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.util;

import com.liferay.portal.kernel.util.StringPool;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.jabsorb.JSONSerializer;
import org.jabsorb.serializer.MarshallException;
import org.jabsorb.serializer.UnmarshallException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * <a href="JSONUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JSONUtil {

	public static Object deserialize(String json) {
		return _instance._deserialize(json);
	}

	public static void put(JSONObject jsonObj, String key, boolean value) {
		try {
			jsonObj.put(key, value);
		}
		catch (JSONException jsone) {
			if (_log.isWarnEnabled()) {
				_log.warn(jsone, jsone);
			}
		}
	}

	public static void put(JSONObject jsonObj, String key, double value) {
		try {
			jsonObj.put(key, value);
		}
		catch (JSONException jsone) {
			if (_log.isWarnEnabled()) {
				_log.warn(jsone, jsone);
			}
		}
	}

	public static void put(JSONObject jsonObj, String key, int value) {
		try {
			jsonObj.put(key, value);
		}
		catch (JSONException jsone) {
			if (_log.isWarnEnabled()) {
				_log.warn(jsone, jsone);
			}
		}
	}

	public static void put(JSONObject jsonObj, String key, long value) {
		try {
			jsonObj.put(key, value);
		}
		catch (JSONException jsone) {
			if (_log.isWarnEnabled()) {
				_log.warn(jsone, jsone);
			}
		}
	}

	public static void put(JSONObject jsonObj, String key, short value) {
		try {
			jsonObj.put(key, value);
		}
		catch (JSONException jsone) {
			if (_log.isWarnEnabled()) {
				_log.warn(jsone, jsone);
			}
		}
	}

	public static void put(JSONObject jsonObj, String key, Date value) {
		if (value == null) {
			try {
				jsonObj.put(key, StringPool.BLANK);
			}
			catch (JSONException jsone) {
				if (_log.isWarnEnabled()) {
					_log.warn(jsone, jsone);
				}
			}
		}
		else {

			// Unix timestamp

			long time = value.getTime() / Time.SECOND;

			put(jsonObj, key, String.valueOf(time));
		}
	}

	public static void put(JSONObject jsonObj, String key, Object value) {
		if (value == null) {
			value = StringPool.BLANK;
		}

		try {
			jsonObj.put(key, value.toString());
		}
		catch (JSONException jsone) {
			if (_log.isWarnEnabled()) {
				_log.warn(jsone, jsone);
			}
		}
	}

	public static String serialize(Object obj) {
		return _instance._serialize(obj);
	}

	private JSONUtil() {
		_serializer = new JSONSerializer();

		 try {
			 _serializer.registerDefaultSerializers();
		 }
		 catch (Exception e) {
			 _log.error(e, e);
		 }
	}

	public Object _deserialize(String json) {
		try {
			return _serializer.fromJSON(json);
		}
		catch (UnmarshallException ue) {
			 _log.error(ue, ue);

			 return null;
		}
	}

	private String _serialize(Object obj) {
		try {
			return _serializer.toJSON(obj).toString();
		}
		catch (MarshallException me) {
			_log.error(me, me);

			 return null;
		}
	}

	private static Log _log = LogFactory.getLog(JSONUtil.class);

	private static JSONUtil _instance = new JSONUtil();

	private JSONSerializer _serializer;

}