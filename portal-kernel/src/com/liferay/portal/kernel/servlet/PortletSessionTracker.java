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

package com.liferay.portal.kernel.servlet;

import java.io.Serializable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 * <a href="PortletSessionTracker.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * See http://issues.liferay.com/browse/LEP-1466.
 * </p>
 *
 * @author Rudy Hilado
 */
public class PortletSessionTracker
	implements HttpSessionBindingListener, Serializable {

	public static void add(HttpSession session) {
		_instance._add(session);
	}

	public static HttpSessionBindingListener getInstance() {
		return _instance;
	}

	public static void invalidate(String sessionId) {
		_instance._invalidate(sessionId);
	}

	public void valueBound(HttpSessionBindingEvent event) {
	}

	public void valueUnbound(HttpSessionBindingEvent event) {
		invalidate(event.getSession().getId());
	}

	private PortletSessionTracker() {
		_sessions = new HashMap<String, Set<HttpSession>>();
	}

	private void _add(HttpSession session) {
		String sessionId = session.getId();

		synchronized (_sessions) {
			Set<HttpSession> portletSessions = _sessions.get(sessionId);

			if (portletSessions == null) {
				portletSessions = new HashSet<HttpSession>();

				_sessions.put(sessionId, portletSessions);
			}

			portletSessions.add(session);
		}
	}

	private void _invalidate(String sessionId) {
		Set<HttpSession> sessionsToInvalidate = new HashSet<HttpSession>();

		synchronized (_sessions) {
			Set<HttpSession> portletSessions = _sessions.get(sessionId);

			if (portletSessions != null) {
				Iterator<HttpSession> itr = portletSessions.iterator();

				while (itr.hasNext()) {
					sessionsToInvalidate.add(itr.next());
				}
			}

			_sessions.remove(sessionId);
		}

		Iterator<HttpSession> itr = sessionsToInvalidate.iterator();

		while (itr.hasNext()) {
			HttpSession session = itr.next();

			try {
				session.invalidate();
			}
			catch (Exception e) {
			}
		}
	}

	private static PortletSessionTracker _instance =
		new PortletSessionTracker();

	private transient Map<String, Set<HttpSession>> _sessions;

}