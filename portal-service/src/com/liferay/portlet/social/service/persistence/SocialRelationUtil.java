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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.social.model.SocialRelation;

import java.util.List;

/**
 * <a href="SocialRelationUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialRelationPersistence
 * @see       SocialRelationPersistenceImpl
 * @generated
 */
public class SocialRelationUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(SocialRelation)
	 */
	public static void clearCache(SocialRelation socialRelation) {
		getPersistence().clearCache(socialRelation);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<SocialRelation> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SocialRelation> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static SocialRelation remove(SocialRelation socialRelation)
		throws SystemException {
		return getPersistence().remove(socialRelation);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static SocialRelation update(SocialRelation socialRelation,
		boolean merge) throws SystemException {
		return getPersistence().update(socialRelation, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.social.model.SocialRelation socialRelation) {
		getPersistence().cacheResult(socialRelation);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.social.model.SocialRelation> socialRelations) {
		getPersistence().cacheResult(socialRelations);
	}

	public static com.liferay.portlet.social.model.SocialRelation create(
		long relationId) {
		return getPersistence().create(relationId);
	}

	public static com.liferay.portlet.social.model.SocialRelation remove(
		long relationId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().remove(relationId);
	}

	public static com.liferay.portlet.social.model.SocialRelation updateImpl(
		com.liferay.portlet.social.model.SocialRelation socialRelation,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(socialRelation, merge);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByPrimaryKey(
		long relationId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByPrimaryKey(relationId);
	}

	public static com.liferay.portlet.social.model.SocialRelation fetchByPrimaryKey(
		long relationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(relationId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRelation[] findByUuid_PrevAndNext(
		long relationId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByUuid_PrevAndNext(relationId, uuid, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRelation[] findByCompanyId_PrevAndNext(
		long relationId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(relationId, companyId,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUserId1(
		long userId1)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId1(userId1);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUserId1(
		long userId1, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId1(userId1, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUserId1(
		long userId1, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId1(userId1, start, end, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByUserId1_First(
		long userId1,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByUserId1_First(userId1, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByUserId1_Last(
		long userId1,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByUserId1_Last(userId1, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRelation[] findByUserId1_PrevAndNext(
		long relationId, long userId1,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByUserId1_PrevAndNext(relationId, userId1,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUserId2(
		long userId2)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId2(userId2);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUserId2(
		long userId2, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId2(userId2, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUserId2(
		long userId2, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId2(userId2, start, end, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByUserId2_First(
		long userId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByUserId2_First(userId2, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByUserId2_Last(
		long userId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByUserId2_Last(userId2, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRelation[] findByUserId2_PrevAndNext(
		long relationId, long userId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByUserId2_PrevAndNext(relationId, userId2,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByType(
		int type) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByType(type);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByType(
		int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByType(type, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByType(
		int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByType(type, start, end, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByType_First(
		int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByType_First(type, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByType_Last(
		int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByType_Last(type, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRelation[] findByType_PrevAndNext(
		long relationId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByType_PrevAndNext(relationId, type, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByC_T(
		long companyId, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_T(companyId, type);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByC_T(
		long companyId, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_T(companyId, type, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByC_T(
		long companyId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_T(companyId, type, start, end, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByC_T_First(
		long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByC_T_First(companyId, type, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByC_T_Last(
		long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByC_T_Last(companyId, type, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRelation[] findByC_T_PrevAndNext(
		long relationId, long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByC_T_PrevAndNext(relationId, companyId, type,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByU1_T(
		long userId1, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByU1_T(userId1, type);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByU1_T(
		long userId1, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByU1_T(userId1, type, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByU1_T(
		long userId1, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU1_T(userId1, type, start, end, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByU1_T_First(
		long userId1, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByU1_T_First(userId1, type, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByU1_T_Last(
		long userId1, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByU1_T_Last(userId1, type, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRelation[] findByU1_T_PrevAndNext(
		long relationId, long userId1, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByU1_T_PrevAndNext(relationId, userId1, type,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByU2_T(
		long userId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByU2_T(userId2, type);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByU2_T(
		long userId2, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByU2_T(userId2, type, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findByU2_T(
		long userId2, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU2_T(userId2, type, start, end, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByU2_T_First(
		long userId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByU2_T_First(userId2, type, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByU2_T_Last(
		long userId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByU2_T_Last(userId2, type, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRelation[] findByU2_T_PrevAndNext(
		long relationId, long userId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByU2_T_PrevAndNext(relationId, userId2, type,
			orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRelation findByU1_U2_T(
		long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByU1_U2_T(userId1, userId2, type);
	}

	public static com.liferay.portlet.social.model.SocialRelation fetchByU1_U2_T(
		long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByU1_U2_T(userId1, userId2, type);
	}

	public static com.liferay.portlet.social.model.SocialRelation fetchByU1_U2_T(
		long userId1, long userId2, int type, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByU1_U2_T(userId1, userId2, type, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByUserId1(long userId1)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId1(userId1);
	}

	public static void removeByUserId2(long userId2)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId2(userId2);
	}

	public static void removeByType(int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByType(type);
	}

	public static void removeByC_T(long companyId, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_T(companyId, type);
	}

	public static void removeByU1_T(long userId1, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByU1_T(userId1, type);
	}

	public static void removeByU2_T(long userId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByU2_T(userId2, type);
	}

	public static void removeByU1_U2_T(long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException {
		getPersistence().removeByU1_U2_T(userId1, userId2, type);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByUserId1(long userId1)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId1(userId1);
	}

	public static int countByUserId2(long userId2)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId2(userId2);
	}

	public static int countByType(int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByType(type);
	}

	public static int countByC_T(long companyId, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_T(companyId, type);
	}

	public static int countByU1_T(long userId1, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByU1_T(userId1, type);
	}

	public static int countByU2_T(long userId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByU2_T(userId2, type);
	}

	public static int countByU1_U2_T(long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByU1_U2_T(userId1, userId2, type);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static SocialRelationPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SocialRelationPersistence)PortalBeanLocatorUtil.locate(SocialRelationPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(SocialRelationPersistence persistence) {
		_persistence = persistence;
	}

	private static SocialRelationPersistence _persistence;
}