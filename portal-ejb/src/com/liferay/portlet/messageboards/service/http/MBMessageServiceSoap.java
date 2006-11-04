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

import com.liferay.portal.kernel.util.StackTraceUtil;

import com.liferay.portlet.messageboards.service.spring.MBMessageServiceUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.rmi.RemoteException;

/**
 * <a href="MBMessageServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBMessageServiceSoap {
	public static com.liferay.portlet.messageboards.model.MBMessageModel addDiscussionMessage(
		java.lang.String groupId, java.lang.String className,
		java.lang.String classPK, java.lang.String threadId,
		java.lang.String parentMessageId, java.lang.String subject,
		java.lang.String body) throws RemoteException {
		try {
			com.liferay.portlet.messageboards.model.MBMessage returnValue = MBMessageServiceUtil.addDiscussionMessage(groupId,
					className, classPK, threadId, parentMessageId, subject, body);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.messageboards.model.MBMessageModel addMessage(
		java.lang.String categoryId, java.lang.String subject,
		java.lang.String body, java.util.List files, boolean anonymous,
		double priority, boolean addCommunityPermissions,
		boolean addGuestPermissions) throws RemoteException {
		try {
			com.liferay.portlet.messageboards.model.MBMessage returnValue = MBMessageServiceUtil.addMessage(categoryId,
					subject, body, files, anonymous, priority,
					addCommunityPermissions, addGuestPermissions);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.messageboards.model.MBMessageModel addMessage(
		java.lang.String categoryId, java.lang.String threadId,
		java.lang.String parentMessageId, java.lang.String subject,
		java.lang.String body, java.util.List files, boolean anonymous,
		double priority, boolean addCommunityPermissions,
		boolean addGuestPermissions) throws RemoteException {
		try {
			com.liferay.portlet.messageboards.model.MBMessage returnValue = MBMessageServiceUtil.addMessage(categoryId,
					threadId, parentMessageId, subject, body, files, anonymous,
					priority, addCommunityPermissions, addGuestPermissions);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void deleteDiscussionMessage(java.lang.String groupId,
		java.lang.String className, java.lang.String classPK,
		java.lang.String messageId) throws RemoteException {
		try {
			MBMessageServiceUtil.deleteDiscussionMessage(groupId, className,
				classPK, messageId);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void deleteMessage(java.lang.String messageId)
		throws RemoteException {
		try {
			MBMessageServiceUtil.deleteMessage(messageId);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.messageboards.model.MBMessageModel getMessage(
		java.lang.String messageId) throws RemoteException {
		try {
			com.liferay.portlet.messageboards.model.MBMessage returnValue = MBMessageServiceUtil.getMessage(messageId);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void subscribeMessage(java.lang.String messageId)
		throws RemoteException {
		try {
			MBMessageServiceUtil.subscribeMessage(messageId);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void unsubscribeMessage(java.lang.String messageId)
		throws RemoteException {
		try {
			MBMessageServiceUtil.unsubscribeMessage(messageId);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.messageboards.model.MBMessageModel updateDiscussionMessage(
		java.lang.String groupId, java.lang.String className,
		java.lang.String classPK, java.lang.String messageId,
		java.lang.String subject, java.lang.String body)
		throws RemoteException {
		try {
			com.liferay.portlet.messageboards.model.MBMessage returnValue = MBMessageServiceUtil.updateDiscussionMessage(groupId,
					className, classPK, messageId, subject, body);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.messageboards.model.MBMessageModel updateMessage(
		java.lang.String messageId, java.lang.String categoryId,
		java.lang.String subject, java.lang.String body, java.util.List files,
		double priority) throws RemoteException {
		try {
			com.liferay.portlet.messageboards.model.MBMessage returnValue = MBMessageServiceUtil.updateMessage(messageId,
					categoryId, subject, body, files, priority);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	private static Log _log = LogFactory.getLog(MBMessageServiceSoap.class);
}