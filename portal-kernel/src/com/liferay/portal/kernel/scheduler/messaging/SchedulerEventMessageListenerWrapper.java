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

package com.liferay.portal.kernel.scheduler.messaging;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;

/**
 * <a href="SchedulerEventMessageListenerWrapper.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Shuyang Zhou
 */
public class SchedulerEventMessageListenerWrapper implements MessageListener {

	public SchedulerEventMessageListenerWrapper(
		MessageListener messageListener) {

		_messageListener = messageListener;

		String className = messageListener.getClass().getName();
		String jobName = className;
		String groupName = className;

		int classNameLength = className.length();
		if (classNameLength > SchedulerEngine.JOB_NAME_MAX_LENGTH) {
			jobName =
				className.substring(0, SchedulerEngine.JOB_NAME_MAX_LENGTH);
		}
		if (classNameLength > SchedulerEngine.GROUP_NAME_MAX_LENGTH) {
			groupName =
				className.substring(0, SchedulerEngine.GROUP_NAME_MAX_LENGTH);
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