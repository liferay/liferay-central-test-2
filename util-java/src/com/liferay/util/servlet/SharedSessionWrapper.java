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

package com.liferay.util.servlet;

import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="SharedSessionWrapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SharedSessionWrapper implements HttpSession {

	public SharedSessionWrapper(HttpSession ses) {
		this(ses, new ConcurrentHashMap<String, Object>());
	}

	public SharedSessionWrapper(
		HttpSession ses, Map<String, Object> sharedAttributes) {

		if (ses == null) {
			_ses = new NullSession();

			if (_log.isWarnEnabled()) {
				_log.warn("Wrapped session is null");
			}
		}
		else {
			_ses = ses;
		}

		_sharedAttributes = sharedAttributes;
	}

	public Object getAttribute(String name) {
		Object value = _ses.getAttribute(name);

		if (value == null) {
			value = _sharedAttributes.get(name);
		}

		return value;
	}

	public Enumeration<String> getAttributeNames() {
		if (_sharedAttributes.size() > 0) {
			List<String> names = ListUtil.fromEnumeration(
				_ses.getAttributeNames());

			for (String name : _sharedAttributes.keySet()) {
				names.add(name);
			}

			return Collections.enumeration(names);
		}
		else {
			return _ses.getAttributeNames();
		}
	}

	public long getCreationTime() {
		return _ses.getCreationTime();
	}

	public String getId() {
		return _ses.getId();
	}

	public long getLastAccessedTime() {
		return _ses.getLastAccessedTime();
	}

	public int getMaxInactiveInterval() {
		return _ses.getMaxInactiveInterval();
	}

	public ServletContext getServletContext() {
		return _ses.getServletContext();
	}

	/**
	 * @deprecated
	 */
	public javax.servlet.http.HttpSessionContext getSessionContext() {
		return _ses.getSessionContext();
	}

	public Object getValue(String name) {
		return getAttribute(name);
	}

	public String[] getValueNames() {
		List<String> names = ListUtil.fromEnumeration(getAttributeNames());

		return names.toArray(new String[names.size()]);
	}

	public void invalidate() {
		_ses.invalidate();
	}

	public boolean isNew() {
		return _ses.isNew();
	}

	public void putValue(String name, Object value) {
		setAttribute(name, value);
	}

	public void removeAttribute(String name) {
		_ses.removeAttribute(name);
	}

	public void removeValue(String name) {
		removeAttribute(name);
	}

	public void setAttribute(String name, Object value) {
		_ses.setAttribute(name, value);
	}

	public void setMaxInactiveInterval(int maxInactiveInterval) {
		_ses.setMaxInactiveInterval(maxInactiveInterval);
	}

	private static Log _log = LogFactory.getLog(SharedSessionWrapper.class);

	private HttpSession _ses;
	private Map<String, Object> _sharedAttributes;

}