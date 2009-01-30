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

package com.liferay.portal.kernel.messaging.jmx;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationStatistics;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

/**
 * <a href="DestinationStatisticsManager.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 *
 */
public class DestinationStatisticsManager
	implements DestinationStatisticsManagerMBean {

	public static ObjectName createObjectName(String destinationName) {
		try {
			return new ObjectName(_OBJECT_NAME_PREFIX + destinationName);
		}
		catch (MalformedObjectNameException mone) {
			throw new IllegalStateException(mone);
		}
	}

	public DestinationStatisticsManager(Destination destination) {
		_destination = destination;
	}

	public int getActiveThreadCount() {
		if (_autoRefresh) {
			refresh();
		}

		return _statistics.getActiveThreadCount();
	}

	public int getCurrentThreadCount() {
		if (_autoRefresh || (_statistics == null)) {
			refresh();
		}

		return _statistics.getCurrentThreadCount();
	}

	public int getLargestThreadCount() {
		if (_autoRefresh || (_statistics == null)) {
			refresh();
		}

		return _statistics.getLargestThreadCount();
	}

	public String getLastRefresh() {
		return String.valueOf(_lastRefresh);
	}

	public int getMaxThreadPoolSize() {
		if (_autoRefresh || (_statistics == null)) {
			refresh();
		}

		return _statistics.getMaxThreadPoolSize();
	}

	public int getMinThreadPoolSize() {
		if (_autoRefresh || (_statistics == null)) {
			refresh();
		}

		return _statistics.getMinThreadPoolSize();
	}

	public long getPendingMessageCount() {
		if (_autoRefresh || (_statistics == null)) {
			refresh();
		}

		return _statistics.getPendingMessageCount();
	}

	public long getSentMessageCount() {
		if (_autoRefresh || (_statistics == null)) {
			refresh();
		}

		return _statistics.getSentMessageCount();
	}

	public boolean isAutoRefresh() {
		return _autoRefresh;
	}

	public void refresh() {
		if (System.currentTimeMillis() > _lastRefresh) {
			_lastRefresh = System.currentTimeMillis();
			_statistics = _destination.getStatistics();
		}
	}

	public void setAutoRefresh(boolean autoRefresh) {
		_autoRefresh = autoRefresh;
	}

	private static final String _OBJECT_NAME_PREFIX =
		"com.liferay.portal.kernel.messaging:type=DestinationStatistics,name=";

	private boolean _autoRefresh;
	private Destination _destination;
	private long _lastRefresh;
	private DestinationStatistics _statistics;

}