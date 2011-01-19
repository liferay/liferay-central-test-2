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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.servlet.NullSession;

import edu.emory.mathcs.backport.java.util.Collections;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 */
public class SharedSessionWrapper implements HttpSession {

	public SharedSessionWrapper(
		HttpSession portalSession, HttpSession portletSession) {

		if (portalSession == null) {
			_portalSession = new NullSession();

			if (_log.isWarnEnabled()) {
				_log.warn("Wrapped portal session is null");
			}
		}

		_portalSession = portalSession;
		_portletSession = portletSession;
	}

	public Object getAttribute(String name) {
		return getSessionDelegate(name).getAttribute(name);
	}

	public Enumeration<String> getAttributeNames() {
		HttpSession session = getSessionDelegate();

		Enumeration<String> namesEnu = session.getAttributeNames();

		if (session == _portletSession) {
			List<String> namesList = Collections.list(namesEnu);

			Set<String> sharedSessionAttributes =
				_sharedSessionAttributes.keySet();

			for (String name : sharedSessionAttributes) {
				if (_portalSession.getAttribute(name) != null) {
					namesList.add(name);
				}
			}

			namesEnu = Collections.enumeration(namesList);
		}

		return namesEnu;
	}

	public long getCreationTime() {
		return getSessionDelegate().getCreationTime();
	}

	public String getId() {
		return getSessionDelegate().getId();
	}

	public long getLastAccessedTime() {
		return getSessionDelegate().getLastAccessedTime();
	}

	public int getMaxInactiveInterval() {
		return getSessionDelegate().getMaxInactiveInterval();
	}

	public ServletContext getServletContext() {
		return getSessionDelegate().getServletContext();
	}

	/**
	 * @deprecated
	 */
	public javax.servlet.http.HttpSessionContext getSessionContext() {
		return getSessionDelegate().getSessionContext();
	}

	public Object getValue(String name) {
		return getAttribute(name);
	}

	public String[] getValueNames() {
		List<String> names = ListUtil.fromEnumeration(getAttributeNames());

		return names.toArray(new String[names.size()]);
	}

	public void invalidate() {
		getSessionDelegate().invalidate();
	}

	public boolean isNew() {
		return getSessionDelegate().isNew();
	}

	public void putValue(String name, Object value) {
		setAttribute(name, value);
	}

	public void removeAttribute(String name) {
		getSessionDelegate(name).removeAttribute(name);
	}

	public void removeValue(String name) {
		removeAttribute(name);
	}

	public void setAttribute(String name, Object value) {
		getSessionDelegate(name).setAttribute(name, value);
	}

	public void setMaxInactiveInterval(int maxInactiveInterval) {
		getSessionDelegate().setMaxInactiveInterval(maxInactiveInterval);
	}

	protected HttpSession getSessionDelegate() {
		if (_portletSession != null) {
			return _portletSession;
		}
		else {
			return _portalSession;
		}
	}

	protected HttpSession getSessionDelegate(String name) {
		if (_portletSession == null) {
			return _portalSession;
		}

		if (_sharedSessionAttributesExcludes.containsKey(name)) {
			return _portletSession;
		}
		else if (_sharedSessionAttributes.containsKey(name)) {
			return _portalSession;
		}
		else {
			return _portletSession;
		}

	}

	private static Log _log = LogFactoryUtil.getLog(SharedSessionWrapper.class);

	private static Map<String, String> _sharedSessionAttributes;
	private static Map<String, String> _sharedSessionAttributesExcludes;

	private HttpSession _portalSession;
	private HttpSession _portletSession;

	static {
		_sharedSessionAttributes = new HashMap<String, String>();

		for (String name : PropsValues.SHARED_SESSION_ATTRIBUTES) {
			_sharedSessionAttributes.put(name, name);
		}

		_sharedSessionAttributesExcludes = new HashMap<String, String>();

		for (String name : PropsValues.SHARED_SESSION_ATTRIBUTES_EXCLUDES) {
			_sharedSessionAttributesExcludes.put(name, name);
		}
	}

}