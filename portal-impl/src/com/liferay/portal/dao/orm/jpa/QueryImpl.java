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

import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.orm.CacheMode;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.ScrollableResults;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnmodifiableList;

import java.io.Serializable;

import java.sql.Timestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.FlushModeType;

/**
 * @author Prashant Dighe
 * @author Brian Wing Shun Chan
 */
public class QueryImpl implements Query {

	public QueryImpl(SessionImpl sessionImpl, String queryString) {
		this.sessionImpl = sessionImpl;
		this.queryString = _hqlTojpql(SQLTransformer.transform(queryString));
	}

	public int executeUpdate() throws ORMException {
		try {
			return sessionImpl.executeUpdate(
				queryString, parameterMap, firstResult, maxResults,
				flushModeType, sqlQuery, entityClass);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public Iterator<?> iterate() throws ORMException {
		return iterate(true);
	}

	public Iterator<?> iterate(boolean unmodifiable) throws ORMException {
		try {
			return list(unmodifiable).iterator();
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public List<?> list() throws ORMException {
		return list(true);
	}

	public List<?> list(boolean unmodifiable) throws ORMException {
		try {
			List<?> list = sessionImpl.list(
				queryString, parameterMap, firstResult, maxResults,
				flushModeType, sqlQuery, entityClass);

			if (unmodifiable) {
				return new UnmodifiableList<Object>(list);
			}
			else {
				return ListUtil.copy(list);
			}
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public ScrollableResults scroll() throws ORMException {
		try {
			return new ScrollableResultsImpl(list());
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public Query setBoolean(int pos, boolean value) {
		parameterMap.put(pos, value);

		return this;
	}

	public Query setCacheable(boolean cacheable) {
		return this;
	}

	public Query setCacheMode(CacheMode cacheMode) {
		return this;
	}

	public Query setCacheRegion(String cacheRegion) {
		return this;
	}

	public Query setDouble(int pos, double value) {
		parameterMap.put(pos, Double.valueOf(value));

		return this;
	}

	public Query setFirstResult(int firstResult) {
		this.firstResult = firstResult;

		return this;
	}

	public Query setFloat(int pos, float value) {
		parameterMap.put(pos, Float.valueOf(value));

		return this;
	}

	public Query setFlushMode(FlushModeType flushModeType) {
		this.flushModeType = flushModeType;

		return this;
	}

	public Query setInteger(int pos, int value) {
		parameterMap.put(pos, Integer.valueOf(value));

		return this;
	}

	public Query setLong(int pos, long value) {
		parameterMap.put(pos, Long.valueOf(value));

		return this;
	}

	public Query setMaxResults(int maxResults) {
		this.maxResults = maxResults;

		return this;
	}

	public Query setSerializable(int pos, Serializable value) {
		parameterMap.put(pos, value);

		return this;
	}

	public Query setShort(int pos, short value) {
		parameterMap.put(pos, Short.valueOf(value));

		return this;
	}

	public Query setString(int pos, String value) {
		parameterMap.put(pos, value);

		return this;
	}

	public Query setTimestamp(int pos, Timestamp value) {
		Date date = null;

		if (value != null) {
			date = new Date(value.getTime());
		}

		parameterMap.put(pos, date);

		return this;
	}

	public Object uniqueResult() throws ORMException {
		try {
			return sessionImpl.uniqueResult(
				queryString, parameterMap, firstResult, maxResults,
				flushModeType, sqlQuery, entityClass);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	private String _hqlTojpql(String queryString) {
		queryString = _transformPositionalParams(queryString);

		queryString = queryString.replaceAll(_HQL_NOT_EQUALS, _JPQL_NOT_EQUALS);
		queryString = queryString.replaceAll(
			_HQL_COMPOSITE_ID_MARKER, _JPQL_DOT_SEPARTOR);

		return queryString;
	}

	private String _transformPositionalParams(String queryString) {
		if (queryString.indexOf(StringPool.QUESTION) == -1) {
			return queryString;
		}

		StringBundler sb = new StringBundler();

		int i = 1;
		int from = 0;
		int to = 0;

		while ((to = queryString.indexOf(StringPool.QUESTION, from)) != -1) {
			sb.append(queryString.substring(from, to));
			sb.append(StringPool.QUESTION);
			sb.append(i++);

			from = to + 1;
		}

		sb.append(queryString.substring(from, queryString.length()));

		return sb.toString();
	}

	protected Class<?> entityClass = null;
	protected int firstResult = -1;
	protected FlushModeType flushModeType = null;
	protected int maxResults = -1;
	protected Map<Integer, Object> parameterMap =
		new HashMap<Integer, Object>();
	protected String queryString;
	protected SessionImpl sessionImpl;
	protected boolean sqlQuery = false;

	private static final String _HQL_COMPOSITE_ID_MARKER = "\\.id\\.";

	private static final String _HQL_NOT_EQUALS = "!=";

	private static final String _JPQL_DOT_SEPARTOR = ".";

	private static final String _JPQL_NOT_EQUALS = "<>";

}