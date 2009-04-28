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

package com.liferay.portlet.categories.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.categories.NoSuchPropertyException;
import com.liferay.portlet.categories.model.CategoriesProperty;
import com.liferay.portlet.categories.model.impl.CategoriesPropertyImpl;
import com.liferay.portlet.categories.model.impl.CategoriesPropertyModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="CategoriesPropertyPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class CategoriesPropertyPersistenceImpl extends BasePersistenceImpl
	implements CategoriesPropertyPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = CategoriesPropertyImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(CategoriesPropertyModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(CategoriesPropertyModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(CategoriesPropertyModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_ENTRYID = new FinderPath(CategoriesPropertyModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByEntryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_ENTRYID = new FinderPath(CategoriesPropertyModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByEntryId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_ENTRYID = new FinderPath(CategoriesPropertyModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByEntryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_K = new FinderPath(CategoriesPropertyModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_K",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_K = new FinderPath(CategoriesPropertyModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_K",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_K = new FinderPath(CategoriesPropertyModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_K",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_E_K = new FinderPath(CategoriesPropertyModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByE_K",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_E_K = new FinderPath(CategoriesPropertyModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByE_K",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(CategoriesPropertyModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CategoriesPropertyModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(CategoriesProperty categoriesProperty) {
		EntityCacheUtil.putResult(CategoriesPropertyModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesPropertyImpl.class, categoriesProperty.getPrimaryKey(),
			categoriesProperty);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_E_K,
			new Object[] {
				new Long(categoriesProperty.getEntryId()),
				
			categoriesProperty.getKey()
			}, categoriesProperty);
	}

	public void cacheResult(List<CategoriesProperty> categoriesProperties) {
		for (CategoriesProperty categoriesProperty : categoriesProperties) {
			if (EntityCacheUtil.getResult(
						CategoriesPropertyModelImpl.ENTITY_CACHE_ENABLED,
						CategoriesPropertyImpl.class,
						categoriesProperty.getPrimaryKey(), this) == null) {
				cacheResult(categoriesProperty);
			}
		}
	}

	public CategoriesProperty create(long propertyId) {
		CategoriesProperty categoriesProperty = new CategoriesPropertyImpl();

		categoriesProperty.setNew(true);
		categoriesProperty.setPrimaryKey(propertyId);

		return categoriesProperty;
	}

	public CategoriesProperty remove(long propertyId)
		throws NoSuchPropertyException, SystemException {
		Session session = null;

		try {
			session = openSession();

			CategoriesProperty categoriesProperty = (CategoriesProperty)session.get(CategoriesPropertyImpl.class,
					new Long(propertyId));

			if (categoriesProperty == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No CategoriesProperty exists with the primary key " +
						propertyId);
				}

				throw new NoSuchPropertyException(
					"No CategoriesProperty exists with the primary key " +
					propertyId);
			}

			return remove(categoriesProperty);
		}
		catch (NoSuchPropertyException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public CategoriesProperty remove(CategoriesProperty categoriesProperty)
		throws SystemException {
		for (ModelListener<CategoriesProperty> listener : listeners) {
			listener.onBeforeRemove(categoriesProperty);
		}

		categoriesProperty = removeImpl(categoriesProperty);

		for (ModelListener<CategoriesProperty> listener : listeners) {
			listener.onAfterRemove(categoriesProperty);
		}

		return categoriesProperty;
	}

	protected CategoriesProperty removeImpl(
		CategoriesProperty categoriesProperty) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (categoriesProperty.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(CategoriesPropertyImpl.class,
						categoriesProperty.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(categoriesProperty);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		CategoriesPropertyModelImpl categoriesPropertyModelImpl = (CategoriesPropertyModelImpl)categoriesProperty;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_E_K,
			new Object[] {
				new Long(categoriesPropertyModelImpl.getOriginalEntryId()),
				
			categoriesPropertyModelImpl.getOriginalKey()
			});

		EntityCacheUtil.removeResult(CategoriesPropertyModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesPropertyImpl.class, categoriesProperty.getPrimaryKey());

		return categoriesProperty;
	}

	/**
	 * @deprecated Use <code>update(CategoriesProperty categoriesProperty, boolean merge)</code>.
	 */
	public CategoriesProperty update(CategoriesProperty categoriesProperty)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(CategoriesProperty categoriesProperty) method. Use update(CategoriesProperty categoriesProperty, boolean merge) instead.");
		}

		return update(categoriesProperty, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        categoriesProperty the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when categoriesProperty is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public CategoriesProperty update(CategoriesProperty categoriesProperty,
		boolean merge) throws SystemException {
		boolean isNew = categoriesProperty.isNew();

		for (ModelListener<CategoriesProperty> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(categoriesProperty);
			}
			else {
				listener.onBeforeUpdate(categoriesProperty);
			}
		}

		categoriesProperty = updateImpl(categoriesProperty, merge);

		for (ModelListener<CategoriesProperty> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(categoriesProperty);
			}
			else {
				listener.onAfterUpdate(categoriesProperty);
			}
		}

		return categoriesProperty;
	}

	public CategoriesProperty updateImpl(
		com.liferay.portlet.categories.model.CategoriesProperty categoriesProperty,
		boolean merge) throws SystemException {
		boolean isNew = categoriesProperty.isNew();

		CategoriesPropertyModelImpl categoriesPropertyModelImpl = (CategoriesPropertyModelImpl)categoriesProperty;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, categoriesProperty, merge);

			categoriesProperty.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(CategoriesPropertyModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesPropertyImpl.class, categoriesProperty.getPrimaryKey(),
			categoriesProperty);

		if (!isNew &&
				((categoriesProperty.getEntryId() != categoriesPropertyModelImpl.getOriginalEntryId()) ||
				!categoriesProperty.getKey()
									   .equals(categoriesPropertyModelImpl.getOriginalKey()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_E_K,
				new Object[] {
					new Long(categoriesPropertyModelImpl.getOriginalEntryId()),
					
				categoriesPropertyModelImpl.getOriginalKey()
				});
		}

		if (isNew ||
				((categoriesProperty.getEntryId() != categoriesPropertyModelImpl.getOriginalEntryId()) ||
				!categoriesProperty.getKey()
									   .equals(categoriesPropertyModelImpl.getOriginalKey()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_E_K,
				new Object[] {
					new Long(categoriesProperty.getEntryId()),
					
				categoriesProperty.getKey()
				}, categoriesProperty);
		}

		return categoriesProperty;
	}

	public CategoriesProperty findByPrimaryKey(long propertyId)
		throws NoSuchPropertyException, SystemException {
		CategoriesProperty categoriesProperty = fetchByPrimaryKey(propertyId);

		if (categoriesProperty == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No CategoriesProperty exists with the primary key " +
					propertyId);
			}

			throw new NoSuchPropertyException(
				"No CategoriesProperty exists with the primary key " +
				propertyId);
		}

		return categoriesProperty;
	}

	public CategoriesProperty fetchByPrimaryKey(long propertyId)
		throws SystemException {
		CategoriesProperty categoriesProperty = (CategoriesProperty)EntityCacheUtil.getResult(CategoriesPropertyModelImpl.ENTITY_CACHE_ENABLED,
				CategoriesPropertyImpl.class, propertyId, this);

		if (categoriesProperty == null) {
			Session session = null;

			try {
				session = openSession();

				categoriesProperty = (CategoriesProperty)session.get(CategoriesPropertyImpl.class,
						new Long(propertyId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (categoriesProperty != null) {
					cacheResult(categoriesProperty);
				}

				closeSession(session);
			}
		}

		return categoriesProperty;
	}

	public List<CategoriesProperty> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<CategoriesProperty> list = (List<CategoriesProperty>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.categories.model.CategoriesProperty WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("key_ ASC");

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
					list = new ArrayList<CategoriesProperty>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<CategoriesProperty> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<CategoriesProperty> findByCompanyId(long companyId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<CategoriesProperty> list = (List<CategoriesProperty>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.categories.model.CategoriesProperty WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("key_ ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<CategoriesProperty>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<CategoriesProperty>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public CategoriesProperty findByCompanyId_First(long companyId,
		OrderByComparator obc) throws NoSuchPropertyException, SystemException {
		List<CategoriesProperty> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No CategoriesProperty exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPropertyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public CategoriesProperty findByCompanyId_Last(long companyId,
		OrderByComparator obc) throws NoSuchPropertyException, SystemException {
		int count = countByCompanyId(companyId);

		List<CategoriesProperty> list = findByCompanyId(companyId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No CategoriesProperty exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPropertyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public CategoriesProperty[] findByCompanyId_PrevAndNext(long propertyId,
		long companyId, OrderByComparator obc)
		throws NoSuchPropertyException, SystemException {
		CategoriesProperty categoriesProperty = findByPrimaryKey(propertyId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.categories.model.CategoriesProperty WHERE ");

			query.append("companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("key_ ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					categoriesProperty);

			CategoriesProperty[] array = new CategoriesPropertyImpl[3];

			array[0] = (CategoriesProperty)objArray[0];
			array[1] = (CategoriesProperty)objArray[1];
			array[2] = (CategoriesProperty)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<CategoriesProperty> findByEntryId(long entryId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(entryId) };

		List<CategoriesProperty> list = (List<CategoriesProperty>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ENTRYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.categories.model.CategoriesProperty WHERE ");

				query.append("entryId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("key_ ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<CategoriesProperty>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_ENTRYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<CategoriesProperty> findByEntryId(long entryId, int start,
		int end) throws SystemException {
		return findByEntryId(entryId, start, end, null);
	}

	public List<CategoriesProperty> findByEntryId(long entryId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(entryId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<CategoriesProperty> list = (List<CategoriesProperty>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_ENTRYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.categories.model.CategoriesProperty WHERE ");

				query.append("entryId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("key_ ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				list = (List<CategoriesProperty>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<CategoriesProperty>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_ENTRYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public CategoriesProperty findByEntryId_First(long entryId,
		OrderByComparator obc) throws NoSuchPropertyException, SystemException {
		List<CategoriesProperty> list = findByEntryId(entryId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No CategoriesProperty exists with the key {");

			msg.append("entryId=" + entryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPropertyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public CategoriesProperty findByEntryId_Last(long entryId,
		OrderByComparator obc) throws NoSuchPropertyException, SystemException {
		int count = countByEntryId(entryId);

		List<CategoriesProperty> list = findByEntryId(entryId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No CategoriesProperty exists with the key {");

			msg.append("entryId=" + entryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPropertyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public CategoriesProperty[] findByEntryId_PrevAndNext(long propertyId,
		long entryId, OrderByComparator obc)
		throws NoSuchPropertyException, SystemException {
		CategoriesProperty categoriesProperty = findByPrimaryKey(propertyId);

		int count = countByEntryId(entryId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.categories.model.CategoriesProperty WHERE ");

			query.append("entryId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("key_ ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(entryId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					categoriesProperty);

			CategoriesProperty[] array = new CategoriesPropertyImpl[3];

			array[0] = (CategoriesProperty)objArray[0];
			array[1] = (CategoriesProperty)objArray[1];
			array[2] = (CategoriesProperty)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<CategoriesProperty> findByC_K(long companyId, String key)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId), key };

		List<CategoriesProperty> list = (List<CategoriesProperty>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_K,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.categories.model.CategoriesProperty WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				if (key == null) {
					query.append("key_ IS NULL");
				}
				else {
					query.append("key_ = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("key_ ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (key != null) {
					qPos.add(key);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<CategoriesProperty>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_K, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<CategoriesProperty> findByC_K(long companyId, String key,
		int start, int end) throws SystemException {
		return findByC_K(companyId, key, start, end, null);
	}

	public List<CategoriesProperty> findByC_K(long companyId, String key,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				key,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<CategoriesProperty> list = (List<CategoriesProperty>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_K,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.categories.model.CategoriesProperty WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				if (key == null) {
					query.append("key_ IS NULL");
				}
				else {
					query.append("key_ = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("key_ ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (key != null) {
					qPos.add(key);
				}

				list = (List<CategoriesProperty>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<CategoriesProperty>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_K,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public CategoriesProperty findByC_K_First(long companyId, String key,
		OrderByComparator obc) throws NoSuchPropertyException, SystemException {
		List<CategoriesProperty> list = findByC_K(companyId, key, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No CategoriesProperty exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("key=" + key);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPropertyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public CategoriesProperty findByC_K_Last(long companyId, String key,
		OrderByComparator obc) throws NoSuchPropertyException, SystemException {
		int count = countByC_K(companyId, key);

		List<CategoriesProperty> list = findByC_K(companyId, key, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No CategoriesProperty exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("key=" + key);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPropertyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public CategoriesProperty[] findByC_K_PrevAndNext(long propertyId,
		long companyId, String key, OrderByComparator obc)
		throws NoSuchPropertyException, SystemException {
		CategoriesProperty categoriesProperty = findByPrimaryKey(propertyId);

		int count = countByC_K(companyId, key);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.categories.model.CategoriesProperty WHERE ");

			query.append("companyId = ?");

			query.append(" AND ");

			if (key == null) {
				query.append("key_ IS NULL");
			}
			else {
				query.append("key_ = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("key_ ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			if (key != null) {
				qPos.add(key);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					categoriesProperty);

			CategoriesProperty[] array = new CategoriesPropertyImpl[3];

			array[0] = (CategoriesProperty)objArray[0];
			array[1] = (CategoriesProperty)objArray[1];
			array[2] = (CategoriesProperty)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public CategoriesProperty findByE_K(long entryId, String key)
		throws NoSuchPropertyException, SystemException {
		CategoriesProperty categoriesProperty = fetchByE_K(entryId, key);

		if (categoriesProperty == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No CategoriesProperty exists with the key {");

			msg.append("entryId=" + entryId);

			msg.append(", ");
			msg.append("key=" + key);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPropertyException(msg.toString());
		}

		return categoriesProperty;
	}

	public CategoriesProperty fetchByE_K(long entryId, String key)
		throws SystemException {
		return fetchByE_K(entryId, key, true);
	}

	public CategoriesProperty fetchByE_K(long entryId, String key,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(entryId), key };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_E_K,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.categories.model.CategoriesProperty WHERE ");

				query.append("entryId = ?");

				query.append(" AND ");

				if (key == null) {
					query.append("key_ IS NULL");
				}
				else {
					query.append("key_ = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("key_ ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				if (key != null) {
					qPos.add(key);
				}

				List<CategoriesProperty> list = q.list();

				result = list;

				CategoriesProperty categoriesProperty = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_E_K,
						finderArgs, list);
				}
				else {
					categoriesProperty = list.get(0);

					cacheResult(categoriesProperty);
				}

				return categoriesProperty;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_E_K,
						finderArgs, new ArrayList<CategoriesProperty>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (CategoriesProperty)result;
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

	public List<CategoriesProperty> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<CategoriesProperty> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<CategoriesProperty> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<CategoriesProperty> list = (List<CategoriesProperty>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.categories.model.CategoriesProperty ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("key_ ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<CategoriesProperty>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<CategoriesProperty>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<CategoriesProperty>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (CategoriesProperty categoriesProperty : findByCompanyId(companyId)) {
			remove(categoriesProperty);
		}
	}

	public void removeByEntryId(long entryId) throws SystemException {
		for (CategoriesProperty categoriesProperty : findByEntryId(entryId)) {
			remove(categoriesProperty);
		}
	}

	public void removeByC_K(long companyId, String key)
		throws SystemException {
		for (CategoriesProperty categoriesProperty : findByC_K(companyId, key)) {
			remove(categoriesProperty);
		}
	}

	public void removeByE_K(long entryId, String key)
		throws NoSuchPropertyException, SystemException {
		CategoriesProperty categoriesProperty = findByE_K(entryId, key);

		remove(categoriesProperty);
	}

	public void removeAll() throws SystemException {
		for (CategoriesProperty categoriesProperty : findAll()) {
			remove(categoriesProperty);
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

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.categories.model.CategoriesProperty WHERE ");

				query.append("companyId = ?");

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

	public int countByEntryId(long entryId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(entryId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ENTRYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.categories.model.CategoriesProperty WHERE ");

				query.append("entryId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ENTRYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_K(long companyId, String key) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId), key };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_K,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.categories.model.CategoriesProperty WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				if (key == null) {
					query.append("key_ IS NULL");
				}
				else {
					query.append("key_ = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (key != null) {
					qPos.add(key);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_K, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByE_K(long entryId, String key) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(entryId), key };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_E_K,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.categories.model.CategoriesProperty WHERE ");

				query.append("entryId = ?");

				query.append(" AND ");

				if (key == null) {
					query.append("key_ IS NULL");
				}
				else {
					query.append("key_ = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				if (key != null) {
					qPos.add(key);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_E_K, finderArgs,
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
						"SELECT COUNT(*) FROM com.liferay.portlet.categories.model.CategoriesProperty");

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
						"value.object.listener.com.liferay.portlet.categories.model.CategoriesProperty")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<CategoriesProperty>> listenersList = new ArrayList<ModelListener<CategoriesProperty>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<CategoriesProperty>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.categories.service.persistence.CategoriesEntryPersistence.impl")
	protected com.liferay.portlet.categories.service.persistence.CategoriesEntryPersistence categoriesEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.categories.service.persistence.CategoriesPropertyPersistence.impl")
	protected com.liferay.portlet.categories.service.persistence.CategoriesPropertyPersistence categoriesPropertyPersistence;
	@BeanReference(name = "com.liferay.portlet.categories.service.persistence.CategoriesVocabularyPersistence.impl")
	protected com.liferay.portlet.categories.service.persistence.CategoriesVocabularyPersistence categoriesVocabularyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static Log _log = LogFactoryUtil.getLog(CategoriesPropertyPersistenceImpl.class);
}