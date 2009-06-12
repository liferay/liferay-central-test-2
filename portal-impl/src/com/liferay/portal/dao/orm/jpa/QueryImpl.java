/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2009 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.dao.orm.jpa;

import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.orm.CacheMode;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.ScrollableResults;
import com.liferay.portal.kernel.util.ListUtil;
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
 * <a href="QueryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Prashant Dighe
 * @author Brian Wing Shun Chan
 *
 */
public class QueryImpl implements Query {

	public QueryImpl(SessionImpl sessionImpl, String queryString) {
		_sessionImpl = sessionImpl;

		queryString = SQLTransformer.transform(queryString);

		_queryString = _hqlTojpql(queryString);
	}

	public int executeUpdate() throws ORMException {
		try {
			return _sessionImpl.executeUpdate(
				_queryString, _parameterMap, _firstResult,
				_maxResults, _flushMode, _sqlQuery, _entityClass);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public Iterator iterate() throws ORMException {
		return iterate(true);
	}

	public Iterator iterate(boolean unmodifiable) throws ORMException {
		try {
			return list(unmodifiable).iterator();
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public List list() throws ORMException {
		return list(true);
	}

	public List list(boolean unmodifiable) throws ORMException {
		try {
			List list = _sessionImpl.list(
				_queryString, _parameterMap, _firstResult,
				_maxResults, _flushMode, _sqlQuery, _entityClass);

			if (unmodifiable) {
				return new UnmodifiableList(list);
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
		_parameterMap.put(pos, value);

		return this;
	}

	public Query setCacheable(boolean cacheable) {

		//TODO: use Query.setHint
		//Hints are provider specific and will need to be configured and
		//read from portal.properties

		return this;
	}

	public Query setCacheMode(CacheMode cacheMode) {
		return this;
	}

	public Query setCacheRegion(String cacheRegion) {
		return this;
	}

	public Query setDouble(int pos, double value) {
		_parameterMap.put(pos, Double.valueOf(value));

		return this;
	}

	public Query setFirstResult(int firstResult) {
		_firstResult = firstResult;

		return this;
	}

	public Query setFloat(int pos, float value) {
		_parameterMap.put(pos, Float.valueOf(value));

		return this;
	}

	public Query setFlushMode(FlushModeType flushMode) {
		_flushMode = flushMode;

		return this;
	}

	public Query setInteger(int pos, int value) {
		_parameterMap.put(pos, Integer.valueOf(value));

		return this;
	}

	public Query setLong(int pos, long value) {
		_parameterMap.put(pos, Long.valueOf(value));

		return this;
	}

	public Query setMaxResults(int maxResults) {
		_maxResults = maxResults;

		return this;
	}

	public Query setSerializable(int pos, Serializable value) {
		_parameterMap.put(pos, value);

		return this;
	}

	public Query setShort(int pos, short value) {
		_parameterMap.put(pos, Short.valueOf(value));

		return this;
	}

	public Query setString(int pos, String value) {
		_parameterMap.put(pos, value);

		return this;
	}

	public Query setTimestamp(int pos, Timestamp value) {
		Date date = (value == null) ? null : new Date(value.getTime());

		_parameterMap.put(pos, date);

		return this;
	}

	public Object uniqueResult() throws ORMException {
		try {
			return _sessionImpl.uniqueResult(
				_queryString, _parameterMap, _firstResult,
				_maxResults, _flushMode, _sqlQuery, _entityClass);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	private String _hqlTojpql(String queryString) {
		String modQuery = _transformPositionalParams(queryString);

		modQuery = modQuery.replaceAll(_HQL_NOT_EQUALS, _JPQL_NOT_EQUALS);

		modQuery = modQuery.replaceAll(
			_HQL_COMPOSITE_ID_MARKER, _JPQL_DOT_SEPARTOR);

		return modQuery;
	}

	private String _transformPositionalParams(String queryString) {
		if (queryString.indexOf('?') == -1) {
			return queryString;
		}

		StringBuilder sb = new StringBuilder();

		int i = 1, from = 0, to;

		while ( (to = queryString.indexOf('?', from)) != -1 ) {
			sb.append(queryString.substring(from, to))
				.append("?")
				.append(i++);

			from = to + 1;
		}

		sb.append(queryString.substring(from, queryString.length()));

		return sb.toString();
	}

	protected static final String _HQL_COMPOSITE_ID_MARKER = "\\.id\\.";
	protected static final String _HQL_NOT_EQUALS = "!=";
	protected static final String _JPQL_DOT_SEPARTOR = ".";
	protected static final String _JPQL_NOT_EQUALS = "<>";

	protected int _firstResult = -1;
	protected FlushModeType _flushMode = null;
	protected Class _entityClass = null;
	protected int _maxResults = -1;
	protected Map<Integer, Object> _parameterMap =
		new HashMap<Integer, Object>();
	protected String _queryString;
	protected SessionImpl _sessionImpl;
	protected boolean _sqlQuery = false;

}