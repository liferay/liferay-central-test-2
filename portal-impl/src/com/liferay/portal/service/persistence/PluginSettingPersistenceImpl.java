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

import com.liferay.portal.NoSuchPluginSettingException;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.PluginSetting;
import com.liferay.portal.model.impl.PluginSettingImpl;
import com.liferay.portal.model.impl.PluginSettingModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="PluginSettingPersistenceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see    PluginSettingPersistence
 * @see    PluginSettingUtil
 */
public class PluginSettingPersistenceImpl extends BasePersistenceImpl
	implements PluginSettingPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = PluginSettingImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_I_T = new FinderPath(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_I_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_I_T = new FinderPath(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_I_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(PluginSetting pluginSetting) {
		EntityCacheUtil.putResult(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingImpl.class, pluginSetting.getPrimaryKey(),
			pluginSetting);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_I_T,
			new Object[] {
				new Long(pluginSetting.getCompanyId()),
				
			pluginSetting.getPluginId(),
				
			pluginSetting.getPluginType()
			}, pluginSetting);
	}

	public void cacheResult(List<PluginSetting> pluginSettings) {
		for (PluginSetting pluginSetting : pluginSettings) {
			if (EntityCacheUtil.getResult(
						PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
						PluginSettingImpl.class, pluginSetting.getPrimaryKey(),
						this) == null) {
				cacheResult(pluginSetting);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(PluginSettingImpl.class.getName());
		EntityCacheUtil.clearCache(PluginSettingImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public PluginSetting create(long pluginSettingId) {
		PluginSetting pluginSetting = new PluginSettingImpl();

		pluginSetting.setNew(true);
		pluginSetting.setPrimaryKey(pluginSettingId);

		return pluginSetting;
	}

	public PluginSetting remove(long pluginSettingId)
		throws NoSuchPluginSettingException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PluginSetting pluginSetting = (PluginSetting)session.get(PluginSettingImpl.class,
					new Long(pluginSettingId));

			if (pluginSetting == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No PluginSetting exists with the primary key " +
						pluginSettingId);
				}

				throw new NoSuchPluginSettingException(
					"No PluginSetting exists with the primary key " +
					pluginSettingId);
			}

			return remove(pluginSetting);
		}
		catch (NoSuchPluginSettingException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PluginSetting remove(PluginSetting pluginSetting)
		throws SystemException {
		for (ModelListener<PluginSetting> listener : listeners) {
			listener.onBeforeRemove(pluginSetting);
		}

		pluginSetting = removeImpl(pluginSetting);

		for (ModelListener<PluginSetting> listener : listeners) {
			listener.onAfterRemove(pluginSetting);
		}

		return pluginSetting;
	}

	protected PluginSetting removeImpl(PluginSetting pluginSetting)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (pluginSetting.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(PluginSettingImpl.class,
						pluginSetting.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(pluginSetting);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		PluginSettingModelImpl pluginSettingModelImpl = (PluginSettingModelImpl)pluginSetting;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_I_T,
			new Object[] {
				new Long(pluginSettingModelImpl.getOriginalCompanyId()),
				
			pluginSettingModelImpl.getOriginalPluginId(),
				
			pluginSettingModelImpl.getOriginalPluginType()
			});

		EntityCacheUtil.removeResult(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingImpl.class, pluginSetting.getPrimaryKey());

		return pluginSetting;
	}

	/**
	 * @deprecated Use {@link #update(PluginSetting, boolean merge)}.
	 */
	public PluginSetting update(PluginSetting pluginSetting)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(PluginSetting pluginSetting) method. Use update(PluginSetting pluginSetting, boolean merge) instead.");
		}

		return update(pluginSetting, false);
	}

	public PluginSetting update(PluginSetting pluginSetting, boolean merge)
		throws SystemException {
		boolean isNew = pluginSetting.isNew();

		for (ModelListener<PluginSetting> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(pluginSetting);
			}
			else {
				listener.onBeforeUpdate(pluginSetting);
			}
		}

		pluginSetting = updateImpl(pluginSetting, merge);

		for (ModelListener<PluginSetting> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(pluginSetting);
			}
			else {
				listener.onAfterUpdate(pluginSetting);
			}
		}

		return pluginSetting;
	}

	public PluginSetting updateImpl(
		com.liferay.portal.model.PluginSetting pluginSetting, boolean merge)
		throws SystemException {
		boolean isNew = pluginSetting.isNew();

		PluginSettingModelImpl pluginSettingModelImpl = (PluginSettingModelImpl)pluginSetting;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, pluginSetting, merge);

			pluginSetting.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingImpl.class, pluginSetting.getPrimaryKey(),
			pluginSetting);

		if (!isNew &&
				((pluginSetting.getCompanyId() != pluginSettingModelImpl.getOriginalCompanyId()) ||
				!Validator.equals(pluginSetting.getPluginId(),
					pluginSettingModelImpl.getOriginalPluginId()) ||
				!Validator.equals(pluginSetting.getPluginType(),
					pluginSettingModelImpl.getOriginalPluginType()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_I_T,
				new Object[] {
					new Long(pluginSettingModelImpl.getOriginalCompanyId()),
					
				pluginSettingModelImpl.getOriginalPluginId(),
					
				pluginSettingModelImpl.getOriginalPluginType()
				});
		}

		if (isNew ||
				((pluginSetting.getCompanyId() != pluginSettingModelImpl.getOriginalCompanyId()) ||
				!Validator.equals(pluginSetting.getPluginId(),
					pluginSettingModelImpl.getOriginalPluginId()) ||
				!Validator.equals(pluginSetting.getPluginType(),
					pluginSettingModelImpl.getOriginalPluginType()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_I_T,
				new Object[] {
					new Long(pluginSetting.getCompanyId()),
					
				pluginSetting.getPluginId(),
					
				pluginSetting.getPluginType()
				}, pluginSetting);
		}

		return pluginSetting;
	}

	public PluginSetting findByPrimaryKey(long pluginSettingId)
		throws NoSuchPluginSettingException, SystemException {
		PluginSetting pluginSetting = fetchByPrimaryKey(pluginSettingId);

		if (pluginSetting == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No PluginSetting exists with the primary key " +
					pluginSettingId);
			}

			throw new NoSuchPluginSettingException(
				"No PluginSetting exists with the primary key " +
				pluginSettingId);
		}

		return pluginSetting;
	}

	public PluginSetting fetchByPrimaryKey(long pluginSettingId)
		throws SystemException {
		PluginSetting pluginSetting = (PluginSetting)EntityCacheUtil.getResult(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
				PluginSettingImpl.class, pluginSettingId, this);

		if (pluginSetting == null) {
			Session session = null;

			try {
				session = openSession();

				pluginSetting = (PluginSetting)session.get(PluginSettingImpl.class,
						new Long(pluginSettingId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (pluginSetting != null) {
					cacheResult(pluginSetting);
				}

				closeSession(session);
			}
		}

		return pluginSetting;
	}

	public List<PluginSetting> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<PluginSetting> list = (List<PluginSetting>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT pluginSetting FROM PluginSetting pluginSetting WHERE ");

				query.append("pluginSetting.companyId = ?");

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
					list = new ArrayList<PluginSetting>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<PluginSetting> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<PluginSetting> findByCompanyId(long companyId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<PluginSetting> list = (List<PluginSetting>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT pluginSetting FROM PluginSetting pluginSetting WHERE ");

				query.append("pluginSetting.companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("pluginSetting.");
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

				list = (List<PluginSetting>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PluginSetting>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public PluginSetting findByCompanyId_First(long companyId,
		OrderByComparator obc)
		throws NoSuchPluginSettingException, SystemException {
		List<PluginSetting> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No PluginSetting exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPluginSettingException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PluginSetting findByCompanyId_Last(long companyId,
		OrderByComparator obc)
		throws NoSuchPluginSettingException, SystemException {
		int count = countByCompanyId(companyId);

		List<PluginSetting> list = findByCompanyId(companyId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No PluginSetting exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPluginSettingException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PluginSetting[] findByCompanyId_PrevAndNext(long pluginSettingId,
		long companyId, OrderByComparator obc)
		throws NoSuchPluginSettingException, SystemException {
		PluginSetting pluginSetting = findByPrimaryKey(pluginSettingId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT pluginSetting FROM PluginSetting pluginSetting WHERE ");

			query.append("pluginSetting.companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("pluginSetting.");
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
					pluginSetting);

			PluginSetting[] array = new PluginSettingImpl[3];

			array[0] = (PluginSetting)objArray[0];
			array[1] = (PluginSetting)objArray[1];
			array[2] = (PluginSetting)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PluginSetting findByC_I_T(long companyId, String pluginId,
		String pluginType) throws NoSuchPluginSettingException, SystemException {
		PluginSetting pluginSetting = fetchByC_I_T(companyId, pluginId,
				pluginType);

		if (pluginSetting == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No PluginSetting exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("pluginId=" + pluginId);

			msg.append(", ");
			msg.append("pluginType=" + pluginType);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPluginSettingException(msg.toString());
		}

		return pluginSetting;
	}

	public PluginSetting fetchByC_I_T(long companyId, String pluginId,
		String pluginType) throws SystemException {
		return fetchByC_I_T(companyId, pluginId, pluginType, true);
	}

	public PluginSetting fetchByC_I_T(long companyId, String pluginId,
		String pluginType, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				pluginId,
				
				pluginType
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_I_T,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT pluginSetting FROM PluginSetting pluginSetting WHERE ");

				query.append("pluginSetting.companyId = ?");

				query.append(" AND ");

				if (pluginId == null) {
					query.append("pluginSetting.pluginId IS NULL");
				}
				else {
					query.append("pluginSetting.pluginId = ?");
				}

				query.append(" AND ");

				if (pluginType == null) {
					query.append("pluginSetting.pluginType IS NULL");
				}
				else {
					query.append("pluginSetting.pluginType = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (pluginId != null) {
					qPos.add(pluginId);
				}

				if (pluginType != null) {
					qPos.add(pluginType);
				}

				List<PluginSetting> list = q.list();

				result = list;

				PluginSetting pluginSetting = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_I_T,
						finderArgs, list);
				}
				else {
					pluginSetting = list.get(0);

					cacheResult(pluginSetting);

					if ((pluginSetting.getCompanyId() != companyId) ||
							(pluginSetting.getPluginId() == null) ||
							!pluginSetting.getPluginId().equals(pluginId) ||
							(pluginSetting.getPluginType() == null) ||
							!pluginSetting.getPluginType().equals(pluginType)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_I_T,
							finderArgs, pluginSetting);
					}
				}

				return pluginSetting;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_I_T,
						finderArgs, new ArrayList<PluginSetting>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (PluginSetting)result;
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

	public List<PluginSetting> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<PluginSetting> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<PluginSetting> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<PluginSetting> list = (List<PluginSetting>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT pluginSetting FROM PluginSetting pluginSetting ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("pluginSetting.");
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
					list = (List<PluginSetting>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<PluginSetting>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PluginSetting>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (PluginSetting pluginSetting : findByCompanyId(companyId)) {
			remove(pluginSetting);
		}
	}

	public void removeByC_I_T(long companyId, String pluginId, String pluginType)
		throws NoSuchPluginSettingException, SystemException {
		PluginSetting pluginSetting = findByC_I_T(companyId, pluginId,
				pluginType);

		remove(pluginSetting);
	}

	public void removeAll() throws SystemException {
		for (PluginSetting pluginSetting : findAll()) {
			remove(pluginSetting);
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

				query.append("SELECT COUNT(pluginSetting) ");
				query.append("FROM PluginSetting pluginSetting WHERE ");

				query.append("pluginSetting.companyId = ?");

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

	public int countByC_I_T(long companyId, String pluginId, String pluginType)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				pluginId,
				
				pluginType
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_I_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(pluginSetting) ");
				query.append("FROM PluginSetting pluginSetting WHERE ");

				query.append("pluginSetting.companyId = ?");

				query.append(" AND ");

				if (pluginId == null) {
					query.append("pluginSetting.pluginId IS NULL");
				}
				else {
					query.append("pluginSetting.pluginId = ?");
				}

				query.append(" AND ");

				if (pluginType == null) {
					query.append("pluginSetting.pluginType IS NULL");
				}
				else {
					query.append("pluginSetting.pluginType = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (pluginId != null) {
					qPos.add(pluginId);
				}

				if (pluginType != null) {
					qPos.add(pluginType);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_I_T,
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

				Query q = session.createQuery(
						"SELECT COUNT(pluginSetting) FROM PluginSetting pluginSetting");

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
						"value.object.listener.com.liferay.portal.model.PluginSetting")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<PluginSetting>> listenersList = new ArrayList<ModelListener<PluginSetting>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<PluginSetting>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portal.service.persistence.AccountPersistence.impl")
	protected com.liferay.portal.service.persistence.AccountPersistence accountPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.AddressPersistence.impl")
	protected com.liferay.portal.service.persistence.AddressPersistence addressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.BrowserTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ClassNamePersistence.impl")
	protected com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence.impl")
	protected com.liferay.portal.service.persistence.CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ContactPersistence.impl")
	protected com.liferay.portal.service.persistence.ContactPersistence contactPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CountryPersistence.impl")
	protected com.liferay.portal.service.persistence.CountryPersistence countryPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.EmailAddressPersistence.impl")
	protected com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence.impl")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence.impl")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPrototypePersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPrototypePersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ListTypePersistence.impl")
	protected com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.MembershipRequestPersistence.impl")
	protected com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrganizationPersistence.impl")
	protected com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupPermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgLaborPersistence.impl")
	protected com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyRelPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PhonePersistence.impl")
	protected com.liferay.portal.service.persistence.PhonePersistence phonePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PluginSettingPersistence.impl")
	protected com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletPersistence portletPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletItemPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletItemPersistence portletItemPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPreferencesPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RegionPersistence.impl")
	protected com.liferay.portal.service.persistence.RegionPersistence regionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ReleasePersistence.impl")
	protected com.liferay.portal.service.persistence.ReleasePersistence releasePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceActionPersistence.impl")
	protected com.liferay.portal.service.persistence.ResourceActionPersistence resourceActionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceCodePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RolePersistence.impl")
	protected com.liferay.portal.service.persistence.RolePersistence rolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ServiceComponentPersistence.impl")
	protected com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ShardPersistence.impl")
	protected com.liferay.portal.service.persistence.ShardPersistence shardPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.SubscriptionPersistence.impl")
	protected com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupPersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserIdMapperPersistence.impl")
	protected com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPathPersistence.impl")
	protected com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebDAVPropsPersistence.impl")
	protected com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebsitePersistence.impl")
	protected com.liferay.portal.service.persistence.WebsitePersistence websitePersistence;
	private static Log _log = LogFactoryUtil.getLog(PluginSettingPersistenceImpl.class);
}