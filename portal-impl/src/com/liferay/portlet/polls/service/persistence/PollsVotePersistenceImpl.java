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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.polls.NoSuchVoteException;
import com.liferay.portlet.polls.model.PollsVote;
import com.liferay.portlet.polls.model.impl.PollsVoteImpl;
import com.liferay.portlet.polls.model.impl.PollsVoteModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="PollsVotePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PollsVotePersistence
 * @see       PollsVoteUtil
 * @generated
 */
public class PollsVotePersistenceImpl extends BasePersistenceImpl<PollsVote>
	implements PollsVotePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = PollsVoteImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_QUESTIONID = new FinderPath(PollsVoteModelImpl.ENTITY_CACHE_ENABLED,
			PollsVoteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByQuestionId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_QUESTIONID = new FinderPath(PollsVoteModelImpl.ENTITY_CACHE_ENABLED,
			PollsVoteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByQuestionId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_QUESTIONID = new FinderPath(PollsVoteModelImpl.ENTITY_CACHE_ENABLED,
			PollsVoteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByQuestionId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_CHOICEID = new FinderPath(PollsVoteModelImpl.ENTITY_CACHE_ENABLED,
			PollsVoteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByChoiceId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_CHOICEID = new FinderPath(PollsVoteModelImpl.ENTITY_CACHE_ENABLED,
			PollsVoteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByChoiceId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_CHOICEID = new FinderPath(PollsVoteModelImpl.ENTITY_CACHE_ENABLED,
			PollsVoteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByChoiceId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_Q_U = new FinderPath(PollsVoteModelImpl.ENTITY_CACHE_ENABLED,
			PollsVoteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByQ_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_Q_U = new FinderPath(PollsVoteModelImpl.ENTITY_CACHE_ENABLED,
			PollsVoteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByQ_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(PollsVoteModelImpl.ENTITY_CACHE_ENABLED,
			PollsVoteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PollsVoteModelImpl.ENTITY_CACHE_ENABLED,
			PollsVoteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(PollsVote pollsVote) {
		EntityCacheUtil.putResult(PollsVoteModelImpl.ENTITY_CACHE_ENABLED,
			PollsVoteImpl.class, pollsVote.getPrimaryKey(), pollsVote);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_Q_U,
			new Object[] {
				new Long(pollsVote.getQuestionId()),
				new Long(pollsVote.getUserId())
			}, pollsVote);
	}

	public void cacheResult(List<PollsVote> pollsVotes) {
		for (PollsVote pollsVote : pollsVotes) {
			if (EntityCacheUtil.getResult(
						PollsVoteModelImpl.ENTITY_CACHE_ENABLED,
						PollsVoteImpl.class, pollsVote.getPrimaryKey(), this) == null) {
				cacheResult(pollsVote);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(PollsVoteImpl.class.getName());
		EntityCacheUtil.clearCache(PollsVoteImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public PollsVote create(long voteId) {
		PollsVote pollsVote = new PollsVoteImpl();

		pollsVote.setNew(true);
		pollsVote.setPrimaryKey(voteId);

		return pollsVote;
	}

	public PollsVote remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public PollsVote remove(long voteId)
		throws NoSuchVoteException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PollsVote pollsVote = (PollsVote)session.get(PollsVoteImpl.class,
					new Long(voteId));

			if (pollsVote == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No PollsVote exists with the primary key " +
						voteId);
				}

				throw new NoSuchVoteException(
					"No PollsVote exists with the primary key " + voteId);
			}

			return remove(pollsVote);
		}
		catch (NoSuchVoteException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PollsVote remove(PollsVote pollsVote) throws SystemException {
		for (ModelListener<PollsVote> listener : listeners) {
			listener.onBeforeRemove(pollsVote);
		}

		pollsVote = removeImpl(pollsVote);

		for (ModelListener<PollsVote> listener : listeners) {
			listener.onAfterRemove(pollsVote);
		}

		return pollsVote;
	}

	protected PollsVote removeImpl(PollsVote pollsVote)
		throws SystemException {
		pollsVote = toUnwrappedModel(pollsVote);

		Session session = null;

		try {
			session = openSession();

			if (pollsVote.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(PollsVoteImpl.class,
						pollsVote.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(pollsVote);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		PollsVoteModelImpl pollsVoteModelImpl = (PollsVoteModelImpl)pollsVote;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_Q_U,
			new Object[] {
				new Long(pollsVoteModelImpl.getOriginalQuestionId()),
				new Long(pollsVoteModelImpl.getOriginalUserId())
			});

		EntityCacheUtil.removeResult(PollsVoteModelImpl.ENTITY_CACHE_ENABLED,
			PollsVoteImpl.class, pollsVote.getPrimaryKey());

		return pollsVote;
	}

	public PollsVote updateImpl(
		com.liferay.portlet.polls.model.PollsVote pollsVote, boolean merge)
		throws SystemException {
		pollsVote = toUnwrappedModel(pollsVote);

		boolean isNew = pollsVote.isNew();

		PollsVoteModelImpl pollsVoteModelImpl = (PollsVoteModelImpl)pollsVote;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, pollsVote, merge);

			pollsVote.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(PollsVoteModelImpl.ENTITY_CACHE_ENABLED,
			PollsVoteImpl.class, pollsVote.getPrimaryKey(), pollsVote);

		if (!isNew &&
				((pollsVote.getQuestionId() != pollsVoteModelImpl.getOriginalQuestionId()) ||
				(pollsVote.getUserId() != pollsVoteModelImpl.getOriginalUserId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_Q_U,
				new Object[] {
					new Long(pollsVoteModelImpl.getOriginalQuestionId()),
					new Long(pollsVoteModelImpl.getOriginalUserId())
				});
		}

		if (isNew ||
				((pollsVote.getQuestionId() != pollsVoteModelImpl.getOriginalQuestionId()) ||
				(pollsVote.getUserId() != pollsVoteModelImpl.getOriginalUserId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_Q_U,
				new Object[] {
					new Long(pollsVote.getQuestionId()),
					new Long(pollsVote.getUserId())
				}, pollsVote);
		}

		return pollsVote;
	}

	protected PollsVote toUnwrappedModel(PollsVote pollsVote) {
		if (pollsVote instanceof PollsVoteImpl) {
			return pollsVote;
		}

		PollsVoteImpl pollsVoteImpl = new PollsVoteImpl();

		pollsVoteImpl.setNew(pollsVote.isNew());
		pollsVoteImpl.setPrimaryKey(pollsVote.getPrimaryKey());

		pollsVoteImpl.setVoteId(pollsVote.getVoteId());
		pollsVoteImpl.setUserId(pollsVote.getUserId());
		pollsVoteImpl.setQuestionId(pollsVote.getQuestionId());
		pollsVoteImpl.setChoiceId(pollsVote.getChoiceId());
		pollsVoteImpl.setVoteDate(pollsVote.getVoteDate());

		return pollsVoteImpl;
	}

	public PollsVote findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public PollsVote findByPrimaryKey(long voteId)
		throws NoSuchVoteException, SystemException {
		PollsVote pollsVote = fetchByPrimaryKey(voteId);

		if (pollsVote == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No PollsVote exists with the primary key " + voteId);
			}

			throw new NoSuchVoteException(
				"No PollsVote exists with the primary key " + voteId);
		}

		return pollsVote;
	}

	public PollsVote fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public PollsVote fetchByPrimaryKey(long voteId) throws SystemException {
		PollsVote pollsVote = (PollsVote)EntityCacheUtil.getResult(PollsVoteModelImpl.ENTITY_CACHE_ENABLED,
				PollsVoteImpl.class, voteId, this);

		if (pollsVote == null) {
			Session session = null;

			try {
				session = openSession();

				pollsVote = (PollsVote)session.get(PollsVoteImpl.class,
						new Long(voteId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (pollsVote != null) {
					cacheResult(pollsVote);
				}

				closeSession(session);
			}
		}

		return pollsVote;
	}

	public List<PollsVote> findByQuestionId(long questionId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(questionId) };

		List<PollsVote> list = (List<PollsVote>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_QUESTIONID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_POLLSVOTE_WHERE);

				query.append("pollsVote.questionId = ?");

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
					list = new ArrayList<PollsVote>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_QUESTIONID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<PollsVote> findByQuestionId(long questionId, int start, int end)
		throws SystemException {
		return findByQuestionId(questionId, start, end, null);
	}

	public List<PollsVote> findByQuestionId(long questionId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(questionId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<PollsVote> list = (List<PollsVote>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_QUESTIONID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_POLLSVOTE_WHERE);

				query.append("pollsVote.questionId = ?");

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("pollsVote.");
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

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(questionId);

				list = (List<PollsVote>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PollsVote>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_QUESTIONID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public PollsVote findByQuestionId_First(long questionId,
		OrderByComparator obc) throws NoSuchVoteException, SystemException {
		List<PollsVote> list = findByQuestionId(questionId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No PollsVote exists with the key {");

			msg.append("questionId=" + questionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchVoteException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PollsVote findByQuestionId_Last(long questionId,
		OrderByComparator obc) throws NoSuchVoteException, SystemException {
		int count = countByQuestionId(questionId);

		List<PollsVote> list = findByQuestionId(questionId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No PollsVote exists with the key {");

			msg.append("questionId=" + questionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchVoteException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PollsVote[] findByQuestionId_PrevAndNext(long voteId,
		long questionId, OrderByComparator obc)
		throws NoSuchVoteException, SystemException {
		PollsVote pollsVote = findByPrimaryKey(voteId);

		int count = countByQuestionId(questionId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_POLLSVOTE_WHERE);

			query.append("pollsVote.questionId = ?");

			if (obc != null) {
				query.append(" ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("pollsVote.");
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

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(questionId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					pollsVote);

			PollsVote[] array = new PollsVoteImpl[3];

			array[0] = (PollsVote)objArray[0];
			array[1] = (PollsVote)objArray[1];
			array[2] = (PollsVote)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<PollsVote> findByChoiceId(long choiceId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(choiceId) };

		List<PollsVote> list = (List<PollsVote>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_CHOICEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_POLLSVOTE_WHERE);

				query.append("pollsVote.choiceId = ?");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(choiceId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PollsVote>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_CHOICEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<PollsVote> findByChoiceId(long choiceId, int start, int end)
		throws SystemException {
		return findByChoiceId(choiceId, start, end, null);
	}

	public List<PollsVote> findByChoiceId(long choiceId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(choiceId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<PollsVote> list = (List<PollsVote>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_CHOICEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_POLLSVOTE_WHERE);

				query.append("pollsVote.choiceId = ?");

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("pollsVote.");
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

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(choiceId);

				list = (List<PollsVote>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PollsVote>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_CHOICEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public PollsVote findByChoiceId_First(long choiceId, OrderByComparator obc)
		throws NoSuchVoteException, SystemException {
		List<PollsVote> list = findByChoiceId(choiceId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No PollsVote exists with the key {");

			msg.append("choiceId=" + choiceId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchVoteException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PollsVote findByChoiceId_Last(long choiceId, OrderByComparator obc)
		throws NoSuchVoteException, SystemException {
		int count = countByChoiceId(choiceId);

		List<PollsVote> list = findByChoiceId(choiceId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No PollsVote exists with the key {");

			msg.append("choiceId=" + choiceId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchVoteException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PollsVote[] findByChoiceId_PrevAndNext(long voteId, long choiceId,
		OrderByComparator obc) throws NoSuchVoteException, SystemException {
		PollsVote pollsVote = findByPrimaryKey(voteId);

		int count = countByChoiceId(choiceId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_POLLSVOTE_WHERE);

			query.append("pollsVote.choiceId = ?");

			if (obc != null) {
				query.append(" ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("pollsVote.");
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

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(choiceId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					pollsVote);

			PollsVote[] array = new PollsVoteImpl[3];

			array[0] = (PollsVote)objArray[0];
			array[1] = (PollsVote)objArray[1];
			array[2] = (PollsVote)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PollsVote findByQ_U(long questionId, long userId)
		throws NoSuchVoteException, SystemException {
		PollsVote pollsVote = fetchByQ_U(questionId, userId);

		if (pollsVote == null) {
			StringBundler msg = new StringBundler();

			msg.append("No PollsVote exists with the key {");

			msg.append("questionId=" + questionId);

			msg.append(", ");
			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchVoteException(msg.toString());
		}

		return pollsVote;
	}

	public PollsVote fetchByQ_U(long questionId, long userId)
		throws SystemException {
		return fetchByQ_U(questionId, userId, true);
	}

	public PollsVote fetchByQ_U(long questionId, long userId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(questionId), new Long(userId)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_Q_U,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_POLLSVOTE_WHERE);

				query.append("pollsVote.questionId = ?");

				query.append(" AND ");

				query.append("pollsVote.userId = ?");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(questionId);

				qPos.add(userId);

				List<PollsVote> list = q.list();

				result = list;

				PollsVote pollsVote = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_Q_U,
						finderArgs, list);
				}
				else {
					pollsVote = list.get(0);

					cacheResult(pollsVote);

					if ((pollsVote.getQuestionId() != questionId) ||
							(pollsVote.getUserId() != userId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_Q_U,
							finderArgs, pollsVote);
					}
				}

				return pollsVote;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_Q_U,
						finderArgs, new ArrayList<PollsVote>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (PollsVote)result;
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

	public List<PollsVote> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<PollsVote> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<PollsVote> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<PollsVote> list = (List<PollsVote>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_POLLSVOTE);

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("pollsVote.");
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

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<PollsVote>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<PollsVote>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PollsVote>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByQuestionId(long questionId) throws SystemException {
		for (PollsVote pollsVote : findByQuestionId(questionId)) {
			remove(pollsVote);
		}
	}

	public void removeByChoiceId(long choiceId) throws SystemException {
		for (PollsVote pollsVote : findByChoiceId(choiceId)) {
			remove(pollsVote);
		}
	}

	public void removeByQ_U(long questionId, long userId)
		throws NoSuchVoteException, SystemException {
		PollsVote pollsVote = findByQ_U(questionId, userId);

		remove(pollsVote);
	}

	public void removeAll() throws SystemException {
		for (PollsVote pollsVote : findAll()) {
			remove(pollsVote);
		}
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

				query.append(_SQL_COUNT_POLLSVOTE_WHERE);

				query.append("pollsVote.questionId = ?");

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

	public int countByChoiceId(long choiceId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(choiceId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CHOICEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_POLLSVOTE_WHERE);

				query.append("pollsVote.choiceId = ?");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(choiceId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CHOICEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByQ_U(long questionId, long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(questionId), new Long(userId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_Q_U,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_POLLSVOTE_WHERE);

				query.append("pollsVote.questionId = ?");

				query.append(" AND ");

				query.append("pollsVote.userId = ?");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(questionId);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_Q_U, finderArgs,
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

				Query q = session.createQuery(_SQL_COUNT_POLLSVOTE);

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
						"value.object.listener.com.liferay.portlet.polls.model.PollsVote")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<PollsVote>> listenersList = new ArrayList<ModelListener<PollsVote>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<PollsVote>)Class.forName(
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
	private static final String _SQL_SELECT_POLLSVOTE = "SELECT pollsVote FROM PollsVote pollsVote";
	private static final String _SQL_SELECT_POLLSVOTE_WHERE = "SELECT pollsVote FROM PollsVote pollsVote WHERE ";
	private static final String _SQL_COUNT_POLLSVOTE = "SELECT COUNT(pollsVote) FROM PollsVote pollsVote";
	private static final String _SQL_COUNT_POLLSVOTE_WHERE = "SELECT COUNT(pollsVote) FROM PollsVote pollsVote WHERE ";
	private static Log _log = LogFactoryUtil.getLog(PollsVotePersistenceImpl.class);
}