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

package com.liferay.portal.messaging.async;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;

/**
 * <a href="AsyncInvokeMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class AsyncInvokeMessageListener implements MessageListener {

	public void receive(Message message) {
		Object payload =  message.getPayload();
		if (payload == null) {
			_log.warn("Missing payload :" + message);
			return;
		}
		if (!(payload instanceof Runnable)) {
			_log.warn("Wrong payload type :" + message);
		}
		Runnable runnable = (Runnable) payload;
		try {
			runnable.run();
		} catch (Throwable t) {
			_log.error("Fail to run method : " + message, t);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		AsyncInvokeMessageListener.class);

}