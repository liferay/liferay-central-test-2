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


/**
 * <a href="MBMessageServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link MBMessageService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBMessageService
 * @generated
 */
public class MBMessageServiceWrapper implements MBMessageService {
	public MBMessageServiceWrapper(MBMessageService mbMessageService) {
		_mbMessageService = mbMessageService;
	}

	public com.liferay.portlet.messageboards.model.MBMessage addDiscussionMessage(
		java.lang.String className, long classPK, long threadId,
		long parentMessageId, java.lang.String subject, java.lang.String body,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _mbMessageService.addDiscussionMessage(className, classPK,
			threadId, parentMessageId, subject, body, serviceContext);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long groupId, long categoryId, java.lang.String subject,
		java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files,
		boolean anonymous, double priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _mbMessageService.addMessage(groupId, categoryId, subject, body,
			files, anonymous, priority, serviceContext);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long groupId, long categoryId, long threadId, long parentMessageId,
		java.lang.String subject, java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files,
		boolean anonymous, double priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _mbMessageService.addMessage(groupId, categoryId, threadId,
			parentMessageId, subject, body, files, anonymous, priority,
			serviceContext);
	}

	public void deleteDiscussionMessage(long groupId,
		java.lang.String className, long classPK, long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_mbMessageService.deleteDiscussionMessage(groupId, className, classPK,
			messageId);
	}

	public void deleteMessage(long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_mbMessageService.deleteMessage(messageId);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getCategoryMessages(
		long groupId, long categoryId, int status, int start, int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _mbMessageService.getCategoryMessages(groupId, categoryId,
			status, start, end);
	}

	public int getCategoryMessagesCount(long groupId, long categoryId,
		int status) throws com.liferay.portal.SystemException {
		return _mbMessageService.getCategoryMessagesCount(groupId, categoryId,
			status);
	}

	public java.lang.String getCategoryMessagesRSS(long groupId,
		long categoryId, int status, int max, java.lang.String type,
		double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _mbMessageService.getCategoryMessagesRSS(groupId, categoryId,
			status, max, type, version, displayStyle, feedURL, entryURL,
			themeDisplay);
	}

	public java.lang.String getCompanyMessagesRSS(long companyId, int status,
		int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _mbMessageService.getCompanyMessagesRSS(companyId, status, max,
			type, version, displayStyle, feedURL, entryURL, themeDisplay);
	}

	public java.lang.String getGroupMessagesRSS(long groupId, int status,
		int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _mbMessageService.getGroupMessagesRSS(groupId, status, max,
			type, version, displayStyle, feedURL, entryURL, themeDisplay);
	}

	public java.lang.String getGroupMessagesRSS(long groupId, long userId,
		int status, int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _mbMessageService.getGroupMessagesRSS(groupId, userId, status,
			max, type, version, displayStyle, feedURL, entryURL, themeDisplay);
	}

	public com.liferay.portlet.messageboards.model.MBMessage getMessage(
		long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _mbMessageService.getMessage(messageId);
	}

	public com.liferay.portlet.messageboards.model.MBMessageDisplay getMessageDisplay(
		long messageId, int status, java.lang.String threadView)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _mbMessageService.getMessageDisplay(messageId, status, threadView);
	}

	public java.lang.String getThreadMessagesRSS(long threadId, int status,
		int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _mbMessageService.getThreadMessagesRSS(threadId, status, max,
			type, version, displayStyle, feedURL, entryURL, themeDisplay);
	}

	public void subscribeMessage(long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_mbMessageService.subscribeMessage(messageId);
	}

	public void unsubscribeMessage(long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_mbMessageService.unsubscribeMessage(messageId);
	}

	public com.liferay.portlet.messageboards.model.MBMessage updateDiscussionMessage(
		java.lang.String className, long classPK, long messageId,
		java.lang.String subject, java.lang.String body,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _mbMessageService.updateDiscussionMessage(className, classPK,
			messageId, subject, body, serviceContext);
	}

	public com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		long messageId, java.lang.String subject, java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files,
		java.util.List<String> existingFiles, double priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _mbMessageService.updateMessage(messageId, subject, body, files,
			existingFiles, priority, serviceContext);
	}

	public MBMessageService getWrappedMBMessageService() {
		return _mbMessageService;
	}

	private MBMessageService _mbMessageService;
}