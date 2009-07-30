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

package com.liferay.portlet.flags.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portlet.flags.service.base.FlagsEntryServiceBaseImpl;
import com.liferay.util.UniqueList;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.mail.internet.InternetAddress;

/**
 * <a href="FlagsEntryServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Julio Camarero
 */
public class FlagsEntryServiceImpl extends FlagsEntryServiceBaseImpl {

	public void addEntry(
			String className, long classPK, String reporterEmailAddress,
			long reportedUserId, String contentTitle, String contentURL,
			String reason, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Company

		long companyId = serviceContext.getCompanyId();

		Company company = companyPersistence.findByPrimaryKey(
			serviceContext.getCompanyId());

		// Group

		Layout layout = layoutPersistence.findByPrimaryKey(
			serviceContext.getPlid());

		Group group = layout.getGroup();

		String groupName = group.getDescriptiveName();

		// Reporter user

		String reporterUserName = null;

		User reporterUser = getUser();

		Locale locale = LocaleUtil.getDefault();

		if (reporterUser.isDefaultUser()) {
			reporterUserName = LanguageUtil.get(locale, "anonymous");
		}
		else {
			reporterUserName = reporterUser.getFullName();
			reporterEmailAddress = reporterUser.getEmailAddress();
		}

		// Reported user

		String reportedUserName = StringPool.BLANK;
		String reportedEmailAddress = StringPool.BLANK;
		String reportedURL = StringPool.BLANK;

		User reportedUser = userPersistence.findByPrimaryKey(reportedUserId);

		if (reportedUser.isDefaultUser()){
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
			locale, "model.resource." + className);

		// Reason

		reason = LanguageUtil.get(locale, reason);

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
					classPK, contentTitle, contentType, contentURL, reason,
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

		Group group = groupLocalService.getGroup(groupId);

		if (group.isCommunity()) {
			roleNames.add(RoleConstants.COMMUNITY_ADMINISTRATOR);
			roleNames.add(RoleConstants.COMMUNITY_OWNER);
		}
		else if (group.isCompany()) {
			roleNames.add(RoleConstants.ADMINISTRATOR);
		}
		else if (group.isOrganization()){
			roleNames.add(RoleConstants.ORGANIZATION_ADMINISTRATOR);
			roleNames.add(RoleConstants.ORGANIZATION_OWNER);
		}

		for (String roleName : roleNames) {
			Role role = roleLocalService.getRole(companyId, roleName);

			List<UserGroupRole> userGroupRoles =
				userGroupRoleLocalService.getUserGroupRolesByGroupAndRole(
					groupId, role.getRoleId());

			for (UserGroupRole userGroupRole : userGroupRoles) {
				recipients.add(userGroupRole.getUser());
			}
		}

		if (recipients.isEmpty()) {
			Role role = roleLocalService.getRole(
				companyId, RoleConstants.ADMINISTRATOR);

			recipients.addAll(userLocalService.getRoleUsers(role.getRoleId()));
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

		mailService.sendEmail(message);
	}

	private static Log _log =
		LogFactoryUtil.getLog(FlagsEntryServiceImpl.class);

}