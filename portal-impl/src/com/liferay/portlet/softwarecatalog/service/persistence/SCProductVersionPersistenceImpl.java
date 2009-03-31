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

package com.liferay.portlet.softwarecatalog.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
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

import com.liferay.portlet.softwarecatalog.NoSuchProductVersionException;
import com.liferay.portlet.softwarecatalog.model.SCProductVersion;
import com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionImpl;
import com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionModelImpl;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="SCProductVersionPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SCProductVersionPersistenceImpl extends BasePersistenceImpl
	implements SCProductVersionPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = SCProductVersionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_PRODUCTENTRYID = new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByProductEntryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_PRODUCTENTRYID = new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByProductEntryId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_PRODUCTENTRYID = new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByProductEntryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL = new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByDirectDownloadURL",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_DIRECTDOWNLOADURL = new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByDirectDownloadURL",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(SCProductVersion scProductVersion) {
		EntityCacheUtil.putResult(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionImpl.class, scProductVersion.getPrimaryKey(),
			scProductVersion);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
			new Object[] { scProductVersion.getDirectDownloadURL() },
			scProductVersion);
	}

	public void cacheResult(List<SCProductVersion> scProductVersions) {
		for (SCProductVersion scProductVersion : scProductVersions) {
			if (EntityCacheUtil.getResult(
						SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
						SCProductVersionImpl.class,
						scProductVersion.getPrimaryKey(), this) == null) {
				cacheResult(scProductVersion);
			}
		}
	}

	public SCProductVersion create(long productVersionId) {
		SCProductVersion scProductVersion = new SCProductVersionImpl();

		scProductVersion.setNew(true);
		scProductVersion.setPrimaryKey(productVersionId);

		return scProductVersion;
	}

	public SCProductVersion remove(long productVersionId)
		throws NoSuchProductVersionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SCProductVersion scProductVersion = (SCProductVersion)session.get(SCProductVersionImpl.class,
					new Long(productVersionId));

			if (scProductVersion == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No SCProductVersion exists with the primary key " +
						productVersionId);
				}

				throw new NoSuchProductVersionException(
					"No SCProductVersion exists with the primary key " +
					productVersionId);
			}

			return remove(scProductVersion);
		}
		catch (NoSuchProductVersionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SCProductVersion remove(SCProductVersion scProductVersion)
		throws SystemException {
		for (ModelListener<SCProductVersion> listener : listeners) {
			listener.onBeforeRemove(scProductVersion);
		}

		scProductVersion = removeImpl(scProductVersion);

		for (ModelListener<SCProductVersion> listener : listeners) {
			listener.onAfterRemove(scProductVersion);
		}

		return scProductVersion;
	}

	protected SCProductVersion removeImpl(SCProductVersion scProductVersion)
		throws SystemException {
		try {
			clearSCFrameworkVersions.clear(scProductVersion.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCFrameworkVersi_SCProductVers");
		}

		Session session = null;

		try {
			session = openSession();

			if (scProductVersion.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(SCProductVersionImpl.class,
						scProductVersion.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(scProductVersion);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		SCProductVersionModelImpl scProductVersionModelImpl = (SCProductVersionModelImpl)scProductVersion;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
			new Object[] {
				scProductVersionModelImpl.getOriginalDirectDownloadURL()
			});

		EntityCacheUtil.removeResult(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionImpl.class, scProductVersion.getPrimaryKey());

		return scProductVersion;
	}

	/**
	 * @deprecated Use <code>update(SCProductVersion scProductVersion, boolean merge)</code>.
	 */
	public SCProductVersion update(SCProductVersion scProductVersion)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(SCProductVersion scProductVersion) method. Use update(SCProductVersion scProductVersion, boolean merge) instead.");
		}

		return update(scProductVersion, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        scProductVersion the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when scProductVersion is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public SCProductVersion update(SCProductVersion scProductVersion,
		boolean merge) throws SystemException {
		boolean isNew = scProductVersion.isNew();

		for (ModelListener<SCProductVersion> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(scProductVersion);
			}
			else {
				listener.onBeforeUpdate(scProductVersion);
			}
		}

		scProductVersion = updateImpl(scProductVersion, merge);

		for (ModelListener<SCProductVersion> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(scProductVersion);
			}
			else {
				listener.onAfterUpdate(scProductVersion);
			}
		}

		return scProductVersion;
	}

	public SCProductVersion updateImpl(
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion,
		boolean merge) throws SystemException {
		boolean isNew = scProductVersion.isNew();

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, scProductVersion, merge);

			scProductVersion.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionImpl.class, scProductVersion.getPrimaryKey(),
			scProductVersion);

		SCProductVersionModelImpl scProductVersionModelImpl = (SCProductVersionModelImpl)scProductVersion;

		if (!isNew &&
				(!scProductVersion.getDirectDownloadURL()
									  .equals(scProductVersionModelImpl.getOriginalDirectDownloadURL()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
				new Object[] {
					scProductVersionModelImpl.getOriginalDirectDownloadURL()
				});
		}

		if (isNew ||
				(!scProductVersion.getDirectDownloadURL()
									  .equals(scProductVersionModelImpl.getOriginalDirectDownloadURL()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
				new Object[] { scProductVersion.getDirectDownloadURL() },
				scProductVersion);
		}

		return scProductVersion;
	}

	public SCProductVersion findByPrimaryKey(long productVersionId)
		throws NoSuchProductVersionException, SystemException {
		SCProductVersion scProductVersion = fetchByPrimaryKey(productVersionId);

		if (scProductVersion == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No SCProductVersion exists with the primary key " +
					productVersionId);
			}

			throw new NoSuchProductVersionException(
				"No SCProductVersion exists with the primary key " +
				productVersionId);
		}

		return scProductVersion;
	}

	public SCProductVersion fetchByPrimaryKey(long productVersionId)
		throws SystemException {
		SCProductVersion scProductVersion = (SCProductVersion)EntityCacheUtil.getResult(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
				SCProductVersionImpl.class, productVersionId, this);

		if (scProductVersion == null) {
			Session session = null;

			try {
				session = openSession();

				scProductVersion = (SCProductVersion)session.get(SCProductVersionImpl.class,
						new Long(productVersionId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (scProductVersion != null) {
					cacheResult(scProductVersion);
				}

				closeSession(session);
			}
		}

		return scProductVersion;
	}

	public List<SCProductVersion> findByProductEntryId(long productEntryId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(productEntryId) };

		List<SCProductVersion> list = (List<SCProductVersion>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_PRODUCTENTRYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductVersion WHERE ");

				query.append("productEntryId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(productEntryId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SCProductVersion>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_PRODUCTENTRYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SCProductVersion> findByProductEntryId(long productEntryId,
		int start, int end) throws SystemException {
		return findByProductEntryId(productEntryId, start, end, null);
	}

	public List<SCProductVersion> findByProductEntryId(long productEntryId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(productEntryId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SCProductVersion> list = (List<SCProductVersion>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_PRODUCTENTRYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductVersion WHERE ");

				query.append("productEntryId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(productEntryId);

				list = (List<SCProductVersion>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SCProductVersion>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_PRODUCTENTRYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SCProductVersion findByProductEntryId_First(long productEntryId,
		OrderByComparator obc)
		throws NoSuchProductVersionException, SystemException {
		List<SCProductVersion> list = findByProductEntryId(productEntryId, 0,
				1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SCProductVersion exists with the key {");

			msg.append("productEntryId=" + productEntryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProductVersionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCProductVersion findByProductEntryId_Last(long productEntryId,
		OrderByComparator obc)
		throws NoSuchProductVersionException, SystemException {
		int count = countByProductEntryId(productEntryId);

		List<SCProductVersion> list = findByProductEntryId(productEntryId,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SCProductVersion exists with the key {");

			msg.append("productEntryId=" + productEntryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProductVersionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCProductVersion[] findByProductEntryId_PrevAndNext(
		long productVersionId, long productEntryId, OrderByComparator obc)
		throws NoSuchProductVersionException, SystemException {
		SCProductVersion scProductVersion = findByPrimaryKey(productVersionId);

		int count = countByProductEntryId(productEntryId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCProductVersion WHERE ");

			query.append("productEntryId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(productEntryId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					scProductVersion);

			SCProductVersion[] array = new SCProductVersionImpl[3];

			array[0] = (SCProductVersion)objArray[0];
			array[1] = (SCProductVersion)objArray[1];
			array[2] = (SCProductVersion)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SCProductVersion findByDirectDownloadURL(String directDownloadURL)
		throws NoSuchProductVersionException, SystemException {
		SCProductVersion scProductVersion = fetchByDirectDownloadURL(directDownloadURL);

		if (scProductVersion == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SCProductVersion exists with the key {");

			msg.append("directDownloadURL=" + directDownloadURL);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchProductVersionException(msg.toString());
		}

		return scProductVersion;
	}

	public SCProductVersion fetchByDirectDownloadURL(String directDownloadURL)
		throws SystemException {
		return fetchByDirectDownloadURL(directDownloadURL, true);
	}

	public SCProductVersion fetchByDirectDownloadURL(String directDownloadURL,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { directDownloadURL };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductVersion WHERE ");

				if (directDownloadURL == null) {
					query.append("directDownloadURL IS NULL");
				}
				else {
					query.append("lower(directDownloadURL) = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (directDownloadURL != null) {
					qPos.add(directDownloadURL);
				}

				List<SCProductVersion> list = q.list();

				result = list;

				SCProductVersion scProductVersion = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
						finderArgs, list);
				}
				else {
					scProductVersion = list.get(0);

					cacheResult(scProductVersion);
				}

				return scProductVersion;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
						finderArgs, new ArrayList<SCProductVersion>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (SCProductVersion)result;
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

	public List<SCProductVersion> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<SCProductVersion> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<SCProductVersion> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SCProductVersion> list = (List<SCProductVersion>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductVersion ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<SCProductVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SCProductVersion>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SCProductVersion>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByProductEntryId(long productEntryId)
		throws SystemException {
		for (SCProductVersion scProductVersion : findByProductEntryId(
				productEntryId)) {
			remove(scProductVersion);
		}
	}

	public void removeByDirectDownloadURL(String directDownloadURL)
		throws NoSuchProductVersionException, SystemException {
		SCProductVersion scProductVersion = findByDirectDownloadURL(directDownloadURL);

		remove(scProductVersion);
	}

	public void removeAll() throws SystemException {
		for (SCProductVersion scProductVersion : findAll()) {
			remove(scProductVersion);
		}
	}

	public int countByProductEntryId(long productEntryId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(productEntryId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_PRODUCTENTRYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductVersion WHERE ");

				query.append("productEntryId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(productEntryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PRODUCTENTRYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByDirectDownloadURL(String directDownloadURL)
		throws SystemException {
		Object[] finderArgs = new Object[] { directDownloadURL };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_DIRECTDOWNLOADURL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductVersion WHERE ");

				if (directDownloadURL == null) {
					query.append("directDownloadURL IS NULL");
				}
				else {
					query.append("lower(directDownloadURL) = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (directDownloadURL != null) {
					qPos.add(directDownloadURL);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_DIRECTDOWNLOADURL,
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
						"SELECT COUNT(*) FROM com.liferay.portlet.softwarecatalog.model.SCProductVersion");

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

	public List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		long pk) throws SystemException {
		return getSCFrameworkVersions(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		long pk, int start, int end) throws SystemException {
		return getSCFrameworkVersions(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_SCFRAMEWORKVERSIONS = new FinderPath(com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED_SCFRAMEWORKVERSI_SCPRODUCTVERS,
			"SCFrameworkVersi_SCProductVers", "getSCFrameworkVersions",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		long pk, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> list = (List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion>)FinderCacheUtil.getResult(FINDER_PATH_GET_SCFRAMEWORKVERSIONS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder sb = new StringBuilder();

				sb.append(_SQL_GETSCFRAMEWORKVERSIONS);

				if (obc != null) {
					sb.append("ORDER BY ");
					sb.append(obc.getOrderBy());
				}

				else {
					sb.append("ORDER BY ");

					sb.append("SCFrameworkVersion.name DESC");
				}

				String sql = sb.toString();

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("SCFrameworkVersion",
					com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion>();
				}

				scFrameworkVersionPersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_SCFRAMEWORKVERSIONS,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_SCFRAMEWORKVERSIONS_SIZE = new FinderPath(com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED_SCFRAMEWORKVERSI_SCPRODUCTVERS,
			"SCFrameworkVersi_SCProductVers", "getSCFrameworkVersionsSize",
			new String[] { Long.class.getName() });

	public int getSCFrameworkVersionsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_SCFRAMEWORKVERSIONS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETSCFRAMEWORKVERSIONSSIZE);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_SCFRAMEWORKVERSIONS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_SCFRAMEWORKVERSION = new FinderPath(com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED_SCFRAMEWORKVERSI_SCPRODUCTVERS,
			"SCFrameworkVersi_SCProductVers", "containsSCFrameworkVersion",
			new String[] { Long.class.getName(), Long.class.getName() });

	public boolean containsSCFrameworkVersion(long pk, long scFrameworkVersionPK)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk),
				
				new Long(scFrameworkVersionPK)
			};

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_SCFRAMEWORKVERSION,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsSCFrameworkVersion.contains(
							pk, scFrameworkVersionPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_SCFRAMEWORKVERSION,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	public boolean containsSCFrameworkVersions(long pk)
		throws SystemException {
		if (getSCFrameworkVersionsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addSCFrameworkVersion(long pk, long scFrameworkVersionPK)
		throws SystemException {
		try {
			addSCFrameworkVersion.add(pk, scFrameworkVersionPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCFrameworkVersi_SCProductVers");
		}
	}

	public void addSCFrameworkVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws SystemException {
		try {
			addSCFrameworkVersion.add(pk, scFrameworkVersion.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCFrameworkVersi_SCProductVers");
		}
	}

	public void addSCFrameworkVersions(long pk, long[] scFrameworkVersionPKs)
		throws SystemException {
		try {
			for (long scFrameworkVersionPK : scFrameworkVersionPKs) {
				addSCFrameworkVersion.add(pk, scFrameworkVersionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCFrameworkVersi_SCProductVers");
		}
	}

	public void addSCFrameworkVersions(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions)
		throws SystemException {
		try {
			for (com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion : scFrameworkVersions) {
				addSCFrameworkVersion.add(pk, scFrameworkVersion.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCFrameworkVersi_SCProductVers");
		}
	}

	public void clearSCFrameworkVersions(long pk) throws SystemException {
		try {
			clearSCFrameworkVersions.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCFrameworkVersi_SCProductVers");
		}
	}

	public void removeSCFrameworkVersion(long pk, long scFrameworkVersionPK)
		throws SystemException {
		try {
			removeSCFrameworkVersion.remove(pk, scFrameworkVersionPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCFrameworkVersi_SCProductVers");
		}
	}

	public void removeSCFrameworkVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws SystemException {
		try {
			removeSCFrameworkVersion.remove(pk,
				scFrameworkVersion.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCFrameworkVersi_SCProductVers");
		}
	}

	public void removeSCFrameworkVersions(long pk, long[] scFrameworkVersionPKs)
		throws SystemException {
		try {
			for (long scFrameworkVersionPK : scFrameworkVersionPKs) {
				removeSCFrameworkVersion.remove(pk, scFrameworkVersionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCFrameworkVersi_SCProductVers");
		}
	}

	public void removeSCFrameworkVersions(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions)
		throws SystemException {
		try {
			for (com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion : scFrameworkVersions) {
				removeSCFrameworkVersion.remove(pk,
					scFrameworkVersion.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCFrameworkVersi_SCProductVers");
		}
	}

	public void setSCFrameworkVersions(long pk, long[] scFrameworkVersionPKs)
		throws SystemException {
		try {
			clearSCFrameworkVersions.clear(pk);

			for (long scFrameworkVersionPK : scFrameworkVersionPKs) {
				addSCFrameworkVersion.add(pk, scFrameworkVersionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCFrameworkVersi_SCProductVers");
		}
	}

	public void setSCFrameworkVersions(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions)
		throws SystemException {
		try {
			clearSCFrameworkVersions.clear(pk);

			for (com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion : scFrameworkVersions) {
				addSCFrameworkVersion.add(pk, scFrameworkVersion.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCFrameworkVersi_SCProductVers");
		}
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.softwarecatalog.model.SCProductVersion")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SCProductVersion>> listenersList = new ArrayList<ModelListener<SCProductVersion>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SCProductVersion>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsSCFrameworkVersion = new ContainsSCFrameworkVersion(this);

		addSCFrameworkVersion = new AddSCFrameworkVersion(this);
		clearSCFrameworkVersions = new ClearSCFrameworkVersions(this);
		removeSCFrameworkVersion = new RemoveSCFrameworkVersion(this);
	}

	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCLicensePersistence.impl")
	protected com.liferay.portlet.softwarecatalog.service.persistence.SCLicensePersistence scLicensePersistence;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCFrameworkVersionPersistence.impl")
	protected com.liferay.portlet.softwarecatalog.service.persistence.SCFrameworkVersionPersistence scFrameworkVersionPersistence;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCProductEntryPersistence.impl")
	protected com.liferay.portlet.softwarecatalog.service.persistence.SCProductEntryPersistence scProductEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCProductScreenshotPersistence.impl")
	protected com.liferay.portlet.softwarecatalog.service.persistence.SCProductScreenshotPersistence scProductScreenshotPersistence;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCProductVersionPersistence.impl")
	protected com.liferay.portlet.softwarecatalog.service.persistence.SCProductVersionPersistence scProductVersionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	protected ContainsSCFrameworkVersion containsSCFrameworkVersion;
	protected AddSCFrameworkVersion addSCFrameworkVersion;
	protected ClearSCFrameworkVersions clearSCFrameworkVersions;
	protected RemoveSCFrameworkVersion removeSCFrameworkVersion;

	protected class ContainsSCFrameworkVersion {
		protected ContainsSCFrameworkVersion(
			SCProductVersionPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSSCFRAMEWORKVERSION,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long productVersionId,
			long frameworkVersionId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(productVersionId), new Long(frameworkVersionId)
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

	protected class AddSCFrameworkVersion {
		protected AddSCFrameworkVersion(
			SCProductVersionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO SCFrameworkVersi_SCProductVers (productVersionId, frameworkVersionId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long productVersionId, long frameworkVersionId)
			throws SystemException {
			if (!_persistenceImpl.containsSCFrameworkVersion.contains(
						productVersionId, frameworkVersionId)) {
				ModelListener<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion>[] scFrameworkVersionListeners =
					scFrameworkVersionPersistence.getListeners();

				for (ModelListener<SCProductVersion> listener : listeners) {
					listener.onBeforeAddAssociation(productVersionId,
						com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion.class.getName(),
						frameworkVersionId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> listener : scFrameworkVersionListeners) {
					listener.onBeforeAddAssociation(frameworkVersionId,
						SCProductVersion.class.getName(), productVersionId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(productVersionId), new Long(frameworkVersionId)
					});

				for (ModelListener<SCProductVersion> listener : listeners) {
					listener.onAfterAddAssociation(productVersionId,
						com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion.class.getName(),
						frameworkVersionId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> listener : scFrameworkVersionListeners) {
					listener.onAfterAddAssociation(frameworkVersionId,
						SCProductVersion.class.getName(), productVersionId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private SCProductVersionPersistenceImpl _persistenceImpl;
	}

	protected class ClearSCFrameworkVersions {
		protected ClearSCFrameworkVersions(
			SCProductVersionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM SCFrameworkVersi_SCProductVers WHERE productVersionId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long productVersionId) throws SystemException {
			ModelListener<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion>[] scFrameworkVersionListeners =
				scFrameworkVersionPersistence.getListeners();

			List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions =
				null;

			if ((listeners.length > 0) ||
					(scFrameworkVersionListeners.length > 0)) {
				scFrameworkVersions = getSCFrameworkVersions(productVersionId);

				for (com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion : scFrameworkVersions) {
					for (ModelListener<SCProductVersion> listener : listeners) {
						listener.onBeforeRemoveAssociation(productVersionId,
							com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion.class.getName(),
							scFrameworkVersion.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> listener : scFrameworkVersionListeners) {
						listener.onBeforeRemoveAssociation(scFrameworkVersion.getPrimaryKey(),
							SCProductVersion.class.getName(), productVersionId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(productVersionId) });

			if ((listeners.length > 0) ||
					(scFrameworkVersionListeners.length > 0)) {
				for (com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion : scFrameworkVersions) {
					for (ModelListener<SCProductVersion> listener : listeners) {
						listener.onAfterRemoveAssociation(productVersionId,
							com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion.class.getName(),
							scFrameworkVersion.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> listener : scFrameworkVersionListeners) {
						listener.onBeforeRemoveAssociation(scFrameworkVersion.getPrimaryKey(),
							SCProductVersion.class.getName(), productVersionId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveSCFrameworkVersion {
		protected RemoveSCFrameworkVersion(
			SCProductVersionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM SCFrameworkVersi_SCProductVers WHERE productVersionId = ? AND frameworkVersionId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long productVersionId, long frameworkVersionId)
			throws SystemException {
			if (_persistenceImpl.containsSCFrameworkVersion.contains(
						productVersionId, frameworkVersionId)) {
				ModelListener<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion>[] scFrameworkVersionListeners =
					scFrameworkVersionPersistence.getListeners();

				for (ModelListener<SCProductVersion> listener : listeners) {
					listener.onBeforeRemoveAssociation(productVersionId,
						com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion.class.getName(),
						frameworkVersionId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> listener : scFrameworkVersionListeners) {
					listener.onBeforeRemoveAssociation(frameworkVersionId,
						SCProductVersion.class.getName(), productVersionId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(productVersionId), new Long(frameworkVersionId)
					});

				for (ModelListener<SCProductVersion> listener : listeners) {
					listener.onAfterRemoveAssociation(productVersionId,
						com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion.class.getName(),
						frameworkVersionId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> listener : scFrameworkVersionListeners) {
					listener.onAfterRemoveAssociation(frameworkVersionId,
						SCProductVersion.class.getName(), productVersionId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private SCProductVersionPersistenceImpl _persistenceImpl;
	}

	private static final String _SQL_GETSCFRAMEWORKVERSIONS = "SELECT {SCFrameworkVersion.*} FROM SCFrameworkVersion INNER JOIN SCFrameworkVersi_SCProductVers ON (SCFrameworkVersi_SCProductVers.frameworkVersionId = SCFrameworkVersion.frameworkVersionId) WHERE (SCFrameworkVersi_SCProductVers.productVersionId = ?)";
	private static final String _SQL_GETSCFRAMEWORKVERSIONSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM SCFrameworkVersi_SCProductVers WHERE productVersionId = ?";
	private static final String _SQL_CONTAINSSCFRAMEWORKVERSION = "SELECT COUNT(*) AS COUNT_VALUE FROM SCFrameworkVersi_SCProductVers WHERE productVersionId = ? AND frameworkVersionId = ?";
	private static Log _log = LogFactoryUtil.getLog(SCProductVersionPersistenceImpl.class);
}