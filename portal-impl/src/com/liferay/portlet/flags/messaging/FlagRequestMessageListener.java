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

package com.liferay.portlet.flags.messaging;

import com.liferay.mail.service.MailService;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.RoleLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserGroupRoleLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portlet.flags.FlagRequest;
import com.liferay.util.UniqueList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.mail.internet.InternetAddress;

/**
 * <a href="FlagRequestMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Julio Camarero
 * @author Michael C. Han
 *
 */
public class FlagRequestMessageListener implements MessageListener {
	public void receive(Message message) {
		FlagRequest flagRequest = (FlagRequest)message.getPayload();
		try {
			doReceive(flagRequest);
		}
		catch (Exception e) {
			if (_log.isErrorEnabled()) {
				_log.error("Unable to process request: " + flagRequest, e);
			}
		}
	}

	protected void doReceive(FlagRequest flagRequest)
		throws PortalException, SystemException {
			// Company

		ServiceContext serviceContext = flagRequest.getServiceContext();
		
		long companyId = serviceContext.getCompanyId();

		Company company = _companyLocalService.getCompany(
			serviceContext.getCompanyId());

		// Group
		serviceContext.getScopeGroupId();
		Layout layout = _layoutLocalService.getLayout(serviceContext.getPlid());

		Group group = layout.getGroup();

		String groupName = group.getDescriptiveName();

		// Reporter user

		String reporterUserName = null;
		String reporterEmailAddress = flagRequest.getReporterEmailAddress();

		User reporterUser = _userLocalService.getUser(
			serviceContext.getUserId());

		Locale locale = LocaleUtil.getDefault();

		if (reporterUser.isDefaultUser()) {
			reporterUserName = LanguageUtil.get(locale, "anonymous");
		}
		else {
			reporterUserName = reporterUser.getFullName();
			reporterEmailAddress = reporterUser.getEmailAddress();
		}

		// Reported user

		String reportedUserName = null;
		String reportedEmailAddress = StringPool.BLANK;
		String reportedURL = StringPool.BLANK;

		User reportedUser = _userLocalService.getUser(
			flagRequest.getReportedUserId());

		if (reportedUser.isDefaultUser()) {
			reportedUserName = group.getDescriptiveName();
		}
		else {
			reportedUserName = reportedUser.getFullName();
			reportedEmailAddress = reportedUser.getEmailAddress();
			reportedURL = reportedUser.getDisplayURL(
				serviceContext.getPortalURL(), serviceContext.getPathMain());
		}

		// Content

		String contentType = LanguageUtil.get(
			locale, "model.resource." + flagRequest.getClassName());

		// Reason

		String reason = LanguageUtil.get(locale, flagRequest.getReason());

		// Email

		String fromName = PrefsPropsUtil.getString(
			companyId, PropsKeys.FLAGS_EMAIL_FROM_NAME);
		String fromAddress = PrefsPropsUtil.getString(
			companyId, PropsKeys.FLAGS_EMAIL_FROM_ADDRESS);
		String subject = PrefsPropsUtil.getContent(
			companyId, PropsKeys.FLAGS_EMAIL_SUBJECT);
		String body = PrefsPropsUtil.getContent(
			companyId, PropsKeys.FLAGS_EMAIL_BODY);

		// Recipients

		List<User> recipients = getRecipients(
			companyId, serviceContext.getScopeGroupId());

		for (User recipient : recipients) {
			try {
				notify(
					company, groupName, reporterEmailAddress, reporterUserName,
					reportedEmailAddress, reportedUserName, reportedURL,
					flagRequest.getClassPK(), flagRequest.getContentTitle(),
					contentType, flagRequest.getContentURL(), reason,
					fromName, fromAddress, recipient.getFullName(),
					recipient.getEmailAddress(), subject, body, serviceContext);
			}
			catch (IOException ioe) {
				if (_log.isWarnEnabled()) {
					_log.warn(ioe);
				}
			}
		}
	}

	protected List<User> getRecipients(long companyId, long groupId)
		throws PortalException, SystemException {

		List<User> recipients = new UniqueList<User>();

		List<String> roleNames = new ArrayList<String>();

		Group group = _groupLocalService.getGroup(groupId);

		if (group.isCommunity()) {
			roleNames.add(RoleConstants.COMMUNITY_ADMINISTRATOR);
			roleNames.add(RoleConstants.COMMUNITY_OWNER);
		}
		else if (group.isCompany()) {
			roleNames.add(RoleConstants.ADMINISTRATOR);
		}
		else if (group.isOrganization()) {
			roleNames.add(RoleConstants.ORGANIZATION_ADMINISTRATOR);
			roleNames.add(RoleConstants.ORGANIZATION_OWNER);
		}

		for (String roleName : roleNames) {
			Role role = _roleLocalService.getRole(companyId, roleName);

			List<UserGroupRole> userGroupRoles =
				_userGroupRoleLocalService.getUserGroupRolesByGroupAndRole(
					groupId, role.getRoleId());

			for (UserGroupRole userGroupRole : userGroupRoles) {
				recipients.add(userGroupRole.getUser());
			}
		}

		if (recipients.isEmpty()) {
			Role role = _roleLocalService.getRole(
				companyId, RoleConstants.ADMINISTRATOR);

			recipients.addAll(_userLocalService.getRoleUsers(role.getRoleId()));
		}

		return recipients;
	}

	protected void notify(
			Company company, String groupName, String reporterEmailAddress,
			String reporterUserName, String reportedEmailAddress,
			String reportedUserName, String reportedUserURL, long contentId,
			String contentTitle, String contentType, String contentURL,
			String reason, String fromName, String fromAddress, String toName,
			String toAddress, String subject, String body,
			ServiceContext serviceContext)
		throws IOException {

		Date now = new Date();

		subject = StringUtil.replace(
			subject,
			new String[] {
				"[$COMMUNITY_NAME$]",
				"[$COMPANY_ID$]",
				"[$COMPANY_MX$]",
				"[$COMPANY_NAME$]",
				"[$CONTENT_ID$]",
				"[$CONTENT_TITLE$]",
				"[$CONTENT_TYPE$]",
				"[$CONTENT_URL$]",
				"[$DATE$]",
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$PORTAL_URL$]",
				"[$REASON$]",
				"[$REPORTED_USER_ADDRESS$]",
				"[$REPORTED_USER_NAME$]",
				"[$REPORTED_USER_URL$]",
				"[$REPORTER_USER_ADDRESS$]",
				"[$REPORTER_USER_NAME$]",
				"[$TO_ADDRESS$]",
				"[$TO_NAME$]"
			},
			new String[] {
				groupName,
				String.valueOf(company.getCompanyId()),
				company.getMx(),
				company.getName(),
				String.valueOf(contentId),
				contentTitle,
				contentType,
				contentURL,
				now.toString(),
				fromAddress,
				fromName,
				serviceContext.getPortalURL(),
				reason,
				reportedEmailAddress,
				reportedUserName,
				reportedUserURL,
				reporterEmailAddress,
				reporterUserName,
				toAddress,
				toName
			});

		body = StringUtil.replace(
			body,
			new String[] {
				"[$COMMUNITY_NAME$]",
				"[$COMPANY_ID$]",
				"[$COMPANY_MX$]",
				"[$COMPANY_NAME$]",
				"[$CONTENT_ID$]",
				"[$CONTENT_TITLE$]",
				"[$CONTENT_TYPE$]",
				"[$CONTENT_URL$]",
				"[$DATE$]",
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$PORTAL_URL$]",
				"[$REASON$]",
				"[$REPORTED_USER_ADDRESS$]",
				"[$REPORTED_USER_NAME$]",
				"[$REPORTED_USER_URL$]",
				"[$REPORTER_USER_ADDRESS$]",
				"[$REPORTER_USER_NAME$]",
				"[$TO_ADDRESS$]",
				"[$TO_NAME$]"
			},
			new String[] {
				groupName,
				String.valueOf(company.getCompanyId()),
				company.getMx(),
				company.getName(),
				String.valueOf(contentId),
				contentTitle,
				contentType,
				contentURL,
				now.toString(),
				fromAddress,
				fromName,
				serviceContext.getPortalURL(),
				reason,
				reportedEmailAddress,
				reportedUserName,
				reportedUserURL,
				reporterEmailAddress,
				reporterUserName,
				toAddress,
				toName
			});

		InternetAddress from = new InternetAddress(fromAddress, fromName);

		InternetAddress to = new InternetAddress(toAddress, toName);

		MailMessage message = new MailMessage(from, to, subject, body, true);

		_mailService.sendEmail(message);
	}

	private static Log _log =
		LogFactoryUtil.getLog(FlagRequestMessageListener.class);

	@BeanReference(name = "com.liferay.portal.service.CompanyLocalService")
	private CompanyLocalService _companyLocalService;
	@BeanReference(name = "com.liferay.portal.service.GroupLocalService")
	private GroupLocalService _groupLocalService;
	@BeanReference(name = "com.liferay.portal.service.LayoutLocalService")
	private LayoutLocalService _layoutLocalService;
	@BeanReference(name = "com.liferay.mail.service.MailService")
	private MailService _mailService;
	@BeanReference(name = "com.liferay.portal.service.RoleLocalService")
	private RoleLocalService _roleLocalService;
	@BeanReference(name = "com.liferay.portal.service.UserLocalService")
	private UserLocalService _userLocalService;
	@BeanReference(name = "com.liferay.portal.service.UserGroupRoleLocalService")
	private UserGroupRoleLocalService _userGroupRoleLocalService;
}
