/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.tags.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.spring.hibernate.CustomSQLUtil;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portlet.tags.model.impl.TagsAssetImpl;
import com.liferay.util.StringUtil;
import com.liferay.util.dao.hibernate.QueryPos;
import com.liferay.util.dao.hibernate.QueryUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * <a href="TagsAssetFinder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TagsAssetFinder {

	public static String COUNT_BY_AND_ENTRY_IDS =
		TagsAssetFinder.class.getName() + ".countByAndEntryIds";

	public static String COUNT_BY_OR_ENTRY_IDS =
		TagsAssetFinder.class.getName() + ".countByOrEntryIds";

	public static String FIND_BY_AND_ENTRY_IDS =
		TagsAssetFinder.class.getName() + ".findByAndEntryIds";

	public static String FIND_BY_OR_ENTRY_IDS =
		TagsAssetFinder.class.getName() + ".findByOrEntryIds";

	public static int countByAndEntryIds(long[] entryIds, long[] notEntryIds)
		throws SystemException {

		if (entryIds.length == 0) {
			return 0;
		}

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			StringMaker sm = new StringMaker();

			sm.append("SELECT COUNT(DISTINCT assetId) AS COUNT_VALUE ");
			sm.append("FROM TagsAsset WHERE TagsAsset.assetId IN (");

			for (int i = 0; i < entryIds.length; i++) {
				sm.append(CustomSQLUtil.get(FIND_BY_AND_ENTRY_IDS));

				if ((i + 1) < entryIds.length) {
					sm.append(" AND TagsAsset.assetId IN (");
				}
			}

			for (int i = 0; i < entryIds.length; i++) {
				if ((i + 1) < entryIds.length) {
					sm.append(StringPool.CLOSE_PARENTHESIS);
				}
			}

			sm.append(StringPool.CLOSE_PARENTHESIS);

			if (notEntryIds.length > 0) {
				sm.append(" AND (");

				for (int i = 0; i < notEntryIds.length; i++) {
					sm.append("TagsAsset.assetId NOT IN (");
					sm.append(CustomSQLUtil.get(FIND_BY_AND_ENTRY_IDS));
					sm.append(StringPool.CLOSE_PARENTHESIS);

					if ((i + 1) < notEntryIds.length) {
						sm.append(" OR ");
					}
				}

				sm.append(StringPool.CLOSE_PARENTHESIS);
			}

			String sql = sm.toString();

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(false);

			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			_setEntryIds(qPos, entryIds);
			_setEntryIds(qPos, notEntryIds);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

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
			HibernateUtil.closeSession(session);
		}
	}

	public static int countByOrEntryIds(long[] entryIds, long[] notEntryIds)
		throws SystemException {

		if (entryIds.length == 0) {
			return 0;
		}

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_OR_ENTRY_IDS);

			sql = StringUtil.replace(
				sql, "[$ENTRY_ID$]", _getEntryIds(entryIds, StringPool.EQUAL));

			if (notEntryIds.length > 0) {
				StringMaker sm = new StringMaker();

				sm.append(" AND (");

				for (int i = 0; i < notEntryIds.length; i++) {
					sm.append("TagsAsset.assetId NOT IN (");
					sm.append(CustomSQLUtil.get(FIND_BY_AND_ENTRY_IDS));
					sm.append(StringPool.CLOSE_PARENTHESIS);

					if ((i + 1) < notEntryIds.length) {
						sm.append(" AND ");
					}
				}

				sm.append(StringPool.CLOSE_PARENTHESIS);

				sql = StringUtil.replace(
					sql, "[$NOT_ENTRY_ID$]", sm.toString());
			}
			else {
				sql = StringUtil.replace(
					sql, "[$NOT_ENTRY_ID$]", StringPool.BLANK);
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(false);

			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			_setEntryIds(qPos, entryIds);
			_setEntryIds(qPos, notEntryIds);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

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
			HibernateUtil.closeSession(session);
		}
	}

	public static List findByAndEntryIds(
			long[] entryIds, long[] notEntryIds, int begin, int end)
		throws SystemException {

		if (entryIds.length == 0) {
			return new ArrayList();
		}

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			StringMaker sm = new StringMaker();

			sm.append("SELECT DISTINCT {TagsAsset.*} ");
			sm.append("FROM TagsAsset WHERE TagsAsset.assetId IN (");

			for (int i = 0; i < entryIds.length; i++) {
				sm.append(CustomSQLUtil.get(FIND_BY_AND_ENTRY_IDS));

				if ((i + 1) < entryIds.length) {
					sm.append(" AND TagsAsset.assetId IN (");
				}
			}

			for (int i = 0; i < entryIds.length; i++) {
				if ((i + 1) < entryIds.length) {
					sm.append(StringPool.CLOSE_PARENTHESIS);
				}
			}

			sm.append(StringPool.CLOSE_PARENTHESIS);

			if (notEntryIds.length > 0) {
				sm.append(" AND (");

				for (int i = 0; i < notEntryIds.length; i++) {
					sm.append("TagsAsset.assetId NOT IN (");
					sm.append(CustomSQLUtil.get(FIND_BY_AND_ENTRY_IDS));
					sm.append(StringPool.CLOSE_PARENTHESIS);

					if ((i + 1) < notEntryIds.length) {
						sm.append(" OR ");
					}
				}

				sm.append(StringPool.CLOSE_PARENTHESIS);
			}

			sm.append(" ORDER BY TagsAsset.modifiedDate DESC");

			String sql = sm.toString();

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(false);

			q.addEntity("TagsAsset", TagsAssetImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			_setEntryIds(qPos, entryIds);
			_setEntryIds(qPos, notEntryIds);

			return QueryUtil.list(q, HibernateUtil.getDialect(), begin, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static List findByOrEntryIds(long[] entryIds, long[] notEntryIds)
		throws SystemException {

		return findByOrEntryIds(
			entryIds, notEntryIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public static List findByOrEntryIds(
			long[] entryIds, long[] notEntryIds, int begin, int end)
		throws SystemException {

		if (entryIds.length == 0) {
			return new ArrayList();
		}

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_OR_ENTRY_IDS);

			sql = StringUtil.replace(
				sql, "[$ENTRY_ID$]", _getEntryIds(entryIds, StringPool.EQUAL));

			if (notEntryIds.length > 0) {
				StringMaker sm = new StringMaker();

				sm.append(" AND (");

				for (int i = 0; i < notEntryIds.length; i++) {
					sm.append("TagsAsset.assetId NOT IN (");
					sm.append(CustomSQLUtil.get(FIND_BY_AND_ENTRY_IDS));
					sm.append(StringPool.CLOSE_PARENTHESIS);

					if ((i + 1) < notEntryIds.length) {
						sm.append(" AND ");
					}
				}

				sm.append(StringPool.CLOSE_PARENTHESIS);

				sql = StringUtil.replace(
					sql, "[$NOT_ENTRY_ID$]", sm.toString());
			}
			else {
				sql = StringUtil.replace(
					sql, "[$NOT_ENTRY_ID$]", StringPool.BLANK);
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(false);

			q.addEntity("TagsAsset", TagsAssetImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			_setEntryIds(qPos, entryIds);
			_setEntryIds(qPos, notEntryIds);

			return QueryUtil.list(q, HibernateUtil.getDialect(), begin, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	private static String _getEntryIds(long[] entryIds, String operator) {
		StringMaker sm = new StringMaker();

		for (int i = 0; i < entryIds.length; i++) {
			sm.append("TagsEntry.entryId ");
			sm.append(operator);
			sm.append(" ? ");

			if ((i + 1) != entryIds.length) {
				sm.append("OR ");
			}
		}

		return sm.toString();
	}

	private static void _setEntryIds(QueryPos qPos, long[] entryIds) {
		for (int i = 0; i < entryIds.length; i++) {
			qPos.add(entryIds[i]);
		}
	}

}