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

package com.liferay.portlet.polls.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.polls.NoSuchChoiceException;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.impl.PollsChoiceImpl;
import com.liferay.portlet.polls.model.impl.PollsChoiceModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="PollsChoicePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PollsChoicePersistence
 * @see       PollsChoiceUtil
 * @generated
 */
public class PollsChoicePersistenceImpl extends BasePersistenceImpl<PollsChoice>
	implements PollsChoicePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = PollsChoiceImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_QUESTIONID = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByQuestionId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_QUESTIONID = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByQuestionId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_QUESTIONID = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByQuestionId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_Q_N = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByQ_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_Q_N = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByQ_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(PollsChoice pollsChoice) {
		EntityCacheUtil.putResult(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceImpl.class, pollsChoice.getPrimaryKey(), pollsChoice);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_Q_N,
			new Object[] {
				new Long(pollsChoice.getQuestionId()),
				
			pollsChoice.getName()
			}, pollsChoice);
	}

	public void cacheResult(List<PollsChoice> pollsChoices) {
		for (PollsChoice pollsChoice : pollsChoices) {
			if (EntityCacheUtil.getResult(
						PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
						PollsChoiceImpl.class, pollsChoice.getPrimaryKey(), this) == null) {
				cacheResult(pollsChoice);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(PollsChoiceImpl.class.getName());
		EntityCacheUtil.clearCache(PollsChoiceImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public PollsChoice create(long choiceId) {
		PollsChoice pollsChoice = new PollsChoiceImpl();

		pollsChoice.setNew(true);
		pollsChoice.setPrimaryKey(choiceId);

		String uuid = PortalUUIDUtil.generate();

		pollsChoice.setUuid(uuid);

		return pollsChoice;
	}

	public PollsChoice remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public PollsChoice remove(long choiceId)
		throws NoSuchChoiceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PollsChoice pollsChoice = (PollsChoice)session.get(PollsChoiceImpl.class,
					new Long(choiceId));

			if (pollsChoice == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No PollsChoice exists with the primary key " +
						choiceId);
				}

				throw new NoSuchChoiceException(
					"No PollsChoice exists with the primary key " + choiceId);
			}

			return remove(pollsChoice);
		}
		catch (NoSuchChoiceException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PollsChoice remove(PollsChoice pollsChoice)
		throws SystemException {
		for (ModelListener<PollsChoice> listener : listeners) {
			listener.onBeforeRemove(pollsChoice);
		}

		pollsChoice = removeImpl(pollsChoice);

		for (ModelListener<PollsChoice> listener : listeners) {
			listener.onAfterRemove(pollsChoice);
		}

		return pollsChoice;
	}

	protected PollsChoice removeImpl(PollsChoice pollsChoice)
		throws SystemException {
		pollsChoice = toUnwrappedModel(pollsChoice);

		Session session = null;

		try {
			session = openSession();

			if (pollsChoice.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(PollsChoiceImpl.class,
						pollsChoice.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(pollsChoice);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		PollsChoiceModelImpl pollsChoiceModelImpl = (PollsChoiceModelImpl)pollsChoice;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_Q_N,
			new Object[] {
				new Long(pollsChoiceModelImpl.getOriginalQuestionId()),
				
			pollsChoiceModelImpl.getOriginalName()
			});

		EntityCacheUtil.removeResult(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceImpl.class, pollsChoice.getPrimaryKey());

		return pollsChoice;
	}

	public PollsChoice updateImpl(
		com.liferay.portlet.polls.model.PollsChoice pollsChoice, boolean merge)
		throws SystemException {
		pollsChoice = toUnwrappedModel(pollsChoice);

		boolean isNew = pollsChoice.isNew();

		PollsChoiceModelImpl pollsChoiceModelImpl = (PollsChoiceModelImpl)pollsChoice;

		if (Validator.isNull(pollsChoice.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			pollsChoice.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, pollsChoice, merge);

			pollsChoice.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceImpl.class, pollsChoice.getPrimaryKey(), pollsChoice);

		if (!isNew &&
				((pollsChoice.getQuestionId() != pollsChoiceModelImpl.getOriginalQuestionId()) ||
				!Validator.equals(pollsChoice.getName(),
					pollsChoiceModelImpl.getOriginalName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_Q_N,
				new Object[] {
					new Long(pollsChoiceModelImpl.getOriginalQuestionId()),
					
				pollsChoiceModelImpl.getOriginalName()
				});
		}

		if (isNew ||
				((pollsChoice.getQuestionId() != pollsChoiceModelImpl.getOriginalQuestionId()) ||
				!Validator.equals(pollsChoice.getName(),
					pollsChoiceModelImpl.getOriginalName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_Q_N,
				new Object[] {
					new Long(pollsChoice.getQuestionId()),
					
				pollsChoice.getName()
				}, pollsChoice);
		}

		return pollsChoice;
	}

	protected PollsChoice toUnwrappedModel(PollsChoice pollsChoice) {
		if (pollsChoice instanceof PollsChoiceImpl) {
			return pollsChoice;
		}

		PollsChoiceImpl pollsChoiceImpl = new PollsChoiceImpl();

		pollsChoiceImpl.setNew(pollsChoice.isNew());
		pollsChoiceImpl.setPrimaryKey(pollsChoice.getPrimaryKey());

		pollsChoiceImpl.setUuid(pollsChoice.getUuid());
		pollsChoiceImpl.setChoiceId(pollsChoice.getChoiceId());
		pollsChoiceImpl.setQuestionId(pollsChoice.getQuestionId());
		pollsChoiceImpl.setName(pollsChoice.getName());
		pollsChoiceImpl.setDescription(pollsChoice.getDescription());

		return pollsChoiceImpl;
	}

	public PollsChoice findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public PollsChoice findByPrimaryKey(long choiceId)
		throws NoSuchChoiceException, SystemException {
		PollsChoice pollsChoice = fetchByPrimaryKey(choiceId);

		if (pollsChoice == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No PollsChoice exists with the primary key " +
					choiceId);
			}

			throw new NoSuchChoiceException(
				"No PollsChoice exists with the primary key " + choiceId);
		}

		return pollsChoice;
	}

	public PollsChoice fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public PollsChoice fetchByPrimaryKey(long choiceId)
		throws SystemException {
		PollsChoice pollsChoice = (PollsChoice)EntityCacheUtil.getResult(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
				PollsChoiceImpl.class, choiceId, this);

		if (pollsChoice == null) {
			Session session = null;

			try {
				session = openSession();

				pollsChoice = (PollsChoice)session.get(PollsChoiceImpl.class,
						new Long(choiceId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (pollsChoice != null) {
					cacheResult(pollsChoice);
				}

				closeSession(session);
			}
		}

		return pollsChoice;
	}

	public List<PollsChoice> findByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		List<PollsChoice> list = (List<PollsChoice>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_POLLSCHOICE_WHERE);

				if (uuid == null) {
					query.append(_FINDER_COLUMN_UUID_UUID_1);
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_UUID_UUID_3);
					}
					else {
						query.append(_FINDER_COLUMN_UUID_UUID_2);
					}
				}

				query.append(" ORDER BY ");

				query.append("pollsChoice.questionId ASC, ");
				query.append("pollsChoice.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PollsChoice>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<PollsChoice> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<PollsChoice> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<PollsChoice> list = (List<PollsChoice>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_POLLSCHOICE_WHERE);

				if (uuid == null) {
					query.append(_FINDER_COLUMN_UUID_UUID_1);
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_UUID_UUID_3);
					}
					else {
						query.append(_FINDER_COLUMN_UUID_UUID_2);
					}
				}

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("pollsChoice.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append(" ORDER BY ");

					query.append("pollsChoice.questionId ASC, ");
					query.append("pollsChoice.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<PollsChoice>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PollsChoice>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public PollsChoice findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchChoiceException, SystemException {
		List<PollsChoice> list = findByUuid(uuid, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No PollsChoice exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchChoiceException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PollsChoice findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchChoiceException, SystemException {
		int count = countByUuid(uuid);

		List<PollsChoice> list = findByUuid(uuid, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No PollsChoice exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchChoiceException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PollsChoice[] findByUuid_PrevAndNext(long choiceId, String uuid,
		OrderByComparator obc) throws NoSuchChoiceException, SystemException {
		PollsChoice pollsChoice = findByPrimaryKey(choiceId);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_POLLSCHOICE_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			if (obc != null) {
				query.append(" ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("pollsChoice.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			else {
				query.append(" ORDER BY ");

				query.append("pollsChoice.questionId ASC, ");
				query.append("pollsChoice.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					pollsChoice);

			PollsChoice[] array = new PollsChoiceImpl[3];

			array[0] = (PollsChoice)objArray[0];
			array[1] = (PollsChoice)objArray[1];
			array[2] = (PollsChoice)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<PollsChoice> findByQuestionId(long questionId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(questionId) };

		List<PollsChoice> list = (List<PollsChoice>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_QUESTIONID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_POLLSCHOICE_WHERE);

				query.append(_FINDER_COLUMN_QUESTIONID_QUESTIONID_2);

				query.append(" ORDER BY ");

				query.append("pollsChoice.questionId ASC, ");
				query.append("pollsChoice.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(questionId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PollsChoice>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_QUESTIONID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<PollsChoice> findByQuestionId(long questionId, int start,
		int end) throws SystemException {
		return findByQuestionId(questionId, start, end, null);
	}

	public List<PollsChoice> findByQuestionId(long questionId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(questionId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<PollsChoice> list = (List<PollsChoice>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_QUESTIONID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_POLLSCHOICE_WHERE);

				query.append(_FINDER_COLUMN_QUESTIONID_QUESTIONID_2);

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("pollsChoice.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append(" ORDER BY ");

					query.append("pollsChoice.questionId ASC, ");
					query.append("pollsChoice.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(questionId);

				list = (List<PollsChoice>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PollsChoice>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_QUESTIONID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public PollsChoice findByQuestionId_First(long questionId,
		OrderByComparator obc) throws NoSuchChoiceException, SystemException {
		List<PollsChoice> list = findByQuestionId(questionId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No PollsChoice exists with the key {");

			msg.append("questionId=" + questionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchChoiceException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PollsChoice findByQuestionId_Last(long questionId,
		OrderByComparator obc) throws NoSuchChoiceException, SystemException {
		int count = countByQuestionId(questionId);

		List<PollsChoice> list = findByQuestionId(questionId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No PollsChoice exists with the key {");

			msg.append("questionId=" + questionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchChoiceException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PollsChoice[] findByQuestionId_PrevAndNext(long choiceId,
		long questionId, OrderByComparator obc)
		throws NoSuchChoiceException, SystemException {
		PollsChoice pollsChoice = findByPrimaryKey(choiceId);

		int count = countByQuestionId(questionId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_POLLSCHOICE_WHERE);

			query.append(_FINDER_COLUMN_QUESTIONID_QUESTIONID_2);

			if (obc != null) {
				query.append(" ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("pollsChoice.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			else {
				query.append(" ORDER BY ");

				query.append("pollsChoice.questionId ASC, ");
				query.append("pollsChoice.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(questionId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					pollsChoice);

			PollsChoice[] array = new PollsChoiceImpl[3];

			array[0] = (PollsChoice)objArray[0];
			array[1] = (PollsChoice)objArray[1];
			array[2] = (PollsChoice)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PollsChoice findByQ_N(long questionId, String name)
		throws NoSuchChoiceException, SystemException {
		PollsChoice pollsChoice = fetchByQ_N(questionId, name);

		if (pollsChoice == null) {
			StringBundler msg = new StringBundler();

			msg.append("No PollsChoice exists with the key {");

			msg.append("questionId=" + questionId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchChoiceException(msg.toString());
		}

		return pollsChoice;
	}

	public PollsChoice fetchByQ_N(long questionId, String name)
		throws SystemException {
		return fetchByQ_N(questionId, name, true);
	}

	public PollsChoice fetchByQ_N(long questionId, String name,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(questionId), name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_Q_N,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_POLLSCHOICE_WHERE);

				query.append(_FINDER_COLUMN_Q_N_QUESTIONID_2);

				if (name == null) {
					query.append(_FINDER_COLUMN_Q_N_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_Q_N_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_Q_N_NAME_2);
					}
				}

				query.append(" ORDER BY ");

				query.append("pollsChoice.questionId ASC, ");
				query.append("pollsChoice.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(questionId);

				if (name != null) {
					qPos.add(name);
				}

				List<PollsChoice> list = q.list();

				result = list;

				PollsChoice pollsChoice = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_Q_N,
						finderArgs, list);
				}
				else {
					pollsChoice = list.get(0);

					cacheResult(pollsChoice);

					if ((pollsChoice.getQuestionId() != questionId) ||
							(pollsChoice.getName() == null) ||
							!pollsChoice.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_Q_N,
							finderArgs, pollsChoice);
					}
				}

				return pollsChoice;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_Q_N,
						finderArgs, new ArrayList<PollsChoice>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (PollsChoice)result;
			}
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

	public List<PollsChoice> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<PollsChoice> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<PollsChoice> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<PollsChoice> list = (List<PollsChoice>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_POLLSCHOICE);

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("pollsChoice.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append(" ORDER BY ");

					query.append("pollsChoice.questionId ASC, ");
					query.append("pollsChoice.name ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<PollsChoice>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<PollsChoice>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PollsChoice>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (PollsChoice pollsChoice : findByUuid(uuid)) {
			remove(pollsChoice);
		}
	}

	public void removeByQuestionId(long questionId) throws SystemException {
		for (PollsChoice pollsChoice : findByQuestionId(questionId)) {
			remove(pollsChoice);
		}
	}

	public void removeByQ_N(long questionId, String name)
		throws NoSuchChoiceException, SystemException {
		PollsChoice pollsChoice = findByQ_N(questionId, name);

		remove(pollsChoice);
	}

	public void removeAll() throws SystemException {
		for (PollsChoice pollsChoice : findAll()) {
			remove(pollsChoice);
		}
	}

	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_POLLSCHOICE_WHERE);

				if (uuid == null) {
					query.append(_FINDER_COLUMN_UUID_UUID_1);
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_UUID_UUID_3);
					}
					else {
						query.append(_FINDER_COLUMN_UUID_UUID_2);
					}
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByQuestionId(long questionId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(questionId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_QUESTIONID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_POLLSCHOICE_WHERE);

				query.append(_FINDER_COLUMN_QUESTIONID_QUESTIONID_2);

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(questionId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_QUESTIONID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByQ_N(long questionId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(questionId), name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_Q_N,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_POLLSCHOICE_WHERE);

				query.append(_FINDER_COLUMN_Q_N_QUESTIONID_2);

				if (name == null) {
					query.append(_FINDER_COLUMN_Q_N_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_Q_N_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_Q_N_NAME_2);
					}
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(questionId);

				if (name != null) {
					qPos.add(name);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_Q_N, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_POLLSCHOICE);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.polls.model.PollsChoice")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<PollsChoice>> listenersList = new ArrayList<ModelListener<PollsChoice>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<PollsChoice>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.polls.service.persistence.PollsChoicePersistence")
	protected com.liferay.portlet.polls.service.persistence.PollsChoicePersistence pollsChoicePersistence;
	@BeanReference(name = "com.liferay.portlet.polls.service.persistence.PollsQuestionPersistence")
	protected com.liferay.portlet.polls.service.persistence.PollsQuestionPersistence pollsQuestionPersistence;
	@BeanReference(name = "com.liferay.portlet.polls.service.persistence.PollsVotePersistence")
	protected com.liferay.portlet.polls.service.persistence.PollsVotePersistence pollsVotePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "pollsChoiceuuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "pollsChoice.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(pollsChoiceuuid IS NULL OR pollsChoice.uuid = ?)";
	private static final String _FINDER_COLUMN_QUESTIONID_QUESTIONID_2 = "pollsChoice.questionId = ?";
	private static final String _FINDER_COLUMN_Q_N_QUESTIONID_2 = "pollsChoice.questionId = ? AND ";
	private static final String _FINDER_COLUMN_Q_N_NAME_1 = "pollsChoicename IS NULL";
	private static final String _FINDER_COLUMN_Q_N_NAME_2 = "pollsChoice.name = ?";
	private static final String _FINDER_COLUMN_Q_N_NAME_3 = "(pollsChoicename IS NULL OR pollsChoice.name = ?)";
	private static final String _SQL_SELECT_POLLSCHOICE = "SELECT pollsChoice FROM PollsChoice pollsChoice";
	private static final String _SQL_SELECT_POLLSCHOICE_WHERE = "SELECT pollsChoice FROM PollsChoice pollsChoice WHERE ";
	private static final String _SQL_COUNT_POLLSCHOICE = "SELECT COUNT(pollsChoice) FROM PollsChoice pollsChoice";
	private static final String _SQL_COUNT_POLLSCHOICE_WHERE = "SELECT COUNT(pollsChoice) FROM PollsChoice pollsChoice WHERE ";
	private static Log _log = LogFactoryUtil.getLog(PollsChoicePersistenceImpl.class);
}