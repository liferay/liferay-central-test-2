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
 * <a href="AverageStatistics.java.html"><b><i>View Source</i></b></a>
 *
 * <a href="RollingAverageStatistics.java.html"><b><i>View Source</i></b></a>
 *
 * @author Rajesh Thiagarajan
 * @author Brian Wing Shun Chan
 */
public class AverageStatistics extends BaseStatistics {

	public AverageStatistics(String name) {
		super(name);

		_countStatistics = new CountStatistics(name);
	}

	public void addDuration(long duration) {
		_countStatistics.incrementCount();

		setLastTime(duration);

		if (getMaxTime() < duration) {
			setMaxTime(duration);
		}
		else if ((getMinTime() == 0) || (getMinTime() > duration)) {
			setMinTime(duration);
		}

		if (_averageTime == 0) {
			_averageTime = duration;
		}
		else {
			long span = 0;

			if (_countStatistics.getCount() < getLowerBound()) {
				span = getLowerBound();
			}
			else if ((_countStatistics.getCount() > getUpperBound())) {
				span = getUpperBound();
			}
			else {
				span = _countStatistics.getCount();
			}

			_averageTime = (_averageTime * span + duration) / (span + 1);
		}

		setLastSampleTime(System.currentTimeMillis());
	}

	public long getAverageTime() {
		return _averageTime;
	}

	public long getCount() {
		return _countStatistics.getCount();
	}

	public void reset() {
		super.reset();

		_averageTime = 0;
	}

	private long _averageTime;
	private CountStatistics _countStatistics;

}