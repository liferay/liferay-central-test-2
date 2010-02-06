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

package com.liferay.portal.kernel.messaging;

/**
 * <a href="DestinationStatistics.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class DestinationStatistics {

	public int getActiveThreadCount() {
		return _activeThreadCount;
	}

	public int getCurrentThreadCount() {
		return _currentThreadCount;
	}

	public int getLargestThreadCount() {
		return _largestThreadCount;
	}

	public int getMaxThreadPoolSize() {
		return _maxThreadPoolSize;
	}

	public int getMinThreadPoolSize() {
		return _minThreadPoolSize;
	}

	public long getPendingMessageCount() {
		return _pendingMessageCount;
	}

	public long getSentMessageCount() {
		return _sentMessageCount;
	}

	public void setActiveThreadCount(int activeThreadCount) {
		_activeThreadCount = activeThreadCount;
	}

	public void setCurrentThreadCount(int currentThreadCount) {
		_currentThreadCount = currentThreadCount;
	}

	public void setLargestThreadCount(int largestThreadCount) {
		_largestThreadCount = largestThreadCount;
	}

	public void setMaxThreadPoolSize(int maxThreadPoolSize) {
		_maxThreadPoolSize = maxThreadPoolSize;
	}

	public void setMinThreadPoolSize(int minThreadPoolSize) {
		_minThreadPoolSize = minThreadPoolSize;
	}

	public void setPendingMessageCount(long pendingMessageCount) {
		_pendingMessageCount = pendingMessageCount;
	}

	public void setSentMessageCount(long sentMessageCount) {
		_sentMessageCount = sentMessageCount;
	}

	private int _activeThreadCount;
	private int _currentThreadCount;
	private int _largestThreadCount;
	private int _maxThreadPoolSize;
	private int _minThreadPoolSize;
	private long _pendingMessageCount;
	private long _sentMessageCount;

}