/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.scheduler.CronText;
import com.liferay.portal.kernel.scheduler.CronTrigger;
import com.liferay.portal.kernel.scheduler.SchedulerEngineUtil;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerRequest;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portlet.messageboards.MailingListEmailAddressException;
import com.liferay.portlet.messageboards.MailingListInServerNameException;
import com.liferay.portlet.messageboards.MailingListInUserNameException;
import com.liferay.portlet.messageboards.MailingListOutEmailAddressException;
import com.liferay.portlet.messageboards.MailingListOutServerNameException;
import com.liferay.portlet.messageboards.MailingListOutUserNameException;
import com.liferay.portlet.messageboards.messaging.MailingListRequest;
import com.liferay.portlet.messageboards.model.MBMailingList;
import com.liferay.portlet.messageboards.service.base.MBMailingListLocalServiceBaseImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <a href="MBMailingListLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Thiago Moreira
 */
public class MBMailingListLocalServiceImpl
	extends MBMailingListLocalServiceBaseImpl {

	public MBMailingList addMailingList(
			String uuid, long userId, long groupId, long categoryId,
			String emailAddress, String inProtocol, String inServerName,
			int inServerPort, boolean inUseSSL, String inUserName,
			String inPassword, int inReadInterval, String outEmailAddress,
			boolean outCustom, String outServerName, int outServerPort,
			boolean outUseSSL, String outUserName, String outPassword,
			boolean active)
		throws PortalException, SystemException {

		// Mailing list

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		validate(
			emailAddress, inServerName, inUserName, outEmailAddress, outCustom,
			outServerName, outUserName, active);

		long mailingListId = counterLocalService.increment();

		MBMailingList mailingList = mbMailingListPersistence.create(
			mailingListId);

		mailingList.setUuid(uuid);
		mailingList.setGroupId(groupId);
		mailingList.setCompanyId(user.getCompanyId());
		mailingList.setUserId(user.getUserId());
		mailingList.setUserName(user.getFullName());
		mailingList.setCreateDate(now);
		mailingList.setModifiedDate(now);
		mailingList.setCategoryId(categoryId);
		mailingList.setEmailAddress(emailAddress);
		mailingList.setInProtocol(inUseSSL ? inProtocol + "s" : inProtocol);
		mailingList.setInServerName(inServerName);
		mailingList.setInServerPort(inServerPort);
		mailingList.setInUseSSL(inUseSSL);
		mailingList.setInUserName(inUserName);
		mailingList.setInPassword(inPassword);
		mailingList.setInReadInterval(inReadInterval);
		mailingList.setOutEmailAddress(outEmailAddress);
		mailingList.setOutCustom(outCustom);
		mailingList.setOutServerName(outServerName);
		mailingList.setOutServerPort(outServerPort);
		mailingList.setOutUseSSL(outUseSSL);
		mailingList.setOutUserName(outUserName);
		mailingList.setOutPassword(outPassword);
		mailingList.setActive(active);

		mbMailingListPersistence.update(mailingList, false);

		// Scheduler

		if (active) {
			scheduleMailingList(mailingList);
		}

		return mailingList;
	}

	public void deleteCategoryMailingList(long groupId, long categoryId)
		throws PortalException, SystemException {

		MBMailingList mailingList = mbMailingListPersistence.findByG_C(
			groupId, categoryId);

		deleteMailingList(mailingList);
	}

	public void deleteMailingList(long mailingListId)
		throws PortalException, SystemException {

		MBMailingList mailingList = mbMailingListPersistence.findByPrimaryKey(
			mailingListId);

		deleteMailingList(mailingList);
	}

	public void deleteMailingList(MBMailingList mailingList)
		throws PortalException, SystemException {

		unscheduleMailingList(mailingList);

		mbMailingListPersistence.remove(mailingList);
	}

	public MBMailingList getCategoryMailingList(long groupId, long categoryId)
		throws PortalException, SystemException {

		return mbMailingListPersistence.findByG_C(groupId, categoryId);
	}

	public MBMailingList updateMailingList(
			long mailingListId, String emailAddress, String inProtocol,
			String inServerName, int inServerPort, boolean inUseSSL,
			String inUserName, String inPassword, int inReadInterval,
			String outEmailAddress, boolean outCustom, String outServerName,
			int outServerPort, boolean outUseSSL, String outUserName,
			String outPassword, boolean active)
		throws PortalException, SystemException {

		// Mailing list

		validate(
			emailAddress, inServerName, inUserName, outEmailAddress, outCustom,
			outServerName, outUserName, active);

		MBMailingList mailingList = mbMailingListPersistence.findByPrimaryKey(
			mailingListId);

		boolean oldActive = mailingList.isActive();

		mailingList.setModifiedDate(new Date());
		mailingList.setEmailAddress(emailAddress);
		mailingList.setInProtocol(inUseSSL ? inProtocol + "s" : inProtocol);
		mailingList.setInServerName(inServerName);
		mailingList.setInServerPort(inServerPort);
		mailingList.setInUseSSL(inUseSSL);
		mailingList.setInUserName(inUserName);
		mailingList.setInPassword(inPassword);
		mailingList.setInReadInterval(inReadInterval);
		mailingList.setOutEmailAddress(outEmailAddress);
		mailingList.setOutCustom(outCustom);
		mailingList.setOutServerName(outServerName);
		mailingList.setOutServerPort(outServerPort);
		mailingList.setOutUseSSL(outUseSSL);
		mailingList.setOutUserName(outUserName);
		mailingList.setOutPassword(outPassword);
		mailingList.setActive(active);

		mbMailingListPersistence.update(mailingList, false);

		// Scheduler

		if (oldActive) {
			unscheduleMailingList(mailingList);
		}

		if (active) {
			scheduleMailingList(mailingList);
		}

		return mailingList;
	}

	protected String getSchedulerGroupName(MBMailingList mailingList) {
		return DestinationNames.MESSAGE_BOARDS_MAILING_LIST.concat(
			StringPool.SLASH).concat(
				String.valueOf(mailingList.getMailingListId()));
	}

	protected void scheduleMailingList(MBMailingList mailingList)
		throws PortalException {

		unscheduleMailingList(mailingList);

		String groupName = getSchedulerGroupName(mailingList);

		Calendar startDate = CalendarFactoryUtil.getCalendar();

		CronText cronText = new CronText(
			startDate, CronText.MINUTELY_FREQUENCY,
			mailingList.getInReadInterval());

		Trigger trigger = new CronTrigger(
			groupName, groupName, startDate.getTime(), null,
			cronText.toString());

		MailingListRequest mailingListRequest = new MailingListRequest();

		mailingListRequest.setCompanyId(mailingList.getCompanyId());
		mailingListRequest.setUserId(mailingList.getUserId());
		mailingListRequest.setGroupId(mailingList.getGroupId());
		mailingListRequest.setCategoryId(mailingList.getCategoryId());
		mailingListRequest.setInProtocol(mailingList.getInProtocol());
		mailingListRequest.setInServerName(mailingList.getInServerName());
		mailingListRequest.setInServerPort(mailingList.getInServerPort());
		mailingListRequest.setInUseSSL(mailingList.getInUseSSL());
		mailingListRequest.setInUserName(mailingList.getInUserName());
		mailingListRequest.setInPassword(mailingList.getInPassword());

		SchedulerEngineUtil.schedule(
			trigger, null, DestinationNames.MESSAGE_BOARDS_MAILING_LIST,
			mailingListRequest);
	}

	protected void unscheduleMailingList(MBMailingList mailingList)
		throws PortalException {

		String groupName = getSchedulerGroupName(mailingList);

		List<SchedulerRequest> schedulerRequests =
			SchedulerEngineUtil.getScheduledJobs(groupName);

		for (SchedulerRequest schedulerRequest : schedulerRequests) {
			SchedulerEngineUtil.unschedule(schedulerRequest.getTrigger());
		}
	}

	protected void validate(
			String emailAddress, String inServerName, String inUserName,
			String outEmailAddress, boolean outCustom, String outServerName,
			String outUserName, boolean active)
		throws PortalException {

		if (!active) {
			return;
		}

		if (!Validator.isEmailAddress(emailAddress)) {
			throw new MailingListEmailAddressException();
		}
		else if (Validator.isNull(inServerName)) {
			throw new MailingListInServerNameException();
		}
		else if (Validator.isNull(inUserName)) {
			throw new MailingListInUserNameException();
		}
		else if (Validator.isNull(outEmailAddress)) {
			throw new MailingListOutEmailAddressException();
		}
		else if (outCustom) {
			if (Validator.isNull(outServerName)) {
				throw new MailingListOutServerNameException();
			}
			else if (Validator.isNull(outUserName)) {
				throw new MailingListOutUserNameException();
			}
		}
	}

}