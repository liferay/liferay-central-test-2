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

package com.liferay.portal.kernel.scheduler.messaging;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Shuyang Zhou
 */
public class SchedulerEventMessageListenerWrapper implements MessageListener {

	public SchedulerEventMessageListenerWrapper(
		MessageListener messageListener, String className) {

		_messageListener = messageListener;

		String jobName = className;

		if (className.length() > SchedulerEngine.JOB_NAME_MAX_LENGTH) {
			jobName = className.substring(
				0, SchedulerEngine.JOB_NAME_MAX_LENGTH);
		}

		String groupName = className;

		if (className.length() > SchedulerEngine.GROUP_NAME_MAX_LENGTH) {
			groupName = className.substring(
				0, SchedulerEngine.GROUP_NAME_MAX_LENGTH);
		}

		_key = jobName.concat(StringPool.COLON).concat(groupName);
	}

	public void receive(Message message) {
		String receiverKey = GetterUtil.getString(
			message.getString(SchedulerEngine.RECEIVER_KEY));

		if (receiverKey.equals(_key)) {
			try{
				_messageListener.receive(message);
			}
			finally {
				if (message.getBoolean(SchedulerEngine.DISABLE)) {
					String destinationName = message.getDestinationName();

					MessageBusUtil.unregisterMessageListener(
						destinationName, this);
				}
			}
		}
	}

	private String _key;
	private MessageListener _messageListener;

}