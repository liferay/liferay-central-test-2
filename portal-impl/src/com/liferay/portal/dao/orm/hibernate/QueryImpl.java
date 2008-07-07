/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.orm.HibernateException;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.ScrollableResults;

import java.io.Serializable;

import java.sql.Timestamp;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="QueryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class QueryImpl implements Query {

	public QueryImpl(org.hibernate.Query query) {
		_query = query;
	}

	public Iterator iterate() throws HibernateException {
		try {
			return _query.iterate();
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public List list() throws HibernateException {
		try {
			return _query.list();
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public ScrollableResults scroll() throws HibernateException {
		try {
			return new ScrollableResultsImpl(_query.scroll());
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public Query setBoolean(int pos, boolean value) {
		_query.setBoolean(pos, value);

		return this;
	}

	public Query setDouble(int pos, double value) {
		_query.setDouble(pos, value);

		return this;
	}

	public Query setFirstResult(int firstResult) {
		_query.setFirstResult(firstResult);

		return this;
	}

	public Query setFloat(int pos, float value) {
		_query.setFloat(pos, value);

		return this;
	}

	public Query setInteger(int pos, int value) {
		_query.setInteger(pos, value);

		return this;
	}

	public Query setLong(int pos, long value) {
		_query.setLong(pos, value);

		return this;
	}

	public Query setMaxResults(int maxResults) {
		_query.setMaxResults(maxResults);

		return this;
	}

	public Query setSerializable(int pos, Serializable value) {
		_query.setSerializable(pos, value);

		return this;
	}

	public Query setShort(int pos, short value) {
		_query.setShort(pos, value);

		return this;
	}

	public Query setString(int pos, String value) {
		_query.setString(pos, value);

		return this;
	}

	public Query setTimestamp(int pos, Timestamp value) {
		_query.setTimestamp(pos, value);

		return this;
	}

	public Object uniqueResult() throws HibernateException {
		try {
			return _query.uniqueResult();
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	private org.hibernate.Query _query;

}