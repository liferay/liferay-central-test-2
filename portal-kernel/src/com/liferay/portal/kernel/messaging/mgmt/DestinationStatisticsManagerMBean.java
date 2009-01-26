/*
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.messaging.mgmt;

/**
 * <a href="MessagingStatisticsMBean.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public interface DestinationStatisticsManagerMBean {

	/**
	 *
	 * @return total number of threads processing messages
	 */
	public int getActiveThreadCount();

	/**
	 *
	 * @return current number of threads in pool, idle + active
	 */
	public int getCurrentThreadCount();

	/**
	 *
	 * @return maximum number of threads that were in the pool
	 */
	public int getLargestThreadCount();

    /**
     *
     * @return last time the statistics was refreshed
     */
    public String getLastRefresh();

    /**
     *
     * @return minimum number of threads in the pool
     */
    public int getMinThreadPoolSize();

	/**
	 *
	 * @return maximum number of threads that can possibility be in the pool
	 */
	public int getMaxThreadPoolSize();

	/**
	 *
	 * @return messages pending to be sent
	 */
	public long getPendingMessageCount();


    /**
	 *
	 * @return total number of messages sent by destination
	 */
	public long getSentMessageCount();

    /**
     *
     * @return true if the statistics automatically refreshes
     */
    public boolean isAutoRefresh();

    /**
     * Refresh statistics
     */
    public void refresh();


    /**
     * @param value to set the auto refreshing of statistics  
     */
    public void setAutoRefresh(boolean value);
}
