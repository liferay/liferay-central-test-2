/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchPasswordPolicyRelException;
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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.PasswordPolicyRel;
import com.liferay.portal.model.impl.PasswordPolicyRelImpl;
import com.liferay.portal.model.impl.PasswordPolicyRelModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="PasswordPolicyRelPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PasswordPolicyRelPersistence
 * @see       PasswordPolicyRelUtil
 * @generated
 */
public class PasswordPolicyRelPersistenceImpl extends BasePersistenceImpl<PasswordPolicyRel>
	implements PasswordPolicyRelPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = PasswordPolicyRelImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_PASSWORDPOLICYID = new FinderPath(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyRelModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByPasswordPolicyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_PASSWORDPOLICYID = new FinderPath(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyRelModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByPasswordPolicyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_PASSWORDPOLICYID = new FinderPath(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyRelModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByPasswordPolicyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C = new FinderPath(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyRelModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyRelModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_P_C_C = new FinderPath(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyRelModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByP_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_P_C_C = new FinderPath(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyRelModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByP_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyRelModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyRelModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(PasswordPolicyRel passwordPolicyRel) {
		EntityCacheUtil.putResult(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyRelImpl.class, passwordPolicyRel.getPrimaryKey(),
			passwordPolicyRel);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				new Long(passwordPolicyRel.getClassNameId()),
				new Long(passwordPolicyRel.getClassPK())
			}, passwordPolicyRel);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_P_C_C,
			new Object[] {
				new Long(passwordPolicyRel.getPasswordPolicyId()),
				new Long(passwordPolicyRel.getClassNameId()),
				new Long(passwordPolicyRel.getClassPK())
			}, passwordPolicyRel);
	}

	public void cacheResult(List<PasswordPolicyRel> passwordPolicyRels) {
		for (PasswordPolicyRel passwordPolicyRel : passwordPolicyRels) {
			if (EntityCacheUtil.getResult(
						PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
						PasswordPolicyRelImpl.class,
						passwordPolicyRel.getPrimaryKey(), this) == null) {
				cacheResult(passwordPolicyRel);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(PasswordPolicyRelImpl.class.getName());
		EntityCacheUtil.clearCache(PasswordPolicyRelImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public PasswordPolicyRel create(long passwordPolicyRelId) {
		PasswordPolicyRel passwordPolicyRel = new PasswordPolicyRelImpl();

		passwordPolicyRel.setNew(true);
		passwordPolicyRel.setPrimaryKey(passwordPolicyRelId);

		return passwordPolicyRel;
	}

	public PasswordPolicyRel remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public PasswordPolicyRel remove(long passwordPolicyRelId)
		throws NoSuchPasswordPolicyRelException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PasswordPolicyRel passwordPolicyRel = (PasswordPolicyRel)session.get(PasswordPolicyRelImpl.class,
					new Long(passwordPolicyRelId));

			if (passwordPolicyRel == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						passwordPolicyRelId);
				}

				throw new NoSuchPasswordPolicyRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					passwordPolicyRelId);
			}

			return remove(passwordPolicyRel);
		}
		catch (NoSuchPasswordPolicyRelException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PasswordPolicyRel remove(PasswordPolicyRel passwordPolicyRel)
		throws SystemException {
		for (ModelListener<PasswordPolicyRel> listener : listeners) {
			listener.onBeforeRemove(passwordPolicyRel);
		}

		passwordPolicyRel = removeImpl(passwordPolicyRel);

		for (ModelListener<PasswordPolicyRel> listener : listeners) {
			listener.onAfterRemove(passwordPolicyRel);
		}

		return passwordPolicyRel;
	}

	protected PasswordPolicyRel removeImpl(PasswordPolicyRel passwordPolicyRel)
		throws SystemException {
		passwordPolicyRel = toUnwrappedModel(passwordPolicyRel);

		Session session = null;

		try {
			session = openSession();

			if (passwordPolicyRel.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(PasswordPolicyRelImpl.class,
						passwordPolicyRel.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(passwordPolicyRel);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		PasswordPolicyRelModelImpl passwordPolicyRelModelImpl = (PasswordPolicyRelModelImpl)passwordPolicyRel;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				new Long(passwordPolicyRelModelImpl.getOriginalClassNameId()),
				new Long(passwordPolicyRelModelImpl.getOriginalClassPK())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_P_C_C,
			new Object[] {
				new Long(passwordPolicyRelModelImpl.getOriginalPasswordPolicyId()),
				new Long(passwordPolicyRelModelImpl.getOriginalClassNameId()),
				new Long(passwordPolicyRelModelImpl.getOriginalClassPK())
			});

		EntityCacheUtil.removeResult(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyRelImpl.class, passwordPolicyRel.getPrimaryKey());

		return passwordPolicyRel;
	}

	public PasswordPolicyRel updateImpl(
		com.liferay.portal.model.PasswordPolicyRel passwordPolicyRel,
		boolean merge) throws SystemException {
		passwordPolicyRel = toUnwrappedModel(passwordPolicyRel);

		boolean isNew = passwordPolicyRel.isNew();

		PasswordPolicyRelModelImpl passwordPolicyRelModelImpl = (PasswordPolicyRelModelImpl)passwordPolicyRel;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, passwordPolicyRel, merge);

			passwordPolicyRel.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyRelImpl.class, passwordPolicyRel.getPrimaryKey(),
			passwordPolicyRel);

		if (!isNew &&
				((passwordPolicyRel.getClassNameId() != passwordPolicyRelModelImpl.getOriginalClassNameId()) ||
				(passwordPolicyRel.getClassPK() != passwordPolicyRelModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
				new Object[] {
					new Long(passwordPolicyRelModelImpl.getOriginalClassNameId()),
					new Long(passwordPolicyRelModelImpl.getOriginalClassPK())
				});
		}

		if (isNew ||
				((passwordPolicyRel.getClassNameId() != passwordPolicyRelModelImpl.getOriginalClassNameId()) ||
				(passwordPolicyRel.getClassPK() != passwordPolicyRelModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
				new Object[] {
					new Long(passwordPolicyRel.getClassNameId()),
					new Long(passwordPolicyRel.getClassPK())
				}, passwordPolicyRel);
		}

		if (!isNew &&
				((passwordPolicyRel.getPasswordPolicyId() != passwordPolicyRelModelImpl.getOriginalPasswordPolicyId()) ||
				(passwordPolicyRel.getClassNameId() != passwordPolicyRelModelImpl.getOriginalClassNameId()) ||
				(passwordPolicyRel.getClassPK() != passwordPolicyRelModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_P_C_C,
				new Object[] {
					new Long(passwordPolicyRelModelImpl.getOriginalPasswordPolicyId()),
					new Long(passwordPolicyRelModelImpl.getOriginalClassNameId()),
					new Long(passwordPolicyRelModelImpl.getOriginalClassPK())
				});
		}

		if (isNew ||
				((passwordPolicyRel.getPasswordPolicyId() != passwordPolicyRelModelImpl.getOriginalPasswordPolicyId()) ||
				(passwordPolicyRel.getClassNameId() != passwordPolicyRelModelImpl.getOriginalClassNameId()) ||
				(passwordPolicyRel.getClassPK() != passwordPolicyRelModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_P_C_C,
				new Object[] {
					new Long(passwordPolicyRel.getPasswordPolicyId()),
					new Long(passwordPolicyRel.getClassNameId()),
					new Long(passwordPolicyRel.getClassPK())
				}, passwordPolicyRel);
		}

		return passwordPolicyRel;
	}

	protected PasswordPolicyRel toUnwrappedModel(
		PasswordPolicyRel passwordPolicyRel) {
		if (passwordPolicyRel instanceof PasswordPolicyRelImpl) {
			return passwordPolicyRel;
		}

		PasswordPolicyRelImpl passwordPolicyRelImpl = new PasswordPolicyRelImpl();

		passwordPolicyRelImpl.setNew(passwordPolicyRel.isNew());
		passwordPolicyRelImpl.setPrimaryKey(passwordPolicyRel.getPrimaryKey());

		passwordPolicyRelImpl.setPasswordPolicyRelId(passwordPolicyRel.getPasswordPolicyRelId());
		passwordPolicyRelImpl.setPasswordPolicyId(passwordPolicyRel.getPasswordPolicyId());
		passwordPolicyRelImpl.setClassNameId(passwordPolicyRel.getClassNameId());
		passwordPolicyRelImpl.setClassPK(passwordPolicyRel.getClassPK());

		return passwordPolicyRelImpl;
	}

	public PasswordPolicyRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public PasswordPolicyRel findByPrimaryKey(long passwordPolicyRelId)
		throws NoSuchPasswordPolicyRelException, SystemException {
		PasswordPolicyRel passwordPolicyRel = fetchByPrimaryKey(passwordPolicyRelId);

		if (passwordPolicyRel == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					passwordPolicyRelId);
			}

			throw new NoSuchPasswordPolicyRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				passwordPolicyRelId);
		}

		return passwordPolicyRel;
	}

	public PasswordPolicyRel fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public PasswordPolicyRel fetchByPrimaryKey(long passwordPolicyRelId)
		throws SystemException {
		PasswordPolicyRel passwordPolicyRel = (PasswordPolicyRel)EntityCacheUtil.getResult(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
				PasswordPolicyRelImpl.class, passwordPolicyRelId, this);

		if (passwordPolicyRel == null) {
			Session session = null;

			try {
				session = openSession();

				passwordPolicyRel = (PasswordPolicyRel)session.get(PasswordPolicyRelImpl.class,
						new Long(passwordPolicyRelId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (passwordPolicyRel != null) {
					cacheResult(passwordPolicyRel);
				}

				closeSession(session);
			}
		}

		return passwordPolicyRel;
	}

	public List<PasswordPolicyRel> findByPasswordPolicyId(long passwordPolicyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(passwordPolicyId) };

		List<PasswordPolicyRel> list = (List<PasswordPolicyRel>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_PASSWORDPOLICYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_PASSWORDPOLICYREL_WHERE);

				query.append(_FINDER_COLUMN_PASSWORDPOLICYID_PASSWORDPOLICYID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(passwordPolicyId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PasswordPolicyRel>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_PASSWORDPOLICYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<PasswordPolicyRel> findByPasswordPolicyId(
		long passwordPolicyId, int start, int end) throws SystemException {
		return findByPasswordPolicyId(passwordPolicyId, start, end, null);
	}

	public List<PasswordPolicyRel> findByPasswordPolicyId(
		long passwordPolicyId, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(passwordPolicyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<PasswordPolicyRel> list = (List<PasswordPolicyRel>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_PASSWORDPOLICYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(3 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(2);
				}

				query.append(_SQL_SELECT_PASSWORDPOLICYREL_WHERE);

				query.append(_FINDER_COLUMN_PASSWORDPOLICYID_PASSWORDPOLICYID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(passwordPolicyId);

				list = (List<PasswordPolicyRel>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PasswordPolicyRel>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_PASSWORDPOLICYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public PasswordPolicyRel findByPasswordPolicyId_First(
		long passwordPolicyId, OrderByComparator obc)
		throws NoSuchPasswordPolicyRelException, SystemException {
		List<PasswordPolicyRel> list = findByPasswordPolicyId(passwordPolicyId,
				0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("passwordPolicyId=");
			msg.append(passwordPolicyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPasswordPolicyRelException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PasswordPolicyRel findByPasswordPolicyId_Last(
		long passwordPolicyId, OrderByComparator obc)
		throws NoSuchPasswordPolicyRelException, SystemException {
		int count = countByPasswordPolicyId(passwordPolicyId);

		List<PasswordPolicyRel> list = findByPasswordPolicyId(passwordPolicyId,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("passwordPolicyId=");
			msg.append(passwordPolicyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPasswordPolicyRelException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PasswordPolicyRel[] findByPasswordPolicyId_PrevAndNext(
		long passwordPolicyRelId, long passwordPolicyId, OrderByComparator obc)
		throws NoSuchPasswordPolicyRelException, SystemException {
		PasswordPolicyRel passwordPolicyRel = findByPrimaryKey(passwordPolicyRelId);

		int count = countByPasswordPolicyId(passwordPolicyId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(3 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_PASSWORDPOLICYREL_WHERE);

			query.append(_FINDER_COLUMN_PASSWORDPOLICYID_PASSWORDPOLICYID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(passwordPolicyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					passwordPolicyRel);

			PasswordPolicyRel[] array = new PasswordPolicyRelImpl[3];

			array[0] = (PasswordPolicyRel)objArray[0];
			array[1] = (PasswordPolicyRel)objArray[1];
			array[2] = (PasswordPolicyRel)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PasswordPolicyRel findByC_C(long classNameId, long classPK)
		throws NoSuchPasswordPolicyRelException, SystemException {
		PasswordPolicyRel passwordPolicyRel = fetchByC_C(classNameId, classPK);

		if (passwordPolicyRel == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPasswordPolicyRelException(msg.toString());
		}

		return passwordPolicyRel;
	}

	public PasswordPolicyRel fetchByC_C(long classNameId, long classPK)
		throws SystemException {
		return fetchByC_C(classNameId, classPK, true);
	}

	public PasswordPolicyRel fetchByC_C(long classNameId, long classPK,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_C,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_PASSWORDPOLICYREL_WHERE);

				query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<PasswordPolicyRel> list = q.list();

				result = list;

				PasswordPolicyRel passwordPolicyRel = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
						finderArgs, list);
				}
				else {
					passwordPolicyRel = list.get(0);

					cacheResult(passwordPolicyRel);

					if ((passwordPolicyRel.getClassNameId() != classNameId) ||
							(passwordPolicyRel.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
							finderArgs, passwordPolicyRel);
					}
				}

				return passwordPolicyRel;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
						finderArgs, new ArrayList<PasswordPolicyRel>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (PasswordPolicyRel)result;
			}
		}
	}

	public PasswordPolicyRel findByP_C_C(long passwordPolicyId,
		long classNameId, long classPK)
		throws NoSuchPasswordPolicyRelException, SystemException {
		PasswordPolicyRel passwordPolicyRel = fetchByP_C_C(passwordPolicyId,
				classNameId, classPK);

		if (passwordPolicyRel == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("passwordPolicyId=");
			msg.append(passwordPolicyId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPasswordPolicyRelException(msg.toString());
		}

		return passwordPolicyRel;
	}

	public PasswordPolicyRel fetchByP_C_C(long passwordPolicyId,
		long classNameId, long classPK) throws SystemException {
		return fetchByP_C_C(passwordPolicyId, classNameId, classPK, true);
	}

	public PasswordPolicyRel fetchByP_C_C(long passwordPolicyId,
		long classNameId, long classPK, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(passwordPolicyId), new Long(classNameId),
				new Long(classPK)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_P_C_C,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_PASSWORDPOLICYREL_WHERE);

				query.append(_FINDER_COLUMN_P_C_C_PASSWORDPOLICYID_2);

				query.append(_FINDER_COLUMN_P_C_C_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_P_C_C_CLASSPK_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(passwordPolicyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<PasswordPolicyRel> list = q.list();

				result = list;

				PasswordPolicyRel passwordPolicyRel = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_P_C_C,
						finderArgs, list);
				}
				else {
					passwordPolicyRel = list.get(0);

					cacheResult(passwordPolicyRel);

					if ((passwordPolicyRel.getPasswordPolicyId() != passwordPolicyId) ||
							(passwordPolicyRel.getClassNameId() != classNameId) ||
							(passwordPolicyRel.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_P_C_C,
							finderArgs, passwordPolicyRel);
					}
				}

				return passwordPolicyRel;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_P_C_C,
						finderArgs, new ArrayList<PasswordPolicyRel>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (PasswordPolicyRel)result;
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

	public List<PasswordPolicyRel> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<PasswordPolicyRel> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<PasswordPolicyRel> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<PasswordPolicyRel> list = (List<PasswordPolicyRel>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;
				String sql = null;

				if (obc != null) {
					query = new StringBundler(2 +
							(obc.getOrderByFields().length * 3));

					query.append(_SQL_SELECT_PASSWORDPOLICYREL);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

					sql = query.toString();
				}

				sql = _SQL_SELECT_PASSWORDPOLICYREL;

				Query q = session.createQuery(sql);

				if (obc == null) {
					list = (List<PasswordPolicyRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<PasswordPolicyRel>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PasswordPolicyRel>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByPasswordPolicyId(long passwordPolicyId)
		throws SystemException {
		for (PasswordPolicyRel passwordPolicyRel : findByPasswordPolicyId(
				passwordPolicyId)) {
			remove(passwordPolicyRel);
		}
	}

	public void removeByC_C(long classNameId, long classPK)
		throws NoSuchPasswordPolicyRelException, SystemException {
		PasswordPolicyRel passwordPolicyRel = findByC_C(classNameId, classPK);

		remove(passwordPolicyRel);
	}

	public void removeByP_C_C(long passwordPolicyId, long classNameId,
		long classPK) throws NoSuchPasswordPolicyRelException, SystemException {
		PasswordPolicyRel passwordPolicyRel = findByP_C_C(passwordPolicyId,
				classNameId, classPK);

		remove(passwordPolicyRel);
	}

	public void removeAll() throws SystemException {
		for (PasswordPolicyRel passwordPolicyRel : findAll()) {
			remove(passwordPolicyRel);
		}
	}

	public int countByPasswordPolicyId(long passwordPolicyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(passwordPolicyId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_PASSWORDPOLICYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_PASSWORDPOLICYREL_WHERE);

				query.append(_FINDER_COLUMN_PASSWORDPOLICYID_PASSWORDPOLICYID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(passwordPolicyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PASSWORDPOLICYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_C(long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_PASSWORDPOLICYREL_WHERE);

				query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByP_C_C(long passwordPolicyId, long classNameId,
		long classPK) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(passwordPolicyId), new Long(classNameId),
				new Long(classPK)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_P_C_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_PASSWORDPOLICYREL_WHERE);

				query.append(_FINDER_COLUMN_P_C_C_PASSWORDPOLICYID_2);

				query.append(_FINDER_COLUMN_P_C_C_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_P_C_C_CLASSPK_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(passwordPolicyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_P_C_C,
					finderArgs, count);

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

				Query q = session.createQuery(_SQL_COUNT_PASSWORDPOLICYREL);

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
						"value.object.listener.com.liferay.portal.model.PasswordPolicyRel")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<PasswordPolicyRel>> listenersList = new ArrayList<ModelListener<PasswordPolicyRel>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<PasswordPolicyRel>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portal.service.persistence.AccountPersistence")
	protected com.liferay.portal.service.persistence.AccountPersistence accountPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.AddressPersistence")
	protected com.liferay.portal.service.persistence.AddressPersistence addressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.BrowserTrackerPersistence")
	protected com.liferay.portal.service.persistence.BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ClassNamePersistence")
	protected com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence")
	protected com.liferay.portal.service.persistence.CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ContactPersistence")
	protected com.liferay.portal.service.persistence.ContactPersistence contactPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CountryPersistence")
	protected com.liferay.portal.service.persistence.CountryPersistence countryPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.EmailAddressPersistence")
	protected com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence")
	protected com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPrototypePersistence")
	protected com.liferay.portal.service.persistence.LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPersistence")
	protected com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPrototypePersistence")
	protected com.liferay.portal.service.persistence.LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ListTypePersistence")
	protected com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LockPersistence")
	protected com.liferay.portal.service.persistence.LockPersistence lockPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.MembershipRequestPersistence")
	protected com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrganizationPersistence")
	protected com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupPermissionPersistence")
	protected com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupRolePersistence")
	protected com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgLaborPersistence")
	protected com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyPersistence")
	protected com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyRelPersistence")
	protected com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordTrackerPersistence")
	protected com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PermissionPersistence")
	protected com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PhonePersistence")
	protected com.liferay.portal.service.persistence.PhonePersistence phonePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PluginSettingPersistence")
	protected com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPersistence")
	protected com.liferay.portal.service.persistence.PortletPersistence portletPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletItemPersistence")
	protected com.liferay.portal.service.persistence.PortletItemPersistence portletItemPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPreferencesPersistence")
	protected com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RegionPersistence")
	protected com.liferay.portal.service.persistence.RegionPersistence regionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ReleasePersistence")
	protected com.liferay.portal.service.persistence.ReleasePersistence releasePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceActionPersistence")
	protected com.liferay.portal.service.persistence.ResourceActionPersistence resourceActionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceCodePersistence")
	protected com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePermissionPersistence")
	protected com.liferay.portal.service.persistence.ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RolePersistence")
	protected com.liferay.portal.service.persistence.RolePersistence rolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ServiceComponentPersistence")
	protected com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ShardPersistence")
	protected com.liferay.portal.service.persistence.ShardPersistence shardPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.SubscriptionPersistence")
	protected com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupPersistence")
	protected com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupGroupRolePersistence")
	protected com.liferay.portal.service.persistence.UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupRolePersistence")
	protected com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserIdMapperPersistence")
	protected com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPersistence")
	protected com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPathPersistence")
	protected com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebDAVPropsPersistence")
	protected com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebsitePersistence")
	protected com.liferay.portal.service.persistence.WebsitePersistence websitePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WorkflowDefinitionLinkPersistence")
	protected com.liferay.portal.service.persistence.WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence")
	protected com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static final String _SQL_SELECT_PASSWORDPOLICYREL = "SELECT passwordPolicyRel FROM PasswordPolicyRel passwordPolicyRel";
	private static final String _SQL_SELECT_PASSWORDPOLICYREL_WHERE = "SELECT passwordPolicyRel FROM PasswordPolicyRel passwordPolicyRel WHERE ";
	private static final String _SQL_COUNT_PASSWORDPOLICYREL = "SELECT COUNT(passwordPolicyRel) FROM PasswordPolicyRel passwordPolicyRel";
	private static final String _SQL_COUNT_PASSWORDPOLICYREL_WHERE = "SELECT COUNT(passwordPolicyRel) FROM PasswordPolicyRel passwordPolicyRel WHERE ";
	private static final String _FINDER_COLUMN_PASSWORDPOLICYID_PASSWORDPOLICYID_2 =
		"passwordPolicyRel.passwordPolicyId = ?";
	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "passwordPolicyRel.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "passwordPolicyRel.classPK = ?";
	private static final String _FINDER_COLUMN_P_C_C_PASSWORDPOLICYID_2 = "passwordPolicyRel.passwordPolicyId = ? AND ";
	private static final String _FINDER_COLUMN_P_C_C_CLASSNAMEID_2 = "passwordPolicyRel.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_P_C_C_CLASSPK_2 = "passwordPolicyRel.classPK = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "passwordPolicyRel.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No PasswordPolicyRel exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No PasswordPolicyRel exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(PasswordPolicyRelPersistenceImpl.class);
}