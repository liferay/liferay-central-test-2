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

package com.liferay.portal.kernel.dao.orm;

import java.io.Serializable;

import java.sql.Timestamp;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="Query.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public interface Query {

	public int executeUpdate() throws ORMException;

	@SuppressWarnings("unchecked")
	public Iterator iterate() throws ORMException;

	@SuppressWarnings("unchecked")
	public Iterator iterate(boolean modifiable) throws ORMException;

	@SuppressWarnings("unchecked")
	public List list() throws ORMException;

	@SuppressWarnings("unchecked")
	public List list(boolean unmodifiable) throws ORMException;

	public ScrollableResults scroll() throws ORMException;

	public Query setBoolean(int pos, boolean value);

	public Query setCacheable(boolean cacheable);

	public Query setCacheMode(CacheMode cacheMode);

	public Query setCacheRegion(String cacheRegion);

	public Query setDouble(int pos, double value);

	public Query setFirstResult(int firstResult);

	public Query setFloat(int pos, float value);

	public Query setInteger(int pos, int value);

	public Query setLong(int pos, long value);

	public Query setMaxResults(int maxResults);

	public Query setShort(int pos, short value);

	public Query setSerializable(int pos, Serializable value);

	public Query setString(int pos, String value);

	public Query setTimestamp(int pos, Timestamp value);

	public Object uniqueResult() throws ORMException;

}