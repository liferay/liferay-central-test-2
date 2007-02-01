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

package com.liferay.portlet.messageboards.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.messageboards.service.MBMessageServiceUtil;

import java.rmi.RemoteException;

/**
 * <a href="MBMessageServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBMessageServiceSoap {
	public static com.liferay.portlet.messageboards.model.MBMessageSoap addDiscussionMessage(
		long groupId, java.lang.String className, java.lang.String classPK,
		java.lang.String threadId, java.lang.String parentMessageId,
		java.lang.String subject, java.lang.String body)
		throws RemoteException {
		try {
			com.liferay.portlet.messageboards.model.MBMessage returnValue = MBMessageServiceUtil.addDiscussionMessage(groupId,
					className, classPK, threadId, parentMessageId, subject, body);

			return com.liferay.portlet.messageboards.model.MBMessageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBMessageSoap addMessage(
		java.lang.String categoryId, java.lang.String subject,
		java.lang.String body, java.util.List files, boolean anonymous,
		double priority, boolean addCommunityPermissions,
		boolean addGuestPermissions) throws RemoteException {
		try {
			com.liferay.portlet.messageboards.model.MBMessage returnValue = MBMessageServiceUtil.addMessage(categoryId,
					subject, body, files, anonymous, priority,
					addCommunityPermissions, addGuestPermissions);

			return com.liferay.portlet.messageboards.model.MBMessageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBMessageSoap addMessage(
		java.lang.String categoryId, java.lang.String threadId,
		java.lang.String parentMessageId, java.lang.String subject,
		java.lang.String body, java.util.List files, boolean anonymous,
		double priority, boolean addCommunityPermissions,
		boolean addGuestPermissions) throws RemoteException {
		try {
			com.liferay.portlet.messageboards.model.MBMessage returnValue = MBMessageServiceUtil.addMessage(categoryId,
					threadId, parentMessageId, subject, body, files, anonymous,
					priority, addCommunityPermissions, addGuestPermissions);

			return com.liferay.portlet.messageboards.model.MBMessageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBMessageSoap addMessage(
		java.lang.String categoryId, java.lang.String subject,
		java.lang.String body, java.util.List files, boolean anonymous,
		double priority, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions) throws RemoteException {
		try {
			com.liferay.portlet.messageboards.model.MBMessage returnValue = MBMessageServiceUtil.addMessage(categoryId,
					subject, body, files, anonymous, priority,
					communityPermissions, guestPermissions);

			return com.liferay.portlet.messageboards.model.MBMessageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBMessageSoap addMessage(
		java.lang.String categoryId, java.lang.String threadId,
		java.lang.String parentMessageId, java.lang.String subject,
		java.lang.String body, java.util.List files, boolean anonymous,
		double priority, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions) throws RemoteException {
		try {
			com.liferay.portlet.messageboards.model.MBMessage returnValue = MBMessageServiceUtil.addMessage(categoryId,
					threadId, parentMessageId, subject, body, files, anonymous,
					priority, communityPermissions, guestPermissions);

			return com.liferay.portlet.messageboards.model.MBMessageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteDiscussionMessage(long groupId,
		java.lang.String className, java.lang.String classPK,
		java.lang.String messageId) throws RemoteException {
		try {
			MBMessageServiceUtil.deleteDiscussionMessage(groupId, className,
				classPK, messageId);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteMessage(java.lang.String messageId)
		throws RemoteException {
		try {
			MBMessageServiceUtil.deleteMessage(messageId);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBMessageSoap getMessage(
		java.lang.String messageId) throws RemoteException {
		try {
			com.liferay.portlet.messageboards.model.MBMessage returnValue = MBMessageServiceUtil.getMessage(messageId);

			return com.liferay.portlet.messageboards.model.MBMessageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBMessageDisplay getMessageDisplay(
		java.lang.String messageId, java.lang.String userId)
		throws RemoteException {
		try {
			com.liferay.portlet.messageboards.model.MBMessageDisplay returnValue =
				MBMessageServiceUtil.getMessageDisplay(messageId, userId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static void subscribeMessage(java.lang.String messageId)
		throws RemoteException {
		try {
			MBMessageServiceUtil.subscribeMessage(messageId);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static void unsubscribeMessage(java.lang.String messageId)
		throws RemoteException {
		try {
			MBMessageServiceUtil.unsubscribeMessage(messageId);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBMessageSoap updateDiscussionMessage(
		long groupId, java.lang.String className, java.lang.String classPK,
		java.lang.String messageId, java.lang.String subject,
		java.lang.String body) throws RemoteException {
		try {
			com.liferay.portlet.messageboards.model.MBMessage returnValue = MBMessageServiceUtil.updateDiscussionMessage(groupId,
					className, classPK, messageId, subject, body);

			return com.liferay.portlet.messageboards.model.MBMessageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBMessageSoap updateMessage(
		java.lang.String messageId, java.lang.String categoryId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		double priority) throws RemoteException {
		try {
			com.liferay.portlet.messageboards.model.MBMessage returnValue = MBMessageServiceUtil.updateMessage(messageId,
					categoryId, subject, body, files, priority);

			return com.liferay.portlet.messageboards.model.MBMessageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(MBMessageServiceSoap.class);
}