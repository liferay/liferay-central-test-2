/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.CacheMode;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.ScrollableResults;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;

import java.io.Serializable;

import java.sql.Timestamp;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="SQLQueryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SQLQueryImpl implements SQLQuery {

	public SQLQueryImpl(org.hibernate.SQLQuery sqlQuery) {
		_sqlQuery = sqlQuery;
	}

	public SQLQuery addEntity(String alias, Class<?> entityClass) {
		_sqlQuery.addEntity(alias, entityClass);

		return this;
	}

	public SQLQuery addScalar(String columnAlias, Type type) {
		_sqlQuery.addScalar(columnAlias, TypeTranslator.translate(type));

		return this;
	}

	public int executeUpdate() throws ORMException {
		try {
			return _sqlQuery.executeUpdate();
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Iterator iterate() throws ORMException {
		return iterate(true);
	}

	@SuppressWarnings("unchecked")
	public Iterator iterate(boolean unmodifiable) throws ORMException {
		try {
			return list(unmodifiable).iterator();
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List list() throws ORMException {
		return list(true);
	}

	@SuppressWarnings("unchecked")
	public List list(boolean unmodifiable) throws ORMException {
		try {
			List list = _sqlQuery.list();

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
			return new ScrollableResultsImpl(_sqlQuery.scroll());
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public Query setBoolean(int pos, boolean value) {
		_sqlQuery.setBoolean(pos, value);

		return this;
	}

	public Query setCacheable(boolean cacheable) {
		_sqlQuery.setCacheable(cacheable);

		return this;
	}

	public Query setCacheMode(CacheMode cacheMode) {
		_sqlQuery.setCacheMode(CacheModeTranslator.translate(cacheMode));

		return this;
	}

	public Query setCacheRegion(String cacheRegion) {
		_sqlQuery.setCacheRegion(cacheRegion);

		return this;
	}

	public Query setDouble(int pos, double value) {
		_sqlQuery.setDouble(pos, value);

		return this;
	}

	public Query setFirstResult(int firstResult) {
		_sqlQuery.setFirstResult(firstResult);

		return this;
	}

	public Query setFloat(int pos, float value) {
		_sqlQuery.setFloat(pos, value);

		return this;
	}

	public Query setInteger(int pos, int value) {
		_sqlQuery.setInteger(pos, value);

		return this;
	}

	public Query setLong(int pos, long value) {
		_sqlQuery.setLong(pos, value);

		return this;
	}

	public Query setMaxResults(int maxResults) {
		_sqlQuery.setMaxResults(maxResults);

		return this;
	}

	public Query setSerializable(int pos, Serializable value) {
		_sqlQuery.setSerializable(pos, value);

		return this;
	}

	public Query setShort(int pos, short value) {
		_sqlQuery.setShort(pos, value);

		return this;
	}

	public Query setString(int pos, String value) {
		_sqlQuery.setString(pos, value);

		return this;
	}

	public Query setTimestamp(int pos, Timestamp value) {
		_sqlQuery.setTimestamp(pos, value);

		return this;
	}

	public Object uniqueResult() throws ORMException {
		try {
			return _sqlQuery.uniqueResult();
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	private org.hibernate.SQLQuery _sqlQuery;

}