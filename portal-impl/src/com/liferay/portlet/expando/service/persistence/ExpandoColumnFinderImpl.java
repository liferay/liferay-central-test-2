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

package com.liferay.portlet.expando.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.hibernate.QueryPos;
import com.liferay.portal.kernel.dao.hibernate.SQLQuery;
import com.liferay.portal.kernel.dao.hibernate.Session;
import com.liferay.portal.kernel.dao.hibernate.Type;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.impl.ExpandoColumnImpl;
import com.liferay.util.dao.hibernate.CustomSQLUtil;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="ExpandoColumnFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class ExpandoColumnFinderImpl
	extends BasePersistenceImpl implements ExpandoColumnFinder {

	public static String COUNT_BY_TC_TN =
		ExpandoColumnFinder.class.getName() + ".countByTC_TN";

	public static String FIND_BY_TC_TN =
		ExpandoColumnFinder.class.getName() + ".findByTC_TN";

	public static String FIND_BY_TC_TN_CN =
		ExpandoColumnFinder.class.getName() + ".findByTC_TN_CN";

	public int countByTC_TN(long classNameId, String tableName)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_TC_TN);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);
			qPos.add(tableName);

			Iterator<Long> itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoColumn> findByTC_TN(long classNameId, String tableName)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_TC_TN);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("ExpandoColumn", ExpandoColumnImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);
			qPos.add(tableName);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoColumn fetchByTC_TN_CN(
			long classNameId, String tableName, String name)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_TC_TN_CN);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("ExpandoColumn", ExpandoColumnImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);
			qPos.add(tableName);
			qPos.add(name);

			return (ExpandoColumn)q.uniqueResult();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}