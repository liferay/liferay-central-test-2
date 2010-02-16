/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.social.model.SocialRequest getSocialRequest(
		long requestId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getSocialRequest(requestId);
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