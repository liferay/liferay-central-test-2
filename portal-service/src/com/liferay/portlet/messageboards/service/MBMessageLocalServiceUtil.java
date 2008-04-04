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
 * This class provides static methods for the
 * <code>com.liferay.portlet.messageboards.service.MBMessageLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.messageboards.service.MBMessageLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.messageboards.service.MBMessageLocalService
 * @see com.liferay.portlet.messageboards.service.MBMessageLocalServiceFactory
 *
 */
public class MBMessageLocalServiceUtil {
	public static com.liferay.portlet.messageboards.model.MBMessage addMBMessage(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage)
		throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.addMBMessage(mbMessage);
	}

	public static void deleteMBMessage(long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		mbMessageLocalService.deleteMBMessage(messageId);
	}

	public static void deleteMBMessage(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		mbMessageLocalService.deleteMBMessage(mbMessage);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.dynamicQuery(queryInitializer, begin, end);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage updateMBMessage(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage)
		throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.updateMBMessage(mbMessage);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addDiscussionMessage(
		long userId, java.lang.String subject, java.lang.String body)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.addDiscussionMessage(userId, subject, body);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addDiscussionMessage(
		long userId, long groupId, java.lang.String className, long classPK,
		long threadId, long parentMessageId, java.lang.String subject,
		java.lang.String body)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.addDiscussionMessage(userId, groupId,
			className, classPK, threadId, parentMessageId, subject, body);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addDiscussionMessage(
		long userId, long groupId, java.lang.String className, long classPK,
		long threadId, long parentMessageId, java.lang.String subject,
		java.lang.String body,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.addDiscussionMessage(userId, groupId,
			className, classPK, threadId, parentMessageId, subject, body,
			themeDisplay);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long userId, long categoryId, java.lang.String subject,
		java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files,
		boolean anonymous, double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		boolean addCommunityPermissions, boolean addGuestPermissions,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.addMessage(userId, categoryId, subject,
			body, files, anonymous, priority, tagsEntries, prefs,
			addCommunityPermissions, addGuestPermissions, themeDisplay);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long userId, long categoryId, java.lang.String subject,
		java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files,
		boolean anonymous, double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.addMessage(userId, categoryId, subject,
			body, files, anonymous, priority, tagsEntries, prefs,
			communityPermissions, guestPermissions, themeDisplay);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long userId, long categoryId, java.lang.String subject,
		java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files,
		boolean anonymous, double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.addMessage(userId, categoryId, subject,
			body, files, anonymous, priority, tagsEntries, prefs,
			addCommunityPermissions, addGuestPermissions, communityPermissions,
			guestPermissions, themeDisplay);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long userId, long categoryId, long threadId, long parentMessageId,
		java.lang.String subject, java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files,
		boolean anonymous, double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		boolean addCommunityPermissions, boolean addGuestPermissions,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.addMessage(userId, categoryId, threadId,
			parentMessageId, subject, body, files, anonymous, priority,
			tagsEntries, prefs, addCommunityPermissions, addGuestPermissions,
			themeDisplay);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long userId, long categoryId, long threadId, long parentMessageId,
		java.lang.String subject, java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files,
		boolean anonymous, double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.addMessage(userId, categoryId, threadId,
			parentMessageId, subject, body, files, anonymous, priority,
			tagsEntries, prefs, communityPermissions, guestPermissions,
			themeDisplay);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String uuid, long userId, long categoryId, long threadId,
		long parentMessageId, java.lang.String subject, java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files,
		boolean anonymous, double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		boolean addCommunityPermissions, boolean addGuestPermissions,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.addMessage(uuid, userId, categoryId,
			threadId, parentMessageId, subject, body, files, anonymous,
			priority, tagsEntries, prefs, addCommunityPermissions,
			addGuestPermissions, themeDisplay);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String uuid, long userId, long categoryId, long threadId,
		long parentMessageId, java.lang.String subject, java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files,
		boolean anonymous, double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.addMessage(uuid, userId, categoryId,
			threadId, parentMessageId, subject, body, files, anonymous,
			priority, tagsEntries, prefs, addCommunityPermissions,
			addGuestPermissions, communityPermissions, guestPermissions,
			themeDisplay);
	}

	public static void addMessageResources(long categoryId, long messageId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		mbMessageLocalService.addMessageResources(categoryId, messageId,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addMessageResources(long categoryId,
		java.lang.String topicId, long messageId,
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

	public static void addMessageResources(long categoryId, long messageId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		mbMessageLocalService.addMessageResources(categoryId, messageId,
			communityPermissions, guestPermissions);
	}

	public static void addMessageResources(long categoryId,
		java.lang.String topicId, long messageId,
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

	public static void deleteDiscussionMessage(long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		mbMessageLocalService.deleteDiscussionMessage(messageId);
	}

	public static void deleteDiscussionMessages(java.lang.String className,
		long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		mbMessageLocalService.deleteDiscussionMessages(className, classPK);
	}

	public static void deleteMessage(long messageId)
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

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getCategoryMessages(
		long categoryId, int begin, int end)
		throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getCategoryMessages(categoryId, begin, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getCategoryMessages(
		long categoryId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getCategoryMessages(categoryId, begin,
			end, obc);
	}

	public static int getCategoryMessagesCount(long categoryId)
		throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getCategoryMessagesCount(categoryId);
	}

	public static int getCategoriesMessagesCount(
		java.util.List<Long> categoryIds)
		throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getCategoriesMessagesCount(categoryIds);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getCompanyMessages(
		long companyId, int begin, int end)
		throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getCompanyMessages(companyId, begin, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getCompanyMessages(
		long companyId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getCompanyMessages(companyId, begin, end,
			obc);
	}

	public static int getCompanyMessagesCount(long companyId)
		throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getCompanyMessagesCount(companyId);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageDisplay getDiscussionMessageDisplay(
		long userId, java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getDiscussionMessageDisplay(userId,
			className, classPK);
	}

	public static int getDiscussionMessagesCount(long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getDiscussionMessagesCount(classNameId,
			classPK);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getGroupMessages(
		long groupId, int begin, int end)
		throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getGroupMessages(groupId, begin, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getGroupMessages(
		long groupId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getGroupMessages(groupId, begin, end, obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getGroupMessages(
		long groupId, long userId, int begin, int end)
		throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getGroupMessages(groupId, userId, begin,
			end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getGroupMessages(
		long groupId, long userId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getGroupMessages(groupId, userId, begin,
			end, obc);
	}

	public static int getGroupMessagesCount(long groupId)
		throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getGroupMessagesCount(groupId);
	}

	public static int getGroupMessagesCount(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getGroupMessagesCount(groupId, userId);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage getMessage(
		long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getMessage(messageId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getMessages(
		java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getMessages(className, classPK);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageDisplay getMessageDisplay(
		long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getMessageDisplay(messageId);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageDisplay getMessageDisplay(
		com.liferay.portlet.messageboards.model.MBMessage message)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getMessageDisplay(message);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getNoAssetMessages()
		throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getNoAssetMessages();
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getThreadMessages(
		long threadId) throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getThreadMessages(threadId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getThreadMessages(
		long threadId,
		java.util.Comparator<com.liferay.portlet.messageboards.model.MBMessage> comparator)
		throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getThreadMessages(threadId, comparator);
	}

	public static int getThreadMessagesCount(long threadId)
		throws com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.getThreadMessagesCount(threadId);
	}

	public static void subscribeMessage(long userId, long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		mbMessageLocalService.subscribeMessage(userId, messageId);
	}

	public static void unsubscribeMessage(long userId, long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		mbMessageLocalService.unsubscribeMessage(userId, messageId);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage updateDiscussionMessage(
		long userId, long messageId, java.lang.String subject,
		java.lang.String body)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.updateDiscussionMessage(userId, messageId,
			subject, body);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		long userId, long messageId, java.lang.String subject,
		java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files,
		java.util.List<String> existingFiles, double priority,
		java.lang.String[] tagsEntries, javax.portlet.PortletPreferences prefs,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.updateMessage(userId, messageId, subject,
			body, files, existingFiles, priority, tagsEntries, prefs,
			themeDisplay);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		long messageId, java.util.Date createDate, java.util.Date modifiedDate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.updateMessage(messageId, createDate,
			modifiedDate);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		long messageId, java.lang.String body)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		return mbMessageLocalService.updateMessage(messageId, body);
	}

	public static void updateTagsAsset(long userId,
		com.liferay.portlet.messageboards.model.MBMessage message,
		java.lang.String[] tagsEntries)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBMessageLocalService mbMessageLocalService = MBMessageLocalServiceFactory.getService();

		mbMessageLocalService.updateTagsAsset(userId, message, tagsEntries);
	}
}