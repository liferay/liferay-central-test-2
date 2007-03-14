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

package com.liferay.portlet.messageboards.service.ejb;

import com.liferay.portlet.messageboards.service.MBMessageLocalService;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="MBMessageLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBMessageLocalServiceEJBImpl implements MBMessageLocalService,
	SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return MBMessageLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return MBMessageLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addDiscussionMessage(
		java.lang.String userId, java.lang.String subject, java.lang.String body)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return MBMessageLocalServiceFactory.getTxImpl().addDiscussionMessage(userId,
			subject, body);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addDiscussionMessage(
		java.lang.String userId, java.lang.String threadId,
		java.lang.String parentMessageId, java.lang.String subject,
		java.lang.String body)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return MBMessageLocalServiceFactory.getTxImpl().addDiscussionMessage(userId,
			threadId, parentMessageId, subject, body);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String userId, java.lang.String categoryId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		boolean anonymous, double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return MBMessageLocalServiceFactory.getTxImpl().addMessage(userId,
			categoryId, subject, body, files, anonymous, priority, tagsEntries,
			prefs, addCommunityPermissions, addGuestPermissions);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String userId, java.lang.String categoryId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		boolean anonymous, double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return MBMessageLocalServiceFactory.getTxImpl().addMessage(userId,
			categoryId, subject, body, files, anonymous, priority, tagsEntries,
			prefs, communityPermissions, guestPermissions);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String userId, java.lang.String categoryId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		boolean anonymous, double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return MBMessageLocalServiceFactory.getTxImpl().addMessage(userId,
			categoryId, subject, body, files, anonymous, priority, tagsEntries,
			prefs, addCommunityPermissions, addGuestPermissions,
			communityPermissions, guestPermissions);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String userId, java.lang.String categoryId,
		java.lang.String threadId, java.lang.String parentMessageId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		boolean anonymous, double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return MBMessageLocalServiceFactory.getTxImpl().addMessage(userId,
			categoryId, threadId, parentMessageId, subject, body, files,
			anonymous, priority, tagsEntries, prefs, addCommunityPermissions,
			addGuestPermissions);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String userId, java.lang.String categoryId,
		java.lang.String threadId, java.lang.String parentMessageId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		boolean anonymous, double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return MBMessageLocalServiceFactory.getTxImpl().addMessage(userId,
			categoryId, threadId, parentMessageId, subject, body, files,
			anonymous, priority, tagsEntries, prefs, communityPermissions,
			guestPermissions);
	}

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
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return MBMessageLocalServiceFactory.getTxImpl().addMessage(userId,
			categoryId, threadId, parentMessageId, subject, body, files,
			anonymous, priority, tagsEntries, prefs, addCommunityPermissions,
			addGuestPermissions, communityPermissions, guestPermissions);
	}

	public void addMessageResources(java.lang.String categoryId,
		java.lang.String messageId, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalServiceFactory.getTxImpl().addMessageResources(categoryId,
			messageId, addCommunityPermissions, addGuestPermissions);
	}

	public void addMessageResources(java.lang.String categoryId,
		java.lang.String topicId, java.lang.String messageId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalServiceFactory.getTxImpl().addMessageResources(categoryId,
			topicId, messageId, addCommunityPermissions, addGuestPermissions);
	}

	public void addMessageResources(
		com.liferay.portlet.messageboards.model.MBCategory category,
		com.liferay.portlet.messageboards.model.MBMessage message,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalServiceFactory.getTxImpl().addMessageResources(category,
			message, addCommunityPermissions, addGuestPermissions);
	}

	public void addMessageResources(java.lang.String categoryId,
		java.lang.String messageId, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalServiceFactory.getTxImpl().addMessageResources(categoryId,
			messageId, communityPermissions, guestPermissions);
	}

	public void addMessageResources(java.lang.String categoryId,
		java.lang.String topicId, java.lang.String messageId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalServiceFactory.getTxImpl().addMessageResources(categoryId,
			topicId, messageId, communityPermissions, guestPermissions);
	}

	public void addMessageResources(
		com.liferay.portlet.messageboards.model.MBCategory category,
		com.liferay.portlet.messageboards.model.MBMessage message,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalServiceFactory.getTxImpl().addMessageResources(category,
			message, communityPermissions, guestPermissions);
	}

	public void deleteDiscussionMessage(java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalServiceFactory.getTxImpl().deleteDiscussionMessage(messageId);
	}

	public void deleteDiscussionMessages(java.lang.String className,
		java.lang.String classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalServiceFactory.getTxImpl().deleteDiscussionMessages(className,
			classPK);
	}

	public void deleteMessage(java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalServiceFactory.getTxImpl().deleteMessage(messageId);
	}

	public void deleteMessage(
		com.liferay.portlet.messageboards.model.MBMessage message)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalServiceFactory.getTxImpl().deleteMessage(message);
	}

	public java.util.List getCategoryMessages(java.lang.String categoryId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return MBMessageLocalServiceFactory.getTxImpl().getCategoryMessages(categoryId,
			begin, end);
	}

	public int getCategoryMessagesCount(java.lang.String categoryId)
		throws com.liferay.portal.SystemException {
		return MBMessageLocalServiceFactory.getTxImpl()
										   .getCategoryMessagesCount(categoryId);
	}

	public int getCategoriesMessagesCount(java.util.List categoryIds)
		throws com.liferay.portal.SystemException {
		return MBMessageLocalServiceFactory.getTxImpl()
										   .getCategoriesMessagesCount(categoryIds);
	}

	public java.lang.String getCategoryMessagesRSS(
		java.lang.String categoryId, int begin, int end, java.lang.String type,
		double version, java.lang.String feedURL, java.lang.String entryURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return MBMessageLocalServiceFactory.getTxImpl().getCategoryMessagesRSS(categoryId,
			begin, end, type, version, feedURL, entryURL);
	}

	public com.liferay.portlet.messageboards.model.MBMessageDisplay getDiscussionMessageDisplay(
		java.lang.String userId, java.lang.String className,
		java.lang.String classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return MBMessageLocalServiceFactory.getTxImpl()
										   .getDiscussionMessageDisplay(userId,
			className, classPK);
	}

	public java.util.List getGroupMessages(long groupId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return MBMessageLocalServiceFactory.getTxImpl().getGroupMessages(groupId,
			begin, end);
	}

	public int getGroupMessagesCount(long groupId)
		throws com.liferay.portal.SystemException {
		return MBMessageLocalServiceFactory.getTxImpl().getGroupMessagesCount(groupId);
	}

	public com.liferay.portlet.messageboards.model.MBMessage getMessage(
		java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return MBMessageLocalServiceFactory.getTxImpl().getMessage(messageId);
	}

	public com.liferay.portlet.messageboards.model.MBMessageDisplay getMessageDisplay(
		java.lang.String messageId, java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return MBMessageLocalServiceFactory.getTxImpl().getMessageDisplay(messageId,
			userId);
	}

	public com.liferay.portlet.messageboards.model.MBMessageDisplay getMessageDisplay(
		com.liferay.portlet.messageboards.model.MBMessage message,
		java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return MBMessageLocalServiceFactory.getTxImpl().getMessageDisplay(message,
			userId);
	}

	public java.util.List getThreadMessages(java.lang.String threadId,
		java.lang.String userId) throws com.liferay.portal.SystemException {
		return MBMessageLocalServiceFactory.getTxImpl().getThreadMessages(threadId,
			userId);
	}

	public java.util.List getThreadMessages(java.lang.String threadId,
		java.lang.String userId, java.util.Comparator comparator)
		throws com.liferay.portal.SystemException {
		return MBMessageLocalServiceFactory.getTxImpl().getThreadMessages(threadId,
			userId, comparator);
	}

	public int getThreadMessagesCount(java.lang.String threadId)
		throws com.liferay.portal.SystemException {
		return MBMessageLocalServiceFactory.getTxImpl().getThreadMessagesCount(threadId);
	}

	public java.lang.String getThreadMessagesRSS(java.lang.String threadId,
		int begin, int end, java.lang.String type, double version,
		java.lang.String feedURL, java.lang.String entryURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return MBMessageLocalServiceFactory.getTxImpl().getThreadMessagesRSS(threadId,
			begin, end, type, version, feedURL, entryURL);
	}

	public void subscribeMessage(java.lang.String userId,
		java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalServiceFactory.getTxImpl().subscribeMessage(userId,
			messageId);
	}

	public void unsubscribeMessage(java.lang.String userId,
		java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBMessageLocalServiceFactory.getTxImpl().unsubscribeMessage(userId,
			messageId);
	}

	public com.liferay.portlet.messageboards.model.MBMessage updateDiscussionMessage(
		java.lang.String messageId, java.lang.String subject,
		java.lang.String body)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return MBMessageLocalServiceFactory.getTxImpl().updateDiscussionMessage(messageId,
			subject, body);
	}

	public com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		java.lang.String messageId, java.lang.String categoryId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return MBMessageLocalServiceFactory.getTxImpl().updateMessage(messageId,
			categoryId, subject, body, files, priority, tagsEntries, prefs);
	}

	public com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		java.lang.String messageId, java.util.Date createDate,
		java.util.Date modifiedDate)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return MBMessageLocalServiceFactory.getTxImpl().updateMessage(messageId,
			createDate, modifiedDate);
	}

	public com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		java.lang.String messageId, java.lang.String body)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return MBMessageLocalServiceFactory.getTxImpl().updateMessage(messageId,
			body);
	}

	public void ejbCreate() throws CreateException {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public SessionContext getSessionContext() {
		return _sc;
	}

	public void setSessionContext(SessionContext sc) {
		_sc = sc;
	}

	private SessionContext _sc;
}