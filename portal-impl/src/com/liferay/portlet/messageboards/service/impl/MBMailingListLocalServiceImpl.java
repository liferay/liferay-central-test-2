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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.scheduler.SchedulerEngineUtil;
import com.liferay.portal.kernel.scheduler.TriggerExpression;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerRequest;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portlet.messageboards.MailingListEmailAddressException;
import com.liferay.portlet.messageboards.MailingListInServerNameException;
import com.liferay.portlet.messageboards.MailingListInUserNameException;
import com.liferay.portlet.messageboards.MailingListOutEmailAddressException;
import com.liferay.portlet.messageboards.MailingListOutServerNameException;
import com.liferay.portlet.messageboards.MailingListOutUserNameException;
import com.liferay.portlet.messageboards.NoSuchMailingListException;
import com.liferay.portlet.messageboards.messaging.EmailReaderRequest;
import com.liferay.portlet.messageboards.model.MBMailingList;
import com.liferay.portlet.messageboards.service.base.MBMailingListLocalServiceBaseImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <a href="MBMailingListLocalServiceImpl.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Thiago Moreira
 */
public class MBMailingListLocalServiceImpl
	extends MBMailingListLocalServiceBaseImpl {

	public MBMailingList addMailing(
		long userId, long groupId, long categoryId, String emailAddress,
		String inProtocol, String inServerName, boolean inUseSSL,
		int inServerPort, String inUserName, String inPassword,
		int inReadInterval, boolean outCustom, String outEmailAddress,
		String outServerName, boolean outUseSSL, int outServerPort,
		String outUserName, String outPassword)
		throws PortalException, SystemException {

		// Mailing

		_validate(
			emailAddress, inServerName, inUserName, outCustom, outEmailAddress,
			outServerName, outUserName);

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();
		long mailingListId = counterLocalService.increment();
		MBMailingList mailingList = mbMailingListPersistence.create(
			mailingListId);

		mailingList.setCompanyId(user.getCompanyId());
		mailingList.setGroupId(groupId);
		mailingList.setUserId(user.getUserId());
		mailingList.setUserName(user.getFullName());
		mailingList.setCreateDate(now);
		mailingList.setModifiedDate(now);
		mailingList.setCategoryId(categoryId);
		mailingList.setEmailAddress(emailAddress);
		mailingList.setInProtocol(inProtocol);
		mailingList.setInServerName(inServerName);
		mailingList.setInUseSSL(inUseSSL);
		mailingList.setInServerPort(inServerPort);
		mailingList.setInUserName(inUserName);
		mailingList.setInPassword(inPassword);
		mailingList.setInReadInterval(inReadInterval);

		// outgoing

		if (outCustom) {
			mailingList.setOutCustom(true);
			mailingList.setOutEmailAddress(outEmailAddress);
			mailingList.setOutServerName(outServerName);
			mailingList.setOutUseSSL(outUseSSL);
			mailingList.setOutServerPort(outServerPort);
			mailingList.setOutUserName(outUserName);
			mailingList.setOutPassword(outPassword);
		}
		else {
			mailingList.setOutCustom(false);
		}

		mailingList.setActive(true);

		mbMailingListPersistence.update(mailingList, false);

		// Resource

		addMailingListResources(mailingList, true, true);

		// Adding a listener to the account

		_scheduleEmailReader(mailingList);

		return mailingList;
	}

	public void addMailingListResources(
		MBMailingList mailingList, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			mailingList.getCompanyId(), mailingList.getGroupId(),
			mailingList.getUserId(), MBMailingList.class.getName(),
			mailingList.getMailingListId(), false, addCommunityPermissions,
			addGuestPermissions);

	}

	public void deleteMailingList(long mailingListId)
		throws PortalException, SystemException {

		MBMailingList mailingList =	mbMailingListPersistence.findByPrimaryKey(
				mailingListId);

		deleteMailingList(mailingList);
	}

	public void deleteMailingList(MBMailingList mailingList)
		throws PortalException, SystemException {

		// Unscheduling

		_unscheduleEmailReader(mailingList);

		// Resources

		resourceLocalService.deleteResource(
			mailingList.getCompanyId(), MBMailingList.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, mailingList.getMailingListId());

		deleteMBMailingList(mailingList);
	}

	public MBMailingList getMailingListByCategory(long categoryId)
		throws PortalException, SystemException {

		return mbMailingListPersistence.findByCategoryId(categoryId);
	}

	public List<MBMailingList> getMailingLists()
		throws PortalException, SystemException {

		return mbMailingListPersistence.findAll();
	}

	public void startMailReader()
		throws PortalException, SystemException {

		List<MBMailingList> list = mbMailingListPersistence.findByActive(true);
		for (MBMailingList mailingList : list) {
			_scheduleEmailReader(mailingList);
		}
	}

	public void stopMailReader()
		throws PortalException, SystemException {

		List<MBMailingList> list = mbMailingListPersistence.findByActive(true);
		for (MBMailingList mailingList : list) {
			_unscheduleEmailReader(mailingList);
		}
	}

	public MBMailingList updateActive(long mailingListId, boolean active)
		throws PortalException, SystemException {

		MBMailingList mailingList = mbMailingListPersistence.findByPrimaryKey(
			mailingListId);

		mailingList.setActive(active);

		mbMailingListPersistence.update(mailingList, false);

		if (active) {
			_scheduleEmailReader(mailingList);
		}
		else {
			_unscheduleEmailReader(mailingList);
		}

		return mailingList;
	}

	public MBMailingList updateMailingList(
		long userId, long groupId, long categoryId, String emailAddress,
		String inProtocol, String inServerName, boolean inUseSSL,
		int inServerPort, String inUserName, String inPassword,
		int inReadInterval, String outEmailAddress, boolean outCustom,
		String outServerName, boolean outUseSSL, int outServerPort,
		String outUserName, String outPassword)
		throws PortalException, SystemException {

		// MailingList

		_validate(
			emailAddress, inServerName, inUserName, outCustom, outEmailAddress,
			outServerName, outUserName);

		Date now = new Date();

		MBMailingList mailingList;
		try {

			mailingList = getMailingListByCategory(categoryId);

		}
		catch (NoSuchMailingListException e) {
			User user = userPersistence.findByPrimaryKey(userId);
			long mailingListId = counterLocalService.increment();
			mailingList = mbMailingListPersistence.create(mailingListId);

			mailingList.setCompanyId(user.getCompanyId());
			mailingList.setGroupId(groupId);
			mailingList.setUserId(user.getUserId());
			mailingList.setUserName(user.getFullName());
			mailingList.setCreateDate(now);
			mailingList.setCategoryId(categoryId);
		}

		mailingList.setModifiedDate(now);
		mailingList.setEmailAddress(emailAddress);
		mailingList.setInProtocol(inProtocol);
		mailingList.setInServerName(inServerName);
		mailingList.setInUseSSL(inUseSSL);
		mailingList.setInServerPort(inServerPort);
		mailingList.setInUserName(inUserName);
		mailingList.setInPassword(inPassword);
		mailingList.setInReadInterval(inReadInterval);

		// outgoing

		if (outCustom) {
			mailingList.setOutCustom(true);
			mailingList.setOutEmailAddress(outEmailAddress);
			mailingList.setOutServerName(outServerName);
			mailingList.setOutUseSSL(outUseSSL);
			mailingList.setOutServerPort(outServerPort);
			mailingList.setOutUserName(outUserName);
			mailingList.setOutPassword(outPassword);
		}
		else {
			mailingList.setOutCustom(false);
		}

		mailingList.setActive(true);

		mbMailingListPersistence.update(mailingList, false);

		// adding a listener to the account

		_scheduleEmailReader(mailingList);

		return mailingList;
	}

	public MBMailingList updateOutCustom(long mailingListId, boolean outCustom)
		throws PortalException, SystemException {

		MBMailingList mailingList = mbMailingListPersistence.findByPrimaryKey(
			mailingListId);

		mailingList.setOutCustom(outCustom);

		mbMailingListPersistence.update(mailingList, false);

		if (mailingList.isActive()) {
			_scheduleEmailReader(mailingList);
		}
		else {
			_unscheduleEmailReader(mailingList);
		}

		return mailingList;
	}

	private String _buildGroupName(MBMailingList mailingList) {

		return mailingList.getMailingListId() + StringPool.AT +
			mailingList.getCategoryId();
	}

	private SchedulerRequest _getSchedulerRequest(MBMailingList mailingList)
		throws PortalException, SystemException {

		String groupName = _buildGroupName(mailingList);

		List<SchedulerRequest> list =
			SchedulerEngineUtil.getScheduledJobs(groupName);

		SchedulerRequest request = null;
		if (list.size() == 1) {
			request = list.get(0);
		}

		return request;
	}

	private void _scheduleEmailReader(MBMailingList mailingList)
		throws PortalException, SystemException {

		_unscheduleEmailReader(mailingList);

		String groupName = _buildGroupName(mailingList);
		Calendar startDate = CalendarFactoryUtil.getCalendar();
		TriggerExpression expression = new TriggerExpression(
			startDate, TriggerExpression.MINUTELY_FREQUENCY,
			mailingList.getInReadInterval());
		String cronText = expression.toCronText();
		EmailReaderRequest request = new EmailReaderRequest();

		request.setCompanyId(mailingList.getCompanyId());
		request.setCategoryId(mailingList.getCategoryId());
		request.setUserId(mailingList.getUserId());
		request.setInProtocol(mailingList.getInProtocol());
		request.setInServerName(mailingList.getInServerName());
		request.setInUseSSL(mailingList.getInUseSSL());
		request.setInServerPort(mailingList.getInServerPort());
		request.setInUserName(mailingList.getInUserName());
		request.setInPassword(mailingList.getInPassword());

		SchedulerEngineUtil.schedule(
			groupName, cronText, startDate.getTime(), null, null,
			DestinationNames.MESSAGE_BOARDS_ACCOUNT_READER,
			JSONFactoryUtil.serialize(request));
	}

	private void _unscheduleEmailReader(MBMailingList mailingList)
		throws PortalException, SystemException {

		SchedulerRequest request = _getSchedulerRequest(mailingList);
		if (request != null) {
			SchedulerEngineUtil.unschedule(
				request.getJobName(), request.getGroupName());
		}
	}

	private void _validate(
		String emailAddress, String inServerName, String inUserName,
		boolean outCustom, String outEmailAddress, String outServerName,
		String outUserName)
		throws PortalException {

		if (Validator.isNull(emailAddress) ||
			!Validator.isEmailAddress(emailAddress)) {

			throw new MailingListEmailAddressException();
		}
		else if (Validator.isNull(inServerName)) {

			throw new MailingListInServerNameException();
		}
		else if (Validator.isNull(inUserName)) {

			throw new MailingListInUserNameException();
		}
		else if (outCustom) {

			if (Validator.isNull(outEmailAddress)) {

				throw new MailingListOutEmailAddressException();
			}
			if (Validator.isNull(outServerName)) {

				throw new MailingListOutServerNameException();
			}
			else if (Validator.isNull(outUserName)) {

				throw new MailingListOutUserNameException();
			}
		}
	}

}