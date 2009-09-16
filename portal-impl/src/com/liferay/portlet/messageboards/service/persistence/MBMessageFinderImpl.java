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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.workflow.StatusConstants;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.impl.MBMessageImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="MBMessageFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class MBMessageFinderImpl
	extends BasePersistenceImpl implements MBMessageFinder {

	public static String COUNT_BY_G_U =
		MBMessageFinder.class.getName() + ".countByG_U";

	public static String COUNT_BY_G_U_S =
		MBMessageFinder.class.getName() + ".countByG_U_S";

	public static String COUNT_BY_G_U_S_A =
		MBMessageFinder.class.getName() + ".countByG_U_S_A";

	public static String COUNT_BY_G_U_A =
		MBMessageFinder.class.getName() + ".countByG_U_A";

	public static String FIND_BY_NO_ASSETS =
		MBMessageFinder.class.getName() + ".findByNoAssets";

	public static String FIND_BY_G_U =
		MBMessageFinder.class.getName() + ".findByG_U";

	public static String FIND_BY_G_U_S =
		MBMessageFinder.class.getName() + ".findByG_U_S";

	public static String FIND_BY_G_U_S_A =
		MBMessageFinder.class.getName() + ".findByG_U_S_A";
	
	public static String FIND_BY_G_U_A =
		MBMessageFinder.class.getName() + ".findByG_U_A";

	public int countByG_U_S(long groupId, long userId, int status) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = null;

			if (status == StatusConstants.ANY) {
				sql = CustomSQLUtil.get(COUNT_BY_G_U);
			}
			else {
				sql = CustomSQLUtil.get(COUNT_BY_G_U_S);
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);

			if (status != StatusConstants.ANY) {
				qPos.add(status);
			}

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

	public int countByG_U_S_A(
			long groupId, long userId, int status, boolean anonymous)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = null;
			
			if (status == StatusConstants.ANY) {
				sql = CustomSQLUtil.get(COUNT_BY_G_U_A);
			}
			else{
				sql = CustomSQLUtil.get(COUNT_BY_G_U_S_A);
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);

			if (status != StatusConstants.ANY) {
				qPos.add(status);
			}

			qPos.add(anonymous);

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

	public List<MBMessage> findByNoAssets() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_NO_ASSETS);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("MBMessage", MBMessageImpl.class);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Long> findByG_U_S(
			long groupId, long userId, int status, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = null;
			
			if (status == StatusConstants.ANY) {
				sql = CustomSQLUtil.get(FIND_BY_G_U);
			}
			else {
				sql = CustomSQLUtil.get(FIND_BY_G_U_S);
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar("threadId", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);

			if (status != StatusConstants.ANY) {
				qPos.add(status);
			}

			return (List<Long>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Long> findByG_U_S_A(
			long groupId, long userId, int status, boolean anonymous, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = null;
			
			if (status == StatusConstants.ANY) {
				sql = CustomSQLUtil.get(FIND_BY_G_U_A);
			}
			else {
				sql = CustomSQLUtil.get(FIND_BY_G_U_S_A);
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar("threadId", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);
			if (status != StatusConstants.ANY) {
				qPos.add(status);
			}
			qPos.add(anonymous);

			return (List<Long>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}