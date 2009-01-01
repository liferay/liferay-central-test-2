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

package com.liferay.portlet.tags.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.tags.NoSuchEntryException;
import com.liferay.portlet.tags.model.TagsEntry;
import com.liferay.portlet.tags.model.impl.TagsEntryImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="TagsEntryFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 *
 */
public class TagsEntryFinderImpl
	extends BasePersistenceImpl implements TagsEntryFinder {

	public static String COUNT_BY_G_C_N_F =
		TagsEntryFinder.class.getName() + ".countByG_C_N_F";

	public static String COUNT_BY_G_N_F_P =
		TagsEntryFinder.class.getName() + ".countByG_N_F_P";

	public static String FIND_BY_FOLKSONOMY =
		TagsEntryFinder.class.getName() + ".findByFolksonomy";

	public static String FIND_BY_A_F =
		TagsEntryFinder.class.getName() + ".findByA_F";

	public static String FIND_BY_G_N_F =
		TagsEntryFinder.class.getName() + ".findByG_N_F";

	public static String FIND_BY_G_C_N_F =
		TagsEntryFinder.class.getName() + ".findByG_C_N_F";

	public static String FIND_BY_G_N_F_P =
		TagsEntryFinder.class.getName() + ".findByG_N_F_P";

	public int countByG_C_N_F(
			long groupId, long classNameId, String name, boolean folksonomy)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_G_C_N_F);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(classNameId);
			qPos.add(name);
			qPos.add(name);
			qPos.add(folksonomy);

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

	public int countByG_N_F_P(
			long groupId, String name, boolean folksonomy, String[] properties)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_G_N_F_P);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			setJoin(qPos, properties);
			qPos.add(groupId);
			qPos.add(name);
			qPos.add(name);
			qPos.add(folksonomy);

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

	public List<TagsEntry> findByFolksonomy(boolean folksonomy)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_FOLKSONOMY);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("TagsEntry", TagsEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(folksonomy);

			return (List<TagsEntry>) QueryUtil.list(
				q, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TagsEntry> findByA_F(long assetId, boolean folksonomy)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_A_F);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("TagsEntry", TagsEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(assetId);
			qPos.add(folksonomy);

			return (List<TagsEntry>) QueryUtil.list(
				q, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public TagsEntry findByG_N_F(long groupId, String name, boolean folksonomy)
		throws NoSuchEntryException, SystemException {

		name = name.trim().toLowerCase();

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_N_F);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("TagsEntry", TagsEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(name);
			qPos.add(folksonomy);

			List<TagsEntry> list = q.list();

			if (list.size() == 0) {
				StringBuilder sb = new StringBuilder();

				sb.append("No TagsEntry exists with the key ");
				sb.append("{groupId=");
				sb.append(groupId);
				sb.append(", name=");
				sb.append(name);
				sb.append(", folksonomy=");
				sb.append(folksonomy);
				sb.append("}");

				throw new NoSuchEntryException(sb.toString());
			}
			else {
				return list.get(0);
			}
		}
		catch (NoSuchEntryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TagsEntry> findByG_C_N_F(
			long groupId, long classNameId, String name, boolean folksonomy)
		throws SystemException {

		return findByG_C_N_F(
			groupId, classNameId, name, folksonomy, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	public List<TagsEntry> findByG_C_N_F(
			long groupId, long classNameId, String name, boolean folksonomy,
			int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_C_N_F);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("TagsEntry", TagsEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(classNameId);
			qPos.add(name);
			qPos.add(name);
			qPos.add(folksonomy);

			return (List<TagsEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TagsEntry> findByG_N_F_P(
			long groupId, String name, boolean folksonomy, String[] properties)
		throws SystemException {

		return findByG_N_F_P(
			groupId, name, folksonomy, properties, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	public List<TagsEntry> findByG_N_F_P(
			long groupId, String name, boolean folksonomy, String[] properties,
			int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_N_F_P);

			sql = StringUtil.replace(sql, "[$JOIN$]", getJoin(properties));

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("TagsEntry", TagsEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			setJoin(qPos, properties);
			qPos.add(groupId);
			qPos.add(name);
			qPos.add(name);
			qPos.add(folksonomy);

			return (List<TagsEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getJoin(String[] properties) {
		if (properties.length == 0) {
			return StringPool.BLANK;
		}
		else {
			StringBuilder sb = new StringBuilder();

			sb.append(" INNER JOIN TagsProperty ON ");
			sb.append(" (TagsProperty.entryId = TagsEntry.entryId) AND ");

			for (int i = 0; i < properties.length; i++) {
				sb.append("(TagsProperty.key_ = ? AND ");
				sb.append("TagsProperty.value = ?) ");

				if ((i + 1) < properties.length) {
					sb.append(" AND ");
				}
			}

			return sb.toString();
		}
	}

	protected void setJoin(QueryPos qPos, String[] properties) {
		for (int i = 0; i < properties.length; i++) {
			String[] property = StringUtil.split(
				properties[i], StringPool.COLON);

			String key = StringPool.BLANK;

			if (property.length > 0) {
				key = GetterUtil.getString(property[0]);
			}

			String value = StringPool.BLANK;

			if (property.length > 1) {
				value = GetterUtil.getString(property[1]);
			}

			qPos.add(key);
			qPos.add(value);
		}
	}

}