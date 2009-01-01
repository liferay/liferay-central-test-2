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
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.tags.model.TagsAsset;
import com.liferay.portlet.tags.model.impl.TagsAssetImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.sql.Timestamp;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="TagsAssetFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TagsAssetFinderImpl
	extends BasePersistenceImpl implements TagsAssetFinder {

	public static String COUNT_BY_AND_ENTRY_IDS =
		TagsAssetFinder.class.getName() + ".countByAndEntryIds";

	public static String COUNT_BY_OR_ENTRY_IDS =
		TagsAssetFinder.class.getName() + ".countByOrEntryIds";

	public static String FIND_BY_AND_ENTRY_IDS =
		TagsAssetFinder.class.getName() + ".findByAndEntryIds";

	public static String FIND_BY_OR_ENTRY_IDS =
		TagsAssetFinder.class.getName() + ".findByOrEntryIds";

	public static String FIND_BY_VIEW_COUNT =
		TagsAssetFinder.class.getName() + ".findByViewCount";

	public static String[] ORDER_BY_COLUMNS = new String[] {
		"title", "createDate", "modifiedDate", "publishDate", "expirationDate",
		"priority", "viewCount"
	};

	public static String[] ORDER_BY_TYPE = new String[] {
		"ASC", "DESC"
	};

	public int countAssets(
			long groupId, long[] classNameIds, boolean excludeZeroViewCount,
			Date publishDate, Date expirationDate)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			StringBuilder sb = new StringBuilder();

			sb.append("SELECT COUNT(assetId) AS COUNT_VALUE ");
			sb.append("FROM TagsAsset WHERE");
			sb.append(" (visible = ?)");

			if (excludeZeroViewCount) {
				sb.append(" AND (TagsAsset.viewCount > 0)");
			}

			sb.append("[$DATES$]");

			if (groupId > 0) {
				sb.append(" AND (TagsAsset.groupId = ?)");
			}

			sb.append(getClassNameIds(classNameIds));

			String sql = sb.toString();

			sql = getDates(sql, publishDate, expirationDate);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(true);

			setDates(qPos, publishDate, expirationDate);

			if (groupId > 0) {
				setGroupId(qPos, groupId);
			}

			setClassNamedIds(qPos, classNameIds);

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

	public int countByAndEntryIds(
			long groupId, long[] classNameIds, long[] entryIds,
			long[] notEntryIds, boolean excludeZeroViewCount, Date publishDate,
			Date expirationDate)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			StringBuilder sb = new StringBuilder();

			sb.append("SELECT COUNT(DISTINCT assetId) AS COUNT_VALUE ");
			sb.append("FROM TagsAsset WHERE");
			sb.append(" (visible = ?)");

			if (entryIds.length > 0) {
				sb.append(" AND TagsAsset.assetId IN (");

				for (int i = 0; i < entryIds.length; i++) {
					sb.append(CustomSQLUtil.get(FIND_BY_AND_ENTRY_IDS));

					if ((i + 1) < entryIds.length) {
						sb.append(" AND TagsAsset.assetId IN (");
					}
				}

				for (int i = 0; i < entryIds.length; i++) {
					if ((i + 1) < entryIds.length) {
						sb.append(StringPool.CLOSE_PARENTHESIS);
					}
				}

				if (excludeZeroViewCount) {
					sb.append(" AND (TagsAsset.viewCount > 0)");
				}

				sb.append(StringPool.CLOSE_PARENTHESIS);
			}

			if (notEntryIds.length > 0) {
				sb.append(" AND (");

				for (int i = 0; i < notEntryIds.length; i++) {
					sb.append("TagsAsset.assetId NOT IN (");
					sb.append(CustomSQLUtil.get(FIND_BY_AND_ENTRY_IDS));
					sb.append(StringPool.CLOSE_PARENTHESIS);

					if ((i + 1) < notEntryIds.length) {
						sb.append(" OR ");
					}
				}

				sb.append(StringPool.CLOSE_PARENTHESIS);
			}

			sb.append("[$DATES$]");

			if (groupId > 0) {
				sb.append(" AND (TagsAsset.groupId = ?)");
			}

			sb.append(getClassNameIds(classNameIds));

			String sql = sb.toString();

			sql = getDates(sql, publishDate, expirationDate);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(true);

			setEntryIds(qPos, entryIds);
			setEntryIds(qPos, notEntryIds);
			setDates(qPos, publishDate, expirationDate);

			if (groupId > 0) {
				setGroupId(qPos, groupId);
			}

			setClassNamedIds(qPos, classNameIds);

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

	public int countByOrEntryIds(
			long groupId, long[] classNameIds, long[] entryIds,
			long[] notEntryIds, boolean excludeZeroViewCount, Date publishDate,
			Date expirationDate)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_OR_ENTRY_IDS);

			sql = StringUtil.replace(
				sql, "[$ENTRY_ID$]", getEntryIds(entryIds, StringPool.EQUAL));

			if (notEntryIds.length > 0) {
				StringBuilder sb = new StringBuilder();

				sb.append(" AND (");

				for (int i = 0; i < notEntryIds.length; i++) {
					sb.append("TagsAsset.assetId NOT IN (");
					sb.append(CustomSQLUtil.get(FIND_BY_AND_ENTRY_IDS));
					sb.append(StringPool.CLOSE_PARENTHESIS);

					if ((i + 1) < notEntryIds.length) {
						sb.append(" AND ");
					}
				}

				sb.append(StringPool.CLOSE_PARENTHESIS);

				sql = StringUtil.replace(
					sql, "[$NOT_ENTRY_ID$]", sb.toString());
			}
			else {
				sql = StringUtil.replace(
					sql, "[$NOT_ENTRY_ID$]", StringPool.BLANK);
			}

			sql = getDates(sql, publishDate, expirationDate);

			sql += " AND (visible = ?)";

			if (groupId > 0) {
				sql += " AND (TagsAsset.groupId = ?)";
			}

			sql += getClassNameIds(classNameIds);

			if (excludeZeroViewCount) {
				sql += " AND (TagsAsset.viewCount > 0)";
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			setEntryIds(qPos, entryIds);
			setEntryIds(qPos, notEntryIds);
			setDates(qPos, publishDate, expirationDate);

			qPos.add(true);

			if (groupId > 0) {
				setGroupId(qPos, groupId);
			}

			setClassNamedIds(qPos, classNameIds);

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

	public List<TagsAsset> findAssets(
			long groupId, long[] classNameIds, String orderByCol1,
			String orderByCol2, String orderByType1, String orderByType2,
			boolean excludeZeroViewCount, Date publishDate, Date expirationDate,
			int start, int end)
		throws SystemException {

		orderByCol1 = checkOrderByCol(orderByCol1);
		orderByCol2 = checkOrderByCol(orderByCol2);
		orderByType1 = checkOrderByType(orderByType1);
		orderByType2 = checkOrderByType(orderByType2);

		Session session = null;

		try {
			session = openSession();

			StringBuilder sb = new StringBuilder();

			sb.append("SELECT {TagsAsset.*} ");
			sb.append("FROM TagsAsset WHERE");
			sb.append(" (visible = ?)");

			if (excludeZeroViewCount) {
				sb.append(" AND (TagsAsset.viewCount > 0)");
			}

			sb.append("[$DATES$]");

			if (groupId > 0) {
				sb.append(" AND (TagsAsset.groupId = ?)");
			}

			sb.append(getClassNameIds(classNameIds));

			sb.append(" ORDER BY TagsAsset.");
			sb.append(orderByCol1);
			sb.append(StringPool.SPACE);
			sb.append(orderByType1);

			if (Validator.isNotNull(orderByCol2) &&
				!orderByCol1.equals(orderByCol2)) {

				sb.append(", TagsAsset.");
				sb.append(orderByCol2);
				sb.append(StringPool.SPACE);
				sb.append(orderByType2);
			}

			String sql = sb.toString();

			sql = getDates(sql, publishDate, expirationDate);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("TagsAsset", TagsAssetImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(true);

			setDates(qPos, publishDate, expirationDate);

			if (groupId > 0) {
				setGroupId(qPos, groupId);
			}

			setClassNamedIds(qPos, classNameIds);

			return (List<TagsAsset>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TagsAsset> findByAndEntryIds(
			long groupId, long[] classNameIds, long[] entryIds,
			long[] notEntryIds, String orderByCol1, String orderByCol2,
			String orderByType1, String orderByType2,
			boolean excludeZeroViewCount, Date publishDate, Date expirationDate,
			int start, int end)
		throws SystemException {

		orderByCol1 = checkOrderByCol(orderByCol1);
		orderByCol2 = checkOrderByCol(orderByCol2);
		orderByType1 = checkOrderByType(orderByType1);
		orderByType2 = checkOrderByType(orderByType2);

		Session session = null;

		try {
			session = openSession();

			StringBuilder sb = new StringBuilder();

			sb.append("SELECT DISTINCT {TagsAsset.*} ");
			sb.append("FROM TagsAsset WHERE");
			sb.append(" (visible = ?)");

			if (entryIds.length > 0) {
				sb.append(" AND TagsAsset.assetId IN (");

				for (int i = 0; i < entryIds.length; i++) {
					sb.append(CustomSQLUtil.get(FIND_BY_AND_ENTRY_IDS));

					if ((i + 1) < entryIds.length) {
						sb.append(" AND TagsAsset.assetId IN (");
					}
				}

				for (int i = 0; i < entryIds.length; i++) {
					if ((i + 1) < entryIds.length) {
						sb.append(StringPool.CLOSE_PARENTHESIS);
					}
				}

				if (excludeZeroViewCount) {
					sb.append(" AND (TagsAsset.viewCount > 0)");
				}

				sb.append(StringPool.CLOSE_PARENTHESIS);
			}

			if (notEntryIds.length > 0) {
				sb.append(" AND (");

				for (int i = 0; i < notEntryIds.length; i++) {
					sb.append("TagsAsset.assetId NOT IN (");
					sb.append(CustomSQLUtil.get(FIND_BY_AND_ENTRY_IDS));
					sb.append(StringPool.CLOSE_PARENTHESIS);

					if ((i + 1) < notEntryIds.length) {
						sb.append(" OR ");
					}
				}

				sb.append(StringPool.CLOSE_PARENTHESIS);
			}

			sb.append("[$DATES$]");

			if (groupId > 0) {
				sb.append(" AND (TagsAsset.groupId = ?)");
			}

			sb.append(getClassNameIds(classNameIds));

			sb.append(" ORDER BY TagsAsset.");
			sb.append(orderByCol1);
			sb.append(StringPool.SPACE);
			sb.append(orderByType1);

			if (Validator.isNotNull(orderByCol2) &&
				!orderByCol1.equals(orderByCol2)) {

				sb.append(", TagsAsset.");
				sb.append(orderByCol2);
				sb.append(StringPool.SPACE);
				sb.append(orderByType2);
			}

			String sql = sb.toString();

			sql = getDates(sql, publishDate, expirationDate);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("TagsAsset", TagsAssetImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(true);

			setEntryIds(qPos, entryIds);
			setEntryIds(qPos, notEntryIds);
			setDates(qPos, publishDate, expirationDate);

			if (groupId > 0) {
				setGroupId(qPos, groupId);
			}

			setClassNamedIds(qPos, classNameIds);

			return (List<TagsAsset>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TagsAsset> findByOrEntryIds(
			long groupId, long[] classNameIds, long[] entryIds,
			long[] notEntryIds, Date publishDate, Date expirationDate )
		throws SystemException {

		return findByOrEntryIds(
			groupId, classNameIds, entryIds, notEntryIds, null, null, null,
			null, false, publishDate, expirationDate, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	public List<TagsAsset> findByOrEntryIds(
			long groupId, long[] classNameIds, long[] entryIds,
			long[] notEntryIds, String orderByCol1, String orderByCol2,
			String orderByType1, String orderByType2,
			boolean excludeZeroViewCount, Date publishDate, Date expirationDate,
			int start, int end)
		throws SystemException {

		orderByCol1 = checkOrderByCol(orderByCol1);
		orderByCol2 = checkOrderByCol(orderByCol2);
		orderByType1 = checkOrderByType(orderByType1);
		orderByType2 = checkOrderByType(orderByType2);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_OR_ENTRY_IDS);

			sql = StringUtil.replace(
				sql, "[$ENTRY_ID$]", getEntryIds(entryIds, StringPool.EQUAL));

			if (notEntryIds.length > 0) {
				StringBuilder sb = new StringBuilder();

				sb.append(" AND (");

				for (int i = 0; i < notEntryIds.length; i++) {
					sb.append("TagsAsset.assetId NOT IN (");
					sb.append(CustomSQLUtil.get(FIND_BY_AND_ENTRY_IDS));
					sb.append(StringPool.CLOSE_PARENTHESIS);

					if ((i + 1) < notEntryIds.length) {
						sb.append(" AND ");
					}
				}

				sb.append(StringPool.CLOSE_PARENTHESIS);

				sql = StringUtil.replace(
					sql, "[$NOT_ENTRY_ID$]", sb.toString());
			}
			else {
				sql = StringUtil.replace(
					sql, "[$NOT_ENTRY_ID$]", StringPool.BLANK);
			}

			sql = getDates(sql, publishDate, expirationDate);

			sql += " AND (visible = ?)";

			if (groupId > 0) {
				sql += " AND (TagsAsset.groupId = ?)";
			}

			sql += getClassNameIds(classNameIds);

			if (excludeZeroViewCount) {
				sql += " AND (TagsAsset.viewCount > 0)";
			}

			StringBuilder sb = new StringBuilder();

			sb.append(" ORDER BY TagsAsset.");
			sb.append(orderByCol1);
			sb.append(StringPool.SPACE);
			sb.append(orderByType1);

			if (Validator.isNotNull(orderByCol2) &&
				!orderByCol1.equals(orderByCol2)) {

				sb.append(", TagsAsset.");
				sb.append(orderByCol2);
				sb.append(StringPool.SPACE);
				sb.append(orderByType2);
			}

			sql += sb.toString();

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("TagsAsset", TagsAssetImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			setEntryIds(qPos, entryIds);
			setEntryIds(qPos, notEntryIds);
			setDates(qPos, publishDate, expirationDate);

			qPos.add(true);

			if (groupId > 0) {
				setGroupId(qPos, groupId);
			}

			setClassNamedIds(qPos, classNameIds);

			return (List<TagsAsset>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TagsAsset> findByViewCount(
			long[] classNameId, boolean asc, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_VIEW_COUNT);

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < classNameId.length; i++) {
				sb.append("(TagsAsset.classNameId = ?)");

				if ((i+1) < classNameId.length) {
					sb.append(" OR ");
				}
			}

			sql = StringUtil.replace(
				sql, "(TagsAsset.classNameId = ?)", sb.toString());

			sql += " AND (visible = ?)";

			sb = new StringBuilder();

			sb.append(" ORDER BY TagsAsset.viewCount");

			if (asc) {
				sb.append(" ASC");
			}
			else {
				sb.append(" DESC");
			}

			sql += sb.toString();

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("TagsAsset", TagsAssetImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			for (int i = 0; i < classNameId.length; i++) {
				qPos.add(classNameId[i]);
			}

			qPos.add(true);

			return (List<TagsAsset>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected String checkOrderByCol(String orderByCol) {
		if (orderByCol == null) {
			return "modifiedDate";
		}

		for (int i = 0; i < ORDER_BY_COLUMNS.length; i++) {
			if (orderByCol.equals(ORDER_BY_COLUMNS[i])) {
				return orderByCol;
			}
		}

		return "modifiedDate";
	}

	protected String checkOrderByType(String orderByType) {
		if (orderByType == null) {
			return "DESC";
		}

		for (int i = 0; i < ORDER_BY_TYPE.length; i++) {
			if (orderByType.equals(ORDER_BY_TYPE[i])) {
				return orderByType;
			}
		}

		return "DESC";
	}

	protected String getClassNameIds(long[] classNameIds) {
		StringBuilder sb = new StringBuilder();

		if (classNameIds.length > 0) {
			sb.append(" AND (");

			for (int i = 0; i < classNameIds.length; i++) {
				sb.append("classNameId = ?");

				if (i > 0) {
					sb.append(" AND ");
				}
			}

			sb.append(") ");
		}

		return sb.toString();
	}

	protected String getDates(
		String sql, Date publishDate, Date expirationDate) {

		StringBuilder sb = new StringBuilder();

		if (publishDate != null) {
			sb.append(" AND (publishDate IS NULL OR publishDate < ?)");
		}

		if (expirationDate != null) {
			sb.append(" AND (expirationDate IS NULL OR expirationDate > ?)");
		}

		sql = StringUtil.replace(sql, "[$DATES$]", sb.toString());

		return sql;
	}

	protected String getEntryIds(long[] entryIds, String operator) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < entryIds.length; i++) {
			sb.append("TagsEntry.entryId ");
			sb.append(operator);
			sb.append(" ? ");

			if ((i + 1) != entryIds.length) {
				sb.append("OR ");
			}
		}

		if (sb.length() == 0) {
			sb.append("(1 = 1)");
		}

		return sb.toString();
	}

	protected void setClassNamedIds(QueryPos qPos, long[] classNameIds) {
		for (int i = 0; i < classNameIds.length; i++) {
			qPos.add(classNameIds[i]);
		}
	}

	protected void setDates(
		QueryPos qPos, Date publishDate, Date expirationDate) {

		if (publishDate != null) {
			Timestamp publishDate_TS = CalendarUtil.getTimestamp(publishDate);

			qPos.add(publishDate_TS);
		}

		if (expirationDate != null) {
			Timestamp expirationDate_TS =
				CalendarUtil.getTimestamp(expirationDate);

			qPos.add(expirationDate_TS);
		}
	}

	protected void setGroupId(QueryPos qPos, long groupId) {
		qPos.add(groupId);
	}

	protected void setEntryIds(QueryPos qPos, long[] entryIds) {
		for (int i = 0; i < entryIds.length; i++) {
			qPos.add(entryIds[i]);
		}
	}

}