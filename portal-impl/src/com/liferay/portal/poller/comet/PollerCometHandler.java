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

import com.liferay.portal.kernel.poller.comet.BaseCometHandler;
import com.liferay.portal.kernel.poller.comet.CometHandler;
import com.liferay.portal.kernel.poller.comet.CometRequest;
import com.liferay.portal.kernel.poller.comet.CometSession;
import com.liferay.portal.poller.PollerRequestHandler;
import com.liferay.portal.poller.PollerRequestHandlerListener;
import com.liferay.portal.poller.PollerResponseWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Edward Han
 * @author Brian Wing Shun Chan
 */
public class PollerCometHandler extends BaseCometHandler {

	public CometHandler clone() {
		return new PollerCometHandler();
	}

	public void receiveData(String data) {
	}

	protected void doDestroy() throws Exception {
		_pollerRequestHandler.shutdown();
	}

	protected void doInit(CometSession cometSession) throws Exception {
		CometRequest cometRequest = cometSession.getCometRequest();

		String pollerRequestString = cometRequest.getParameter("pollerRequest");

		PollerResponseWriter pollerResponseWriter =
			new CometPollerResponseWriter(cometSession);

		List<PollerRequestHandlerListener> pollerRequestHandlerListeners =
			new ArrayList<PollerRequestHandlerListener>(1);

		pollerRequestHandlerListeners.add(
			new CometPollerRequestHandlerListener(cometSession));

		_pollerRequestHandler = new PollerRequestHandler(
			cometRequest.getPathInfo(), pollerRequestString,
			pollerResponseWriter, pollerRequestHandlerListeners);

		_pollerRequestHandler.processRequest();
	}

	private PollerRequestHandler _pollerRequestHandler;

}