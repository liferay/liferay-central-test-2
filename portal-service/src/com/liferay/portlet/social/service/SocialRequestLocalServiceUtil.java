/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
 * This class provides static methods for the
 * <code>com.liferay.portlet.social.service.SocialRequestLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.social.service.SocialRequestLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.social.service.SocialRequestLocalService
 * @see com.liferay.portlet.social.service.SocialRequestLocalServiceFactory
 *
 */
public class SocialRequestLocalServiceUtil {
	public static com.liferay.portlet.social.model.SocialRequest addSocialRequest(
		com.liferay.portlet.social.model.SocialRequest socialRequest)
		throws com.liferay.portal.SystemException {
		SocialRequestLocalService socialRequestLocalService = SocialRequestLocalServiceFactory.getService();

		return socialRequestLocalService.addSocialRequest(socialRequest);
	}

	public static void deleteSocialRequest(long requestId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SocialRequestLocalService socialRequestLocalService = SocialRequestLocalServiceFactory.getService();

		socialRequestLocalService.deleteSocialRequest(requestId);
	}

	public static void deleteSocialRequest(
		com.liferay.portlet.social.model.SocialRequest socialRequest)
		throws com.liferay.portal.SystemException {
		SocialRequestLocalService socialRequestLocalService = SocialRequestLocalServiceFactory.getService();

		socialRequestLocalService.deleteSocialRequest(socialRequest);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		SocialRequestLocalService socialRequestLocalService = SocialRequestLocalServiceFactory.getService();

		return socialRequestLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException {
		SocialRequestLocalService socialRequestLocalService = SocialRequestLocalServiceFactory.getService();

		return socialRequestLocalService.dynamicQuery(queryInitializer, start,
			end);
	}

	public static com.liferay.portlet.social.model.SocialRequest getSocialRequest(
		long requestId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SocialRequestLocalService socialRequestLocalService = SocialRequestLocalServiceFactory.getService();

		return socialRequestLocalService.getSocialRequest(requestId);
	}

	public static com.liferay.portlet.social.model.SocialRequest updateSocialRequest(
		com.liferay.portlet.social.model.SocialRequest socialRequest)
		throws com.liferay.portal.SystemException {
		SocialRequestLocalService socialRequestLocalService = SocialRequestLocalServiceFactory.getService();

		return socialRequestLocalService.updateSocialRequest(socialRequest);
	}

	public static void init() {
		SocialRequestLocalService socialRequestLocalService = SocialRequestLocalServiceFactory.getService();

		socialRequestLocalService.init();
	}

	public static com.liferay.portlet.social.model.SocialRequest addRequest(
		long userId, long groupId, java.lang.String className, long classPK,
		int type, java.lang.String extraData, long receiverUserId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SocialRequestLocalService socialRequestLocalService = SocialRequestLocalServiceFactory.getService();

		return socialRequestLocalService.addRequest(userId, groupId, className,
			classPK, type, extraData, receiverUserId);
	}

	public static void deleteReceiverUserRequests(long receiverUserId)
		throws com.liferay.portal.SystemException {
		SocialRequestLocalService socialRequestLocalService = SocialRequestLocalServiceFactory.getService();

		socialRequestLocalService.deleteReceiverUserRequests(receiverUserId);
	}

	public static void deleteRequest(long requestId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SocialRequestLocalService socialRequestLocalService = SocialRequestLocalServiceFactory.getService();

		socialRequestLocalService.deleteRequest(requestId);
	}

	public static void deleteUserRequests(long userId)
		throws com.liferay.portal.SystemException {
		SocialRequestLocalService socialRequestLocalService = SocialRequestLocalServiceFactory.getService();

		socialRequestLocalService.deleteUserRequests(userId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> getReceiverUserRequests(
		long receiverUserId, int start, int end)
		throws com.liferay.portal.SystemException {
		SocialRequestLocalService socialRequestLocalService = SocialRequestLocalServiceFactory.getService();

		return socialRequestLocalService.getReceiverUserRequests(receiverUserId,
			start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> getReceiverUserRequests(
		long receiverUserId, int status, int start, int end)
		throws com.liferay.portal.SystemException {
		SocialRequestLocalService socialRequestLocalService = SocialRequestLocalServiceFactory.getService();

		return socialRequestLocalService.getReceiverUserRequests(receiverUserId,
			status, start, end);
	}

	public static int getReceiverUserRequestsCount(long receiverUserId)
		throws com.liferay.portal.SystemException {
		SocialRequestLocalService socialRequestLocalService = SocialRequestLocalServiceFactory.getService();

		return socialRequestLocalService.getReceiverUserRequestsCount(receiverUserId);
	}

	public static int getReceiverUserRequestsCount(long receiverUserId,
		int status) throws com.liferay.portal.SystemException {
		SocialRequestLocalService socialRequestLocalService = SocialRequestLocalServiceFactory.getService();

		return socialRequestLocalService.getReceiverUserRequestsCount(receiverUserId,
			status);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> getUserRequests(
		long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		SocialRequestLocalService socialRequestLocalService = SocialRequestLocalServiceFactory.getService();

		return socialRequestLocalService.getUserRequests(userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRequest> getUserRequests(
		long userId, int status, int start, int end)
		throws com.liferay.portal.SystemException {
		SocialRequestLocalService socialRequestLocalService = SocialRequestLocalServiceFactory.getService();

		return socialRequestLocalService.getUserRequests(userId, status, start,
			end);
	}

	public static int getUserRequestsCount(long userId)
		throws com.liferay.portal.SystemException {
		SocialRequestLocalService socialRequestLocalService = SocialRequestLocalServiceFactory.getService();

		return socialRequestLocalService.getUserRequestsCount(userId);
	}

	public static int getUserRequestsCount(long userId, int status)
		throws com.liferay.portal.SystemException {
		SocialRequestLocalService socialRequestLocalService = SocialRequestLocalServiceFactory.getService();

		return socialRequestLocalService.getUserRequestsCount(userId, status);
	}

	public static boolean hasRequest(long userId, java.lang.String className,
		long classPK, int type, int status)
		throws com.liferay.portal.SystemException {
		SocialRequestLocalService socialRequestLocalService = SocialRequestLocalServiceFactory.getService();

		return socialRequestLocalService.hasRequest(userId, className, classPK,
			type, status);
	}

	public static boolean hasRequest(long userId, java.lang.String className,
		long classPK, int type, long receiverUserId, int status)
		throws com.liferay.portal.SystemException {
		SocialRequestLocalService socialRequestLocalService = SocialRequestLocalServiceFactory.getService();

		return socialRequestLocalService.hasRequest(userId, className, classPK,
			type, receiverUserId, status);
	}

	public static com.liferay.portlet.social.model.SocialRequest updateRequest(
		long requestId, int status,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SocialRequestLocalService socialRequestLocalService = SocialRequestLocalServiceFactory.getService();

		return socialRequestLocalService.updateRequest(requestId, status,
			themeDisplay);
	}
}