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
 * <a href="MBMessageService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is <code>com.liferay.portlet.messageboards.service.impl.MBMessageServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be accessed
 * remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.messageboards.service.MBMessageServiceFactory
 * @see com.liferay.portlet.messageboards.service.MBMessageServiceUtil
 *
 */
public interface MBMessageService {
	public com.liferay.portlet.messageboards.model.MBMessage addDiscussionMessage(
		long groupId, java.lang.String className, long classPK, long threadId,
		long parentMessageId, java.lang.String subject, java.lang.String body)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long categoryId, java.lang.String subject, java.lang.String body,
		java.util.List files, boolean anonymous, double priority,
		java.lang.String[] tagsEntries, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long categoryId, java.lang.String subject, java.lang.String body,
		java.util.List files, boolean anonymous, double priority,
		java.lang.String[] tagsEntries, javax.portlet.PortletPreferences prefs,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long categoryId, long threadId, long parentMessageId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		boolean anonymous, double priority, java.lang.String[] tagsEntries,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long categoryId, long threadId, long parentMessageId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		boolean anonymous, double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long categoryId, java.lang.String subject, java.lang.String body,
		java.util.List files, boolean anonymous, double priority,
		java.lang.String[] tagsEntries,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long categoryId, java.lang.String subject, java.lang.String body,
		java.util.List files, boolean anonymous, double priority,
		java.lang.String[] tagsEntries, javax.portlet.PortletPreferences prefs,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long categoryId, long threadId, long parentMessageId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		boolean anonymous, double priority, java.lang.String[] tagsEntries,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long categoryId, long threadId, long parentMessageId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		boolean anonymous, double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public void deleteDiscussionMessage(long groupId,
		java.lang.String className, long classPK, long messageId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public void deleteMessage(long messageId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public com.liferay.portlet.messageboards.model.MBMessage getMessage(
		long messageId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public com.liferay.portlet.messageboards.model.MBMessageDisplay getMessageDisplay(
		long userId, long messageId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public void subscribeMessage(long messageId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public void unsubscribeMessage(long messageId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public com.liferay.portlet.messageboards.model.MBMessage updateDiscussionMessage(
		long groupId, java.lang.String className, long classPK, long messageId,
		java.lang.String subject, java.lang.String body)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		long messageId, long categoryId, java.lang.String subject,
		java.lang.String body, java.util.List files, double priority,
		java.lang.String[] tagsEntries)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		long messageId, long categoryId, java.lang.String subject,
		java.lang.String body, java.util.List files, double priority,
		java.lang.String[] tagsEntries, javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;
}