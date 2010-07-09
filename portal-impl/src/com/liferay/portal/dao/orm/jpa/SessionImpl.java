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

package com.liferay.portal.dao.orm.jpa;

import com.liferay.portal.kernel.dao.orm.LockMode;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;

import java.io.Serializable;

import java.sql.Connection;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;

/**
 * @author Prashant Dighe
 * @author Brian Wing Shun Chan
 */
public class SessionImpl implements Session {

	public void clear() throws ORMException {
		try {
			_entityManager.clear();
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public Connection close() throws ORMException {
		return null;
	}

	public boolean contains(Object object) throws ORMException {
		try {
			return _entityManager.contains(object);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public Query createQuery(String queryString) throws ORMException {
		return new QueryImpl(this, queryString);
	}

	public SQLQuery createSQLQuery(String queryString) throws ORMException {
		return new SQLQueryImpl(this, queryString);
	}

	public void delete(Object object) throws ORMException {
		try {
			_entityManager.remove(_entityManager.merge(object));
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public void evict(Object object) throws ORMException {
	}

	public void flush() throws ORMException {
		try {
			_entityManager.flush();
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public Object get(Class<?> clazz, Serializable id) throws ORMException {
		try {
			return _entityManager.find(clazz, id);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public Object get(Class<?> clazz, Serializable id, LockMode lockMode)
		throws ORMException {

		try {
			Object entity = _entityManager.find(clazz, id);

			javax.persistence.LockModeType lockModeType =
				LockModeTranslator.translate(lockMode);

			if (lockModeType != null) {
				_entityManager.lock(entity, lockModeType);
			}

			return entity;
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public Object getWrappedSession() throws ORMException {
		return _entityManager;
	}

	public Object load(Class<?> clazz, Serializable id) throws ORMException {
		try {
			return _entityManager.getReference(clazz, id);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public Object merge(Object object) throws ORMException {
		try {
			return _entityManager.merge(object);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public Serializable save(Object object) throws ORMException {
		try {
			_entityManager.persist(object);

			// Hibernate returns generated idenitfier which is not used
			// anywhere

			return null;
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public void saveOrUpdate(Object object) throws ORMException {
		try {
			_entityManager.merge(object);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	protected int executeUpdate(
		String queryString, Map<Integer, Object> parameterMap,
		int firstResult, int maxResults, FlushModeType flushMode,
		boolean sqlQuery, Class<?> entityClass) {

		javax.persistence.Query query = _getExecutableQuery(
			queryString, parameterMap, firstResult, maxResults,
			flushMode, sqlQuery, entityClass);

		return query.executeUpdate();
	}

	protected List<?> list(
		String queryString, Map<Integer, Object> parameterMap, int firstResult,
		int maxResults, FlushModeType flushMode, boolean sqlQuery,
		Class<?> entityClass) {

		javax.persistence.Query query = _getExecutableQuery(
			queryString, parameterMap, firstResult, maxResults,
			flushMode, sqlQuery, entityClass);

		return query.getResultList();
	}

	protected Object uniqueResult(
		String queryString, Map<Integer, Object> parameterMap, int firstResult,
		int maxResults, FlushModeType flushMode, boolean sqlQuery,
		Class<?> entityClass) {

		javax.persistence.Query query = _getExecutableQuery(
			queryString, parameterMap, firstResult, maxResults,
			flushMode, sqlQuery, entityClass);

		return query.getSingleResult();

	}

	private javax.persistence.Query _getExecutableQuery(
		String queryString, Map<Integer, Object> parameterMap,
		int firstResult, int maxResults, FlushModeType flushMode,
		boolean sqlQuery, Class<?> entityClass) {

		javax.persistence.Query query = null;

		if (sqlQuery) {
			if (entityClass != null) {
				query = _entityManager.createNativeQuery(
					queryString, entityClass);
			}
			else {
				query = _entityManager.createNativeQuery(queryString);
			}
		}
		else {
			query = _entityManager.createQuery(queryString);
		}

		_setParameters(query, parameterMap);

		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}

		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}

		if (flushMode != null) {
			query.setFlushMode(flushMode);
		}

		return query;
	}

	private void _setParameters(
		javax.persistence.Query query, Map<Integer, Object> parameterMap) {

		for (Map.Entry<Integer, Object> entry : parameterMap.entrySet()) {
			int position = entry.getKey() + 1;

			Object value = entry.getValue();

			if (value instanceof Date) {
				query.setParameter(
					position, (Date)value, TemporalType.TIMESTAMP);
			}
			else {
				query.setParameter(position, value);
			}
		}
	}

	@PersistenceContext
	protected EntityManager _entityManager;

}