/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
 * <a href="MBMessageLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is <code>com.liferay.portlet.messageboards.service.impl.MBMessageLocalServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be accessed
 * from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.messageboards.service.MBMessageServiceFactory
 * @see com.liferay.portlet.messageboards.service.MBMessageServiceUtil
 *
 */
public interface MBMessageLocalService {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage addDiscussionMessage(
		java.lang.String userId, java.lang.String subject, java.lang.String body)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.messageboards.model.MBMessage addDiscussionMessage(
		java.lang.String userId, java.lang.String threadId,
		java.lang.String parentMessageId, java.lang.String subject,
		java.lang.String body)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String userId, java.lang.String categoryId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		boolean anonymous, double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String userId, java.lang.String categoryId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		boolean anonymous, double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String userId, java.lang.String categoryId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		boolean anonymous, double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String userId, java.lang.String categoryId,
		java.lang.String threadId, java.lang.String parentMessageId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		boolean anonymous, double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String userId, java.lang.String categoryId,
		java.lang.String threadId, java.lang.String parentMessageId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		boolean anonymous, double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String userId, java.lang.String categoryId,
		java.lang.String threadId, java.lang.String parentMessageId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		boolean anonymous, double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addMessageResources(java.lang.String categoryId,
		java.lang.String messageId, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addMessageResources(java.lang.String categoryId,
		java.lang.String topicId, java.lang.String messageId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addMessageResources(
		com.liferay.portlet.messageboards.model.MBCategory category,
		com.liferay.portlet.messageboards.model.MBMessage message,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addMessageResources(java.lang.String categoryId,
		java.lang.String messageId, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addMessageResources(java.lang.String categoryId,
		java.lang.String topicId, java.lang.String messageId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addMessageResources(
		com.liferay.portlet.messageboards.model.MBCategory category,
		com.liferay.portlet.messageboards.model.MBMessage message,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void deleteDiscussionMessage(java.lang.String messageId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void deleteDiscussionMessages(java.lang.String className,
		java.lang.String classPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void deleteMessage(java.lang.String messageId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void deleteMessage(
		com.liferay.portlet.messageboards.model.MBMessage message)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.List getCategoryMessages(java.lang.String categoryId,
		int begin, int end) throws com.liferay.portal.SystemException;

	public int getCategoryMessagesCount(java.lang.String categoryId)
		throws com.liferay.portal.SystemException;

	public int getCategoriesMessagesCount(java.util.List categoryIds)
		throws com.liferay.portal.SystemException;

	public java.lang.String getCategoryMessagesRSS(
		java.lang.String categoryId, int begin, int end, java.lang.String type,
		double version, java.lang.String feedURL, java.lang.String entryURL)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.messageboards.model.MBMessageDisplay getDiscussionMessageDisplay(
		java.lang.String userId, java.lang.String className,
		java.lang.String classPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.List getGroupMessages(long groupId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public int getGroupMessagesCount(long groupId)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage getMessage(
		java.lang.String messageId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.messageboards.model.MBMessageDisplay getMessageDisplay(
		java.lang.String messageId, java.lang.String userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.messageboards.model.MBMessageDisplay getMessageDisplay(
		com.liferay.portlet.messageboards.model.MBMessage message,
		java.lang.String userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.List getThreadMessages(java.lang.String threadId,
		java.lang.String userId) throws com.liferay.portal.SystemException;

	public java.util.List getThreadMessages(java.lang.String threadId,
		java.lang.String userId, java.util.Comparator comparator)
		throws com.liferay.portal.SystemException;

	public int getThreadMessagesCount(java.lang.String threadId)
		throws com.liferay.portal.SystemException;

	public java.lang.String getThreadMessagesRSS(java.lang.String threadId,
		int begin, int end, java.lang.String type, double version,
		java.lang.String feedURL, java.lang.String entryURL)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void subscribeMessage(java.lang.String userId,
		java.lang.String messageId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void unsubscribeMessage(java.lang.String userId,
		java.lang.String messageId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.messageboards.model.MBMessage updateDiscussionMessage(
		java.lang.String messageId, java.lang.String subject,
		java.lang.String body)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		java.lang.String messageId, java.lang.String categoryId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		java.lang.String messageId, java.util.Date createDate,
		java.util.Date modifiedDate)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		java.lang.String messageId, java.lang.String body)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;
}