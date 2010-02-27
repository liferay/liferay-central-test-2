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

package com.liferay.portal.spring.hibernate;

import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import java.lang.Object;
import java.lang.String;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.hibernate.classic.Session;

/**
 * <a href="SessionInvocationHandler.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * See http://issues.liferay.com/browse/LEP-2996.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class SessionInvocationHandler implements InvocationHandler {

	public SessionInvocationHandler(Session session) {
		_session = session;
	}

	public Session getSession() {
		return _session;
	}

	@SuppressWarnings("deprecation")
	public Object invoke(Object proxy, Method method, Object[] args)
		throws Throwable {

		String methodName = method.getName();

		if (methodName.equals(_CONNECTION)) {
			Thread currentThread = Thread.currentThread();

			ClassLoader contextClassLoader =
				currentThread.getContextClassLoader();

			try {
				ClassLoader portalClassLoader =
					PortalClassLoaderUtil.getClassLoader();

				currentThread.setContextClassLoader(portalClassLoader);

				return _session.connection();
			}
			finally {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
		else if (methodName.equals(_EQUALS)) {
			if (proxy == args[0]) {
				return Boolean.TRUE;
			}
			else {
				return Boolean.FALSE;
			}
		}
		else if (methodName.equals(_HASHCODE)) {
			return new Integer(hashCode());
		}

		try {
			return method.invoke(_session, args);
		}
		catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}
	}

	private static final String _CONNECTION = "connection";

	private static final String _EQUALS = "equals";

	private static final String _HASHCODE = "hashCode";

	private final Session _session;

}