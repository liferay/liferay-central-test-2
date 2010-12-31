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

package com.liferay.util.servlet;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.mortbay.jetty.servlet.AbstractSessionManager;

/**
 * @author Brian Wing Shun Chan
 */
public class JettySharedSessionWrapper
	extends SharedSessionWrapper implements AbstractSessionManager.SessionIf {

	public JettySharedSessionWrapper(HttpSession session) {
		super(session);

		_session = session;
	}

	public JettySharedSessionWrapper(
		HttpSession session, Map<String, Object> sharedAttributes) {

		super(session, sharedAttributes);

		_session = session;
	}

	public AbstractSessionManager.Session getSession() {
		return (AbstractSessionManager.Session)_session;
	}

	private HttpSession _session;

}