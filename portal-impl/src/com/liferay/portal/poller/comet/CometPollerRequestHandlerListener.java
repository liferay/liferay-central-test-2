/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.poller.comet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.poller.comet.CometException;
import com.liferay.portal.kernel.poller.comet.CometSession;
import com.liferay.portal.poller.PollerRequestHandlerListener;

/**
 * @author Edward Han
 */
public class CometPollerRequestHandlerListener
	implements PollerRequestHandlerListener {

	public CometPollerRequestHandlerListener(CometSession cometSession) {
		_cometSession = cometSession;
	}

	public void notifyHandlingComplete() {
		try {
			_cometSession.close();
		}
		catch (CometException ce) {
			_log.error("Unable to notify handling complete", ce);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CometPollerRequestHandlerListener.class);

	private CometSession _cometSession;

}