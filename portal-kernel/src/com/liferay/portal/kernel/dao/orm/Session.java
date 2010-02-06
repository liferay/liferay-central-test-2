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

package com.liferay.portal.kernel.dao.orm;

import java.io.Serializable;

import java.sql.Connection;

/**
 * <a href="Session.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public interface Session {

	public void clear() throws ORMException;

	public Connection close() throws ORMException;

	public boolean contains(Object object) throws ORMException;

	public Query createQuery(String queryString) throws ORMException;

	public SQLQuery createSQLQuery(String queryString) throws ORMException;

	public void delete(Object object) throws ORMException;

	public void evict(Object object) throws ORMException;

	public void flush() throws ORMException;

	public Object get(Class<?> clazz, Serializable id) throws ORMException;

	public Object get(Class<?> clazz, Serializable id, LockMode lockMode)
		throws ORMException;

	public Object getWrappedSession() throws ORMException;

	public Object load(Class<?> clazz, Serializable id) throws ORMException;

	public Object merge(Object object) throws ORMException;

	public Serializable save(Object object) throws ORMException;

	public void saveOrUpdate(Object object) throws ORMException;

}