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

package com.liferay.portlet.social.service;


/**
 * <a href="SocialRequestLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link SocialRequestLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialRequestLocalService
 * @generated
 */
public class SocialRequestLocalServiceWrapper
	implements SocialRequestLocalService {
	public SocialRequestLocalServiceWrapper(
		SocialRequestLocalService socialRequestLocalService) {
		_socialRequestLocalService = socialRequestLocalService;
	}

	public com.liferay.portlet.social.model.SocialRequest addSocialRequest(
		com.liferay.portlet.social.model.SocialRequest socialRequest)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.addSocialRequest(socialRequest);
	}

	public com.liferay.portlet.social.model.SocialRequest createSocialRequest(
		long requestId) {
		return _socialRequestLocalService.createSocialRequest(requestId);
	}

	public void deleteSocialRequest(long requestId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialRequestLocalService.deleteSocialRequest(requestId);
	}

	public void deleteSocialRequest(
		com.liferay.portlet.social.model.SocialRequest socialRequest)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialRequestLocalService.deleteSocialRequest(socialRequest);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.social.model.SocialRequest getSocialRequest(
		long requestId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getSocialRequest(requestId);
	}

	public com.liferay.portlet.social.model.SocialRequest getSocialRequestByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getSocialRequestByUuidAndGroupId(uuid,
			groupId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> getSocialRequests(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getSocialRequests(start, end);
	}

	public int getSocialRequestsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getSocialRequestsCount();
	}

	public com.liferay.portlet.social.model.SocialRequest updateSocialRequest(
		com.liferay.portlet.social.model.SocialRequest socialRequest)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.updateSocialRequest(socialRequest);
	}

	public com.liferay.portlet.social.model.SocialRequest updateSocialRequest(
		com.liferay.portlet.social.model.SocialRequest socialRequest,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.updateSocialRequest(socialRequest,
			merge);
	}

	public com.liferay.portlet.social.model.SocialRequest addRequest(
		long userId, long groupId, java.lang.String className, long classPK,
		int type, java.lang.String extraData, long receiverUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.addRequest(userId, groupId,
			className, classPK, type, extraData, receiverUserId);
	}

	public void deleteReceiverUserRequests(long receiverUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialRequestLocalService.deleteReceiverUserRequests(receiverUserId);
	}

	public void deleteRequest(long requestId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialRequestLocalService.deleteRequest(requestId);
	}

	public void deleteUserRequests(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialRequestLocalService.deleteUserRequests(userId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> getReceiverUserRequests(
		long receiverUserId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getReceiverUserRequests(receiverUserId,
			start, end);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> getReceiverUserRequests(
		long receiverUserId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getReceiverUserRequests(receiverUserId,
			status, start, end);
	}

	public int getReceiverUserRequestsCount(long receiverUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getReceiverUserRequestsCount(receiverUserId);
	}

	public int getReceiverUserRequestsCount(long receiverUserId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getReceiverUserRequestsCount(receiverUserId,
			status);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> getUserRequests(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getUserRequests(userId, start, end);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> getUserRequests(
		long userId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getUserRequests(userId, status,
			start, end);
	}

	public int getUserRequestsCount(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getUserRequestsCount(userId);
	}

	public int getUserRequestsCount(long userId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getUserRequestsCount(userId, status);
	}

	public boolean hasRequest(long userId, java.lang.String className,
		long classPK, int type, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.hasRequest(userId, className,
			classPK, type, status);
	}

	public boolean hasRequest(long userId, java.lang.String className,
		long classPK, int type, long receiverUserId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.hasRequest(userId, className,
			classPK, type, receiverUserId, status);
	}

	public com.liferay.portlet.social.model.SocialRequest updateRequest(
		long requestId, int status,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.updateRequest(requestId, status,
			themeDisplay);
	}

	public SocialRequestLocalService getWrappedSocialRequestLocalService() {
		return _socialRequestLocalService;
	}

	private SocialRequestLocalService _socialRequestLocalService;
}