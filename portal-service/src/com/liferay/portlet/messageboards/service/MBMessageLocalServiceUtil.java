/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
 * <a href="MBMessageLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link MBMessageLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBMessageLocalService
 * @generated
 */
public class MBMessageLocalServiceUtil {
	public static com.liferay.portlet.messageboards.model.MBMessage addMBMessage(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage)
		throws com.liferay.portal.SystemException {
		return getService().addMBMessage(mbMessage);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage createMBMessage(
		long messageId) {
		return getService().createMBMessage(messageId);
	}

	public static void deleteMBMessage(long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteMBMessage(messageId);
	}

	public static void deleteMBMessage(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage)
		throws com.liferay.portal.SystemException {
		getService().deleteMBMessage(mbMessage);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage getMBMessage(
		long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getMBMessage(messageId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getMBMessages(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getMBMessages(start, end);
	}

	public static int getMBMessagesCount()
		throws com.liferay.portal.SystemException {
		return getService().getMBMessagesCount();
	}

	public static com.liferay.portlet.messageboards.model.MBMessage updateMBMessage(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage)
		throws com.liferay.portal.SystemException {
		return getService().updateMBMessage(mbMessage);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage updateMBMessage(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService().updateMBMessage(mbMessage, merge);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addDiscussionMessage(
		long userId, java.lang.String userName, java.lang.String className,
		long classPK, int status)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addDiscussionMessage(userId, userName, className, classPK,
			status);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addDiscussionMessage(
		long userId, java.lang.String userName, java.lang.String className,
		long classPK, long threadId, long parentMessageId,
		java.lang.String subject, java.lang.String body,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addDiscussionMessage(userId, userName, className, classPK,
			threadId, parentMessageId, subject, body, serviceContext);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long userId, java.lang.String userName, long groupId, long categoryId,
		java.lang.String subject, java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files,
		boolean anonymous, double priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addMessage(userId, userName, groupId, categoryId, subject,
			body, files, anonymous, priority, serviceContext);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long userId, java.lang.String userName, long groupId, long categoryId,
		long threadId, long parentMessageId, java.lang.String subject,
		java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files,
		boolean anonymous, double priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addMessage(userId, userName, groupId, categoryId, threadId,
			parentMessageId, subject, body, files, anonymous, priority,
			serviceContext);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String uuid, long userId, java.lang.String userName,
		long groupId, long categoryId, long threadId, long parentMessageId,
		java.lang.String subject, java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files,
		boolean anonymous, double priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addMessage(uuid, userId, userName, groupId, categoryId,
			threadId, parentMessageId, subject, body, files, anonymous,
			priority, serviceContext);
	}

	public static void addMessageResources(long messageId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addMessageResources(messageId, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addMessageResources(
		com.liferay.portlet.messageboards.model.MBMessage message,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addMessageResources(message, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addMessageResources(long messageId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addMessageResources(messageId, communityPermissions,
			guestPermissions);
	}

	public static void addMessageResources(
		com.liferay.portlet.messageboards.model.MBMessage message,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addMessageResources(message, communityPermissions, guestPermissions);
	}

	public static void deleteDiscussionMessage(long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteDiscussionMessage(messageId);
	}

	public static void deleteDiscussionMessages(java.lang.String className,
		long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteDiscussionMessages(className, classPK);
	}

	public static void deleteMessage(long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteMessage(messageId);
	}

	public static void deleteMessage(
		com.liferay.portlet.messageboards.model.MBMessage message)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteMessage(message);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getCategoryMessages(
		long groupId, long categoryId, int status, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService()
				   .getCategoryMessages(groupId, categoryId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getCategoryMessages(
		long groupId, long categoryId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getService()
				   .getCategoryMessages(groupId, categoryId, status, start,
			end, obc);
	}

	public static int getCategoryMessagesCount(long groupId, long categoryId,
		int status) throws com.liferay.portal.SystemException {
		return getService().getCategoryMessagesCount(groupId, categoryId, status);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getCompanyMessages(
		long companyId, int status, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getCompanyMessages(companyId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getCompanyMessages(
		long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getService()
				   .getCompanyMessages(companyId, status, start, end, obc);
	}

	public static int getCompanyMessagesCount(long companyId, int status)
		throws com.liferay.portal.SystemException {
		return getService().getCompanyMessagesCount(companyId, status);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageDisplay getDiscussionMessageDisplay(
		long userId, java.lang.String className, long classPK, int status)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .getDiscussionMessageDisplay(userId, className, classPK,
			status);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageDisplay getDiscussionMessageDisplay(
		long userId, java.lang.String className, long classPK, int status,
		java.lang.String threadView)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .getDiscussionMessageDisplay(userId, className, classPK,
			status, threadView);
	}

	public static int getDiscussionMessagesCount(long classNameId,
		long classPK, int status) throws com.liferay.portal.SystemException {
		return getService()
				   .getDiscussionMessagesCount(classNameId, classPK, status);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBDiscussion> getDiscussions(
		java.lang.String className) throws com.liferay.portal.SystemException {
		return getService().getDiscussions(className);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getGroupMessages(
		long groupId, int status, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getGroupMessages(groupId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getGroupMessages(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getService().getGroupMessages(groupId, status, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getGroupMessages(
		long groupId, long userId, int status, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getGroupMessages(groupId, userId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getGroupMessages(
		long groupId, long userId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getService()
				   .getGroupMessages(groupId, userId, status, start, end, obc);
	}

	public static int getGroupMessagesCount(long groupId, int status)
		throws com.liferay.portal.SystemException {
		return getService().getGroupMessagesCount(groupId, status);
	}

	public static int getGroupMessagesCount(long groupId, long userId,
		int status) throws com.liferay.portal.SystemException {
		return getService().getGroupMessagesCount(groupId, userId, status);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage getMessage(
		long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getMessage(messageId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getMessages(
		java.lang.String className, long classPK, int status)
		throws com.liferay.portal.SystemException {
		return getService().getMessages(className, classPK, status);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageDisplay getMessageDisplay(
		long messageId, int status, java.lang.String threadView)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getMessageDisplay(messageId, status, threadView);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageDisplay getMessageDisplay(
		com.liferay.portlet.messageboards.model.MBMessage message, int status,
		java.lang.String threadView)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getMessageDisplay(message, status, threadView);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getNoAssetMessages()
		throws com.liferay.portal.SystemException {
		return getService().getNoAssetMessages();
	}

	public static int getPositionInThread(long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getPositionInThread(messageId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getThreadMessages(
		long threadId, int status) throws com.liferay.portal.SystemException {
		return getService().getThreadMessages(threadId, status);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getThreadMessages(
		long threadId, int status,
		java.util.Comparator<com.liferay.portlet.messageboards.model.MBMessage> comparator)
		throws com.liferay.portal.SystemException {
		return getService().getThreadMessages(threadId, status, comparator);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getThreadMessages(
		long threadId, int status, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getThreadMessages(threadId, status, start, end);
	}

	public static int getThreadMessagesCount(long threadId, int status)
		throws com.liferay.portal.SystemException {
		return getService().getThreadMessagesCount(threadId, status);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getThreadRepliesMessages(
		long threadId, int status, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService()
				   .getThreadRepliesMessages(threadId, status, start, end);
	}

	public static void reindex(long messageId)
		throws com.liferay.portal.SystemException {
		getService().reindex(messageId);
	}

	public static void reindex(
		com.liferay.portlet.messageboards.model.MBMessage message)
		throws com.liferay.portal.SystemException {
		getService().reindex(message);
	}

	public static void subscribeMessage(long userId, long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().subscribeMessage(userId, messageId);
	}

	public static void unsubscribeMessage(long userId, long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().unsubscribeMessage(userId, messageId);
	}

	public static void updateAsset(long userId,
		com.liferay.portlet.messageboards.model.MBMessage message,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.updateAsset(userId, message, assetCategoryIds, assetTagNames);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage updateDiscussionMessage(
		long userId, long messageId, java.lang.String subject,
		java.lang.String body, int status)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateDiscussionMessage(userId, messageId, subject, body,
			status);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		long userId, long messageId, java.lang.String subject,
		java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files,
		java.util.List<String> existingFiles, double priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateMessage(userId, messageId, subject, body, files,
			existingFiles, priority, serviceContext);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		long messageId, java.util.Date createDate, java.util.Date modifiedDate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateMessage(messageId, createDate, modifiedDate);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		long messageId, java.lang.String body)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateMessage(messageId, body);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage updateStatus(
		long userId, com.liferay.portlet.messageboards.model.MBMessage message,
		com.liferay.portal.service.ServiceContext serviceContext,
		boolean reindex)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateStatus(userId, message, serviceContext, reindex);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage updateStatus(
		long userId, long messageId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateStatus(userId, messageId, serviceContext);
	}

	public static MBMessageLocalService getService() {
		if (_service == null) {
			_service = (MBMessageLocalService)PortalBeanLocatorUtil.locate(MBMessageLocalService.class.getName());
		}

		return _service;
	}

	public void setService(MBMessageLocalService service) {
		_service = service;
	}

	private static MBMessageLocalService _service;
}