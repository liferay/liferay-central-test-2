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

package com.liferay.util.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 */
public class SharedSessionWrapper implements HttpSession {

	public SharedSessionWrapper(HttpSession session) {
		this(session, new ConcurrentHashMap<String, Object>());
	}

	public SharedSessionWrapper(
		HttpSession session, Map<String, Object> sharedAttributes) {

		if (session == null) {
			_session = new NullSession();

			if (_log.isWarnEnabled()) {
				_log.warn("Wrapped session is null");
			}
		}
		else {
			_session = session;
		}

		_sharedAttributes = sharedAttributes;
	}

	public Object getAttribute(String name) {
		Object value = _session.getAttribute(name);

		if (value == null) {
			value = _sharedAttributes.get(name);
		}

		return value;
	}

	public Enumeration<String> getAttributeNames() {
		if (_sharedAttributes.size() > 0) {

			Enumeration<String> sessionAttributeNames =
				_session.getAttributeNames();

			List<String> names = null;

			synchronized (sessionAttributeNames) {
				names = ListUtil.fromEnumeration(sessionAttributeNames);
			}

			names.addAll(_sharedAttributes.keySet());

			return Collections.enumeration(names);
		}
		else {
			return _session.getAttributeNames();
		}
	}

	public long getCreationTime() {
		return _session.getCreationTime();
	}

	public String getId() {
		return _session.getId();
	}

	public long getLastAccessedTime() {
		return _session.getLastAccessedTime();
	}

	public int getMaxInactiveInterval() {
		return _session.getMaxInactiveInterval();
	}

	public ServletContext getServletContext() {
		return _session.getServletContext();
	}

	/**
	 * @deprecated
	 */
	public javax.servlet.http.HttpSessionContext getSessionContext() {
		return _session.getSessionContext();
	}

	public Object getValue(String name) {
		return getAttribute(name);
	}

	public String[] getValueNames() {
		List<String> names = ListUtil.fromEnumeration(getAttributeNames());

		return names.toArray(new String[names.size()]);
	}

	public void invalidate() {
		_session.invalidate();
	}

	public boolean isNew() {
		return _session.isNew();
	}

	public void putValue(String name, Object value) {
		setAttribute(name, value);
	}

	public void removeAttribute(String name) {
		_session.removeAttribute(name);
	}

	public void removeValue(String name) {
		removeAttribute(name);
	}

	public void setAttribute(String name, Object value) {
		_session.setAttribute(name, value);
	}

	public void setMaxInactiveInterval(int maxInactiveInterval) {
		_session.setMaxInactiveInterval(maxInactiveInterval);
	}

	private static Log _log = LogFactoryUtil.getLog(SharedSessionWrapper.class);

	private HttpSession _session;
	private Map<String, Object> _sharedAttributes;

}