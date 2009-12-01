/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.pop;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.kernel.pop.MessageListenerException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionCheckerUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.messageboards.NoSuchCategoryException;
import com.liferay.portlet.messageboards.NoSuchMessageException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageServiceUtil;
import com.liferay.portlet.messageboards.util.MBMailMessage;
import com.liferay.portlet.messageboards.util.MBUtil;

import javax.mail.Message;

import org.apache.commons.lang.time.StopWatch;

/**
 * <a href="MessageListenerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Michael C. Han
 */
public class MessageListenerImpl implements MessageListener {

	public boolean accept(String from, String recipient, Message message) {
		try {
			String messageId = getMessageId(recipient, message);

			if ((messageId == null) ||
				(!messageId.startsWith(
					MBUtil.POP_PORTLET_PREFIX, getOffset()))) {

				return false;
			}

			Company company = getCompany(recipient);
			long categoryId = getCategoryId(messageId);

			MBCategory category = MBCategoryLocalServiceUtil.getCategory(
				categoryId);

			if (category.getCompanyId() != company.getCompanyId()) {
				return false;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Check to see if user " + from + " exists");
			}

			UserLocalServiceUtil.getUserByEmailAddress(
				company.getCompanyId(), from);

			return true;
		}
		catch (Exception e) {
			if (_log.isErrorEnabled()) {
				_log.error("Unable to process message: " + message, e);
			}

			return false;
		}
	}

	public void deliver(String from, String recipient, Message message)
		throws MessageListenerException {

		try {
			StopWatch stopWatch = null;

			if (_log.isDebugEnabled()) {
				stopWatch = new StopWatch();

				stopWatch.start();

				_log.debug("Deliver message from " + from + " to " + recipient);
			}

			Company company = getCompany(recipient);

			String messageId = getMessageId(recipient, message);

			if (_log.isDebugEnabled()) {
				_log.debug("Message id " + messageId);
			}

			long groupId = 0;
			long categoryId = getCategoryId(messageId);

			try {
				MBCategory category = MBCategoryLocalServiceUtil.getCategory(
					categoryId);

				groupId = category.getGroupId();
		 	}
		 	catch (NoSuchCategoryException nsce) {
		 		groupId = categoryId;
		 		categoryId = MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID;
		 	}

		 	if (_log.isDebugEnabled()) {
		 		_log.debug("Group id " + groupId);
		 		_log.debug("Category id " + categoryId);
		 	}

			User user = UserLocalServiceUtil.getUserByEmailAddress(
				company.getCompanyId(), from);

			long parentMessageId = getParentMessageId(recipient, message);

			if (_log.isDebugEnabled()) {
				_log.debug("Parent message id " + parentMessageId);
			}

			MBMessage parentMessage = null;

			try {
				if (parentMessageId > 0) {
					parentMessage = MBMessageLocalServiceUtil.getMessage(
						parentMessageId);
				}
			}
			catch (NoSuchMessageException nsme) {

				// If the parent message does not exist we ignore it and post
				// the message as a new thread.

			}

			if (_log.isDebugEnabled()) {
				_log.debug("Parent message " + parentMessage);
			}

			String subject = MBUtil.getSubjectWithoutMessageId(message);

			MBMailMessage collector = new MBMailMessage();

			MBUtil.collectPartContent(message, collector);

			PermissionCheckerUtil.setThreadValues(user);

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddCommunityPermissions(true);
			serviceContext.setAddGuestPermissions(true);
			serviceContext.setLayoutFullURL(
				MBUtil.getLayoutFullURL(company.getCompanyId(), groupId));
			serviceContext.setScopeGroupId(groupId);

			if (parentMessage == null) {
				MBMessageServiceUtil.addMessage(
					groupId, categoryId, subject, collector.getBody(),
					collector.getFiles(), false, 0.0, serviceContext);
			}
			else {
				MBMessageServiceUtil.addMessage(
					groupId, categoryId, parentMessage.getThreadId(),
					parentMessage.getMessageId(), subject, collector.getBody(),
					collector.getFiles(), false, 0.0, serviceContext);
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Delivering message takes " + stopWatch.getTime() + " ms");
			}
		}
		catch (PrincipalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug("Prevented unauthorized post from " + from);
			}

			throw new MessageListenerException(pe);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new MessageListenerException(e);
		}
	}

	public String getId() {
		return MessageListenerImpl.class.getName();
	}

	protected long getCategoryId(String recipient) {
		int pos = recipient.indexOf(StringPool.AT);

		String target = recipient.substring(
			MBUtil.POP_PORTLET_PREFIX.length() + getOffset(), pos);

		String[] parts = StringUtil.split(target, StringPool.PERIOD);

		return GetterUtil.getLong(parts[0]);
	}

	protected Company getCompany(String recipient) throws Exception {
		int pos =
			recipient.indexOf(StringPool.AT) +
				MBUtil.POP_SERVER_SUBDOMAIN_LENGTH + 1;

		if (MBUtil.POP_SERVER_SUBDOMAIN_LENGTH > 0) {
			pos++;
		}

		String mx = recipient.substring(pos);

		return CompanyLocalServiceUtil.getCompanyByMx(mx);
	}

	protected String getMessageId(String recipient, Message message)
		throws Exception {

		if (MBUtil.POP_SERVER_SUBDOMAIN_LENGTH > 0) {
			return recipient;
		}
		else {
			return MBUtil.getParentMessageIdString(message);
		}
	}

	protected int getOffset() {
		if (MBUtil.POP_SERVER_SUBDOMAIN_LENGTH == 0) {
			return 1;
		}
		return 0;
	}

	protected long getParentMessageId(String recipient, Message message)
		throws Exception {

		// Get the parent message ID from the recipient address

		int pos = recipient.indexOf(StringPool.AT);

		String target = recipient.substring(
			MBUtil.POP_PORTLET_PREFIX.length(), pos);

		String[] parts = StringUtil.split(target, StringPool.PERIOD);

		long parentMessageId = 0;

		if (parts.length == 2) {
			parentMessageId = GetterUtil.getLong(parts[1]);
		}

		if (parentMessageId > 0) {
			return parentMessageId;
		}
		else {
			return MBUtil.getParentMessageId(message);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(MessageListenerImpl.class);

}