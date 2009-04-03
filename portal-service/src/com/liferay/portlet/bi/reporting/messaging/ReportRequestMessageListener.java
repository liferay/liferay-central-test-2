/*
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.bi.reporting.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portlet.bi.reporting.ReportEngine;
import com.liferay.portlet.bi.reporting.ReportResultContainer;
import com.liferay.portlet.bi.reporting.ReportRequest;
import com.liferay.portlet.bi.reporting.ReportGenerationException;

/**
 * <a href="ReportEngineMessageReceiver.java.html"><b><i>View
 * Source</i></b></a>
 * <p/>
 * Integrates a report engine with the Liferay Message Bus.  This listeners
 * enable report engines to listen for ReportRequests from the Liferay Message
 * Bus.
 *
 * @author Michael C. Han
 */
public class ReportRequestMessageListener implements MessageListener {
	public ReportRequestMessageListener(
		ReportEngine engine, ReportResultContainer container) {
		_reportEngine = engine;
		_containerPrototype = container;
	}

	public void receive(Message message) {
		ReportRequest request = (ReportRequest) message.getPayload();
		ReportResultContainer container =
			_containerPrototype.clone(request.getReportName());
		Message reply = new Message();
		reply.setDestination(message.getResponseDestination());
		reply.setResponseId(message.getResponseId());
		try {
			_reportEngine.execute(request, container);
		}
		catch (ReportGenerationException e) {
			if (_log.isErrorEnabled()) {
				_log.error("Unable to generate report", e);
			}
			container.setReportGenerationException(e);
		}
		finally {
			reply.setPayload(container);
			MessageBusUtil.sendMessage(message.getResponseDestination(), reply);
		}
	}

	private static final Log _log =
		LogFactoryUtil.getLog(ReportRequestMessageListener.class);
	private ReportEngine _reportEngine;
	private ReportResultContainer _containerPrototype;
}
