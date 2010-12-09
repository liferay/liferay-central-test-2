/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.service.impl;

import com.liferay.portal.MembershipRequestCommentsException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.MembershipRequest;
import com.liferay.portal.model.MembershipRequestConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.service.base.MembershipRequestLocalServiceBaseImpl;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.util.UniqueList;

import java.io.IOException;

import java.util.Date;
import java.util.List;

import javax.mail.internet.InternetAddress;

/**
 * @author Jorge Ferrer
 */
public class MembershipRequestLocalServiceImpl
	extends MembershipRequestLocalServiceBaseImpl {

	public MembershipRequest addMembershipRequest(
			long userId, long groupId, String comments)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		validate(comments);

		long membershipRequestId = counterLocalService.increment();

		MembershipRequest membershipRequest =
			membershipRequestPersistence.create(membershipRequestId);

		membershipRequest.setCompanyId(user.getCompanyId());
		membershipRequest.setUserId(userId);
		membershipRequest.setCreateDate(now);
		membershipRequest.setGroupId(groupId);
		membershipRequest.setComments(comments);
		membershipRequest.setStatusId(
			MembershipRequestConstants.STATUS_PENDING);

		membershipRequestPersistence.update(membershipRequest, false);

		try {
			notifyCommunityAdministrators(membershipRequest);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		return membershipRequest;
	}

	public MembershipRequest getMembershipRequest(long membershipRequestId)
		throws PortalException, SystemException {

		return membershipRequestPersistence.findByPrimaryKey(
			membershipRequestId);
	}

	public void deleteMembershipRequests(long groupId) throws SystemException {
		membershipRequestPersistence.removeByGroupId(groupId);
	}

	public void deleteMembershipRequests(long groupId, int statusId)
		throws SystemException {

		membershipRequestPersistence.removeByG_S(groupId, statusId);
	}

	public void deleteMembershipRequestsByUserId(long userId)
		throws SystemException {

		membershipRequestPersistence.removeByUserId(userId);
	}

	public List<MembershipRequest> search(
			long groupId, int status, int start, int end)
		throws SystemException {

		return membershipRequestPersistence.findByG_S(
			groupId, status, start, end);
	}

	public int searchCount(long groupId, int status) throws SystemException {
		return membershipRequestPersistence.countByG_S(groupId, status);
	}

	public void updateStatus(
			long replierUserId, long membershipRequestId, String replyComments,
			int statusId)
		throws PortalException, SystemException {

		validate(replyComments);

		MembershipRequest membershipRequest =
			membershipRequestPersistence.findByPrimaryKey(
				membershipRequestId);

		membershipRequest.setReplyComments(replyComments);
		membershipRequest.setReplyDate(new Date());
		membershipRequest.setReplierUserId(replierUserId);
		membershipRequest.setStatusId(statusId);

		membershipRequestPersistence.update(membershipRequest, false);

		if (statusId == MembershipRequestConstants.STATUS_APPROVED) {
			long[] addUserIds = new long[] {membershipRequest.getUserId()};

			Group group = groupPersistence.findByPrimaryKey(
				membershipRequest.getGroupId());

			if (group.isOrganization()) {
				userLocalService.addOrganizationUsers(
					group.getOrganizationId(),
					new long[] {membershipRequest.getUserId()});
			}
			else {
				userLocalService.addGroupUsers(
					membershipRequest.getGroupId(), addUserIds);
			}
		}

		try {
			notify(
				membershipRequest.getUserId(), membershipRequest,
				PropsKeys.COMMUNITIES_EMAIL_MEMBERSHIP_REPLY_SUBJECT,
				PropsKeys.COMMUNITIES_EMAIL_MEMBERSHIP_REPLY_BODY);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	protected void notify(
			long userId, MembershipRequest membershipRequest,
			String subjectProperty, String bodyProperty)
		throws IOException, PortalException, SystemException {

		Company company = companyPersistence.findByPrimaryKey(
			membershipRequest.getCompanyId());

		Group group = groupPersistence.findByPrimaryKey(
			membershipRequest.getGroupId());

		User user = userPersistence.findByPrimaryKey(userId);
		User requestUser = userPersistence.findByPrimaryKey(
			membershipRequest.getUserId());

		String fromName = PrefsPropsUtil.getString(
			membershipRequest.getCompanyId(),
			PropsKeys.COMMUNITIES_EMAIL_FROM_NAME);

		String fromAddress = PrefsPropsUtil.getString(
			membershipRequest.getCompanyId(),
			PropsKeys.COMMUNITIES_EMAIL_FROM_ADDRESS);

		String toName = user.getFullName();
		String toAddress = user.getEmailAddress();

		String subject = PrefsPropsUtil.getContent(
			membershipRequest.getCompanyId(), subjectProperty);

		String body = PrefsPropsUtil.getContent(
			membershipRequest.getCompanyId(), bodyProperty);

		String statusKey = null;

		if (membershipRequest.getStatusId() ==
				MembershipRequestConstants.STATUS_APPROVED) {

			statusKey = "approved";
		}
		else if (membershipRequest.getStatusId() ==
					MembershipRequestConstants.STATUS_DENIED) {

			statusKey = "denied";
		}
		else {
			statusKey = "pending";
		}

		subject = StringUtil.replace(
			subject,
			new String[] {
				"[$COMMUNITY_NAME$]",
				"[$COMPANY_ID$]",
				"[$COMPANY_MX$]",
				"[$COMPANY_NAME$]",
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$PORTAL_URL$]",
				"[$REQUEST_USER_ADDRESS$]",
				"[$REQUEST_USER_NAME$]",
				"[$STATUS$]",
				"[$TO_NAME$]",
				"[$USER_ADDRESS$]",
				"[$USER_NAME$]",
			},
			new String[] {
				group.getName(),
				String.valueOf(company.getCompanyId()),
				company.getMx(),
				company.getName(),
				fromAddress,
				fromName,
				company.getVirtualHostname(),
				requestUser.getEmailAddress(),
				requestUser.getFullName(),
				LanguageUtil.get(user.getLocale(), statusKey),
				toName,
				user.getEmailAddress(),
				user.getFullName()
			});

		body = StringUtil.replace(
			body,
			new String[] {
				"[$COMMENTS$]",
				"[$COMMUNITY_NAME$]",
				"[$COMPANY_ID$]",
				"[$COMPANY_MX$]",
				"[$COMPANY_NAME$]",
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$PORTAL_URL$]",
				"[$REPLY_COMMENTS$]",
				"[$REQUEST_USER_NAME$]",
				"[$REQUEST_USER_ADDRESS$]",
				"[$STATUS$]",
				"[$TO_NAME$]",
				"[$USER_ADDRESS$]",
				"[$USER_NAME$]",
			},
			new String[] {
				membershipRequest.getComments(),
				group.getName(),
				String.valueOf(company.getCompanyId()),
				company.getMx(),
				company.getName(),
				fromAddress,
				fromName,
				company.getVirtualHostname(),
				membershipRequest.getReplyComments(),
				requestUser.getFullName(),
				requestUser.getEmailAddress(),
				LanguageUtil.get(user.getLocale(), statusKey),
				toName,
				user.getEmailAddress(),
				user.getFullName()
			});

		InternetAddress from = new InternetAddress(fromAddress, fromName);

		InternetAddress to = new InternetAddress(toAddress, toName);

		MailMessage message = new MailMessage(from, to, subject, body, true);

		mailService.sendEmail(message);
	}

	protected void notifyCommunityAdministrators(
			MembershipRequest membershipRequest)
		throws IOException, PortalException, SystemException {

		List<UserGroupRole> admins = new UniqueList<UserGroupRole>();

		Role communityAdminRole = roleLocalService.getRole(
			membershipRequest.getCompanyId(),
			RoleConstants.COMMUNITY_ADMINISTRATOR);

		List<UserGroupRole> communityAdmins =
			userGroupRoleLocalService.getUserGroupRolesByGroupAndRole(
				membershipRequest.getGroupId(), communityAdminRole.getRoleId());

		admins.addAll(communityAdmins);

		Role communityOwnerRole = rolePersistence.findByC_N(
			membershipRequest.getCompanyId(), RoleConstants.COMMUNITY_OWNER);

		List<UserGroupRole> communityOwners =
			userGroupRoleLocalService.getUserGroupRolesByGroupAndRole(
				membershipRequest.getGroupId(), communityOwnerRole.getRoleId());

		admins.addAll(communityOwners);

		for (UserGroupRole userGroupRole : admins) {
			notify(
				userGroupRole.getUserId(), membershipRequest,
				PropsKeys.COMMUNITIES_EMAIL_MEMBERSHIP_REQUEST_SUBJECT,
				PropsKeys.COMMUNITIES_EMAIL_MEMBERSHIP_REQUEST_BODY);
		}
	}

	protected void validate(String comments)
		throws PortalException {

		if ((Validator.isNull(comments)) || (Validator.isNumber(comments))) {
			throw new MembershipRequestCommentsException();
		}
	}

}