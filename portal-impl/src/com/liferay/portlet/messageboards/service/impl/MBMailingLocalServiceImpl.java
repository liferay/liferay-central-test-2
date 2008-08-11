/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.cal.Recurrence;
import com.liferay.portal.kernel.cal.RecurrenceSerializer;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.scheduler.SchedulerEngineUtil;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerRequest;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portlet.messageboards.MailAddressException;
import com.liferay.portlet.messageboards.MailInServerNameException;
import com.liferay.portlet.messageboards.MailInUserNameException;
import com.liferay.portlet.messageboards.MailOutServerNameException;
import com.liferay.portlet.messageboards.MailOutUserNameException;
import com.liferay.portlet.messageboards.MailingListAddressException;
import com.liferay.portlet.messageboards.messaging.EmailReaderRequest;
import com.liferay.portlet.messageboards.model.MBMailing;
import com.liferay.portlet.messageboards.service.base.MBMailingLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * <a href="MBMailingLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Thiago Moreira
 */
public class MBMailingLocalServiceImpl extends MBMailingLocalServiceBaseImpl {

	public MBMailing addMailing(
		long userId, long groupId, long categoryId, String mailingListAddress,
		String mailAddress, String mailInProtocol, String mailInServerName,
		boolean mailInUseSSL, int mailInServerPort, String mailInUserName,
		String mailInPassword, int mailInReadInterval,
		boolean mailOutConfigured, String mailOutServerName,
		boolean mailOutUseSSL, int mailOutServerPort,
		String mailOutUserName, String mailOutPassword)
		throws PortalException, SystemException {

		// Mailing

		_validate(
			mailingListAddress, mailAddress, mailInServerName, mailInUserName,
			mailOutConfigured, mailOutServerName, mailOutUserName);

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		long mailingId = counterLocalService.increment();

		MBMailing mailing = mbMailingPersistence.create(mailingId);
		mailing.setCompanyId(user.getCompanyId());
		mailing.setGroupId(groupId);
		mailing.setUserId(user.getUserId());
		mailing.setUserName(user.getFullName());
		mailing.setCreateDate(now);
		mailing.setModifiedDate(now);

		mailing.setCategoryId(categoryId);

		// mailing

		mailing.setMailingListAddress(mailingListAddress);
		mailing.setMailAddress(mailAddress);

		// incoming

		mailing.setMailInProtocol(mailInProtocol);
		mailing.setMailInServerName(mailInServerName);
		mailing.setMailInUseSSL(mailInUseSSL);
		mailing.setMailInServerPort(mailInServerPort);
		mailing.setMailInUserName(mailInUserName);
		mailing.setMailInPassword(mailInPassword);
		mailing.setMailInReadInterval(mailInReadInterval);

		// outgoing

		if (mailOutConfigured) {
			mailing.setMailOutConfigured(true);
			mailing.setMailOutServerName(mailOutServerName);
			mailing.setMailOutUseSSL(mailOutUseSSL);
			mailing.setMailOutServerPort(mailOutServerPort);
			mailing.setMailOutUserName(mailOutUserName);
			mailing.setMailOutPassword(mailOutPassword);
		}
		else {
			mailing.setMailOutConfigured(false);
		}

		mailing.setActive(true);

		mbMailingPersistence.update(mailing, false);

		// Resource

		addMailingResources(mailing, true, true);

		// adding a listener to the account

		_scheduleEmailReader(mailing);

		return mailing;
	}

	public void addMailingResources(
		MBMailing mailing, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			mailing.getCompanyId(), mailing.getGroupId(), mailing.getUserId(),
			MBMailing.class.getName(), mailing.getMailingId(), false,
			addCommunityPermissions, addGuestPermissions);

	}

	public void deleteMailing(long mailingId)
		throws PortalException, SystemException {

		MBMailing mailing = mbMailingPersistence.findByPrimaryKey(mailingId);

		deleteMailing(mailing);
	}

	public void deleteMailing(MBMailing mailing)
		throws PortalException, SystemException {

		//unscheduling

		_unscheduleEmailReader(mailing);

		// Resources

		resourceLocalService.deleteResource(
			mailing.getCompanyId(), MBMailing.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, mailing.getMailingId());

		deleteMBMailing(mailing);
	}

	public MBMailing getMailingByCategory(long categoryId)
		throws PortalException, SystemException {

		return mbMailingPersistence.findByCategoryId(categoryId);
	}

	public List<MBMailing> getMailings()
		throws PortalException, SystemException {

		return mbMailingPersistence.findAll();
	}

	public void startMailReader()
		throws PortalException, SystemException {

		List<MBMailing> list = mbMailingPersistence.findByActive(true);
		for (MBMailing mailing : list) {
			_scheduleEmailReader(mailing);
		}
	}

	public void stopMailReader()
		throws PortalException, SystemException {

		List<MBMailing> list = mbMailingPersistence.findByActive(true);
		for (MBMailing mailing : list) {
			_unscheduleEmailReader(mailing);
		}
	}

	public MBMailing updateActive(long mailingId, boolean active)
		throws PortalException, SystemException {

		MBMailing mailing = mbMailingPersistence.findByPrimaryKey(mailingId);

		mailing.setActive(active);

		mbMailingPersistence.update(mailing, false);

		if (active) {
			_scheduleEmailReader(mailing);
		}
		else {
			_unscheduleEmailReader(mailing);
		}

		return mailing;
	}

	public MBMailing updateMailing(
		long userId, long groupId, long categoryId, String mailingListAddress,
		String mailAddress, String mailInProtocol, String mailInServerName,
		boolean mailInUseSSL, int mailInServerPort, String mailInUserName,
		String mailInPassword, int mailInReadInterval,
		boolean mailOutConfigured, String mailOutServerName,
		boolean mailOutUseSSL, int mailOutServerPort,
		String mailOutUserName, String mailOutPassword)
		throws PortalException, SystemException {

		// Mailing

		_validate(
			mailingListAddress, mailAddress, mailInServerName, mailInUserName,
			mailOutConfigured, mailOutServerName, mailOutUserName);

		Date now = new Date();

		MBMailing mailing;
		try {

			mailing = getMailingByCategory(categoryId);

		}
		catch (Exception e) {
			User user = userPersistence.findByPrimaryKey(userId);

			long mailingId = counterLocalService.increment();
			mailing = mbMailingPersistence.create(mailingId);

			mailing.setCompanyId(user.getCompanyId());
			mailing.setGroupId(groupId);
			mailing.setUserId(user.getUserId());
			mailing.setUserName(user.getFullName());
			mailing.setCreateDate(now);
			mailing.setCategoryId(categoryId);
		}

		mailing.setModifiedDate(now);

		// mailing

		mailing.setMailingListAddress(mailingListAddress);
		mailing.setMailAddress(mailAddress);

		// incoming

		mailing.setMailInProtocol(mailInProtocol);
		mailing.setMailInServerName(mailInServerName);
		mailing.setMailInUseSSL(mailInUseSSL);
		mailing.setMailInServerPort(mailInServerPort);
		mailing.setMailInUserName(mailInUserName);
		mailing.setMailInPassword(mailInPassword);
		mailing.setMailInReadInterval(mailInReadInterval);

		// outgoing

		if (mailOutConfigured) {
			mailing.setMailOutConfigured(true);
			mailing.setMailOutServerName(mailOutServerName);
			mailing.setMailOutUseSSL(mailOutUseSSL);
			mailing.setMailOutServerPort(mailOutServerPort);
			mailing.setMailOutUserName(mailOutUserName);
			mailing.setMailOutPassword(mailOutPassword);
		}
		else {
			mailing.setMailOutConfigured(false);
		}

		mailing.setActive(true);

		mbMailingPersistence.update(mailing, false);

		// adding a listener to the account

		_scheduleEmailReader(mailing);

		return mailing;
	}

	public MBMailing updateMailOutConfigured(
		long mailingId, boolean mailOutConfigured)
		throws PortalException, SystemException {

		MBMailing mailing = mbMailingPersistence.findByPrimaryKey(mailingId);

		mailing.setMailOutConfigured(mailOutConfigured);

		mbMailingPersistence.update(mailing, false);

		if (mailing.isActive()) {
			_scheduleEmailReader(mailing);
		}
		else {
			_unscheduleEmailReader(mailing);
		}

		return mailing;
	}

	private String _buildGroupName(MBMailing mailing) {

		return mailing.getMailingId() + StringPool.AT + mailing.getCategoryId();
	}

	private SchedulerRequest _getSchedulerRequest(MBMailing mailing)
		throws PortalException, SystemException {

		String groupName = _buildGroupName(mailing);

		List<SchedulerRequest> list =
			SchedulerEngineUtil.getScheduledJobs(groupName);

		SchedulerRequest request = null;
		if (list.size() == 1) {
			request = list.get(0);
		}

		return request;
	}

	private void _scheduleEmailReader(MBMailing mailing)
		throws PortalException, SystemException {

		_unscheduleEmailReader(mailing);

		EmailReaderRequest request = new EmailReaderRequest();
		request.setCompanyId(mailing.getCompanyId());
		request.setCategoryId(mailing.getCategoryId());
		request.setUserId(mailing.getUserId());

		// incoming

		request.setMailInProtocol(mailing.getMailInProtocol());
		request.setMailInServerName(mailing.getMailInServerName());
		request.setMailInUseSSL(mailing.getMailInUseSSL());
		request.setMailInServerPort(mailing.getMailInServerPort());
		request.setMailInUserName(mailing.getMailInUserName());
		request.setMailInPassword(mailing.getMailInPassword());

		Recurrence recurrence =
			new Recurrence(
				CalendarFactoryUtil.getCalendar(), Recurrence.MINUTES,
				mailing.getMailInReadInterval());

		String groupName = _buildGroupName(mailing);
		String cronText = RecurrenceSerializer.toCronText(recurrence);

		SchedulerEngineUtil.schedule(
			groupName, cronText, new Date(), null, null,
			DestinationNames.MESSAGE_BOARDS_ACCOUNT_READER,
			JSONFactoryUtil.serialize(request));
	}

	private void _unscheduleEmailReader(MBMailing mailing)
		throws PortalException, SystemException {

		SchedulerRequest request = _getSchedulerRequest(mailing);
		if (request != null) {
			SchedulerEngineUtil.unschedule(
				request.getJobName(), request.getGroupName());
		}
	}

	private void _validate(
		String mailingListAddress, String mailAddress, String mailInServerName,
		String mailInUserName, boolean mailOutConfigured,
		String mailOutServerName, String mailOutUserName)
		throws PortalException {

		if (Validator.isNull(mailingListAddress) ||
			!Validator.isEmailAddress(mailingListAddress)) {

			throw new MailingListAddressException();
		}
		else if (Validator.isNull(mailAddress) ||
			!Validator.isEmailAddress(mailAddress)) {

			throw new MailAddressException();
		}
		else if (Validator.isNull(mailInServerName)) {

			throw new MailInServerNameException();
		}
		else if (Validator.isNull(mailInUserName)) {

			throw new MailInUserNameException();
		}
		else if (mailOutConfigured) {

			if (Validator.isNull(mailOutServerName)) {

				throw new MailOutServerNameException();
			}
			else if (Validator.isNull(mailOutUserName)) {

				throw new MailOutUserNameException();
			}
		}
	}

}