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

package com.liferay.portal.monitoring.statistics;

import com.liferay.portal.util.PropsValues;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * <a href="ThreadLocalStatisticsKeeper.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class ThreadLocalDataSampleCache {

	public static void add(DataSample DataSample) {
		if (isActive()) {
			get()._add(DataSample);
		}
	}

	public static void clear() {
		_dataSampleCache.remove();
	}

	public static ThreadLocalDataSampleCache get() {
		if (isActive()) {
			return _dataSampleCache.get();
		}
		return null;
	}

	public static List<DataSample> getDataSample() {
		if (isActive()) {
			return get()._getDataSample();
		}
		return Collections.EMPTY_LIST;
	}

	public static boolean isActive() {
		return _active;
	}

	public static void setActive(boolean active) {
		_active = active;
	}

	public long getMonitorTime() {
		return _monitorTime;
	}

	private ThreadLocalDataSampleCache() {
		_monitorTime = System.currentTimeMillis();
	}

	private List<DataSample> _getDataSample() {
		return _dataSamples;
	}

	private void _add(DataSample DataSample) {
		_dataSamples.add(DataSample);
	}

	private static boolean _active =
		PropsValues.MONITORING_DATA_SAMPLE_THREAD_LOCAL_CACHE_ENABLED;

	private static ThreadLocal<ThreadLocalDataSampleCache> _dataSampleCache =
		new ThreadLocal<ThreadLocalDataSampleCache>() {
			protected ThreadLocalDataSampleCache initialValue() {
				return new ThreadLocalDataSampleCache();				
			}
		};

	private List<DataSample> _dataSamples = new ArrayList<DataSample>();
	private long _monitorTime;
}
