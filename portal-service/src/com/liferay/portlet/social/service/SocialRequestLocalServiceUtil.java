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

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.social.model.SocialRequest getSocialRequest(
		long requestId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialRequest(requestId);
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