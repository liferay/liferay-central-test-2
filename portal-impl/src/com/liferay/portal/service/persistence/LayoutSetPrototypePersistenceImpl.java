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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchLayoutSetPrototypeException;
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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.LayoutSetPrototypeImpl;
import com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="LayoutSetPrototypePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutSetPrototypePersistence
 * @see       LayoutSetPrototypeUtil
 * @generated
 */
public class LayoutSetPrototypePersistenceImpl extends BasePersistenceImpl<LayoutSetPrototype>
	implements LayoutSetPrototypePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = LayoutSetPrototypeImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_A = new FinderPath(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_A",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_A = new FinderPath(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_A = new FinderPath(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_A",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(LayoutSetPrototype layoutSetPrototype) {
		EntityCacheUtil.putResult(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeImpl.class, layoutSetPrototype.getPrimaryKey(),
			layoutSetPrototype);
	}

	public void cacheResult(List<LayoutSetPrototype> layoutSetPrototypes) {
		for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
			if (EntityCacheUtil.getResult(
						LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
						LayoutSetPrototypeImpl.class,
						layoutSetPrototype.getPrimaryKey(), this) == null) {
				cacheResult(layoutSetPrototype);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(LayoutSetPrototypeImpl.class.getName());
		EntityCacheUtil.clearCache(LayoutSetPrototypeImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public LayoutSetPrototype create(long layoutSetPrototypeId) {
		LayoutSetPrototype layoutSetPrototype = new LayoutSetPrototypeImpl();

		layoutSetPrototype.setNew(true);
		layoutSetPrototype.setPrimaryKey(layoutSetPrototypeId);

		return layoutSetPrototype;
	}

	public LayoutSetPrototype remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public LayoutSetPrototype remove(long layoutSetPrototypeId)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		Session session = null;

		try {
			session = openSession();

			LayoutSetPrototype layoutSetPrototype = (LayoutSetPrototype)session.get(LayoutSetPrototypeImpl.class,
					new Long(layoutSetPrototypeId));

			if (layoutSetPrototype == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No LayoutSetPrototype exists with the primary key " +
						layoutSetPrototypeId);
				}

				throw new NoSuchLayoutSetPrototypeException(
					"No LayoutSetPrototype exists with the primary key " +
					layoutSetPrototypeId);
			}

			return remove(layoutSetPrototype);
		}
		catch (NoSuchLayoutSetPrototypeException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public LayoutSetPrototype remove(LayoutSetPrototype layoutSetPrototype)
		throws SystemException {
		for (ModelListener<LayoutSetPrototype> listener : listeners) {
			listener.onBeforeRemove(layoutSetPrototype);
		}

		layoutSetPrototype = removeImpl(layoutSetPrototype);

		for (ModelListener<LayoutSetPrototype> listener : listeners) {
			listener.onAfterRemove(layoutSetPrototype);
		}

		return layoutSetPrototype;
	}

	protected LayoutSetPrototype removeImpl(
		LayoutSetPrototype layoutSetPrototype) throws SystemException {
		layoutSetPrototype = toUnwrappedModel(layoutSetPrototype);

		Session session = null;

		try {
			session = openSession();

			if (layoutSetPrototype.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(LayoutSetPrototypeImpl.class,
						layoutSetPrototype.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(layoutSetPrototype);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeImpl.class, layoutSetPrototype.getPrimaryKey());

		return layoutSetPrototype;
	}

	public LayoutSetPrototype updateImpl(
		com.liferay.portal.model.LayoutSetPrototype layoutSetPrototype,
		boolean merge) throws SystemException {
		layoutSetPrototype = toUnwrappedModel(layoutSetPrototype);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, layoutSetPrototype, merge);

			layoutSetPrototype.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeImpl.class, layoutSetPrototype.getPrimaryKey(),
			layoutSetPrototype);

		return layoutSetPrototype;
	}

	protected LayoutSetPrototype toUnwrappedModel(
		LayoutSetPrototype layoutSetPrototype) {
		if (layoutSetPrototype instanceof LayoutSetPrototypeImpl) {
			return layoutSetPrototype;
		}

		LayoutSetPrototypeImpl layoutSetPrototypeImpl = new LayoutSetPrototypeImpl();

		layoutSetPrototypeImpl.setNew(layoutSetPrototype.isNew());
		layoutSetPrototypeImpl.setPrimaryKey(layoutSetPrototype.getPrimaryKey());

		layoutSetPrototypeImpl.setLayoutSetPrototypeId(layoutSetPrototype.getLayoutSetPrototypeId());
		layoutSetPrototypeImpl.setCompanyId(layoutSetPrototype.getCompanyId());
		layoutSetPrototypeImpl.setName(layoutSetPrototype.getName());
		layoutSetPrototypeImpl.setDescription(layoutSetPrototype.getDescription());
		layoutSetPrototypeImpl.setSettings(layoutSetPrototype.getSettings());
		layoutSetPrototypeImpl.setActive(layoutSetPrototype.isActive());

		return layoutSetPrototypeImpl;
	}

	public LayoutSetPrototype findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public LayoutSetPrototype findByPrimaryKey(long layoutSetPrototypeId)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		LayoutSetPrototype layoutSetPrototype = fetchByPrimaryKey(layoutSetPrototypeId);

		if (layoutSetPrototype == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No LayoutSetPrototype exists with the primary key " +
					layoutSetPrototypeId);
			}

			throw new NoSuchLayoutSetPrototypeException(
				"No LayoutSetPrototype exists with the primary key " +
				layoutSetPrototypeId);
		}

		return layoutSetPrototype;
	}

	public LayoutSetPrototype fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public LayoutSetPrototype fetchByPrimaryKey(long layoutSetPrototypeId)
		throws SystemException {
		LayoutSetPrototype layoutSetPrototype = (LayoutSetPrototype)EntityCacheUtil.getResult(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetPrototypeImpl.class, layoutSetPrototypeId, this);

		if (layoutSetPrototype == null) {
			Session session = null;

			try {
				session = openSession();

				layoutSetPrototype = (LayoutSetPrototype)session.get(LayoutSetPrototypeImpl.class,
						new Long(layoutSetPrototypeId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (layoutSetPrototype != null) {
					cacheResult(layoutSetPrototype);
				}

				closeSession(session);
			}
		}

		return layoutSetPrototype;
	}

	public List<LayoutSetPrototype> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<LayoutSetPrototype> list = (List<LayoutSetPrototype>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT layoutSetPrototype FROM LayoutSetPrototype layoutSetPrototype WHERE ");

				query.append("layoutSetPrototype.companyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<LayoutSetPrototype>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<LayoutSetPrototype> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<LayoutSetPrototype> findByCompanyId(long companyId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<LayoutSetPrototype> list = (List<LayoutSetPrototype>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT layoutSetPrototype FROM LayoutSetPrototype layoutSetPrototype WHERE ");

				query.append("layoutSetPrototype.companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("layoutSetPrototype.");
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

				qPos.add(companyId);

				list = (List<LayoutSetPrototype>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<LayoutSetPrototype>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public LayoutSetPrototype findByCompanyId_First(long companyId,
		OrderByComparator obc)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		List<LayoutSetPrototype> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No LayoutSetPrototype exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutSetPrototypeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public LayoutSetPrototype findByCompanyId_Last(long companyId,
		OrderByComparator obc)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		int count = countByCompanyId(companyId);

		List<LayoutSetPrototype> list = findByCompanyId(companyId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No LayoutSetPrototype exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutSetPrototypeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public LayoutSetPrototype[] findByCompanyId_PrevAndNext(
		long layoutSetPrototypeId, long companyId, OrderByComparator obc)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		LayoutSetPrototype layoutSetPrototype = findByPrimaryKey(layoutSetPrototypeId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT layoutSetPrototype FROM LayoutSetPrototype layoutSetPrototype WHERE ");

			query.append("layoutSetPrototype.companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("layoutSetPrototype.");
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

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					layoutSetPrototype);

			LayoutSetPrototype[] array = new LayoutSetPrototypeImpl[3];

			array[0] = (LayoutSetPrototype)objArray[0];
			array[1] = (LayoutSetPrototype)objArray[1];
			array[2] = (LayoutSetPrototype)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<LayoutSetPrototype> findByC_A(long companyId, boolean active)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), Boolean.valueOf(active)
			};

		List<LayoutSetPrototype> list = (List<LayoutSetPrototype>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_A,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT layoutSetPrototype FROM LayoutSetPrototype layoutSetPrototype WHERE ");

				query.append("layoutSetPrototype.companyId = ?");

				query.append(" AND ");

				query.append("layoutSetPrototype.active = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(active);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<LayoutSetPrototype>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_A, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<LayoutSetPrototype> findByC_A(long companyId, boolean active,
		int start, int end) throws SystemException {
		return findByC_A(companyId, active, start, end, null);
	}

	public List<LayoutSetPrototype> findByC_A(long companyId, boolean active,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), Boolean.valueOf(active),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<LayoutSetPrototype> list = (List<LayoutSetPrototype>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_A,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT layoutSetPrototype FROM LayoutSetPrototype layoutSetPrototype WHERE ");

				query.append("layoutSetPrototype.companyId = ?");

				query.append(" AND ");

				query.append("layoutSetPrototype.active = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("layoutSetPrototype.");
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

				qPos.add(companyId);

				qPos.add(active);

				list = (List<LayoutSetPrototype>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<LayoutSetPrototype>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_A,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public LayoutSetPrototype findByC_A_First(long companyId, boolean active,
		OrderByComparator obc)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		List<LayoutSetPrototype> list = findByC_A(companyId, active, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No LayoutSetPrototype exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("active=" + active);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutSetPrototypeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public LayoutSetPrototype findByC_A_Last(long companyId, boolean active,
		OrderByComparator obc)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		int count = countByC_A(companyId, active);

		List<LayoutSetPrototype> list = findByC_A(companyId, active, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No LayoutSetPrototype exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("active=" + active);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutSetPrototypeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public LayoutSetPrototype[] findByC_A_PrevAndNext(
		long layoutSetPrototypeId, long companyId, boolean active,
		OrderByComparator obc)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		LayoutSetPrototype layoutSetPrototype = findByPrimaryKey(layoutSetPrototypeId);

		int count = countByC_A(companyId, active);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT layoutSetPrototype FROM LayoutSetPrototype layoutSetPrototype WHERE ");

			query.append("layoutSetPrototype.companyId = ?");

			query.append(" AND ");

			query.append("layoutSetPrototype.active = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("layoutSetPrototype.");
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

			qPos.add(companyId);

			qPos.add(active);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					layoutSetPrototype);

			LayoutSetPrototype[] array = new LayoutSetPrototypeImpl[3];

			array[0] = (LayoutSetPrototype)objArray[0];
			array[1] = (LayoutSetPrototype)objArray[1];
			array[2] = (LayoutSetPrototype)objArray[2];

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

	public List<LayoutSetPrototype> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<LayoutSetPrototype> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<LayoutSetPrototype> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<LayoutSetPrototype> list = (List<LayoutSetPrototype>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT layoutSetPrototype FROM LayoutSetPrototype layoutSetPrototype ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("layoutSetPrototype.");
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
					list = (List<LayoutSetPrototype>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<LayoutSetPrototype>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<LayoutSetPrototype>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (LayoutSetPrototype layoutSetPrototype : findByCompanyId(companyId)) {
			remove(layoutSetPrototype);
		}
	}

	public void removeByC_A(long companyId, boolean active)
		throws SystemException {
		for (LayoutSetPrototype layoutSetPrototype : findByC_A(companyId, active)) {
			remove(layoutSetPrototype);
		}
	}

	public void removeAll() throws SystemException {
		for (LayoutSetPrototype layoutSetPrototype : findAll()) {
			remove(layoutSetPrototype);
		}
	}

	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(layoutSetPrototype) ");
				query.append(
					"FROM LayoutSetPrototype layoutSetPrototype WHERE ");

				query.append("layoutSetPrototype.companyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COMPANYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_A(long companyId, boolean active)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), Boolean.valueOf(active)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_A,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(layoutSetPrototype) ");
				query.append(
					"FROM LayoutSetPrototype layoutSetPrototype WHERE ");

				query.append("layoutSetPrototype.companyId = ?");

				query.append(" AND ");

				query.append("layoutSetPrototype.active = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(active);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_A, finderArgs,
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

				Query q = session.createQuery(
						"SELECT COUNT(layoutSetPrototype) FROM LayoutSetPrototype layoutSetPrototype");

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
						"value.object.listener.com.liferay.portal.model.LayoutSetPrototype")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<LayoutSetPrototype>> listenersList = new ArrayList<ModelListener<LayoutSetPrototype>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<LayoutSetPrototype>)Class.forName(
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
	private static Log _log = LogFactoryUtil.getLog(LayoutSetPrototypePersistenceImpl.class);
}