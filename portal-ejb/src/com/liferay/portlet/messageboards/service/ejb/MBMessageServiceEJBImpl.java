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

import com.liferay.portal.service.impl.PrincipalSessionBean;

import com.liferay.portlet.messageboards.service.MBMessageService;
import com.liferay.portlet.messageboards.service.MBMessageServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="MBMessageServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is the EJB implementation of the service that is used when Liferay
 * is run inside a full J2EE container.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.messageboards.service.MBMessageService
 * @see com.liferay.portlet.messageboards.service.MBMessageServiceUtil
 * @see com.liferay.portlet.messageboards.service.ejb.MBMessageServiceEJB
 * @see com.liferay.portlet.messageboards.service.ejb.MBMessageServiceHome
 * @see com.liferay.portlet.messageboards.service.impl.MBMessageServiceImpl
 *
 */
public class MBMessageServiceEJBImpl implements MBMessageService, SessionBean {
	public com.liferay.portlet.messageboards.model.MBMessage addDiscussionMessage(
		long groupId, java.lang.String className, java.lang.String classPK,
		java.lang.String threadId, java.lang.String parentMessageId,
		java.lang.String subject, java.lang.String body)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return MBMessageServiceFactory.getTxImpl().addDiscussionMessage(groupId,
			className, classPK, threadId, parentMessageId, subject, body);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String categoryId, java.lang.String subject,
		java.lang.String body, java.util.List files, boolean anonymous,
		double priority, java.lang.String[] tagsEntries,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return MBMessageServiceFactory.getTxImpl().addMessage(categoryId,
			subject, body, files, anonymous, priority, tagsEntries,
			addCommunityPermissions, addGuestPermissions);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String categoryId, java.lang.String subject,
		java.lang.String body, java.util.List files, boolean anonymous,
		double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return MBMessageServiceFactory.getTxImpl().addMessage(categoryId,
			subject, body, files, anonymous, priority, tagsEntries, prefs,
			addCommunityPermissions, addGuestPermissions);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String categoryId, java.lang.String threadId,
		java.lang.String parentMessageId, java.lang.String subject,
		java.lang.String body, java.util.List files, boolean anonymous,
		double priority, java.lang.String[] tagsEntries,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return MBMessageServiceFactory.getTxImpl().addMessage(categoryId,
			threadId, parentMessageId, subject, body, files, anonymous,
			priority, tagsEntries, addCommunityPermissions, addGuestPermissions);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String categoryId, java.lang.String threadId,
		java.lang.String parentMessageId, java.lang.String subject,
		java.lang.String body, java.util.List files, boolean anonymous,
		double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return MBMessageServiceFactory.getTxImpl().addMessage(categoryId,
			threadId, parentMessageId, subject, body, files, anonymous,
			priority, tagsEntries, prefs, addCommunityPermissions,
			addGuestPermissions);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String categoryId, java.lang.String subject,
		java.lang.String body, java.util.List files, boolean anonymous,
		double priority, java.lang.String[] tagsEntries,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return MBMessageServiceFactory.getTxImpl().addMessage(categoryId,
			subject, body, files, anonymous, priority, tagsEntries,
			communityPermissions, guestPermissions);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String categoryId, java.lang.String subject,
		java.lang.String body, java.util.List files, boolean anonymous,
		double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return MBMessageServiceFactory.getTxImpl().addMessage(categoryId,
			subject, body, files, anonymous, priority, tagsEntries, prefs,
			communityPermissions, guestPermissions);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String categoryId, java.lang.String threadId,
		java.lang.String parentMessageId, java.lang.String subject,
		java.lang.String body, java.util.List files, boolean anonymous,
		double priority, java.lang.String[] tagsEntries,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return MBMessageServiceFactory.getTxImpl().addMessage(categoryId,
			threadId, parentMessageId, subject, body, files, anonymous,
			priority, tagsEntries, communityPermissions, guestPermissions);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String categoryId, java.lang.String threadId,
		java.lang.String parentMessageId, java.lang.String subject,
		java.lang.String body, java.util.List files, boolean anonymous,
		double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return MBMessageServiceFactory.getTxImpl().addMessage(categoryId,
			threadId, parentMessageId, subject, body, files, anonymous,
			priority, tagsEntries, prefs, communityPermissions, guestPermissions);
	}

	public void deleteDiscussionMessage(long groupId,
		java.lang.String className, java.lang.String classPK,
		java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		MBMessageServiceFactory.getTxImpl().deleteDiscussionMessage(groupId,
			className, classPK, messageId);
	}

	public void deleteMessage(java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		MBMessageServiceFactory.getTxImpl().deleteMessage(messageId);
	}

	public com.liferay.portlet.messageboards.model.MBMessage getMessage(
		java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return MBMessageServiceFactory.getTxImpl().getMessage(messageId);
	}

	public com.liferay.portlet.messageboards.model.MBMessageDisplay getMessageDisplay(
		java.lang.String messageId, java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return MBMessageServiceFactory.getTxImpl().getMessageDisplay(messageId,
			userId);
	}

	public void subscribeMessage(java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		MBMessageServiceFactory.getTxImpl().subscribeMessage(messageId);
	}

	public void unsubscribeMessage(java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		MBMessageServiceFactory.getTxImpl().unsubscribeMessage(messageId);
	}

	public com.liferay.portlet.messageboards.model.MBMessage updateDiscussionMessage(
		long groupId, java.lang.String className, java.lang.String classPK,
		java.lang.String messageId, java.lang.String subject,
		java.lang.String body)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return MBMessageServiceFactory.getTxImpl().updateDiscussionMessage(groupId,
			className, classPK, messageId, subject, body);
	}

	public com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		java.lang.String messageId, java.lang.String categoryId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		double priority, java.lang.String[] tagsEntries)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return MBMessageServiceFactory.getTxImpl().updateMessage(messageId,
			categoryId, subject, body, files, priority, tagsEntries);
	}

	public com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		java.lang.String messageId, java.lang.String categoryId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		double priority, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return MBMessageServiceFactory.getTxImpl().updateMessage(messageId,
			categoryId, subject, body, files, priority, tagsEntries, prefs);
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