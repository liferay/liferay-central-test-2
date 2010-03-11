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

package com.liferay.portlet.blogs.messaging;

import com.liferay.ibm.icu.util.Calendar;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.workflow.StatusConstants;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.util.Date;
import java.util.List;

/**
 * <a href="CheckSpamMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class CheckSpamMessageListener implements MessageListener {

	public void receive(Message message) {
		try {
			doReceive(message);
		}
		catch (Exception e) {
			_log.error("Unable to process message " + message, e);
		}
	}

	protected void doReceive(Message message) throws Exception {
		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.DATE, -14);

		Date expiration = cal.getTime();

		List<MBMessage> mbMessages =
			BlogsEntryLocalServiceUtil.getDiscussionMessages(
				-1, StatusConstants.DENIED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (MBMessage mbMessage : mbMessages) {
			Date statusDate = mbMessage.getStatusDate();

			if (statusDate.before(expiration)) {
				MBMessageLocalServiceUtil.deleteDiscussionMessage(
					mbMessage.getMessageId());
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CheckSpamMessageListener.class);

}