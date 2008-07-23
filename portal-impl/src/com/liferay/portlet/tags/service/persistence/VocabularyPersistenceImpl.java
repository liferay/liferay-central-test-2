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

package com.liferay.portlet.tags.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.InitializingBean;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.tags.NoSuchVocabularyException;
import com.liferay.portlet.tags.model.Vocabulary;
import com.liferay.portlet.tags.model.impl.VocabularyImpl;
import com.liferay.portlet.tags.model.impl.VocabularyModelImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="VocabularyPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class VocabularyPersistenceImpl extends BasePersistenceImpl
	implements VocabularyPersistence, InitializingBean {
	public Vocabulary create(long vocabularyId) {
		Vocabulary vocabulary = new VocabularyImpl();

		vocabulary.setNew(true);
		vocabulary.setPrimaryKey(vocabularyId);

		return vocabulary;
	}

	public Vocabulary remove(long vocabularyId)
		throws NoSuchVocabularyException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Vocabulary vocabulary = (Vocabulary)session.get(VocabularyImpl.class,
					new Long(vocabularyId));

			if (vocabulary == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No Vocabulary exists with the primary key " +
						vocabularyId);
				}

				throw new NoSuchVocabularyException(
					"No Vocabulary exists with the primary key " +
					vocabularyId);
			}

			return remove(vocabulary);
		}
		catch (NoSuchVocabularyException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Vocabulary remove(Vocabulary vocabulary) throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(vocabulary);
			}
		}

		vocabulary = removeImpl(vocabulary);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(vocabulary);
			}
		}

		return vocabulary;
	}

	protected Vocabulary removeImpl(Vocabulary vocabulary)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(vocabulary);

			session.flush();

			return vocabulary;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(Vocabulary.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(Vocabulary vocabulary, boolean merge)</code>.
	 */
	public Vocabulary update(Vocabulary vocabulary) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(Vocabulary vocabulary) method. Use update(Vocabulary vocabulary, boolean merge) instead.");
		}

		return update(vocabulary, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        vocabulary the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when vocabulary is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public Vocabulary update(Vocabulary vocabulary, boolean merge)
		throws SystemException {
		boolean isNew = vocabulary.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(vocabulary);
				}
				else {
					listener.onBeforeUpdate(vocabulary);
				}
			}
		}

		vocabulary = updateImpl(vocabulary, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(vocabulary);
				}
				else {
					listener.onAfterUpdate(vocabulary);
				}
			}
		}

		return vocabulary;
	}

	public Vocabulary updateImpl(
		com.liferay.portlet.tags.model.Vocabulary vocabulary, boolean merge)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(vocabulary);
			}
			else {
				if (vocabulary.isNew()) {
					session.save(vocabulary);
				}
			}

			session.flush();

			vocabulary.setNew(false);

			return vocabulary;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(Vocabulary.class.getName());
		}
	}

	public Vocabulary findByPrimaryKey(long vocabularyId)
		throws NoSuchVocabularyException, SystemException {
		Vocabulary vocabulary = fetchByPrimaryKey(vocabularyId);

		if (vocabulary == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No Vocabulary exists with the primary key " +
					vocabularyId);
			}

			throw new NoSuchVocabularyException(
				"No Vocabulary exists with the primary key " + vocabularyId);
		}

		return vocabulary;
	}

	public Vocabulary fetchByPrimaryKey(long vocabularyId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (Vocabulary)session.get(VocabularyImpl.class,
				new Long(vocabularyId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Vocabulary findByC_N(long companyId, String name)
		throws NoSuchVocabularyException, SystemException {
		Vocabulary vocabulary = fetchByC_N(companyId, name);

		if (vocabulary == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Vocabulary exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchVocabularyException(msg.toString());
		}

		return vocabulary;
	}

	public Vocabulary fetchByC_N(long companyId, String name)
		throws SystemException {
		boolean finderClassNameCacheEnabled = VocabularyModelImpl.CACHE_ENABLED;
		String finderClassName = Vocabulary.class.getName();
		String finderMethodName = "fetchByC_N";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(companyId), name };

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
					"FROM com.liferay.portlet.tags.model.Vocabulary WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("name IS NULL");
				}
				else {
					query.append("name = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
				}

				List<Vocabulary> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
				}
				else {
					return list.get(0);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<Vocabulary> list = (List<Vocabulary>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<Vocabulary> findByCompanyId(long companyId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = VocabularyModelImpl.CACHE_ENABLED;
		String finderClassName = Vocabulary.class.getName();
		String finderMethodName = "findByCompanyId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(companyId) };

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
					"FROM com.liferay.portlet.tags.model.Vocabulary WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				List<Vocabulary> list = q.list();

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
			return (List<Vocabulary>)result;
		}
	}

	public List<Vocabulary> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<Vocabulary> findByCompanyId(long companyId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = VocabularyModelImpl.CACHE_ENABLED;
		String finderClassName = Vocabulary.class.getName();
		String finderMethodName = "findByCompanyId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
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
					"FROM com.liferay.portlet.tags.model.Vocabulary WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				List<Vocabulary> list = (List<Vocabulary>)QueryUtil.list(q,
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
			return (List<Vocabulary>)result;
		}
	}

	public Vocabulary findByCompanyId_First(long companyId,
		OrderByComparator obc)
		throws NoSuchVocabularyException, SystemException {
		List<Vocabulary> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Vocabulary exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchVocabularyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Vocabulary findByCompanyId_Last(long companyId, OrderByComparator obc)
		throws NoSuchVocabularyException, SystemException {
		int count = countByCompanyId(companyId);

		List<Vocabulary> list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Vocabulary exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchVocabularyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Vocabulary[] findByCompanyId_PrevAndNext(long vocabularyId,
		long companyId, OrderByComparator obc)
		throws NoSuchVocabularyException, SystemException {
		Vocabulary vocabulary = findByPrimaryKey(vocabularyId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.tags.model.Vocabulary WHERE ");

			query.append("companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					vocabulary);

			Vocabulary[] array = new VocabularyImpl[3];

			array[0] = (Vocabulary)objArray[0];
			array[1] = (Vocabulary)objArray[1];
			array[2] = (Vocabulary)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Vocabulary> findByC_F(long companyId, boolean folksonomy)
		throws SystemException {
		boolean finderClassNameCacheEnabled = VocabularyModelImpl.CACHE_ENABLED;
		String finderClassName = Vocabulary.class.getName();
		String finderMethodName = "findByC_F";
		String[] finderParams = new String[] {
				Long.class.getName(), Boolean.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), Boolean.valueOf(folksonomy)
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
					"FROM com.liferay.portlet.tags.model.Vocabulary WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("folksonomy = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(folksonomy);

				List<Vocabulary> list = q.list();

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
			return (List<Vocabulary>)result;
		}
	}

	public List<Vocabulary> findByC_F(long companyId, boolean folksonomy,
		int start, int end) throws SystemException {
		return findByC_F(companyId, folksonomy, start, end, null);
	}

	public List<Vocabulary> findByC_F(long companyId, boolean folksonomy,
		int start, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = VocabularyModelImpl.CACHE_ENABLED;
		String finderClassName = Vocabulary.class.getName();
		String finderMethodName = "findByC_F";
		String[] finderParams = new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), Boolean.valueOf(folksonomy),
				
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
					"FROM com.liferay.portlet.tags.model.Vocabulary WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("folksonomy = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(folksonomy);

				List<Vocabulary> list = (List<Vocabulary>)QueryUtil.list(q,
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
			return (List<Vocabulary>)result;
		}
	}

	public Vocabulary findByC_F_First(long companyId, boolean folksonomy,
		OrderByComparator obc)
		throws NoSuchVocabularyException, SystemException {
		List<Vocabulary> list = findByC_F(companyId, folksonomy, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Vocabulary exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("folksonomy=" + folksonomy);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchVocabularyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Vocabulary findByC_F_Last(long companyId, boolean folksonomy,
		OrderByComparator obc)
		throws NoSuchVocabularyException, SystemException {
		int count = countByC_F(companyId, folksonomy);

		List<Vocabulary> list = findByC_F(companyId, folksonomy, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Vocabulary exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("folksonomy=" + folksonomy);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchVocabularyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Vocabulary[] findByC_F_PrevAndNext(long vocabularyId,
		long companyId, boolean folksonomy, OrderByComparator obc)
		throws NoSuchVocabularyException, SystemException {
		Vocabulary vocabulary = findByPrimaryKey(vocabularyId);

		int count = countByC_F(companyId, folksonomy);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.tags.model.Vocabulary WHERE ");

			query.append("companyId = ?");

			query.append(" AND ");

			query.append("folksonomy = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			qPos.add(folksonomy);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					vocabulary);

			Vocabulary[] array = new VocabularyImpl[3];

			array[0] = (Vocabulary)objArray[0];
			array[1] = (Vocabulary)objArray[1];
			array[2] = (Vocabulary)objArray[2];

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

	public List<Vocabulary> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Vocabulary> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<Vocabulary> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = VocabularyModelImpl.CACHE_ENABLED;
		String finderClassName = Vocabulary.class.getName();
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

				query.append("FROM com.liferay.portlet.tags.model.Vocabulary ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<Vocabulary> list = (List<Vocabulary>)QueryUtil.list(q,
						getDialect(), start, end);

				if (obc == null) {
					Collections.sort(list);
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
			return (List<Vocabulary>)result;
		}
	}

	public void removeByC_N(long companyId, String name)
		throws NoSuchVocabularyException, SystemException {
		Vocabulary vocabulary = findByC_N(companyId, name);

		remove(vocabulary);
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (Vocabulary vocabulary : findByCompanyId(companyId)) {
			remove(vocabulary);
		}
	}

	public void removeByC_F(long companyId, boolean folksonomy)
		throws SystemException {
		for (Vocabulary vocabulary : findByC_F(companyId, folksonomy)) {
			remove(vocabulary);
		}
	}

	public void removeAll() throws SystemException {
		for (Vocabulary vocabulary : findAll()) {
			remove(vocabulary);
		}
	}

	public int countByC_N(long companyId, String name)
		throws SystemException {
		boolean finderClassNameCacheEnabled = VocabularyModelImpl.CACHE_ENABLED;
		String finderClassName = Vocabulary.class.getName();
		String finderMethodName = "countByC_N";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(companyId), name };

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
					"FROM com.liferay.portlet.tags.model.Vocabulary WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("name IS NULL");
				}
				else {
					query.append("name = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
				}

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

	public int countByCompanyId(long companyId) throws SystemException {
		boolean finderClassNameCacheEnabled = VocabularyModelImpl.CACHE_ENABLED;
		String finderClassName = Vocabulary.class.getName();
		String finderMethodName = "countByCompanyId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(companyId) };

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
					"FROM com.liferay.portlet.tags.model.Vocabulary WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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

	public int countByC_F(long companyId, boolean folksonomy)
		throws SystemException {
		boolean finderClassNameCacheEnabled = VocabularyModelImpl.CACHE_ENABLED;
		String finderClassName = Vocabulary.class.getName();
		String finderMethodName = "countByC_F";
		String[] finderParams = new String[] {
				Long.class.getName(), Boolean.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), Boolean.valueOf(folksonomy)
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
					"FROM com.liferay.portlet.tags.model.Vocabulary WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("folksonomy = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(folksonomy);

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
		boolean finderClassNameCacheEnabled = VocabularyModelImpl.CACHE_ENABLED;
		String finderClassName = Vocabulary.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portlet.tags.model.Vocabulary");

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

	public void registerListener(ModelListener listener) {
		List<ModelListener> listeners = ListUtil.fromArray(_listeners);

		listeners.add(listener);

		_listeners = listeners.toArray(new ModelListener[listeners.size()]);
	}

	public void unregisterListener(ModelListener listener) {
		List<ModelListener> listeners = ListUtil.fromArray(_listeners);

		listeners.remove(listener);

		_listeners = listeners.toArray(new ModelListener[listeners.size()]);
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.tags.model.Vocabulary")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener> listeners = new ArrayList<ModelListener>();

				for (String listenerClassName : listenerClassNames) {
					listeners.add((ModelListener)Class.forName(
							listenerClassName).newInstance());
				}

				_listeners = listeners.toArray(new ModelListener[listeners.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	private static Log _log = LogFactory.getLog(VocabularyPersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}