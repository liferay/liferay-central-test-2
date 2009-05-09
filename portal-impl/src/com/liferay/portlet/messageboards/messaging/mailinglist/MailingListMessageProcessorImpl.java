/*
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

package com.liferay.portlet.messageboards.messaging.mailinglist;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionCheckerUtil;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portlet.messageboards.NoSuchMessageException;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;
import com.liferay.portlet.messageboards.service.MBMessageService;
import com.liferay.portlet.messageboards.util.MBMailMessage;
import com.liferay.portlet.messageboards.util.MBUtil;
import com.liferay.portlet.messageboards.util.MailingListThreadLocal;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

/**
 * <a href="MailingListMessageProcessorImpl.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Thiago Moreira
 * @author Michael C. Han
 */
public class MailingListMessageProcessorImpl
	implements MailingListMessageProcessor {

	public MailingListMessageProcessorImpl(
		CompanyLocalService companyLocalService,
		MBMessageLocalService mbMessageLocalService,
		MBMessageService mbMessageService,
		UserLocalService userLocalService) {
		
		_companyLocalService = companyLocalService;
		_mbMessageService = mbMessageService;
		_mbMessageLocalService = mbMessageLocalService;
		_userLocalService = userLocalService;
	}

	/**
	 * Only accepts messages that have not originated from the message boards
	 *
	 * @param message to validate
	 * @return false if the message originated from the message boards.
	 * @throws Exception
	 */
	public boolean accept(Message message)
		throws Exception {

		return !MBUtil.hasMailIdHeader(message);
	}

	public void deliver(
			MailingListRequest mailingListRequest, Message mailMessage)
		throws Exception {

		String from = _getFromAddress(mailMessage);

		Company company = _companyLocalService.getCompany(
			mailingListRequest.getCompanyId());

		long categoryId = mailingListRequest.getCategoryId();

		if (_log.isDebugEnabled()) {
			_log.debug("Category id " + categoryId);
		}

		MBMessage parentMessage = _getParentMessage(mailMessage);

		if (_log.isDebugEnabled()) {
			_log.debug("Parent message " + parentMessage);
		}

		boolean anonymous = _validateUser(company, mailingListRequest, from);

		MBMailMessage collector = new MBMailMessage();

		MBUtil.collectPartContent(mailMessage, collector);

		MailingListThreadLocal.setSourceMailingList(true);

		String subject = MBUtil.getSubjectWithoutMessageId(mailMessage);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		if (parentMessage == null) {
			_mbMessageService.addMessage(
				categoryId, subject, collector.getBody(), collector.getFiles(),
				anonymous, 0.0, serviceContext);
		}
		else {
			_mbMessageService.addMessage(
				categoryId, parentMessage.getThreadId(),
				parentMessage.getMessageId(), subject, collector.getBody(),
				collector.getFiles(), anonymous, 0.0, serviceContext);
		}
	}

	private boolean _validateUser(
			Company company, MailingListRequest mailingListRequest, String from)
		throws PortalException, SystemException {

		boolean anonymous = false;
		User user;
		try {
			user = _userLocalService.getUserByEmailAddress(
				company.getCompanyId(), from);
		}
		catch (NoSuchUserException nsue) {
			user = _userLocalService.getUserById(
				company.getCompanyId(), mailingListRequest.getUserId());
			anonymous = true;
		}
		PermissionCheckerUtil.setThreadValues(user);

		return anonymous;
	}

	private String _getFromAddress(Message mailMessage)
		throws MessagingException {
		String from = null;

		Address[] addresses = mailMessage.getFrom();

		if ((addresses != null) && (addresses.length > 0)) {
			Address address = addresses[0];

			if (address instanceof InternetAddress) {
				from = ((InternetAddress) address).getAddress();
			}
			else {
				from = address.toString();
			}
		}
		return from;
	}

	private MBMessage _getParentMessage(Message mailMessage) throws Exception {

		long parentMessageId = MBUtil.getParentMessageId(mailMessage);

		if (_log.isDebugEnabled()) {
			_log.debug("Parent message id " + parentMessageId);
		}

		MBMessage parentMessage = null;

		try {
			if (parentMessageId > 0) {
				parentMessage = _mbMessageLocalService.getMessage(
					parentMessageId);
			}
		}
		catch (NoSuchMessageException nsme) {
			//nothing to do here...
		}
		return parentMessage;
	}

	private static final Log _log =
		LogFactoryUtil.getLog(MailingListMessageProcessorImpl.class);

	private CompanyLocalService _companyLocalService;
	private MBMessageLocalService _mbMessageLocalService;
	private MBMessageService _mbMessageService;
	private UserLocalService _userLocalService;
}
