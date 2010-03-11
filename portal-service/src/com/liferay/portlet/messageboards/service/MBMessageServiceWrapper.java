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
		java.lang.String className, long classPK,
		java.lang.String permissionClassName, long permissionClassPK,
		long threadId, long parentMessageId, java.lang.String subject,
		java.lang.String body,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageService.addDiscussionMessage(className, classPK,
			permissionClassName, permissionClassPK, threadId, parentMessageId,
			subject, body, serviceContext);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long groupId, long categoryId, java.lang.String subject,
		java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files,
		boolean anonymous, double priority, boolean allowPingbacks,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageService.addMessage(groupId, categoryId, subject, body,
			files, anonymous, priority, allowPingbacks, serviceContext);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long groupId, long categoryId, long threadId, long parentMessageId,
		java.lang.String subject, java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files,
		boolean anonymous, double priority, boolean allowPingbacks,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageService.addMessage(groupId, categoryId, threadId,
			parentMessageId, subject, body, files, anonymous, priority,
			allowPingbacks, serviceContext);
	}

	public void deleteDiscussionMessage(long groupId,
		java.lang.String className, long classPK,
		java.lang.String permissionClassName, long permissionClassPK,
		long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbMessageService.deleteDiscussionMessage(groupId, className, classPK,
			permissionClassName, permissionClassPK, messageId);
	}

	public void deleteMessage(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbMessageService.deleteMessage(messageId);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getCategoryMessages(
		long groupId, long categoryId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageService.getCategoryMessages(groupId, categoryId,
			status, start, end);
	}

	public int getCategoryMessagesCount(long groupId, long categoryId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageService.getCategoryMessagesCount(groupId, categoryId,
			status);
	}

	public java.lang.String getCategoryMessagesRSS(long groupId,
		long categoryId, int status, int max, java.lang.String type,
		double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageService.getCategoryMessagesRSS(groupId, categoryId,
			status, max, type, version, displayStyle, feedURL, entryURL,
			themeDisplay);
	}

	public java.lang.String getCompanyMessagesRSS(long companyId, int status,
		int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageService.getCompanyMessagesRSS(companyId, status, max,
			type, version, displayStyle, feedURL, entryURL, themeDisplay);
	}

	public java.lang.String getGroupMessagesRSS(long groupId, int status,
		int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageService.getGroupMessagesRSS(groupId, status, max,
			type, version, displayStyle, feedURL, entryURL, themeDisplay);
	}

	public java.lang.String getGroupMessagesRSS(long groupId, long userId,
		int status, int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageService.getGroupMessagesRSS(groupId, userId, status,
			max, type, version, displayStyle, feedURL, entryURL, themeDisplay);
	}

	public com.liferay.portlet.messageboards.model.MBMessage getMessage(
		long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageService.getMessage(messageId);
	}

	public com.liferay.portlet.messageboards.model.MBMessageDisplay getMessageDisplay(
		long messageId, int status, java.lang.String threadView)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageService.getMessageDisplay(messageId, status, threadView);
	}

	public java.lang.String getThreadMessagesRSS(long threadId, int status,
		int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageService.getThreadMessagesRSS(threadId, status, max,
			type, version, displayStyle, feedURL, entryURL, themeDisplay);
	}

	public void subscribeMessage(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbMessageService.subscribeMessage(messageId);
	}

	public void unsubscribeMessage(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbMessageService.unsubscribeMessage(messageId);
	}

	public com.liferay.portlet.messageboards.model.MBMessage updateDiscussionMessage(
		java.lang.String className, long classPK,
		java.lang.String permissionClassName, long permissionClassPK,
		long messageId, java.lang.String subject, java.lang.String body,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageService.updateDiscussionMessage(className, classPK,
			permissionClassName, permissionClassPK, messageId, subject, body,
			serviceContext);
	}

	public com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		long messageId, java.lang.String subject, java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files,
		java.util.List<String> existingFiles, double priority,
		boolean allowPingbacks,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageService.updateMessage(messageId, subject, body, files,
			existingFiles, priority, allowPingbacks, serviceContext);
	}

	public MBMessageService getWrappedMBMessageService() {
		return _mbMessageService;
	}

	private MBMessageService _mbMessageService;
}