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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portlet.messageboards.NoSuchMessageFlagException;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageFlag;
import com.liferay.portlet.messageboards.model.MBMessageFlagConstants;
import com.liferay.portlet.messageboards.service.base.MBMessageFlagServiceBaseImpl;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class MBMessageFlagServiceImpl extends MBMessageFlagServiceBaseImpl {

	public void addAnswerFlag(long messageId)
		throws PortalException, SystemException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		if (message.isRoot()) {
			return;
		}

		MBMessage rootMessage = mbMessagePersistence.findByPrimaryKey(
			message.getRootMessageId());

		MBMessagePermission.check(
			getPermissionChecker(), rootMessage.getMessageId(),
			ActionKeys.UPDATE);

		MBMessageFlag questionMessageFlag =
			mbMessageFlagPersistence.fetchByU_M_F(
				rootMessage.getUserId(), rootMessage.getMessageId(),
				MBMessageFlagConstants.QUESTION_FLAG);

		MBMessageFlag answerMessageFlag =
			mbMessageFlagPersistence.fetchByU_M_F(
				rootMessage.getUserId(), rootMessage.getMessageId(),
				MBMessageFlagConstants.ANSWER_FLAG);

		if ((questionMessageFlag != null) && (answerMessageFlag == null)) {
			questionMessageFlag.setFlag(MBMessageFlagConstants.ANSWER_FLAG);

			mbMessageFlagPersistence.update(questionMessageFlag, false);
		}

		MBMessageFlag messageFlag = mbMessageFlagPersistence.fetchByU_M_F(
			message.getUserId(), message.getMessageId(),
			MBMessageFlagConstants.ANSWER_FLAG);

		if (messageFlag == null) {
			long messageFlagId = counterLocalService.increment();

			messageFlag = mbMessageFlagPersistence.create(messageFlagId);

			messageFlag.setUserId(message.getUserId());
			messageFlag.setModifiedDate(new Date());
			messageFlag.setThreadId(message.getThreadId());
			messageFlag.setMessageId(message.getMessageId());
			messageFlag.setFlag(MBMessageFlagConstants.ANSWER_FLAG);

			mbMessageFlagPersistence.update(messageFlag, false);
		}
	}

	public void deleteAnswerFlag(long messageId)
		throws PortalException, SystemException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		if (message.isRoot()) {
			return;
		}

		MBMessage rootMessage = mbMessagePersistence.findByPrimaryKey(
			message.getRootMessageId());

		MBMessagePermission.check(
			getPermissionChecker(), rootMessage.getMessageId(),
			ActionKeys.UPDATE);

		try {
			mbMessageFlagPersistence.removeByU_M_F(
				message.getUserId(), message.getMessageId(),
				MBMessageFlagConstants.ANSWER_FLAG);
		}
		catch (NoSuchMessageFlagException nsmfe) {
		}

		MBMessageFlag answerMessageFlag =
			mbMessageFlagPersistence.fetchByU_M_F(
				rootMessage.getUserId(), rootMessage.getMessageId(),
				MBMessageFlagConstants.ANSWER_FLAG);

		if (answerMessageFlag == null) {
			return;
		}

		int answerFlagsCount = mbMessageFlagPersistence.countByT_F(
			message.getThreadId(), MBMessageFlagConstants.ANSWER_FLAG);

		if (answerFlagsCount == 1) {
			answerMessageFlag.setFlag(MBMessageFlagConstants.QUESTION_FLAG);

			mbMessageFlagPersistence.update(answerMessageFlag, false);
		}
	}

}