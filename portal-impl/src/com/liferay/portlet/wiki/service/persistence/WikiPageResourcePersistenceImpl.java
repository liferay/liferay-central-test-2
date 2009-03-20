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

package com.liferay.portlet.wiki.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.wiki.NoSuchPageResourceException;
import com.liferay.portlet.wiki.model.WikiPageResource;
import com.liferay.portlet.wiki.model.impl.WikiPageResourceImpl;
import com.liferay.portlet.wiki.model.impl.WikiPageResourceModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="WikiPageResourcePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class WikiPageResourcePersistenceImpl extends BasePersistenceImpl
	implements WikiPageResourcePersistence {
	public WikiPageResource create(long resourcePrimKey) {
		WikiPageResource wikiPageResource = new WikiPageResourceImpl();

		wikiPageResource.setNew(true);
		wikiPageResource.setPrimaryKey(resourcePrimKey);

		return wikiPageResource;
	}

	public WikiPageResource remove(long resourcePrimKey)
		throws NoSuchPageResourceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			WikiPageResource wikiPageResource = (WikiPageResource)session.get(WikiPageResourceImpl.class,
					new Long(resourcePrimKey));

			if (wikiPageResource == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No WikiPageResource exists with the primary key " +
						resourcePrimKey);
				}

				throw new NoSuchPageResourceException(
					"No WikiPageResource exists with the primary key " +
					resourcePrimKey);
			}

			return remove(wikiPageResource);
		}
		catch (NoSuchPageResourceException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public WikiPageResource remove(WikiPageResource wikiPageResource)
		throws SystemException {
		for (ModelListener<WikiPageResource> listener : listeners) {
			listener.onBeforeRemove(wikiPageResource);
		}

		wikiPageResource = removeImpl(wikiPageResource);

		for (ModelListener<WikiPageResource> listener : listeners) {
			listener.onAfterRemove(wikiPageResource);
		}

		return wikiPageResource;
	}

	protected WikiPageResource removeImpl(WikiPageResource wikiPageResource)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(WikiPageResourceImpl.class,
						wikiPageResource.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(wikiPageResource);

			session.flush();

			return wikiPageResource;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(WikiPageResource.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(WikiPageResource wikiPageResource, boolean merge)</code>.
	 */
	public WikiPageResource update(WikiPageResource wikiPageResource)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(WikiPageResource wikiPageResource) method. Use update(WikiPageResource wikiPageResource, boolean merge) instead.");
		}

		return update(wikiPageResource, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        wikiPageResource the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when wikiPageResource is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public WikiPageResource update(WikiPageResource wikiPageResource,
		boolean merge) throws SystemException {
		boolean isNew = wikiPageResource.isNew();

		for (ModelListener<WikiPageResource> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(wikiPageResource);
			}
			else {
				listener.onBeforeUpdate(wikiPageResource);
			}
		}

		wikiPageResource = updateImpl(wikiPageResource, merge);

		for (ModelListener<WikiPageResource> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(wikiPageResource);
			}
			else {
				listener.onAfterUpdate(wikiPageResource);
			}
		}

		return wikiPageResource;
	}

	public WikiPageResource updateImpl(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, wikiPageResource, merge);

			wikiPageResource.setNew(false);

			return wikiPageResource;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(WikiPageResource.class.getName());
		}
	}

	public WikiPageResource findByPrimaryKey(long resourcePrimKey)
		throws NoSuchPageResourceException, SystemException {
		WikiPageResource wikiPageResource = fetchByPrimaryKey(resourcePrimKey);

		if (wikiPageResource == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No WikiPageResource exists with the primary key " +
					resourcePrimKey);
			}

			throw new NoSuchPageResourceException(
				"No WikiPageResource exists with the primary key " +
				resourcePrimKey);
		}

		return wikiPageResource;
	}

	public WikiPageResource fetchByPrimaryKey(long resourcePrimKey)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (WikiPageResource)session.get(WikiPageResourceImpl.class,
				new Long(resourcePrimKey));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public WikiPageResource findByN_T(long nodeId, String title)
		throws NoSuchPageResourceException, SystemException {
		WikiPageResource wikiPageResource = fetchByN_T(nodeId, title);

		if (wikiPageResource == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WikiPageResource exists with the key {");

			msg.append("nodeId=" + nodeId);

			msg.append(", ");
			msg.append("title=" + title);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPageResourceException(msg.toString());
		}

		return wikiPageResource;
	}

	public WikiPageResource fetchByN_T(long nodeId, String title)
		throws SystemException {
		boolean finderClassNameCacheEnabled = WikiPageResourceModelImpl.CACHE_ENABLED;
		String finderClassName = WikiPageResource.class.getName();
		String finderMethodName = "fetchByN_T";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(nodeId), title };

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
					"FROM com.liferay.portlet.wiki.model.WikiPageResource WHERE ");

				query.append("nodeId = ?");

				query.append(" AND ");

				if (title == null) {
					query.append("title IS NULL");
				}
				else {
					query.append("title = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (title != null) {
					qPos.add(title);
				}

				List<WikiPageResource> list = q.list();

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
			List<WikiPageResource> list = (List<WikiPageResource>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
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

	public List<WikiPageResource> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<WikiPageResource> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<WikiPageResource> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = WikiPageResourceModelImpl.CACHE_ENABLED;
		String finderClassName = WikiPageResource.class.getName();
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

				query.append(
					"FROM com.liferay.portlet.wiki.model.WikiPageResource ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<WikiPageResource> list = null;

				if (obc == null) {
					list = (List<WikiPageResource>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<WikiPageResource>)QueryUtil.list(q,
							getDialect(), start, end);
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
			return (List<WikiPageResource>)result;
		}
	}

	public void removeByN_T(long nodeId, String title)
		throws NoSuchPageResourceException, SystemException {
		WikiPageResource wikiPageResource = findByN_T(nodeId, title);

		remove(wikiPageResource);
	}

	public void removeAll() throws SystemException {
		for (WikiPageResource wikiPageResource : findAll()) {
			remove(wikiPageResource);
		}
	}

	public int countByN_T(long nodeId, String title) throws SystemException {
		boolean finderClassNameCacheEnabled = WikiPageResourceModelImpl.CACHE_ENABLED;
		String finderClassName = WikiPageResource.class.getName();
		String finderMethodName = "countByN_T";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(nodeId), title };

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
					"FROM com.liferay.portlet.wiki.model.WikiPageResource WHERE ");

				query.append("nodeId = ?");

				query.append(" AND ");

				if (title == null) {
					query.append("title IS NULL");
				}
				else {
					query.append("title = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (title != null) {
					qPos.add(title);
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

	public int countAll() throws SystemException {
		boolean finderClassNameCacheEnabled = WikiPageResourceModelImpl.CACHE_ENABLED;
		String finderClassName = WikiPageResource.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portlet.wiki.model.WikiPageResource");

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

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.wiki.model.WikiPageResource")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<WikiPageResource>> listenersList = new ArrayList<ModelListener<WikiPageResource>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<WikiPageResource>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.wiki.service.persistence.WikiNodePersistence.impl")
	protected com.liferay.portlet.wiki.service.persistence.WikiNodePersistence wikiNodePersistence;
	@BeanReference(name = "com.liferay.portlet.wiki.service.persistence.WikiPagePersistence.impl")
	protected com.liferay.portlet.wiki.service.persistence.WikiPagePersistence wikiPagePersistence;
	@BeanReference(name = "com.liferay.portlet.wiki.service.persistence.WikiPageResourcePersistence.impl")
	protected com.liferay.portlet.wiki.service.persistence.WikiPageResourcePersistence wikiPageResourcePersistence;
	private static Log _log = LogFactoryUtil.getLog(WikiPageResourcePersistenceImpl.class);
}