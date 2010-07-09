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

package com.liferay.portal.monitoring.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.monitoring.statistics.DataSample;
import com.liferay.portal.monitoring.statistics.DataSampleProcessor;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class MonitoringMessageListener implements MessageListener {

	public void receive(Message message) {
		try {
			doReceive(message);
		}
		catch (Exception e) {
			_log.error("Unable to process message " + message, e);
		}
	}

	public void setDataSampleProcessor(
		DataSampleProcessor<DataSample> dataSampleProcessor) {

		_dataSampleProcessor = dataSampleProcessor;
	}

	protected void doReceive(Message message) throws Exception {
		DataSample dataSample = (DataSample)message.getPayload();

		if (dataSample != null) {
			_dataSampleProcessor.processDataSample(dataSample);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		MonitoringMessageListener.class);

	private DataSampleProcessor<DataSample> _dataSampleProcessor;

}