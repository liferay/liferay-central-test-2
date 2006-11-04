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

package com.liferay.portlet.messageboards.service.spring;

/**
 * <a href="MBMessageService.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public interface MBMessageService {
	public com.liferay.portlet.messageboards.model.MBMessage addDiscussionMessage(
		java.lang.String groupId, java.lang.String className,
		java.lang.String classPK, java.lang.String threadId,
		java.lang.String parentMessageId, java.lang.String subject,
		java.lang.String body)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException;

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String categoryId, java.lang.String subject,
		java.lang.String body, java.util.List files, boolean anonymous,
		double priority, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException;

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String categoryId, java.lang.String subject,
		java.lang.String body, java.util.List files, boolean anonymous,
		double priority, javax.portlet.PortletPreferences prefs,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException;

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String categoryId, java.lang.String threadId,
		java.lang.String parentMessageId, java.lang.String subject,
		java.lang.String body, java.util.List files, boolean anonymous,
		double priority, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException;

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String categoryId, java.lang.String threadId,
		java.lang.String parentMessageId, java.lang.String subject,
		java.lang.String body, java.util.List files, boolean anonymous,
		double priority, javax.portlet.PortletPreferences prefs,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException;

	public void deleteDiscussionMessage(java.lang.String groupId,
		java.lang.String className, java.lang.String classPK,
		java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException;

	public void deleteMessage(java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException;

	public com.liferay.portlet.messageboards.model.MBMessage getMessage(
		java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException;

	public void subscribeMessage(java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException;

	public void unsubscribeMessage(java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException;

	public com.liferay.portlet.messageboards.model.MBMessage updateDiscussionMessage(
		java.lang.String groupId, java.lang.String className,
		java.lang.String classPK, java.lang.String messageId,
		java.lang.String subject, java.lang.String body)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException;

	public com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		java.lang.String messageId, java.lang.String categoryId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		double priority)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException;

	public com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		java.lang.String messageId, java.lang.String categoryId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		double priority, javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException;
}