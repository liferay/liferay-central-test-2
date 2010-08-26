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

package com.liferay.portal.kernel.dao.orm;

import java.io.Serializable;

import java.sql.Connection;

/**
 * @author Shuyang Zhou
 */
public class ClassLoaderSessionWrapper implements Session {

	public ClassLoaderSessionWrapper(
		ClassLoader classLoader, Session wrappedSession) {
		_classLoader = classLoader;
		_wrappedSession = wrappedSession;
	}

	public void clear() throws ORMException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(_classLoader);
			}

			_wrappedSession.clear();
		}
		finally {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	public Connection close() throws ORMException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(_classLoader);
			}

			return _wrappedSession.close();
		}
		finally {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	public boolean contains(Object object) throws ORMException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(_classLoader);
			}

			return _wrappedSession.contains(object);
		}
		finally {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	public Query createQuery(String queryString) throws ORMException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(_classLoader);
			}

			return _wrappedSession.createQuery(queryString);
		}
		finally {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	public SQLQuery createSQLQuery(String queryString) throws ORMException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(_classLoader);
			}

			return _wrappedSession.createSQLQuery(queryString);
		}
		finally {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	public void delete(Object object) throws ORMException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(_classLoader);
			}

			_wrappedSession.delete(object);
		}
		finally {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	public void evict(Object object) throws ORMException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(_classLoader);
			}

			_wrappedSession.evict(object);
		}
		finally {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	public void flush() throws ORMException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(_classLoader);
			}

			_wrappedSession.flush();
		}
		finally {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	public Object get(Class<?> clazz, Serializable id) throws ORMException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(_classLoader);
			}

			return _wrappedSession.get(clazz, id);
		}
		finally {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	public Object get(Class<?> clazz, Serializable id, LockMode lockMode)
		throws ORMException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(_classLoader);
			}

			return _wrappedSession.get(clazz, id, lockMode);
		}
		finally {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	public Object getWrappedSession() throws ORMException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(_classLoader);
			}

			return _wrappedSession.getWrappedSession();
		}
		finally {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	public Object load(Class<?> clazz, Serializable id) throws ORMException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(_classLoader);
			}

			return _wrappedSession.load(clazz, id);
		}
		finally {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	public Object merge(Object object) throws ORMException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(_classLoader);
			}

			return _wrappedSession.merge(object);
		}
		finally {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	public Serializable save(Object object) throws ORMException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(_classLoader);
			}

			return _wrappedSession.save(object);
		}
		finally {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	public void saveOrUpdate(Object object) throws ORMException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(_classLoader);
			}

			_wrappedSession.saveOrUpdate(object);
		}
		finally {
			if (contextClassLoader != _classLoader) {

				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	private final ClassLoader _classLoader;
	private final Session _wrappedSession;

}