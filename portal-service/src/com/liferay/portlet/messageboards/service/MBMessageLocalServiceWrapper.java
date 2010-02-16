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
 * <a href="MBMessageLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link MBMessageLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBMessageLocalService
 * @generated
 */
public class MBMessageLocalServiceWrapper implements MBMessageLocalService {
	public MBMessageLocalServiceWrapper(
		MBMessageLocalService mbMessageLocalService) {
		_mbMessageLocalService = mbMessageLocalService;
	}

	public com.liferay.portlet.messageboards.model.MBMessage addMBMessage(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.addMBMessage(mbMessage);
	}

	public com.liferay.portlet.messageboards.model.MBMessage createMBMessage(
		long messageId) {
		return _mbMessageLocalService.createMBMessage(messageId);
	}

	public void deleteMBMessage(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbMessageLocalService.deleteMBMessage(messageId);
	}

	public void deleteMBMessage(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbMessageLocalService.deleteMBMessage(mbMessage);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.messageboards.model.MBMessage getMBMessage(
		long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getMBMessage(messageId);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getMBMessages(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getMBMessages(start, end);
	}

	public int getMBMessagesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getMBMessagesCount();
	}

	public com.liferay.portlet.messageboards.model.MBMessage updateMBMessage(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.updateMBMessage(mbMessage);
	}

	public com.liferay.portlet.messageboards.model.MBMessage updateMBMessage(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.updateMBMessage(mbMessage, merge);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addDiscussionMessage(
		long userId, java.lang.String userName, java.lang.String className,
		long classPK, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.addDiscussionMessage(userId, userName,
			className, classPK, status);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addDiscussionMessage(
		long userId, java.lang.String userName, java.lang.String className,
		long classPK, long threadId, long parentMessageId,
		java.lang.String subject, java.lang.String body,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.addDiscussionMessage(userId, userName,
			className, classPK, threadId, parentMessageId, subject, body,
			serviceContext);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long userId, java.lang.String userName, long groupId, long categoryId,
		java.lang.String subject, java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files,
		boolean anonymous, double priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.addMessage(userId, userName, groupId,
			categoryId, subject, body, files, anonymous, priority,
			serviceContext);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long userId, java.lang.String userName, long groupId, long categoryId,
		long threadId, long parentMessageId, java.lang.String subject,
		java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files,
		boolean anonymous, double priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.addMessage(userId, userName, groupId,
			categoryId, threadId, parentMessageId, subject, body, files,
			anonymous, priority, serviceContext);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String uuid, long userId, java.lang.String userName,
		long groupId, long categoryId, long threadId, long parentMessageId,
		java.lang.String subject, java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files,
		boolean anonymous, double priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.addMessage(uuid, userId, userName,
			groupId, categoryId, threadId, parentMessageId, subject, body,
			files, anonymous, priority, serviceContext);
	}

	public void addMessageResources(long messageId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbMessageLocalService.addMessageResources(messageId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addMessageResources(
		com.liferay.portlet.messageboards.model.MBMessage message,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbMessageLocalService.addMessageResources(message,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addMessageResources(long messageId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbMessageLocalService.addMessageResources(messageId,
			communityPermissions, guestPermissions);
	}

	public void addMessageResources(
		com.liferay.portlet.messageboards.model.MBMessage message,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbMessageLocalService.addMessageResources(message,
			communityPermissions, guestPermissions);
	}

	public void deleteDiscussionMessage(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbMessageLocalService.deleteDiscussionMessage(messageId);
	}

	public void deleteDiscussionMessages(java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbMessageLocalService.deleteDiscussionMessages(className, classPK);
	}

	public void deleteMessage(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbMessageLocalService.deleteMessage(messageId);
	}

	public void deleteMessage(
		com.liferay.portlet.messageboards.model.MBMessage message)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbMessageLocalService.deleteMessage(message);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getCategoryMessages(
		long groupId, long categoryId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getCategoryMessages(groupId, categoryId,
			status, start, end);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getCategoryMessages(
		long groupId, long categoryId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getCategoryMessages(groupId, categoryId,
			status, start, end, obc);
	}

	public int getCategoryMessagesCount(long groupId, long categoryId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getCategoryMessagesCount(groupId,
			categoryId, status);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getCompanyMessages(
		long companyId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getCompanyMessages(companyId, status,
			start, end);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getCompanyMessages(
		long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getCompanyMessages(companyId, status,
			start, end, obc);
	}

	public int getCompanyMessagesCount(long companyId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getCompanyMessagesCount(companyId, status);
	}

	public com.liferay.portlet.messageboards.model.MBMessageDisplay getDiscussionMessageDisplay(
		long userId, java.lang.String className, long classPK, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getDiscussionMessageDisplay(userId,
			className, classPK, status);
	}

	public com.liferay.portlet.messageboards.model.MBMessageDisplay getDiscussionMessageDisplay(
		long userId, java.lang.String className, long classPK, int status,
		java.lang.String threadView)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getDiscussionMessageDisplay(userId,
			className, classPK, status, threadView);
	}

	public int getDiscussionMessagesCount(long classNameId, long classPK,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getDiscussionMessagesCount(classNameId,
			classPK, status);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBDiscussion> getDiscussions(
		java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getDiscussions(className);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getGroupMessages(
		long groupId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getGroupMessages(groupId, status, start,
			end);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getGroupMessages(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getGroupMessages(groupId, status, start,
			end, obc);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getGroupMessages(
		long groupId, long userId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getGroupMessages(groupId, userId, status,
			start, end);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getGroupMessages(
		long groupId, long userId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getGroupMessages(groupId, userId, status,
			start, end, obc);
	}

	public int getGroupMessagesCount(long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getGroupMessagesCount(groupId, status);
	}

	public int getGroupMessagesCount(long groupId, long userId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getGroupMessagesCount(groupId, userId,
			status);
	}

	public com.liferay.portlet.messageboards.model.MBMessage getMessage(
		long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getMessage(messageId);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getMessages(
		java.lang.String className, long classPK, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getMessages(className, classPK, status);
	}

	public com.liferay.portlet.messageboards.model.MBMessageDisplay getMessageDisplay(
		long messageId, int status, java.lang.String threadView)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getMessageDisplay(messageId, status,
			threadView);
	}

	public com.liferay.portlet.messageboards.model.MBMessageDisplay getMessageDisplay(
		com.liferay.portlet.messageboards.model.MBMessage message, int status,
		java.lang.String threadView)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getMessageDisplay(message, status,
			threadView);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getNoAssetMessages()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getNoAssetMessages();
	}

	public int getPositionInThread(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getPositionInThread(messageId);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getThreadMessages(
		long threadId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getThreadMessages(threadId, status);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getThreadMessages(
		long threadId, int status,
		java.util.Comparator<com.liferay.portlet.messageboards.model.MBMessage> comparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getThreadMessages(threadId, status,
			comparator);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getThreadMessages(
		long threadId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getThreadMessages(threadId, status,
			start, end);
	}

	public int getThreadMessagesCount(long threadId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getThreadMessagesCount(threadId, status);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getThreadRepliesMessages(
		long threadId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.getThreadRepliesMessages(threadId,
			status, start, end);
	}

	public void subscribeMessage(long userId, long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbMessageLocalService.subscribeMessage(userId, messageId);
	}

	public void unsubscribeMessage(long userId, long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbMessageLocalService.unsubscribeMessage(userId, messageId);
	}

	public void updateAsset(long userId,
		com.liferay.portlet.messageboards.model.MBMessage message,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbMessageLocalService.updateAsset(userId, message, assetCategoryIds,
			assetTagNames);
	}

	public com.liferay.portlet.messageboards.model.MBMessage updateDiscussionMessage(
		long userId, long messageId, java.lang.String subject,
		java.lang.String body, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.updateDiscussionMessage(userId,
			messageId, subject, body, status);
	}

	public com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		long userId, long messageId, java.lang.String subject,
		java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files,
		java.util.List<String> existingFiles, double priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.updateMessage(userId, messageId, subject,
			body, files, existingFiles, priority, serviceContext);
	}

	public com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		long messageId, java.util.Date createDate, java.util.Date modifiedDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.updateMessage(messageId, createDate,
			modifiedDate);
	}

	public com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		long messageId, java.lang.String body)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.updateMessage(messageId, body);
	}

	public com.liferay.portlet.messageboards.model.MBMessage updateStatus(
		long userId, com.liferay.portlet.messageboards.model.MBMessage message,
		com.liferay.portal.service.ServiceContext serviceContext,
		boolean reindex)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.updateStatus(userId, message,
			serviceContext, reindex);
	}

	public com.liferay.portlet.messageboards.model.MBMessage updateStatus(
		long userId, long messageId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageLocalService.updateStatus(userId, messageId,
			serviceContext);
	}

	public MBMessageLocalService getWrappedMBMessageLocalService() {
		return _mbMessageLocalService;
	}

	private MBMessageLocalService _mbMessageLocalService;
}