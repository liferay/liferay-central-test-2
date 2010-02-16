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
import com.liferay.portal.NoSuchPortletException;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.model.impl.PortletModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="PortletPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletPersistence
 * @see       PortletUtil
 * @generated
 */
public class PortletPersistenceImpl extends BasePersistenceImpl<Portlet>
	implements PortletPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = PortletImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(PortletModelImpl.ENTITY_CACHE_ENABLED,
			PortletModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(PortletModelImpl.ENTITY_CACHE_ENABLED,
			PortletModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(PortletModelImpl.ENTITY_CACHE_ENABLED,
			PortletModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_P = new FinderPath(PortletModelImpl.ENTITY_CACHE_ENABLED,
			PortletModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_P",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_P = new FinderPath(PortletModelImpl.ENTITY_CACHE_ENABLED,
			PortletModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_P",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(PortletModelImpl.ENTITY_CACHE_ENABLED,
			PortletModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PortletModelImpl.ENTITY_CACHE_ENABLED,
			PortletModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(Portlet portlet) {
		EntityCacheUtil.putResult(PortletModelImpl.ENTITY_CACHE_ENABLED,
			PortletImpl.class, portlet.getPrimaryKey(), portlet);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_P,
			new Object[] {
				new Long(portlet.getCompanyId()),
				
			portlet.getPortletId()
			}, portlet);
	}

	public void cacheResult(List<Portlet> portlets) {
		for (Portlet portlet : portlets) {
			if (EntityCacheUtil.getResult(
						PortletModelImpl.ENTITY_CACHE_ENABLED,
						PortletImpl.class, portlet.getPrimaryKey(), this) == null) {
				cacheResult(portlet);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(PortletImpl.class.getName());
		EntityCacheUtil.clearCache(PortletImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public Portlet create(long id) {
		Portlet portlet = new PortletImpl();

		portlet.setNew(true);
		portlet.setPrimaryKey(id);

		return portlet;
	}

	public Portlet remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public Portlet remove(long id)
		throws NoSuchPortletException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Portlet portlet = (Portlet)session.get(PortletImpl.class,
					new Long(id));

			if (portlet == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
				}

				throw new NoSuchPortletException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					id);
			}

			return remove(portlet);
		}
		catch (NoSuchPortletException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Portlet remove(Portlet portlet) throws SystemException {
		for (ModelListener<Portlet> listener : listeners) {
			listener.onBeforeRemove(portlet);
		}

		portlet = removeImpl(portlet);

		for (ModelListener<Portlet> listener : listeners) {
			listener.onAfterRemove(portlet);
		}

		return portlet;
	}

	protected Portlet removeImpl(Portlet portlet) throws SystemException {
		portlet = toUnwrappedModel(portlet);

		Session session = null;

		try {
			session = openSession();

			if (portlet.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(PortletImpl.class,
						portlet.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(portlet);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		PortletModelImpl portletModelImpl = (PortletModelImpl)portlet;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_P,
			new Object[] {
				new Long(portletModelImpl.getOriginalCompanyId()),
				
			portletModelImpl.getOriginalPortletId()
			});

		EntityCacheUtil.removeResult(PortletModelImpl.ENTITY_CACHE_ENABLED,
			PortletImpl.class, portlet.getPrimaryKey());

		return portlet;
	}

	public Portlet updateImpl(com.liferay.portal.model.Portlet portlet,
		boolean merge) throws SystemException {
		portlet = toUnwrappedModel(portlet);

		boolean isNew = portlet.isNew();

		PortletModelImpl portletModelImpl = (PortletModelImpl)portlet;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, portlet, merge);

			portlet.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(PortletModelImpl.ENTITY_CACHE_ENABLED,
			PortletImpl.class, portlet.getPrimaryKey(), portlet);

		if (!isNew &&
				((portlet.getCompanyId() != portletModelImpl.getOriginalCompanyId()) ||
				!Validator.equals(portlet.getPortletId(),
					portletModelImpl.getOriginalPortletId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_P,
				new Object[] {
					new Long(portletModelImpl.getOriginalCompanyId()),
					
				portletModelImpl.getOriginalPortletId()
				});
		}

		if (isNew ||
				((portlet.getCompanyId() != portletModelImpl.getOriginalCompanyId()) ||
				!Validator.equals(portlet.getPortletId(),
					portletModelImpl.getOriginalPortletId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_P,
				new Object[] {
					new Long(portlet.getCompanyId()),
					
				portlet.getPortletId()
				}, portlet);
		}

		return portlet;
	}

	protected Portlet toUnwrappedModel(Portlet portlet) {
		if (portlet instanceof PortletImpl) {
			return portlet;
		}

		PortletImpl portletImpl = new PortletImpl();

		portletImpl.setNew(portlet.isNew());
		portletImpl.setPrimaryKey(portlet.getPrimaryKey());

		portletImpl.setId(portlet.getId());
		portletImpl.setCompanyId(portlet.getCompanyId());
		portletImpl.setPortletId(portlet.getPortletId());
		portletImpl.setRoles(portlet.getRoles());
		portletImpl.setActive(portlet.isActive());

		return portletImpl;
	}

	public Portlet findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Portlet findByPrimaryKey(long id)
		throws NoSuchPortletException, SystemException {
		Portlet portlet = fetchByPrimaryKey(id);

		if (portlet == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
			}

			throw new NoSuchPortletException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				id);
		}

		return portlet;
	}

	public Portlet fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Portlet fetchByPrimaryKey(long id) throws SystemException {
		Portlet portlet = (Portlet)EntityCacheUtil.getResult(PortletModelImpl.ENTITY_CACHE_ENABLED,
				PortletImpl.class, id, this);

		if (portlet == null) {
			Session session = null;

			try {
				session = openSession();

				portlet = (Portlet)session.get(PortletImpl.class, new Long(id));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (portlet != null) {
					cacheResult(portlet);
				}

				closeSession(session);
			}
		}

		return portlet;
	}

	public List<Portlet> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<Portlet> list = (List<Portlet>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_PORTLET_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Portlet>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Portlet> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<Portlet> findByCompanyId(long companyId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Portlet> list = (List<Portlet>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
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

				query.append(_SQL_SELECT_PORTLET_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<Portlet>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Portlet>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Portlet findByCompanyId_First(long companyId, OrderByComparator obc)
		throws NoSuchPortletException, SystemException {
		List<Portlet> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPortletException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Portlet findByCompanyId_Last(long companyId, OrderByComparator obc)
		throws NoSuchPortletException, SystemException {
		int count = countByCompanyId(companyId);

		List<Portlet> list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPortletException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Portlet[] findByCompanyId_PrevAndNext(long id, long companyId,
		OrderByComparator obc) throws NoSuchPortletException, SystemException {
		Portlet portlet = findByPrimaryKey(id);

		int count = countByCompanyId(companyId);

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

			query.append(_SQL_SELECT_PORTLET_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, portlet);

			Portlet[] array = new PortletImpl[3];

			array[0] = (Portlet)objArray[0];
			array[1] = (Portlet)objArray[1];
			array[2] = (Portlet)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Portlet findByC_P(long companyId, String portletId)
		throws NoSuchPortletException, SystemException {
		Portlet portlet = fetchByC_P(companyId, portletId);

		if (portlet == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", portletId=");
			msg.append(portletId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPortletException(msg.toString());
		}

		return portlet;
	}

	public Portlet fetchByC_P(long companyId, String portletId)
		throws SystemException {
		return fetchByC_P(companyId, portletId, true);
	}

	public Portlet fetchByC_P(long companyId, String portletId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId), portletId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_P,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_PORTLET_WHERE);

				query.append(_FINDER_COLUMN_C_P_COMPANYID_2);

				if (portletId == null) {
					query.append(_FINDER_COLUMN_C_P_PORTLETID_1);
				}
				else {
					if (portletId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_C_P_PORTLETID_3);
					}
					else {
						query.append(_FINDER_COLUMN_C_P_PORTLETID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (portletId != null) {
					qPos.add(portletId);
				}

				List<Portlet> list = q.list();

				result = list;

				Portlet portlet = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_P,
						finderArgs, list);
				}
				else {
					portlet = list.get(0);

					cacheResult(portlet);

					if ((portlet.getCompanyId() != companyId) ||
							(portlet.getPortletId() == null) ||
							!portlet.getPortletId().equals(portletId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_P,
							finderArgs, portlet);
					}
				}

				return portlet;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_P,
						finderArgs, new ArrayList<Portlet>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Portlet)result;
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

	public List<Portlet> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Portlet> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<Portlet> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Portlet> list = (List<Portlet>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_PORTLET);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

					sql = query.toString();
				}

				sql = _SQL_SELECT_PORTLET;

				Query q = session.createQuery(sql);

				if (obc == null) {
					list = (List<Portlet>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Portlet>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Portlet>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (Portlet portlet : findByCompanyId(companyId)) {
			remove(portlet);
		}
	}

	public void removeByC_P(long companyId, String portletId)
		throws NoSuchPortletException, SystemException {
		Portlet portlet = findByC_P(companyId, portletId);

		remove(portlet);
	}

	public void removeAll() throws SystemException {
		for (Portlet portlet : findAll()) {
			remove(portlet);
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

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_PORTLET_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

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

	public int countByC_P(long companyId, String portletId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId), portletId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_PORTLET_WHERE);

				query.append(_FINDER_COLUMN_C_P_COMPANYID_2);

				if (portletId == null) {
					query.append(_FINDER_COLUMN_C_P_PORTLETID_1);
				}
				else {
					if (portletId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_C_P_PORTLETID_3);
					}
					else {
						query.append(_FINDER_COLUMN_C_P_PORTLETID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (portletId != null) {
					qPos.add(portletId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_P, finderArgs,
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

				Query q = session.createQuery(_SQL_COUNT_PORTLET);

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
						"value.object.listener.com.liferay.portal.model.Portlet")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Portlet>> listenersList = new ArrayList<ModelListener<Portlet>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Portlet>)Class.forName(
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
	private static final String _SQL_SELECT_PORTLET = "SELECT portlet FROM Portlet portlet";
	private static final String _SQL_SELECT_PORTLET_WHERE = "SELECT portlet FROM Portlet portlet WHERE ";
	private static final String _SQL_COUNT_PORTLET = "SELECT COUNT(portlet) FROM Portlet portlet";
	private static final String _SQL_COUNT_PORTLET_WHERE = "SELECT COUNT(portlet) FROM Portlet portlet WHERE ";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "portlet.companyId = ?";
	private static final String _FINDER_COLUMN_C_P_COMPANYID_2 = "portlet.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_P_PORTLETID_1 = "portlet.portletId IS NULL";
	private static final String _FINDER_COLUMN_C_P_PORTLETID_2 = "portlet.portletId = ?";
	private static final String _FINDER_COLUMN_C_P_PORTLETID_3 = "(portlet.portletId IS NULL OR portlet.portletId = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "portlet.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Portlet exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Portlet exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(PortletPersistenceImpl.class);
}