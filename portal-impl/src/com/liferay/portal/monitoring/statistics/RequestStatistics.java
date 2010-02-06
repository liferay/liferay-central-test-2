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

package com.liferay.portal.monitoring.statistics;

/**
 * <a href="RequestStatistics.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class RequestStatistics implements Statistics {

	public RequestStatistics(String name) {
		_name = name;
		_errorStatistics = new CountStatistics(name);
		_successStatistics = new AverageStatistics(name);
		_timeoutStatistics = new CountStatistics(name);
	}

	public long getAverageTime() {
		return _successStatistics.getAverageTime();
	}

	public String getDescription() {
		return _description;
	}

	public long getErrorCount() {
		return _errorStatistics.getCount();
	}

	public long getMaxTime() {
		return _successStatistics.getMaxTime();
	}

	public long getMinTime() {
		return _successStatistics.getMinTime();
	}

	public String getName() {
		return _name;
	}

	public long getRequestCount() {
		return getErrorCount() + getSuccessCount() + getTimeoutCount();
	}

	public long getSuccessCount() {
		return _successStatistics.getCount();
	}

	public long getTimeoutCount() {
		return _timeoutStatistics.getCount();
	}

	public void incrementError() {
		_errorStatistics.incrementCount();
	}

	public void incrementSuccessDuration(long duration) {
		_successStatistics.addDuration(duration);
	}

	public void incrementTimeout() {
		_timeoutStatistics.incrementCount();
	}

	public void reset() {
		_errorStatistics.reset();
		_successStatistics.reset();
		_timeoutStatistics.reset();
	}

	public void setDescription(String description) {
		_description = description;
	}

	private String _description;
	private CountStatistics _errorStatistics;
	private String _name;
	private AverageStatistics _successStatistics;
	private CountStatistics _timeoutStatistics;

}