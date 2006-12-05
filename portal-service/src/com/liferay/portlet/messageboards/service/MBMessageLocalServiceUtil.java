/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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
 * @author  Brian Wing Shun Chan
 *
 */
public class MBMessageLocalServiceUtil {
	public static com.liferay.portlet.messageboards.model.MBMessage addDiscussionMessage(
		java.lang.String userId, java.lang.String subject, java.lang.String body)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.addDiscussionMessage(userId, subject, body);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addDiscussionMessage(
		java.lang.String userId, java.lang.String threadId,
		java.lang.String parentMessageId, java.lang.String subject,
		java.lang.String body)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.addDiscussionMessage(userId, threadId,
			parentMessageId, subject, body);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String userId, java.lang.String categoryId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		boolean anonymous, double priority,
		javax.portlet.PortletPreferences prefs,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.addMessage(userId, categoryId, subject,
			body, files, anonymous, priority, prefs, addCommunityPermissions,
			addGuestPermissions);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String userId, java.lang.String categoryId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		boolean anonymous, double priority,
		javax.portlet.PortletPreferences prefs,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.addMessage(userId, categoryId, subject,
			body, files, anonymous, priority, prefs, communityPermissions,
			guestPermissions);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String userId, java.lang.String categoryId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		boolean anonymous, double priority,
		javax.portlet.PortletPreferences prefs,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.addMessage(userId, categoryId, subject,
			body, files, anonymous, priority, prefs, addCommunityPermissions,
			addGuestPermissions, communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String userId, java.lang.String categoryId,
		java.lang.String threadId, java.lang.String parentMessageId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		boolean anonymous, double priority,
		javax.portlet.PortletPreferences prefs,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.addMessage(userId, categoryId, threadId,
			parentMessageId, subject, body, files, anonymous, priority, prefs,
			addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String userId, java.lang.String categoryId,
		java.lang.String threadId, java.lang.String parentMessageId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		boolean anonymous, double priority,
		javax.portlet.PortletPreferences prefs,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.addMessage(userId, categoryId, threadId,
			parentMessageId, subject, body, files, anonymous, priority, prefs,
			communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String userId, java.lang.String categoryId,
		java.lang.String threadId, java.lang.String parentMessageId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		boolean anonymous, double priority,
		javax.portlet.PortletPreferences prefs,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.addMessage(userId, categoryId, threadId,
			parentMessageId, subject, body, files, anonymous, priority, prefs,
			addCommunityPermissions, addGuestPermissions, communityPermissions,
			guestPermissions);
	}

	public static void addMessageResources(java.lang.String categoryId,
		java.lang.String messageId, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();
		mbMessageLocalService.addMessageResources(categoryId, messageId,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addMessageResources(java.lang.String categoryId,
		java.lang.String topicId, java.lang.String messageId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();
		mbMessageLocalService.addMessageResources(categoryId, topicId,
			messageId, addCommunityPermissions, addGuestPermissions);
	}

	public static void addMessageResources(
		com.liferay.portlet.messageboards.model.MBCategory category,
		com.liferay.portlet.messageboards.model.MBMessage message,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();
		mbMessageLocalService.addMessageResources(category, message,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addMessageResources(java.lang.String categoryId,
		java.lang.String messageId, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();
		mbMessageLocalService.addMessageResources(categoryId, messageId,
			communityPermissions, guestPermissions);
	}

	public static void addMessageResources(java.lang.String categoryId,
		java.lang.String topicId, java.lang.String messageId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();
		mbMessageLocalService.addMessageResources(categoryId, topicId,
			messageId, communityPermissions, guestPermissions);
	}

	public static void addMessageResources(
		com.liferay.portlet.messageboards.model.MBCategory category,
		com.liferay.portlet.messageboards.model.MBMessage message,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();
		mbMessageLocalService.addMessageResources(category, message,
			communityPermissions, guestPermissions);
	}

	public static void deleteDiscussionMessage(java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();
		mbMessageLocalService.deleteDiscussionMessage(messageId);
	}

	public static void deleteDiscussionMessages(java.lang.String className,
		java.lang.String classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();
		mbMessageLocalService.deleteDiscussionMessages(className, classPK);
	}

	public static void deleteMessage(java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();
		mbMessageLocalService.deleteMessage(messageId);
	}

	public static void deleteMessage(
		com.liferay.portlet.messageboards.model.MBMessage message)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();
		mbMessageLocalService.deleteMessage(message);
	}

	public static java.util.List getCategoryMessages(
		java.lang.String categoryId, int begin, int end)
		throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getCategoryMessages(categoryId, begin, end);
	}

	public static int getCategoryMessagesCount(java.lang.String categoryId)
		throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getCategoryMessagesCount(categoryId);
	}

	public static int getCategoriesMessagesCount(java.util.List categoryIds)
		throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getCategoriesMessagesCount(categoryIds);
	}

	public static java.lang.String getCategoryMessagesRSS(
		java.lang.String categoryId, int begin, int end, java.lang.String type,
		double version, java.lang.String url)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getCategoryMessagesRSS(categoryId, begin,
			end, type, version, url);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageDisplay getDiscussionMessageDisplay(
		java.lang.String userId, java.lang.String className,
		java.lang.String classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getDiscussionMessageDisplay(userId,
			className, classPK);
	}

	public static java.util.List getGroupMessages(java.lang.String groupId,
		int begin, int end) throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getGroupMessages(groupId, begin, end);
	}

	public static int getGroupMessagesCount(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getGroupMessagesCount(groupId);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage getMessage(
		java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getMessage(messageId);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageDisplay getMessageDisplay(
		java.lang.String messageId, java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getMessageDisplay(messageId, userId);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageDisplay getMessageDisplay(
		com.liferay.portlet.messageboards.model.MBMessage message,
		java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getMessageDisplay(message, userId);
	}

	public static java.util.List getThreadMessages(java.lang.String threadId,
		java.lang.String userId) throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getThreadMessages(threadId, userId);
	}

	public static java.util.List getThreadMessages(java.lang.String threadId,
		java.lang.String userId, java.util.Comparator comparator)
		throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getThreadMessages(threadId, userId,
			comparator);
	}

	public static int getThreadMessagesCount(java.lang.String threadId)
		throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getThreadMessagesCount(threadId);
	}

	public static java.lang.String getThreadMessagesRSS(
		java.lang.String threadId, int begin, int end, java.lang.String type,
		double version, java.lang.String url)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getThreadMessagesRSS(threadId, begin, end,
			type, version, url);
	}

	public static void subscribeMessage(java.lang.String userId,
		java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();
		mbMessageLocalService.subscribeMessage(userId, messageId);
	}

	public static void unsubscribeMessage(java.lang.String userId,
		java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();
		mbMessageLocalService.unsubscribeMessage(userId, messageId);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage updateDiscussionMessage(
		java.lang.String messageId, java.lang.String subject,
		java.lang.String body)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.updateDiscussionMessage(messageId,
			subject, body);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		java.lang.String messageId, java.lang.String categoryId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		double priority, javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.updateMessage(messageId, categoryId,
			subject, body, files, priority, prefs);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		java.lang.String messageId, java.util.Date createDate,
		java.util.Date modifiedDate)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.updateMessage(messageId, createDate,
			modifiedDate);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		java.lang.String messageId, java.lang.String body)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.updateMessage(messageId, body);
	}
}