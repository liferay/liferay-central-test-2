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

package com.liferay.portlet.tags.service.persistence;

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
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.tags.NoSuchPropertyException;
import com.liferay.portlet.tags.model.TagsProperty;
import com.liferay.portlet.tags.model.impl.TagsPropertyImpl;
import com.liferay.portlet.tags.model.impl.TagsPropertyModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="TagsPropertyPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TagsPropertyPersistenceImpl extends BasePersistenceImpl
	implements TagsPropertyPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = TagsPropertyImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(TagsPropertyModelImpl.ENTITY_CACHE_ENABLED,
			TagsPropertyModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(TagsPropertyModelImpl.ENTITY_CACHE_ENABLED,
			TagsPropertyModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(TagsPropertyModelImpl.ENTITY_CACHE_ENABLED,
			TagsPropertyModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_ENTRYID = new FinderPath(TagsPropertyModelImpl.ENTITY_CACHE_ENABLED,
			TagsPropertyModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByEntryId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_ENTRYID = new FinderPath(TagsPropertyModelImpl.ENTITY_CACHE_ENABLED,
			TagsPropertyModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByEntryId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_ENTRYID = new FinderPath(TagsPropertyModelImpl.ENTITY_CACHE_ENABLED,
			TagsPropertyModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByEntryId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_K = new FinderPath(TagsPropertyModelImpl.ENTITY_CACHE_ENABLED,
			TagsPropertyModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_K",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_K = new FinderPath(TagsPropertyModelImpl.ENTITY_CACHE_ENABLED,
			TagsPropertyModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_K",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_K = new FinderPath(TagsPropertyModelImpl.ENTITY_CACHE_ENABLED,
			TagsPropertyModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_K",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_E_K = new FinderPath(TagsPropertyModelImpl.ENTITY_CACHE_ENABLED,
			TagsPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByE_K",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_E_K = new FinderPath(TagsPropertyModelImpl.ENTITY_CACHE_ENABLED,
			TagsPropertyModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByE_K",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(TagsPropertyModelImpl.ENTITY_CACHE_ENABLED,
			TagsPropertyModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(TagsPropertyModelImpl.ENTITY_CACHE_ENABLED,
			TagsPropertyModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(TagsProperty tagsProperty) {
		EntityCacheUtil.putResult(TagsPropertyModelImpl.ENTITY_CACHE_ENABLED,
			TagsPropertyImpl.class, tagsProperty.getPrimaryKey(), tagsProperty);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_E_K,
			new Object[] {
				new Long(tagsProperty.getEntryId()),
				
			tagsProperty.getKey()
			}, tagsProperty);
	}

	public void cacheResult(List<TagsProperty> tagsProperties) {
		for (TagsProperty tagsProperty : tagsProperties) {
			if (EntityCacheUtil.getResult(
						TagsPropertyModelImpl.ENTITY_CACHE_ENABLED,
						TagsPropertyImpl.class, tagsProperty.getPrimaryKey(),
						this) == null) {
				cacheResult(tagsProperty);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(TagsPropertyImpl.class.getName());
		EntityCacheUtil.clearCache(TagsPropertyImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public TagsProperty create(long propertyId) {
		TagsProperty tagsProperty = new TagsPropertyImpl();

		tagsProperty.setNew(true);
		tagsProperty.setPrimaryKey(propertyId);

		return tagsProperty;
	}

	public TagsProperty remove(long propertyId)
		throws NoSuchPropertyException, SystemException {
		Session session = null;

		try {
			session = openSession();

			TagsProperty tagsProperty = (TagsProperty)session.get(TagsPropertyImpl.class,
					new Long(propertyId));

			if (tagsProperty == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No TagsProperty exists with the primary key " +
						propertyId);
				}

				throw new NoSuchPropertyException(
					"No TagsProperty exists with the primary key " +
					propertyId);
			}

			return remove(tagsProperty);
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

	public TagsProperty remove(TagsProperty tagsProperty)
		throws SystemException {
		for (ModelListener<TagsProperty> listener : listeners) {
			listener.onBeforeRemove(tagsProperty);
		}

		tagsProperty = removeImpl(tagsProperty);

		for (ModelListener<TagsProperty> listener : listeners) {
			listener.onAfterRemove(tagsProperty);
		}

		return tagsProperty;
	}

	protected TagsProperty removeImpl(TagsProperty tagsProperty)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (tagsProperty.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(TagsPropertyImpl.class,
						tagsProperty.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(tagsProperty);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		TagsPropertyModelImpl tagsPropertyModelImpl = (TagsPropertyModelImpl)tagsProperty;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_E_K,
			new Object[] {
				new Long(tagsPropertyModelImpl.getOriginalEntryId()),
				
			tagsPropertyModelImpl.getOriginalKey()
			});

		EntityCacheUtil.removeResult(TagsPropertyModelImpl.ENTITY_CACHE_ENABLED,
			TagsPropertyImpl.class, tagsProperty.getPrimaryKey());

		return tagsProperty;
	}

	/**
	 * @deprecated Use <code>update(TagsProperty tagsProperty, boolean merge)</code>.
	 */
	public TagsProperty update(TagsProperty tagsProperty)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(TagsProperty tagsProperty) method. Use update(TagsProperty tagsProperty, boolean merge) instead.");
		}

		return update(tagsProperty, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        tagsProperty the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when tagsProperty is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public TagsProperty update(TagsProperty tagsProperty, boolean merge)
		throws SystemException {
		boolean isNew = tagsProperty.isNew();

		for (ModelListener<TagsProperty> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(tagsProperty);
			}
			else {
				listener.onBeforeUpdate(tagsProperty);
			}
		}

		tagsProperty = updateImpl(tagsProperty, merge);

		for (ModelListener<TagsProperty> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(tagsProperty);
			}
			else {
				listener.onAfterUpdate(tagsProperty);
			}
		}

		return tagsProperty;
	}

	public TagsProperty updateImpl(
		com.liferay.portlet.tags.model.TagsProperty tagsProperty, boolean merge)
		throws SystemException {
		boolean isNew = tagsProperty.isNew();

		TagsPropertyModelImpl tagsPropertyModelImpl = (TagsPropertyModelImpl)tagsProperty;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, tagsProperty, merge);

			tagsProperty.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(TagsPropertyModelImpl.ENTITY_CACHE_ENABLED,
			TagsPropertyImpl.class, tagsProperty.getPrimaryKey(), tagsProperty);

		if (!isNew &&
				((tagsProperty.getEntryId() != tagsPropertyModelImpl.getOriginalEntryId()) ||
				!Validator.equals(tagsProperty.getKey(),
					tagsPropertyModelImpl.getOriginalKey()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_E_K,
				new Object[] {
					new Long(tagsPropertyModelImpl.getOriginalEntryId()),
					
				tagsPropertyModelImpl.getOriginalKey()
				});
		}

		if (isNew ||
				((tagsProperty.getEntryId() != tagsPropertyModelImpl.getOriginalEntryId()) ||
				!tagsProperty.getKey()
								 .equals(tagsPropertyModelImpl.getOriginalKey()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_E_K,
				new Object[] {
					new Long(tagsProperty.getEntryId()),
					
				tagsProperty.getKey()
				}, tagsProperty);
		}

		return tagsProperty;
	}

	public TagsProperty findByPrimaryKey(long propertyId)
		throws NoSuchPropertyException, SystemException {
		TagsProperty tagsProperty = fetchByPrimaryKey(propertyId);

		if (tagsProperty == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No TagsProperty exists with the primary key " +
					propertyId);
			}

			throw new NoSuchPropertyException(
				"No TagsProperty exists with the primary key " + propertyId);
		}

		return tagsProperty;
	}

	public TagsProperty fetchByPrimaryKey(long propertyId)
		throws SystemException {
		TagsProperty tagsProperty = (TagsProperty)EntityCacheUtil.getResult(TagsPropertyModelImpl.ENTITY_CACHE_ENABLED,
				TagsPropertyImpl.class, propertyId, this);

		if (tagsProperty == null) {
			Session session = null;

			try {
				session = openSession();

				tagsProperty = (TagsProperty)session.get(TagsPropertyImpl.class,
						new Long(propertyId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (tagsProperty != null) {
					cacheResult(tagsProperty);
				}

				closeSession(session);
			}
		}

		return tagsProperty;
	}

	public List<TagsProperty> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<TagsProperty> list = (List<TagsProperty>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.tags.model.TagsProperty WHERE ");

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
					list = new ArrayList<TagsProperty>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<TagsProperty> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<TagsProperty> findByCompanyId(long companyId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<TagsProperty> list = (List<TagsProperty>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.tags.model.TagsProperty WHERE ");

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

				list = (List<TagsProperty>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TagsProperty>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public TagsProperty findByCompanyId_First(long companyId,
		OrderByComparator obc) throws NoSuchPropertyException, SystemException {
		List<TagsProperty> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No TagsProperty exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPropertyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TagsProperty findByCompanyId_Last(long companyId,
		OrderByComparator obc) throws NoSuchPropertyException, SystemException {
		int count = countByCompanyId(companyId);

		List<TagsProperty> list = findByCompanyId(companyId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No TagsProperty exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPropertyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TagsProperty[] findByCompanyId_PrevAndNext(long propertyId,
		long companyId, OrderByComparator obc)
		throws NoSuchPropertyException, SystemException {
		TagsProperty tagsProperty = findByPrimaryKey(propertyId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.tags.model.TagsProperty WHERE ");

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
					tagsProperty);

			TagsProperty[] array = new TagsPropertyImpl[3];

			array[0] = (TagsProperty)objArray[0];
			array[1] = (TagsProperty)objArray[1];
			array[2] = (TagsProperty)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TagsProperty> findByEntryId(long entryId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(entryId) };

		List<TagsProperty> list = (List<TagsProperty>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ENTRYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.tags.model.TagsProperty WHERE ");

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
					list = new ArrayList<TagsProperty>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_ENTRYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<TagsProperty> findByEntryId(long entryId, int start, int end)
		throws SystemException {
		return findByEntryId(entryId, start, end, null);
	}

	public List<TagsProperty> findByEntryId(long entryId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(entryId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<TagsProperty> list = (List<TagsProperty>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_ENTRYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.tags.model.TagsProperty WHERE ");

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

				list = (List<TagsProperty>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TagsProperty>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_ENTRYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public TagsProperty findByEntryId_First(long entryId, OrderByComparator obc)
		throws NoSuchPropertyException, SystemException {
		List<TagsProperty> list = findByEntryId(entryId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No TagsProperty exists with the key {");

			msg.append("entryId=" + entryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPropertyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TagsProperty findByEntryId_Last(long entryId, OrderByComparator obc)
		throws NoSuchPropertyException, SystemException {
		int count = countByEntryId(entryId);

		List<TagsProperty> list = findByEntryId(entryId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No TagsProperty exists with the key {");

			msg.append("entryId=" + entryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPropertyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TagsProperty[] findByEntryId_PrevAndNext(long propertyId,
		long entryId, OrderByComparator obc)
		throws NoSuchPropertyException, SystemException {
		TagsProperty tagsProperty = findByPrimaryKey(propertyId);

		int count = countByEntryId(entryId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.tags.model.TagsProperty WHERE ");

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
					tagsProperty);

			TagsProperty[] array = new TagsPropertyImpl[3];

			array[0] = (TagsProperty)objArray[0];
			array[1] = (TagsProperty)objArray[1];
			array[2] = (TagsProperty)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TagsProperty> findByC_K(long companyId, String key)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId), key };

		List<TagsProperty> list = (List<TagsProperty>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_K,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.tags.model.TagsProperty WHERE ");

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
					list = new ArrayList<TagsProperty>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_K, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<TagsProperty> findByC_K(long companyId, String key, int start,
		int end) throws SystemException {
		return findByC_K(companyId, key, start, end, null);
	}

	public List<TagsProperty> findByC_K(long companyId, String key, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				key,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<TagsProperty> list = (List<TagsProperty>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_K,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.tags.model.TagsProperty WHERE ");

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

				list = (List<TagsProperty>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TagsProperty>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_K,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public TagsProperty findByC_K_First(long companyId, String key,
		OrderByComparator obc) throws NoSuchPropertyException, SystemException {
		List<TagsProperty> list = findByC_K(companyId, key, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No TagsProperty exists with the key {");

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

	public TagsProperty findByC_K_Last(long companyId, String key,
		OrderByComparator obc) throws NoSuchPropertyException, SystemException {
		int count = countByC_K(companyId, key);

		List<TagsProperty> list = findByC_K(companyId, key, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No TagsProperty exists with the key {");

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

	public TagsProperty[] findByC_K_PrevAndNext(long propertyId,
		long companyId, String key, OrderByComparator obc)
		throws NoSuchPropertyException, SystemException {
		TagsProperty tagsProperty = findByPrimaryKey(propertyId);

		int count = countByC_K(companyId, key);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.tags.model.TagsProperty WHERE ");

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
					tagsProperty);

			TagsProperty[] array = new TagsPropertyImpl[3];

			array[0] = (TagsProperty)objArray[0];
			array[1] = (TagsProperty)objArray[1];
			array[2] = (TagsProperty)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public TagsProperty findByE_K(long entryId, String key)
		throws NoSuchPropertyException, SystemException {
		TagsProperty tagsProperty = fetchByE_K(entryId, key);

		if (tagsProperty == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No TagsProperty exists with the key {");

			msg.append("entryId=" + entryId);

			msg.append(", ");
			msg.append("key=" + key);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPropertyException(msg.toString());
		}

		return tagsProperty;
	}

	public TagsProperty fetchByE_K(long entryId, String key)
		throws SystemException {
		return fetchByE_K(entryId, key, true);
	}

	public TagsProperty fetchByE_K(long entryId, String key,
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
					"FROM com.liferay.portlet.tags.model.TagsProperty WHERE ");

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

				List<TagsProperty> list = q.list();

				result = list;

				TagsProperty tagsProperty = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_E_K,
						finderArgs, list);
				}
				else {
					tagsProperty = list.get(0);

					cacheResult(tagsProperty);

					if ((tagsProperty.getEntryId() != entryId) ||
							(tagsProperty.getKey() == null) ||
							!tagsProperty.getKey().equals(key)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_E_K,
							finderArgs, tagsProperty);
					}
				}

				return tagsProperty;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_E_K,
						finderArgs, new ArrayList<TagsProperty>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (TagsProperty)result;
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

	public List<TagsProperty> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<TagsProperty> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<TagsProperty> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<TagsProperty> list = (List<TagsProperty>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.tags.model.TagsProperty ");

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
					list = (List<TagsProperty>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<TagsProperty>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TagsProperty>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (TagsProperty tagsProperty : findByCompanyId(companyId)) {
			remove(tagsProperty);
		}
	}

	public void removeByEntryId(long entryId) throws SystemException {
		for (TagsProperty tagsProperty : findByEntryId(entryId)) {
			remove(tagsProperty);
		}
	}

	public void removeByC_K(long companyId, String key)
		throws SystemException {
		for (TagsProperty tagsProperty : findByC_K(companyId, key)) {
			remove(tagsProperty);
		}
	}

	public void removeByE_K(long entryId, String key)
		throws NoSuchPropertyException, SystemException {
		TagsProperty tagsProperty = findByE_K(entryId, key);

		remove(tagsProperty);
	}

	public void removeAll() throws SystemException {
		for (TagsProperty tagsProperty : findAll()) {
			remove(tagsProperty);
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
					"FROM com.liferay.portlet.tags.model.TagsProperty WHERE ");

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
					"FROM com.liferay.portlet.tags.model.TagsProperty WHERE ");

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
					"FROM com.liferay.portlet.tags.model.TagsProperty WHERE ");

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
					"FROM com.liferay.portlet.tags.model.TagsProperty WHERE ");

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
						"SELECT COUNT(*) FROM com.liferay.portlet.tags.model.TagsProperty");

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
						"value.object.listener.com.liferay.portlet.tags.model.TagsProperty")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<TagsProperty>> listenersList = new ArrayList<ModelListener<TagsProperty>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<TagsProperty>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsAssetPersistence.impl")
	protected com.liferay.portlet.tags.service.persistence.TagsAssetPersistence tagsAssetPersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsEntryPersistence.impl")
	protected com.liferay.portlet.tags.service.persistence.TagsEntryPersistence tagsEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsPropertyPersistence.impl")
	protected com.liferay.portlet.tags.service.persistence.TagsPropertyPersistence tagsPropertyPersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsSourcePersistence.impl")
	protected com.liferay.portlet.tags.service.persistence.TagsSourcePersistence tagsSourcePersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsVocabularyPersistence.impl")
	protected com.liferay.portlet.tags.service.persistence.TagsVocabularyPersistence tagsVocabularyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static Log _log = LogFactoryUtil.getLog(TagsPropertyPersistenceImpl.class);
}