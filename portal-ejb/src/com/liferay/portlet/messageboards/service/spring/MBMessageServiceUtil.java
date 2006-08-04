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

package com.liferay.portlet.messageboards.service.spring;

/**
 * <a href="MBMessageServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBMessageServiceUtil {
	public static com.liferay.portlet.messageboards.model.MBMessage addDiscussionMessage(
		java.lang.String groupId, java.lang.String className,
		java.lang.String classPK, java.lang.String threadId,
		java.lang.String parentMessageId, java.lang.String subject,
		java.lang.String body)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		MBMessageService mbMessageService = MBMessageServiceFactory.getService();

		return mbMessageService.addDiscussionMessage(groupId, className,
			classPK, threadId, parentMessageId, subject, body);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String categoryId, java.lang.String subject,
		java.lang.String body, java.util.List files, boolean anonymous,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		MBMessageService mbMessageService = MBMessageServiceFactory.getService();

		return mbMessageService.addMessage(categoryId, subject, body, files,
			anonymous, addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String categoryId, java.lang.String subject,
		java.lang.String body, java.util.List files, boolean anonymous,
		javax.portlet.PortletPreferences prefs,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		MBMessageService mbMessageService = MBMessageServiceFactory.getService();

		return mbMessageService.addMessage(categoryId, subject, body, files,
			anonymous, prefs, addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String categoryId, java.lang.String threadId,
		java.lang.String parentMessageId, java.lang.String subject,
		java.lang.String body, java.util.List files, boolean anonymous,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		MBMessageService mbMessageService = MBMessageServiceFactory.getService();

		return mbMessageService.addMessage(categoryId, threadId,
			parentMessageId, subject, body, files, anonymous,
			addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String categoryId, java.lang.String threadId,
		java.lang.String parentMessageId, java.lang.String subject,
		java.lang.String body, java.util.List files, boolean anonymous,
		javax.portlet.PortletPreferences prefs,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		MBMessageService mbMessageService = MBMessageServiceFactory.getService();

		return mbMessageService.addMessage(categoryId, threadId,
			parentMessageId, subject, body, files, anonymous, prefs,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void deleteDiscussionMessage(java.lang.String groupId,
		java.lang.String className, java.lang.String classPK,
		java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		MBMessageService mbMessageService = MBMessageServiceFactory.getService();
		mbMessageService.deleteDiscussionMessage(groupId, className, classPK,
			messageId);
	}

	public static void deleteMessage(java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		MBMessageService mbMessageService = MBMessageServiceFactory.getService();
		mbMessageService.deleteMessage(messageId);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage getMessage(
		java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		MBMessageService mbMessageService = MBMessageServiceFactory.getService();

		return mbMessageService.getMessage(messageId);
	}

	public static void subscribeMessage(java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		MBMessageService mbMessageService = MBMessageServiceFactory.getService();
		mbMessageService.subscribeMessage(messageId);
	}

	public static void unsubscribeMessage(java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		MBMessageService mbMessageService = MBMessageServiceFactory.getService();
		mbMessageService.unsubscribeMessage(messageId);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage updateDiscussionMessage(
		java.lang.String groupId, java.lang.String className,
		java.lang.String classPK, java.lang.String messageId,
		java.lang.String subject, java.lang.String body)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		MBMessageService mbMessageService = MBMessageServiceFactory.getService();

		return mbMessageService.updateDiscussionMessage(groupId, className,
			classPK, messageId, subject, body);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		java.lang.String messageId, java.lang.String categoryId,
		java.lang.String subject, java.lang.String body, java.util.List files)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		MBMessageService mbMessageService = MBMessageServiceFactory.getService();

		return mbMessageService.updateMessage(messageId, categoryId, subject,
			body, files);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		java.lang.String messageId, java.lang.String categoryId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		MBMessageService mbMessageService = MBMessageServiceFactory.getService();

		return mbMessageService.updateMessage(messageId, categoryId, subject,
			body, files, prefs);
	}
}