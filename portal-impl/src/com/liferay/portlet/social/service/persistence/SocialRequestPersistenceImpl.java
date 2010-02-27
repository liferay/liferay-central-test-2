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

import com.liferay.portlet.social.NoSuchRequestException;
import com.liferay.portlet.social.model.SocialRequest;
import com.liferay.portlet.social.model.impl.SocialRequestImpl;
import com.liferay.portlet.social.model.impl.SocialRequestModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="SocialRequestPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialRequestPersistence
 * @see       SocialRequestUtil
 * @generated
 */
public class SocialRequestPersistenceImpl extends BasePersistenceImpl<SocialRequest>
	implements SocialRequestPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = SocialRequestImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_USERID = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_USERID = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_RECEIVERUSERID = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByReceiverUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_RECEIVERUSERID = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByReceiverUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_RECEIVERUSERID = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByReceiverUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_U_S = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByU_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_U_S = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByU_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_U_S = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByU_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_R_S = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByR_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_R_S = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByR_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_R_S = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByR_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_U_C_C_T_R = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByU_C_C_T_R",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_U_C_C_T_R = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByU_C_C_T_R",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_U_C_C_T_S = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByU_C_C_T_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_U_C_C_T_S = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByU_C_C_T_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_U_C_C_T_S = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByU_C_C_T_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_C_C_T_R_S = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_C_T_R_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_C_T_R_S = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_C_T_R_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_T_R_S = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_C_T_R_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(SocialRequest socialRequest) {
		EntityCacheUtil.putResult(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestImpl.class, socialRequest.getPrimaryKey(),
			socialRequest);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				socialRequest.getUuid(), new Long(socialRequest.getGroupId())
			}, socialRequest);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_C_C_T_R,
			new Object[] {
				new Long(socialRequest.getUserId()),
				new Long(socialRequest.getClassNameId()),
				new Long(socialRequest.getClassPK()),
				new Integer(socialRequest.getType()),
				new Long(socialRequest.getReceiverUserId())
			}, socialRequest);
	}

	public void cacheResult(List<SocialRequest> socialRequests) {
		for (SocialRequest socialRequest : socialRequests) {
			if (EntityCacheUtil.getResult(
						SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
						SocialRequestImpl.class, socialRequest.getPrimaryKey(),
						this) == null) {
				cacheResult(socialRequest);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(SocialRequestImpl.class.getName());
		EntityCacheUtil.clearCache(SocialRequestImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public SocialRequest create(long requestId) {
		SocialRequest socialRequest = new SocialRequestImpl();

		socialRequest.setNew(true);
		socialRequest.setPrimaryKey(requestId);

		String uuid = PortalUUIDUtil.generate();

		socialRequest.setUuid(uuid);

		return socialRequest;
	}

	public SocialRequest remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public SocialRequest remove(long requestId)
		throws NoSuchRequestException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SocialRequest socialRequest = (SocialRequest)session.get(SocialRequestImpl.class,
					new Long(requestId));

			if (socialRequest == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + requestId);
				}

				throw new NoSuchRequestException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					requestId);
			}

			return remove(socialRequest);
		}
		catch (NoSuchRequestException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SocialRequest remove(SocialRequest socialRequest)
		throws SystemException {
		for (ModelListener<SocialRequest> listener : listeners) {
			listener.onBeforeRemove(socialRequest);
		}

		socialRequest = removeImpl(socialRequest);

		for (ModelListener<SocialRequest> listener : listeners) {
			listener.onAfterRemove(socialRequest);
		}

		return socialRequest;
	}

	protected SocialRequest removeImpl(SocialRequest socialRequest)
		throws SystemException {
		socialRequest = toUnwrappedModel(socialRequest);

		Session session = null;

		try {
			session = openSession();

			if (socialRequest.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(SocialRequestImpl.class,
						socialRequest.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(socialRequest);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		SocialRequestModelImpl socialRequestModelImpl = (SocialRequestModelImpl)socialRequest;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				socialRequestModelImpl.getOriginalUuid(),
				new Long(socialRequestModelImpl.getOriginalGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_C_C_T_R,
			new Object[] {
				new Long(socialRequestModelImpl.getOriginalUserId()),
				new Long(socialRequestModelImpl.getOriginalClassNameId()),
				new Long(socialRequestModelImpl.getOriginalClassPK()),
				new Integer(socialRequestModelImpl.getOriginalType()),
				new Long(socialRequestModelImpl.getOriginalReceiverUserId())
			});

		EntityCacheUtil.removeResult(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestImpl.class, socialRequest.getPrimaryKey());

		return socialRequest;
	}

	public SocialRequest updateImpl(
		com.liferay.portlet.social.model.SocialRequest socialRequest,
		boolean merge) throws SystemException {
		socialRequest = toUnwrappedModel(socialRequest);

		boolean isNew = socialRequest.isNew();

		SocialRequestModelImpl socialRequestModelImpl = (SocialRequestModelImpl)socialRequest;

		if (Validator.isNull(socialRequest.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			socialRequest.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, socialRequest, merge);

			socialRequest.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestImpl.class, socialRequest.getPrimaryKey(),
			socialRequest);

		if (!isNew &&
				(!Validator.equals(socialRequest.getUuid(),
					socialRequestModelImpl.getOriginalUuid()) ||
				(socialRequest.getGroupId() != socialRequestModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					socialRequestModelImpl.getOriginalUuid(),
					new Long(socialRequestModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(socialRequest.getUuid(),
					socialRequestModelImpl.getOriginalUuid()) ||
				(socialRequest.getGroupId() != socialRequestModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					socialRequest.getUuid(),
					new Long(socialRequest.getGroupId())
				}, socialRequest);
		}

		if (!isNew &&
				((socialRequest.getUserId() != socialRequestModelImpl.getOriginalUserId()) ||
				(socialRequest.getClassNameId() != socialRequestModelImpl.getOriginalClassNameId()) ||
				(socialRequest.getClassPK() != socialRequestModelImpl.getOriginalClassPK()) ||
				(socialRequest.getType() != socialRequestModelImpl.getOriginalType()) ||
				(socialRequest.getReceiverUserId() != socialRequestModelImpl.getOriginalReceiverUserId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_C_C_T_R,
				new Object[] {
					new Long(socialRequestModelImpl.getOriginalUserId()),
					new Long(socialRequestModelImpl.getOriginalClassNameId()),
					new Long(socialRequestModelImpl.getOriginalClassPK()),
					new Integer(socialRequestModelImpl.getOriginalType()),
					new Long(socialRequestModelImpl.getOriginalReceiverUserId())
				});
		}

		if (isNew ||
				((socialRequest.getUserId() != socialRequestModelImpl.getOriginalUserId()) ||
				(socialRequest.getClassNameId() != socialRequestModelImpl.getOriginalClassNameId()) ||
				(socialRequest.getClassPK() != socialRequestModelImpl.getOriginalClassPK()) ||
				(socialRequest.getType() != socialRequestModelImpl.getOriginalType()) ||
				(socialRequest.getReceiverUserId() != socialRequestModelImpl.getOriginalReceiverUserId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_C_C_T_R,
				new Object[] {
					new Long(socialRequest.getUserId()),
					new Long(socialRequest.getClassNameId()),
					new Long(socialRequest.getClassPK()),
					new Integer(socialRequest.getType()),
					new Long(socialRequest.getReceiverUserId())
				}, socialRequest);
		}

		return socialRequest;
	}

	protected SocialRequest toUnwrappedModel(SocialRequest socialRequest) {
		if (socialRequest instanceof SocialRequestImpl) {
			return socialRequest;
		}

		SocialRequestImpl socialRequestImpl = new SocialRequestImpl();

		socialRequestImpl.setNew(socialRequest.isNew());
		socialRequestImpl.setPrimaryKey(socialRequest.getPrimaryKey());

		socialRequestImpl.setUuid(socialRequest.getUuid());
		socialRequestImpl.setRequestId(socialRequest.getRequestId());
		socialRequestImpl.setGroupId(socialRequest.getGroupId());
		socialRequestImpl.setCompanyId(socialRequest.getCompanyId());
		socialRequestImpl.setUserId(socialRequest.getUserId());
		socialRequestImpl.setCreateDate(socialRequest.getCreateDate());
		socialRequestImpl.setModifiedDate(socialRequest.getModifiedDate());
		socialRequestImpl.setClassNameId(socialRequest.getClassNameId());
		socialRequestImpl.setClassPK(socialRequest.getClassPK());
		socialRequestImpl.setType(socialRequest.getType());
		socialRequestImpl.setExtraData(socialRequest.getExtraData());
		socialRequestImpl.setReceiverUserId(socialRequest.getReceiverUserId());
		socialRequestImpl.setStatus(socialRequest.getStatus());

		return socialRequestImpl;
	}

	public SocialRequest findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public SocialRequest findByPrimaryKey(long requestId)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = fetchByPrimaryKey(requestId);

		if (socialRequest == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + requestId);
			}

			throw new NoSuchRequestException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				requestId);
		}

		return socialRequest;
	}

	public SocialRequest fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public SocialRequest fetchByPrimaryKey(long requestId)
		throws SystemException {
		SocialRequest socialRequest = (SocialRequest)EntityCacheUtil.getResult(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
				SocialRequestImpl.class, requestId, this);

		if (socialRequest == null) {
			Session session = null;

			try {
				session = openSession();

				socialRequest = (SocialRequest)session.get(SocialRequestImpl.class,
						new Long(requestId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (socialRequest != null) {
					cacheResult(socialRequest);
				}

				closeSession(session);
			}
		}

		return socialRequest;
	}

	public List<SocialRequest> findByUuid(String uuid)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		List<SocialRequest> list = (List<SocialRequest>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

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

				query.append(SocialRequestModelImpl.ORDER_BY_JPQL);

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
					list = new ArrayList<SocialRequest>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialRequest> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<SocialRequest> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SocialRequest> list = (List<SocialRequest>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
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

				query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

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
					query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<SocialRequest>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRequest>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialRequest findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchRequestException, SystemException {
		List<SocialRequest> list = findByUuid(uuid, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRequest findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchRequestException, SystemException {
		int count = countByUuid(uuid);

		List<SocialRequest> list = findByUuid(uuid, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRequest[] findByUuid_PrevAndNext(long requestId, String uuid,
		OrderByComparator obc) throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByPrimaryKey(requestId);

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

			query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

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
				query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRequest);

			SocialRequest[] array = new SocialRequestImpl[3];

			array[0] = (SocialRequest)objArray[0];
			array[1] = (SocialRequest)objArray[1];
			array[2] = (SocialRequest)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SocialRequest findByUUID_G(String uuid, long groupId)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = fetchByUUID_G(uuid, groupId);

		if (socialRequest == null) {
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

			throw new NoSuchRequestException(msg.toString());
		}

		return socialRequest;
	}

	public SocialRequest fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	public SocialRequest fetchByUUID_G(String uuid, long groupId,
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

				query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

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

				query.append(SocialRequestModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<SocialRequest> list = q.list();

				result = list;

				SocialRequest socialRequest = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					socialRequest = list.get(0);

					cacheResult(socialRequest);

					if ((socialRequest.getUuid() == null) ||
							!socialRequest.getUuid().equals(uuid) ||
							(socialRequest.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, socialRequest);
					}
				}

				return socialRequest;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, new ArrayList<SocialRequest>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (SocialRequest)result;
			}
		}
	}

	public List<SocialRequest> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<SocialRequest> list = (List<SocialRequest>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				query.append(SocialRequestModelImpl.ORDER_BY_JPQL);

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
					list = new ArrayList<SocialRequest>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialRequest> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<SocialRequest> findByCompanyId(long companyId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SocialRequest> list = (List<SocialRequest>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
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

				query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<SocialRequest>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRequest>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialRequest findByCompanyId_First(long companyId,
		OrderByComparator obc) throws NoSuchRequestException, SystemException {
		List<SocialRequest> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRequest findByCompanyId_Last(long companyId,
		OrderByComparator obc) throws NoSuchRequestException, SystemException {
		int count = countByCompanyId(companyId);

		List<SocialRequest> list = findByCompanyId(companyId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRequest[] findByCompanyId_PrevAndNext(long requestId,
		long companyId, OrderByComparator obc)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByPrimaryKey(requestId);

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
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRequest);

			SocialRequest[] array = new SocialRequestImpl[3];

			array[0] = (SocialRequest)objArray[0];
			array[1] = (SocialRequest)objArray[1];
			array[2] = (SocialRequest)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialRequest> findByUserId(long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		List<SocialRequest> list = (List<SocialRequest>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				query.append(SocialRequestModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRequest>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialRequest> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<SocialRequest> findByUserId(long userId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SocialRequest> list = (List<SocialRequest>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_USERID,
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

				query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<SocialRequest>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRequest>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialRequest findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchRequestException, SystemException {
		List<SocialRequest> list = findByUserId(userId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRequest findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchRequestException, SystemException {
		int count = countByUserId(userId);

		List<SocialRequest> list = findByUserId(userId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRequest[] findByUserId_PrevAndNext(long requestId,
		long userId, OrderByComparator obc)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByPrimaryKey(requestId);

		int count = countByUserId(userId);

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

			query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRequest);

			SocialRequest[] array = new SocialRequestImpl[3];

			array[0] = (SocialRequest)objArray[0];
			array[1] = (SocialRequest)objArray[1];
			array[2] = (SocialRequest)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialRequest> findByReceiverUserId(long receiverUserId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(receiverUserId) };

		List<SocialRequest> list = (List<SocialRequest>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_RECEIVERUSERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

				query.append(_FINDER_COLUMN_RECEIVERUSERID_RECEIVERUSERID_2);

				query.append(SocialRequestModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(receiverUserId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRequest>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_RECEIVERUSERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialRequest> findByReceiverUserId(long receiverUserId,
		int start, int end) throws SystemException {
		return findByReceiverUserId(receiverUserId, start, end, null);
	}

	public List<SocialRequest> findByReceiverUserId(long receiverUserId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(receiverUserId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SocialRequest> list = (List<SocialRequest>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_RECEIVERUSERID,
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

				query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

				query.append(_FINDER_COLUMN_RECEIVERUSERID_RECEIVERUSERID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(receiverUserId);

				list = (List<SocialRequest>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRequest>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_RECEIVERUSERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialRequest findByReceiverUserId_First(long receiverUserId,
		OrderByComparator obc) throws NoSuchRequestException, SystemException {
		List<SocialRequest> list = findByReceiverUserId(receiverUserId, 0, 1,
				obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("receiverUserId=");
			msg.append(receiverUserId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRequest findByReceiverUserId_Last(long receiverUserId,
		OrderByComparator obc) throws NoSuchRequestException, SystemException {
		int count = countByReceiverUserId(receiverUserId);

		List<SocialRequest> list = findByReceiverUserId(receiverUserId,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("receiverUserId=");
			msg.append(receiverUserId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRequest[] findByReceiverUserId_PrevAndNext(long requestId,
		long receiverUserId, OrderByComparator obc)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByPrimaryKey(requestId);

		int count = countByReceiverUserId(receiverUserId);

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

			query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

			query.append(_FINDER_COLUMN_RECEIVERUSERID_RECEIVERUSERID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(receiverUserId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRequest);

			SocialRequest[] array = new SocialRequestImpl[3];

			array[0] = (SocialRequest)objArray[0];
			array[1] = (SocialRequest)objArray[1];
			array[2] = (SocialRequest)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialRequest> findByU_S(long userId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId), new Integer(status) };

		List<SocialRequest> list = (List<SocialRequest>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_U_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

				query.append(_FINDER_COLUMN_U_S_USERID_2);

				query.append(_FINDER_COLUMN_U_S_STATUS_2);

				query.append(SocialRequestModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(status);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRequest>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_U_S, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialRequest> findByU_S(long userId, int status, int start,
		int end) throws SystemException {
		return findByU_S(userId, status, start, end, null);
	}

	public List<SocialRequest> findByU_S(long userId, int status, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId), new Integer(status),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SocialRequest> list = (List<SocialRequest>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_U_S,
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

				query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

				query.append(_FINDER_COLUMN_U_S_USERID_2);

				query.append(_FINDER_COLUMN_U_S_STATUS_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(status);

				list = (List<SocialRequest>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRequest>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_U_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialRequest findByU_S_First(long userId, int status,
		OrderByComparator obc) throws NoSuchRequestException, SystemException {
		List<SocialRequest> list = findByU_S(userId, status, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRequest findByU_S_Last(long userId, int status,
		OrderByComparator obc) throws NoSuchRequestException, SystemException {
		int count = countByU_S(userId, status);

		List<SocialRequest> list = findByU_S(userId, status, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRequest[] findByU_S_PrevAndNext(long requestId, long userId,
		int status, OrderByComparator obc)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByPrimaryKey(requestId);

		int count = countByU_S(userId, status);

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

			query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

			query.append(_FINDER_COLUMN_U_S_USERID_2);

			query.append(_FINDER_COLUMN_U_S_STATUS_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			qPos.add(status);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRequest);

			SocialRequest[] array = new SocialRequestImpl[3];

			array[0] = (SocialRequest)objArray[0];
			array[1] = (SocialRequest)objArray[1];
			array[2] = (SocialRequest)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialRequest> findByR_S(long receiverUserId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(receiverUserId), new Integer(status)
			};

		List<SocialRequest> list = (List<SocialRequest>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_R_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

				query.append(_FINDER_COLUMN_R_S_RECEIVERUSERID_2);

				query.append(_FINDER_COLUMN_R_S_STATUS_2);

				query.append(SocialRequestModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(receiverUserId);

				qPos.add(status);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRequest>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_R_S, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialRequest> findByR_S(long receiverUserId, int status,
		int start, int end) throws SystemException {
		return findByR_S(receiverUserId, status, start, end, null);
	}

	public List<SocialRequest> findByR_S(long receiverUserId, int status,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(receiverUserId), new Integer(status),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SocialRequest> list = (List<SocialRequest>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_R_S,
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

				query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

				query.append(_FINDER_COLUMN_R_S_RECEIVERUSERID_2);

				query.append(_FINDER_COLUMN_R_S_STATUS_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(receiverUserId);

				qPos.add(status);

				list = (List<SocialRequest>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRequest>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_R_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialRequest findByR_S_First(long receiverUserId, int status,
		OrderByComparator obc) throws NoSuchRequestException, SystemException {
		List<SocialRequest> list = findByR_S(receiverUserId, status, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("receiverUserId=");
			msg.append(receiverUserId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRequest findByR_S_Last(long receiverUserId, int status,
		OrderByComparator obc) throws NoSuchRequestException, SystemException {
		int count = countByR_S(receiverUserId, status);

		List<SocialRequest> list = findByR_S(receiverUserId, status, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("receiverUserId=");
			msg.append(receiverUserId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRequest[] findByR_S_PrevAndNext(long requestId,
		long receiverUserId, int status, OrderByComparator obc)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByPrimaryKey(requestId);

		int count = countByR_S(receiverUserId, status);

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

			query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

			query.append(_FINDER_COLUMN_R_S_RECEIVERUSERID_2);

			query.append(_FINDER_COLUMN_R_S_STATUS_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(receiverUserId);

			qPos.add(status);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRequest);

			SocialRequest[] array = new SocialRequestImpl[3];

			array[0] = (SocialRequest)objArray[0];
			array[1] = (SocialRequest)objArray[1];
			array[2] = (SocialRequest)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SocialRequest findByU_C_C_T_R(long userId, long classNameId,
		long classPK, int type, long receiverUserId)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = fetchByU_C_C_T_R(userId, classNameId,
				classPK, type, receiverUserId);

		if (socialRequest == null) {
			StringBundler msg = new StringBundler(12);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", type=");
			msg.append(type);

			msg.append(", receiverUserId=");
			msg.append(receiverUserId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchRequestException(msg.toString());
		}

		return socialRequest;
	}

	public SocialRequest fetchByU_C_C_T_R(long userId, long classNameId,
		long classPK, int type, long receiverUserId) throws SystemException {
		return fetchByU_C_C_T_R(userId, classNameId, classPK, type,
			receiverUserId, true);
	}

	public SocialRequest fetchByU_C_C_T_R(long userId, long classNameId,
		long classPK, int type, long receiverUserId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(classNameId), new Long(classPK),
				new Integer(type), new Long(receiverUserId)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_U_C_C_T_R,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(7);

				query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

				query.append(_FINDER_COLUMN_U_C_C_T_R_USERID_2);

				query.append(_FINDER_COLUMN_U_C_C_T_R_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_U_C_C_T_R_CLASSPK_2);

				query.append(_FINDER_COLUMN_U_C_C_T_R_TYPE_2);

				query.append(_FINDER_COLUMN_U_C_C_T_R_RECEIVERUSERID_2);

				query.append(SocialRequestModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				qPos.add(receiverUserId);

				List<SocialRequest> list = q.list();

				result = list;

				SocialRequest socialRequest = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_C_C_T_R,
						finderArgs, list);
				}
				else {
					socialRequest = list.get(0);

					cacheResult(socialRequest);

					if ((socialRequest.getUserId() != userId) ||
							(socialRequest.getClassNameId() != classNameId) ||
							(socialRequest.getClassPK() != classPK) ||
							(socialRequest.getType() != type) ||
							(socialRequest.getReceiverUserId() != receiverUserId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_C_C_T_R,
							finderArgs, socialRequest);
					}
				}

				return socialRequest;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_C_C_T_R,
						finderArgs, new ArrayList<SocialRequest>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (SocialRequest)result;
			}
		}
	}

	public List<SocialRequest> findByU_C_C_T_S(long userId, long classNameId,
		long classPK, int type, int status) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(classNameId), new Long(classPK),
				new Integer(type), new Integer(status)
			};

		List<SocialRequest> list = (List<SocialRequest>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_U_C_C_T_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(7);

				query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

				query.append(_FINDER_COLUMN_U_C_C_T_S_USERID_2);

				query.append(_FINDER_COLUMN_U_C_C_T_S_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_U_C_C_T_S_CLASSPK_2);

				query.append(_FINDER_COLUMN_U_C_C_T_S_TYPE_2);

				query.append(_FINDER_COLUMN_U_C_C_T_S_STATUS_2);

				query.append(SocialRequestModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				qPos.add(status);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRequest>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_U_C_C_T_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialRequest> findByU_C_C_T_S(long userId, long classNameId,
		long classPK, int type, int status, int start, int end)
		throws SystemException {
		return findByU_C_C_T_S(userId, classNameId, classPK, type, status,
			start, end, null);
	}

	public List<SocialRequest> findByU_C_C_T_S(long userId, long classNameId,
		long classPK, int type, int status, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(classNameId), new Long(classPK),
				new Integer(type), new Integer(status),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SocialRequest> list = (List<SocialRequest>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_U_C_C_T_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(7 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(7);
				}

				query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

				query.append(_FINDER_COLUMN_U_C_C_T_S_USERID_2);

				query.append(_FINDER_COLUMN_U_C_C_T_S_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_U_C_C_T_S_CLASSPK_2);

				query.append(_FINDER_COLUMN_U_C_C_T_S_TYPE_2);

				query.append(_FINDER_COLUMN_U_C_C_T_S_STATUS_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				qPos.add(status);

				list = (List<SocialRequest>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRequest>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_U_C_C_T_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialRequest findByU_C_C_T_S_First(long userId, long classNameId,
		long classPK, int type, int status, OrderByComparator obc)
		throws NoSuchRequestException, SystemException {
		List<SocialRequest> list = findByU_C_C_T_S(userId, classNameId,
				classPK, type, status, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(12);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", type=");
			msg.append(type);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRequest findByU_C_C_T_S_Last(long userId, long classNameId,
		long classPK, int type, int status, OrderByComparator obc)
		throws NoSuchRequestException, SystemException {
		int count = countByU_C_C_T_S(userId, classNameId, classPK, type, status);

		List<SocialRequest> list = findByU_C_C_T_S(userId, classNameId,
				classPK, type, status, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(12);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", type=");
			msg.append(type);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRequest[] findByU_C_C_T_S_PrevAndNext(long requestId,
		long userId, long classNameId, long classPK, int type, int status,
		OrderByComparator obc) throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByPrimaryKey(requestId);

		int count = countByU_C_C_T_S(userId, classNameId, classPK, type, status);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(7 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(7);
			}

			query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

			query.append(_FINDER_COLUMN_U_C_C_T_S_USERID_2);

			query.append(_FINDER_COLUMN_U_C_C_T_S_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_U_C_C_T_S_CLASSPK_2);

			query.append(_FINDER_COLUMN_U_C_C_T_S_TYPE_2);

			query.append(_FINDER_COLUMN_U_C_C_T_S_STATUS_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			qPos.add(classNameId);

			qPos.add(classPK);

			qPos.add(type);

			qPos.add(status);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRequest);

			SocialRequest[] array = new SocialRequestImpl[3];

			array[0] = (SocialRequest)objArray[0];
			array[1] = (SocialRequest)objArray[1];
			array[2] = (SocialRequest)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialRequest> findByC_C_T_R_S(long classNameId, long classPK,
		int type, long receiverUserId, int status) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK), new Integer(type),
				new Long(receiverUserId), new Integer(status)
			};

		List<SocialRequest> list = (List<SocialRequest>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_C_T_R_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(7);

				query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

				query.append(_FINDER_COLUMN_C_C_T_R_S_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_T_R_S_CLASSPK_2);

				query.append(_FINDER_COLUMN_C_C_T_R_S_TYPE_2);

				query.append(_FINDER_COLUMN_C_C_T_R_S_RECEIVERUSERID_2);

				query.append(_FINDER_COLUMN_C_C_T_R_S_STATUS_2);

				query.append(SocialRequestModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				qPos.add(receiverUserId);

				qPos.add(status);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRequest>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_C_T_R_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialRequest> findByC_C_T_R_S(long classNameId, long classPK,
		int type, long receiverUserId, int status, int start, int end)
		throws SystemException {
		return findByC_C_T_R_S(classNameId, classPK, type, receiverUserId,
			status, start, end, null);
	}

	public List<SocialRequest> findByC_C_T_R_S(long classNameId, long classPK,
		int type, long receiverUserId, int status, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK), new Integer(type),
				new Long(receiverUserId), new Integer(status),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SocialRequest> list = (List<SocialRequest>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_C_T_R_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(7 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(7);
				}

				query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

				query.append(_FINDER_COLUMN_C_C_T_R_S_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_T_R_S_CLASSPK_2);

				query.append(_FINDER_COLUMN_C_C_T_R_S_TYPE_2);

				query.append(_FINDER_COLUMN_C_C_T_R_S_RECEIVERUSERID_2);

				query.append(_FINDER_COLUMN_C_C_T_R_S_STATUS_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				qPos.add(receiverUserId);

				qPos.add(status);

				list = (List<SocialRequest>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRequest>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_C_T_R_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialRequest findByC_C_T_R_S_First(long classNameId, long classPK,
		int type, long receiverUserId, int status, OrderByComparator obc)
		throws NoSuchRequestException, SystemException {
		List<SocialRequest> list = findByC_C_T_R_S(classNameId, classPK, type,
				receiverUserId, status, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(12);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", type=");
			msg.append(type);

			msg.append(", receiverUserId=");
			msg.append(receiverUserId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRequest findByC_C_T_R_S_Last(long classNameId, long classPK,
		int type, long receiverUserId, int status, OrderByComparator obc)
		throws NoSuchRequestException, SystemException {
		int count = countByC_C_T_R_S(classNameId, classPK, type,
				receiverUserId, status);

		List<SocialRequest> list = findByC_C_T_R_S(classNameId, classPK, type,
				receiverUserId, status, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(12);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", type=");
			msg.append(type);

			msg.append(", receiverUserId=");
			msg.append(receiverUserId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRequest[] findByC_C_T_R_S_PrevAndNext(long requestId,
		long classNameId, long classPK, int type, long receiverUserId,
		int status, OrderByComparator obc)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByPrimaryKey(requestId);

		int count = countByC_C_T_R_S(classNameId, classPK, type,
				receiverUserId, status);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(7 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(7);
			}

			query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

			query.append(_FINDER_COLUMN_C_C_T_R_S_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_T_R_S_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_T_R_S_TYPE_2);

			query.append(_FINDER_COLUMN_C_C_T_R_S_RECEIVERUSERID_2);

			query.append(_FINDER_COLUMN_C_C_T_R_S_STATUS_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

			qPos.add(classPK);

			qPos.add(type);

			qPos.add(receiverUserId);

			qPos.add(status);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRequest);

			SocialRequest[] array = new SocialRequestImpl[3];

			array[0] = (SocialRequest)objArray[0];
			array[1] = (SocialRequest)objArray[1];
			array[2] = (SocialRequest)objArray[2];

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

	public List<SocialRequest> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<SocialRequest> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<SocialRequest> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SocialRequest> list = (List<SocialRequest>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_SOCIALREQUEST);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_SOCIALREQUEST.concat(SocialRequestModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (obc == null) {
					list = (List<SocialRequest>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SocialRequest>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRequest>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (SocialRequest socialRequest : findByUuid(uuid)) {
			remove(socialRequest);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByUUID_G(uuid, groupId);

		remove(socialRequest);
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (SocialRequest socialRequest : findByCompanyId(companyId)) {
			remove(socialRequest);
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		for (SocialRequest socialRequest : findByUserId(userId)) {
			remove(socialRequest);
		}
	}

	public void removeByReceiverUserId(long receiverUserId)
		throws SystemException {
		for (SocialRequest socialRequest : findByReceiverUserId(receiverUserId)) {
			remove(socialRequest);
		}
	}

	public void removeByU_S(long userId, int status) throws SystemException {
		for (SocialRequest socialRequest : findByU_S(userId, status)) {
			remove(socialRequest);
		}
	}

	public void removeByR_S(long receiverUserId, int status)
		throws SystemException {
		for (SocialRequest socialRequest : findByR_S(receiverUserId, status)) {
			remove(socialRequest);
		}
	}

	public void removeByU_C_C_T_R(long userId, long classNameId, long classPK,
		int type, long receiverUserId)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByU_C_C_T_R(userId, classNameId,
				classPK, type, receiverUserId);

		remove(socialRequest);
	}

	public void removeByU_C_C_T_S(long userId, long classNameId, long classPK,
		int type, int status) throws SystemException {
		for (SocialRequest socialRequest : findByU_C_C_T_S(userId, classNameId,
				classPK, type, status)) {
			remove(socialRequest);
		}
	}

	public void removeByC_C_T_R_S(long classNameId, long classPK, int type,
		long receiverUserId, int status) throws SystemException {
		for (SocialRequest socialRequest : findByC_C_T_R_S(classNameId,
				classPK, type, receiverUserId, status)) {
			remove(socialRequest);
		}
	}

	public void removeAll() throws SystemException {
		for (SocialRequest socialRequest : findAll()) {
			remove(socialRequest);
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

				query.append(_SQL_COUNT_SOCIALREQUEST_WHERE);

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

				query.append(_SQL_COUNT_SOCIALREQUEST_WHERE);

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

	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_SOCIALREQUEST_WHERE);

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

	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_SOCIALREQUEST_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByReceiverUserId(long receiverUserId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(receiverUserId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_RECEIVERUSERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_SOCIALREQUEST_WHERE);

				query.append(_FINDER_COLUMN_RECEIVERUSERID_RECEIVERUSERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(receiverUserId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_RECEIVERUSERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByU_S(long userId, int status) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId), new Integer(status) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_SOCIALREQUEST_WHERE);

				query.append(_FINDER_COLUMN_U_S_USERID_2);

				query.append(_FINDER_COLUMN_U_S_STATUS_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(status);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_S, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByR_S(long receiverUserId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(receiverUserId), new Integer(status)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_R_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_SOCIALREQUEST_WHERE);

				query.append(_FINDER_COLUMN_R_S_RECEIVERUSERID_2);

				query.append(_FINDER_COLUMN_R_S_STATUS_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(receiverUserId);

				qPos.add(status);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_R_S, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByU_C_C_T_R(long userId, long classNameId, long classPK,
		int type, long receiverUserId) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(classNameId), new Long(classPK),
				new Integer(type), new Long(receiverUserId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_C_C_T_R,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(6);

				query.append(_SQL_COUNT_SOCIALREQUEST_WHERE);

				query.append(_FINDER_COLUMN_U_C_C_T_R_USERID_2);

				query.append(_FINDER_COLUMN_U_C_C_T_R_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_U_C_C_T_R_CLASSPK_2);

				query.append(_FINDER_COLUMN_U_C_C_T_R_TYPE_2);

				query.append(_FINDER_COLUMN_U_C_C_T_R_RECEIVERUSERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				qPos.add(receiverUserId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_C_C_T_R,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByU_C_C_T_S(long userId, long classNameId, long classPK,
		int type, int status) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(classNameId), new Long(classPK),
				new Integer(type), new Integer(status)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_C_C_T_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(6);

				query.append(_SQL_COUNT_SOCIALREQUEST_WHERE);

				query.append(_FINDER_COLUMN_U_C_C_T_S_USERID_2);

				query.append(_FINDER_COLUMN_U_C_C_T_S_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_U_C_C_T_S_CLASSPK_2);

				query.append(_FINDER_COLUMN_U_C_C_T_S_TYPE_2);

				query.append(_FINDER_COLUMN_U_C_C_T_S_STATUS_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				qPos.add(status);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_C_C_T_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_C_T_R_S(long classNameId, long classPK, int type,
		long receiverUserId, int status) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK), new Integer(type),
				new Long(receiverUserId), new Integer(status)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C_T_R_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(6);

				query.append(_SQL_COUNT_SOCIALREQUEST_WHERE);

				query.append(_FINDER_COLUMN_C_C_T_R_S_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_T_R_S_CLASSPK_2);

				query.append(_FINDER_COLUMN_C_C_T_R_S_TYPE_2);

				query.append(_FINDER_COLUMN_C_C_T_R_S_RECEIVERUSERID_2);

				query.append(_FINDER_COLUMN_C_C_T_R_S_STATUS_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				qPos.add(receiverUserId);

				qPos.add(status);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C_T_R_S,
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

				Query q = session.createQuery(_SQL_COUNT_SOCIALREQUEST);

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
						"value.object.listener.com.liferay.portlet.social.model.SocialRequest")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SocialRequest>> listenersList = new ArrayList<ModelListener<SocialRequest>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SocialRequest>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialActivityPersistence")
	protected com.liferay.portlet.social.service.persistence.SocialActivityPersistence socialActivityPersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialRelationPersistence")
	protected com.liferay.portlet.social.service.persistence.SocialRelationPersistence socialRelationPersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialRequestPersistence")
	protected com.liferay.portlet.social.service.persistence.SocialRequestPersistence socialRequestPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static final String _SQL_SELECT_SOCIALREQUEST = "SELECT socialRequest FROM SocialRequest socialRequest";
	private static final String _SQL_SELECT_SOCIALREQUEST_WHERE = "SELECT socialRequest FROM SocialRequest socialRequest WHERE ";
	private static final String _SQL_COUNT_SOCIALREQUEST = "SELECT COUNT(socialRequest) FROM SocialRequest socialRequest";
	private static final String _SQL_COUNT_SOCIALREQUEST_WHERE = "SELECT COUNT(socialRequest) FROM SocialRequest socialRequest WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "socialRequest.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "socialRequest.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(socialRequest.uuid IS NULL OR socialRequest.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "socialRequest.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "socialRequest.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(socialRequest.uuid IS NULL OR socialRequest.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "socialRequest.groupId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "socialRequest.companyId = ?";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "socialRequest.userId = ?";
	private static final String _FINDER_COLUMN_RECEIVERUSERID_RECEIVERUSERID_2 = "socialRequest.receiverUserId = ?";
	private static final String _FINDER_COLUMN_U_S_USERID_2 = "socialRequest.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_S_STATUS_2 = "socialRequest.status = ?";
	private static final String _FINDER_COLUMN_R_S_RECEIVERUSERID_2 = "socialRequest.receiverUserId = ? AND ";
	private static final String _FINDER_COLUMN_R_S_STATUS_2 = "socialRequest.status = ?";
	private static final String _FINDER_COLUMN_U_C_C_T_R_USERID_2 = "socialRequest.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_C_C_T_R_CLASSNAMEID_2 = "socialRequest.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_U_C_C_T_R_CLASSPK_2 = "socialRequest.classPK = ? AND ";
	private static final String _FINDER_COLUMN_U_C_C_T_R_TYPE_2 = "socialRequest.type = ? AND ";
	private static final String _FINDER_COLUMN_U_C_C_T_R_RECEIVERUSERID_2 = "socialRequest.receiverUserId = ?";
	private static final String _FINDER_COLUMN_U_C_C_T_S_USERID_2 = "socialRequest.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_C_C_T_S_CLASSNAMEID_2 = "socialRequest.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_U_C_C_T_S_CLASSPK_2 = "socialRequest.classPK = ? AND ";
	private static final String _FINDER_COLUMN_U_C_C_T_S_TYPE_2 = "socialRequest.type = ? AND ";
	private static final String _FINDER_COLUMN_U_C_C_T_S_STATUS_2 = "socialRequest.status = ?";
	private static final String _FINDER_COLUMN_C_C_T_R_S_CLASSNAMEID_2 = "socialRequest.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_T_R_S_CLASSPK_2 = "socialRequest.classPK = ? AND ";
	private static final String _FINDER_COLUMN_C_C_T_R_S_TYPE_2 = "socialRequest.type = ? AND ";
	private static final String _FINDER_COLUMN_C_C_T_R_S_RECEIVERUSERID_2 = "socialRequest.receiverUserId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_T_R_S_STATUS_2 = "socialRequest.status = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "socialRequest.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SocialRequest exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SocialRequest exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(SocialRequestPersistenceImpl.class);
}