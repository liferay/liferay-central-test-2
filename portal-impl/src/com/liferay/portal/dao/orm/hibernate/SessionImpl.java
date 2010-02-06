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

import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.orm.LockMode;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;

import java.io.Serializable;

import java.sql.Connection;

/**
 * <a href="SessionImpl.java.html"><b><i>View Source</i></b></a>
 *
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

	private org.hibernate.Session _session;

}