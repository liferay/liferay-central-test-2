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

import com.liferay.portal.service.impl.PrincipalSessionBean;
import com.liferay.portal.spring.util.SpringUtil;

import com.liferay.portlet.messageboards.service.spring.MBMessageService;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="MBMessageServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBMessageServiceEJBImpl implements MBMessageService, SessionBean {
	public static final String CLASS_NAME = MBMessageService.class.getName() +
		".transaction";

	public static MBMessageService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (MBMessageService)ctx.getBean(CLASS_NAME);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addDiscussionMessage(
		java.lang.String groupId, java.lang.String className,
		java.lang.String classPK, java.lang.String threadId,
		java.lang.String parentMessageId, java.lang.String subject,
		java.lang.String body)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().addDiscussionMessage(groupId, className, classPK,
			threadId, parentMessageId, subject, body);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String topicId, java.lang.String subject,
		java.lang.String body, java.util.List files, boolean anonymous,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().addMessage(topicId, subject, body, files,
			anonymous, addCommunityPermissions, addGuestPermissions);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String topicId, java.lang.String subject,
		java.lang.String body, java.util.List files, boolean anonymous,
		javax.portlet.PortletPreferences prefs,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().addMessage(topicId, subject, body, files,
			anonymous, prefs, addCommunityPermissions, addGuestPermissions);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String topicId, java.lang.String threadId,
		java.lang.String parentMessageId, java.lang.String subject,
		java.lang.String body, java.util.List files, boolean anonymous,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().addMessage(topicId, threadId, parentMessageId,
			subject, body, files, anonymous, addCommunityPermissions,
			addGuestPermissions);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String topicId, java.lang.String threadId,
		java.lang.String parentMessageId, java.lang.String subject,
		java.lang.String body, java.util.List files, boolean anonymous,
		javax.portlet.PortletPreferences prefs,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().addMessage(topicId, threadId, parentMessageId,
			subject, body, files, anonymous, prefs, addCommunityPermissions,
			addGuestPermissions);
	}

	public void deleteDiscussionMessage(java.lang.String groupId,
		java.lang.String className, java.lang.String classPK,
		java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		getService().deleteDiscussionMessage(groupId, className, classPK,
			messageId);
	}

	public void deleteMessage(java.lang.String topicId,
		java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		getService().deleteMessage(topicId, messageId);
	}

	public com.liferay.portlet.messageboards.model.MBMessage getMessage(
		java.lang.String topicId, java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getMessage(topicId, messageId);
	}

	public void subscribeMessage(java.lang.String topicId,
		java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		getService().subscribeMessage(topicId, messageId);
	}

	public void unsubscribeMessage(java.lang.String topicId,
		java.lang.String messageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		getService().unsubscribeMessage(topicId, messageId);
	}

	public com.liferay.portlet.messageboards.model.MBMessage updateDiscussionMessage(
		java.lang.String groupId, java.lang.String className,
		java.lang.String classPK, java.lang.String messageId,
		java.lang.String subject, java.lang.String body)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().updateDiscussionMessage(groupId, className,
			classPK, messageId, subject, body);
	}

	public com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		java.lang.String topicId, java.lang.String messageId,
		java.lang.String subject, java.lang.String body, java.util.List files)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().updateMessage(topicId, messageId, subject, body,
			files);
	}

	public com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		java.lang.String topicId, java.lang.String messageId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().updateMessage(topicId, messageId, subject, body,
			files, prefs);
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