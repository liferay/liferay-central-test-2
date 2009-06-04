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
 * <a href="RollingAverageStatistics.java.html"><b><i>View Source</i></b></a>
 *
 * @author Rajesh Thiagarajan
 */
public class RollingAverageStatistics extends BaseStatistics {

	public RollingAverageStatistics(String name) {
		super(name);
		_counter = new CountStatistics(name);
	}

	public void addDuration(long sampleDuration) {
		_counter.incrementCount();
		setLastTime(sampleDuration);

		if (getMaxTime() < sampleDuration) {
			setMaxTime(sampleDuration);
		}
		else if ((getMinTime() == 0) || (getMinTime() > sampleDuration)) {
			setMinTime(sampleDuration);
		}

		if (_rollingAvgTime == 0) {
			_rollingAvgTime = sampleDuration;
		}
		else {
			long span;
			long currentCount = _counter.getCount();
			// span should be at least LowerBound
			if (_counter.getCount() < getLowerBound()) {
				span = getLowerBound();
			}
			else {
				span = currentCount;
			}

			// span can be max UpperBound
			if ((currentCount > getUpperBound())) {
				span = getUpperBound();
			}
			else {
				span = currentCount;
			}

			_rollingAvgTime =
				(_rollingAvgTime * span + sampleDuration) / (span + 1);
		}

		setLastSampleTime(System.currentTimeMillis());
	}

	public long getRollingAvgTime() {
		return _rollingAvgTime;
	}

	@Override
	public void reset() {
		super.reset();
		_rollingAvgTime = 0;
	}
	
	private long _rollingAvgTime;
	private CountStatistics _counter;
}