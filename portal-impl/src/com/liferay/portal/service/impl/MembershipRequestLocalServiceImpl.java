/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailService;
import com.liferay.mail.kernel.template.MailTemplate;
import com.liferay.mail.kernel.template.MailTemplateContext;
import com.liferay.mail.kernel.template.MailTemplateContextBuilder;
import com.liferay.mail.kernel.template.MailTemplateFactoryUtil;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.MembershipRequestCommentsException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.MembershipRequest;
import com.liferay.portal.kernel.model.MembershipRequestConstants;
import com.liferay.portal.kernel.model.Resource;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.EscapableLocalizableFunction;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.base.MembershipRequestLocalServiceBaseImpl;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.ResourcePermissionUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.mail.internet.InternetAddress;

/**
 * @author Jorge Ferrer
 */
public class MembershipRequestLocalServiceImpl
	extends MembershipRequestLocalServiceBaseImpl {

	@Override
	public MembershipRequest addMembershipRequest(
			long userId, long groupId, String comments,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		validate(comments);

		long membershipRequestId = counterLocalService.increment();

		MembershipRequest membershipRequest =
			membershipRequestPersistence.create(membershipRequestId);

		membershipRequest.setCompanyId(user.getCompanyId());
		membershipRequest.setUserId(userId);
		membershipRequest.setCreateDate(new Date());
		membershipRequest.setGroupId(groupId);
		membershipRequest.setComments(comments);
		membershipRequest.setStatusId(
			MembershipRequestConstants.STATUS_PENDING);

		membershipRequestPersistence.update(membershipRequest);

		notifyGroupAdministrators(membershipRequest, serviceContext);

		return membershipRequest;
	}

	@Override
	public void deleteMembershipRequests(long groupId) {
		List<MembershipRequest> membershipRequests =
			membershipRequestPersistence.findByGroupId(groupId);

		for (MembershipRequest membershipRequest : membershipRequests) {
			deleteMembershipRequest(membershipRequest);
		}
	}

	@Override
	public void deleteMembershipRequests(long groupId, long statusId) {
		List<MembershipRequest> membershipRequests =
			membershipRequestPersistence.findByG_S(groupId, statusId);

		for (MembershipRequest membershipRequest : membershipRequests) {
			deleteMembershipRequest(membershipRequest);
		}
	}

	@Override
	public void deleteMembershipRequestsByUserId(long userId) {
		List<MembershipRequest> membershipRequests =
			membershipRequestPersistence.findByUserId(userId);

		for (MembershipRequest membershipRequest : membershipRequests) {
			deleteMembershipRequest(membershipRequest);
		}
	}

	@Override
	public List<MembershipRequest> getMembershipRequests(
		long userId, long groupId, long statusId) {

		return membershipRequestPersistence.findByG_U_S(
			groupId, userId, statusId);
	}

	@Override
	public boolean hasMembershipRequest(
		long userId, long groupId, long statusId) {

		List<MembershipRequest> membershipRequests = getMembershipRequests(
			userId, groupId, statusId);

		if (membershipRequests.isEmpty()) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public List<MembershipRequest> search(
		long groupId, int status, int start, int end) {

		return membershipRequestPersistence.findByG_S(
			groupId, status, start, end);
	}

	@Override
	public List<MembershipRequest> search(
		long groupId, int status, int start, int end,
		OrderByComparator<MembershipRequest> obc) {

		return membershipRequestPersistence.findByG_S(
			groupId, status, start, end, obc);
	}

	@Override
	public int searchCount(long groupId, int status) {
		return membershipRequestPersistence.countByG_S(groupId, status);
	}

	@Override
	public void updateStatus(
			long replierUserId, long membershipRequestId, String replyComments,
			long statusId, boolean addUserToGroup,
			ServiceContext serviceContext)
		throws PortalException {

		validate(replyComments);

		MembershipRequest membershipRequest =
			membershipRequestPersistence.findByPrimaryKey(membershipRequestId);

		membershipRequest.setReplyComments(replyComments);
		membershipRequest.setReplyDate(new Date());

		if (replierUserId != 0) {
			membershipRequest.setReplierUserId(replierUserId);
		}
		else {
			long defaultUserId = userLocalService.getDefaultUserId(
				membershipRequest.getCompanyId());

			membershipRequest.setReplierUserId(defaultUserId);
		}

		membershipRequest.setStatusId(statusId);

		membershipRequestPersistence.update(membershipRequest);

		if ((statusId == MembershipRequestConstants.STATUS_APPROVED) &&
			addUserToGroup) {

			long[] addUserIds = new long[] {membershipRequest.getUserId()};

			userLocalService.addGroupUsers(
				membershipRequest.getGroupId(), addUserIds);
		}

		if (replierUserId != 0) {
			notify(
				membershipRequest.getUserId(), membershipRequest,
				PropsKeys.SITES_EMAIL_MEMBERSHIP_REPLY_SUBJECT,
				PropsKeys.SITES_EMAIL_MEMBERSHIP_REPLY_BODY, serviceContext);
		}
	}

	protected List<Long> getGroupAdministratorUserIds(long groupId)
		throws PortalException {

		Set<Long> userIds = new LinkedHashSet<>();

		Group group = groupLocalService.getGroup(groupId);
		String modelResource = Group.class.getName();

		List<Role> roles = ListUtil.copy(
			ResourceActionsUtil.getRoles(
				group.getCompanyId(), group, modelResource, null));

		List<Role> teamRoles = roleLocalService.getTeamRoles(groupId);

		roles.addAll(teamRoles);

		Resource resource = resourceLocalService.getResource(
			group.getCompanyId(), modelResource,
			ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(groupId));

		List<String> actions = ResourceActionsUtil.getResourceActions(
			Group.class.getName());

		for (Role role : roles) {
			String roleName = role.getName();

			if (roleName.equals(RoleConstants.OWNER)) {
				continue;
			}

			if ((roleName.equals(RoleConstants.ORGANIZATION_ADMINISTRATOR) ||
				 roleName.equals(RoleConstants.ORGANIZATION_OWNER)) &&
				!group.isOrganization()) {

				continue;
			}

			if (roleName.equals(RoleConstants.SITE_ADMINISTRATOR) ||
				roleName.equals(RoleConstants.SITE_OWNER) ||
				roleName.equals(RoleConstants.ORGANIZATION_ADMINISTRATOR) ||
				roleName.equals(RoleConstants.ORGANIZATION_OWNER)) {

				Role curRole = roleLocalService.getRole(
					group.getCompanyId(), roleName);

				List<UserGroupRole> userGroupRoles =
					userGroupRoleLocalService.getUserGroupRolesByGroupAndRole(
						groupId, curRole.getRoleId());

				for (UserGroupRole userGroupRole : userGroupRoles) {
					userIds.add(userGroupRole.getUserId());
				}
			}

			List<String> currentIndividualActions = new ArrayList<>();
			List<String> currentGroupActions = new ArrayList<>();
			List<String> currentGroupTemplateActions = new ArrayList<>();
			List<String> currentCompanyActions = new ArrayList<>();

			ResourcePermissionUtil.populateResourcePermissionActionIds(
				groupId, role, resource, actions, currentIndividualActions,
				currentGroupActions, currentGroupTemplateActions,
				currentCompanyActions);

			if (currentIndividualActions.contains(ActionKeys.ASSIGN_MEMBERS) ||
				currentGroupActions.contains(ActionKeys.ASSIGN_MEMBERS) ||
				currentGroupTemplateActions.contains(
					ActionKeys.ASSIGN_MEMBERS) ||
				currentCompanyActions.contains(ActionKeys.ASSIGN_MEMBERS)) {

				List<UserGroupRole> currentUserGroupRoles =
					userGroupRoleLocalService.getUserGroupRolesByGroupAndRole(
						groupId, role.getRoleId());

				for (UserGroupRole userGroupRole : currentUserGroupRoles) {
					userIds.add(userGroupRole.getUserId());
				}
			}
		}

		return new ArrayList<>(userIds);
	}

	protected void notify(
			long userId, MembershipRequest membershipRequest,
			String subjectProperty, String bodyProperty,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);
		User requestUser = userPersistence.findByPrimaryKey(
			membershipRequest.getUserId());

		String fromName = PrefsPropsUtil.getStringFromNames(
			membershipRequest.getCompanyId(), PropsKeys.SITES_EMAIL_FROM_NAME,
			PropsKeys.ADMIN_EMAIL_FROM_NAME);

		String fromAddress = PrefsPropsUtil.getStringFromNames(
			membershipRequest.getCompanyId(),
			PropsKeys.SITES_EMAIL_FROM_ADDRESS,
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);

		String toAddress = user.getEmailAddress();

		String subject = PrefsPropsUtil.getContent(
			membershipRequest.getCompanyId(), subjectProperty);

		String body = PrefsPropsUtil.getContent(
			membershipRequest.getCompanyId(), bodyProperty);

		final String statusKey;

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

		Company company = companyLocalService.getCompany(user.getCompanyId());

		String portalURL = company.getPortalURL(0);

		MailTemplateContextBuilder mailTemplateContextBuilder =
			MailTemplateFactoryUtil.createMailTemplateContextBuilder();

		mailTemplateContextBuilder.put(
			"[$COMPANY_ID$]", String.valueOf(company.getCompanyId()));
		mailTemplateContextBuilder.put("[$COMPANY_MX$]", company.getMx());
		mailTemplateContextBuilder.put("[$COMPANY_NAME$]", company.getName());
		mailTemplateContextBuilder.put(
			"[$COMMENTS$]", membershipRequest.getComments());
		mailTemplateContextBuilder.put("[$FROM_ADDRESS$]", fromAddress);
		mailTemplateContextBuilder.put("[$FROM_NAME$]", fromName);
		mailTemplateContextBuilder.put("[$PORTAL_URL$]", portalURL);
		mailTemplateContextBuilder.put(
			"[$REPLY_COMMENTS$]", membershipRequest.getReplyComments());
		mailTemplateContextBuilder.put(
			"[$REQUEST_USER_ADDRESS$]", requestUser.getEmailAddress());
		mailTemplateContextBuilder.put(
			"[$REQUEST_USER_NAME$]", requestUser.getFullName());

		Group group = groupLocalService.getGroup(
			membershipRequest.getGroupId());

		mailTemplateContextBuilder.put(
			"[$SITE_NAME$]", group.getDescriptiveName());

		mailTemplateContextBuilder.put(
			"[$STATUS$]",
			new EscapableLocalizableFunction(
				locale -> LanguageUtil.get(locale, statusKey)));
		mailTemplateContextBuilder.put(
			"[$TO_ADDRESS$]", user.getEmailAddress());
		mailTemplateContextBuilder.put("[$TO_NAME$]", user.getFullName());
		mailTemplateContextBuilder.put(
			"[$USER_ADDRESS$]", user.getEmailAddress());
		mailTemplateContextBuilder.put("[$USER_NAME$]", user.getFullName());

		MailTemplateContext mailTemplateContext =
			mailTemplateContextBuilder.build();

		_sendNotificationEmail(
			fromAddress, fromName, toAddress, user, subject, body,
			membershipRequest, mailTemplateContext);
	}

	protected void notifyGroupAdministrators(
			MembershipRequest membershipRequest, ServiceContext serviceContext)
		throws PortalException {

		List<Long> userIds = getGroupAdministratorUserIds(
			membershipRequest.getGroupId());

		for (Long userId : userIds) {
			notify(
				userId, membershipRequest,
				PropsKeys.SITES_EMAIL_MEMBERSHIP_REQUEST_SUBJECT,
				PropsKeys.SITES_EMAIL_MEMBERSHIP_REQUEST_BODY, serviceContext);
		}
	}

	protected void validate(String comments) throws PortalException {
		if (Validator.isNull(comments) || Validator.isNumber(comments)) {
			throw new MembershipRequestCommentsException();
		}
	}

	@BeanReference(type = MailService.class)
	protected MailService mailService;

	private void _sendNotificationEmail(
			String fromAddress, String fromName, String toAddress, User toUser,
			String subject, String body, MembershipRequest membershipRequest,
			MailTemplateContext mailTemplateContext)
		throws PortalException {

		try {
			MailTemplate subjectTemplate =
				MailTemplateFactoryUtil.createMailTemplate(subject, false);

			MailTemplate bodyTemplate =
				MailTemplateFactoryUtil.createMailTemplate(body, true);

			MailMessage mailMessage = new MailMessage(
				new InternetAddress(fromAddress, fromName),
				new InternetAddress(toAddress, toUser.getFullName()),
				subjectTemplate.renderAsString(
					toUser.getLocale(), mailTemplateContext),
				bodyTemplate.renderAsString(
					toUser.getLocale(), mailTemplateContext),
				true);

			Company company = companyLocalService.getCompany(
				toUser.getCompanyId());

			mailMessage.setMessageId(
				PortalUtil.getMailId(
					company.getMx(), "membership_request",
					membershipRequest.getMembershipRequestId()));

			mailService.sendEmail(mailMessage);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

}