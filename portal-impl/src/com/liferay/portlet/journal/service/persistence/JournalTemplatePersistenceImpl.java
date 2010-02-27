/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.NoSuchModelException;
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
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.journal.NoSuchTemplateException;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.impl.JournalTemplateImpl;
import com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="JournalTemplatePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalTemplatePersistence
 * @see       JournalTemplateUtil
 * @generated
 */
public class JournalTemplatePersistenceImpl extends BasePersistenceImpl<JournalTemplate>
	implements JournalTemplatePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = JournalTemplateImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_TEMPLATEID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByTemplateId",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_TEMPLATEID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByTemplateId",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_TEMPLATEID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByTemplateId",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_SMALLIMAGEID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchBySmallImageId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_SMALLIMAGEID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countBySmallImageId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_T = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_T",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_G_T = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_T",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_S = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_S",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_S = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_S = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_S",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(JournalTemplate journalTemplate) {
		EntityCacheUtil.putResult(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateImpl.class, journalTemplate.getPrimaryKey(),
			journalTemplate);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				journalTemplate.getUuid(),
				new Long(journalTemplate.getGroupId())
			}, journalTemplate);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
			new Object[] { new Long(journalTemplate.getSmallImageId()) },
			journalTemplate);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_T,
			new Object[] {
				new Long(journalTemplate.getGroupId()),
				
			journalTemplate.getTemplateId()
			}, journalTemplate);
	}

	public void cacheResult(List<JournalTemplate> journalTemplates) {
		for (JournalTemplate journalTemplate : journalTemplates) {
			if (EntityCacheUtil.getResult(
						JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
						JournalTemplateImpl.class,
						journalTemplate.getPrimaryKey(), this) == null) {
				cacheResult(journalTemplate);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(JournalTemplateImpl.class.getName());
		EntityCacheUtil.clearCache(JournalTemplateImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public JournalTemplate create(long id) {
		JournalTemplate journalTemplate = new JournalTemplateImpl();

		journalTemplate.setNew(true);
		journalTemplate.setPrimaryKey(id);

		String uuid = PortalUUIDUtil.generate();

		journalTemplate.setUuid(uuid);

		return journalTemplate;
	}

	public JournalTemplate remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public JournalTemplate remove(long id)
		throws NoSuchTemplateException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalTemplate journalTemplate = (JournalTemplate)session.get(JournalTemplateImpl.class,
					new Long(id));

			if (journalTemplate == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
				}

				throw new NoSuchTemplateException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					id);
			}

			return remove(journalTemplate);
		}
		catch (NoSuchTemplateException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalTemplate remove(JournalTemplate journalTemplate)
		throws SystemException {
		for (ModelListener<JournalTemplate> listener : listeners) {
			listener.onBeforeRemove(journalTemplate);
		}

		journalTemplate = removeImpl(journalTemplate);

		for (ModelListener<JournalTemplate> listener : listeners) {
			listener.onAfterRemove(journalTemplate);
		}

		return journalTemplate;
	}

	protected JournalTemplate removeImpl(JournalTemplate journalTemplate)
		throws SystemException {
		journalTemplate = toUnwrappedModel(journalTemplate);

		Session session = null;

		try {
			session = openSession();

			if (journalTemplate.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(JournalTemplateImpl.class,
						journalTemplate.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(journalTemplate);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		JournalTemplateModelImpl journalTemplateModelImpl = (JournalTemplateModelImpl)journalTemplate;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				journalTemplateModelImpl.getOriginalUuid(),
				new Long(journalTemplateModelImpl.getOriginalGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
			new Object[] {
				new Long(journalTemplateModelImpl.getOriginalSmallImageId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_T,
			new Object[] {
				new Long(journalTemplateModelImpl.getOriginalGroupId()),
				
			journalTemplateModelImpl.getOriginalTemplateId()
			});

		EntityCacheUtil.removeResult(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateImpl.class, journalTemplate.getPrimaryKey());

		return journalTemplate;
	}

	public JournalTemplate updateImpl(
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate,
		boolean merge) throws SystemException {
		journalTemplate = toUnwrappedModel(journalTemplate);

		boolean isNew = journalTemplate.isNew();

		JournalTemplateModelImpl journalTemplateModelImpl = (JournalTemplateModelImpl)journalTemplate;

		if (Validator.isNull(journalTemplate.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			journalTemplate.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, journalTemplate, merge);

			journalTemplate.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateImpl.class, journalTemplate.getPrimaryKey(),
			journalTemplate);

		if (!isNew &&
				(!Validator.equals(journalTemplate.getUuid(),
					journalTemplateModelImpl.getOriginalUuid()) ||
				(journalTemplate.getGroupId() != journalTemplateModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					journalTemplateModelImpl.getOriginalUuid(),
					new Long(journalTemplateModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(journalTemplate.getUuid(),
					journalTemplateModelImpl.getOriginalUuid()) ||
				(journalTemplate.getGroupId() != journalTemplateModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					journalTemplate.getUuid(),
					new Long(journalTemplate.getGroupId())
				}, journalTemplate);
		}

		if (!isNew &&
				(journalTemplate.getSmallImageId() != journalTemplateModelImpl.getOriginalSmallImageId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
				new Object[] {
					new Long(journalTemplateModelImpl.getOriginalSmallImageId())
				});
		}

		if (isNew ||
				(journalTemplate.getSmallImageId() != journalTemplateModelImpl.getOriginalSmallImageId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
				new Object[] { new Long(journalTemplate.getSmallImageId()) },
				journalTemplate);
		}

		if (!isNew &&
				((journalTemplate.getGroupId() != journalTemplateModelImpl.getOriginalGroupId()) ||
				!Validator.equals(journalTemplate.getTemplateId(),
					journalTemplateModelImpl.getOriginalTemplateId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_T,
				new Object[] {
					new Long(journalTemplateModelImpl.getOriginalGroupId()),
					
				journalTemplateModelImpl.getOriginalTemplateId()
				});
		}

		if (isNew ||
				((journalTemplate.getGroupId() != journalTemplateModelImpl.getOriginalGroupId()) ||
				!Validator.equals(journalTemplate.getTemplateId(),
					journalTemplateModelImpl.getOriginalTemplateId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_T,
				new Object[] {
					new Long(journalTemplate.getGroupId()),
					
				journalTemplate.getTemplateId()
				}, journalTemplate);
		}

		return journalTemplate;
	}

	protected JournalTemplate toUnwrappedModel(JournalTemplate journalTemplate) {
		if (journalTemplate instanceof JournalTemplateImpl) {
			return journalTemplate;
		}

		JournalTemplateImpl journalTemplateImpl = new JournalTemplateImpl();

		journalTemplateImpl.setNew(journalTemplate.isNew());
		journalTemplateImpl.setPrimaryKey(journalTemplate.getPrimaryKey());

		journalTemplateImpl.setUuid(journalTemplate.getUuid());
		journalTemplateImpl.setId(journalTemplate.getId());
		journalTemplateImpl.setGroupId(journalTemplate.getGroupId());
		journalTemplateImpl.setCompanyId(journalTemplate.getCompanyId());
		journalTemplateImpl.setUserId(journalTemplate.getUserId());
		journalTemplateImpl.setUserName(journalTemplate.getUserName());
		journalTemplateImpl.setCreateDate(journalTemplate.getCreateDate());
		journalTemplateImpl.setModifiedDate(journalTemplate.getModifiedDate());
		journalTemplateImpl.setTemplateId(journalTemplate.getTemplateId());
		journalTemplateImpl.setStructureId(journalTemplate.getStructureId());
		journalTemplateImpl.setName(journalTemplate.getName());
		journalTemplateImpl.setDescription(journalTemplate.getDescription());
		journalTemplateImpl.setXsl(journalTemplate.getXsl());
		journalTemplateImpl.setLangType(journalTemplate.getLangType());
		journalTemplateImpl.setCacheable(journalTemplate.isCacheable());
		journalTemplateImpl.setSmallImage(journalTemplate.isSmallImage());
		journalTemplateImpl.setSmallImageId(journalTemplate.getSmallImageId());
		journalTemplateImpl.setSmallImageURL(journalTemplate.getSmallImageURL());

		return journalTemplateImpl;
	}

	public JournalTemplate findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public JournalTemplate findByPrimaryKey(long id)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchByPrimaryKey(id);

		if (journalTemplate == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
			}

			throw new NoSuchTemplateException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				id);
		}

		return journalTemplate;
	}

	public JournalTemplate fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public JournalTemplate fetchByPrimaryKey(long id) throws SystemException {
		JournalTemplate journalTemplate = (JournalTemplate)EntityCacheUtil.getResult(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
				JournalTemplateImpl.class, id, this);

		if (journalTemplate == null) {
			Session session = null;

			try {
				session = openSession();

				journalTemplate = (JournalTemplate)session.get(JournalTemplateImpl.class,
						new Long(id));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (journalTemplate != null) {
					cacheResult(journalTemplate);
				}

				closeSession(session);
			}
		}

		return journalTemplate;
	}

	public List<JournalTemplate> findByUuid(String uuid)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		List<JournalTemplate> list = (List<JournalTemplate>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

				if (uuid == null) {
					query.append(_FINDER_COLUMN_UUID_UUID_1);
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_UUID_UUID_3);
					}
					else {
						query.append(_FINDER_COLUMN_UUID_UUID_2);
					}
				}

				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalTemplate>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalTemplate> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<JournalTemplate> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalTemplate> list = (List<JournalTemplate>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
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
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

				if (uuid == null) {
					query.append(_FINDER_COLUMN_UUID_UUID_1);
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_UUID_UUID_3);
					}
					else {
						query.append(_FINDER_COLUMN_UUID_UUID_2);
					}
				}

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<JournalTemplate>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalTemplate>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalTemplate findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchTemplateException, SystemException {
		List<JournalTemplate> list = findByUuid(uuid, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalTemplate findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchTemplateException, SystemException {
		int count = countByUuid(uuid);

		List<JournalTemplate> list = findByUuid(uuid, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalTemplate[] findByUuid_PrevAndNext(long id, String uuid,
		OrderByComparator obc) throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByPrimaryKey(id);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(3 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalTemplate);

			JournalTemplate[] array = new JournalTemplateImpl[3];

			array[0] = (JournalTemplate)objArray[0];
			array[1] = (JournalTemplate)objArray[1];
			array[2] = (JournalTemplate)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalTemplate findByUUID_G(String uuid, long groupId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchByUUID_G(uuid, groupId);

		if (journalTemplate == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchTemplateException(msg.toString());
		}

		return journalTemplate;
	}

	public JournalTemplate fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	public JournalTemplate fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, new Long(groupId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

				if (uuid == null) {
					query.append(_FINDER_COLUMN_UUID_G_UUID_1);
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_UUID_G_UUID_3);
					}
					else {
						query.append(_FINDER_COLUMN_UUID_G_UUID_2);
					}
				}

				query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<JournalTemplate> list = q.list();

				result = list;

				JournalTemplate journalTemplate = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					journalTemplate = list.get(0);

					cacheResult(journalTemplate);

					if ((journalTemplate.getUuid() == null) ||
							!journalTemplate.getUuid().equals(uuid) ||
							(journalTemplate.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, journalTemplate);
					}
				}

				return journalTemplate;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, new ArrayList<JournalTemplate>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (JournalTemplate)result;
			}
		}
	}

	public List<JournalTemplate> findByGroupId(long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<JournalTemplate> list = (List<JournalTemplate>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalTemplate>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalTemplate> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<JournalTemplate> findByGroupId(long groupId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalTemplate> list = (List<JournalTemplate>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
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
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<JournalTemplate>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalTemplate>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalTemplate findByGroupId_First(long groupId,
		OrderByComparator obc) throws NoSuchTemplateException, SystemException {
		List<JournalTemplate> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalTemplate findByGroupId_Last(long groupId,
		OrderByComparator obc) throws NoSuchTemplateException, SystemException {
		int count = countByGroupId(groupId);

		List<JournalTemplate> list = findByGroupId(groupId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalTemplate[] findByGroupId_PrevAndNext(long id, long groupId,
		OrderByComparator obc) throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByPrimaryKey(id);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(3 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalTemplate);

			JournalTemplate[] array = new JournalTemplateImpl[3];

			array[0] = (JournalTemplate)objArray[0];
			array[1] = (JournalTemplate)objArray[1];
			array[2] = (JournalTemplate)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalTemplate> findByTemplateId(String templateId)
		throws SystemException {
		Object[] finderArgs = new Object[] { templateId };

		List<JournalTemplate> list = (List<JournalTemplate>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_TEMPLATEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

				if (templateId == null) {
					query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_1);
				}
				else {
					if (templateId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_3);
					}
					else {
						query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_2);
					}
				}

				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (templateId != null) {
					qPos.add(templateId);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalTemplate>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_TEMPLATEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalTemplate> findByTemplateId(String templateId, int start,
		int end) throws SystemException {
		return findByTemplateId(templateId, start, end, null);
	}

	public List<JournalTemplate> findByTemplateId(String templateId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				templateId,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalTemplate> list = (List<JournalTemplate>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_TEMPLATEID,
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
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

				if (templateId == null) {
					query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_1);
				}
				else {
					if (templateId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_3);
					}
					else {
						query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_2);
					}
				}

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (templateId != null) {
					qPos.add(templateId);
				}

				list = (List<JournalTemplate>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalTemplate>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_TEMPLATEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalTemplate findByTemplateId_First(String templateId,
		OrderByComparator obc) throws NoSuchTemplateException, SystemException {
		List<JournalTemplate> list = findByTemplateId(templateId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("templateId=");
			msg.append(templateId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalTemplate findByTemplateId_Last(String templateId,
		OrderByComparator obc) throws NoSuchTemplateException, SystemException {
		int count = countByTemplateId(templateId);

		List<JournalTemplate> list = findByTemplateId(templateId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("templateId=");
			msg.append(templateId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalTemplate[] findByTemplateId_PrevAndNext(long id,
		String templateId, OrderByComparator obc)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByPrimaryKey(id);

		int count = countByTemplateId(templateId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(3 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

			if (templateId == null) {
				query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_1);
			}
			else {
				if (templateId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_3);
				}
				else {
					query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_2);
				}
			}

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			if (templateId != null) {
				qPos.add(templateId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalTemplate);

			JournalTemplate[] array = new JournalTemplateImpl[3];

			array[0] = (JournalTemplate)objArray[0];
			array[1] = (JournalTemplate)objArray[1];
			array[2] = (JournalTemplate)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalTemplate findBySmallImageId(long smallImageId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchBySmallImageId(smallImageId);

		if (journalTemplate == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("smallImageId=");
			msg.append(smallImageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchTemplateException(msg.toString());
		}

		return journalTemplate;
	}

	public JournalTemplate fetchBySmallImageId(long smallImageId)
		throws SystemException {
		return fetchBySmallImageId(smallImageId, true);
	}

	public JournalTemplate fetchBySmallImageId(long smallImageId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(smallImageId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

				query.append(_FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2);

				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

				List<JournalTemplate> list = q.list();

				result = list;

				JournalTemplate journalTemplate = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
						finderArgs, list);
				}
				else {
					journalTemplate = list.get(0);

					cacheResult(journalTemplate);

					if ((journalTemplate.getSmallImageId() != smallImageId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
							finderArgs, journalTemplate);
					}
				}

				return journalTemplate;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
						finderArgs, new ArrayList<JournalTemplate>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (JournalTemplate)result;
			}
		}
	}

	public JournalTemplate findByG_T(long groupId, String templateId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchByG_T(groupId, templateId);

		if (journalTemplate == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", templateId=");
			msg.append(templateId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchTemplateException(msg.toString());
		}

		return journalTemplate;
	}

	public JournalTemplate fetchByG_T(long groupId, String templateId)
		throws SystemException {
		return fetchByG_T(groupId, templateId, true);
	}

	public JournalTemplate fetchByG_T(long groupId, String templateId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), templateId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_T,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

				query.append(_FINDER_COLUMN_G_T_GROUPID_2);

				if (templateId == null) {
					query.append(_FINDER_COLUMN_G_T_TEMPLATEID_1);
				}
				else {
					if (templateId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_T_TEMPLATEID_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_T_TEMPLATEID_2);
					}
				}

				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (templateId != null) {
					qPos.add(templateId);
				}

				List<JournalTemplate> list = q.list();

				result = list;

				JournalTemplate journalTemplate = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_T,
						finderArgs, list);
				}
				else {
					journalTemplate = list.get(0);

					cacheResult(journalTemplate);

					if ((journalTemplate.getGroupId() != groupId) ||
							(journalTemplate.getTemplateId() == null) ||
							!journalTemplate.getTemplateId().equals(templateId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_T,
							finderArgs, journalTemplate);
					}
				}

				return journalTemplate;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_T,
						finderArgs, new ArrayList<JournalTemplate>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (JournalTemplate)result;
			}
		}
	}

	public List<JournalTemplate> findByG_S(long groupId, String structureId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), structureId };

		List<JournalTemplate> list = (List<JournalTemplate>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

				query.append(_FINDER_COLUMN_G_S_GROUPID_2);

				if (structureId == null) {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
				}
				else {
					if (structureId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
					}
				}

				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (structureId != null) {
					qPos.add(structureId);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalTemplate>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_S, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalTemplate> findByG_S(long groupId, String structureId,
		int start, int end) throws SystemException {
		return findByG_S(groupId, structureId, start, end, null);
	}

	public List<JournalTemplate> findByG_S(long groupId, String structureId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				structureId,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalTemplate> list = (List<JournalTemplate>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(4 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(4);
				}

				query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

				query.append(_FINDER_COLUMN_G_S_GROUPID_2);

				if (structureId == null) {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
				}
				else {
					if (structureId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
					}
				}

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (structureId != null) {
					qPos.add(structureId);
				}

				list = (List<JournalTemplate>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalTemplate>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalTemplate findByG_S_First(long groupId, String structureId,
		OrderByComparator obc) throws NoSuchTemplateException, SystemException {
		List<JournalTemplate> list = findByG_S(groupId, structureId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", structureId=");
			msg.append(structureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalTemplate findByG_S_Last(long groupId, String structureId,
		OrderByComparator obc) throws NoSuchTemplateException, SystemException {
		int count = countByG_S(groupId, structureId);

		List<JournalTemplate> list = findByG_S(groupId, structureId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", structureId=");
			msg.append(structureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalTemplate[] findByG_S_PrevAndNext(long id, long groupId,
		String structureId, OrderByComparator obc)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByPrimaryKey(id);

		int count = countByG_S(groupId, structureId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(4 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			if (structureId == null) {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
			}
			else {
				if (structureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
				}
			}

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (structureId != null) {
				qPos.add(structureId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalTemplate);

			JournalTemplate[] array = new JournalTemplateImpl[3];

			array[0] = (JournalTemplate)objArray[0];
			array[1] = (JournalTemplate)objArray[1];
			array[2] = (JournalTemplate)objArray[2];

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

	public List<JournalTemplate> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<JournalTemplate> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<JournalTemplate> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalTemplate> list = (List<JournalTemplate>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_JOURNALTEMPLATE);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_JOURNALTEMPLATE.concat(JournalTemplateModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (obc == null) {
					list = (List<JournalTemplate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<JournalTemplate>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalTemplate>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (JournalTemplate journalTemplate : findByUuid(uuid)) {
			remove(journalTemplate);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByUUID_G(uuid, groupId);

		remove(journalTemplate);
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (JournalTemplate journalTemplate : findByGroupId(groupId)) {
			remove(journalTemplate);
		}
	}

	public void removeByTemplateId(String templateId) throws SystemException {
		for (JournalTemplate journalTemplate : findByTemplateId(templateId)) {
			remove(journalTemplate);
		}
	}

	public void removeBySmallImageId(long smallImageId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findBySmallImageId(smallImageId);

		remove(journalTemplate);
	}

	public void removeByG_T(long groupId, String templateId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByG_T(groupId, templateId);

		remove(journalTemplate);
	}

	public void removeByG_S(long groupId, String structureId)
		throws SystemException {
		for (JournalTemplate journalTemplate : findByG_S(groupId, structureId)) {
			remove(journalTemplate);
		}
	}

	public void removeAll() throws SystemException {
		for (JournalTemplate journalTemplate : findAll()) {
			remove(journalTemplate);
		}
	}

	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

				if (uuid == null) {
					query.append(_FINDER_COLUMN_UUID_UUID_1);
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_UUID_UUID_3);
					}
					else {
						query.append(_FINDER_COLUMN_UUID_UUID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

				if (uuid == null) {
					query.append(_FINDER_COLUMN_UUID_G_UUID_1);
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_UUID_G_UUID_3);
					}
					else {
						query.append(_FINDER_COLUMN_UUID_G_UUID_2);
					}
				}

				query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_GROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByTemplateId(String templateId) throws SystemException {
		Object[] finderArgs = new Object[] { templateId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_TEMPLATEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

				if (templateId == null) {
					query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_1);
				}
				else {
					if (templateId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_3);
					}
					else {
						query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (templateId != null) {
					qPos.add(templateId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_TEMPLATEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countBySmallImageId(long smallImageId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(smallImageId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

				query.append(_FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_T(long groupId, String templateId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), templateId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

				query.append(_FINDER_COLUMN_G_T_GROUPID_2);

				if (templateId == null) {
					query.append(_FINDER_COLUMN_G_T_TEMPLATEID_1);
				}
				else {
					if (templateId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_T_TEMPLATEID_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_T_TEMPLATEID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (templateId != null) {
					qPos.add(templateId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_T, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_S(long groupId, String structureId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), structureId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

				query.append(_FINDER_COLUMN_G_S_GROUPID_2);

				if (structureId == null) {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
				}
				else {
					if (structureId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (structureId != null) {
					qPos.add(structureId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_S, finderArgs,
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

				Query q = session.createQuery(_SQL_COUNT_JOURNALTEMPLATE);

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
						"value.object.listener.com.liferay.portlet.journal.model.JournalTemplate")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<JournalTemplate>> listenersList = new ArrayList<ModelListener<JournalTemplate>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<JournalTemplate>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticlePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalArticlePersistence journalArticlePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticleImagePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalArticleImagePersistence journalArticleImagePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticleResourcePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalArticleResourcePersistence journalArticleResourcePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalContentSearchPersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalContentSearchPersistence journalContentSearchPersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalFeedPersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalFeedPersistence journalFeedPersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalStructurePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalStructurePersistence journalStructurePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalTemplatePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalTemplatePersistence journalTemplatePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebDAVPropsPersistence")
	protected com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence")
	protected com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence expandoValuePersistence;
	private static final String _SQL_SELECT_JOURNALTEMPLATE = "SELECT journalTemplate FROM JournalTemplate journalTemplate";
	private static final String _SQL_SELECT_JOURNALTEMPLATE_WHERE = "SELECT journalTemplate FROM JournalTemplate journalTemplate WHERE ";
	private static final String _SQL_COUNT_JOURNALTEMPLATE = "SELECT COUNT(journalTemplate) FROM JournalTemplate journalTemplate";
	private static final String _SQL_COUNT_JOURNALTEMPLATE_WHERE = "SELECT COUNT(journalTemplate) FROM JournalTemplate journalTemplate WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "journalTemplate.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "journalTemplate.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(journalTemplate.uuid IS NULL OR journalTemplate.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "journalTemplate.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "journalTemplate.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(journalTemplate.uuid IS NULL OR journalTemplate.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "journalTemplate.groupId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "journalTemplate.groupId = ?";
	private static final String _FINDER_COLUMN_TEMPLATEID_TEMPLATEID_1 = "journalTemplate.templateId IS NULL";
	private static final String _FINDER_COLUMN_TEMPLATEID_TEMPLATEID_2 = "journalTemplate.templateId = ?";
	private static final String _FINDER_COLUMN_TEMPLATEID_TEMPLATEID_3 = "(journalTemplate.templateId IS NULL OR journalTemplate.templateId = ?)";
	private static final String _FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2 = "journalTemplate.smallImageId = ?";
	private static final String _FINDER_COLUMN_G_T_GROUPID_2 = "journalTemplate.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_T_TEMPLATEID_1 = "journalTemplate.templateId IS NULL";
	private static final String _FINDER_COLUMN_G_T_TEMPLATEID_2 = "journalTemplate.templateId = ?";
	private static final String _FINDER_COLUMN_G_T_TEMPLATEID_3 = "(journalTemplate.templateId IS NULL OR journalTemplate.templateId = ?)";
	private static final String _FINDER_COLUMN_G_S_GROUPID_2 = "journalTemplate.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREID_1 = "journalTemplate.structureId IS NULL";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREID_2 = "journalTemplate.structureId = ?";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREID_3 = "(journalTemplate.structureId IS NULL OR journalTemplate.structureId = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "journalTemplate.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No JournalTemplate exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No JournalTemplate exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(JournalTemplatePersistenceImpl.class);
}