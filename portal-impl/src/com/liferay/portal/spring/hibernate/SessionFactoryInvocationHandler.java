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

import java.lang.Object;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;

import org.springframework.orm.hibernate3.SessionFactoryUtils;

/**
 * <a href="SessionFactoryInvocationHandler.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * <p>
 * See http://issues.liferay.com/browse/LEP-2996.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class SessionFactoryInvocationHandler implements InvocationHandler {

	public SessionFactoryInvocationHandler(SessionFactory sessionFactory) {
		_sessionFactory = sessionFactory;
	}

	public Object invoke(Object proxy, Method method, Object[] args)
		throws Throwable {

		String methodName = method.getName();

		if (methodName.equals(_GET_CURRENT_SESSION)) {
			try {
				Session session = (Session)SessionFactoryUtils.doGetSession(
					(SessionFactory)proxy, false);

				return wrapSession(session);
			}
			catch (IllegalStateException ise) {
				throw new HibernateException(ise.getMessage());
			}
		}
		else if (methodName.equals(_OPEN_SESSION)) {
			Session session = (Session)method.invoke(_sessionFactory, args);

			return wrapSession(session);
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
			return method.invoke(_sessionFactory, args);
		}
		catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}
	}

	protected Session wrapSession(Session session) {
		if (Proxy.isProxyClass(session.getClass())) {
			return session;
		}
		else {
			return (Session)Proxy.newProxyInstance(
				Session.class.getClassLoader(), new Class[] {Session.class},
				new SessionInvocationHandler(session));
		}
	}

	private static final String _EQUALS = "equals";

	private static final String _GET_CURRENT_SESSION = "getCurrentSession";

	private static final String _HASHCODE = "hashCode";

	private static final String _OPEN_SESSION = "openSession";

	private final SessionFactory _sessionFactory;

}