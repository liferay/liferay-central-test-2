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

import com.liferay.portlet.social.model.SocialRequest;

import java.util.List;

/**
 * <a href="SocialRequestUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialRequestPersistence
 * @see       SocialRequestPersistenceImpl
 * @generated
 */
public class SocialRequestUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static SocialRequest remove(SocialRequest socialRequest)
		throws SystemException {
		return getPersistence().remove(socialRequest);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static SocialRequest update(SocialRequest socialRequest,
		boolean merge) throws SystemException {
		return getPersistence().update(socialRequest, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.social.model.SocialRequest socialRequest) {
		getPersistence().cacheResult(socialRequest);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.social.model.SocialRequest> socialRequests) {
		getPersistence().cacheResult(socialRequests);
	}

	public static com.liferay.portlet.social.model.SocialRequest create(
		long requestId) {
		return getPersistence().create(requestId);
	}

	public static com.liferay.portlet.social.model.SocialRequest remove(
		long requestId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence().remove(requestId);
	}

	public static com.liferay.portlet.social.model.SocialRequest updateImpl(
		com.liferay.portlet.social.model.SocialRequest socialRequest,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(socialRequest, merge);
	}

	public static com.liferay.portlet.social.model.SocialRequest findByPrimaryKey(
		long requestId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence().findByPrimaryKey(requestId);
	}

	public static com.liferay.portlet.social.model.SocialRequest fetchByPrimaryKey(
		long requestId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(requestId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRequest findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRequest findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRequest[] findByUuid_PrevAndNext(
		long requestId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByUuid_PrevAndNext(requestId, uuid, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRequest findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.social.model.SocialRequest fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.social.model.SocialRequest fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRequest findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRequest findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRequest[] findByCompanyId_PrevAndNext(
		long requestId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(requestId, companyId,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRequest findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRequest findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRequest[] findByUserId_PrevAndNext(
		long requestId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByUserId_PrevAndNext(requestId, userId,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> findByReceiverUserId(
		long receiverUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByReceiverUserId(receiverUserId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> findByReceiverUserId(
		long receiverUserId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByReceiverUserId(receiverUserId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> findByReceiverUserId(
		long receiverUserId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByReceiverUserId(receiverUserId, start, end,
			orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRequest findByReceiverUserId_First(
		long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByReceiverUserId_First(receiverUserId, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRequest findByReceiverUserId_Last(
		long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByReceiverUserId_Last(receiverUserId, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRequest[] findByReceiverUserId_PrevAndNext(
		long requestId, long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByReceiverUserId_PrevAndNext(requestId, receiverUserId,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> findByU_S(
		long userId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByU_S(userId, status);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> findByU_S(
		long userId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByU_S(userId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> findByU_S(
		long userId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_S(userId, status, start, end, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRequest findByU_S_First(
		long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByU_S_First(userId, status, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRequest findByU_S_Last(
		long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence().findByU_S_Last(userId, status, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRequest[] findByU_S_PrevAndNext(
		long requestId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByU_S_PrevAndNext(requestId, userId, status,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> findByR_S(
		long receiverUserId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByR_S(receiverUserId, status);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> findByR_S(
		long receiverUserId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByR_S(receiverUserId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> findByR_S(
		long receiverUserId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByR_S(receiverUserId, status, start, end,
			orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRequest findByR_S_First(
		long receiverUserId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByR_S_First(receiverUserId, status, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRequest findByR_S_Last(
		long receiverUserId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByR_S_Last(receiverUserId, status, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRequest[] findByR_S_PrevAndNext(
		long requestId, long receiverUserId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByR_S_PrevAndNext(requestId, receiverUserId, status,
			orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRequest findByU_C_C_T_R(
		long userId, long classNameId, long classPK, int type,
		long receiverUserId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByU_C_C_T_R(userId, classNameId, classPK, type,
			receiverUserId);
	}

	public static com.liferay.portlet.social.model.SocialRequest fetchByU_C_C_T_R(
		long userId, long classNameId, long classPK, int type,
		long receiverUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByU_C_C_T_R(userId, classNameId, classPK, type,
			receiverUserId);
	}

	public static com.liferay.portlet.social.model.SocialRequest fetchByU_C_C_T_R(
		long userId, long classNameId, long classPK, int type,
		long receiverUserId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByU_C_C_T_R(userId, classNameId, classPK, type,
			receiverUserId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> findByU_C_C_T_S(
		long userId, long classNameId, long classPK, int type, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_C_C_T_S(userId, classNameId, classPK, type, status);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> findByU_C_C_T_S(
		long userId, long classNameId, long classPK, int type, int status,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_C_C_T_S(userId, classNameId, classPK, type, status,
			start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> findByU_C_C_T_S(
		long userId, long classNameId, long classPK, int type, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_C_C_T_S(userId, classNameId, classPK, type, status,
			start, end, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRequest findByU_C_C_T_S_First(
		long userId, long classNameId, long classPK, int type, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByU_C_C_T_S_First(userId, classNameId, classPK, type,
			status, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRequest findByU_C_C_T_S_Last(
		long userId, long classNameId, long classPK, int type, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByU_C_C_T_S_Last(userId, classNameId, classPK, type,
			status, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRequest[] findByU_C_C_T_S_PrevAndNext(
		long requestId, long userId, long classNameId, long classPK, int type,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByU_C_C_T_S_PrevAndNext(requestId, userId, classNameId,
			classPK, type, status, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> findByC_C_T_R_S(
		long classNameId, long classPK, int type, long receiverUserId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_C_T_R_S(classNameId, classPK, type, receiverUserId,
			status);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> findByC_C_T_R_S(
		long classNameId, long classPK, int type, long receiverUserId,
		int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_C_T_R_S(classNameId, classPK, type, receiverUserId,
			status, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> findByC_C_T_R_S(
		long classNameId, long classPK, int type, long receiverUserId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_C_T_R_S(classNameId, classPK, type, receiverUserId,
			status, start, end, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRequest findByC_C_T_R_S_First(
		long classNameId, long classPK, int type, long receiverUserId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByC_C_T_R_S_First(classNameId, classPK, type,
			receiverUserId, status, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRequest findByC_C_T_R_S_Last(
		long classNameId, long classPK, int type, long receiverUserId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByC_C_T_R_S_Last(classNameId, classPK, type,
			receiverUserId, status, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialRequest[] findByC_C_T_R_S_PrevAndNext(
		long requestId, long classNameId, long classPK, int type,
		long receiverUserId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByC_C_T_R_S_PrevAndNext(requestId, classNameId,
			classPK, type, receiverUserId, status, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	public static void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		getPersistence().removeByUUID_G(uuid, groupId);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeByReceiverUserId(long receiverUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByReceiverUserId(receiverUserId);
	}

	public static void removeByU_S(long userId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByU_S(userId, status);
	}

	public static void removeByR_S(long receiverUserId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByR_S(receiverUserId, status);
	}

	public static void removeByU_C_C_T_R(long userId, long classNameId,
		long classPK, int type, long receiverUserId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRequestException {
		getPersistence()
			.removeByU_C_C_T_R(userId, classNameId, classPK, type,
			receiverUserId);
	}

	public static void removeByU_C_C_T_S(long userId, long classNameId,
		long classPK, int type, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByU_C_C_T_S(userId, classNameId, classPK, type, status);
	}

	public static void removeByC_C_T_R_S(long classNameId, long classPK,
		int type, long receiverUserId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByC_C_T_R_S(classNameId, classPK, type, receiverUserId,
			status);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countByReceiverUserId(long receiverUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByReceiverUserId(receiverUserId);
	}

	public static int countByU_S(long userId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByU_S(userId, status);
	}

	public static int countByR_S(long receiverUserId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByR_S(receiverUserId, status);
	}

	public static int countByU_C_C_T_R(long userId, long classNameId,
		long classPK, int type, long receiverUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByU_C_C_T_R(userId, classNameId, classPK, type,
			receiverUserId);
	}

	public static int countByU_C_C_T_S(long userId, long classNameId,
		long classPK, int type, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByU_C_C_T_S(userId, classNameId, classPK, type, status);
	}

	public static int countByC_C_T_R_S(long classNameId, long classPK,
		int type, long receiverUserId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByC_C_T_R_S(classNameId, classPK, type,
			receiverUserId, status);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static SocialRequestPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SocialRequestPersistence)PortalBeanLocatorUtil.locate(SocialRequestPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(SocialRequestPersistence persistence) {
		_persistence = persistence;
	}

	private static SocialRequestPersistence _persistence;
}