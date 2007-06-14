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
		long userId, java.lang.String subject, java.lang.String body)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.messageboards.model.MBMessage addDiscussionMessage(
		long userId, long threadId, long parentMessageId,
		java.lang.String subject, java.lang.String body)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long userId, long categoryId, java.lang.String subject,
		java.lang.String body, java.util.List files, boolean anonymous,
		double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long userId, long categoryId, java.lang.String subject,
		java.lang.String body, java.util.List files, boolean anonymous,
		double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long userId, long categoryId, java.lang.String subject,
		java.lang.String body, java.util.List files, boolean anonymous,
		double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long userId, long categoryId, long threadId, long parentMessageId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		boolean anonymous, double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long userId, long categoryId, long threadId, long parentMessageId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		boolean anonymous, double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long userId, long categoryId, long threadId, long parentMessageId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		boolean anonymous, double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addMessageResources(long categoryId, long messageId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addMessageResources(long categoryId, java.lang.String topicId,
		long messageId, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addMessageResources(
		com.liferay.portlet.messageboards.model.MBCategory category,
		com.liferay.portlet.messageboards.model.MBMessage message,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addMessageResources(long categoryId, long messageId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addMessageResources(long categoryId, java.lang.String topicId,
		long messageId, java.lang.String[] communityPermissions,
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

	public void deleteDiscussionMessage(long messageId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void deleteDiscussionMessages(java.lang.String className,
		long classPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void deleteMessage(long messageId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void deleteMessage(
		com.liferay.portlet.messageboards.model.MBMessage message)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.List getCategoryMessages(long categoryId, int begin,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List getCategoryMessages(long categoryId, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public int getCategoryMessagesCount(long categoryId)
		throws com.liferay.portal.SystemException;

	public int getCategoriesMessagesCount(java.util.List categoryIds)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessageDisplay getDiscussionMessageDisplay(
		long userId, java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.List getGroupMessages(long groupId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public int getGroupMessagesCount(long groupId)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage getMessage(
		long messageId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.messageboards.model.MBMessageDisplay getMessageDisplay(
		long userId, long messageId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.messageboards.model.MBMessageDisplay getMessageDisplay(
		long userId, com.liferay.portlet.messageboards.model.MBMessage message)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.List getThreadMessages(long threadId)
		throws com.liferay.portal.SystemException;

	public java.util.List getThreadMessages(long threadId,
		java.util.Comparator comparator)
		throws com.liferay.portal.SystemException;

	public int getThreadMessagesCount(long threadId)
		throws com.liferay.portal.SystemException;

	public void subscribeMessage(long userId, long messageId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void unsubscribeMessage(long userId, long messageId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.messageboards.model.MBMessage updateDiscussionMessage(
		long messageId, java.lang.String subject, java.lang.String body)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		long messageId, long categoryId, java.lang.String subject,
		java.lang.String body, java.util.List files, double priority,
		java.lang.String[] tagsEntries, javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		long messageId, java.util.Date createDate, java.util.Date modifiedDate)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		long messageId, java.lang.String body)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;
}