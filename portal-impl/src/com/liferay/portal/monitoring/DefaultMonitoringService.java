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

package com.liferay.portal.monitoring;

import com.liferay.portal.monitoring.statistics.DataSample;
import com.liferay.portal.monitoring.statistics.DataSampleProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="DefaultMonitoringService.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class DefaultMonitoringService
	implements DataSampleProcessor<DataSample>, MonitoringService {

	public Level getLevel(String namespace) {
		Level level = _levels.get(namespace);

		if (level == null) {
			return Level.OFF;
		}

		return level;
	}

	public Set<String> getNamespaces() {
		return _levels.keySet();
	}

	public void processDataSample(DataSample dataSample)
		throws MonitoringException {

		String namespace = dataSample.getNamespace();

		Level level = _levels.get(namespace);

		if ((level != null) && (level.equals(Level.OFF))) {
			return;
		}

		List<DataSampleProcessor<DataSample>> dataSampleProcessors =
			_dataSampleProcessors.get(namespace);

		if ((dataSampleProcessors == null) || dataSampleProcessors.isEmpty()) {
			return;
		}

		for (DataSampleProcessor<DataSample> dataSampleProcessor :
				dataSampleProcessors) {

			dataSampleProcessor.processDataSample(dataSample);
		}
	}

	public void registerDataSampleProcessor(
		String namespace, DataSampleProcessor<DataSample> dataSampleProcessor) {

		List<DataSampleProcessor<DataSample>> dataSampleProcessors =
			_dataSampleProcessors.get(namespace);

		if (dataSampleProcessors == null) {
			dataSampleProcessors =
				new ArrayList<DataSampleProcessor<DataSample>>();

			_dataSampleProcessors.put(namespace, dataSampleProcessors);
		}

		dataSampleProcessors.add(dataSampleProcessor);
	}

	public void setDataSampleProcessors(
		Map<String, List<DataSampleProcessor<DataSample>>>
			dataSampleProcessors) {

		_dataSampleProcessors.putAll(dataSampleProcessors);
	}

	public void setLevel(String namespace, Level level) {
		_levels.put(namespace, level);
	}

	public void setLevels(Map<String, String> levels) {
		for (Map.Entry<String, String> entry : levels.entrySet()) {
			String namespace = entry.getKey();
			String levelName = entry.getValue();

			Level level = Level.valueOf(levelName);

			_levels.put(namespace, level);
		}
	}

	public void unregisterDataSampleProcessor(
		String namespace, DataSampleProcessor<DataSample> dataSampleProcessor) {

		List<DataSampleProcessor<DataSample>> dataSampleProcessors =
			_dataSampleProcessors.get(namespace);

		if (dataSampleProcessors != null) {
			dataSampleProcessors.remove(dataSampleProcessor);
		}
	}

	private Map<String, List<DataSampleProcessor<DataSample>>>
		_dataSampleProcessors = new ConcurrentHashMap
			<String, List<DataSampleProcessor<DataSample>>>();
	private Map<String, Level> _levels = new ConcurrentHashMap<String, Level>();

}