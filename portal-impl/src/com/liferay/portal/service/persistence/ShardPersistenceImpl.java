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
import com.liferay.portal.NoSuchShardException;
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
import com.liferay.portal.model.Shard;
import com.liferay.portal.model.impl.ShardImpl;
import com.liferay.portal.model.impl.ShardModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="ShardPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShardPersistence
 * @see       ShardUtil
 * @generated
 */
public class ShardPersistenceImpl extends BasePersistenceImpl<Shard>
	implements ShardPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = ShardImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FETCH_BY_NAME = new FinderPath(ShardModelImpl.ENTITY_CACHE_ENABLED,
			ShardModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByName", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_NAME = new FinderPath(ShardModelImpl.ENTITY_CACHE_ENABLED,
			ShardModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByName", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C = new FinderPath(ShardModelImpl.ENTITY_CACHE_ENABLED,
			ShardModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(ShardModelImpl.ENTITY_CACHE_ENABLED,
			ShardModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ShardModelImpl.ENTITY_CACHE_ENABLED,
			ShardModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ShardModelImpl.ENTITY_CACHE_ENABLED,
			ShardModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(Shard shard) {
		EntityCacheUtil.putResult(ShardModelImpl.ENTITY_CACHE_ENABLED,
			ShardImpl.class, shard.getPrimaryKey(), shard);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NAME,
			new Object[] { shard.getName() }, shard);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				new Long(shard.getClassNameId()), new Long(shard.getClassPK())
			}, shard);
	}

	public void cacheResult(List<Shard> shards) {
		for (Shard shard : shards) {
			if (EntityCacheUtil.getResult(ShardModelImpl.ENTITY_CACHE_ENABLED,
						ShardImpl.class, shard.getPrimaryKey(), this) == null) {
				cacheResult(shard);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(ShardImpl.class.getName());
		EntityCacheUtil.clearCache(ShardImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public Shard create(long shardId) {
		Shard shard = new ShardImpl();

		shard.setNew(true);
		shard.setPrimaryKey(shardId);

		return shard;
	}

	public Shard remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public Shard remove(long shardId)
		throws NoSuchShardException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Shard shard = (Shard)session.get(ShardImpl.class, new Long(shardId));

			if (shard == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + shardId);
				}

				throw new NoSuchShardException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					shardId);
			}

			return remove(shard);
		}
		catch (NoSuchShardException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Shard remove(Shard shard) throws SystemException {
		for (ModelListener<Shard> listener : listeners) {
			listener.onBeforeRemove(shard);
		}

		shard = removeImpl(shard);

		for (ModelListener<Shard> listener : listeners) {
			listener.onAfterRemove(shard);
		}

		return shard;
	}

	protected Shard removeImpl(Shard shard) throws SystemException {
		shard = toUnwrappedModel(shard);

		Session session = null;

		try {
			session = openSession();

			if (shard.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(ShardImpl.class,
						shard.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(shard);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		ShardModelImpl shardModelImpl = (ShardModelImpl)shard;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_NAME,
			new Object[] { shardModelImpl.getOriginalName() });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				new Long(shardModelImpl.getOriginalClassNameId()),
				new Long(shardModelImpl.getOriginalClassPK())
			});

		EntityCacheUtil.removeResult(ShardModelImpl.ENTITY_CACHE_ENABLED,
			ShardImpl.class, shard.getPrimaryKey());

		return shard;
	}

	public Shard updateImpl(com.liferay.portal.model.Shard shard, boolean merge)
		throws SystemException {
		shard = toUnwrappedModel(shard);

		boolean isNew = shard.isNew();

		ShardModelImpl shardModelImpl = (ShardModelImpl)shard;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, shard, merge);

			shard.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(ShardModelImpl.ENTITY_CACHE_ENABLED,
			ShardImpl.class, shard.getPrimaryKey(), shard);

		if (!isNew &&
				(!Validator.equals(shard.getName(),
					shardModelImpl.getOriginalName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_NAME,
				new Object[] { shardModelImpl.getOriginalName() });
		}

		if (isNew ||
				(!Validator.equals(shard.getName(),
					shardModelImpl.getOriginalName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NAME,
				new Object[] { shard.getName() }, shard);
		}

		if (!isNew &&
				((shard.getClassNameId() != shardModelImpl.getOriginalClassNameId()) ||
				(shard.getClassPK() != shardModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
				new Object[] {
					new Long(shardModelImpl.getOriginalClassNameId()),
					new Long(shardModelImpl.getOriginalClassPK())
				});
		}

		if (isNew ||
				((shard.getClassNameId() != shardModelImpl.getOriginalClassNameId()) ||
				(shard.getClassPK() != shardModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
				new Object[] {
					new Long(shard.getClassNameId()),
					new Long(shard.getClassPK())
				}, shard);
		}

		return shard;
	}

	protected Shard toUnwrappedModel(Shard shard) {
		if (shard instanceof ShardImpl) {
			return shard;
		}

		ShardImpl shardImpl = new ShardImpl();

		shardImpl.setNew(shard.isNew());
		shardImpl.setPrimaryKey(shard.getPrimaryKey());

		shardImpl.setShardId(shard.getShardId());
		shardImpl.setClassNameId(shard.getClassNameId());
		shardImpl.setClassPK(shard.getClassPK());
		shardImpl.setName(shard.getName());

		return shardImpl;
	}

	public Shard findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Shard findByPrimaryKey(long shardId)
		throws NoSuchShardException, SystemException {
		Shard shard = fetchByPrimaryKey(shardId);

		if (shard == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + shardId);
			}

			throw new NoSuchShardException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				shardId);
		}

		return shard;
	}

	public Shard fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Shard fetchByPrimaryKey(long shardId) throws SystemException {
		Shard shard = (Shard)EntityCacheUtil.getResult(ShardModelImpl.ENTITY_CACHE_ENABLED,
				ShardImpl.class, shardId, this);

		if (shard == null) {
			Session session = null;

			try {
				session = openSession();

				shard = (Shard)session.get(ShardImpl.class, new Long(shardId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (shard != null) {
					cacheResult(shard);
				}

				closeSession(session);
			}
		}

		return shard;
	}

	public Shard findByName(String name)
		throws NoSuchShardException, SystemException {
		Shard shard = fetchByName(name);

		if (shard == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchShardException(msg.toString());
		}

		return shard;
	}

	public Shard fetchByName(String name) throws SystemException {
		return fetchByName(name, true);
	}

	public Shard fetchByName(String name, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_NAME,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_SHARD_WHERE);

				if (name == null) {
					query.append(_FINDER_COLUMN_NAME_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_NAME_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_NAME_NAME_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (name != null) {
					qPos.add(name);
				}

				List<Shard> list = q.list();

				result = list;

				Shard shard = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NAME,
						finderArgs, list);
				}
				else {
					shard = list.get(0);

					cacheResult(shard);

					if ((shard.getName() == null) ||
							!shard.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NAME,
							finderArgs, shard);
					}
				}

				return shard;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NAME,
						finderArgs, new ArrayList<Shard>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Shard)result;
			}
		}
	}

	public Shard findByC_C(long classNameId, long classPK)
		throws NoSuchShardException, SystemException {
		Shard shard = fetchByC_C(classNameId, classPK);

		if (shard == null) {
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

			throw new NoSuchShardException(msg.toString());
		}

		return shard;
	}

	public Shard fetchByC_C(long classNameId, long classPK)
		throws SystemException {
		return fetchByC_C(classNameId, classPK, true);
	}

	public Shard fetchByC_C(long classNameId, long classPK,
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

				query.append(_SQL_SELECT_SHARD_WHERE);

				query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<Shard> list = q.list();

				result = list;

				Shard shard = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
						finderArgs, list);
				}
				else {
					shard = list.get(0);

					cacheResult(shard);

					if ((shard.getClassNameId() != classNameId) ||
							(shard.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
							finderArgs, shard);
					}
				}

				return shard;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
						finderArgs, new ArrayList<Shard>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Shard)result;
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

	public List<Shard> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Shard> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<Shard> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Shard> list = (List<Shard>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_SHARD);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

					sql = query.toString();
				}

				sql = _SQL_SELECT_SHARD;

				Query q = session.createQuery(sql);

				if (obc == null) {
					list = (List<Shard>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Shard>)QueryUtil.list(q, getDialect(), start,
							end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Shard>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByName(String name)
		throws NoSuchShardException, SystemException {
		Shard shard = findByName(name);

		remove(shard);
	}

	public void removeByC_C(long classNameId, long classPK)
		throws NoSuchShardException, SystemException {
		Shard shard = findByC_C(classNameId, classPK);

		remove(shard);
	}

	public void removeAll() throws SystemException {
		for (Shard shard : findAll()) {
			remove(shard);
		}
	}

	public int countByName(String name) throws SystemException {
		Object[] finderArgs = new Object[] { name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_NAME,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_SHARD_WHERE);

				if (name == null) {
					query.append(_FINDER_COLUMN_NAME_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_NAME_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_NAME_NAME_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_NAME,
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

				query.append(_SQL_COUNT_SHARD_WHERE);

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

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SHARD);

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
						"value.object.listener.com.liferay.portal.model.Shard")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Shard>> listenersList = new ArrayList<ModelListener<Shard>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Shard>)Class.forName(
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
	private static final String _SQL_SELECT_SHARD = "SELECT shard FROM Shard shard";
	private static final String _SQL_SELECT_SHARD_WHERE = "SELECT shard FROM Shard shard WHERE ";
	private static final String _SQL_COUNT_SHARD = "SELECT COUNT(shard) FROM Shard shard";
	private static final String _SQL_COUNT_SHARD_WHERE = "SELECT COUNT(shard) FROM Shard shard WHERE ";
	private static final String _FINDER_COLUMN_NAME_NAME_1 = "shard.name IS NULL";
	private static final String _FINDER_COLUMN_NAME_NAME_2 = "shard.name = ?";
	private static final String _FINDER_COLUMN_NAME_NAME_3 = "(shard.name IS NULL OR shard.name = ?)";
	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "shard.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "shard.classPK = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "shard.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Shard exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Shard exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(ShardPersistenceImpl.class);
}