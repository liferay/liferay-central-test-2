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

package com.liferay.portal.monitoring.statistics;

import com.liferay.portal.kernel.util.InitialThreadLocal;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="DataSampleThreadLocal.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class DataSampleThreadLocal implements Cloneable {

	public static void addDataSample(DataSample dataSample) {
		if (!_monitoringDataSampleThreadLocal) {
			return;
		}

		_dataSampleThreadLocal.get()._addDataSample(dataSample);
	}

	public static void clearDataSamples() {
		_dataSampleThreadLocal.remove();
	}

	public static List<DataSample> getDataSamples() {
		if (!_monitoringDataSampleThreadLocal) {
			return Collections.EMPTY_LIST;
		}

		return _dataSampleThreadLocal.get()._getDataSamples();
	}

	public static boolean isMonitoringDataSampleThreadLocal() {
		return _monitoringDataSampleThreadLocal;
	}

	public static void setMonitoringDataSampleThreadLocal(
		boolean monitoringDataSampleThreadLocal) {

		_monitoringDataSampleThreadLocal = monitoringDataSampleThreadLocal;
	}

	public Object clone() {
		return new DataSampleThreadLocal();
	}

	public long getMonitorTime() {
		return _monitorTime;
	}

	private DataSampleThreadLocal() {
		_monitorTime = System.currentTimeMillis();
	}

	private void _addDataSample(DataSample dataSample) {
		_dataSamples.add(dataSample);
	}

	private List<DataSample> _getDataSamples() {
		return _dataSamples;
	}

	private static ThreadLocal<DataSampleThreadLocal> _dataSampleThreadLocal =
		new InitialThreadLocal<DataSampleThreadLocal>(
			new DataSampleThreadLocal());
	private static boolean _monitoringDataSampleThreadLocal =
		PropsValues.MONITORING_DATA_SAMPLE_THREAD_LOCAL;

	private List<DataSample> _dataSamples = new ArrayList<DataSample>();
	private long _monitorTime;

}