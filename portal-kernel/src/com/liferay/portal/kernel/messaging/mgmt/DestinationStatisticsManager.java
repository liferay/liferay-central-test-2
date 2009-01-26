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

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationStatistics;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.util.Date;

/**
 * <a href="DestinationStatisticsMBeanImpl.java.html"><b><i>View
 * Source</i></b></a>
 *
 * MBean to report stats on the workings of a MessagingDestination including:
 * a) messages sent
 * b) messages pending
 * c) active threads
 * d) total current threads
 * e) highest thread count
 * f) maximum pool size
 * g) minimum pool size.
 *
 * Since gathering stats does incur a performance cost, this MBean does not
 * refresh stats automatically.  You may (de)activate the auto refresh by
 * invoking the setAutoRefresh(value) control.  You may manually refresh
 * the statistics by invoking refresh
 *
 *
 * @author Michael C. Han
 */
public class DestinationStatisticsManager
        implements DestinationStatisticsManagerMBean {

    public DestinationStatisticsManager(Destination destination) {
        _destination = destination;
    }

    public int getActiveThreadCount() {
        if (_autoRefresh) {
            refresh();
        }
        return _statistics.getActiveThreadCount();
    }

    public boolean getAutoRefresh() {
        return _autoRefresh;
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
        return new Date(_lastRefresh).toString();
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
            _statistics = _destination.getStatistics();
            _lastRefresh = System.currentTimeMillis();
        }
    }

    public void setAutoRefresh(boolean value) {
        _autoRefresh = value;
    }

    static ObjectName createObjectName(String destinationName) {
        try {
            return new ObjectName(
                    "com.liferay.portal.kernel.messaging:type=MessagingStatistics,name="
                            + destinationName);
        } catch (MalformedObjectNameException e) {
            throw new IllegalStateException(e);
        }
    }

    
    private boolean _autoRefresh;
    private Destination _destination;
    private long _lastRefresh;
    private DestinationStatistics _statistics;
}
