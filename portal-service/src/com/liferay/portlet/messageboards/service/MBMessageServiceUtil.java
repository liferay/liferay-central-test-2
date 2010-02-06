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

package com.liferay.portlet.messageboards.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="MBMessageServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link MBMessageService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBMessageService
 * @generated
 */
public class MBMessageServiceUtil {
	public static com.liferay.portlet.messageboards.model.MBMessage addDiscussionMessage(
		java.lang.String className, long classPK, long threadId,
		long parentMessageId, java.lang.String subject, java.lang.String body,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addDiscussionMessage(className, classPK, threadId,
			parentMessageId, subject, body, serviceContext);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long groupId, long categoryId, java.lang.String subject,
		java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files,
		boolean anonymous, double priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addMessage(groupId, categoryId, subject, body, files,
			anonymous, priority, serviceContext);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long groupId, long categoryId, long threadId, long parentMessageId,
		java.lang.String subject, java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files,
		boolean anonymous, double priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addMessage(groupId, categoryId, threadId, parentMessageId,
			subject, body, files, anonymous, priority, serviceContext);
	}

	public static void deleteDiscussionMessage(long groupId,
		java.lang.String className, long classPK, long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.deleteDiscussionMessage(groupId, className, classPK, messageId);
	}

	public static void deleteMessage(long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteMessage(messageId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getCategoryMessages(
		long groupId, long categoryId, int status, int start, int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .getCategoryMessages(groupId, categoryId, status, start, end);
	}

	public static int getCategoryMessagesCount(long groupId, long categoryId,
		int status) throws com.liferay.portal.SystemException {
		return getService().getCategoryMessagesCount(groupId, categoryId, status);
	}

	public static java.lang.String getCategoryMessagesRSS(long groupId,
		long categoryId, int status, int max, java.lang.String type,
		double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .getCategoryMessagesRSS(groupId, categoryId, status, max,
			type, version, displayStyle, feedURL, entryURL, themeDisplay);
	}

	public static java.lang.String getCompanyMessagesRSS(long companyId,
		int status, int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .getCompanyMessagesRSS(companyId, status, max, type,
			version, displayStyle, feedURL, entryURL, themeDisplay);
	}

	public static java.lang.String getGroupMessagesRSS(long groupId,
		int status, int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .getGroupMessagesRSS(groupId, status, max, type, version,
			displayStyle, feedURL, entryURL, themeDisplay);
	}

	public static java.lang.String getGroupMessagesRSS(long groupId,
		long userId, int status, int max, java.lang.String type,
		double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .getGroupMessagesRSS(groupId, userId, status, max, type,
			version, displayStyle, feedURL, entryURL, themeDisplay);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage getMessage(
		long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getMessage(messageId);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageDisplay getMessageDisplay(
		long messageId, int status, java.lang.String threadView)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getMessageDisplay(messageId, status, threadView);
	}

	public static java.lang.String getThreadMessagesRSS(long threadId,
		int status, int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .getThreadMessagesRSS(threadId, status, max, type, version,
			displayStyle, feedURL, entryURL, themeDisplay);
	}

	public static void subscribeMessage(long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().subscribeMessage(messageId);
	}

	public static void unsubscribeMessage(long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().unsubscribeMessage(messageId);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage updateDiscussionMessage(
		java.lang.String className, long classPK, long messageId,
		java.lang.String subject, java.lang.String body,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateDiscussionMessage(className, classPK, messageId,
			subject, body, serviceContext);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		long messageId, java.lang.String subject, java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files,
		java.util.List<String> existingFiles, double priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateMessage(messageId, subject, body, files,
			existingFiles, priority, serviceContext);
	}

	public static MBMessageService getService() {
		if (_service == null) {
			_service = (MBMessageService)PortalBeanLocatorUtil.locate(MBMessageService.class.getName());
		}

		return _service;
	}

	public void setService(MBMessageService service) {
		_service = service;
	}

	private static MBMessageService _service;
}