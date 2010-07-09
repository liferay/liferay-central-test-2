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

import com.liferay.portal.kernel.util.ServerDetector;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 */
public class SharedSessionServletRequest extends HttpServletRequestWrapper {

	public SharedSessionServletRequest(
		HttpServletRequest request, Map<String, Object> sharedSessionAttributes,
		boolean shared) {

		super(request);

		_sharedSessionAttributes = sharedSessionAttributes;

		_session = getSharedSessionWrapper(request.getSession());
		_shared = shared;
	}

	public HttpSession getSession() {
		if (_shared) {
			return _session;
		}
		else {
			return getSharedSessionWrapper(super.getSession());
		}
	}

	public HttpSession getSession(boolean create) {
		if (_shared) {
			return _session;
		}
		else {
			return getSharedSessionWrapper(super.getSession(create));
		}
	}

	public HttpSession getSharedSession() {
		return _session;
	}

	protected HttpSession getSharedSessionWrapper(HttpSession session) {
		if (ServerDetector.isJetty()) {
			return new JettySharedSessionWrapper(
				session, _sharedSessionAttributes);
		}
		else {
			return new SharedSessionWrapper(session, _sharedSessionAttributes);
		}
	}

	private HttpSession _session;
	private Map<String, Object> _sharedSessionAttributes;
	private boolean _shared;

}