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

/**
 * <a href="BaseStatistics.java.html"><b><i>View Source</i></b></a>
 *
 * @author Rajesh Thiagarajan
 * @author Brian Wing Shun Chan
 */
public class BaseStatistics implements Statistics {

	public BaseStatistics(String name) {
		_name = name;
		_startTime = System.currentTimeMillis();
	}

	public String getDescription() {
		return _description;
	}

	public long getLastSampleTime() {
		return _lastSampleTime;
	}

	public long getLastTime() {
		return _lastTime;
	}

	public long getLowerBound() {
		return _lowerBound;
	}

	public long getMaxTime() {
		return _maxTime;
	}

	public long getMinTime() {
		return _minTime;
	}

	public String getName() {
		return _name;
	}

	public long getStartTime() {
		return _startTime;
	}

	public long getUpperBound() {
		return _upperBound;
	}

	public long getUptime() {
		return System.currentTimeMillis() - _startTime;
	}

	public void reset() {
		_maxTime = 0;
		_minTime = 0;
		_lastTime = 0;
		_startTime = System.currentTimeMillis();
		_lastSampleTime = _startTime;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setLastSampleTime(long lastSampleTime) {
		_lastSampleTime = lastSampleTime;
	}

	public void setLastTime(long lastTime) {
		_lastTime = lastTime;
	}

	public void setLowerBound(long lowerBound) {
		_lowerBound = lowerBound;
	}

	public void setMaxTime(long maxTime) {
		_maxTime = maxTime;
	}

	public void setMinTime(long minTime) {
		_minTime = minTime;
	}

	public void setStartTime(long startTime) {
		_startTime = startTime;
	}

	public void setUpperBound(long upperBound) {
		_upperBound = upperBound;
	}

	private String _description;
	private long _lastSampleTime;
	private long _lastTime;
	private long _lowerBound;
	private long _maxTime;
	private long _minTime;
	private String _name;
	private long _startTime;
	private long _upperBound;

}