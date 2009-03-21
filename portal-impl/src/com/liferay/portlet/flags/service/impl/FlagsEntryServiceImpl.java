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
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
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
 *
 */
public class FlagsEntryServiceImpl extends FlagsEntryServiceBaseImpl {

	public void addFlagEntry(
			String className, long classPK, long reportedUserId,
			String contentTitle, String contentURL,	String reason,
			String emailAddress, ServiceContext serviceContext)
		throws Exception{

		// Company

		long companyId = serviceContext.getCompanyId();
		Company company = companyPersistence.findByPrimaryKey(
			serviceContext.getCompanyId());

		// Group

		Layout layout = layoutPersistence.findByPrimaryKey(
			serviceContext.getPlid());

		Group group = layout.getGroup();

		// Reporter User

		String reporterUserName = null;
		String reporterUserEmailAddress = null;

		User reporterUser = userPersistence.findByPrimaryKey(
			serviceContext.getUserId());

		Locale locale = LocaleUtil.getDefault();

		if (reporterUser == null || reporterUser.isDefaultUser()) {
			reporterUserName = LanguageUtil.get(locale, "an-anonymous-user");

			if (Validator.isNotNull(emailAddress)) {
				reporterUserEmailAddress = emailAddress;
			}
			else {
				reporterUserEmailAddress = LanguageUtil.get(
					locale, "no-email-address-provided");
			}
		}
		else {
			reporterUserName = reporterUser.getFullName();
			reporterUserEmailAddress = reporterUser.getEmailAddress();
		}

		// Reported User
		String reportedUserName = StringPool.BLANK;
		String reportedUserEmailAddress = StringPool.BLANK;
		String reportedUserURL = StringPool.BLANK;

		User reportedUser = userPersistence.findByPrimaryKey(reportedUserId);
		if (reportedUser.isDefaultUser()){
			reportedUserName = group.getDescriptiveName();
		}
		else {
			reportedUserName = reportedUser.getFullName();
			reportedUserEmailAddress = reportedUser.getEmailAddress();
			reportedUserURL = reportedUser.getDisplayURL(
			serviceContext.getPortalURL(), serviceContext.getPathMain());
		}


		// Email

		String fromName = PrefsPropsUtil.getString(
			companyId, PropsKeys.FLAGS_EMAIL_FROM_NAME);

		String fromAddress = PrefsPropsUtil.getString(
			companyId, PropsKeys.FLAGS_EMAIL_FROM_ADDRESS);

		String subject = PrefsPropsUtil.getContent(
			companyId, PropsKeys.FLAGS_EMAIL_SUBJECT);

		String body = PrefsPropsUtil.getContent(
			companyId, PropsKeys.FLAGS_EMAIL_BODY);

		List<User> receivers = getAdministrators(
			companyId, serviceContext.getScopeGroupId());

		String localizedReason = LanguageUtil.get(locale, reason);
		String contentType = LanguageUtil.get(
			locale, "model.resource." + className);

		for (User receiver : receivers) {
			notify(
				fromAddress, fromName, receiver.getEmailAddress(),
				receiver.getFullName(), companyId, company.getMx(),
				company.getName(), company.getVirtualHost(),
				group.getDescriptiveName(), reporterUserEmailAddress,
				reporterUserName, reportedUserEmailAddress, reportedUserName,
				reportedUserURL, contentTitle, contentType, contentURL, classPK,
				localizedReason, subject, body);
		}
	}

	protected void notify(
			String fromAddress, String fromName, String toAddress,
			String toName, long companyId, String companyMx, String companyName,
			String portalURL, String groupName, String reporterUserEmailAddress,
			String reporterUserName, String reportedUserEmailAddress,
			String reportedUserName,String reportedUserURL,
			String contentTitle, String contentType, String contentURL,
			long contentId, String reason, String subject, String body)
		throws IOException, PortalException, SystemException {

		Date now = new Date();

		subject = StringUtil.replace(
			subject,
			new String[] {
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$TO_ADDRESS$]",
				"[$TO_NAME$]",
				"[$COMPANY_ID$]",
				"[$COMPANY_MX$]",
				"[$COMPANY_NAME$]",
				"[$PORTAL_URL$]",
				"[$COMMUNITY_NAME$]",
				"[$REPORTER_USER_ADDRESS$]",
				"[$REPORTER_USER_NAME$]",
				"[$REPORTED_USER_ADDRESS$]",
				"[$REPORTED_USER_NAME$]",
				"[$REPORTED_USER_URL$]",
				"[$CONTENT_TITLE$]",
				"[$CONTENT_TYPE$]",
				"[$CONTENT_URL$]",
				"[$CONTENT_ID$]",
				"[$REASON$]",
				"[$DATE$]"
			},
			new String[] {
				fromAddress,
				fromName,
				toAddress,
				toName,
				String.valueOf(companyId),
				companyMx,
				companyName,
				portalURL,
				groupName,
				reporterUserEmailAddress,
				reporterUserName,
				reportedUserEmailAddress,
				reportedUserName,
				reportedUserURL,
				contentTitle,
				contentType,
				contentURL,
				String.valueOf(contentId),
				reason,
				now.toString()
			});

		body = StringUtil.replace(
			body,
			new String[] {
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$TO_ADDRESS$]",
				"[$TO_NAME$]",
				"[$COMPANY_ID$]",
				"[$COMPANY_MX$]",
				"[$COMPANY_NAME$]",
				"[$PORTAL_URL$]",
				"[$COMMUNITY_NAME$]",
				"[$REPORTER_USER_ADDRESS$]",
				"[$REPORTER_USER_NAME$]",
				"[$REPORTED_USER_ADDRESS$]",
				"[$REPORTED_USER_NAME$]",
				"[$REPORTED_USER_URL$]",
				"[$CONTENT_TITLE$]",
				"[$CONTENT_TYPE$]",
				"[$CONTENT_URL$]",
				"[$CONTENT_ID$]",
				"[$REASON$]",
				"[$DATE$]"
			},
			new String[] {
				fromAddress,
				fromName,
				toAddress,
				toName,
				String.valueOf(companyId),
				companyMx,
				companyName,
				portalURL,
				groupName,
				reporterUserEmailAddress,
				reporterUserName,
				reportedUserEmailAddress,
				reportedUserName,
				reportedUserURL,
				contentTitle,
				contentType,
				contentURL,
				String.valueOf(contentId),
				reason,
				now.toString()
			});

		InternetAddress from = new InternetAddress(fromAddress, fromName);

		InternetAddress to = new InternetAddress(toAddress, toName);

		MailMessage message = new MailMessage(from, to, subject, body, true);

		mailService.sendEmail(message);
	}

	protected List<User> getAdministrators(long companyId, long groupId)
		throws IOException, PortalException, SystemException {

		List<User> receivers = new UniqueList<User>();

		List<String> roleNames = new ArrayList<String>();

		Group group = groupLocalService.getGroup(groupId);

		if (group.isCommunity()) {
			roleNames.add(RoleConstants.COMMUNITY_ADMINISTRATOR);
			roleNames.add(RoleConstants.COMMUNITY_OWNER);
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
				receivers.add(userGroupRole.getUser());
			}
		}

		if (receivers.isEmpty()) {
			Role role = roleLocalService.getRole(
				companyId, RoleConstants.ADMINISTRATOR);

			receivers.addAll(userLocalService.getRoleUsers(role.getRoleId()));
		}

		return receivers;
	}

}