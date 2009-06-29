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

package com.liferay.portlet.asset.service.persistence;

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
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.impl.AssetEntryImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.sql.Timestamp;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="AssetEntryFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 *
 */
public class AssetEntryFinderImpl
	extends BasePersistenceImpl implements AssetEntryFinder {

	public static String COUNT_BY_AND_TAG_IDS =
		AssetEntryFinder.class.getName() + ".countByAndTagIds";

	public static String COUNT_BY_OR_TAG_IDS =
		AssetEntryFinder.class.getName() + ".countByOrTagIds";

	public static String FIND_BY_AND_CATEGORY_IDS =
		AssetEntryFinder.class.getName() + ".findByAndCategoryIds";

	public static String FIND_BY_AND_TAG_IDS =
		AssetEntryFinder.class.getName() + ".findByAndTagIds";

	public static String FIND_BY_OR_TAG_IDS =
		AssetEntryFinder.class.getName() + ".findByOrTagIds";

	public static String FIND_BY_VIEW_COUNT =
		AssetEntryFinder.class.getName() + ".findByViewCount";

	public static String[] ORDER_BY_COLUMNS = new String[] {
		"title", "createDate", "modifiedDate", "publishDate", "expirationDate",
		"priority", "viewCount"
	};

	public static String[] ORDER_BY_TYPE = new String[] {
		"ASC", "DESC"
	};

	public int countByAndTagIds(
			long groupId, long[] classNameIds, long[] tagIds,
			long[] notTagIds, boolean excludeZeroViewCount, Date publishDate,
			Date expirationDate)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			StringBuilder sb = new StringBuilder();

			sb.append("SELECT COUNT(DISTINCT entryId) AS COUNT_VALUE ");
			sb.append("FROM AssetEntry WHERE");
			sb.append(" (visible = ?)");

			if (tagIds.length > 0) {
				sb.append(" AND AssetEntry.entryId IN (");

				for (int i = 0; i < tagIds.length; i++) {
					sb.append(CustomSQLUtil.get(FIND_BY_AND_TAG_IDS));

					if ((i + 1) < tagIds.length) {
						sb.append(" AND AssetEntry.entryId IN (");
					}
				}

				for (int i = 0; i < tagIds.length; i++) {
					if ((i + 1) < tagIds.length) {
						sb.append(StringPool.CLOSE_PARENTHESIS);
					}
				}

				if (excludeZeroViewCount) {
					sb.append(" AND (AssetEntry.viewCount > 0)");
				}

				sb.append(StringPool.CLOSE_PARENTHESIS);
			}

			if (notTagIds.length > 0) {
				sb.append(" AND (");

				for (int i = 0; i < notTagIds.length; i++) {
					sb.append("AssetEntry.entryId NOT IN (");
					sb.append(CustomSQLUtil.get(FIND_BY_AND_TAG_IDS));
					sb.append(StringPool.CLOSE_PARENTHESIS);

					if ((i + 1) < notTagIds.length) {
						sb.append(" OR ");
					}
				}

				sb.append(StringPool.CLOSE_PARENTHESIS);
			}

			sb.append("[$DATES$]");

			if (groupId > 0) {
				sb.append(" AND (AssetEntry.groupId = ?)");
			}

			sb.append(getClassNameIds(classNameIds));

			String sql = sb.toString();

			sql = getDates(sql, publishDate, expirationDate);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(true);

			setTagIds(qPos, tagIds);
			setTagIds(qPos, notTagIds);
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

	public int countByOrTagIds(
			long groupId, long[] classNameIds, long[] tagIds,
			long[] notTagIds, boolean excludeZeroViewCount, Date publishDate,
			Date expirationDate)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_OR_TAG_IDS);

			sql = StringUtil.replace(
				sql, "[$TAG_ID$]", getTagIds(tagIds, StringPool.EQUAL));

			if (notTagIds.length > 0) {
				StringBuilder sb = new StringBuilder();

				sb.append(" AND (");

				for (int i = 0; i < notTagIds.length; i++) {
					sb.append("AssetEntry.entryId NOT IN (");
					sb.append(CustomSQLUtil.get(FIND_BY_AND_TAG_IDS));
					sb.append(StringPool.CLOSE_PARENTHESIS);

					if ((i + 1) < notTagIds.length) {
						sb.append(" AND ");
					}
				}

				sb.append(StringPool.CLOSE_PARENTHESIS);

				sql = StringUtil.replace(
					sql, "[$NOT_TAG_ID$]", sb.toString());
			}
			else {
				sql = StringUtil.replace(
					sql, "[$NOT_TAG_ID$]", StringPool.BLANK);
			}

			sql = getDates(sql, publishDate, expirationDate);

			sql += " AND (visible = ?)";

			if (groupId > 0) {
				sql += " AND (AssetEntry.groupId = ?)";
			}

			sql += getClassNameIds(classNameIds);

			if (excludeZeroViewCount) {
				sql += " AND (AssetEntry.viewCount > 0)";
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			setTagIds(qPos, tagIds);
			setTagIds(qPos, notTagIds);
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

	public int countEntries(AssetEntryQuery entryQuery)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sqlPrefix = "SELECT COUNT(entryId) AS COUNT_VALUE ";

			SQLQuery q = buildAssetQuerySQL(
				entryQuery, sqlPrefix, true, session);

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

	public int countEntries(
			long groupId, long[] classNameIds, boolean excludeZeroViewCount,
			Date publishDate, Date expirationDate)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			StringBuilder sb = new StringBuilder();

			sb.append("SELECT COUNT(entryId) AS COUNT_VALUE ");
			sb.append("FROM AssetEntry WHERE");
			sb.append(" (visible = ?)");

			if (excludeZeroViewCount) {
				sb.append(" AND (AssetEntry.viewCount > 0)");
			}

			sb.append("[$DATES$]");

			if (groupId > 0) {
				sb.append(" AND (AssetEntry.groupId = ?)");
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

	public List<AssetEntry> findByAndTagIds(
			long groupId, long[] classNameIds, long[] tagIds,
			long[] notTagIds, String orderByCol1, String orderByCol2,
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

			sb.append("SELECT DISTINCT {AssetEntry.*} ");
			sb.append("FROM AssetEntry WHERE");
			sb.append(" (visible = ?)");

			if (tagIds.length > 0) {
				sb.append(" AND AssetEntry.entryId IN (");

				for (int i = 0; i < tagIds.length; i++) {
					sb.append(CustomSQLUtil.get(FIND_BY_AND_TAG_IDS));

					if ((i + 1) < tagIds.length) {
						sb.append(" AND AssetEntry.entryId IN (");
					}
				}

				for (int i = 0; i < tagIds.length; i++) {
					if ((i + 1) < tagIds.length) {
						sb.append(StringPool.CLOSE_PARENTHESIS);
					}
				}

				if (excludeZeroViewCount) {
					sb.append(" AND (AssetEntry.viewCount > 0)");
				}

				sb.append(StringPool.CLOSE_PARENTHESIS);
			}

			if (notTagIds.length > 0) {
				sb.append(" AND (");

				for (int i = 0; i < notTagIds.length; i++) {
					sb.append("AssetEntry.entryId NOT IN (");
					sb.append(CustomSQLUtil.get(FIND_BY_AND_TAG_IDS));
					sb.append(StringPool.CLOSE_PARENTHESIS);

					if ((i + 1) < notTagIds.length) {
						sb.append(" OR ");
					}
				}

				sb.append(StringPool.CLOSE_PARENTHESIS);
			}

			sb.append("[$DATES$]");

			if (groupId > 0) {
				sb.append(" AND (AssetEntry.groupId = ?)");
			}

			sb.append(getClassNameIds(classNameIds));

			sb.append(" ORDER BY AssetEntry.");
			sb.append(orderByCol1);
			sb.append(StringPool.SPACE);
			sb.append(orderByType1);

			if (Validator.isNotNull(orderByCol2) &&
				!orderByCol1.equals(orderByCol2)) {

				sb.append(", AssetEntry.");
				sb.append(orderByCol2);
				sb.append(StringPool.SPACE);
				sb.append(orderByType2);
			}

			String sql = sb.toString();

			sql = getDates(sql, publishDate, expirationDate);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("AssetEntry", AssetEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(true);

			setTagIds(qPos, tagIds);
			setTagIds(qPos, notTagIds);
			setDates(qPos, publishDate, expirationDate);

			if (groupId > 0) {
				setGroupId(qPos, groupId);
			}

			setClassNamedIds(qPos, classNameIds);

			return (List<AssetEntry>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AssetEntry> findByOrTagIds(
			long groupId, long[] classNameIds, long[] tagIds,
			long[] notTagIds, Date publishDate, Date expirationDate )
		throws SystemException {

		return findByOrTagIds(
			groupId, classNameIds, tagIds, notTagIds, null, null, null,
			null, false, publishDate, expirationDate, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	public List<AssetEntry> findByOrTagIds(
			long groupId, long[] classNameIds, long[] tagIds,
			long[] notTagIds, String orderByCol1, String orderByCol2,
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

			String sql = CustomSQLUtil.get(FIND_BY_OR_TAG_IDS);

			sql = StringUtil.replace(
				sql, "[$TAG_ID$]", getTagIds(tagIds, StringPool.EQUAL));

			if (notTagIds.length > 0) {
				StringBuilder sb = new StringBuilder();

				sb.append(" AND (");

				for (int i = 0; i < notTagIds.length; i++) {
					sb.append("AssetEntry.entryId NOT IN (");
					sb.append(CustomSQLUtil.get(FIND_BY_AND_TAG_IDS));
					sb.append(StringPool.CLOSE_PARENTHESIS);

					if ((i + 1) < notTagIds.length) {
						sb.append(" AND ");
					}
				}

				sb.append(StringPool.CLOSE_PARENTHESIS);

				sql = StringUtil.replace(
					sql, "[$NOT_TAG_ID$]", sb.toString());
			}
			else {
				sql = StringUtil.replace(
					sql, "[$NOT_TAG_ID$]", StringPool.BLANK);
			}

			sql = getDates(sql, publishDate, expirationDate);

			sql += " AND (visible = ?)";

			if (groupId > 0) {
				sql += " AND (AssetEntry.groupId = ?)";
			}

			sql += getClassNameIds(classNameIds);

			if (excludeZeroViewCount) {
				sql += " AND (AssetEntry.viewCount > 0)";
			}

			StringBuilder sb = new StringBuilder();

			sb.append(" ORDER BY AssetEntry.");
			sb.append(orderByCol1);
			sb.append(StringPool.SPACE);
			sb.append(orderByType1);

			if (Validator.isNotNull(orderByCol2) &&
				!orderByCol1.equals(orderByCol2)) {

				sb.append(", AssetEntry.");
				sb.append(orderByCol2);
				sb.append(StringPool.SPACE);
				sb.append(orderByType2);
			}

			sql += sb.toString();

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("AssetEntry", AssetEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			setTagIds(qPos, tagIds);
			setTagIds(qPos, notTagIds);
			setDates(qPos, publishDate, expirationDate);

			qPos.add(true);

			if (groupId > 0) {
				setGroupId(qPos, groupId);
			}

			setClassNamedIds(qPos, classNameIds);

			return (List<AssetEntry>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AssetEntry> findByViewCount(
			long[] classNameId, boolean asc, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_VIEW_COUNT);

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < classNameId.length; i++) {
				sb.append("(AssetEntry.classNameId = ?)");

				if ((i+1) < classNameId.length) {
					sb.append(" OR ");
				}
			}

			sql = StringUtil.replace(
				sql, "(AssetEntry.classNameId = ?)", sb.toString());

			sql += " AND (visible = ?)";

			sb = new StringBuilder();

			sb.append(" ORDER BY AssetEntry.viewCount");

			if (asc) {
				sb.append(" ASC");
			}
			else {
				sb.append(" DESC");
			}

			sql += sb.toString();

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("AssetEntry", AssetEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			for (int i = 0; i < classNameId.length; i++) {
				qPos.add(classNameId[i]);
			}

			qPos.add(true);

			return (List<AssetEntry>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AssetEntry> findEntries(AssetEntryQuery entryQuery)
		throws SystemException {

		return findEntries(
			entryQuery, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<AssetEntry> findEntries(
			AssetEntryQuery entryQuery, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sqlPrefix = "SELECT DISTINCT {AssetEntry.*} ";

			SQLQuery q = buildAssetQuerySQL(
				entryQuery, sqlPrefix, false, session);

			return (List<AssetEntry>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AssetEntry> findEntries(
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

			sb.append("SELECT {AssetEntry.*} ");
			sb.append("FROM AssetEntry WHERE");
			sb.append(" (visible = ?)");

			if (excludeZeroViewCount) {
				sb.append(" AND (AssetEntry.viewCount > 0)");
			}

			sb.append("[$DATES$]");

			if (groupId > 0) {
				sb.append(" AND (AssetEntry.groupId = ?)");
			}

			sb.append(getClassNameIds(classNameIds));

			sb.append(" ORDER BY AssetEntry.");
			sb.append(orderByCol1);
			sb.append(StringPool.SPACE);
			sb.append(orderByType1);

			if (Validator.isNotNull(orderByCol2) &&
				!orderByCol1.equals(orderByCol2)) {

				sb.append(", AssetEntry.");
				sb.append(orderByCol2);
				sb.append(StringPool.SPACE);
				sb.append(orderByType2);
			}

			String sql = sb.toString();

			sql = getDates(sql, publishDate, expirationDate);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("AssetEntry", AssetEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(true);

			setDates(qPos, publishDate, expirationDate);

			if (groupId > 0) {
				setGroupId(qPos, groupId);
			}

			setClassNamedIds(qPos, classNameIds);

			return (List<AssetEntry>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected void buildAndCategoriesSQL(
		AssetEntryQuery entryQuery, StringBuilder sb) {

		sb.append(" AND AssetEntry.entryId IN (");

		for (int i = 0; i < entryQuery.getCategoryIds().length; i++) {
			sb.append(CustomSQLUtil.get(FIND_BY_AND_CATEGORY_IDS));

			if ((i + 1) < entryQuery.getCategoryIds().length) {
				sb.append(" AND AssetEntry.entryId IN (");
			}
		}

		for (int i = 0; i < entryQuery.getCategoryIds().length; i++) {
			if ((i + 1) < entryQuery.getCategoryIds().length) {
				sb.append(StringPool.CLOSE_PARENTHESIS);
			}
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);
	}

	protected void buildAndNotCategoriesSQL(
		AssetEntryQuery entryQuery, StringBuilder sb) {

		sb.append(" AND (");

		for (int i = 0; i < entryQuery.getNotCategoryIds().length; i++) {
			sb.append("AssetEntry.entryId NOT IN (");
			sb.append(CustomSQLUtil.get(FIND_BY_AND_CATEGORY_IDS));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			if ((i + 1) < entryQuery.getNotCategoryIds().length) {
				sb.append(" OR ");
			}
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);
	}

	protected void buildAndNotTagsSQL(
		AssetEntryQuery entryQuery, StringBuilder sb) {

		sb.append(" AND (");

		for (int i = 0; i < entryQuery.getNotTagIds().length; i++) {
			sb.append("AssetEntry.entryId NOT IN (");
			sb.append(CustomSQLUtil.get(FIND_BY_AND_TAG_IDS));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			if ((i + 1) < entryQuery.getNotTagIds().length) {
				sb.append(" OR ");
			}
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);
	}

	protected void buildAndTagsSQL(
		AssetEntryQuery entryQuery, StringBuilder sb) {

		sb.append(" AND AssetEntry.entryId IN (");

		for (int i = 0; i < entryQuery.getTagIds().length; i++) {
			sb.append(CustomSQLUtil.get(FIND_BY_AND_TAG_IDS));

			if ((i + 1) < entryQuery.getTagIds().length) {
				sb.append(" AND AssetEntry.entryId IN (");
			}
		}

		for (int i = 0; i < entryQuery.getTagIds().length; i++) {
			if ((i + 1) < entryQuery.getTagIds().length) {
				sb.append(StringPool.CLOSE_PARENTHESIS);
			}
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);
	}

	protected SQLQuery buildAssetQuerySQL(
		AssetEntryQuery entryQuery, String sqlPrefix, boolean count,
		Session session) {

		StringBuilder sb = new StringBuilder();

		sb.append(sqlPrefix);

		sb.append("FROM AssetEntry ");

		if (!entryQuery.isTagIdsAndOperator() &&
			entryQuery.getTagIds().length > 0) {

			sb.append("INNER JOIN ");
			sb.append("AssetEntries_AssetTags ON ");
			sb.append("(AssetEntries_AssetTags.entryId = ");
			sb.append("AssetEntry.entryId) ");
			sb.append("INNER JOIN ");
			sb.append("AssetTag ON ");
			sb.append("(AssetTag.tagId = AssetEntries_AssetTags.tagId) ");
		}

		if (!entryQuery.isCategoryIdsAndOperator() &&
			entryQuery.getCategoryIds().length > 0) {

			sb.append("INNER JOIN ");
			sb.append("AssetEntries_AssetCategories ON ");
			sb.append("(AssetEntries_AssetCategories.entryId = ");
			sb.append("AssetEntry.entryId) ");
			sb.append("INNER JOIN ");
			sb.append("AssetCategory ON ");
			sb.append("(AssetCategory.categoryId = ");
			sb.append("AssetEntries_AssetCategories.categoryId) ");
		}

		sb.append("WHERE (1 = 1)");

		if (entryQuery.getVisible() != null) {
			sb.append(" AND (visible = ?)");
		}

		if (entryQuery.isExcludeZeroViewCount()) {
			sb.append(" AND (AssetEntry.viewCount > 0)");
		}

		// AND conditions

		if (entryQuery.isCategoryIdsAndOperator() &&
			entryQuery.getCategoryIds().length > 0) {

			buildAndCategoriesSQL(entryQuery, sb);
		}

		if (entryQuery.isNotCategoryIdsAndOperator() &&
			entryQuery.getNotCategoryIds().length > 0) {

			buildAndNotCategoriesSQL(entryQuery, sb);
		}

		if (entryQuery.isNotTagIdsAndOperator() &&
			entryQuery.getNotTagIds().length > 0) {

			buildAndNotTagsSQL(entryQuery, sb);
		}

		if (entryQuery.isTagIdsAndOperator() &&
			entryQuery.getTagIds().length > 0) {

			buildAndTagsSQL(entryQuery, sb);
		}

		// OR conditions

		if (!entryQuery.isCategoryIdsAndOperator() &&
			entryQuery.getCategoryIds().length > 0) {

			sb.append(" AND (");
			sb.append(getCategoryIds(
				entryQuery.getCategoryIds(), StringPool.EQUAL));
			sb.append(") ");
		}

		if (!entryQuery.isNotCategoryIdsAndOperator() &&
			entryQuery.getNotCategoryIds().length > 0) {

			sb.append(" AND (");
			sb.append(getNotCategoryIds(entryQuery.getNotCategoryIds()));
			sb.append(") ");
		}

		if (!entryQuery.isTagIdsAndOperator() &&
			entryQuery.getTagIds().length > 0) {

			sb.append(" AND (");
			sb.append(getTagIds(entryQuery.getTagIds(), StringPool.EQUAL));
			sb.append(") ");
		}

		if (!entryQuery.isNotTagIdsAndOperator() &&
			entryQuery.getNotTagIds().length > 0) {

			sb.append(" AND (");
			sb.append(getNotTagIds(entryQuery.getNotTagIds()));
			sb.append(") ");
		}

		// Other conditions

		sb.append("[$DATES$]");

		if (entryQuery.getGroupId() > 0) {
			sb.append(" AND (AssetEntry.groupId = ?)");
		}

		sb.append(getClassNameIds(entryQuery.getClassNameIds()));

		if (!count) {
			sb.append(" ORDER BY AssetEntry.");
			sb.append(entryQuery.getOrderByCol1());
			sb.append(StringPool.SPACE);
			sb.append(entryQuery.getOrderByType1());

			if (Validator.isNotNull(entryQuery.getOrderByCol2()) &&
				!entryQuery.getOrderByCol1().equals(
					entryQuery.getOrderByCol2())) {

				sb.append(", AssetEntry.");
				sb.append(entryQuery.getOrderByCol2());
				sb.append(StringPool.SPACE);
				sb.append(entryQuery.getOrderByType2());
			}
		}

		String sql = sb.toString();

		sql = getDates(
			sql, entryQuery.getPublishDate(), entryQuery.getExpirationDate());

		SQLQuery q = session.createSQLQuery(sql);

		if (count) {
			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);
		}
		else {
			q.addEntity("AssetEntry", AssetEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		if (entryQuery.getVisible() != null) {
			qPos.add(entryQuery.getVisible().booleanValue());
		}

		setCategoryIds(qPos, entryQuery.getCategoryIds());
		setCategoryIds(qPos, entryQuery.getNotCategoryIds());

		setTagIds(qPos, entryQuery.getNotTagIds());
		setTagIds(qPos, entryQuery.getTagIds());

		setDates(
			qPos, entryQuery.getPublishDate(),
			entryQuery.getExpirationDate());

		if (entryQuery.getGroupId() > 0) {
			setGroupId(qPos, entryQuery.getGroupId());
		}

		setClassNamedIds(qPos, entryQuery.getClassNameIds());

		return q;
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

	protected String getCategoryIds(long[] categoryIds, String operator) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < categoryIds.length; i++) {
			sb.append("AssetCategory.categoryId ");
			sb.append(operator);
			sb.append(" ? ");

			if ((i + 1) != categoryIds.length) {
				sb.append("OR ");
			}
		}

		if (sb.length() == 0) {
			sb.append("(1 = 1)");
		}

		return sb.toString();
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

	protected String getNotCategoryIds(long[] notCategoryIds) {
		StringBuilder sb = new StringBuilder();

		sb.append(" AND (");

		for (int i = 0; i < notCategoryIds.length; i++) {
			sb.append("AssetEntry.entryId NOT IN (");
			sb.append(CustomSQLUtil.get(FIND_BY_AND_CATEGORY_IDS));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			if ((i + 1) < notCategoryIds.length) {
				sb.append(" AND ");
			}
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	protected String getNotTagIds(long[] notTagIds) {
		StringBuilder sb = new StringBuilder();

		sb.append(" AND (");

		for (int i = 0; i < notTagIds.length; i++) {
			sb.append("AssetEntry.entryId NOT IN (");
			sb.append(CustomSQLUtil.get(FIND_BY_AND_TAG_IDS));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			if ((i + 1) < notTagIds.length) {
				sb.append(" AND ");
			}
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	protected String getTagIds(long[] tagIds, String operator) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < tagIds.length; i++) {
			sb.append("AssetTag.tagId ");
			sb.append(operator);
			sb.append(" ? ");

			if ((i + 1) != tagIds.length) {
				sb.append("OR ");
			}
		}

		if (sb.length() == 0) {
			sb.append("(1 = 1)");
		}

		return sb.toString();
	}

	protected void setCategoryIds(QueryPos qPos, long[] categoryIds) {
		for (int i = 0; i < categoryIds.length; i++) {
			qPos.add(categoryIds[i]);
		}
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

	protected void setTagIds(QueryPos qPos, long[] tagIds) {
		for (int i = 0; i < tagIds.length; i++) {
			qPos.add(tagIds[i]);
		}
	}

}