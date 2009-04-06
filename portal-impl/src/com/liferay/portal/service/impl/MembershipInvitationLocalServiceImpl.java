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

package com.liferay.portal.service.impl;

import com.liferay.portal.MembershipInvitationAlreadyUsedException;
import com.liferay.portal.MembershipInvitationUserNotInvitedException;
import com.liferay.portal.NoSuchMembershipInvitationException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.MembershipInvitation;
import com.liferay.portal.model.User;
import com.liferay.portal.service.base.MembershipInvitationLocalServiceBaseImpl;
import com.liferay.portal.util.ContentUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsKeys;

import java.util.Date;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * <a href="MembershipInvitationLocalServiceImpl.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Brett Swaim
 *
 */
public class MembershipInvitationLocalServiceImpl
	extends MembershipInvitationLocalServiceBaseImpl {

	public MembershipInvitation accept(long membershipInvitationId, long userId)
		throws Exception {

		MembershipInvitation membershipInvitation =
			membershipInvitationPersistence.findByPrimaryKey(
				membershipInvitationId);

		return accept(membershipInvitation.getKey(), userId);
	}

	public MembershipInvitation accept(
			String membershipInvitationKey, long userId)
		throws Exception {

		Date now = new Date();
		long[] userIds = {userId};

		MembershipInvitation membershipInvitation =
			membershipInvitationPersistence.findByKey(membershipInvitationKey);

		validate(membershipInvitation, userId);

		membershipInvitation.setAcceptedDate(now);

		userLocalService.addGroupUsers(
			membershipInvitation.getGroupId(), userIds);

		return membershipInvitationPersistence.update(
			membershipInvitation, false);
	}

	public MembershipInvitation addMembershipInvitation(
			long companyId, long groupId, long userId, long invitedUserId)
		throws SystemException {

		Date now = new Date();

		MembershipInvitation membershipInvitation =
			membershipInvitationPersistence.create(
				counterLocalService.increment());

		membershipInvitation.setCompanyId(companyId);
		membershipInvitation.setUserId(userId);
		membershipInvitation.setGroupId(groupId);
		membershipInvitation.setCreateDate(now);
		membershipInvitation.setKey(PortalUUIDUtil.generate());
		membershipInvitation.setInvitedUserId(invitedUserId);

		return membershipInvitationPersistence.update(
			membershipInvitation, false);
	}

	public MembershipInvitation decline(
			long membershipInvitationId, long userId)
		throws Exception {

		MembershipInvitation membershipInvitation =
			membershipInvitationPersistence.findByPrimaryKey(
				membershipInvitationId);

		return decline(membershipInvitation.getKey(), userId);
	}

	public MembershipInvitation decline(
			String membershipInvitationKey, long userId)
		throws Exception {

		Date now = new Date();

		MembershipInvitation membershipInvitation =
			membershipInvitationPersistence.findByKey(membershipInvitationKey);

		validate(membershipInvitation, userId);

		membershipInvitation.setDeclinedDate(now);

		return membershipInvitationPersistence.update(
			membershipInvitation, false);
	}

	public MembershipInvitation getMembershipInvitation(String key)
		throws SystemException, NoSuchMembershipInvitationException {

		return membershipInvitationPersistence.findByKey(key);
	}

	public void invite(
			long companyId, long groupId, Map<String, String> entries)
		throws Exception {

		for (Map.Entry<String, String> entry : entries.entrySet()) {
			String email = entry.getKey();
			String url = entry.getValue();
			String name;

			try {
				User user = userLocalService.getUserByEmailAddress(
					companyId, email);

				name = user.getFullName();
			}
			catch (NoSuchUserException nsue) {
				name = email;
			}

			sendInvite(companyId, groupId, name, email, url);
		}
	}

	protected void sendInvite(
			long companyId, long groupId, String fullName, String email,
			String url)
		throws Exception {

		Group group = groupLocalService.getGroup(groupId);

		InternetAddress from = new InternetAddress(
			email, fullName);

		String body = ContentUtil.get(
			PrefsPropsUtil.getString(
				companyId,
				PropsKeys.COMMUNITIES_MEMBERSHIP_INVITATION_EMAIL_BODY));

		String subject = ContentUtil.get(
			PrefsPropsUtil.getString(
				companyId,
				PropsKeys.COMMUNITIES_MEMBERSHIP_INVITATION_EMAIL_SUBJECT));

		body = StringUtil.replace(
			body,
			new String[]
			{
				"[$COMMUNITY_NAME$]",
				"[$PORTAL_URL$]",
				"[$SENDER_EMAIL_ADDRESS$]",
				"[$SENDER_FULL_NAME$]"
			},
			new String[]
			{
				group.getName(),
				url,
				email,
				fullName
			}

		);

		MailMessage mailMessage = new MailMessage(from, subject, body, true);

		try {
			InternetAddress to = new InternetAddress(email);

			mailMessage.setTo(to);

			mailService.sendEmail(mailMessage);
		}
		catch (AddressException ae) {
			if (_log.isWarnEnabled()) {
				_log.warn("Invalid email address " + email);
			}
		}
	}

	protected void validate(
			MembershipInvitation membershipInvitation, long userId)
		throws Exception {

		long invitedUserId = membershipInvitation.getInvitedUserId();

		if (Validator.isNotNull(membershipInvitation.getAcceptedDate())) {
			throw new MembershipInvitationAlreadyUsedException();
		}

		if (invitedUserId != 0 && invitedUserId != userId) {
			throw new MembershipInvitationUserNotInvitedException();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		MembershipInvitationLocalServiceImpl.class);

}