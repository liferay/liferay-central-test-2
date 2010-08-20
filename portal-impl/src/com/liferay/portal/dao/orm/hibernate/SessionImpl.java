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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.orm.LockMode;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;

import java.io.Serializable;

import java.sql.Connection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 */
public class SessionImpl implements Session {

	public SessionImpl(org.hibernate.Session session) {
		_session = session;
	}

	public void clear() throws ORMException {
		try {
			_session.clear();
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public Connection close() throws ORMException {
		try {
			return _session.close();
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public boolean contains(Object object) throws ORMException {
		try {
			return _session.contains(object);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public Query createQuery(String queryString) throws ORMException {
		try {
			queryString = SQLTransformer.transform(queryString);

			queryString = _jpqlTohql(queryString);

			return new QueryImpl(_session.createQuery(queryString));
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public SQLQuery createSQLQuery(String queryString)
		throws ORMException {

		try {
			queryString = SQLTransformer.transform(queryString);

			queryString = _jpqlTohql(queryString);

			return new SQLQueryImpl(_session.createSQLQuery(queryString));
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public void delete(Object object) throws ORMException {
		try {
			_session.delete(object);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public void evict(Object object) throws ORMException {
		try {
			_session.evict(object);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public void flush() throws ORMException {
		try {
			_session.flush();
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public Object get(Class<?> clazz, Serializable id) throws ORMException {
		try {
			return _session.get(clazz, id);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	/**
	 * @deprecated
	 */
	public Object get(Class<?> clazz, Serializable id, LockMode lockMode)
		throws ORMException {

		try {
			return _session.get(
				clazz, id, LockModeTranslator.translate(lockMode));
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public Object getWrappedSession() {
		return _session;
	}

	public Object load(Class<?> clazz, Serializable id) throws ORMException {
		try {
			return _session.load(clazz, id);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public Object merge(Object object) throws ORMException {
		try {
			return _session.merge(object);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public Serializable save(Object object) throws ORMException {
		try {
			return _session.save(object);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public void saveOrUpdate(Object object) throws ORMException {
		try {
			_session.saveOrUpdate(object);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}


	private String _jpqlTohql(String queryString) {

		Matcher countMatcher = _JPQL_COUNT.matcher(queryString);

		if (countMatcher.find()) {
			String countExpression = countMatcher.group(1);
			String entityAlias = countMatcher.group(3);

			if (entityAlias.equals(countExpression)) {
				queryString = countMatcher.replaceFirst(_HQL_COUNT);
			}
		}

		return queryString;
	}

	private org.hibernate.Session _session;

	private static final Pattern _JPQL_COUNT = Pattern.compile("SELECT COUNT\\((\\S+)\\) FROM (\\S+) (\\S+)");
	private static final String _HQL_COUNT = "SELECT COUNT(*) FROM $2 $3";

}