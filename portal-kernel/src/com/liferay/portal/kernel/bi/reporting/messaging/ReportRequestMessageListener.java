/**
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

package com.liferay.portal.kernel.bi.reporting.messaging;

import com.liferay.portal.kernel.bi.reporting.ReportEngine;
import com.liferay.portal.kernel.bi.reporting.ReportGenerationException;
import com.liferay.portal.kernel.bi.reporting.ReportRequest;
import com.liferay.portal.kernel.bi.reporting.ReportResultContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;

/**
 * <a href="ReportRequestMessageListener.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Michael C. Han
 *
 */
public class ReportRequestMessageListener implements MessageListener {

	public ReportRequestMessageListener(
		ReportEngine reportEngine,
		ReportResultContainer reportResultContainer) {

		_reportEngine = reportEngine;
		_reportResultContainer = reportResultContainer;
	}

	public void receive(Message message) {
		ReportRequest request = (ReportRequest)message.getPayload();

		ReportResultContainer reportResultContainer =
			_reportResultContainer.clone(request.getReportName());

		Message responseMessage = MessageBusUtil.createResponseMessage(message);

		try {
			_reportEngine.execute(request, reportResultContainer);
		}
		catch (ReportGenerationException rge) {
			_log.error("Unable to generate report", rge);

			reportResultContainer.setReportGenerationException(rge);
		}
		finally {
			responseMessage.setPayload(reportResultContainer);

			MessageBusUtil.sendMessage(
				responseMessage.getDestination(), responseMessage);
		}
	}

	private static final Log _log =
		LogFactoryUtil.getLog(ReportRequestMessageListener.class);

	private ReportEngine _reportEngine;
	private ReportResultContainer _reportResultContainer;

}