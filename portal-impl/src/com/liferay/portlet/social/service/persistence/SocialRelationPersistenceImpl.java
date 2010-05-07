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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
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
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.social.NoSuchRelationException;
import com.liferay.portlet.social.model.SocialRelation;
import com.liferay.portlet.social.model.impl.SocialRelationImpl;
import com.liferay.portlet.social.model.impl.SocialRelationModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="SocialRelationPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialRelationPersistence
 * @see       SocialRelationUtil
 * @generated
 */
public class SocialRelationPersistenceImpl extends BasePersistenceImpl<SocialRelation>
	implements SocialRelationPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = SocialRelationImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_USERID1 = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId1",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_USERID1 = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId1",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID1 = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUserId1",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_USERID2 = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId2",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_USERID2 = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId2",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID2 = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUserId2",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_TYPE = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByType",
			new String[] { Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_TYPE = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByType",
			new String[] {
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_TYPE = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByType",
			new String[] { Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_T",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_T",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_U1_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByU1_T",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_U1_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByU1_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_U1_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByU1_T",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_U2_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByU2_T",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_U2_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByU2_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_U2_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByU2_T",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_U1_U2_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByU1_U2_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_U1_U2_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByU1_U2_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(SocialRelation socialRelation) {
		EntityCacheUtil.putResult(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationImpl.class, socialRelation.getPrimaryKey(),
			socialRelation);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U1_U2_T,
			new Object[] {
				new Long(socialRelation.getUserId1()),
				new Long(socialRelation.getUserId2()),
				new Integer(socialRelation.getType())
			}, socialRelation);
	}

	public void cacheResult(List<SocialRelation> socialRelations) {
		for (SocialRelation socialRelation : socialRelations) {
			if (EntityCacheUtil.getResult(
						SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
						SocialRelationImpl.class,
						socialRelation.getPrimaryKey(), this) == null) {
				cacheResult(socialRelation);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(SocialRelationImpl.class.getName());
		EntityCacheUtil.clearCache(SocialRelationImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(SocialRelation socialRelation) {
		EntityCacheUtil.removeResult(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationImpl.class, socialRelation.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U1_U2_T,
			new Object[] {
				new Long(socialRelation.getUserId1()),
				new Long(socialRelation.getUserId2()),
				new Integer(socialRelation.getType())
			});
	}

	public SocialRelation create(long relationId) {
		SocialRelation socialRelation = new SocialRelationImpl();

		socialRelation.setNew(true);
		socialRelation.setPrimaryKey(relationId);

		String uuid = PortalUUIDUtil.generate();

		socialRelation.setUuid(uuid);

		return socialRelation;
	}

	public SocialRelation remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public SocialRelation remove(long relationId)
		throws NoSuchRelationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SocialRelation socialRelation = (SocialRelation)session.get(SocialRelationImpl.class,
					new Long(relationId));

			if (socialRelation == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + relationId);
				}

				throw new NoSuchRelationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					relationId);
			}

			return remove(socialRelation);
		}
		catch (NoSuchRelationException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SocialRelation remove(SocialRelation socialRelation)
		throws SystemException {
		for (ModelListener<SocialRelation> listener : listeners) {
			listener.onBeforeRemove(socialRelation);
		}

		socialRelation = removeImpl(socialRelation);

		for (ModelListener<SocialRelation> listener : listeners) {
			listener.onAfterRemove(socialRelation);
		}

		return socialRelation;
	}

	protected SocialRelation removeImpl(SocialRelation socialRelation)
		throws SystemException {
		socialRelation = toUnwrappedModel(socialRelation);

		Session session = null;

		try {
			session = openSession();

			if (socialRelation.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(SocialRelationImpl.class,
						socialRelation.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(socialRelation);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		SocialRelationModelImpl socialRelationModelImpl = (SocialRelationModelImpl)socialRelation;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U1_U2_T,
			new Object[] {
				new Long(socialRelationModelImpl.getOriginalUserId1()),
				new Long(socialRelationModelImpl.getOriginalUserId2()),
				new Integer(socialRelationModelImpl.getOriginalType())
			});

		EntityCacheUtil.removeResult(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationImpl.class, socialRelation.getPrimaryKey());

		return socialRelation;
	}

	public SocialRelation updateImpl(
		com.liferay.portlet.social.model.SocialRelation socialRelation,
		boolean merge) throws SystemException {
		socialRelation = toUnwrappedModel(socialRelation);

		boolean isNew = socialRelation.isNew();

		SocialRelationModelImpl socialRelationModelImpl = (SocialRelationModelImpl)socialRelation;

		if (Validator.isNull(socialRelation.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			socialRelation.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, socialRelation, merge);

			socialRelation.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationImpl.class, socialRelation.getPrimaryKey(),
			socialRelation);

		if (!isNew &&
				((socialRelation.getUserId1() != socialRelationModelImpl.getOriginalUserId1()) ||
				(socialRelation.getUserId2() != socialRelationModelImpl.getOriginalUserId2()) ||
				(socialRelation.getType() != socialRelationModelImpl.getOriginalType()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U1_U2_T,
				new Object[] {
					new Long(socialRelationModelImpl.getOriginalUserId1()),
					new Long(socialRelationModelImpl.getOriginalUserId2()),
					new Integer(socialRelationModelImpl.getOriginalType())
				});
		}

		if (isNew ||
				((socialRelation.getUserId1() != socialRelationModelImpl.getOriginalUserId1()) ||
				(socialRelation.getUserId2() != socialRelationModelImpl.getOriginalUserId2()) ||
				(socialRelation.getType() != socialRelationModelImpl.getOriginalType()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U1_U2_T,
				new Object[] {
					new Long(socialRelation.getUserId1()),
					new Long(socialRelation.getUserId2()),
					new Integer(socialRelation.getType())
				}, socialRelation);
		}

		return socialRelation;
	}

	protected SocialRelation toUnwrappedModel(SocialRelation socialRelation) {
		if (socialRelation instanceof SocialRelationImpl) {
			return socialRelation;
		}

		SocialRelationImpl socialRelationImpl = new SocialRelationImpl();

		socialRelationImpl.setNew(socialRelation.isNew());
		socialRelationImpl.setPrimaryKey(socialRelation.getPrimaryKey());

		socialRelationImpl.setUuid(socialRelation.getUuid());
		socialRelationImpl.setRelationId(socialRelation.getRelationId());
		socialRelationImpl.setCompanyId(socialRelation.getCompanyId());
		socialRelationImpl.setCreateDate(socialRelation.getCreateDate());
		socialRelationImpl.setUserId1(socialRelation.getUserId1());
		socialRelationImpl.setUserId2(socialRelation.getUserId2());
		socialRelationImpl.setType(socialRelation.getType());

		return socialRelationImpl;
	}

	public SocialRelation findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public SocialRelation findByPrimaryKey(long relationId)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = fetchByPrimaryKey(relationId);

		if (socialRelation == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + relationId);
			}

			throw new NoSuchRelationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				relationId);
		}

		return socialRelation;
	}

	public SocialRelation fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public SocialRelation fetchByPrimaryKey(long relationId)
		throws SystemException {
		SocialRelation socialRelation = (SocialRelation)EntityCacheUtil.getResult(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
				SocialRelationImpl.class, relationId, this);

		if (socialRelation == null) {
			Session session = null;

			try {
				session = openSession();

				socialRelation = (SocialRelation)session.get(SocialRelationImpl.class,
						new Long(relationId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (socialRelation != null) {
					cacheResult(socialRelation);
				}

				closeSession(session);
			}
		}

		return socialRelation;
	}

	public List<SocialRelation> findByUuid(String uuid)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

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

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialRelation> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<SocialRelation> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(3 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(2);
				}

				query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

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

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<SocialRelation>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialRelation findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		List<SocialRelation> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		int count = countByUuid(uuid);

		List<SocialRelation> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation[] findByUuid_PrevAndNext(long relationId,
		String uuid, OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		Session session = null;

		try {
			session = openSession();

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = getByUuid_PrevAndNext(session, socialRelation, uuid,
					orderByComparator, true);

			array[1] = socialRelation;

			array[2] = getByUuid_PrevAndNext(session, socialRelation, uuid,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialRelation getByUuid_PrevAndNext(Session session,
		SocialRelation socialRelation, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

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

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}

			query.append(WHERE_LIMIT_2);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(socialRelation);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialRelation> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<SocialRelation> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

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
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialRelation> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<SocialRelation> findByCompanyId(long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(3 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(2);
				}

				query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<SocialRelation>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialRelation findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		List<SocialRelation> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		int count = countByCompanyId(companyId);

		List<SocialRelation> list = findByCompanyId(companyId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation[] findByCompanyId_PrevAndNext(long relationId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		Session session = null;

		try {
			session = openSession();

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, socialRelation,
					companyId, orderByComparator, true);

			array[1] = socialRelation;

			array[2] = getByCompanyId_PrevAndNext(session, socialRelation,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialRelation getByCompanyId_PrevAndNext(Session session,
		SocialRelation socialRelation, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}

			query.append(WHERE_LIMIT_2);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(socialRelation);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialRelation> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<SocialRelation> findByUserId1(long userId1)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId1) };

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID1,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

				query.append(_FINDER_COLUMN_USERID1_USERID1_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId1);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID1,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialRelation> findByUserId1(long userId1, int start, int end)
		throws SystemException {
		return findByUserId1(userId1, start, end, null);
	}

	public List<SocialRelation> findByUserId1(long userId1, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId1),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_USERID1,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(3 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(2);
				}

				query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

				query.append(_FINDER_COLUMN_USERID1_USERID1_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId1);

				list = (List<SocialRelation>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_USERID1,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialRelation findByUserId1_First(long userId1,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		List<SocialRelation> list = findByUserId1(userId1, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId1=");
			msg.append(userId1);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation findByUserId1_Last(long userId1,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		int count = countByUserId1(userId1);

		List<SocialRelation> list = findByUserId1(userId1, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId1=");
			msg.append(userId1);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation[] findByUserId1_PrevAndNext(long relationId,
		long userId1, OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		Session session = null;

		try {
			session = openSession();

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = getByUserId1_PrevAndNext(session, socialRelation,
					userId1, orderByComparator, true);

			array[1] = socialRelation;

			array[2] = getByUserId1_PrevAndNext(session, socialRelation,
					userId1, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialRelation getByUserId1_PrevAndNext(Session session,
		SocialRelation socialRelation, long userId1,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

		query.append(_FINDER_COLUMN_USERID1_USERID1_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}

			query.append(WHERE_LIMIT_2);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId1);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(socialRelation);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialRelation> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<SocialRelation> findByUserId2(long userId2)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId2) };

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID2,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

				query.append(_FINDER_COLUMN_USERID2_USERID2_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId2);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID2,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialRelation> findByUserId2(long userId2, int start, int end)
		throws SystemException {
		return findByUserId2(userId2, start, end, null);
	}

	public List<SocialRelation> findByUserId2(long userId2, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId2),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_USERID2,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(3 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(2);
				}

				query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

				query.append(_FINDER_COLUMN_USERID2_USERID2_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId2);

				list = (List<SocialRelation>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_USERID2,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialRelation findByUserId2_First(long userId2,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		List<SocialRelation> list = findByUserId2(userId2, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId2=");
			msg.append(userId2);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation findByUserId2_Last(long userId2,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		int count = countByUserId2(userId2);

		List<SocialRelation> list = findByUserId2(userId2, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId2=");
			msg.append(userId2);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation[] findByUserId2_PrevAndNext(long relationId,
		long userId2, OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		Session session = null;

		try {
			session = openSession();

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = getByUserId2_PrevAndNext(session, socialRelation,
					userId2, orderByComparator, true);

			array[1] = socialRelation;

			array[2] = getByUserId2_PrevAndNext(session, socialRelation,
					userId2, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialRelation getByUserId2_PrevAndNext(Session session,
		SocialRelation socialRelation, long userId2,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

		query.append(_FINDER_COLUMN_USERID2_USERID2_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}

			query.append(WHERE_LIMIT_2);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId2);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(socialRelation);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialRelation> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<SocialRelation> findByType(int type) throws SystemException {
		Object[] finderArgs = new Object[] { new Integer(type) };

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_TYPE,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

				query.append(_FINDER_COLUMN_TYPE_TYPE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(type);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_TYPE, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialRelation> findByType(int type, int start, int end)
		throws SystemException {
		return findByType(type, start, end, null);
	}

	public List<SocialRelation> findByType(int type, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Integer(type),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_TYPE,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(3 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(2);
				}

				query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

				query.append(_FINDER_COLUMN_TYPE_TYPE_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(type);

				list = (List<SocialRelation>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_TYPE,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialRelation findByType_First(int type,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		List<SocialRelation> list = findByType(type, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation findByType_Last(int type,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		int count = countByType(type);

		List<SocialRelation> list = findByType(type, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation[] findByType_PrevAndNext(long relationId, int type,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		Session session = null;

		try {
			session = openSession();

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = getByType_PrevAndNext(session, socialRelation, type,
					orderByComparator, true);

			array[1] = socialRelation;

			array[2] = getByType_PrevAndNext(session, socialRelation, type,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialRelation getByType_PrevAndNext(Session session,
		SocialRelation socialRelation, int type,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

		query.append(_FINDER_COLUMN_TYPE_TYPE_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}

			query.append(WHERE_LIMIT_2);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(socialRelation);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialRelation> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<SocialRelation> findByC_T(long companyId, int type)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Integer(type)
			};

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

				query.append(_FINDER_COLUMN_C_T_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_T_TYPE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(type);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_T, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialRelation> findByC_T(long companyId, int type, int start,
		int end) throws SystemException {
		return findByC_T(companyId, type, start, end, null);
	}

	public List<SocialRelation> findByC_T(long companyId, int type, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Integer(type),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(4 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

				query.append(_FINDER_COLUMN_C_T_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_T_TYPE_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(type);

				list = (List<SocialRelation>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_T,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialRelation findByC_T_First(long companyId, int type,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		List<SocialRelation> list = findByC_T(companyId, type, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation findByC_T_Last(long companyId, int type,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		int count = countByC_T(companyId, type);

		List<SocialRelation> list = findByC_T(companyId, type, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation[] findByC_T_PrevAndNext(long relationId,
		long companyId, int type, OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		Session session = null;

		try {
			session = openSession();

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = getByC_T_PrevAndNext(session, socialRelation, companyId,
					type, orderByComparator, true);

			array[1] = socialRelation;

			array[2] = getByC_T_PrevAndNext(session, socialRelation, companyId,
					type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialRelation getByC_T_PrevAndNext(Session session,
		SocialRelation socialRelation, long companyId, int type,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

		query.append(_FINDER_COLUMN_C_T_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_T_TYPE_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}

			query.append(WHERE_LIMIT_2);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(socialRelation);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialRelation> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<SocialRelation> findByU1_T(long userId1, int type)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId1), new Integer(type) };

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_U1_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

				query.append(_FINDER_COLUMN_U1_T_USERID1_2);

				query.append(_FINDER_COLUMN_U1_T_TYPE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId1);

				qPos.add(type);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_U1_T, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialRelation> findByU1_T(long userId1, int type, int start,
		int end) throws SystemException {
		return findByU1_T(userId1, type, start, end, null);
	}

	public List<SocialRelation> findByU1_T(long userId1, int type, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId1), new Integer(type),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_U1_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(4 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

				query.append(_FINDER_COLUMN_U1_T_USERID1_2);

				query.append(_FINDER_COLUMN_U1_T_TYPE_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId1);

				qPos.add(type);

				list = (List<SocialRelation>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_U1_T,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialRelation findByU1_T_First(long userId1, int type,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		List<SocialRelation> list = findByU1_T(userId1, type, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId1=");
			msg.append(userId1);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation findByU1_T_Last(long userId1, int type,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		int count = countByU1_T(userId1, type);

		List<SocialRelation> list = findByU1_T(userId1, type, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId1=");
			msg.append(userId1);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation[] findByU1_T_PrevAndNext(long relationId,
		long userId1, int type, OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		Session session = null;

		try {
			session = openSession();

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = getByU1_T_PrevAndNext(session, socialRelation, userId1,
					type, orderByComparator, true);

			array[1] = socialRelation;

			array[2] = getByU1_T_PrevAndNext(session, socialRelation, userId1,
					type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialRelation getByU1_T_PrevAndNext(Session session,
		SocialRelation socialRelation, long userId1, int type,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

		query.append(_FINDER_COLUMN_U1_T_USERID1_2);

		query.append(_FINDER_COLUMN_U1_T_TYPE_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}

			query.append(WHERE_LIMIT_2);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId1);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(socialRelation);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialRelation> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<SocialRelation> findByU2_T(long userId2, int type)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId2), new Integer(type) };

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_U2_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

				query.append(_FINDER_COLUMN_U2_T_USERID2_2);

				query.append(_FINDER_COLUMN_U2_T_TYPE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId2);

				qPos.add(type);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_U2_T, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialRelation> findByU2_T(long userId2, int type, int start,
		int end) throws SystemException {
		return findByU2_T(userId2, type, start, end, null);
	}

	public List<SocialRelation> findByU2_T(long userId2, int type, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId2), new Integer(type),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_U2_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(4 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

				query.append(_FINDER_COLUMN_U2_T_USERID2_2);

				query.append(_FINDER_COLUMN_U2_T_TYPE_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId2);

				qPos.add(type);

				list = (List<SocialRelation>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_U2_T,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialRelation findByU2_T_First(long userId2, int type,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		List<SocialRelation> list = findByU2_T(userId2, type, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId2=");
			msg.append(userId2);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation findByU2_T_Last(long userId2, int type,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		int count = countByU2_T(userId2, type);

		List<SocialRelation> list = findByU2_T(userId2, type, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId2=");
			msg.append(userId2);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation[] findByU2_T_PrevAndNext(long relationId,
		long userId2, int type, OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		Session session = null;

		try {
			session = openSession();

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = getByU2_T_PrevAndNext(session, socialRelation, userId2,
					type, orderByComparator, true);

			array[1] = socialRelation;

			array[2] = getByU2_T_PrevAndNext(session, socialRelation, userId2,
					type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialRelation getByU2_T_PrevAndNext(Session session,
		SocialRelation socialRelation, long userId2, int type,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

		query.append(_FINDER_COLUMN_U2_T_USERID2_2);

		query.append(_FINDER_COLUMN_U2_T_TYPE_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}

			query.append(WHERE_LIMIT_2);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId2);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(socialRelation);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialRelation> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public SocialRelation findByU1_U2_T(long userId1, long userId2, int type)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = fetchByU1_U2_T(userId1, userId2, type);

		if (socialRelation == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId1=");
			msg.append(userId1);

			msg.append(", userId2=");
			msg.append(userId2);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchRelationException(msg.toString());
		}

		return socialRelation;
	}

	public SocialRelation fetchByU1_U2_T(long userId1, long userId2, int type)
		throws SystemException {
		return fetchByU1_U2_T(userId1, userId2, type, true);
	}

	public SocialRelation fetchByU1_U2_T(long userId1, long userId2, int type,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId1), new Long(userId2), new Integer(type)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_U1_U2_T,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

				query.append(_FINDER_COLUMN_U1_U2_T_USERID1_2);

				query.append(_FINDER_COLUMN_U1_U2_T_USERID2_2);

				query.append(_FINDER_COLUMN_U1_U2_T_TYPE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId1);

				qPos.add(userId2);

				qPos.add(type);

				List<SocialRelation> list = q.list();

				result = list;

				SocialRelation socialRelation = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U1_U2_T,
						finderArgs, list);
				}
				else {
					socialRelation = list.get(0);

					cacheResult(socialRelation);

					if ((socialRelation.getUserId1() != userId1) ||
							(socialRelation.getUserId2() != userId2) ||
							(socialRelation.getType() != type)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U1_U2_T,
							finderArgs, socialRelation);
					}
				}

				return socialRelation;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U1_U2_T,
						finderArgs, new ArrayList<SocialRelation>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (SocialRelation)result;
			}
		}
	}

	public List<SocialRelation> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<SocialRelation> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<SocialRelation> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;
				String sql = null;

				if (orderByComparator != null) {
					query = new StringBundler(2 +
							(orderByComparator.getOrderByFields().length * 3));

					query.append(_SQL_SELECT_SOCIALRELATION);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				sql = _SQL_SELECT_SOCIALRELATION;

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<SocialRelation>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SocialRelation>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (SocialRelation socialRelation : findByUuid(uuid)) {
			remove(socialRelation);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (SocialRelation socialRelation : findByCompanyId(companyId)) {
			remove(socialRelation);
		}
	}

	public void removeByUserId1(long userId1) throws SystemException {
		for (SocialRelation socialRelation : findByUserId1(userId1)) {
			remove(socialRelation);
		}
	}

	public void removeByUserId2(long userId2) throws SystemException {
		for (SocialRelation socialRelation : findByUserId2(userId2)) {
			remove(socialRelation);
		}
	}

	public void removeByType(int type) throws SystemException {
		for (SocialRelation socialRelation : findByType(type)) {
			remove(socialRelation);
		}
	}

	public void removeByC_T(long companyId, int type) throws SystemException {
		for (SocialRelation socialRelation : findByC_T(companyId, type)) {
			remove(socialRelation);
		}
	}

	public void removeByU1_T(long userId1, int type) throws SystemException {
		for (SocialRelation socialRelation : findByU1_T(userId1, type)) {
			remove(socialRelation);
		}
	}

	public void removeByU2_T(long userId2, int type) throws SystemException {
		for (SocialRelation socialRelation : findByU2_T(userId2, type)) {
			remove(socialRelation);
		}
	}

	public void removeByU1_U2_T(long userId1, long userId2, int type)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByU1_U2_T(userId1, userId2, type);

		remove(socialRelation);
	}

	public void removeAll() throws SystemException {
		for (SocialRelation socialRelation : findAll()) {
			remove(socialRelation);
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

				query.append(_SQL_COUNT_SOCIALRELATION_WHERE);

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

	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_SOCIALRELATION_WHERE);

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

	public int countByUserId1(long userId1) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId1) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID1,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_SOCIALRELATION_WHERE);

				query.append(_FINDER_COLUMN_USERID1_USERID1_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId1);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID1,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByUserId2(long userId2) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId2) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID2,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_SOCIALRELATION_WHERE);

				query.append(_FINDER_COLUMN_USERID2_USERID2_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId2);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID2,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByType(int type) throws SystemException {
		Object[] finderArgs = new Object[] { new Integer(type) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_TYPE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_SOCIALRELATION_WHERE);

				query.append(_FINDER_COLUMN_TYPE_TYPE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(type);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_TYPE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_T(long companyId, int type) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Integer(type)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_SOCIALRELATION_WHERE);

				query.append(_FINDER_COLUMN_C_T_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_T_TYPE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(type);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_T, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByU1_T(long userId1, int type) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId1), new Integer(type) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U1_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_SOCIALRELATION_WHERE);

				query.append(_FINDER_COLUMN_U1_T_USERID1_2);

				query.append(_FINDER_COLUMN_U1_T_TYPE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId1);

				qPos.add(type);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U1_T,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByU2_T(long userId2, int type) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId2), new Integer(type) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U2_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_SOCIALRELATION_WHERE);

				query.append(_FINDER_COLUMN_U2_T_USERID2_2);

				query.append(_FINDER_COLUMN_U2_T_TYPE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId2);

				qPos.add(type);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U2_T,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByU1_U2_T(long userId1, long userId2, int type)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId1), new Long(userId2), new Integer(type)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U1_U2_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_SOCIALRELATION_WHERE);

				query.append(_FINDER_COLUMN_U1_U2_T_USERID1_2);

				query.append(_FINDER_COLUMN_U1_U2_T_USERID2_2);

				query.append(_FINDER_COLUMN_U1_U2_T_TYPE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId1);

				qPos.add(userId2);

				qPos.add(type);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U1_U2_T,
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

				Query q = session.createQuery(_SQL_COUNT_SOCIALRELATION);

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
						"value.object.listener.com.liferay.portlet.social.model.SocialRelation")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SocialRelation>> listenersList = new ArrayList<ModelListener<SocialRelation>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SocialRelation>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(type = SocialActivityPersistence.class)
	protected SocialActivityPersistence socialActivityPersistence;
	@BeanReference(type = SocialEquityAssetEntryPersistence.class)
	protected SocialEquityAssetEntryPersistence socialEquityAssetEntryPersistence;
	@BeanReference(type = SocialEquityHistoryPersistence.class)
	protected SocialEquityHistoryPersistence socialEquityHistoryPersistence;
	@BeanReference(type = SocialEquityLogPersistence.class)
	protected SocialEquityLogPersistence socialEquityLogPersistence;
	@BeanReference(type = SocialEquitySettingPersistence.class)
	protected SocialEquitySettingPersistence socialEquitySettingPersistence;
	@BeanReference(type = SocialEquityUserPersistence.class)
	protected SocialEquityUserPersistence socialEquityUserPersistence;
	@BeanReference(type = SocialRelationPersistence.class)
	protected SocialRelationPersistence socialRelationPersistence;
	@BeanReference(type = SocialRequestPersistence.class)
	protected SocialRequestPersistence socialRequestPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_SOCIALRELATION = "SELECT socialRelation FROM SocialRelation socialRelation";
	private static final String _SQL_SELECT_SOCIALRELATION_WHERE = "SELECT socialRelation FROM SocialRelation socialRelation WHERE ";
	private static final String _SQL_COUNT_SOCIALRELATION = "SELECT COUNT(socialRelation) FROM SocialRelation socialRelation";
	private static final String _SQL_COUNT_SOCIALRELATION_WHERE = "SELECT COUNT(socialRelation) FROM SocialRelation socialRelation WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "socialRelation.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "socialRelation.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(socialRelation.uuid IS NULL OR socialRelation.uuid = ?)";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "socialRelation.companyId = ?";
	private static final String _FINDER_COLUMN_USERID1_USERID1_2 = "socialRelation.userId1 = ?";
	private static final String _FINDER_COLUMN_USERID2_USERID2_2 = "socialRelation.userId2 = ?";
	private static final String _FINDER_COLUMN_TYPE_TYPE_2 = "socialRelation.type = ?";
	private static final String _FINDER_COLUMN_C_T_COMPANYID_2 = "socialRelation.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_T_TYPE_2 = "socialRelation.type = ?";
	private static final String _FINDER_COLUMN_U1_T_USERID1_2 = "socialRelation.userId1 = ? AND ";
	private static final String _FINDER_COLUMN_U1_T_TYPE_2 = "socialRelation.type = ?";
	private static final String _FINDER_COLUMN_U2_T_USERID2_2 = "socialRelation.userId2 = ? AND ";
	private static final String _FINDER_COLUMN_U2_T_TYPE_2 = "socialRelation.type = ?";
	private static final String _FINDER_COLUMN_U1_U2_T_USERID1_2 = "socialRelation.userId1 = ? AND ";
	private static final String _FINDER_COLUMN_U1_U2_T_USERID2_2 = "socialRelation.userId2 = ? AND ";
	private static final String _FINDER_COLUMN_U1_U2_T_TYPE_2 = "socialRelation.type = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "socialRelation.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SocialRelation exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SocialRelation exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(SocialRelationPersistenceImpl.class);
}