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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="SocialRequestLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link SocialRequestLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialRequestLocalService
 * @generated
 */
public class SocialRequestLocalServiceUtil {
	public static com.liferay.portlet.social.model.SocialRequest addSocialRequest(
		com.liferay.portlet.social.model.SocialRequest socialRequest)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addSocialRequest(socialRequest);
	}

	public static com.liferay.portlet.social.model.SocialRequest createSocialRequest(
		long requestId) {
		return getService().createSocialRequest(requestId);
	}

	public static void deleteSocialRequest(long requestId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSocialRequest(requestId);
	}

	public static void deleteSocialRequest(
		com.liferay.portlet.social.model.SocialRequest socialRequest)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSocialRequest(socialRequest);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portlet.social.model.SocialRequest getSocialRequest(
		long requestId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialRequest(requestId);
	}

	public static com.liferay.portlet.social.model.SocialRequest getSocialRequestByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialRequestByUuidAndGroupId(uuid, groupId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> getSocialRequests(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialRequests(start, end);
	}

	public static int getSocialRequestsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialRequestsCount();
	}

	public static com.liferay.portlet.social.model.SocialRequest updateSocialRequest(
		com.liferay.portlet.social.model.SocialRequest socialRequest)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSocialRequest(socialRequest);
	}

	public static com.liferay.portlet.social.model.SocialRequest updateSocialRequest(
		com.liferay.portlet.social.model.SocialRequest socialRequest,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSocialRequest(socialRequest, merge);
	}

	public static com.liferay.portlet.social.model.SocialRequest addRequest(
		long userId, long groupId, java.lang.String className, long classPK,
		int type, java.lang.String extraData, long receiverUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addRequest(userId, groupId, className, classPK, type,
			extraData, receiverUserId);
	}

	public static void deleteReceiverUserRequests(long receiverUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteReceiverUserRequests(receiverUserId);
	}

	public static void deleteRequest(long requestId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRequest(requestId);
	}

	public static void deleteUserRequests(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserRequests(userId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> getReceiverUserRequests(
		long receiverUserId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getReceiverUserRequests(receiverUserId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> getReceiverUserRequests(
		long receiverUserId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getReceiverUserRequests(receiverUserId, status, start, end);
	}

	public static int getReceiverUserRequestsCount(long receiverUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getReceiverUserRequestsCount(receiverUserId);
	}

	public static int getReceiverUserRequestsCount(long receiverUserId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getReceiverUserRequestsCount(receiverUserId, status);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> getUserRequests(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserRequests(userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> getUserRequests(
		long userId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserRequests(userId, status, start, end);
	}

	public static int getUserRequestsCount(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserRequestsCount(userId);
	}

	public static int getUserRequestsCount(long userId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserRequestsCount(userId, status);
	}

	public static boolean hasRequest(long userId, java.lang.String className,
		long classPK, int type, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasRequest(userId, className, classPK, type, status);
	}

	public static boolean hasRequest(long userId, java.lang.String className,
		long classPK, int type, long receiverUserId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .hasRequest(userId, className, classPK, type,
			receiverUserId, status);
	}

	public static com.liferay.portlet.social.model.SocialRequest updateRequest(
		long requestId, int status,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateRequest(requestId, status, themeDisplay);
	}

	public static SocialRequestLocalService getService() {
		if (_service == null) {
			_service = (SocialRequestLocalService)PortalBeanLocatorUtil.locate(SocialRequestLocalService.class.getName());
		}

		return _service;
	}

	public void setService(SocialRequestLocalService service) {
		_service = service;
	}

	private static SocialRequestLocalService _service;
}