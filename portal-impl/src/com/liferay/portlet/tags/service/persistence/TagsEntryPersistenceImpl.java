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
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.tags.NoSuchEntryException;
import com.liferay.portlet.tags.model.TagsEntry;
import com.liferay.portlet.tags.model.impl.TagsEntryImpl;
import com.liferay.portlet.tags.model.impl.TagsEntryModelImpl;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="TagsEntryPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TagsEntryPersistenceImpl extends BasePersistenceImpl
	implements TagsEntryPersistence {
	public TagsEntry create(long entryId) {
		TagsEntry tagsEntry = new TagsEntryImpl();

		tagsEntry.setNew(true);
		tagsEntry.setPrimaryKey(entryId);

		return tagsEntry;
	}

	public TagsEntry remove(long entryId)
		throws NoSuchEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			TagsEntry tagsEntry = (TagsEntry)session.get(TagsEntryImpl.class,
					new Long(entryId));

			if (tagsEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No TagsEntry exists with the primary key " +
						entryId);
				}

				throw new NoSuchEntryException(
					"No TagsEntry exists with the primary key " + entryId);
			}

			return remove(tagsEntry);
		}
		catch (NoSuchEntryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public TagsEntry remove(TagsEntry tagsEntry) throws SystemException {
		for (ModelListener<TagsEntry> listener : listeners) {
			listener.onBeforeRemove(tagsEntry);
		}

		tagsEntry = removeImpl(tagsEntry);

		for (ModelListener<TagsEntry> listener : listeners) {
			listener.onAfterRemove(tagsEntry);
		}

		return tagsEntry;
	}

	protected TagsEntry removeImpl(TagsEntry tagsEntry)
		throws SystemException {
		try {
			clearTagsAssets.clear(tagsEntry.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_TagsEntries");
		}

		Session session = null;

		try {
			session = openSession();

			if (BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(TagsEntryImpl.class,
						tagsEntry.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(tagsEntry);

			session.flush();

			return tagsEntry;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(TagsEntry.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(TagsEntry tagsEntry, boolean merge)</code>.
	 */
	public TagsEntry update(TagsEntry tagsEntry) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(TagsEntry tagsEntry) method. Use update(TagsEntry tagsEntry, boolean merge) instead.");
		}

		return update(tagsEntry, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        tagsEntry the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when tagsEntry is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public TagsEntry update(TagsEntry tagsEntry, boolean merge)
		throws SystemException {
		boolean isNew = tagsEntry.isNew();

		for (ModelListener<TagsEntry> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(tagsEntry);
			}
			else {
				listener.onBeforeUpdate(tagsEntry);
			}
		}

		tagsEntry = updateImpl(tagsEntry, merge);

		for (ModelListener<TagsEntry> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(tagsEntry);
			}
			else {
				listener.onAfterUpdate(tagsEntry);
			}
		}

		return tagsEntry;
	}

	public TagsEntry updateImpl(
		com.liferay.portlet.tags.model.TagsEntry tagsEntry, boolean merge)
		throws SystemException {
		FinderCacheUtil.clearCache("TagsAssets_TagsEntries");

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, tagsEntry, merge);

			tagsEntry.setNew(false);

			return tagsEntry;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(TagsEntry.class.getName());
		}
	}

	public TagsEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException, SystemException {
		TagsEntry tagsEntry = fetchByPrimaryKey(entryId);

		if (tagsEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No TagsEntry exists with the primary key " +
					entryId);
			}

			throw new NoSuchEntryException(
				"No TagsEntry exists with the primary key " + entryId);
		}

		return tagsEntry;
	}

	public TagsEntry fetchByPrimaryKey(long entryId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (TagsEntry)session.get(TagsEntryImpl.class, new Long(entryId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TagsEntry> findByVocabularyId(long vocabularyId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = TagsEntryModelImpl.CACHE_ENABLED;
		String finderClassName = TagsEntry.class.getName();
		String finderMethodName = "findByVocabularyId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(vocabularyId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.tags.model.TagsEntry WHERE ");

				query.append("vocabularyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(vocabularyId);

				List<TagsEntry> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<TagsEntry>)result;
		}
	}

	public List<TagsEntry> findByVocabularyId(long vocabularyId, int start,
		int end) throws SystemException {
		return findByVocabularyId(vocabularyId, start, end, null);
	}

	public List<TagsEntry> findByVocabularyId(long vocabularyId, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = TagsEntryModelImpl.CACHE_ENABLED;
		String finderClassName = TagsEntry.class.getName();
		String finderMethodName = "findByVocabularyId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(vocabularyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.tags.model.TagsEntry WHERE ");

				query.append("vocabularyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(vocabularyId);

				List<TagsEntry> list = (List<TagsEntry>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<TagsEntry>)result;
		}
	}

	public TagsEntry findByVocabularyId_First(long vocabularyId,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		List<TagsEntry> list = findByVocabularyId(vocabularyId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No TagsEntry exists with the key {");

			msg.append("vocabularyId=" + vocabularyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TagsEntry findByVocabularyId_Last(long vocabularyId,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		int count = countByVocabularyId(vocabularyId);

		List<TagsEntry> list = findByVocabularyId(vocabularyId, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No TagsEntry exists with the key {");

			msg.append("vocabularyId=" + vocabularyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TagsEntry[] findByVocabularyId_PrevAndNext(long entryId,
		long vocabularyId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		TagsEntry tagsEntry = findByPrimaryKey(entryId);

		int count = countByVocabularyId(vocabularyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portlet.tags.model.TagsEntry WHERE ");

			query.append("vocabularyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(vocabularyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					tagsEntry);

			TagsEntry[] array = new TagsEntryImpl[3];

			array[0] = (TagsEntry)objArray[0];
			array[1] = (TagsEntry)objArray[1];
			array[2] = (TagsEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TagsEntry> findByP_V(long parentEntryId, long vocabularyId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = TagsEntryModelImpl.CACHE_ENABLED;
		String finderClassName = TagsEntry.class.getName();
		String finderMethodName = "findByP_V";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(parentEntryId), new Long(vocabularyId)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.tags.model.TagsEntry WHERE ");

				query.append("parentEntryId = ?");

				query.append(" AND ");

				query.append("vocabularyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentEntryId);

				qPos.add(vocabularyId);

				List<TagsEntry> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<TagsEntry>)result;
		}
	}

	public List<TagsEntry> findByP_V(long parentEntryId, long vocabularyId,
		int start, int end) throws SystemException {
		return findByP_V(parentEntryId, vocabularyId, start, end, null);
	}

	public List<TagsEntry> findByP_V(long parentEntryId, long vocabularyId,
		int start, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = TagsEntryModelImpl.CACHE_ENABLED;
		String finderClassName = TagsEntry.class.getName();
		String finderMethodName = "findByP_V";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(parentEntryId), new Long(vocabularyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.tags.model.TagsEntry WHERE ");

				query.append("parentEntryId = ?");

				query.append(" AND ");

				query.append("vocabularyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentEntryId);

				qPos.add(vocabularyId);

				List<TagsEntry> list = (List<TagsEntry>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<TagsEntry>)result;
		}
	}

	public TagsEntry findByP_V_First(long parentEntryId, long vocabularyId,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		List<TagsEntry> list = findByP_V(parentEntryId, vocabularyId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No TagsEntry exists with the key {");

			msg.append("parentEntryId=" + parentEntryId);

			msg.append(", ");
			msg.append("vocabularyId=" + vocabularyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TagsEntry findByP_V_Last(long parentEntryId, long vocabularyId,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		int count = countByP_V(parentEntryId, vocabularyId);

		List<TagsEntry> list = findByP_V(parentEntryId, vocabularyId,
				count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No TagsEntry exists with the key {");

			msg.append("parentEntryId=" + parentEntryId);

			msg.append(", ");
			msg.append("vocabularyId=" + vocabularyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TagsEntry[] findByP_V_PrevAndNext(long entryId, long parentEntryId,
		long vocabularyId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		TagsEntry tagsEntry = findByPrimaryKey(entryId);

		int count = countByP_V(parentEntryId, vocabularyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portlet.tags.model.TagsEntry WHERE ");

			query.append("parentEntryId = ?");

			query.append(" AND ");

			query.append("vocabularyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(parentEntryId);

			qPos.add(vocabularyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					tagsEntry);

			TagsEntry[] array = new TagsEntryImpl[3];

			array[0] = (TagsEntry)objArray[0];
			array[1] = (TagsEntry)objArray[1];
			array[2] = (TagsEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.setLimit(start, end);

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TagsEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<TagsEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<TagsEntry> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = TagsEntryModelImpl.CACHE_ENABLED;
		String finderClassName = TagsEntry.class.getName();
		String finderMethodName = "findAll";
		String[] finderParams = new String[] {
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portlet.tags.model.TagsEntry ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("name ASC");
				}

				Query q = session.createQuery(query.toString());

				List<TagsEntry> list = null;

				if (obc == null) {
					list = (List<TagsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<TagsEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<TagsEntry>)result;
		}
	}

	public void removeByVocabularyId(long vocabularyId)
		throws SystemException {
		for (TagsEntry tagsEntry : findByVocabularyId(vocabularyId)) {
			remove(tagsEntry);
		}
	}

	public void removeByP_V(long parentEntryId, long vocabularyId)
		throws SystemException {
		for (TagsEntry tagsEntry : findByP_V(parentEntryId, vocabularyId)) {
			remove(tagsEntry);
		}
	}

	public void removeAll() throws SystemException {
		for (TagsEntry tagsEntry : findAll()) {
			remove(tagsEntry);
		}
	}

	public int countByVocabularyId(long vocabularyId) throws SystemException {
		boolean finderClassNameCacheEnabled = TagsEntryModelImpl.CACHE_ENABLED;
		String finderClassName = TagsEntry.class.getName();
		String finderMethodName = "countByVocabularyId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(vocabularyId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.tags.model.TagsEntry WHERE ");

				query.append("vocabularyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(vocabularyId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByP_V(long parentEntryId, long vocabularyId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = TagsEntryModelImpl.CACHE_ENABLED;
		String finderClassName = TagsEntry.class.getName();
		String finderMethodName = "countByP_V";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(parentEntryId), new Long(vocabularyId)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.tags.model.TagsEntry WHERE ");

				query.append("parentEntryId = ?");

				query.append(" AND ");

				query.append("vocabularyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentEntryId);

				qPos.add(vocabularyId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countAll() throws SystemException {
		boolean finderClassNameCacheEnabled = TagsEntryModelImpl.CACHE_ENABLED;
		String finderClassName = TagsEntry.class.getName();
		String finderMethodName = "countAll";
		String[] finderParams = new String[] {  };
		Object[] finderArgs = new Object[] {  };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portlet.tags.model.TagsEntry");

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public List<com.liferay.portlet.tags.model.TagsAsset> getTagsAssets(long pk)
		throws SystemException {
		return getTagsAssets(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portlet.tags.model.TagsAsset> getTagsAssets(
		long pk, int start, int end) throws SystemException {
		return getTagsAssets(pk, start, end, null);
	}

	public List<com.liferay.portlet.tags.model.TagsAsset> getTagsAssets(
		long pk, int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = TagsEntryModelImpl.CACHE_ENABLED_TAGSASSETS_TAGSENTRIES;

		String finderClassName = "TagsAssets_TagsEntries";

		String finderMethodName = "getTagsAssets";
		String[] finderParams = new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder sb = new StringBuilder();

				sb.append(_SQL_GETTAGSASSETS);

				if (obc != null) {
					sb.append("ORDER BY ");
					sb.append(obc.getOrderBy());
				}

				String sql = sb.toString();

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("TagsAsset",
					com.liferay.portlet.tags.model.impl.TagsAssetImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				List<com.liferay.portlet.tags.model.TagsAsset> list = (List<com.liferay.portlet.tags.model.TagsAsset>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<com.liferay.portlet.tags.model.TagsAsset>)result;
		}
	}

	public int getTagsAssetsSize(long pk) throws SystemException {
		boolean finderClassNameCacheEnabled = TagsEntryModelImpl.CACHE_ENABLED_TAGSASSETS_TAGSENTRIES;

		String finderClassName = "TagsAssets_TagsEntries";

		String finderMethodName = "getTagsAssetsSize";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(pk) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETTAGSASSETSSIZE);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public boolean containsTagsAsset(long pk, long tagsAssetPK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = TagsEntryModelImpl.CACHE_ENABLED_TAGSASSETS_TAGSENTRIES;

		String finderClassName = "TagsAssets_TagsEntries";

		String finderMethodName = "containsTagsAssets";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(pk), new Long(tagsAssetPK) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			try {
				Boolean value = Boolean.valueOf(containsTagsAsset.contains(pk,
							tagsAssetPK));

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, value);

				return value.booleanValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
		}
		else {
			return ((Boolean)result).booleanValue();
		}
	}

	public boolean containsTagsAssets(long pk) throws SystemException {
		if (getTagsAssetsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addTagsAsset(long pk, long tagsAssetPK)
		throws SystemException {
		try {
			addTagsAsset.add(pk, tagsAssetPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_TagsEntries");
		}
	}

	public void addTagsAsset(long pk,
		com.liferay.portlet.tags.model.TagsAsset tagsAsset)
		throws SystemException {
		try {
			addTagsAsset.add(pk, tagsAsset.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_TagsEntries");
		}
	}

	public void addTagsAssets(long pk, long[] tagsAssetPKs)
		throws SystemException {
		try {
			for (long tagsAssetPK : tagsAssetPKs) {
				addTagsAsset.add(pk, tagsAssetPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_TagsEntries");
		}
	}

	public void addTagsAssets(long pk,
		List<com.liferay.portlet.tags.model.TagsAsset> tagsAssets)
		throws SystemException {
		try {
			for (com.liferay.portlet.tags.model.TagsAsset tagsAsset : tagsAssets) {
				addTagsAsset.add(pk, tagsAsset.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_TagsEntries");
		}
	}

	public void clearTagsAssets(long pk) throws SystemException {
		try {
			clearTagsAssets.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_TagsEntries");
		}
	}

	public void removeTagsAsset(long pk, long tagsAssetPK)
		throws SystemException {
		try {
			removeTagsAsset.remove(pk, tagsAssetPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_TagsEntries");
		}
	}

	public void removeTagsAsset(long pk,
		com.liferay.portlet.tags.model.TagsAsset tagsAsset)
		throws SystemException {
		try {
			removeTagsAsset.remove(pk, tagsAsset.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_TagsEntries");
		}
	}

	public void removeTagsAssets(long pk, long[] tagsAssetPKs)
		throws SystemException {
		try {
			for (long tagsAssetPK : tagsAssetPKs) {
				removeTagsAsset.remove(pk, tagsAssetPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_TagsEntries");
		}
	}

	public void removeTagsAssets(long pk,
		List<com.liferay.portlet.tags.model.TagsAsset> tagsAssets)
		throws SystemException {
		try {
			for (com.liferay.portlet.tags.model.TagsAsset tagsAsset : tagsAssets) {
				removeTagsAsset.remove(pk, tagsAsset.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_TagsEntries");
		}
	}

	public void setTagsAssets(long pk, long[] tagsAssetPKs)
		throws SystemException {
		try {
			clearTagsAssets.clear(pk);

			for (long tagsAssetPK : tagsAssetPKs) {
				addTagsAsset.add(pk, tagsAssetPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_TagsEntries");
		}
	}

	public void setTagsAssets(long pk,
		List<com.liferay.portlet.tags.model.TagsAsset> tagsAssets)
		throws SystemException {
		try {
			clearTagsAssets.clear(pk);

			for (com.liferay.portlet.tags.model.TagsAsset tagsAsset : tagsAssets) {
				addTagsAsset.add(pk, tagsAsset.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_TagsEntries");
		}
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.tags.model.TagsEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<TagsEntry>> listenersList = new ArrayList<ModelListener<TagsEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<TagsEntry>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsTagsAsset = new ContainsTagsAsset(this);

		addTagsAsset = new AddTagsAsset(this);
		clearTagsAssets = new ClearTagsAssets(this);
		removeTagsAsset = new RemoveTagsAsset(this);
	}

	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsAssetPersistence.impl")
	protected com.liferay.portlet.tags.service.persistence.TagsAssetPersistence tagsAssetPersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsEntryPersistence.impl")
	protected com.liferay.portlet.tags.service.persistence.TagsEntryPersistence tagsEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsPropertyPersistence.impl")
	protected com.liferay.portlet.tags.service.persistence.TagsPropertyPersistence tagsPropertyPersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsSourcePersistence.impl")
	protected com.liferay.portlet.tags.service.persistence.TagsSourcePersistence tagsSourcePersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsVocabularyPersistence.impl")
	protected com.liferay.portlet.tags.service.persistence.TagsVocabularyPersistence tagsVocabularyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	protected ContainsTagsAsset containsTagsAsset;
	protected AddTagsAsset addTagsAsset;
	protected ClearTagsAssets clearTagsAssets;
	protected RemoveTagsAsset removeTagsAsset;

	protected class ContainsTagsAsset {
		protected ContainsTagsAsset(TagsEntryPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSTAGSASSET,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long entryId, long assetId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(entryId), new Long(assetId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery _mappingSqlQuery;
	}

	protected class AddTagsAsset {
		protected AddTagsAsset(TagsEntryPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO TagsAssets_TagsEntries (entryId, assetId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long entryId, long assetId)
			throws SystemException {
			if (!_persistenceImpl.containsTagsAsset.contains(entryId, assetId)) {
				ModelListener<com.liferay.portlet.tags.model.TagsAsset>[] tagsAssetListeners =
					tagsAssetPersistence.getListeners();

				for (ModelListener<TagsEntry> listener : listeners) {
					listener.onBeforeAddAssociation(entryId,
						com.liferay.portlet.tags.model.TagsAsset.class.getName(),
						assetId);
				}

				for (ModelListener<com.liferay.portlet.tags.model.TagsAsset> listener : tagsAssetListeners) {
					listener.onBeforeAddAssociation(assetId,
						TagsEntry.class.getName(), entryId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(entryId), new Long(assetId)
					});

				for (ModelListener<TagsEntry> listener : listeners) {
					listener.onAfterAddAssociation(entryId,
						com.liferay.portlet.tags.model.TagsAsset.class.getName(),
						assetId);
				}

				for (ModelListener<com.liferay.portlet.tags.model.TagsAsset> listener : tagsAssetListeners) {
					listener.onAfterAddAssociation(assetId,
						TagsEntry.class.getName(), entryId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private TagsEntryPersistenceImpl _persistenceImpl;
	}

	protected class ClearTagsAssets {
		protected ClearTagsAssets(TagsEntryPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM TagsAssets_TagsEntries WHERE entryId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long entryId) throws SystemException {
			ModelListener<com.liferay.portlet.tags.model.TagsAsset>[] tagsAssetListeners =
				tagsAssetPersistence.getListeners();

			List<com.liferay.portlet.tags.model.TagsAsset> tagsAssets = null;

			if ((listeners.length > 0) || (tagsAssetListeners.length > 0)) {
				tagsAssets = getTagsAssets(entryId);

				for (com.liferay.portlet.tags.model.TagsAsset tagsAsset : tagsAssets) {
					for (ModelListener<TagsEntry> listener : listeners) {
						listener.onBeforeRemoveAssociation(entryId,
							com.liferay.portlet.tags.model.TagsAsset.class.getName(),
							tagsAsset.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.tags.model.TagsAsset> listener : tagsAssetListeners) {
						listener.onBeforeRemoveAssociation(tagsAsset.getPrimaryKey(),
							TagsEntry.class.getName(), entryId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(entryId) });

			if ((listeners.length > 0) || (tagsAssetListeners.length > 0)) {
				for (com.liferay.portlet.tags.model.TagsAsset tagsAsset : tagsAssets) {
					for (ModelListener<TagsEntry> listener : listeners) {
						listener.onAfterRemoveAssociation(entryId,
							com.liferay.portlet.tags.model.TagsAsset.class.getName(),
							tagsAsset.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.tags.model.TagsAsset> listener : tagsAssetListeners) {
						listener.onBeforeRemoveAssociation(tagsAsset.getPrimaryKey(),
							TagsEntry.class.getName(), entryId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveTagsAsset {
		protected RemoveTagsAsset(TagsEntryPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM TagsAssets_TagsEntries WHERE entryId = ? AND assetId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long entryId, long assetId)
			throws SystemException {
			if (_persistenceImpl.containsTagsAsset.contains(entryId, assetId)) {
				ModelListener<com.liferay.portlet.tags.model.TagsAsset>[] tagsAssetListeners =
					tagsAssetPersistence.getListeners();

				for (ModelListener<TagsEntry> listener : listeners) {
					listener.onBeforeRemoveAssociation(entryId,
						com.liferay.portlet.tags.model.TagsAsset.class.getName(),
						assetId);
				}

				for (ModelListener<com.liferay.portlet.tags.model.TagsAsset> listener : tagsAssetListeners) {
					listener.onBeforeRemoveAssociation(assetId,
						TagsEntry.class.getName(), entryId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(entryId), new Long(assetId)
					});

				for (ModelListener<TagsEntry> listener : listeners) {
					listener.onAfterRemoveAssociation(entryId,
						com.liferay.portlet.tags.model.TagsAsset.class.getName(),
						assetId);
				}

				for (ModelListener<com.liferay.portlet.tags.model.TagsAsset> listener : tagsAssetListeners) {
					listener.onAfterRemoveAssociation(assetId,
						TagsEntry.class.getName(), entryId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private TagsEntryPersistenceImpl _persistenceImpl;
	}

	private static final String _SQL_GETTAGSASSETS = "SELECT {TagsAsset.*} FROM TagsAsset INNER JOIN TagsAssets_TagsEntries ON (TagsAssets_TagsEntries.assetId = TagsAsset.assetId) WHERE (TagsAssets_TagsEntries.entryId = ?)";
	private static final String _SQL_GETTAGSASSETSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM TagsAssets_TagsEntries WHERE entryId = ?";
	private static final String _SQL_CONTAINSTAGSASSET = "SELECT COUNT(*) AS COUNT_VALUE FROM TagsAssets_TagsEntries WHERE entryId = ? AND assetId = ?";
	private static Log _log = LogFactoryUtil.getLog(TagsEntryPersistenceImpl.class);
}