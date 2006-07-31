/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.portal.spring.util.SpringUtil;

import com.liferay.portlet.messageboards.service.spring.MBMessageLocalService;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="MBMessageLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBMessageLocalServiceEJBImpl implements MBMessageLocalService,
	SessionBean {
	public static final String CLASS_NAME = MBMessageLocalService.class.getName() +
		".transaction";

	public static MBMessageLocalService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (MBMessageLocalService)ctx.getBean(CLASS_NAME);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addDiscussionMessage(
		java.lang.String userId, java.lang.String subject, java.lang.String body)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().addDiscussionMessage(userId, subject, body);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addDiscussionMessage(
		java.lang.String userId, java.lang.String threadId,
		java.lang.String parentMessageId, java.lang.String subject,
		java.lang.String body)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().addDiscussionMessage(userId, threadId,
			parentMessageId, subject, body);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String userId, java.lang.String categoryId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		boolean anonymous, javax.portlet.PortletPreferences prefs,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().addMessage(userId, categoryId, subject, body,
			files, anonymous, prefs, addCommunityPermissions,
			addGuestPermissions);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String userId, java.lang.String categoryId,
		java.lang.String threadId, java.lang.String parentMessageId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		boolean anonymous, javax.portlet.PortletPreferences prefs,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().addMessage(userId, categoryId, threadId,
			parentMessageId, subject, body, files, anonymous, prefs,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addMessageResources(java.lang.String categoryId,
		java.lang.String messageId, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().addMessageResources(categoryId, messageId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addMessageResources(
		com.liferay.portlet.messageboards.model.MBCategory category,
		com.liferay.portlet.messageboards.model.MBMessage message,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().addMessageResources(category, message,
			addCommunityPermissions, addGuestPermissions);
	}

	public void deleteDiscussionMessage(java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteDiscussionMessage(messageId);
	}

	public void deleteDiscussionMessages(java.lang.String className,
		java.lang.String classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteDiscussionMessages(className, classPK);
	}

	public void deleteMessage(java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteMessage(messageId);
	}

	public void deleteMessage(
		com.liferay.portlet.messageboards.model.MBMessage message)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteMessage(message);
	}

	public java.util.List getCategoryMessages(java.lang.String categoryId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getService().getCategoryMessages(categoryId, begin, end);
	}

	public int getCategoryMessagesCount(java.lang.String categoryId)
		throws com.liferay.portal.SystemException {
		return getService().getCategoryMessagesCount(categoryId);
	}

	public int getCategoriesMessagesCount(java.util.List categoryIds)
		throws com.liferay.portal.SystemException {
		return getService().getCategoriesMessagesCount(categoryIds);
	}

	public java.lang.String getCategoryMessagesCountRSS(
		java.lang.String categoryId, int begin, int end, double version,
		java.lang.String url)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getCategoryMessagesCountRSS(categoryId, begin, end,
			version, url);
	}

	public com.liferay.portlet.messageboards.model.MBMessageDisplay getDiscussionMessageDisplay(
		java.lang.String userId, java.lang.String className,
		java.lang.String classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getDiscussionMessageDisplay(userId, className,
			classPK);
	}

	public java.util.List getGroupMessages(java.lang.String groupId, int begin,
		int end) throws com.liferay.portal.SystemException {
		return getService().getGroupMessages(groupId, begin, end);
	}

	public int getGroupMessagesCount(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return getService().getGroupMessagesCount(groupId);
	}

	public com.liferay.portlet.messageboards.model.MBMessage getMessage(
		java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getMessage(messageId);
	}

	public com.liferay.portlet.messageboards.model.MBMessageDisplay getMessageDisplay(
		java.lang.String messageId, java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getMessageDisplay(messageId, userId);
	}

	public com.liferay.portlet.messageboards.model.MBMessageDisplay getMessageDisplay(
		com.liferay.portlet.messageboards.model.MBMessage message,
		java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getMessageDisplay(message, userId);
	}

	public java.util.List getThreadMessages(java.lang.String threadId,
		java.lang.String userId) throws com.liferay.portal.SystemException {
		return getService().getThreadMessages(threadId, userId);
	}

	public java.util.List getThreadMessages(java.lang.String threadId,
		java.lang.String userId, java.util.Comparator comparator)
		throws com.liferay.portal.SystemException {
		return getService().getThreadMessages(threadId, userId, comparator);
	}

	public int getThreadMessagesCount(java.lang.String threadId)
		throws com.liferay.portal.SystemException {
		return getService().getThreadMessagesCount(threadId);
	}

	public void subscribeMessage(java.lang.String userId,
		java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().subscribeMessage(userId, messageId);
	}

	public void unsubscribeMessage(java.lang.String userId,
		java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().unsubscribeMessage(userId, messageId);
	}

	public com.liferay.portlet.messageboards.model.MBMessage updateDiscussionMessage(
		java.lang.String messageId, java.lang.String subject,
		java.lang.String body)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().updateDiscussionMessage(messageId, subject, body);
	}

	public com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		java.lang.String messageId, java.lang.String categoryId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().updateMessage(messageId, categoryId, subject, body,
			files, prefs);
	}

	public com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		java.lang.String messageId, java.util.Date createDate,
		java.util.Date modifiedDate)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().updateMessage(messageId, createDate, modifiedDate);
	}

	public com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		java.lang.String messageId, java.lang.String body)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().updateMessage(messageId, body);
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