/*
 * Copyright (c) 2008, Your Corporation. All Rights Reserved.
 */

package com.liferay.portal.kernel.messaging;

import java.io.Serializable;

/**
 * <a href="MessagingStatistics.java.html"><b><i>View Source</i></b></a>
 *
 * Statistics to keep track of messaging performance
 * 
 * @author Michael C. Han
 */
public class DestinationStatistics implements Serializable {


	public DestinationStatistics(long sentMessageCount, long pendingMessageCount,
							   int activeThreadCount, int currentThreadCount,
							   int largestThreadCount, int maxThreadPoolSize,
							   int minThreadPoolSize) {
		_sentMessageCount = sentMessageCount;
		_pendingMessageCount = pendingMessageCount;
		_activeThreadCount = activeThreadCount;
		_currentThreadCount = currentThreadCount;
		_largestThreadCount = largestThreadCount;
		_maxThreadPoolSize = maxThreadPoolSize;
		_minThreadPoolSize = minThreadPoolSize;
	}

    /**
	 *
	 * @return total number of messages sent by destination
	 */
	public long getSentMessageCount() {
		return _sentMessageCount;
	}

	/**
	 *
	 * @return messages pending to be sent
	 */
	public long getPendingMessageCount() {
		return _pendingMessageCount;
	}

	/**
	 *
	 * @return total number of threads processing messages
	 */
	public int getActiveThreadCount() {
		return _activeThreadCount;
	}

	/**
	 *
	 * @return current number of threads in pool, idle + active
	 */
	public int getCurrentThreadCount() {
		return _currentThreadCount;
	}

	/**
	 *
	 * @return maximum number of threads that were in the pool
	 */
	public int getLargestThreadCount() {
		return _largestThreadCount;
	}

	/**
	 *
	 * @return maximum number of threads that can possibility be in the pool
	 */
	public int getMaxThreadPoolSize() {
		return _maxThreadPoolSize;
	}

	/**
	 *
	 * @return minimum number of threads in the pool
	 */
	public int getMinThreadPoolSize() {
		return _minThreadPoolSize;
	}
	
	private long _sentMessageCount;
	private long _pendingMessageCount;
	private int _activeThreadCount;
	private int _currentThreadCount;
	private int _largestThreadCount;
	private int _maxThreadPoolSize;
	private int _minThreadPoolSize;
	
}
