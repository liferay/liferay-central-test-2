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

package com.liferay.portal.monitoring.impl;

import com.liferay.portal.monitoring.MonitoringLevel;
import com.liferay.portal.monitoring.MonitoringService;
import com.liferay.portal.monitoring.statistics.instance.PortalRequestDataSample;
import com.liferay.portal.monitoring.statistics.DataSampleProcessor;
import com.liferay.portal.monitoring.statistics.DataSample;
import com.liferay.portal.monitoring.statistics.Statistics;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="MonitoringService.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class DefaultMonitoringServiceImpl
	implements MonitoringService, DataSampleProcessor {

	public DefaultMonitoringServiceImpl() {
		
	}

	public MonitoringLevel getMonitoringLevel(String monitorType) {
		MonitoringLevel level = _monitoringLevels.get(monitorType);
		if (level == null) {
			return MonitoringLevel.OFF;
		}
		return level;
	}

	public Set<String> getMonitoredNamespaces() {
		return _monitoringLevels.keySet();
	}

	public void processDataSample(DataSample dataSample) {
		String namespace = dataSample.getNamespace();

		MonitoringLevel level =
			_monitoringLevels.get(namespace);

		if ((level != null) && level.equals(MonitoringLevel.OFF)) {
			return;
		}

		List<DataSampleProcessor> dataProcessors =
			_dataSampleProcessors.get(namespace);

		if ((dataProcessors == null) || dataProcessors.isEmpty()) {
			return;
		}

		for (DataSampleProcessor processor : dataProcessors) {
			processor.processDataSample(dataSample);
		}
	}

	public void registerDataSampleProcessor(
		String namespace, DataSampleProcessor dataSampleProcessor) {

		List<DataSampleProcessor> processors =
			_dataSampleProcessors.get(namespace);

		if (processors == null) {
			processors = new ArrayList<DataSampleProcessor>();

			_dataSampleProcessors.put(namespace, processors);
		}

		processors.add(dataSampleProcessor);
	}

	public void setDataSampleProcessors(
		Map<String, List<DataSampleProcessor>> processors) {

		_dataSampleProcessors.putAll(processors);
	}

	public void setMonitoringLevels(Map<String, String> monitoringLevels) {
		for (Map.Entry<String, String> monitoringLevel :
				monitoringLevels.entrySet()) {

			String namespace = monitoringLevel.getKey();
			String level = monitoringLevel.getValue();
			_monitoringLevels.put(namespace, MonitoringLevel.valueOf(level));

		}
	}

	public void setMonitoringLevel(String namespace, MonitoringLevel level) {
		_monitoringLevels.put(namespace, level);
	}

	public void unregisterDataSampleProcessor(
		String namespace, DataSampleProcessor dataSampleProcessor) {

		List<DataSampleProcessor> processors =
			_dataSampleProcessors.get(namespace);

		if (processors != null) {
			processors.remove(dataSampleProcessor);
		}
	}

	private Map<String, MonitoringLevel> _monitoringLevels =
		new ConcurrentHashMap<String, MonitoringLevel>();
	private Map<String, List<DataSampleProcessor>> _dataSampleProcessors =
		new ConcurrentHashMap<String, List<DataSampleProcessor>>();
}
