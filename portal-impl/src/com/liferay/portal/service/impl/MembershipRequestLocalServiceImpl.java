/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.MembershipRequestCommentsException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.MembershipRequest;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.model.impl.MembershipRequestImpl;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.base.MembershipRequestLocalServiceBaseImpl;
import com.liferay.portal.service.persistence.MembershipRequestUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.RoleNames;
import com.liferay.util.UniqueList;

import java.io.IOException;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.mail.internet.InternetAddress;

/**
 * <a href="MembershipRequestLocalServiceImpl.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class MembershipRequestLocalServiceImpl
	extends MembershipRequestLocalServiceBaseImpl {

	public MembershipRequest addMembershipRequest(
		long userId, long groupId, String comments)
		throws PortalException, SystemException {

		validate(comments);

		User user = UserUtil.findByPrimaryKey(userId);
		Date now = new Date();

		long requestId = CounterLocalServiceUtil.increment();

		MembershipRequest membershipRequest =
			MembershipRequestUtil.create(requestId);

		membershipRequest.setCompanyId(user.getCompanyId());
		membershipRequest.setUserId(userId);
		membershipRequest.setGroupId(groupId);
		membershipRequest.setComments(comments);
		membershipRequest.setCreateDate(now);
		membershipRequest.setStatusId(MembershipRequestImpl.STATUS_PENDING);

		MembershipRequestUtil.update(membershipRequest);

		notifyCommunityAdministrators(membershipRequest);

		return membershipRequest;
	}

	public MembershipRequest getMembershipRequest(long membershipRequestId)
		throws PortalException, SystemException {
		return MembershipRequestUtil.findByPrimaryKey(membershipRequestId);
	}

	public void deleteByGroupId(long groupId) throws SystemException {
		MembershipRequestUtil.removeBygroupId(groupId);
	}

	public void deleteByGroupIdAndStatus(long groupId, int statusId)
		throws SystemException {
		MembershipRequestUtil.removeByG_S(groupId, statusId);
	}

	public List search(long groupId, int status, int begin, int end)
		throws SystemException {
		return MembershipRequestUtil.findByG_S(groupId, status, begin, end);
	}

	public int count(long groupId, int status)
		throws SystemException {
		return MembershipRequestUtil.countByG_S(groupId, status);
	}

	public void updateStatus(
		long replierUserId, long membershipRequestId, String replyComments,
		int statusId)
		throws PortalException, SystemException {

		MembershipRequest membershipRequest =
			getMembershipRequest(membershipRequestId);

		validate(replyComments);

		Date now = new Date();

		if (statusId == MembershipRequestImpl.STATUS_APPROVED) {
			long[] addUserIds = new long[]{membershipRequest.getUserId()};

			UserLocalServiceUtil.addGroupUsers(
				membershipRequest.getGroupId(), addUserIds);
		}

		membershipRequest.setStatusId(statusId);
		membershipRequest.setReplyComments(replyComments);
		membershipRequest.setReplyDate(now);
		membershipRequest.setReplierUserId(replierUserId);

		MembershipRequestUtil.update(membershipRequest);

		notify(
			membershipRequest.getUserId(), membershipRequest,
			PropsUtil.COMMUNITIES_EMAIL_MEMBERSHIP_REPLY_SUBJECT,
			PropsUtil.COMMUNITIES_EMAIL_MEMBERSHIP_REPLY_BODY);
	}

	protected void validate(String comments)
		throws PortalException, SystemException {

		if ((Validator.isNull(comments)) || (Validator.isNumber(comments))) {

			throw new MembershipRequestCommentsException();
		}
	}

	protected void notifyCommunityAdministrators(
		MembershipRequest membershipRequest)
		throws PortalException, SystemException {

		List administrators = new UniqueList();

		Role roleCommunityAdmin = RoleLocalServiceUtil.getRole(
			membershipRequest.getCompanyId(),
			RoleNames.COMMUNITY_ADMINISTRATOR);

		List communityAdmins =
			UserGroupRoleLocalServiceUtil.getUserGroupRolesByGroupAndRole(
				membershipRequest.getGroupId(), roleCommunityAdmin.getRoleId());

		administrators.addAll(communityAdmins);

		Role roleCommunityOwner = RoleLocalServiceUtil.getRole(
			membershipRequest.getCompanyId(),
			RoleNames.COMMUNITY_OWNER);

		List communityOwners =
			UserGroupRoleLocalServiceUtil.getUserGroupRolesByGroupAndRole(
				membershipRequest.getGroupId(), roleCommunityOwner.getRoleId());

		administrators.addAll(communityOwners);

		// TODO: Should we add users that have MANAGE permission in the group?

		for (Iterator it = administrators.iterator(); it.hasNext();) {
			UserGroupRole userGroupRole = (UserGroupRole) it.next();

			notify(
				userGroupRole.getUserId(), membershipRequest,
				PropsUtil.COMMUNITIES_EMAIL_MEMBERSHIP_REQUEST_SUBJECT,
				PropsUtil.COMMUNITIES_EMAIL_MEMBERSHIP_REQUEST_BODY);
		}
	}

	protected void notify(
		long userId, MembershipRequest membershipRequest,
		String subjectProperty, String bodyProperty)
		throws PortalException, SystemException {

		try {
			Group group = GroupServiceUtil.getGroup(
				membershipRequest.getGroupId());

			String fromName = PrefsPropsUtil.getString(
				membershipRequest.getCompanyId(),
				PropsUtil.COMMUNITIES_EMAIL_FROM_NAME);

			String fromAddress = PrefsPropsUtil.getString(
				membershipRequest.getCompanyId(),
				PropsUtil.COMMUNITIES_EMAIL_FROM_ADDRESS);

			User user = UserLocalServiceUtil.getUserById(userId);
			Company company = CompanyLocalServiceUtil.getCompanyById(
				membershipRequest.getCompanyId());

			String toName = user.getFullName();
			String toAddress = user.getEmailAddress();

			String subject = PrefsPropsUtil.getContent(
				membershipRequest.getCompanyId(), subjectProperty);

			String body = PrefsPropsUtil.getContent(
				membershipRequest.getCompanyId(), bodyProperty);

			String statusKey;

			if (membershipRequest.getStatusId() ==
					MembershipRequestImpl.STATUS_APPROVED) {
				statusKey = "approved";
			}
			else if (membershipRequest.getStatusId() ==
					MembershipRequestImpl.STATUS_DENIED) {
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
					"[$FROM_NAME$]",
					"[$FROM_ADDRESS$]",
					"[$PORTAL_URL$]",
					"[$STATUS$]",
					"[$TO_NAME$]",
					"[$USER_EMAIL$]",
					"[$USER_NAME$]",
				},
				new String[] {
					group.getName(),
					String.valueOf(company.getCompanyId()),
					company.getMx(),
					company.getName(),
					fromName,
					fromAddress,
					company.getVirtualHost(),
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
					"[$FROM_NAME$]",
					"[$FROM_ADDRESS$]",
					"[$PORTAL_URL$]",
					"[$REPLY_COMMENTS$]",
					"[$STATUS$]",
					"[$TO_NAME$]",
					"[$USER_EMAIL$]",
					"[$USER_NAME$]",
				},
				new String[] {
					membershipRequest.getComments(),
					group.getName(),
					String.valueOf(company.getCompanyId()),
					company.getMx(),
					company.getName(),
					fromName,
					fromAddress,
					company.getVirtualHost(),
					membershipRequest.getReplyComments(),
					LanguageUtil.get(user.getLocale(), statusKey),
					toName,
					user.getEmailAddress(),
					user.getFullName()
				});

			// TODO: Should we implement a JMS Producer/Consumer?

			InternetAddress from = new InternetAddress(fromAddress, fromName);

			InternetAddress to = new InternetAddress(toAddress, toName);

			MailMessage message = new MailMessage(
				from, to, subject, body, true);

			MailServiceUtil.sendEmail(message);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

}