/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.jbi;

import com.liferay.portal.model.User;
import com.liferay.util.Http;
import com.liferay.util.StringPool;
import com.liferay.util.Validator;

import java.io.IOException;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="JBIRequestURL.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public abstract class JBIRequestURL {

	public JBIRequestURL(User user) {
		_user = user;
		_params = new LinkedHashMap();

		_params.put("userId", _user.getUserId());
		_params.put("timeZoneId", _user.getTimeZone().getID());
	}

	public void addParameterMap(Map parameterMap) {
		Iterator itr = parameterMap.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry entry = (Map.Entry)itr.next();

			String key = (String)entry.getKey();
			String[] value = (String[])entry.getValue();

			if ((Validator.isNotNull(key)) && (value != null) &&
				(value.length > 0) && (Validator.isNotNull(value[0]))) {

				_params.put(key, value[0]);
			}
		}
	}

	public void setParameter(String name, boolean value) {
		setParameter(name, String.valueOf(value));
	}

	public void setParameter(String name, double value) {
		setParameter(name, String.valueOf(value));
	}

	public void setParameter(String name, float value) {
		setParameter(name, String.valueOf(value));
	}

	public void setParameter(String name, int value) {
		setParameter(name, String.valueOf(value));
	}

	public void setParameter(String name, long value) {
		setParameter(name, String.valueOf(value));
	}

	public void setParameter(String name, short value) {
		setParameter(name, String.valueOf(value));
	}

	public void setParameter(String name, String value) {
		_params.put(name, value);

		_toString = null;
	}

	public String getContent() throws IOException {
		return Http.URLtoString(toString(), true);
	}

	public String toString() {
		if (_toString != null) {
			return _toString;
		}

		return generateToString();
	}

	protected abstract String getURL();

	protected String generateToString() {
		StringBuffer sb = new StringBuffer();

		String url = getURL();

		sb.append(url);

		if (url.indexOf(StringPool.QUESTION) == -1) {
			sb.append(StringPool.QUESTION);
		}

		Iterator itr = _params.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry entry = (Map.Entry)itr.next();

			String key = (String)entry.getKey();
			String value = (String)entry.getValue();

			sb.append(StringPool.AMPERSAND);
			sb.append(key);
			sb.append(StringPool.EQUAL);
			sb.append(Http.encodeURL(value));
		}

		_toString = sb.toString();

		if (_log.isDebugEnabled()) {
			_log.debug(_toString);
		}

		return _toString;
	}

	private static Log _log = LogFactory.getLog(JBIRequestURL.class);

	private User _user;
	private Map _params;
	private String _toString;

}