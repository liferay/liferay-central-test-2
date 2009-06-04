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

/**
 * <a href="RequestStatistics.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class RequestStatistics implements Statistics {

	public RequestStatistics(String name) {
		_name = name;
		_errorStatistics = new CountStatistics(name);
		_timeCountStatistics = new CountStatistics(name);
		_successStatistics = new RollingAverageStatistics(name);
	}

	public String getDescription() {
		return _description;
	}

	public CountStatistics getErrorStatistics() {
		return _errorStatistics;
	}

	public String getName() {
		return _name;
	}

	public RollingAverageStatistics getSuccessStatistics() {
		return _successStatistics;
	}

	public CountStatistics getTimeCountStatistics() {
		return _timeCountStatistics;
	}

	public void setDescription(String description) {
		_description = description;
	}

	private String _description;
	private CountStatistics _errorStatistics;
	private String _name;
	private RollingAverageStatistics _successStatistics;
	private CountStatistics _timeCountStatistics;
}
